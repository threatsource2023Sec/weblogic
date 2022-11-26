package org.cryptacular.pbe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.io.CipherOutputStream;
import org.bouncycastle.util.io.Streams;
import org.cryptacular.CryptoException;

public abstract class AbstractEncryptionScheme implements EncryptionScheme {
   private BufferedBlockCipher cipher;
   private CipherParameters parameters;

   public byte[] encrypt(byte[] plaintext) {
      this.cipher.init(true, this.parameters);
      return this.process(plaintext);
   }

   public void encrypt(InputStream in, OutputStream out) throws IOException {
      this.cipher.init(true, this.parameters);
      Streams.pipeAll(in, new CipherOutputStream(out, this.cipher));
   }

   public byte[] decrypt(byte[] ciphertext) {
      this.cipher.init(false, this.parameters);
      return this.process(ciphertext);
   }

   public void decrypt(InputStream in, OutputStream out) throws IOException {
      this.cipher.init(false, this.parameters);
      Streams.pipeAll(new CipherInputStream(in, this.cipher), out);
   }

   protected void setCipher(BufferedBlockCipher bufferedBlockCipher) {
      if (bufferedBlockCipher == null) {
         throw new IllegalArgumentException("Block cipher cannot be null");
      } else {
         this.cipher = bufferedBlockCipher;
      }
   }

   protected void setCipherParameters(CipherParameters parameters) {
      if (parameters == null) {
         throw new IllegalArgumentException("Cipher parameters cannot be null");
      } else {
         this.parameters = parameters;
      }
   }

   private byte[] process(byte[] input) {
      byte[] output = new byte[this.cipher.getOutputSize(input.length)];
      int processed = this.cipher.processBytes(input, 0, input.length, output, 0);

      try {
         processed += this.cipher.doFinal(output, processed);
      } catch (InvalidCipherTextException var5) {
         throw new CryptoException("Cipher error", var5);
      }

      return Arrays.copyOfRange(output, 0, processed);
   }
}
