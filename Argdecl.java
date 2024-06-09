
public class Argdecl extends Token{

    boolean checkIfArray;
    String type;
    String ID;
  
    public Argdecl(String type, String id, boolean checkIfArray){
        this.type = type;
        this.ID = id;
        this.checkIfArray = checkIfArray;
    }
  
    public String toString(int t){
        String finalString;
        if(checkIfArray == true){
            finalString = type + " " + ID + "[]";
        }
        else{
            finalString = type + " " + ID;
        }
        return finalString;
    }

    public String typeCheck() throws LangException{
        boolean checkIfAdded = symbolTable.addInCurrentScope(ID, type, checkIfArray, false);
        if(!checkIfAdded){
            if(checkIfArray){
                throw new LangException("Error: Array " + ID + " is already defined in this scope.");
            }
            throw new LangException("Error: Variable " + ID + " is already defined in this scope.");
        }
        return type;
    }

  }
  
  