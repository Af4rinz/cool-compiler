package compiler;

import compiler.symbols.Symbol;

import java.util.HashMap;

public class Table {
    private HashMap<String, Symbol> symbolTable = new HashMap<>();

    public HashMap<String, Symbol> getSymbolTable() {
        return symbolTable;
    }
    public void insert(String key,Symbol value){
        symbolTable.put(key, value);
    }
    public boolean lookup(String key){
        return symbolTable.containsKey(key);
    }
}
