package weblogic.security.providers.authentication.shared;

public final class UnknownPasswordTypeException extends Exception {
   public UnknownPasswordTypeException() {
   }

   public UnknownPasswordTypeException(String msg) {
      super(msg);
   }
}
