package com.bea.core.repackaged.jdt.internal.compiler.util;

public final class HashSetOfInt implements Cloneable {
   public int[] set;
   public int elementSize;
   int threshold;

   public HashSetOfInt() {
      this(13);
   }

   public HashSetOfInt(int size) {
      this.elementSize = 0;
      this.threshold = size;
      int extraRoom = (int)((float)size * 1.75F);
      if (this.threshold == extraRoom) {
         ++extraRoom;
      }

      this.set = new int[extraRoom];
   }

   public Object clone() throws CloneNotSupportedException {
      HashSetOfInt result = (HashSetOfInt)super.clone();
      result.elementSize = this.elementSize;
      result.threshold = this.threshold;
      int length = this.set.length;
      result.set = new int[length];
      System.arraycopy(this.set, 0, result.set, 0, length);
      return result;
   }

   public boolean contains(int element) {
      int length = this.set.length;
      int index = element % length;

      int currentElement;
      while((currentElement = this.set[index]) != 0) {
         if (currentElement == element) {
            return true;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return false;
   }

   public int add(int element) {
      int length = this.set.length;
      int index = element % length;

      int currentElement;
      while((currentElement = this.set[index]) != 0) {
         if (currentElement == element) {
            return this.set[index] = element;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      this.set[index] = element;
      if (++this.elementSize > this.threshold) {
         this.rehash();
      }

      return element;
   }

   public int remove(int element) {
      int length = this.set.length;
      int index = element % length;

      int currentElement;
      while((currentElement = this.set[index]) != 0) {
         if (currentElement == element) {
            int existing = this.set[index];
            --this.elementSize;
            this.set[index] = 0;
            this.rehash();
            return existing;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return 0;
   }

   private void rehash() {
      HashSetOfInt newHashSet = new HashSetOfInt(this.elementSize * 2);
      int i = this.set.length;

      while(true) {
         --i;
         if (i < 0) {
            this.set = newHashSet.set;
            this.threshold = newHashSet.threshold;
            return;
         }

         int currentElement;
         if ((currentElement = this.set[i]) != 0) {
            newHashSet.add(currentElement);
         }
      }
   }

   public int size() {
      return this.elementSize;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      int i = 0;

      for(int length = this.set.length; i < length; ++i) {
         int element;
         if ((element = this.set[i]) != 0) {
            buffer.append(element);
            if (i != length - 1) {
               buffer.append('\n');
            }
         }
      }

      return buffer.toString();
   }
}
