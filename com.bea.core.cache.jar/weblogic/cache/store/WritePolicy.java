package weblogic.cache.store;

import commonj.work.WorkManager;
import java.util.Collections;
import java.util.Map;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.CacheStore;
import weblogic.cache.CacheStoreListener;
import weblogic.cache.KeyFilter;
import weblogic.cache.util.ListenerSupport;

public abstract class WritePolicy {
   protected CacheStore store;
   protected boolean started = true;
   protected final ListenerSupport listeners;

   public WritePolicy(CacheStore store, WorkManager listenerWorkManager) throws CacheRuntimeException {
      this.store = store;
      this.listeners = new ListenerSupport(listenerWorkManager) {
         protected void onEvent(CacheStoreListener listener, WriteEvent type, Map data, Throwable problem) {
            if (type == WritePolicy.WriteEvent.SUCCESS) {
               listener.onStore(data);
            } else {
               listener.onStoreError(data, problem);
            }

         }
      };
   }

   public void setCacheStore(CacheStore store) {
      this.store = store;
   }

   public void addCacheStoreListener(CacheStoreListener listener) {
      this.listeners.add(listener);
   }

   public void addCacheStoreListener(CacheStoreListener listener, Object key) {
   }

   public void addCacheStoreListener(CacheStoreListener listener, KeyFilter keys) {
   }

   public void removeCacheStoreListener(CacheStoreListener listener) {
      this.listeners.remove(listener);
   }

   public void copyCacheStoreListeners(WritePolicy other) {
      this.listeners.addAll(other.listeners);
   }

   public void setListenerWorkManager(WorkManager workManager) {
      this.listeners.setWorkManager(workManager);
   }

   protected void fireSuccessListeners(Object key, Object value) throws ListenerSupport.ListenerSupportException {
      this.fireSuccessListeners(Collections.singletonMap(key, value));
   }

   protected void fireSuccessListeners(Map storedMap) throws ListenerSupport.ListenerSupportException {
      this.listeners.fireEvent(WritePolicy.WriteEvent.SUCCESS, storedMap, (Object)null);
   }

   protected void fireFailureListeners(Object key, Object value, Throwable error) throws ListenerSupport.ListenerSupportException {
      this.fireFailureListeners(Collections.singletonMap(key, value), error);
   }

   protected void fireFailureListeners(Map storedMap, Throwable error) throws ListenerSupport.ListenerSupportException {
      this.listeners.fireEvent(WritePolicy.WriteEvent.FAILURE, storedMap, error);
   }

   public abstract void store(Object var1, Object var2);

   public abstract void storeAll(Map var1);

   public void shutdown(boolean graceful) {
      this.listeners.stop(graceful);
      this.started = false;
   }

   public void start() {
      this.listeners.start();
      this.started = true;
   }

   public boolean isStarted() {
      return this.started;
   }

   public static enum WriteEvent {
      SUCCESS,
      FAILURE;
   }
}
