package Graf;

import java.util.ArrayList;
import java.util.LinkedList;

public class Dijkstra {
    public Graf graf;

    public Dijkstra(Graf graf) {
        this.graf = graf;
    }

    public LinkedList<Vertex> calculatePath (int idOrigin, int idDestination, String station){

        Vertex[] paths = new Vertex[graf.matrix.length];
        int[] times = new int[graf.matrix.length];
        for (int i = 0; i < graf.matrix.length; i++) {
            times[i] = Integer.MAX_VALUE;
        }
        graf.unvisitVertexsFromGraph();
        int indexIdOrigin = graf.getIndexFromId(idOrigin);
        paths[indexIdOrigin] = graf.vertexs[indexIdOrigin];
        times[indexIdOrigin] = 0;
        Vertex actual = graf.vertexs[graf.getIndexFromId(idOrigin)];
        for(int i = 0; i < graf.vertexs.length; i++) {
            if (!graf.vertexs[graf.getIndexFromId(idDestination)].visited && !graf.vertexs[i].visited) {
                ArrayList<Vertex> successors = graf.findAllAdjacentsFromId(actual.id);
                for (Vertex s : successors) {
                    if (!s.visited) {
                        Edge a = graf.getEdgeFromIds(actual.id, s.id);
                        if(a != null) {
                            int nouTemps = times[graf.getIndexFromId(actual.id)] + calculateNewTime(a, station);
                            //condicional per un cas que no és viable (serà negatiu)
                            if(nouTemps < 0){
                                continue;
                            }
                            int indexIdS = graf.getIndexFromId(s.id);
                            if (nouTemps < times[indexIdS]) {
                                times[indexIdS] = nouTemps;
                                paths[indexIdS] = actual;
                            }
                        }
                    }
                }
                actual.visited = true;
                actual = chooseActual(times);
            }
        }
        return rebuild(paths, idOrigin, idDestination);
    }

    private int calculateNewTime (Edge a, String season) {
        int temps = 0;
        switch(a.climate.toString()){
            case "ARID":
                switch(season){
                    case "SUMMER":
                        temps = Integer.MIN_VALUE;
                        break;
                    case "SPRING":
                        temps = a.tmpAdvers;
                        break;
                    case "AUTUMN":
                        temps = a.tmpIdeal;
                        break;
                    case "WINTER":
                        temps = a.tmpIdeal;
                        break;
                }
                break;
            case "POLAR":
                switch(season){
                    case "SUMMER":
                        temps = a.tmpIdeal;
                        break;
                    case "SPRING":
                        temps = a.tmpIdeal;
                        break;
                    case "AUTUMN":
                        temps = a.tmpAdvers;
                        break;
                    case "WINTER":
                        temps = Integer.MIN_VALUE;
                }
                break;
            case "TEMPERATE":
                switch(season){
                    case "SUMMER":
                        temps = a.tmpAdvers;
                        break;
                    case "SPRING":
                        temps = a.tmpIdeal;
                        break;
                    case "AUTUMN":
                        temps = a.tmpIdeal;
                        break;
                    case "WINTER":
                        temps = a.tmpAdvers;
                        break;
                }
                break;
        }
        return temps;
    }

    private LinkedList<Vertex> rebuild(Vertex[] paths, int idOrigin, int idDestination) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex actual = graf.vertexs[graf.getIndexFromId(idDestination)];
        while (actual.id != idOrigin) {
            int indexIdActual = graf.getIndexFromId(actual.id);
            if (paths[indexIdActual] != null) {
                path.addFirst(actual);
                actual = paths[indexIdActual];
            }else{
                return null;
            }
        }
        path.addFirst(paths[graf.getIndexFromId(idOrigin)]);
        return path;
    }

    private Vertex chooseActual(int[] times) {
        int minTemps = Integer.MAX_VALUE;
        int index = -1;
        for(int i = 0; i < times.length; i++){
            if(times[i] < minTemps && !graf.vertexs[i].visited){
                minTemps = times[i];
                index = i;
            }
        }
        return graf.vertexs[index];
    }


}


