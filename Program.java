import java.util.HashMap;

import javax.lang.model.element.VariableElement;

public class Program extends Token {
    String id;
    Memberdecls memberDeclarations;

    Program(String id, Memberdecls memberdecls){
         this.id = id;
         this.memberDeclarations = memberdecls;
         symbolTable = new SymbolTable();
     }

    public String toString(int t){
        String finalString = "class "+id+" {\n"+ memberDeclarations.toString(t)+" }\n";
        return finalString;
    }

    public String typeCheck() throws LangException{
        symbolTable.addScope();
        memberDeclarations.typeCheck();
        symbolTable.removeScope();
        return "";
    }
}
