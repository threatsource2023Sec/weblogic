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
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.python.bouncycastle.util.Arrays;

public class DSTU4145Signer implements DSA {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private ECKeyParameters key;
   private SecureRandom random;

   public void init(boolean var1, CipherParameters var2) {
      if (var1) {
         if (var2 instanceof ParametersWithRandom) {
            ParametersWithRandom var3 = (ParametersWithRandom)var2;
            this.random = var3.getRandom();
            var2 = var3.getParameters();
         } else {
            this.random = new SecureRandom();
         }

         this.key = (ECPrivateKeyParameters)var2;
      } else {
         this.key = (ECPublicKeyParameters)var2;
      }

   }

   public BigInteger[] generateSignature(byte[] var1) {
      ECDomainParameters var2 = this.key.getParameters();
      ECCurve var3 = var2.getCurve();
      ECFieldElement var4 = hash2FieldElement(var3, var1);
      if (var4.isZero()) {
         var4 = var3.fromBigInteger(ONE);
      }

      BigInteger var5 = var2.getN();
      BigInteger var6 = ((ECPrivateKeyParameters)this.key).getD();
      ECMultiplier var7 = this.createBasePointMultiplier();

      while(true) {
         BigInteger var8;
         ECFieldElement var9;
         do {
            var8 = generateRandomInteger(var5, this.random);
            var9 = var7.multiply(var2.getG(), var8).normalize().getAffineXCoord();
         } while(var9.isZero());

         ECFieldElement var10 = var4.multiply(var9);
         BigInteger var11 = fieldElement2Integer(var5, var10);
         if (var11.signum() != 0) {
            BigInteger var12 = var11.multiply(var6).add(var8).mod(var5);
            if (var12.signum() != 0) {
               return new BigInteger[]{var11, var12};
            }
         }
      }
   }

   public boolean verifySignature(byte[] var1, BigInteger var2, BigInteger var3) {
      if (var2.signum() > 0 && var3.signum() > 0) {
         ECDomainParameters var4 = this.key.getParameters();
         BigInteger var5 = var4.getN();
         if (var2.compareTo(var5) < 0 && var3.compareTo(var5) < 0) {
            ECCurve var6 = var4.getCurve();
            ECFieldElement var7 = hash2FieldElement(var6, var1);
            if (var7.isZero()) {
               var7 = var6.fromBigInteger(ONE);
            }

            ECPoint var8 = ECAlgorithms.sumOfTwoMultiplies(var4.getG(), var3, ((ECPublicKeyParameters)this.key).getQ(), var2).normalize();
            if (var8.isInfinity()) {
               return false;
            } else {
               ECFieldElement var9 = var7.multiply(var8.getAffineXCoord());
               return fieldElement2Integer(var5, var9).compareTo(var2) == 0;
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

   private static BigInteger generateRandomInteger(BigInteger var0, SecureRandom var1) {
      return new BigInteger(var0.bitLength() - 1, var1);
   }

   private static ECFieldElement hash2FieldElement(ECCurve var0, byte[] var1) {
      byte[] var2 = Arrays.reverse(var1);
      return var0.fromBigInteger(truncate(new BigInteger(1, var2), var0.getFieldSize()));
   }

   private static BigInteger fieldElement2Integer(BigInteger var0, ECFieldElement var1) {
      return truncate(var1.toBigInteger(), var0.bitLength() - 1);
   }

   private static BigInteger truncate(BigInteger var0, int var1) {
      if (var0.bitLength() > var1) {
         var0 = var0.mod(ONE.shiftLeft(var1));
      }

      return var0;
   }
}
