package weblogic.store;

import weblogic.common.CompletionRequest;

public interface PersistentStoreTransaction {
   void commit(CompletionRequest var1);

   void commit() throws PersistentStoreException;

   void rollback(CompletionRequest var1);

   void rollback() throws PersistentStoreException;

   boolean hasPendingWork();
}
