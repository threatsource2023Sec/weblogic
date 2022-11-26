package com.bea.staxb.runtime.internal.util.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class IntArrayIterator implements Iterator {
   private final int[] array;
   private final int maxIndex;
   private int index;

   public IntArrayIterator(int[] a) {
      this(a, 0, a.length);
   }

   public IntArrayIterator(int[] a, int off, int len) {
      if (off < 0) {
         throw new IllegalArgumentException();
      } else if (off > a.length) {
         throw new IllegalArgumentException();
      } else if (len > a.length - off) {
         throw new IllegalArgumentException();
      } else {
         this.array = a;
         this.index = off;
         this.maxIndex = len + off;
      }
   }

   public boolean hasNext() {
      return this.index < this.maxIndex;
   }

   public Object next() {
      if (this.index >= this.maxIndex) {
         throw new NoSuchElementException();
      } else {
         return new Integer(this.array[this.index++]);
      }
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
