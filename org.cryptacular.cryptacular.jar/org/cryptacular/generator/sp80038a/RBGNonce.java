package org.cryptacular.generator.sp80038a;

import org.bouncycastle.crypto.prng.drbg.SP80090DRBG;
import org.cryptacular.generator.LimitException;
import org.cryptacular.generator.Nonce;
import org.cryptacular.util.NonceUtil;

public class RBGNonce implements Nonce {
   private final int length;
   private final SP80090DRBG rbg;

   public RBGNonce() {
      this(16);
   }

   public RBGNonce(int length) {
      if (length < 1) {
         throw new IllegalArgumentException("Length must be positive");
      } else {
         this.length = length;
         this.rbg = NonceUtil.newRBG(length);
      }
   }

   public byte[] generate() throws LimitException {
      byte[] random = new byte[this.length];
      synchronized(this.rbg) {
         this.rbg.generate(random, (byte[])null, false);
         return random;
      }
   }

   public int getLength() {
      return this.length;
   }
}
