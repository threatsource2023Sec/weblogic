package org.omg.CosTransactions;

public interface SynchronizationOperations extends TransactionalObjectOperations {
   void before_completion();

   void after_completion(Status var1);
}
