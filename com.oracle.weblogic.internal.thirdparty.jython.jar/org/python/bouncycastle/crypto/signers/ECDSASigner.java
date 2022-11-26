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
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.FixedPointCombMultiplier;

public class ECDSASigner implements ECConstants, DSA {
   private final DSAKCalculator kCalculator;
   private ECKeyParameters key;
   private SecureRandom random;

   public ECDSASigner() {
      this.kCalculator = new RandomDSAKCalculator();
   }

   public ECDSASigner(DSAKCalculator var1) {
      this.kCalculator = var1;
   }

   public void init(boolean var1, CipherParameters var2) {
      SecureRandom var3 = null;
      if (var1) {
         if (var2 instanceof ParametersWithRandom) {
            ParametersWithRandom var4 = (ParametersWithRandom)var2;
            this.key = (ECPrivateKeyParameters)var4.getParameters();
            var3 = var4.getRandom();
         } else {
            this.key = (ECPrivateKeyParameters)var2;
         }
      } else {
         this.key = (ECPublicKeyParameters)var2;
      }

      this.random = this.initSecureRandom(var1 && !this.kCalculator.isDeterministic(), var3);
   }

   public BigInteger[] generateSignature(byte[] var1) {
      ECDomainParameters var2 = this.key.getParameters();
      BigInteger var3 = var2.getN();
      BigInteger var4 = this.calculateE(var3, var1);
      BigInteger var5 = ((ECPrivateKeyParameters)this.key).getD();
      if (this.kCalculator.isDeterministic()) {
         this.kCalculator.init(var3, var5, var1);
      } else {
         this.kCalculator.init(var3, this.random);
      }

      ECMultiplier var6 = this.createBasePointMultiplier();

      BigInteger var9;
      BigInteger var10;
      do {
         BigInteger var7;
         do {
            var7 = this.kCalculator.nextK();
            ECPoint var8 = var6.multiply(var2.getG(), var7).normalize();
            var9 = var8.getAffineXCoord().toBigInteger().mod(var3);
         } while(var9.equals(ZERO));

         var10 = var7.modInverse(var3).multiply(var4.add(var5.multiply(var9))).mod(var3);
      } while(var10.equals(ZERO));

      return new BigInteger[]{var9, var10};
   }

   public boolean verifySignature(byte[] var1, BigInteger var2, BigInteger var3) {
      ECDomainParameters var4 = this.key.getParameters();
      BigInteger var5 = var4.getN();
      BigInteger var6 = this.calculateE(var5, var1);
      if (var2.compareTo(ONE) >= 0 && var2.compareTo(var5) < 0) {
         if (var3.compareTo(ONE) >= 0 && var3.compareTo(var5) < 0) {
            BigInteger var7 = var3.modInverse(var5);
            BigInteger var8 = var6.multiply(var7).mod(var5);
            BigInteger var9 = var2.multiply(var7).mod(var5);
            ECPoint var10 = var4.getG();
            ECPoint var11 = ((ECPublicKeyParameters)this.key).getQ();
            ECPoint var12 = ECAlgorithms.sumOfTwoMultiplies(var10, var8, var11, var9);
            if (var12.isInfinity()) {
               return false;
            } else {
               ECCurve var13 = var12.getCurve();
               BigInteger var14;
               if (var13 != null) {
                  var14 = var13.getCofactor();
                  if (var14 != null && var14.compareTo(EIGHT) <= 0) {
                     ECFieldElement var15 = this.getDenominator(var13.getCoordinateSystem(), var12);
                     if (var15 != null && !var15.isZero()) {
                        for(ECFieldElement var16 = var12.getXCoord(); var13.isValidFieldElement(var2); var2 = var2.add(var5)) {
                           ECFieldElement var17 = var13.fromBigInteger(var2).multiply(var15);
                           if (var17.equals(var16)) {
                              return true;
                           }
                        }

                        return false;
                     }
                  }
               }

               var14 = var12.normalize().getAffineXCoord().toBigInteger().mod(var5);
               return var14.equals(var2);
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected BigInteger calculateE(BigInteger var1, byte[] var2) {
      int var3 = var1.bitLength();
      int var4 = var2.length * 8;
      BigInteger var5 = new BigInteger(1, var2);
      if (var3 < var4) {
         var5 = var5.shiftRight(var4 - var3);
      }

      return var5;
   }

   protected ECMultiplier createBasePointMultiplier() {
      return new FixedPointCombMultiplier();
   }

   protected ECFieldElement getDenominator(int var1, ECPoint var2) {
      switch (var1) {
         case 1:
         case 6:
         case 7:
            return var2.getZCoord(0);
         case 2:
         case 3:
         case 4:
            return var2.getZCoord(0).square();
         case 5:
         default:
            return null;
      }
   }

   protected SecureRandom initSecureRandom(boolean var1, SecureRandom var2) {
      return !var1 ? null : (var2 != null ? var2 : new SecureRandom());
   }
}
