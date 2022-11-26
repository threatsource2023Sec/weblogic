package weblogic.cache;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EmptyIterator implements Iterator {
   public boolean hasNext() {
      return false;
   }

   public Object next() {
      throw new NoSuchElementException("This is empty");
   }

   public void remove() {
      throw new UnsupportedOperationException("Cannot remove from an empty iterator");
   }
}
