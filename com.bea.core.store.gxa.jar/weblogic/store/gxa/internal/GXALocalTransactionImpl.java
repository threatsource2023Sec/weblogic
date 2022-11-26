package weblogic.store.gxa.internal;

import weblogic.common.CompletionListener;
import weblogic.common.CompletionRequest;
import weblogic.store.PersistentStoreException;
import weblogic.store.gxa.GXAException;
import weblogic.store.gxa.GXALocalTransaction;
import weblogic.store.gxa.GXid;
import weblogic.transaction.XIDFactory;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

final class GXALocalTransactionImpl extends GXAAbstractTransaction implements GXALocalTransaction {
   private static final GXidImpl LOCAL_XID = new GXidImpl(XIDFactory.createXID(new byte[10], (byte[])null));

   GXALocalTransactionImpl(GXAResourceImpl gxaResource) {
      super(gxaResource);
      this.setStatus(1);
   }

   public boolean isRecovered() {
      return false;
   }

   public GXid getGXid() {
      return LOCAL_XID;
   }

   GXidImpl getGXidImpl() {
      return LOCAL_XID;
   }

   public long getMillisSinceBeginIffTimedOut() {
      return 0L;
   }

   private void commitStart() throws GXAException {
      this.setStatus(2);
      this.storeTxAvailable = true;
      boolean ok = this.loopOperationCallbacks(1, 1);
      if (!ok) {
         this.handlePrepareFailure();
      } else {
         ok = this.loopOperationCallbacks(2, 1);
         if (!ok) {
            this.handlePrepareFailure();
         } else {
            ok = this.loopOperationCallbacks(3, 1);
            if (!ok) {
               this.handlePrepareFailure();
            } else {
               this.setStatus(6);
               this.loopOperationCallbacks(1, 3);
               this.loopOperationCallbacks(2, 3);
            }
         }
      }
   }

   public void commit(CompletionRequest completion) {
      this.commit(completion, WorkManagerFactory.getInstance().getSystem());
   }

   public void commit(CompletionRequest completion, WorkManager workManager) {
      try {
         this.commitStart();
      } catch (GXAException var4) {
         completion.setResult(var4);
         return;
      }

      if (this.hasStoreWork()) {
         CompletionRequest storeRequest = new CompletionRequest();
         storeRequest.addListener(new CommitCompletionListener(completion), workManager);
         this.persistentStoreTransaction.commit(storeRequest);
         this.loopOperationCallbacks(21, 3);
      } else {
         this.storeTxAvailable = false;
         this.loopOperationCallbacks(21, 3);
         this.loopOperationCallbacks(3, 3);
         completion.setResult((Object)null);
      }

      GXAResourceImpl var10000 = this.gxaResource;
      GXAResourceImpl.setThreadLocalTransaction((GXALocalTransactionImpl)null);
   }

   public void commitFailed() {
      this.loopOperationCallbacks(32, 3);
   }

   public void commit() throws GXAException {
      this.commitStart();
      if (this.hasStoreWork()) {
         CompletionRequest storeRequest = new CompletionRequest();
         this.persistentStoreTransaction.commit(storeRequest);
         this.loopOperationCallbacks(21, 3);

         try {
            storeRequest.getResult();
         } catch (PersistentStoreException var3) {
            this.storeTxAvailable = false;
            throw new GXAException(var3);
         } catch (Throwable var4) {
            this.storeTxAvailable = false;
            throw new RuntimeException(var4);
         }
      } else {
         this.loopOperationCallbacks(21, 3);
      }

      this.storeTxAvailable = false;
      this.loopOperationCallbacks(3, 3);
      GXAResourceImpl var10000 = this.gxaResource;
      GXAResourceImpl.setThreadLocalTransaction((GXALocalTransactionImpl)null);
   }

   private void handlePrepareFailure() throws GXAException {
      this.setStatus(4);
      if (this.hasStoreWork()) {
         try {
            this.persistentStoreTransaction.rollback();
         } catch (PersistentStoreException var2) {
         }
      }

      this.callRollbackCallbacks();
      throw new GXAException("Prepare failure on GXA local transaction");
   }

   protected void callRollbackCallbacks() {
      this.loopOperationCallbacks(1, 5);
      this.loopOperationCallbacks(2, 5);
      this.storeTxAvailable = false;
      this.loopOperationCallbacks(3, 5);
   }

   public void rollback() {
      try {
         this.setStatus(4);
         if (this.hasStoreWork()) {
            try {
               this.persistentStoreTransaction.rollback();
            } catch (PersistentStoreException var5) {
            }
         }

         this.storeTxAvailable = false;
         this.callRollbackCallbacks();
      } finally {
         GXAResourceImpl var10000 = this.gxaResource;
         GXAResourceImpl.setThreadLocalTransaction((GXALocalTransactionImpl)null);
      }

   }

   private final class CommitCompletionListener implements CompletionListener {
      private CompletionRequest parent;

      CommitCompletionListener(CompletionRequest parent) {
         this.parent = parent;
         parent.runListenersInSetResult(true);
      }

      public void onCompletion(CompletionRequest request, Object result) {
         GXALocalTransactionImpl.this.storeTxAvailable = false;
         GXALocalTransactionImpl.this.loopOperationCallbacks(3, 3);
         this.parent.setResult((Object)null);
      }

      public void onException(CompletionRequest request, Throwable reason) {
         GXALocalTransactionImpl.this.storeTxAvailable = false;
         GXALocalTransactionImpl.this.loopOperationCallbacks(32, 3);
         this.parent.setResult(reason);
      }
   }
}
