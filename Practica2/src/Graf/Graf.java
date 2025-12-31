package Graf;

import java.util.ArrayList;

public class Graf {
    public ArrayList<Edge>[] matrix;
    public Vertex[] vertexs;

    /*
    * Funció que crea una instància de la classe graf.
     */
    public Graf() {}

    /*
     * Funció que rep array de verèxs i arestes i crea matriu d'adjecències.
     *
     */
    public Graf createGraf(Edge[] edges, Vertex[] vertexs) {

        this.matrix = new ArrayList[vertexs.length];
        this.vertexs = vertexs;

        int aux = 0, check;
        for (int i = 0; i < this.matrix.length; i++) {
            ArrayList<Edge> edgeList = new ArrayList<>();
            for (int j = 0; j < aux; j++) {
                edgeList.add(null);
            }
            edgeList.add(null);
            matrix[i] = edgeList;
            aux++;
        }
        for (int i = 0; i < edges.length; i++) { //Per tots i cadascun dels vertexs.
//            ArrayList<Edge> edgeList = new ArrayList<>();
//            for (int j = 0; j < aux; j++) { //Incremento en 1 cada cop, matriu triangular, part inferior.
//                check = checkEdgeConnection(j, aux, edges, vertexs); //Aqui no hauria de ser aux-1???
//                if (check == -1) {
//                    edgeList.add(null); //Si no són adjacents poso null.
//                } else {
//                    edgeList.add(edges[check]); //Si són adjacents poso l'Edge que els uneix.
//                } //cambiar perque per cada aresta busca lo que junta, tambe la cerca binaria al buscar un circulito con el id i tal
//            }
//            edgeList.add(null);
//            graf.matrix[i] = edgeList; //Afegeixo a la posició de la matriu, els vèrtexs adjacents.
//            aux++;
            addEdge(edges[i]);
        }
        return this;
    }

    public static Graf createSubgrafByRegion(Edge[] edges, Vertex[] vertexs, String faction) {
        ArrayList<Vertex> vertexsOfRegion= new ArrayList<Vertex>();
        ArrayList<Edge> edgesThatInterconnectRegion = new ArrayList<Edge>();

        for (int i = 0; i < vertexs.length; i++) {
            if ((vertexs[i].faccion).toString().equals(faction)) {
                vertexsOfRegion.add(vertexs[i]);
            }
        }

        for (int i = 0; i < edges.length; i++) {
            if (edges[i].vertexA.faccion.toString().equals(faction) && edges[i].vertexB.faccion.toString().equals(faction)) {
                edgesThatInterconnectRegion.add(edges[i]);
            }
        }

        return new Graf().createGraf(edgesThatInterconnectRegion.toArray(new Edge[0]), vertexsOfRegion.toArray(new Vertex[0]));
    }

    //Funció per copiar un graf amb tots els vertex però la matriu buida.
    public Graf (Graf g) {
        this.matrix = new ArrayList[g.vertexs.length];
        int aux = 0;
        for (int i = 0; i < g.matrix.length; i++) {
            ArrayList<Edge> edgeList = new ArrayList<>();
            for (int j = 0; j < aux; j++) {
                edgeList.add(null);
            }
            edgeList.add(null);
            this.matrix[i] = edgeList;
            aux++;
        }
        this.vertexs = g.vertexs.clone();
    }

    /*
     * Funció que determina si dos vèrtexs són adjacents.
     *
     */
    public static int checkEdgeConnection(int posVert1, int posVert2, Edge[] edges, Vertex[] vertexs) {
        for(int i = 0; i < edges.length; i++) { //Recorre totes les arestes per a identificar si alguna connecta els nodes.
            if ((vertexs[posVert1].id == edges[i].vertexA.id && vertexs[posVert2].id == edges[i].vertexB.id)
                    ||(vertexs[posVert2].id == edges[i].vertexA.id && vertexs[posVert1].id == edges[i].vertexB.id)) {
                return i;
            }
        }
        return -1;
    }

    /*
     * Retorna el index a l'array de vertexs del vèrtex que coincideix amb el id passat per paràmetre.
     *
     */
    public int getIndexFromId(int id) {
        int esquerra = 0;
        int dreta = vertexs.length - 1;
        int meitat = 0;
        while (esquerra <= dreta) {
            meitat = (esquerra + dreta) / 2;
            if (id > vertexs[meitat].id) {
                esquerra = meitat + 1;
            } else if (id < vertexs[meitat].id) {
                dreta = meitat - 1;
            } else {
                return meitat;
            }
        }
        return -1;
    }

    private ArrayList<Edge> findRoutesFromHigherId(int id){
        int index = getIndexFromId(id);
        ArrayList<Edge> routes = new ArrayList<>();
        for(int i = 0; i < index; i++){
            if(matrix[index] != null) {
                if (matrix[index].get(i) != null) {
                    routes.add(matrix[index].get(i));
                }
            }
        }
        return routes;
    }


    /*
     * Retorna tots els vèrtexs adjacents d'un vèrtex. Mateixa facció? Necessitat?
     *
     */
    public ArrayList<Vertex> findAllAdjacentsFromId (int id){
        int index = getIndexFromId(id); //Cerca el índex del vèrtex proporcionat
        ArrayList<Vertex> successors = new ArrayList<>();

        if(index == -1){
            return null;
        }

        for(int i = 0; i < index; i++){
            if(matrix[index].get(i) != null) { //Pq index -1???
                if(matrix[index].get(i).vertexA.id == id) {
                    successors.add(matrix[index].get(i).vertexB);
                } else if (matrix[index].get(i).vertexB.id == id) {
                    successors.add(matrix[index].get(i).vertexA);
                }
            }
        }

        for(int i = index; i < matrix.length; i++) {
            if(matrix[i].get(index) != null){
                if(matrix[i].get(index).vertexA.id == id) {
                    successors.add(matrix[i].get(index).vertexB);
                } else if (matrix[i].get(index).vertexB.id == id) {
                    successors.add(matrix[i].get(index).vertexA);
                }
            }
        }
        return successors;
    } //no mirem com molts cops les faccions??

    /*
     * Funció que defineix tots els vèrtexs d'un array com a no visitats.
     */
    public void unvisitVertexsFromGraph() {
        for(Vertex vertex : vertexs){
            if(vertex != null) {
                vertex.visited = false;
            }
        }
    }

    public void addEdge(Edge Edge) {
        //si el idA es más pequeño que idB --> ponerlo en la fila de B
        //al reves, ponerlo en A
        //matrix[i].add(i,Edge);
        int indexA = getIndexFromId(Edge.vertexA.id);
        int indexB = getIndexFromId(Edge.vertexB.id);
        if(Edge.vertexA.id < Edge.vertexB.id ){
            matrix[indexB].set(indexA,Edge); //si A más pequeño que B
        } else {
            matrix[indexA].set(indexB,Edge); //si b más pequeño que A
        }
    }

    public static void showGraf(Graf graf) {
        for (int i = 0; i < graf.matrix.length; i++) {
            for (int j = 0; j < graf.matrix[i].size(); j++) {
                if(graf.matrix[i].get(j) != null) {
                    //System.out.println("From " + graf.matrix[i].get(j).vertexA.id + " to " + graf.matrix[i].get(j).vertexB.id);
                    System.out.print(1+" ");
                }else{
                    //System.out.println("The edge is null");
                    System.out.print(0+" ");
                }
            }
            System.out.println();
        }
    }

    public static void showEdges(ArrayList<Edge> matrix) {
        for (int j = 0; j < matrix.size(); j++) {
            if (matrix.get(j) != null) {
                System.out.println("From " + matrix.get(j).vertexA.id + " to " + matrix.get(j).vertexB.id);
            } else {
                System.out.println("The edge is null");
            }
        }
    }

    public void showRutes() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].size(); j++) {
                if(matrix[i].get(j) != null) {
                    System.out.println("Ruta " + matrix[i].get(j).vertexA.id + "-" + matrix[i].get(j).vertexB.id + ": " + matrix[i].get(j).vertexA.name + " - " + matrix[i].get(j).vertexB.name + " (" + matrix[i].get(j).dist +")");
                }
            }
        }
    }

    public int calculateTime (Edge a, String season) {
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

    /*
     * Retorna l'aresta que coincideix amb els dos id passats per paràmetre.
     *
     */
    public Edge getEdgeFromIds(int idA, int idB){
        if(idA < idB){
            return matrix[getIndexFromId(idB)].get(getIndexFromId(idA));
        }else{
            return matrix[getIndexFromId(idA)].get(getIndexFromId(idB));
        }
    }


}
