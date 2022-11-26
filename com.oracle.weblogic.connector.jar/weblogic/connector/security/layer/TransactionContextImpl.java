package weblogic.connector.security.layer;

import javax.resource.NotSupportedException;
import javax.resource.spi.work.TransactionContext;
import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkContextLifecycleListener;
import javax.transaction.Transaction;
import javax.transaction.xa.Xid;
import weblogic.connector.security.SubjectStack;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class TransactionContextImpl extends TransactionContext implements WorkContextWrapper {
   private static final long serialVersionUID = 6950101113723277922L;
   private WorkContextImpl contextImpl;
   private boolean use16ErrorCode = true;
   private boolean txOK;
   private transient Transaction tx;

   public TransactionContextImpl(TransactionContext context, SubjectStack adapterLayer, AuthenticatedSubject kernelId) {
      this.contextImpl = new WorkContextImpl(context, adapterLayer, "TransactionContext", kernelId);
   }

   public Class getOriginalClass() {
      return this.contextImpl.getOriginalClass();
   }

   public WorkContext getOriginalWorkContext() {
      return this.contextImpl.getOriginalWorkContext();
   }

   public WorkContextLifecycleListener getOriginalWorkContextLifecycleListener() {
      return this.contextImpl.getOriginalWorkContextLifecycleListener();
   }

   public boolean supportWorkContextLifecycleListener() {
      return this.contextImpl.supportWorkContextLifecycleListener();
   }

   public String getDescription() {
      return this.contextImpl.getDescription();
   }

   public String getName() {
      return this.contextImpl.getName();
   }

   public void contextSetupComplete() {
      this.contextImpl.contextSetupComplete();
   }

   public void contextSetupFailed(String errorCode) {
      this.contextImpl.contextSetupFailed(errorCode);
   }

   public boolean equals(Object other) {
      return this == other;
   }

   public int hashCode() {
      return this.contextImpl.hashCode();
   }

   public String toString() {
      return this.contextImpl.toString();
   }

   public TransactionContext getOriginalTransactionContext() {
      return (TransactionContext)this.getOriginalWorkContext();
   }

   public void setXid(Xid xid) {
      this.contextImpl.rejectIllegalUpdate();
   }

   public Xid getXid() {
      this.contextImpl.push();

      Xid var1;
      try {
         var1 = this.getOriginalTransactionContext().getXid();
      } finally {
         this.contextImpl.pop();
      }

      return var1;
   }

   public void setTransactionTimeout(long timeout) throws NotSupportedException {
      this.contextImpl.rejectIllegalUpdate();
   }

   public long getTransactionTimeout() {
      this.contextImpl.push();

      long var1;
      try {
         var1 = this.getOriginalTransactionContext().getTransactionTimeout();
      } finally {
         this.contextImpl.pop();
      }

      return var1;
   }

   public void setUse16ErrorCode(boolean use16ErrorCode) {
      this.use16ErrorCode = use16ErrorCode;
   }

   public boolean isUse16ErrorCode() {
      return this.use16ErrorCode;
   }

   public boolean isTxOK() {
      return this.txOK;
   }

   public void setTxOK(boolean txOK) {
      this.txOK = txOK;
   }

   public Transaction getTx() {
      return this.tx;
   }

   public void setTx(Transaction tx) {
      this.tx = tx;
   }
}
