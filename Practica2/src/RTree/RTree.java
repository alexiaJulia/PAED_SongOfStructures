package RTree;

import java.util.*;

public class RTree {

    public static Rectangle root;

    public RTree(NodeR[] nodes) {
        if (nodes.length > 0) {
            root = new Rectangle();
            root.addChild(nodes[0]);
            for (int i = 1; i < nodes.length; i++) {
                insertNode(nodes[i]);
            }
        }
    }

    public void insertNode(NodeR node) {
        Rectangle leaf = findRectangleToInsert(root, node);
        leaf.addChild(node);
        leaf.recalculatePerimeter();

        if (leaf.isOverflow()) {
            Rectangle[] splitedRec = leaf.split();
            adjustTree(leaf, splitedRec[0], splitedRec[1]);
        }
    }
    //Busco el rectangle el qual la inserció provocarà un mínim augment de paràmetre
    private Rectangle findRectangleToInsert(Rectangle current, NodeR node) {
        while (!current.isLeaf()) {
            Rectangle best = null;
            double minEnlargement = Double.MAX_VALUE;

            for (Figure child : current.getChilds()) {
                if (child instanceof Rectangle rectangle) {
                    double enlargement = rectangle.recalculatePerimeter(node);
                    if (enlargement < minEnlargement) {
                        minEnlargement = enlargement;
                        best = rectangle;
                    }
                }
            }
            current = best;
        }
        return current;
    }

    //En cas d'overflow i divisió de rectangles, reajusto tot el arbre.
    private void adjustTree(Rectangle oldRect, Rectangle newRect1, Rectangle newRect2) {
        if (oldRect == root) {
            root = new Rectangle();
            root.addChild(newRect1);
            root.addChild(newRect2);
            newRect1.setFather(root);
            newRect2.setFather(root);
            root.recalculatePerimeter();
        }else {
            Rectangle father = oldRect.getFather();
            father.removeChild(oldRect);
            father.addChild(newRect1);
            father.addChild(newRect2);
            newRect1.setFather(father);
            newRect2.setFather(father);
            father.recalculatePerimeter();

            if (father.isOverflow()) {
                Rectangle[] split = father.split();
                adjustTree(father, split[0], split[1]);
            }
        }
    }



    public List<NodeR> cercaDivisions(int minimRe, int maxRe, int minimG, int maxG){
        List<NodeR> divisions = new LinkedList<NodeR>();
        cercaDivisionsRecursiva(minimRe, maxRe, minimG, maxG, divisions, root);
        return divisions;
    }

    private void cercaDivisionsRecursiva(int minimRe, int maxRe, int minimG, int maxG, List<NodeR> result, Figure figura){
        if (figura instanceof Rectangle){
            //aqui llamada recursiva
            if (totalmentDins(figura, minimG, minimRe, maxG, maxRe) || parcialmentDins(figura, minimG, minimRe, maxG, maxRe)){
                //si todo el rectangulo esta dentro del rectanguloBusqueda, las dos se cumplen
                for (Figure f : ((Rectangle) figura).getChilds()) {
                    cercaDivisionsRecursiva(minimRe, maxRe, minimG, maxG, result, f);
                }
            }
        } else if (figura instanceof NodeR){
            //aqui mirar si añadir
            if (((NodeR) figura).battlesWinned >= minimG && ((NodeR) figura).battlesWinned <= maxG &&
                    ((NodeR) figura).battlesPlayed >= minimRe && ((NodeR) figura).battlesPlayed <= maxRe){
                result.add((NodeR) figura);
            }
        }
    }

    public boolean totalmentDins(Figure rectA, int minX, int minY, int maxX, int maxY) {
        return rectA.minX >= minX &&
                rectA.maxX <= maxX &&
                rectA.minY >= minY &&
                rectA.maxY <= maxY;
    }

    public boolean parcialmentDins(Figure rectA, int minX, int minY, int maxX, int maxY) {
        return rectA.maxX >= minX &&
                rectA.minX <= maxX &&
                rectA.maxY >= minY &&
                rectA.minY <= maxY;
    }

    public boolean cercaID(int id, Figure figura, boolean found){
        if (figura instanceof Rectangle){
            //aqui llamada recursiva
                for (Figure f : ((Rectangle) figura).getChilds()) {
                    if (!found) {
                        found = cercaID(id, f, found);
                    }
                }
        } else if (figura instanceof NodeR){
            //aqui mirar si añadir
            if (((NodeR) figura).ID == id){
                return true;
            } else {
                return found;
            }
        }
        return found;
    }

    public Queue<NodeR> knn(NodeR punt, int k){
        PriorityQueue<NodeR> lista = new PriorityQueue<>(k, new Comparator<NodeR>() {
            @Override
            public int compare(NodeR n1, NodeR n2) {
                double d1 = n1.calculateDistance(punt);
                double d2 = n2.calculateDistance(punt);
                return Double.compare(d2, d1); // orden inverso: más lejos primero
            }
        });

        knnRecursive(root, punt, lista, k);
        return lista;
    }

    public void knnRecursive(Figure figura, NodeR punt, Queue<NodeR> lista, int k){
        if (figura instanceof Rectangle) {
            //aqui llamada recursiva
            PriorityQueue<Rectangle> kids = new PriorityQueue<>(k, new Comparator<Rectangle>() {
                @Override
                public int compare(Rectangle r1, Rectangle r2) {
                    double d1 = r1.calculateDistance(punt);
                    double d2 = r2.calculateDistance(punt);
                    return Double.compare(d1, d2); // más cerca primero
                }
            });
            for (Figure f : ((Rectangle) figura).getChilds()) { //ordenamos los childs segun cual esta más cerca del punto
                if (f instanceof Rectangle) {
                    kids.add((Rectangle) f);
                } else {
                    knnRecursive(f, punt, lista, k);
                }
            }
            while (!kids.isEmpty()) { //iteramos en los childs
                Rectangle rect = kids.poll();
                double distancia;
                if (lista.size() < k){ //si no tengo los k pues exploro
                    distancia = Double.MAX_VALUE;
                } else {
                    distancia = lista.peek().calculateDistance(punt); //si tengo los k pillo el que más lejso está
                }

                if (rect.calculateDistance(punt) < distancia) { //si la distancia del rectangulo al ultimo elemento de la lista es menor, nos importa explorarlo
                    knnRecursive(rect, punt, lista, k);
                }
            }
        } else if (figura instanceof NodeR) {
            //aqui mirar si añadir
            //tener en cuenta que tiene que tener pvp true
            //su distancia debe mejorar las que tengo
            NodeR nodo = (NodeR) figura;
            if (nodo.isPvp()) {
                double distancia = nodo.calculateDistance(punt);

                if (lista.size() < k) { //mientras no tengamos todos vamos insertando
                    lista.offer(nodo);
                } else {
                    NodeR farther = lista.peek();
                    if (distancia < farther.calculateDistance(punt)) {
                        lista.poll(); // quitamos el más apartado
                        lista.offer(nodo); // añadimos ya que está más cerca
                    }
                }
            }


        }
    }


}

