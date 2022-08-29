package adria.pfa.adriaReporting.enumeration;

public enum TypeTransaction {
    EMISSION("EM"),
    MODIFICATION("MOD"),
    AMENDEMENT("AM"),
    UTILISATION_A_VUE("UV"),
    UTILISATION_A_ECHEANCE("UH"),
    MESSAGE("MSG");

    private final String value;

    private TypeTransaction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
