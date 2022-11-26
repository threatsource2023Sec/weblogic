package weblogic.store.gxa;

import weblogic.common.CompletionRequest;
import weblogic.work.WorkManager;

public interface GXALocalTransaction extends GXATransaction {
   void commit(CompletionRequest var1);

   void commit(CompletionRequest var1, WorkManager var2);

   void commit() throws GXAException;

   void rollback();

   void commitFailed();
}
