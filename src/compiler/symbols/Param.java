package compiler.symbols;

public class Param extends Symbol {
    private final String name;
    private final String type;

    public Param(String name, String type, int row, int column) {
        super(row, column);
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Param name: %s Param Type: %s", name, type);
    }

    public String getType() {
        return type;
    }
}
