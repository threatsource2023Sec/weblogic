package weblogic.store.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import weblogic.common.CompletionRequest;
import weblogic.store.PersistentHandle;

public final class PersistentStoreTransactionImpl extends PersistentTransactionImpl {
   private final PersistentStoreImpl store;
   private final HashMap pending = new HashMap();
   private final List handleList = new ArrayList();

   public PersistentStoreTransactionImpl(PersistentStoreImpl store) {
      this.store = store;
   }

   synchronized boolean hasPendingWorkInternal() {
      return this.handleList.size() != 0;
   }

   synchronized void commitInternal(CompletionRequest userCompReq) {
      this.store.schedule(this.handleList, userCompReq);
   }

   synchronized void rollbackInternal(CompletionRequest cr) {
      for(int i = 0; i < this.handleList.size(); ++i) {
         StoreRequest req = (StoreRequest)this.handleList.get(i);
         if (req.getType() == 1) {
            PersistentHandleImpl handle = req.getHandle();
            this.store.releaseHandle(req.getTypeCode(), handle);
         }
      }

      cr.setResult(Boolean.TRUE);
   }

   synchronized StoreRequest put(PersistentHandle handle, StoreRequest req) {
      this.handleList.add(req);
      StoreRequest oldStoreRequest = (StoreRequest)this.pending.put(handle, req);
      return oldStoreRequest;
   }

   synchronized StoreRequest get(PersistentHandle handle) {
      return (StoreRequest)this.pending.get(handle);
   }
}
