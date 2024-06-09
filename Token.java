abstract class Token {
  
    protected static SymbolTable symbolTable;
    
    protected String getTabs(int t)
    {
        String tabs = "";
        for (int i = 0; i < t; i++)
            tabs = tabs + "\t";
        return tabs;
    }
  
    public String toString(int t)
    {
        return "";
    }
  
    abstract String typeCheck() throws LangException;

    public static class CurrentMethod {
        static boolean containsReturn = false;
        static String returnType;
        static String variableName;

        public static void setContainsReturn(boolean ifContainsReturn){
            CurrentMethod.containsReturn = ifContainsReturn;
        }

        public static String getContainsReturn(){
            if(CurrentMethod.containsReturn == true){
                return "true";
            }
            return "false";
        }

        public static void setReturnType(String currentReturnType){
            CurrentMethod.returnType = currentReturnType;
        }

        public static String getReturnType(){
            return CurrentMethod.returnType;
        }

        public static void setVariableName(String varName){
            CurrentMethod.variableName = varName;
        }

        public static String getCurrentVariableName(){
            return CurrentMethod.variableName;
        }
    }
        
    }
     
  