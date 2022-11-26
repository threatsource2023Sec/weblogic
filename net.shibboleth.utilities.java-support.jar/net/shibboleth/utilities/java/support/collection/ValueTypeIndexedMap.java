package net.shibboleth.utilities.java.support.collection;

import com.google.common.annotations.Beta;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Beta
public class ValueTypeIndexedMap implements Map {
   private Map index;
   private Map map;
   private Set types;

   public ValueTypeIndexedMap() {
      this(new HashSet());
   }

   public ValueTypeIndexedMap(Map newMap, Collection newTypes) {
      this.map = newMap;
      this.types = new HashSet(newTypes);
      this.index = new HashMap();
      this.rebuildIndex();
   }

   public ValueTypeIndexedMap(Collection newTypes) {
      this(new HashMap(), newTypes);
   }

   public void clear() {
      this.map.clear();
      this.rebuildIndex();
   }

   public boolean containsKey(Object key) {
      return this.map.containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.map.containsValue(value);
   }

   public Set entrySet() {
      return this.map.entrySet();
   }

   public Object get(Object key) {
      return this.map.get(key);
   }

   public Set getTypes() {
      return this.types;
   }

   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   public Set keySet() {
      return this.map.keySet();
   }

   private Boolean matchType(Class type, Object object) {
      return type.isInstance(object) || type == NullValue.class && object == null;
   }

   public Object put(Object key, Object value) {
      Object oldValue = this.map.put(key, value);
      Iterator i$ = this.index.keySet().iterator();

      while(i$.hasNext()) {
         Class clazz = (Class)i$.next();
         Class type;
         if (clazz == null) {
            type = NullValue.class;
         } else {
            type = clazz;
         }

         if (this.matchType(type, value)) {
            ((Map)this.index.get(type)).put(key, value);
         } else if (this.matchType(type, oldValue)) {
            ((Map)this.index.get(type)).remove(key);
         }
      }

      return oldValue;
   }

   public void putAll(Map t) {
      Iterator i$ = t.keySet().iterator();

      while(i$.hasNext()) {
         Object key = i$.next();
         this.put(key, t.get(key));
      }

   }

   public void rebuildIndex() {
      this.index.clear();
      Iterator i$ = this.types.iterator();

      while(i$.hasNext()) {
         Class clazz = (Class)i$.next();
         Class type;
         if (clazz == null) {
            type = NullValue.class;
         } else {
            type = clazz;
         }

         this.index.put(type, new HashMap());
         Iterator i$ = this.map.keySet().iterator();

         while(i$.hasNext()) {
            Object key = i$.next();
            Object value = this.map.get(key);
            if (this.matchType(type, value)) {
               ((Map)this.index.get(type)).put(key, value);
            }
         }
      }

   }

   public Object remove(Object key) {
      Object value = this.map.remove(key);
      Iterator i$ = this.index.keySet().iterator();

      while(i$.hasNext()) {
         Class type = (Class)i$.next();
         if (type.isInstance(value)) {
            ((Map)this.index.get(type)).remove(key);
         }
      }

      return value;
   }

   public void setTypes(Collection newTypes) {
      this.types = new HashSet(newTypes);
   }

   public int size() {
      return this.map.size();
   }

   public Map subMap(Class type) {
      Class key = type;
      if (type == null) {
         key = NullValue.class;
      }

      return this.index.containsKey(key) ? Collections.unmodifiableMap((Map)this.index.get(key)) : Collections.emptyMap();
   }

   public int hashCode() {
      return this.map.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj != null && this.getClass() == obj.getClass() ? this.map.equals(((ValueTypeIndexedMap)obj).map) : false;
      }
   }

   public String toString() {
      return this.map.toString();
   }

   public Collection values() {
      return this.map.values();
   }

   private static class NullValue {
   }
}
