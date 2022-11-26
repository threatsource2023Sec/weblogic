package javax.transaction;

public class HeuristicRollbackException extends Exception {
   private static final long serialVersionUID = -3483618944556408897L;

   public HeuristicRollbackException() {
   }

   public HeuristicRollbackException(String msg) {
      super(msg);
   }
}
