package weblogic.ejb.container.monitoring;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.pool.Pool;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EJBPoolRuntimeMBean;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class EJBPoolRuntimeMBeanImpl extends RuntimeMBeanDelegate implements EJBPoolRuntimeMBean {
   private static final long serialVersionUID = 6012017468151169855L;
   private final AtomicInteger beansInUseCount = new AtomicInteger(0);
   private final AtomicInteger waiterCount = new AtomicInteger(0);
   private final AtomicLong destroyedTotalCount = new AtomicLong(0L);
   private final AtomicLong timeoutTotalCount = new AtomicLong(0L);
   private final BeanInfo bi;
   private Pool pool;

   public EJBPoolRuntimeMBeanImpl(String name, BeanInfo bi, EJBRuntimeMBean ejbRuntime) throws ManagementException {
      super(name, ejbRuntime, true, "PoolRuntime");
      this.bi = bi;
   }

   public void setPool(Pool pool) {
      this.pool = pool;
   }

   public void initializePool() {
      if (this.pool != null) {
         ManagedInvocationContext mic = this.bi.setCIC();
         Throwable var2 = null;

         try {
            this.pool.reInitializePool();
         } catch (Throwable var11) {
            var2 = var11;
            throw var11;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var10) {
                     var2.addSuppressed(var10);
                  }
               } else {
                  mic.close();
               }
            }

         }

      }
   }

   public int getIdleBeansCount() {
      if (this.pool == null) {
         return 0;
      } else {
         ManagedInvocationContext mic = this.bi.setCIC();
         Throwable var2 = null;

         int var3;
         try {
            var3 = this.pool.getFreeCount();
         } catch (Throwable var12) {
            var2 = var12;
            throw var12;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var11) {
                     var2.addSuppressed(var11);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var3;
      }
   }

   public int getPooledBeansCurrentCount() {
      if (this.pool == null) {
         return 0;
      } else {
         ManagedInvocationContext mic = this.bi.setCIC();
         Throwable var2 = null;

         int var3;
         try {
            var3 = this.pool.getFreeCount();
         } catch (Throwable var12) {
            var2 = var12;
            throw var12;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var11) {
                     var2.addSuppressed(var11);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var3;
      }
   }

   public long getAccessTotalCount() {
      if (this.pool == null) {
         return 0L;
      } else {
         ManagedInvocationContext mic = this.bi.setCIC();
         Throwable var2 = null;

         long var3;
         try {
            var3 = this.pool.getAccessCount();
         } catch (Throwable var13) {
            var2 = var13;
            throw var13;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var12) {
                     var2.addSuppressed(var12);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var3;
      }
   }

   public long getMissTotalCount() {
      if (this.pool == null) {
         return 0L;
      } else {
         ManagedInvocationContext mic = this.bi.setCIC();
         Throwable var2 = null;

         long var3;
         try {
            var3 = this.pool.getMissCount();
         } catch (Throwable var13) {
            var2 = var13;
            throw var13;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var12) {
                     var2.addSuppressed(var12);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var3;
      }
   }

   public int getBeansInUseCount() {
      return this.beansInUseCount.get();
   }

   public int getBeansInUseCurrentCount() {
      return this.beansInUseCount.get();
   }

   public void incrementBeansInUseCount() {
      this.beansInUseCount.incrementAndGet();
   }

   public void decrementBeansInUseCount() {
      this.beansInUseCount.decrementAndGet();
   }

   public long getDestroyedTotalCount() {
      return this.destroyedTotalCount.get();
   }

   public void incrementDestroyedTotalCount() {
      this.destroyedTotalCount.incrementAndGet();
   }

   public long getWaiterTotalCount() {
      return (long)this.waiterCount.get();
   }

   public int getWaiterCurrentCount() {
      return this.waiterCount.get();
   }

   public void incrementWaiterCount() {
      this.waiterCount.incrementAndGet();
   }

   public void decrementWaiterCount() {
      this.waiterCount.decrementAndGet();
   }

   public long getTimeoutTotalCount() {
      return this.timeoutTotalCount.get();
   }

   public void incrementTotalTimeoutCount() {
      this.timeoutTotalCount.incrementAndGet();
   }
}
