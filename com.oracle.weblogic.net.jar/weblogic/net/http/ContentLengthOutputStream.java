package weblogic.net.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ProtocolException;

/** @deprecated */
@Deprecated
public final class ContentLengthOutputStream extends OutputStream {
   private boolean closed;
   private int count;
   private int clen;
   private OutputStream os;

   public ContentLengthOutputStream(OutputStream o, int contentLength) {
      this.os = o;
      this.clen = contentLength;
      this.closed = false;
      this.count = 0;
   }

   public synchronized void write(int i) throws IOException {
      if (this.closed) {
         throw new IOException("stream is closed");
      } else if (this.count + 1 > this.clen) {
         throw new ProtocolException("Exceeding stated content length of " + this.clen);
      } else {
         this.os.write(i);
         ++this.count;
      }
   }

   public synchronized void write(byte[] b, int off, int len) throws IOException {
      if (this.closed) {
         throw new IOException("stream is closed");
      } else if (this.count + len > this.clen) {
         throw new ProtocolException("Exceeding stated content length of " + this.clen);
      } else {
         this.os.write(b, off, len);
         this.count += len;
      }
   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public synchronized void close() throws IOException {
      if (!this.closed) {
         if (this.count != this.clen) {
            throw new ProtocolException("Did not meet stated content length of OutputStream:  you wrote " + this.count + " bytes and I was expecting  you to write exactly " + this.clen + " bytes!!!");
         } else {
            this.closed = true;
            this.os.flush();
         }
      }
   }
}
