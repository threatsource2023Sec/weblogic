package weblogic.cache.store;

import weblogic.cache.CacheLoader;
import weblogic.cache.CacheStore;
import weblogic.cache.configuration.ConfigurationException;

public class StoreFactory {
   private StoreFactory() {
   }

   public static StoreFactory getInstance() {
      return StoreFactory.Factory.THE_ONE;
   }

   public CacheLoader createCacheLoader(String classname) throws ConfigurationException {
      try {
         return (CacheLoader)Thread.currentThread().getContextClassLoader().loadClass(classname).newInstance();
      } catch (InstantiationException var3) {
         throw new ConfigurationException("Unable to create CacheLoader", "CustomCacheLoaderClassname", classname, var3);
      } catch (IllegalAccessException var4) {
         throw new ConfigurationException("Unable to create CacheLoader", "CustomCacheLoaderClassname", classname, var4);
      } catch (ClassNotFoundException var5) {
         throw new ConfigurationException("Unable to create CacheLoader", "CustomCacheLoaderClassname", classname, var5);
      }
   }

   public CacheStore createCacheStore(String classname) throws ConfigurationException {
      try {
         return (CacheStore)Thread.currentThread().getContextClassLoader().loadClass(classname).newInstance();
      } catch (InstantiationException var3) {
         throw new ConfigurationException("Unable to create CacheStore", "CustomCacheStoreClassname", classname, var3);
      } catch (IllegalAccessException var4) {
         throw new ConfigurationException("Unable to create CacheStore", "CustomCacheStoreClassname", classname, var4);
      } catch (ClassNotFoundException var5) {
         throw new ConfigurationException("Unable to create CacheStore", "CustomCacheStoreClassname", classname, var5);
      }
   }

   // $FF: synthetic method
   StoreFactory(Object x0) {
      this();
   }

   private static final class Factory {
      static final StoreFactory THE_ONE = new StoreFactory();
   }
}
