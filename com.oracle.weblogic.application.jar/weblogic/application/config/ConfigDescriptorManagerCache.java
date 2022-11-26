package weblogic.application.config;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import weblogic.application.ModuleException;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.classloaders.GenericClassLoader;

public final class ConfigDescriptorManagerCache {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private volatile Map _cache = new WeakHashMap();
   public static final ConfigDescriptorManagerCache SINGLETON = new ConfigDescriptorManagerCache();

   private ConfigDescriptorManagerCache() {
   }

   public DescriptorManager getEntry(GenericClassLoader bindingClassLoader) throws ModuleException {
      synchronized(this._cache) {
         WeakReference value = (WeakReference)this._cache.get(bindingClassLoader);
         DescriptorManager entry = null;
         if (value != null) {
            entry = (DescriptorManager)value.get();
         }

         if (entry == null) {
            entry = new DescriptorManager(bindingClassLoader);
            this._cache.put(bindingClassLoader, new WeakReference(entry));
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Creating entry using key " + bindingClassLoader + ". New size: " + this._cache.size());
            }
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Got existing entry using key " + bindingClassLoader + ". Current size: " + this._cache.size());
         }

         return entry;
      }
   }

   public void removeEntry(GenericClassLoader bindingClassLoader) {
      synchronized(this._cache) {
         WeakReference value = (WeakReference)this._cache.remove(bindingClassLoader);
         if (debugLogger.isDebugEnabled()) {
            DescriptorManager entry = null;
            if (value != null) {
               entry = (DescriptorManager)value.get();
            }

            if (entry != null) {
               debugLogger.debug("Removing entry for key " + bindingClassLoader + ". New size: " + this._cache.size());
            } else {
               debugLogger.debug("Trying to remove non-existant entry for " + bindingClassLoader + ". Current size: " + this._cache.size());
            }
         }

      }
   }
}
