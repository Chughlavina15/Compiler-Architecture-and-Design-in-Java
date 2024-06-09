public class Name extends Token {
    String ID;
    Expr expression;
    int rule;
    String exprString;
  
    public Name(String id)
    {
      this.ID = id;
      rule = 1;
    }

    public Name(String id, Expr expression){
      this.ID = id;
      this.expression = expression;
      rule = 2;
    }

    public Name(){
      
    }
  
    public String toString(int t)
    {
      switch(rule){
        case 1 : return ID;
        
        case 2: 
          this.exprString = expression.toString(t);
          return ID+" ["+this.exprString+"]";
      }
      return "default string";
    }

    public String typeCheck() throws LangException{
        String nameReturnType;
        if(rule == 2){
          if(!expression.typeCheck().equals("int")){
            throw new LangException("Error: Expected int as array index, provided " + this.exprString);
          }
        }
        SymbolTable.VariableElement varObject = symbolTable.findVariable(ID);
        if(varObject == null){
          throw new LangException(" Error: Variable " + ID + " is not defined in this scope!");
        }
        nameReturnType = varObject.varType;
        return nameReturnType;
    }
}
