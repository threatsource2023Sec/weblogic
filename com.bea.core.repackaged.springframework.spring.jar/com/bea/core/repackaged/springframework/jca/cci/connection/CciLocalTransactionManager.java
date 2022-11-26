package com.bea.core.repackaged.springframework.jca.cci.connection;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.CannotCreateTransactionException;
import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;
import com.bea.core.repackaged.springframework.transaction.TransactionException;
import com.bea.core.repackaged.springframework.transaction.TransactionSystemException;
import com.bea.core.repackaged.springframework.transaction.support.AbstractPlatformTransactionManager;
import com.bea.core.repackaged.springframework.transaction.support.DefaultTransactionStatus;
import com.bea.core.repackaged.springframework.transaction.support.ResourceTransactionManager;
import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronizationManager;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.spi.LocalTransactionException;

public class CciLocalTransactionManager extends AbstractPlatformTransactionManager implements ResourceTransactionManager, InitializingBean {
   @Nullable
   private ConnectionFactory connectionFactory;

   public CciLocalTransactionManager() {
   }

   public CciLocalTransactionManager(ConnectionFactory connectionFactory) {
      this.setConnectionFactory(connectionFactory);
      this.afterPropertiesSet();
   }

   public void setConnectionFactory(@Nullable ConnectionFactory cf) {
      if (cf instanceof TransactionAwareConnectionFactoryProxy) {
         this.connectionFactory = ((TransactionAwareConnectionFactoryProxy)cf).getTargetConnectionFactory();
      } else {
         this.connectionFactory = cf;
      }

   }

   @Nullable
   public ConnectionFactory getConnectionFactory() {
      return this.connectionFactory;
   }

   private ConnectionFactory obtainConnectionFactory() {
      ConnectionFactory connectionFactory = this.getConnectionFactory();
      Assert.state(connectionFactory != null, "No ConnectionFactory set");
      return connectionFactory;
   }

   public void afterPropertiesSet() {
      if (this.getConnectionFactory() == null) {
         throw new IllegalArgumentException("Property 'connectionFactory' is required");
      }
   }

   public Object getResourceFactory() {
      return this.obtainConnectionFactory();
   }

   protected Object doGetTransaction() {
      CciLocalTransactionObject txObject = new CciLocalTransactionObject();
      ConnectionHolder conHolder = (ConnectionHolder)TransactionSynchronizationManager.getResource(this.obtainConnectionFactory());
      txObject.setConnectionHolder(conHolder);
      return txObject;
   }

   protected boolean isExistingTransaction(Object transaction) {
      CciLocalTransactionObject txObject = (CciLocalTransactionObject)transaction;
      return txObject.hasConnectionHolder();
   }

   protected void doBegin(Object transaction, TransactionDefinition definition) {
      CciLocalTransactionObject txObject = (CciLocalTransactionObject)transaction;
      ConnectionFactory connectionFactory = this.obtainConnectionFactory();
      Connection con = null;

      try {
         con = connectionFactory.getConnection();
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Acquired Connection [" + con + "] for local CCI transaction");
         }

         ConnectionHolder connectionHolder = new ConnectionHolder(con);
         connectionHolder.setSynchronizedWithTransaction(true);
         con.getLocalTransaction().begin();
         int timeout = this.determineTimeout(definition);
         if (timeout != -1) {
            connectionHolder.setTimeoutInSeconds(timeout);
         }

         txObject.setConnectionHolder(connectionHolder);
         TransactionSynchronizationManager.bindResource(connectionFactory, connectionHolder);
      } catch (NotSupportedException var8) {
         ConnectionFactoryUtils.releaseConnection(con, connectionFactory);
         throw new CannotCreateTransactionException("CCI Connection does not support local transactions", var8);
      } catch (LocalTransactionException var9) {
         ConnectionFactoryUtils.releaseConnection(con, connectionFactory);
         throw new CannotCreateTransactionException("Could not begin local CCI transaction", var9);
      } catch (Throwable var10) {
         ConnectionFactoryUtils.releaseConnection(con, connectionFactory);
         throw new TransactionSystemException("Unexpected failure on begin of CCI local transaction", var10);
      }
   }

   protected Object doSuspend(Object transaction) {
      CciLocalTransactionObject txObject = (CciLocalTransactionObject)transaction;
      txObject.setConnectionHolder((ConnectionHolder)null);
      return TransactionSynchronizationManager.unbindResource(this.obtainConnectionFactory());
   }

   protected void doResume(@Nullable Object transaction, Object suspendedResources) {
      ConnectionHolder conHolder = (ConnectionHolder)suspendedResources;
      TransactionSynchronizationManager.bindResource(this.obtainConnectionFactory(), conHolder);
   }

   protected boolean isRollbackOnly(Object transaction) throws TransactionException {
      CciLocalTransactionObject txObject = (CciLocalTransactionObject)transaction;
      return txObject.getConnectionHolder().isRollbackOnly();
   }

   protected void doCommit(DefaultTransactionStatus status) {
      CciLocalTransactionObject txObject = (CciLocalTransactionObject)status.getTransaction();
      Connection con = txObject.getConnectionHolder().getConnection();
      if (status.isDebug()) {
         this.logger.debug("Committing CCI local transaction on Connection [" + con + "]");
      }

      try {
         con.getLocalTransaction().commit();
      } catch (LocalTransactionException var5) {
         throw new TransactionSystemException("Could not commit CCI local transaction", var5);
      } catch (ResourceException var6) {
         throw new TransactionSystemException("Unexpected failure on commit of CCI local transaction", var6);
      }
   }

   protected void doRollback(DefaultTransactionStatus status) {
      CciLocalTransactionObject txObject = (CciLocalTransactionObject)status.getTransaction();
      Connection con = txObject.getConnectionHolder().getConnection();
      if (status.isDebug()) {
         this.logger.debug("Rolling back CCI local transaction on Connection [" + con + "]");
      }

      try {
         con.getLocalTransaction().rollback();
      } catch (LocalTransactionException var5) {
         throw new TransactionSystemException("Could not roll back CCI local transaction", var5);
      } catch (ResourceException var6) {
         throw new TransactionSystemException("Unexpected failure on rollback of CCI local transaction", var6);
      }
   }

   protected void doSetRollbackOnly(DefaultTransactionStatus status) {
      CciLocalTransactionObject txObject = (CciLocalTransactionObject)status.getTransaction();
      if (status.isDebug()) {
         this.logger.debug("Setting CCI local transaction [" + txObject.getConnectionHolder().getConnection() + "] rollback-only");
      }

      txObject.getConnectionHolder().setRollbackOnly();
   }

   protected void doCleanupAfterCompletion(Object transaction) {
      CciLocalTransactionObject txObject = (CciLocalTransactionObject)transaction;
      ConnectionFactory connectionFactory = this.obtainConnectionFactory();
      TransactionSynchronizationManager.unbindResource(connectionFactory);
      txObject.getConnectionHolder().clear();
      Connection con = txObject.getConnectionHolder().getConnection();
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Releasing CCI Connection [" + con + "] after transaction");
      }

      ConnectionFactoryUtils.releaseConnection(con, connectionFactory);
   }

   private static class CciLocalTransactionObject {
      @Nullable
      private ConnectionHolder connectionHolder;

      private CciLocalTransactionObject() {
      }

      public void setConnectionHolder(@Nullable ConnectionHolder connectionHolder) {
         this.connectionHolder = connectionHolder;
      }

      public ConnectionHolder getConnectionHolder() {
         Assert.state(this.connectionHolder != null, "No ConnectionHolder available");
         return this.connectionHolder;
      }

      public boolean hasConnectionHolder() {
         return this.connectionHolder != null;
      }

      // $FF: synthetic method
      CciLocalTransactionObject(Object x0) {
         this();
      }
   }
}
