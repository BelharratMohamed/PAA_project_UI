package reseau;

/**
 * Indique la consomation d'une maison BASSE , NORMAL ou FORTE
 */

public enum Consommation {
    BASSE(10),NORMAL(20),FORTE(40);

    private int value;
    private Consommation(int value) {
        this.value = value;
    }

    /** Retourne la valeur associé a consommation **/
    public int getConsommation() {
        return value;
    }
}
