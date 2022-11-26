package org.python.bouncycastle.pqc.crypto.mceliece;

import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

public class McElieceCCA2PublicKeyParameters extends McElieceCCA2KeyParameters {
   private int n;
   private int t;
   private GF2Matrix matrixG;

   public McElieceCCA2PublicKeyParameters(int var1, int var2, GF2Matrix var3, String var4) {
      super(false, var4);
      this.n = var1;
      this.t = var2;
      this.matrixG = new GF2Matrix(var3);
   }

   public int getN() {
      return this.n;
   }

   public int getT() {
      return this.t;
   }

   public GF2Matrix getG() {
      return this.matrixG;
   }

   public int getK() {
      return this.matrixG.getNumRows();
   }
}
