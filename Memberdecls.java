public class Memberdecls extends Token {

    Members members;

    public Memberdecls(Members members){
        this.members = members;
    }

    public Memberdecls(){
        this.members = null;
    }

    public String toString(int t){
        return members.toString(t);
    }

    public String typeCheck() throws LangException{
        members.typeCheck();
        return "";
    }

}