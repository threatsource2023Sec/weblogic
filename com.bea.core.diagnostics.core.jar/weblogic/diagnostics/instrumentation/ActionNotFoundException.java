package weblogic.diagnostics.instrumentation;

public class ActionNotFoundException extends InstrumentationException {
   public ActionNotFoundException() {
   }

   public ActionNotFoundException(String msg) {
      super(msg);
   }
}
