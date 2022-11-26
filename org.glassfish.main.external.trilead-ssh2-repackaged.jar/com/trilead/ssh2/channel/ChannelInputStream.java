package com.trilead.ssh2.channel;

import java.io.IOException;
import java.io.InputStream;

public final class ChannelInputStream extends InputStream {
   Channel c;
   boolean isClosed = false;
   boolean isEOF = false;
   boolean extendedFlag = false;

   ChannelInputStream(Channel c, boolean isExtended) {
      this.c = c;
      this.extendedFlag = isExtended;
   }

   public int available() throws IOException {
      if (this.isEOF) {
         return 0;
      } else {
         int avail = this.c.cm.getAvailable(this.c, this.extendedFlag);
         return avail > 0 ? avail : 0;
      }
   }

   public void close() throws IOException {
      this.isClosed = true;
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (b == null) {
         throw new NullPointerException();
      } else if (off >= 0 && len >= 0 && off + len <= b.length && off + len >= 0 && off <= b.length) {
         if (len == 0) {
            return 0;
         } else if (this.isEOF) {
            return -1;
         } else {
            int ret = this.c.cm.getChannelData(this.c, this.extendedFlag, b, off, len);
            if (ret == -1) {
               this.isEOF = true;
            }

            return ret;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int read(byte[] b) throws IOException {
      return this.read(b, 0, b.length);
   }

   public int read() throws IOException {
      byte[] b = new byte[1];
      int ret = this.read(b, 0, 1);
      return ret != 1 ? -1 : b[0] & 255;
   }
}
