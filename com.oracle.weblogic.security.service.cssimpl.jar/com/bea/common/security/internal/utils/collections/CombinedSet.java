package com.bea.common.security.internal.utils.collections;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

class CombinedSet extends AbstractSet {
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
      return new CombinedIterator();
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

   private class CombinedIterator implements Iterator {
      private int setIndex;
      private Iterator iterator;
      private Iterator lastIterator;

      private CombinedIterator() {
         this.setIndex = 0;
         this.iterator = null;
         this.lastIterator = null;
      }

      public boolean hasNext() {
         while(this.setIndex < CombinedSet.this.sets.length) {
            if (this.iterator == null) {
               this.iterator = CombinedSet.this.sets[this.setIndex].iterator();
            }

            if (this.iterator.hasNext()) {
               return true;
            }

            this.iterator = null;
            ++this.setIndex;
         }

         return false;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.lastIterator = this.iterator;
            return this.lastIterator.next();
         }
      }

      public void remove() {
         if (this.lastIterator == null) {
            throw new IllegalStateException();
         } else {
            this.lastIterator.remove();
         }
      }

      // $FF: synthetic method
      CombinedIterator(Object x1) {
         this();
      }
   }
}
