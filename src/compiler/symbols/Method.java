package compiler.symbols;

import java.util.LinkedList;

public class Method extends Symbol {
    private final String name;
    private final String returnType;
    private final LinkedList<String> params = new LinkedList<>();

    public Method(String name, String returnType, int row, int column) {
        super(row, column);
        this.name = name;
        this.returnType = returnType;
    }

    public void addParam(String param) {
        params.add(param);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(String.format("Method: %s Returns: %s Parameters: ", name, returnType));
        for (String param : params)
            out.append(param).append(' ');
        return out.toString();
    }
}
