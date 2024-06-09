public class Stmt extends Token{
    String ID, unaryOperatorString;
    Expr expr;
    Stmt statement;
    Stmts statements;
    int rule;
    Name name;
    Readlist readList;
    Printlist printList;
    Printlinelist printLineList;
    boolean functionArgumentsPresent;
    Args arguments;
    boolean isUnaryOperator;
    boolean isSemicolonPresent;
    Fielddecls fieldDeclarations;
    String exprString;
    String argumentString;
    Stmt elseStmt;

    public Stmt(String id){
        this.ID = id;
        rule = 1;
    }

    public Stmt(String id, Args arguments){
        this.ID = id;
        this.arguments = arguments; 
        rule = 7; 
    }


    public Stmt(Expr expr, Stmt st){
        this.statement = st;
        this.expr = expr;
        rule = 2;
    }

    public Stmt(Name name, Expr expression){
        this.expr = expression;
        this.name = name;
        rule = 3;
    }

    public Stmt(Name name, String id, boolean isUnaryOperator){
        this.name = name;
        this.ID = id;
        if(isUnaryOperator == false) {  rule = 10;   }
        else    {   this.unaryOperatorString = id; rule = 10; }
    }

    public Stmt(Readlist readlist){
        this.readList = readlist;
        rule = 4;
    }


    public Stmt(Printlist printList){
        this.printList = printList;
        rule = 5;
    }

    public Stmt(Printlinelist printLineList){
        this.printLineList = printLineList;
        rule = 6;
    }

    public Stmt(Args arguments){
        this.arguments = arguments;
        //rule = 8;
    }

    public Stmt(Expr expr, Stmt stmt, Stmt ie){
        this.expr = expr;
        this.statement = stmt;
        this.elseStmt = ie;
        rule = 8;
    }

    public Stmt(){
        rule = 12;
    }

    public Stmt(Expr e){
        if(e == null){
            rule = 12;
        }
        else{
            this.expr = e;
            rule = 9;
        }
    }

    public Stmt(Fielddecls fieldDecls, Stmts sts, boolean isSemicolonPresent){
        this.statements = sts;
        this.fieldDeclarations = fieldDecls;
        this.isSemicolonPresent = isSemicolonPresent;
        rule = 11;
    }


    public String toString(int t){
        if(rule == 1 ){
            return (ID == null? "" : ID)+" ( "+" ); \n";
        }
        else if(rule == 2){
            return "while( "+expr.toString(t)+")"+ "\n { "+ statement.toString(t)+ "\n }; \n";
        }
        else if(rule == 3){
            return getTabs(t)  + name.toString(t)+" = "+expr.toString(t)+"; \n";
        }
        else if(rule == 4){
            return getTabs(t)  + "read( "+readList.toString(t)+" ); \n";
        }
        else if(rule == 5){
            return getTabs(t)  + "print ( "+printList.toString(t)+" ); \n";
        }
        else if(rule == 6){
            return getTabs(t)  + "printline ( "+printLineList.toString(t)+" ); \n";
        }
        else if(rule == 7){
            this.argumentString = arguments.toString(t);
            return getTabs(t)  + (ID == null? "" : ID)+" ("+(arguments==null?"":this.argumentString)+"); \n";
        }
        else if(rule == 8){
            return getTabs(t) + "if (" + expr.toString(t) + ")\n" +
            (statement.rule == 11 ? statement.toString(t) : getTabs(t) + "{\n" + statement.toString(t+1) + getTabs(t) + "}\n") +
            ((elseStmt == null ? "" : getTabs(t) + "else\n" + ( elseStmt.rule == 11 ? elseStmt.toString(t) : getTabs(t) + "{\n" + elseStmt.toString(t+1) + getTabs(t) + "}\n")));
        }
        else if(rule == 9){
            this.exprString = expr.toString(t);
            return "return "+(expr==null? "" : this.exprString)+" ;";
        }
        else if(rule == 10){
            return getTabs(t)  + name.toString(t)+" "+unaryOperatorString+"; \n";
        }
        else if(rule == 11){
            return getTabs(t)+"{ "+ fieldDeclarations.toString(t) + statements.toString(t)+"\n"+"} " +(isSemicolonPresent == true? ";" : "")+ "\n";
        }
        else if(rule == 12){
            CurrentMethod.setContainsReturn(true);
            return "return "+"; \n";
        }
        return ID;
    }

    public void typeCheckingForBlocks() throws LangException{
        symbolTable.addScope();
        statement.typeCheck();
        symbolTable.removeScope();
    }

    public void typeCheckingForStmtBlock() throws LangException{
        symbolTable.addScope();
        fieldDeclarations.typeCheck();
        statements.typeCheck();
        symbolTable.removeScope();
    }

    public String typeCheck() throws LangException{ 
        String stmtReturnType;
        SymbolTable.VariableElement varObject;
        switch(rule){
        case 1:
            varObject = symbolTable.findVariable(ID);
            if(varObject == null){
                throw new LangException("Error: No method found by the name of " + ID);
            }
            else{
                if(varObject.methodArguments!=null){
                    throw new LangException("Error: No method found by the name of " + ID+" with 0 arguments");
                }
            }
            stmtReturnType = varObject.varType;
            break;
        
        case 2:
            stmtReturnType = expr.typeCheck();
            if(!stmtReturnType.equals("bool") && !stmtReturnType.equals("int")){
                throw new LangException("Error: Incompatible type for while statement: " + stmtReturnType + 
                " cannot be converted to Boolean");
            }
            typeCheckingForBlocks();
            break; 

        case 3:
            String nameType = name.typeCheck();
            String exprType = expr.typeCheck();
            SymbolTable.VariableElement varObj = symbolTable.findVariable(name.ID);
            if(!nameType.equals(exprType)){
                if(varObj != null){
                    if(varObj.isArray){
                        throw new LangException("Error: Incompatible types: " + expr.typeCheck() + " cannot be casted to " + nameType+" array ");
                    }
                }
                throw new LangException("Error: Incompatible types: " + expr.typeCheck() + " cannot be casted to " + nameType);
            }
            varObject = symbolTable.findVariable(name.ID);
            if(varObject == null){
                throw new LangException("Error: No variable found by the name of " + name.ID);
            }
            if(varObject.isFinal){
                throw new LangException("Error: Cannot reassign a value to final variable " + name.ID);
            }
            break;
        case 4:
            readList.typeCheck();
            break;
        case 5:
            printList.typeCheck();
            break;
        case 6:
            printLineList.typeCheck();
            break;
        case 7:
            varObject = symbolTable.findVariable(ID);
            if(varObject == null ){
                if(!varObject.isMethod && varObject.methodArguments == null){
                    throw new LangException("Error: No method found by the name of " + ID+ "() with arguments "+this.argumentString);
                }
            }
            arguments.typeCheck(ID);
            break;
        case 8:
            stmtReturnType = expr.typeCheck();
            if(!stmtReturnType.equals("bool") && !stmtReturnType.equals("int")){
                throw new LangException(" Error in if(): Incompatible types: " + stmtReturnType + " cannot be converted to Boolean");
            }
            typeCheckingForBlocks();
            if (elseStmt != null){
                typeCheckingForBlocks();
            }
            break;

        case 9:
            String expressionString = expr.typeCheck();
            CurrentMethod.setContainsReturn(true);
            CurrentMethod.setReturnType(expressionString);
            break;

        case 10:
            varObject = symbolTable.findVariable(name.ID);
            if(varObject == null){
                throw new LangException("Error: No variable found by the name of " + name.ID);
            }
            if(!varObject.varType.equals("int") && !varObject.varType.equals("float")){
                throw new LangException(" Error: bad operand type " + name.ID + " for unary operator " + unaryOperatorString);
            }
            break;

        case 11:
            typeCheckingForStmtBlock();
            break;

        case 12:
            CurrentMethod.setContainsReturn(true);
            CurrentMethod.setReturnType("void");
        }
    
        return null; }
}