package org.jboss.weld.util.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import org.jboss.weld.util.Preconditions;

abstract class AbstractMultimap implements Multimap, Serializable {
   private static final long serialVersionUID = -8363450390652782067L;
   protected final Supplier supplier;
   private final Map map;

   protected AbstractMultimap(Supplier mapSupplier, Supplier collectionSupplier, Multimap multimap) {
      Preconditions.checkArgumentNotNull(mapSupplier, "mapSupplier");
      Preconditions.checkArgumentNotNull(collectionSupplier, "collectionSupplier");
      this.supplier = collectionSupplier;
      this.map = (Map)mapSupplier.get();
      if (multimap != null) {
         Iterator var4 = multimap.entrySet().iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            Collection values = (Collection)this.supplier.get();
            values.addAll((Collection)entry.getValue());
            this.putAll(entry.getKey(), values);
         }
      }

   }

   public int size() {
      return this.map.size();
   }

   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   public Collection get(Object key) {
      return (Collection)this.map.computeIfAbsent(key, (k) -> {
         return (Collection)this.supplier.get();
      });
   }

   public boolean put(Object key, Object value) {
      return this.get(key).add(value);
   }

   public boolean putAll(Object key, Collection values) {
      return this.get(key).addAll(values);
   }

   public Collection replaceValues(Object key, Iterable values) {
      Collection replacement = (Collection)this.supplier.get();
      Iterables.addAll(replacement, values);
      return (Collection)this.map.put(key, replacement);
   }

   public boolean containsKey(Object key) {
      return this.map.containsKey(key);
   }

   public Set keySet() {
      return ImmutableSet.copyOf(this.map.keySet());
   }

   public List values() {
      return ImmutableList.copyOf(Iterables.concat(this.map.values()));
   }

   public Set uniqueValues() {
      ImmutableSet.Builder builder = ImmutableSet.builder();
      Iterator var2 = this.map.values().iterator();

      while(var2.hasNext()) {
         Collection values = (Collection)var2.next();
         builder.addAll((Iterable)values);
      }

      return builder.build();
   }

   public Set entrySet() {
      ImmutableSet.Builder builder = ImmutableSet.builder();
      Iterator var2 = this.map.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         builder.add(new MultimapEntry(entry.getKey(), Multimaps.unmodifiableValueCollection((Collection)entry.getValue())));
      }

      return builder.build();
   }

   public void clear() {
      this.map.clear();
   }

   public String toString() {
      return this.map.toString();
   }

   static class MultimapEntry implements Map.Entry {
      private final Object key;
      private final Object value;

      public MultimapEntry(Object key, Object value) {
         this.key = key;
         this.value = value;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         throw new UnsupportedOperationException();
      }

      public int hashCode() {
         return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry e = (Map.Entry)o;
            return Objects.equals(this.key, e.getKey()) && Objects.equals(this.value, e.getValue());
         }
      }

      public String toString() {
         return this.key + "=" + this.value;
      }
   }
}
