package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class SynchronizationUnavailable extends UserException {
   public SynchronizationUnavailable() {
      super(SynchronizationUnavailableHelper.id());
   }

   public SynchronizationUnavailable(String $reason) {
      super(SynchronizationUnavailableHelper.id() + "  " + $reason);
   }
}
