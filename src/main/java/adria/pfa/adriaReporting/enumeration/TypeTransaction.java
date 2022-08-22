package adria.pfa.adriaReporting.enumeration;

public enum TypeTransaction {
    EMISSION("Emission"),
    MODIFICATION("Modification"),
    AMENDEMENT("Amendement"),
    UTILISATION_A_VUE("Utilisation à vue"),
    UTILISATION_A_ECHEANCE("Utilisation à échéance"),
    MESSAGE("Message");

    private final String value;

    private TypeTransaction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
