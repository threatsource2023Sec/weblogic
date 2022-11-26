package org.cryptacular.generator;

import java.security.SecureRandom;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.prng.SP800SecureRandomBuilder;
import org.cryptacular.util.NonceUtil;

public final class SecretKeyGenerator {
   private SecretKeyGenerator() {
   }

   public static SecretKey generate(BlockCipher cipher) {
      return generate(cipher.getBlockSize() * 8, cipher);
   }

   public static SecretKey generate(int bitLength, BlockCipher cipher) {
      byte[] nonce = NonceUtil.randomNonce((bitLength + 7) / 8);
      return generate(bitLength, cipher, (new SP800SecureRandomBuilder()).buildHash(new SHA256Digest(), nonce, false));
   }

   public static SecretKey generate(int bitLength, BlockCipher cipher, SecureRandom random) {
      byte[] key = new byte[(bitLength + 7) / 8];
      random.nextBytes(key);
      return new SecretKeySpec(key, cipher.getAlgorithmName());
   }
}
