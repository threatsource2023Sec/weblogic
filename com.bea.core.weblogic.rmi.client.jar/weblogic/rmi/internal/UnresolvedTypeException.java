package weblogic.rmi.internal;

public class UnresolvedTypeException extends RuntimeException {
   public UnresolvedTypeException() {
   }

   public UnresolvedTypeException(String message) {
      super(message);
   }

   public UnresolvedTypeException(Throwable cause) {
      super(cause);
   }

   public UnresolvedTypeException(String message, Throwable cause) {
      super(message, cause);
   }
}
