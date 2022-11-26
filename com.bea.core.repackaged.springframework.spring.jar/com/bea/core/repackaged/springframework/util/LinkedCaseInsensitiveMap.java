package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class LinkedCaseInsensitiveMap implements Map, Serializable, Cloneable {
   private final LinkedHashMap targetMap;
   private final HashMap caseInsensitiveKeys;
   private final Locale locale;
   @Nullable
   private transient volatile Set keySet;
   @Nullable
   private transient volatile Collection values;
   @Nullable
   private transient volatile Set entrySet;

   public LinkedCaseInsensitiveMap() {
      this((Locale)null);
   }

   public LinkedCaseInsensitiveMap(@Nullable Locale locale) {
      this(16, locale);
   }

   public LinkedCaseInsensitiveMap(int initialCapacity) {
      this(initialCapacity, (Locale)null);
   }

   public LinkedCaseInsensitiveMap(int initialCapacity, @Nullable Locale locale) {
      this.targetMap = new LinkedHashMap(initialCapacity) {
         public boolean containsKey(Object key) {
            return LinkedCaseInsensitiveMap.this.containsKey(key);
         }

         protected boolean removeEldestEntry(Map.Entry eldest) {
            boolean doRemove = LinkedCaseInsensitiveMap.this.removeEldestEntry(eldest);
            if (doRemove) {
               LinkedCaseInsensitiveMap.this.removeCaseInsensitiveKey((String)eldest.getKey());
            }

            return doRemove;
         }
      };
      this.caseInsensitiveKeys = new HashMap(initialCapacity);
      this.locale = locale != null ? locale : Locale.getDefault();
   }

   private LinkedCaseInsensitiveMap(LinkedCaseInsensitiveMap other) {
      this.targetMap = (LinkedHashMap)other.targetMap.clone();
      this.caseInsensitiveKeys = (HashMap)other.caseInsensitiveKeys.clone();
      this.locale = other.locale;
   }

   public int size() {
      return this.targetMap.size();
   }

   public boolean isEmpty() {
      return this.targetMap.isEmpty();
   }

   public boolean containsKey(Object key) {
      return key instanceof String && this.caseInsensitiveKeys.containsKey(this.convertKey((String)key));
   }

   public boolean containsValue(Object value) {
      return this.targetMap.containsValue(value);
   }

   @Nullable
   public Object get(Object key) {
      if (key instanceof String) {
         String caseInsensitiveKey = (String)this.caseInsensitiveKeys.get(this.convertKey((String)key));
         if (caseInsensitiveKey != null) {
            return this.targetMap.get(caseInsensitiveKey);
         }
      }

      return null;
   }

   @Nullable
   public Object getOrDefault(Object key, Object defaultValue) {
      if (key instanceof String) {
         String caseInsensitiveKey = (String)this.caseInsensitiveKeys.get(this.convertKey((String)key));
         if (caseInsensitiveKey != null) {
            return this.targetMap.get(caseInsensitiveKey);
         }
      }

      return defaultValue;
   }

   @Nullable
   public Object put(String key, @Nullable Object value) {
      String oldKey = (String)this.caseInsensitiveKeys.put(this.convertKey(key), key);
      Object oldKeyValue = null;
      if (oldKey != null && !oldKey.equals(key)) {
         oldKeyValue = this.targetMap.remove(oldKey);
      }

      Object oldValue = this.targetMap.put(key, value);
      return oldKeyValue != null ? oldKeyValue : oldValue;
   }

   public void putAll(Map map) {
      if (!map.isEmpty()) {
         map.forEach(this::put);
      }
   }

   @Nullable
   public Object putIfAbsent(String key, @Nullable Object value) {
      String oldKey = (String)this.caseInsensitiveKeys.putIfAbsent(this.convertKey(key), key);
      return oldKey != null ? this.targetMap.get(oldKey) : this.targetMap.putIfAbsent(key, value);
   }

   @Nullable
   public Object computeIfAbsent(String key, Function mappingFunction) {
      String oldKey = (String)this.caseInsensitiveKeys.putIfAbsent(this.convertKey(key), key);
      return oldKey != null ? this.targetMap.get(oldKey) : this.targetMap.computeIfAbsent(key, mappingFunction);
   }

   @Nullable
   public Object remove(Object key) {
      if (key instanceof String) {
         String caseInsensitiveKey = this.removeCaseInsensitiveKey((String)key);
         if (caseInsensitiveKey != null) {
            return this.targetMap.remove(caseInsensitiveKey);
         }
      }

      return null;
   }

   public void clear() {
      this.caseInsensitiveKeys.clear();
      this.targetMap.clear();
   }

   public Set keySet() {
      Set keySet = this.keySet;
      if (keySet == null) {
         keySet = new KeySet(this.targetMap.keySet());
         this.keySet = (Set)keySet;
      }

      return (Set)keySet;
   }

   public Collection values() {
      Collection values = this.values;
      if (values == null) {
         values = new Values(this.targetMap.values());
         this.values = (Collection)values;
      }

      return (Collection)values;
   }

   public Set entrySet() {
      Set entrySet = this.entrySet;
      if (entrySet == null) {
         entrySet = new EntrySet(this.targetMap.entrySet());
         this.entrySet = (Set)entrySet;
      }

      return (Set)entrySet;
   }

   public LinkedCaseInsensitiveMap clone() {
      return new LinkedCaseInsensitiveMap(this);
   }

   public boolean equals(@Nullable Object obj) {
      return this.targetMap.equals(obj);
   }

   public int hashCode() {
      return this.targetMap.hashCode();
   }

   public String toString() {
      return this.targetMap.toString();
   }

   public Locale getLocale() {
      return this.locale;
   }

   protected String convertKey(String key) {
      return key.toLowerCase(this.getLocale());
   }

   protected boolean removeEldestEntry(Map.Entry eldest) {
      return false;
   }

   @Nullable
   private String removeCaseInsensitiveKey(String key) {
      return (String)this.caseInsensitiveKeys.remove(this.convertKey(key));
   }

   private class EntrySetIterator extends EntryIterator {
      private EntrySetIterator() {
         super();
      }

      public Map.Entry next() {
         return this.nextEntry();
      }

      // $FF: synthetic method
      EntrySetIterator(Object x1) {
         this();
      }
   }

   private class ValuesIterator extends EntryIterator {
      private ValuesIterator() {
         super();
      }

      public Object next() {
         return this.nextEntry().getValue();
      }

      // $FF: synthetic method
      ValuesIterator(Object x1) {
         this();
      }
   }

   private class KeySetIterator extends EntryIterator {
      private KeySetIterator() {
         super();
      }

      public String next() {
         return (String)this.nextEntry().getKey();
      }

      // $FF: synthetic method
      KeySetIterator(Object x1) {
         this();
      }
   }

   private abstract class EntryIterator implements Iterator {
      private final Iterator delegate;
      @Nullable
      private Map.Entry last;

      public EntryIterator() {
         this.delegate = LinkedCaseInsensitiveMap.this.targetMap.entrySet().iterator();
      }

      protected Map.Entry nextEntry() {
         Map.Entry entry = (Map.Entry)this.delegate.next();
         this.last = entry;
         return entry;
      }

      public boolean hasNext() {
         return this.delegate.hasNext();
      }

      public void remove() {
         this.delegate.remove();
         if (this.last != null) {
            LinkedCaseInsensitiveMap.this.removeCaseInsensitiveKey((String)this.last.getKey());
            this.last = null;
         }

      }
   }

   private class EntrySet extends AbstractSet {
      private final Set delegate;

      public EntrySet(Set delegate) {
         this.delegate = delegate;
      }

      public int size() {
         return this.delegate.size();
      }

      public boolean contains(Object o) {
         return this.delegate.contains(o);
      }

      public Iterator iterator() {
         return LinkedCaseInsensitiveMap.this.new EntrySetIterator();
      }

      public boolean remove(Object o) {
         if (this.delegate.remove(o)) {
            LinkedCaseInsensitiveMap.this.removeCaseInsensitiveKey((String)((Map.Entry)o).getKey());
            return true;
         } else {
            return false;
         }
      }

      public void clear() {
         this.delegate.clear();
         LinkedCaseInsensitiveMap.this.caseInsensitiveKeys.clear();
      }

      public Spliterator spliterator() {
         return this.delegate.spliterator();
      }

      public void forEach(Consumer action) {
         this.delegate.forEach(action);
      }
   }

   private class Values extends AbstractCollection {
      private final Collection delegate;

      Values(Collection delegate) {
         this.delegate = delegate;
      }

      public int size() {
         return this.delegate.size();
      }

      public boolean contains(Object o) {
         return this.delegate.contains(o);
      }

      public Iterator iterator() {
         return LinkedCaseInsensitiveMap.this.new ValuesIterator();
      }

      public void clear() {
         LinkedCaseInsensitiveMap.this.clear();
      }

      public Spliterator spliterator() {
         return this.delegate.spliterator();
      }

      public void forEach(Consumer action) {
         this.delegate.forEach(action);
      }
   }

   private class KeySet extends AbstractSet {
      private final Set delegate;

      KeySet(Set delegate) {
         this.delegate = delegate;
      }

      public int size() {
         return this.delegate.size();
      }

      public boolean contains(Object o) {
         return this.delegate.contains(o);
      }

      public Iterator iterator() {
         return LinkedCaseInsensitiveMap.this.new KeySetIterator();
      }

      public boolean remove(Object o) {
         return LinkedCaseInsensitiveMap.this.remove(o) != null;
      }

      public void clear() {
         LinkedCaseInsensitiveMap.this.clear();
      }

      public Spliterator spliterator() {
         return this.delegate.spliterator();
      }

      public void forEach(Consumer action) {
         this.delegate.forEach(action);
      }
   }
}
