package org.cryptacular.generator.sp80038a;

import javax.crypto.SecretKey;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.cryptacular.generator.LimitException;
import org.cryptacular.generator.Nonce;
import org.cryptacular.spec.Spec;
import org.cryptacular.util.NonceUtil;

public class EncryptedNonce implements Nonce {
   private final BlockCipher cipher;
   private final SecretKey key;

   public EncryptedNonce(Spec cipherSpec, SecretKey key) {
      this((BlockCipher)cipherSpec.newInstance(), key);
   }

   public EncryptedNonce(BlockCipher cipher, SecretKey key) {
      this.cipher = cipher;
      this.key = key;
   }

   public byte[] generate() throws LimitException {
      byte[] result = new byte[this.cipher.getBlockSize()];
      byte[] nonce = NonceUtil.randomNonce(result.length);
      synchronized(this.cipher) {
         this.cipher.init(true, new KeyParameter(this.key.getEncoded()));
         this.cipher.processBlock(nonce, 0, result, 0);
         this.cipher.reset();
         return result;
      }
   }

   public int getLength() {
      return this.cipher.getBlockSize();
   }
}
