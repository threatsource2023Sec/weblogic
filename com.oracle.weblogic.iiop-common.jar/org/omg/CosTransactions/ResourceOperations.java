package org.omg.CosTransactions;

public interface ResourceOperations {
   Vote prepare() throws HeuristicMixed, HeuristicHazard;

   void rollback() throws HeuristicCommit, HeuristicMixed, HeuristicHazard;

   void commit() throws NotPrepared, HeuristicRollback, HeuristicMixed, HeuristicHazard;

   void commit_one_phase() throws HeuristicHazard;

   void forget();
}
