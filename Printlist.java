public class Printlist extends Token{
    Expr expr;
    Printlist printlist;

    public Printlist(Expr expr, Printlist pl)
    {
        this.expr = expr;
        this.printlist = pl;
    }
    public Printlist(Expr expr)
    {
        this.expr = expr;
        this.printlist = null;
    }

    public String toString(int t){
        if (printlist == null)  return expr.toString(t);
        return expr.toString(t) + ", " + printlist.toString(t);
    }

    public String typeCheck() throws LangException{
        String returnType = null;
        if(expr.rule == 9 && expr.binaryExpressions.operator.equals("+")){
            returnType = "string";
        }
        else{
            returnType = expr.typeCheck();
            if(returnType.equals("void")){
                throw new LangException(" Error: Cant apply print on variables of type void");
            }
        }
        if(printlist != null){
            returnType = printlist.typeCheck();
        }
        return returnType;
    }
    
}
