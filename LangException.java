class LangException extends Exception
{
    String error;
    public LangException(String s)
    {
        error = s;
    }

    public String toString()
    {
      return error;
    }
}
