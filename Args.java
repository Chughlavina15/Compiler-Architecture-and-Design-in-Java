import java.util.ArrayList;
import java.util.List;

public class Args extends Token{
    Expr expr;
    Args args;

    public Args(Expr expr, Args args)
    {
        this.expr = expr;
        this.args = args;
    }
    
    public Args(Expr expr)
    {
        this.expr = expr;
        args = null;
    }

    public Args(){
        this.expr = null;
        this.args = null;
    }

    public String toString( int t)
    {
        if (args == null && expr == null)
            return "";
        return (expr == null?"" : expr.toString(t)) + (args == null ? "" : (", " + args.toString(t)));    

    }

    public String typeCheck() throws LangException{return null;}
    
    public String typeCheck(String varName) throws LangException{
        List<String> argTypes = new ArrayList<>();
        gatherTypesFromCall(argTypes);
        SymbolTable.VariableElement varobj = symbolTable.findVariable(varName);
        int methodArgsSize = varobj.methodArguments.size();
        List<String> methodArgs = varobj.methodArguments;

        if(methodArgsSize != argTypes.size()){
            throw new LangException("Error: Function " + varName + " expected " + methodArgsSize + " arguments, " + argTypes.size() + " provided");
        }

        for(int i=0;i<methodArgsSize;i++){
            String expectedType = methodArgs.get(i);
            String actualType = argTypes.get(i);

            if(symbolTable.isCoercible(expectedType, actualType) == false){
                throw new LangException("Error: Expected argument of type " + expectedType + " in position " + i + " of function " + varName + ", " + actualType + " provided");
            }
        }
        return null;
    }

    public void gatherTypesFromCall(List<String> argTypes) throws LangException {
        if (expr != null) {
            String exprType = expr.typeCheck();
            argTypes.add(exprType);
            if (args != null && args.expr != null)
                args.gatherTypesFromCall(argTypes);
        }
    }
    
}
