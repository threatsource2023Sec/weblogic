package org.apache.xmlbeans.impl.piccolo.util;

public class StringStack {
   private String[] stack;
   private int pos;

   public StringStack(int initialSize) {
      this.stack = new String[initialSize];
      this.pos = -1;
   }

   public String pop() {
      return this.pos >= 0 ? this.stack[this.pos--] : null;
   }

   public void push(String s) {
      if (this.pos + 1 < this.stack.length) {
         this.stack[++this.pos] = s;
      } else {
         this.setSize(this.stack.length * 2);
         this.stack[++this.pos] = s;
      }

   }

   public void setSize(int newSize) {
      if (newSize != this.stack.length) {
         String[] newStack = new String[newSize];
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
