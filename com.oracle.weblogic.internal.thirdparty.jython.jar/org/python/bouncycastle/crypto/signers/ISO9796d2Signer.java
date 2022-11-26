package org.python.bouncycastle.crypto.signers;

import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.SignerWithRecovery;
import org.python.bouncycastle.crypto.params.RSAKeyParameters;
import org.python.bouncycastle.util.Arrays;

public class ISO9796d2Signer implements SignerWithRecovery {
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
   private int trailer;
   private int keyBits;
   private byte[] block;
   private byte[] mBuf;
   private int messageLength;
   private boolean fullMessage;
   private byte[] recoveredMessage;
   private byte[] preSig;
   private byte[] preBlock;

   public ISO9796d2Signer(AsymmetricBlockCipher var1, Digest var2, boolean var3) {
      this.cipher = var1;
      this.digest = var2;
      if (var3) {
         this.trailer = 188;
      } else {
         Integer var4 = ISOTrailers.getTrailer(var2);
         if (var4 == null) {
            throw new IllegalArgumentException("no valid trailer for digest: " + var2.getAlgorithmName());
         }

         this.trailer = var4;
      }

   }

   public ISO9796d2Signer(AsymmetricBlockCipher var1, Digest var2) {
      this(var1, var2, false);
   }

   public void init(boolean var1, CipherParameters var2) {
      RSAKeyParameters var3 = (RSAKeyParameters)var2;
      this.cipher.init(var1, var3);
      this.keyBits = var3.getModulus().bitLength();
      this.block = new byte[(this.keyBits + 7) / 8];
      if (this.trailer == 188) {
         this.mBuf = new byte[this.block.length - this.digest.getDigestSize() - 2];
      } else {
         this.mBuf = new byte[this.block.length - this.digest.getDigestSize() - 3];
      }

      this.reset();
   }

   private boolean isSameAs(byte[] var1, byte[] var2) {
      boolean var3 = true;
      int var4;
      if (this.messageLength > this.mBuf.length) {
         if (this.mBuf.length > var2.length) {
            var3 = false;
         }

         for(var4 = 0; var4 != this.mBuf.length; ++var4) {
            if (var1[var4] != var2[var4]) {
               var3 = false;
            }
         }
      } else {
         if (this.messageLength != var2.length) {
            var3 = false;
         }

         for(var4 = 0; var4 != var2.length; ++var4) {
            if (var1[var4] != var2[var4]) {
               var3 = false;
            }
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
      if ((var2[0] & 192 ^ 64) != 0) {
         throw new InvalidCipherTextException("malformed signature");
      } else if ((var2[var2.length - 1] & 15 ^ 12) != 0) {
         throw new InvalidCipherTextException("malformed signature");
      } else {
         boolean var3 = false;
         int var4;
         byte var6;
         if ((var2[var2.length - 1] & 255 ^ 188) == 0) {
            var6 = 1;
         } else {
            var4 = (var2[var2.length - 2] & 255) << 8 | var2[var2.length - 1] & 255;
            Integer var5 = ISOTrailers.getTrailer(this.digest);
            if (var5 == null) {
               throw new IllegalArgumentException("unrecognised hash in signature");
            }

            if (var4 != var5) {
               throw new IllegalStateException("signer initialised with wrong digest for trailer " + var4);
            }

            var6 = 2;
         }

         boolean var7 = false;

         for(var4 = 0; var4 != var2.length && (var2[var4] & 15 ^ 10) != 0; ++var4) {
         }

         ++var4;
         int var8 = var2.length - var6 - this.digest.getDigestSize();
         if (var8 - var4 <= 0) {
            throw new InvalidCipherTextException("malformed block");
         } else {
            if ((var2[0] & 32) == 0) {
               this.fullMessage = true;
               this.recoveredMessage = new byte[var8 - var4];
               System.arraycopy(var2, var4, this.recoveredMessage, 0, this.recoveredMessage.length);
            } else {
               this.fullMessage = false;
               this.recoveredMessage = new byte[var8 - var4];
               System.arraycopy(var2, var4, this.recoveredMessage, 0, this.recoveredMessage.length);
            }

            this.preSig = var1;
            this.preBlock = var2;
            this.digest.update(this.recoveredMessage, 0, this.recoveredMessage.length);
            this.messageLength = this.recoveredMessage.length;
            System.arraycopy(this.recoveredMessage, 0, this.mBuf, 0, this.recoveredMessage.length);
         }
      }
   }

   public void update(byte var1) {
      this.digest.update(var1);
      if (this.messageLength < this.mBuf.length) {
         this.mBuf[this.messageLength] = var1;
      }

      ++this.messageLength;
   }

   public void update(byte[] var1, int var2, int var3) {
      while(var3 > 0 && this.messageLength < this.mBuf.length) {
         this.update(var1[var2]);
         ++var2;
         --var3;
      }

      this.digest.update(var1, var2, var3);
      this.messageLength += var3;
   }

   public void reset() {
      this.digest.reset();
      this.messageLength = 0;
      this.clearBlock(this.mBuf);
      if (this.recoveredMessage != null) {
         this.clearBlock(this.recoveredMessage);
      }

      this.recoveredMessage = null;
      this.fullMessage = false;
      if (this.preSig != null) {
         this.preSig = null;
         this.clearBlock(this.preBlock);
         this.preBlock = null;
      }

   }

   public byte[] generateSignature() throws CryptoException {
      int var1 = this.digest.getDigestSize();
      boolean var2 = false;
      boolean var3 = false;
      byte var7;
      int var8;
      if (this.trailer == 188) {
         var7 = 8;
         var8 = this.block.length - var1 - 1;
         this.digest.doFinal(this.block, var8);
         this.block[this.block.length - 1] = -68;
      } else {
         var7 = 16;
         var8 = this.block.length - var1 - 2;
         this.digest.doFinal(this.block, var8);
         this.block[this.block.length - 2] = (byte)(this.trailer >>> 8);
         this.block[this.block.length - 1] = (byte)this.trailer;
      }

      boolean var4 = false;
      int var5 = (var1 + this.messageLength) * 8 + var7 + 4 - this.keyBits;
      int var6;
      byte var9;
      if (var5 > 0) {
         var6 = this.messageLength - (var5 + 7) / 8;
         var9 = 96;
         var8 -= var6;
         System.arraycopy(this.mBuf, 0, this.block, var8, var6);
         this.recoveredMessage = new byte[var6];
      } else {
         var9 = 64;
         var8 -= this.messageLength;
         System.arraycopy(this.mBuf, 0, this.block, var8, this.messageLength);
         this.recoveredMessage = new byte[this.messageLength];
      }

      byte[] var10000;
      if (var8 - 1 > 0) {
         for(var6 = var8 - 1; var6 != 0; --var6) {
            this.block[var6] = -69;
         }

         var10000 = this.block;
         var10000[var8 - 1] = (byte)(var10000[var8 - 1] ^ 1);
         this.block[0] = 11;
         var10000 = this.block;
         var10000[0] |= var9;
      } else {
         this.block[0] = 10;
         var10000 = this.block;
         var10000[0] |= var9;
      }

      byte[] var10 = this.cipher.processBlock(this.block, 0, this.block.length);
      this.fullMessage = (var9 & 32) == 0;
      System.arraycopy(this.mBuf, 0, this.recoveredMessage, 0, this.recoveredMessage.length);
      this.messageLength = 0;
      this.clearBlock(this.mBuf);
      this.clearBlock(this.block);
      return var10;
   }

   public boolean verifySignature(byte[] var1) {
      Object var2 = null;
      byte[] var10;
      if (this.preSig == null) {
         try {
            var10 = this.cipher.processBlock(var1, 0, var1.length);
         } catch (Exception var9) {
            return false;
         }
      } else {
         if (!Arrays.areEqual(this.preSig, var1)) {
            throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
         }

         var10 = this.preBlock;
         this.preSig = null;
         this.preBlock = null;
      }

      if ((var10[0] & 192 ^ 64) != 0) {
         return this.returnFalse(var10);
      } else if ((var10[var10.length - 1] & 15 ^ 12) != 0) {
         return this.returnFalse(var10);
      } else {
         boolean var3 = false;
         int var4;
         byte var11;
         if ((var10[var10.length - 1] & 255 ^ 188) == 0) {
            var11 = 1;
         } else {
            var4 = (var10[var10.length - 2] & 255) << 8 | var10[var10.length - 1] & 255;
            Integer var5 = ISOTrailers.getTrailer(this.digest);
            if (var5 == null) {
               throw new IllegalArgumentException("unrecognised hash in signature");
            }

            if (var4 != var5) {
               throw new IllegalStateException("signer initialised with wrong digest for trailer " + var4);
            }

            var11 = 2;
         }

         boolean var12 = false;

         for(var4 = 0; var4 != var10.length && (var10[var4] & 15 ^ 10) != 0; ++var4) {
         }

         ++var4;
         byte[] var13 = new byte[this.digest.getDigestSize()];
         int var6 = var10.length - var11 - var13.length;
         if (var6 - var4 <= 0) {
            return this.returnFalse(var10);
         } else {
            boolean var7;
            int var8;
            if ((var10[0] & 32) == 0) {
               this.fullMessage = true;
               if (this.messageLength > var6 - var4) {
                  return this.returnFalse(var10);
               }

               this.digest.reset();
               this.digest.update(var10, var4, var6 - var4);
               this.digest.doFinal(var13, 0);
               var7 = true;

               for(var8 = 0; var8 != var13.length; ++var8) {
                  var10[var6 + var8] ^= var13[var8];
                  if (var10[var6 + var8] != 0) {
                     var7 = false;
                  }
               }

               if (!var7) {
                  return this.returnFalse(var10);
               }

               this.recoveredMessage = new byte[var6 - var4];
               System.arraycopy(var10, var4, this.recoveredMessage, 0, this.recoveredMessage.length);
            } else {
               this.fullMessage = false;
               this.digest.doFinal(var13, 0);
               var7 = true;

               for(var8 = 0; var8 != var13.length; ++var8) {
                  var10[var6 + var8] ^= var13[var8];
                  if (var10[var6 + var8] != 0) {
                     var7 = false;
                  }
               }

               if (!var7) {
                  return this.returnFalse(var10);
               }

               this.recoveredMessage = new byte[var6 - var4];
               System.arraycopy(var10, var4, this.recoveredMessage, 0, this.recoveredMessage.length);
            }

            if (this.messageLength != 0 && !this.isSameAs(this.mBuf, this.recoveredMessage)) {
               return this.returnFalse(var10);
            } else {
               this.clearBlock(this.mBuf);
               this.clearBlock(var10);
               this.messageLength = 0;
               return true;
            }
         }
      }
   }

   private boolean returnFalse(byte[] var1) {
      this.messageLength = 0;
      this.clearBlock(this.mBuf);
      this.clearBlock(var1);
      return false;
   }

   public boolean hasFullMessage() {
      return this.fullMessage;
   }

   public byte[] getRecoveredMessage() {
      return this.recoveredMessage;
   }
}
