package org.python.bouncycastle.pqc.math.linearalgebra;

import java.math.BigInteger;
import java.security.SecureRandom;

public class GF2nONBElement extends GF2nElement {
   private static final long[] mBitmask = new long[]{1L, 2L, 4L, 8L, 16L, 32L, 64L, 128L, 256L, 512L, 1024L, 2048L, 4096L, 8192L, 16384L, 32768L, 65536L, 131072L, 262144L, 524288L, 1048576L, 2097152L, 4194304L, 8388608L, 16777216L, 33554432L, 67108864L, 134217728L, 268435456L, 536870912L, 1073741824L, 2147483648L, 4294967296L, 8589934592L, 17179869184L, 34359738368L, 68719476736L, 137438953472L, 274877906944L, 549755813888L, 1099511627776L, 2199023255552L, 4398046511104L, 8796093022208L, 17592186044416L, 35184372088832L, 70368744177664L, 140737488355328L, 281474976710656L, 562949953421312L, 1125899906842624L, 2251799813685248L, 4503599627370496L, 9007199254740992L, 18014398509481984L, 36028797018963968L, 72057594037927936L, 144115188075855872L, 288230376151711744L, 576460752303423488L, 1152921504606846976L, 2305843009213693952L, 4611686018427387904L, Long.MIN_VALUE};
   private static final long[] mMaxmask = new long[]{1L, 3L, 7L, 15L, 31L, 63L, 127L, 255L, 511L, 1023L, 2047L, 4095L, 8191L, 16383L, 32767L, 65535L, 131071L, 262143L, 524287L, 1048575L, 2097151L, 4194303L, 8388607L, 16777215L, 33554431L, 67108863L, 134217727L, 268435455L, 536870911L, 1073741823L, 2147483647L, 4294967295L, 8589934591L, 17179869183L, 34359738367L, 68719476735L, 137438953471L, 274877906943L, 549755813887L, 1099511627775L, 2199023255551L, 4398046511103L, 8796093022207L, 17592186044415L, 35184372088831L, 70368744177663L, 140737488355327L, 281474976710655L, 562949953421311L, 1125899906842623L, 2251799813685247L, 4503599627370495L, 9007199254740991L, 18014398509481983L, 36028797018963967L, 72057594037927935L, 144115188075855871L, 288230376151711743L, 576460752303423487L, 1152921504606846975L, 2305843009213693951L, 4611686018427387903L, Long.MAX_VALUE, -1L};
   private static final int[] mIBY64 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
   private static final int MAXLONG = 64;
   private int mLength;
   private int mBit;
   private long[] mPol;

   public GF2nONBElement(GF2nONBField var1, SecureRandom var2) {
      this.mField = var1;
      this.mDegree = this.mField.getDegree();
      this.mLength = var1.getONBLength();
      this.mBit = var1.getONBBit();
      this.mPol = new long[this.mLength];
      if (this.mLength > 1) {
         for(int var3 = 0; var3 < this.mLength - 1; ++var3) {
            this.mPol[var3] = var2.nextLong();
         }

         long var4 = var2.nextLong();
         this.mPol[this.mLength - 1] = var4 >>> 64 - this.mBit;
      } else {
         this.mPol[0] = var2.nextLong();
         this.mPol[0] >>>= 64 - this.mBit;
      }

   }

   public GF2nONBElement(GF2nONBField var1, byte[] var2) {
      this.mField = var1;
      this.mDegree = this.mField.getDegree();
      this.mLength = var1.getONBLength();
      this.mBit = var1.getONBBit();
      this.mPol = new long[this.mLength];
      this.assign(var2);
   }

   public GF2nONBElement(GF2nONBField var1, BigInteger var2) {
      this.mField = var1;
      this.mDegree = this.mField.getDegree();
      this.mLength = var1.getONBLength();
      this.mBit = var1.getONBBit();
      this.mPol = new long[this.mLength];
      this.assign(var2);
   }

   private GF2nONBElement(GF2nONBField var1, long[] var2) {
      this.mField = var1;
      this.mDegree = this.mField.getDegree();
      this.mLength = var1.getONBLength();
      this.mBit = var1.getONBBit();
      this.mPol = var2;
   }

   public GF2nONBElement(GF2nONBElement var1) {
      this.mField = var1.mField;
      this.mDegree = this.mField.getDegree();
      this.mLength = ((GF2nONBField)this.mField).getONBLength();
      this.mBit = ((GF2nONBField)this.mField).getONBBit();
      this.mPol = new long[this.mLength];
      this.assign(var1.getElement());
   }

   public Object clone() {
      return new GF2nONBElement(this);
   }

   public static GF2nONBElement ZERO(GF2nONBField var0) {
      long[] var1 = new long[var0.getONBLength()];
      return new GF2nONBElement(var0, var1);
   }

   public static GF2nONBElement ONE(GF2nONBField var0) {
      int var1 = var0.getONBLength();
      long[] var2 = new long[var1];

      for(int var3 = 0; var3 < var1 - 1; ++var3) {
         var2[var3] = -1L;
      }

      var2[var1 - 1] = mMaxmask[var0.getONBBit() - 1];
      return new GF2nONBElement(var0, var2);
   }

   void assignZero() {
      this.mPol = new long[this.mLength];
   }

   void assignOne() {
      for(int var1 = 0; var1 < this.mLength - 1; ++var1) {
         this.mPol[var1] = -1L;
      }

      this.mPol[this.mLength - 1] = mMaxmask[this.mBit - 1];
   }

   private void assign(BigInteger var1) {
      this.assign(var1.toByteArray());
   }

   private void assign(long[] var1) {
      System.arraycopy(var1, 0, this.mPol, 0, this.mLength);
   }

   private void assign(byte[] var1) {
      this.mPol = new long[this.mLength];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         long[] var10000 = this.mPol;
         var10000[var2 >>> 3] |= ((long)var1[var1.length - 1 - var2] & 255L) << ((var2 & 7) << 3);
      }

   }

   public boolean isZero() {
      boolean var1 = true;

      for(int var2 = 0; var2 < this.mLength && var1; ++var2) {
         var1 = var1 && (this.mPol[var2] & -1L) == 0L;
      }

      return var1;
   }

   public boolean isOne() {
      boolean var1 = true;

      for(int var2 = 0; var2 < this.mLength - 1 && var1; ++var2) {
         var1 = var1 && (this.mPol[var2] & -1L) == -1L;
      }

      if (var1) {
         var1 = var1 && (this.mPol[this.mLength - 1] & mMaxmask[this.mBit - 1]) == mMaxmask[this.mBit - 1];
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof GF2nONBElement) {
         GF2nONBElement var2 = (GF2nONBElement)var1;

         for(int var3 = 0; var3 < this.mLength; ++var3) {
            if (this.mPol[var3] != var2.mPol[var3]) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.mPol.hashCode();
   }

   public boolean testRightmostBit() {
      return (this.mPol[this.mLength - 1] & mBitmask[this.mBit - 1]) != 0L;
   }

   boolean testBit(int var1) {
      if (var1 >= 0 && var1 <= this.mDegree) {
         long var2 = this.mPol[var1 >>> 6] & mBitmask[var1 & 63];
         return var2 != 0L;
      } else {
         return false;
      }
   }

   private long[] getElement() {
      long[] var1 = new long[this.mPol.length];
      System.arraycopy(this.mPol, 0, var1, 0, this.mPol.length);
      return var1;
   }

   private long[] getElementReverseOrder() {
      long[] var1 = new long[this.mPol.length];

      for(int var2 = 0; var2 < this.mDegree; ++var2) {
         if (this.testBit(this.mDegree - var2 - 1)) {
            var1[var2 >>> 6] |= mBitmask[var2 & 63];
         }
      }

      return var1;
   }

   void reverseOrder() {
      this.mPol = this.getElementReverseOrder();
   }

   public GFElement add(GFElement var1) throws RuntimeException {
      GF2nONBElement var2 = new GF2nONBElement(this);
      var2.addToThis(var1);
      return var2;
   }

   public void addToThis(GFElement var1) throws RuntimeException {
      if (!(var1 instanceof GF2nONBElement)) {
         throw new RuntimeException();
      } else if (!this.mField.equals(((GF2nONBElement)var1).mField)) {
         throw new RuntimeException();
      } else {
         for(int var2 = 0; var2 < this.mLength; ++var2) {
            long[] var10000 = this.mPol;
            var10000[var2] ^= ((GF2nONBElement)var1).mPol[var2];
         }

      }
   }

   public GF2nElement increase() {
      GF2nONBElement var1 = new GF2nONBElement(this);
      var1.increaseThis();
      return var1;
   }

   public void increaseThis() {
      this.addToThis(ONE((GF2nONBField)this.mField));
   }

   public GFElement multiply(GFElement var1) throws RuntimeException {
      GF2nONBElement var2 = new GF2nONBElement(this);
      var2.multiplyThisBy(var1);
      return var2;
   }

   public void multiplyThisBy(GFElement var1) throws RuntimeException {
      if (!(var1 instanceof GF2nONBElement)) {
         throw new RuntimeException("The elements have different representation: not yet implemented");
      } else if (!this.mField.equals(((GF2nONBElement)var1).mField)) {
         throw new RuntimeException();
      } else {
         if (this.equals(var1)) {
            this.squareThis();
         } else {
            long[] var2 = this.mPol;
            long[] var3 = ((GF2nONBElement)var1).mPol;
            long[] var4 = new long[this.mLength];
            int[][] var5 = ((GF2nONBField)this.mField).mMult;
            int var6 = this.mLength - 1;
            int var7 = this.mBit - 1;
            boolean var8 = false;
            long var9 = mBitmask[63];
            long var11 = mBitmask[var7];

            for(int var13 = 0; var13 < this.mDegree; ++var13) {
               var8 = false;

               int var14;
               int var15;
               int var16;
               for(var14 = 0; var14 < this.mDegree; ++var14) {
                  var15 = mIBY64[var14];
                  var16 = var14 & 63;
                  int var17 = mIBY64[var5[var14][0]];
                  int var18 = var5[var14][0] & 63;
                  if ((var2[var15] & mBitmask[var16]) != 0L) {
                     if ((var3[var17] & mBitmask[var18]) != 0L) {
                        var8 ^= true;
                     }

                     if (var5[var14][1] != -1) {
                        var17 = mIBY64[var5[var14][1]];
                        var18 = var5[var14][1] & 63;
                        if ((var3[var17] & mBitmask[var18]) != 0L) {
                           var8 ^= true;
                        }
                     }
                  }
               }

               var15 = mIBY64[var13];
               var16 = var13 & 63;
               if (var8) {
                  var4[var15] ^= mBitmask[var16];
               }

               boolean var19;
               if (this.mLength <= 1) {
                  var19 = (var2[0] & 1L) == 1L;
                  var2[0] >>>= 1;
                  if (var19) {
                     var2[0] ^= var11;
                  }

                  var19 = (var3[0] & 1L) == 1L;
                  var3[0] >>>= 1;
                  if (var19) {
                     var3[0] ^= var11;
                  }
               } else {
                  var19 = (var2[var6] & 1L) == 1L;

                  boolean var20;
                  for(var14 = var6 - 1; var14 >= 0; --var14) {
                     var20 = (var2[var14] & 1L) != 0L;
                     var2[var14] >>>= 1;
                     if (var19) {
                        var2[var14] ^= var9;
                     }

                     var19 = var20;
                  }

                  var2[var6] >>>= 1;
                  if (var19) {
                     var2[var6] ^= var11;
                  }

                  var19 = (var3[var6] & 1L) == 1L;

                  for(var14 = var6 - 1; var14 >= 0; --var14) {
                     var20 = (var3[var14] & 1L) != 0L;
                     var3[var14] >>>= 1;
                     if (var19) {
                        var3[var14] ^= var9;
                     }

                     var19 = var20;
                  }

                  var3[var6] >>>= 1;
                  if (var19) {
                     var3[var6] ^= var11;
                  }
               }
            }

            this.assign(var4);
         }

      }
   }

   public GF2nElement square() {
      GF2nONBElement var1 = new GF2nONBElement(this);
      var1.squareThis();
      return var1;
   }

   public void squareThis() {
      long[] var1 = this.getElement();
      int var2 = this.mLength - 1;
      int var3 = this.mBit - 1;
      long var4 = mBitmask[63];
      boolean var6 = (var1[var2] & mBitmask[var3]) != 0L;

      boolean var8;
      for(int var7 = 0; var7 < var2; ++var7) {
         var8 = (var1[var7] & var4) != 0L;
         var1[var7] <<= 1;
         if (var6) {
            var1[var7] ^= 1L;
         }

         var6 = var8;
      }

      var8 = (var1[var2] & mBitmask[var3]) != 0L;
      var1[var2] <<= 1;
      if (var6) {
         var1[var2] ^= 1L;
      }

      if (var8) {
         var1[var2] ^= mBitmask[var3 + 1];
      }

      this.assign(var1);
   }

   public GFElement invert() throws ArithmeticException {
      GF2nONBElement var1 = new GF2nONBElement(this);
      var1.invertThis();
      return var1;
   }

   public void invertThis() throws ArithmeticException {
      if (this.isZero()) {
         throw new ArithmeticException();
      } else {
         int var1 = 31;

         for(boolean var2 = false; !var2 && var1 >= 0; --var1) {
            if (((long)(this.mDegree - 1) & mBitmask[var1]) != 0L) {
               var2 = true;
            }
         }

         ++var1;
         GF2nONBElement var7 = ZERO((GF2nONBField)this.mField);
         GF2nONBElement var3 = new GF2nONBElement(this);
         int var4 = 1;

         for(int var5 = var1 - 1; var5 >= 0; --var5) {
            GF2nElement var8 = (GF2nElement)var3.clone();

            for(int var6 = 1; var6 <= var4; ++var6) {
               var8.squareThis();
            }

            var3.multiplyThisBy(var8);
            var4 <<= 1;
            if (((long)(this.mDegree - 1) & mBitmask[var5]) != 0L) {
               var3.squareThis();
               var3.multiplyThisBy(this);
               ++var4;
            }
         }

         var3.squareThis();
      }
   }

   public GF2nElement squareRoot() {
      GF2nONBElement var1 = new GF2nONBElement(this);
      var1.squareRootThis();
      return var1;
   }

   public void squareRootThis() {
      long[] var1 = this.getElement();
      int var2 = this.mLength - 1;
      int var3 = this.mBit - 1;
      long var4 = mBitmask[63];
      boolean var6 = (var1[0] & 1L) != 0L;

      for(int var7 = var2; var7 >= 0; --var7) {
         boolean var8 = (var1[var7] & 1L) != 0L;
         var1[var7] >>>= 1;
         if (var6) {
            if (var7 == var2) {
               var1[var7] ^= mBitmask[var3];
            } else {
               var1[var7] ^= var4;
            }
         }

         var6 = var8;
      }

      this.assign(var1);
   }

   public int trace() {
      int var1 = 0;
      int var2 = this.mLength - 1;

      int var3;
      int var4;
      for(var3 = 0; var3 < var2; ++var3) {
         for(var4 = 0; var4 < 64; ++var4) {
            if ((this.mPol[var3] & mBitmask[var4]) != 0L) {
               var1 ^= 1;
            }
         }
      }

      var3 = this.mBit;

      for(var4 = 0; var4 < var3; ++var4) {
         if ((this.mPol[var2] & mBitmask[var4]) != 0L) {
            var1 ^= 1;
         }
      }

      return var1;
   }

   public GF2nElement solveQuadraticEquation() throws RuntimeException {
      if (this.trace() == 1) {
         throw new RuntimeException();
      } else {
         long var1 = mBitmask[63];
         long var3 = 0L;
         long var5 = 1L;
         long[] var7 = new long[this.mLength];
         long var8 = 0L;
         boolean var10 = true;

         int var11;
         int var14;
         for(var11 = 0; var11 < this.mLength - 1; ++var11) {
            for(var14 = 1; var14 < 64; ++var14) {
               if (((mBitmask[var14] & this.mPol[var11]) == var3 || (var8 & mBitmask[var14 - 1]) == var3) && ((this.mPol[var11] & mBitmask[var14]) != var3 || (var8 & mBitmask[var14 - 1]) != var3)) {
                  var8 ^= mBitmask[var14];
               }
            }

            var7[var11] = var8;
            if (((var1 & var8) == var3 || (var5 & this.mPol[var11 + 1]) != var5) && ((var1 & var8) != var3 || (var5 & this.mPol[var11 + 1]) != var3)) {
               var8 = var5;
            } else {
               var8 = var3;
            }
         }

         var11 = this.mDegree & 63;
         long var12 = this.mPol[this.mLength - 1];

         for(var14 = 1; var14 < var11; ++var14) {
            if (((mBitmask[var14] & var12) == var3 || (mBitmask[var14 - 1] & var8) == var3) && ((mBitmask[var14] & var12) != var3 || (mBitmask[var14 - 1] & var8) != var3)) {
               var8 ^= mBitmask[var14];
            }
         }

         var7[this.mLength - 1] = var8;
         return new GF2nONBElement((GF2nONBField)this.mField, var7);
      }
   }

   public String toString() {
      return this.toString(16);
   }

   public String toString(int var1) {
      String var2 = "";
      long[] var3 = this.getElement();
      int var4 = this.mBit;
      int var6;
      if (var1 == 2) {
         int var5;
         for(var5 = var4 - 1; var5 >= 0; --var5) {
            if ((var3[var3.length - 1] & 1L << var5) == 0L) {
               var2 = var2 + "0";
            } else {
               var2 = var2 + "1";
            }
         }

         for(var5 = var3.length - 2; var5 >= 0; --var5) {
            for(var6 = 63; var6 >= 0; --var6) {
               if ((var3[var5] & mBitmask[var6]) == 0L) {
                  var2 = var2 + "0";
               } else {
                  var2 = var2 + "1";
               }
            }
         }
      } else if (var1 == 16) {
         char[] var7 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

         for(var6 = var3.length - 1; var6 >= 0; --var6) {
            var2 = var2 + var7[(int)(var3[var6] >>> 60) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 56) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 52) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 48) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 44) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 40) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 36) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 32) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 28) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 24) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 20) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 16) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 12) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 8) & 15];
            var2 = var2 + var7[(int)(var3[var6] >>> 4) & 15];
            var2 = var2 + var7[(int)var3[var6] & 15];
            var2 = var2 + " ";
         }
      }

      return var2;
   }

   public BigInteger toFlexiBigInt() {
      return new BigInteger(1, this.toByteArray());
   }

   public byte[] toByteArray() {
      int var1 = (this.mDegree - 1 >> 3) + 1;
      byte[] var2 = new byte[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var1 - var3 - 1] = (byte)((int)((this.mPol[var3 >>> 3] & 255L << ((var3 & 7) << 3)) >>> ((var3 & 7) << 3)));
      }

      return var2;
   }
}
