package com.bea.staxb.runtime.internal.util.collections;

public final class BooleanList implements Accumulator {
   private boolean[] store;
   private int size;

   public BooleanList() {
      this(16);
   }

   public BooleanList(int initial_capacity) {
      this.size = 0;
      this.store = new boolean[initial_capacity];
   }

   public boolean[] getMinSizedArray() {
      if (this.size == this.store.length) {
         return this.store;
      } else {
         boolean[] new_a = new boolean[this.size];
         System.arraycopy(this.store, 0, new_a, 0, this.size);
         this.store = new_a;
         return new_a;
      }
   }

   public Object getFinalArray() {
      return this.getMinSizedArray();
   }

   public int getCapacity() {
      return this.store.length;
   }

   public int getSize() {
      return this.size;
   }

   public void append(Object o) {
      assert o instanceof Boolean;

      this.add((Boolean)o);
   }

   public void appendDefault() {
      this.add(false);
   }

   public void set(int index, Object value) {
      this.set(index, (Boolean)value);
   }

   public int size() {
      return this.size;
   }

   public void set(int index, boolean value) {
      this.ensureCapacity(index + 1);
      if (index >= this.size) {
         this.size = index + 1;
      }

      this.store[index] = value;
   }

   public void add(boolean i) {
      this.ensureCapacity(this.size + 1);
      this.store[this.size++] = i;
   }

   public boolean get(int idx) {
      return this.store[idx];
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.store.length;
      if (minCapacity > oldCapacity) {
         boolean[] oldData = this.store;
         int newCapacity = oldCapacity * 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.store = new boolean[newCapacity];
         System.arraycopy(oldData, 0, this.store, 0, this.size);
      }

   }
}
