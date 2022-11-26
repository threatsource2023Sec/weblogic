package org.omg.CosTransactions;

public interface TransactionFactoryOperations {
   Control create(int var1);

   Control recreate(PropagationContext var1);
}
