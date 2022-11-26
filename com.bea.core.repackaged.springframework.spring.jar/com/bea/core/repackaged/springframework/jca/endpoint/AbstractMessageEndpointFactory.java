package com.bea.core.repackaged.springframework.jca.endpoint;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanNameAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.jta.SimpleTransactionFactory;
import com.bea.core.repackaged.springframework.transaction.jta.TransactionFactory;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.Method;
import javax.resource.ResourceException;
import javax.resource.spi.ApplicationServerInternalException;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

public abstract class AbstractMessageEndpointFactory implements MessageEndpointFactory, BeanNameAware {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private TransactionFactory transactionFactory;
   @Nullable
   private String transactionName;
   private int transactionTimeout = -1;
   @Nullable
   private String beanName;

   public void setTransactionManager(Object transactionManager) {
      if (transactionManager instanceof TransactionFactory) {
         this.transactionFactory = (TransactionFactory)transactionManager;
      } else {
         if (!(transactionManager instanceof TransactionManager)) {
            throw new IllegalArgumentException("Transaction manager [" + transactionManager + "] is neither a [org.springframework.transaction.jta.TransactionFactory} nor a [javax.transaction.TransactionManager]");
         }

         this.transactionFactory = new SimpleTransactionFactory((TransactionManager)transactionManager);
      }

   }

   public void setTransactionFactory(TransactionFactory transactionFactory) {
      this.transactionFactory = transactionFactory;
   }

   public void setTransactionName(String transactionName) {
      this.transactionName = transactionName;
   }

   public void setTransactionTimeout(int transactionTimeout) {
      this.transactionTimeout = transactionTimeout;
   }

   public void setBeanName(String beanName) {
      this.beanName = beanName;
   }

   @Nullable
   public String getActivationName() {
      return this.beanName;
   }

   @Nullable
   public Class getEndpointClass() {
      return null;
   }

   public boolean isDeliveryTransacted(Method method) throws NoSuchMethodException {
      return this.transactionFactory != null;
   }

   public MessageEndpoint createEndpoint(XAResource xaResource) throws UnavailableException {
      AbstractMessageEndpoint endpoint = this.createEndpointInternal();
      endpoint.initXAResource(xaResource);
      return endpoint;
   }

   public MessageEndpoint createEndpoint(XAResource xaResource, long timeout) throws UnavailableException {
      AbstractMessageEndpoint endpoint = this.createEndpointInternal();
      endpoint.initXAResource(xaResource);
      return endpoint;
   }

   protected abstract AbstractMessageEndpoint createEndpointInternal() throws UnavailableException;

   private class TransactionDelegate {
      @Nullable
      private final XAResource xaResource;
      @Nullable
      private Transaction transaction;
      private boolean rollbackOnly;

      public TransactionDelegate(@Nullable XAResource xaResource) {
         if (xaResource == null && AbstractMessageEndpointFactory.this.transactionFactory != null && !AbstractMessageEndpointFactory.this.transactionFactory.supportsResourceAdapterManagedTransactions()) {
            throw new IllegalStateException("ResourceAdapter-provided XAResource is required for transaction management. Check your ResourceAdapter's configuration.");
         } else {
            this.xaResource = xaResource;
         }
      }

      public void beginTransaction() throws Exception {
         if (AbstractMessageEndpointFactory.this.transactionFactory != null && this.xaResource != null) {
            this.transaction = AbstractMessageEndpointFactory.this.transactionFactory.createTransaction(AbstractMessageEndpointFactory.this.transactionName, AbstractMessageEndpointFactory.this.transactionTimeout);
            this.transaction.enlistResource(this.xaResource);
         }

      }

      public void setRollbackOnly() {
         if (this.transaction != null) {
            this.rollbackOnly = true;
         }

      }

      public void endTransaction() throws Exception {
         if (this.transaction != null) {
            try {
               if (this.rollbackOnly) {
                  this.transaction.rollback();
               } else {
                  this.transaction.commit();
               }
            } finally {
               this.transaction = null;
               this.rollbackOnly = false;
            }
         }

      }
   }

   protected abstract class AbstractMessageEndpoint implements MessageEndpoint {
      @Nullable
      private TransactionDelegate transactionDelegate;
      private boolean beforeDeliveryCalled = false;
      @Nullable
      private ClassLoader previousContextClassLoader;

      void initXAResource(XAResource xaResource) {
         this.transactionDelegate = AbstractMessageEndpointFactory.this.new TransactionDelegate(xaResource);
      }

      public void beforeDelivery(@Nullable Method method) throws ResourceException {
         this.beforeDeliveryCalled = true;
         Assert.state(this.transactionDelegate != null, "Not initialized");

         try {
            this.transactionDelegate.beginTransaction();
         } catch (Throwable var3) {
            throw new ApplicationServerInternalException("Failed to begin transaction", var3);
         }

         Thread currentThread = Thread.currentThread();
         this.previousContextClassLoader = currentThread.getContextClassLoader();
         currentThread.setContextClassLoader(this.getEndpointClassLoader());
      }

      protected abstract ClassLoader getEndpointClassLoader();

      protected final boolean hasBeforeDeliveryBeenCalled() {
         return this.beforeDeliveryCalled;
      }

      protected void onEndpointException(Throwable ex) {
         Assert.state(this.transactionDelegate != null, "Not initialized");
         this.transactionDelegate.setRollbackOnly();
         AbstractMessageEndpointFactory.this.logger.debug("Transaction marked as rollback-only after endpoint exception", ex);
      }

      public void afterDelivery() throws ResourceException {
         Assert.state(this.transactionDelegate != null, "Not initialized");
         this.beforeDeliveryCalled = false;
         Thread.currentThread().setContextClassLoader(this.previousContextClassLoader);
         this.previousContextClassLoader = null;

         try {
            this.transactionDelegate.endTransaction();
         } catch (Throwable var2) {
            AbstractMessageEndpointFactory.this.logger.warn("Failed to complete transaction after endpoint delivery", var2);
            throw new ApplicationServerInternalException("Failed to complete transaction", var2);
         }
      }

      public void release() {
         if (this.transactionDelegate != null) {
            try {
               this.transactionDelegate.setRollbackOnly();
               this.transactionDelegate.endTransaction();
            } catch (Throwable var2) {
               AbstractMessageEndpointFactory.this.logger.warn("Could not complete unfinished transaction on endpoint release", var2);
            }
         }

      }
   }
}
