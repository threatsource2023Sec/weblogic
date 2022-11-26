package org.apache.xmlbeans.impl.piccolo.util;

public final class LongStack {
   private long[] stack;
   private int pos;

   public LongStack(int initialSize) {
      this.stack = new long[initialSize];
      this.pos = -1;
   }

   public long pop() {
      if (this.pos >= 0) {
         return this.stack[this.pos--];
      } else {
         throw new ArrayIndexOutOfBoundsException("stack underflow");
      }
   }

   public void push(long s) {
      if (this.pos + 1 < this.stack.length) {
         this.stack[++this.pos] = s;
      } else {
         this.setSize(this.stack.length * 2);
         this.stack[++this.pos] = s;
      }

   }

   public void setSize(int newSize) {
      if (newSize != this.stack.length) {
         long[] newStack = new long[newSize];
         System.arraycopy(this.stack, 0, newStack, 0, Math.min(this.stack.length, newSize));
         this.stack = newStack;
      }

   }

   public void clear() {
      this.pos = -1;
   }

   public int size() {
      return this.pos + 1;
   }
}
