package org.python.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.python.bouncycastle.math.ec.ECConstants;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.Arrays;

public class ECDomainParameters implements ECConstants {
   private ECCurve curve;
   private byte[] seed;
   private ECPoint G;
   private BigInteger n;
   private BigInteger h;

   public ECDomainParameters(ECCurve var1, ECPoint var2, BigInteger var3) {
      this(var1, var2, var3, ONE, (byte[])null);
   }

   public ECDomainParameters(ECCurve var1, ECPoint var2, BigInteger var3, BigInteger var4) {
      this(var1, var2, var3, var4, (byte[])null);
   }

   public ECDomainParameters(ECCurve var1, ECPoint var2, BigInteger var3, BigInteger var4, byte[] var5) {
      this.curve = var1;
      this.G = var2.normalize();
      this.n = var3;
      this.h = var4;
      this.seed = var5;
   }

   public ECCurve getCurve() {
      return this.curve;
   }

   public ECPoint getG() {
      return this.G;
   }

   public BigInteger getN() {
      return this.n;
   }

   public BigInteger getH() {
      return this.h;
   }

   public byte[] getSeed() {
      return Arrays.clone(this.seed);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ECDomainParameters)) {
         return false;
      } else {
         ECDomainParameters var2 = (ECDomainParameters)var1;
         return this.curve.equals(var2.curve) && this.G.equals(var2.G) && this.n.equals(var2.n) && this.h.equals(var2.h);
      }
   }

   public int hashCode() {
      int var1 = this.curve.hashCode();
      var1 *= 37;
      var1 ^= this.G.hashCode();
      var1 *= 37;
      var1 ^= this.n.hashCode();
      var1 *= 37;
      var1 ^= this.h.hashCode();
      return var1;
   }
}
