package Tree;

//Representa un heroi
public class Node {
    public Node left;
    public Node right;

    public int ID;
    public String name;
    public float power;
    public String home;
    public String RGB_armor;
    public boolean selected;
    public int currentSituation;
    public int height;

    public Node (String name, int ID, float power, String home, String RGB_armor) {
        this.name = name;
        this.ID = ID;
        this.power = power;
        this.home = home;
        this.RGB_armor = RGB_armor;
        this.selected = false;
        this.height = 1;
    }

    public Node (String stringFromFile) {
        String[] parts = stringFromFile.split(";");

        this.left = null;
        this.right = null;

        this.ID = Integer.parseInt(parts[0]);
        this.name = parts[1];
        this.power = Float.parseFloat(parts[2]);
        this.home = parts[3];
        this.RGB_armor = parts[4];
        this.selected = false;
        this.height = 1;
    }

    public void printNode(){
        System.out.println(name + " ("+ID+", "+home+"): "+power);
        //Jules Pidieu (5, RCM): 49.9
    }

    public boolean hasLeftKid() {
        return this.left != null;
    }

    public boolean hasRightKid() {
        return this.right != null;
    }

    //Caldrà inserir els elements un a un tenint en compte el seu nivell de poder, seguint la
    //lògica BST. Sabent que els deixebles d’un heroi sovint acaben superant al seu mentor en poder,
    //interpretarem que el mentor d’un heroi serà el personatge que es trobi al node pare d’aquest.


    void printNodeWithColor() {
        int red = Integer.parseInt(RGB_armor.substring(1, 3), 16);
        int green = Integer.parseInt(RGB_armor.substring(3, 5), 16);
        int blue = Integer.parseInt(RGB_armor.substring(5, 7), 16);
        if(selected){
            red = 255;
            green = blue = 0;
        }

        String colorArmor = "\u001B[38;2;" + red + ";" + green + ";" + blue + "m";
        String sinColor = "\u001B[0m";
        System.out.print(colorArmor +name + sinColor + " (" + ID + ", " + home + "): " +  power  + "\n");
        }
}