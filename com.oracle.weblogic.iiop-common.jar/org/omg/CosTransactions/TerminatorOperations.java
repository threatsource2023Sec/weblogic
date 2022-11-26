package org.omg.CosTransactions;

public interface TerminatorOperations {
   void commit(boolean var1) throws HeuristicMixed, HeuristicHazard;

   void rollback();
}
