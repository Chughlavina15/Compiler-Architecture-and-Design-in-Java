public class Readlist extends Token {
    Name name;
    Readlist readlist;

    public Readlist(Name name, Readlist r) {
        this.name = name;
        this.readlist = r;
    }

    public Readlist(Name name) {
        this.name = name;
        this.readlist = null;
    }

    public String toString(int t) {
        if(readlist == null) return name.toString(t);
        return name.toString(t) + ", " + readlist.toString(t); 
    }

    public String typeCheck() throws LangException{
        String nameType = null;
        SymbolTable.VariableElement varObject = symbolTable.findVariable(name.ID);
        if(varObject == null){
            throw new LangException(" Error: Variable " + name.ID + " not found in the scope");
        }
        if(varObject.isMethod){
            throw new LangException(" Error: Cant use read with method "+name.ID+ " ()");
        }
        if(varObject.isArray && name.expression == null){
            throw new LangException(" Error: Cant use read with array "+name.ID+ " []");
        }
        
        if(readlist!=null){
            readlist.typeCheck();
        }
        return nameType;
    }

}
