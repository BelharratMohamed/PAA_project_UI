package menu;

import factory.ReseauFactory;
import reseau.Reseau;

import java.io.IOException;
import java.util.Scanner;

public class Menu3 {
    public static void menu(Scanner sc, Reseau reseau, String fichierReseau) {
        int choix;
        boolean fin = false;
        reseau.calculCout();
        do {
            afficherMenu();
            try {
                choix = Integer.parseInt(sc.nextLine());
                switch (choix) {
                    case 1: {
                        System.out.println("Ancien cout : " + reseau.getCout());
                        reseau.solveurCSP();
                        System.out.println("Nouveau cout : " + reseau.getCout());
                        break;
                    }
                    case 2: {
                        try {
                            sauvgarde(sc, reseau, fichierReseau);
                        } catch (IOException e) {
                            System.out.println("Erreur : " + e.getMessage());
                        }
                        break;
                    }
                    case 3: {
                        fin = true;
                        break;
                    }
                    default: {
                        System.out.println("Erreur : entrez un nombre entre 1 et 3 ");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur : entrez un nombre entre 1 et 3");
            }
        } while (!fin);
    }

    private static void sauvgarde(Scanner sc, Reseau r, String fichierReseau) throws IOException {
        System.out.println("Entrez le nom du fichier : ");
        String fichier = sc.nextLine();
        if (fichier.equals(fichierReseau)) {
            throw new IOException("Le fichier de sauvegarde doit être différent du fichier du premier reseau.");
        }
        try {
            ReseauFactory.reseauToFile(fichier, r);
            System.out.println("Sauvegarde effectuée dans le fichier : " + fichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private static void afficherMenu() {
        System.out.print("1) Résolution automatique \n" +
                "2) Sauvegarder la solution actuelle \n" +
                "3) Fin \n" +
                "Votre choix :");
    }
}
