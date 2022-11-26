package weblogic.xml.xpath.common.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class EmptyIterator implements Iterator {
   private static final Iterator INSTANCE = new EmptyIterator();

   public static Iterator getInstance() {
      return INSTANCE;
   }

   private EmptyIterator() {
   }

   public boolean hasNext() {
      return false;
   }

   public Object next() {
      throw new NoSuchElementException();
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
