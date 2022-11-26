package org.jboss.weld.util.collections;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;

abstract class AbstractImmutableSet extends AbstractSet implements Set {
   public boolean add(Object e) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean isEmpty() {
      return false;
   }

   public boolean containsAll(Collection c) {
      Iterator var2 = c.iterator();

      Object item;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         item = var2.next();
      } while(this.contains(item));

      return false;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj instanceof Set ? this.equalsSet((Set)obj) : false;
      }
   }

   boolean equalsSet(Set that) {
      return this.size() == that.size() && that.containsAll(this);
   }

   public Spliterator spliterator() {
      return Spliterators.spliterator(this, 1281);
   }
}
