package org.python.apache.commons.compress.compressors.brotli;

import java.io.IOException;
import java.io.InputStream;
import org.brotli.dec.BrotliInputStream;
import org.python.apache.commons.compress.compressors.CompressorInputStream;

public class BrotliCompressorInputStream extends CompressorInputStream {
   private final BrotliInputStream decIS;

   public BrotliCompressorInputStream(InputStream in) throws IOException {
      this.decIS = new BrotliInputStream(in);
   }

   public int available() throws IOException {
      return this.decIS.available();
   }

   public void close() throws IOException {
      this.decIS.close();
   }

   public int read(byte[] b) throws IOException {
      return this.decIS.read(b);
   }

   public long skip(long n) throws IOException {
      return this.decIS.skip(n);
   }

   public void mark(int readlimit) {
      this.decIS.mark(readlimit);
   }

   public boolean markSupported() {
      return this.decIS.markSupported();
   }

   public int read() throws IOException {
      int ret = this.decIS.read();
      this.count(ret == -1 ? 0 : 1);
      return ret;
   }

   public int read(byte[] buf, int off, int len) throws IOException {
      int ret = this.decIS.read(buf, off, len);
      this.count(ret);
      return ret;
   }

   public String toString() {
      return this.decIS.toString();
   }

   public void reset() throws IOException {
      this.decIS.reset();
   }
}
