public class Argdecls extends Token{

    ArgdeclList argsdeclsList;

    public Argdecls(ArgdeclList argsdeclsList){
        this.argsdeclsList = argsdeclsList;
    }

    public Argdecls(){
        this.argsdeclsList = null;
    }

    public String toString(int t){
        String finalString = "";
        
        if (argsdeclsList == null)
            return finalString;
        finalString = argsdeclsList.toString(t);
        return finalString;
    }

    public String typeCheck() throws LangException{
        if(argsdeclsList != null){
            argsdeclsList.typeCheck();
        }
        return null;
    }
}
