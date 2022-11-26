package org.python.apache.commons.compress.compressors.deflate;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.python.apache.commons.compress.compressors.CompressorInputStream;

public class DeflateCompressorInputStream extends CompressorInputStream {
   private static final int MAGIC_1 = 120;
   private static final int MAGIC_2a = 1;
   private static final int MAGIC_2b = 94;
   private static final int MAGIC_2c = 156;
   private static final int MAGIC_2d = 218;
   private final InputStream in;
   private final Inflater inflater;

   public DeflateCompressorInputStream(InputStream inputStream) {
      this(inputStream, new DeflateParameters());
   }

   public DeflateCompressorInputStream(InputStream inputStream, DeflateParameters parameters) {
      this.inflater = new Inflater(!parameters.withZlibHeader());
      this.in = new InflaterInputStream(inputStream, this.inflater);
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
      try {
         this.in.close();
      } finally {
         this.inflater.end();
      }

   }

   public static boolean matches(byte[] signature, int length) {
      return length > 3 && signature[0] == 120 && (signature[1] == 1 || signature[1] == 94 || signature[1] == -100 || signature[1] == -38);
   }
}
