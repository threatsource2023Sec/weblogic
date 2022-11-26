package weblogic.security.utils;

import java.util.Iterator;
import java.util.Map;

public class LRUCache implements Cache {
   private int mMaxSize = 1024;
   private OrderedHashMap mCache;

   public LRUCache() {
      this.mCache = new OrderedHashMap(this.mMaxSize);
   }

   public LRUCache(int maxSize) {
      this.mCache = new OrderedHashMap(this.mMaxSize);
      this.setMaximumSize(maxSize);
   }

   public int getMaximumSize() {
      return this.mMaxSize;
   }

   public void setMaximumSize(int maxSize) {
      if (maxSize < 0) {
         throw new IllegalArgumentException("Maximum size cannot be negative");
      } else {
         if (maxSize == 0) {
            this.clear();
         } else {
            while(this.size() > maxSize) {
               this.remove();
            }
         }

         this.mMaxSize = maxSize;
      }
   }

   public int size() {
      return this.mCache.size();
   }

   public Object put(Object key, Object value) {
      if (this.mCache.size() >= this.mMaxSize) {
         if (this.mMaxSize == 0) {
            return null;
         }

         this.mCache.removeFirst();
      }

      return this.mCache.putLast(key, value);
   }

   public Object get(Object key) {
      return this.mCache.moveToLast(key);
   }

   public Object lookup(Object key) {
      return this.mCache.get(key);
   }

   public void putOff(Object key) {
      this.mCache.moveToFirst(key);
   }

   public boolean containsKey(Object key) {
      return this.mCache.containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.mCache.containsValue(value);
   }

   public Map.Entry remove() {
      return this.mCache.removeFirst();
   }

   public Object remove(Object key) {
      return this.mCache.remove(key);
   }

   public void clear() {
      this.mCache.clear();
   }

   public Iterator iterator() {
      return this.mCache.iterator();
   }
}
