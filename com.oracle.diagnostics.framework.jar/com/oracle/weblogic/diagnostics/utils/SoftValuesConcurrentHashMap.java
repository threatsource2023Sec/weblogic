package com.oracle.weblogic.diagnostics.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SoftValuesConcurrentHashMap implements ConcurrentMap {
   private static final int CACHE_INIT_SIZE = 1024;
   private ConcurrentHashMap map = new ConcurrentHashMap(1024);
   private ReferenceQueue refQ = new ReferenceQueue();

   private void cleanup() {
      SoftReferenceValue ref = null;

      while((ref = (SoftReferenceValue)this.refQ.poll()) != null) {
         this.map.remove(ref.key);
      }

   }

   public Object put(Object key, Object value) {
      this.cleanup();
      SoftReferenceValue prev = (SoftReferenceValue)this.map.put(key, new SoftReferenceValue(key, value));
      return prev == null ? null : prev.get();
   }

   public Object putIfAbsent(Object key, Object value) {
      this.cleanup();
      return this.map.containsKey(value) ? null : this.put(key, value);
   }

   public Object get(Object key) {
      this.cleanup();
      SoftReferenceValue ref = (SoftReferenceValue)this.map.get(key);
      if (ref == null) {
         return null;
      } else if (ref.get() == null) {
         this.map.remove(key);
         return null;
      } else {
         return ref.get();
      }
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
      return this.map.containsKey(key);
   }

   public boolean containsValue(Object value) {
      this.cleanup();
      Iterator var2 = this.map.entrySet().iterator();

      Object entryVal;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         Map.Entry entry = (Map.Entry)var2.next();
         SoftReferenceValue entryRef = (SoftReferenceValue)entry.getValue();
         entryVal = entryRef.get();
      } while(entryVal == null || !entryVal.equals(value));

      return true;
   }

   public Object remove(Object key) {
      this.cleanup();
      SoftReferenceValue removed = (SoftReferenceValue)this.map.remove(key);
      return removed != null ? removed.get() : null;
   }

   public void putAll(Map m) {
      this.cleanup();
      Iterator var2 = m.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         Object value = entry.getValue();
         Object key = entry.getKey();
         this.map.put(key, new SoftReferenceValue(key, value));
      }

   }

   public Set keySet() {
      this.cleanup();
      return this.map.keySet();
   }

   public Collection values() {
      this.cleanup();
      List valuesList = new LinkedList();
      Collection valueRefs = this.map.values();
      Iterator var3 = valueRefs.iterator();

      while(var3.hasNext()) {
         SoftReferenceValue ref = (SoftReferenceValue)var3.next();
         Object refVal = ref.get();
         if (refVal != null) {
            valuesList.add(refVal);
         }
      }

      return valuesList;
   }

   public boolean remove(Object key, Object value) {
      if (this.map.containsKey(key)) {
         SoftReferenceValue ref = (SoftReferenceValue)this.map.get(key);
         Object val = ref != null ? ref.get() : null;
         if (val != null && val.equals(value)) {
            return this.map.remove(key) != null;
         }
      }

      return false;
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      throw new UnsupportedOperationException();
   }

   public Object replace(Object key, Object value) {
      throw new UnsupportedOperationException();
   }

   public Set entrySet() {
      throw new UnsupportedOperationException();
   }

   private class SoftReferenceValue extends SoftReference {
      final Object key;

      SoftReferenceValue(Object key, Object value) {
         super(value, SoftValuesConcurrentHashMap.this.refQ);
         this.key = key;
      }
   }
}
