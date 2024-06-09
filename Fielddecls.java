import java.util.List;
import java.util.LinkedList;

public class Fielddecls extends Token {
    private List<Fielddecl> fieldDeclarationsList;
    Fielddecl fieldDecl;

    public Fielddecls() {
      fieldDeclarationsList = new LinkedList<Fielddecl>();
    }

    public Fielddecls prepend(Fielddecl fieldDecl) {
        fieldDeclarationsList.add(0,fieldDecl);
        return this;
      }


    public String toString(int t) {
        String finalString = "";
        for(Fielddecl fieldDeclaration : fieldDeclarationsList){
          finalString += fieldDeclaration.toString(t);
        }
        return finalString;
      }

      public String typeCheck() throws LangException {
        String finalString = "";
        if(fieldDecl != null){
          return fieldDecl.typeCheck();
        }
        if(fieldDeclarationsList != null){
          for(Fielddecl fieldDeclaration : fieldDeclarationsList){
            finalString += fieldDeclaration.typeCheck();
        }
        }
        return finalString;
      }

  }

   