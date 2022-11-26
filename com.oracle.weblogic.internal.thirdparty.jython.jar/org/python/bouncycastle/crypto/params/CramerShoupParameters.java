package org.python.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;

public class CramerShoupParameters implements CipherParameters {
   private BigInteger p;
   private BigInteger g1;
   private BigInteger g2;
   private Digest H;

   public CramerShoupParameters(BigInteger var1, BigInteger var2, BigInteger var3, Digest var4) {
      this.p = var1;
      this.g1 = var2;
      this.g2 = var3;
      this.H = var4;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof DSAParameters)) {
         return false;
      } else {
         CramerShoupParameters var2 = (CramerShoupParameters)var1;
         return var2.getP().equals(this.p) && var2.getG1().equals(this.g1) && var2.getG2().equals(this.g2);
      }
   }

   public int hashCode() {
      return this.getP().hashCode() ^ this.getG1().hashCode() ^ this.getG2().hashCode();
   }

   public BigInteger getG1() {
      return this.g1;
   }

   public BigInteger getG2() {
      return this.g2;
   }

   public BigInteger getP() {
      return this.p;
   }

   public Digest getH() {
      this.H.reset();
      return this.H;
   }
}
