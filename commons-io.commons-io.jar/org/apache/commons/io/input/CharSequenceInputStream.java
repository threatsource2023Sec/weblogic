package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class CharSequenceInputStream extends InputStream {
   private static final int BUFFER_SIZE = 2048;
   private static final int NO_MARK = -1;
   private final CharsetEncoder encoder;
   private final CharBuffer cbuf;
   private final ByteBuffer bbuf;
   private int mark_cbuf;
   private int mark_bbuf;

   public CharSequenceInputStream(CharSequence cs, Charset charset, int bufferSize) {
      this.encoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
      float maxBytesPerChar = this.encoder.maxBytesPerChar();
      if ((float)bufferSize < maxBytesPerChar) {
         throw new IllegalArgumentException("Buffer size " + bufferSize + " is less than maxBytesPerChar " + maxBytesPerChar);
      } else {
         this.bbuf = ByteBuffer.allocate(bufferSize);
         this.bbuf.flip();
         this.cbuf = CharBuffer.wrap(cs);
         this.mark_cbuf = -1;
         this.mark_bbuf = -1;
      }
   }

   public CharSequenceInputStream(CharSequence cs, String charset, int bufferSize) {
      this(cs, Charset.forName(charset), bufferSize);
   }

   public CharSequenceInputStream(CharSequence cs, Charset charset) {
      this(cs, (Charset)charset, 2048);
   }

   public CharSequenceInputStream(CharSequence cs, String charset) {
      this(cs, (String)charset, 2048);
   }

   private void fillBuffer() throws CharacterCodingException {
      this.bbuf.compact();
      CoderResult result = this.encoder.encode(this.cbuf, this.bbuf, true);
      if (result.isError()) {
         result.throwException();
      }

      this.bbuf.flip();
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (b == null) {
         throw new NullPointerException("Byte array is null");
      } else if (len >= 0 && off + len <= b.length) {
         if (len == 0) {
            return 0;
         } else if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
            return -1;
         } else {
            int bytesRead = 0;

            while(len > 0) {
               if (this.bbuf.hasRemaining()) {
                  int chunk = Math.min(this.bbuf.remaining(), len);
                  this.bbuf.get(b, off, chunk);
                  off += chunk;
                  len -= chunk;
                  bytesRead += chunk;
               } else {
                  this.fillBuffer();
                  if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
                     break;
                  }
               }
            }

            return bytesRead == 0 && !this.cbuf.hasRemaining() ? -1 : bytesRead;
         }
      } else {
         throw new IndexOutOfBoundsException("Array Size=" + b.length + ", offset=" + off + ", length=" + len);
      }
   }

   public int read() throws IOException {
      do {
         if (this.bbuf.hasRemaining()) {
            return this.bbuf.get() & 255;
         }

         this.fillBuffer();
      } while(this.bbuf.hasRemaining() || this.cbuf.hasRemaining());

      return -1;
   }

   public int read(byte[] b) throws IOException {
      return this.read(b, 0, b.length);
   }

   public long skip(long n) throws IOException {
      long skipped;
      for(skipped = 0L; n > 0L && this.available() > 0; ++skipped) {
         this.read();
         --n;
      }

      return skipped;
   }

   public int available() throws IOException {
      return this.bbuf.remaining() + this.cbuf.remaining();
   }

   public void close() throws IOException {
   }

   public synchronized void mark(int readlimit) {
      this.mark_cbuf = this.cbuf.position();
      this.mark_bbuf = this.bbuf.position();
      this.cbuf.mark();
      this.bbuf.mark();
   }

   public synchronized void reset() throws IOException {
      if (this.mark_cbuf != -1) {
         if (this.cbuf.position() != 0) {
            this.encoder.reset();
            this.cbuf.rewind();
            this.bbuf.rewind();
            this.bbuf.limit(0);

            while(this.cbuf.position() < this.mark_cbuf) {
               this.bbuf.rewind();
               this.bbuf.limit(0);
               this.fillBuffer();
            }
         }

         if (this.cbuf.position() != this.mark_cbuf) {
            throw new IllegalStateException("Unexpected CharBuffer postion: actual=" + this.cbuf.position() + " expected=" + this.mark_cbuf);
         }

         this.bbuf.position(this.mark_bbuf);
         this.mark_cbuf = -1;
         this.mark_bbuf = -1;
      }

   }

   public boolean markSupported() {
      return true;
   }
}
