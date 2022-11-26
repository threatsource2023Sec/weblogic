package org.python.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class GF2Vector extends Vector {
   private int[] v;

   public GF2Vector(int var1) {
      if (var1 < 0) {
         throw new ArithmeticException("Negative length.");
      } else {
         this.length = var1;
         this.v = new int[var1 + 31 >> 5];
      }
   }

   public GF2Vector(int var1, SecureRandom var2) {
      this.length = var1;
      int var3 = var1 + 31 >> 5;
      this.v = new int[var3];

      int var4;
      for(var4 = var3 - 1; var4 >= 0; --var4) {
         this.v[var4] = var2.nextInt();
      }

      var4 = var1 & 31;
      if (var4 != 0) {
         int[] var10000 = this.v;
         var10000[var3 - 1] &= (1 << var4) - 1;
      }

   }

   public GF2Vector(int var1, int var2, SecureRandom var3) {
      if (var2 > var1) {
         throw new ArithmeticException("The hamming weight is greater than the length of vector.");
      } else {
         this.length = var1;
         int var4 = var1 + 31 >> 5;
         this.v = new int[var4];
         int[] var5 = new int[var1];

         int var6;
         for(var6 = 0; var6 < var1; var5[var6] = var6++) {
         }

         var6 = var1;

         for(int var7 = 0; var7 < var2; ++var7) {
            int var8 = RandUtils.nextInt(var3, var6);
            this.setBit(var5[var8]);
            --var6;
            var5[var8] = var5[var6];
         }

      }
   }

   public GF2Vector(int var1, int[] var2) {
      if (var1 < 0) {
         throw new ArithmeticException("negative length");
      } else {
         this.length = var1;
         int var3 = var1 + 31 >> 5;
         if (var2.length != var3) {
            throw new ArithmeticException("length mismatch");
         } else {
            this.v = IntUtils.clone(var2);
            int var4 = var1 & 31;
            if (var4 != 0) {
               int[] var10000 = this.v;
               var10000[var3 - 1] &= (1 << var4) - 1;
            }

         }
      }
   }

   public GF2Vector(GF2Vector var1) {
      this.length = var1.length;
      this.v = IntUtils.clone(var1.v);
   }

   protected GF2Vector(int[] var1, int var2) {
      this.v = var1;
      this.length = var2;
   }

   public static GF2Vector OS2VP(int var0, byte[] var1) {
      if (var0 < 0) {
         throw new ArithmeticException("negative length");
      } else {
         int var2 = var0 + 7 >> 3;
         if (var1.length > var2) {
            throw new ArithmeticException("length mismatch");
         } else {
            return new GF2Vector(var0, LittleEndianConversions.toIntArray(var1));
         }
      }
   }

   public byte[] getEncoded() {
      int var1 = this.length + 7 >> 3;
      return LittleEndianConversions.toByteArray(this.v, var1);
   }

   public int[] getVecArray() {
      return this.v;
   }

   public int getHammingWeight() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.v.length; ++var2) {
         int var3 = this.v[var2];

         for(int var4 = 0; var4 < 32; ++var4) {
            int var5 = var3 & 1;
            if (var5 != 0) {
               ++var1;
            }

            var3 >>>= 1;
         }
      }

      return var1;
   }

   public boolean isZero() {
      for(int var1 = this.v.length - 1; var1 >= 0; --var1) {
         if (this.v[var1] != 0) {
            return false;
         }
      }

      return true;
   }

   public int getBit(int var1) {
      if (var1 >= this.length) {
         throw new IndexOutOfBoundsException();
      } else {
         int var2 = var1 >> 5;
         int var3 = var1 & 31;
         return (this.v[var2] & 1 << var3) >>> var3;
      }
   }

   public void setBit(int var1) {
      if (var1 >= this.length) {
         throw new IndexOutOfBoundsException();
      } else {
         int[] var10000 = this.v;
         var10000[var1 >> 5] |= 1 << (var1 & 31);
      }
   }

   public Vector add(Vector var1) {
      if (!(var1 instanceof GF2Vector)) {
         throw new ArithmeticException("vector is not defined over GF(2)");
      } else {
         GF2Vector var2 = (GF2Vector)var1;
         if (this.length != var2.length) {
            throw new ArithmeticException("length mismatch");
         } else {
            int[] var3 = IntUtils.clone(((GF2Vector)var1).v);

            for(int var4 = var3.length - 1; var4 >= 0; --var4) {
               var3[var4] ^= this.v[var4];
            }

            return new GF2Vector(this.length, var3);
         }
      }
   }

   public Vector multiply(Permutation var1) {
      int[] var2 = var1.getVector();
      if (this.length != var2.length) {
         throw new ArithmeticException("length mismatch");
      } else {
         GF2Vector var3 = new GF2Vector(this.length);

         for(int var4 = 0; var4 < var2.length; ++var4) {
            int var5 = this.v[var2[var4] >> 5] & 1 << (var2[var4] & 31);
            if (var5 != 0) {
               int[] var10000 = var3.v;
               var10000[var4 >> 5] |= 1 << (var4 & 31);
            }
         }

         return var3;
      }
   }

   public GF2Vector extractVector(int[] var1) {
      int var2 = var1.length;
      if (var1[var2 - 1] > this.length) {
         throw new ArithmeticException("invalid index set");
      } else {
         GF2Vector var3 = new GF2Vector(var2);

         for(int var4 = 0; var4 < var2; ++var4) {
            int var5 = this.v[var1[var4] >> 5] & 1 << (var1[var4] & 31);
            if (var5 != 0) {
               int[] var10000 = var3.v;
               var10000[var4 >> 5] |= 1 << (var4 & 31);
            }
         }

         return var3;
      }
   }

   public GF2Vector extractLeftVector(int var1) {
      if (var1 > this.length) {
         throw new ArithmeticException("invalid length");
      } else if (var1 == this.length) {
         return new GF2Vector(this);
      } else {
         GF2Vector var2 = new GF2Vector(var1);
         int var3 = var1 >> 5;
         int var4 = var1 & 31;
         System.arraycopy(this.v, 0, var2.v, 0, var3);
         if (var4 != 0) {
            var2.v[var3] = this.v[var3] & (1 << var4) - 1;
         }

         return var2;
      }
   }

   public GF2Vector extractRightVector(int var1) {
      if (var1 > this.length) {
         throw new ArithmeticException("invalid length");
      } else if (var1 == this.length) {
         return new GF2Vector(this);
      } else {
         GF2Vector var2 = new GF2Vector(var1);
         int var3 = this.length - var1 >> 5;
         int var4 = this.length - var1 & 31;
         int var5 = var1 + 31 >> 5;
         int var6 = var3;
         if (var4 != 0) {
            for(int var7 = 0; var7 < var5 - 1; ++var7) {
               var2.v[var7] = this.v[var6++] >>> var4 | this.v[var6] << 32 - var4;
            }

            var2.v[var5 - 1] = this.v[var6++] >>> var4;
            if (var6 < this.v.length) {
               int[] var10000 = var2.v;
               var10000[var5 - 1] |= this.v[var6] << 32 - var4;
            }
         } else {
            System.arraycopy(this.v, var3, var2.v, 0, var5);
         }

         return var2;
      }
   }

   public GF2mVector toExtensionFieldVector(GF2mField var1) {
      int var2 = var1.getDegree();
      if (this.length % var2 != 0) {
         throw new ArithmeticException("conversion is impossible");
      } else {
         int var3 = this.length / var2;
         int[] var4 = new int[var3];
         int var5 = 0;

         for(int var6 = var3 - 1; var6 >= 0; --var6) {
            for(int var7 = var1.getDegree() - 1; var7 >= 0; --var7) {
               int var8 = var5 >>> 5;
               int var9 = var5 & 31;
               int var10 = this.v[var8] >>> var9 & 1;
               if (var10 == 1) {
                  var4[var6] ^= 1 << var7;
               }

               ++var5;
            }
         }

         return new GF2mVector(var1, var4);
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof GF2Vector)) {
         return false;
      } else {
         GF2Vector var2 = (GF2Vector)var1;
         return this.length == var2.length && IntUtils.equals(this.v, var2.v);
      }
   }

   public int hashCode() {
      int var1 = this.length;
      var1 = var1 * 31 + this.v.hashCode();
      return var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < this.length; ++var2) {
         if (var2 != 0 && (var2 & 31) == 0) {
            var1.append(' ');
         }

         int var3 = var2 >> 5;
         int var4 = var2 & 31;
         int var5 = this.v[var3] & 1 << var4;
         if (var5 == 0) {
            var1.append('0');
         } else {
            var1.append('1');
         }
      }

      return var1.toString();
   }
}
