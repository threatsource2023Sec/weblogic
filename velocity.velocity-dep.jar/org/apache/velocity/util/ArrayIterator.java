package org.apache.velocity.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator implements Iterator {
   private Object array;
   private int pos;
   private int size;

   public ArrayIterator(Object array) {
      if (!array.getClass().isArray()) {
         throw new IllegalArgumentException("Programmer error : internal ArrayIterator invoked w/o array");
      } else {
         this.array = array;
         this.pos = 0;
         this.size = Array.getLength(this.array);
      }
   }

   public Object next() {
      if (this.pos < this.size) {
         return Array.get(this.array, this.pos++);
      } else {
         throw new NoSuchElementException("No more elements: " + this.pos + " / " + this.size);
      }
   }

   public boolean hasNext() {
      return this.pos < this.size;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
