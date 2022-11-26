package org.python.bouncycastle.pqc.crypto.rainbow;

public class RainbowPublicKeyParameters extends RainbowKeyParameters {
   private short[][] coeffquadratic;
   private short[][] coeffsingular;
   private short[] coeffscalar;

   public RainbowPublicKeyParameters(int var1, short[][] var2, short[][] var3, short[] var4) {
      super(false, var1);
      this.coeffquadratic = var2;
      this.coeffsingular = var3;
      this.coeffscalar = var4;
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
