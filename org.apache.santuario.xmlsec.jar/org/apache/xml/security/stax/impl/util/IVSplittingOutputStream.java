package org.apache.xml.security.stax.impl.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import org.apache.xml.security.encryption.XMLCipherUtil;

public class IVSplittingOutputStream extends FilterOutputStream {
   private ReplaceableOuputStream replaceableOuputStream;
   private final byte[] iv;
   private final int ivLength;
   private int pos;
   private final Cipher cipher;
   private final Key secretKey;

   public IVSplittingOutputStream(OutputStream out, Cipher cipher, Key secretKey, int ivLength) {
      super(out);
      this.ivLength = ivLength;
      this.iv = new byte[ivLength];
      this.cipher = cipher;
      this.secretKey = secretKey;
   }

   public byte[] getIv() {
      return this.iv;
   }

   public boolean isIVComplete() {
      return this.pos == this.iv.length;
   }

   private void initializeCipher() throws IOException {
      AlgorithmParameterSpec iv = XMLCipherUtil.constructBlockCipherParameters(this.cipher.getAlgorithm().toUpperCase().contains("GCM"), this.getIv(), this.getClass());

      try {
         this.cipher.init(2, this.secretKey, iv);
      } catch (InvalidKeyException var3) {
         throw new IOException(var3);
      } catch (InvalidAlgorithmParameterException var4) {
         throw new IOException(var4);
      }
   }

   public void write(int b) throws IOException {
      if (this.pos >= this.ivLength) {
         this.initializeCipher();
         this.out.write(b);
         this.replaceableOuputStream.setNewOutputStream(this.out);
      } else {
         this.iv[this.pos++] = (byte)b;
      }
   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      int missingBytes = this.ivLength - this.pos;
      if (missingBytes > len) {
         System.arraycopy(b, off, this.iv, this.pos, len);
         this.pos += len;
      } else {
         System.arraycopy(b, off, this.iv, this.pos, missingBytes);
         this.pos += missingBytes;
         this.initializeCipher();
         this.out.write(b, off + missingBytes, len - missingBytes);
         this.replaceableOuputStream.setNewOutputStream(this.out);
      }

   }

   public void setParentOutputStream(ReplaceableOuputStream replaceableOuputStream) {
      this.replaceableOuputStream = replaceableOuputStream;
   }
}
