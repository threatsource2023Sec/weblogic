package com.bea.xbean.common;

import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.SystemProperties;
import java.lang.ref.SoftReference;

public class SystemCache {
   private static SystemCache INSTANCE = new SystemCache();
   private ThreadLocal tl_saxLoaders = new ThreadLocal();

   public static final synchronized void set(SystemCache instance) {
      INSTANCE = instance;
   }

   public static final SystemCache get() {
      return INSTANCE;
   }

   public SchemaTypeLoader getFromTypeLoaderCache(ClassLoader cl) {
      return null;
   }

   public void addToTypeLoaderCache(SchemaTypeLoader stl, ClassLoader cl) {
   }

   public Object getSaxLoader() {
      SoftReference s = (SoftReference)this.tl_saxLoaders.get();
      return s == null ? null : s.get();
   }

   public void setSaxLoader(Object saxLoader) {
      this.tl_saxLoaders.set(new SoftReference(saxLoader));
   }

   static {
      String cacheClass = SystemProperties.getProperty("xmlbean.systemcacheimpl");
      Object impl = null;
      if (cacheClass != null) {
         try {
            impl = Class.forName(cacheClass).newInstance();
            if (!(impl instanceof SystemCache)) {
               throw new ClassCastException("Value for system property \"xmlbean.systemcacheimpl\" points to a class (" + cacheClass + ") which does not derive from SystemCache");
            }
         } catch (ClassNotFoundException var3) {
            throw new RuntimeException("Cache class " + cacheClass + " specified by \"xmlbean.systemcacheimpl\" was not found.", var3);
         } catch (InstantiationException var4) {
            throw new RuntimeException("Could not instantiate class " + cacheClass + " as specified by \"xmlbean.systemcacheimpl\"." + " An empty constructor may be missing.", var4);
         } catch (IllegalAccessException var5) {
            throw new RuntimeException("Could not instantiate class " + cacheClass + " as specified by \"xmlbean.systemcacheimpl\"." + " A public empty constructor may be missing.", var5);
         }
      }

      if (impl != null) {
         INSTANCE = (SystemCache)impl;
      }

   }
}
