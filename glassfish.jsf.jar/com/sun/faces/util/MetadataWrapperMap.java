package com.sun.faces.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MetadataWrapperMap implements Map {
   private Map wrapped;
   private Map metadata;

   public MetadataWrapperMap(Map toWrap) {
      this.wrapped = toWrap;
      this.metadata = new ConcurrentHashMap();
   }

   protected Map getMetadata() {
      return this.metadata;
   }

   public void clear() {
      this.wrapped.clear();
   }

   public boolean containsKey(Object key) {
      return this.wrapped.containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.wrapped.containsValue(value);
   }

   public Set entrySet() {
      return this.wrapped.entrySet();
   }

   public Object get(Object key) {
      return this.wrapped.get(key);
   }

   public boolean isEmpty() {
      return this.wrapped.isEmpty();
   }

   public Set keySet() {
      return this.wrapped.keySet();
   }

   public Object put(Object key, Object value) {
      this.onPut(key, value);
      return this.wrapped.put(key, value);
   }

   protected abstract Object onPut(Object var1, Object var2);

   public void putAll(Map m) {
      this.wrapped.putAll(m);
   }

   public Object remove(Object key) {
      return this.wrapped.remove(key);
   }

   public int size() {
      return this.wrapped.size();
   }

   public Collection values() {
      return this.wrapped.values();
   }
}
