package compiler;

import java.util.LinkedList;

public class ScopeStructure {
    private final String name;
    private final ScopeStructure parent;
    private final Table table = new Table();
    private final LinkedList<ScopeStructure> children = new LinkedList<>();
    private final int depth ;

    public ScopeStructure(String name, ScopeStructure parent, int depth) {
        this.name = name;
        this.parent = parent;
        this.depth = depth;
    }

    public void addChild(ScopeStructure child){
        children.add(child);
    }

    public void insert(String key,Symbol value){
        table.insert(key, value);
    }

    public boolean lookup(String key){
        return table.lookup(key);
    }

    public String getName() {
        return name;
    }

    public ScopeStructure getParent() {
        return parent;
    }

    public Table getTable() {
        return table;
    }

    public LinkedList<ScopeStructure> getChildren() {
        return children;
    }

    public int getDepth() {
        return depth;
    }
}
