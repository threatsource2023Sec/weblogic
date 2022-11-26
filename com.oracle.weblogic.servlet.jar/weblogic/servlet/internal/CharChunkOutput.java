package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import javax.servlet.ServletResponse;
import weblogic.utils.io.Chunk;

public class CharChunkOutput extends ChunkOutput {
   private int count;
   private char[] buf;
   private CharsetDecoder decoder;
   private static final int BUF_SIZE = 64;
   private String encoding;

   public CharChunkOutput(ServletResponse response) {
      this.autoflush = false;
      this.buflimit = -1;
      this.encoding = response.getCharacterEncoding();
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void reset() {
      this.count = 0;
   }

   public void release() {
      this.count = 0;
      this.buf = null;
      this.decoder = null;
   }

   public Chunk getHead() {
      return null;
   }

   public long getTotal() {
      return 0L;
   }

   public int getCount() {
      return this.count;
   }

   public int getBufferSize() {
      return -1;
   }

   public void setBufferSize(int bsize) {
   }

   public boolean isAutoFlush() {
      return this.autoflush;
   }

   public void setAutoFlush(boolean b) {
   }

   public boolean isChunking() {
      return false;
   }

   public void setChunking(boolean b) {
   }

   public void write(int i) throws IOException {
      this.initBuf();
      int nc = this.adjustBuf(1);
      this.buf[this.count] = (char)i;
      this.count = nc;
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.initBuf();
      if (this.decoder == null) {
         Charset cs = Charset.forName(this.getEncoding());
         this.decoder = cs.newDecoder();
      }

      ByteBuffer byteBuf = ByteBuffer.wrap(b, off, len);
      int est = (int)(this.decoder.averageCharsPerByte() * (float)len);
      this.adjustBuf(est);
      int start = false;

      while(byteBuf.hasRemaining()) {
         CharBuffer cb = CharBuffer.wrap(this.buf, this.count, this.buf.length - this.count);
         int start = cb.position();
         CoderResult cr = this.decoder.decode(byteBuf, cb, true);
         this.count += cb.position() - start;
         if (cr == CoderResult.UNDERFLOW) {
            break;
         }

         if (cr == CoderResult.OVERFLOW) {
            est = (int)((float)byteBuf.remaining() * this.decoder.averageCharsPerByte());
            this.adjustBuf(est == 0 ? 1 : est);
         } else {
            cr.throwException();
         }
      }

   }

   public void write(char[] c, int off, int len) throws IOException {
      this.initBuf();
      int nc = this.adjustBuf(len);
      System.arraycopy(c, off, this.buf, this.count, len);
      this.count = nc;
   }

   public void print(String s) throws IOException {
      this.initBuf();
      int nc = this.adjustBuf(s.length());
      s.getChars(0, s.length(), this.buf, this.count);
      this.count = nc;
   }

   public void commit() throws IOException {
   }

   public void clearBuffer() {
      this.count = 0;
   }

   public void flush() throws IOException {
   }

   public void writeStream(InputStream is, long len, long clen) throws IOException {
      Chunk c = Chunk.getChunk();
      byte[] buf = c.buf;

      try {
         boolean readAll = len == -1L;
         int index = 0;

         int r;
         while((r = is.read()) != -1) {
            if (index == Chunk.CHUNK_SIZE) {
               this.write((byte[])buf, 0, index);
               index = 0;
            }

            buf[index] = (byte)r;
            --len;
            ++index;
            if (!readAll && len == 0L) {
               break;
            }
         }

         if (index > 0) {
            this.write((byte[])buf, 0, index);
            boolean var14 = false;
         }
      } finally {
         Chunk.releaseChunk(c);
      }

   }

   private int adjustBuf(int len) {
      int newcount = this.count + len;
      if (newcount > this.buf.length) {
         char[] ret = new char[Math.max(this.buf.length << 1, newcount)];
         System.arraycopy(this.buf, 0, ret, 0, this.count);
         this.buf = ret;
      }

      return newcount;
   }

   private void initBuf() {
      if (this.buf == null) {
         this.buf = new char[64];
      }

   }

   public char[] getCharBuffer() {
      return this.buf;
   }
}
