package menu;

import factory.ReseauFactory;
import reseau.Optimisation;
import reseau.Reseau;

import java.io.IOException;
import java.util.Scanner;

/**
 * Menu de résolution automatique du réseau électrique.
 * <p>
 * Propose l'optimisation par algorithme hybride et la sauvegarde
 * de la solution obtenue.
 *
 * @author Votre nom
 * @version 1.0
 */
public class Menu3 {

    /**
     * Affiche et gère le menu de résolution automatique.
     * <p>
     * Options disponibles :
     * <ul>
     * <li>Résolution automatique par optimisation</li>
     * <li>Sauvegarde de la solution actuelle</li>
     * <li>Fin du programme</li>
     * </ul>
     *
     * @param sc le scanner pour la saisie utilisateur
     * @param reseau le réseau à optimiser
     * @param fichierReseau le chemin du fichier réseau source
     */
    public static void menu(Scanner sc, Reseau reseau, String fichierReseau) {
        int choix;
        boolean fin = false;
        do {
            afficherMenu();
            try {
                choix = Integer.parseInt(sc.nextLine());
                switch (choix) {
                    case 1:
                        resolutionAutomatique(reseau);
                        break;
                    case 2: {
                        try {
                            sauvgarde(sc, reseau, fichierReseau);
                        } catch (IOException e) {
                            System.out.println("Erreur : " + e.getMessage());
                        }
                        break;
                    }
                    case 3:
                        fin = true;
                        System.out.println("Fin du programme.");
                        break;
                    default:
                        System.out.println("Erreur : entrez un nombre entre 1 et 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur : entrez un nombre entier valide.");
            }
        } while (!fin);
    }

    /**
     * Lance l'optimisation automatique du réseau et affiche les résultats.
     * <p>
     * Compare le coût initial et final, puis affiche un récapitulatif
     * détaillé avec le pourcentage d'amélioration.
     *
     * @param reseau le réseau à optimiser
     */
    private static void resolutionAutomatique(Reseau reseau) {
        reseau.calculCout();
        double coutInitial = reseau.getCout();
        System.out.printf("Cout avant optimisation : %.3f%n", coutInitial);

        double coutFinal = Optimisation.optimiser(reseau);

        Optimisation.afficherDetails(reseau);

        double amelioration = coutInitial - coutFinal;
        double pourcentage = (coutInitial > 0) ? (100.0 * amelioration / coutInitial) : 0.0;

        System.out.println("=== Récapitulatif ===");
        System.out.printf("Cout initial  : %.3f%n", coutInitial);
        System.out.printf("Cout final    : %.3f%n", coutFinal);
        System.out.printf("Amelioration  : %.3f (%.1f%%)%n", amelioration, pourcentage);
        System.out.println();
    }

    /**
     * Sauvegarde le réseau actuel dans un fichier.
     * <p>
     * Demande le nom du fichier de destination et vérifie qu'il
     * diffère du fichier source.
     *
     * @param sc le scanner pour la saisie utilisateur
     * @param r le réseau à sauvegarder
     * @param fichierReseau le chemin du fichier source (interdit comme destination)
     * @throws IOException si le fichier de destination est identique au fichier source
     */
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

    /**
     * Affiche les options du menu à l'écran.
     */
    private static void afficherMenu() {
        System.out.println();
        System.out.println("1) Resolution automatique");
        System.out.println("2) Sauvegarder la solution actuelle");
        System.out.println("3) Fin");
        System.out.print("Votre choix : ");
    }
}
