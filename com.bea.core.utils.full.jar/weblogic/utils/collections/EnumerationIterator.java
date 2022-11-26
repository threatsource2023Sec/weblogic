package weblogic.utils.collections;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class EnumerationIterator implements Iterator {
   private final Enumeration enumeration;

   public EnumerationIterator(Enumeration e) {
      this.enumeration = e;
   }

   public Object next() throws NoSuchElementException {
      return this.enumeration.nextElement();
   }

   public boolean hasNext() {
      return this.enumeration.hasMoreElements();
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
