package org.python.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.digests.SM3Digest;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECKeyParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.math.ec.ECConstants;
import org.python.bouncycastle.math.ec.ECFieldElement;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.BigIntegers;

public class SM2Engine {
   private final Digest digest;
   private boolean forEncryption;
   private ECKeyParameters ecKey;
   private ECDomainParameters ecParams;
   private int curveLength;
   private SecureRandom random;

   public SM2Engine() {
      this(new SM3Digest());
   }

   public SM2Engine(Digest var1) {
      this.digest = var1;
   }

   public void init(boolean var1, CipherParameters var2) {
      this.forEncryption = var1;
      if (var1) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         this.ecKey = (ECKeyParameters)var3.getParameters();
         this.ecParams = this.ecKey.getParameters();
         ECPoint var4 = ((ECPublicKeyParameters)this.ecKey).getQ().multiply(this.ecParams.getH());
         if (var4.isInfinity()) {
            throw new IllegalArgumentException("invalid key: [h]Q at infinity");
         }

         this.random = var3.getRandom();
      } else {
         this.ecKey = (ECKeyParameters)var2;
         this.ecParams = this.ecKey.getParameters();
      }

      this.curveLength = (this.ecParams.getCurve().getFieldSize() + 7) / 8;
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      return this.forEncryption ? this.encrypt(var1, var2, var3) : this.decrypt(var1, var2, var3);
   }

   private byte[] encrypt(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      byte[] var4 = new byte[var3];
      System.arraycopy(var1, var2, var4, 0, var4.length);

      byte[] var7;
      ECPoint var8;
      do {
         BigInteger var5 = this.nextK();
         ECPoint var6 = this.ecParams.getG().multiply(var5).normalize();
         var7 = var6.getEncoded(false);
         var8 = ((ECPublicKeyParameters)this.ecKey).getQ().multiply(var5).normalize();
         this.kdf(this.digest, var8, var4);
      } while(this.notEncrypted(var4, var1, var2));

      byte[] var9 = new byte[this.digest.getDigestSize()];
      this.addFieldElement(this.digest, var8.getAffineXCoord());
      this.digest.update(var1, var2, var3);
      this.addFieldElement(this.digest, var8.getAffineYCoord());
      this.digest.doFinal(var9, 0);
      return Arrays.concatenate(var7, var4, var9);
   }

   private byte[] decrypt(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      byte[] var4 = new byte[this.curveLength * 2 + 1];
      System.arraycopy(var1, var2, var4, 0, var4.length);
      ECPoint var5 = this.ecParams.getCurve().decodePoint(var4);
      ECPoint var6 = var5.multiply(this.ecParams.getH());
      if (var6.isInfinity()) {
         throw new InvalidCipherTextException("[h]C1 at infinity");
      } else {
         var5 = var5.multiply(((ECPrivateKeyParameters)this.ecKey).getD()).normalize();
         byte[] var7 = new byte[var3 - var4.length - this.digest.getDigestSize()];
         System.arraycopy(var1, var2 + var4.length, var7, 0, var7.length);
         this.kdf(this.digest, var5, var7);
         byte[] var8 = new byte[this.digest.getDigestSize()];
         this.addFieldElement(this.digest, var5.getAffineXCoord());
         this.digest.update(var7, 0, var7.length);
         this.addFieldElement(this.digest, var5.getAffineYCoord());
         this.digest.doFinal(var8, 0);
         int var9 = 0;

         for(int var10 = 0; var10 != var8.length; ++var10) {
            var9 |= var8[var10] ^ var1[var4.length + var7.length + var10];
         }

         this.clearBlock(var4);
         this.clearBlock(var8);
         if (var9 != 0) {
            this.clearBlock(var7);
            throw new InvalidCipherTextException("invalid cipher text");
         } else {
            return var7;
         }
      }
   }

   private boolean notEncrypted(byte[] var1, byte[] var2, int var3) {
      for(int var4 = 0; var4 != var1.length; ++var4) {
         if (var1[var4] != var2[var3]) {
            return false;
         }
      }

      return true;
   }

   private void kdf(Digest var1, ECPoint var2, byte[] var3) {
      int var4 = 1;
      int var5 = var1.getDigestSize();
      byte[] var6 = new byte[var1.getDigestSize()];
      int var7 = 0;

      for(int var8 = 1; var8 <= (var3.length + var5 - 1) / var5; ++var8) {
         this.addFieldElement(var1, var2.getAffineXCoord());
         this.addFieldElement(var1, var2.getAffineYCoord());
         var1.update((byte)(var4 >> 24));
         var1.update((byte)(var4 >> 16));
         var1.update((byte)(var4 >> 8));
         var1.update((byte)var4);
         var1.doFinal(var6, 0);
         if (var7 + var6.length < var3.length) {
            this.xor(var3, var6, var7, var6.length);
         } else {
            this.xor(var3, var6, var7, var3.length - var7);
         }

         var7 += var6.length;
         ++var4;
      }

   }

   private void xor(byte[] var1, byte[] var2, int var3, int var4) {
      for(int var5 = 0; var5 != var4; ++var5) {
         var1[var3 + var5] ^= var2[var5];
      }

   }

   private BigInteger nextK() {
      int var1 = this.ecParams.getN().bitLength();

      BigInteger var2;
      do {
         do {
            var2 = new BigInteger(var1, this.random);
         } while(var2.equals(ECConstants.ZERO));
      } while(var2.compareTo(this.ecParams.getN()) >= 0);

      return var2;
   }

   private void addFieldElement(Digest var1, ECFieldElement var2) {
      byte[] var3 = BigIntegers.asUnsignedByteArray(this.curveLength, var2.toBigInteger());
      var1.update(var3, 0, var3.length);
   }

   private void clearBlock(byte[] var1) {
      for(int var2 = 0; var2 != var1.length; ++var2) {
         var1[var2] = 0;
      }

   }
}
