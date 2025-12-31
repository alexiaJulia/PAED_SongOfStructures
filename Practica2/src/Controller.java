import Tree.*;
import Graf.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    public UI ui;
    public static Scanner sc;
    public static String dataset;

    public Tree tree;

    public Controller(){
        this.ui = new UI();
        this.sc = new Scanner(System.in);
    }

    public void mainMenu() {
        ui.welcome();
        int validOption, dataset = 0;
        do {
            ui.printMenu();
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();

            validOption = getValidOption(input, 5);
            if (validOption >= 1 && validOption <= 4) {
                dataset = chooseValidDataset();
                if (dataset == 8) {
                    System.out.println();
                    continue;
                }
            }

            switch (validOption) {
                case 1:
                    assignDataset(dataset);
                    graphs();
                    break;
                case 2:
                    assignDataset(dataset);
                    trees();
                    break;
                case 3:
                    assignDataset(dataset);
                    Rtrees();
                    break;
                case 4:
                    assignDataset(dataset);
                    taules();
                    break;
                case 5:
                    System.out.println("\nAturant Song of Structures.");
                    System.out.println(".:· ··· --- ··· ·:.");
                    break;
                default:
                    ui.printInvalidOption();
                    System.out.println();
            }


        } while (validOption != 5);
    }

    private int chooseValidDataset() {
        int dataset = 0;
        do {
            ui.chooseDataset();
            dataset = getValidOption(sc.nextLine(), 8);
            if (dataset == -1) {
                ui.printInvalidOption();
            }
        } while (dataset == -1);
        return dataset;
    }

    public int getValidOption(String option_, int max) {
        try {
            int option = Integer.parseInt(option_);
            if (option >= 1 && option <= max) {
                return option;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
        return -1;
    }

    public void assignDataset(int dataset) {
        String[] datasets = {"XXS.paed", "XS.paed", "S.paed", "M.paed", "L.paed", "XL.paed", "XXL.paed"};
        this.dataset = datasets[dataset - 1];
    }

    public static char getValidOption(String option, char maxLetter) {
        if (option.length() != 1) return '0';
        if (option.charAt(0) >= 'A' && option.charAt(0) <= maxLetter) {
            return option.charAt(0);
        }
        return '0';
    }

    //Grafs
    public void graphs(){
        ControllerGraph contGraph = new ControllerGraph(dataset, sc);
        char option;
        do {
            ui.option1Menu();
            option = getValidOption(sc.nextLine(), 'D');
            System.out.println();
            switch (option) {
                case 'A':
                    contGraph.assentamentsRegionals();
                    break;
                case 'B':
                    contGraph.mapaSimplificat();
                    break;
                case 'C':
                    contGraph.simulacioViatge();
                    break;
                case 'D':
                    System.out.println();
                    break;
                default:
                    ui.printInvalidOption();
            }
        } while (option != 'D');
    }

    //Trees
    public void trees() {
        ControllerTree contTree = new ControllerTree(sc, dataset);
        char option;
        do {
            ui.option2Menu();
            option = getValidOption(sc.nextLine(), 'I');
            System.out.println();
            switch (option) {
                case 'A':
                    contTree.afegirHeroi();
                    break;
                case 'B':
                    contTree.eliminarHeroi();
                    break;
                case 'C':
                    //representacióVisual();
                    contTree.treeVisualualizer();
                    break;
                case 'D':
                    contTree.showSearchedHero();
                    break;
                case 'E':
                    contTree.generarPatrulles();
                    break;
                case 'F':
                    contTree.enviatsEspecials();
                    break;
                case 'G':
                    contTree.lluitaCases();
                    break;
                case 'H':
                    contTree.redistribucioMentors(dataset);
                    break;
                case 'I':
                    System.out.println();
                    break;
                default:
                    ui.printInvalidOption();
            }
        } while (option != 'I');
    }

    public void Rtrees() {
        ControllerRTree contRTree = new ControllerRTree(sc, dataset);
        char option;
        do {
            ui.option3Menu();
            option = getValidOption(sc.nextLine(), 'F');
            System.out.println();
            switch (option) {
                case 'A':
                    contRTree.insertJugador();
                    break;
                case 'B':
                    contRTree.eliminarJugador();
                    break;
                case 'C':
                    //representacióVisual();
                    contRTree.representacioGrafica();
                    break;
                case 'D':
                    contRTree.cercaDivisions();
                    break;
                case 'E':
                    contRTree.formacioGrups();
                    break;
                case 'F':
                    System.out.println();
                    break;
                default:
                    ui.printInvalidOption();
            }
        } while (option != 'F');
    }

    //Taules
    public void taules(){
        ControllerTables contTables = new ControllerTables(sc, dataset);
        char option;
        do {
            ui.option4Menu();
            option = getValidOption(sc.nextLine(), 'F');
            System.out.println();
            switch (option) {
                case 'A':
                    contTables.inserirProduccio();
                    break;
                case 'B':
                    contTables.eliminarProduccio();
                    break;
                case 'C':
                    contTables.consulta();
                    break;
                case 'D':
                    contTables.cercaFacturacio();
                    break;
                case 'E':
                    contTables.estadistiques();
                    break;
                case 'F':
                    System.out.println();
                    break;
                default:
                    ui.printInvalidOption();
            }
        } while (option != 'F');
    }

    public static String getDataset(){
        return dataset;
    }
}

