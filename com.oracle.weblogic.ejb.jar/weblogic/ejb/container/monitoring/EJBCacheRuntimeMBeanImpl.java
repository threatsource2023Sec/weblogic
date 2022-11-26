package weblogic.ejb.container.monitoring;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.spi.EJBCache;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EJBCacheRuntimeMBean;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class EJBCacheRuntimeMBeanImpl extends RuntimeMBeanDelegate implements EJBCacheRuntimeMBean {
   private static final long serialVersionUID = -4664975769315082605L;
   private final AtomicInteger cachedBeansCurrentCount = new AtomicInteger(0);
   private final AtomicLong cacheAccessCount = new AtomicLong(0L);
   private final AtomicLong cacheHitCount = new AtomicLong(0L);
   private final AtomicLong activationCount = new AtomicLong(0L);
   private final AtomicLong passivationCount = new AtomicLong(0L);
   private final BeanInfo bi;
   private EJBCache cache;

   public EJBCacheRuntimeMBeanImpl(String name, BeanInfo bi, EJBRuntimeMBean ejbRuntime) throws ManagementException {
      super(name, ejbRuntime, true, "CacheRuntime");
      this.bi = bi;
   }

   public void setReInitializableCache(EJBCache c) {
      this.cache = c;
   }

   public void reInitializeCacheAndPools() {
      ManagedInvocationContext mic = this.bi.setCIC();
      Throwable var2 = null;

      try {
         this.cache.reInitializeCacheAndPools();
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

   public int getCachedBeansCurrentCount() {
      return this.cachedBeansCurrentCount.get();
   }

   public void incrementCachedBeansCurrentCount() {
      this.cachedBeansCurrentCount.incrementAndGet();
   }

   public void decrementCachedBeansCurrentCount() {
      this.cachedBeansCurrentCount.decrementAndGet();
   }

   public long getCacheAccessCount() {
      return this.cacheAccessCount.get();
   }

   public void incrementCacheAccessCount() {
      this.cacheAccessCount.incrementAndGet();
   }

   public long getCacheHitCount() {
      return this.cacheHitCount.get();
   }

   public long getCacheMissCount() {
      return this.cacheAccessCount.get() - this.cacheHitCount.get();
   }

   public void incrementCacheHitCount() {
      this.cacheHitCount.incrementAndGet();
   }

   public long getActivationCount() {
      return this.activationCount.get();
   }

   public void incrementActivationCount() {
      this.activationCount.incrementAndGet();
   }

   public long getPassivationCount() {
      return this.passivationCount.get();
   }

   public void incrementPassivationCount() {
      this.passivationCount.incrementAndGet();
   }
}
