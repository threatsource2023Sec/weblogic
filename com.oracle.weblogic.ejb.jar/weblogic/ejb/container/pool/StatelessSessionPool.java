package weblogic.ejb.container.pool;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.internal.StatelessEJBHome;
import weblogic.ejb.container.internal.StatelessEJBLocalHome;
import weblogic.ejb.container.manager.StatelessManager;
import weblogic.management.runtime.EJBPoolRuntimeMBean;

public final class StatelessSessionPool extends Pool {
   private final StatelessEJBHome remoteHome;
   private final StatelessEJBLocalHome localHome;
   private final StatelessManager beanManager;
   private final AtomicInteger currentSize = new AtomicInteger(0);
   private final Lock sizeLock = new ReentrantLock();
   private final Condition notEmpty;

   public StatelessSessionPool(StatelessEJBHome remoteHome, StatelessEJBLocalHome localHome, StatelessManager beanManager, BeanInfo info, EJBPoolRuntimeMBean mbean) {
      super(beanManager, info, mbean);
      this.notEmpty = this.sizeLock.newCondition();
      this.beanClass = ((SessionBeanInfo)this.beanInfo).getGeneratedBeanClass();
      this.remoteHome = remoteHome;
      this.localHome = localHome;
      this.beanManager = beanManager;
   }

   public Object getBean() {
      throw new AssertionError("StatelessSessionPool.getBean() must be called with mustWait=true");
   }

   public Object getBean(long txTimeoutMS) throws InternalException {
      Object result = null;
      if (this.getMaxBeansInFreePool() > 0) {
         result = super.getBean();
         if (null == result) {
            try {
               this.sizeLock.lock();
               if (this.getMaxBeansInFreePool() <= this.currentSize.get()) {
                  result = super.getBean();
                  if (null == result) {
                     result = this.waitForBean(txTimeoutMS);
                  }
               }

               if (null == result) {
                  this.incrementCurrentSize();
               }
            } finally {
               this.sizeLock.unlock();
            }
         }
      } else {
         this.incrementCurrentSize();
      }

      if (null == result) {
         try {
            result = this.createBean();
            if (debugLogger.isDebugEnabled()) {
               debug("allocate new: '" + result + "'");
            }
         } finally {
            if (result == null) {
               this.currentSize.decrementAndGet();
            }

         }
      }

      this.getPoolRuntime().incrementBeansInUseCount();
      return result;
   }

   protected void removeBean(Object bean) {
      this.currentSize.decrementAndGet();

      try {
         this.beanManager.doEjbRemove(bean);
      } catch (RemoteException var3) {
         EJBLogger.logExceptionDuringEJBRemove(var3);
      }

   }

   protected Object createBean() throws InternalException {
      EJBObject eo = null;
      EJBLocalObject elo = null;
      if (this.remoteHome != null) {
         eo = this.remoteHome.allocateEO();
      }

      if (this.localHome != null) {
         elo = this.localHome.allocateELO();
      }

      return this.beanManager.createBean(eo, elo);
   }

   public void destroyBean(Object eb) {
      if (debugLogger.isDebugEnabled()) {
         debug("Destroying a bean in: '" + this + "'");
      }

      this.currentSize.decrementAndGet();
      super.destroyBean(eb);

      try {
         this.sizeLock.lock();
         this.notEmpty.signal();
      } finally {
         this.sizeLock.unlock();
      }

   }

   public void releaseBean(Object bean) {
      try {
         this.sizeLock.lock();
         super.releaseBean(bean);
         this.notEmpty.signal();
      } finally {
         this.sizeLock.unlock();
      }

   }

   private Object waitForBean(long timeoutms) throws InternalException {
      if (debugLogger.isDebugEnabled()) {
         debug("Waiting for an instance in pool: '" + this + "'");
      }

      this.getPoolRuntime().incrementWaiterCount();
      long maxTime = System.currentTimeMillis() + timeoutms;

      Object result;
      do {
         try {
            this.notEmpty.await(maxTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
         } catch (InterruptedException var6) {
         }

         if (System.currentTimeMillis() >= maxTime) {
            this.getPoolRuntime().decrementWaiterCount();
            this.getPoolRuntime().incrementTotalTimeoutCount();
            throw new RuntimeException("An invocation of EJB " + this.beanInfo.getDisplayName() + " timed out while waiting to get an instance from the free pool.");
         }

         result = super.getBean();
      } while(null == result && this.getMaxBeansInFreePool() <= this.currentSize.get());

      this.getPoolRuntime().decrementWaiterCount();
      return result;
   }

   public void reset() {
      this.beanClass = ((SessionBeanInfo)this.beanInfo).getGeneratedBeanClass();
      this.cleanup();
   }

   public void updateMaxBeansInFreePool(int max) {
      List toRemove = super.resizePool(max);
      Iterator var3 = toRemove.iterator();

      while(var3.hasNext()) {
         Object bean = var3.next();
         this.removeBean(bean);
      }

   }

   protected void incrementCurrentSize() {
      this.currentSize.incrementAndGet();
   }

   private static void debug(String s) {
      debugLogger.debug("[StatelessSessionPool] " + s);
   }
}
