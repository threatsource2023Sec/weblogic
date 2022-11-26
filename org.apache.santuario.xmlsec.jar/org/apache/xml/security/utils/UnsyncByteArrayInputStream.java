package org.apache.xml.security.utils;

import java.io.IOException;
import java.io.InputStream;

public class UnsyncByteArrayInputStream extends InputStream {
   protected byte[] buf;
   protected int pos;
   protected int mark;
   protected int count;

   public UnsyncByteArrayInputStream(byte[] buf) {
      this.mark = 0;
      this.buf = buf;
      this.count = buf.length;
   }

   public UnsyncByteArrayInputStream(byte[] buf, int offset, int length) {
      this.buf = buf;
      this.pos = offset;
      this.mark = offset;
      this.count = offset + length > buf.length ? buf.length : offset + length;
   }

   public int available() {
      return this.count - this.pos;
   }

   public void close() throws IOException {
   }

   public void mark(int readlimit) {
      this.mark = this.pos;
   }

   public boolean markSupported() {
      return true;
   }

   public int read() {
      return this.pos < this.count ? this.buf[this.pos++] & 255 : -1;
   }

   public int read(byte[] b, int offset, int length) {
      if (b == null) {
         throw new NullPointerException();
      } else if (offset >= 0 && offset <= b.length && length >= 0 && length <= b.length - offset) {
         if (this.pos >= this.count) {
            return -1;
         } else if (length == 0) {
            return 0;
         } else {
            int copylen = this.count - this.pos < length ? this.count - this.pos : length;
            System.arraycopy(this.buf, this.pos, b, offset, copylen);
            this.pos += copylen;
            return copylen;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void reset() {
      this.pos = this.mark;
   }

   public long skip(long n) {
      if (n <= 0L) {
         return 0L;
      } else {
         int temp = this.pos;
         this.pos = (long)(this.count - this.pos) < n ? this.count : (int)((long)this.pos + n);
         return (long)(this.pos - temp);
      }
   }
}
