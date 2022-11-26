package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class NotSubtransaction extends UserException {
   public NotSubtransaction() {
      super(NotSubtransactionHelper.id());
   }

   public NotSubtransaction(String $reason) {
      super(NotSubtransactionHelper.id() + "  " + $reason);
   }
}
