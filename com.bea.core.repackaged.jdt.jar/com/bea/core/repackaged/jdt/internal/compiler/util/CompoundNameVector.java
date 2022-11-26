package com.bea.core.repackaged.jdt.internal.compiler.util;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;

public final class CompoundNameVector {
   static int INITIAL_SIZE = 10;
   public int size;
   int maxSize;
   char[][][] elements;

   public CompoundNameVector() {
      this.maxSize = INITIAL_SIZE;
      this.size = 0;
      this.elements = new char[this.maxSize][][];
   }

   public void add(char[][] newElement) {
      if (this.size == this.maxSize) {
         System.arraycopy(this.elements, 0, this.elements = new char[this.maxSize *= 2][][], 0, this.size);
      }

      this.elements[this.size++] = newElement;
   }

   public void addAll(char[][][] newElements) {
      if (this.size + newElements.length >= this.maxSize) {
         this.maxSize = this.size + newElements.length;
         System.arraycopy(this.elements, 0, this.elements = new char[this.maxSize][][], 0, this.size);
      }

      System.arraycopy(newElements, 0, this.elements, this.size, newElements.length);
      this.size += newElements.length;
   }

   public boolean contains(char[][] element) {
      int i = this.size;

      do {
         --i;
         if (i < 0) {
            return false;
         }
      } while(!CharOperation.equals(element, this.elements[i]));

      return true;
   }

   public char[][] elementAt(int index) {
      return this.elements[index];
   }

   public char[][] remove(char[][] element) {
      int i = this.size;

      do {
         --i;
         if (i < 0) {
            return null;
         }
      } while(element != this.elements[i]);

      System.arraycopy(this.elements, i + 1, this.elements, i, --this.size - i);
      this.elements[this.size] = null;
      return element;
   }

   public void removeAll() {
      int i = this.size;

      while(true) {
         --i;
         if (i < 0) {
            this.size = 0;
            return;
         }

         this.elements[i] = null;
      }
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();

      for(int i = 0; i < this.size; ++i) {
         buffer.append(CharOperation.toString(this.elements[i])).append("\n");
      }

      return buffer.toString();
   }
}
