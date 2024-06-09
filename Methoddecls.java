import java.util.LinkedList;
import java.util.List;

public class Methoddecls extends Token
{
    private List<Methoddecl> methodDeclarationsList;

    public Methoddecls() {
      methodDeclarationsList = new LinkedList<Methoddecl>();
    }

    public Methoddecls prepend(Methoddecl s) {
        methodDeclarationsList.add(0,s);
        return this;
      }

    public String toString(int t) {
        String finslString = "";
        for (Methoddecl methodDeclaration : methodDeclarationsList) {
          finslString += methodDeclaration.toString(t);
        }
        return finslString;
      }

      public String typeCheck() throws LangException{
        for (Methoddecl methodDeclaration : methodDeclarationsList) {
            methodDeclaration.typeCheck();
        }
        return "";
    }
}

    