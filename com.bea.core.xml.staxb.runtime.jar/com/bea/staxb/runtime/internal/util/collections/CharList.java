package com.bea.staxb.runtime.internal.util.collections;

public final class CharList implements Accumulator {
   private char[] store;
   private int size;

   public CharList() {
      this(16);
   }

   public CharList(int initial_capacity) {
      this.size = 0;
      this.store = new char[initial_capacity];
   }

   public Object getFinalArray() {
      return this.getMinSizedArray();
   }

   public char[] getMinSizedArray() {
      if (this.size == this.store.length) {
         return this.store;
      } else {
         char[] new_a = new char[this.size];
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
      assert o instanceof Character;

      this.add((Character)o);
   }

   public void appendDefault() {
      this.add('\u0000');
   }

   public void set(int index, Object value) {
      this.set(index, (Character)value);
   }

   public int size() {
      return this.size;
   }

   public void set(int index, char value) {
      this.ensureCapacity(index + 1);
      if (index >= this.size) {
         this.size = index + 1;
      }

      this.store[index] = value;
   }

   public void add(char i) {
      this.ensureCapacity(this.size + 1);
      this.store[this.size++] = i;
   }

   public char get(int idx) {
      return this.store[idx];
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.store.length;
      if (minCapacity > oldCapacity) {
         char[] oldData = this.store;
         int newCapacity = oldCapacity * 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.store = new char[newCapacity];
         System.arraycopy(oldData, 0, this.store, 0, this.size);
      }

   }
}
