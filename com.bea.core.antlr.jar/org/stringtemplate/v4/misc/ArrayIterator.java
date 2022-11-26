package org.stringtemplate.v4.misc;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator implements Iterator {
   protected int i = -1;
   protected Object array = null;
   protected int n;

   public ArrayIterator(Object array) {
      this.array = array;
      this.n = Array.getLength(array);
   }

   public boolean hasNext() {
      return this.i + 1 < this.n && this.n > 0;
   }

   public Object next() {
      ++this.i;
      if (this.i >= this.n) {
         throw new NoSuchElementException();
      } else {
         return Array.get(this.array, this.i);
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
