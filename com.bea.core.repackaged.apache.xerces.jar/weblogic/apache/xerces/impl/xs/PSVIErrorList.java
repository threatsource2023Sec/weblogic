package weblogic.apache.xerces.impl.xs;

import java.util.AbstractList;
import weblogic.apache.xerces.xs.StringList;

final class PSVIErrorList extends AbstractList implements StringList {
   private final String[] fArray;
   private final int fLength;
   private final int fOffset;

   public PSVIErrorList(String[] var1, boolean var2) {
      this.fArray = var1;
      this.fLength = this.fArray.length >> 1;
      this.fOffset = var2 ? 0 : 1;
   }

   public boolean contains(String var1) {
      int var2;
      if (var1 == null) {
         for(var2 = 0; var2 < this.fLength; ++var2) {
            if (this.fArray[(var2 << 1) + this.fOffset] == null) {
               return true;
            }
         }
      } else {
         for(var2 = 0; var2 < this.fLength; ++var2) {
            if (var1.equals(this.fArray[(var2 << 1) + this.fOffset])) {
               return true;
            }
         }
      }

      return false;
   }

   public int getLength() {
      return this.fLength;
   }

   public String item(int var1) {
      return var1 >= 0 && var1 < this.fLength ? this.fArray[(var1 << 1) + this.fOffset] : null;
   }

   public Object get(int var1) {
      if (var1 >= 0 && var1 < this.fLength) {
         return this.fArray[(var1 << 1) + this.fOffset];
      } else {
         throw new IndexOutOfBoundsException("Index: " + var1);
      }
   }

   public int size() {
      return this.getLength();
   }
}
