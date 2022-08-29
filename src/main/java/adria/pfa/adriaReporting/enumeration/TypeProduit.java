package adria.pfa.adriaReporting.enumeration;

public enum TypeProduit {
    IMPORT("Import", "IM"),
    EXPORT("Export", "EX");

    private final String value;
    private final String code;

    private TypeProduit(String value, String code) {
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
