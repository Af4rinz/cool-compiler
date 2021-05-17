package compiler;

public class Property extends Symbol{
    private final String name;
    private final String type;

    public Property(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString(){
        return String.format("Field name: %s type: %s", name, type);
    }
}
