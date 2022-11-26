package org.python.bouncycastle.crypto.engines;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import org.python.bouncycastle.crypto.BasicAgreement;
import org.python.bouncycastle.crypto.BufferedBlockCipher;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DerivationFunction;
import org.python.bouncycastle.crypto.EphemeralKeyPair;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.KeyParser;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.generators.EphemeralKeyPairGenerator;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.params.IESParameters;
import org.python.bouncycastle.crypto.params.IESWithCipherParameters;
import org.python.bouncycastle.crypto.params.KDFParameters;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.BigIntegers;
import org.python.bouncycastle.util.Pack;

public class IESEngine {
   BasicAgreement agree;
   DerivationFunction kdf;
   Mac mac;
   BufferedBlockCipher cipher;
   byte[] macBuf;
   boolean forEncryption;
   CipherParameters privParam;
   CipherParameters pubParam;
   IESParameters param;
   byte[] V;
   private EphemeralKeyPairGenerator keyPairGenerator;
   private KeyParser keyParser;
   private byte[] IV;

   public IESEngine(BasicAgreement var1, DerivationFunction var2, Mac var3) {
      this.agree = var1;
      this.kdf = var2;
      this.mac = var3;
      this.macBuf = new byte[var3.getMacSize()];
      this.cipher = null;
   }

   public IESEngine(BasicAgreement var1, DerivationFunction var2, Mac var3, BufferedBlockCipher var4) {
      this.agree = var1;
      this.kdf = var2;
      this.mac = var3;
      this.macBuf = new byte[var3.getMacSize()];
      this.cipher = var4;
   }

   public void init(boolean var1, CipherParameters var2, CipherParameters var3, CipherParameters var4) {
      this.forEncryption = var1;
      this.privParam = var2;
      this.pubParam = var3;
      this.V = new byte[0];
      this.extractParams(var4);
   }

   public void init(AsymmetricKeyParameter var1, CipherParameters var2, EphemeralKeyPairGenerator var3) {
      this.forEncryption = true;
      this.pubParam = var1;
      this.keyPairGenerator = var3;
      this.extractParams(var2);
   }

   public void init(AsymmetricKeyParameter var1, CipherParameters var2, KeyParser var3) {
      this.forEncryption = false;
      this.privParam = var1;
      this.keyParser = var3;
      this.extractParams(var2);
   }

   private void extractParams(CipherParameters var1) {
      if (var1 instanceof ParametersWithIV) {
         this.IV = ((ParametersWithIV)var1).getIV();
         this.param = (IESParameters)((ParametersWithIV)var1).getParameters();
      } else {
         this.IV = null;
         this.param = (IESParameters)var1;
      }

   }

   public BufferedBlockCipher getCipher() {
      return this.cipher;
   }

   public Mac getMac() {
      return this.mac;
   }

   private byte[] encryptBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      Object var4 = null;
      Object var5 = null;
      Object var6 = null;
      Object var7 = null;
      int var9;
      byte[] var13;
      byte[] var14;
      byte[] var15;
      byte[] var16;
      if (this.cipher == null) {
         var15 = new byte[var3];
         var16 = new byte[this.param.getMacKeySize() / 8];
         var14 = new byte[var15.length + var16.length];
         this.kdf.generateBytes(var14, 0, var14.length);
         if (this.V.length != 0) {
            System.arraycopy(var14, 0, var16, 0, var16.length);
            System.arraycopy(var14, var16.length, var15, 0, var15.length);
         } else {
            System.arraycopy(var14, 0, var15, 0, var15.length);
            System.arraycopy(var14, var3, var16, 0, var16.length);
         }

         var13 = new byte[var3];

         for(int var8 = 0; var8 != var3; ++var8) {
            var13[var8] = (byte)(var1[var2 + var8] ^ var15[var8]);
         }

         var9 = var3;
      } else {
         var15 = new byte[((IESWithCipherParameters)this.param).getCipherKeySize() / 8];
         var16 = new byte[this.param.getMacKeySize() / 8];
         var14 = new byte[var15.length + var16.length];
         this.kdf.generateBytes(var14, 0, var14.length);
         System.arraycopy(var14, 0, var15, 0, var15.length);
         System.arraycopy(var14, var15.length, var16, 0, var16.length);
         if (this.IV != null) {
            this.cipher.init(true, new ParametersWithIV(new KeyParameter(var15), this.IV));
         } else {
            this.cipher.init(true, new KeyParameter(var15));
         }

         var13 = new byte[this.cipher.getOutputSize(var3)];
         var9 = this.cipher.processBytes(var1, var2, var3, var13, 0);
         var9 += this.cipher.doFinal(var13, var9);
      }

      byte[] var17 = this.param.getEncodingV();
      byte[] var10 = null;
      if (this.V.length != 0) {
         var10 = this.getLengthTag(var17);
      }

      byte[] var11 = new byte[this.mac.getMacSize()];
      this.mac.init(new KeyParameter(var16));
      this.mac.update(var13, 0, var13.length);
      if (var17 != null) {
         this.mac.update(var17, 0, var17.length);
      }

      if (this.V.length != 0) {
         this.mac.update(var10, 0, var10.length);
      }

      this.mac.doFinal(var11, 0);
      byte[] var12 = new byte[this.V.length + var9 + var11.length];
      System.arraycopy(this.V, 0, var12, 0, this.V.length);
      System.arraycopy(var13, 0, var12, this.V.length, var9);
      System.arraycopy(var11, 0, var12, this.V.length + var9, var11.length);
      return var12;
   }

   private byte[] decryptBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      int var4 = 0;
      if (var3 < this.V.length + this.mac.getMacSize()) {
         throw new InvalidCipherTextException("Length of input must be greater than the MAC and V combined");
      } else {
         byte[] var5;
         byte[] var6;
         byte[] var7;
         byte[] var8;
         if (this.cipher == null) {
            var5 = new byte[var3 - this.V.length - this.mac.getMacSize()];
            var6 = new byte[this.param.getMacKeySize() / 8];
            var7 = new byte[var5.length + var6.length];
            this.kdf.generateBytes(var7, 0, var7.length);
            if (this.V.length != 0) {
               System.arraycopy(var7, 0, var6, 0, var6.length);
               System.arraycopy(var7, var6.length, var5, 0, var5.length);
            } else {
               System.arraycopy(var7, 0, var5, 0, var5.length);
               System.arraycopy(var7, var5.length, var6, 0, var6.length);
            }

            var8 = new byte[var5.length];

            for(int var9 = 0; var9 != var5.length; ++var9) {
               var8[var9] = (byte)(var1[var2 + this.V.length + var9] ^ var5[var9]);
            }
         } else {
            var5 = new byte[((IESWithCipherParameters)this.param).getCipherKeySize() / 8];
            var6 = new byte[this.param.getMacKeySize() / 8];
            var7 = new byte[var5.length + var6.length];
            this.kdf.generateBytes(var7, 0, var7.length);
            System.arraycopy(var7, 0, var5, 0, var5.length);
            System.arraycopy(var7, var5.length, var6, 0, var6.length);
            if (this.IV != null) {
               this.cipher.init(false, new ParametersWithIV(new KeyParameter(var5), this.IV));
            } else {
               this.cipher.init(false, new KeyParameter(var5));
            }

            var8 = new byte[this.cipher.getOutputSize(var3 - this.V.length - this.mac.getMacSize())];
            var4 = this.cipher.processBytes(var1, var2 + this.V.length, var3 - this.V.length - this.mac.getMacSize(), var8, 0);
         }

         byte[] var14 = this.param.getEncodingV();
         byte[] var10 = null;
         if (this.V.length != 0) {
            var10 = this.getLengthTag(var14);
         }

         int var11 = var2 + var3;
         byte[] var12 = Arrays.copyOfRange(var1, var11 - this.mac.getMacSize(), var11);
         byte[] var13 = new byte[var12.length];
         this.mac.init(new KeyParameter(var6));
         this.mac.update(var1, var2 + this.V.length, var3 - this.V.length - var13.length);
         if (var14 != null) {
            this.mac.update(var14, 0, var14.length);
         }

         if (this.V.length != 0) {
            this.mac.update(var10, 0, var10.length);
         }

         this.mac.doFinal(var13, 0);
         if (!Arrays.constantTimeAreEqual(var12, var13)) {
            throw new InvalidCipherTextException("invalid MAC");
         } else if (this.cipher == null) {
            return var8;
         } else {
            var4 += this.cipher.doFinal(var8, var4);
            return Arrays.copyOfRange((byte[])var8, 0, var4);
         }
      }
   }

   public byte[] processBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException {
      if (this.forEncryption) {
         if (this.keyPairGenerator != null) {
            EphemeralKeyPair var4 = this.keyPairGenerator.generate();
            this.privParam = var4.getKeyPair().getPrivate();
            this.V = var4.getEncodedPublicKey();
         }
      } else if (this.keyParser != null) {
         ByteArrayInputStream var15 = new ByteArrayInputStream(var1, var2, var3);

         try {
            this.pubParam = this.keyParser.readKey(var15);
         } catch (IOException var13) {
            throw new InvalidCipherTextException("unable to recover ephemeral public key: " + var13.getMessage(), var13);
         } catch (IllegalArgumentException var14) {
            throw new InvalidCipherTextException("unable to recover ephemeral public key: " + var14.getMessage(), var14);
         }

         int var5 = var3 - var15.available();
         this.V = Arrays.copyOfRange(var1, var2, var2 + var5);
      }

      this.agree.init(this.privParam);
      BigInteger var16 = this.agree.calculateAgreement(this.pubParam);
      byte[] var17 = BigIntegers.asUnsignedByteArray(this.agree.getFieldSize(), var16);
      if (this.V.length != 0) {
         byte[] var6 = Arrays.concatenate(this.V, var17);
         Arrays.fill((byte[])var17, (byte)0);
         var17 = var6;
      }

      byte[] var7;
      try {
         KDFParameters var18 = new KDFParameters(var17, this.param.getDerivationV());
         this.kdf.init(var18);
         var7 = this.forEncryption ? this.encryptBlock(var1, var2, var3) : this.decryptBlock(var1, var2, var3);
      } finally {
         Arrays.fill((byte[])var17, (byte)0);
      }

      return var7;
   }

   protected byte[] getLengthTag(byte[] var1) {
      byte[] var2 = new byte[8];
      if (var1 != null) {
         Pack.longToBigEndian((long)var1.length * 8L, var2, 0);
      }

      return var2;
   }
}
