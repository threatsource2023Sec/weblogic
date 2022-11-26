package org.antlr.misc;

import java.util.AbstractList;

public class IntArrayList extends AbstractList implements Cloneable {
   private static final int DEFAULT_CAPACITY = 10;
   protected int n;
   protected int[] elements;

   public IntArrayList() {
      this(10);
   }

   public IntArrayList(int initialCapacity) {
      this.n = 0;
      this.elements = null;
      this.elements = new int[initialCapacity];
   }

   public int set(int i, int newValue) {
      if (i >= this.n) {
         this.setSize(i);
      }

      int v = this.elements[i];
      this.elements[i] = newValue;
      return v;
   }

   public boolean add(int o) {
      if (this.n >= this.elements.length) {
         this.grow();
      }

      this.elements[this.n] = o;
      ++this.n;
      return true;
   }

   public void setSize(int newSize) {
      if (newSize >= this.elements.length) {
         this.ensureCapacity(newSize);
      }

      this.n = newSize;
   }

   protected void grow() {
      this.ensureCapacity(this.elements.length * 3 / 2 + 1);
   }

   public boolean contains(int v) {
      for(int i = 0; i < this.n; ++i) {
         int element = this.elements[i];
         if (element == v) {
            return true;
         }
      }

      return false;
   }

   public void ensureCapacity(int newCapacity) {
      int oldCapacity = this.elements.length;
      if (this.n >= oldCapacity) {
         int[] oldData = this.elements;
         this.elements = new int[newCapacity];
         System.arraycopy(oldData, 0, this.elements, 0, this.n);
      }

   }

   public Integer get(int i) {
      return Utils.integer(this.element(i));
   }

   public int element(int i) {
      return this.elements[i];
   }

   public int[] elements() {
      int[] a = new int[this.n];
      System.arraycopy(this.elements, 0, a, 0, this.n);
      return a;
   }

   public int size() {
      return this.n;
   }

   public int capacity() {
      return this.elements.length;
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else {
         IntArrayList other = (IntArrayList)o;
         if (this.size() != other.size()) {
            return false;
         } else {
            for(int i = 0; i < this.n; ++i) {
               if (this.elements[i] != other.elements[i]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public Object clone() throws CloneNotSupportedException {
      IntArrayList a = (IntArrayList)super.clone();
      a.n = this.n;
      System.arraycopy(this.elements, 0, a.elements, 0, this.elements.length);
      return a;
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();

      for(int i = 0; i < this.n; ++i) {
         if (i > 0) {
            buf.append(", ");
         }

         buf.append(this.elements[i]);
      }

      return buf.toString();
   }
}
