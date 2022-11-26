package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import weblogic.servlet.HTTPLogger;
import weblogic.utils.io.Chunk;

final class PostInputStream extends InputStream {
   protected ByteBuffer buf;
   private InputStream in;
   private Chunk c;
   private boolean isEOF = false;
   private long contentLen;
   protected long nread = 0L;
   private long timeReading = 0L;
   private int maxPostTime = -1;
   private AbstractHttpConnectionHandler ms;
   private boolean timedOut = false;

   PostInputStream(AbstractHttpConnectionHandler ms, long clen, byte[] b, int startPos, int endPos) {
      this.ms = ms;
      this.in = ms.getInputStream();
      this.contentLen = clen;
      int postDataLen = endPos - startPos;
      if (postDataLen > 0) {
         this.buf = ByteBuffer.wrap(b);
         this.buf.position(startPos);
         this.buf.limit(endPos);
      }

      this.maxPostTime = ms.getHttpServer().getMaxPostTimeSecs() * 1000;
   }

   private boolean isContentLenSet() {
      return this.contentLen >= 0L;
   }

   private void initChunk() {
      if (this.c == null) {
         this.c = Chunk.getChunk();
         this.buf = ByteBuffer.wrap(this.c.buf);
      }

      this.buf.clear();
   }

   public String toString() {
      return super.toString() + " - contentLen: '" + this.contentLen + "', nread: '" + this.nread + "', timedOut: '" + this.timedOut + "', position in Buff: '" + (this.buf == null ? 0 : this.buf.position()) + "', limit for buff: '" + (this.buf == null ? 0 : this.buf.limit()) + "'";
   }

   private void complain() throws ProtocolException {
      ProtocolException e = new ProtocolException("EOF after reading only: '" + this.nread + "' of: '" + this.contentLen + "' promised bytes, out of which at least: '" + (this.buf == null ? 0 : this.buf.remaining()) + "' were already buffered");
      this.in = null;
      this.nread = this.contentLen = 0L;
      this.markEOF();
      throw e;
   }

   private void checkPostTime() throws PostTimeoutException {
      if (this.maxPostTime >= 0 && this.timeReading > (long)this.maxPostTime) {
         HTTPLogger.logPOSTTimeExceeded(this.maxPostTime / 1000);
         this.nread = 0L;
         this.releaseBuf();
         throw new PostTimeoutException("Attempt to read for more thanthe max POST read time: " + this.maxPostTime / 1000 + "' seconds");
      }
   }

   public int read() throws IOException {
      this.checkTimedOut();

      try {
         if (this.isEOF) {
            return -1;
         } else if (this.isContentLenSet() && this.nread == this.contentLen) {
            return this.markEOF();
         } else {
            int count = false;
            if (this.buf == null || this.buf.remaining() <= 0) {
               this.initChunk();
               int count;
               if (this.isContentLenSet()) {
                  count = this.readAndCalTime((ByteBuffer)this.buf, 0, (long)((int)Math.min(this.contentLen - this.nread, (long)Chunk.CHUNK_SIZE)));
                  if (count < 0) {
                     this.complain();
                  }
               } else {
                  count = this.readAndCalTime((ByteBuffer)this.buf, 0, (long)Chunk.CHUNK_SIZE);
                  if (count == -1) {
                     return this.markEOF();
                  }
               }
            }

            ++this.nread;
            this.ms.incrementBytesReceivedCount(1L);
            return this.buf.get() & 255;
         }
      } catch (SocketTimeoutException var2) {
         this.setTimedOut(true);
         this.releaseBuf();
         throw var2;
      }
   }

   private int markEOF() {
      this.releaseBuf();
      this.isEOF = true;
      return -1;
   }

   private void releaseBuf() {
      if (this.c != null) {
         Chunk.releaseChunk(this.c);
         this.c = null;
      }

      this.buf = null;
   }

   public int read(byte[] b, int off, int len) throws IOException {
      this.checkTimedOut();

      try {
         if (this.isEOF) {
            return -1;
         } else {
            long remaining = 0L;
            if (this.isContentLenSet()) {
               remaining = this.contentLen - this.nread;
               if (remaining <= 0L) {
                  return this.markEOF();
               }
            }

            int count = false;
            int count;
            if (this.buf != null && this.buf.remaining() > 0) {
               if (this.isContentLenSet()) {
                  count = this.copyFromBuf(b, off, len, (int)Math.min(remaining, (long)this.buf.remaining()));
               } else {
                  count = this.copyFromBuf(b, off, len, this.buf.remaining());
               }
            } else if (this.isContentLenSet()) {
               if ((long)len >= remaining) {
                  count = this.readAndCalTime(b, off, remaining);
               } else {
                  count = this.readInternal(b, off, len, (int)Math.min((long)Chunk.CHUNK_SIZE, remaining));
               }
            } else {
               count = this.readInternal(b, off, len, Chunk.CHUNK_SIZE);
            }

            if (this.isContentLenSet() && count < 0) {
               this.complain();
            }

            this.ms.incrementBytesReceivedCount((long)count);
            this.nread += (long)count;
            return count;
         }
      } catch (SocketTimeoutException var7) {
         this.setTimedOut(true);
         this.releaseBuf();
         throw var7;
      }
   }

   private int readInternal(byte[] b, int off, int lenForRead, int lenForFill) throws IOException {
      return lenForRead != lenForFill && Chunk.CHUNK_SIZE > lenForRead ? this.fillAndRead(b, off, lenForRead, lenForFill) : this.readAndCalTime(b, off, (long)lenForRead);
   }

   private int fillAndRead(byte[] b, int off, int lenForRead, int lenForFill) throws IOException {
      this.initChunk();
      int availInBuf = this.readAndCalTime((ByteBuffer)this.buf, 0, (long)lenForFill);
      return this.copyFromBuf(b, off, lenForRead, availInBuf);
   }

   private int readAndCalTime(ByteBuffer b, int off, long len) throws IOException {
      int r = this.readAndCalTime(b.array(), off, len);
      if (r > 0) {
         b.limit(r);
      }

      return r;
   }

   private int readAndCalTime(byte[] b, int off, long len) throws IOException {
      int r = false;
      this.checkPostTime();
      long startTime = System.currentTimeMillis();
      int r = this.in.read(b, off, (int)len);
      this.timeReading += System.currentTimeMillis() - startTime;
      return r;
   }

   private int copyFromBuf(byte[] b, int off, int len, int availInBuf) {
      if (availInBuf > 0) {
         len = Math.min(availInBuf, len);
         this.buf.get(b, off, len);
      } else {
         len = this.markEOF();
      }

      return len;
   }

   public int available() throws IOException {
      int x = (this.buf == null ? 0 : this.buf.remaining()) + this.in.available();
      return this.isContentLenSet() ? (int)Math.min((long)x, this.contentLen) : x;
   }

   private void skipUnreadBody() throws IOException {
      if (this.isContentLenSet() && this.nread < this.contentLen) {
         this.skip(this.contentLen - this.nread);
      }

   }

   public void close() throws IOException {
      this.skipUnreadBody();
      this.releaseBuf();
   }

   private void checkTimedOut() throws SocketTimeoutException {
      if (this.timedOut) {
         throw new SocketTimeoutException("read is alrady timed out");
      }
   }

   private void setTimedOut(boolean _timedOut) {
      this.timedOut = _timedOut;
   }
}
