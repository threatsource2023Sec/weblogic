package kodo.datacache;

import com.solarmetric.manage.jmx.MBeanHelper;
import com.solarmetric.manage.jmx.MultiMBean;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.management.MBeanServer;
import kodo.manage.Management;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.datacache.DataCache;
import org.apache.openjpa.datacache.DataCacheManager;
import org.apache.openjpa.datacache.DataCachePCDataGenerator;
import org.apache.openjpa.datacache.DataCacheScheduler;
import org.apache.openjpa.datacache.QueryCache;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.ObjectValue;
import org.apache.openjpa.lib.conf.PluginListValue;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.UserException;

public class KodoDataCacheManager implements DataCacheManager {
   private static final Localizer _loc = Localizer.forPackage(KodoDataCacheManager.class);
   private OpenJPAConfiguration _conf = null;
   private ObjectValue _cacheValue = null;
   private DataCache _default = null;
   private DataCache _system = null;
   private QueryCache _queryCache = null;
   private final Map _caches = new ConcurrentHashMap();
   private DataCachePCDataGenerator _pcGenerator = null;
   private DataCacheScheduler _scheduler = null;
   private String _defDataCacheClass = null;
   private String _defDataCacheProps = null;

   public void initialize(OpenJPAConfiguration conf, ObjectValue dataCache, ObjectValue queryCache) {
      this._conf = conf;
      this._cacheValue = dataCache;
      this._system = new FederatingDataCache(conf, this);
      DataCache[] caches = this.instantiateDataCaches(conf, (PluginListValue)dataCache);
      if (caches != null && caches.length != 0) {
         if (conf.getDynamicDataStructs()) {
            this._pcGenerator = new DataCachePCDataGenerator(conf);
         }

         this._scheduler = new DataCacheScheduler(conf);

         for(int i = 0; i < caches.length; ++i) {
            this._caches.put(caches[i].getName(), this.initializeDataCache(caches[i]));
         }

         this._queryCache = (QueryCache)queryCache.instantiate(QueryCache.class, conf);
         if (this._queryCache != null) {
            this._queryCache = this.initializeQueryCache(this._queryCache);
         }

      }
   }

   private DataCache[] instantiateDataCaches(OpenJPAConfiguration conf, PluginListValue dataCaches) {
      DataCache[] caches = (DataCache[])((DataCache[])dataCaches.instantiate(DataCache.class, conf));
      if (caches.length == 0) {
         return caches;
      } else {
         String[] classNames = dataCaches.getClassNames();
         String[] props = dataCaches.getProperties();

         for(int i = 0; i < caches.length; ++i) {
            if ("default".equals(caches[i].getName())) {
               if (this._defDataCacheClass != null) {
                  throw new UserException(_loc.get("multi-default-cache", dataCaches.getString(), "default"));
               }

               this._defDataCacheClass = classNames[i];
               this._defDataCacheProps = props[i];
               this._default = caches[i];
            }
         }

         if (this._defDataCacheClass == null) {
            throw new UserException(_loc.get("no-default-cache", dataCaches.getString(), "default"));
         } else {
            return caches;
         }
      }
   }

   private DataCache initializeDataCache(DataCache cache) {
      cache.initialize(this);
      if (cache instanceof MonitoringDataCache) {
         return cache;
      } else {
         MonitoringDataCache mcache = new MonitoringDataCache(this._conf, cache);
         MBeanServer server = Management.getInstance(this._conf).getMBeanServer();
         if (server == null) {
            return mcache;
         } else {
            MultiMBean mb = DataCacheMBeanFactory.createDataCacheMBean(mcache, this._conf);
            MBeanHelper.register(mb, "DataCache", mcache.getName(), server, this._conf);
            return mcache;
         }
      }
   }

   private QueryCache initializeQueryCache(QueryCache cache) {
      cache.initialize(this);
      if (cache instanceof MonitoringQueryCache) {
         return cache;
      } else {
         MonitoringQueryCache mcache = new MonitoringQueryCache(this._conf, cache);
         MBeanServer server = Management.getInstance(this._conf).getMBeanServer();
         if (server == null) {
            return mcache;
         } else {
            MultiMBean mb = DataCacheMBeanFactory.createQueryCacheMBean(mcache, this._conf);
            MBeanHelper.register(mb, "QueryCache", (String)null, server, this._conf);
            return mcache;
         }
      }
   }

   public int size() {
      return this._caches.size();
   }

   public String[] getDataCacheNames() {
      Collection keys = this._caches.keySet();
      return (String[])((String[])keys.toArray(new String[keys.size()]));
   }

   public DataCache getDefaultDataCache() {
      return this._default;
   }

   public DataCache getSystemDataCache() {
      return this._system;
   }

   public DataCache getDataCache(String name) {
      return name == null ? this._default : (DataCache)this._caches.get(name);
   }

   public DataCache getDataCache(String name, boolean create) {
      if (name == null) {
         return this._default;
      } else if (!create && !this._caches.isEmpty()) {
         return (DataCache)this._caches.get(name);
      } else {
         DataCache cache = (DataCache)this._caches.get(name);
         if (cache != null) {
            return cache;
         } else {
            synchronized(this) {
               cache = (DataCache)this._caches.get(name);
               if (cache != null) {
                  return cache;
               } else {
                  cache = (DataCache)this._cacheValue.newInstance(this._defDataCacheClass, DataCache.class, this._conf, true);
                  if (cache == null) {
                     return null;
                  } else {
                     Configurations.configureInstance(cache, this._conf, this._defDataCacheProps, this._cacheValue.getProperty());
                     cache.setName(name);
                     DataCache initializedCache = this.initializeDataCache(cache);
                     this._caches.put(name, initializedCache);
                     return initializedCache;
                  }
               }
            }
         }
      }
   }

   public QueryCache getSystemQueryCache() {
      return this._queryCache;
   }

   public DataCachePCDataGenerator getPCDataGenerator() {
      return this._pcGenerator;
   }

   public DataCacheScheduler getDataCacheScheduler() {
      return this._scheduler;
   }

   public void close() {
      Iterator itr = this._caches.values().iterator();

      while(itr.hasNext()) {
         ImplHelper.close(itr.next());
      }

      ImplHelper.close(this._queryCache);
      if (this._scheduler != null) {
         this._scheduler.stop();
      }

   }
}
