import Graf.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class ControllerGraph {
    public Graf routes;
    public Edge[] edges;
    public Vertex[] vertexs;
    public Scanner sc;

    public ControllerGraph(String dataset, Scanner sc) {
        vertexs = ReadFromFile.readVertexsFromFile(dataset);
        QuickSort.quickSort(vertexs, 0, vertexs.length - 1);
        edges = ReadFromFile.readEdgesFromFile(vertexs, dataset);
        Graf g = new Graf();
        routes = g.createGraf(edges, vertexs);
        this.sc = sc;
    }

    public void assentamentsRegionals(){
        String faction;
        DFS dfs = new DFS();
        do {
            System.out.print("Facció (GRAPHADIA/ETREELIA/RECTANIA/HASHPERIA) a consultar: ");
            faction = Controller.sc.nextLine().toUpperCase();

            if (!comprovarFacció(faction)) {
                System.out.println("La facció ingresada no és vàlida. Torna-ho a intentar.\n");
            }
        } while(!comprovarFacció(faction));
        ArrayList<Vertex> vertex = dfs.DFS(Graf.createSubgrafByRegion(edges, vertexs, faction));

        if(vertex.isEmpty()){
            System.out.println("La facció no té llocs.");
        } else {
            System.out.println("\nLa regió més gran que controlen està formada pels següents " + vertex.size() + " llocs:\n");
            for(Vertex v : vertex) {
                System.out.println(v.id + " - " + v.name + " (" + v.faccion + ")");
            }
        }
    }

    public void mapaSimplificat(){
        System.out.println("El mapa simplificat està format per les següents rutes:\n");
        Kruskal kr = new Kruskal();
        LinkedList<Edge> routesList = kr.mstKruskal(new Graf(routes), edges);
        for (Edge aresta: routesList){
            System.out.println("Ruta " + aresta.vertexA.id + "-" + aresta.vertexB.id + ": " + aresta.vertexA.name + " - " + aresta.vertexB.name + " (" +aresta.dist +"km)");
        }
    }

    public void simulacioViatge (){
        String id;
        String season;
        boolean season_valid;
        int idOrigin;
        int idDestiny;

        Dijkstra djk = new Dijkstra(routes);

        do{
            System.out.print("Identificador del lloc origen: ");
            id = sc.nextLine();
            idOrigin = validId(id);
        } while (idOrigin == -1);
        do{
            System.out.print("Identificador del lloc destí: ");
            id = sc.nextLine();
            idDestiny = validId(id);
        } while (idDestiny == -1); //se tendrá que mirar que no sea el mismo supongo

        do {
            System.out.print("Estació de l’any (SPRING/SUMMER/AUTUMN/WINTER): ");
            season = sc.nextLine();
            switch (season) {
                case "SPRING":
                case "SUMMER":
                case "AUTUMN":
                case "WINTER":
                    season_valid = true;
                    break;
                default:
                    season_valid = false;
                    System.out.println("Estació no vàlida. Torna-ho a intentar.");
            }
        } while (!season_valid);


        LinkedList<Vertex> cami = djk.calculatePath(idOrigin, idDestiny, season);
        if(cami == null){
            System.out.println("We could not find a possible path, the ids may be disconnected");
        }else {
            int[] minuts = new int[cami.size() - 1];
            int minutsTotal = 0;
            for (int i = 0; i < cami.size() - 1; i++) {
                minuts[i] = routes.calculateTime(routes.getEdgeFromIds(cami.get(i).id, cami.get(i + 1).id), season);
                minutsTotal += minuts[i];
            }
            System.out.println("\nEl temps mínim per anar del lloc " + idOrigin + " al " + idDestiny + " durant "
                    + season + " són " + minutsTotal + " minuts.");
            System.out.println("Cal seguir el següent camí:\n");
            System.out.println(cami.get(0).id + " - " + cami.get(0).name + " (" + cami.get(0).faccion + ")");
            for (int i = 0; i < minuts.length; i++) {
                System.out.println("::: +" + minuts[i] + " minuts :::");
                System.out.println(cami.get(i + 1).id + " - " + cami.get(i + 1).name + " (" + cami.get(i + 1).faccion + ")");
            }
        }
    }

    public int validId(String id){
        try{
            int idInt = Integer.parseInt(id);
            for(int i = 0; i < routes.vertexs.length; i++){
                if(idInt == routes.vertexs[i].id){
                    return idInt;
                }
            }
            System.out.println("L'identificador introduït no existeix. Torna-ho a intentar.");
            return -1;
        } catch (NumberFormatException e) {
            System.out.println("L'identificador d'un lloc està format únicament per números. Torna-ho a intentar.");
            return -1;
        }
    }

    public static boolean comprovarFacció(String f){
        return switch (f) {
            case "GRAPHADIA" -> true;
            case "ETREELIA" -> true;
            case "RECTANIA" -> true;
            case "HASHPERIA" -> true;
            default -> false;
        };
    }
}
