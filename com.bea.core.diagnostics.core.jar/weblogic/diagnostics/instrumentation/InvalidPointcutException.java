package weblogic.diagnostics.instrumentation;

public class InvalidPointcutException extends InstrumentationException {
   public InvalidPointcutException() {
   }

   public InvalidPointcutException(String msg) {
      super(msg);
   }

   public InvalidPointcutException(Throwable t) {
      super(t);
   }

   public InvalidPointcutException(String msg, Throwable t) {
      super(msg, t);
   }
}
