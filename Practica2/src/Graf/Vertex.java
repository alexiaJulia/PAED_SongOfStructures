package Graf;

public class Vertex {
    public int id;
    public String name;
    public Faccion faccion;
    public boolean visited;

    public Vertex(int id, String name, String faccionStr) {
        this.id = id;
        this.name = name;
        switch (faccionStr) {
            case "GRAPHADIA":
                faccion = Faccion.GRAPHADIA;
                break;
            case "ETREELIA":
                faccion = Faccion.ETREELIA;
                break;
            case "RECTANIA":
                faccion = Faccion.RECTANIA;
                break;
            default:
                faccion = Faccion.HASHPERIA;
                break;
        }
    }

    public static Vertex buildVertexFromFile(String line) {
        String[] partes = line.split(";");
        return new Vertex(Integer.parseInt(partes[0]), partes[1], partes[2]);
    }
}

