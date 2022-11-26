package com.bea.staxb.runtime.internal.util.collections;

public final class ShortList implements Accumulator {
   private short[] store;
   private int size;

   public ShortList() {
      this(16);
   }

   public ShortList(int initial_capacity) {
      this.size = 0;
      this.store = new short[initial_capacity];
   }

   public Object getFinalArray() {
      return this.getMinSizedArray();
   }

   public short[] getMinSizedArray() {
      short[] new_a = new short[this.size];
      System.arraycopy(this.store, 0, new_a, 0, this.size);
      return new_a;
   }

   public int getCapacity() {
      return this.store.length;
   }

   public int getSize() {
      return this.size;
   }

   public void append(Object o) {
      assert o instanceof Number;

      this.add(((Number)o).shortValue());
   }

   public void appendDefault() {
      this.add((short)0);
   }

   public void set(int index, Object value) {
      this.set(index, ((Number)value).shortValue());
   }

   public int size() {
      return this.size;
   }

   public void set(int index, short value) {
      this.ensureCapacity(index + 1);
      if (index >= this.size) {
         this.size = index + 1;
      }

      this.store[index] = value;
   }

   public void add(short i) {
      this.ensureCapacity(this.size + 1);
      this.store[this.size++] = i;
   }

   public short get(int idx) {
      return this.store[idx];
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.store.length;
      if (minCapacity > oldCapacity) {
         short[] oldData = this.store;
         int newCapacity = oldCapacity * 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.store = new short[newCapacity];
         System.arraycopy(oldData, 0, this.store, 0, this.size);
      }

   }
}
