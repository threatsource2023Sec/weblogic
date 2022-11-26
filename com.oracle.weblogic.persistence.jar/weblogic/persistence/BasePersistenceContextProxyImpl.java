package weblogic.persistence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Synchronization;
import javax.transaction.Transaction;
import javax.transaction.TransactionSynchronizationRegistry;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.internal.InterpositionTier;
import weblogic.transaction.internal.ServerTransactionManagerImpl;

public abstract class BasePersistenceContextProxyImpl implements InterceptingInvocationHandler {
   protected static final boolean CICSCOPED_EM_DISABLED = Boolean.getBoolean("weblogic.persistence.DisableCICScopedEM");
   private final String appName;
   private final String moduleName;
   private final String persistenceUnitName;
   private final String unqualifiedPersistenceUnitName;
   private InvocationHandlerInterceptor iceptor;
   protected final BasePersistenceUnitInfo persistenceUnit;
   protected Method transactionAccessMethod;
   protected Method closeMethod;
   protected Set transactionalMethods = Collections.emptySet();
   protected final TransactionSynchronizationRegistry txRegistry;
   protected final TransactionHelper txHelper;

   public BasePersistenceContextProxyImpl(String appName, String moduleName, String unitName, PersistenceUnitRegistry reg) {
      this.persistenceUnit = (BasePersistenceUnitInfo)reg.getPersistenceUnit(unitName);
      this.unqualifiedPersistenceUnitName = unitName;
      this.persistenceUnitName = this.persistenceUnit.getPersistenceUnitId();
      this.appName = appName;
      this.moduleName = moduleName;
      this.txHelper = TransactionHelper.getTransactionHelper();
      this.txRegistry = (TransactionSynchronizationRegistry)this.txHelper.getTransactionManager();
   }

   public void setInterceptor(InvocationHandlerInterceptor ihi) {
      this.iceptor = ihi;
   }

   public void setTransactionAccessMethod(Method meth) {
      this.transactionAccessMethod = meth;
   }

   public void setCloseMethod(Method meth) {
      this.closeMethod = meth;
   }

   public void setTransactionalMethods(Set meths) {
      this.transactionalMethods = meths;
   }

   public String getAppName() {
      return this.appName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getUnitName() {
      return this.persistenceUnitName;
   }

   public String getUnqualifiedUnitName() {
      return this.unqualifiedPersistenceUnitName;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (this.iceptor != null) {
         this.iceptor.preInvoke(method, args);
      }

      Object result = this.invoke(proxy, method, args, this.txHelper.getTransaction());
      return this.iceptor != null ? this.iceptor.postInvoke(method, args, result) : result;
   }

   protected Object invoke(Object proxy, Method method, Object[] args, Transaction tx) throws Throwable {
      this.validateInvocation(method, tx);
      Object o = this.getPersistenceContext(tx);
      this.checkTransactionStatus(o, tx);

      Object var6;
      try {
         var6 = method.invoke(o, args);
      } catch (InvocationTargetException var10) {
         throw var10.getCause();
      } finally {
         if (tx == null) {
            if (CICSCOPED_EM_DISABLED) {
               this.perhapsClose(o, method);
            } else {
               this.perhapsClean(o, method);
            }
         }

      }

      return var6;
   }

   protected void checkTransactionStatus(Object pc, Transaction tx) {
   }

   protected void validateInvocation(Method method, Transaction tx) {
      if (method.equals(this.transactionAccessMethod)) {
         throw new IllegalStateException("The method " + method + " cannot be invoked in the context of a JTA " + this.getPersistenceContextStyleName() + ".");
      } else if (method.equals(this.closeMethod)) {
         throw new IllegalStateException("The method " + method + " cannot be invoked on a container-managed " + this.getPersistenceContextStyleName() + ".");
      } else if (tx == null && this.transactionalMethods.contains(method)) {
         throw new TransactionRequiredException("The method " + method + " must be called in the context of a transaction.");
      }
   }

   protected abstract String getPersistenceContextStyleName();

   protected Object getPersistenceContext(Transaction tx) {
      Object o;
      if (tx != null) {
         o = this.txRegistry.getResource(this.persistenceUnitName);
         if (o != null) {
            return o;
         }
      }

      o = this.newPersistenceContext(this.persistenceUnit);
      if (tx != null) {
         this.txRegistry.putResource(this.persistenceUnitName, o);
         ((ServerTransactionManagerImpl)this.txRegistry).registerInterposedSynchronization(new PersistenceContextCloser(o), InterpositionTier.WLS_INTERNAL_SYNCHRONIZATION);
      }

      return o;
   }

   protected abstract Object newPersistenceContext(BasePersistenceUnitInfo var1);

   protected abstract void perhapsClose(Object var1, Method var2);

   protected abstract void perhapsClean(Object var1, Method var2);

   protected final class PersistenceContextCloser implements Synchronization {
      private final Object pc;

      protected PersistenceContextCloser(Object pc) {
         this.pc = pc;
      }

      public void beforeCompletion() {
      }

      public void afterCompletion(int status) {
         BasePersistenceContextProxyImpl.this.perhapsClose(this.pc, (Method)null);
      }
   }
}
