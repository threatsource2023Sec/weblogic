package com.bea.core.repackaged.springframework.transaction;

import com.bea.core.repackaged.springframework.lang.Nullable;

public interface PlatformTransactionManager {
   TransactionStatus getTransaction(@Nullable TransactionDefinition var1) throws TransactionException;

   void commit(TransactionStatus var1) throws TransactionException;

   void rollback(TransactionStatus var1) throws TransactionException;
}
