package adria.pfa.adriaReporting.enumeration;

public enum TypeTransaction {
    EMISSION("Emission", "EM"),
    MODIFICATION("Modification", "MOD"),
    AMENDEMENT("Amendement", "AM"),
    UTILISATION_A_VUE("Utilisation à vue", "UV"),
    UTILISATION_A_ECHEANCE("Utilisation à échéance", "UE"),
    MESSAGE("Message", "MSG");

    private final String value;
    private final String code;

    private TypeTransaction(String value, String code) {
        this.value = value;
        this.code = code;
    }

    public String getValue() {
        return value;
    }
    public String getCode() {
        return code;
    }
}
