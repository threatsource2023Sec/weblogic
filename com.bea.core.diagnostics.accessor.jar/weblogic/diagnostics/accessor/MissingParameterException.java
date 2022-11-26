package weblogic.diagnostics.accessor;

public class MissingParameterException extends Exception {
   public MissingParameterException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public MissingParameterException(String msg) {
      super(msg);
   }
}
