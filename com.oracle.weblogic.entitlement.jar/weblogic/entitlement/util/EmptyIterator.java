package weblogic.entitlement.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class EmptyIterator implements Iterator, Enumeration {
   public static final EmptyIterator INSTANCE = new EmptyIterator();

   public boolean hasNext() {
      return false;
   }

   public Object next() {
      throw new NoSuchElementException();
   }

   public void remove() {
      throw new IllegalStateException();
   }

   public boolean hasMoreElements() {
      return false;
   }

   public Object nextElement() {
      throw new NoSuchElementException();
   }
}
