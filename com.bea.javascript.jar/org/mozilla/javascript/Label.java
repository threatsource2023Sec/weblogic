package org.mozilla.javascript;

public class Label {
   private static final int FIXUPTABLE_SIZE = 8;
   private static final boolean DEBUG = true;
   private short itsPC = -1;
   private int[] itsFixupTable;
   private int itsFixupTableTop;

   public void addFixup(int var1) {
      if (this.itsFixupTable == null) {
         this.itsFixupTableTop = 1;
         this.itsFixupTable = new int[8];
         this.itsFixupTable[0] = var1;
      } else {
         if (this.itsFixupTableTop == this.itsFixupTable.length) {
            int var2 = this.itsFixupTable.length;
            int[] var3 = new int[var2 + 8];
            System.arraycopy(this.itsFixupTable, 0, var3, 0, var2);
            this.itsFixupTable = var3;
         }

         this.itsFixupTable[this.itsFixupTableTop++] = var1;
      }

   }

   public void fixGotos(byte[] var1) {
      if (this.itsPC == -1 && this.itsFixupTable != null) {
         throw new RuntimeException("Unlocated label");
      } else {
         if (this.itsFixupTable != null) {
            for(int var2 = 0; var2 < this.itsFixupTableTop; ++var2) {
               int var3 = this.itsFixupTable[var2];
               short var4 = (short)(this.itsPC - (var3 - 1));
               var1[var3++] = (byte)(var4 >> 8);
               var1[var3] = (byte)var4;
            }
         }

         this.itsFixupTable = null;
      }
   }

   public short getPC() {
      return this.itsPC;
   }

   public void setPC(short var1) {
      if (this.itsPC != -1 && this.itsPC != var1) {
         throw new RuntimeException("Duplicate label");
      } else {
         this.itsPC = var1;
      }
   }
}
