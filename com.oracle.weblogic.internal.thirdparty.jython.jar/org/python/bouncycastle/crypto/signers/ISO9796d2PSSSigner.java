package org.python.bouncycastle.crypto.signers;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.SignerWithRecovery;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.crypto.params.ParametersWithSalt;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.util.Arrays;

public class ISO9796d2PSSSigner implements SignerWithRecovery {
   /** @deprecated */
   public static final int TRAILER_IMPLICIT = 188;
   /** @deprecated */
   public static final int TRAILER_RIPEMD160 = 12748;
   /** @deprecated */
   public static final int TRAILER_RIPEMD128 = 13004;
   /** @deprecated */
   public static final int TRAILER_SHA1 = 13260;
   /** @deprecated */
   public static final int TRAILER_SHA256 = 13516;
   /** @deprecated */
   public static final int TRAILER_SHA512 = 13772;
   /** @deprecated */
   public static final int TRAILER_SHA384 = 14028;
   /** @deprecated */
   public static final int TRAILER_WHIRLPOOL = 14284;
   private Digest digest;
   private AsymmetricBlockCipher cipher;
   private SecureRandom random;
   private byte[] standardSalt;
   private int hLen;
   private int trailer;
   private int keyBits;
   private byte[] block;
   private byte[] mBuf;
   private int messageLength;
   private int saltLength;
   private boolean fullMessage;
   private byte[] recoveredMessage;
   private byte[] preSig;
   private byte[] preBlock;
   private int preMStart;
   private int preTLength;

   public ISO9796d2PSSSigner(AsymmetricBlockCipher var1, Digest var2, int var3, boolean var4) {
      this.cipher = var1;
      this.digest = var2;
      this.hLen = var2.getDigestSize();
      this.saltLength = var3;
      if (var4) {
         this.trailer = 188;
      } else {
         Integer var5 = ISOTrailers.getTrailer(var2);
         if (var5 == null) {
            throw new IllegalArgumentException("no valid trailer for digest: " + var2.getAlgorithmName());
         }

         this.trailer = var5;
      }

   }

   public ISO9796d2PSSSigner(AsymmetricBlockCipher var1, Digest var2, int var3) {
      this(var1, var2, var3, false);
   }

   public void init(boolean var1, CipherParameters var2) {
      int var3 = this.saltLength;
      RSAKeyParameters var5;
      if (var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var4 = (ParametersWithRandom)var2;
         var5 = (RSAKeyParameters)var4.getParameters();
         if (var1) {
            this.random = var4.getRandom();
         }
      } else if (var2 instanceof ParametersWithSalt) {
         ParametersWithSalt var6 = (ParametersWithSalt)var2;
         var5 = (RSAKeyParameters)var6.getParameters();
         this.standardSalt = var6.getSalt();
         var3 = this.standardSalt.length;
         if (this.standardSalt.length != this.saltLength) {
            throw new IllegalArgumentException("Fixed salt is of wrong length");
         }
      } else {
         var5 = (RSAKeyParameters)var2;
         if (var1) {
            this.random = new SecureRandom();
         }
      }

      this.cipher.init(var1, var5);
      this.keyBits = var5.getModulus().bitLength();
      this.block = new byte[(this.keyBits + 7) / 8];
      if (this.trailer == 188) {
         this.mBuf = new byte[this.block.length - this.digest.getDigestSize() - var3 - 1 - 1];
      } else {
         this.mBuf = new byte[this.block.length - this.digest.getDigestSize() - var3 - 1 - 2];
      }

      this.reset();
   }

   private boolean isSameAs(byte[] var1, byte[] var2) {
      boolean var3 = true;
      if (this.messageLength != var2.length) {
         var3 = false;
      }

      for(int var4 = 0; var4 != var2.length; ++var4) {
         if (var1[var4] != var2[var4]) {
            var3 = false;
         }
      }

      return var3;
   }

   private void clearBlock(byte[] var1) {
      for(int var2 = 0; var2 != var1.length; ++var2) {
         var1[var2] = 0;
      }

   }

   public void updateWithRecoveredMessage(byte[] var1) throws InvalidCipherTextException {
      byte[] var2 = this.cipher.processBlock(var1, 0, var1.length);
      if (var2.length < (this.keyBits + 7) / 8) {
         byte[] var3 = new byte[(this.keyBits + 7) / 8];
         System.arraycopy(var2, 0, var3, var3.length - var2.length, var2.length);
         this.clearBlock(var2);
         var2 = var3;
      }

      byte var7;
      if ((var2[var2.length - 1] & 255 ^ 188) == 0) {
         var7 = 1;
      } else {
         int var4 = (var2[var2.length - 2] & 255) << 8 | var2[var2.length - 1] & 255;
         Integer var5 = ISOTrailers.getTrailer(this.digest);
         if (var5 == null) {
            throw new IllegalArgumentException("unrecognised hash in signature");
         }

         if (var4 != var5) {
            throw new IllegalStateException("signer initialised with wrong digest for trailer " + var4);
         }

         var7 = 2;
      }

      byte[] var8 = new byte[this.hLen];
      this.digest.doFinal(var8, 0);
      byte[] var9 = this.maskGeneratorFunction1(var2, var2.length - this.hLen - var7, this.hLen, var2.length - this.hLen - var7);

      int var6;
      for(var6 = 0; var6 != var9.length; ++var6) {
         var2[var6] ^= var9[var6];
      }

      var2[0] = (byte)(var2[0] & 127);

      for(var6 = 0; var6 != var2.length && var2[var6] != 1; ++var6) {
      }

      ++var6;
      if (var6 >= var2.length) {
         this.clearBlock(var2);
      }

      this.fullMessage = var6 > 1;
      this.recoveredMessage = new byte[var9.length - var6 - this.saltLength];
      System.arraycopy(var2, var6, this.recoveredMessage, 0, this.recoveredMessage.length);
      System.arraycopy(this.recoveredMessage, 0, this.mBuf, 0, this.recoveredMessage.length);
      this.preSig = var1;
      this.preBlock = var2;
      this.preMStart = var6;
      this.preTLength = var7;
   }

   public void update(byte var1) {
      if (this.preSig == null && this.messageLength < this.mBuf.length) {
         this.mBuf[this.messageLength++] = var1;
      } else {
         this.digest.update(var1);
      }

   }

   public void update(byte[] var1, int var2, int var3) {
      if (this.preSig == null) {
         while(var3 > 0 && this.messageLength < this.mBuf.length) {
            this.update(var1[var2]);
            ++var2;
            --var3;
         }
      }

      if (var3 > 0) {
         this.digest.update(var1, var2, var3);
      }

   }

   public void reset() {
      this.digest.reset();
      this.messageLength = 0;
      if (this.mBuf != null) {
         this.clearBlock(this.mBuf);
      }

      if (this.recoveredMessage != null) {
         this.clearBlock(this.recoveredMessage);
         this.recoveredMessage = null;
      }

      this.fullMessage = false;
      if (this.preSig != null) {
         this.preSig = null;
         this.clearBlock(this.preBlock);
         this.preBlock = null;
      }

   }

   public byte[] generateSignature() throws CryptoException {
      int var1 = this.digest.getDigestSize();
      byte[] var2 = new byte[var1];
      this.digest.doFinal(var2, 0);
      byte[] var3 = new byte[8];
      this.LtoOSP((long)(this.messageLength * 8), var3);
      this.digest.update(var3, 0, var3.length);
      this.digest.update(this.mBuf, 0, this.messageLength);
      this.digest.update(var2, 0, var2.length);
      byte[] var4;
      if (this.standardSalt != null) {
         var4 = this.standardSalt;
      } else {
         var4 = new byte[this.saltLength];
         this.random.nextBytes(var4);
      }

      this.digest.update(var4, 0, var4.length);
      byte[] var5 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var5, 0);
      byte var6 = 2;
      if (this.trailer == 188) {
         var6 = 1;
      }

      int var7 = this.block.length - this.messageLength - var4.length - this.hLen - var6 - 1;
      this.block[var7] = 1;
      System.arraycopy(this.mBuf, 0, this.block, var7 + 1, this.messageLength);
      System.arraycopy(var4, 0, this.block, var7 + 1 + this.messageLength, var4.length);
      byte[] var8 = this.maskGeneratorFunction1(var5, 0, var5.length, this.block.length - this.hLen - var6);

      byte[] var10000;
      for(int var9 = 0; var9 != var8.length; ++var9) {
         var10000 = this.block;
         var10000[var9] ^= var8[var9];
      }

      System.arraycopy(var5, 0, this.block, this.block.length - this.hLen - var6, this.hLen);
      if (this.trailer == 188) {
         this.block[this.block.length - 1] = -68;
      } else {
         this.block[this.block.length - 2] = (byte)(this.trailer >>> 8);
         this.block[this.block.length - 1] = (byte)this.trailer;
      }

      var10000 = this.block;
      var10000[0] = (byte)(var10000[0] & 127);
      byte[] var10 = this.cipher.processBlock(this.block, 0, this.block.length);
      this.recoveredMessage = new byte[this.messageLength];
      this.fullMessage = this.messageLength <= this.mBuf.length;
      System.arraycopy(this.mBuf, 0, this.recoveredMessage, 0, this.recoveredMessage.length);
      this.clearBlock(this.mBuf);
      this.clearBlock(this.block);
      this.messageLength = 0;
      return var10;
   }

   public boolean verifySignature(byte[] var1) {
      byte[] var2 = new byte[this.hLen];
      this.digest.doFinal(var2, 0);
      boolean var3 = false;
      if (this.preSig == null) {
         try {
            this.updateWithRecoveredMessage(var1);
         } catch (Exception var11) {
            return false;
         }
      } else if (!Arrays.areEqual(this.preSig, var1)) {
         throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
      }

      byte[] var5 = this.preBlock;
      int var12 = this.preMStart;
      int var6 = this.preTLength;
      this.preSig = null;
      this.preBlock = null;
      byte[] var4 = new byte[8];
      this.LtoOSP((long)(this.recoveredMessage.length * 8), var4);
      this.digest.update(var4, 0, var4.length);
      if (this.recoveredMessage.length != 0) {
         this.digest.update(this.recoveredMessage, 0, this.recoveredMessage.length);
      }

      this.digest.update(var2, 0, var2.length);
      if (this.standardSalt != null) {
         this.digest.update(this.standardSalt, 0, this.standardSalt.length);
      } else {
         this.digest.update(var5, var12 + this.recoveredMessage.length, this.saltLength);
      }

      byte[] var7 = new byte[this.digest.getDigestSize()];
      this.digest.doFinal(var7, 0);
      int var8 = var5.length - var6 - var7.length;
      boolean var9 = true;

      for(int var10 = 0; var10 != var7.length; ++var10) {
         if (var7[var10] != var5[var8 + var10]) {
            var9 = false;
         }
      }

      this.clearBlock(var5);
      this.clearBlock(var7);
      if (!var9) {
         this.fullMessage = false;
         this.messageLength = 0;
         this.clearBlock(this.recoveredMessage);
         return false;
      } else if (this.messageLength != 0 && !this.isSameAs(this.mBuf, this.recoveredMessage)) {
         this.messageLength = 0;
         this.clearBlock(this.mBuf);
         return false;
      } else {
         this.messageLength = 0;
         this.clearBlock(this.mBuf);
         return true;
      }
   }

   public boolean hasFullMessage() {
      return this.fullMessage;
   }

   public byte[] getRecoveredMessage() {
      return this.recoveredMessage;
   }

   private void ItoOSP(int var1, byte[] var2) {
      var2[0] = (byte)(var1 >>> 24);
      var2[1] = (byte)(var1 >>> 16);
      var2[2] = (byte)(var1 >>> 8);
      var2[3] = (byte)(var1 >>> 0);
   }

   private void LtoOSP(long var1, byte[] var3) {
      var3[0] = (byte)((int)(var1 >>> 56));
      var3[1] = (byte)((int)(var1 >>> 48));
      var3[2] = (byte)((int)(var1 >>> 40));
      var3[3] = (byte)((int)(var1 >>> 32));
      var3[4] = (byte)((int)(var1 >>> 24));
      var3[5] = (byte)((int)(var1 >>> 16));
      var3[6] = (byte)((int)(var1 >>> 8));
      var3[7] = (byte)((int)(var1 >>> 0));
   }

   private byte[] maskGeneratorFunction1(byte[] var1, int var2, int var3, int var4) {
      byte[] var5 = new byte[var4];
      byte[] var6 = new byte[this.hLen];
      byte[] var7 = new byte[4];
      int var8 = 0;
      this.digest.reset();

      while(var8 < var4 / this.hLen) {
         this.ItoOSP(var8, var7);
         this.digest.update(var1, var2, var3);
         this.digest.update(var7, 0, var7.length);
         this.digest.doFinal(var6, 0);
         System.arraycopy(var6, 0, var5, var8 * this.hLen, this.hLen);
         ++var8;
      }

      if (var8 * this.hLen < var4) {
         this.ItoOSP(var8, var7);
         this.digest.update(var1, var2, var3);
         this.digest.update(var7, 0, var7.length);
         this.digest.doFinal(var6, 0);
         System.arraycopy(var6, 0, var5, var8 * this.hLen, var5.length - var8 * this.hLen);
      }

      return var5;
   }
}
