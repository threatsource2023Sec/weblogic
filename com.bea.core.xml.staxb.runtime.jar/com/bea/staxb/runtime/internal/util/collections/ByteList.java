package com.bea.staxb.runtime.internal.util.collections;

public final class ByteList implements Accumulator {
   private byte[] store;
   private int size;

   public ByteList() {
      this(16);
   }

   public ByteList(int initial_capacity) {
      this.size = 0;
      this.store = new byte[initial_capacity];
   }

   public byte[] getMinSizedArray() {
      if (this.size == this.store.length) {
         return this.store;
      } else {
         byte[] new_a = new byte[this.size];
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

      this.add(((Number)o).byteValue());
   }

   public void appendDefault() {
      this.add((byte)0);
   }

   public void set(int index, Object value) {
      this.set(index, ((Number)value).byteValue());
   }

   public int size() {
      return this.size;
   }

   public void add(byte i) {
      this.ensureCapacity(this.size + 1);
      this.store[this.size++] = i;
   }

   public byte get(int idx) {
      return this.store[idx];
   }

   public void set(int index, byte value) {
      this.ensureCapacity(index + 1);
      if (index >= this.size) {
         this.size = index + 1;
      }

      this.store[index] = value;
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.store.length;
      if (minCapacity > oldCapacity) {
         byte[] oldData = this.store;
         int newCapacity = oldCapacity * 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.store = new byte[newCapacity];
         System.arraycopy(oldData, 0, this.store, 0, this.size);
      }

   }
}
