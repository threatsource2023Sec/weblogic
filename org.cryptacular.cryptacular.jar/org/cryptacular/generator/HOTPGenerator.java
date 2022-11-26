package org.cryptacular.generator;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;

public class HOTPGenerator extends AbstractOTPGenerator {
   public int generate(byte[] key, long count) {
      return this.generateInternal(key, count);
   }

   protected Digest getDigest() {
      return new SHA1Digest();
   }
}
