import RTree.*;
import RTree.ReadFromFileRTree;
import RTree.RTree;

import java.time.LocalDate;
import java.util.*;

public class ControllerRTree {
    public Scanner sc;
    public RTree rTree;
    public NodeR[] nodes;

    public ControllerRTree(Scanner sc, String dataset) {
        this.sc = sc;
        nodes = ReadFromFileRTree.readNodesFromFile(dataset);
        rTree = new RTree(nodes);
        System.out.println();
    }

    public int getValidInt(String option_, int max, boolean message){
        try {
            int option = Integer.parseInt(option_);
            if (option >= 0 && option <= max) {
                return option;
            }
        } catch (NumberFormatException e) {
            System.out.print(message? "El número de batalles":"L'identificador d'un jugador");
            System.out.println(" està format únicament per números. Torna-ho a intentar.");
            return -1;
        }
        System.out.print(message? "El número de batalles":"L'identificador d'un jugador");
        System.out.println("ha de ser positiu. Torna-ho a intentar.");
        return -1;
    }

    public void insertJugador(){
        int id, batallesRe, batallesGu;
        String nom, pvp, color;
        boolean PvP = false;
        int okPvP;
        do{
            System.out.print("Identificador: ");
            id = getValidInt(sc.nextLine(), Integer.MAX_VALUE, false);
            if (rTree.cercaID(id, rTree.root, false)){
                id = -1;
                System.out.println("Aquest identificador ja està utilitzat per un altre jugador. Torna-ho a intentar.");
            }
        } while (id == -1);

        System.out.print("Nom: ");
        nom = sc.nextLine();

        do{
            System.out.print("Batalles realitzades: ");
            batallesRe = getValidInt(sc.nextLine(), Integer.MAX_VALUE, true);
        } while (batallesRe == -1);

        do{
            System.out.print("Batalles guanyades: ");
            batallesGu = getValidInt(sc.nextLine(), Integer.MAX_VALUE, true);
        } while (batallesGu == -1);
        do{
            okPvP = -1;
            System.out.print("PvP activat (si/no): ");
            pvp = sc.nextLine();
            if (pvp.equals("si")){
                PvP = true;
                okPvP = 1;
            } else if (pvp.equals("no")){
                PvP = false;
                okPvP = 1;
            }
        } while (okPvP == -1);
        do{
            System.out.print("Color de perfil: ");
            color = sc.nextLine();
            okPvP = 1;
            if (color.charAt(0) != '#' || color.length() != 7){
                System.out.println("Color no vàlid. Torna-ho a intentar.");
                okPvP = 0;
            }
        } while (okPvP == 0);

        NodeR nodeR = new NodeR(id, nom, LocalDate.now().toString(),batallesRe,batallesGu,PvP,color);
        rTree.insertNode(nodeR);
        System.out.println("El jugador "+nom+" ha entrat al sistema!");
    }

    public void eliminarJugador(){
        int id;
        String nom = "";
        do {
            System.out.print("Introdueix l’identificador del jugador a eliminar: ");
            id = getValidInt(sc.nextLine(), Integer.MAX_VALUE, false);
        } while (id == -1);
        System.out.println();
        nom = EliminarRTree.eliminatePlayer(rTree, id);
        if (nom == null) {
            System.out.println("No existeix cap jugador amb aquest identificador!");
        } else {
            System.out.println("El jugador " + nom + " ha estat eliminat del sistema!");
        }
    }

    public void representacioGrafica(){
        RTreeVisualizer rTreeVisualizer = new RTreeVisualizer(rTree);
        rTreeVisualizer.start();
    }

    public void cercaDivisions(){
        int minimRe = -1;
        int maximRe = -1;
        int minimGu = -1;
        int maximGu = -1;
        do {
            System.out.print("Introdueix el mínim de batalles realitzades: ");
            minimRe = getValidInt(sc.nextLine(), Integer.MAX_VALUE, true);
        } while (minimRe == -1);
        do{
            System.out.print("Introdueix el mínim de batalles guanyades: ");
            minimGu = getValidInt(sc.nextLine(), Integer.MAX_VALUE, true);
        } while (minimGu == -1);
        do {
            System.out.print("Introdueix el màxim de batalles realitzades: ");
            maximRe = getValidInt(sc.nextLine(), Integer.MAX_VALUE, true);
        } while (maximRe == -1);
        do{
            System.out.print("Introdueix el màxim de batalles guanyades: ");
            maximGu = getValidInt(sc.nextLine(), Integer.MAX_VALUE, true);
        } while (maximGu == -1);

        List<NodeR> nodes;
        nodes = rTree.cercaDivisions(minimRe, maximRe, minimGu, maximGu);
        System.out.println();
        System.out.println("S'han trobat "+ nodes.size()+" jugadors en aquest rang!");
        for (NodeR n : nodes) {
            n.printingNode(); //creo que el formato se tien que cambiar
        }
    }

    public void formacioGrups(){
        int batallesRealitzades = -1;
        int batallesGuanyades = -1;
        int jugadors = -1;
        do {
            System.out.print("Introdueix el nombre de batalles realitzades: ");
            batallesRealitzades = getValidInt(sc.nextLine(), Integer.MAX_VALUE, true);
        } while (batallesRealitzades == -1);
        do{
            System.out.print("Introdueix el nombre de batalles guanyades: ");
            batallesGuanyades = getValidInt(sc.nextLine(), Integer.MAX_VALUE, true);
        } while (batallesGuanyades == -1);
        do {
            System.out.print("Introdueix el nombre de jugadors a cercar: ");
            jugadors = getValidInt(sc.nextLine(), Integer.MAX_VALUE, true);
            if (jugadors == 0){
                System.out.println("Els jugadors a cercar han de ser positius.");
                jugadors = -1;
            }
        } while (jugadors == -1);

        NodeR point = new NodeR(999999, "KnnPoint", "2025-01-01", batallesRealitzades, batallesGuanyades, true, "#FFFFFF");
        Queue<NodeR> knn;
        knn = rTree.knn(point, jugadors);

        //nodes = la funcion

        List<NodeR> nodes = new ArrayList<>(knn);
        nodes.sort(Comparator.comparingDouble(n -> n.calculateDistance(point)));

        System.out.println("Els "+ nodes.size() +" jugadors més propers són:");

        if (jugadors > 5 && jugadors % 2 == 0) {
            List<NodeR> team1 = new ArrayList<>();
            List<NodeR> team2 = new ArrayList<>();

            int totalFoughtBattlesTeam1 = 0;
            int totalWonBattlesTeam1 = 0;
            int totalFoughtBattlesTeam2 = 0;
            int totalWonBattlesTeam2 = 0;

            for (NodeR player : knn) {

                double totalBattlesTeam1;
                double totalBattlesTeam2;

                if (totalFoughtBattlesTeam1 == 0) {
                    totalBattlesTeam1 = 0;
                } else {
                    totalBattlesTeam1 = (double) totalWonBattlesTeam1 / totalFoughtBattlesTeam1;
                }

                if (totalFoughtBattlesTeam2 == 0) {
                    totalBattlesTeam2 = 0;
                } else {
                    totalBattlesTeam2 = (double) totalWonBattlesTeam2 / totalFoughtBattlesTeam2;
                }

                if ((team1.size() < jugadors / 2 && totalBattlesTeam1 <= totalBattlesTeam2) || team2.size() >= jugadors / 2) {
                    team1.add(player);
                    totalFoughtBattlesTeam1 += player.battlesPlayed;
                    totalWonBattlesTeam1 += player.battlesWinned;
                } else {
                    team2.add(player);
                    totalFoughtBattlesTeam2 += player.battlesPlayed;
                    totalWonBattlesTeam2 += player.battlesWinned;
                }
            }

            String team1Colour = getAvgColour(team1);
            String team2Colour = getAvgColour(team2);
            System.out.println("Equip 1 - Color mitjà: " + team1Colour + ":");
            for (NodeR player : team1) {
                player.printingNode();
            }

            System.out.println("Equip 2 - Color mitjà: " + team2Colour + ":");
            for (NodeR player : team2) {
               player.printingNode();
            }
        } else {
            for (NodeR n : nodes) {
                n.printingNode(); //el format s'ha de canviar
            }
        }

    }


    // recorro y devuelvo lista con todos los players que tengan el pvp enabled
    private List<NodeR> getAllPlayers(Rectangle current) {
        List<NodeR> players = new ArrayList<>();
        collectPlayersRecursive(current, players);

        return players;
    }

    private void collectPlayersRecursive(Rectangle rectangle, List<NodeR> players) {
        if (rectangle == null) {
            return;
        }
        // si el nodo es hoja, tiene players
        if (rectangle.isLeaf()) {
            for (Figure child : rectangle.getChilds()) {
                NodeR point = (NodeR) child;
                if (point.isPvp()) {
                    players.add(point);
                }
            }
        }
        // sino, pues otros subarboles
        else {
            for (Figure child : rectangle.getChilds()) {
                Rectangle childRectangle = (Rectangle) child;
                collectPlayersRecursive(childRectangle, players);
            }
        }
    }

    private void calculateDistanceForFormation(List<NodeR> players, int foughtBattles, int wonBattles) {
        for (NodeR player : players) {
            double distance = 0;
            if (player.isPvp()) {
                // distancia euclidiana (distancia en linea recta entre 2 puntos)
                // simetrica
                distance = Math.sqrt(Math.pow(player.getY() - foughtBattles, 2) + Math.pow(player.getX() - wonBattles, 2));
                player.setNodeDistance(distance);
            }
            else {
                player.setNodeDistance(Double.MAX_VALUE); // si no es pvp, pone valor mas grande para irse al final
            }
        }
    }

    private String getAvgColour(List<NodeR> team) {
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;

        for (NodeR player : team) {
            String colour = player.getRGB_profile();

            if (colour.startsWith("#")) {
                colour = colour.substring(1); // remove the '#' character
            }

            int red = Integer.parseInt(colour.substring(0, 2), 16);
            int green = Integer.parseInt(colour.substring(2, 4), 16);
            int blue = Integer.parseInt(colour.substring(4, 6), 16);
            totalRed += red;
            totalGreen += green;
            totalBlue += blue;
        }

        int totalTeam = team.size();
        int averageRed = totalRed / totalTeam;
        int averageGreen = totalGreen / totalTeam;
        int averageBlue = totalBlue / totalTeam;

        return String.format("#%02X%02X%02X", averageRed, averageGreen, averageBlue);
    }

}
