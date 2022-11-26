package weblogic.security.service;

public class NotYetInitializedException extends SecurityServiceRuntimeException {
   public NotYetInitializedException() {
   }

   public NotYetInitializedException(String msg) {
      super(msg);
   }

   public NotYetInitializedException(Throwable nested) {
      super(nested);
   }

   public NotYetInitializedException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
