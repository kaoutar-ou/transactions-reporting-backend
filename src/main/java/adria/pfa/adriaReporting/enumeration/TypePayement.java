package adria.pfa.adriaReporting.enumeration;

public enum TypePayement {
    MIXTE("Mixte"),
    PAYEMENT_DIFFERE("Payement différé"),
    PAYEMENT_CONTRE("Payement contre");

    private final String value;

    private TypePayement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
