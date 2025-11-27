package menu;

import reseau.*;
import java.util.*;

/**
 * Deuxième menu de notre projet, permet de gérer le réseau créé dans le menu précédent.
 * Il offre trois actions : calculer le coût, modifier une connexion, ou afficher le réseau.
 */
public class Menu2 {

    public static void menu(Scanner sc, Reseau reseau) {
        int choix;
        boolean fin = false;
        do{
            afficherMenu();
            try {
                choix = Integer.parseInt(sc.nextLine());
                switch(choix){
                    case 1:{
                        calculerCout(reseau);
                        break;
                    }
                    case 2:{
                        modifierConnexion(sc, reseau);
                        break;
                    }
                    case 3:{
                        afficherReseau(reseau);
                        break;
                    }
                    case 4:{
                        fin = true;
                        break;
                    }
                    default:{
                        System.out.println("Erreur de choix, entrez un nombre entre 1 et 4.");
                    }
                }
            }catch (NumberFormatException e){
                System.out.println("Erreur de choix, entrez un nombre entre 1 et 4.");
            }

        }while(!fin);
    }

    /**
     * Calcule et affiche le coût du réseau
     */
    private static void calculerCout(Reseau reseau){
        reseau.calculCout();
        System.out.println("===== COÛT DU RÉSEAU \"" + reseau.getNom() + "\" =====");
        System.out.printf("Taux d'utilisation moyen : %.3f%n", reseau.getTauxUtilisationMoyen());
        System.out.printf("Dispersion totale : %.3f%n", reseau.getDisp());
        System.out.printf("Surcharge totale : %.3f%n", reseau.getSurcharge());
        System.out.printf("Pénalité λ : %.2f%n", reseau.getPenalite());
        System.out.printf("Coût total = Dispersion + λ × Surcharge : %.3f%n", reseau.getCout());
        System.out.println();
    }

    /**
     * Permet de modifier une connexion existante (ancienne -> nouvelle)
     */
    private static void modifierConnexion(Scanner sc, Reseau reseau){
        System.out.println("Saisissez l'ANCIENNE connexion (format : M1 G1 ou G1 M1) :");
        String ancienneLigne = sc.nextLine();
        if(!ancienneLigne.matches("(?i)^[A-Za-z0-9]+\\s[A-Za-z0-9]+$")){
            System.out.println("Erreur : format INCORRECT");
            return;
        }
        String[] ancienneTable = ancienneLigne.split(" ");
        String ancienA = ancienneTable[0];
        String ancienB = ancienneTable[1];

        String nomMaisonAncienne;
        String nomGenerateurAncien;

        if(reseau.maisonDansReseau(ancienA) && reseau.generateurDansReseau(ancienB)){
            nomMaisonAncienne = ancienA; nomGenerateurAncien = ancienB;
        }else if(reseau.maisonDansReseau(ancienB) && reseau.generateurDansReseau(ancienA)){
            nomMaisonAncienne = ancienB; nomGenerateurAncien = ancienA;
        }else{
            System.out.println("Erreur : maison et/ou générateur inexistant.");
            return;
        }

        Maison maison = reseau.getMaison(nomMaisonAncienne);
        Generateur generateurAncien = reseau.getGenerateur(nomGenerateurAncien);

        Generateur generateurActuel = reseau.getConnexions().get(maison);
        if (generateurActuel == null) {
            System.out.println("Erreur : la maison " + nomMaisonAncienne + " n'est connectée à aucun générateur.");
            return;
        }
        if (!generateurActuel.getNom().equals(nomGenerateurAncien)) {
            System.out.println("Erreur : la maison " + nomMaisonAncienne + " n'est pas connectée à " + nomGenerateurAncien +
                    " (générateur actuel : " + generateurActuel.getNom() + ").");
            return;
        }

        System.out.println("Saisissez la NOUVELLE connexion (même maison, autre générateur) :");
        String nouvelleLigne = sc.nextLine();
        if(!nouvelleLigne.matches("(?i)^[A-Za-z0-9]+\\s[A-Za-z0-9]+$")){
            System.out.println("Erreur : format INCORRECT");
            return;
        }
        String[] nouvelleTable = nouvelleLigne.split(" ");
        String nouveauA = nouvelleTable[0];
        String nouveauB = nouvelleTable[1];

        String nomMaisonNouvelle;
        String nomGenerateurNouveau;

        if(reseau.maisonDansReseau(nouveauA) && reseau.generateurDansReseau(nouveauB)){
            nomMaisonNouvelle = nouveauA; nomGenerateurNouveau = nouveauB;
        }else if(reseau.maisonDansReseau(nouveauB) && reseau.generateurDansReseau(nouveauA)){
            nomMaisonNouvelle = nouveauB; nomGenerateurNouveau = nouveauA;
        }else{
            System.out.println("Erreur : maison et/ou générateur inexistant.");
            return;
        }

        if(!nomMaisonNouvelle.equals(nomMaisonAncienne)){
            System.out.println("Erreur : la maison doit rester la même (" + nomMaisonAncienne + ").");
            return;
        }
        if(nomGenerateurNouveau.equals(nomGenerateurAncien)){
            System.out.println("Erreur : le nouveau générateur doit être différent de l'ancien (" + nomGenerateurAncien + ").");
            return;
        }

        Generateur generateurNouveau = reseau.getGenerateur(nomGenerateurNouveau);
        reseau.changeConnexion(maison, generateurAncien, generateurNouveau);
        System.out.println("Connexion modifiée : " + nomMaisonAncienne + " ----- " + nomGenerateurAncien + " -> " + nomGenerateurNouveau);
    }

    /**
     * Affiche le réseau de manière lisible
     */
    private static void afficherReseau(Reseau reseau){
        System.out.println();
        System.out.println("===== RÉSEAU \"" + reseau.getNom() + "\" =====");

        List<String> nomsGenerateurs = new ArrayList<String>();
        for(Generateur g : reseau.getGenerateurs()){
            nomsGenerateurs.add(g.getNom());
        }
        Collections.sort(nomsGenerateurs);

        for(String nomGenerateur : nomsGenerateurs){
            Generateur generateur = reseau.getGenerateur(nomGenerateur);
            List<String> infosMaisons = new ArrayList<>();
            for(Maison maison : reseau.getMaisons(generateur)){
                int consommation = maison.getConsommation();
                infosMaisons.add(maison.getNom() + "(" + consommation + "kW)");
            }
            Collections.sort(infosMaisons);

            System.out.println("- " + generateur.getNom() + " (capacité : " + generateur.getCapacite() + "kW)");
            if(infosMaisons.isEmpty()){
                System.out.println("  Maisons alimentées : (aucune)");
            }else{
                System.out.println("  Maisons alimentées : " + String.join(", ", infosMaisons));
            }
            System.out.printf("  Charge totale : %dkW | taux d'utilisation = %.3f%n", reseau.getCharge(), generateur.calculTauxUtilisation());
        }

        List<String> maisonsNonConnectees = new ArrayList<>();
        for(Maison maison : reseau.getMaisons()){
            if(!reseau.maisonConnecte(maison)){
                maisonsNonConnectees.add(maison.getNom());
            }
        }
        if(!maisonsNonConnectees.isEmpty()){
            Collections.sort(maisonsNonConnectees);
            System.out.println("Maisons non connectées : " + String.join(", ", maisonsNonConnectees));
        }

        System.out.println();
    }

    private static void afficherMenu(){
        System.out.print("1) Calculer le coût du réseau \n" +
                "2) Modifier une connexion \n" +
                "3) Afficher le réseau \n" +
                "4) Fin \n" +
                "Votre choix :  ");
    }
}