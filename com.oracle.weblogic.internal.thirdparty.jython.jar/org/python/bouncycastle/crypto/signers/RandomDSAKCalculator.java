package org.python.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomDSAKCalculator implements DSAKCalculator {
   private static final BigInteger ZERO = BigInteger.valueOf(0L);
   private BigInteger q;
   private SecureRandom random;

   public boolean isDeterministic() {
      return false;
   }

   public void init(BigInteger var1, SecureRandom var2) {
      this.q = var1;
      this.random = var2;
   }

   public void init(BigInteger var1, BigInteger var2, byte[] var3) {
      throw new IllegalStateException("Operation not supported");
   }

   public BigInteger nextK() {
      int var1 = this.q.bitLength();

      BigInteger var2;
      do {
         do {
            var2 = new BigInteger(var1, this.random);
         } while(var2.equals(ZERO));
      } while(var2.compareTo(this.q) >= 0);

      return var2;
   }
}
