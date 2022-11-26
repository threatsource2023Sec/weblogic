package com.bea.core.repackaged.springframework.transaction.jta;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jndi.JndiTemplate;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.CannotCreateTransactionException;
import com.bea.core.repackaged.springframework.transaction.HeuristicCompletionException;
import com.bea.core.repackaged.springframework.transaction.IllegalTransactionStateException;
import com.bea.core.repackaged.springframework.transaction.InvalidIsolationLevelException;
import com.bea.core.repackaged.springframework.transaction.NestedTransactionNotSupportedException;
import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;
import com.bea.core.repackaged.springframework.transaction.TransactionSuspensionNotSupportedException;
import com.bea.core.repackaged.springframework.transaction.TransactionSystemException;
import com.bea.core.repackaged.springframework.transaction.UnexpectedRollbackException;
import com.bea.core.repackaged.springframework.transaction.support.AbstractPlatformTransactionManager;
import com.bea.core.repackaged.springframework.transaction.support.DefaultTransactionStatus;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.UserTransaction;

public class JtaTransactionManager extends AbstractPlatformTransactionManager implements TransactionFactory, InitializingBean, Serializable {
   public static final String DEFAULT_USER_TRANSACTION_NAME = "java:comp/UserTransaction";
   public static final String[] FALLBACK_TRANSACTION_MANAGER_NAMES = new String[]{"java:comp/TransactionManager", "java:appserver/TransactionManager", "java:pm/TransactionManager", "java:/TransactionManager"};
   public static final String DEFAULT_TRANSACTION_SYNCHRONIZATION_REGISTRY_NAME = "java:comp/TransactionSynchronizationRegistry";
   private transient JndiTemplate jndiTemplate;
   @Nullable
   private transient UserTransaction userTransaction;
   @Nullable
   private String userTransactionName;
   private boolean autodetectUserTransaction;
   private boolean cacheUserTransaction;
   private boolean userTransactionObtainedFromJndi;
   @Nullable
   private transient TransactionManager transactionManager;
   @Nullable
   private String transactionManagerName;
   private boolean autodetectTransactionManager;
   @Nullable
   private transient TransactionSynchronizationRegistry transactionSynchronizationRegistry;
   @Nullable
   private String transactionSynchronizationRegistryName;
   private boolean autodetectTransactionSynchronizationRegistry;
   private boolean allowCustomIsolationLevels;

   public JtaTransactionManager() {
      this.jndiTemplate = new JndiTemplate();
      this.autodetectUserTransaction = true;
      this.cacheUserTransaction = true;
      this.userTransactionObtainedFromJndi = false;
      this.autodetectTransactionManager = true;
      this.autodetectTransactionSynchronizationRegistry = true;
      this.allowCustomIsolationLevels = false;
      this.setNestedTransactionAllowed(true);
   }

   public JtaTransactionManager(UserTransaction userTransaction) {
      this();
      Assert.notNull(userTransaction, (String)"UserTransaction must not be null");
      this.userTransaction = userTransaction;
   }

   public JtaTransactionManager(UserTransaction userTransaction, TransactionManager transactionManager) {
      this();
      Assert.notNull(userTransaction, (String)"UserTransaction must not be null");
      Assert.notNull(transactionManager, (String)"TransactionManager must not be null");
      this.userTransaction = userTransaction;
      this.transactionManager = transactionManager;
   }

   public JtaTransactionManager(TransactionManager transactionManager) {
      this();
      Assert.notNull(transactionManager, (String)"TransactionManager must not be null");
      this.transactionManager = transactionManager;
      this.userTransaction = this.buildUserTransaction(transactionManager);
   }

   public void setJndiTemplate(JndiTemplate jndiTemplate) {
      Assert.notNull(jndiTemplate, (String)"JndiTemplate must not be null");
      this.jndiTemplate = jndiTemplate;
   }

   public JndiTemplate getJndiTemplate() {
      return this.jndiTemplate;
   }

   public void setJndiEnvironment(@Nullable Properties jndiEnvironment) {
      this.jndiTemplate = new JndiTemplate(jndiEnvironment);
   }

   @Nullable
   public Properties getJndiEnvironment() {
      return this.jndiTemplate.getEnvironment();
   }

   public void setUserTransaction(@Nullable UserTransaction userTransaction) {
      this.userTransaction = userTransaction;
   }

   @Nullable
   public UserTransaction getUserTransaction() {
      return this.userTransaction;
   }

   public void setUserTransactionName(String userTransactionName) {
      this.userTransactionName = userTransactionName;
   }

   public void setAutodetectUserTransaction(boolean autodetectUserTransaction) {
      this.autodetectUserTransaction = autodetectUserTransaction;
   }

   public void setCacheUserTransaction(boolean cacheUserTransaction) {
      this.cacheUserTransaction = cacheUserTransaction;
   }

   public void setTransactionManager(@Nullable TransactionManager transactionManager) {
      this.transactionManager = transactionManager;
   }

   @Nullable
   public TransactionManager getTransactionManager() {
      return this.transactionManager;
   }

   public void setTransactionManagerName(String transactionManagerName) {
      this.transactionManagerName = transactionManagerName;
   }

   public void setAutodetectTransactionManager(boolean autodetectTransactionManager) {
      this.autodetectTransactionManager = autodetectTransactionManager;
   }

   public void setTransactionSynchronizationRegistry(@Nullable TransactionSynchronizationRegistry transactionSynchronizationRegistry) {
      this.transactionSynchronizationRegistry = transactionSynchronizationRegistry;
   }

   @Nullable
   public TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
      return this.transactionSynchronizationRegistry;
   }

   public void setTransactionSynchronizationRegistryName(String transactionSynchronizationRegistryName) {
      this.transactionSynchronizationRegistryName = transactionSynchronizationRegistryName;
   }

   public void setAutodetectTransactionSynchronizationRegistry(boolean autodetectTransactionSynchronizationRegistry) {
      this.autodetectTransactionSynchronizationRegistry = autodetectTransactionSynchronizationRegistry;
   }

   public void setAllowCustomIsolationLevels(boolean allowCustomIsolationLevels) {
      this.allowCustomIsolationLevels = allowCustomIsolationLevels;
   }

   public void afterPropertiesSet() throws TransactionSystemException {
      this.initUserTransactionAndTransactionManager();
      this.checkUserTransactionAndTransactionManager();
      this.initTransactionSynchronizationRegistry();
   }

   protected void initUserTransactionAndTransactionManager() throws TransactionSystemException {
      if (this.userTransaction == null) {
         if (StringUtils.hasLength(this.userTransactionName)) {
            this.userTransaction = this.lookupUserTransaction(this.userTransactionName);
            this.userTransactionObtainedFromJndi = true;
         } else {
            this.userTransaction = this.retrieveUserTransaction();
            if (this.userTransaction == null && this.autodetectUserTransaction) {
               this.userTransaction = this.findUserTransaction();
            }
         }
      }

      if (this.transactionManager == null) {
         if (StringUtils.hasLength(this.transactionManagerName)) {
            this.transactionManager = this.lookupTransactionManager(this.transactionManagerName);
         } else {
            this.transactionManager = this.retrieveTransactionManager();
            if (this.transactionManager == null && this.autodetectTransactionManager) {
               this.transactionManager = this.findTransactionManager(this.userTransaction);
            }
         }
      }

      if (this.userTransaction == null && this.transactionManager != null) {
         this.userTransaction = this.buildUserTransaction(this.transactionManager);
      }

   }

   protected void checkUserTransactionAndTransactionManager() throws IllegalStateException {
      if (this.userTransaction != null) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Using JTA UserTransaction: " + this.userTransaction);
         }

         if (this.transactionManager != null) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Using JTA TransactionManager: " + this.transactionManager);
            }
         } else {
            this.logger.warn("No JTA TransactionManager found: transaction suspension not available");
         }

      } else {
         throw new IllegalStateException("No JTA UserTransaction available - specify either 'userTransaction' or 'userTransactionName' or 'transactionManager' or 'transactionManagerName'");
      }
   }

   protected void initTransactionSynchronizationRegistry() {
      if (this.transactionSynchronizationRegistry == null) {
         if (StringUtils.hasLength(this.transactionSynchronizationRegistryName)) {
            this.transactionSynchronizationRegistry = this.lookupTransactionSynchronizationRegistry(this.transactionSynchronizationRegistryName);
         } else {
            this.transactionSynchronizationRegistry = this.retrieveTransactionSynchronizationRegistry();
            if (this.transactionSynchronizationRegistry == null && this.autodetectTransactionSynchronizationRegistry) {
               this.transactionSynchronizationRegistry = this.findTransactionSynchronizationRegistry(this.userTransaction, this.transactionManager);
            }
         }
      }

      if (this.transactionSynchronizationRegistry != null && this.logger.isDebugEnabled()) {
         this.logger.debug("Using JTA TransactionSynchronizationRegistry: " + this.transactionSynchronizationRegistry);
      }

   }

   protected UserTransaction buildUserTransaction(TransactionManager transactionManager) {
      return (UserTransaction)(transactionManager instanceof UserTransaction ? (UserTransaction)transactionManager : new UserTransactionAdapter(transactionManager));
   }

   protected UserTransaction lookupUserTransaction(String userTransactionName) throws TransactionSystemException {
      try {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Retrieving JTA UserTransaction from JNDI location [" + userTransactionName + "]");
         }

         return (UserTransaction)this.getJndiTemplate().lookup(userTransactionName, UserTransaction.class);
      } catch (NamingException var3) {
         throw new TransactionSystemException("JTA UserTransaction is not available at JNDI location [" + userTransactionName + "]", var3);
      }
   }

   protected TransactionManager lookupTransactionManager(String transactionManagerName) throws TransactionSystemException {
      try {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Retrieving JTA TransactionManager from JNDI location [" + transactionManagerName + "]");
         }

         return (TransactionManager)this.getJndiTemplate().lookup(transactionManagerName, TransactionManager.class);
      } catch (NamingException var3) {
         throw new TransactionSystemException("JTA TransactionManager is not available at JNDI location [" + transactionManagerName + "]", var3);
      }
   }

   protected TransactionSynchronizationRegistry lookupTransactionSynchronizationRegistry(String registryName) throws TransactionSystemException {
      try {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Retrieving JTA TransactionSynchronizationRegistry from JNDI location [" + registryName + "]");
         }

         return (TransactionSynchronizationRegistry)this.getJndiTemplate().lookup(registryName, TransactionSynchronizationRegistry.class);
      } catch (NamingException var3) {
         throw new TransactionSystemException("JTA TransactionSynchronizationRegistry is not available at JNDI location [" + registryName + "]", var3);
      }
   }

   @Nullable
   protected UserTransaction retrieveUserTransaction() throws TransactionSystemException {
      return null;
   }

   @Nullable
   protected TransactionManager retrieveTransactionManager() throws TransactionSystemException {
      return null;
   }

   @Nullable
   protected TransactionSynchronizationRegistry retrieveTransactionSynchronizationRegistry() throws TransactionSystemException {
      return null;
   }

   @Nullable
   protected UserTransaction findUserTransaction() {
      String jndiName = "java:comp/UserTransaction";

      try {
         UserTransaction ut = (UserTransaction)this.getJndiTemplate().lookup(jndiName, UserTransaction.class);
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("JTA UserTransaction found at default JNDI location [" + jndiName + "]");
         }

         this.userTransactionObtainedFromJndi = true;
         return ut;
      } catch (NamingException var3) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("No JTA UserTransaction found at default JNDI location [" + jndiName + "]", var3);
         }

         return null;
      }
   }

   @Nullable
   protected TransactionManager findTransactionManager(@Nullable UserTransaction ut) {
      if (ut instanceof TransactionManager) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("JTA UserTransaction object [" + ut + "] implements TransactionManager");
         }

         return (TransactionManager)ut;
      } else {
         String[] var2 = FALLBACK_TRANSACTION_MANAGER_NAMES;
         int var3 = var2.length;
         int var4 = 0;

         while(var4 < var3) {
            String jndiName = var2[var4];

            try {
               TransactionManager tm = (TransactionManager)this.getJndiTemplate().lookup(jndiName, TransactionManager.class);
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("JTA TransactionManager found at fallback JNDI location [" + jndiName + "]");
               }

               return tm;
            } catch (NamingException var7) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("No JTA TransactionManager found at fallback JNDI location [" + jndiName + "]", var7);
               }

               ++var4;
            }
         }

         return null;
      }
   }

   @Nullable
   protected TransactionSynchronizationRegistry findTransactionSynchronizationRegistry(@Nullable UserTransaction ut, @Nullable TransactionManager tm) throws TransactionSystemException {
      if (this.userTransactionObtainedFromJndi) {
         String jndiName = "java:comp/TransactionSynchronizationRegistry";

         try {
            TransactionSynchronizationRegistry tsr = (TransactionSynchronizationRegistry)this.getJndiTemplate().lookup(jndiName, TransactionSynchronizationRegistry.class);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("JTA TransactionSynchronizationRegistry found at default JNDI location [" + jndiName + "]");
            }

            return tsr;
         } catch (NamingException var5) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("No JTA TransactionSynchronizationRegistry found at default JNDI location [" + jndiName + "]", var5);
            }
         }
      }

      if (ut instanceof TransactionSynchronizationRegistry) {
         return (TransactionSynchronizationRegistry)ut;
      } else {
         return tm instanceof TransactionSynchronizationRegistry ? (TransactionSynchronizationRegistry)tm : null;
      }
   }

   protected Object doGetTransaction() {
      UserTransaction ut = this.getUserTransaction();
      if (ut == null) {
         throw new CannotCreateTransactionException("No JTA UserTransaction available - programmatic PlatformTransactionManager.getTransaction usage not supported");
      } else {
         if (!this.cacheUserTransaction) {
            ut = this.lookupUserTransaction(this.userTransactionName != null ? this.userTransactionName : "java:comp/UserTransaction");
         }

         return this.doGetJtaTransaction(ut);
      }
   }

   protected JtaTransactionObject doGetJtaTransaction(UserTransaction ut) {
      return new JtaTransactionObject(ut);
   }

   protected boolean isExistingTransaction(Object transaction) {
      JtaTransactionObject txObject = (JtaTransactionObject)transaction;

      try {
         return txObject.getUserTransaction().getStatus() != 6;
      } catch (SystemException var4) {
         throw new TransactionSystemException("JTA failure on getStatus", var4);
      }
   }

   protected boolean useSavepointForNestedTransaction() {
      return false;
   }

   protected void doBegin(Object transaction, TransactionDefinition definition) {
      JtaTransactionObject txObject = (JtaTransactionObject)transaction;

      try {
         this.doJtaBegin(txObject, definition);
      } catch (UnsupportedOperationException | NotSupportedException var5) {
         throw new NestedTransactionNotSupportedException("JTA implementation does not support nested transactions", var5);
      } catch (SystemException var6) {
         throw new CannotCreateTransactionException("JTA failure on begin", var6);
      }
   }

   protected void doJtaBegin(JtaTransactionObject txObject, TransactionDefinition definition) throws NotSupportedException, SystemException {
      this.applyIsolationLevel(txObject, definition.getIsolationLevel());
      int timeout = this.determineTimeout(definition);
      this.applyTimeout(txObject, timeout);
      txObject.getUserTransaction().begin();
   }

   protected void applyIsolationLevel(JtaTransactionObject txObject, int isolationLevel) throws InvalidIsolationLevelException, SystemException {
      if (!this.allowCustomIsolationLevels && isolationLevel != -1) {
         throw new InvalidIsolationLevelException("JtaTransactionManager does not support custom isolation levels by default - switch 'allowCustomIsolationLevels' to 'true'");
      }
   }

   protected void applyTimeout(JtaTransactionObject txObject, int timeout) throws SystemException {
      if (timeout > -1) {
         txObject.getUserTransaction().setTransactionTimeout(timeout);
         if (timeout > 0) {
            txObject.resetTransactionTimeout = true;
         }
      }

   }

   protected Object doSuspend(Object transaction) {
      JtaTransactionObject txObject = (JtaTransactionObject)transaction;

      try {
         return this.doJtaSuspend(txObject);
      } catch (SystemException var4) {
         throw new TransactionSystemException("JTA failure on suspend", var4);
      }
   }

   protected Object doJtaSuspend(JtaTransactionObject txObject) throws SystemException {
      if (this.getTransactionManager() == null) {
         throw new TransactionSuspensionNotSupportedException("JtaTransactionManager needs a JTA TransactionManager for suspending a transaction: specify the 'transactionManager' or 'transactionManagerName' property");
      } else {
         return this.getTransactionManager().suspend();
      }
   }

   protected void doResume(@Nullable Object transaction, Object suspendedResources) {
      JtaTransactionObject txObject = (JtaTransactionObject)transaction;

      try {
         this.doJtaResume(txObject, suspendedResources);
      } catch (InvalidTransactionException var5) {
         throw new IllegalTransactionStateException("Tried to resume invalid JTA transaction", var5);
      } catch (IllegalStateException var6) {
         throw new TransactionSystemException("Unexpected internal transaction state", var6);
      } catch (SystemException var7) {
         throw new TransactionSystemException("JTA failure on resume", var7);
      }
   }

   protected void doJtaResume(@Nullable JtaTransactionObject txObject, Object suspendedTransaction) throws InvalidTransactionException, SystemException {
      if (this.getTransactionManager() == null) {
         throw new TransactionSuspensionNotSupportedException("JtaTransactionManager needs a JTA TransactionManager for suspending a transaction: specify the 'transactionManager' or 'transactionManagerName' property");
      } else {
         this.getTransactionManager().resume((Transaction)suspendedTransaction);
      }
   }

   protected boolean shouldCommitOnGlobalRollbackOnly() {
      return true;
   }

   protected void doCommit(DefaultTransactionStatus status) {
      JtaTransactionObject txObject = (JtaTransactionObject)status.getTransaction();

      try {
         int jtaStatus = txObject.getUserTransaction().getStatus();
         if (jtaStatus == 6) {
            throw new UnexpectedRollbackException("JTA transaction already completed - probably rolled back");
         } else if (jtaStatus == 4) {
            try {
               txObject.getUserTransaction().rollback();
            } catch (IllegalStateException var5) {
               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Rollback failure with transaction already marked as rolled back: " + var5);
               }
            }

            throw new UnexpectedRollbackException("JTA transaction already rolled back (probably due to a timeout)");
         } else {
            txObject.getUserTransaction().commit();
         }
      } catch (RollbackException var6) {
         throw new UnexpectedRollbackException("JTA transaction unexpectedly rolled back (maybe due to a timeout)", var6);
      } catch (HeuristicMixedException var7) {
         throw new HeuristicCompletionException(3, var7);
      } catch (HeuristicRollbackException var8) {
         throw new HeuristicCompletionException(2, var8);
      } catch (IllegalStateException var9) {
         throw new TransactionSystemException("Unexpected internal transaction state", var9);
      } catch (SystemException var10) {
         throw new TransactionSystemException("JTA failure on commit", var10);
      }
   }

   protected void doRollback(DefaultTransactionStatus status) {
      JtaTransactionObject txObject = (JtaTransactionObject)status.getTransaction();

      try {
         int jtaStatus = txObject.getUserTransaction().getStatus();
         if (jtaStatus != 6) {
            try {
               txObject.getUserTransaction().rollback();
            } catch (IllegalStateException var5) {
               if (jtaStatus != 4) {
                  throw new TransactionSystemException("Unexpected internal transaction state", var5);
               }

               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Rollback failure with transaction already marked as rolled back: " + var5);
               }
            }
         }

      } catch (SystemException var6) {
         throw new TransactionSystemException("JTA failure on rollback", var6);
      }
   }

   protected void doSetRollbackOnly(DefaultTransactionStatus status) {
      JtaTransactionObject txObject = (JtaTransactionObject)status.getTransaction();
      if (status.isDebug()) {
         this.logger.debug("Setting JTA transaction rollback-only");
      }

      try {
         int jtaStatus = txObject.getUserTransaction().getStatus();
         if (jtaStatus != 6 && jtaStatus != 4) {
            txObject.getUserTransaction().setRollbackOnly();
         }

      } catch (IllegalStateException var4) {
         throw new TransactionSystemException("Unexpected internal transaction state", var4);
      } catch (SystemException var5) {
         throw new TransactionSystemException("JTA failure on setRollbackOnly", var5);
      }
   }

   protected void registerAfterCompletionWithExistingTransaction(Object transaction, List synchronizations) {
      JtaTransactionObject txObject = (JtaTransactionObject)transaction;
      this.logger.debug("Registering after-completion synchronization with existing JTA transaction");

      try {
         this.doRegisterAfterCompletionWithJtaTransaction(txObject, synchronizations);
      } catch (SystemException var5) {
         throw new TransactionSystemException("JTA failure on registerSynchronization", var5);
      } catch (Exception var6) {
         if (!(var6 instanceof RollbackException) && !(var6.getCause() instanceof RollbackException)) {
            this.logger.debug("Participating in existing JTA transaction, but unexpected internal transaction state encountered: cannot register Spring after-completion callbacks with outer JTA transaction - processing Spring after-completion callbacks with outcome status 'unknown'Original exception: " + var6);
            this.invokeAfterCompletion(synchronizations, 2);
         } else {
            this.logger.debug("Participating in existing JTA transaction that has been marked for rollback: cannot register Spring after-completion callbacks with outer JTA transaction - immediately performing Spring after-completion callbacks with outcome status 'rollback'. Original exception: " + var6);
            this.invokeAfterCompletion(synchronizations, 1);
         }
      }

   }

   protected void doRegisterAfterCompletionWithJtaTransaction(JtaTransactionObject txObject, List synchronizations) throws RollbackException, SystemException {
      int jtaStatus = txObject.getUserTransaction().getStatus();
      if (jtaStatus == 6) {
         throw new RollbackException("JTA transaction already completed - probably rolled back");
      } else if (jtaStatus == 4) {
         throw new RollbackException("JTA transaction already rolled back (probably due to a timeout)");
      } else {
         if (this.transactionSynchronizationRegistry != null) {
            this.transactionSynchronizationRegistry.registerInterposedSynchronization(new JtaAfterCompletionSynchronization(synchronizations));
         } else if (this.getTransactionManager() != null) {
            Transaction transaction = this.getTransactionManager().getTransaction();
            if (transaction == null) {
               throw new IllegalStateException("No JTA Transaction available");
            }

            transaction.registerSynchronization(new JtaAfterCompletionSynchronization(synchronizations));
         } else {
            this.logger.warn("Participating in existing JTA transaction, but no JTA TransactionManager available: cannot register Spring after-completion callbacks with outer JTA transaction - processing Spring after-completion callbacks with outcome status 'unknown'");
            this.invokeAfterCompletion(synchronizations, 2);
         }

      }
   }

   protected void doCleanupAfterCompletion(Object transaction) {
      JtaTransactionObject txObject = (JtaTransactionObject)transaction;
      if (txObject.resetTransactionTimeout) {
         try {
            txObject.getUserTransaction().setTransactionTimeout(0);
         } catch (SystemException var4) {
            this.logger.debug("Failed to reset transaction timeout after JTA completion", var4);
         }
      }

   }

   public Transaction createTransaction(@Nullable String name, int timeout) throws NotSupportedException, SystemException {
      TransactionManager tm = this.getTransactionManager();
      Assert.state(tm != null, "No JTA TransactionManager available");
      if (timeout >= 0) {
         tm.setTransactionTimeout(timeout);
      }

      tm.begin();
      return new ManagedTransactionAdapter(tm);
   }

   public boolean supportsResourceAdapterManagedTransactions() {
      return false;
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this.jndiTemplate = new JndiTemplate();
      this.initUserTransactionAndTransactionManager();
      this.initTransactionSynchronizationRegistry();
   }
}
