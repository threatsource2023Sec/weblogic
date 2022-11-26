package weblogic.net.http;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import weblogic.utils.io.NullInputStream;

public final class KeepAliveStream extends FilterInputStream {
   protected boolean closed = false;
   protected int expected;
   protected int count = 0;
   protected int markedCount = 0;
   protected int markLimit = -1;
   protected HttpClient client;

   public KeepAliveStream(HttpClient hc, InputStream is, int contentLen) {
      super((InputStream)(contentLen == 0 ? NullInputStream.getInstance() : is));
      this.expected = contentLen;
      this.client = hc;
   }

   private final void justRead(int n) throws IOException {
      if (n == -1) {
         if (!this.isMarked()) {
            this.close();
         }

      } else {
         this.count += n;
         if (this.count > this.markLimit) {
            this.markLimit = -1;
         }

         if (!this.isMarked()) {
            if (this.expected != -1 && this.count >= this.expected) {
               this.close();
            }

         }
      }
   }

   private boolean isMarked() {
      if (this.markLimit < 0) {
         return false;
      } else {
         return this.count <= this.markLimit;
      }
   }

   public int read() throws IOException {
      if (this.closed) {
         return -1;
      } else {
         int c = this.in.read();
         if (c != -1) {
            this.justRead(1);
         } else {
            this.justRead(c);
         }

         return c;
      }
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (this.closed) {
         return -1;
      } else {
         int n = this.in.read(b, off, len);
         this.justRead(n);
         return n;
      }
   }

   public long skip(long n) throws IOException {
      if (this.closed) {
         return 0L;
      } else {
         int min = (int)n;
         if (this.expected != -1 && n > (long)(this.expected - this.count)) {
            min = this.expected - this.count;
         }

         n = this.in.skip((long)min);
         this.justRead((int)n);
         return n;
      }
   }

   public int available() throws IOException {
      return this.closed ? 0 : this.in.available();
   }

   public synchronized void close() throws IOException {
      if (!this.closed) {
         boolean bytesRemaining = false;

         try {
            if (this.expected == -1) {
               bytesRemaining = this.in.read() != -1;
            }

            if (bytesRemaining || this.expected > this.count) {
               this.client.setKeepingAlive(false);
               this.in.close();
            }
         } finally {
            this.closed = true;
            HttpClient.finished(this.client);
         }

      }
   }

   public void mark(int readlimit) {
      if (!this.closed && !this.client.isKeepingAlive()) {
         super.mark(readlimit);
         this.markedCount = this.count;
         this.markLimit = readlimit;
      }
   }

   public void reset() throws IOException {
      if (!this.closed) {
         if (this.client.isKeepingAlive()) {
            throw new IOException("mark/reset not supported");
         } else if (!this.isMarked()) {
            throw new IOException("Resetting to an invalid mark");
         } else {
            this.count = this.markedCount;
            super.reset();
         }
      }
   }

   public boolean markSupported() {
      return !this.closed && !this.client.isKeepingAlive() ? super.markSupported() : false;
   }
}
