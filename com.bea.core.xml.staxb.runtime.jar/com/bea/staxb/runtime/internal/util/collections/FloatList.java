package com.bea.staxb.runtime.internal.util.collections;

public final class FloatList implements Accumulator {
   private float[] store;
   private int size;

   public FloatList() {
      this(16);
   }

   public FloatList(int initial_capacity) {
      this.size = 0;
      this.store = new float[initial_capacity];
   }

   public Object getFinalArray() {
      return this.getMinSizedArray();
   }

   public float[] getMinSizedArray() {
      if (this.size == this.store.length) {
         return this.store;
      } else {
         float[] new_a = new float[this.size];
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

      this.add(((Number)o).floatValue());
   }

   public void appendDefault() {
      this.add(0.0F);
   }

   public void set(int index, Object value) {
      this.set(index, ((Number)value).floatValue());
   }

   public int size() {
      return this.size;
   }

   public void set(int index, float value) {
      this.ensureCapacity(index + 1);
      if (index >= this.size) {
         this.size = index + 1;
      }

      this.store[index] = value;
   }

   public void add(float i) {
      this.ensureCapacity(this.size + 1);
      this.store[this.size++] = i;
   }

   public float get(int idx) {
      return this.store[idx];
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.store.length;
      if (minCapacity > oldCapacity) {
         float[] oldData = this.store;
         int newCapacity = oldCapacity * 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.store = new float[newCapacity];
         System.arraycopy(oldData, 0, this.store, 0, this.size);
      }

   }
}
