package kodo.datacache;

import com.solarmetric.manage.jmx.MultiMBean;
import com.solarmetric.manage.jmx.StatisticMBeanFactory;
import com.solarmetric.manage.jmx.SubMBean;
import java.util.ArrayList;
import kodo.util.MonitoringCacheMap;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.datacache.ConcurrentDataCache;
import org.apache.openjpa.datacache.ConcurrentQueryCache;
import org.apache.openjpa.util.CacheMap;

public class DataCacheMBeanFactory {
   public static MultiMBean createDataCacheMBean(MonitoringDataCache cache, OpenJPAConfiguration conf) {
      ArrayList subs = new ArrayList();
      subs.add(new DataCacheSubMBean(cache, ""));
      subs.addAll(StatisticMBeanFactory.createStatisticMBeans(cache, conf));
      CacheMap map = null;
      if (cache.getInnermostDelegate() instanceof LRUDataCache) {
         map = ((LRUDataCache)cache.getInnermostDelegate()).getCacheMap();
      } else if (cache.getInnermostDelegate() instanceof ConcurrentDataCache) {
         map = ((ConcurrentDataCache)cache.getInnermostDelegate()).getCacheMap();
      }

      if (map instanceof MonitoringCacheMap) {
         subs.addAll(StatisticMBeanFactory.createStatisticMBeans((MonitoringCacheMap)map, conf));
      }

      return new MultiMBean("Kodo Datastore Cache", (SubMBean[])((SubMBean[])subs.toArray(new SubMBean[subs.size()])));
   }

   public static MultiMBean createQueryCacheMBean(MonitoringQueryCache cache, OpenJPAConfiguration conf) {
      ArrayList subs = new ArrayList();
      subs.add(new QueryCacheSubMBean(cache, ""));
      subs.addAll(StatisticMBeanFactory.createStatisticMBeans(cache, conf));
      CacheMap map = null;
      if (cache.getInnermostDelegate() instanceof LRUQueryCache) {
         map = ((LRUQueryCache)cache.getInnermostDelegate()).getCacheMap();
      } else if (cache.getInnermostDelegate() instanceof ConcurrentQueryCache) {
         map = ((ConcurrentQueryCache)cache.getInnermostDelegate()).getCacheMap();
      }

      if (map instanceof MonitoringCacheMap) {
         subs.addAll(StatisticMBeanFactory.createStatisticMBeans((MonitoringCacheMap)map, conf));
      }

      return new MultiMBean("Kodo Query Cache", (SubMBean[])((SubMBean[])subs.toArray(new SubMBean[subs.size()])));
   }
}
