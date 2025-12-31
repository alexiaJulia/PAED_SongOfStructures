package RTree;

//Representa un jugador
public class NodeR extends Figure {

    public int ID;
    public String name;
    String date;
    public int battlesPlayed;   //eix Y
    public int battlesWinned;   //eix X
    public boolean pvp;
    public String RGB_profile;
    public double distance;

    public NodeR(int ID, String name, String date, int battlesPlayed, int battlesWinned, boolean pvp, String RGB_profile) {
        super(battlesWinned, battlesPlayed, battlesWinned, battlesPlayed);
        this.name = name;
        this.ID = ID;
        this.date = date;
        this.battlesPlayed = battlesPlayed;
        this.battlesWinned = battlesWinned;
        this.pvp = pvp;
        this.RGB_profile = RGB_profile;
    }

    public int getID () {
        return ID;
    }
    public void printNode(){
        System.out.println(name + " ("+ID+", "+ date +"): "+ battlesWinned);
    }

    public int getX(){
        return battlesWinned;
    }

    public int getY(){
        return battlesPlayed;
    }

    public String getName() {
        return name;
    }

    public boolean isPvp(){
        return pvp;
    }

    public String getRGB_profile(){
        return this.RGB_profile;
    }
    public double getNodeDistance(){
        return distance;
    }

    public void setNodeDistance(double distance){
        this.distance = distance;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    public double calculateDistance(NodeR node){
        double distancia1;

        distancia1 = Math.sqrt(Math.pow(battlesWinned-node.battlesWinned, 2) + Math.pow(battlesPlayed-node.battlesPlayed, 2));
        return distancia1;
    }

    public void printingNode(){
        System.out.println("\t* "+name+" ("+ID+" - "+battlesWinned+"/"+battlesPlayed+" - PvP "+(pvp? "activat)":"desactivat)"));
    }
}