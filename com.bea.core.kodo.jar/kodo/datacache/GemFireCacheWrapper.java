package kodo.datacache;

import com.gemstone.gemfire.InternalGemFireException;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheException;
import com.gemstone.gemfire.cache.CacheExistsException;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.CacheTransactionManager;
import com.gemstone.gemfire.cache.EntryNotFoundException;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.TimeoutException;
import com.gemstone.gemfire.distributed.DistributedSystem;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class GemFireCacheWrapper {
   private static final Localizer s_loc = Localizer.forPackage(GemFireCacheWrapper.class);
   private Log _log;
   private Cache _cache;
   private Region _region;
   private Method _putMethod;
   private Method _destroyMethod;
   private boolean _transactionActive = false;

   public GemFireCacheWrapper(String regionName, Log log) {
      this._log = log;
      this._cache = this.initializeCache();
      this._region = this.initializeRegion(this._cache, regionName);
   }

   public Cache getCache() {
      return this._cache;
   }

   public Region getRegion() {
      return this._region;
   }

   public void close() {
      this._cache.close();
   }

   public Object get(Object key) {
      if (this._log.isTraceEnabled()) {
         this._log.trace(s_loc.get("gemfire-get", key));
      }

      try {
         Object obj = this._region.get(key);
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-retrieved", obj, key));
         }

         return obj;
      } catch (TimeoutException var3) {
         if (this._log.isWarnEnabled()) {
            this._log.warn(s_loc.get("gemfire-cache-get-timeout"), var3);
         }

         return null;
      } catch (Exception var4) {
         throw new UserException(var4);
      }
   }

   public Object put(Object key, Object value) {
      if (key == null) {
         return null;
      } else {
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-put", key, value));
         }

         try {
            Object oldObj = this._region.containsValueForKey(key) ? this._region.get(key) : null;

            try {
               this._region.put(key, value);
            } catch (NoSuchMethodError var5) {
               if (this._putMethod == null) {
                  this._putMethod = Region.class.getMethod("put", Object.class, Object.class);
               }

               this._putMethod.invoke(this._region, key, value);
            }

            if (this._log.isTraceEnabled()) {
               this._log.trace(s_loc.get("gemfire-stored", key, value));
               this._log.trace(s_loc.get("gemfire-stored-answer", oldObj, key));
            }

            return oldObj;
         } catch (Exception var6) {
            throw new InternalGemFireException(var6.getMessage());
         }
      }
   }

   public Object remove(Object key) {
      if (key == null) {
         return null;
      } else {
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-remove", key));
         }

         try {
            Object oldObj = this._region.containsValueForKey(key) ? this._region.get(key) : null;

            try {
               this._region.destroy(key);
            } catch (NoSuchMethodError var4) {
               if (this._destroyMethod == null) {
                  this._destroyMethod = Region.class.getMethod("destroy", Object.class);
               }

               this._destroyMethod.invoke(this._region, key);
            }

            if (this._log.isTraceEnabled()) {
               this._log.trace(s_loc.get("gemfire-removed", key));
               this._log.trace(s_loc.get("gemfire-removed-answer", oldObj, key));
            }

            return oldObj;
         } catch (EntryNotFoundException var5) {
            if (this._log.isWarnEnabled()) {
               this._log.trace(s_loc.get("gemfire-remove-key-notfound", key));
            }

            return null;
         } catch (CacheException var6) {
            throw new InternalGemFireException(var6.getMessage());
         } catch (NoSuchMethodException var7) {
            throw new InternalGemFireException(var7.getMessage());
         } catch (IllegalAccessException var8) {
            throw new InternalGemFireException(var8.getMessage());
         } catch (InvocationTargetException var9) {
            throw new InternalGemFireException(var9.getMessage());
         }
      }
   }

   public void clear() {
      this._region.localInvalidateRegion();
   }

   public Set keySet() {
      return this._region.keySet();
   }

   protected Cache initializeCache() {
      if (this._log.isTraceEnabled()) {
         this._log.trace(s_loc.get("gemfire-init"));
      }

      Cache cache;
      try {
         Properties properties = new Properties();
         DistributedSystem distributedSystem = DistributedSystem.connect(properties);
         cache = CacheFactory.create(distributedSystem);
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-create", cache.getName()));
         }

         return cache;
      } catch (CacheExistsException var4) {
         cache = CacheFactory.getAnyInstance();
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-retrieved-cache", cache.getName()));
         }

         return cache;
      } catch (Exception var5) {
         throw new InternalGemFireException(s_loc.get("gemfire-init-error").getMessage(), var5);
      }
   }

   protected Region initializeRegion(Cache cache, String regionName) {
      if (this._log.isTraceEnabled()) {
         this._log.trace(s_loc.get("gemfire-retrieve-region", regionName));
      }

      Region region = cache.getRegion(regionName);
      if (region == null) {
         throw new InternalGemFireException(s_loc.get("gemfire-region-notconfiged", regionName).getMessage());
      } else {
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-retrieved-region", region));
         }

         return region;
      }
   }

   public boolean beginGemFireTransaction() {
      if (!this._transactionActive) {
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-beginning"));
         }

         CacheTransactionManager tm = this.getCache().getCacheTransactionManager();
         tm.begin();
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-begun", tm.getTransactionId()));
         }

         this._transactionActive = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean commitGemFireTransaction() {
      try {
         CacheTransactionManager tm = this.getCache().getCacheTransactionManager();
         Object txid = tm.getTransactionId();
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-committing", txid));
         }

         tm.commit();
         this._transactionActive = false;
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-committed", txid));
         }

         return true;
      } catch (Exception var3) {
         this._log.warn(var3);
         return false;
      }
   }

   public void rollbackGemFireTransaction() {
      try {
         CacheTransactionManager tm = this.getCache().getCacheTransactionManager();
         Object txid = tm.getTransactionId();
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-rollingback", txid));
         }

         tm.rollback();
         this._transactionActive = false;
         if (this._log.isTraceEnabled()) {
            this._log.trace(s_loc.get("gemfire-rolledback", txid));
         }
      } catch (Exception var3) {
         this._log.warn(var3);
      }

   }
}
