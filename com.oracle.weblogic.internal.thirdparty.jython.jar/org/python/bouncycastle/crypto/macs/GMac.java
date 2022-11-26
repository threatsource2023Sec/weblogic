package org.python.bouncycastle.crypto.macs;

import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.InvalidCipherTextException;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.modes.GCMBlockCipher;
import org.python.bouncycastle.crypto.params.AEADParameters;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.crypto.params.ParametersWithIV;

public class GMac implements Mac {
   private final GCMBlockCipher cipher;
   private final int macSizeBits;

   public GMac(GCMBlockCipher var1) {
      this.cipher = var1;
      this.macSizeBits = 128;
   }

   public GMac(GCMBlockCipher var1, int var2) {
      this.cipher = var1;
      this.macSizeBits = var2;
   }

   public void init(CipherParameters var1) throws IllegalArgumentException {
      if (var1 instanceof ParametersWithIV) {
         ParametersWithIV var2 = (ParametersWithIV)var1;
         byte[] var3 = var2.getIV();
         KeyParameter var4 = (KeyParameter)var2.getParameters();
         this.cipher.init(true, new AEADParameters(var4, this.macSizeBits, var3));
      } else {
         throw new IllegalArgumentException("GMAC requires ParametersWithIV");
      }
   }

   public String getAlgorithmName() {
      return this.cipher.getUnderlyingCipher().getAlgorithmName() + "-GMAC";
   }

   public int getMacSize() {
      return this.macSizeBits / 8;
   }

   public void update(byte var1) throws IllegalStateException {
      this.cipher.processAADByte(var1);
   }

   public void update(byte[] var1, int var2, int var3) throws DataLengthException, IllegalStateException {
      this.cipher.processAADBytes(var1, var2, var3);
   }

   public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException {
      try {
         return this.cipher.doFinal(var1, var2);
      } catch (InvalidCipherTextException var4) {
         throw new IllegalStateException(var4.toString());
      }
   }

   public void reset() {
      this.cipher.reset();
   }
}
