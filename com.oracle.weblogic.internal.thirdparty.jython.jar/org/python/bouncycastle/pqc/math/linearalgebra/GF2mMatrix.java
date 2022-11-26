package org.python.bouncycastle.pqc.math.linearalgebra;

public class GF2mMatrix extends Matrix {
   protected GF2mField field;
   protected int[][] matrix;

   public GF2mMatrix(GF2mField var1, byte[] var2) {
      this.field = var1;
      int var3 = 8;

      int var4;
      for(var4 = 1; var1.getDegree() > var3; var3 += 8) {
         ++var4;
      }

      if (var2.length < 5) {
         throw new IllegalArgumentException(" Error: given array is not encoded matrix over GF(2^m)");
      } else {
         this.numRows = (var2[3] & 255) << 24 ^ (var2[2] & 255) << 16 ^ (var2[1] & 255) << 8 ^ var2[0] & 255;
         int var5 = var4 * this.numRows;
         if (this.numRows > 0 && (var2.length - 4) % var5 == 0) {
            this.numColumns = (var2.length - 4) / var5;
            this.matrix = new int[this.numRows][this.numColumns];
            var4 = 4;

            for(int var6 = 0; var6 < this.numRows; ++var6) {
               for(int var7 = 0; var7 < this.numColumns; ++var7) {
                  for(int var8 = 0; var8 < var3; var8 += 8) {
                     int[] var10000 = this.matrix[var6];
                     var10000[var7] ^= (var2[var4++] & 255) << var8;
                  }

                  if (!this.field.isElementOfThisField(this.matrix[var6][var7])) {
                     throw new IllegalArgumentException(" Error: given array is not encoded matrix over GF(2^m)");
                  }
               }
            }

         } else {
            throw new IllegalArgumentException(" Error: given array is not encoded matrix over GF(2^m)");
         }
      }
   }

   public GF2mMatrix(GF2mMatrix var1) {
      this.numRows = var1.numRows;
      this.numColumns = var1.numColumns;
      this.field = var1.field;
      this.matrix = new int[this.numRows][];

      for(int var2 = 0; var2 < this.numRows; ++var2) {
         this.matrix[var2] = IntUtils.clone(var1.matrix[var2]);
      }

   }

   protected GF2mMatrix(GF2mField var1, int[][] var2) {
      this.field = var1;
      this.matrix = var2;
      this.numRows = var2.length;
      this.numColumns = var2[0].length;
   }

   public byte[] getEncoded() {
      int var1 = 8;

      int var2;
      for(var2 = 1; this.field.getDegree() > var1; var1 += 8) {
         ++var2;
      }

      byte[] var3 = new byte[this.numRows * this.numColumns * var2 + 4];
      var3[0] = (byte)(this.numRows & 255);
      var3[1] = (byte)(this.numRows >>> 8 & 255);
      var3[2] = (byte)(this.numRows >>> 16 & 255);
      var3[3] = (byte)(this.numRows >>> 24 & 255);
      var2 = 4;

      for(int var4 = 0; var4 < this.numRows; ++var4) {
         for(int var5 = 0; var5 < this.numColumns; ++var5) {
            for(int var6 = 0; var6 < var1; var6 += 8) {
               var3[var2++] = (byte)(this.matrix[var4][var5] >>> var6);
            }
         }
      }

      return var3;
   }

   public boolean isZero() {
      for(int var1 = 0; var1 < this.numRows; ++var1) {
         for(int var2 = 0; var2 < this.numColumns; ++var2) {
            if (this.matrix[var1][var2] != 0) {
               return false;
            }
         }
      }

      return true;
   }

   public Matrix computeInverse() {
      if (this.numRows != this.numColumns) {
         throw new ArithmeticException("Matrix is not invertible.");
      } else {
         int[][] var1 = new int[this.numRows][this.numRows];

         for(int var2 = this.numRows - 1; var2 >= 0; --var2) {
            var1[var2] = IntUtils.clone(this.matrix[var2]);
         }

         int[][] var9 = new int[this.numRows][this.numRows];

         int var3;
         for(var3 = this.numRows - 1; var3 >= 0; --var3) {
            var9[var3][var3] = 1;
         }

         for(var3 = 0; var3 < this.numRows; ++var3) {
            int var5;
            if (var1[var3][var3] == 0) {
               boolean var4 = false;

               for(var5 = var3 + 1; var5 < this.numRows; ++var5) {
                  if (var1[var5][var3] != 0) {
                     var4 = true;
                     swapColumns(var1, var3, var5);
                     swapColumns(var9, var3, var5);
                     var5 = this.numRows;
                  }
               }

               if (!var4) {
                  throw new ArithmeticException("Matrix is not invertible.");
               }
            }

            int var10 = var1[var3][var3];
            var5 = this.field.inverse(var10);
            this.multRowWithElementThis(var1[var3], var5);
            this.multRowWithElementThis(var9[var3], var5);

            for(int var6 = 0; var6 < this.numRows; ++var6) {
               if (var6 != var3) {
                  var10 = var1[var6][var3];
                  if (var10 != 0) {
                     int[] var7 = this.multRowWithElement(var1[var3], var10);
                     int[] var8 = this.multRowWithElement(var9[var3], var10);
                     this.addToRow(var7, var1[var6]);
                     this.addToRow(var8, var9[var6]);
                  }
               }
            }
         }

         return new GF2mMatrix(this.field, var9);
      }
   }

   private static void swapColumns(int[][] var0, int var1, int var2) {
      int[] var3 = var0[var1];
      var0[var1] = var0[var2];
      var0[var2] = var3;
   }

   private void multRowWithElementThis(int[] var1, int var2) {
      for(int var3 = var1.length - 1; var3 >= 0; --var3) {
         var1[var3] = this.field.mult(var1[var3], var2);
      }

   }

   private int[] multRowWithElement(int[] var1, int var2) {
      int[] var3 = new int[var1.length];

      for(int var4 = var1.length - 1; var4 >= 0; --var4) {
         var3[var4] = this.field.mult(var1[var4], var2);
      }

      return var3;
   }

   private void addToRow(int[] var1, int[] var2) {
      for(int var3 = var2.length - 1; var3 >= 0; --var3) {
         var2[var3] = this.field.add(var1[var3], var2[var3]);
      }

   }

   public Matrix rightMultiply(Matrix var1) {
      throw new RuntimeException("Not implemented.");
   }

   public Matrix rightMultiply(Permutation var1) {
      throw new RuntimeException("Not implemented.");
   }

   public Vector leftMultiply(Vector var1) {
      throw new RuntimeException("Not implemented.");
   }

   public Vector rightMultiply(Vector var1) {
      throw new RuntimeException("Not implemented.");
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof GF2mMatrix) {
         GF2mMatrix var2 = (GF2mMatrix)var1;
         if (this.field.equals(var2.field) && var2.numRows == this.numColumns && var2.numColumns == this.numColumns) {
            for(int var3 = 0; var3 < this.numRows; ++var3) {
               for(int var4 = 0; var4 < this.numColumns; ++var4) {
                  if (this.matrix[var3][var4] != var2.matrix[var3][var4]) {
                     return false;
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = (this.field.hashCode() * 31 + this.numRows) * 31 + this.numColumns;

      for(int var2 = 0; var2 < this.numRows; ++var2) {
         for(int var3 = 0; var3 < this.numColumns; ++var3) {
            var1 = var1 * 31 + this.matrix[var2][var3];
         }
      }

      return var1;
   }

   public String toString() {
      String var1 = this.numRows + " x " + this.numColumns + " Matrix over " + this.field.toString() + ": \n";

      for(int var2 = 0; var2 < this.numRows; ++var2) {
         for(int var3 = 0; var3 < this.numColumns; ++var3) {
            var1 = var1 + this.field.elementToStr(this.matrix[var2][var3]) + " : ";
         }

         var1 = var1 + "\n";
      }

      return var1;
   }
}
