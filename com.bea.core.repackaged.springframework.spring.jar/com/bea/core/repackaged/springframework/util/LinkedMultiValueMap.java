package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LinkedMultiValueMap implements MultiValueMap, Serializable, Cloneable {
   private static final long serialVersionUID = 3801124242820219131L;
   private final Map targetMap;

   public LinkedMultiValueMap() {
      this.targetMap = new LinkedHashMap();
   }

   public LinkedMultiValueMap(int initialCapacity) {
      this.targetMap = new LinkedHashMap(initialCapacity);
   }

   public LinkedMultiValueMap(Map otherMap) {
      this.targetMap = new LinkedHashMap(otherMap);
   }

   @Nullable
   public Object getFirst(Object key) {
      List values = (List)this.targetMap.get(key);
      return values != null && !values.isEmpty() ? values.get(0) : null;
   }

   public void add(Object key, @Nullable Object value) {
      List values = (List)this.targetMap.computeIfAbsent(key, (k) -> {
         return new LinkedList();
      });
      values.add(value);
   }

   public void addAll(Object key, List values) {
      List currentValues = (List)this.targetMap.computeIfAbsent(key, (k) -> {
         return new LinkedList();
      });
      currentValues.addAll(values);
   }

   public void addAll(MultiValueMap values) {
      Iterator var2 = values.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.addAll(entry.getKey(), (List)entry.getValue());
      }

   }

   public void set(Object key, @Nullable Object value) {
      List values = new LinkedList();
      values.add(value);
      this.targetMap.put(key, values);
   }

   public void setAll(Map values) {
      values.forEach(this::set);
   }

   public Map toSingleValueMap() {
      LinkedHashMap singleValueMap = new LinkedHashMap(this.targetMap.size());
      this.targetMap.forEach((key, values) -> {
         if (values != null && !values.isEmpty()) {
            singleValueMap.put(key, values.get(0));
         }

      });
      return singleValueMap;
   }

   public int size() {
      return this.targetMap.size();
   }

   public boolean isEmpty() {
      return this.targetMap.isEmpty();
   }

   public boolean containsKey(Object key) {
      return this.targetMap.containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.targetMap.containsValue(value);
   }

   @Nullable
   public List get(Object key) {
      return (List)this.targetMap.get(key);
   }

   @Nullable
   public List put(Object key, List value) {
      return (List)this.targetMap.put(key, value);
   }

   @Nullable
   public List remove(Object key) {
      return (List)this.targetMap.remove(key);
   }

   public void putAll(Map map) {
      this.targetMap.putAll(map);
   }

   public void clear() {
      this.targetMap.clear();
   }

   public Set keySet() {
      return this.targetMap.keySet();
   }

   public Collection values() {
      return this.targetMap.values();
   }

   public Set entrySet() {
      return this.targetMap.entrySet();
   }

   public LinkedMultiValueMap deepCopy() {
      LinkedMultiValueMap copy = new LinkedMultiValueMap(this.targetMap.size());
      this.targetMap.forEach((key, value) -> {
         copy.put(key, (List)(new LinkedList(value)));
      });
      return copy;
   }

   public LinkedMultiValueMap clone() {
      return new LinkedMultiValueMap(this);
   }

   public boolean equals(Object obj) {
      return this.targetMap.equals(obj);
   }

   public int hashCode() {
      return this.targetMap.hashCode();
   }

   public String toString() {
      return this.targetMap.toString();
   }
}
