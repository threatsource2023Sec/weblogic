package org.apache.xml.security.stax.impl.util;

import java.io.IOException;
import java.io.InputStream;

public class MultiInputStream extends InputStream {
   private final InputStream[] inputStreams;
   private final int inputStreamCount;
   private int inputStreamIndex;

   public MultiInputStream(InputStream... inputStreams) {
      this.inputStreams = inputStreams;
      this.inputStreamCount = inputStreams.length;
   }

   public int read() throws IOException {
      for(int i = this.inputStreamIndex; i < this.inputStreamCount; ++i) {
         int b = this.inputStreams[i].read();
         if (b >= 0) {
            return b;
         }

         ++this.inputStreamIndex;
      }

      return -1;
   }

   public int read(byte[] b) throws IOException {
      return this.read(b, 0, b.length);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      for(int i = this.inputStreamIndex; i < this.inputStreamCount; ++i) {
         int read = this.inputStreams[i].read(b, off, len);
         if (read >= 0) {
            return read;
         }

         ++this.inputStreamIndex;
      }

      return -1;
   }

   public long skip(long n) throws IOException {
      throw new IOException("skip() not supported");
   }

   public int available() throws IOException {
      return this.inputStreamIndex < this.inputStreamCount ? this.inputStreams[this.inputStreamIndex].available() : 0;
   }

   public void close() throws IOException {
      for(int i = 0; i < this.inputStreamCount; ++i) {
         try {
            this.inputStreams[i].close();
         } catch (IOException var3) {
         }
      }

   }
}
