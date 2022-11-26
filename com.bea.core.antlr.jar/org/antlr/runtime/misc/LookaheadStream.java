package org.antlr.runtime.misc;

public abstract class LookaheadStream extends FastQueue {
   public static final int UNINITIALIZED_EOF_ELEMENT_INDEX = Integer.MAX_VALUE;
   protected int currentElementIndex = 0;
   protected Object prevElement;
   public Object eof = null;
   protected int lastMarker;
   protected int markDepth = 0;

   public void reset() {
      super.reset();
      this.currentElementIndex = 0;
      this.p = 0;
      this.prevElement = null;
   }

   public abstract Object nextElement();

   public abstract boolean isEOF(Object var1);

   public Object remove() {
      Object o = this.elementAt(0);
      ++this.p;
      if (this.p == this.data.size() && this.markDepth == 0) {
         this.prevElement = o;
         this.clear();
      }

      return o;
   }

   public void consume() {
      this.syncAhead(1);
      this.remove();
      ++this.currentElementIndex;
   }

   protected void syncAhead(int need) {
      int n = this.p + need - 1 - this.data.size() + 1;
      if (n > 0) {
         this.fill(n);
      }

   }

   public void fill(int n) {
      for(int i = 1; i <= n; ++i) {
         Object o = this.nextElement();
         if (this.isEOF(o)) {
            this.eof = o;
         }

         this.data.add(o);
      }

   }

   public int size() {
      throw new UnsupportedOperationException("streams are of unknown size");
   }

   public Object LT(int k) {
      if (k == 0) {
         return null;
      } else if (k < 0) {
         return this.LB(-k);
      } else {
         this.syncAhead(k);
         return this.p + k - 1 > this.data.size() ? this.eof : this.elementAt(k - 1);
      }
   }

   public int index() {
      return this.currentElementIndex;
   }

   public int mark() {
      ++this.markDepth;
      this.lastMarker = this.p;
      return this.lastMarker;
   }

   public void release(int marker) {
   }

   public void rewind(int marker) {
      --this.markDepth;
      int delta = this.p - marker;
      this.currentElementIndex -= delta;
      this.p = marker;
   }

   public void rewind() {
      int delta = this.p - this.lastMarker;
      this.currentElementIndex -= delta;
      this.p = this.lastMarker;
   }

   public void seek(int index) {
      if (index < 0) {
         throw new IllegalArgumentException("can't seek before the beginning of the input");
      } else {
         int delta = this.currentElementIndex - index;
         if (this.p - delta < 0) {
            throw new UnsupportedOperationException("can't seek before the beginning of this stream's buffer");
         } else {
            this.p -= delta;
            this.currentElementIndex = index;
         }
      }
   }

   protected Object LB(int k) {
      assert k > 0;

      int index = this.p - k;
      if (index == -1) {
         return this.prevElement;
      } else if (index >= 0) {
         return this.data.get(index);
      } else if (index < -1) {
         throw new UnsupportedOperationException("can't look more than one token before the beginning of this stream's buffer");
      } else {
         throw new UnsupportedOperationException("can't look past the end of this stream's buffer using LB(int)");
      }
   }
}
