package com.bea.core.repackaged.springframework.transaction.jta;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;
import com.bea.core.repackaged.springframework.transaction.TransactionSystemException;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

public class WebLogicJtaTransactionManager extends JtaTransactionManager {
   private static final String USER_TRANSACTION_CLASS_NAME = "weblogic.transaction.UserTransaction";
   private static final String CLIENT_TRANSACTION_MANAGER_CLASS_NAME = "weblogic.transaction.ClientTransactionManager";
   private static final String TRANSACTION_CLASS_NAME = "weblogic.transaction.Transaction";
   private static final String TRANSACTION_HELPER_CLASS_NAME = "weblogic.transaction.TransactionHelper";
   private static final String ISOLATION_LEVEL_KEY = "ISOLATION LEVEL";
   private boolean weblogicUserTransactionAvailable;
   @Nullable
   private Method beginWithNameMethod;
   @Nullable
   private Method beginWithNameAndTimeoutMethod;
   private boolean weblogicTransactionManagerAvailable;
   @Nullable
   private Method forceResumeMethod;
   @Nullable
   private Method setPropertyMethod;
   @Nullable
   private Object transactionHelper;

   public void afterPropertiesSet() throws TransactionSystemException {
      super.afterPropertiesSet();
      this.loadWebLogicTransactionClasses();
   }

   @Nullable
   protected UserTransaction retrieveUserTransaction() throws TransactionSystemException {
      Object helper = this.loadWebLogicTransactionHelper();

      try {
         this.logger.trace("Retrieving JTA UserTransaction from WebLogic TransactionHelper");
         Method getUserTransactionMethod = helper.getClass().getMethod("getUserTransaction");
         return (UserTransaction)getUserTransactionMethod.invoke(this.transactionHelper);
      } catch (InvocationTargetException var3) {
         throw new TransactionSystemException("WebLogic's TransactionHelper.getUserTransaction() method failed", var3.getTargetException());
      } catch (Exception var4) {
         throw new TransactionSystemException("Could not invoke WebLogic's TransactionHelper.getUserTransaction() method", var4);
      }
   }

   @Nullable
   protected TransactionManager retrieveTransactionManager() throws TransactionSystemException {
      Object helper = this.loadWebLogicTransactionHelper();

      try {
         this.logger.trace("Retrieving JTA TransactionManager from WebLogic TransactionHelper");
         Method getTransactionManagerMethod = helper.getClass().getMethod("getTransactionManager");
         return (TransactionManager)getTransactionManagerMethod.invoke(this.transactionHelper);
      } catch (InvocationTargetException var3) {
         throw new TransactionSystemException("WebLogic's TransactionHelper.getTransactionManager() method failed", var3.getTargetException());
      } catch (Exception var4) {
         throw new TransactionSystemException("Could not invoke WebLogic's TransactionHelper.getTransactionManager() method", var4);
      }
   }

   private Object loadWebLogicTransactionHelper() throws TransactionSystemException {
      Object helper = this.transactionHelper;
      if (helper == null) {
         try {
            Class transactionHelperClass = this.getClass().getClassLoader().loadClass("weblogic.transaction.TransactionHelper");
            Method getTransactionHelperMethod = transactionHelperClass.getMethod("getTransactionHelper");
            helper = getTransactionHelperMethod.invoke((Object)null);
            this.transactionHelper = helper;
            this.logger.trace("WebLogic TransactionHelper found");
         } catch (InvocationTargetException var4) {
            throw new TransactionSystemException("WebLogic's TransactionHelper.getTransactionHelper() method failed", var4.getTargetException());
         } catch (Exception var5) {
            throw new TransactionSystemException("Could not initialize WebLogicJtaTransactionManager because WebLogic API classes are not available", var5);
         }
      }

      return helper;
   }

   private void loadWebLogicTransactionClasses() throws TransactionSystemException {
      try {
         Class userTransactionClass = this.getClass().getClassLoader().loadClass("weblogic.transaction.UserTransaction");
         this.weblogicUserTransactionAvailable = userTransactionClass.isInstance(this.getUserTransaction());
         if (this.weblogicUserTransactionAvailable) {
            this.beginWithNameMethod = userTransactionClass.getMethod("begin", String.class);
            this.beginWithNameAndTimeoutMethod = userTransactionClass.getMethod("begin", String.class, Integer.TYPE);
            this.logger.debug("Support for WebLogic transaction names available");
         } else {
            this.logger.debug("Support for WebLogic transaction names not available");
         }

         Class transactionManagerClass = this.getClass().getClassLoader().loadClass("weblogic.transaction.ClientTransactionManager");
         this.logger.trace("WebLogic ClientTransactionManager found");
         this.weblogicTransactionManagerAvailable = transactionManagerClass.isInstance(this.getTransactionManager());
         if (this.weblogicTransactionManagerAvailable) {
            Class transactionClass = this.getClass().getClassLoader().loadClass("weblogic.transaction.Transaction");
            this.forceResumeMethod = transactionManagerClass.getMethod("forceResume", Transaction.class);
            this.setPropertyMethod = transactionClass.getMethod("setProperty", String.class, Serializable.class);
            this.logger.debug("Support for WebLogic forceResume available");
         } else {
            this.logger.debug("Support for WebLogic forceResume not available");
         }

      } catch (Exception var4) {
         throw new TransactionSystemException("Could not initialize WebLogicJtaTransactionManager because WebLogic API classes are not available", var4);
      }
   }

   private TransactionManager obtainTransactionManager() {
      TransactionManager tm = this.getTransactionManager();
      Assert.state(tm != null, "No TransactionManager set");
      return tm;
   }

   protected void doJtaBegin(JtaTransactionObject txObject, TransactionDefinition definition) throws NotSupportedException, SystemException {
      int timeout = this.determineTimeout(definition);
      if (this.weblogicUserTransactionAvailable && definition.getName() != null) {
         try {
            if (timeout > -1) {
               Assert.state(this.beginWithNameAndTimeoutMethod != null, "WebLogic JTA API not initialized");
               this.beginWithNameAndTimeoutMethod.invoke(txObject.getUserTransaction(), definition.getName(), timeout);
            } else {
               Assert.state(this.beginWithNameMethod != null, "WebLogic JTA API not initialized");
               this.beginWithNameMethod.invoke(txObject.getUserTransaction(), definition.getName());
            }
         } catch (InvocationTargetException var8) {
            throw new TransactionSystemException("WebLogic's UserTransaction.begin() method failed", var8.getTargetException());
         } catch (Exception var9) {
            throw new TransactionSystemException("Could not invoke WebLogic's UserTransaction.begin() method", var9);
         }
      } else {
         this.applyTimeout(txObject, timeout);
         txObject.getUserTransaction().begin();
      }

      if (this.weblogicTransactionManagerAvailable) {
         if (definition.getIsolationLevel() != -1) {
            try {
               Transaction tx = this.obtainTransactionManager().getTransaction();
               Integer isolationLevel = definition.getIsolationLevel();
               Assert.state(this.setPropertyMethod != null, "WebLogic JTA API not initialized");
               this.setPropertyMethod.invoke(tx, "ISOLATION LEVEL", isolationLevel);
            } catch (InvocationTargetException var6) {
               throw new TransactionSystemException("WebLogic's Transaction.setProperty(String, Serializable) method failed", var6.getTargetException());
            } catch (Exception var7) {
               throw new TransactionSystemException("Could not invoke WebLogic's Transaction.setProperty(String, Serializable) method", var7);
            }
         }
      } else {
         this.applyIsolationLevel(txObject, definition.getIsolationLevel());
      }

   }

   protected void doJtaResume(@Nullable JtaTransactionObject txObject, Object suspendedTransaction) throws InvalidTransactionException, SystemException {
      try {
         this.obtainTransactionManager().resume((Transaction)suspendedTransaction);
      } catch (InvalidTransactionException var7) {
         if (!this.weblogicTransactionManagerAvailable) {
            throw var7;
         }

         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Standard JTA resume threw InvalidTransactionException: " + var7.getMessage() + " - trying WebLogic JTA forceResume");
         }

         try {
            Assert.state(this.forceResumeMethod != null, "WebLogic JTA API not initialized");
            this.forceResumeMethod.invoke(this.getTransactionManager(), suspendedTransaction);
         } catch (InvocationTargetException var5) {
            throw new TransactionSystemException("WebLogic's TransactionManager.forceResume(Transaction) method failed", var5.getTargetException());
         } catch (Exception var6) {
            throw new TransactionSystemException("Could not access WebLogic's TransactionManager.forceResume(Transaction) method", var6);
         }
      }

   }

   public Transaction createTransaction(@Nullable String name, int timeout) throws NotSupportedException, SystemException {
      if (this.weblogicUserTransactionAvailable && name != null) {
         try {
            if (timeout >= 0) {
               Assert.state(this.beginWithNameAndTimeoutMethod != null, "WebLogic JTA API not initialized");
               this.beginWithNameAndTimeoutMethod.invoke(this.getUserTransaction(), name, timeout);
            } else {
               Assert.state(this.beginWithNameMethod != null, "WebLogic JTA API not initialized");
               this.beginWithNameMethod.invoke(this.getUserTransaction(), name);
            }
         } catch (InvocationTargetException var4) {
            if (var4.getTargetException() instanceof NotSupportedException) {
               throw (NotSupportedException)var4.getTargetException();
            }

            if (var4.getTargetException() instanceof SystemException) {
               throw (SystemException)var4.getTargetException();
            }

            if (var4.getTargetException() instanceof RuntimeException) {
               throw (RuntimeException)var4.getTargetException();
            }

            throw new SystemException("WebLogic's begin() method failed with an unexpected error: " + var4.getTargetException());
         } catch (Exception var5) {
            throw new SystemException("Could not invoke WebLogic's UserTransaction.begin() method: " + var5);
         }

         return new ManagedTransactionAdapter(this.obtainTransactionManager());
      } else {
         return super.createTransaction(name, timeout);
      }
   }
}
