package weblogic.cache.store;

import commonj.work.WorkManager;
import java.util.Map;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.CacheStore;
import weblogic.cache.configuration.BEACacheLogger;
import weblogic.cache.util.ListenerSupport;

public class WriteThrough extends WritePolicy {
   public WriteThrough(CacheStore store, WorkManager timerWorkManager) {
      super(store, timerWorkManager);
   }

   public void store(Object key, Object value) {
      if (this.started) {
         try {
            this.store.store(key, value);
            this.fireSuccessListeners(key, value);
         } catch (CacheRuntimeException var6) {
            CacheRuntimeException e = var6;

            try {
               this.fireFailureListeners(key, value, e);
            } catch (ListenerSupport.ListenerSupportException var5) {
               BEACacheLogger.logUnableToFireListeners(var5);
            }
         }

      }
   }

   public void storeAll(Map map) {
      if (this.started) {
         try {
            this.store.storeAll(map);
            this.fireSuccessListeners(map);
         } catch (CacheRuntimeException var5) {
            CacheRuntimeException e = var5;

            try {
               this.fireFailureListeners(map, e);
            } catch (ListenerSupport.ListenerSupportException var4) {
               BEACacheLogger.logUnableToFireListeners(var4);
            }
         }

      }
   }

   public void shutdown(boolean graceful) {
      super.shutdown(graceful);
   }
}
