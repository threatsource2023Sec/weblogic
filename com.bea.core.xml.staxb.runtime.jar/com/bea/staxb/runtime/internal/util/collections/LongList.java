package com.bea.staxb.runtime.internal.util.collections;

public final class LongList implements Accumulator {
   private long[] store;
   private int size;

   public LongList() {
      this(16);
   }

   public LongList(int initial_capacity) {
      this.size = 0;
      this.store = new long[initial_capacity];
   }

   public long[] getMinSizedArray() {
      if (this.size == this.store.length) {
         return this.store;
      } else {
         long[] new_a = new long[this.size];
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
      assert o instanceof Number;

      this.add(((Number)o).longValue());
   }

   public void appendDefault() {
      this.add(0L);
   }

   public void set(int index, Object value) {
      this.set(index, ((Number)value).longValue());
   }

   public int size() {
      return this.size;
   }

   public void set(int index, long value) {
      this.ensureCapacity(index + 1);
      if (index >= this.size) {
         this.size = index + 1;
      }

      this.store[index] = value;
   }

   public void add(long i) {
      this.ensureCapacity(this.size + 1);
      this.store[this.size++] = i;
   }

   public long get(int idx) {
      return this.store[idx];
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.store.length;
      if (minCapacity > oldCapacity) {
         long[] oldData = this.store;
         int newCapacity = oldCapacity * 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.store = new long[newCapacity];
         System.arraycopy(oldData, 0, this.store, 0, this.size);
      }

   }
}
