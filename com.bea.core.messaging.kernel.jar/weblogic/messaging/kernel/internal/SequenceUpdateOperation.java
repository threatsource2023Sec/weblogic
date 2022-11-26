package weblogic.messaging.kernel.internal;

import weblogic.messaging.MessagingLogger;
import weblogic.messaging.kernel.internal.persistence.PersistedSequenceRecord;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXAOperationWrapper;
import weblogic.store.gxa.GXATraceLogger;
import weblogic.store.gxa.GXATransaction;
import weblogic.store.gxa.GXid;

final class SequenceUpdateOperation implements GXAOperation {
   private KernelImpl kernel;
   private GXATransaction transaction;
   private GXid xid;
   private PersistedSequenceRecord persistedRec;
   private SequenceImpl sequence;
   private boolean twoPhase;

   SequenceUpdateOperation(KernelImpl kernel, SequenceImpl sequence) {
      this.kernel = kernel;
      this.sequence = sequence;
      this.persistedRec = sequence.getNumberRecord();
   }

   public void onInitialize(GXATraceLogger traceLogger, GXATransaction gxaTransaction, GXAOperationWrapper operationWrapper) {
      this.transaction = gxaTransaction;
      this.xid = gxaTransaction.getGXid();
      if (this.transaction.isRecovered()) {
         this.sequence.lock(this.transaction);
         this.twoPhase = true;
      }

   }

   public GXid getGXid() {
      return this.xid;
   }

   void setGXid(GXid xid) {
      this.xid = xid;
   }

   public String getDebugPrefix() {
      return "Kernel SequenceUpdateOperation";
   }

   public boolean onPrepare(int pass, boolean isOnePhase) {
      if (this.kernel.isOpened() && this.sequence.getQueue().isActive()) {
         if (!isOnePhase) {
            this.persistedRec.setXid(this.xid);
            this.twoPhase = true;
         }

         if (pass == 1 && this.sequence.getQueue().isDurable()) {
            this.kernel.getPersistence().updateSequenceNumber(this.transaction.getStoreTransaction(), this.sequence.getNumberPersistentHandle(), this.persistedRec);
         }

         return true;
      } else {
         MessagingLogger.logSequenceCommitAfterDeactivate(this.kernel.getName());
         return false;
      }
   }

   private void unSetXid() {
      this.persistedRec.setXid((GXid)null);
      this.kernel.getPersistence().updateSequenceNumber(this.transaction.getStoreTransaction(), this.sequence.getNumberPersistentHandle(), this.persistedRec);
   }

   public void onCommit(int pass) {
      if (this.twoPhase && pass == 1 && this.sequence.getQueue().isDurable()) {
         this.unSetXid();
      }

      if (pass == 3) {
         this.sequence.unlock(this.transaction);
      } else if (pass == 32) {
         this.sequence.setPoisoned(true);
         this.sequence.unlock(this.transaction);
      }

   }

   public void onRollback(int pass) {
      if (this.twoPhase && pass == 1 && this.sequence.getQueue().isDurable()) {
         this.unSetXid();
      }

      if (pass == 3) {
         if (!this.sequence.isLocked(this.transaction)) {
            this.sequence.lock(this.transaction);
         }

         this.sequence.setLastValueInternal(this.persistedRec.getOldValue());
         this.sequence.setLastAssignedValueInternal(this.persistedRec.getOldAssignedValue());
         this.sequence.setUserDataInternal(this.persistedRec.getOldUserData());
         this.sequence.unlock(this.transaction);
      }

   }
}
