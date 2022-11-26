package org.python.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DSA;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.digests.SM3Digest;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECKeyParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.crypto.params.ParametersWithID;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.math.ec.ECConstants;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.python.bouncycastle.util.BigIntegers;

public class SM2Signer implements DSA, ECConstants {
   private final DSAKCalculator kCalculator = new RandomDSAKCalculator();
   private byte[] userID;
   private int curveLength;
   private ECDomainParameters ecParams;
   private ECPoint pubPoint;
   private ECKeyParameters ecKey;
   private SecureRandom random;

   public void init(boolean var1, CipherParameters var2) {
      CipherParameters var3;
      if (var2 instanceof ParametersWithID) {
         var3 = ((ParametersWithID)var2).getParameters();
         this.userID = ((ParametersWithID)var2).getID();
      } else {
         var3 = var2;
         this.userID = new byte[0];
      }

      if (var1) {
         if (var3 instanceof ParametersWithRandom) {
            ParametersWithRandom var4 = (ParametersWithRandom)var3;
            this.ecKey = (ECKeyParameters)var4.getParameters();
            this.ecParams = this.ecKey.getParameters();
            this.kCalculator.init(this.ecParams.getN(), var4.getRandom());
         } else {
            this.ecKey = (ECKeyParameters)var3;
            this.ecParams = this.ecKey.getParameters();
            this.kCalculator.init(this.ecParams.getN(), new SecureRandom());
         }

         this.pubPoint = this.ecParams.getG().multiply(((ECPrivateKeyParameters)this.ecKey).getD()).normalize();
      } else {
         this.ecKey = (ECKeyParameters)var3;
         this.ecParams = this.ecKey.getParameters();
         this.pubPoint = ((ECPublicKeyParameters)this.ecKey).getQ();
      }

      this.curveLength = (this.ecParams.getCurve().getFieldSize() + 7) / 8;
   }

   public BigInteger[] generateSignature(byte[] var1) {
      SM3Digest var2 = new SM3Digest();
      byte[] var3 = this.getZ(var2);
      var2.update(var3, 0, var3.length);
      var2.update(var1, 0, var1.length);
      byte[] var4 = new byte[var2.getDigestSize()];
      var2.doFinal(var4, 0);
      BigInteger var5 = this.ecParams.getN();
      BigInteger var6 = this.calculateE(var4);
      BigInteger var7 = ((ECPrivateKeyParameters)this.ecKey).getD();
      ECMultiplier var8 = this.createBasePointMultiplier();

      while(true) {
         BigInteger var9;
         BigInteger var11;
         do {
            var9 = this.kCalculator.nextK();
            ECPoint var10 = var8.multiply(this.ecParams.getG(), var9).normalize();
            var11 = var6.add(var10.getAffineXCoord().toBigInteger()).mod(var5);
         } while(var11.equals(ZERO));

         if (!var11.add(var9).equals(var5)) {
            BigInteger var13 = var7.add(ONE).modInverse(var5);
            BigInteger var12 = var9.subtract(var11.multiply(var7)).mod(var5);
            var12 = var13.multiply(var12).mod(var5);
            if (!var12.equals(ZERO)) {
               return new BigInteger[]{var11, var12};
            }
         }
      }
   }

   public boolean verifySignature(byte[] var1, BigInteger var2, BigInteger var3) {
      BigInteger var4 = this.ecParams.getN();
      if (var2.compareTo(ONE) >= 0 && var2.compareTo(var4) <= 0) {
         if (var3.compareTo(ONE) >= 0 && var3.compareTo(var4) <= 0) {
            ECPoint var5 = ((ECPublicKeyParameters)this.ecKey).getQ();
            SM3Digest var6 = new SM3Digest();
            byte[] var7 = this.getZ(var6);
            var6.update(var7, 0, var7.length);
            var6.update(var1, 0, var1.length);
            byte[] var8 = new byte[var6.getDigestSize()];
            var6.doFinal(var8, 0);
            BigInteger var9 = this.calculateE(var8);
            BigInteger var10 = var2.add(var3).mod(var4);
            if (var10.equals(ZERO)) {
               return false;
            } else {
               ECPoint var11 = this.ecParams.getG().multiply(var3);
               var11 = var11.add(var5.multiply(var10)).normalize();
               return var2.equals(var9.add(var11.getAffineXCoord().toBigInteger()).mod(var4));
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private byte[] getZ(Digest var1) {
      this.addUserID(var1, this.userID);
      this.addFieldElement(var1, this.ecParams.getCurve().getA());
      this.addFieldElement(var1, this.ecParams.getCurve().getB());
      this.addFieldElement(var1, this.ecParams.getG().getAffineXCoord());
      this.addFieldElement(var1, this.ecParams.getG().getAffineYCoord());
      this.addFieldElement(var1, this.pubPoint.getAffineXCoord());
      this.addFieldElement(var1, this.pubPoint.getAffineYCoord());
      byte[] var2 = new byte[var1.getDigestSize()];
      var1.doFinal(var2, 0);
      return var2;
   }

   private void addUserID(Digest var1, byte[] var2) {
      int var3 = var2.length * 8;
      var1.update((byte)(var3 >> 8 & 255));
      var1.update((byte)(var3 & 255));
      var1.update(var2, 0, var2.length);
   }

   private void addFieldElement(Digest var1, ECFieldElement var2) {
      byte[] var3 = BigIntegers.asUnsignedByteArray(this.curveLength, var2.toBigInteger());
      var1.update(var3, 0, var3.length);
   }

   protected ECMultiplier createBasePointMultiplier() {
      return new FixedPointCombMultiplier();
   }

   protected BigInteger calculateE(byte[] var1) {
      return new BigInteger(1, var1);
   }
}
