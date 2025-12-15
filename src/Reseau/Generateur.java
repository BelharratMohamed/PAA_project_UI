package reseau;

import java.util.HashSet;

/**
 *  Generateur électrique 
 */

public class Generateur {
    /** Capaciite max du generateur et charge actuelle du generateur */
    private int capacite;
    private int chargeActuelle;
    
    private String nom;


    /**
     * Cree un nouveau generateur
     *
     * @param nom Nom du generateur
     * @param capacite Capacite max du generateur
     */
    public Generateur(String nom , int capacite) {
        this.capacite = capacite;
        this.nom = nom;
    }

    /**
     * Calcul le taux d'utilisation actuelle du generateur
     *
     * @return taux d'utilisation
     */
    public double calculTauxUtilisation(){
        return capacite == 0 ? 0.0 : (double) chargeActuelle / capacite;
    }

    /**
     * Ajoute une maison m au generateur, utilisée quand on ajoute une connexion
     * @param m
     */
    public void addMaison(Maison m){
        chargeActuelle += m.getConsommation();
    }

    /**
     * Supprime une maison m au generateur, utilisée quand on supprime une connexion
     * @param m
     */
    public void supprimerMaison(Maison m){
        chargeActuelle -= m.getConsommation();
    }

    /**
     * Supprime une maison m du generateur, utilisée quand on change une connexion
     * @param m
     */
    public void deleteMaison(Maison m){
        chargeActuelle -= m.getConsommation();
    }

    public void resetCharge() {
        this.chargeActuelle = 0;
    }

    public int getCapacite() {
        return capacite;
    }

    public int getChargeActuelle() {
        return chargeActuelle;
    }

    public String getNom() {
        return nom;
    }

    public String toString(){
        return "generateur(" + nom + "," + capacite + ").";
    }
}