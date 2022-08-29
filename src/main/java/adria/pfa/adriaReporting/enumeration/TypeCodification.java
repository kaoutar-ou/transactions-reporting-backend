package adria.pfa.adriaReporting.enumeration;

public enum TypeCodification {
    PRODUIT("Produit"),
    TRANSACTION("Transaction");

    private final String value;

    private TypeCodification(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
