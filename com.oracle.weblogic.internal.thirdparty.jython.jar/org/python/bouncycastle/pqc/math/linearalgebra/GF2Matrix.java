package org.python.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class GF2Matrix extends Matrix {
   private int[][] matrix;
   private int length;

   public GF2Matrix(byte[] var1) {
      if (var1.length < 9) {
         throw new ArithmeticException("given array is not an encoded matrix over GF(2)");
      } else {
         this.numRows = LittleEndianConversions.OS2IP(var1, 0);
         this.numColumns = LittleEndianConversions.OS2IP(var1, 4);
         int var2 = (this.numColumns + 7 >>> 3) * this.numRows;
         if (this.numRows > 0 && var2 == var1.length - 8) {
            this.length = this.numColumns + 31 >>> 5;
            this.matrix = new int[this.numRows][this.length];
            int var3 = this.numColumns >> 5;
            int var4 = this.numColumns & 31;
            int var5 = 8;

            for(int var6 = 0; var6 < this.numRows; ++var6) {
               int var7;
               for(var7 = 0; var7 < var3; var5 += 4) {
                  this.matrix[var6][var7] = LittleEndianConversions.OS2IP(var1, var5);
                  ++var7;
               }

               for(var7 = 0; var7 < var4; var7 += 8) {
                  int[] var10000 = this.matrix[var6];
                  var10000[var3] ^= (var1[var5++] & 255) << var7;
               }
            }

         } else {
            throw new ArithmeticException("given array is not an encoded matrix over GF(2)");
         }
      }
   }

   public GF2Matrix(int var1, int[][] var2) {
      if (var2[0].length != var1 + 31 >> 5) {
         throw new ArithmeticException("Int array does not match given number of columns.");
      } else {
         this.numColumns = var1;
         this.numRows = var2.length;
         this.length = var2[0].length;
         int var3 = var1 & 31;
         int var4;
         if (var3 == 0) {
            var4 = -1;
         } else {
            var4 = (1 << var3) - 1;
         }

         for(int var5 = 0; var5 < this.numRows; ++var5) {
            int[] var10000 = var2[var5];
            int var10001 = this.length - 1;
            var10000[var10001] &= var4;
         }

         this.matrix = var2;
      }
   }

   public GF2Matrix(int var1, char var2) {
      this(var1, var2, new SecureRandom());
   }

   public GF2Matrix(int var1, char var2, SecureRandom var3) {
      if (var1 <= 0) {
         throw new ArithmeticException("Size of matrix is non-positive.");
      } else {
         switch (var2) {
            case 'I':
               this.assignUnitMatrix(var1);
               break;
            case 'L':
               this.assignRandomLowerTriangularMatrix(var1, var3);
               break;
            case 'R':
               this.assignRandomRegularMatrix(var1, var3);
               break;
            case 'U':
               this.assignRandomUpperTriangularMatrix(var1, var3);
               break;
            case 'Z':
               this.assignZeroMatrix(var1, var1);
               break;
            default:
               throw new ArithmeticException("Unknown matrix type.");
         }

      }
   }

   public GF2Matrix(GF2Matrix var1) {
      this.numColumns = var1.getNumColumns();
      this.numRows = var1.getNumRows();
      this.length = var1.length;
      this.matrix = new int[var1.matrix.length][];

      for(int var2 = 0; var2 < this.matrix.length; ++var2) {
         this.matrix[var2] = IntUtils.clone(var1.matrix[var2]);
      }

   }

   private GF2Matrix(int var1, int var2) {
      if (var2 > 0 && var1 > 0) {
         this.assignZeroMatrix(var1, var2);
      } else {
         throw new ArithmeticException("size of matrix is non-positive");
      }
   }

   private void assignZeroMatrix(int var1, int var2) {
      this.numRows = var1;
      this.numColumns = var2;
      this.length = var2 + 31 >>> 5;
      this.matrix = new int[this.numRows][this.length];

      for(int var3 = 0; var3 < this.numRows; ++var3) {
         for(int var4 = 0; var4 < this.length; ++var4) {
            this.matrix[var3][var4] = 0;
         }
      }

   }

   private void assignUnitMatrix(int var1) {
      this.numRows = var1;
      this.numColumns = var1;
      this.length = var1 + 31 >>> 5;
      this.matrix = new int[this.numRows][this.length];

      int var2;
      int var3;
      for(var2 = 0; var2 < this.numRows; ++var2) {
         for(var3 = 0; var3 < this.length; ++var3) {
            this.matrix[var2][var3] = 0;
         }
      }

      for(var2 = 0; var2 < this.numRows; ++var2) {
         var3 = var2 & 31;
         this.matrix[var2][var2 >>> 5] = 1 << var3;
      }

   }

   private void assignRandomLowerTriangularMatrix(int var1, SecureRandom var2) {
      this.numRows = var1;
      this.numColumns = var1;
      this.length = var1 + 31 >>> 5;
      this.matrix = new int[this.numRows][this.length];

      for(int var3 = 0; var3 < this.numRows; ++var3) {
         int var4 = var3 >>> 5;
         int var5 = var3 & 31;
         int var6 = 31 - var5;
         var5 = 1 << var5;

         int var7;
         for(var7 = 0; var7 < var4; ++var7) {
            this.matrix[var3][var7] = var2.nextInt();
         }

         this.matrix[var3][var4] = var2.nextInt() >>> var6 | var5;

         for(var7 = var4 + 1; var7 < this.length; ++var7) {
            this.matrix[var3][var7] = 0;
         }
      }

   }

   private void assignRandomUpperTriangularMatrix(int var1, SecureRandom var2) {
      this.numRows = var1;
      this.numColumns = var1;
      this.length = var1 + 31 >>> 5;
      this.matrix = new int[this.numRows][this.length];
      int var3 = var1 & 31;
      int var4;
      if (var3 == 0) {
         var4 = -1;
      } else {
         var4 = (1 << var3) - 1;
      }

      for(int var5 = 0; var5 < this.numRows; ++var5) {
         int var6 = var5 >>> 5;
         int var7 = var5 & 31;
         int var8 = var7;
         var7 = 1 << var7;

         int var9;
         for(var9 = 0; var9 < var6; ++var9) {
            this.matrix[var5][var9] = 0;
         }

         this.matrix[var5][var6] = var2.nextInt() << var8 | var7;

         for(var9 = var6 + 1; var9 < this.length; ++var9) {
            this.matrix[var5][var9] = var2.nextInt();
         }

         int[] var10000 = this.matrix[var5];
         int var10001 = this.length - 1;
         var10000[var10001] &= var4;
      }

   }

   private void assignRandomRegularMatrix(int var1, SecureRandom var2) {
      this.numRows = var1;
      this.numColumns = var1;
      this.length = var1 + 31 >>> 5;
      this.matrix = new int[this.numRows][this.length];
      GF2Matrix var3 = new GF2Matrix(var1, 'L', var2);
      GF2Matrix var4 = new GF2Matrix(var1, 'U', var2);
      GF2Matrix var5 = (GF2Matrix)var3.rightMultiply((Matrix)var4);
      Permutation var6 = new Permutation(var1, var2);
      int[] var7 = var6.getVector();

      for(int var8 = 0; var8 < var1; ++var8) {
         System.arraycopy(var5.matrix[var8], 0, this.matrix[var7[var8]], 0, this.length);
      }

   }

   public static GF2Matrix[] createRandomRegularMatrixAndItsInverse(int var0, SecureRandom var1) {
      GF2Matrix[] var2 = new GF2Matrix[2];
      int var3 = var0 + 31 >> 5;
      GF2Matrix var4 = new GF2Matrix(var0, 'L', var1);
      GF2Matrix var5 = new GF2Matrix(var0, 'U', var1);
      GF2Matrix var6 = (GF2Matrix)var4.rightMultiply((Matrix)var5);
      Permutation var7 = new Permutation(var0, var1);
      int[] var8 = var7.getVector();
      int[][] var9 = new int[var0][var3];

      for(int var10 = 0; var10 < var0; ++var10) {
         System.arraycopy(var6.matrix[var8[var10]], 0, var9[var10], 0, var3);
      }

      var2[0] = new GF2Matrix(var0, var9);
      GF2Matrix var19 = new GF2Matrix(var0, 'I');

      int var12;
      int var13;
      int var14;
      int var15;
      int var16;
      int[] var10000;
      int var17;
      for(int var11 = 0; var11 < var0; ++var11) {
         var12 = var11 & 31;
         var13 = var11 >>> 5;
         var14 = 1 << var12;

         for(var15 = var11 + 1; var15 < var0; ++var15) {
            var16 = var4.matrix[var15][var13] & var14;
            if (var16 != 0) {
               for(var17 = 0; var17 <= var13; ++var17) {
                  var10000 = var19.matrix[var15];
                  var10000[var17] ^= var19.matrix[var11][var17];
               }
            }
         }
      }

      GF2Matrix var20 = new GF2Matrix(var0, 'I');

      for(var12 = var0 - 1; var12 >= 0; --var12) {
         var13 = var12 & 31;
         var14 = var12 >>> 5;
         var15 = 1 << var13;

         for(var16 = var12 - 1; var16 >= 0; --var16) {
            var17 = var5.matrix[var16][var14] & var15;
            if (var17 != 0) {
               for(int var18 = var14; var18 < var3; ++var18) {
                  var10000 = var20.matrix[var16];
                  var10000[var18] ^= var20.matrix[var12][var18];
               }
            }
         }
      }

      var2[1] = (GF2Matrix)var20.rightMultiply(var19.rightMultiply(var7));
      return var2;
   }

   public int[][] getIntArray() {
      return this.matrix;
   }

   public int getLength() {
      return this.length;
   }

   public int[] getRow(int var1) {
      return this.matrix[var1];
   }

   public byte[] getEncoded() {
      int var1 = this.numColumns + 7 >>> 3;
      var1 *= this.numRows;
      var1 += 8;
      byte[] var2 = new byte[var1];
      LittleEndianConversions.I2OSP(this.numRows, var2, 0);
      LittleEndianConversions.I2OSP(this.numColumns, var2, 4);
      int var3 = this.numColumns >>> 5;
      int var4 = this.numColumns & 31;
      int var5 = 8;

      for(int var6 = 0; var6 < this.numRows; ++var6) {
         int var7;
         for(var7 = 0; var7 < var3; var5 += 4) {
            LittleEndianConversions.I2OSP(this.matrix[var6][var7], var2, var5);
            ++var7;
         }

         for(var7 = 0; var7 < var4; var7 += 8) {
            var2[var5++] = (byte)(this.matrix[var6][var3] >>> var7 & 255);
         }
      }

      return var2;
   }

   public double getHammingWeight() {
      double var1 = 0.0;
      double var3 = 0.0;
      int var5 = this.numColumns & 31;
      int var6;
      if (var5 == 0) {
         var6 = this.length;
      } else {
         var6 = this.length - 1;
      }

      for(int var7 = 0; var7 < this.numRows; ++var7) {
         int var8;
         int var9;
         int var10;
         for(var8 = 0; var8 < var6; ++var8) {
            var9 = this.matrix[var7][var8];

            for(var10 = 0; var10 < 32; ++var10) {
               int var11 = var9 >>> var10 & 1;
               var1 += (double)var11;
               ++var3;
            }
         }

         var8 = this.matrix[var7][this.length - 1];

         for(var9 = 0; var9 < var5; ++var9) {
            var10 = var8 >>> var9 & 1;
            var1 += (double)var10;
            ++var3;
         }
      }

      return var1 / var3;
   }

   public boolean isZero() {
      for(int var1 = 0; var1 < this.numRows; ++var1) {
         for(int var2 = 0; var2 < this.length; ++var2) {
            if (this.matrix[var1][var2] != 0) {
               return false;
            }
         }
      }

      return true;
   }

   public GF2Matrix getLeftSubMatrix() {
      if (this.numColumns <= this.numRows) {
         throw new ArithmeticException("empty submatrix");
      } else {
         int var1 = this.numRows + 31 >> 5;
         int[][] var2 = new int[this.numRows][var1];
         int var3 = (1 << (this.numRows & 31)) - 1;
         if (var3 == 0) {
            var3 = -1;
         }

         for(int var4 = this.numRows - 1; var4 >= 0; --var4) {
            System.arraycopy(this.matrix[var4], 0, var2[var4], 0, var1);
            var2[var4][var1 - 1] &= var3;
         }

         return new GF2Matrix(this.numRows, var2);
      }
   }

   public GF2Matrix extendLeftCompactForm() {
      int var1 = this.numColumns + this.numRows;
      GF2Matrix var2 = new GF2Matrix(this.numRows, var1);
      int var3 = this.numRows - 1 + this.numColumns;

      for(int var4 = this.numRows - 1; var4 >= 0; --var3) {
         System.arraycopy(this.matrix[var4], 0, var2.matrix[var4], 0, this.length);
         int[] var10000 = var2.matrix[var4];
         var10000[var3 >> 5] |= 1 << (var3 & 31);
         --var4;
      }

      return var2;
   }

   public GF2Matrix getRightSubMatrix() {
      if (this.numColumns <= this.numRows) {
         throw new ArithmeticException("empty submatrix");
      } else {
         int var1 = this.numRows >> 5;
         int var2 = this.numRows & 31;
         GF2Matrix var3 = new GF2Matrix(this.numRows, this.numColumns - this.numRows);

         for(int var4 = this.numRows - 1; var4 >= 0; --var4) {
            if (var2 == 0) {
               System.arraycopy(this.matrix[var4], var1, var3.matrix[var4], 0, var3.length);
            } else {
               int var5 = var1;

               for(int var6 = 0; var6 < var3.length - 1; ++var6) {
                  var3.matrix[var4][var6] = this.matrix[var4][var5++] >>> var2 | this.matrix[var4][var5] << 32 - var2;
               }

               var3.matrix[var4][var3.length - 1] = this.matrix[var4][var5++] >>> var2;
               if (var5 < this.length) {
                  int[] var10000 = var3.matrix[var4];
                  int var10001 = var3.length - 1;
                  var10000[var10001] |= this.matrix[var4][var5] << 32 - var2;
               }
            }
         }

         return var3;
      }
   }

   public GF2Matrix extendRightCompactForm() {
      GF2Matrix var1 = new GF2Matrix(this.numRows, this.numRows + this.numColumns);
      int var2 = this.numRows >> 5;
      int var3 = this.numRows & 31;

      for(int var4 = this.numRows - 1; var4 >= 0; --var4) {
         int[] var10000 = var1.matrix[var4];
         var10000[var4 >> 5] |= 1 << (var4 & 31);
         if (var3 == 0) {
            System.arraycopy(this.matrix[var4], 0, var1.matrix[var4], var2, this.length);
         } else {
            int var5 = var2;

            int var10001;
            int var6;
            for(var6 = 0; var6 < this.length - 1; ++var6) {
               int var7 = this.matrix[var4][var6];
               var10000 = var1.matrix[var4];
               var10001 = var5++;
               var10000[var10001] |= var7 << var3;
               var10000 = var1.matrix[var4];
               var10000[var5] |= var7 >>> 32 - var3;
            }

            var6 = this.matrix[var4][this.length - 1];
            var10000 = var1.matrix[var4];
            var10001 = var5++;
            var10000[var10001] |= var6 << var3;
            if (var5 < var1.length) {
               var10000 = var1.matrix[var4];
               var10000[var5] |= var6 >>> 32 - var3;
            }
         }
      }

      return var1;
   }

   public Matrix computeTranspose() {
      int[][] var1 = new int[this.numColumns][this.numRows + 31 >>> 5];

      for(int var2 = 0; var2 < this.numRows; ++var2) {
         for(int var3 = 0; var3 < this.numColumns; ++var3) {
            int var4 = var3 >>> 5;
            int var5 = var3 & 31;
            int var6 = this.matrix[var2][var4] >>> var5 & 1;
            int var7 = var2 >>> 5;
            int var8 = var2 & 31;
            if (var6 == 1) {
               var1[var3][var7] |= 1 << var8;
            }
         }
      }

      return new GF2Matrix(this.numRows, var1);
   }

   public Matrix computeInverse() {
      if (this.numRows != this.numColumns) {
         throw new ArithmeticException("Matrix is not invertible.");
      } else {
         int[][] var1 = new int[this.numRows][this.length];

         for(int var2 = this.numRows - 1; var2 >= 0; --var2) {
            var1[var2] = IntUtils.clone(this.matrix[var2]);
         }

         int[][] var8 = new int[this.numRows][this.length];

         int var3;
         int var4;
         int var5;
         for(var3 = this.numRows - 1; var3 >= 0; --var3) {
            var4 = var3 >> 5;
            var5 = var3 & 31;
            var8[var3][var4] = 1 << var5;
         }

         for(var3 = 0; var3 < this.numRows; ++var3) {
            var4 = var3 >> 5;
            var5 = 1 << (var3 & 31);
            if ((var1[var3][var4] & var5) == 0) {
               boolean var6 = false;

               for(int var7 = var3 + 1; var7 < this.numRows; ++var7) {
                  if ((var1[var7][var4] & var5) != 0) {
                     var6 = true;
                     swapRows(var1, var3, var7);
                     swapRows(var8, var3, var7);
                     var7 = this.numRows;
                  }
               }

               if (!var6) {
                  throw new ArithmeticException("Matrix is not invertible.");
               }
            }

            for(int var9 = this.numRows - 1; var9 >= 0; --var9) {
               if (var9 != var3 && (var1[var9][var4] & var5) != 0) {
                  addToRow(var1[var3], var1[var9], var4);
                  addToRow(var8[var3], var8[var9], 0);
               }
            }
         }

         return new GF2Matrix(this.numColumns, var8);
      }
   }

   public Matrix leftMultiply(Permutation var1) {
      int[] var2 = var1.getVector();
      if (var2.length != this.numRows) {
         throw new ArithmeticException("length mismatch");
      } else {
         int[][] var3 = new int[this.numRows][];

         for(int var4 = this.numRows - 1; var4 >= 0; --var4) {
            var3[var4] = IntUtils.clone(this.matrix[var2[var4]]);
         }

         return new GF2Matrix(this.numRows, var3);
      }
   }

   public Vector leftMultiply(Vector var1) {
      if (!(var1 instanceof GF2Vector)) {
         throw new ArithmeticException("vector is not defined over GF(2)");
      } else if (var1.length != this.numRows) {
         throw new ArithmeticException("length mismatch");
      } else {
         int[] var2 = ((GF2Vector)var1).getVecArray();
         int[] var3 = new int[this.length];
         int var4 = this.numRows >> 5;
         int var5 = 1 << (this.numRows & 31);
         int var6 = 0;

         int var7;
         int var8;
         int var9;
         for(var7 = 0; var7 < var4; ++var7) {
            var8 = 1;

            do {
               var9 = var2[var7] & var8;
               if (var9 != 0) {
                  for(int var10 = 0; var10 < this.length; ++var10) {
                     var3[var10] ^= this.matrix[var6][var10];
                  }
               }

               ++var6;
               var8 <<= 1;
            } while(var8 != 0);
         }

         for(var7 = 1; var7 != var5; var7 <<= 1) {
            var8 = var2[var4] & var7;
            if (var8 != 0) {
               for(var9 = 0; var9 < this.length; ++var9) {
                  var3[var9] ^= this.matrix[var6][var9];
               }
            }

            ++var6;
         }

         return new GF2Vector(var3, this.numColumns);
      }
   }

   public Vector leftMultiplyLeftCompactForm(Vector var1) {
      if (!(var1 instanceof GF2Vector)) {
         throw new ArithmeticException("vector is not defined over GF(2)");
      } else if (var1.length != this.numRows) {
         throw new ArithmeticException("length mismatch");
      } else {
         int[] var2 = ((GF2Vector)var1).getVecArray();
         int[] var3 = new int[this.numRows + this.numColumns + 31 >>> 5];
         int var4 = this.numRows >>> 5;
         int var5 = 0;

         int var6;
         int var7;
         int var8;
         int var9;
         int var10;
         for(var6 = 0; var6 < var4; ++var6) {
            var7 = 1;

            do {
               var8 = var2[var6] & var7;
               if (var8 != 0) {
                  for(var9 = 0; var9 < this.length; ++var9) {
                     var3[var9] ^= this.matrix[var5][var9];
                  }

                  var9 = this.numColumns + var5 >>> 5;
                  var10 = this.numColumns + var5 & 31;
                  var3[var9] |= 1 << var10;
               }

               ++var5;
               var7 <<= 1;
            } while(var7 != 0);
         }

         var6 = 1 << (this.numRows & 31);

         for(var7 = 1; var7 != var6; var7 <<= 1) {
            var8 = var2[var4] & var7;
            if (var8 != 0) {
               for(var9 = 0; var9 < this.length; ++var9) {
                  var3[var9] ^= this.matrix[var5][var9];
               }

               var9 = this.numColumns + var5 >>> 5;
               var10 = this.numColumns + var5 & 31;
               var3[var9] |= 1 << var10;
            }

            ++var5;
         }

         return new GF2Vector(var3, this.numRows + this.numColumns);
      }
   }

   public Matrix rightMultiply(Matrix var1) {
      if (!(var1 instanceof GF2Matrix)) {
         throw new ArithmeticException("matrix is not defined over GF(2)");
      } else if (var1.numRows != this.numColumns) {
         throw new ArithmeticException("length mismatch");
      } else {
         GF2Matrix var2 = (GF2Matrix)var1;
         GF2Matrix var3 = new GF2Matrix(this.numRows, var1.numColumns);
         int var4 = this.numColumns & 31;
         int var5;
         if (var4 == 0) {
            var5 = this.length;
         } else {
            var5 = this.length - 1;
         }

         for(int var6 = 0; var6 < this.numRows; ++var6) {
            int var7 = 0;

            int var8;
            int var9;
            int var10;
            int var11;
            int[] var10000;
            for(var8 = 0; var8 < var5; ++var8) {
               var9 = this.matrix[var6][var8];

               for(var10 = 0; var10 < 32; ++var10) {
                  var11 = var9 & 1 << var10;
                  if (var11 != 0) {
                     for(int var12 = 0; var12 < var2.length; ++var12) {
                        var10000 = var3.matrix[var6];
                        var10000[var12] ^= var2.matrix[var7][var12];
                     }
                  }

                  ++var7;
               }
            }

            var8 = this.matrix[var6][this.length - 1];

            for(var9 = 0; var9 < var4; ++var9) {
               var10 = var8 & 1 << var9;
               if (var10 != 0) {
                  for(var11 = 0; var11 < var2.length; ++var11) {
                     var10000 = var3.matrix[var6];
                     var10000[var11] ^= var2.matrix[var7][var11];
                  }
               }

               ++var7;
            }
         }

         return var3;
      }
   }

   public Matrix rightMultiply(Permutation var1) {
      int[] var2 = var1.getVector();
      if (var2.length != this.numColumns) {
         throw new ArithmeticException("length mismatch");
      } else {
         GF2Matrix var3 = new GF2Matrix(this.numRows, this.numColumns);

         for(int var4 = this.numColumns - 1; var4 >= 0; --var4) {
            int var5 = var4 >>> 5;
            int var6 = var4 & 31;
            int var7 = var2[var4] >>> 5;
            int var8 = var2[var4] & 31;

            for(int var9 = this.numRows - 1; var9 >= 0; --var9) {
               int[] var10000 = var3.matrix[var9];
               var10000[var5] |= (this.matrix[var9][var7] >>> var8 & 1) << var6;
            }
         }

         return var3;
      }
   }

   public Vector rightMultiply(Vector var1) {
      if (!(var1 instanceof GF2Vector)) {
         throw new ArithmeticException("vector is not defined over GF(2)");
      } else if (var1.length != this.numColumns) {
         throw new ArithmeticException("length mismatch");
      } else {
         int[] var2 = ((GF2Vector)var1).getVecArray();
         int[] var3 = new int[this.numRows + 31 >>> 5];

         for(int var4 = 0; var4 < this.numRows; ++var4) {
            int var5 = 0;

            int var6;
            for(var6 = 0; var6 < this.length; ++var6) {
               var5 ^= this.matrix[var4][var6] & var2[var6];
            }

            var6 = 0;

            for(int var7 = 0; var7 < 32; ++var7) {
               var6 ^= var5 >>> var7 & 1;
            }

            if (var6 == 1) {
               var3[var4 >>> 5] |= 1 << (var4 & 31);
            }
         }

         return new GF2Vector(var3, this.numRows);
      }
   }

   public Vector rightMultiplyRightCompactForm(Vector var1) {
      if (!(var1 instanceof GF2Vector)) {
         throw new ArithmeticException("vector is not defined over GF(2)");
      } else if (var1.length != this.numColumns + this.numRows) {
         throw new ArithmeticException("length mismatch");
      } else {
         int[] var2 = ((GF2Vector)var1).getVecArray();
         int[] var3 = new int[this.numRows + 31 >>> 5];
         int var4 = this.numRows >> 5;
         int var5 = this.numRows & 31;

         for(int var6 = 0; var6 < this.numRows; ++var6) {
            int var7 = var2[var6 >> 5] >>> (var6 & 31) & 1;
            int var8 = var4;
            int var10;
            int var11;
            if (var5 == 0) {
               for(var11 = 0; var11 < this.length; ++var11) {
                  var7 ^= this.matrix[var6][var11] & var2[var8++];
               }
            } else {
               boolean var9 = false;

               for(var10 = 0; var10 < this.length - 1; ++var10) {
                  var11 = var2[var8++] >>> var5 | var2[var8] << 32 - var5;
                  var7 ^= this.matrix[var6][var10] & var11;
               }

               var11 = var2[var8++] >>> var5;
               if (var8 < var2.length) {
                  var11 |= var2[var8] << 32 - var5;
               }

               var7 ^= this.matrix[var6][this.length - 1] & var11;
            }

            var11 = 0;

            for(var10 = 0; var10 < 32; ++var10) {
               var11 ^= var7 & 1;
               var7 >>>= 1;
            }

            if (var11 == 1) {
               var3[var6 >> 5] |= 1 << (var6 & 31);
            }
         }

         return new GF2Vector(var3, this.numRows);
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof GF2Matrix)) {
         return false;
      } else {
         GF2Matrix var2 = (GF2Matrix)var1;
         if (this.numRows == var2.numRows && this.numColumns == var2.numColumns && this.length == var2.length) {
            for(int var3 = 0; var3 < this.numRows; ++var3) {
               if (!IntUtils.equals(this.matrix[var3], var2.matrix[var3])) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      int var1 = (this.numRows * 31 + this.numColumns) * 31 + this.length;

      for(int var2 = 0; var2 < this.numRows; ++var2) {
         var1 = var1 * 31 + this.matrix[var2].hashCode();
      }

      return var1;
   }

   public String toString() {
      int var1 = this.numColumns & 31;
      int var2;
      if (var1 == 0) {
         var2 = this.length;
      } else {
         var2 = this.length - 1;
      }

      StringBuffer var3 = new StringBuffer();

      for(int var4 = 0; var4 < this.numRows; ++var4) {
         var3.append(var4 + ": ");

         int var5;
         int var6;
         int var7;
         for(var5 = 0; var5 < var2; ++var5) {
            var6 = this.matrix[var4][var5];

            for(var7 = 0; var7 < 32; ++var7) {
               int var8 = var6 >>> var7 & 1;
               if (var8 == 0) {
                  var3.append('0');
               } else {
                  var3.append('1');
               }
            }

            var3.append(' ');
         }

         var5 = this.matrix[var4][this.length - 1];

         for(var6 = 0; var6 < var1; ++var6) {
            var7 = var5 >>> var6 & 1;
            if (var7 == 0) {
               var3.append('0');
            } else {
               var3.append('1');
            }
         }

         var3.append('\n');
      }

      return var3.toString();
   }

   private static void swapRows(int[][] var0, int var1, int var2) {
      int[] var3 = var0[var1];
      var0[var1] = var0[var2];
      var0[var2] = var3;
   }

   private static void addToRow(int[] var0, int[] var1, int var2) {
      for(int var3 = var1.length - 1; var3 >= var2; --var3) {
         var1[var3] ^= var0[var3];
      }

   }
}
