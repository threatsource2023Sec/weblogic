package org.cryptacular.generator;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.cryptacular.util.ByteUtil;

public abstract class AbstractOTPGenerator {
   private static final int[] MODULUS = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
   private int numberOfDigits = 6;

   public int getNumberOfDigits() {
      return this.numberOfDigits;
   }

   public void setNumberOfDigits(int digits) {
      if (digits >= 6 && digits <= 9) {
         this.numberOfDigits = digits;
      } else {
         throw new IllegalArgumentException("Number of generated digits must be in range 6-9.");
      }
   }

   protected int generateInternal(byte[] key, long count) {
      HMac hmac = new HMac(this.getDigest());
      byte[] output = new byte[hmac.getMacSize()];
      hmac.init(new KeyParameter(key));
      hmac.update(ByteUtil.toBytes(count), 0, 8);
      hmac.doFinal(output, 0);
      return this.truncate(output) % MODULUS[this.numberOfDigits];
   }

   protected abstract Digest getDigest();

   private int truncate(byte[] hmac) {
      int offset = hmac[19] & 15;
      return (hmac[offset] & 127) << 24 | (hmac[offset + 1] & 255) << 16 | (hmac[offset + 2] & 255) << 8 | hmac[offset + 3] & 255;
   }
}
