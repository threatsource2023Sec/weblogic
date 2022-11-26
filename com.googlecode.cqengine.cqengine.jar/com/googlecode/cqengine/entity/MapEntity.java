package com.googlecode.cqengine.entity;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MapEntity implements Map {
   final Map wrappedMap;
   final int cachedHashCode;

   public MapEntity(Map mapToWrap) {
      this(mapToWrap, mapToWrap.hashCode());
   }

   protected MapEntity(Map mapToWrap, int hashCode) {
      this.wrappedMap = mapToWrap;
      this.cachedHashCode = hashCode;
   }

   public Map getWrappedMap() {
      return this.wrappedMap;
   }

   public int hashCode() {
      return this.cachedHashCode;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof MapEntity)) {
         return false;
      } else {
         MapEntity that = (MapEntity)o;
         return this.cachedHashCode != that.cachedHashCode ? false : this.wrappedMap.equals(that.wrappedMap);
      }
   }

   public String toString() {
      return "MapEntity{cachedHashCode=" + this.cachedHashCode + ", wrappedMap=" + this.wrappedMap + '}';
   }

   public Object get(Object key) {
      return this.wrappedMap.get(key);
   }

   public Object put(Object o, Object o2) {
      return this.wrappedMap.put(o, o2);
   }

   public Object remove(Object o) {
      return this.wrappedMap.remove(o);
   }

   public void putAll(Map map) {
      this.wrappedMap.putAll(map);
   }

   public void clear() {
      this.wrappedMap.clear();
   }

   public Set keySet() {
      return this.wrappedMap.keySet();
   }

   public Collection values() {
      return this.wrappedMap.values();
   }

   public Set entrySet() {
      return this.wrappedMap.entrySet();
   }

   public int size() {
      return this.wrappedMap.size();
   }

   public boolean isEmpty() {
      return this.wrappedMap.isEmpty();
   }

   public boolean containsKey(Object o) {
      return this.wrappedMap.containsKey(o);
   }

   public boolean containsValue(Object o) {
      return this.wrappedMap.containsValue(o);
   }
}
