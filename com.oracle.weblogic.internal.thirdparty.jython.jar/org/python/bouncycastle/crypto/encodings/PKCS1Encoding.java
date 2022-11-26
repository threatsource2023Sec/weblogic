package org.python.bouncycastle.crypto.encodings;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.util.Arrays;

public class PKCS1Encoding implements AsymmetricBlockCipher {
   /** @deprecated */
   public static final String STRICT_LENGTH_ENABLED_PROPERTY = "org.python.bouncycastle.pkcs1.strict";
   public static final String NOT_STRICT_LENGTH_ENABLED_PROPERTY = "org.python.bouncycastle.pkcs1.not_strict";
   private static final int HEADER_LENGTH = 10;
   private SecureRandom random;
   private AsymmetricBlockCipher engine;
   private boolean forEncryption;
   private boolean forPrivateKey;
   private boolean useStrictLength;
   private int pLen = -1;
   private byte[] fallback = null;
   private byte[] blockBuffer;

   public PKCS1Encoding(AsymmetricBlockCipher var1) {
      this.engine = var1;
      this.useStrictLength = this.useStrict();
   }

   public PKCS1Encoding(AsymmetricBlockCipher var1, int var2) {
      this.engine = var1;
      this.useStrictLength = this.useStrict();
      this.pLen = var2;
   }

   public PKCS1Encoding(AsymmetricBlockCipher var1, byte[] var2) {
      this.engine = var1;
      this.useStrictLength = this.useStrict();
      this.fallback = var2;
      this.pLen = var2.length;
   }

   private boolean useStrict() {
      String var1 = (String)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return System.getProperty("org.python.bouncycastle.pkcs1.strict");
         }
      });
      String var2 = (String)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return System.getProperty("org.python.bouncycastle.pkcs1.not_strict");
         }
      });
      if (var2 != null) {
         return !var2.equals("true");
      } else {
         return var1 == null || var1.equals("true");
      }
   }

   public AsymmetricBlockCipher getUnderlyingCipher() {
      return this.engine;
   }

   public void init(boolean var1, CipherParameters var2) {
      AsymmetricKeyParameter var4;
      if (var2 instanceof ParametersWithRandom) {
         ParametersWithRandom var3 = (ParametersWithRandom)var2;
         this.random = var3.getRandom();
         var4 = (AsymmetricKeyParameter)var3.getParameters();
      } else {
         var4 = (AsymmetricKeyParameter)var2;
         if (!var4.isPrivate() && var1) {
            this.random = new SecureRandom();
         }
      }

      this.engine.init(var1, var2);
      this.forPrivateKey = var4.isPrivate();
      this.forEncryption = var1;
      this.blockBuffer = new byte[this.engine.getOutputBlockSize()];
      if (this.pLen > 0 && this.fallback == null && this.random == null) {
         throw new IllegalArgumentException("encoder requires random");
      }
   }

   public int getInputBlockSize() {
      int var1 = this.engine.getInputBlockSize();
      return this.forEncryption ? var1 - 10 : var1;
   }

   public int getOutputBlockSize() {
      int var1 = this.engine.getOutputBlockSize();
      return this.forEncryption ? var1 : var1 - 10;
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      return this.forEncryption ? this.encodeBlock(var1, var2, var3) : this.decodeBlock(var1, var2, var3);
   }

   private byte[] encodeBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if (var3 > this.getInputBlockSize()) {
         throw new IllegalArgumentException("input data too large");
      } else {
         byte[] var4 = new byte[this.engine.getInputBlockSize()];
         int var5;
         if (this.forPrivateKey) {
            var4[0] = 1;

            for(var5 = 1; var5 != var4.length - var3 - 1; ++var5) {
               var4[var5] = -1;
            }
         } else {
            this.random.nextBytes(var4);
            var4[0] = 2;

            for(var5 = 1; var5 != var4.length - var3 - 1; ++var5) {
               while(var4[var5] == 0) {
                  var4[var5] = (byte)this.random.nextInt();
               }
            }
         }

         var4[var4.length - var3 - 1] = 0;
         System.arraycopy(var1, var2, var4, var4.length - var3, var3);
         return this.engine.processBlock(var4, 0, var4.length);
      }
   }

   private static int checkPkcs1Encoding(byte[] var0, int var1) {
      int var2 = 0;
      var2 |= var0[0] ^ 2;
      int var3 = var0.length - (var1 + 1);

      for(int var4 = 1; var4 < var3; ++var4) {
         int var5 = var0[var4];
         var5 |= var5 >> 1;
         var5 |= var5 >> 2;
         var5 |= var5 >> 4;
         var2 |= (var5 & 1) - 1;
      }

      var2 |= var0[var0.length - (var1 + 1)];
      var2 |= var2 >> 1;
      var2 |= var2 >> 2;
      var2 |= var2 >> 4;
      return ~((var2 & 1) - 1);
   }

   private byte[] decodeBlockOrRandom(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if (!this.forPrivateKey) {
         throw new InvalidCipherTextException("sorry, this method is only for decryption, not for signing");
      } else {
         byte[] var4 = this.engine.processBlock(var1, var2, var3);
         byte[] var5;
         if (this.fallback == null) {
            var5 = new byte[this.pLen];
            this.random.nextBytes(var5);
         } else {
            var5 = this.fallback;
         }

         byte[] var6 = this.useStrictLength & var4.length != this.engine.getOutputBlockSize() ? this.blockBuffer : var4;
         int var7 = checkPkcs1Encoding(var6, this.pLen);
         byte[] var8 = new byte[this.pLen];

         for(int var9 = 0; var9 < this.pLen; ++var9) {
            var8[var9] = (byte)(var6[var9 + (var6.length - this.pLen)] & ~var7 | var5[var9] & var7);
         }

         Arrays.fill((byte[])var6, (byte)0);
         return var8;
      }
   }

   private byte[] decodeBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if (this.pLen != -1) {
         return this.decodeBlockOrRandom(var1, var2, var3);
      } else {
         byte[] var4 = this.engine.processBlock(var1, var2, var3);
         boolean var5 = this.useStrictLength & var4.length != this.engine.getOutputBlockSize();
         byte[] var6;
         if (var4.length < this.getOutputBlockSize()) {
            var6 = this.blockBuffer;
         } else {
            var6 = var4;
         }

         byte var7 = var6[0];
         boolean var8;
         if (this.forPrivateKey) {
            var8 = var7 != 2;
         } else {
            var8 = var7 != 1;
         }

         int var9 = this.findStart(var7, var6);
         ++var9;
         if (var8 | var9 < 10) {
            Arrays.fill((byte[])var6, (byte)0);
            throw new InvalidCipherTextException("block incorrect");
         } else if (var5) {
            Arrays.fill((byte[])var6, (byte)0);
            throw new InvalidCipherTextException("block incorrect size");
         } else {
            byte[] var10 = new byte[var6.length - var9];
            System.arraycopy(var6, var9, var10, 0, var10.length);
            return var10;
         }
      }
   }

   private int findStart(byte var1, byte[] var2) throws InvalidCipherTextException {
      int var3 = -1;
      boolean var4 = false;

      for(int var5 = 1; var5 != var2.length; ++var5) {
         byte var6 = var2[var5];
         if (var6 == 0 & var3 < 0) {
            var3 = var5;
         }

         var4 |= var1 == 1 & var3 < 0 & var6 != -1;
      }

      if (var4) {
         return -1;
      } else {
         return var3;
      }
   }
}
