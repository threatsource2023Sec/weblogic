package com.bea.staxb.runtime.internal.util.collections;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class ReflectiveArrayIterator implements Iterator {
   private final Object array;
   private final int maxIndex;
   private int index;

   public ReflectiveArrayIterator(Object a) {
      this(a, 0, Array.getLength(a));
   }

   public ReflectiveArrayIterator(Object a, int off, int len) {
      if (!a.getClass().isArray()) {
         throw new IllegalArgumentException();
      } else {
         int asize = Array.getLength(a);
         if (off < 0) {
            throw new IllegalArgumentException();
         } else if (off > asize) {
            throw new IllegalArgumentException();
         } else if (len > asize - off) {
            throw new IllegalArgumentException();
         } else {
            this.array = a;
            this.index = off;
            this.maxIndex = len + off;
         }
      }
   }

   public boolean hasNext() {
      return this.index < this.maxIndex;
   }

   public Object next() {
      if (this.index >= this.maxIndex) {
         throw new NoSuchElementException();
      } else {
         return Array.get(this.array, this.index++);
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
