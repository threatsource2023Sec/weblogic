package org.apache.xmlbeans.impl.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class PushedInputStream extends InputStream {
   private static int defaultBufferSize = 2048;
   protected byte[] buf;
   protected int writepos;
   protected int readpos;
   protected int markpos;
   protected int marklimit;
   protected OutputStream outputStream;

   protected abstract void fill(int var1) throws IOException;

   public final OutputStream getOutputStream() {
      return this.outputStream;
   }

   public PushedInputStream() {
      this(defaultBufferSize);
   }

   public PushedInputStream(int size) {
      this.markpos = -1;
      this.outputStream = new InternalOutputStream();
      if (size < 0) {
         throw new IllegalArgumentException("Negative initial buffer size");
      } else {
         this.buf = new byte[size];
      }
   }

   private void shift(int cb) {
      int savepos = this.readpos;
      if (this.markpos > 0) {
         if (this.readpos - this.markpos > this.marklimit) {
            this.markpos = -1;
         } else {
            savepos = this.markpos;
         }
      }

      int size = this.writepos - savepos;
      if (savepos > 0 && this.buf.length - size >= cb && size <= cb) {
         System.arraycopy(this.buf, savepos, this.buf, 0, size);
      } else {
         int newcount = size + cb;
         byte[] newbuf = new byte[Math.max(this.buf.length << 1, newcount)];
         System.arraycopy(this.buf, savepos, newbuf, 0, size);
         this.buf = newbuf;
      }

      if (savepos > 0) {
         this.readpos -= savepos;
         if (this.markpos > 0) {
            this.markpos -= savepos;
         }

         this.writepos -= savepos;
      }

   }

   public synchronized int read() throws IOException {
      if (this.readpos >= this.writepos) {
         this.fill(1);
         if (this.readpos >= this.writepos) {
            return -1;
         }
      }

      return this.buf[this.readpos++] & 255;
   }

   public synchronized int read(byte[] b, int off, int len) throws IOException {
      int avail = this.writepos - this.readpos;
      if (avail < len) {
         this.fill(len - avail);
         avail = this.writepos - this.readpos;
         if (avail <= 0) {
            return -1;
         }
      }

      int cnt = avail < len ? avail : len;
      System.arraycopy(this.buf, this.readpos, b, off, cnt);
      this.readpos += cnt;
      return cnt;
   }

   public synchronized long skip(long n) throws IOException {
      if (n <= 0L) {
         return 0L;
      } else {
         long avail = (long)(this.writepos - this.readpos);
         long req;
         if (avail < n) {
            req = n - avail;
            if (req > 2147483647L) {
               req = 2147483647L;
            }

            this.fill((int)req);
            avail = (long)(this.writepos - this.readpos);
            if (avail <= 0L) {
               return 0L;
            }
         }

         req = avail < n ? avail : n;
         this.readpos = (int)((long)this.readpos + req);
         return req;
      }
   }

   public synchronized int available() {
      return this.writepos - this.readpos;
   }

   public synchronized void mark(int readlimit) {
      this.marklimit = readlimit;
      this.markpos = this.readpos;
   }

   public synchronized void reset() throws IOException {
      if (this.markpos < 0) {
         throw new IOException("Resetting to invalid mark");
      } else {
         this.readpos = this.markpos;
      }
   }

   public boolean markSupported() {
      return true;
   }

   private class InternalOutputStream extends OutputStream {
      private InternalOutputStream() {
      }

      public synchronized void write(int b) throws IOException {
         if (PushedInputStream.this.writepos + 1 > PushedInputStream.this.buf.length) {
            PushedInputStream.this.shift(1);
         }

         PushedInputStream.this.buf[PushedInputStream.this.writepos] = (byte)b;
         ++PushedInputStream.this.writepos;
      }

      public synchronized void write(byte[] b, int off, int len) {
         if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length && off + len >= 0) {
            if (len != 0) {
               if (PushedInputStream.this.writepos + len > PushedInputStream.this.buf.length) {
                  PushedInputStream.this.shift(len);
               }

               System.arraycopy(b, off, PushedInputStream.this.buf, PushedInputStream.this.writepos, len);
               PushedInputStream var10000 = PushedInputStream.this;
               var10000.writepos += len;
            }
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      // $FF: synthetic method
      InternalOutputStream(Object x1) {
         this();
      }
   }
}
