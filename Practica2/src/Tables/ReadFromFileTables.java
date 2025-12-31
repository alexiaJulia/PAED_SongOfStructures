package Tables;

import Tables.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ReadFromFileTables {
    static String name_file = "datasets/Tables/tables";
    static int numProductions;

    public static Production[] readProductionsFromFile(String file_name) {
        Production[] productions = null;
        String full_path = name_file + file_name;
        try {
            List<String> lines = Files.readAllLines(Path.of(full_path));
            numProductions = Integer.parseInt(lines.get(0));
            productions = new Production[numProductions];
            lines.removeFirst();
            int i = 0;
            for (String line : lines) {
                if(i == numProductions){
                    break;
                }
                String[] parts = line.split(";");
                productions[i] = new Production(
                        parts[0],
                        parts[1],
                        Integer.parseInt(parts[2])
                );
                i++;
            }
        } catch (IOException e) {
            System.err.println("Can't open file " + full_path);
        }
        return productions;
    }
}