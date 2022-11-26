package org.python.icu.impl;

import org.python.icu.util.Freezable;

public class Row implements Comparable, Cloneable, Freezable {
   protected Object[] items;
   protected volatile boolean frozen;

   public static R2 of(Object p0, Object p1) {
      return new R2(p0, p1);
   }

   public static R3 of(Object p0, Object p1, Object p2) {
      return new R3(p0, p1, p2);
   }

   public static R4 of(Object p0, Object p1, Object p2, Object p3) {
      return new R4(p0, p1, p2, p3);
   }

   public static R5 of(Object p0, Object p1, Object p2, Object p3, Object p4) {
      return new R5(p0, p1, p2, p3, p4);
   }

   public Row set0(Object item) {
      return this.set(0, item);
   }

   public Object get0() {
      return this.items[0];
   }

   public Row set1(Object item) {
      return this.set(1, item);
   }

   public Object get1() {
      return this.items[1];
   }

   public Row set2(Object item) {
      return this.set(2, item);
   }

   public Object get2() {
      return this.items[2];
   }

   public Row set3(Object item) {
      return this.set(3, item);
   }

   public Object get3() {
      return this.items[3];
   }

   public Row set4(Object item) {
      return this.set(4, item);
   }

   public Object get4() {
      return this.items[4];
   }

   protected Row set(int i, Object item) {
      if (this.frozen) {
         throw new UnsupportedOperationException("Attempt to modify frozen object");
      } else {
         this.items[i] = item;
         return this;
      }
   }

   public int hashCode() {
      int sum = this.items.length;
      Object[] var2 = this.items;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object item = var2[var4];
         sum = sum * 37 + Utility.checkHash(item);
      }

      return sum;
   }

   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (this == other) {
         return true;
      } else {
         try {
            Row that = (Row)other;
            if (this.items.length != that.items.length) {
               return false;
            } else {
               int i = 0;
               Object[] var4 = this.items;
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Object item = var4[var6];
                  if (!Utility.objectEquals(item, that.items[i++])) {
                     return false;
                  }
               }

               return true;
            }
         } catch (Exception var8) {
            return false;
         }
      }
   }

   public int compareTo(Object other) {
      Row that = (Row)other;
      int result = this.items.length - that.items.length;
      if (result != 0) {
         return result;
      } else {
         int i = 0;
         Object[] var5 = this.items;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Object item = var5[var7];
            result = Utility.checkCompare((Comparable)item, (Comparable)that.items[i++]);
            if (result != 0) {
               return result;
            }
         }

         return 0;
      }
   }

   public String toString() {
      StringBuilder result = new StringBuilder("[");
      boolean first = true;
      Object[] var3 = this.items;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object item = var3[var5];
         if (first) {
            first = false;
         } else {
            result.append(", ");
         }

         result.append(item);
      }

      return result.append("]").toString();
   }

   public boolean isFrozen() {
      return this.frozen;
   }

   public Row freeze() {
      this.frozen = true;
      return this;
   }

   public Object clone() {
      if (this.frozen) {
         return this;
      } else {
         try {
            Row result = (Row)super.clone();
            this.items = (Object[])this.items.clone();
            return result;
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   public Row cloneAsThawed() {
      try {
         Row result = (Row)super.clone();
         this.items = (Object[])this.items.clone();
         result.frozen = false;
         return result;
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   public static class R5 extends Row {
      public R5(Object a, Object b, Object c, Object d, Object e) {
         this.items = new Object[]{a, b, c, d, e};
      }
   }

   public static class R4 extends Row {
      public R4(Object a, Object b, Object c, Object d) {
         this.items = new Object[]{a, b, c, d};
      }
   }

   public static class R3 extends Row {
      public R3(Object a, Object b, Object c) {
         this.items = new Object[]{a, b, c};
      }
   }

   public static class R2 extends Row {
      public R2(Object a, Object b) {
         this.items = new Object[]{a, b};
      }
   }
}
