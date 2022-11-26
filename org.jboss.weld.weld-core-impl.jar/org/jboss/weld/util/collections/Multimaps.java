package org.jboss.weld.util.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Multimaps {
   public static final Multimap EMPTY_MULTIMAP = new EmptyMultimap();

   private Multimaps() {
   }

   public static Multimap unmodifiableMultimap(Multimap multimap) {
      return (Multimap)(multimap instanceof UnmodifiableMultimap ? multimap : new UnmodifiableMultimap(multimap));
   }

   public static Multimap emptyMultimap() {
      return EMPTY_MULTIMAP;
   }

   static Collection unmodifiableValueCollection(Collection values) {
      if (values instanceof Set) {
         return Collections.unmodifiableSet((Set)values);
      } else {
         return (Collection)(values instanceof List ? Collections.unmodifiableList((List)values) : Collections.unmodifiableCollection(values));
      }
   }

   static class EmptyMultimap implements Multimap, Serializable {
      private static final long serialVersionUID = -7386055586552408792L;

      public int size() {
         return 0;
      }

      public boolean isEmpty() {
         return true;
      }

      public Collection get(Object key) {
         return Collections.emptyList();
      }

      public boolean containsKey(Object key) {
         return false;
      }

      public Set keySet() {
         return Collections.emptySet();
      }

      public List values() {
         return Collections.emptyList();
      }

      public Set uniqueValues() {
         return Collections.emptySet();
      }

      public Set entrySet() {
         return Collections.emptySet();
      }

      public boolean put(Object key, Object value) {
         throw new UnsupportedOperationException();
      }

      public boolean putAll(Object key, Collection values) {
         throw new UnsupportedOperationException();
      }

      public Collection replaceValues(Object key, Iterable values) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public boolean equals(Object o) {
         return o instanceof Multimap && ((Multimap)o).isEmpty();
      }

      public int hashCode() {
         return 1;
      }

      private Object readResolve() {
         return Multimaps.EMPTY_MULTIMAP;
      }
   }

   static class UnmodifiableMultimap implements Multimap {
      private final Multimap delegate;

      public UnmodifiableMultimap(Multimap delegate) {
         this.delegate = delegate;
      }

      public int size() {
         return this.delegate.size();
      }

      public boolean isEmpty() {
         return this.delegate.isEmpty();
      }

      public Collection get(Object key) {
         return (Collection)(this.delegate.containsKey(key) ? Multimaps.unmodifiableValueCollection(this.delegate.get(key)) : Collections.emptyList());
      }

      public boolean put(Object key, Object value) {
         throw new UnsupportedOperationException();
      }

      public boolean containsKey(Object key) {
         return this.delegate.containsKey(key);
      }

      public Set keySet() {
         return this.delegate.keySet();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public List values() {
         return this.delegate.values();
      }

      public Set uniqueValues() {
         return this.delegate.uniqueValues();
      }

      public boolean putAll(Object key, Collection values) {
         throw new UnsupportedOperationException();
      }

      public Collection replaceValues(Object key, Iterable values) {
         throw new UnsupportedOperationException();
      }

      public Set entrySet() {
         return this.delegate.entrySet();
      }
   }
}
