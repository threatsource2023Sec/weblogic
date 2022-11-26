package weblogic.xml.saaj.mime4j.util;

import java.io.IOException;
import java.io.InputStream;

public class PartialInputStream extends PositionInputStream {
   private final long limit;

   public PartialInputStream(InputStream inputStream, long offset, long length) throws IOException {
      super(inputStream);
      inputStream.skip(offset);
      this.limit = offset + length;
   }

   public int available() throws IOException {
      return Math.min(super.available(), this.getBytesLeft());
   }

   public int read() throws IOException {
      return this.limit > this.position ? super.read() : -1;
   }

   public int read(byte[] b) throws IOException {
      return this.read(b, 0, b.length);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      len = Math.min(len, this.getBytesLeft());
      return super.read(b, off, len);
   }

   public long skip(long n) throws IOException {
      n = Math.min(n, (long)this.getBytesLeft());
      return super.skip(n);
   }

   private int getBytesLeft() {
      return (int)Math.min(2147483647L, this.limit - this.position);
   }
}
