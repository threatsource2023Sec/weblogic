package weblogic.xml.saaj.mime4j;

import java.io.IOException;
import java.io.InputStream;

public class CloseShieldInputStream extends InputStream {
   private InputStream is;

   public CloseShieldInputStream(InputStream is) {
      this.is = is;
   }

   public InputStream getUnderlyingStream() {
      return this.is;
   }

   public int read() throws IOException {
      this.checkIfClosed();
      return this.is.read();
   }

   public int available() throws IOException {
      this.checkIfClosed();
      return this.is.available();
   }

   public void close() throws IOException {
      this.is = null;
   }

   public synchronized void reset() throws IOException {
      this.checkIfClosed();
      this.is.reset();
   }

   public boolean markSupported() {
      return this.is == null ? false : this.is.markSupported();
   }

   public synchronized void mark(int readlimit) {
      if (this.is != null) {
         this.is.mark(readlimit);
      }

   }

   public long skip(long n) throws IOException {
      this.checkIfClosed();
      return this.is.skip(n);
   }

   public int read(byte[] b) throws IOException {
      this.checkIfClosed();
      return this.is.read(b);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      this.checkIfClosed();
      return this.is.read(b, off, len);
   }

   private void checkIfClosed() throws IOException {
      if (this.is == null) {
         throw new IOException("Stream is closed");
      }
   }
}
