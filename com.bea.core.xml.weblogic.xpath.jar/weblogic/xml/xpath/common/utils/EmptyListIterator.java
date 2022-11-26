package weblogic.xml.xpath.common.utils;

import java.util.ListIterator;
import java.util.NoSuchElementException;

final class EmptyListIterator implements ListIterator {
   private static final ListIterator INSTANCE = new EmptyListIterator();

   public static ListIterator getInstance() {
      return INSTANCE;
   }

   private EmptyListIterator() {
   }

   public boolean hasNext() {
      return false;
   }

   public Object next() {
      throw new NoSuchElementException();
   }

   public boolean hasPrevious() {
      return false;
   }

   public Object previous() {
      throw new NoSuchElementException();
   }

   public int nextIndex() {
      return 0;
   }

   public int previousIndex() {
      return -1;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }

   public void set(Object o) {
      throw new UnsupportedOperationException();
   }

   public void add(Object o) {
      throw new UnsupportedOperationException();
   }
}
