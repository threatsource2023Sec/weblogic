package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class SubtransactionsUnavailable extends UserException {
   public SubtransactionsUnavailable() {
      super(SubtransactionsUnavailableHelper.id());
   }

   public SubtransactionsUnavailable(String $reason) {
      super(SubtransactionsUnavailableHelper.id() + "  " + $reason);
   }
}
