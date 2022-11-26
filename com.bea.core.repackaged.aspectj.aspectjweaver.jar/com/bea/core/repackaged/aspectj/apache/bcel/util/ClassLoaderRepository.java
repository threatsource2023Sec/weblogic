package com.bea.core.repackaged.aspectj.apache.bcel.util;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassParser;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.JavaClass;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class ClassLoaderRepository implements Repository {
   private static ClassLoader bootClassLoader = null;
   private ClassLoaderReference loaderRef;
   private WeakHashMap localCache = new WeakHashMap();
   private static SoftHashMap sharedCache = new SoftHashMap(Collections.synchronizedMap(new HashMap()));
   private SoftHashMap nameMap = new SoftHashMap(new HashMap(), false);
   public static boolean useSharedCache = System.getProperty("com.bea.core.repackaged.aspectj.apache.bcel.useSharedCache", "true").equalsIgnoreCase("true");
   private static int cacheHitsShared = 0;
   private static int missSharedEvicted = 0;
   private long timeManipulatingURLs = 0L;
   private long timeSpentLoading = 0L;
   private int classesLoadedCount = 0;
   private int misses = 0;
   private int cacheHitsLocal = 0;
   private int missLocalEvicted = 0;

   public ClassLoaderRepository(ClassLoader loader) {
      this.loaderRef = new DefaultClassLoaderReference(loader != null ? loader : getBootClassLoader());
   }

   public ClassLoaderRepository(ClassLoaderReference loaderRef) {
      this.loaderRef = loaderRef;
   }

   private static synchronized ClassLoader getBootClassLoader() {
      if (bootClassLoader == null) {
         bootClassLoader = new URLClassLoader(new URL[0]);
      }

      return bootClassLoader;
   }

   private void storeClassAsReference(URL url, JavaClass clazz) {
      if (useSharedCache) {
         clazz.setRepository((Repository)null);
         sharedCache.put(url, clazz);
      } else {
         clazz.setRepository(this);
         this.localCache.put(url, new SoftReference(clazz));
      }

   }

   public void storeClass(JavaClass clazz) {
      this.storeClassAsReference(this.toURL(clazz.getClassName()), clazz);
   }

   public void removeClass(JavaClass clazz) {
      if (useSharedCache) {
         sharedCache.remove(this.toURL(clazz.getClassName()));
      } else {
         this.localCache.remove(this.toURL(clazz.getClassName()));
      }

   }

   public JavaClass findClass(String className) {
      return useSharedCache ? this.findClassShared(this.toURL(className)) : this.findClassLocal(this.toURL(className));
   }

   private JavaClass findClassLocal(URL url) {
      Object o = this.localCache.get(url);
      if (o != null) {
         o = ((Reference)o).get();
         if (o != null) {
            return (JavaClass)o;
         }

         ++this.missLocalEvicted;
      }

      return null;
   }

   private JavaClass findClassShared(URL url) {
      return (JavaClass)sharedCache.get(url);
   }

   private URL toURL(String className) {
      URL url = (URL)this.nameMap.get(className);
      if (url == null) {
         String classFile = className.replace('.', '/');
         url = this.loaderRef.getClassLoader().getResource(classFile + ".class");
         this.nameMap.put(className, url);
      }

      return url;
   }

   public JavaClass loadClass(String className) throws ClassNotFoundException {
      long time = System.currentTimeMillis();
      URL url = this.toURL(className);
      this.timeManipulatingURLs += System.currentTimeMillis() - time;
      if (url == null) {
         throw new ClassNotFoundException(className + " not found - unable to determine URL");
      } else {
         JavaClass clazz = null;
         if (useSharedCache) {
            clazz = this.findClassShared(url);
            if (clazz != null) {
               ++cacheHitsShared;
               return clazz;
            }
         } else {
            clazz = this.findClassLocal(url);
            if (clazz != null) {
               ++this.cacheHitsLocal;
               return clazz;
            }
         }

         ++this.misses;

         try {
            String classFile = className.replace('.', '/');
            InputStream is = useSharedCache ? url.openStream() : this.loaderRef.getClassLoader().getResourceAsStream(classFile + ".class");
            if (is == null) {
               throw new ClassNotFoundException(className + " not found using url " + url);
            } else {
               ClassParser parser = new ClassParser(is, className);
               clazz = parser.parse();
               this.storeClassAsReference(url, clazz);
               this.timeSpentLoading += System.currentTimeMillis() - time;
               ++this.classesLoadedCount;
               return clazz;
            }
         } catch (IOException var9) {
            throw new ClassNotFoundException(var9.toString());
         }
      }
   }

   public String report() {
      StringBuffer sb = new StringBuffer();
      sb.append("BCEL repository report.");
      if (useSharedCache) {
         sb.append(" (shared cache)");
      } else {
         sb.append(" (local cache)");
      }

      sb.append(" Total time spent loading: " + this.timeSpentLoading + "ms.");
      sb.append(" Time spent manipulating URLs: " + this.timeManipulatingURLs + "ms.");
      sb.append(" Classes loaded: " + this.classesLoadedCount + ".");
      if (useSharedCache) {
         sb.append(" Shared cache size: " + sharedCache.size());
         sb.append(" Shared cache (hits/missDueToEviction): (" + cacheHitsShared + "/" + missSharedEvicted + ").");
      } else {
         sb.append(" Local cache size: " + this.localCache.size());
         sb.append(" Local cache (hits/missDueToEviction): (" + this.cacheHitsLocal + "/" + this.missLocalEvicted + ").");
      }

      return sb.toString();
   }

   public long[] reportStats() {
      return new long[]{this.timeSpentLoading, this.timeManipulatingURLs, (long)this.classesLoadedCount, (long)cacheHitsShared, (long)missSharedEvicted, (long)this.cacheHitsLocal, (long)this.missLocalEvicted, (long)sharedCache.size()};
   }

   public void reset() {
      this.timeManipulatingURLs = 0L;
      this.timeSpentLoading = 0L;
      this.classesLoadedCount = 0;
      this.cacheHitsLocal = 0;
      cacheHitsShared = 0;
      missSharedEvicted = 0;
      this.missLocalEvicted = 0;
      this.misses = 0;
      this.clear();
   }

   public JavaClass loadClass(Class clazz) throws ClassNotFoundException {
      return this.loadClass(clazz.getName());
   }

   public void clear() {
      if (useSharedCache) {
         sharedCache.clear();
      } else {
         this.localCache.clear();
      }

   }

   public static class SoftHashMap extends AbstractMap {
      private Map map;
      boolean recordMiss;
      private ReferenceQueue rq;

      public SoftHashMap(Map map) {
         this.recordMiss = true;
         this.rq = new ReferenceQueue();
         this.map = map;
      }

      public SoftHashMap() {
         this(new HashMap());
      }

      public SoftHashMap(Map map, boolean b) {
         this(map);
         this.recordMiss = b;
      }

      private void processQueue() {
         SpecialValue sv = null;

         while((sv = (SpecialValue)this.rq.poll()) != null) {
            this.map.remove(sv.key);
         }

      }

      public Object get(Object key) {
         SpecialValue value = (SpecialValue)this.map.get(key);
         if (value == null) {
            return null;
         } else if (value.get() == null) {
            this.map.remove(value.key);
            if (this.recordMiss) {
               ClassLoaderRepository.missSharedEvicted = ClassLoaderRepository.missSharedEvicted + 1;
            }

            return null;
         } else {
            return value.get();
         }
      }

      public Object put(Object k, Object v) {
         this.processQueue();
         return this.map.put(k, new SpecialValue(k, v));
      }

      public Set entrySet() {
         return this.map.entrySet();
      }

      public void clear() {
         this.processQueue();
         this.map.clear();
      }

      public int size() {
         this.processQueue();
         return this.map.size();
      }

      public Object remove(Object k) {
         this.processQueue();
         SpecialValue value = (SpecialValue)this.map.remove(k);
         if (value == null) {
            return null;
         } else {
            return value.get() != null ? value.get() : null;
         }
      }

      class SpecialValue extends SoftReference {
         private final Object key;

         SpecialValue(Object k, Object v) {
            super(v, SoftHashMap.this.rq);
            this.key = k;
         }
      }
   }
}
