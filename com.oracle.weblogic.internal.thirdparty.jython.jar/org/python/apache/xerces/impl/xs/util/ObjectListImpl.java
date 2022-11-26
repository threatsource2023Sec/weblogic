package org.python.apache.xerces.impl.xs.util;

import java.lang.reflect.Array;
import java.util.AbstractList;
import org.python.apache.xerces.xs.datatypes.ObjectList;

public final class ObjectListImpl extends AbstractList implements ObjectList {
   public static final ObjectListImpl EMPTY_LIST = new ObjectListImpl(new Object[0], 0);
   private final Object[] fArray;
   private final int fLength;

   public ObjectListImpl(Object[] var1, int var2) {
      this.fArray = var1;
      this.fLength = var2;
   }

   public int getLength() {
      return this.fLength;
   }

   public boolean contains(Object var1) {
      int var2;
      if (var1 == null) {
         for(var2 = 0; var2 < this.fLength; ++var2) {
            if (this.fArray[var2] == null) {
               return true;
            }
         }
      } else {
         for(var2 = 0; var2 < this.fLength; ++var2) {
            if (var1.equals(this.fArray[var2])) {
               return true;
            }
         }
      }

      return false;
   }

   public Object item(int var1) {
      return var1 >= 0 && var1 < this.fLength ? this.fArray[var1] : null;
   }

   public Object get(int var1) {
      if (var1 >= 0 && var1 < this.fLength) {
         return this.fArray[var1];
      } else {
         throw new IndexOutOfBoundsException("Index: " + var1);
      }
   }

   public int size() {
      return this.getLength();
   }

   public Object[] toArray() {
      Object[] var1 = new Object[this.fLength];
      this.toArray0(var1);
      return var1;
   }

   public Object[] toArray(Object[] var1) {
      if (var1.length < this.fLength) {
         Class var2 = var1.getClass();
         Class var3 = var2.getComponentType();
         var1 = (Object[])Array.newInstance(var3, this.fLength);
      }

      this.toArray0(var1);
      if (var1.length > this.fLength) {
         var1[this.fLength] = null;
      }

      return var1;
   }

   private void toArray0(Object[] var1) {
      if (this.fLength > 0) {
         System.arraycopy(this.fArray, 0, var1, 0, this.fLength);
      }

   }
}
