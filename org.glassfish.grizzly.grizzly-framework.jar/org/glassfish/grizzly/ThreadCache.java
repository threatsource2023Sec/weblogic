package org.glassfish.grizzly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.glassfish.grizzly.threadpool.DefaultWorkerThread;

public final class ThreadCache {
   private static final ObjectCacheElement[] INITIAL_OBJECT_ARRAY = new ObjectCacheElement[16];
   private static final Map typeIndexMap = new HashMap();
   private static int indexCounter;
   private static final ThreadLocal genericCacheAttr = new ThreadLocal();

   public static synchronized CachedTypeIndex obtainIndex(Class clazz, int size) {
      return obtainIndex(clazz.getName(), clazz, size);
   }

   public static synchronized CachedTypeIndex obtainIndex(String name, Class clazz, int size) {
      CachedTypeIndex typeIndex = (CachedTypeIndex)typeIndexMap.get(name);
      if (typeIndex == null) {
         typeIndex = new CachedTypeIndex(indexCounter++, name, clazz, size);
         typeIndexMap.put(name, typeIndex);
      }

      return typeIndex;
   }

   public static boolean putToCache(CachedTypeIndex index, Object o) {
      return putToCache(Thread.currentThread(), index, o);
   }

   public static boolean putToCache(Thread currentThread, CachedTypeIndex index, Object o) {
      if (currentThread instanceof DefaultWorkerThread) {
         return ((DefaultWorkerThread)currentThread).putToCache(index, o);
      } else {
         ObjectCache genericCache = (ObjectCache)genericCacheAttr.get();
         if (genericCache == null) {
            genericCache = new ObjectCache();
            genericCacheAttr.set(genericCache);
         }

         return genericCache.put(index, o);
      }
   }

   public static Object getFromCache(CachedTypeIndex index) {
      return getFromCache(Thread.currentThread(), index);
   }

   public static Object getFromCache(Thread currentThread, CachedTypeIndex index) {
      assert currentThread == Thread.currentThread();

      if (currentThread instanceof DefaultWorkerThread) {
         return ((DefaultWorkerThread)currentThread).getFromCache(index);
      } else {
         ObjectCache genericCache = (ObjectCache)genericCacheAttr.get();
         return genericCache != null ? genericCache.get(index) : null;
      }
   }

   public static Object takeFromCache(CachedTypeIndex index) {
      return takeFromCache(Thread.currentThread(), index);
   }

   public static Object takeFromCache(Thread currentThread, CachedTypeIndex index) {
      if (currentThread instanceof DefaultWorkerThread) {
         return ((DefaultWorkerThread)currentThread).takeFromCache(index);
      } else {
         ObjectCache genericCache = (ObjectCache)genericCacheAttr.get();
         return genericCache != null ? genericCache.take(index) : null;
      }
   }

   public static final class CachedTypeIndex {
      private final int index;
      private final Class clazz;
      private final int size;
      private final String name;

      public CachedTypeIndex(int index, String name, Class clazz, int size) {
         this.index = index;
         this.name = name;
         this.clazz = clazz;
         this.size = size;
      }

      public int getIndex() {
         return this.index;
      }

      public String getName() {
         return this.name;
      }

      public Class getClazz() {
         return this.clazz;
      }

      public int getSize() {
         return this.size;
      }
   }

   public static final class ObjectCacheElement {
      private final int size;
      private final Object[] cache;
      private int index;

      public ObjectCacheElement(int size) {
         this.size = size;
         this.cache = new Object[size];
      }

      public boolean put(Object o) {
         if (this.index < this.size) {
            this.cache[this.index++] = o;
            return true;
         } else {
            return false;
         }
      }

      public Object get() {
         if (this.index > 0) {
            Object o = this.cache[this.index - 1];
            return o;
         } else {
            return null;
         }
      }

      public Object take() {
         if (this.index > 0) {
            --this.index;
            Object o = this.cache[this.index];
            this.cache[this.index] = null;
            return o;
         } else {
            return null;
         }
      }
   }

   public static final class ObjectCache {
      private ObjectCacheElement[] objectCacheElements;

      public boolean put(CachedTypeIndex index, Object o) {
         if (this.objectCacheElements != null && index.getIndex() < this.objectCacheElements.length) {
            ObjectCacheElement objectCache = this.objectCacheElements[index.getIndex()];
            if (objectCache == null) {
               objectCache = new ObjectCacheElement(index.size);
               this.objectCacheElements[index.getIndex()] = objectCache;
            }

            return objectCache.put(o);
         } else {
            ObjectCacheElement[] arrayToGrow = this.objectCacheElements != null ? this.objectCacheElements : ThreadCache.INITIAL_OBJECT_ARRAY;
            int newSize = Math.max(index.getIndex() + 1, arrayToGrow.length * 3 / 2 + 1);
            this.objectCacheElements = (ObjectCacheElement[])Arrays.copyOf(arrayToGrow, newSize);
            ObjectCacheElement objectCache = new ObjectCacheElement(index.getSize());
            this.objectCacheElements[index.getIndex()] = objectCache;
            return objectCache.put(o);
         }
      }

      public Object get(CachedTypeIndex index) {
         int idx;
         if (this.objectCacheElements != null && (idx = index.getIndex()) < this.objectCacheElements.length) {
            ObjectCacheElement objectCache = this.objectCacheElements[idx];
            return objectCache == null ? null : objectCache.get();
         } else {
            return null;
         }
      }

      public Object take(CachedTypeIndex index) {
         int idx;
         if (this.objectCacheElements != null && (idx = index.getIndex()) < this.objectCacheElements.length) {
            ObjectCacheElement objectCache = this.objectCacheElements[idx];
            return objectCache == null ? null : objectCache.take();
         } else {
            return null;
         }
      }
   }
}
