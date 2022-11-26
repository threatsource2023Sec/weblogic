package weblogic.apache.xerces.impl.xs.util;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Vector;
import weblogic.apache.xerces.xs.StringList;

public final class StringListImpl extends AbstractList implements StringList {
   public static final StringListImpl EMPTY_LIST = new StringListImpl(new String[0], 0);
   private final String[] fArray;
   private final int fLength;
   private final Vector fVector;

   public StringListImpl(Vector var1) {
      this.fVector = var1;
      this.fLength = var1 == null ? 0 : var1.size();
      this.fArray = null;
   }

   public StringListImpl(String[] var1, int var2) {
      this.fArray = var1;
      this.fLength = var2;
      this.fVector = null;
   }

   public int getLength() {
      return this.fLength;
   }

   public boolean contains(String var1) {
      if (this.fVector != null) {
         return this.fVector.contains(var1);
      } else {
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
   }

   public String item(int var1) {
      if (var1 >= 0 && var1 < this.fLength) {
         return this.fVector != null ? (String)this.fVector.elementAt(var1) : this.fArray[var1];
      } else {
         return null;
      }
   }

   public Object get(int var1) {
      if (var1 >= 0 && var1 < this.fLength) {
         return this.fVector != null ? this.fVector.elementAt(var1) : this.fArray[var1];
      } else {
         throw new IndexOutOfBoundsException("Index: " + var1);
      }
   }

   public int size() {
      return this.getLength();
   }

   public Object[] toArray() {
      if (this.fVector != null) {
         return this.fVector.toArray();
      } else {
         Object[] var1 = new Object[this.fLength];
         this.toArray0(var1);
         return var1;
      }
   }

   public Object[] toArray(Object[] var1) {
      if (this.fVector != null) {
         return this.fVector.toArray(var1);
      } else {
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
   }

   private void toArray0(Object[] var1) {
      if (this.fLength > 0) {
         System.arraycopy(this.fArray, 0, var1, 0, this.fLength);
      }

   }
}
