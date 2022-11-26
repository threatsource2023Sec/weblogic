package weblogic.cache.store;

import commonj.work.WorkManager;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import weblogic.cache.CacheLoadListener;
import weblogic.cache.CacheLoader;
import weblogic.cache.util.ListenerSupport;

public abstract class LoadingScheme {
   protected CacheLoader delegate;
   protected final long timeout;
   private final ListenerSupport listeners;
   protected boolean started = true;
   public static final long NO_TIMEOUT = -1L;

   public LoadingScheme(CacheLoader delegate, long timeout, WorkManager timerWorkManager) {
      this.delegate = delegate;
      this.timeout = timeout;
      this.listeners = new ListenerSupport(timerWorkManager) {
         protected void onEvent(CacheLoadListener listener, LoadEvent type, Object data, Throwable problem) {
            if (type == LoadingScheme.LoadEvent.SUCCESS) {
               listener.onLoad((Map)data);
            } else {
               listener.onLoadError((Collection)data, problem);
            }

         }
      };
   }

   public void setCacheLoader(CacheLoader loader) {
      this.delegate = loader;
   }

   public boolean addCacheLoadListener(CacheLoadListener listener) {
      this.listeners.add(listener);
      return true;
   }

   public void removeCacheLoadListener(CacheLoadListener listener) {
      this.listeners.remove(listener);
   }

   public void copyCacheLoadListeners(LoadingScheme other) {
      this.listeners.addAll(other.listeners);
   }

   public void setListenerWorkManager(WorkManager workManager) {
      this.listeners.setWorkManager(workManager);
   }

   protected void fireSuccessEvent(Object key, Object value) throws ListenerSupport.ListenerSupportException {
      this.fireSuccessEvent(Collections.singletonMap(key, value));
   }

   protected void fireSuccessEvent(Map loadedMap) throws ListenerSupport.ListenerSupportException {
      this.listeners.fireEvent(LoadingScheme.LoadEvent.SUCCESS, loadedMap, (Object)null);
   }

   protected void fireFailureEvent(Object key, Throwable error) throws ListenerSupport.ListenerSupportException {
      this.fireFailureEvent((Collection)Collections.singleton(key), error);
   }

   protected void fireFailureEvent(Collection keys, Throwable error) throws ListenerSupport.ListenerSupportException {
      this.listeners.fireEvent(LoadingScheme.LoadEvent.FAILURE, keys, error);
   }

   public abstract void markStale(Object var1);

   public abstract Object load(Object var1);

   public abstract Map loadAll(Collection var1);

   public void start() {
      this.listeners.start();
      this.started = true;
   }

   public void shutdown(boolean graceful) {
      this.listeners.stop(graceful);
      this.started = false;
   }

   public boolean isStarted() {
      return this.started;
   }

   protected static enum LoadEvent {
      SUCCESS,
      FAILURE;
   }
}
