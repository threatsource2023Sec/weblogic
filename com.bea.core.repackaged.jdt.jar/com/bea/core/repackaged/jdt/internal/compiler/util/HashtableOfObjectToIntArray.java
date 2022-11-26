package com.bea.core.repackaged.jdt.internal.compiler.util;

public final class HashtableOfObjectToIntArray implements Cloneable {
   public Object[] keyTable;
   public int[][] valueTable;
   public int elementSize;
   int threshold;

   public HashtableOfObjectToIntArray() {
      this(13);
   }

   public HashtableOfObjectToIntArray(int size) {
      this.elementSize = 0;
      this.threshold = size;
      int extraRoom = (int)((float)size * 1.75F);
      if (this.threshold == extraRoom) {
         ++extraRoom;
      }

      this.keyTable = new Object[extraRoom];
      this.valueTable = new int[extraRoom][];
   }

   public Object clone() throws CloneNotSupportedException {
      HashtableOfObjectToIntArray result = (HashtableOfObjectToIntArray)super.clone();
      result.elementSize = this.elementSize;
      result.threshold = this.threshold;
      int length = this.keyTable.length;
      result.keyTable = new Object[length];
      System.arraycopy(this.keyTable, 0, result.keyTable, 0, length);
      length = this.valueTable.length;
      result.valueTable = new int[length][];
      System.arraycopy(this.valueTable, 0, result.valueTable, 0, length);
      return result;
   }

   public boolean containsKey(Object key) {
      int length = this.keyTable.length;
      int index = (key.hashCode() & Integer.MAX_VALUE) % length;

      Object currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.equals(key)) {
            return true;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return false;
   }

   public int[] get(Object key) {
      int length = this.keyTable.length;
      int index = (key.hashCode() & Integer.MAX_VALUE) % length;

      Object currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.equals(key)) {
            return this.valueTable[index];
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return null;
   }

   public void keysToArray(Object[] array) {
      int index = 0;
      int i = 0;

      for(int length = this.keyTable.length; i < length; ++i) {
         if (this.keyTable[i] != null) {
            array[index++] = this.keyTable[i];
         }
      }

   }

   public int[] put(Object key, int[] value) {
      int length = this.keyTable.length;
      int index = (key.hashCode() & Integer.MAX_VALUE) % length;

      Object currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.equals(key)) {
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

   public int[] removeKey(Object key) {
      int length = this.keyTable.length;
      int index = (key.hashCode() & Integer.MAX_VALUE) % length;

      Object currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.equals(key)) {
            int[] value = this.valueTable[index];
            --this.elementSize;
            this.keyTable[index] = null;
            this.rehash();
            return value;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return null;
   }

   private void rehash() {
      HashtableOfObjectToIntArray newHashtable = new HashtableOfObjectToIntArray(this.elementSize * 2);
      int i = this.keyTable.length;

      while(true) {
         --i;
         if (i < 0) {
            this.keyTable = newHashtable.keyTable;
            this.valueTable = newHashtable.valueTable;
            this.threshold = newHashtable.threshold;
            return;
         }

         Object currentKey;
         if ((currentKey = this.keyTable[i]) != null) {
            newHashtable.put(currentKey, this.valueTable[i]);
         }
      }
   }

   public int size() {
      return this.elementSize;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      int i = 0;

      for(int length = this.keyTable.length; i < length; ++i) {
         Object key;
         if ((key = this.keyTable[i]) != null) {
            buffer.append(key).append(" -> ");
            int[] ints = this.valueTable[i];
            buffer.append('[');
            if (ints != null) {
               int j = 0;

               for(int max = ints.length; j < max; ++j) {
                  if (j > 0) {
                     buffer.append(',');
                  }

                  buffer.append(ints[j]);
               }
            }

            buffer.append("]\n");
         }
      }

      return String.valueOf(buffer);
   }
}
