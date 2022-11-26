package org.python.google.common.collect;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
abstract class AbstractMultimap implements Multimap {
   private transient Collection entries;
   private transient Set keySet;
   private transient Multiset keys;
   private transient Collection values;
   private transient Map asMap;

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public boolean containsValue(@Nullable Object value) {
      Iterator var2 = this.asMap().values().iterator();

      Collection collection;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         collection = (Collection)var2.next();
      } while(!collection.contains(value));

      return true;
   }

   public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
      Collection collection = (Collection)this.asMap().get(key);
      return collection != null && collection.contains(value);
   }

   @CanIgnoreReturnValue
   public boolean remove(@Nullable Object key, @Nullable Object value) {
      Collection collection = (Collection)this.asMap().get(key);
      return collection != null && collection.remove(value);
   }

   @CanIgnoreReturnValue
   public boolean put(@Nullable Object key, @Nullable Object value) {
      return this.get(key).add(value);
   }

   @CanIgnoreReturnValue
   public boolean putAll(@Nullable Object key, Iterable values) {
      Preconditions.checkNotNull(values);
      if (values instanceof Collection) {
         Collection valueCollection = (Collection)values;
         return !valueCollection.isEmpty() && this.get(key).addAll(valueCollection);
      } else {
         Iterator valueItr = values.iterator();
         return valueItr.hasNext() && Iterators.addAll(this.get(key), valueItr);
      }
   }

   @CanIgnoreReturnValue
   public boolean putAll(Multimap multimap) {
      boolean changed = false;

      Map.Entry entry;
      for(Iterator var3 = multimap.entries().iterator(); var3.hasNext(); changed |= this.put(entry.getKey(), entry.getValue())) {
         entry = (Map.Entry)var3.next();
      }

      return changed;
   }

   @CanIgnoreReturnValue
   public Collection replaceValues(@Nullable Object key, Iterable values) {
      Preconditions.checkNotNull(values);
      Collection result = this.removeAll(key);
      this.putAll(key, values);
      return result;
   }

   public Collection entries() {
      Collection result = this.entries;
      return result == null ? (this.entries = this.createEntries()) : result;
   }

   Collection createEntries() {
      return (Collection)(this instanceof SetMultimap ? new EntrySet() : new Entries());
   }

   abstract Iterator entryIterator();

   public Set keySet() {
      Set result = this.keySet;
      return result == null ? (this.keySet = this.createKeySet()) : result;
   }

   Set createKeySet() {
      return new Maps.KeySet(this.asMap());
   }

   public Multiset keys() {
      Multiset result = this.keys;
      return result == null ? (this.keys = this.createKeys()) : result;
   }

   Multiset createKeys() {
      return new Multimaps.Keys(this);
   }

   public Collection values() {
      Collection result = this.values;
      return result == null ? (this.values = this.createValues()) : result;
   }

   Collection createValues() {
      return new Values();
   }

   Iterator valueIterator() {
      return Maps.valueIterator(this.entries().iterator());
   }

   public Map asMap() {
      Map result = this.asMap;
      return result == null ? (this.asMap = this.createAsMap()) : result;
   }

   abstract Map createAsMap();

   public boolean equals(@Nullable Object object) {
      return Multimaps.equalsImpl(this, object);
   }

   public int hashCode() {
      return this.asMap().hashCode();
   }

   public String toString() {
      return this.asMap().toString();
   }

   class Values extends AbstractCollection {
      public Iterator iterator() {
         return AbstractMultimap.this.valueIterator();
      }

      public int size() {
         return AbstractMultimap.this.size();
      }

      public boolean contains(@Nullable Object o) {
         return AbstractMultimap.this.containsValue(o);
      }

      public void clear() {
         AbstractMultimap.this.clear();
      }
   }

   private class EntrySet extends Entries implements Set {
      private EntrySet() {
         super(null);
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }

      public boolean equals(@Nullable Object obj) {
         return Sets.equalsImpl(this, obj);
      }

      // $FF: synthetic method
      EntrySet(Object x1) {
         this();
      }
   }

   private class Entries extends Multimaps.Entries {
      private Entries() {
      }

      Multimap multimap() {
         return AbstractMultimap.this;
      }

      public Iterator iterator() {
         return AbstractMultimap.this.entryIterator();
      }

      // $FF: synthetic method
      Entries(Object x1) {
         this();
      }
   }
}
