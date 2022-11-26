package org.python.bouncycastle.pqc.jcajce.provider.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.ShortBufferException;

public abstract class AsymmetricHybridCipher extends CipherSpiExt {
   protected AlgorithmParameterSpec paramSpec;

   protected final void setMode(String var1) {
   }

   protected final void setPadding(String var1) {
   }

   public final byte[] getIV() {
      return null;
   }

   public final int getBlockSize() {
      return 0;
   }

   public final AlgorithmParameterSpec getParameters() {
      return this.paramSpec;
   }

   public final int getOutputSize(int var1) {
      return this.opMode == 1 ? this.encryptOutputSize(var1) : this.decryptOutputSize(var1);
   }

   public final void initEncrypt(Key var1) throws InvalidKeyException {
      try {
         this.initEncrypt(var1, (AlgorithmParameterSpec)null, new SecureRandom());
      } catch (InvalidAlgorithmParameterException var3) {
         throw new InvalidParameterException("This cipher needs algorithm parameters for initialization (cannot be null).");
      }
   }

   public final void initEncrypt(Key var1, SecureRandom var2) throws InvalidKeyException {
      try {
         this.initEncrypt(var1, (AlgorithmParameterSpec)null, var2);
      } catch (InvalidAlgorithmParameterException var4) {
         throw new InvalidParameterException("This cipher needs algorithm parameters for initialization (cannot be null).");
      }
   }

   public final void initEncrypt(Key var1, AlgorithmParameterSpec var2) throws InvalidKeyException, InvalidAlgorithmParameterException {
      this.initEncrypt(var1, var2, new SecureRandom());
   }

   public final void initEncrypt(Key var1, AlgorithmParameterSpec var2, SecureRandom var3) throws InvalidKeyException, InvalidAlgorithmParameterException {
      this.opMode = 1;
      this.initCipherEncrypt(var1, var2, var3);
   }

   public final void initDecrypt(Key var1) throws InvalidKeyException {
      try {
         this.initDecrypt(var1, (AlgorithmParameterSpec)null);
      } catch (InvalidAlgorithmParameterException var3) {
         throw new InvalidParameterException("This cipher needs algorithm parameters for initialization (cannot be null).");
      }
   }

   public final void initDecrypt(Key var1, AlgorithmParameterSpec var2) throws InvalidKeyException, InvalidAlgorithmParameterException {
      this.opMode = 2;
      this.initCipherDecrypt(var1, var2);
   }

   public abstract byte[] update(byte[] var1, int var2, int var3);

   public final int update(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ShortBufferException {
      if (var4.length < this.getOutputSize(var3)) {
         throw new ShortBufferException("output");
      } else {
         byte[] var6 = this.update(var1, var2, var3);
         System.arraycopy(var6, 0, var4, var5, var6.length);
         return var6.length;
      }
   }

   public abstract byte[] doFinal(byte[] var1, int var2, int var3) throws BadPaddingException;

   public final int doFinal(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ShortBufferException, BadPaddingException {
      if (var4.length < this.getOutputSize(var3)) {
         throw new ShortBufferException("Output buffer too short.");
      } else {
         byte[] var6 = this.doFinal(var1, var2, var3);
         System.arraycopy(var6, 0, var4, var5, var6.length);
         return var6.length;
      }
   }

   protected abstract int encryptOutputSize(int var1);

   protected abstract int decryptOutputSize(int var1);

   protected abstract void initCipherEncrypt(Key var1, AlgorithmParameterSpec var2, SecureRandom var3) throws InvalidKeyException, InvalidAlgorithmParameterException;

   protected abstract void initCipherDecrypt(Key var1, AlgorithmParameterSpec var2) throws InvalidKeyException, InvalidAlgorithmParameterException;
}
