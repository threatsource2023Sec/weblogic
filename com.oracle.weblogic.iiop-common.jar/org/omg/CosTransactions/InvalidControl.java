package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class InvalidControl extends UserException {
   public InvalidControl() {
      super(InvalidControlHelper.id());
   }

   public InvalidControl(String $reason) {
      super(InvalidControlHelper.id() + "  " + $reason);
   }
}
