package com.bea.core.repackaged.jdt.internal.compiler.util;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public final class HashtableOfObject implements Cloneable {
   public char[][] keyTable;
   public Object[] valueTable;
   public int elementSize;
   int threshold;

   public HashtableOfObject() {
      this(13);
   }

   public HashtableOfObject(int size) {
      this.elementSize = 0;
      this.threshold = size;
      int extraRoom = (int)((float)size * 1.75F);
      if (this.threshold == extraRoom) {
         ++extraRoom;
      }

      this.keyTable = new char[extraRoom][];
      this.valueTable = new Object[extraRoom];
   }

   public void clear() {
      int i = this.keyTable.length;

      while(true) {
         --i;
         if (i < 0) {
            this.elementSize = 0;
            return;
         }

         this.keyTable[i] = null;
         this.valueTable[i] = null;
      }
   }

   public Object clone() throws CloneNotSupportedException {
      HashtableOfObject result = (HashtableOfObject)super.clone();
      result.elementSize = this.elementSize;
      result.threshold = this.threshold;
      int length = this.keyTable.length;
      result.keyTable = new char[length][];
      System.arraycopy(this.keyTable, 0, result.keyTable, 0, length);
      length = this.valueTable.length;
      result.valueTable = new Object[length];
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

   public Object get(char[] key) {
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

      return null;
   }

   public Object put(char[] key, Object value) {
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

   public void putUnsafely(char[] key, Object value) {
      int length = this.keyTable.length;
      int index = CharOperation.hashCode(key) % length;

      while(this.keyTable[index] != null) {
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

   }

   public Object removeKey(char[] key) {
      int length = this.keyTable.length;
      int index = CharOperation.hashCode(key) % length;
      int keyLength = key.length;

      char[] currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.length == keyLength && CharOperation.equals(currentKey, key)) {
            Object value = this.valueTable[index];
            --this.elementSize;
            this.keyTable[index] = null;
            this.valueTable[index] = null;
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
      HashtableOfObject newHashtable = new HashtableOfObject(this.elementSize * 2);
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
            newHashtable.putUnsafely(currentKey, this.valueTable[i]);
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
         Object object;
         if ((object = this.valueTable[i]) != null) {
            s = s + new String(this.keyTable[i]) + " -> " + object.toString() + "\n";
         }
      }

      return s;
   }
}
