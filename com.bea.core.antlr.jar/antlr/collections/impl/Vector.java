package antlr.collections.impl;

import java.util.Enumeration;

public class Vector implements Cloneable {
   protected Object[] data;
   protected int lastElement;

   public Vector() {
      this(10);
   }

   public Vector(int var1) {
      this.lastElement = -1;
      this.data = new Object[var1];
   }

   public synchronized void appendElement(Object var1) {
      this.ensureCapacity(this.lastElement + 2);
      this.data[++this.lastElement] = var1;
   }

   public int capacity() {
      return this.data.length;
   }

   public Object clone() {
      Vector var1 = null;

      try {
         var1 = (Vector)super.clone();
      } catch (CloneNotSupportedException var3) {
         System.err.println("cannot clone Vector.super");
         return null;
      }

      var1.data = new Object[this.size()];
      System.arraycopy(this.data, 0, var1.data, 0, this.size());
      return var1;
   }

   public synchronized Object elementAt(int var1) {
      if (var1 >= this.data.length) {
         throw new ArrayIndexOutOfBoundsException(var1 + " >= " + this.data.length);
      } else if (var1 < 0) {
         throw new ArrayIndexOutOfBoundsException(var1 + " < 0 ");
      } else {
         return this.data[var1];
      }
   }

   public synchronized Enumeration elements() {
      return new VectorEnumerator(this);
   }

   public synchronized void ensureCapacity(int var1) {
      if (var1 + 1 > this.data.length) {
         Object[] var2 = this.data;
         int var3 = this.data.length * 2;
         if (var1 + 1 > var3) {
            var3 = var1 + 1;
         }

         this.data = new Object[var3];
         System.arraycopy(var2, 0, this.data, 0, var2.length);
      }

   }

   public synchronized boolean removeElement(Object var1) {
      int var2;
      for(var2 = 0; var2 <= this.lastElement && this.data[var2] != var1; ++var2) {
      }

      if (var2 <= this.lastElement) {
         this.data[var2] = null;
         int var3 = this.lastElement - var2;
         if (var3 > 0) {
            System.arraycopy(this.data, var2 + 1, this.data, var2, var3);
         }

         --this.lastElement;
         return true;
      } else {
         return false;
      }
   }

   public synchronized void setElementAt(Object var1, int var2) {
      if (var2 >= this.data.length) {
         throw new ArrayIndexOutOfBoundsException(var2 + " >= " + this.data.length);
      } else {
         this.data[var2] = var1;
         if (var2 > this.lastElement) {
            this.lastElement = var2;
         }

      }
   }

   public int size() {
      return this.lastElement + 1;
   }
}
