package weblogic.diagnostics.instrumentation;

public class DuplicateActionException extends InstrumentationException {
   public DuplicateActionException() {
   }

   public DuplicateActionException(String msg) {
      super(msg);
   }
}
