package menu;

import reseau.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Menu de construction manuelle du réseau électrique.
 * <p>
 * Permet de créer le réseau en ajoutant des générateurs, des maisons
 * et en établissant les connexions entre eux. Vérifie la cohérence
 * du réseau avant de passer au menu suivant.
 *
 * @author Votre nom
 * @version 1.0
 */
public class Menu1 {

    /**
     * Affiche et gère le menu principal de construction du réseau.
     * <p>
     * Options disponibles :
     * <ul>
     * <li>Ajout de générateurs</li>
     * <li>Ajout de maisons</li>
     * <li>Création de connexions maison-générateur</li>
     * <li>Suppression de connexions</li>
     * <li>Passage au menu suivant (après validation)</li>
     * </ul>
     *
     * @param sc le scanner pour la saisie utilisateur
     * @param r le réseau à construire
     */
    public static void menu(Scanner sc, Reseau r) {
        System.out.println("----MENU PRINCIPAL----");
        int choix;
        boolean fin = false;
        do {
            afficherMenu();
            try {
                choix = Integer.parseInt(sc.nextLine());
                switch (choix) {
                    case 1: {
                        addGenerateur(sc, r);
                        break;
                    }
                    case 2: {
                        addMaison(sc, r);
                        break;
                    }
                    case 3: {
                        addConnexion(sc, r);
                        break;
                    }
                    case 4: {
                        supprConnnexion(sc, r);
                        break;
                    }
                    case 5: {
                        try {
                            passageSuivant(r);
                            System.out.println("Passage au menu suivant...\n");
                            fin = true;
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            break;
                        }
                    }
                    default: {
                        System.out.println("Erreur de choix, entrez un nombre entre 1 et 5.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur de choix, entrez un nombre entre 1 et 5.");
            }

        } while (!fin);
        System.out.println(r);
    }

    /**
     * Ajoute une maison au réseau avec validation des entrées.
     * <p>
     * Format attendu : {@code NOM CONSOMMATION} où CONSOMMATION est
     * BASSE, NORMAL ou FORTE.
     * Exemple : {@code M1 NORMAL}
     *
     * @param sc le scanner pour la saisie utilisateur
     * @param r le réseau cible
     */
    private static void addMaison(Scanner sc, Reseau r) {
        System.out.println("Ajouter une maison (format : NOM CONSOMMATION (BASSE,NORMAL,FORTE) ex : M1 NORMAL) : \t ");
        String maisonString = sc.nextLine();
        if (maisonString.matches("(?i)^[A-Za-z0-9]+\\s(BASSE|NORMAL|FORTE)$")) {
            String[] maisonTab = maisonString.split(" ");
            Consommation conso = Consommation.valueOf(maisonTab[1].toUpperCase());
            if (!r.maisonDansReseau(maisonTab[0])) {
                Maison m = new Maison(maisonTab[0], conso);
                try {
                    r.addMaison(m);
                    System.out.println("Ajout de la maison " + maisonTab[0] + " avec une consommation de "
                            + conso.getConsommation() + "kW (" + conso + ") effectué.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("La maison " + maisonTab[0] + " est déja dans le reseau.");
            }
        } else {
            System.out.println(
                    "Erreur : Format INCORRECT, entrez bien le nom de la maison suivi de basse, normal ou forte.");
        }
    }

    /**
     * Ajoute un générateur au réseau avec validation des entrées.
     * <p>
     * Format attendu : {@code NOM CAPACITE} où CAPACITE est un entier positif.
     * Exemple : {@code G1 60}
     *
     * @param sc le scanner pour la saisie utilisateur
     * @param r le réseau cible
     */
    private static void addGenerateur(Scanner sc, Reseau r) {
        System.out.println("Ajouter un générateur (format : NOM CAPACITE ex : G1 60) :  ");
        String generateurString = sc.nextLine();
        if (generateurString.matches("(?i)^[A-Za-z0-9]+\\s-?\\d+$")) {
            String[] generateurTab = generateurString.split(" ");
            if (!r.generateurDansReseau(generateurTab[0])) {
                int capacite = Integer.parseInt(generateurTab[1]);
                if (capacite > 0) {
                    Generateur g = new Generateur(generateurTab[0], capacite);
                    r.addGenerateur(g);
                    System.out.println("Ajout du générateur " + generateurTab[0] + " avec une capacité de "
                            + generateurTab[1] + "kW effectué.");
                } else {
                    System.out.println("Erreur : La capacité du générateur doit être supérieur à 0");
                }
            } else {
                System.out.println("Le générateur " + generateurTab[0] + " est déjà dans le reseau.");
            }
        } else {
            System.out.println("Erreur : Format INCORRECT");
        }
    }

    /**
     * Valide et ajoute une connexion entre une maison et un générateur.
     * <p>
     * Vérifie que la maison et le générateur existent, puis appelle
     * la méthode d'ajout effectif. Format : {@code M1 G1} ou {@code G1 M1}
     *
     * @param sc le scanner pour la saisie utilisateur
     * @param r le réseau cible
     */
    private static void addConnexion(Scanner sc, Reseau r) {
        System.out.println(
                "Ajouter une connexion (format : GENERATEUR MAISON ou MAISON GENERATEUR ex : M1 G1 ou G1 M1) : \t");
        String connexion = sc.nextLine();
        if (connexion.matches("(?i)^[A-Za-z0-9]+\\s[A-Za-z0-9]+$")) {
            String maisonNom, generateur;
            String[] connexionTab = connexion.split(" ");
            if (r.maisonDansReseau(connexionTab[0]) && r.generateurDansReseau(connexionTab[1])) {
                maisonNom = connexionTab[0];
                generateur = connexionTab[1];
                Maison maison = r.getMaison(maisonNom);
                ajouterConnexionAuReseau(r, generateur, maison, maisonNom);
            } else if (r.generateurDansReseau(connexionTab[0]) && r.maisonDansReseau(connexionTab[1])) {
                maisonNom = connexionTab[1];
                generateur = connexionTab[0];
                Maison maison = r.getMaison(maisonNom);
                ajouterConnexionAuReseau(r, generateur, maison, maisonNom);
            } else {
                System.out.println(
                        "Erreur : Maison et/ou générateur inexistant " + connexionTab[0] + " " + connexionTab[1]);
            }
        } else {
            System.out.println("Erreur : Format INCORRECT");
        }
    }

    /**
     * Valide et supprime une connexion entre une maison et un générateur.
     * <p>
     * Vérifie l'existence de la connexion avant suppression.
     * Format : {@code M1 G1} ou {@code G1 M1}
     *
     * @param sc le scanner pour la saisie utilisateur
     * @param r le réseau cible
     */
    private static void supprConnnexion(Scanner sc, Reseau r) {
        System.out.println(
                "Supprimer une connexion (format : GENERATEUR MAISON ou MAISON GENERATEUR ex : M1 G1 ou G1 M1) : \t");
        String connexion = sc.nextLine();
        if (connexion.matches("(?i)^[A-Za-z0-9]+\\s[A-Za-z0-9]+$")) {
            String maisonNom, generateur;
            String[] connexionTab = connexion.split(" ");
            if (r.maisonDansReseau(connexionTab[0]) && r.generateurDansReseau(connexionTab[1])) {
                maisonNom = connexionTab[0];
                generateur = connexionTab[1];
                Maison maison = r.getMaison(maisonNom);
                supprConnexionReseau(r, generateur, maison, maisonNom);
            } else if (r.generateurDansReseau(connexionTab[0]) && r.maisonDansReseau(connexionTab[1])) {
                maisonNom = connexionTab[1];
                generateur = connexionTab[0];
                Maison maison = r.getMaison(maisonNom);
                supprConnexionReseau(r, generateur, maison, maisonNom);
            } else {
                System.out.println(
                        "Erreur : Maison et/ou générateur inexistant " + connexionTab[0] + " " + connexionTab[1]);
            }
        } else {
            System.out.println("Erreur : Format INCORRECT");
        }
    }

    /**
     * Supprime effectivement une connexion du réseau.
     * <p>
     * Vérifie que la maison est bien connectée au générateur spécifié
     * avant de procéder à la suppression.
     *
     * @param r le réseau
     * @param generateur le nom du générateur
     * @param maison la maison à déconnecter
     * @param maisonNom le nom de la maison
     */
    private static void supprConnexionReseau(Reseau r, String generateur, Maison maison, String maisonNom) {
        if (r.maisonConnecte(maison)) {
            if (r.getConnexions().get(maison).getNom().equals(generateur)) {
                r.supprConnexion(maison, r.getGenerateur(generateur));
                System.out.println("Suppression de la connexion : " + generateur + "-----" + maisonNom + " effectué.");
            } else {
                System.out.println("La maison " + maisonNom + " est connectée au generateur "
                        + r.getConnexions().get(maison).getNom());
            }
        } else {
            System.out.println("La maison " + maisonNom + " n'est pas connectée à un generateur.");
        }
    }

    /**
     * Ajoute effectivement une connexion au réseau.
     * <p>
     * Vérifie que la maison n'est pas déjà connectée à un autre générateur
     * avant de créer la connexion.
     *
     * @param r le réseau
     * @param generateur le nom du générateur
     * @param maison la maison à connecter
     * @param maisonNom le nom de la maison
     */
    private static void ajouterConnexionAuReseau(Reseau r, String generateur, Maison maison, String maisonNom) {
        if (!r.maisonConnecte(maison)) {
            r.addConnexion(maison, r.getGenerateur(generateur));
            System.out.println("Ajout de la connexion " + generateur + "-----" + maisonNom + " effectué.");
        } else {
            System.out.println("La maison " + maisonNom + " est déjà connectée au generateur "
                    + r.getConnexions().get(maison).getNom() + " et une maison" +
                    " ne peut être associé qu'à un seul générateur ");
        }
    }

    /**
     * Vérifie la validité du réseau avant passage au menu suivant.
     * <p>
     * Contrôle que toutes les maisons sont connectées à un générateur.
     * Le réseau doit contenir au moins une connexion.
     *
     * @param r le réseau à valider
     * @throws Exception si le réseau est vide ou contient des maisons non connectées
     */
    private static void passageSuivant(Reseau r) throws Exception {
        ArrayList<Maison> maisonsNonConnectees = new ArrayList<>();
        if (r.getConnexions().isEmpty()) {
            throw new Exception("Aucune connexion dans le réseau.");
        }
        for (Maison m : r.getMaisons()) {
            if (r.getConnexions().get(m) == null) {
                maisonsNonConnectees.add(m);
            }
        }
        if (!maisonsNonConnectees.isEmpty()) {
            StringBuilder maisons = new StringBuilder();
            for (Maison m : maisonsNonConnectees) {
                maisons.append(m.getNom()).append(" ");
            }
            throw new Exception("Maison(s) : " + maisons.toString() + " non connectée(s) au réseau.");
        }
    }

    /**
     * Affiche les options du menu à l'écran.
     */
    private static void afficherMenu() {
        System.out.print("1) Ajouter un générateur \n" +
                "2) Ajouter une maison \n" +
                "3) Ajouter une connexion entre une maison et un générateur exitsants \n" +
                "4) Supprimer une connexion entre une maison et un générateur existants \n" +
                "5) Passage au menu suivant \n" +
                "Votre choix :  ");
    }
}
