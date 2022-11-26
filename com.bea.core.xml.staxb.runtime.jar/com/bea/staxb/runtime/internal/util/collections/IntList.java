package com.bea.staxb.runtime.internal.util.collections;

public final class IntList implements Accumulator {
   private int[] store;
   private int size;

   public IntList() {
      this(16);
   }

   public IntList(int initial_capacity) {
      this.size = 0;
      this.store = new int[initial_capacity];
   }

   public Object getFinalArray() {
      return this.getMinSizedArray();
   }

   public int[] getMinSizedArray() {
      if (this.size == this.store.length) {
         return this.store;
      } else {
         int[] new_a = new int[this.size];
         System.arraycopy(this.store, 0, new_a, 0, this.size);
         this.store = new_a;
         return new_a;
      }
   }

   public int getCapacity() {
      return this.store.length;
   }

   public int getSize() {
      return this.size;
   }

   public void append(Object o) {
      assert o instanceof Number;

      this.add(((Number)o).intValue());
   }

   public void appendDefault() {
      this.add(0);
   }

   public void set(int index, Object value) {
      this.set(index, ((Number)value).intValue());
   }

   public int size() {
      return this.size;
   }

   public void set(int index, int value) {
      this.ensureCapacity(index + 1);
      if (index >= this.size) {
         this.size = index + 1;
      }

      this.store[index] = value;
   }

   public void add(int i) {
      this.ensureCapacity(this.size + 1);
      this.store[this.size++] = i;
   }

   public int get(int idx) {
      return this.store[idx];
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.store.length;
      if (minCapacity > oldCapacity) {
         int[] oldData = this.store;
         int newCapacity = oldCapacity * 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.store = new int[newCapacity];
         System.arraycopy(oldData, 0, this.store, 0, this.size);
      }

   }
}
