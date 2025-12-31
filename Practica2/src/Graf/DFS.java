package Graf;

import java.util.ArrayList;
import java.util.Stack;

public class DFS {
    public Graf graf;

    public DFS() {}
    // Funcionalitat 1: assentamentsRegionals.

    /*
     * Implementació Graf.DFS. Troba el grup més gran de vèrtex connectats que pertànyen a una mateixa facció.
     *
     */
    public ArrayList<Vertex> DFS (Graf graf){
        this.graf = graf;
        graf.unvisitVertexsFromGraph(); //Reinicio tots els vèrtexs com no visitats.
        ArrayList<Vertex> biggestVertexConnection = new ArrayList<>(); //Es guardarà el major nombre de vèrtexs d'aquella facció.
        for(int i = 0; i < graf.vertexs.length; i++) { //Recorro tots els vèrtexs de l'array.
            if (graf.vertexs[i] != null) {
                if (!graf.vertexs[i].visited) { //Si el vèrtex encara no ha estat visitat.
                    ArrayList<Vertex> actualVertexConnection = DFS(0); //Retorna llista de vèrtexs connectas al present vèrtex que pertanyin a la mateixa facció.
                    if (biggestVertexConnection.size() < actualVertexConnection.size()) {
                        biggestVertexConnection = actualVertexConnection; //Si la regió actual explorada és major a la que tenia, me la guardo!
                    }
                }
            }
        }
        return biggestVertexConnection;
    }

    /*
     * Algorisme Graf.DFS.
     *
     */
    private ArrayList<Vertex> DFS (int pos){
        ArrayList<Vertex> vertex = new ArrayList<>();
        Stack<Vertex> stack = new Stack<>();

        stack.push(graf.vertexs[pos]);
        graf.vertexs[pos].visited = true;

        while(!stack.isEmpty()){
            Vertex v = stack.pop();
            vertex.add(v); //Quan exploro els seus nodes adjecents, el trec de la pila i ja l'inclueixo com a part de l'array.
            //contador de lugares conectados + devolver lista de ids para printar
            ArrayList<Vertex> successors = graf.findAllAdjacentsFromId(v.id);
            for(Vertex s : successors){
                if(!s.visited){
                    stack.push(s); //Troba successors amb mateixa facció i els introdueix a la pila.
                    s.visited = true; //Els marca com a visitats.
                }
            }
        }
        return vertex;
    }

// Fi de funcions relacionades amb assentamentsRegionals
}
