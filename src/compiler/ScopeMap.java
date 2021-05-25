package compiler;

import java.util.HashMap;

public class ScopeMap {
    private static HashMap<String ,Scope> scopes = new HashMap<>();
    public void insertScope(String key,Scope value){
        scopes.put(key,value);
    }
    public Scope getScope(String key){
        return scopes.get(key);
    }
}
