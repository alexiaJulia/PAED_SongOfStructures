import Tree.*;

import java.util.*;

import static Tree.Tree.printNodesOfTree;

public class ControllerTree {
    public Scanner sc;
    public Tree tree;
    public Node[] nodesAVL;
    private SpecialSends sp = new SpecialSends();

    public ControllerTree(Scanner sc, String dataset) {
        this.sc = sc;
        this.tree = new Tree(ReadFromFileTree.readNodesFromFile(dataset));
        this.sp.desmarkSpecialSends = false;
    }

    public float getValidFloat(String option){
        String frase;
        float nivell = 0;
        boolean ok;
        do{
            System.out.print(option); // "Nivell mínim: "
            frase = sc.nextLine();
            try{
                nivell = Float.parseFloat(frase);
                if (nivell > 0) {
                    ok = true;
                } else {
                    System.out.println("El nivell d'un heroi ha de ser positiu!");
                    ok = false;
                }
            } catch (NumberFormatException e){
                System.out.println("El nivell d'un heroi està format únicament per números. Torna-ho a intentar.");
                ok = false;
            }
        } while (!ok);
        return nivell;
    }

    public int getValidInt(String option_, int max){
        try {
            int option = Integer.parseInt(option_);
            if (option >= 0 && option <= max) {
                return option;
            }
        } catch (NumberFormatException e) {
            System.out.println("L'identificador d'un heroi està format únicament per números. Torna-ho a intentar.");
            return -1;
        }
        System.out.println("L'identificador d'un heroi ha de ser positiu. Torna-ho a intentar.");
        return -1;
    }

    public Node getValidNode(String option){
        boolean ok = true;
        Node node;
        float nivell;
        do {
            nivell = getValidFloat(option);
            node = tree.cerca(tree.root, nivell);
            ok = true;
            if (node == null){
                ok = false;
                System.out.println("No existeix cap heroi amb aquest poder. Torna-ho a intentar.");
            }
        } while (!ok);
        return node;
    }

    public void representTree(){
        String option_;
        boolean ok = true;
        int option = 0;
        do {
            System.out.println("Com vols representar l'arbre? ");
            System.out.println("\t1. Gràficament (per datasets petits) \n\t 2. Per terminal \n\t 3. Les dues\n");
            System.out.print("Escull una opcio:");
            try {
                option = Integer.parseInt(sc.nextLine());
                if (option >= 1 && option <= 3) {
                    ok = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Introdueix només números. Torna-ho a intentar.");
            }
        } while (ok);

        switch(option){
            case 1:
                break;
            case 2:
                break;
            case 3:

                break;
        }
    }

    public void generarPatrulles(){
        float nivellMin = getValidFloat("Nivell mínim: ");
        float nivellMax;
        do {
            nivellMax = getValidFloat("Nivell màxim: ");
            if (nivellMax < nivellMin) {
                System.out.println("El nivell màxim ha de ser superior al nivell mínim!");
            }
        } while (nivellMax < nivellMin);


        //he de mirar que el max no sigui més petit que el min?


        List<Node> lista = tree.generacióPatrulles(nivellMin, nivellMax);
        System.out.println();
        System.out.println("S’han trobat "+ lista.size() +" herois per la patrulla:\n");

        for (Node n : lista) {
            System.out.print("\t* ");
            n.printNode();
        }
    }

    public void lluitaCases() {
        Node node1;
        Node node2;
        node1 = getValidNode("Nivell del primer combatent: ");
        node2 = getValidNode("Nivell del segon combatent: ");
        System.out.println();
        Node pare = tree.lluitaCases(node1, node2);
        if (pare == null){
            System.out.println("No s'ha trobat cap jutge pel combat entre " + node1.name + " i " + node2.name+"."); //El combat entre The Deserter i Cuno hauria de ser jutjat per Harry Du Bois.
        } else {
            System.out.println("El combat entre " + node1.name + " i " + node2.name + " hauria de ser jutjat per " + pare.name+"."); //El combat entre The Deserter i Cuno hauria de ser jutjat per Harry Du Bois.
        }
    }


    public void enviatsEspecials() {
        sp.specialSends(Tree.root);
        ArrayList<Node> selectedNodes = Tree.returnSelectedNodesOfTree(Tree.root);
        if (selectedNodes.size() == 0){
            System.out.println("Els enviats especials han tornat de la missió.");
        } else {
            System.out.println("S'envien " + selectedNodes.size() + " a la missió especial:\n");
            for (Node n : selectedNodes) {
                System.out.print("\t* ");
                n.printNode();
            }
        }
    }

    public void afegirHeroi(){
        int id;
        do {
            System.out.print("Identificador de l'heroi: ");
            id = getValidInt(sc.nextLine(), Integer.MAX_VALUE);
        } while (id == -1);
        System.out.print("Nom de l'heroi: ");
        String name = sc.nextLine();
        float poder;
        poder = getValidFloat("Nivell de poder de l'heroi: ");
        System.out.print("Casa de l'heroi: ");
        String casa = sc.nextLine();
        String material;
        boolean ok;
        do{
            System.out.print("Material de l'armadura: ");
            material = sc.nextLine();
            ok = true;
            if (material.charAt(0) != '#' || material.length() != 7){
                System.out.println("Material d'armadura no vàlid. Torna-ho a intentar.");
                ok = false;
            }
        } while (!ok);
        Node node = new Node(name, id, poder, casa, material);

        tree.insertNode(tree.root, node);
        System.out.println();
        System.out.println("L'univers s'ha expandit amb l'heroi "+name+".");
    }

    public void eliminarHeroi(){
        int ID;
        do {
            System.out.print("Identificador de l’heroi: ");
            ID = getValidInt(sc.nextLine(), Integer.MAX_VALUE);
        } while(ID == -1);
        //cerca primer per id
        String name = tree.eliminateNodeSearchingByPostOrdre(tree.root, ID);
        printNodesOfTree(tree.root);
        if (name == null){
            System.out.println("L'heroi a eliminar no existeix.");
        } else {
            System.out.println("L’heroi " + name + " ha estat descartat de la narrativa.");
        }
    }

    public void showSearchedHero() {
        float power = 0;

        power = getValidFloat("Nivell de poder: ");
        Node node = tree.getHeroByPowerIterative(tree.root, power);
        System.out.println();
        if (node != null) {
            System.out.print("S'ha trobat l'heroi ");
            node.printNode();
        } else {
            System.out.println("L'heroi no existeix.");
        }
    }

    public void treeVisualualizer() {
        tree.printTreeConsole();
        if(Controller.getDataset().equals("XXS.paed") || Controller.getDataset().equals("XS.paed")){
            System.out.println("Vols mostrar també la visualització en 2D? (Escriu 'Si')");
            Scanner sc = new Scanner(System.in);
            String resposta = sc.nextLine();
            if (resposta.equalsIgnoreCase("Si")) {
                BinaryTreeVisualizer binaryTreeVisualizer = new BinaryTreeVisualizer(this.tree, null, false);
                binaryTreeVisualizer.start();
            }
        }
    }


    public void redistribucioMentors(String dataset){
        nodesAVL = ReadFromFileTree.readNodesFromFile(dataset);
        BinaryTreeAVL binaryTreeAVL = new BinaryTreeAVL(nodesAVL);
        System.out.println("\nEls mentors han estat redistribuïts. Ara l’arbre té " + binaryTreeAVL.root.height + " nivells.");
        if(Controller.getDataset().equals("XXS.paed") || Controller.getDataset().equals("XS.paed")){
            System.out.println("\nVols mostrar també la visualització en 2D? (Escriu 'Si')");
            String resposta = sc.nextLine();
            if (resposta.equalsIgnoreCase("Si")) {
                BinaryTreeVisualizer binaryTreeVisualizer = new BinaryTreeVisualizer(null, binaryTreeAVL, true);
                binaryTreeVisualizer.start();
            }
        }
    }
}

