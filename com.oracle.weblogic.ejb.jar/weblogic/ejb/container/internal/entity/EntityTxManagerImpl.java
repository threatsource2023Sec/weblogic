package weblogic.ejb.container.internal.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.cache.EntityCache;
import weblogic.ejb.container.cache.TxPk;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.EntityTxManager;
import weblogic.ejb.container.interfaces.InvalidationBeanManager;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.TxManagerImpl;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.utils.PartialOrderSet;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.collections.ArrayMap;

public class EntityTxManagerImpl extends TxManagerImpl implements EntityTxManager {
   static final String MODIFIED_LISTENERS = "modifiedListeners";
   private static final String MODIFIED_BMP_LISTENERS = "modifiedBMPListeners";
   private static final String CURRENT_ITERATION_LISTENERS = "currentIterationListeners";
   private static final String ALREADY_OWNED = "alreadyOwned";
   private final boolean isBMP;
   private final InvalidationBeanManager invalidationTargetBeanManager;
   private final boolean doOptimisticInvalidation;

   public EntityTxManagerImpl(BaseEntityManager bem) {
      super(bem);
      EntityBeanInfo info = (EntityBeanInfo)bem.getBeanInfo();
      this.doOptimisticInvalidation = info.isOptimistic() && info.getCacheBetweenTransactions();
      this.invalidationTargetBeanManager = info.getInvalidationTargetBeanManager();
      this.instanceLockOrder = info.getInstanceLockOrder();
      this.isBMP = bem.isBeanManagedPersistence();
   }

   protected void addModifiedKey(TxManagerImpl.TxListener l, Object pk) {
      if (this.isBMP) {
         ((EntityTxListener)l).addModifiedKey(pk);
      }

   }

   public PartialOrderSet getEnrolledKeys(Transaction tx) {
      if (!this.hasListener(tx)) {
         return null;
      } else {
         try {
            return this.getListener(tx).getPrimaryKeys();
         } catch (Exception var3) {
            return null;
         }
      }
   }

   public List getNotModifiedOtherTxKeys(Transaction excludeTx) {
      List l = new ArrayList();
      Iterator var3 = this.listeners.keySet().iterator();

      while(var3.hasNext()) {
         Transaction tx = (Transaction)var3.next();
         if (tx != null && tx != excludeTx) {
            EntityTxListener listener = (EntityTxListener)this.listeners.get(tx);
            if (listener != null) {
               l.addAll(listener.getNotModifiedKeys());
            }
         }
      }

      return l;
   }

   public boolean isFlushPending(Transaction tx, Object pk) {
      if (!this.hasListener(tx)) {
         return false;
      } else {
         try {
            return ((EntityTxListener)this.getListener(tx)).isFlushPending(pk);
         } catch (Exception var4) {
            return false;
         }
      }
   }

   public boolean needsToBeInserted(Transaction tx, Object pk) throws SystemException, RollbackException {
      return ((EntityTxListener)this.getListener(tx)).needsToBeInserted(pk);
   }

   public List getFlushedKeys(Transaction tx) {
      if (!this.hasListener(tx)) {
         return null;
      } else {
         try {
            return ((EntityTxListener)this.getListener(tx)).getFlushedKeys();
         } catch (Exception var3) {
            return null;
         }
      }
   }

   public void executeUpdateOperations(Transaction tx, Set finishedBeanManagerSet, boolean isFlushModified, boolean internalFlush) throws InternalException {
      EntityTxListener l = this.getExistingListener(tx);
      if (l != null) {
         l.executeUpdate(finishedBeanManagerSet, isFlushModified, internalFlush);
      }

   }

   public void executeDeleteOperations(Transaction tx, Set finishedBeanManagerSet, boolean isFlushModified, boolean internalFlush) throws InternalException {
      EntityTxListener l = this.getExistingListener(tx);
      if (l != null) {
         l.executeDelete(finishedBeanManagerSet, isFlushModified, internalFlush);
      }

   }

   public void executeInsertOperations(Transaction tx, Set finishedBeanManagerSet, boolean isFlushModified, boolean internalFlush) throws InternalException {
      EntityTxListener l = this.getExistingListener(tx);
      if (l != null) {
         l.executeInsert(finishedBeanManagerSet, isFlushModified, internalFlush);
      }

   }

   public void registerInsertBean(Object pk, Transaction tx) throws InternalException {
      try {
         if (!this.handleRollback(tx)) {
            EntityTxListener l = (EntityTxListener)this.getListener(tx);
            l.addPrimaryKey(pk);
            l.addInsertKey(pk);
         }
      } catch (Exception var4) {
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", var4);
      }

   }

   public boolean registerDeleteBean(Object pk, Transaction tx) throws InternalException {
      try {
         return this.handleRollback(tx) ? false : ((EntityTxListener)this.getListener(tx)).addDeleteKey(pk);
      } catch (Exception var4) {
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", var4);
         return false;
      }
   }

   public void registerInsertDeletedBean(Object pk, Transaction tx) throws InternalException {
      try {
         if (!this.handleRollback(tx)) {
            ((EntityTxListener)this.getListener(tx)).addInsertDeletedKey(pk);
         }
      } catch (Exception var4) {
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", var4);
      }

   }

   public void registerM2NJoinTableInsert(Object pk, String cmrField, Transaction tx) throws InternalException {
      try {
         if (!this.handleRollback(tx)) {
            ((EntityTxListener)this.getListener(tx)).addM2NJoinTableInsertMap(pk, cmrField);
         }
      } catch (Exception var5) {
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", var5);
      }

   }

   public void registerModifiedBean(Object pk, Transaction tx) throws InternalException {
      try {
         if (!this.handleRollback(tx)) {
            ((EntityTxListener)this.getListener(tx)).addModifiedKey(pk);
         }
      } catch (Exception var4) {
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", var4);
      }

   }

   public void registerInvalidatedBean(Object pk, Transaction tx) throws InternalException {
      try {
         if (!this.handleRollback(tx)) {
            ((EntityTxListener)this.getListener(tx)).addInvalidationKey(pk);
         }
      } catch (Exception var4) {
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", var4);
      }

   }

   public void unregisterModifiedBean(Object pk, Transaction tx) throws InternalException {
      try {
         if (this.handleRollback(tx)) {
            return;
         }
      } catch (Exception var7) {
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", var7);
      }

      EntityTxListener l = (EntityTxListener)this.listeners.get(tx);
      if (l == null) {
         synchronized(this.listeners) {
            l = (EntityTxListener)this.listeners.get(tx);
            if (l == null) {
               throw new AssertionError("Fatal error: attempted to unregister an EJB 2.0 CMP bean that was not registered with a transaction.");
            }
         }
      }

      l.removeModifiedKey(pk);
   }

   public void flushModifiedBeans(Transaction tx) throws InternalException {
      this.flushModifiedBeans(tx, false);
   }

   public void flushModifiedBeans(Transaction tx, boolean internalFlush) throws InternalException {
      weblogic.transaction.Transaction wtx = (weblogic.transaction.Transaction)tx;
      Set modifiedListeners = (Set)wtx.getLocalProperty("modifiedListeners");
      Set bmpListeners = (Set)wtx.getLocalProperty("modifiedBMPListeners");
      Boolean alreadyOwned = (Boolean)wtx.getLocalProperty("alreadyOwned");
      boolean owner = false;
      if (alreadyOwned == null) {
         wtx.setLocalProperty("alreadyOwned", new Boolean(true));
         owner = true;
      } else {
         Set currentIterationListener = (Set)wtx.getLocalProperty("currentIterationListeners");
         if (modifiedListeners != null) {
            Set hs = new HashSet();
            hs.addAll(currentIterationListener);
            hs.addAll((Collection)modifiedListeners);
            modifiedListeners = hs;
         } else {
            modifiedListeners = currentIterationListener;
         }
      }

      while(modifiedListeners != null) {
         wtx.setLocalProperty("modifiedListeners", (Object)null);
         wtx.setLocalProperty("currentIterationListeners", modifiedListeners);
         if (internalFlush) {
            this.initializeFlushHistory((Set)modifiedListeners);
         }

         Iterator i = ((Set)modifiedListeners).iterator();

         while(i.hasNext()) {
            EntityTxListener listener = (EntityTxListener)i.next();
            listener.flushModifiedKeys(internalFlush);
            if (listener.isBMPListener()) {
               if (bmpListeners == null) {
                  bmpListeners = new HashSet();
                  wtx.setLocalProperty("modifiedBMPListeners", bmpListeners);
               }

               ((Set)bmpListeners).add(listener);
            }
         }

         modifiedListeners = (Set)wtx.getLocalProperty("modifiedListeners");
      }

      if (owner) {
         wtx.setLocalProperty("alreadyOwned", (Object)null);
         wtx.setLocalProperty("modifiedListeners", bmpListeners);
         wtx.setLocalProperty("modifiedBMPListeners", (Object)null);
         wtx.setLocalProperty("currentIterationListeners", (Object)null);
         owner = false;
      }

   }

   private void initializeFlushHistory(Set modifiedListeners) {
      if (modifiedListeners != null) {
         Iterator i = modifiedListeners.iterator();

         while(i.hasNext()) {
            EntityTxListener listener = (EntityTxListener)i.next();
            listener.initializeFlushHistory();
         }
      }

   }

   protected EntityTxListener getExistingListener(Transaction tx) throws InternalException {
      try {
         return this.handleRollback(tx) ? null : (EntityTxListener)this.listeners.get(tx);
      } catch (SystemException var3) {
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", var3);
         throw new AssertionError();
      }
   }

   protected TxManagerImpl.TxListener createTxListener(Transaction tx) {
      return new EntityTxListener(tx);
   }

   final class EntityTxListener extends TxManagerImpl.TxListener implements Synchronization {
      private static final boolean DEBUG = false;
      private final weblogic.transaction.Transaction wtx;
      private List modifiedKeys = Collections.synchronizedList(new LinkedList());
      private final List insertKeys = Collections.synchronizedList(new LinkedList());
      private final List deleteKeys = Collections.synchronizedList(new LinkedList());
      private final List invalidationKeys;
      private List flushedModifiedKeys = new ArrayList();
      private List flushedInsertKeys = null;
      private List flushedDeleteKeys = null;
      private List flushedKeys = null;
      private ArrayMap m2nJoinTableInsertMap = null;
      private EntityCache entityCache = null;
      private boolean needsFlushModified = false;
      private boolean isJtaCallback = true;

      EntityTxListener(Transaction transaction) {
         super(transaction);
         this.wtx = (weblogic.transaction.Transaction)transaction;
         if (EntityTxManagerImpl.this.invalidationTargetBeanManager == null && !EntityTxManagerImpl.this.doOptimisticInvalidation) {
            this.invalidationKeys = null;
         } else {
            this.invalidationKeys = new LinkedList();
         }

      }

      boolean isBMPListener() {
         return EntityTxManagerImpl.this.isBMP;
      }

      public List getNotModifiedKeys() {
         List l = new ArrayList();
         Iterator it = this.getPrimaryKeys().iterator();

         while(it.hasNext()) {
            Object pk = it.next();
            if (!this.isFlushPending(pk)) {
               l.add(new TxPk(this.tx, pk));
            }
         }

         return l;
      }

      private void enrollAsModifiedListener() {
         this.needsFlushModified = false;
         Set modifiedListeners = (Set)this.wtx.getLocalProperty("modifiedListeners");
         if (modifiedListeners == null) {
            modifiedListeners = new HashSet();
            this.wtx.setLocalProperty("modifiedListeners", modifiedListeners);
         }

         ((Set)modifiedListeners).add(this);
      }

      public boolean isFlushPending(Object pk) {
         return this.modifiedKeys.contains(pk) || this.insertKeys.contains(pk) || this.deleteKeys.contains(pk);
      }

      public void addModifiedKey(Object pk) {
         if (this.modifiedKeys.isEmpty()) {
            this.enrollAsModifiedListener();
         }

         if (pk != null) {
            this.modifiedKeys.add(pk);
         }

         if (this.invalidationKeys != null) {
            this.invalidationKeys.add(pk);
         }

      }

      public void addInvalidationKey(Object pk) {
         if (this.invalidationKeys != null) {
            this.invalidationKeys.add(pk);
         }

      }

      public void removeModifiedKey(Object pk) {
         this.modifiedKeys.remove(pk);
         if (this.modifiedKeys.isEmpty() && this.insertKeys.isEmpty() && this.deleteKeys.isEmpty()) {
            this.needsFlushModified = true;
            Set modifiedListeners = (Set)this.wtx.getLocalProperty("modifiedListeners");
            if (modifiedListeners != null) {
               modifiedListeners.remove(this);
            }
         }

      }

      public void addInsertKey(Object pk) {
         if (this.insertKeys.isEmpty()) {
            this.enrollAsModifiedListener();
         }

         this.insertKeys.add(pk);
      }

      public boolean needsToBeInserted(Object pk) {
         return this.insertKeys.contains(pk);
      }

      public boolean addDeleteKey(Object pk) {
         if (this.insertKeys.remove(pk)) {
            this.removeModifiedKey(pk);
            return false;
         } else {
            if (this.deleteKeys.isEmpty()) {
               this.enrollAsModifiedListener();
            }

            this.deleteKeys.add(pk);
            if (this.invalidationKeys != null) {
               this.invalidationKeys.add(pk);
            }

            return true;
         }
      }

      public void addInsertDeletedKey(Object pk) {
         if (this.deleteKeys.remove(pk) && !this.modifiedKeys.contains(pk)) {
            this.addModifiedKey(pk);
         }

      }

      public void addM2NJoinTableInsertMap(Object key, String cmrField) {
         if (this.m2nJoinTableInsertMap == null) {
            this.m2nJoinTableInsertMap = new ArrayMap();
         }

         List l = (List)this.m2nJoinTableInsertMap.get(cmrField);
         if (l == null) {
            l = new ArrayList();
            this.m2nJoinTableInsertMap.put(cmrField, l);
         }

         if (!((List)l).contains(key)) {
            ((List)l).add(key);
         }

      }

      public List getFlushedKeys() {
         if (this.flushedModifiedKeys == null && this.flushedInsertKeys == null && this.flushedDeleteKeys == null) {
            return null;
         } else {
            if (this.flushedKeys == null) {
               this.flushedKeys = new ArrayList();
            } else {
               this.flushedKeys.clear();
            }

            if (this.flushedModifiedKeys != null) {
               this.flushedKeys.addAll(this.flushedModifiedKeys);
            }

            if (this.flushedInsertKeys != null) {
               this.flushedKeys.addAll(this.flushedInsertKeys);
            }

            if (this.flushedDeleteKeys != null) {
               this.flushedKeys.addAll(this.flushedDeleteKeys);
            }

            return this.flushedKeys;
         }
      }

      public void executeDBOperations(boolean isFlushModified, boolean internalFlush) throws InternalException {
         Set finishedBeanManagerSet = new HashSet();
         this.executeInsert(finishedBeanManagerSet, isFlushModified, internalFlush);
         finishedBeanManagerSet.clear();
         this.executeUpdate(finishedBeanManagerSet, isFlushModified, internalFlush);
         finishedBeanManagerSet.clear();
         this.executeDelete(finishedBeanManagerSet, isFlushModified, internalFlush);
         this.executeM2NJoinTableInserts(internalFlush);
      }

      public void executeDelete(Set finishedBeanManagerSet, boolean isFlushModified, boolean internalFlush) throws InternalException {
         ((BaseEntityManager)EntityTxManagerImpl.this.beanManager).executeDeleteStmt(this.deleteKeys, this.tx, finishedBeanManagerSet, isFlushModified, internalFlush, this.flushedDeleteKeys);
         this.deleteKeys.clear();
         if (!isFlushModified) {
            Set modifiedListeners = (Set)this.wtx.getLocalProperty("modifiedListeners");
            if (modifiedListeners != null) {
               modifiedListeners.remove(this);
            }
         }

      }

      public void executeUpdate(Set finishedBeanManagerSet, boolean isFlushModified, boolean internalFlush) throws InternalException {
         ((BaseEntityManager)EntityTxManagerImpl.this.beanManager).executeUpdateStmt(this.modifiedKeys, this.tx, finishedBeanManagerSet, isFlushModified, internalFlush, this.flushedModifiedKeys);
         this.modifiedKeys.clear();
      }

      public void executeInsert(Set finishedBeanManagerSet, boolean isFlushModified, boolean internalFlush) throws InternalException {
         if (isFlushModified) {
            this.flushModifiedKeys(internalFlush);
         } else {
            this.isJtaCallback = false;
            this.beforeCompletion();
         }

         this.isJtaCallback = true;
         ((BaseEntityManager)EntityTxManagerImpl.this.beanManager).executeInsertStmt(this.insertKeys, this.tx, finishedBeanManagerSet, isFlushModified, internalFlush, this.flushedInsertKeys);
         this.insertKeys.clear();
      }

      public void executeM2NJoinTableInserts(boolean internalFlush) throws InternalException {
         if (this.m2nJoinTableInsertMap != null) {
            ((BaseEntityManager)EntityTxManagerImpl.this.beanManager).executeM2NJoinTableInserts(this.m2nJoinTableInsertMap, this.tx, internalFlush);
            this.m2nJoinTableInsertMap.clear();
         }
      }

      public void flushModifiedKeys(boolean internalFlush) throws InternalException {
         if (!this.needsFlushModified) {
            this.needsFlushModified = true;

            try {
               EntityTxManagerImpl.this.beanManager.pushEnvironment();
               List allModifiedKeys = this.modifiedKeys;

               for(boolean looping = false; !this.modifiedKeys.isEmpty(); looping = true) {
                  if (looping) {
                     allModifiedKeys.addAll(this.modifiedKeys);
                  }

                  List newKeys = Collections.synchronizedList(new LinkedList(this.modifiedKeys));
                  this.modifiedKeys = Collections.synchronizedList(new LinkedList());
                  ((BaseEntityManager)EntityTxManagerImpl.this.beanManager).flushModified(newKeys, this.tx, internalFlush, this.flushedModifiedKeys);
               }

               this.modifiedKeys = allModifiedKeys;
               this.needsFlushModified = true;
               if (((BaseEntityManager)EntityTxManagerImpl.this.beanManager).getOrderDatabaseOperations()) {
                  if (this.isJtaCallback) {
                     this.executeDBOperations(true, internalFlush);
                  }
               } else if (EntityTxManagerImpl.this.isBMP) {
                  this.needsFlushModified = false;
               } else {
                  this.modifiedKeys.clear();
               }
            } finally {
               EntityTxManagerImpl.this.beanManager.popEnvironment();
            }

         }
      }

      protected void initializeFlushHistory() {
         if (this.flushedModifiedKeys != null) {
            this.flushedModifiedKeys.clear();
         } else {
            this.flushedModifiedKeys = new ArrayList();
         }

         if (this.flushedInsertKeys != null) {
            this.flushedInsertKeys.clear();
         } else {
            this.flushedInsertKeys = new ArrayList();
         }

         if (this.flushedDeleteKeys != null) {
            this.flushedDeleteKeys.clear();
         } else {
            this.flushedDeleteKeys = new ArrayList();
         }

         if (this.flushedKeys != null) {
            this.flushedKeys.clear();
         } else {
            this.flushedKeys = new ArrayList();
         }

      }

      protected void inValidateAndAfterCompletion(int result) {
         if (this.invalidationKeys != null && !this.invalidationKeys.isEmpty() && result == 3) {
            if (EntityTxManagerImpl.this.doOptimisticInvalidation) {
               try {
                  ((InvalidationBeanManager)EntityTxManagerImpl.this.beanManager).invalidate(this.tx, (Collection)this.invalidationKeys);
               } catch (InternalException var8) {
                  EJBLogger.logExceptionDuringROInvalidation(EntityTxManagerImpl.this.beanManager.getDisplayName(), StackTraceUtils.throwable2StackTrace(var8));
               } finally {
                  EntityTxManagerImpl.this.beanManager.afterCompletion(this.getPrimaryKeys(), this.tx, result, this.entityCache);
               }
            } else {
               EntityTxManagerImpl.this.beanManager.afterCompletion(this.getPrimaryKeys(), this.tx, result, this.entityCache);

               try {
                  EntityTxManagerImpl.this.invalidationTargetBeanManager.invalidate((Object)null, (Collection)this.invalidationKeys);
               } catch (InternalException var7) {
                  EJBLogger.logExceptionDuringROInvalidation(EntityTxManagerImpl.this.beanManager.getDisplayName(), StackTraceUtils.throwable2StackTrace(var7));
               }
            }
         } else {
            EntityTxManagerImpl.this.beanManager.afterCompletion(this.getPrimaryKeys(), this.tx, result, this.entityCache);
         }

      }

      protected void beforeCompletionTail() throws InternalException {
         if (EntityTxManagerImpl.this.beanManager instanceof BaseEntityManager && ((BaseEntityManager)EntityTxManagerImpl.this.beanManager).getOrderDatabaseOperations() && this.isJtaCallback) {
            this.executeDBOperations(false, false);
         }

      }
   }
}
