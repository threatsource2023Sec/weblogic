package org.antlr.runtime.misc;

public class IntArray {
   public static final int INITIAL_SIZE = 10;
   public int[] data;
   protected int p = -1;

   public void add(int v) {
      this.ensureCapacity(this.p + 1);
      this.data[++this.p] = v;
   }

   public void push(int v) {
      this.add(v);
   }

   public int pop() {
      int v = this.data[this.p];
      --this.p;
      return v;
   }

   public int size() {
      return this.p;
   }

   public void clear() {
      this.p = -1;
   }

   public void ensureCapacity(int index) {
      if (this.data == null) {
         this.data = new int[10];
      } else if (index + 1 >= this.data.length) {
         int newSize = this.data.length * 2;
         if (index > newSize) {
            newSize = index + 1;
         }

         int[] newData = new int[newSize];
         System.arraycopy(this.data, 0, newData, 0, this.data.length);
         this.data = newData;
      }

   }
}
