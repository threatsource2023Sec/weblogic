package weblogic.ejb.container.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.TxManager;
import weblogic.ejb.container.manager.BaseEJBManager;
import weblogic.ejb.container.monitoring.EJBTransactionRuntimeMBeanImpl;
import weblogic.ejb.container.utils.PartialOrderSet;
import weblogic.ejb.spi.SessionBeanInfo;
import weblogic.utils.NestedRuntimeException;

public class TxManagerImpl implements TxManager {
   protected final ConcurrentMap listeners = new ConcurrentHashMap();
   protected final BaseEJBManager beanManager;
   private final BeanInfo beanInfo;
   private final EJBTransactionRuntimeMBeanImpl rtMBean;
   private final boolean beanTypeHasPKs;
   protected int instanceLockOrder = 100;
   private boolean isDead = false;

   public TxManagerImpl(BeanManager beanManager) {
      this.beanManager = (BaseEJBManager)beanManager;
      this.beanInfo = this.beanManager.getBeanInfo();
      this.rtMBean = (EJBTransactionRuntimeMBeanImpl)beanManager.getEJBRuntimeMBean().getTransactionRuntime();
      this.beanTypeHasPKs = this.beanInfo.isEntityBean() || this.beanInfo.isSessionBean() && ((SessionBeanInfo)this.beanInfo).isStateful();
   }

   public void registerSynchronization(Object pk, Transaction tx) throws InternalException {
      try {
         if (this.handleRollback(tx)) {
            return;
         }

         TxListener l = this.getListener(tx);
         if (this.beanTypeHasPKs) {
            l.addPrimaryKey(pk);
         }

         this.addModifiedKey(l, pk);
      } catch (Exception var4) {
         EJBRuntimeUtils.throwInternalException("EJB Exception: ", var4);
      }

   }

   protected void addModifiedKey(TxListener l, Object pk) {
   }

   public boolean hasListener(Transaction tx) {
      return this.listeners.get(tx) != null;
   }

   protected TxListener getListener(Transaction tx) throws RollbackException, SystemException {
      TxListener l = (TxListener)this.listeners.get(tx);
      if (l != null) {
         if (l.beforeCompletionInvoked) {
            tx.registerSynchronization(l);
            l.beforeCompletionInvoked = false;
         }

         return l;
      } else {
         if (this.isDead) {
            tx.rollback();
         }

         TxListener newListener = this.createTxListener(tx);
         l = (TxListener)this.listeners.putIfAbsent(tx, newListener);
         if (l != null) {
            return l;
         } else {
            try {
               tx.registerSynchronization(newListener);
               return newListener;
            } catch (SystemException | RollbackException | RuntimeException var5) {
               this.listeners.remove(tx);
               throw var5;
            }
         }
      }
   }

   protected TxListener createTxListener(Transaction tx) {
      return new TxListener(tx);
   }

   protected boolean handleRollback(Transaction tx) throws SystemException {
      if (this.isDead) {
         tx.rollback();
      }

      return this.isDead;
   }

   public void undeploy() {
      this.isDead = true;
      Iterator var1 = this.listeners.values().iterator();

      while(var1.hasNext()) {
         TxListener l = (TxListener)var1.next();

         try {
            l.tx.rollback();
         } catch (IllegalStateException var4) {
         } catch (SystemException var5) {
            EJBLogger.logIgnoreExcepOnRollback(this.beanInfo.getDisplayName(), var5);
         }
      }

   }

   protected class TxListener implements Synchronization {
      protected final Transaction tx;
      private PartialOrderSet primaryKeys;
      private final ClassLoader cl;
      private boolean beforeCompletionInvoked;

      protected TxListener(Transaction transaction) {
         this.tx = transaction;
         this.primaryKeys = new PartialOrderSet(TxManagerImpl.this.instanceLockOrder);
         this.beforeCompletionInvoked = false;
         this.cl = TxManagerImpl.this.beanInfo.getModuleClassLoader();
      }

      public void addPrimaryKey(Object pk) {
         this.primaryKeys.add(pk);
      }

      public PartialOrderSet getPrimaryKeys() {
         return this.primaryKeys;
      }

      public void afterCompletion(int result) {
         if (result == 3) {
            TxManagerImpl.this.rtMBean.incrementTransactionsCommitted();
         } else {
            TxManagerImpl.this.rtMBean.incrementTransactionsRolledBack();
            weblogic.transaction.Transaction wltx = (weblogic.transaction.Transaction)this.tx;
            if (wltx.isTimedOut()) {
               TxManagerImpl.this.rtMBean.incrementTransactionsTimedOut();
            }
         }

         if (!TxManagerImpl.this.beanTypeHasPKs) {
            TxManagerImpl.this.listeners.remove(this.tx);
         } else {
            Thread th = Thread.currentThread();
            ClassLoader clSave = th.getContextClassLoader();

            try {
               th.setContextClassLoader(this.cl);
               TxManagerImpl.this.beanManager.pushEnvironment();
               this.inValidateAndAfterCompletion(result);
            } finally {
               th.setContextClassLoader(clSave);
               TxManagerImpl.this.listeners.remove(this.tx);
               TxManagerImpl.this.beanManager.popEnvironment();
            }

         }
      }

      protected void inValidateAndAfterCompletion(int result) {
         TxManagerImpl.this.beanManager.afterCompletion(this.primaryKeys, this.tx, result, (Object)null);
      }

      public void beforeCompletion() {
         if (TxManagerImpl.this.beanTypeHasPKs) {
            Thread th = Thread.currentThread();
            ClassLoader clSave = th.getContextClassLoader();

            try {
               th.setContextClassLoader(this.cl);
               TxManagerImpl.this.beanManager.pushEnvironment();
               if (!this.beforeCompletionInvoked) {
                  boolean looping = false;
                  PartialOrderSet allKeys = this.primaryKeys;

                  try {
                     while(!this.primaryKeys.isEmpty()) {
                        if (looping) {
                           Iterator var5 = this.primaryKeys.iterator();

                           while(var5.hasNext()) {
                              Object obj = var5.next();
                              if (obj != null) {
                                 allKeys.add(obj);
                              }
                           }
                        }

                        Collection newKeys = this.primaryKeys;
                        this.primaryKeys = new PartialOrderSet(TxManagerImpl.this.instanceLockOrder);
                        TxManagerImpl.this.beanManager.beforeCompletion(newKeys, this.tx);
                        looping = true;
                     }
                  } finally {
                     this.primaryKeys = allKeys;
                     this.beforeCompletionInvoked = true;
                  }
               }

               this.beforeCompletionTail();
            } catch (InternalException var16) {
               if (var16.detail == null) {
                  throw new NestedRuntimeException("Error in beforeCompletion", var16);
               }

               if (var16.detail instanceof RuntimeException) {
                  throw (RuntimeException)var16.detail;
               }

               throw new NestedRuntimeException("Error in beforeCompletion", var16.detail);
            } finally {
               th.setContextClassLoader(clSave);
               TxManagerImpl.this.beanManager.popEnvironment();
            }

         }
      }

      protected void beforeCompletionTail() throws InternalException {
      }
   }
}
