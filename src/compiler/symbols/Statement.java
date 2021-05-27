package compiler.symbols;

public class Statement extends Symbol {
    private final String name;

    public Statement(String name, int row, int column) {
        super(row, column);
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Statement name: %s", name);
    }
}
