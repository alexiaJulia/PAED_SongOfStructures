package Graf;


import java.util.*;

public class Kruskal {
    public BFS bfs;
    public LinkedList<Edge> mstKruskal(Graf mst, Edge[] arestas) {

        bfs = new BFS();
        PriorityQueue<Edge> lista = new PriorityQueue<>();
        LinkedList<Edge> list = new LinkedList<>();
        for (Edge aresta : arestas) {
            lista.offer(aresta);
        }
        int i = 0;
        while (!lista.isEmpty() && i < mst.vertexs.length -1) {
            //que mst no sea spanning tree es que tingui el minim numero d'arestes possible, amb les arestes de menor pes en total
            Edge edge = lista.poll();

            if (checkSubgraf(mst, edge)){
                mst.addEdge(edge);
                list.addLast(edge);
                i++;
            }
        }
        return list;
    }

    private boolean checkSubgraf(Graf mst, Edge Edge) {
        mst.unvisitVertexsFromGraph();
        LinkedList<Vertex> lista = bfs.cridaBFS(mst, Edge.vertexA);
        for (Vertex v : lista) {
            if (v.id == Edge.vertexB.id) {
                return false;
            }
        }
        return true;
    }

    //si el bfs fem un subgraf amb només lo de una faccio podem aprofitar el bfs y tal
    public Kruskal(){}

}
