package weblogic.ejb.container.pool;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.manager.MessageDrivenManager;
import weblogic.management.runtime.EJBPoolRuntimeMBean;

public final class MessageDrivenPool extends Pool {
   private final MessageDrivenManager beanManager;
   private final AtomicInteger currentSize = new AtomicInteger(0);
   private final Lock sizeLock = new ReentrantLock();
   private final Condition notEmpty;

   public MessageDrivenPool(MessageDrivenManager beanManager, MessageDrivenBeanInfo info, EJBPoolRuntimeMBean mbean) {
      super(beanManager, info, mbean);
      this.notEmpty = this.sizeLock.newCondition();
      this.beanClass = info.getBeanClassToInstantiate();
      this.beanManager = beanManager;
      if (debugLogger.isDebugEnabled()) {
         debug("Created: " + this + " with initialSize: '" + this.initialSize + "', maximumSize: '" + this.maximumSize + "'");
      }

   }

   public Object getBean() throws InternalException {
      throw new AssertionError("MessageDrivenPool.getBean() must be called with mustWait=true");
   }

   public Object getBean(long txTimeoutMS) throws InternalException {
      Object result = null;
      if (this.getMaxBeansInFreePool() > 0) {
         result = super.getBean();
         if (null == result) {
            try {
               this.sizeLock.lock();
               if (this.getMaxBeansInFreePool() <= this.currentSize.get()) {
                  result = this.waitForBean(txTimeoutMS);
               }
            } finally {
               this.sizeLock.unlock();
            }
         }
      }

      if (null == result) {
         result = this.createBean();
         this.incrementCurrentSize();
         if (debugLogger.isDebugEnabled()) {
            debug("allocate new: '" + result + "'");
         }
      }

      this.getPoolRuntime().incrementBeansInUseCount();
      return result;
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
      } while(null == result);

      this.getPoolRuntime().decrementWaiterCount();
      return result;
   }

   protected void removeBean(Object bean) {
      this.currentSize.decrementAndGet();

      try {
         this.beanManager.doEjbRemove(bean);
      } catch (Exception var3) {
         EJBLogger.logExceptionDuringEJBRemove(var3);
      }

   }

   protected Object createBean() throws InternalException {
      return this.beanManager.createBean();
   }

   public void destroyBean(Object eb) {
      if (debugLogger.isDebugEnabled()) {
         debug("Destroying a bean in: '" + this + "'");
      }

      this.currentSize.decrementAndGet();
      super.destroyBean(eb);
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

   public void reset() {
      this.beanClass = ((MessageDrivenBeanInfo)this.beanInfo).getBeanClassToInstantiate();
      this.cleanup();
   }

   protected void incrementCurrentSize() {
      this.currentSize.incrementAndGet();
   }

   private static void debug(String s) {
      debugLogger.debug("[MessageDrivenPool] " + s);
   }
}
