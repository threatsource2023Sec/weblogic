package weblogic.kodo.monitoring;

import kodo.datacache.MonitoringQueryCache;
import weblogic.management.ManagementException;
import weblogic.management.runtime.KodoQueryCacheRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class KodoQueryCacheRuntimeMBeanImpl extends RuntimeMBeanDelegate implements KodoQueryCacheRuntimeMBean {
   private MonitoringQueryCache delegate;

   public KodoQueryCacheRuntimeMBeanImpl(String name, RuntimeMBean parent, MonitoringQueryCache cache) throws ManagementException {
      super(name, parent, true, "QueryCacheRuntimes");
      this.delegate = cache;
   }

   public void clear() {
      this.delegate.clear();
   }

   public int getCacheHitCount() {
      return this.delegate.getHits();
   }

   public double getCacheHitRatio() {
      return this.delegate.getHitRate();
   }

   public int getCacheMissCount() {
      return this.delegate.getMisses();
   }

   public int getTotalCurrentEntries() {
      return 1;
   }

   public String getStatistics() {
      return this.delegate.getStatisticsString();
   }
}
