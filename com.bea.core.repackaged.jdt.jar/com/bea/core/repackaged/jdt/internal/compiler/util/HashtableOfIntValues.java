package com.bea.core.repackaged.jdt.internal.compiler.util;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public final class HashtableOfIntValues implements Cloneable {
   public static final int NO_VALUE = Integer.MIN_VALUE;
   public char[][] keyTable;
   public int[] valueTable;
   public int elementSize;
   int threshold;

   public HashtableOfIntValues() {
      this(13);
   }

   public HashtableOfIntValues(int size) {
      this.elementSize = 0;
      this.threshold = size;
      int extraRoom = (int)((float)size * 1.75F);
      if (this.threshold == extraRoom) {
         ++extraRoom;
      }

      this.keyTable = new char[extraRoom][];
      this.valueTable = new int[extraRoom];
   }

   public Object clone() throws CloneNotSupportedException {
      HashtableOfIntValues result = (HashtableOfIntValues)super.clone();
      result.elementSize = this.elementSize;
      result.threshold = this.threshold;
      int length = this.keyTable.length;
      result.keyTable = new char[length][];
      System.arraycopy(this.keyTable, 0, result.keyTable, 0, length);
      length = this.valueTable.length;
      result.valueTable = new int[length];
      System.arraycopy(this.valueTable, 0, result.valueTable, 0, length);
      return result;
   }

   public boolean containsKey(char[] key) {
      int length = this.keyTable.length;
      int index = CharOperation.hashCode(key) % length;
      int keyLength = key.length;

      char[] currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.length == keyLength && CharOperation.equals(currentKey, key)) {
            return true;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return false;
   }

   public int get(char[] key) {
      int length = this.keyTable.length;
      int index = CharOperation.hashCode(key) % length;
      int keyLength = key.length;

      char[] currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.length == keyLength && CharOperation.equals(currentKey, key)) {
            return this.valueTable[index];
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return Integer.MIN_VALUE;
   }

   public int put(char[] key, int value) {
      int length = this.keyTable.length;
      int index = CharOperation.hashCode(key) % length;
      int keyLength = key.length;

      char[] currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.length == keyLength && CharOperation.equals(currentKey, key)) {
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

   public int removeKey(char[] key) {
      int length = this.keyTable.length;
      int index = CharOperation.hashCode(key) % length;
      int keyLength = key.length;

      char[] currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.length == keyLength && CharOperation.equals(currentKey, key)) {
            int value = this.valueTable[index];
            --this.elementSize;
            this.keyTable[index] = null;
            this.valueTable[index] = Integer.MIN_VALUE;
            this.rehash();
            return value;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return Integer.MIN_VALUE;
   }

   private void rehash() {
      HashtableOfIntValues newHashtable = new HashtableOfIntValues(this.elementSize * 2);
      int i = this.keyTable.length;

      while(true) {
         --i;
         if (i < 0) {
            this.keyTable = newHashtable.keyTable;
            this.valueTable = newHashtable.valueTable;
            this.threshold = newHashtable.threshold;
            return;
         }

         char[] currentKey;
         if ((currentKey = this.keyTable[i]) != null) {
            newHashtable.put(currentKey, this.valueTable[i]);
         }
      }
   }

   public int size() {
      return this.elementSize;
   }

   public String toString() {
      String s = "";
      int i = 0;

      for(int length = this.valueTable.length; i < length; ++i) {
         char[] key;
         if ((key = this.keyTable[i]) != null) {
            s = s + new String(key) + " -> " + this.valueTable[i] + "\n";
         }
      }

      return s;
   }
}
