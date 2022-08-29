package adria.pfa.adriaReporting.enumeration;

public enum TypeProduit {
    IMPORT("IM"),
    EXPORT("EX");

    private final String value;

    private TypeProduit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
