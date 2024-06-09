public class Fielddecl extends Token
{
    String finalStringInput;
    String type;
    String ID,str;
    Expr opExpr;
    int length;
    int rule;
    boolean isVariableFinal, isVariableArray;


    public Fielddecl(String finalStringInput, String type, String ID, Expr opExpr){
        if(finalStringInput == "" ){
            this.finalStringInput = null;
            this.isVariableFinal = false;
        } else {
            this.finalStringInput = finalStringInput;
            this.isVariableFinal = true;
        }
        this.type = type; 
        this.ID = ID;
        this.opExpr = opExpr;
        this.isVariableArray = false;
        rule = 1;
    }

    public Fielddecl(String type, String ID, int length){
        this.type = type; 
        this.ID = ID;
        this.length = length;
        this.isVariableArray = true;
        rule = 2;
    }

    public Fielddecl(){
       
    }

    public String toString(int t){
        String finalString = "";
        if (rule == 1)
        {
            finalString = getTabs(t) + (finalStringInput!=null ? "final " : "") + type + " " + ID + (opExpr == null ? "" : " = " + opExpr.toString(t)) + ";\n";
            return finalString;

        }
        finalString = getTabs(t) + type + " " + ID + "[" + length + "]" + ";\n";
        return finalString;
    }

    public String typeCheck() throws LangException{
        String variableType = this.type;
        if(rule == 1){
        SymbolTable.VariableElement varObj = symbolTable.findVariable(ID);
        if(opExpr!= null)   {
            String exprType = opExpr.typeCheck();
            if(symbolTable.isCoercible(variableType, exprType) == false){
                if(varObj != null){
                    if(varObj.isArray){
                        throw new LangException("Error: Incompatible types: " + exprType + " cannot be casted to array" + variableType);
                    }
                }
                throw new LangException(" Error: Incompatible types: " + exprType + " cannot be casted to " + variableType);
            }
        }
        boolean checkIfAdded = symbolTable.addInCurrentScope(ID, variableType, false, this.isVariableFinal);
        if (!checkIfAdded){
            throw new LangException("Error: Variable " + ID + " is already defined in this scope!");
        }
    }
    else{
        boolean checkIfAdded = symbolTable.addInCurrentScope(ID, variableType, true, false);
        if (checkIfAdded == false){
            throw new LangException("Error: Variable " + ID + " is already defined in this scope!");
        }
    }
        return "";
  }
}