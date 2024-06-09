public class Members extends Token
{
    Fielddecl fielddecl;
    Members members;
    Methoddecl methoddecl;
    Methoddecls methodDeclarations;
    Fielddecls fieldDeclarations;

    public Members(Fielddecl fielddecl, Members members){
        this.fielddecl = fielddecl;
        this.members = members;
        this.methodDeclarations = null;
        this.methoddecl = null;

    }

    public Members(Methoddecl methoddecl, Methoddecls methoddecls){
        this.methoddecl = methoddecl;
        this.methodDeclarations = methoddecls;
        this.fielddecl = null;
        this.members = null;
    }

    public Members(){
        this.methoddecl = null;
        this.methodDeclarations = null;
        this.fielddecl = null;
        this.members = null;

    }

    public String toString(int t) {

        if (methoddecl != null) {
            return methoddecl.toString(t) + "" + (methodDeclarations == null ? "" : methodDeclarations.toString(t));
        } else if (fielddecl != null ){
            return fielddecl.toString(t) + "" + (members == null ? "" : members.toString(t));
        }else{
            return "";
        }
    }

    public String typeCheck() throws LangException{
        if (fielddecl != null) { fielddecl.typeCheck();   }
        if (members != null) {  members.typeCheck(); }
        if (methoddecl != null) { methoddecl.typeCheck();   }
        if (methodDeclarations != null) {  methodDeclarations.typeCheck();    }
        return "";
    }
}