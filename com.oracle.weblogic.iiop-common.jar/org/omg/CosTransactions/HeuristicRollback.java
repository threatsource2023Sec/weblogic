package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class HeuristicRollback extends UserException {
   public HeuristicRollback() {
      super(HeuristicRollbackHelper.id());
   }

   public HeuristicRollback(String $reason) {
      super(HeuristicRollbackHelper.id() + "  " + $reason);
   }
}
