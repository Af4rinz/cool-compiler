package compiler;

import compiler.symbols.Symbol;

import java.util.LinkedList;

public class Scope {
    private final String name;
    private final Scope parent;
    private final Table table = new Table();
    private final LinkedList<Scope> children = new LinkedList<>();
    private final int depth ;
    private String type;

    public Scope(String name, Scope parent, int depth, String type) {
        this.name = name;
        this.parent = parent;
        this.depth = depth;
        this.type = type;
    }

    public void addChild(Scope child){
        children.add(child);
    }

    public void insert(String key, Symbol value){
        table.insert(key, value);
    }

    public boolean lookup(String key){
        return table.lookup(key);
    }

    public String getName() {
        return name;
    }

    public Scope getParent() {
        return parent;
    }

    public Table getTable() {
        return table;
    }

    public LinkedList<Scope> getChildren() {
        return children;
    }

    public int getDepth() {
        return depth;
    }
}
