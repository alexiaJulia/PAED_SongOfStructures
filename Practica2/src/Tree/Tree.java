package Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tree {
    public static Node root;
    public int i;
    public Tree (Node[] nodes) {
        root = nodes[0];
        for (int i = 1; i < nodes.length; i++) {
            insertNode(root, nodes[i]);
        }
        i = 0;
    }

    public String eliminateNodeSearchingByPostOrdre(Node currentNode, int ID) {
        String name;
        if (currentNode.hasLeftKid()) {
            if (currentNode.left.ID == ID) {
                name = currentNode.left.name;
                currentNode.left = eliminateNode(currentNode.left);
                return name;
            }
            eliminateNodeSearchingByPostOrdre(currentNode.left, ID);
        }

        if (currentNode.hasRightKid()) {
            if (currentNode.right.ID == ID) {
                name = currentNode.right.name;
                currentNode.right = eliminateNode(currentNode.right);
                return name;
            }
            eliminateNodeSearchingByPostOrdre(currentNode.right, ID);
        }
        return null;
    }

    public Node eliminateNode(Node nodeToEliminate) {
        //no té fills
        if (!nodeToEliminate.hasRightKid() && !nodeToEliminate.hasLeftKid()) {
            System.out.println("1");
            return null;
        }

        //té fills
        if (nodeToEliminate.hasLeftKid() && !nodeToEliminate.hasRightKid()) {
            System.out.println("2");
            return nodeToEliminate.left;
        }

        if (!nodeToEliminate.hasLeftKid() && nodeToEliminate.hasRightKid()) {
            System.out.println("3");
            return nodeToEliminate.right;
        }
        System.out.println("4");
        //té dos fills
        Node predecessorFather = nodeToEliminate;
        Node predecessor = nodeToEliminate.left;

        while (predecessor.hasRightKid()) {
            predecessorFather = predecessor;
            predecessor = predecessor.right;
        }

        predecessor.printNode();
        predecessorFather.printNode();


        if (predecessorFather != nodeToEliminate) {
            if (predecessor.hasLeftKid()) {
                predecessorFather.right = predecessor.left;
            } else {
                predecessorFather.right = null;
            }
            predecessor.right = nodeToEliminate.right;
            predecessor.left = nodeToEliminate.left;
            return predecessor;
        }

        predecessor.left = null;
        predecessor.right = nodeToEliminate.right;


        return predecessor;
    }


    public static void printNodesOfTree(Node currentNode) {
        currentNode.printNode();
        if (currentNode.hasLeftKid()) {
            printNodesOfTree(currentNode.left);
        }
        if (currentNode.hasRightKid()) {
            printNodesOfTree(currentNode.right);
        }
    }

    public static ArrayList<Node> returnSelectedNodesOfTree(Node currentNode) {
        ArrayList<Node> selectedNodes = new ArrayList<Node>();
        if (currentNode == null) return selectedNodes;

        if (currentNode.selected) selectedNodes.add(currentNode);

        if (currentNode.hasLeftKid()) {
            selectedNodes.addAll(returnSelectedNodesOfTree(currentNode.left));
        }
        if (currentNode.hasRightKid()) {
            selectedNodes.addAll(returnSelectedNodesOfTree(currentNode.right));
        }

        return selectedNodes;
    }


    public void insertNode(Node currentNode, Node newNode) {
        if (newNode.power < currentNode.power) {
            if (currentNode.hasLeftKid()) {
                insertNode(currentNode.left, newNode);
            } else {
                currentNode.left = newNode;
            }
        }
        if (newNode.power > currentNode.power) {
            if (currentNode.hasRightKid()) {
                insertNode(currentNode.right, newNode);
            } else {
                currentNode.right = newNode;
            }
        }
    }



    //donat un mínim i un màxim poder trobar tots els que estàn en l'interval
    public List<Node> generacióPatrulles(float minim, float maxim){
        List<Node> patrulles = new LinkedList<Node>();
        //ha de fer un inordre, el indordre retorna una llisat JA ORDENADA i només pritnar el list.size() i els nodes que compleixen
        inordre(minim, maxim, root, patrulles);
        return patrulles;
    }

    public void inordre(float minim, float maxim, Node node, List<Node> list) {
        if (node.hasLeftKid() && !(node.power < minim)) {
            //si el poder ja és més petit que el mínim no vull explorar, tot lo de l'esquerra serà més petit
            inordre(minim, maxim, node.left, list);
            i++;
        }
        if (node.power >= minim && node.power <= maxim) {
            list.add(node);
        }
        //nopmés incluir si està dins l'interval
        if (node.hasRightKid() && !(node.power > maxim)) {
            //si el poder ja és més gran que el màxim no vull explorar la branca, tot lo de la dreta erà més gran que el màxim
            inordre(minim, maxim, node.right, list);
            i++;
        }

    }


    //donat dos nodes trobar el seu pare en comú, que no sigui de la mateixa casa que cap d'ells
    public Node lluitaCases(Node node1, Node node2){
        /*
        Tinc els següents casos:
            - Un està a l'esquerra i l'altre a la dreta de l'arrel, bifurquen a l'arrel, l'arrel és el comú mirar si és de la mateixa casa
            - Els dos són germans i tenen el mateix pare
            - Trobo un molt ràpid i he de seguir per buscar l'altre
         */
        Node actual = root;
        Node mentor = null;
        while (actual != null) {
            if (actual.power < node1.power && actual.power < node2.power) { //si los dos se van para la derecha
                if (!actual.home.equals(node1.home) || !actual.home.equals(node2.home)) {
                    mentor = actual;
                }
                actual = actual.right;
            } else {
                if (actual.power > node1.power && actual.power > node2.power) { //si los dos se van para la izquierda
                    if (!actual.home.equals(node1.home) || !actual.home.equals(node2.home)) {
                        mentor = actual;
                    }
                    actual = actual.left;
                } else {
                    //aqui bifurcan
                    if (actual.power != node1.power && actual.power != node2.power) {
                        mentor = actual;
                    }
                    break;
                }
            }
        }
        return mentor;
    }

    /**
     * Busca un node segons els seu poder.
     * @param nodeInicial el node on comença. Normalment root.
     * @param valor el valor del poder de node a buscar
     * @return el node si l'ha trobat, null si no
     */
    public Node cerca(Node nodeInicial, float valor){
        Node actual = nodeInicial;
        while (actual != null && actual.power != valor) {
            if (valor < actual.power) {
                actual = actual.left;
            } else if (valor > actual.power) {
                actual = actual.right;
            }
        }
        return actual;
    }

    public  Node getHeroByPowerFacade(float power){
        if (root == null) {
            return null;
        }
        else {
            return getHeroByPower(root, power);
        }
    }

    public Node getHeroByPower(Node node, float power) {
        if (node == null) {
            return null;
        }
        //que subarbol quiero?
        if (node.power > power) {
            //quiero el de la derecha
            return getHeroByPower(node.left, power);
        }

        if (node.power < power) {
            return getHeroByPower(node.right, power);
        }

        if (node.power == power) {
            return node;
        }

        return null;
    }

    public Node getHeroByPowerIterative(Node node, float power) {
        Node actual = node;

        while (actual != null && actual.power != power) {
            if (power < actual.power) {
                actual = actual.left;
            }
            else {
                if (power > actual.power) {
                    actual = actual.right;
                }
                else {
                    return actual;
                }
            }
        }
        return actual;
    }

    public void printTreeConsole() {
        inordrePrintTree(root, "", false, true);
    }

    private void inordrePrintTree(Node node, String prefix, boolean isLeft, boolean isFirst) {

        String palitoTab = "|\t";
        String tab = "\t";

        String newPrefixLeft = prefix + (isLeft ? tab : palitoTab);
        String newPrefixRight = prefix + (isLeft ? palitoTab : tab);

        if (node.hasLeftKid()) {
            if (isFirst){
                inordrePrintTree(node.left, prefix, true, false);
                System.out.println(prefix + palitoTab);
            } else {
                inordrePrintTree(node.left, newPrefixLeft, true, false);
                System.out.println(newPrefixLeft + palitoTab);
            }
        }

        if (node == root){
            System.out.print("* ");
        } else {
            System.out.print(prefix + "|---");
        }
        node.printNodeWithColor();

        if (node.hasRightKid()) {
            if (isFirst){
                System.out.println(prefix + palitoTab);
                inordrePrintTree(node.right, prefix, false, false);
            } else {
                System.out.println(newPrefixRight + palitoTab);
                inordrePrintTree(node.right, newPrefixRight, false, false);
            }
        }
    }
}