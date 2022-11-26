package org.python.icu.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SimpleCache implements ICUCache {
   private static final int DEFAULT_CAPACITY = 16;
   private volatile Reference cacheRef;
   private int type;
   private int capacity;

   public SimpleCache() {
      this.cacheRef = null;
      this.type = 0;
      this.capacity = 16;
   }

   public SimpleCache(int cacheType) {
      this(cacheType, 16);
   }

   public SimpleCache(int cacheType, int initialCapacity) {
      this.cacheRef = null;
      this.type = 0;
      this.capacity = 16;
      if (cacheType == 1) {
         this.type = cacheType;
      }

      if (initialCapacity > 0) {
         this.capacity = initialCapacity;
      }

   }

   public Object get(Object key) {
      Reference ref = this.cacheRef;
      if (ref != null) {
         Map map = (Map)ref.get();
         if (map != null) {
            return map.get(key);
         }
      }

      return null;
   }

   public void put(Object key, Object value) {
      Reference ref = this.cacheRef;
      Map map = null;
      if (ref != null) {
         map = (Map)ref.get();
      }

      if (map == null) {
         map = Collections.synchronizedMap(new HashMap(this.capacity));
         Object ref;
         if (this.type == 1) {
            ref = new WeakReference(map);
         } else {
            ref = new SoftReference(map);
         }

         this.cacheRef = (Reference)ref;
      }

      map.put(key, value);
   }

   public void clear() {
      this.cacheRef = null;
   }
}
