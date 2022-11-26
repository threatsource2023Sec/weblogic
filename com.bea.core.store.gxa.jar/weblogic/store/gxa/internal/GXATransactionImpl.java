package weblogic.store.gxa.internal;

import javax.transaction.xa.XAException;
import weblogic.common.CompletionRequest;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.gxa.GXid;
import weblogic.transaction.TimedOutException;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;

final class GXATransactionImpl extends GXAAbstractTransaction {
   private GXidImpl gxid;
   private boolean isRecovered;
   private GXATwoPhaseRecord twoPhaseRecord;

   GXATransactionImpl(GXAResourceImpl gxaResource, GXidImpl gxid, boolean isRecovered) {
      super(gxaResource);
      this.gxid = gxid;
      this.isRecovered = isRecovered;
   }

   GXATransactionImpl(GXAResourceImpl gxaResource, GXATwoPhaseRecord tpr) {
      super(gxaResource);
      this.gxid = tpr.getGXid();
      this.isRecovered = true;
      this.twoPhaseRecord = tpr;
   }

   private void notifyIOIssued(int type) {
      if (type == 3 || type == 4) {
         this.loopOperationCallbacks(21, type);
      }

   }

   private void commitStoreIO(int type) throws PersistentStoreException {
      PersistentStoreTransaction pst;
      synchronized(this) {
         pst = this.persistentStoreTransaction;
         if (pst == null) {
            this.notifyIOIssued(type);
            return;
         }

         this.persistentStoreTransaction = null;
      }

      CompletionRequest cr = new CompletionRequest();

      try {
         pst.commit(cr);
      } finally {
         this.notifyIOIssued(type);
      }

      try {
         cr.getResult();
      } catch (PersistentStoreException var10) {
         throw new PersistentStoreException(var10);
      } catch (Throwable var11) {
         throw new RuntimeException(var11);
      }
   }

   public boolean isRecovered() {
      return this.isRecovered;
   }

   public GXid getGXid() {
      return this.gxid;
   }

   GXidImpl getGXidImpl() {
      return this.gxid;
   }

   boolean doOperationCallbacks(String fn, int type) throws XAException {
      boolean allPassesOK = true;
      int pass = 1;
      this.storeTxAvailable = true;
      GXATwoPhaseRecord localTwoPhaseRecord = null;

      boolean var6;
      try {
         if (this.twoPhaseRecord != null) {
            if (type != 5 && type != 4) {
               throw new AssertionError("int prog err");
            }

            this.gxaResource.getStoreConnection().delete(this.getStoreTransaction(), this.twoPhaseRecord.getPersistentHandle(), 0);
            this.twoPhaseRecord = null;
            if (type == 4 && this.isRecovered()) {
               this.twoPhaseRecord = new GXATwoPhaseRecord(this.getGXidImpl(), true);
               this.twoPhaseRecord.setPersistentHandle(this.gxaResource.getStoreConnection().create(this.getStoreTransaction(), this.twoPhaseRecord, 0));
            }
         }

         for(; pass <= 3; ++pass) {
            allPassesOK = this.loopOperationCallbacks(pass, type);
            if (!allPassesOK) {
               var6 = false;
               return var6;
            }

            if (pass == 2) {
               if (type == 2 && this.hasStoreIOWork()) {
                  localTwoPhaseRecord = new GXATwoPhaseRecord(this.getGXidImpl(), false);
                  localTwoPhaseRecord.setPersistentHandle(this.gxaResource.getStoreConnection().create(this.getStoreTransaction(), localTwoPhaseRecord, 0));
               }

               if (type != 1) {
                  this.commitStoreIO(type);
               }

               this.storeTxAvailable = false;
            }
         }

         this.twoPhaseRecord = localTwoPhaseRecord;
         if (type != 1 && type != 2) {
            this.close();
         }

         var6 = true;
      } catch (Throwable var11) {
         this.gxaResource.registerFailedTransaction(fn, pass, this, var11);
         if (type == 3 || type == 4) {
            this.loopOperationCallbacks(32, type);
         }

         this.close();
         XAException xae = new XAException(-3);
         xae.initCause(var11);
         throw xae;
      } finally {
         this.storeTxAvailable = false;
      }

      return var6;
   }

   private synchronized void close() {
      GXAOperationWrapperImpl nextOperation;
      for(GXAOperationWrapperImpl operation = this.firstOperation; operation != null; operation = nextOperation) {
         nextOperation = operation.getNext();
         operation.setNext((GXAOperationWrapperImpl)null);
         operation.setPrev((GXAOperationWrapperImpl)null);
      }

   }

   private Transaction getWeblogicTransaction() {
      TransactionManager tranManager = null;

      try {
         tranManager = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
      } catch (ClassCastException var3) {
         throw new IllegalStateException("Transactions cannot be used in a client that uses both T3 and IIOP.");
      }

      javax.transaction.Transaction tx = tranManager.getTransaction(this.gxid.getXAXid());
      return tx instanceof Transaction ? (Transaction)tx : null;
   }

   public long getMillisSinceBeginIffTimedOut() {
      Transaction wtx = this.getWeblogicTransaction();
      return wtx != null && wtx.getRollbackReason() instanceof TimedOutException ? wtx.getMillisSinceBegin() : 0L;
   }
}
