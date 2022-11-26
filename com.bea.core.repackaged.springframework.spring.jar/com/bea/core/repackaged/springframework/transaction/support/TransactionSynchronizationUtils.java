package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.scope.ScopedObject;
import com.bea.core.repackaged.springframework.core.InfrastructureProxy;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.Iterator;
import java.util.List;

public abstract class TransactionSynchronizationUtils {
   private static final Log logger = LogFactory.getLog(TransactionSynchronizationUtils.class);
   private static final boolean aopAvailable = ClassUtils.isPresent("com.bea.core.repackaged.springframework.aop.scope.ScopedObject", TransactionSynchronizationUtils.class.getClassLoader());

   public static boolean sameResourceFactory(ResourceTransactionManager tm, Object resourceFactory) {
      return unwrapResourceIfNecessary(tm.getResourceFactory()).equals(unwrapResourceIfNecessary(resourceFactory));
   }

   static Object unwrapResourceIfNecessary(Object resource) {
      Assert.notNull(resource, "Resource must not be null");
      Object resourceRef = resource;
      if (resource instanceof InfrastructureProxy) {
         resourceRef = ((InfrastructureProxy)resource).getWrappedObject();
      }

      if (aopAvailable) {
         resourceRef = TransactionSynchronizationUtils.ScopedProxyUnwrapper.unwrapIfNecessary(resourceRef);
      }

      return resourceRef;
   }

   public static void triggerFlush() {
      Iterator var0 = TransactionSynchronizationManager.getSynchronizations().iterator();

      while(var0.hasNext()) {
         TransactionSynchronization synchronization = (TransactionSynchronization)var0.next();
         synchronization.flush();
      }

   }

   public static void triggerBeforeCommit(boolean readOnly) {
      Iterator var1 = TransactionSynchronizationManager.getSynchronizations().iterator();

      while(var1.hasNext()) {
         TransactionSynchronization synchronization = (TransactionSynchronization)var1.next();
         synchronization.beforeCommit(readOnly);
      }

   }

   public static void triggerBeforeCompletion() {
      Iterator var0 = TransactionSynchronizationManager.getSynchronizations().iterator();

      while(var0.hasNext()) {
         TransactionSynchronization synchronization = (TransactionSynchronization)var0.next();

         try {
            synchronization.beforeCompletion();
         } catch (Throwable var3) {
            logger.error("TransactionSynchronization.beforeCompletion threw exception", var3);
         }
      }

   }

   public static void triggerAfterCommit() {
      invokeAfterCommit(TransactionSynchronizationManager.getSynchronizations());
   }

   public static void invokeAfterCommit(@Nullable List synchronizations) {
      if (synchronizations != null) {
         Iterator var1 = synchronizations.iterator();

         while(var1.hasNext()) {
            TransactionSynchronization synchronization = (TransactionSynchronization)var1.next();
            synchronization.afterCommit();
         }
      }

   }

   public static void triggerAfterCompletion(int completionStatus) {
      List synchronizations = TransactionSynchronizationManager.getSynchronizations();
      invokeAfterCompletion(synchronizations, completionStatus);
   }

   public static void invokeAfterCompletion(@Nullable List synchronizations, int completionStatus) {
      if (synchronizations != null) {
         Iterator var2 = synchronizations.iterator();

         while(var2.hasNext()) {
            TransactionSynchronization synchronization = (TransactionSynchronization)var2.next();

            try {
               synchronization.afterCompletion(completionStatus);
            } catch (Throwable var5) {
               logger.error("TransactionSynchronization.afterCompletion threw exception", var5);
            }
         }
      }

   }

   private static class ScopedProxyUnwrapper {
      public static Object unwrapIfNecessary(Object resource) {
         return resource instanceof ScopedObject ? ((ScopedObject)resource).getTargetObject() : resource;
      }
   }
}
