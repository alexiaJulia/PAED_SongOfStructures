package RTree;

import Tree.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadFromFileRTree {
    static String name_file = "datasets/RTree/rtree";
    static int numNodes;

    public static NodeR[] readNodesFromFile(String file_name) {
        NodeR[] nodes = null;
        String full_path = name_file + file_name;
        try {
            List<String> lines = Files.readAllLines(Path.of(full_path));
            numNodes = Integer.parseInt(lines.get(0));
            nodes = new NodeR[numNodes];
            lines.removeFirst();
            int i = 0;
            for (String line : lines) {
                if(i == numNodes){
                    break;
                }
                String[] parts = line.split(";");
                nodes[i] = new NodeR(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]),
                        Boolean.parseBoolean(parts[5]),
                        parts[6]
                );
                i++;
            }
        } catch (IOException e) {
            System.err.println("Can't open file");
        }
        return nodes;
    }
}