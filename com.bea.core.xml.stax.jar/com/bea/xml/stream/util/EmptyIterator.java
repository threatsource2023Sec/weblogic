package com.bea.xml.stream.util;

import java.util.Iterator;

public class EmptyIterator implements Iterator {
   public static final EmptyIterator emptyIterator = new EmptyIterator();

   public boolean hasNext() {
      return false;
   }

   public Object next() {
      return null;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
