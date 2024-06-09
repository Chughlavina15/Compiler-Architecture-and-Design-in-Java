public class Printlinelist extends Token{
    Printlist printlist;

    public Printlinelist(Printlist printlist)
    {
        this.printlist = printlist;
    }

    public Printlinelist(){
        this.printlist = null;
    }

    public String toString(int t)
    {
        if (printlist == null)
            return "";
        return printlist.toString(t);    
    }

    public String typeCheck() throws LangException{
        if(printlist!=null){
            printlist.typeCheck();
        }
        return null;
    }
}
