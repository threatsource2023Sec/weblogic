package org.apache.xml.security.stax.impl.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.xml.security.utils.I18n;

public class LimitingInputStream extends FilterInputStream {
   private long limit;
   private long count;

   public LimitingInputStream(InputStream in, long limit) {
      super(in);
      this.limit = limit;
   }

   public int read() throws IOException {
      int r = super.read();
      if (r >= 0) {
         this.incrementCountAndTestLimit((long)r);
      }

      return r;
   }

   public int read(byte[] b) throws IOException {
      return this.read(b, 0, b.length);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      int r = super.read(b, off, len);
      if (r >= 0) {
         this.incrementCountAndTestLimit((long)r);
      }

      return r;
   }

   private void incrementCountAndTestLimit(long read) throws IOException {
      this.count += read;
      if (this.count > this.limit) {
         throw new IOException(I18n.getExceptionMessage("secureProcessing.inputStreamLimitReached", new Object[]{this.limit}));
      }
   }
}
