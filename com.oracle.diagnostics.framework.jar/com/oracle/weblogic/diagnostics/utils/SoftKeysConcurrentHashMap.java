package com.oracle.weblogic.diagnostics.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SoftKeysConcurrentHashMap implements ConcurrentMap {
   private static final int CACHE_INIT_SIZE = 128;
   private ConcurrentHashMap map = new ConcurrentHashMap(128);
   private ReferenceQueue refQ = new ReferenceQueue();

   private void cleanup() {
      SoftReferenceKey ref = null;

      while((ref = (SoftReferenceKey)this.refQ.poll()) != null) {
         Object key = ref.get();
         if (key != null) {
            this.map.remove(key);
         }
      }

   }

   private Map.Entry findEntry(Object key) {
      Iterator var2 = this.map.entrySet().iterator();

      Map.Entry entry;
      Object softKey;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         entry = (Map.Entry)var2.next();
         softKey = ((SoftReferenceKey)entry.getKey()).get();
         if (softKey == null) {
            this.map.remove(entry.getKey());
         }
      } while(softKey != key && !key.equals(softKey));

      return entry;
   }

   public Object put(Object key, Object value) {
      this.cleanup();
      return this.map.put(new SoftReferenceKey(key), value);
   }

   public Object putIfAbsent(Object key, Object value) {
      this.cleanup();
      return this.map.putIfAbsent(new SoftReferenceKey(key), value);
   }

   public Object get(Object key) {
      this.cleanup();
      Map.Entry entry = this.findEntry(key);
      return entry != null ? entry.getValue() : null;
   }

   public int size() {
      this.cleanup();
      return this.map.size();
   }

   public void clear() {
      this.cleanup();
      this.map.clear();
   }

   public boolean isEmpty() {
      this.cleanup();
      return this.map.isEmpty();
   }

   public boolean containsKey(Object key) {
      this.cleanup();
      Map.Entry entry = this.findEntry(key);
      return entry != null ? this.map.containsKey(entry.getKey()) : false;
   }

   public boolean containsValue(Object value) {
      this.cleanup();
      return this.map.containsValue(value);
   }

   public Object remove(Object key) {
      this.cleanup();
      Map.Entry entry = this.findEntry(key);
      return entry != null ? this.map.remove(entry.getKey()) : null;
   }

   public void putAll(Map m) {
      this.cleanup();
      Iterator var2 = m.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         Object value = entry.getValue();
         Object key = entry.getKey();
         this.map.put(new SoftReferenceKey(key), value);
      }

   }

   public Set keySet() {
      this.cleanup();
      HashSet set = new HashSet();
      Iterator var2 = this.map.keySet().iterator();

      while(var2.hasNext()) {
         SoftReferenceKey keyRef = (SoftReferenceKey)var2.next();
         Object key = keyRef.get();
         if (key != null) {
            set.add(key);
         }
      }

      return set;
   }

   public Collection values() {
      this.cleanup();
      return this.map.values();
   }

   public boolean remove(Object key, Object value) {
      boolean result = false;
      this.cleanup();
      Map.Entry entry = this.findEntry(key);
      if (entry != null && value.equals(entry.getValue())) {
         result = this.map.remove(entry.getKey(), entry.getValue());
      }

      return result;
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      Map.Entry entry = this.findEntry(key);
      if (entry != null && entry.getValue() != null && entry.getValue().equals(oldValue)) {
         this.put(key, newValue);
         return true;
      } else {
         return false;
      }
   }

   public Object replace(Object key, Object value) {
      Map.Entry entry = this.findEntry(key);
      return entry != null && entry.getValue() != null && entry.getValue().equals(value) ? this.put(key, value) : null;
   }

   public Set entrySet() {
      throw new UnsupportedOperationException();
   }

   private class SoftReferenceKey extends SoftReference {
      public SoftReferenceKey(Object key) {
         super(key, SoftKeysConcurrentHashMap.this.refQ);
      }

      public int hashCode() {
         Object key = this.get();
         return key != null ? key.hashCode() : 0;
      }

      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         } else {
            Object key = this.get();
            if (obj.getClass() != this.getClass()) {
               return key != null ? key.equals(obj) : false;
            } else {
               SoftReferenceKey rhs = (SoftReferenceKey)obj;
               Object rhsKey = rhs.get();
               return super.equals(obj) || key == rhsKey || key.equals(rhsKey);
            }
         }
      }
   }
}
