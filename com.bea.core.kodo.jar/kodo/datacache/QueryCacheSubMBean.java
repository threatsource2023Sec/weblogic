package kodo.datacache;

import com.solarmetric.manage.jmx.SubMBeanOperations;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import org.apache.openjpa.datacache.QueryCache;

public class QueryCacheSubMBean implements SubMBeanOperations {
   QueryCache _cache;
   String _prefix;

   public QueryCacheSubMBean(QueryCache cache, String prefix) {
      this._cache = cache;
      this._prefix = prefix;
   }

   public String getPrefix() {
      return this._prefix;
   }

   public Object getSub() {
      return this._cache;
   }

   public MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[]{new MBeanAttributeInfo("Hits", "int", "Number of cache hits.", true, false, false), new MBeanAttributeInfo("Misses", "int", "Number of cache misses.", true, false, false), new MBeanAttributeInfo("HitRate", "double", "Cache hit rate.", true, false, false), new MBeanAttributeInfo("CacheSize", "int", "Size of the LRU cache.", true, true, false), new MBeanAttributeInfo("SoftReferenceSize", "int", "Number of soft references held by cache.", true, true, false)};
   }

   public MBeanOperationInfo[] createMBeanOperationInfo() {
      return new MBeanOperationInfo[]{new MBeanOperationInfo("clear", "Clears cache", new MBeanParameterInfo[0], Void.class.getName(), 1), new MBeanOperationInfo("resetStatistics", "Resets cache statistics.", new MBeanParameterInfo[0], Void.class.getName(), 1)};
   }
}
