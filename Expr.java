

public class Expr extends Token{
    String ID;
    Name name;
    int rule;
    Args arguments;
    String type;
    Type typeExtra;
    Expr expression;
    String prefixOperators, charOrString;
    Binaryoperations binaryExpressions;
    int number;
    String expresionString;
    float expressionFloat;
    boolean expressionBoolean;
    Expr multipleExpr[];
    boolean ischar = false;
    boolean isString = false;


    public Expr(String id, Args arguments){
        this.ID = id;
        if(arguments != null) this.arguments = arguments;
        rule = 2;
    }

    public Expr(String id){
        this.ID = id;
        this.arguments = null;
        rule = 2;
    }


    public Expr(Name name){
        this.name = name;
        rule = 1;
    }

    public Expr(){
        this.ID = null;
        rule = 0;
    }

    public Expr(Type type){
        this.typeExtra = type;
        rule = 3;
    }

    public Expr(Expr expression, String prefixOperators){
        this.expression = expression;
        if(prefixOperators == "()") rule = 4;
        if(prefixOperators == "~") rule = 5;
        if(prefixOperators == "-") rule = 6;
        if(prefixOperators == "+") rule = 7;
    }

    public Expr(Type type, Expr expression){
        this.typeExtra = type;
        this.expression = expression;
    }

    public Expr(Binaryoperations binaryExpressions){
        this.binaryExpressions = binaryExpressions;
        rule = 9;
    }

    public Expr(int number){
        this.number = number;
        rule = 10;
    }

    public Expr(String expressionString, String charOrString){
        if(charOrString.equals("char")){
            this.ischar = true;
        }
        else if(charOrString.equals("string")){
            this.isString = true;
        }
        this.expresionString = expressionString;
        rule = 11;
    }

    public Expr(float expressionFloat){
        this.expressionFloat = expressionFloat;
        rule = 12;
    }

    public Expr(boolean expressionBoolean){
        this.expressionBoolean = expressionBoolean;
        rule = 13;
    }

    public Expr(String exprString, Expr expression){
        if(exprString == "int" || exprString == "float" || exprString == "char" || exprString == "bool" || exprString == "void"){
            this.expression = expression;
            this.type = exprString;
            rule = 8;
        }
        else{
            this.expression = expression;
            rule = 14;
        }
    }

    public Expr(Expr expression1, Expr expression2, Expr expression3){
        this.multipleExpr = new Expr[]{expression1, expression2, expression3};
        rule = 15;
    }

    public String toString(int t){
        switch(rule){
            case 0 : return "default string";
            case 1: return name.toString(t);
            //case 2: return (id == null? "" : id);
            case 2: return (ID == null? "" : ID)+" ( "+(arguments == null?" " : arguments.toString(t))+" )";
            //case 3: return type.toString(t);
            case 4: return "("+expression.toString(t)+")";
            case 5: return " ~ "+expression.toString(t);
            case 6: return " - "+expression.toString(t);
            case 7: return " + "+expression.toString(t);
            case 8: return "( "+type+" )"+expression.toString(t);
            case 9: return binaryExpressions.toString(t);
            case 10: return String.valueOf(number);
            case 11: return expresionString;
            case 12: return String.valueOf(expressionFloat);
            case 13: return expressionBoolean == true? "true" : "false";
            case 14: return expression.toString(t);
            case 15: return "(" + multipleExpr[0].toString(t) + " ? " + multipleExpr[1].toString(t) + " : " + multipleExpr[2].toString(t) + ")";
        }
        return "";
    }


    public String typeCheck() throws LangException{
        String expressionType = "";
        SymbolTable.VariableElement variableObject;
        switch(rule){
            case 1:
                expressionType = name.typeCheck();
                break;
            case 2:
                variableObject = symbolTable.findVariable(ID);
                if( variableObject == null || variableObject.isMethod == false){
                    throw new LangException(" Error: No method found by the name of " + ID);
                }
                if (arguments != null)
                    arguments.typeCheck();
                expressionType = variableObject.varType;
                break;
            case 4:
                expressionType = expression.typeCheck();
                break;
            case 5:
                expressionType = expression.typeCheck();
                if(symbolTable.isCoercible("bool", expressionType) == false){
                    throw new LangException(" Error: " + "~ is incompatible with variables of type " + expressionType);
                }
                break;
            case 6:
            case 7:
                expressionType = expression.typeCheck();
                if(expressionType != "int" && expressionType != "float"){
                    throw new LangException(" Error: " + "~ is incompatible with variables of type " + expressionType);
                }
                break;
            case 8:
                String castType = type;
                expressionType = expression.typeCheck();
                if(expressionType.equals("int")){
                    if(!castType.equals("int") && !castType.equals("float") && !castType.equals("string")){
                        throw new LangException("Unable to cast expression of type " + expressionType + " to " + castType);
                    }
                }
                else if(expressionType.equals("float")){
                    if(!castType.equals("int") && !castType.equals("float") && !castType.equals("string")){
                        throw new LangException("Unable to cast expression of type " + expressionType + " to " + castType);
                    }
                }
                break;
            case 9:
                    expressionType = binaryExpressions.typeCheck();
                    break;
            case 10:
                    expressionType = "int";
                    break;
            case 11:
                    if(ischar){
                        expressionType = "char";
                    }
                    else if(isString){
                        expressionType = "string";
                    }
                    break;
            case 12:
                    expressionType = "float";
                    break;
            case 13:
                    expressionType = "bool";
                    break;
            case 14:
                    expressionType = expression.typeCheck();
                    break;
            case 15:
                Expr conditional = multipleExpr[0];
                String conditionalType = conditional.typeCheck();
                String expr1Type = multipleExpr[1].typeCheck();
                String expr2Type = multipleExpr[2].typeCheck();

                if (!conditionalType.equals("bool") && !conditionalType.equals("int")){
                    throw new LangException(" Error: Incompatible types for ternary operator: " + conditionalType + " cannot be converted to Boolean");
                }
                if (!expr1Type.equals(expr2Type)){
                    throw new LangException(" Error: Incompatible types for ternary operator: expr1 type " + expr1Type + " doesn't match expr2 type " + expr2Type);
                }
                expressionType = expr1Type;
        }
        return expressionType;
    }
}