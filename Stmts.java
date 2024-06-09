import java.util.List;
import java.util.LinkedList;

public class Stmts extends Token {
    private List<Stmt> stmtList;
  
    public Stmts() {
        stmtList = new LinkedList<Stmt>();
    }

    public Stmts prepend(Stmt s) {
        stmtList.add(0,s);
        return this;
      }

    public String toString(int t) {
        String finalString= "";
        for (Stmt s : stmtList) {
          finalString+= (getTabs(t)+s.toString(t)+"      ");
        }
        return finalString;
      }

    public String typeCheck() throws LangException{
      for (Stmt s : stmtList) {
        s.typeCheck();
      }
      return "";
    }
}
