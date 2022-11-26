package com.bea.xbean.piccolo.util;

public final class IntStack {
   private int[] stack;
   private int pos;

   public IntStack(int initialSize) {
      this.stack = new int[initialSize];
      this.pos = -1;
   }

   public int pop() {
      if (this.pos >= 0) {
         return this.stack[this.pos--];
      } else {
         throw new ArrayIndexOutOfBoundsException("stack underflow");
      }
   }

   public void push(int s) {
      if (this.pos + 1 < this.stack.length) {
         this.stack[++this.pos] = s;
      } else {
         this.setSize(this.stack.length * 2);
         this.stack[++this.pos] = s;
      }

   }

   public void setSize(int newSize) {
      if (newSize != this.stack.length) {
         int[] newStack = new int[newSize];
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
