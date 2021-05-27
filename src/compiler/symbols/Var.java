package compiler.symbols;

public class Var extends Symbol {
    private final String name;
    private final String type;

    public Var(String name, String type, int row, int column) {
        super(row, column);
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Var name: %s Var type: %s", name, type);
    }

    public String getType() {
        return type;
    }
}
