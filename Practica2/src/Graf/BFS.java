package Graf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    public Graf graf;

    BFS(){}

    public LinkedList<Vertex> cridaBFS(Graf graf, Vertex vertex) {
        this.graf = graf;
        LinkedList<Vertex> assentaments;
        LinkedList<Vertex> solution;
        assentaments = BFS(vertex);
        solution = assentaments;
        return solution;
    }

    public LinkedList<Vertex> BFS(Vertex initialVertex){
        Queue<Vertex> cua = new LinkedList();
        cua.add(initialVertex);
        initialVertex.visited = true;
        Vertex actual;
        ArrayList<Vertex> adjacents;
        LinkedList<Vertex> vertexs = new LinkedList<>();
        while (!cua.isEmpty()){
            actual = cua.poll();
            vertexs.add(actual);
            adjacents = graf.findAllAdjacentsFromId(actual.id);
            for(Vertex adj: adjacents){
                if (!adj.visited){ //solo lo añadimos si no visitado
                    cua.add(adj);
                    adj.visited = true;

                }
            }
        }
        return vertexs;
    }

}
