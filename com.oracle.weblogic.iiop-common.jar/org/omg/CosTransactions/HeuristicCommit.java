package org.omg.CosTransactions;

import org.omg.CORBA.UserException;

public final class HeuristicCommit extends UserException {
   public HeuristicCommit() {
      super(HeuristicCommitHelper.id());
   }

   public HeuristicCommit(String $reason) {
      super(HeuristicCommitHelper.id() + "  " + $reason);
   }
}
