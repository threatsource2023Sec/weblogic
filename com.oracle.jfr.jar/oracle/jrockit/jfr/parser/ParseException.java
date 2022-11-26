package oracle.jrockit.jfr.parser;

public class ParseException extends Exception {
   private static final long serialVersionUID = -1334542415605055233L;

   public ParseException() {
   }

   public ParseException(String message) {
      super(message);
   }

   public ParseException(Throwable cause) {
      super(cause);
   }

   public ParseException(String message, Throwable cause) {
      super(message, cause);
   }
}
