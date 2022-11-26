package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class Inactive extends UserException {
   public Inactive() {
      super(InactiveHelper.id());
   }

   public Inactive(String $reason) {
      super(InactiveHelper.id() + "  " + $reason);
   }
}
