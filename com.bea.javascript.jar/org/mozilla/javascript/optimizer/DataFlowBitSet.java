package org.mozilla.javascript.optimizer;

class DataFlowBitSet {
   private int[] itsBits;
   int itsSize;

   DataFlowBitSet(int var1) {
      this.itsSize = var1;
      this.itsBits = new int[(var1 >> 5) + 1];
   }

   void clear() {
      int var1 = this.itsBits.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         this.itsBits[var2] = 0;
      }

   }

   void clear(int var1) {
      if (var1 >= 0 && var1 < this.itsSize) {
         int[] var10000 = this.itsBits;
         var10000[var1 >> 5] &= ~(1 << (var1 & 31));
      } else {
         throw new RuntimeException("DataFlowBitSet bad index " + var1);
      }
   }

   boolean df(DataFlowBitSet var1, DataFlowBitSet var2, DataFlowBitSet var3) {
      int var4 = this.itsBits.length;
      boolean var5 = false;

      for(int var6 = 0; var6 < var4; ++var6) {
         int var7 = this.itsBits[var6];
         this.itsBits[var6] = (var1.itsBits[var6] | var2.itsBits[var6]) & var3.itsBits[var6];
         var5 |= var7 != this.itsBits[var6];
      }

      return var5;
   }

   boolean df2(DataFlowBitSet var1, DataFlowBitSet var2, DataFlowBitSet var3) {
      int var4 = this.itsBits.length;
      boolean var5 = false;

      for(int var6 = 0; var6 < var4; ++var6) {
         int var7 = this.itsBits[var6];
         this.itsBits[var6] = var1.itsBits[var6] & var3.itsBits[var6] | var2.itsBits[var6];
         var5 |= var7 != this.itsBits[var6];
      }

      return var5;
   }

   void not() {
      int var1 = this.itsBits.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         this.itsBits[var2] = ~this.itsBits[var2];
      }

   }

   void or(DataFlowBitSet var1) {
      int var2 = this.itsBits.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         int[] var10000 = this.itsBits;
         var10000[var3] |= var1.itsBits[var3];
      }

   }

   void set(int var1) {
      if (var1 >= 0 && var1 < this.itsSize) {
         int[] var10000 = this.itsBits;
         var10000[var1 >> 5] |= 1 << (var1 & 31);
      } else {
         throw new RuntimeException("DataFlowBitSet bad index " + var1);
      }
   }

   int size() {
      return this.itsSize;
   }

   boolean test(int var1) {
      if (var1 >= 0 && var1 < this.itsSize) {
         return (this.itsBits[var1 >> 5] & 1 << (var1 & 31)) != 0;
      } else {
         throw new RuntimeException("DataFlowBitSet bad index " + var1);
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("DataFlowBitSet, size = " + this.itsSize + "\n");

      for(int var2 = 0; var2 < this.itsBits.length; ++var2) {
         var1.append(Integer.toHexString(this.itsBits[var2]) + " ");
      }

      return var1.toString();
   }
}
