package org.python.util;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public abstract class ConsoleInputStream extends FilterInputStream {
   protected final EOLPolicy eolPolicy;
   protected final String eol;
   protected final Charset encoding;
   private ByteBuffer buf;
   protected static final ByteBuffer EMPTY_BUF = ByteBuffer.allocate(0);
   protected static final String LINE_SEPARATOR = System.getProperty("line.separator");

   ConsoleInputStream(InputStream in, Charset encoding, EOLPolicy eolPolicy, String eol) {
      super(in);
      this.encoding = encoding;
      this.eolPolicy = eolPolicy;
      this.eol = eol != null ? eol : LINE_SEPARATOR;
      this.buf = EMPTY_BUF;
   }

   protected abstract CharSequence getLine() throws IOException, EOFException;

   private void fillBuffer() throws IOException, EOFException {
      this.buf = EMPTY_BUF;
      CharSequence line = this.getLine();
      CharBuffer cb = CharBuffer.allocate(line.length() + this.eol.length());
      cb.append(line);
      switch (this.eolPolicy) {
         case LEAVE:
         default:
            break;
         case ADD:
            cb.append(this.eol);
            break;
         case REPLACE:
            int n = cb.position() - 1;
            if (n >= 0 && cb.charAt(n) == '\n') {
               --n;
            }

            if (n >= 0 && cb.charAt(n) == '\r') {
               --n;
            }

            cb.position(n + 1);
            cb.append(this.eol);
      }

      cb.flip();
      if (cb.hasRemaining()) {
         this.buf = this.encoding.encode(cb);
      }

   }

   public int read() throws IOException {
      try {
         while(!this.buf.hasRemaining()) {
            this.fillBuffer();
         }

         return this.buf.get() & 255;
      } catch (EOFException var2) {
         return -1;
      }
   }

   public int read(byte[] b, int off, int len) throws IOException, EOFException {
      if (off >= 0 && len >= 0 && len <= b.length - off) {
         try {
            if (len > 0) {
               int n = this.buf.remaining();
               if (n <= 0) {
                  this.fillBuffer();
                  n = this.buf.remaining();
               }

               len = n < len ? n : len;
               this.buf.get(b, off, len);
            }

            return len;
         } catch (EOFException var5) {
            return -1;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public long skip(long n) throws IOException {
      long r = (long)this.buf.remaining();
      if (n > r) {
         n = r;
      }

      this.buf.position(this.buf.position() + (int)n);
      return n;
   }

   public int available() throws IOException {
      return this.buf.remaining();
   }

   public synchronized void mark(int readlimit) {
   }

   public synchronized void reset() throws IOException {
   }

   public boolean markSupported() {
      return false;
   }

   public static enum EOLPolicy {
      LEAVE,
      ADD,
      REPLACE;
   }
}
