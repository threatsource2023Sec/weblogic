package com.bea.staxb.runtime.internal.util.collections;

public final class DoubleList implements Accumulator {
   private double[] store;
   private int size;

   public DoubleList() {
      this(16);
   }

   public DoubleList(int initial_capacity) {
      this.size = 0;
      this.store = new double[initial_capacity];
   }

   public Object getFinalArray() {
      return this.getMinSizedArray();
   }

   public double[] getMinSizedArray() {
      if (this.size == this.store.length) {
         return this.store;
      } else {
         double[] new_a = new double[this.size];
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

      this.add(((Number)o).doubleValue());
   }

   public void appendDefault() {
      this.add(0.0);
   }

   public void set(int index, Object value) {
      this.set(index, ((Number)value).doubleValue());
   }

   public int size() {
      return this.size;
   }

   public void set(int index, double value) {
      this.ensureCapacity(index + 1);
      if (index >= this.size) {
         this.size = index + 1;
      }

      this.store[index] = value;
   }

   public void add(double i) {
      this.ensureCapacity(this.size + 1);
      this.store[this.size++] = i;
   }

   public double get(int idx) {
      return this.store[idx];
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.store.length;
      if (minCapacity > oldCapacity) {
         double[] oldData = this.store;
         int newCapacity = oldCapacity * 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.store = new double[newCapacity];
         System.arraycopy(oldData, 0, this.store, 0, this.size);
      }

   }
}
