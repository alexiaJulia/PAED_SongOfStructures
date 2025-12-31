package RTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rectangle extends Figure {

    public static int tree_order = 3;
    public static int underflow = 1; //valor de fills mínims per figura
    public Figure[] childs;
    public int size;

    public Rectangle() {
        super(0, 0, 0, 0);
        this.childs = new Figure[tree_order+1];
        this.size = 0;
    }

    //Afegeix un fill a un rectangle, tot recalculant el seu perímetre. Es recalcula per tots els antecedents.
    public void addChild(Figure figura) {
        childs[size] = figura;
        size++;
        figura.setFather(this);
        updatePerimeter(figura);
        if(father != null) {
            father.recalculateBounds();
        }
    }

    //Es recalcula els límits del rectangle actual, marcat per la ubicació dels seus fills.
    public void recalculateBounds() {
        if(childs[0] != null) {
            minX = Integer.MAX_VALUE;
            minY = Integer.MAX_VALUE;
            maxX = Integer.MIN_VALUE;
            maxY = Integer.MIN_VALUE;

            for (int i = 0; i < size; i++) {
                minX = Math.min(minX, childs[i].minX);
                minY = Math.min(minY, childs[i].minY);
                maxX = Math.max(maxX, childs[i].maxX);
                maxY = Math.max(maxY, childs[i].maxY);
            }
            if (father != null) { //Es recalcula per tots els pares fins arribar a la root.
                father.recalculateBounds();
            }
        }
    }

    public void clearChildren() {
        for (Figure child: getChilds()) {
            removeChild(child);
        }
        minX = 0;
        minY = 0;
        maxX = 0;
        maxY = 0;
    }

    //Elimina un fill de un rectangle, en cas d'estar contingut.
    public void removeChild(Figure figura) {
        boolean found = false;
        for (int i = 0; i < size; i++) {
            if (childs[i] == figura) {
                childs[i] = childs[size - 1];
                childs[size - 1] = null;
                size--;
                found = true;
                break;
            }
        }
        if (found) {
            recalculatePerimeter(); //Recalcula el perimetre del rectangle del que hem eliminat un fill.
        }
    }

    public void updatePerimeter(Figure figure) {
        minX = Math.min(minX, figure.minX);
        minY = Math.min(minY, figure.minY);
        maxX = Math.max(maxX, figure.maxX);
        maxY = Math.max(maxY, figure.maxY);
    }

    public void recalculatePerimeter() {
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;

        for (int i = 0; i < size; i++) {
            updatePerimeter(childs[i]);
        }
    }

    public boolean isOverflow() {
        return size > tree_order;
    }

    public boolean isUnderFlow() {
        return size < underflow;
    }

    public Figure[] getChilds() {
        return Arrays.copyOf(childs, size);
    }

    //Per quan hi ha un overFlow?? Per repartir equitativament els punts??
    public Rectangle[] split() {
        Rectangle[] splitedRec = new Rectangle[2];
        Rectangle r1 = new Rectangle();
        Rectangle r2 = new Rectangle();

        // #1 trobar els punts amb la major distancia euclidiana
        int[] indexPoints = selectMostDistantPoints();
        r1.addChild(childs[indexPoints[0]]);
        r2.addChild(childs[indexPoints[1]]);

        // #2 repartir els dos punts sobrants
        distributePoints(r1, r2, indexPoints[0], indexPoints[1]);
        r1.recalculatePerimeter();
        r2.recalculatePerimeter();
        splitedRec[0] = r1;
        splitedRec[1] = r2;
        return splitedRec;
    }

    //Selecciona els dos punts més distants per distància ecludiana i els retorna.
    private int[] selectMostDistantPoints() {
        int indexPoint1 = 0, indexPoint2 = 0;
        int[] indexPoints = new int[2];
        double maxDistance = -1;

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                double distance = euclideanDistance(childs[i], childs[j]);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    indexPoint1 = i;
                    indexPoint2 = j;
                }
            }
        }
        indexPoints[0] = indexPoint1;
        indexPoints[1] = indexPoint2;
        return indexPoints;
    }

    private double euclideanDistance(Figure a, Figure b) {
        double dx = a.minX - b.minX;
        double dy = a.minY - b.minY;
        return Math.sqrt(dx*dx + dy*dy);
    }

    //Reparteix els punts sobrants entre els dos
    private void distributePoints(Rectangle r1, Rectangle r2, int point1, int point2) {
        List<Figure> remaining = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (i != point1 && i != point2) {
                remaining.add(childs[i]);
            }
        }

        while (!remaining.isEmpty()) {

            // Es decideix a quin rectangle s'inserirà el punt segons l'heurística quadràtica
            double maxDifference = -1;
            Figure selected = null;
            boolean insertInR1 = true;

            for (Figure f : remaining) {
                double simulatedR1 = simulatedPerimeterAfterAdd(r1, f);
                double simulatedR2 = simulatedPerimeterAfterAdd(r2, f);
                double incR1 = simulatedR1 - r1.getPerimeter();
                double incR2 = simulatedR2 - r2.getPerimeter();
                double difference = Math.abs(incR1 - incR2);

                if (difference > maxDifference) {
                    maxDifference = difference;
                    selected = f;
                    if(incR1 < incR2){
                        insertInR1 = true;
                    }else{
                        insertInR1 = false;
                    }
                }
            }

            remaining.remove(selected);
            if (insertInR1) {
                r1.addChild(selected);
                r1.recalculatePerimeter();
            } else {
                r2.addChild(selected);
                r2.recalculatePerimeter();
            }
        }
    }

    //Simulació de perimetre després d'afegir un nova figura
    private double simulatedPerimeterAfterAdd(Rectangle rect, Figure f) {
        int newMinX = Math.min(rect.minX, f.minX);
        int newMinY = Math.min(rect.minY, f.minY);
        int newMaxX = Math.max(rect.maxX, f.maxX);
        int newMaxY = Math.max(rect.maxY, f.maxY);
        return 2 * ((newMaxX - newMinX) + (newMaxY - newMinY));
    }

    //Com augmentaria el perimetre després d'afegir un nou node.
    public double recalculatePerimeter(Figure f) {
        double newMinX = Math.min(minX, f.minX);
        double newMinY = Math.min(minY, f.minY);
        double newMaxX = Math.max(maxX, f.maxX);
        double newMaxY = Math.max(maxY, f.maxY);

        double newPerimeter = 2 * ((newMaxX - newMinX) + (newMaxY - newMinY));
        return newPerimeter - getPerimeter();
    }

    public Rectangle getFather() {
        return father;
    }

    @Override
    public String toString() {
        return "Rectangle [(" + minX + ", " + minY + "), (" + maxX + ", " + maxY + ")] con " + size + " hijos";
    }

    @Override
    public boolean isLeaf() {
        return size == 0 || (childs[0] instanceof NodeR);
    }

    public double calculateDistance(NodeR node){
        double px = node.battlesWinned;
        double py = node.battlesPlayed;
        double dx = 0.0;
        if (px < minX) dx = minX - px;           // está a la izquierda
        else if (px > maxX) dx = px - maxX;      // está a la derecha

        double dy = 0.0;
        if (py < minY) dy = minY - py;           // está abajo
        else if (py > maxY) dy = py - maxY;      // está arriba
        return Math.sqrt(dx * dx + dy * dy);
    }

}
