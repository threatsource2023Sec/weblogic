package javax.transaction;

public class RollbackException extends Exception {
   private static final long serialVersionUID = 4151607774785285395L;

   public RollbackException() {
   }

   public RollbackException(String msg) {
      super(msg);
   }
}
