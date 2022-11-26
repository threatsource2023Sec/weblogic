package weblogic.cache.store;

import commonj.work.WorkManager;
import java.util.Collection;
import java.util.Map;
import weblogic.cache.CacheLoader;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.configuration.BEACacheLogger;
import weblogic.cache.util.ListenerSupport;

public class ReadThroughScheme extends LoadingScheme {
   public ReadThroughScheme(CacheLoader delegate, long timeout, WorkManager timerWorkManager) {
      super(delegate, timeout, timerWorkManager);
   }

   public Object load(Object key) {
      if (!this.started) {
         throw new CacheRuntimeException("The loading policy is not started");
      } else {
         Object value = null;

         try {
            value = this.delegate.load(key);
            this.fireSuccessEvent(key, value);
            return value;
         } catch (CacheRuntimeException var6) {
            CacheRuntimeException e = var6;

            try {
               this.fireFailureEvent(key, e);
            } catch (ListenerSupport.ListenerSupportException var5) {
               BEACacheLogger.logUnableToFireListeners(var5);
            }

            throw var6;
         }
      }
   }

   public Map loadAll(Collection keys) {
      if (!this.started) {
         throw new CacheRuntimeException("The loading policy is not started");
      } else {
         Map loadedMap = null;

         try {
            loadedMap = this.delegate.loadAll(keys);
            this.fireSuccessEvent(loadedMap);
            return loadedMap;
         } catch (CacheRuntimeException var6) {
            CacheRuntimeException e = var6;

            try {
               this.fireFailureEvent(keys, e);
            } catch (ListenerSupport.ListenerSupportException var5) {
               BEACacheLogger.logUnableToFireListeners(var5);
            }

            throw var6;
         }
      }
   }

   public void markStale(Object key) {
   }
}
