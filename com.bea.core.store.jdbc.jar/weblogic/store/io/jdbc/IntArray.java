package weblogic.store.io.jdbc;

final class IntArray {
   private int[] values;
   private int pointer;
   private int initialSize;

   IntArray() {
      this(128);
   }

   IntArray(int size) {
      if (size < 1) {
         throw new IllegalArgumentException();
      } else {
         this.initialSize = size;
         this.clear();
      }
   }

   private void resize(int index) {
      int newSize = this.values.length;
      if (newSize < 32768) {
         newSize *= 2;
      } else {
         newSize += 32768;
      }

      newSize = Math.max(index + 1, newSize);
      int[] o = new int[newSize];
      System.arraycopy(this.values, 0, o, 0, this.values.length);
      this.values = o;
   }

   void clear() {
      this.values = new int[this.initialSize];
      this.pointer = 0;
   }

   int size() {
      return this.pointer;
   }

   int get(int index) {
      return index >= this.values.length ? 0 : this.values[index];
   }

   void set(int index, int value) {
      if (index >= this.values.length) {
         this.resize(index);
      }

      this.values[index] = value;
      if (index >= this.pointer) {
         this.pointer = index + 1;
      }

   }
}
