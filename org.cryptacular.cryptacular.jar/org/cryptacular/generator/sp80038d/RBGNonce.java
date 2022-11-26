package org.cryptacular.generator.sp80038d;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.prng.drbg.HashSP800DRBG;
import org.bouncycastle.crypto.prng.drbg.SP80090DRBG;
import org.cryptacular.generator.LimitException;
import org.cryptacular.generator.Nonce;
import org.cryptacular.util.ByteUtil;
import org.cryptacular.util.NonceUtil;

public class RBGNonce implements Nonce {
   private final byte[] fixed;
   private final int randomLength;
   private final SP80090DRBG rbg;

   public RBGNonce() {
      this(12);
   }

   public RBGNonce(int randomLength) {
      this((String)null, randomLength);
   }

   public RBGNonce(String fixed, int randomLength) {
      if (randomLength < 12) {
         throw new IllegalArgumentException("Must specify at least 12 bytes (96 bits) for random part.");
      } else {
         this.randomLength = randomLength;
         if (fixed != null) {
            this.fixed = ByteUtil.toBytes(fixed);
         } else {
            this.fixed = new byte[0];
         }

         this.rbg = newRBG(this.randomLength, this.fixed);
      }
   }

   public byte[] generate() throws LimitException {
      byte[] random = new byte[this.randomLength];
      synchronized(this.rbg) {
         this.rbg.generate(random, (byte[])null, false);
      }

      byte[] value = new byte[this.getLength()];
      System.arraycopy(this.fixed, 0, value, 0, this.fixed.length);
      System.arraycopy(random, 0, value, this.fixed.length, random.length);
      return value;
   }

   public int getLength() {
      return this.fixed.length + this.randomLength;
   }

   private static SP80090DRBG newRBG(int length, byte[] domain) {
      return new HashSP800DRBG(new SHA256Digest(), length, NonceUtil.randomEntropySource(length), domain, NonceUtil.timestampNonce(8));
   }
}
