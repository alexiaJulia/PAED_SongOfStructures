package RTree;

public abstract class Figure {

    public int minY;
    public int maxY;
    public int minX;
    public int maxX;
    public Rectangle father;

    public Figure(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    //metode per identificar si la figura és un Punt (true) o un Rectangle (false)
    //Un rectangle serà considerat fulla si els seus fills són Punts
    public abstract boolean isLeaf();


    public double getPerimeter() {
        return 2 * ((maxX - minX) + (maxY - minY));
    }

    public void setFather(Rectangle father) {
        this.father = father;
    }

    @Override
    public String toString() {
        return "(" + minX + ", " + minY + ")";
    }
}
