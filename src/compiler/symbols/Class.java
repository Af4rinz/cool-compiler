package compiler.symbols;

public class Class extends Symbol{
    private final String name;
    private final String parent;

    public Class(String name, int row, int column) {
        super(row, column);
        this.name = name;
        this.parent = "object";
    }

    public Class(String name, String parent, int row, int column) {
        super(row, column);
        this.name = name;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return String.format("Class: %s Parent: %s", name, parent);
    }
}
