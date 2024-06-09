import java.util.ArrayList;
import java.util.LinkedList;

public class Methoddecl extends Token
{
    Argdecls argdecls; 
    Fielddecls fielddecls;
    Stmts stmts;
    String type;
    String ID;
    boolean semiColonPresent;

    public Methoddecl(String type,String id, Argdecls as, Fielddecls fs, Stmts stmts, boolean issemi)
    {
        this.type = type;
        this.ID = id;
        this.argdecls = as;
        this.fielddecls = fs;
        this.stmts = stmts;
        this.semiColonPresent = issemi;
    }

    public Methoddecl(){
        
    }

    public String toString(int t)
    {
        String finalString;
        finalString = getTabs(t)  + type + " " + ID + "(" + argdecls.toString(t) +")\n"+ getTabs(t) + "{\n" + fielddecls.toString(t+1) + stmts.toString(t+1) + getTabs(t) + "}" + ( semiColonPresent ? ";" : "" ) + "\n\n";
        return finalString;
    }

    public String typeCheck() throws LangException{
        String methodType = type;
        boolean isVariableMethod = true;
        

        boolean checkIfAdded = symbolTable.addInCurrentScope(ID, methodType, isVariableMethod, null);
        if(checkIfAdded == false ){
            throw new LangException(" Error: Function " + ID + " is already defined in this scope!");
        }
        symbolTable.addScope();
        symbolTable.findVariable(ID).methodArguments = new ArrayList<>();
        CurrentMethod.setVariableName(ID);
        argdecls.typeCheck();
        fielddecls.typeCheck();
        stmts.typeCheck();

        if(!methodType.equals("void") && !CurrentMethod.containsReturn){
            throw new LangException("Error: Missing return statement from " + ID + "() method");
        }

        String currentMethodReturnType = CurrentMethod.getReturnType();
        //if(false){
        if(!symbolTable.isCoercible(methodType, currentMethodReturnType)){
            throw new LangException(" Error: Incompatible types for method " +ID+"( ) with "+
            "return type :"+ currentMethodReturnType + " doesn't match expected return type " + methodType);
        }

        symbolTable.removeScope();

        CurrentMethod.setContainsReturn(false);
        CurrentMethod.setReturnType(null);

        return "";
    }
}
