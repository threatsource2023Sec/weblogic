package javax.transaction;

public class TransactionalException extends RuntimeException {
   private static final long serialVersionUID = -8196645329560986417L;

   public TransactionalException(String s, Throwable throwable) {
      super(s, throwable);
   }
}
