package org.python.bouncycastle.pqc.jcajce.spec;

import java.security.spec.KeySpec;

public class RainbowPublicKeySpec implements KeySpec {
   private short[][] coeffquadratic;
   private short[][] coeffsingular;
   private short[] coeffscalar;
   private int docLength;

   public RainbowPublicKeySpec(int var1, short[][] var2, short[][] var3, short[] var4) {
      this.docLength = var1;
      this.coeffquadratic = var2;
      this.coeffsingular = var3;
      this.coeffscalar = var4;
   }

   public int getDocLength() {
      return this.docLength;
   }

   public short[][] getCoeffQuadratic() {
      return this.coeffquadratic;
   }

   public short[][] getCoeffSingular() {
      return this.coeffsingular;
   }

   public short[] getCoeffScalar() {
      return this.coeffscalar;
   }
}
