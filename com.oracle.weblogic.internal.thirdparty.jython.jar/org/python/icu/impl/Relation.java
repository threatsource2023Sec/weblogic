package org.python.icu.impl;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.python.icu.util.Freezable;

public class Relation implements Freezable {
   private Map data;
   Constructor setCreator;
   Object[] setComparatorParam;
   volatile boolean frozen;

   public static Relation of(Map map, Class setCreator) {
      return new Relation(map, setCreator);
   }

   public static Relation of(Map map, Class setCreator, Comparator setComparator) {
      return new Relation(map, setCreator, setComparator);
   }

   public Relation(Map map, Class setCreator) {
      this(map, setCreator, (Comparator)null);
   }

   public Relation(Map map, Class setCreator, Comparator setComparator) {
      this.frozen = false;

      try {
         this.setComparatorParam = setComparator == null ? null : new Object[]{setComparator};
         if (setComparator == null) {
            this.setCreator = setCreator.getConstructor();
            this.setCreator.newInstance(this.setComparatorParam);
         } else {
            this.setCreator = setCreator.getConstructor(Comparator.class);
            this.setCreator.newInstance(this.setComparatorParam);
         }

         this.data = (Map)(map == null ? new HashMap() : map);
      } catch (Exception var5) {
         throw (RuntimeException)(new IllegalArgumentException("Can't create new set")).initCause(var5);
      }
   }

   public void clear() {
      this.data.clear();
   }

   public boolean containsKey(Object key) {
      return this.data.containsKey(key);
   }

   public boolean containsValue(Object value) {
      Iterator var2 = this.data.values().iterator();

      Set values;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         values = (Set)var2.next();
      } while(!values.contains(value));

      return true;
   }

   public final Set entrySet() {
      return this.keyValueSet();
   }

   public Set keyValuesSet() {
      return this.data.entrySet();
   }

   public Set keyValueSet() {
      Set result = new LinkedHashSet();
      Iterator var2 = this.data.keySet().iterator();

      while(var2.hasNext()) {
         Object key = var2.next();
         Iterator var4 = ((Set)this.data.get(key)).iterator();

         while(var4.hasNext()) {
            Object value = var4.next();
            result.add(new SimpleEntry(key, value));
         }
      }

      return result;
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else {
         return o.getClass() != this.getClass() ? false : this.data.equals(((Relation)o).data);
      }
   }

   public Set getAll(Object key) {
      return (Set)this.data.get(key);
   }

   public Set get(Object key) {
      return (Set)this.data.get(key);
   }

   public int hashCode() {
      return this.data.hashCode();
   }

   public boolean isEmpty() {
      return this.data.isEmpty();
   }

   public Set keySet() {
      return this.data.keySet();
   }

   public Object put(Object key, Object value) {
      Set set = (Set)this.data.get(key);
      if (set == null) {
         this.data.put(key, set = this.newSet());
      }

      set.add(value);
      return value;
   }

   public Object putAll(Object key, Collection values) {
      Set set = (Set)this.data.get(key);
      if (set == null) {
         this.data.put(key, set = this.newSet());
      }

      set.addAll(values);
      return values.size() == 0 ? null : values.iterator().next();
   }

   public Object putAll(Collection keys, Object value) {
      Object result = null;

      Object key;
      for(Iterator var4 = keys.iterator(); var4.hasNext(); result = this.put(key, value)) {
         key = var4.next();
      }

      return result;
   }

   private Set newSet() {
      try {
         return (Set)this.setCreator.newInstance(this.setComparatorParam);
      } catch (Exception var2) {
         throw (RuntimeException)(new IllegalArgumentException("Can't create new set")).initCause(var2);
      }
   }

   public void putAll(Map t) {
      Iterator var2 = t.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.put(entry.getKey(), entry.getValue());
      }

   }

   public void putAll(Relation t) {
      Iterator var2 = t.keySet().iterator();

      while(var2.hasNext()) {
         Object key = var2.next();
         Iterator var4 = t.getAll(key).iterator();

         while(var4.hasNext()) {
            Object value = var4.next();
            this.put(key, value);
         }
      }

   }

   public Set removeAll(Object key) {
      try {
         return (Set)this.data.remove(key);
      } catch (NullPointerException var3) {
         return null;
      }
   }

   public boolean remove(Object key, Object value) {
      try {
         Set set = (Set)this.data.get(key);
         if (set == null) {
            return false;
         } else {
            boolean result = set.remove(value);
            if (set.size() == 0) {
               this.data.remove(key);
            }

            return result;
         }
      } catch (NullPointerException var5) {
         return false;
      }
   }

   public int size() {
      return this.data.size();
   }

   public Set values() {
      return (Set)this.values(new LinkedHashSet());
   }

   public Collection values(Collection result) {
      Iterator var2 = this.data.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry keyValue = (Map.Entry)var2.next();
         result.addAll((Collection)keyValue.getValue());
      }

      return result;
   }

   public String toString() {
      return this.data.toString();
   }

   public Relation addAllInverted(Relation source) {
      Iterator var2 = source.data.keySet().iterator();

      while(var2.hasNext()) {
         Object value = var2.next();
         Iterator var4 = ((Set)source.data.get(value)).iterator();

         while(var4.hasNext()) {
            Object key = var4.next();
            this.put(key, value);
         }
      }

      return this;
   }

   public Relation addAllInverted(Map source) {
      Iterator var2 = source.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.put(entry.getValue(), entry.getKey());
      }

      return this;
   }

   public boolean isFrozen() {
      return this.frozen;
   }

   public Relation freeze() {
      if (!this.frozen) {
         Iterator var1 = this.data.keySet().iterator();

         while(var1.hasNext()) {
            Object key = var1.next();
            this.data.put(key, Collections.unmodifiableSet((Set)this.data.get(key)));
         }

         this.data = Collections.unmodifiableMap(this.data);
         this.frozen = true;
      }

      return this;
   }

   public Relation cloneAsThawed() {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Relation toBeRemoved) {
      boolean result = false;
      Iterator var3 = toBeRemoved.keySet().iterator();

      while(var3.hasNext()) {
         Object key = var3.next();

         try {
            Set values = toBeRemoved.getAll(key);
            if (values != null) {
               result |= this.removeAll(key, values);
            }
         } catch (NullPointerException var6) {
         }
      }

      return result;
   }

   public Set removeAll(Object... keys) {
      return this.removeAll((Collection)Arrays.asList(keys));
   }

   public boolean removeAll(Object key, Iterable toBeRemoved) {
      boolean result = false;

      Object value;
      for(Iterator var4 = toBeRemoved.iterator(); var4.hasNext(); result |= this.remove(key, value)) {
         value = var4.next();
      }

      return result;
   }

   public Set removeAll(Collection toBeRemoved) {
      Set result = new LinkedHashSet();
      Iterator var3 = toBeRemoved.iterator();

      while(var3.hasNext()) {
         Object key = var3.next();

         try {
            Set removals = (Set)this.data.remove(key);
            if (removals != null) {
               result.addAll(removals);
            }
         } catch (NullPointerException var6) {
         }
      }

      return result;
   }

   static class SimpleEntry implements Map.Entry {
      Object key;
      Object value;

      public SimpleEntry(Object key, Object value) {
         this.key = key;
         this.value = value;
      }

      public SimpleEntry(Map.Entry e) {
         this.key = e.getKey();
         this.value = e.getValue();
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         Object oldValue = this.value;
         this.value = value;
         return oldValue;
      }
   }
}
