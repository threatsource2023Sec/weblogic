package com.bea.core.repackaged.springframework.transaction.jta;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.IllegalTransactionStateException;
import com.bea.core.repackaged.springframework.transaction.InvalidTimeoutException;
import com.bea.core.repackaged.springframework.transaction.NestedTransactionNotSupportedException;
import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;
import com.bea.core.repackaged.springframework.transaction.TransactionException;
import com.bea.core.repackaged.springframework.transaction.TransactionSystemException;
import com.bea.core.repackaged.springframework.transaction.support.AbstractPlatformTransactionManager;
import com.bea.core.repackaged.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;
import com.bea.core.repackaged.springframework.transaction.support.DefaultTransactionDefinition;
import com.bea.core.repackaged.springframework.transaction.support.DefaultTransactionStatus;
import com.bea.core.repackaged.springframework.transaction.support.SmartTransactionObject;
import com.bea.core.repackaged.springframework.transaction.support.TransactionCallback;
import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronizationManager;
import com.bea.core.repackaged.springframework.transaction.support.TransactionSynchronizationUtils;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.ibm.wsspi.uow.UOWAction;
import com.ibm.wsspi.uow.UOWActionException;
import com.ibm.wsspi.uow.UOWException;
import com.ibm.wsspi.uow.UOWManager;
import com.ibm.wsspi.uow.UOWManagerFactory;
import java.util.List;
import javax.naming.NamingException;

public class WebSphereUowTransactionManager extends JtaTransactionManager implements CallbackPreferringPlatformTransactionManager {
   public static final String DEFAULT_UOW_MANAGER_NAME = "java:comp/websphere/UOWManager";
   @Nullable
   private UOWManager uowManager;
   @Nullable
   private String uowManagerName;

   public WebSphereUowTransactionManager() {
      this.setAutodetectTransactionManager(false);
   }

   public WebSphereUowTransactionManager(UOWManager uowManager) {
      this();
      this.uowManager = uowManager;
   }

   public void setUowManager(UOWManager uowManager) {
      this.uowManager = uowManager;
   }

   public void setUowManagerName(String uowManagerName) {
      this.uowManagerName = uowManagerName;
   }

   public void afterPropertiesSet() throws TransactionSystemException {
      this.initUserTransactionAndTransactionManager();
      if (this.uowManager == null) {
         if (this.uowManagerName != null) {
            this.uowManager = this.lookupUowManager(this.uowManagerName);
         } else {
            this.uowManager = this.lookupDefaultUowManager();
         }
      }

   }

   protected UOWManager lookupUowManager(String uowManagerName) throws TransactionSystemException {
      try {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Retrieving WebSphere UOWManager from JNDI location [" + uowManagerName + "]");
         }

         return (UOWManager)this.getJndiTemplate().lookup(uowManagerName, UOWManager.class);
      } catch (NamingException var3) {
         throw new TransactionSystemException("WebSphere UOWManager is not available at JNDI location [" + uowManagerName + "]", var3);
      }
   }

   protected UOWManager lookupDefaultUowManager() throws TransactionSystemException {
      try {
         this.logger.debug("Retrieving WebSphere UOWManager from default JNDI location [java:comp/websphere/UOWManager]");
         return (UOWManager)this.getJndiTemplate().lookup("java:comp/websphere/UOWManager", UOWManager.class);
      } catch (NamingException var2) {
         this.logger.debug("WebSphere UOWManager is not available at default JNDI location [java:comp/websphere/UOWManager] - falling back to UOWManagerFactory lookup");
         return UOWManagerFactory.getUOWManager();
      }
   }

   private UOWManager obtainUOWManager() {
      Assert.state(this.uowManager != null, "No UOWManager set");
      return this.uowManager;
   }

   protected void doRegisterAfterCompletionWithJtaTransaction(JtaTransactionObject txObject, List synchronizations) {
      this.obtainUOWManager().registerInterposedSynchronization(new JtaAfterCompletionSynchronization(synchronizations));
   }

   public boolean supportsResourceAdapterManagedTransactions() {
      return true;
   }

   @Nullable
   public Object execute(@Nullable TransactionDefinition definition, TransactionCallback callback) throws TransactionException {
      if (definition == null) {
         definition = new DefaultTransactionDefinition();
      }

      if (((TransactionDefinition)definition).getTimeout() < -1) {
         throw new InvalidTimeoutException("Invalid transaction timeout", ((TransactionDefinition)definition).getTimeout());
      } else {
         UOWManager uowManager = this.obtainUOWManager();
         int pb = ((TransactionDefinition)definition).getPropagationBehavior();
         boolean existingTx = uowManager.getUOWStatus() != 5 && uowManager.getUOWType() != 0;
         int uowType = 1;
         boolean joinTx = false;
         boolean newSynch = false;
         if (existingTx) {
            if (pb == 5) {
               throw new IllegalTransactionStateException("Transaction propagation 'never' but existing transaction found");
            }

            if (pb == 6) {
               throw new NestedTransactionNotSupportedException("Transaction propagation 'nested' not supported for WebSphere UOW transactions");
            }

            if (pb != 1 && pb != 0 && pb != 2) {
               if (pb == 4) {
                  uowType = 0;
                  newSynch = this.getTransactionSynchronization() == 0;
               } else {
                  newSynch = this.getTransactionSynchronization() != 2;
               }
            } else {
               joinTx = true;
               newSynch = this.getTransactionSynchronization() != 2;
            }
         } else {
            if (pb == 2) {
               throw new IllegalTransactionStateException("Transaction propagation 'mandatory' but no existing transaction found");
            }

            if (pb != 1 && pb != 4 && pb != 5) {
               newSynch = this.getTransactionSynchronization() != 2;
            } else {
               uowType = 0;
               newSynch = this.getTransactionSynchronization() == 0;
            }
         }

         boolean debug = this.logger.isDebugEnabled();
         if (debug) {
            this.logger.debug("Creating new transaction with name [" + ((TransactionDefinition)definition).getName() + "]: " + definition);
         }

         AbstractPlatformTransactionManager.SuspendedResourcesHolder suspendedResources = !joinTx ? this.suspend((Object)null) : null;
         UOWActionAdapter action = null;

         Object var12;
         try {
            if (((TransactionDefinition)definition).getTimeout() > -1) {
               uowManager.setUOWTimeout(uowType, ((TransactionDefinition)definition).getTimeout());
            }

            if (debug) {
               this.logger.debug("Invoking WebSphere UOW action: type=" + uowType + ", join=" + joinTx);
            }

            action = new UOWActionAdapter((TransactionDefinition)definition, callback, uowType == 1, !joinTx, newSynch, debug);
            uowManager.runUnderUOW(uowType, joinTx, action);
            if (debug) {
               this.logger.debug("Returned from WebSphere UOW action: type=" + uowType + ", join=" + joinTx);
            }

            var12 = action.getResult();
         } catch (UOWActionException | UOWException var18) {
            TransactionSystemException tse = new TransactionSystemException("UOWManager transaction processing failed", var18);
            Throwable appEx = action.getException();
            if (appEx != null) {
               this.logger.error("Application exception overridden by rollback exception", appEx);
               tse.initApplicationException(appEx);
            }

            throw tse;
         } finally {
            if (suspendedResources != null) {
               this.resume((Object)null, suspendedResources);
            }

         }

         return var12;
      }
   }

   private class UOWActionAdapter implements UOWAction, SmartTransactionObject {
      private final TransactionDefinition definition;
      private final TransactionCallback callback;
      private final boolean actualTransaction;
      private final boolean newTransaction;
      private final boolean newSynchronization;
      private boolean debug;
      @Nullable
      private Object result;
      @Nullable
      private Throwable exception;

      public UOWActionAdapter(TransactionDefinition definition, TransactionCallback callback, boolean actualTransaction, boolean newTransaction, boolean newSynchronization, boolean debug) {
         this.definition = definition;
         this.callback = callback;
         this.actualTransaction = actualTransaction;
         this.newTransaction = newTransaction;
         this.newSynchronization = newSynchronization;
         this.debug = debug;
      }

      public void run() {
         UOWManager uowManager = WebSphereUowTransactionManager.this.obtainUOWManager();
         DefaultTransactionStatus status = WebSphereUowTransactionManager.this.prepareTransactionStatus(this.definition, this.actualTransaction ? this : null, this.newTransaction, this.newSynchronization, this.debug, (Object)null);
         boolean var8 = false;

         List synchronizations;
         label163: {
            try {
               try {
                  var8 = true;
                  this.result = this.callback.doInTransaction(status);
                  WebSphereUowTransactionManager.this.triggerBeforeCommit(status);
                  var8 = false;
                  break label163;
               } catch (Throwable var9) {
                  this.exception = var9;
                  if (status.isDebug()) {
                     WebSphereUowTransactionManager.this.logger.debug("Rolling back on application exception from transaction callback", var9);
                  }
               }

               uowManager.setRollbackOnly();
               var8 = false;
            } finally {
               if (var8) {
                  if (status.isLocalRollbackOnly()) {
                     if (status.isDebug()) {
                        WebSphereUowTransactionManager.this.logger.debug("Transaction callback has explicitly requested rollback");
                     }

                     uowManager.setRollbackOnly();
                  }

                  WebSphereUowTransactionManager.this.triggerBeforeCompletion(status);
                  if (status.isNewSynchronization()) {
                     List synchronizationsx = TransactionSynchronizationManager.getSynchronizations();
                     TransactionSynchronizationManager.clear();
                     if (!synchronizationsx.isEmpty()) {
                        uowManager.registerInterposedSynchronization(new JtaAfterCompletionSynchronization(synchronizationsx));
                     }
                  }

               }
            }

            if (status.isLocalRollbackOnly()) {
               if (status.isDebug()) {
                  WebSphereUowTransactionManager.this.logger.debug("Transaction callback has explicitly requested rollback");
               }

               uowManager.setRollbackOnly();
            }

            WebSphereUowTransactionManager.this.triggerBeforeCompletion(status);
            if (status.isNewSynchronization()) {
               synchronizations = TransactionSynchronizationManager.getSynchronizations();
               TransactionSynchronizationManager.clear();
               if (!synchronizations.isEmpty()) {
                  uowManager.registerInterposedSynchronization(new JtaAfterCompletionSynchronization(synchronizations));
                  return;
               }
            }

            return;
         }

         if (status.isLocalRollbackOnly()) {
            if (status.isDebug()) {
               WebSphereUowTransactionManager.this.logger.debug("Transaction callback has explicitly requested rollback");
            }

            uowManager.setRollbackOnly();
         }

         WebSphereUowTransactionManager.this.triggerBeforeCompletion(status);
         if (status.isNewSynchronization()) {
            synchronizations = TransactionSynchronizationManager.getSynchronizations();
            TransactionSynchronizationManager.clear();
            if (!synchronizations.isEmpty()) {
               uowManager.registerInterposedSynchronization(new JtaAfterCompletionSynchronization(synchronizations));
            }
         }

      }

      @Nullable
      public Object getResult() {
         if (this.exception != null) {
            ReflectionUtils.rethrowRuntimeException(this.exception);
         }

         return this.result;
      }

      @Nullable
      public Throwable getException() {
         return this.exception;
      }

      public boolean isRollbackOnly() {
         return WebSphereUowTransactionManager.this.obtainUOWManager().getRollbackOnly();
      }

      public void flush() {
         TransactionSynchronizationUtils.triggerFlush();
      }
   }
}
