package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class NoTransaction extends UserException {
   public NoTransaction() {
      super(NoTransactionHelper.id());
   }

   public NoTransaction(String $reason) {
      super(NoTransactionHelper.id() + "  " + $reason);
   }
}
