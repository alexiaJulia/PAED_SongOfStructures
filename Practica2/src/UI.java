public class UI {

    public void printMenu() {
        System.out.println("1. Disseny de lore (Grafs)");
        System.out.println("2. Control d’herois (Arbres)");
        System.out.println("3. Expansió virtual (Arbres R)");
        System.out.println("4. Dominació multimèdia (Taules)");
        System.out.println("\n5. Aturar");
        System.out.print("\nEscull un bloc: ");
    }

    public void option1Menu() {
        System.out.println("\n\tA. Assentaments regionals");
        System.out.println("\tB. Mapa simplificat");
        System.out.println("\tC. Simulació de viatge");
        System.out.println("\n\tD. Tornar enrere");
        quinaEina();
    }

    private void quinaEina() {
        System.out.println();
        System.out.print("Quina eina vols fer servir? ");
    }

    public void option2Menu() {
        System.out.println("\n\tA. Afegir heroi");
        System.out.println("\tB. Eliminar heroi");
        System.out.println("\tC. Representació visual");
        System.out.println("\tD. Cercar heroi");
        System.out.println("\tE. Generació de patrulles");
        System.out.println("\tF. Enviats especials");
        System.out.println("\tG. Lluita de cases");
        System.out.println("\tH. Redistribució de mentors");
        System.out.println("\n\tI. Tornar enrere");
        quinaEina();
    }

    public void option3Menu() {
        System.out.println("\n\tA. Afegir jugador");
        System.out.println("\tB. Eliminar jugador");
        System.out.println("\tC. Representació gràfica");
        System.out.println("\tD. Cerca de divisions");
        System.out.println("\tE. Formació de grups");
        System.out.println("\n\tF. Tornar enrere");
        quinaEina();
    }

    public void option4Menu() {
        System.out.println("\n\tA. Afegir producció");
        System.out.println("\tB. Eliminar producció");
        System.out.println("\tC. Consulta");
        System.out.println("\tD. Cerca per facturació");
        System.out.println("\tE. Estadístiques");
        System.out.println("\n\tF. Tornar enrere");
        quinaEina();
    }

    public void chooseDataset() {
        System.out.println("\n\t1. XXS");
        System.out.println("\t2. XS");
        System.out.println("\t3. S");
        System.out.println("\t4. M");
        System.out.println("\t5. L");
        System.out.println("\t6. XL");
        System.out.println("\t7. XXL");
        System.out.println("\n\t8. Tornar enrere");
        System.out.println();
        System.out.print("Quin dataset vols fer servir? ");
    }


    public void printInvalidOption(){
        System.out.println("Opció no vàlida. Torna-ho a intentar.");
    }

    public void welcome(){
        System.out.println("·:. Song of Structures .:·\n");
    }
}
