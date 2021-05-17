package compiler;

import org.stringtemplate.v4.ST;

public class Statement extends Symbol {
    private final String name;

    public Statement(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Statement name: %s", name);
    }
}
