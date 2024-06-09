import java.util.*;

public class SymbolTable{
    LinkedList<HashMap<String, VariableElement>> symbolTable;

    public SymbolTable(){
        symbolTable = new LinkedList<>();
    }

    public class VariableElement{
        String varType;
        boolean isFinal = false;
        boolean isArray = false;
        boolean isMethod = false;
        List<String> methodArguments;
        boolean containsReturn = false;

        public VariableElement(String variableType, boolean isArray, boolean isFinal){
            this.varType = variableType;
            this.isFinal = isFinal;
            this.isArray = isArray;
        }

        public VariableElement(String variableType, boolean isArray){
            this.varType = variableType;

        }

        public VariableElement(String variableType, List<String> arguments){
            this.varType = variableType;
            this.isFinal = false;
            this.isMethod = true;
            this.methodArguments = arguments;
        }

    }

    void addScope(){
        //HashMap<String, VariableElement> newScope = new HashMap<String, VariableElement>();
        symbolTable.addFirst(new HashMap<String, VariableElement>());
    }

    void removeScope(){
        symbolTable.removeFirst();
    }

    public boolean addInCurrentScope(String variableName, String variableType, boolean isVariableArray, boolean isVariableFinal){
        if(symbolTable.getFirst().containsKey(variableName)){
            return false;
        }
        VariableElement variableObject = new VariableElement(variableType, isVariableArray, isVariableFinal);
        symbolTable.getFirst().put(variableName, variableObject);
        return true;
    }

    public boolean addInCurrentScope(String methodName, String methodType, boolean isMethod, List<String> arguments){
        if(symbolTable.getFirst().containsKey(methodName)){
            return false;
        }
        VariableElement variableObject = new VariableElement(methodType, arguments);
        symbolTable.getFirst().put(methodName, variableObject);
        return true;   
    }

    public VariableElement findVariable(String variableToFind){
        for(HashMap<String, VariableElement> currenrScope : symbolTable){
            if(currenrScope.containsKey(variableToFind)){
                return currenrScope.get(variableToFind);
            }
        }
        return null;
    }

    public String getVariableType(String variableName){
        String variableTypeString = "";
        VariableElement variableObject = findVariable(variableName);
        String variableType = variableObject.varType;
        return variableTypeString;
    }

    public boolean isCoercible(String type1, String type2){
        if(type1.equals("float")){
            if(type2.equals("int") || type2.equals("float")){
                return true;
            }
        }
        else if(type1.equals("int")){
            if(type2.equals("int")){
                return true;
            }
        }
        else if(type1.equals("bool")){
            if(type2.equals("bool") || type2.equals("int")){
                return true;
            }
        }
        else if(type1.equals("char")){
            if(type2.equals("char")){
                return true;
            }
        }
        else if(type1.equals("void")){
            if(type2 == null || type2.equals("void")){
                return true;
            }
        }
        else if(type1.equals("string")){
            if(type2.equals("void")){
                return false;
            }
            return true;
        }
        
        return false;
    }
}