package weblogic.connector.work;

import java.util.HashMap;
import javax.resource.NotSupportedException;
import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.TransactionContext;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkCompletedException;
import javax.resource.spi.work.WorkContext;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.connector.common.Debug;
import weblogic.connector.security.SubjectStack;
import weblogic.connector.security.layer.TransactionContextImpl;
import weblogic.connector.security.layer.WorkContextWrapper;
import weblogic.connector.security.layer.WorkImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.transaction.InterposedTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TxHelper;

public class TransactionContextProcessor extends BaseWorkContextProcessor {
   private static HashMap txIds = new HashMap(10);

   public Class getSupportedContextClass() {
      return TransactionContext.class;
   }

   public String validate(WorkContextWrapper context, WorkRuntimeMetadata work) {
      TransactionContextImpl txContext = (TransactionContextImpl)context;
      long timeout = txContext.getTransactionTimeout();
      Xid xid = txContext.getXid();
      StringBuilder sb = new StringBuilder();
      if (xid != null && xid.getGlobalTransactionId() == null) {
         sb.append("Xid must have valid global transaction id.");
      }

      if (timeout <= 0L && timeout != -1L) {
         sb.append("timeout must be positive long value but is [" + timeout + "]");
      }

      return sb.length() == 0 ? null : sb.toString();
   }

   public void setupContext(WorkContextWrapper context, WorkRuntimeMetadata metadata) throws Exception {
      TransactionContextImpl txContext = (TransactionContextImpl)context;
      this.setExecutionContext(txContext, metadata.getWork());
   }

   private void setExecutionContext(TransactionContextImpl txContext, WorkImpl work) throws WorkCompletedException {
      txContext.setTx((Transaction)null);
      txContext.setTxOK(false);

      try {
         if (Debug.isXAworkEnabled()) {
            Debug.xaWork("TransactionContextProcessor.setExecutionContext()");
         }

         Xid xid = txContext.getXid();
         long timeout = txContext.getTransactionTimeout();
         txContext.setTxOK(this.checkImportedTx(txContext, work, xid, timeout));
         if (Debug.isXAworkEnabled()) {
            Debug.xaWork("TransactionContextProcessor.setExecutionContext(),  xid = " + TxHelper.xidToString(xid, true));
         }

         if (xid != null) {
            long txTimeout = timeout / 1000L;
            if (txTimeout > 2147483647L) {
               throw new XAException(-5);
            }

            TransactionManager tm = TxHelper.getTransactionManager();
            txContext.setTx(tm.getTransaction(xid));
            weblogic.transaction.Transaction currentTx = TxHelper.getTransaction();
            if (Debug.isXAworkEnabled()) {
               Debug.xaWork("TransactionContextProcessor.setExecutionContext() tx(" + TxHelper.xidToString(xid, true) + ") = " + txContext.getTx());
               Debug.xaWork("TransactionContextProcessor.setExecutionContext() currentTx = " + currentTx);
            }

            if (txContext.getTx() != null && ((weblogic.transaction.Transaction)txContext.getTx()).isCoordinatorLocal()) {
               if (Debug.isXAworkEnabled()) {
                  Debug.xaWork("TransactionContextProcessor.setExecutionContext() Setting tx on Work Thread (txid = " + TxHelper.xidToString(xid, true));
               }

               tm.resume(txContext.getTx());
               if (Debug.isXAworkEnabled()) {
                  Debug.xaWork("TransactionContextProcessor.setExecutionContext() Succeeded in associating tx with thread. tx = " + TxHelper.xidToString(xid, true));
               }
            } else {
               if (txContext.getTx() != null && currentTx != null && !currentTx.equals(txContext.getTx())) {
                  if (Debug.isXAworkEnabled()) {
                     Debug.xaWork("TransactionContextProcessor.setExecutionContext() Can't associate tx with thread for txid = " + TxHelper.xidToString(xid, true) + ".  There is a different tx already associated with the thread. (tx = " + TxHelper.xidToString(currentTx.getXID(), true) + ")");
                  }

                  throw new IllegalStateException("Attempt to associate tx with Thread when a different tx is already associated with the thread");
               }

               if (Debug.isXAworkEnabled()) {
                  Debug.xaWork("TransactionContextProcessor.setExecutionContext() Starting tx using InterposedTransactionManager");
               }

               XAResource xaRes = this.getXAResourceFromInterposedTransactionManager();
               int txTimeoutInt = (int)txTimeout;
               xaRes.setTransactionTimeout(txTimeoutInt);
               xaRes.start(xid, 0);
            }
         }

      } catch (WorkCompletedException var12) {
         if (Debug.isXAworkEnabled()) {
            Debug.xaWork("TransactionContextProcessor.setExecutionContext() get WorkCompletedException", var12);
         }

         if (var12.getErrorCode() == null) {
            var12.setErrorCode(this.getErrorCodeOfRecreationFailed(txContext));
         }

         throw var12;
      } catch (Throwable var13) {
         if (Debug.isXAworkEnabled()) {
            Debug.xaWork("TransactionContextProcessor.setExecutionContext() get Throwable", var13);
         }

         String exMsg = Debug.getExceptionSetExecutionContextFailed(var13.toString());
         WorkCompletedException we = new WorkCompletedException(exMsg, var13);
         String errorCode = this.getErrorCodeOfRecreationFailed(txContext);
         we.setErrorCode(errorCode);
         throw we;
      }
   }

   private String getErrorCodeOfConcurrentWork(TransactionContextImpl txContext) {
      String errorCode;
      if (txContext.isUse16ErrorCode()) {
         errorCode = "4";
         if (Debug.isXAworkEnabled()) {
            Debug.xaWork("TransactionContextProcessor.getErrorCodeOfConcurrentWork() using errorCode: WorkContextErrorCodes.CONTEXT_SETUP_UNSUPPORTED:" + errorCode);
         }
      } else {
         errorCode = "2";
         if (Debug.isXAworkEnabled()) {
            Debug.xaWork("TransactionContextProcessor.getErrorCodeOfConcurrentWork() using errorCode: WorkException.TX_CONCURRENT_WORK_DISALLOWED:" + errorCode);
         }
      }

      return errorCode;
   }

   private String getErrorCodeOfRecreationFailed(TransactionContextImpl txContext) {
      String errorCode;
      if (txContext.isUse16ErrorCode()) {
         errorCode = "3";
         if (Debug.isXAworkEnabled()) {
            Debug.xaWork("TransactionContextProcessor.getErrorCodeOfRecreationFailed() using errorCode: WorkContextErrorCodes.CONTEXT_SETUP_FAILED:" + errorCode);
         }
      } else {
         errorCode = "3";
         if (Debug.isXAworkEnabled()) {
            Debug.xaWork("TransactionContextProcessor.getErrorCodeOfRecreationFailed() using errorCode: WorkException.TX_RECREATE_FAILED:" + errorCode);
         }
      }

      return errorCode;
   }

   private synchronized boolean checkImportedTx(TransactionContextImpl txContext, WorkImpl work, Xid xid, long timeout) throws WorkCompletedException {
      if (Debug.isXAworkEnabled()) {
         Debug.xaWork("TransactionContextProcessor..checkImportedTx( )");
      }

      String debugStr = null;
      if (xid == null) {
         if (Debug.isXAworkEnabled()) {
            Debug.xaWork("TransactionContextProcessor..checkImportedTx() found null Xid in ExcecutionContext; continuing with non-tx Work processing");
         }

         return false;
      } else {
         Gid gid = new Gid(xid.getGlobalTransactionId());
         if (Debug.isXAworkEnabled()) {
            debugStr = "TransactionContextProcessor..checkImportedTx(  ) with gid=" + gid;
            Debug.xaWork(debugStr);
         }

         if (txIds.containsKey(gid)) {
            if (Debug.isXAworkEnabled()) {
               Debug.xaWork(debugStr + " REJECTED.");
            }

            String exMsg = Debug.getExceptionImportedTxAlreadyActive(gid != null ? gid.toString() : null);
            throw new WorkCompletedException(exMsg, this.getErrorCodeOfConcurrentWork(txContext));
         } else {
            if (Debug.isXAworkEnabled()) {
               Debug.xaWork(debugStr + " ACCEPTED.");
            }

            txIds.put(gid, work);
            return true;
         }
      }
   }

   private synchronized void releaseImportedTx(TransactionContextImpl txContext, Work work) throws XAException {
      if (txContext.isTxOK()) {
         String ctxString = txContext.toString();
         if (Debug.isXAworkEnabled()) {
            Debug.xaWork("TransactionContextProcessor.releaseImportedTx( " + work + ", " + ctxString + " )");
         }

         Xid xid = txContext.getXid();
         if (xid == null) {
            if (Debug.isXAworkEnabled()) {
               Debug.xaWork("TransactionContextProcessor.releaseImportedTx() found null Xid; no imported tx to dissassociate from Work");
            }

         } else {
            if (txContext.getTx() != null) {
               try {
                  Transaction suspendedTx = TxHelper.getTransactionManager().suspend();
                  if (suspendedTx == null || !suspendedTx.equals(txContext.getTx())) {
                     Debug.xaWork("WARNING: TransactionContextProcessor.releaseImportedTx() tm.suspend() release tx = " + suspendedTx + " instead of tx = " + txContext.getTx() + " as expected");
                  }
               } catch (SystemException var11) {
                  Debug.xaWork("WARNING: TransactionContextProcessor.releaseImportedTx() tm.suspend() threw Exception " + var11);
               } finally {
                  txContext.setTx((Transaction)null);
               }
            } else {
               XAResource xar = this.getXAResourceFromInterposedTransactionManager();

               try {
                  xar.end(xid, 67108864);
               } catch (XAException var13) {
                  if (var13.errorCode != -4) {
                     throw var13;
                  }

                  Debug.xaWork("TransactionContextProcessor.releaseImportedTx() get XAER_NOTA, ignore", var13);
               }
            }

            Gid gid = new Gid(xid.getGlobalTransactionId());
            if (Debug.isXAworkEnabled()) {
               Debug.xaWork("TransactionContextProcessor.releaseImportedTx( " + work + ", " + ctxString + " ) releasing global tx " + gid);
            }

            Work workOfTx = (Work)txIds.get(gid);
            String exMsg;
            if (workOfTx != work) {
               if (Debug.isXAworkEnabled()) {
                  Debug.xaWork("TransactionContextProcessor.releaseImportedTx( " + work + ", " + ctxString + " ) failed to release global tx " + gid + " ; Work instance does match Work that started tx: " + workOfTx);
               }

               exMsg = Debug.getExceptionInvalidGid(gid.toString());
               throw new IllegalArgumentException(exMsg);
            } else if (txIds.remove(gid) == null) {
               if (Debug.isXAworkEnabled()) {
                  Debug.xaWork("TransactionContextProcessor.releaseImportedTx( " + work + ", " + ctxString + " ) failed to release global tx " + gid + " ; WorkManager is not holding Work instance for gid");
               }

               exMsg = Debug.getExceptionGidNotRegistered(gid.toString());
               throw new IllegalArgumentException(exMsg);
            }
         }
      }
   }

   private XAResource getXAResourceFromInterposedTransactionManager() {
      TransactionHelper txHelper = TransactionHelper.getTransactionHelper();
      InterposedTransactionManager tm = (InterposedTransactionManager)txHelper.getTransactionManager();
      return tm.getXAResource();
   }

   public void cleanupContext(WorkContextWrapper context, boolean executionSuccess, WorkRuntimeMetadata work) throws Exception {
      TransactionContextImpl txContext = (TransactionContextImpl)context;
      this.releaseImportedTx(txContext, work.getWork());
   }

   public WorkContextWrapper createWrapper(WorkContext originalWorkContext, SubjectStack adapterLayer, AuthenticatedSubject kernelId) {
      return new TransactionContextImpl((TransactionContext)originalWorkContext, adapterLayer, kernelId);
   }

   public static TransactionContextImpl createWrapper(ExecutionContext ec, SubjectStack adapterLayer, AuthenticatedSubject kernelId) throws NotSupportedException {
      TransactionContext txContext = new TransactionContext();
      txContext.setXid(ec.getXid());
      if (ec.getTransactionTimeout() != -1L) {
         txContext.setTransactionTimeout(ec.getTransactionTimeout());
      }

      TransactionContextImpl txContextImpl = new TransactionContextImpl(txContext, adapterLayer, kernelId);
      txContextImpl.setUse16ErrorCode(false);
      return txContextImpl;
   }
}
