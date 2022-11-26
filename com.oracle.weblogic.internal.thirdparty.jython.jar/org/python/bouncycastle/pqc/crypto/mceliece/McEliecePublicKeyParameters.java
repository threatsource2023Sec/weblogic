package org.python.bouncycastle.pqc.crypto.mceliece;

import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

public class McEliecePublicKeyParameters extends McElieceKeyParameters {
   private int n;
   private int t;
   private GF2Matrix g;

   public McEliecePublicKeyParameters(int var1, int var2, GF2Matrix var3) {
      super(false, (McElieceParameters)null);
      this.n = var1;
      this.t = var2;
      this.g = new GF2Matrix(var3);
   }

   public int getN() {
      return this.n;
   }

   public int getT() {
      return this.t;
   }

   public GF2Matrix getG() {
      return this.g;
   }

   public int getK() {
      return this.g.getNumRows();
   }
}
