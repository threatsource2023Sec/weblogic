package com.bea.core.repackaged.jdt.internal.compiler.util;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;

public final class HashtableOfModule {
   public char[][] keyTable;
   public ModuleBinding[] valueTable;
   public int elementSize;
   int threshold;

   public HashtableOfModule() {
      this(3);
   }

   public HashtableOfModule(int size) {
      this.elementSize = 0;
      this.threshold = size;
      int extraRoom = (int)((float)size * 1.75F);
      if (this.threshold == extraRoom) {
         ++extraRoom;
      }

      this.keyTable = new char[extraRoom][];
      this.valueTable = new ModuleBinding[extraRoom];
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

   public ModuleBinding get(char[] key) {
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

   public ModuleBinding put(char[] key, ModuleBinding value) {
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

   private void rehash() {
      HashtableOfModule newHashtable = new HashtableOfModule(this.elementSize * 2);
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
         ModuleBinding pkg;
         if ((pkg = this.valueTable[i]) != null) {
            s = s + pkg.toString() + "\n";
         }
      }

      return s;
   }
}
