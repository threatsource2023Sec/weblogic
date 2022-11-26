package org.python.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.digests.SM3Digest;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ParametersWithID;
import org.python.bouncycastle.crypto.params.SM2KeyExchangePrivateParameters;
import org.python.bouncycastle.crypto.params.SM2KeyExchangePublicParameters;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.BigIntegers;

public class SM2KeyExchange {
   private final Digest digest;
   private byte[] userID;
   private ECPrivateKeyParameters staticKey;
   private ECPoint staticPubPoint;
   private ECPoint ephemeralPubPoint;
   private ECDomainParameters ecParams;
   private int curveLength;
   private int w;
   private ECPrivateKeyParameters ephemeralKey;
   private boolean initiator;

   public SM2KeyExchange() {
      this(new SM3Digest());
   }

   public SM2KeyExchange(Digest var1) {
      this.digest = var1;
   }

   public void init(CipherParameters var1) {
      SM2KeyExchangePrivateParameters var2;
      if (var1 instanceof ParametersWithID) {
         var2 = (SM2KeyExchangePrivateParameters)((ParametersWithID)var1).getParameters();
         this.userID = ((ParametersWithID)var1).getID();
      } else {
         var2 = (SM2KeyExchangePrivateParameters)var1;
         this.userID = new byte[0];
      }

      this.initiator = var2.isInitiator();
      this.staticKey = var2.getStaticPrivateKey();
      this.ephemeralKey = var2.getEphemeralPrivateKey();
      this.ecParams = this.staticKey.getParameters();
      this.staticPubPoint = var2.getStaticPublicPoint();
      this.ephemeralPubPoint = var2.getEphemeralPublicPoint();
      this.curveLength = (this.ecParams.getCurve().getFieldSize() + 7) / 8;
      this.w = this.ecParams.getCurve().getFieldSize() / 2 - 1;
   }

   public int getFieldSize() {
      return (this.staticKey.getParameters().getCurve().getFieldSize() + 7) / 8;
   }

   public byte[] calculateKey(int var1, CipherParameters var2) {
      SM2KeyExchangePublicParameters var3;
      byte[] var4;
      if (var2 instanceof ParametersWithID) {
         var3 = (SM2KeyExchangePublicParameters)((ParametersWithID)var2).getParameters();
         var4 = ((ParametersWithID)var2).getID();
      } else {
         var3 = (SM2KeyExchangePublicParameters)var2;
         var4 = new byte[0];
      }

      byte[] var5 = this.getZ(this.digest, this.userID, this.staticPubPoint);
      byte[] var6 = this.getZ(this.digest, var4, var3.getStaticPublicKey().getQ());
      ECPoint var7 = this.calculateU(var3);
      byte[] var8;
      if (this.initiator) {
         var8 = this.kdf(var7, var5, var6, var1);
      } else {
         var8 = this.kdf(var7, var6, var5, var1);
      }

      return var8;
   }

   public byte[][] calculateKeyWithConfirmation(int var1, byte[] var2, CipherParameters var3) {
      SM2KeyExchangePublicParameters var4;
      byte[] var5;
      if (var3 instanceof ParametersWithID) {
         var4 = (SM2KeyExchangePublicParameters)((ParametersWithID)var3).getParameters();
         var5 = ((ParametersWithID)var3).getID();
      } else {
         var4 = (SM2KeyExchangePublicParameters)var3;
         var5 = new byte[0];
      }

      if (this.initiator && var2 == null) {
         throw new IllegalArgumentException("if initiating, confirmationTag must be set");
      } else {
         byte[] var6 = this.getZ(this.digest, this.userID, this.staticPubPoint);
         byte[] var7 = this.getZ(this.digest, var5, var4.getStaticPublicKey().getQ());
         ECPoint var8 = this.calculateU(var4);
         byte[] var9;
         byte[] var10;
         if (this.initiator) {
            var9 = this.kdf(var8, var6, var7, var1);
            var10 = this.calculateInnerHash(this.digest, var8, var6, var7, this.ephemeralPubPoint, var4.getEphemeralPublicKey().getQ());
            byte[] var11 = this.S1(this.digest, var8, var10);
            if (!Arrays.constantTimeAreEqual(var11, var2)) {
               throw new IllegalStateException("confirmation tag mismatch");
            } else {
               return new byte[][]{var9, this.S2(this.digest, var8, var10)};
            }
         } else {
            var9 = this.kdf(var8, var7, var6, var1);
            var10 = this.calculateInnerHash(this.digest, var8, var7, var6, var4.getEphemeralPublicKey().getQ(), this.ephemeralPubPoint);
            return new byte[][]{var9, this.S1(this.digest, var8, var10), this.S2(this.digest, var8, var10)};
         }
      }
   }

   private ECPoint calculateU(SM2KeyExchangePublicParameters var1) {
      BigInteger var2 = this.reduce(this.ephemeralPubPoint.getAffineXCoord().toBigInteger());
      BigInteger var3 = this.staticKey.getD().add(var2.multiply(this.ephemeralKey.getD())).mod(this.ecParams.getN());
      BigInteger var4 = this.reduce(var1.getEphemeralPublicKey().getQ().getAffineXCoord().toBigInteger());
      ECPoint var5 = var1.getEphemeralPublicKey().getQ().multiply(var4).normalize();
      ECPoint var6 = var1.getStaticPublicKey().getQ().add(var5).normalize();
      return var6.multiply(this.ecParams.getH().multiply(var3)).normalize();
   }

   private byte[] kdf(ECPoint var1, byte[] var2, byte[] var3, int var4) {
      int var5 = 1;
      int var6 = this.digest.getDigestSize() * 8;
      byte[] var7 = new byte[this.digest.getDigestSize()];
      byte[] var8 = new byte[(var4 + 7) / 8];
      int var9 = 0;

      for(int var10 = 1; var10 <= (var4 + var6 - 1) / var6; ++var10) {
         this.addFieldElement(this.digest, var1.getAffineXCoord());
         this.addFieldElement(this.digest, var1.getAffineYCoord());
         this.digest.update(var2, 0, var2.length);
         this.digest.update(var3, 0, var3.length);
         this.digest.update((byte)(var5 >> 24));
         this.digest.update((byte)(var5 >> 16));
         this.digest.update((byte)(var5 >> 8));
         this.digest.update((byte)var5);
         this.digest.doFinal(var7, 0);
         if (var9 + var7.length < var8.length) {
            System.arraycopy(var7, 0, var8, var9, var7.length);
         } else {
            System.arraycopy(var7, 0, var8, var9, var8.length - var9);
         }

         var9 += var7.length;
         ++var5;
      }

      return var8;
   }

   private BigInteger reduce(BigInteger var1) {
      return var1.and(BigInteger.valueOf(1L).shiftLeft(this.w).subtract(BigInteger.valueOf(1L))).setBit(this.w);
   }

   private byte[] S1(Digest var1, ECPoint var2, byte[] var3) {
      byte[] var4 = new byte[var1.getDigestSize()];
      var1.update((byte)2);
      this.addFieldElement(var1, var2.getAffineYCoord());
      var1.update(var3, 0, var3.length);
      var1.doFinal(var4, 0);
      return var4;
   }

   private byte[] calculateInnerHash(Digest var1, ECPoint var2, byte[] var3, byte[] var4, ECPoint var5, ECPoint var6) {
      this.addFieldElement(var1, var2.getAffineXCoord());
      var1.update(var3, 0, var3.length);
      var1.update(var4, 0, var4.length);
      this.addFieldElement(var1, var5.getAffineXCoord());
      this.addFieldElement(var1, var5.getAffineYCoord());
      this.addFieldElement(var1, var6.getAffineXCoord());
      this.addFieldElement(var1, var6.getAffineYCoord());
      byte[] var7 = new byte[var1.getDigestSize()];
      var1.doFinal(var7, 0);
      return var7;
   }

   private byte[] S2(Digest var1, ECPoint var2, byte[] var3) {
      byte[] var4 = new byte[var1.getDigestSize()];
      var1.update((byte)3);
      this.addFieldElement(var1, var2.getAffineYCoord());
      var1.update(var3, 0, var3.length);
      var1.doFinal(var4, 0);
      return var4;
   }

   private byte[] getZ(Digest var1, byte[] var2, ECPoint var3) {
      this.addUserID(var1, var2);
      this.addFieldElement(var1, this.ecParams.getCurve().getA());
      this.addFieldElement(var1, this.ecParams.getCurve().getB());
      this.addFieldElement(var1, this.ecParams.getG().getAffineXCoord());
      this.addFieldElement(var1, this.ecParams.getG().getAffineYCoord());
      this.addFieldElement(var1, var3.getAffineXCoord());
      this.addFieldElement(var1, var3.getAffineYCoord());
      byte[] var4 = new byte[var1.getDigestSize()];
      var1.doFinal(var4, 0);
      return var4;
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
}
