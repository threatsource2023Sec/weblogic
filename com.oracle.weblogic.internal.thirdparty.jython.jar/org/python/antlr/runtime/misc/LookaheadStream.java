package org.python.antlr.runtime.misc;

public abstract class LookaheadStream extends FastQueue {
   public static final int UNINITIALIZED_EOF_ELEMENT_INDEX = Integer.MAX_VALUE;
   protected int eofElementIndex = Integer.MAX_VALUE;
   public Object eof = null;
   protected int lastMarker;
   protected int markDepth = 0;

   public LookaheadStream(Object eof) {
      this.eof = eof;
   }

   public void reset() {
      this.eofElementIndex = Integer.MAX_VALUE;
      super.reset();
   }

   public abstract Object nextElement();

   public Object remove() {
      Object o = this.get(0);
      ++this.p;
      if (this.p == this.data.size() && this.markDepth == 0) {
         this.clear();
      }

      return o;
   }

   public void consume() {
      this.sync(1);
      this.remove();
   }

   public void sync(int need) {
      int n = this.p + need - 1 - this.data.size() + 1;
      if (n > 0) {
         this.fill(n);
      }

   }

   public void fill(int n) {
      for(int i = 1; i <= n; ++i) {
         Object o = this.nextElement();
         if (o == this.eof) {
            this.data.add(this.eof);
            this.eofElementIndex = this.data.size() - 1;
         } else {
            this.data.add(o);
         }
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
      } else if (this.p + k - 1 >= this.eofElementIndex) {
         return this.eof;
      } else {
         this.sync(k);
         return this.get(k - 1);
      }
   }

   protected Object LB(int k) {
      if (k == 0) {
         return null;
      } else {
         return this.p - k < 0 ? null : this.get(-k);
      }
   }

   public Object getCurrentSymbol() {
      return this.LT(1);
   }

   public int index() {
      return this.p;
   }

   public int mark() {
      ++this.markDepth;
      this.lastMarker = this.index();
      return this.lastMarker;
   }

   public void release(int marker) {
   }

   public void rewind(int marker) {
      --this.markDepth;
      this.seek(marker);
   }

   public void rewind() {
      this.seek(this.lastMarker);
   }

   public void seek(int index) {
      this.p = index;
   }
}
