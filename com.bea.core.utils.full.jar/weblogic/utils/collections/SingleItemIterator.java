package weblogic.utils.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SingleItemIterator implements Iterator {
   private boolean done = false;
   private final Object item;

   public SingleItemIterator(Object o) {
      this.item = o;
   }

   public boolean hasNext() {
      return !this.done;
   }

   public Object next() {
      if (this.done) {
         throw new NoSuchElementException();
      } else {
         this.done = true;
         return this.item;
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
