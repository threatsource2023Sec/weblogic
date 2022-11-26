package com.bea.staxb.runtime.internal.util.collections;

import java.util.Arrays;

public final class StringList implements Accumulator {
   private String[] store;
   private int size;

   public StringList() {
      this(16);
   }

   public StringList(int initial_capacity) {
      this.size = 0;
      this.store = new String[initial_capacity];
   }

   public Object getFinalArray() {
      return this.getMinSizedArray();
   }

   public String[] getMinSizedArray() {
      if (this.size == this.store.length) {
         return this.store;
      } else {
         String[] new_a = new String[this.size];
         System.arraycopy(this.store, 0, new_a, 0, this.size);
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
      this.add((String)o);
   }

   public void appendDefault() {
      this.append((Object)null);
   }

   public void set(int index, Object value) {
      this.set(index, (String)value);
   }

   public int size() {
      return this.size;
   }

   public void set(int index, String value) {
      this.ensureCapacity(index + 1);
      if (index >= this.size) {
         this.size = index + 1;
      }

      this.store[index] = value;
   }

   public void clear() {
      if (this.size != 0) {
         Arrays.fill(this.store, (Object)null);
         this.size = 0;
      }
   }

   public void add(String i) {
      this.ensureCapacity(this.size + 1);
      this.store[this.size++] = i;
   }

   public String get(int idx) {
      return this.store[idx];
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.store.length;
      if (minCapacity > oldCapacity) {
         String[] oldData = this.store;
         int newCapacity = oldCapacity * 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.store = new String[newCapacity];
         System.arraycopy(oldData, 0, this.store, 0, this.size);
      }

   }
}
