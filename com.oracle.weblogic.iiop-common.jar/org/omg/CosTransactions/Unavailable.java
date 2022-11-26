package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class Unavailable extends UserException {
   public Unavailable() {
      super(UnavailableHelper.id());
   }

   public Unavailable(String $reason) {
      super(UnavailableHelper.id() + "  " + $reason);
   }
}
