public class Binaryoperations extends Token {
    Expr lhs, rhs;
    String operator;

    public Binaryoperations(Expr leftHandExpression, Expr rightHandExpression, String operatorString){
        this.lhs = leftHandExpression;
        this.rhs = rightHandExpression;
        this.operator = operatorString;
    }
    
    public String toString(int t ){
        return "(" + lhs.toString(t) + " " + operator  + rhs.toString(t) + " )";
    }

    public String typeCheck() throws LangException{
        String finalExprType = null;
        String lhsExprType = lhs.typeCheck();
        String rhsExprType = rhs.typeCheck();
        if(operator.equals("*") || operator.equals("+") || operator.equals("-") || operator.equals("/")){
            if(lhsExprType.equals("string") && rhsExprType.equals("string")){   
                if(operator.equals("+")){
                    finalExprType = "string";
                }
                else    {
                    throw new LangException("Error: Cant apply arithmetic operator " + operator + " on expression of type: " + lhsExprType + " and " + rhsExprType);
                }
            }
            
            if(!lhsExprType.equals("int") && !lhsExprType.equals("float")){
                throw new LangException("Error: Cant apply arithmetic operator " + operator + " on expression of type: " + lhsExprType + " and " + rhsExprType);
            }

            if(!rhsExprType.equals("int") && !rhsExprType.equals("float")){
                throw new LangException("Error: Cant apply arithmetic operator " + operator + " on expression of type: " + lhsExprType + " and " + rhsExprType);
            }

            if(lhsExprType.equals("int") && rhsExprType.equals("int")){
                finalExprType = "int";
            }
            finalExprType = "float";
        }
        else if(operator.equals("||") || operator.equals("&&")){
            if(!lhsExprType.equals("int") && !lhsExprType.equals("bool")){
                throw new LangException("Error: Cant apply logical operator " + operator + " on expression of type: " + lhsExprType + " and " + rhsExprType);
            }

            if(!rhsExprType.equals("int") && !rhsExprType.equals("bool")){
                throw new LangException("Error: Cant apply logical operator " + operator + " on expression of type: " + lhsExprType + " and " + rhsExprType);
            }
            finalExprType = "bool";
        }
        else{ // operators are : <, >, <=, >=
            if(!lhsExprType.equals("int") && !lhsExprType.equals("bool" ) && !lhsExprType.equals("float")){
                throw new LangException("Error: Cant apply comparator operator " + operator + " on expression of type: " + lhsExprType + " and " + rhsExprType);
            }

            if(!rhsExprType.equals("int") && !rhsExprType.equals("bool")  && !rhsExprType.equals("float")){
                throw new LangException("Error: Cant apply comparator operator " + operator + " on expression of type: " + lhsExprType + " and " + rhsExprType);
            }
            finalExprType = "bool";
        }
    return finalExprType;
    }
}
