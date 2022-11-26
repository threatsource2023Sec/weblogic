package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class TransactionSynchronizationManager {
   private static final Log logger = LogFactory.getLog(TransactionSynchronizationManager.class);
   private static final ThreadLocal resources = new NamedThreadLocal("Transactional resources");
   private static final ThreadLocal synchronizations = new NamedThreadLocal("Transaction synchronizations");
   private static final ThreadLocal currentTransactionName = new NamedThreadLocal("Current transaction name");
   private static final ThreadLocal currentTransactionReadOnly = new NamedThreadLocal("Current transaction read-only status");
   private static final ThreadLocal currentTransactionIsolationLevel = new NamedThreadLocal("Current transaction isolation level");
   private static final ThreadLocal actualTransactionActive = new NamedThreadLocal("Actual transaction active");

   public static Map getResourceMap() {
      Map map = (Map)resources.get();
      return map != null ? Collections.unmodifiableMap(map) : Collections.emptyMap();
   }

   public static boolean hasResource(Object key) {
      Object actualKey = TransactionSynchronizationUtils.unwrapResourceIfNecessary(key);
      Object value = doGetResource(actualKey);
      return value != null;
   }

   @Nullable
   public static Object getResource(Object key) {
      Object actualKey = TransactionSynchronizationUtils.unwrapResourceIfNecessary(key);
      Object value = doGetResource(actualKey);
      if (value != null && logger.isTraceEnabled()) {
         logger.trace("Retrieved value [" + value + "] for key [" + actualKey + "] bound to thread [" + Thread.currentThread().getName() + "]");
      }

      return value;
   }

   @Nullable
   private static Object doGetResource(Object actualKey) {
      Map map = (Map)resources.get();
      if (map == null) {
         return null;
      } else {
         Object value = map.get(actualKey);
         if (value instanceof ResourceHolder && ((ResourceHolder)value).isVoid()) {
            map.remove(actualKey);
            if (map.isEmpty()) {
               resources.remove();
            }

            value = null;
         }

         return value;
      }
   }

   public static void bindResource(Object key, Object value) throws IllegalStateException {
      Object actualKey = TransactionSynchronizationUtils.unwrapResourceIfNecessary(key);
      Assert.notNull(value, "Value must not be null");
      Map map = (Map)resources.get();
      if (map == null) {
         map = new HashMap();
         resources.set(map);
      }

      Object oldValue = ((Map)map).put(actualKey, value);
      if (oldValue instanceof ResourceHolder && ((ResourceHolder)oldValue).isVoid()) {
         oldValue = null;
      }

      if (oldValue != null) {
         throw new IllegalStateException("Already value [" + oldValue + "] for key [" + actualKey + "] bound to thread [" + Thread.currentThread().getName() + "]");
      } else {
         if (logger.isTraceEnabled()) {
            logger.trace("Bound value [" + value + "] for key [" + actualKey + "] to thread [" + Thread.currentThread().getName() + "]");
         }

      }
   }

   public static Object unbindResource(Object key) throws IllegalStateException {
      Object actualKey = TransactionSynchronizationUtils.unwrapResourceIfNecessary(key);
      Object value = doUnbindResource(actualKey);
      if (value == null) {
         throw new IllegalStateException("No value for key [" + actualKey + "] bound to thread [" + Thread.currentThread().getName() + "]");
      } else {
         return value;
      }
   }

   @Nullable
   public static Object unbindResourceIfPossible(Object key) {
      Object actualKey = TransactionSynchronizationUtils.unwrapResourceIfNecessary(key);
      return doUnbindResource(actualKey);
   }

   @Nullable
   private static Object doUnbindResource(Object actualKey) {
      Map map = (Map)resources.get();
      if (map == null) {
         return null;
      } else {
         Object value = map.remove(actualKey);
         if (map.isEmpty()) {
            resources.remove();
         }

         if (value instanceof ResourceHolder && ((ResourceHolder)value).isVoid()) {
            value = null;
         }

         if (value != null && logger.isTraceEnabled()) {
            logger.trace("Removed value [" + value + "] for key [" + actualKey + "] from thread [" + Thread.currentThread().getName() + "]");
         }

         return value;
      }
   }

   public static boolean isSynchronizationActive() {
      return synchronizations.get() != null;
   }

   public static void initSynchronization() throws IllegalStateException {
      if (isSynchronizationActive()) {
         throw new IllegalStateException("Cannot activate transaction synchronization - already active");
      } else {
         logger.trace("Initializing transaction synchronization");
         synchronizations.set(new LinkedHashSet());
      }
   }

   public static void registerSynchronization(TransactionSynchronization synchronization) throws IllegalStateException {
      Assert.notNull(synchronization, (String)"TransactionSynchronization must not be null");
      Set synchs = (Set)synchronizations.get();
      if (synchs == null) {
         throw new IllegalStateException("Transaction synchronization is not active");
      } else {
         synchs.add(synchronization);
      }
   }

   public static List getSynchronizations() throws IllegalStateException {
      Set synchs = (Set)synchronizations.get();
      if (synchs == null) {
         throw new IllegalStateException("Transaction synchronization is not active");
      } else if (synchs.isEmpty()) {
         return Collections.emptyList();
      } else {
         List sortedSynchs = new ArrayList(synchs);
         AnnotationAwareOrderComparator.sort((List)sortedSynchs);
         return Collections.unmodifiableList(sortedSynchs);
      }
   }

   public static void clearSynchronization() throws IllegalStateException {
      if (!isSynchronizationActive()) {
         throw new IllegalStateException("Cannot deactivate transaction synchronization - not active");
      } else {
         logger.trace("Clearing transaction synchronization");
         synchronizations.remove();
      }
   }

   public static void setCurrentTransactionName(@Nullable String name) {
      currentTransactionName.set(name);
   }

   @Nullable
   public static String getCurrentTransactionName() {
      return (String)currentTransactionName.get();
   }

   public static void setCurrentTransactionReadOnly(boolean readOnly) {
      currentTransactionReadOnly.set(readOnly ? Boolean.TRUE : null);
   }

   public static boolean isCurrentTransactionReadOnly() {
      return currentTransactionReadOnly.get() != null;
   }

   public static void setCurrentTransactionIsolationLevel(@Nullable Integer isolationLevel) {
      currentTransactionIsolationLevel.set(isolationLevel);
   }

   @Nullable
   public static Integer getCurrentTransactionIsolationLevel() {
      return (Integer)currentTransactionIsolationLevel.get();
   }

   public static void setActualTransactionActive(boolean active) {
      actualTransactionActive.set(active ? Boolean.TRUE : null);
   }

   public static boolean isActualTransactionActive() {
      return actualTransactionActive.get() != null;
   }

   public static void clear() {
      synchronizations.remove();
      currentTransactionName.remove();
      currentTransactionReadOnly.remove();
      currentTransactionIsolationLevel.remove();
      actualTransactionActive.remove();
   }
}
