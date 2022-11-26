package weblogic.kodo.monitoring;

import kodo.datacache.KodoDataCacheManager;
import kodo.datacache.MonitoringDataCache;
import weblogic.management.ManagementException;
import weblogic.management.runtime.KodoDataCacheRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class KodoDataCacheRuntimeMBeanImpl extends RuntimeMBeanDelegate implements KodoDataCacheRuntimeMBean {
   private MonitoringDataCache dataCache = null;
   private KodoDataCacheManager manager = null;

   public KodoDataCacheRuntimeMBeanImpl(String name, RuntimeMBean parent, KodoDataCacheManager manager, MonitoringDataCache cache) throws ManagementException {
      super(name, parent, true, "DataCacheRuntimes");
      this.dataCache = cache;
      this.manager = manager;
   }

   public void clear() {
      this.dataCache.clear();
   }

   public int getCacheHitCount() {
      return this.dataCache.getHits();
   }

   public double getCacheHitRatio() {
      return this.dataCache.getHitRate();
   }

   public int getCacheMissCount() {
      return this.dataCache.getMisses();
   }

   public int getTotalCurrentEntries() {
      return this.manager.size();
   }

   public String getStatistics() {
      return this.dataCache.getStatisticsString();
   }
}
