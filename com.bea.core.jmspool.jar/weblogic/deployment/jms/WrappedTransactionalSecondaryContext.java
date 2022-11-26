package weblogic.deployment.jms;

import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;

public class WrappedTransactionalSecondaryContext extends WrappedSecondaryContext implements Synchronization {
   protected String resourceName;
   protected boolean hasNativeTransactions;
   protected Transaction enlistedTransaction;
   protected boolean localTransaction;

   protected void init(String resourceName, SecondaryContextHolder holder, boolean nativeTransactions, WrappedClassManager manager) {
      super.init(holder, manager);
      this.hasNativeTransactions = nativeTransactions;
      this.resourceName = resourceName;
   }

   public synchronized void close() throws JMSException {
      this.closed = true;
      this.closeProviderSecondaryContext();
   }

   private void enlistInNewTransaction() throws SystemException, NotSupportedException, RollbackException {
      TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
      tm.begin("MDB");
      Transaction tran = (Transaction)tm.getTransaction();
      this.localTransaction = true;
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Starting local transaction for JMS operation: " + tran.getName() + " and enlisting it using XA");
      }

      if (!this.hasNativeTransactions) {
         tran.enlistResource(this.getXAResource());
      }

      this.enlistedTransaction = tran;
   }

   private void enlistInExistingTransaction(Transaction tran) throws SystemException, RollbackException {
      if (this.xaJMSContext == null) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logXANotAvailableLoggable());
      } else {
         JMSPoolDebug.logger.debug("Enlisting wrapped JMS secondary context using XA");
         tran.enlistResource(this.getXAResource());
         if (this.enlistedTransaction == null) {
            tran.registerSynchronization(this);
            this.enlistedTransaction = tran;
         }

      }
   }

   protected synchronized void enlistInTransaction(boolean isProducer) {
      try {
         ClientTransactionManager tm = TransactionHelper.getTransactionHelper().getTransactionManager();
         Transaction curTran = (Transaction)tm.getTransaction();
         if (curTran == null) {
            if (this.enlistedTransaction != null) {
               throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSSessionAlreadyEnlistedLoggable());
            }

            if (this.xaJMSContext != null && (!isProducer || !this.hasNativeTransactions)) {
               this.enlistInNewTransaction();
            }
         } else {
            if (this.enlistedTransaction != null && !curTran.equals(this.enlistedTransaction)) {
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Already enlisted in another transaction, status: " + this.enlistedTransaction.getStatusAsString());
               }

               throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSSessionAlreadyEnlistedLoggable());
            }

            if (!this.hasNativeTransactions) {
               this.enlistInExistingTransaction(curTran);
            }
         }

      } catch (RollbackException var6) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var6);
      } catch (NotSupportedException var7) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var7);
      } catch (IllegalStateException var8) {
         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var8);
      } catch (SystemException var9) {
         SystemException se = var9;
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("enlistInTransaction failed with SystemException " + var9);
         }

         if (!this.hasNativeTransactions) {
            try {
               JMSSessionPool sessionPool = JMSSessionPoolManager.getSessionPoolManager().find(this.resourceName);
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Need to refresh XAResource on sessionPool " + sessionPool + " xaSession " + this.xaJMSContext + " resourceName " + this.resourceName);
               }

               if (sessionPool != null) {
                  JMSException jmse = JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), se);
                  sessionPool.getExceptionListener().onException(jmse);
               }
            } catch (Exception var5) {
            }
         }

         throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var9);
      }
   }

   protected synchronized void delistFromTransaction(boolean commit) {
      if (this.localTransaction) {
         try {
            if (commit) {
               this.enlistedTransaction.commit();
            } else {
               this.enlistedTransaction.rollback();
            }
         } catch (RollbackException var11) {
            throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var11);
         } catch (HeuristicMixedException var12) {
            throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var12);
         } catch (HeuristicRollbackException var13) {
            throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var13);
         } catch (SecurityException var14) {
            throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var14);
         } catch (IllegalStateException var15) {
            throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var15);
         } catch (SystemException var16) {
            throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var16);
         } finally {
            this.enlistedTransaction = null;
            this.localTransaction = false;
         }
      }

   }

   public synchronized XAResource getXAResource() {
      XAResource xaResource = this.secondaryContextHolder.getXAResource();
      if (this.xaJMSContext != null && xaResource == null) {
         XAResource res = this.xaJMSContext.getXAResource();

         WrappedXAResource pooledRes;
         try {
            pooledRes = (WrappedXAResource)this.wrapperManager.getWrappedInstance(16, res);
         } catch (JMSException var5) {
            throw new JMSRuntimeException(var5.getMessage(), var5.getErrorCode(), var5);
         }

         if (this.resourceName != null) {
            pooledRes.init(this.resourceName, res);
         } else {
            pooledRes.init((String)null, res);
         }

         xaResource = (XAResource)pooledRes;
         this.secondaryContextHolder.setXAResource(xaResource);
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Created new XAResource " + xaResource + " for vendor XAResource " + res);
         }
      }

      return xaResource;
   }

   protected synchronized Transaction getEnlistedTransaction() {
      return this.enlistedTransaction;
   }

   public void beforeCompletion() {
   }

   public synchronized void afterCompletion(int status) {
      this.enlistedTransaction = null;
   }
}
