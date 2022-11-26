package weblogic.xml.schema.binding.util.runtime;

import weblogic.utils.Debug;

public final class ByteList implements Accumulator {
   private static final boolean DEBUG = false;
   private static final boolean ASSERT = false;
   private byte[] store;
   private int size;

   public ByteList() {
      this(16);
   }

   public ByteList(int initial_capacity) {
      this.size = 0;
      this.store = new byte[initial_capacity];
   }

   public byte[] getStore() {
      return this.store;
   }

   public byte[] getMinSizedArray() {
      byte[] new_a = new byte[this.size];
      System.arraycopy(this.store, 0, new_a, 0, this.size);
      return new_a;
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
      this.add(((Number)o).byteValue());
   }

   public void add(byte i) {
      this.ensureCapacity(this.size + 1);
      this.store[this.size++] = i;
   }

   public byte get(int idx) {
      return this.store[idx];
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.store.length;
      if (minCapacity > oldCapacity) {
         byte[] oldData = this.store;
         int newCapacity = oldCapacity * 3 / 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.store = new byte[newCapacity];
         System.arraycopy(oldData, 0, this.store, 0, this.size);
      }

   }

   public static void main(String[] args) throws Exception {
      int max_size = true;
      ByteList l = new ByteList();

      byte i;
      for(i = 0; i < 126; ++i) {
         l.add(i);
      }

      byte i;
      for(i = 0; i < 126; ++i) {
         i = l.get(i);
         Debug.assertion(i == i);
      }

      byte[] arr = l.getStore();

      for(i = 0; i < 126; ++i) {
         byte t = l.get(i);
         byte t2 = arr[i];
         Debug.assertion(t == t2);
         Debug.assertion(t == i);
      }

      Debug.say("good");
   }
}
