package Graf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ReadFromFile {
    static String name_file = "datasets/graphs/graphs"; //"C:\\Users\\User\\Desktop\\Practica2\\Practica2\\Datasets\\GrafS";
    public static int numVertexs;

    public static Vertex[] readVertexsFromFile(String filename) {
        Vertex[] vertexs = null;
        String full_path = name_file + filename;
        try {
            List<String> lines = Files.readAllLines(Path.of(full_path));
            numVertexs = Integer.parseInt(lines.get(0));
            vertexs = new Vertex[numVertexs];
            lines.removeFirst();
            int i = 0;
            for (String linea : lines) {
                if(i == numVertexs){
                    break;
                }
                vertexs[i] = Vertex.buildVertexFromFile(linea);
                i++;
            }
        } catch (IOException e) {
            System.err.println("Can't open file");
        }
        return vertexs;
    }

    public static Edge[] readEdgesFromFile(Vertex[] vertexs, String dataset) {
        String full_path = name_file + dataset;
        Edge[] arestas = null;
        try {
            List<String> lineas = Files.readAllLines(Path.of(full_path));
            int num_arestas = Integer.parseInt(lineas.get(numVertexs+1));
            arestas = new Edge[num_arestas];

            int i = 0;
            for (int j = numVertexs + 2; j < lineas.size(); j++) {
                if(i == num_arestas){
                    break;
                }
                arestas[i] = Edge.buildEdgesFromFile(lineas.get(j), vertexs);
                i++;
            }
        } catch (IOException e) {
            System.err.println("Can't open file");
        }
        return arestas;
    }

}
