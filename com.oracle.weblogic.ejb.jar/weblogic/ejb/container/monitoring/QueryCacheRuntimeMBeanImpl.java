package weblogic.ejb.container.monitoring;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.management.ManagementException;
import weblogic.management.runtime.QueryCacheRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class QueryCacheRuntimeMBeanImpl extends RuntimeMBeanDelegate implements QueryCacheRuntimeMBean {
   private final AtomicInteger totalCachedCount = new AtomicInteger(0);
   private final AtomicLong cacheAccessCount = new AtomicLong(0L);
   private final AtomicLong cacheHitCount = new AtomicLong(0L);
   private final AtomicLong totalCacheMissCount = new AtomicLong(0L);
   private final AtomicLong timedOutCacheMissCount = new AtomicLong(0L);
   private final AtomicLong beanEvictionCacheMissCount = new AtomicLong(0L);
   private final AtomicLong relatedQueryMissCacheMissCount = new AtomicLong(0L);
   private final AtomicLong dependentQueryMissCacheMissCount = new AtomicLong(0L);

   public QueryCacheRuntimeMBeanImpl(String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
   }

   public int getTotalCachedQueriesCount() {
      return this.totalCachedCount.get();
   }

   public long getCacheAccessCount() {
      return this.cacheAccessCount.get();
   }

   public long getCacheHitCount() {
      return this.cacheHitCount.get();
   }

   public long getTotalCacheMissCount() {
      return this.totalCacheMissCount.get();
   }

   public long getCacheMissByTimeoutCount() {
      return this.timedOutCacheMissCount.get();
   }

   public long getCacheMissByBeanEvictionCount() {
      return this.beanEvictionCacheMissCount.get();
   }

   public long getCacheMissByRelatedQueryMissCount() {
      return this.relatedQueryMissCacheMissCount.get();
   }

   public long getCacheMissByDependentQueryMissCount() {
      return this.dependentQueryMissCacheMissCount.get();
   }

   public void decrementTotalCachedQueriesCount() {
      this.totalCachedCount.decrementAndGet();
   }

   public void incrementTotalCachedQueriesCount() {
      this.totalCachedCount.incrementAndGet();
   }

   public void incrementCacheAccessCount() {
      this.cacheAccessCount.incrementAndGet();
   }

   public void incrementCacheHitCount() {
      this.cacheHitCount.incrementAndGet();
   }

   public void incrementCacheMissCount() {
      this.totalCacheMissCount.incrementAndGet();
   }

   public void incrementCacheMissByTimeoutCount() {
      this.timedOutCacheMissCount.incrementAndGet();
   }

   public void incrementCacheMissByBeanEvictionCount() {
      this.beanEvictionCacheMissCount.incrementAndGet();
      this.totalCacheMissCount.incrementAndGet();
   }

   public void incrementCacheMissByRelatedQueryMissCount() {
      this.relatedQueryMissCacheMissCount.incrementAndGet();
   }

   public void incrementCacheMissByDependentQueryMissCount() {
      this.dependentQueryMissCacheMissCount.incrementAndGet();
   }
}
