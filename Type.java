public class Type {
    int intVariable;;
    char charVariable;
    String stringVariable;
    float floatVariable;
    boolean booleanVariable;
    int rule;

    public Type(int intVariable){
        this.intVariable = intVariable;
        rule = 1;
    }

    public Type(char charVariable){
        this.charVariable = charVariable;
        rule = 2;
    }

    public Type(String stringVariable){
        this.stringVariable = stringVariable;
        rule = 3;
    }

    public Type(float floatVariable){
        this.floatVariable = floatVariable;
        rule = 4;
    }

    public Type(boolean booleanVariable){
        this.booleanVariable = booleanVariable;
        rule = 5;
    }

    public String toString(int t){
        switch (rule) {
            case 1: return String.valueOf(intVariable);
            case 2: return String.valueOf(charVariable);
            case 3: return String.valueOf(stringVariable);
            case 4: return String.valueOf(floatVariable);
            case 5: return String.valueOf(booleanVariable);
            default: return "";
        }
    }

    
}
