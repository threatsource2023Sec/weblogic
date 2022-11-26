package org.python.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DSA;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECKeyParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.math.ec.ECAlgorithms;
import org.python.bouncycastle.math.ec.ECConstants;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.FixedPointCombMultiplier;

public class ECGOST3410Signer implements DSA {
   ECKeyParameters key;
   SecureRandom random;

   public void init(boolean var1, CipherParameters var2) {
      if (var1) {
         if (var2 instanceof ParametersWithRandom) {
            ParametersWithRandom var3 = (ParametersWithRandom)var2;
            this.random = var3.getRandom();
            this.key = (ECPrivateKeyParameters)var3.getParameters();
         } else {
            this.random = new SecureRandom();
            this.key = (ECPrivateKeyParameters)var2;
         }
      } else {
         this.key = (ECPublicKeyParameters)var2;
      }

   }

   public BigInteger[] generateSignature(byte[] var1) {
      byte[] var2 = new byte[var1.length];

      for(int var3 = 0; var3 != var2.length; ++var3) {
         var2[var3] = var1[var2.length - 1 - var3];
      }

      BigInteger var12 = new BigInteger(1, var2);
      ECDomainParameters var4 = this.key.getParameters();
      BigInteger var5 = var4.getN();
      BigInteger var6 = ((ECPrivateKeyParameters)this.key).getD();
      ECMultiplier var7 = this.createBasePointMultiplier();

      while(true) {
         BigInteger var8;
         do {
            var8 = new BigInteger(var5.bitLength(), this.random);
         } while(var8.equals(ECConstants.ZERO));

         ECPoint var9 = var7.multiply(var4.getG(), var8).normalize();
         BigInteger var10 = var9.getAffineXCoord().toBigInteger().mod(var5);
         if (!var10.equals(ECConstants.ZERO)) {
            BigInteger var11 = var8.multiply(var12).add(var6.multiply(var10)).mod(var5);
            if (!var11.equals(ECConstants.ZERO)) {
               return new BigInteger[]{var10, var11};
            }
         }
      }
   }

   public boolean verifySignature(byte[] var1, BigInteger var2, BigInteger var3) {
      byte[] var4 = new byte[var1.length];

      for(int var5 = 0; var5 != var4.length; ++var5) {
         var4[var5] = var1[var4.length - 1 - var5];
      }

      BigInteger var14 = new BigInteger(1, var4);
      BigInteger var6 = this.key.getParameters().getN();
      if (var2.compareTo(ECConstants.ONE) >= 0 && var2.compareTo(var6) < 0) {
         if (var3.compareTo(ECConstants.ONE) >= 0 && var3.compareTo(var6) < 0) {
            BigInteger var7 = var14.modInverse(var6);
            BigInteger var8 = var3.multiply(var7).mod(var6);
            BigInteger var9 = var6.subtract(var2).multiply(var7).mod(var6);
            ECPoint var10 = this.key.getParameters().getG();
            ECPoint var11 = ((ECPublicKeyParameters)this.key).getQ();
            ECPoint var12 = ECAlgorithms.sumOfTwoMultiplies(var10, var8, var11, var9).normalize();
            if (var12.isInfinity()) {
               return false;
            } else {
               BigInteger var13 = var12.getAffineXCoord().toBigInteger().mod(var6);
               return var13.equals(var2);
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected ECMultiplier createBasePointMultiplier() {
      return new FixedPointCombMultiplier();
   }
}
