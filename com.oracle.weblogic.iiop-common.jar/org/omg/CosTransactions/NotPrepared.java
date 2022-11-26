package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class NotPrepared extends UserException {
   public NotPrepared() {
      super(NotPreparedHelper.id());
   }

   public NotPrepared(String $reason) {
      super(NotPreparedHelper.id() + "  " + $reason);
   }
}
