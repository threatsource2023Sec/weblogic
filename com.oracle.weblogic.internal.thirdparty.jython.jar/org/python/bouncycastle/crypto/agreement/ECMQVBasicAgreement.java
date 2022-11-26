package org.python.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.BasicAgreement;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.crypto.params.MQVPrivateParameters;
import org.python.bouncycastle.crypto.params.MQVPublicParameters;
import org.python.bouncycastle.math.ec.ECAlgorithms;
import org.python.bouncycastle.math.ec.ECConstants;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.Properties;

public class ECMQVBasicAgreement implements BasicAgreement {
   MQVPrivateParameters privParams;

   public void init(CipherParameters var1) {
      this.privParams = (MQVPrivateParameters)var1;
   }

   public int getFieldSize() {
      return (this.privParams.getStaticPrivateKey().getParameters().getCurve().getFieldSize() + 7) / 8;
   }

   public BigInteger calculateAgreement(CipherParameters var1) {
      if (Properties.isOverrideSet("org.python.bouncycastle.ec.disable_mqv")) {
         throw new IllegalStateException("ECMQV explicitly disabled");
      } else {
         MQVPublicParameters var2 = (MQVPublicParameters)var1;
         ECPrivateKeyParameters var3 = this.privParams.getStaticPrivateKey();
         ECDomainParameters var4 = var3.getParameters();
         if (!var4.equals(var2.getStaticPublicKey().getParameters())) {
            throw new IllegalStateException("ECMQV public key components have wrong domain parameters");
         } else {
            ECPoint var5 = this.calculateMqvAgreement(var4, var3, this.privParams.getEphemeralPrivateKey(), this.privParams.getEphemeralPublicKey(), var2.getStaticPublicKey(), var2.getEphemeralPublicKey()).normalize();
            if (var5.isInfinity()) {
               throw new IllegalStateException("Infinity is not a valid agreement value for MQV");
            } else {
               return var5.getAffineXCoord().toBigInteger();
            }
         }
      }
   }

   private ECPoint calculateMqvAgreement(ECDomainParameters var1, ECPrivateKeyParameters var2, ECPrivateKeyParameters var3, ECPublicKeyParameters var4, ECPublicKeyParameters var5, ECPublicKeyParameters var6) {
      BigInteger var7 = var1.getN();
      int var8 = (var7.bitLength() + 1) / 2;
      BigInteger var9 = ECConstants.ONE.shiftLeft(var8);
      ECCurve var10 = var1.getCurve();
      ECPoint[] var11 = new ECPoint[]{ECAlgorithms.importPoint(var10, var4.getQ()), ECAlgorithms.importPoint(var10, var5.getQ()), ECAlgorithms.importPoint(var10, var6.getQ())};
      var10.normalizeAll(var11);
      ECPoint var12 = var11[0];
      ECPoint var13 = var11[1];
      ECPoint var14 = var11[2];
      BigInteger var15 = var12.getAffineXCoord().toBigInteger();
      BigInteger var16 = var15.mod(var9);
      BigInteger var17 = var16.setBit(var8);
      BigInteger var18 = var2.getD().multiply(var17).add(var3.getD()).mod(var7);
      BigInteger var19 = var14.getAffineXCoord().toBigInteger();
      BigInteger var20 = var19.mod(var9);
      BigInteger var21 = var20.setBit(var8);
      BigInteger var22 = var1.getH().multiply(var18).mod(var7);
      return ECAlgorithms.sumOfTwoMultiplies(var13, var21.multiply(var22).mod(var7), var14, var22);
   }
}
