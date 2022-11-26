package com.bea.core.repackaged.jdt.internal.compiler.util;

import java.util.Iterator;

public final class ObjectVector implements Iterable {
   static int INITIAL_SIZE = 10;
   public int size;
   int maxSize;
   Object[] elements;

   public ObjectVector() {
      this(INITIAL_SIZE);
   }

   public ObjectVector(int initialSize) {
      this.maxSize = initialSize > 0 ? initialSize : INITIAL_SIZE;
      this.size = 0;
      this.elements = new Object[this.maxSize];
   }

   public void add(Object newElement) {
      if (this.size == this.maxSize) {
         System.arraycopy(this.elements, 0, this.elements = new Object[this.maxSize *= 2], 0, this.size);
      }

      this.elements[this.size++] = newElement;
   }

   public void addAll(Object[] newElements) {
      if (this.size + newElements.length >= this.maxSize) {
         this.maxSize = this.size + newElements.length;
         System.arraycopy(this.elements, 0, this.elements = new Object[this.maxSize], 0, this.size);
      }

      System.arraycopy(newElements, 0, this.elements, this.size, newElements.length);
      this.size += newElements.length;
   }

   public void addAll(ObjectVector newVector) {
      if (this.size + newVector.size >= this.maxSize) {
         this.maxSize = this.size + newVector.size;
         System.arraycopy(this.elements, 0, this.elements = new Object[this.maxSize], 0, this.size);
      }

      System.arraycopy(newVector.elements, 0, this.elements, this.size, newVector.size);
      this.size += newVector.size;
   }

   public boolean containsIdentical(Object element) {
      int i = this.size;

      do {
         --i;
         if (i < 0) {
            return false;
         }
      } while(element != this.elements[i]);

      return true;
   }

   public boolean contains(Object element) {
      int i = this.size;

      do {
         --i;
         if (i < 0) {
            return false;
         }
      } while(!element.equals(this.elements[i]));

      return true;
   }

   public void copyInto(Object[] targetArray) {
      this.copyInto(targetArray, 0);
   }

   public void copyInto(Object[] targetArray, int index) {
      System.arraycopy(this.elements, 0, targetArray, index, this.size);
   }

   public Object elementAt(int index) {
      return this.elements[index];
   }

   public Object find(Object element) {
      int i = this.size;

      do {
         --i;
         if (i < 0) {
            return null;
         }
      } while(!element.equals(this.elements[i]));

      return this.elements[i];
   }

   public Object remove(Object element) {
      int i = this.size;

      do {
         --i;
         if (i < 0) {
            return null;
         }
      } while(!element.equals(this.elements[i]));

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

   public int size() {
      return this.size;
   }

   public String toString() {
      String s = "";

      for(int i = 0; i < this.size; ++i) {
         s = s + this.elements[i].toString() + "\n";
      }

      return s;
   }

   public Iterator iterator() {
      return new Iterator() {
         int i = 0;

         public boolean hasNext() {
            return this.i < ObjectVector.this.size;
         }

         public Object next() {
            return ObjectVector.this.elementAt(this.i++);
         }
      };
   }
}
