package javax.transaction;

public class HeuristicCommitException extends Exception {
   private static final long serialVersionUID = -3977609782149921760L;

   public HeuristicCommitException() {
   }

   public HeuristicCommitException(String msg) {
      super(msg);
   }
}
