package weblogic.ejb.container.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.PoolIntf;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.monitoring.EJBPoolRuntimeMBeanImpl;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.runtime.EJBPoolRuntimeMBean;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.StackTraceUtils;

public abstract class Pool implements PoolIntf, TimerListener {
   protected static final DebugLogger debugLogger;
   private final BeanManager beanManager;
   protected final BeanInfo beanInfo;
   protected int initialSize;
   protected volatile int maximumSize;
   protected int idleTimeoutSeconds;
   protected Class beanClass;
   protected final String ejbName;
   private NewMonitoredPool pool;
   private Timer timer;
   private Lock poolLock;
   private final EJBPoolRuntimeMBeanImpl mbean;
   private boolean doneInitialCreation = false;

   public Pool(BeanManager beanManager, BeanInfo info, EJBPoolRuntimeMBean mb) {
      this.mbean = (EJBPoolRuntimeMBeanImpl)mb;
      this.initialSize = info.getCachingDescriptor().getInitialBeansInFreePool();
      this.maximumSize = info.getCachingDescriptor().getMaxBeansInFreePool();
      if (this.initialSize > this.maximumSize) {
         this.maximumSize = this.initialSize;
      }

      this.beanInfo = info;
      this.beanManager = beanManager;
      this.ejbName = info.getEJBName();
      this.idleTimeoutSeconds = info.getCachingDescriptor().getIdleTimeoutSecondsPool();
      this.createInitialPool();
      this.mbean.setPool(this);
   }

   public String toString() {
      return super.toString() + " - ejb name: '" + this.beanInfo.getEJBName() + "'";
   }

   protected EJBPoolRuntimeMBeanImpl getPoolRuntime() {
      return this.mbean;
   }

   private void createInitialPool() {
      this.poolLock = new ReentrantLock();
      this.pool = new NewMonitoredPool(new NewShrinkablePool(this.maximumSize, this.initialSize));
   }

   public int getInitialBeansInFreePool() {
      return this.initialSize;
   }

   public void setInitialBeansInFreePool(int m) {
      this.initialSize = m;
      if (debugLogger.isDebugEnabled()) {
         this.debug("setInitialBeansInFreePool(" + m + ")");
      }

      this.createInitialPool();
   }

   public int getMaxBeansInFreePool() {
      return this.maximumSize;
   }

   public void setMaxBeansInFreePool(int n) {
      this.maximumSize = n;
      if (debugLogger.isDebugEnabled()) {
         this.debug("setMaxBeansInFreePool(" + n + ")");
      }

   }

   public void createInitialBeans() throws WLDeploymentException {
      if (!this.doneInitialCreation) {
         if (debugLogger.isDebugEnabled()) {
            this.debug("Creating InitialBeans in pool: '" + this + "' initialSize: '" + this.initialSize + "', maximumSize: '" + this.maximumSize + "'");
         }

         try {
            for(int i = 0; i < this.initialSize; ++i) {
               this.pool.add(this.createBean());
               this.incrementCurrentSize();
            }

            this.doneInitialCreation = true;
         } catch (InternalException var3) {
            if (var3.detail != null) {
               throw new WLDeploymentException(var3.detail.getMessage() + StackTraceUtils.throwable2StackTrace(var3.detail));
            }

            throw new WLDeploymentException(var3.getMessage() + StackTraceUtils.throwable2StackTrace(var3));
         }
      }

      try {
         this.startIdleTimeout(0L);
      } catch (Exception var2) {
         EJBLogger.logErrorStartingFreepoolTimer(this.ejbName, var2.getMessage());
      }

   }

   protected abstract Object createBean() throws InternalException;

   protected abstract void removeBean(Object var1);

   protected void incrementCurrentSize() {
   }

   public void destroyBean(Object bean) {
      this.mbean.decrementBeansInUseCount();
      this.mbean.incrementDestroyedTotalCount();
   }

   public Object getBean() throws InternalException {
      Object bean = this.pool.remove();
      if (debugLogger.isDebugEnabled()) {
         this.debug("Returning bean from the pool: '" + bean + "'");
      }

      return bean;
   }

   public void releaseBean(Object bean) {
      this.mbean.decrementBeansInUseCount();
      boolean releaseIntoPool = false;
      if ((this.beanInfo.isEJB30() || bean != null && bean.getClass() == this.beanClass) && this.doneInitialCreation) {
         releaseIntoPool = this.pool.add(bean);
      }

      if (!releaseIntoPool) {
         this.removeBean(bean);
      }

   }

   public void updateMaxBeansInFreePool(int max) {
      Iterator var2 = this.resizePool(max).iterator();

      while(var2.hasNext()) {
         Object bean = var2.next();
         this.removeBean(bean);
      }

   }

   protected List resizePool(int size) {
      try {
         this.stopIdleTimeout();
      } catch (Exception var10) {
         EJBLogger.logErrorStoppingFreepoolTimer(this.ejbName, var10.getMessage());
      }

      new ArrayList();
      this.maximumSize = size;

      List toRemove;
      try {
         this.poolLock.lock();
         int oldMaximumSize = this.pool.getCapacity();
         this.pool.setCapacity(this.maximumSize);
         toRemove = this.pool.trim(oldMaximumSize - this.maximumSize);
      } finally {
         this.poolLock.unlock();
      }

      try {
         this.startIdleTimeout(0L);
      } catch (Exception var8) {
         EJBLogger.logErrorStartingFreepoolTimer(this.ejbName, var8.getMessage());
      }

      return toRemove;
   }

   public void cleanup() {
      try {
         this.stopIdleTimeout();
      } catch (Exception var7) {
         EJBLogger.logErrorStoppingFreepoolTimer(this.ejbName, var7.getMessage());
      }

      List beans = new ArrayList(this.maximumSize);

      while(!this.pool.isEmpty()) {
         beans.add(this.pool.remove());
      }

      this.pushEnvironment();

      try {
         Iterator var2 = beans.iterator();

         while(var2.hasNext()) {
            Object bean = var2.next();
            this.removeBean(bean);
         }
      } finally {
         this.popEnvironment();
      }

      this.doneInitialCreation = false;
   }

   protected void pushEnvironment() {
      EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());
   }

   protected void popEnvironment() {
      EJBRuntimeUtils.popEnvironment();
   }

   private void startIdleTimeout(long delay) {
      if (debugLogger.isDebugEnabled()) {
         this.debug(this.ejbName + ", entered startIdleTimeout timer with idleTimeoutSeconds = " + this.idleTimeoutSeconds);
      }

      if (this.idleTimeoutSeconds > 0) {
         if (debugLogger.isDebugEnabled()) {
            this.debug(this.ejbName + ", startIdleTimeout timer");
         }

         TimerManager tm = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
         this.timer = tm.scheduleAtFixedRate(this, delay, (long)(this.idleTimeoutSeconds * 1000));
      }
   }

   public void stopIdleTimeout() {
      if (this.idleTimeoutSeconds > 0) {
         if (debugLogger.isDebugEnabled()) {
            this.debug(this.ejbName + " stopping IdleTimeout timer ");
         }

         if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
         }

      }
   }

   public void updateIdleTimeoutSeconds(int seconds) {
      long delay = 0L;
      if (this.timer != null) {
         long nextTimeout = this.timer.getTimeout();
         this.stopIdleTimeout();
         delay = nextTimeout - System.currentTimeMillis();
         if (delay < 0L) {
            delay = 0L;
         }

         if ((long)(seconds * 1000) < delay) {
            delay = (long)(seconds * 1000);
         }
      }

      this.idleTimeoutSeconds = seconds;
      this.startIdleTimeout(delay);
   }

   public void timerExpired(Timer timer) {
      ManagedInvocationContext mic = this.beanInfo.setCIC();
      Throwable var3 = null;

      try {
         Collection c = null;

         try {
            this.poolLock.lock();
            c = this.pool.trim(true);
         } finally {
            this.poolLock.unlock();
         }

         if (c != null) {
            if (debugLogger.isDebugEnabled()) {
               this.debug(this.ejbName + " timerExpired: about to call remove on " + c.size() + " beans.");
            }

            Iterator var5 = c.iterator();

            while(var5.hasNext()) {
               Object bean = var5.next();
               this.removeBean(bean);
            }
         } else if (debugLogger.isDebugEnabled()) {
            this.debug(this.ejbName + " timerExpired: no beans to remove ");
         }
      } catch (Throwable var20) {
         var3 = var20;
         throw var20;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var18) {
                  var3.addSuppressed(var18);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void reInitializePool() {
      List c;
      try {
         this.poolLock.lock();
         c = this.pool.trim(false);
      } finally {
         this.poolLock.unlock();
      }

      if (c != null) {
         if (debugLogger.isDebugEnabled()) {
            this.debug(this.ejbName + " reInitializePool: about to call remove on " + c.size() + " beans.");
         }

         Iterator var2 = c.iterator();

         while(var2.hasNext()) {
            Object bean = var2.next();
            this.removeBean(bean);
         }
      } else if (debugLogger.isDebugEnabled()) {
         this.debug(this.ejbName + " reInitializePool: no beans to remove ");
      }

   }

   public int getFreeCount() {
      return this.pool.getFreeCount();
   }

   public long getAccessCount() {
      return this.pool.getAccessCount();
   }

   public long getMissCount() {
      return this.pool.getMissCount();
   }

   private void debug(String s) {
      debugLogger.debug("[Pool] " + this.ejbName + " - " + s);
   }

   static {
      debugLogger = EJBDebugService.poolingLogger;
   }
}
