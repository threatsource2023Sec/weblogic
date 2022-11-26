package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class HeuristicHazard extends UserException {
   public HeuristicHazard() {
      super(HeuristicHazardHelper.id());
   }

   public HeuristicHazard(String $reason) {
      super(HeuristicHazardHelper.id() + "  " + $reason);
   }
}
