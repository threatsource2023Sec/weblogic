package org.python.bouncycastle.crypto.generators;

import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.DerivationFunction;
import org.python.bouncycastle.crypto.DerivationParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.params.MGFParameters;

public class MGF1BytesGenerator implements DerivationFunction {
   private Digest digest;
   private byte[] seed;
   private int hLen;

   public MGF1BytesGenerator(Digest var1) {
      this.digest = var1;
      this.hLen = var1.getDigestSize();
   }

   public void init(DerivationParameters var1) {
      if (!(var1 instanceof MGFParameters)) {
         throw new IllegalArgumentException("MGF parameters required for MGF1Generator");
      } else {
         MGFParameters var2 = (MGFParameters)var1;
         this.seed = var2.getSeed();
      }
   }

   public Digest getDigest() {
      return this.digest;
   }

   private void ItoOSP(int var1, byte[] var2) {
      var2[0] = (byte)(var1 >>> 24);
      var2[1] = (byte)(var1 >>> 16);
      var2[2] = (byte)(var1 >>> 8);
      var2[3] = (byte)(var1 >>> 0);
   }

   public int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException {
      if (var1.length - var3 < var2) {
         throw new DataLengthException("output buffer too small");
      } else {
         byte[] var4 = new byte[this.hLen];
         byte[] var5 = new byte[4];
         int var6 = 0;
         this.digest.reset();
         if (var3 > this.hLen) {
            do {
               this.ItoOSP(var6, var5);
               this.digest.update(this.seed, 0, this.seed.length);
               this.digest.update(var5, 0, var5.length);
               this.digest.doFinal(var4, 0);
               System.arraycopy(var4, 0, var1, var2 + var6 * this.hLen, this.hLen);
               ++var6;
            } while(var6 < var3 / this.hLen);
         }

         if (var6 * this.hLen < var3) {
            this.ItoOSP(var6, var5);
            this.digest.update(this.seed, 0, this.seed.length);
            this.digest.update(var5, 0, var5.length);
            this.digest.doFinal(var4, 0);
            System.arraycopy(var4, 0, var1, var2 + var6 * this.hLen, var3 - var6 * this.hLen);
         }

         return var3;
      }
   }
}
