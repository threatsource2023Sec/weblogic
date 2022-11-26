package com.bea.core.repackaged.jdt.internal.compiler.util;

public final class SimpleLookupTable implements Cloneable {
   public Object[] keyTable;
   public Object[] valueTable;
   public int elementSize;
   public int threshold;

   public SimpleLookupTable() {
      this(13);
   }

   public SimpleLookupTable(int size) {
      this.elementSize = 0;
      this.threshold = size;
      int extraRoom = (int)((float)size * 1.5F);
      if (this.threshold == extraRoom) {
         ++extraRoom;
      }

      this.keyTable = new Object[extraRoom];
      this.valueTable = new Object[extraRoom];
   }

   public Object clone() throws CloneNotSupportedException {
      SimpleLookupTable result = (SimpleLookupTable)super.clone();
      result.elementSize = this.elementSize;
      result.threshold = this.threshold;
      int length = this.keyTable.length;
      result.keyTable = new Object[length];
      System.arraycopy(this.keyTable, 0, result.keyTable, 0, length);
      length = this.valueTable.length;
      result.valueTable = new Object[length];
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

   public Object get(Object key) {
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

   public Object getKey(Object key) {
      int length = this.keyTable.length;
      int index = (key.hashCode() & Integer.MAX_VALUE) % length;

      Object currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.equals(key)) {
            return currentKey;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return key;
   }

   public Object keyForValue(Object valueToMatch) {
      if (valueToMatch != null) {
         int i = 0;

         for(int l = this.keyTable.length; i < l; ++i) {
            if (this.keyTable[i] != null && valueToMatch.equals(this.valueTable[i])) {
               return this.keyTable[i];
            }
         }
      }

      return null;
   }

   public Object put(Object key, Object value) {
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

   public Object removeKey(Object key) {
      int length = this.keyTable.length;
      int index = (key.hashCode() & Integer.MAX_VALUE) % length;

      Object currentKey;
      while((currentKey = this.keyTable[index]) != null) {
         if (currentKey.equals(key)) {
            --this.elementSize;
            Object oldValue = this.valueTable[index];
            this.keyTable[index] = null;
            this.valueTable[index] = null;
            if (this.keyTable[index + 1 == length ? 0 : index + 1] != null) {
               this.rehash();
            }

            return oldValue;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return null;
   }

   public void removeValue(Object valueToRemove) {
      boolean rehash = false;
      int i = 0;

      for(int l = this.valueTable.length; i < l; ++i) {
         Object value = this.valueTable[i];
         if (value != null && value.equals(valueToRemove)) {
            --this.elementSize;
            this.keyTable[i] = null;
            this.valueTable[i] = null;
            if (!rehash && this.keyTable[i + 1 == l ? 0 : i + 1] != null) {
               rehash = true;
            }
         }
      }

      if (rehash) {
         this.rehash();
      }

   }

   private void rehash() {
      SimpleLookupTable newLookupTable = new SimpleLookupTable(this.elementSize * 2);
      int i = this.keyTable.length;

      while(true) {
         --i;
         if (i < 0) {
            this.keyTable = newLookupTable.keyTable;
            this.valueTable = newLookupTable.valueTable;
            this.elementSize = newLookupTable.elementSize;
            this.threshold = newLookupTable.threshold;
            return;
         }

         Object currentKey;
         if ((currentKey = this.keyTable[i]) != null) {
            newLookupTable.put(currentKey, this.valueTable[i]);
         }
      }
   }

   public String toString() {
      String s = "";
      int i = 0;

      for(int l = this.valueTable.length; i < l; ++i) {
         Object object;
         if ((object = this.valueTable[i]) != null) {
            s = s + this.keyTable[i].toString() + " -> " + object.toString() + "\n";
         }
      }

      return s;
   }
}
