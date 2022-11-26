package weblogic.transaction;

public class TimedOutException extends Exception {
   public TimedOutException() {
   }

   public TimedOutException(String s) {
      super(s);
   }

   public TimedOutException(Transaction tx) {
      this("Transaction timed out after " + tx.getMillisSinceBegin() / 1000L + " seconds \n" + tx.getXid().toString());
   }
}
