package Tables;

import java.util.ArrayList;
import java.util.Arrays;

public class Table {

    Production[] table;
    private int rehash = 0;
    private int collisions = 0;
    private int insertedElements;

    public static int constant_HASH = 31;

    public Table (Production[] production){
        int length;
        if (production.length < 20){
            length = 17;
        }else{
            length = calculateTableSize(production.length);
        }
        table = new Production[length];
        insertedElements = 0;
        for (Production p : production) {
            insertProduction(p, false);
        }
    }

    public void insertProduction(Production p, boolean isResize) {
        int indexTable;

        indexTable = hash(p.getName(), table.length);
        if (table[indexTable] == null || table[indexTable].isEliminated()) {
            table[indexTable] = p; //Ya se marca solo como no eliminado, ya que hemos creado un nuevo nodo, con el campo eliminated a 0, al ser inserido.
            insertedElements++;
        } else {
            int i = 1;
            int newIndex;
            do {
                newIndex = rehash(indexTable, i, table.length);
                i++;
                rehash++;
                if (i > table.length - 10) {
                    resizeTable();
                    int newIndexTable = hash(p.getName(), table.length); //si fa un resize hem de tornar a calcular on cau
                    if (newIndexTable != indexTable) {
                        i = 1;
                        indexTable = newIndexTable;
                    }
                }
            } while (table[newIndex] != null && !table[newIndex].isEliminated() && i < table.length);
            collisions++;

            if (table[newIndex] == null || table[newIndex].isEliminated()) {
                table[newIndex] = p;
                insertedElements++;
            } else { //Control de errores.
                System.err.println("Error: tabla llena, no se pudo insertar " + p.getName()); //en teoria mai hauria d'arribar
            }
        }
        if(insertedElements >= table.length * 0.8 && !isResize){
            resizeTable();
        }
    }

        public Production eliminateProductionByKey(String key) throws RuntimeException {
        int indexTable = hash(key, table.length);
        int currentIndex = indexTable;
        int numJumps = 1;
        Production prod;

        if(!table[indexTable].getName().equals(key)){
            do {
                currentIndex = rehash(indexTable, numJumps, table.length);
                numJumps++;
                if(table[currentIndex] == null){
                    throw new RuntimeException("Error: no se ha podido encontrar " + key);
                }
            }while(!table[currentIndex].getName().equals(key));
        }
        prod = table[indexTable];
        table[currentIndex].setAsEliminated();
        return prod;
    }

    public void resizeTable() {
        Production[] auxTable = new Production[table.length];
        Production[] newTable = new Production[calculateTableSize((int) ((int)table.length*1.7))];
        System.arraycopy(table, 0, auxTable, 0, table.length);
        table = newTable;
        for (Production p : auxTable) {
            if (p != null && !p.isEliminated()) {
                insertProduction(p, true);
            }
        }

    }

    public Production getProductionByKey(String key){
        int indexTable = hash(key, table.length);
        if (table[indexTable] != null) {
            if(!table[indexTable].getName().equals(key)){
                int i = 1;
                int newIndex;
                do {
                    newIndex = rehash(indexTable, i, table.length);
                    i++;
                    if(table[newIndex] == null){
                        return null;
                    }
                }while( !table[newIndex].getName().equals(key) );

                if (!table[newIndex].isEliminated()) {
                    return table[newIndex];
                } else {
                    return null;
                }
            }

            if (!table[indexTable].isEliminated()) {
                return table[indexTable];
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private int calculateTableSize(int length) {
        int size = (int) (length / 0.7);
        while (!isPrime(size)) {
            size++;
        }
        return size;
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }


    public int hash(String str, int size)
    {
        int b     = 378551;
        int a     = 63689;
        long hash = 0;

        for(int i = 0; i < str.length(); i++)
        {
            hash = hash * a + str.charAt(i);
            a    = a * b;
            a = Math.abs(a);
            hash = hash % size;
        }

        return(int) hash;
    }

    int rehash(int originalHash, int i, int arraySize) {
        int c1 = 3;
        int c2 = 5;
        return (originalHash + c1 * i + c2 * i * i) % arraySize;
    }

    public void printTable() {
        int i = 0;
        for(Production p : table) {
            if (p != null) {
                System.out.println("Element " + i + ": " + p.getName() + ", " + p.getRevenue() + ", " + p.getType() + " eliminated? " + p.isEliminated() + "original hash value: " + hash(p.getName(), table.length));
            } else {
                System.out.println("Element " + i + ": null");
            }
            i++;
        }
    }

    public Production[] getTable(){
        return table;
    }

    public ArrayList<Production> findProductionByRevenue(int minim, int maxim) {
        ArrayList<Production> productions = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if(table[i] != null && !table[i].isEliminated()){
                if(table[i].getRevenue() <= maxim && table[i].getRevenue() >= minim){
                    productions.add(table[i]);
                }
            }
        }
        return productions;
    }

    public int[] findStatistics() {
        int[] statistics = new int[6]; // 0-> FILM, 1-> BOOK, 2-> GAME, 3-> VIDEOGAMES, 4->SERIES 5-> THEATRE
        Arrays.fill(statistics, 0);
        for (int i = 0; i < table.length; i++) {
            if(table[i] != null && !table[i].isEliminated()){
                switch(table[i].getType()){
                    case Type.FILM -> statistics[0]++;
                    case Type.BOOK -> statistics[1]++;
                    case Type.GAME -> statistics[2]++;
                    case Type.VIDEOGAME -> statistics[3]++;
                    case Type.SERIES -> statistics[4]++;
                    case Type.THEATRE -> statistics[5]++;
                    default -> {
                    }
                }
            }
        }
        return statistics;
    }


}