package weblogic.transaction.internal;

import weblogic.transaction.Transaction;

public final class TimedOutException extends weblogic.transaction.TimedOutException {
   private static final long serialVersionUID = -3098142331391809219L;

   public TimedOutException() {
   }

   public TimedOutException(String s) {
      super(s);
   }

   public TimedOutException(TransactionImpl tx) {
      super((Transaction)tx);
   }
}
