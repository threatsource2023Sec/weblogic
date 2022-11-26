package weblogic.diagnostics.accessor;

public class InvalidParameterException extends Exception {
   public InvalidParameterException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public InvalidParameterException(String msg) {
      super(msg);
   }
}
