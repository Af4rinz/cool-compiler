package compiler.symbols;

public class ClassSymbol extends Symbol {
    private final String name;
    private final String parent;

    public ClassSymbol(String name, int row, int column) {
        super(row, column);
        this.name = name;
        this.parent = "Object";
    }

    public ClassSymbol(String name, String parent, int row, int column) {
        super(row, column);
        this.name = name;
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return String.format("Class: %s Parent: %s", name, parent);
    }
}
