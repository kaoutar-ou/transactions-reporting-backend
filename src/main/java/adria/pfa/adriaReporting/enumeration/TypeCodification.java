package adria.pfa.adriaReporting.enumeration;

public enum TypeCodification {
    TYPE_PRODUIT("Type du produit"),
    TYPE_TRANSACTION("Type de transaction");

    private final String value;

    private TypeCodification(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
