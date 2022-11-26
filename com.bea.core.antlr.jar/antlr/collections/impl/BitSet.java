package antlr.collections.impl;

import antlr.CharFormatter;

public class BitSet implements Cloneable {
   protected static final int BITS = 64;
   protected static final int NIBBLE = 4;
   protected static final int LOG_BITS = 6;
   protected static final int MOD_MASK = 63;
   protected long[] bits;

   public BitSet() {
      this(64);
   }

   public BitSet(long[] var1) {
      this.bits = var1;
   }

   public BitSet(int var1) {
      this.bits = new long[(var1 - 1 >> 6) + 1];
   }

   public void add(int var1) {
      int var2 = wordNumber(var1);
      if (var2 >= this.bits.length) {
         this.growToInclude(var1);
      }

      long[] var10000 = this.bits;
      var10000[var2] |= bitMask(var1);
   }

   public BitSet and(BitSet var1) {
      BitSet var2 = (BitSet)this.clone();
      var2.andInPlace(var1);
      return var2;
   }

   public void andInPlace(BitSet var1) {
      int var2 = Math.min(this.bits.length, var1.bits.length);

      int var3;
      for(var3 = var2 - 1; var3 >= 0; --var3) {
         long[] var10000 = this.bits;
         var10000[var3] &= var1.bits[var3];
      }

      for(var3 = var2; var3 < this.bits.length; ++var3) {
         this.bits[var3] = 0L;
      }

   }

   private static final long bitMask(int var0) {
      int var1 = var0 & 63;
      return 1L << var1;
   }

   public void clear() {
      for(int var1 = this.bits.length - 1; var1 >= 0; --var1) {
         this.bits[var1] = 0L;
      }

   }

   public void clear(int var1) {
      int var2 = wordNumber(var1);
      if (var2 >= this.bits.length) {
         this.growToInclude(var1);
      }

      long[] var10000 = this.bits;
      var10000[var2] &= ~bitMask(var1);
   }

   public Object clone() {
      try {
         BitSet var1 = (BitSet)super.clone();
         var1.bits = new long[this.bits.length];
         System.arraycopy(this.bits, 0, var1.bits, 0, this.bits.length);
         return var1;
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }
   }

   public int degree() {
      int var1 = 0;

      for(int var2 = this.bits.length - 1; var2 >= 0; --var2) {
         long var3 = this.bits[var2];
         if (var3 != 0L) {
            for(int var5 = 63; var5 >= 0; --var5) {
               if ((var3 & 1L << var5) != 0L) {
                  ++var1;
               }
            }
         }
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof BitSet) {
         BitSet var2 = (BitSet)var1;
         int var3 = Math.min(this.bits.length, var2.bits.length);
         int var4 = var3;

         while(var4-- > 0) {
            if (this.bits[var4] != var2.bits[var4]) {
               return false;
            }
         }

         if (this.bits.length > var3) {
            var4 = this.bits.length;

            while(var4-- > var3) {
               if (this.bits[var4] != 0L) {
                  return false;
               }
            }
         } else if (var2.bits.length > var3) {
            var4 = var2.bits.length;

            while(var4-- > var3) {
               if (var2.bits[var4] != 0L) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static Vector getRanges(int[] var0) {
      if (var0.length == 0) {
         return null;
      } else {
         int var1 = var0[0];
         int var10000 = var0[var0.length - 1];
         if (var0.length <= 2) {
            return null;
         } else {
            Vector var3 = new Vector(5);

            for(int var4 = 0; var4 < var0.length - 2; ++var4) {
               int var5 = var0.length - 1;

               for(int var6 = var4 + 1; var6 < var0.length; ++var6) {
                  if (var0[var6] != var0[var6 - 1] + 1) {
                     var5 = var6 - 1;
                     break;
                  }
               }

               if (var5 - var4 > 2) {
                  var3.appendElement(new IntRange(var0[var4], var0[var5]));
               }
            }

            return var3;
         }
      }
   }

   public void growToInclude(int var1) {
      int var2 = Math.max(this.bits.length << 1, this.numWordsToHold(var1));
      long[] var3 = new long[var2];
      System.arraycopy(this.bits, 0, var3, 0, this.bits.length);
      this.bits = var3;
   }

   public boolean member(int var1) {
      int var2 = wordNumber(var1);
      if (var2 >= this.bits.length) {
         return false;
      } else {
         return (this.bits[var2] & bitMask(var1)) != 0L;
      }
   }

   public boolean nil() {
      for(int var1 = this.bits.length - 1; var1 >= 0; --var1) {
         if (this.bits[var1] != 0L) {
            return false;
         }
      }

      return true;
   }

   public BitSet not() {
      BitSet var1 = (BitSet)this.clone();
      var1.notInPlace();
      return var1;
   }

   public void notInPlace() {
      for(int var1 = this.bits.length - 1; var1 >= 0; --var1) {
         this.bits[var1] = ~this.bits[var1];
      }

   }

   public void notInPlace(int var1) {
      this.notInPlace(0, var1);
   }

   public void notInPlace(int var1, int var2) {
      this.growToInclude(var2);

      for(int var3 = var1; var3 <= var2; ++var3) {
         int var4 = wordNumber(var3);
         long[] var10000 = this.bits;
         var10000[var4] ^= bitMask(var3);
      }

   }

   private final int numWordsToHold(int var1) {
      return (var1 >> 6) + 1;
   }

   public static BitSet of(int var0) {
      BitSet var1 = new BitSet(var0 + 1);
      var1.add(var0);
      return var1;
   }

   public BitSet or(BitSet var1) {
      BitSet var2 = (BitSet)this.clone();
      var2.orInPlace(var1);
      return var2;
   }

   public void orInPlace(BitSet var1) {
      if (var1.bits.length > this.bits.length) {
         this.setSize(var1.bits.length);
      }

      int var2 = Math.min(this.bits.length, var1.bits.length);

      for(int var3 = var2 - 1; var3 >= 0; --var3) {
         long[] var10000 = this.bits;
         var10000[var3] |= var1.bits[var3];
      }

   }

   public void remove(int var1) {
      int var2 = wordNumber(var1);
      if (var2 >= this.bits.length) {
         this.growToInclude(var1);
      }

      long[] var10000 = this.bits;
      var10000[var2] &= ~bitMask(var1);
   }

   private void setSize(int var1) {
      long[] var2 = new long[var1];
      int var3 = Math.min(var1, this.bits.length);
      System.arraycopy(this.bits, 0, var2, 0, var3);
      this.bits = var2;
   }

   public int size() {
      return this.bits.length << 6;
   }

   public int lengthInLongWords() {
      return this.bits.length;
   }

   public boolean subset(BitSet var1) {
      return var1 != null && var1 instanceof BitSet ? this.and(var1).equals(this) : false;
   }

   public void subtractInPlace(BitSet var1) {
      if (var1 != null) {
         for(int var2 = 0; var2 < this.bits.length && var2 < var1.bits.length; ++var2) {
            long[] var10000 = this.bits;
            var10000[var2] &= ~var1.bits[var2];
         }

      }
   }

   public int[] toArray() {
      int[] var1 = new int[this.degree()];
      int var2 = 0;

      for(int var3 = 0; var3 < this.bits.length << 6; ++var3) {
         if (this.member(var3)) {
            var1[var2++] = var3;
         }
      }

      return var1;
   }

   public long[] toPackedArray() {
      return this.bits;
   }

   public String toString() {
      return this.toString(",");
   }

   public String toString(String var1) {
      String var2 = "";

      for(int var3 = 0; var3 < this.bits.length << 6; ++var3) {
         if (this.member(var3)) {
            if (var2.length() > 0) {
               var2 = var2 + var1;
            }

            var2 = var2 + var3;
         }
      }

      return var2;
   }

   public String toString(String var1, CharFormatter var2) {
      String var3 = "";

      for(int var4 = 0; var4 < this.bits.length << 6; ++var4) {
         if (this.member(var4)) {
            if (var3.length() > 0) {
               var3 = var3 + var1;
            }

            var3 = var3 + var2.literalChar(var4);
         }
      }

      return var3;
   }

   public String toString(String var1, Vector var2) {
      if (var2 == null) {
         return this.toString(var1);
      } else {
         String var3 = "";

         for(int var4 = 0; var4 < this.bits.length << 6; ++var4) {
            if (this.member(var4)) {
               if (var3.length() > 0) {
                  var3 = var3 + var1;
               }

               if (var4 >= var2.size()) {
                  var3 = var3 + "<bad element " + var4 + ">";
               } else if (var2.elementAt(var4) == null) {
                  var3 = var3 + "<" + var4 + ">";
               } else {
                  var3 = var3 + (String)var2.elementAt(var4);
               }
            }
         }

         return var3;
      }
   }

   public String toStringOfHalfWords() {
      String var1 = new String();

      for(int var2 = 0; var2 < this.bits.length; ++var2) {
         if (var2 != 0) {
            var1 = var1 + ", ";
         }

         long var3 = this.bits[var2];
         var3 &= 4294967295L;
         var1 = var1 + var3 + "UL";
         var1 = var1 + ", ";
         var3 = this.bits[var2] >>> 32;
         var3 &= 4294967295L;
         var1 = var1 + var3 + "UL";
      }

      return var1;
   }

   public String toStringOfWords() {
      String var1 = new String();

      for(int var2 = 0; var2 < this.bits.length; ++var2) {
         if (var2 != 0) {
            var1 = var1 + ", ";
         }

         var1 = var1 + this.bits[var2] + "L";
      }

      return var1;
   }

   public String toStringWithRanges(String var1, CharFormatter var2) {
      String var3 = "";
      int[] var4 = this.toArray();
      if (var4.length == 0) {
         return "";
      } else {
         for(int var5 = 0; var5 < var4.length; ++var5) {
            int var6 = 0;

            for(int var7 = var5 + 1; var7 < var4.length && var4[var7] == var4[var7 - 1] + 1; var6 = var7++) {
            }

            if (var3.length() > 0) {
               var3 = var3 + var1;
            }

            if (var6 - var5 >= 2) {
               var3 = var3 + var2.literalChar(var4[var5]);
               var3 = var3 + "..";
               var3 = var3 + var2.literalChar(var4[var6]);
               var5 = var6;
            } else {
               var3 = var3 + var2.literalChar(var4[var5]);
            }
         }

         return var3;
      }
   }

   private static final int wordNumber(int var0) {
      return var0 >> 6;
   }
}
