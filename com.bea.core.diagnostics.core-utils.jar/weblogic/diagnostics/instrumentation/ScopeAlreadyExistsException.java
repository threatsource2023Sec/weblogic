package weblogic.diagnostics.instrumentation;

public class ScopeAlreadyExistsException extends InstrumentationException {
   public ScopeAlreadyExistsException() {
   }

   public ScopeAlreadyExistsException(String msg) {
      super(msg);
   }

   public ScopeAlreadyExistsException(Throwable t) {
      super(t);
   }

   public ScopeAlreadyExistsException(String msg, Throwable t) {
      super(msg, t);
   }
}
