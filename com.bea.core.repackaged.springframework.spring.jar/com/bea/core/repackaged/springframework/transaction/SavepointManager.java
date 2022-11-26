package com.bea.core.repackaged.springframework.transaction;

public interface SavepointManager {
   Object createSavepoint() throws TransactionException;

   void rollbackToSavepoint(Object var1) throws TransactionException;

   void releaseSavepoint(Object var1) throws TransactionException;
}
