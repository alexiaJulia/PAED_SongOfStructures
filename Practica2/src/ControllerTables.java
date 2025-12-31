import Tables.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ControllerTables {
    private Scanner sc;
    private Production[] productions;
    private Table table;

    public ControllerTables(Scanner sc, String dataset) {
        this.sc = sc;
        this.productions = ReadFromFileTables.readProductionsFromFile(dataset);
        table = new Table(productions);
    }

    public int getValidInt(String option_, int max){
        try {
            int option = Integer.parseInt(option_);
            if (option >= 0 && option <= max) {
                return option;
            }
        } catch (NumberFormatException e) {
            System.out.println("\tEls diners generats estan format únicament per números. Torna-ho a intentar.");
            return -1;
        }
        System.out.println("\tEls diners generats han de ser positius. Torna-ho a intentar.");
        return -1;
    }

    public void inserirProduccio(){
        int revenue;
        System.out.print("Nom de la producció: ");
        String nom = sc.nextLine();
        Type tipus = null;
        boolean valido = false;

        while (!valido) {
            System.out.print("Tipus de producció: ");
            String entrada = sc.nextLine().toUpperCase();

            try {
                tipus = Type.valueOf(entrada);
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Tipus no vàlid. Intenta-ho de nou.");
            }
        }
        do {
            System.out.print("Diners generats: ");
            String diners = sc.nextLine();
            revenue = getValidInt(diners, Integer.MAX_VALUE);
        } while (revenue == -1);

        //cridar la funcio
        Production prod = new Production(nom, tipus.toString(), revenue);
        table.insertProduction(prod, false);
        String[] tipuss = {"pel·lícula", "llibre", "joc", "videojoc", "sèrie", "teatre"};
        System.out.println();
        System.out.println("El " + tipuss[tipus.ordinal()]+" " +nom+ " s'ha registrat!");
    }

    public void eliminarProduccio(){
        String nom;
        System.out.print("Introdueix el nom de la producció a eliminar: ");
        nom = sc.nextLine();
        Production prod;
        try {
            prod = table.eliminateProductionByKey(nom);
        } catch (RuntimeException e) {
            nom = null;
            prod = null;
        }
        //cridar la funcio, si pot mirar que el nom no existeixi millor!
        //produccio =  funcio
        System.out.println();
        if (prod == null) { //nom de la produccio retornada!
            System.out.println("No existeix cap producció amb aquest nom.");
        } else {
            System.out.print("La producció ");
            prod.printProd();
            System.out.println(" ha estat eliminada del sistema!");
        }
    }

    public void consulta(){
        System.out.print("Introdueix el nom de la producció a consultar: ");
        String nom = sc.nextLine();

        Production production = table.getProductionByKey(nom);
        System.out.println();
        if (production == null){
            System.out.println("No existeix cap producció amb aquest nom! Torna-ho a intentar.");
        } else {
            System.out.print("S'ha trobat la següent producció: ");
            production.printProd();
            System.out.println();
        }
    }

    public void cercaFacturacio() {
        int minim;
        int maxim;
        do {
            System.out.print("Introdueix el mínim de diners generats: ");
            String optin = sc.nextLine();
            minim = getValidInt(optin, Integer.MAX_VALUE);
        } while (minim == -1);
        do {
            System.out.print("Introdueix el màxim de diners generats: ");
            String optin = sc.nextLine();
            maxim = getValidInt(optin, Integer.MAX_VALUE);
            if(maxim < minim){
                System.out.println("El valor màxim ha de ser més gran que el mínim");
            }
        } while (maxim == -1 || maxim < minim);

        ArrayList<Production> productions = table.findProductionByRevenue(minim, maxim);
        System.out.println();
        if (productions.isEmpty()){
            System.out.println("No hi ha cap producció en aquest rang!");
        } else {
            System.out.println("Les produccions en el rang són:");
            for (Production production : productions) {
                System.out.print("\t* ");
                production.printProd();
                System.out.println();
            }
        }

    }

    public void estadistiques(){
        int[] statistics = table.findStatistics();
        TableVisualizer tableVisualizer = new TableVisualizer(statistics);
        tableVisualizer.setVisible(true);
        System.out.println("Les estadístiques són les següents:");
        System.out.println("\t* Llibres: "+ statistics[1]);
        System.out.println("\t* Pel·lícules: "+ statistics[0]);
        System.out.println("\t* Videojocs: "+ statistics[3]);
        System.out.println("\t* Jocs: "+ statistics[2]);
        System.out.println("\t* Sèries: "+ statistics[4]);
        System.out.println("\t* Teatre: "+ statistics[5]);
    }
}
