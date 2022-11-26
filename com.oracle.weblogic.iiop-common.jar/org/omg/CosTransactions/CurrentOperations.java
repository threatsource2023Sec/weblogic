package org.omg.CosTransactions;

public interface CurrentOperations {
   void begin() throws SubtransactionsUnavailable;

   void commit(boolean var1) throws NoTransaction, HeuristicMixed, HeuristicHazard;

   void rollback() throws NoTransaction;

   void rollback_only() throws NoTransaction;

   Status get_status();

   String get_transaction_name();

   void set_timeout(int var1);

   int get_timeout();

   Control get_control();

   Control suspend();

   void resume(Control var1) throws InvalidControl;
}
