package ErrorHandler;
import compiler.Scope;
public interface Detector {
    public void printRedefineError(String name, String type, int row, int column);
    public void printUndefinedError(String name, String type, int row, int column);
    public void printTypeNotFoundError(String type, int row, int column);
    public boolean lookUpScopes(Scope scope, String key);
    public String FindClassName(Scope scope, String objectName);
}

