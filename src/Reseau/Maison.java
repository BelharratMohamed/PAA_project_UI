package reseau;

/**
 * Maison forcément connectée à un generateur
 */
public class Maison{
    /** Consommation de la maison */
    private Consommation conso;
    private String nom;

    /**
     * Instancie une nouvelle maison
     * @param nom Nom de la maison
     * @param conso Consommation de la maison
     */
    public Maison(String nom, Consommation conso){
        this.nom = nom;
        this.conso = conso;
    }

    public String getNom() {
        return nom;
    }

    public int getConsommation() {
        return conso.getConsommation();
    }

    public String toString(){
        return "maison(" + nom + "," + conso.toString() + ").";
    }
}