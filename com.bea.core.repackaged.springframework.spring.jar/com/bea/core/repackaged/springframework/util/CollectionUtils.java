package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;

public abstract class CollectionUtils {
   public static boolean isEmpty(@Nullable Collection collection) {
      return collection == null || collection.isEmpty();
   }

   public static boolean isEmpty(@Nullable Map map) {
      return map == null || map.isEmpty();
   }

   public static List arrayToList(@Nullable Object source) {
      return Arrays.asList(ObjectUtils.toObjectArray(source));
   }

   public static void mergeArrayIntoCollection(@Nullable Object array, Collection collection) {
      Object[] arr = ObjectUtils.toObjectArray(array);
      Object[] var3 = arr;
      int var4 = arr.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object elem = var3[var5];
         collection.add(elem);
      }

   }

   public static void mergePropertiesIntoMap(@Nullable Properties props, Map map) {
      String key;
      Object value;
      if (props != null) {
         for(Enumeration en = props.propertyNames(); en.hasMoreElements(); map.put(key, value)) {
            key = (String)en.nextElement();
            value = props.get(key);
            if (value == null) {
               value = props.getProperty(key);
            }
         }
      }

   }

   public static boolean contains(@Nullable Iterator iterator, Object element) {
      if (iterator != null) {
         while(iterator.hasNext()) {
            Object candidate = iterator.next();
            if (ObjectUtils.nullSafeEquals(candidate, element)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean contains(@Nullable Enumeration enumeration, Object element) {
      if (enumeration != null) {
         while(enumeration.hasMoreElements()) {
            Object candidate = enumeration.nextElement();
            if (ObjectUtils.nullSafeEquals(candidate, element)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean containsInstance(@Nullable Collection collection, Object element) {
      if (collection != null) {
         Iterator var2 = collection.iterator();

         while(var2.hasNext()) {
            Object candidate = var2.next();
            if (candidate == element) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean containsAny(Collection source, Collection candidates) {
      if (!isEmpty(source) && !isEmpty(candidates)) {
         Iterator var2 = candidates.iterator();

         Object candidate;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            candidate = var2.next();
         } while(!source.contains(candidate));

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public static Object findFirstMatch(Collection source, Collection candidates) {
      if (!isEmpty(source) && !isEmpty(candidates)) {
         Iterator var2 = candidates.iterator();

         Object candidate;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            candidate = var2.next();
         } while(!source.contains(candidate));

         return candidate;
      } else {
         return null;
      }
   }

   @Nullable
   public static Object findValueOfType(Collection collection, @Nullable Class type) {
      if (isEmpty(collection)) {
         return null;
      } else {
         Object value = null;
         Iterator var3 = collection.iterator();

         while(true) {
            Object element;
            do {
               if (!var3.hasNext()) {
                  return value;
               }

               element = var3.next();
            } while(type != null && !type.isInstance(element));

            if (value != null) {
               return null;
            }

            value = element;
         }
      }
   }

   @Nullable
   public static Object findValueOfType(Collection collection, Class[] types) {
      if (!isEmpty(collection) && !ObjectUtils.isEmpty((Object[])types)) {
         Class[] var2 = types;
         int var3 = types.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class type = var2[var4];
            Object value = findValueOfType(collection, type);
            if (value != null) {
               return value;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static boolean hasUniqueObject(Collection collection) {
      if (isEmpty(collection)) {
         return false;
      } else {
         boolean hasCandidate = false;
         Object candidate = null;
         Iterator var3 = collection.iterator();

         while(var3.hasNext()) {
            Object elem = var3.next();
            if (!hasCandidate) {
               hasCandidate = true;
               candidate = elem;
            } else if (candidate != elem) {
               return false;
            }
         }

         return true;
      }
   }

   @Nullable
   public static Class findCommonElementType(Collection collection) {
      if (isEmpty(collection)) {
         return null;
      } else {
         Class candidate = null;
         Iterator var2 = collection.iterator();

         while(var2.hasNext()) {
            Object val = var2.next();
            if (val != null) {
               if (candidate == null) {
                  candidate = val.getClass();
               } else if (candidate != val.getClass()) {
                  return null;
               }
            }
         }

         return candidate;
      }
   }

   @Nullable
   public static Object lastElement(@Nullable Set set) {
      if (isEmpty((Collection)set)) {
         return null;
      } else if (set instanceof SortedSet) {
         return ((SortedSet)set).last();
      } else {
         Iterator it = set.iterator();

         Object last;
         for(last = null; it.hasNext(); last = it.next()) {
         }

         return last;
      }
   }

   @Nullable
   public static Object lastElement(@Nullable List list) {
      return isEmpty((Collection)list) ? null : list.get(list.size() - 1);
   }

   public static Object[] toArray(Enumeration enumeration, Object[] array) {
      ArrayList elements = new ArrayList();

      while(enumeration.hasMoreElements()) {
         elements.add(enumeration.nextElement());
      }

      return elements.toArray(array);
   }

   public static Iterator toIterator(@Nullable Enumeration enumeration) {
      return (Iterator)(enumeration != null ? new EnumerationIterator(enumeration) : Collections.emptyIterator());
   }

   public static MultiValueMap toMultiValueMap(Map map) {
      return new MultiValueMapAdapter(map);
   }

   public static MultiValueMap unmodifiableMultiValueMap(MultiValueMap map) {
      Assert.notNull(map, (String)"'map' must not be null");
      Map result = new LinkedHashMap(map.size());
      map.forEach((key, value) -> {
         List values = Collections.unmodifiableList(value);
         result.put(key, values);
      });
      Map unmodifiableMap = Collections.unmodifiableMap(result);
      return toMultiValueMap(unmodifiableMap);
   }

   private static class MultiValueMapAdapter implements MultiValueMap, Serializable {
      private final Map map;

      public MultiValueMapAdapter(Map map) {
         Assert.notNull(map, (String)"'map' must not be null");
         this.map = map;
      }

      @Nullable
      public Object getFirst(Object key) {
         List values = (List)this.map.get(key);
         return values != null ? values.get(0) : null;
      }

      public void add(Object key, @Nullable Object value) {
         List values = (List)this.map.computeIfAbsent(key, (k) -> {
            return new LinkedList();
         });
         values.add(value);
      }

      public void addAll(Object key, List values) {
         List currentValues = (List)this.map.computeIfAbsent(key, (k) -> {
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
         this.map.put(key, values);
      }

      public void setAll(Map values) {
         values.forEach(this::set);
      }

      public Map toSingleValueMap() {
         LinkedHashMap singleValueMap = new LinkedHashMap(this.map.size());
         this.map.forEach((key, value) -> {
            singleValueMap.put(key, value.get(0));
         });
         return singleValueMap;
      }

      public int size() {
         return this.map.size();
      }

      public boolean isEmpty() {
         return this.map.isEmpty();
      }

      public boolean containsKey(Object key) {
         return this.map.containsKey(key);
      }

      public boolean containsValue(Object value) {
         return this.map.containsValue(value);
      }

      public List get(Object key) {
         return (List)this.map.get(key);
      }

      public List put(Object key, List value) {
         return (List)this.map.put(key, value);
      }

      public List remove(Object key) {
         return (List)this.map.remove(key);
      }

      public void putAll(Map map) {
         this.map.putAll(map);
      }

      public void clear() {
         this.map.clear();
      }

      public Set keySet() {
         return this.map.keySet();
      }

      public Collection values() {
         return this.map.values();
      }

      public Set entrySet() {
         return this.map.entrySet();
      }

      public boolean equals(Object other) {
         return this == other ? true : this.map.equals(other);
      }

      public int hashCode() {
         return this.map.hashCode();
      }

      public String toString() {
         return this.map.toString();
      }
   }

   private static class EnumerationIterator implements Iterator {
      private final Enumeration enumeration;

      public EnumerationIterator(Enumeration enumeration) {
         this.enumeration = enumeration;
      }

      public boolean hasNext() {
         return this.enumeration.hasMoreElements();
      }

      public Object next() {
         return this.enumeration.nextElement();
      }

      public void remove() throws UnsupportedOperationException {
         throw new UnsupportedOperationException("Not supported");
      }
   }
}
