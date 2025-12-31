package Tree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ReadFromFileTree {
    static String name_file = "datasets/binaryTrees/tree";
    static int numNodes;

    public static Node[] readNodesFromFile(String file_name) {
        Node[] nodes = null;
        String full_path = name_file + file_name;
        try {
            List<String> lines = Files.readAllLines(Path.of(full_path));
            numNodes = Integer.parseInt(lines.get(0));
            nodes = new Node[numNodes];
            lines.removeFirst();
            int i = 0;
            for (String line : lines) {
                if(i == numNodes){
                    break;
                }
                nodes[i] = new Node(line);
                i++;
            }
        } catch (IOException e) {
            System.err.println("Can't open file");
        }
        return nodes;
    }

}
//
//package Tree;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.*;
//
//public class ReadFromFileTree {
//    static String name_file = "datasets/binaryTrees/tree";
//    static int numNodes;
//
//    public static List<String> readNodesFromFile(String file_name) {
//        Node[] nodes = null;
//        String full_path = name_file + file_name;
//        try {
//            List<String> lines = Files.readAllLines(Path.of(full_path));
//            return lines;
////            numNodes = Integer.parseInt(lines.get(0));
////            nodes = new Node[numNodes];
////            lines.removeFirst();
////            int i = 0;
////            for (String line : lines) {
////                if(i == numNodes){
////                    break;
////                }
////                nodes[i] = new Node(line);
////                i++;
////            }
//        } catch (IOException e) {
//            System.err.println("Can't open file");
//        }
//        //return nodes;
//        return null;
//    }
//
//}