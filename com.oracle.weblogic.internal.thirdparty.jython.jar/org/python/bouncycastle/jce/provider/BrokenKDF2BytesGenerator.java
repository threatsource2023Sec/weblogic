package org.python.bouncycastle.jce.provider;

import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.DerivationFunction;
import org.python.bouncycastle.crypto.DerivationParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.params.KDFParameters;

public class BrokenKDF2BytesGenerator implements DerivationFunction {
   private Digest digest;
   private byte[] shared;
   private byte[] iv;

   public BrokenKDF2BytesGenerator(Digest var1) {
      this.digest = var1;
   }

   public void init(DerivationParameters var1) {
      if (!(var1 instanceof KDFParameters)) {
         throw new IllegalArgumentException("KDF parameters required for generator");
      } else {
         KDFParameters var2 = (KDFParameters)var1;
         this.shared = var2.getSharedSecret();
         this.iv = var2.getIV();
      }
   }

   public Digest getDigest() {
      return this.digest;
   }

   public int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException {
      if (var1.length - var3 < var2) {
         throw new DataLengthException("output buffer too small");
      } else {
         long var4 = (long)var3 * 8L;
         if (var4 > (long)this.digest.getDigestSize() * 8L * 2147483648L) {
            new IllegalArgumentException("Output length to large");
         }

         int var6 = (int)(var4 / (long)this.digest.getDigestSize());
         Object var7 = null;
         byte[] var9 = new byte[this.digest.getDigestSize()];

         for(int var8 = 1; var8 <= var6; ++var8) {
            this.digest.update(this.shared, 0, this.shared.length);
            this.digest.update((byte)(var8 & 255));
            this.digest.update((byte)(var8 >> 8 & 255));
            this.digest.update((byte)(var8 >> 16 & 255));
            this.digest.update((byte)(var8 >> 24 & 255));
            this.digest.update(this.iv, 0, this.iv.length);
            this.digest.doFinal(var9, 0);
            if (var3 - var2 > var9.length) {
               System.arraycopy(var9, 0, var1, var2, var9.length);
               var2 += var9.length;
            } else {
               System.arraycopy(var9, 0, var1, var2, var3 - var2);
            }
         }

         this.digest.reset();
         return var3;
      }
   }
}
