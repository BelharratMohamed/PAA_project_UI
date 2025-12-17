package reseau;

/**
 * Énumération des niveaux de consommation électrique d'une maison.
 * <p>
 * Définit trois niveaux de consommation prédéfinis :
 * <ul>
 * <li>BASSE : 10 kW</li>
 * <li>NORMAL : 20 kW</li>
 * <li>FORTE : 40 kW</li>
 * </ul>
 *
 * @author Votre nom
 * @version 1.0
 */
public enum Consommation {

    /** Consommation basse : 10 kW */
    BASSE(10),

    /** Consommation normale : 20 kW */
    NORMAL(20),

    /** Consommation forte : 40 kW */
    FORTE(40);

    /** Valeur de consommation en kW */
    private int value;

    /**
     * Constructeur privé pour initialiser une valeur de consommation.
     *
     * @param value la consommation en kW
     */
    private Consommation(int value) {
        this.value = value;
    }

    /**
     * Retourne la valeur de consommation en kilowatts.
     *
     * @return la consommation en kW
     */
    public int getConsommation() {
        return value;
    }
}
