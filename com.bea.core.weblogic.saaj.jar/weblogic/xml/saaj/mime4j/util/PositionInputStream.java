package weblogic.xml.saaj.mime4j.util;

import java.io.IOException;
import java.io.InputStream;

public class PositionInputStream extends InputStream {
   private final InputStream inputStream;
   protected long position = 0L;
   private long markedPosition = 0L;

   public PositionInputStream(InputStream inputStream) {
      this.inputStream = inputStream;
   }

   public long getPosition() {
      return this.position;
   }

   public int available() throws IOException {
      return this.inputStream.available();
   }

   public int read() throws IOException {
      int b = this.inputStream.read();
      if (b != -1) {
         ++this.position;
      }

      return b;
   }

   public void close() throws IOException {
      this.inputStream.close();
   }

   public void reset() throws IOException {
      this.inputStream.reset();
      this.position = this.markedPosition;
   }

   public boolean markSupported() {
      return this.inputStream.markSupported();
   }

   public void mark(int readlimit) {
      this.inputStream.mark(readlimit);
      this.markedPosition = this.position;
   }

   public long skip(long n) throws IOException {
      long c = this.inputStream.skip(n);
      this.position += c;
      return c;
   }

   public int read(byte[] b) throws IOException {
      int c = this.inputStream.read(b);
      this.position += (long)c;
      return c;
   }

   public int read(byte[] b, int off, int len) throws IOException {
      int c = this.inputStream.read(b, off, len);
      this.position += (long)c;
      return c;
   }
}
