package Tables;

public class Production {
    private String name;
    private Type type;
    private int revenue;
    private boolean eliminated;

    public Production(String name, String type, int revenue) {
        this.name = name;
        this.type = Type.valueOf(type);
        this.revenue = revenue;
        eliminated = false;
    }

    public String getName() {
        return name;
    }

    public Enum getType() {
        return type;
    }

    public int getRevenue() {
        return revenue;
    }

    public void printProd(){
        System.out.print( name + " (" + type.toString() + " - " + revenue+"€)");
    }

    public void setAsEliminated() {
        eliminated = true;
    }

    public boolean isEliminated() {
        return eliminated;
    }
}