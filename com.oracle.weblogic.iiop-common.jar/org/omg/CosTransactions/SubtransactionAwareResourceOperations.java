package org.omg.CosTransactions;

public interface SubtransactionAwareResourceOperations extends ResourceOperations {
   void commit_subtransaction(Coordinator var1);

   void rollback_subtransaction();
}
