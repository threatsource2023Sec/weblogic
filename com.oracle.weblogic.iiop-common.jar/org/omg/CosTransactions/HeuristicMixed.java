package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class HeuristicMixed extends UserException {
   public HeuristicMixed() {
      super(HeuristicMixedHelper.id());
   }

   public HeuristicMixed(String $reason) {
      super(HeuristicMixedHelper.id() + "  " + $reason);
   }
}
