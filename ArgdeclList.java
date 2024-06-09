import java.util.LinkedList;
import java.util.List;

public class ArgdeclList extends Token{

    Argdecl argdecl;
    ArgdeclList argdeclList;

    public ArgdeclList(Argdecl argdecl, ArgdeclList argdeclList){
        this.argdecl = argdecl;
        this.argdeclList = argdeclList;
    }

    public ArgdeclList(Argdecl argdecl){
        this.argdecl = argdecl;
        this.argdeclList = null;
    }

    public String toString(int t){
        String finalString = "";
        if (argdecl == null && argdeclList == null)
            return finalString;
        finalString = argdecl.toString(t) + (argdeclList == null ? "" : ", " + argdeclList.toString(t));
        return finalString;
    }

    public String typeCheck() throws LangException{
        if(argdecl!=null){
            String argType = argdecl.typeCheck();
            String methodName = CurrentMethod.getCurrentVariableName();
            symbolTable.findVariable(methodName).methodArguments.add(argType);
        }
        if(argdeclList!=null){
            argdeclList.typeCheck();
        }
        return null;
    }
}
