package reseau;

/**
 * Représente une maison du réseau électrique.
 * <p>
 * Chaque maison possède une consommation électrique définie et doit
 * obligatoirement être connectée à un générateur pour être alimentée.
 *
 * @author Votre nom
 * @version 1.0
 */
public class Maison {

    /** Consommation électrique de la maison */
    private Consommation conso;

    /** Nom identifiant unique de la maison */
    private String nom;

    /**
     * Crée une nouvelle maison avec un nom et une consommation.
     *
     * @param nom le nom identifiant de la maison
     * @param conso l'objet Consommation définissant la demande électrique
     */
    public Maison(String nom, Consommation conso) {
        this.nom = nom;
        this.conso = conso;
    }

    /**
     * Retourne le nom de la maison.
     *
     * @return le nom identifiant de la maison
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la consommation électrique de la maison en kW.
     *
     * @return la consommation en kilowatts
     */
    public int getConsommation() {
        return conso.getConsommation();
    }

    /**
     * Retourne une représentation textuelle de la maison au format Prolog.
     * <p>
     * Format : {@code maison(nom, consommation).}
     *
     * @return la chaîne de caractères représentant la maison
     */
    public String toString() {
        return "maison(" + nom + "," + conso.toString() + ").";
    }
}
