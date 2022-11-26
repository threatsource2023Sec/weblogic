package weblogic.rmi.rmic;

final class ParseException extends Exception {
   private static final long serialVersionUID = 2161006850779122473L;

   public ParseException(int line, String msg) {
      super("at line (" + line + "): " + msg);
   }

   public ParseException(String msg) {
      super(msg);
   }
}
