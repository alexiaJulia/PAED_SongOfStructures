package Graf;

public class Edge implements Comparable<Edge>{
    public Vertex vertexA;
    public Vertex vertexB;

    public Climate climate;
    public float dist;
    public int tmpIdeal;
    public int tmpAdvers;

    public Edge (int idA, int idB, String climaStr, float km, int tmpIdeal, int tmpAdv, Vertex[] arrayVertexs) {
        assignVertexsToEdge (arrayVertexs, idA, idB);
        switch (climaStr) {
            case "ARID":
                this.climate = Climate.ARID;
                break;
            case "POLAR":
                this.climate = Climate.POLAR;
                break;
            case "TEMPERATE":
                this.climate = Climate.TEMPERATE;
                break;
        }
        this.dist = km;
        this.tmpIdeal = tmpIdeal;
        this.tmpAdvers = tmpAdv;
    }

    public static Edge buildEdgesFromFile (String line, Vertex[] arrayVertexs) {
        String[] partes = line.split(";");
        return new Edge (Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), partes[2], Float.parseFloat(partes[3]), Integer.parseInt(partes[4]), Integer.parseInt(partes[5]), arrayVertexs);
    }

    public void assignVertexsToEdge (Vertex[] arrayVertexs, int idA, int idB) {
        int found = 0;
        for (Vertex vertex : arrayVertexs) {
            if (vertex.id == idA) {
                vertexA = vertex;
                found++;
            }
            if (vertex.id == idB) {
                vertexB = vertex;
                found++;
            }
            if (found == 2) {
                break;
            }
        }
    }

    @Override
    public int compareTo(Edge other) {
        return (int) (this.dist - other.dist); // Ordenar de menor a major pes
    }
}
