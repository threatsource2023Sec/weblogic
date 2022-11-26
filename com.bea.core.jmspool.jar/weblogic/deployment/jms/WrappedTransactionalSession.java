package weblogic.deployment.jms;

import javax.jms.JMSException;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicSession;
import javax.jms.XASession;
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

public class WrappedTransactionalSession extends WrappedSession implements Synchronization {
   protected String resourceName;
   protected boolean hasNativeTransactions;
   protected Transaction enlistedTransaction;
   protected boolean localTransaction;

   protected void init(String resourceName, JMSSessionHolder holder, boolean nativeTransactions, WrappedClassManager manager) throws JMSException {
      super.init(holder, manager);
      this.hasNativeTransactions = nativeTransactions;
      this.resourceName = resourceName;
   }

   protected void init(Session session, XASession xaSession, int wrapStyle, WrappedClassManager manager, String resourceName, boolean hasNativeTransactions) throws JMSException {
      JMSSessionHolder holder;
      if (session instanceof QueueSession) {
         holder = new JMSSessionHolder((JMSConnectionHelperService)null, session, xaSession, 1, 1, false, System.currentTimeMillis());
      } else if (session instanceof TopicSession) {
         holder = new JMSSessionHolder((JMSConnectionHelperService)null, session, xaSession, 2, 1, false, System.currentTimeMillis());
      } else {
         holder = new JMSSessionHolder((JMSConnectionHelperService)null, session, xaSession, 3, 1, false, System.currentTimeMillis());
      }

      super.init(holder, manager);
      this.setConnectionStarted(true);
      this.setWrapStyle(wrapStyle);
      this.hasNativeTransactions = hasNativeTransactions;
      this.resourceName = resourceName;
   }

   public synchronized void close() throws JMSException {
      this.closed = true;
      this.closeProviderSession();
   }

   private void enlistInNewTransaction() throws JMSException, SystemException, NotSupportedException, RollbackException {
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

   private void enlistInExistingTransaction(Transaction tran) throws JMSException, SystemException, RollbackException {
      if (this.xaSession == null) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logXANotAvailableLoggable());
      } else {
         JMSPoolDebug.logger.debug("Enlisting wrapped JMS session using XA");
         tran.enlistResource(this.getXAResource());
         if (this.enlistedTransaction == null) {
            tran.registerSynchronization(this);
            this.enlistedTransaction = tran;
         }

      }
   }

   protected synchronized void enlistInTransaction(boolean isProducer) throws JMSException {
      try {
         ClientTransactionManager tm = TransactionHelper.getTransactionHelper().getTransactionManager();
         Transaction curTran = (Transaction)tm.getTransaction();
         if (curTran == null) {
            if (this.enlistedTransaction != null) {
               throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSSessionAlreadyEnlistedLoggable());
            }

            if (this.xaSession != null && (!isProducer || !this.hasNativeTransactions)) {
               this.enlistInNewTransaction();
            }
         } else {
            if (this.enlistedTransaction != null && !curTran.equals(this.enlistedTransaction)) {
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Already enlisted in another transaction, status: " + this.enlistedTransaction.getStatusAsString());
               }

               throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSSessionAlreadyEnlistedLoggable());
            }

            if (!this.hasNativeTransactions) {
               this.enlistInExistingTransaction(curTran);
            }
         }

      } catch (RollbackException var6) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var6);
      } catch (NotSupportedException var7) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var7);
      } catch (IllegalStateException var8) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var8);
      } catch (SystemException var9) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("enlistInTransaction failed with SystemException " + var9);
         }

         JMSException jmse = JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTARegistrationErrorLoggable(), var9);
         if (!this.hasNativeTransactions) {
            try {
               JMSSessionPool sessionPool = JMSSessionPoolManager.getSessionPoolManager().find(this.resourceName);
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Need to refresh XAResource on sessionPool " + sessionPool + " xaSession " + this.xaSession + " resourceName " + this.resourceName);
               }

               if (sessionPool != null) {
                  sessionPool.getExceptionListener().onException(jmse);
               }
            } catch (Exception var5) {
            }
         }

         throw jmse;
      }
   }

   protected synchronized void delistFromTransaction(boolean commit) throws JMSException {
      if (this.localTransaction) {
         try {
            if (commit) {
               this.enlistedTransaction.commit();
            } else {
               this.enlistedTransaction.rollback();
            }
         } catch (RollbackException var11) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var11);
         } catch (HeuristicMixedException var12) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var12);
         } catch (HeuristicRollbackException var13) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var13);
         } catch (SecurityException var14) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var14);
         } catch (IllegalStateException var15) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var15);
         } catch (SystemException var16) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logJMSJTAResolutionErrorLoggable(), var16);
         } finally {
            this.enlistedTransaction = null;
            this.localTransaction = false;
         }
      }

   }

   public synchronized XAResource getXAResource() throws JMSException {
      XAResource xaResource = this.sessionHolder.getXAResource();
      if (this.xaSession != null && xaResource == null) {
         XAResource res = this.xaSession.getXAResource();
         WrappedXAResource pooledRes = (WrappedXAResource)this.wrapperManager.getWrappedInstance(16, res);
         if (this.resourceName != null) {
            pooledRes.init(this.resourceName, res);
         } else {
            pooledRes.init((String)null, res);
         }

         xaResource = (XAResource)pooledRes;
         this.sessionHolder.setXAResource(xaResource);
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
