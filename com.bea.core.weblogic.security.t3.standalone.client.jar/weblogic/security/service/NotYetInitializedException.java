package weblogic.security.service;

public class NotYetInitializedException extends RuntimeException {
   public NotYetInitializedException() {
   }

   public NotYetInitializedException(String arg0) {
      super(arg0);
   }

   public NotYetInitializedException(String arg0, Throwable arg1) {
      super(arg0, arg1);
   }

   public NotYetInitializedException(Throwable arg0) {
      super(arg0);
   }
}
