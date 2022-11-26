package org.cryptacular.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import org.cryptacular.CiphertextHeader;
import org.cryptacular.StreamException;
import org.cryptacular.adapter.BlockCipherAdapter;
import org.cryptacular.generator.Nonce;
import org.cryptacular.util.StreamUtil;

public abstract class AbstractBlockCipherBean extends AbstractCipherBean {
   public AbstractBlockCipherBean() {
   }

   public AbstractBlockCipherBean(KeyStore keyStore, String keyAlias, String keyPassword, Nonce nonce) {
      super(keyStore, keyAlias, keyPassword, nonce);
   }

   protected byte[] process(CiphertextHeader header, boolean mode, byte[] input) {
      BlockCipherAdapter cipher = this.newCipher(header, mode);
      byte[] headerBytes = header.encode();
      int outOff;
      int inOff;
      int length;
      byte[] output;
      int outSize;
      if (mode) {
         outSize = headerBytes.length + cipher.getOutputSize(input.length);
         output = new byte[outSize];
         System.arraycopy(headerBytes, 0, output, 0, headerBytes.length);
         inOff = 0;
         outOff = headerBytes.length;
         length = input.length;
      } else {
         length = input.length - headerBytes.length;
         outSize = cipher.getOutputSize(length);
         output = new byte[outSize];
         inOff = headerBytes.length;
         outOff = 0;
      }

      outOff += cipher.processBytes(input, inOff, length, output, outOff);
      outOff += cipher.doFinal(output, outOff);
      if (outOff < output.length) {
         byte[] copy = new byte[outOff];
         System.arraycopy(output, 0, copy, 0, outOff);
         return copy;
      } else {
         return output;
      }
   }

   protected void process(CiphertextHeader header, boolean mode, InputStream input, OutputStream output) {
      BlockCipherAdapter cipher = this.newCipher(header, mode);
      int outSize = cipher.getOutputSize(1024);
      byte[] outBuf = new byte[outSize > 1024 ? outSize : 1024];
      StreamUtil.pipeAll(input, output, (in, inOff, len, out) -> {
         int n = cipher.processBytes(in, inOff, len, outBuf, 0);
         out.write(outBuf, 0, n);
      });
      int n = cipher.doFinal(outBuf, 0);

      try {
         output.write(outBuf, 0, n);
      } catch (IOException var10) {
         throw new StreamException(var10);
      }
   }

   protected abstract BlockCipherAdapter newCipher(CiphertextHeader var1, boolean var2);
}
