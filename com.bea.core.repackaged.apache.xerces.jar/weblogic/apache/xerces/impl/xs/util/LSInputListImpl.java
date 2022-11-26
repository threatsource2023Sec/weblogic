package weblogic.apache.xerces.impl.xs.util;

import java.lang.reflect.Array;
import java.util.AbstractList;
import org.w3c.dom.ls.LSInput;
import weblogic.apache.xerces.xs.LSInputList;

public final class LSInputListImpl extends AbstractList implements LSInputList {
   public static final LSInputListImpl EMPTY_LIST = new LSInputListImpl(new LSInput[0], 0);
   private final LSInput[] fArray;
   private final int fLength;

   public LSInputListImpl(LSInput[] var1, int var2) {
      this.fArray = var1;
      this.fLength = var2;
   }

   public int getLength() {
      return this.fLength;
   }

   public LSInput item(int var1) {
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
