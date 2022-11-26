package com.bea.core.repackaged.springframework.util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class CompositeIterator implements Iterator {
   private final Set iterators = new LinkedHashSet();
   private boolean inUse = false;

   public void add(Iterator iterator) {
      Assert.state(!this.inUse, "You can no longer add iterators to a composite iterator that's already in use");
      if (this.iterators.contains(iterator)) {
         throw new IllegalArgumentException("You cannot add the same iterator twice");
      } else {
         this.iterators.add(iterator);
      }
   }

   public boolean hasNext() {
      this.inUse = true;
      Iterator var1 = this.iterators.iterator();

      Iterator iterator;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         iterator = (Iterator)var1.next();
      } while(!iterator.hasNext());

      return true;
   }

   public Object next() {
      this.inUse = true;
      Iterator var1 = this.iterators.iterator();

      Iterator iterator;
      do {
         if (!var1.hasNext()) {
            throw new NoSuchElementException("All iterators exhausted");
         }

         iterator = (Iterator)var1.next();
      } while(!iterator.hasNext());

      return iterator.next();
   }

   public void remove() {
      throw new UnsupportedOperationException("CompositeIterator does not support remove()");
   }
}
