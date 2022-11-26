package org.python.apache.commons.compress.compressors.lzma;

import java.io.IOException;
import java.io.InputStream;
import org.python.apache.commons.compress.compressors.CompressorInputStream;
import org.tukaani.xz.LZMAInputStream;
import org.tukaani.xz.MemoryLimitException;

public class LZMACompressorInputStream extends CompressorInputStream {
   private final InputStream in;

   public LZMACompressorInputStream(InputStream inputStream) throws IOException {
      this.in = new LZMAInputStream(inputStream, -1);
   }

   public LZMACompressorInputStream(InputStream inputStream, int memoryLimitInKb) throws IOException {
      try {
         this.in = new LZMAInputStream(inputStream, memoryLimitInKb);
      } catch (MemoryLimitException var4) {
         throw new org.python.apache.commons.compress.MemoryLimitException((long)var4.getMemoryNeeded(), var4.getMemoryLimit(), var4);
      }
   }

   public int read() throws IOException {
      int ret = this.in.read();
      this.count(ret == -1 ? 0 : 1);
      return ret;
   }

   public int read(byte[] buf, int off, int len) throws IOException {
      int ret = this.in.read(buf, off, len);
      this.count(ret);
      return ret;
   }

   public long skip(long n) throws IOException {
      return this.in.skip(n);
   }

   public int available() throws IOException {
      return this.in.available();
   }

   public void close() throws IOException {
      this.in.close();
   }

   public static boolean matches(byte[] signature, int length) {
      if (signature != null && length >= 3) {
         if (signature[0] != 93) {
            return false;
         } else if (signature[1] != 0) {
            return false;
         } else {
            return signature[2] == 0;
         }
      } else {
         return false;
      }
   }
}
