package weblogic.diagnostics.image;

import java.io.IOException;
import java.io.OutputStream;

public class ImageSourceOutputStream extends OutputStream {
   private OutputStream out;
   private boolean streamExpired = false;

   public ImageSourceOutputStream(OutputStream stream) {
      this.out = stream;
   }

   public void close() {
      this.streamExpired = true;
   }

   public void flush() throws IOException {
      this.checkExpired();
      synchronized(this.out) {
         this.out.flush();
      }
   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.checkExpired();
      synchronized(this.out) {
         this.out.write(b, off, len);
      }
   }

   public void write(int b) throws IOException {
      this.checkExpired();
      synchronized(this.out) {
         this.out.write(b);
      }
   }

   private void checkExpired() throws IOException {
      if (this.streamExpired) {
         throw new IOException("Stream no longer writable.");
      }
   }
}
