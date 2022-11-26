package com.bea.core.repackaged.jdt.internal.compiler.codegen;

public class LongCache {
   public long[] keyTable;
   public int[] valueTable;
   int elementSize;
   int threshold;

   public LongCache() {
      this(13);
   }

   public LongCache(int initialCapacity) {
      this.elementSize = 0;
      this.threshold = (int)((double)initialCapacity * 0.66);
      this.keyTable = new long[initialCapacity];
      this.valueTable = new int[initialCapacity];
   }

   public void clear() {
      int i = this.keyTable.length;

      while(true) {
         --i;
         if (i < 0) {
            this.elementSize = 0;
            return;
         }

         this.keyTable[i] = 0L;
         this.valueTable[i] = 0;
      }
   }

   public boolean containsKey(long key) {
      int index = this.hash(key);
      int length = this.keyTable.length;

      while(this.keyTable[index] != 0L || this.keyTable[index] == 0L && this.valueTable[index] != 0) {
         if (this.keyTable[index] == key) {
            return true;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return false;
   }

   public int hash(long key) {
      return ((int)key & Integer.MAX_VALUE) % this.keyTable.length;
   }

   public int put(long key, int value) {
      int index = this.hash(key);
      int length = this.keyTable.length;

      while(this.keyTable[index] != 0L || this.keyTable[index] == 0L && this.valueTable[index] != 0) {
         if (this.keyTable[index] == key) {
            return this.valueTable[index] = value;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      this.keyTable[index] = key;
      this.valueTable[index] = value;
      if (++this.elementSize > this.threshold) {
         this.rehash();
      }

      return value;
   }

   public int putIfAbsent(long key, int value) {
      int index = this.hash(key);
      int length = this.keyTable.length;

      while(this.keyTable[index] != 0L || this.keyTable[index] == 0L && this.valueTable[index] != 0) {
         if (this.keyTable[index] == key) {
            return this.valueTable[index];
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      this.keyTable[index] = key;
      this.valueTable[index] = value;
      if (++this.elementSize > this.threshold) {
         this.rehash();
      }

      return -value;
   }

   private void rehash() {
      LongCache newHashtable = new LongCache(this.keyTable.length * 2);
      int i = this.keyTable.length;

      while(true) {
         long key;
         int value;
         do {
            --i;
            if (i < 0) {
               this.keyTable = newHashtable.keyTable;
               this.valueTable = newHashtable.valueTable;
               this.threshold = newHashtable.threshold;
               return;
            }

            key = this.keyTable[i];
            value = this.valueTable[i];
         } while(key == 0L && (key != 0L || value == 0));

         newHashtable.put(key, value);
      }
   }

   public int size() {
      return this.elementSize;
   }

   public String toString() {
      int max = this.size();
      StringBuffer buf = new StringBuffer();
      buf.append("{");

      for(int i = 0; i < max; ++i) {
         if (this.keyTable[i] != 0L || this.keyTable[i] == 0L && this.valueTable[i] != 0) {
            buf.append(this.keyTable[i]).append("->").append(this.valueTable[i]);
         }

         if (i < max) {
            buf.append(", ");
         }
      }

      buf.append("}");
      return buf.toString();
   }
}
