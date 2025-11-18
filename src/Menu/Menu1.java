package Menu;

import Reseau.*;

import java.io.Reader;
import java.util.Scanner;

/**
 * Premier menu de notre projet, permet de gerer le reseau manuellement
 */
public class Menu1 {
    public static void menu(Scanner sc, Reseau r) {
        int choix;
        boolean fin = false;
        do{
            menu();
            try {
                choix = Integer.parseInt(sc.nextLine());
                switch(choix){
                    case 1:{
                        addGenerateur(sc, r);
                        break;
                    }
                    case 2:{
                        addMaison(sc, r);
                        break;
                    }
                    case 3:{
                        addConnexion(sc, r);
                        break;
                    }
                    case 4:{
                        supprConnnexion(sc, r);
                        break;
                    }
                    case 5:{
                            System.out.println("Passage au menu suivant...\n");
                            fin = true;
                        break;
                    }
                    default: {
                        System.out.println("Erreur de choix, entrez un nombre entre 1 et 4.");
                    }
                }
            }catch (NumberFormatException e){
                System.out.println("Erreur de choix, entrez un nombre entre 1 et 4.");
            }

        }while(!fin);
        System.out.println(r);
    }

    /**
     * Ajoute une maison au reseau
     * @param sc Scanner d'entrée
     * @param r Reseau
     */
    private static void addMaison(Scanner sc, Reseau r){
        System.out.println("Ajouter une maison (format : NOM CONSOMMATION (BASSE,NORMAL,FORTE) ex : M1 NORMAL) : \t ");
        String maisonString = sc.nextLine();
        if(maisonString.matches("(?i)^[A-Za-z0-9]+\\s(BASSE|NORMAL|FORTE)$")){
            String[] maisonTab = maisonString.split(" ");
            Consommation conso = Consommation.valueOf(maisonTab[1].toUpperCase());
            if(!r.maisonDansReseau(maisonTab[0])) {
                Maison m = new Maison(maisonTab[0].toUpperCase(), conso);
                try {
                    r.addMaison(m);
                    System.out.println("Ajout de la maison " + maisonTab[0].toUpperCase() + " avec une consommation de " + conso.getConsommation() + "kW (" + conso + ") effectué.");
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }else{
                System.out.println("La maison " + maisonTab[0] + " est déja dans le reseau.");
            }
        }else{
            System.out.println("Erreur : Format INCORRECT, entrez bien le nom de la maison suivi de basse, normal ou forte.");
        }
    }

    /**
     * Ajoute un générateur au reseau
     * @param sc Scanner d'entrée
     * @param r Reseau
     */
    private static void addGenerateur(Scanner sc,Reseau r){
        System.out.println("Ajouter un générateur (format : NOM CAPACITE ex : G1 60) :  ");
        String generateurString = sc.nextLine();
        if(generateurString.matches("(?i)^[A-Za-z0-9]+\\s-?\\d+$")){                                          //regex généré par chatGPT
            String[] generateurTab = generateurString.split(" ");
            if(!r.generateurDansReseau(generateurTab[0])) {
                int capacite = Integer.parseInt(generateurTab[1]);
                if (capacite > 0) {
                    Generateur g = new Generateur(generateurTab[0].toUpperCase(), capacite);
                    r.addGenerateur(g);
                    System.out.println("Ajout du générateur " + generateurTab[0].toUpperCase() + " avec une capacité de " + generateurTab[1] + "kW effectué.");
                } else {
                    System.out.println("Erreur : La capacité du générateur doit être supérieur à 0");
                }
            }else{
                System.out.println("Le générateur " + generateurTab[0] + " est déjà dans le reseau.");
            }
        }else{
            System.out.println("Erreur : Format INCORRECT");
        }
    }

    /**
     * Verifie que la connexion est possible et appelle ajouterConnexionAuReseau
     * @param sc Scanner d'entrée
     * @param r Reseau
     */
    private static void addConnexion(Scanner sc,Reseau r){
        System.out.println("Ajouter une connexion (format : GENERATEUR MAISON ou MAISON GENERATEUR ex : M1 G1 ou G1 M1) : \t");
        String connexion = sc.nextLine();
        if(connexion.matches("(?i)^[A-Za-z0-9]+\\s[A-Za-z0-9]+$")){
            String maisonNom,generateur;
            String[] connexionTab = connexion.split(" ");
            if(r.maisonDansReseau(connexionTab[0].toUpperCase()) && r.generateurDansReseau(connexionTab[1].toUpperCase())){
                maisonNom = connexionTab[0].toUpperCase();
                generateur = connexionTab[1].toUpperCase();
                Maison maison = r.getMaisons().get(maisonNom);
                ajouterConnexionAuReseau(r,generateur,maison,maisonNom);
            }else if(r.generateurDansReseau(connexionTab[0].toUpperCase()) &&  r.maisonDansReseau(connexionTab[1].toUpperCase())){
                maisonNom = connexionTab[1].toUpperCase();
                generateur = connexionTab[0].toUpperCase();
                Maison maison = r.getMaisons().get(maisonNom);
                ajouterConnexionAuReseau(r,generateur,maison,maisonNom);
            }else{
                System.out.println("Erreur : Maison et/ou générateur inexistant " + connexionTab[0].toUpperCase() + " "  + connexionTab[1].toUpperCase());
            }
        }else{
            System.out.println("Erreur : Format INCORRECT");
        }
    }

    /**
     * Verifie que la connexion est possible et appelle supprConnexionReseau
     * @param sc Scanner d'entrée
     * @param r Reseau
     */
    private static void supprConnnexion(Scanner sc,Reseau r){
        System.out.println("Supprimer une connexion (format : GENERATEUR MAISON ou MAISON GENERATEUR ex : M1 G1 ou G1 M1) : \t");
        String connexion = sc.nextLine();
        if(connexion.matches("(?i)^[A-Za-z0-9]+\\s[A-Za-z0-9]+$")){
            String maisonNom,generateur;
            String[] connexionTab = connexion.split(" ");
            if(r.maisonDansReseau(connexionTab[0].toUpperCase()) && r.generateurDansReseau(connexionTab[1].toUpperCase())){
                maisonNom = connexionTab[0].toUpperCase();
                generateur = connexionTab[1].toUpperCase();
                Maison maison = r.getMaisons().get(maisonNom);
                supprConnexionReseau(r,generateur,maison,maisonNom);
            }else if(r.generateurDansReseau(connexionTab[0].toUpperCase()) &&  r.maisonDansReseau(connexionTab[1].toUpperCase())){
                maisonNom = connexionTab[1].toUpperCase();
                generateur = connexionTab[0].toUpperCase();
                Maison maison = r.getMaisons().get(maisonNom);
                supprConnexionReseau(r,generateur,maison,maisonNom);
            }else{
                System.out.println("Erreur : Maison et/ou générateur inexistant " + connexionTab[0].toUpperCase() + " "  + connexionTab[1].toUpperCase());
            }
        }else{
            System.out.println("Erreur : Format INCORRECT");
        }
    }

    /**
     * Supprime concrètement la connexion au reseau
     * @param r Reseau
     * @param generateur Generateur
     * @param maison Maison
     * @param maisonNom Nom de la maison
     */
    private static void supprConnexionReseau(Reseau r, String generateur, Maison maison, String maisonNom){
        if(r.maisonConnecte(maison)) {
            if(r.getConnexions().get(maison).getNom().equals(generateur)) {
                r.supprConnexion(maison, r.getGenerateurs().get(generateur));
                System.out.println("Suppression de la connexion : " + generateur + "-----" + maisonNom + " effectué.");
            }else{
                System.out.println("La maison " + maisonNom + " est connectée au generateur " + r.getConnexions().get(maison).getNom());
                }
        }else{
            System.out.println("La maison " + maisonNom + " n'est pas connectée à un generateur.");
        }
    }

    /**
     * Ajoute concrètement la connexion au reseau
     * @param r Reseau
     * @param generateur Generateur
     * @param maison Maison
     * @param maisonNom Nom de la maison
     */
    private static void ajouterConnexionAuReseau(Reseau r, String generateur, Maison maison, String maisonNom){
        if(!r.maisonConnecte(maison)) {
            r.addConnexion(maison, r.getGenerateurs().get(generateur));
            System.out.println("Ajout de la connexion " + generateur + "-----" + maisonNom + " effectué.");
        }else{
            System.out.println("La maison " + maisonNom + " est déjà connectée au generateur " + r.getConnexions().get(maison).getNom() + " et une maison" +
                    " ne peut être associé qu'à un seul générateur ");
        }
    }

    /**
     * Vérifie que chaque maison est connectée à un seul générateur.
     * Affiche les maisons sans connexion ou avec plusieurs connexions.
     * Retourne true si tout est correct, sinon false.
     */
    private static boolean verifierConnexionsMaisons(Reseau r) {
        boolean ok = true;
        StringBuilder maisonsSans = new StringBuilder();
        StringBuilder maisonsPlusieurs = new StringBuilder();

        // Parcourt les maisons du réseau
        for (Maison m : r.getMaisons().values()) {
            String nomMaison = m.getNom();
            int compteur = 0;

            // Compte le nombre de générateurs reliés à cette maison
            for (java.util.Map.Entry<Maison, Generateur> e : r.getConnexions().entrySet()) {
                Maison maisonConnectee = e.getKey();
                if (maisonConnectee != null && maisonConnectee.getNom().equals(nomMaison)) {
                    compteur++;
                }
            }

            if (compteur == 0) {
                ok = false;
                maisonsSans.append(nomMaison).append(" ");
            } else if (compteur > 1) {
                ok = false;
                maisonsPlusieurs.append(nomMaison).append(" ");
            }
        }

        // Affiche les erreurs éventuelles
        if (!ok) {
            System.out.println();
            System.out.println("Maisons sans connexion : " + maisonsSans.toString().trim());
            System.out.println("Maisons avec plusieurs connexions : " + maisonsPlusieurs.toString().trim());
            System.out.println();
        }

        return ok;
    }


    private static void menu(){
        System.out.print("1) Ajouter un générateur \n" +
                "2) Ajouter une maison \n" +
                "3) Ajouter une connexion entre une maison et un générateur exitsants \n" +
                "4) Supprimer une connexion entre une maison et un générateur existants \n" +
                "5) Passage au menu suivant \n" +
                "Votre choix :  ");
    }


}
