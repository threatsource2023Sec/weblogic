package org.python.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DSA;
import org.python.bouncycastle.crypto.params.GOST3410KeyParameters;
import org.python.bouncycastle.crypto.params.GOST3410Parameters;
import org.python.bouncycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.python.bouncycastle.crypto.params.GOST3410PublicKeyParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;

public class GOST3410Signer implements DSA {
   GOST3410KeyParameters key;
   SecureRandom random;

   public void init(boolean var1, CipherParameters var2) {
      if (var1) {
         if (var2 instanceof ParametersWithRandom) {
            ParametersWithRandom var3 = (ParametersWithRandom)var2;
            this.random = var3.getRandom();
            this.key = (GOST3410PrivateKeyParameters)var3.getParameters();
         } else {
            this.random = new SecureRandom();
            this.key = (GOST3410PrivateKeyParameters)var2;
         }
      } else {
         this.key = (GOST3410PublicKeyParameters)var2;
      }

   }

   public BigInteger[] generateSignature(byte[] var1) {
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 != var2.length; ++var3) {
         var2[var3] = var1[var2.length - 1 - var3];
      }

      BigInteger var9 = new BigInteger(1, var2);
      GOST3410Parameters var4 = this.key.getParameters();

      BigInteger var5;
      do {
         var5 = new BigInteger(var4.getQ().bitLength(), this.random);
      } while(var5.compareTo(var4.getQ()) >= 0);

      BigInteger var6 = var4.getA().modPow(var5, var4.getP()).mod(var4.getQ());
      BigInteger var7 = var5.multiply(var9).add(((GOST3410PrivateKeyParameters)this.key).getX().multiply(var6)).mod(var4.getQ());
      BigInteger[] var8 = new BigInteger[]{var6, var7};
      return var8;
   }

   public boolean verifySignature(byte[] var1, BigInteger var2, BigInteger var3) {
      byte[] var4 = new byte[var1.length];

      for(int var5 = 0; var5 != var4.length; ++var5) {
         var4[var5] = var1[var4.length - 1 - var5];
      }

      BigInteger var12 = new BigInteger(1, var4);
      GOST3410Parameters var6 = this.key.getParameters();
      BigInteger var7 = BigInteger.valueOf(0L);
      if (var7.compareTo(var2) < 0 && var6.getQ().compareTo(var2) > 0) {
         if (var7.compareTo(var3) < 0 && var6.getQ().compareTo(var3) > 0) {
            BigInteger var8 = var12.modPow(var6.getQ().subtract(new BigInteger("2")), var6.getQ());
            BigInteger var9 = var3.multiply(var8).mod(var6.getQ());
            BigInteger var10 = var6.getQ().subtract(var2).multiply(var8).mod(var6.getQ());
            var9 = var6.getA().modPow(var9, var6.getP());
            var10 = ((GOST3410PublicKeyParameters)this.key).getY().modPow(var10, var6.getP());
            BigInteger var11 = var9.multiply(var10).mod(var6.getP()).mod(var6.getQ());
            return var11.equals(var2);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
