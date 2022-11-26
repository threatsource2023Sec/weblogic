package weblogic.transaction.cdi;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.InvocationContext;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.internal.TXLogger;
import weblogic.transaction.internal.TransactionManagerImpl;
import weblogic.transaction.internal.TxDebug;

public class TransactionalInterceptorBase implements Serializable {
   private static TransactionManager testTransactionManager;
   private static volatile TransactionManager transactionManager;

   public TransactionManager getTransactionManager() {
      if (testTransactionManager != null) {
         return testTransactionManager;
      } else {
         if (transactionManager == null) {
            try {
               Class var1 = TransactionalInterceptorBase.class;
               synchronized(TransactionalInterceptorBase.class) {
                  if (transactionManager == null) {
                     transactionManager = TransactionHelper.getTransactionHelper().getTransactionManager();
                  }
               }
            } catch (Exception var4) {
               TXLogger.logNamingExceptionOnTMLookup(var4);
               throw new RuntimeException("Unable to obtain TransactionManager for Transactional Interceptor", var4);
            }
         }

         return transactionManager;
      }
   }

   static void setTestTransactionManager(TransactionManager transactionManager) {
      testTransactionManager = transactionManager;
   }

   boolean isLifeCycleMethod(InvocationContext ctx) {
      return ctx.getMethod().getAnnotation(PostConstruct.class) != null || ctx.getMethod().getAnnotation(PreDestroy.class) != null;
   }

   public Object proceed(InvocationContext ctx) throws Exception {
      Transactional transactionalAnnotation = (Transactional)ctx.getMethod().getAnnotation(Transactional.class);
      Class[] rollbackOn = null;
      Class[] dontRollbackOn = null;
      if (transactionalAnnotation != null) {
         rollbackOn = transactionalAnnotation.rollbackOn();
         dontRollbackOn = transactionalAnnotation.dontRollbackOn();
      } else {
         Class targetClass = ctx.getTarget().getClass();
         transactionalAnnotation = (Transactional)targetClass.getAnnotation(Transactional.class);
         if (transactionalAnnotation != null) {
            rollbackOn = transactionalAnnotation.rollbackOn();
            dontRollbackOn = transactionalAnnotation.dontRollbackOn();
         }
      }

      Class dontRollbackOnClass;
      Class rollbackOnClass;
      try {
         Object object = ctx.proceed();
         return object;
      } catch (RuntimeException var9) {
         if (TxDebug.JTACDI.isDebugEnabled()) {
            TxDebug.JTACDI.debug(this + "Error during transaction processing", var9);
         }

         dontRollbackOnClass = this.getClassInArrayClosestToClassOrNull(dontRollbackOn, var9.getClass());
         if (dontRollbackOnClass == null) {
            this.markRollbackIfActiveTransaction();
            throw var9;
         } else if (!dontRollbackOnClass.equals(var9.getClass()) && !dontRollbackOnClass.isAssignableFrom(var9.getClass())) {
            rollbackOnClass = this.getClassInArrayClosestToClassOrNull(rollbackOn, var9.getClass());
            if (rollbackOnClass != null) {
               if (rollbackOnClass.isAssignableFrom(dontRollbackOnClass)) {
                  throw var9;
               }

               if (dontRollbackOnClass.isAssignableFrom(rollbackOnClass)) {
                  this.markRollbackIfActiveTransaction();
                  throw var9;
               }
            }

            this.markRollbackIfActiveTransaction();
            throw var9;
         } else {
            throw var9;
         }
      } catch (Exception var10) {
         if (TxDebug.JTACDI.isDebugEnabled()) {
            TxDebug.JTACDI.debug(this + "Error during transaction processing", var10);
         }

         dontRollbackOnClass = this.getClassInArrayClosestToClassOrNull(rollbackOn, var10.getClass());
         if (dontRollbackOnClass == null) {
            throw var10;
         } else {
            rollbackOnClass = this.getClassInArrayClosestToClassOrNull(dontRollbackOn, var10.getClass());
            if (rollbackOnClass != null) {
               if (dontRollbackOnClass.isAssignableFrom(rollbackOnClass)) {
                  throw var10;
               }

               if (rollbackOnClass.isAssignableFrom(dontRollbackOnClass)) {
                  this.markRollbackIfActiveTransaction();
                  throw var10;
               }
            }

            if (!dontRollbackOnClass.equals(var10.getClass()) && !dontRollbackOnClass.isAssignableFrom(var10.getClass())) {
               throw var10;
            } else {
               this.markRollbackIfActiveTransaction();
               throw var10;
            }
         }
      }
   }

   private Class getClassInArrayClosestToClassOrNull(Class[] exceptionArray, Class exception) {
      if (exceptionArray != null && exception != null) {
         Class closestMatch = null;
         Class[] var4 = exceptionArray;
         int var5 = exceptionArray.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class exceptionArrayElement = var4[var6];
            if (exceptionArrayElement.equals(exception)) {
               return exceptionArrayElement;
            }

            if (exceptionArrayElement.isAssignableFrom(exception) && (closestMatch == null || closestMatch.isAssignableFrom(exceptionArrayElement))) {
               closestMatch = exceptionArrayElement;
            }
         }

         return closestMatch;
      } else {
         return null;
      }
   }

   private void markRollbackIfActiveTransaction() throws SystemException {
      Transaction transaction = this.getTransactionManager().getTransaction();
      if (transaction != null) {
         if (TxDebug.JTACDI.isDebugEnabled()) {
            TxDebug.JTACDI.debug(this + "About to setRollbackOnly from @Transactional interceptor on transaction: " + transaction);
         }

         this.getTransactionManager().setRollbackOnly();
      }

   }

   void setTransactionalTransactionOperationsManger(boolean userTransactionMethodsAllowed) {
      if (testTransactionManager == null) {
         ((TransactionManagerImpl)this.getTransactionManager()).setCDIOnTxThreadProperty(userTransactionMethodsAllowed);
      }
   }

   void resetTransactionOperationsManager() {
      if (testTransactionManager == null) {
         ((TransactionManagerImpl)this.getTransactionManager()).setCDIOnTxThreadProperty(true);
      }
   }
}
