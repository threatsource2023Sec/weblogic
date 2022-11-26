package weblogic.utils.collections;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

public class CombinedSet extends AbstractSet {
   private final Collection[] sets;

   public CombinedSet(Collection[] sets) {
      this.sets = sets;
   }

   public int size() {
      int n = 0;

      for(int i = 0; i < this.sets.length; ++i) {
         n += this.sets[i].size();
      }

      return n;
   }

   public boolean isEmpty() {
      for(int i = 0; i < this.sets.length; ++i) {
         if (!this.sets[i].isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public boolean contains(Object o) {
      for(int i = 0; i < this.sets.length; ++i) {
         if (this.sets[i].contains(o)) {
            return true;
         }
      }

      return false;
   }

   public Iterator iterator() {
      Iterator[] iterators = new Iterator[this.sets.length];

      for(int i = 0; i < this.sets.length; ++i) {
         iterators[i] = this.sets[i].iterator();
      }

      return new CombinedIterator(iterators);
   }

   public boolean add(Object o) {
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
}
