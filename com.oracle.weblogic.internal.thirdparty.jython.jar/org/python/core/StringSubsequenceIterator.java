package org.python.core;

import java.util.Iterator;

class StringSubsequenceIterator implements Iterator {
   private final String s;
   private int current;
   private int k;
   private int start;
   private int stop;
   private int step;

   StringSubsequenceIterator(String s, int start, int stop, int step) {
      this.s = s;
      this.k = 0;
      this.current = start;
      this.start = start;
      this.stop = stop;
      this.step = step;
      int count = getCodePointCount(s);
      if (start >= count) {
         this.stop = -1;
      } else if (stop >= count) {
         this.stop = count;
      }

      for(int i = 0; i < start; ++i) {
         this.nextCodePoint();
      }

   }

   StringSubsequenceIterator(String s) {
      this(s, 0, getCodePointCount(s), 1);
   }

   private static int getCodePointCount(String s) {
      return s.codePointCount(0, s.length());
   }

   public boolean hasNext() {
      return this.current < this.stop;
   }

   public Object next() {
      int codePoint = this.nextCodePoint();
      ++this.current;

      for(int j = 1; j < this.step && this.hasNext(); ++j) {
         this.nextCodePoint();
         ++this.current;
      }

      return codePoint;
   }

   private int nextCodePoint() {
      int W1 = this.s.charAt(this.k);
      int U;
      if (W1 >= '\ud800' && W1 < '\udc00') {
         int W2 = this.s.charAt(this.k + 1);
         U = ((W1 & 1023) << 10 | W2 & 1023) + 65536;
         this.k += 2;
      } else {
         U = W1;
         ++this.k;
      }

      return U;
   }

   public void remove() {
      throw new UnsupportedOperationException("Not supported on String objects (immutable)");
   }
}
