package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import weblogic.utils.io.Chunk;

class CharsetChunkOutput extends ChunkOutput {
   private CharsetEncoder encoder;
   private String encoding;
   private boolean useDefaultEncoding = false;
   private ByteBuffer headBuf;
   private ByteBuffer buf;
   private int charcount;
   private CharChunk charChunk;
   private CharBuffer charBuffer;

   protected CharsetChunkOutput(int bufsize, boolean autoflush, OutputStream os, ServletOutputStreamImpl sos, Charset cs, boolean useDefaultEncoding) {
      super(bufsize, autoflush, os, sos);
      this.encoder = cs.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
      this.useDefaultEncoding = useDefaultEncoding;
      this.encoding = cs.name();
      this.headBuf = makeByteBuffer(this.head);
      this.buf = this.headBuf;
      this.charChunk = CharChunk.getChunk();
      this.charBuffer = CharBuffer.wrap(this.charChunk.buf);
   }

   CharsetChunkOutput(ChunkOutput orig, Charset cs, boolean useDefaultEncoding) {
      this.encoder = cs.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
      if (orig instanceof CharsetChunkOutput) {
         CharsetChunkOutput originalCharsetChunkOutput = (CharsetChunkOutput)orig;
         this.charChunk = originalCharsetChunkOutput.charChunk;
         this.charBuffer = originalCharsetChunkOutput.charBuffer;
      } else {
         this.charChunk = CharChunk.getChunk();
         this.charBuffer = CharBuffer.wrap(this.charChunk.buf);
      }

      this.useDefaultEncoding = useDefaultEncoding;
      this.encoding = cs.name();
      this.head = orig.head;
      this.tail = orig.tail;
      this.total = orig.total;
      this.count = orig.count;
      this.buflimit = orig.buflimit;
      this.os = orig.os;
      this.sos = orig.sos;
      this.autoflush = orig.autoflush;
      this.stickyBufferSize = orig.stickyBufferSize;
      this.chunking = orig.chunking;
      this.alwaysFlush = orig.alwaysFlush;
      this.setBufferFlushStrategy();
      this.headBuf = makeByteBuffer(this.head);
      if (this.head == this.tail) {
         this.buf = this.headBuf;
      } else {
         this.buf = makeByteBuffer(this.tail);
      }

      this.encoder.reset();
   }

   public String getEncoding() {
      return this.useDefaultEncoding ? null : this.encoding;
   }

   public void reset() {
      if (HTTPDebugLogger.isEnabled()) {
         p("reset() " + this.toString());
      }

      if (this.encoder != null) {
         this.encoder.reset();
      }

      super.reset();
      this.headBuf = null;
      this.buf = null;
      if (this.charBuffer != null) {
         this.charBuffer.clear();
      }

   }

   public void release() {
      if (HTTPDebugLogger.isEnabled()) {
         p("release()" + this.toString());
      }

      this.buf = null;
      this.headBuf = null;
      this.encoder = null;
      super.release();
      this.charBuffer = null;
      CharChunk.releaseChunks(this.charChunk);
   }

   public void write(int i) throws IOException {
      if (!this.released) {
         this.flushStrategy.checkOverflow(1L);
         ++this.charcount;
         this.ensureCharBufferNotFull();
         this.charBuffer.put((char)i);
         this.flushStrategy.checkForFlush();
      }
   }

   void writeByte(int i) throws IOException {
      if (!this.released) {
         this.ensureCharBufferEmpty();
         this.flushStrategy.checkOverflow(1L);
         ++this.charcount;
         this.tail = Chunk.tail(this.tail);
         int rem = this.buf.remaining();
         if (rem == 0) {
            this.tail = ensureCapacity(this.tail);
            this.buf = makeByteBuffer(this.tail);
            rem = this.buf.remaining();
         }

         this.buf.put((byte)i);
         ++this.tail.end;
         ++this.count;
         this.flushStrategy.checkForFlush();
      }
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (!this.released) {
         if (HTTPDebugLogger.isEnabled()) {
            p("write(b, off, len)" + len);
         }

         this.ensureCharBufferEmpty();
         this.flushStrategy.checkOverflow((long)len);
         this.charcount += len;
         this.tail = Chunk.tail(this.tail);
         this.implWrite(b, off, len);
         this.flushStrategy.checkForFlush();
      }
   }

   public void write(ByteBuffer buf) throws IOException {
      if (!this.released) {
         this.ensureCharBufferEmpty();
         super.write(buf);
      }
   }

   public void writeStream(InputStream is, long len, long clen) throws IOException {
      if (!this.released) {
         this.ensureCharBufferEmpty();
         if (len > 0L) {
            this.flushStrategy.checkOverflow(len);
            this.charcount = (int)((long)this.charcount + len);
         }

         this.tail = Chunk.tail(this.tail);
         super.writeStream(is, len, clen);
         this.tail = Chunk.tail(this.tail);
         this.buf = makeByteBuffer(this.tail);
      }
   }

   public void write(char[] c, int off, int len) throws IOException {
      if (!this.released) {
         if (HTTPDebugLogger.isEnabled()) {
            p(" write(char[]) : " + this.toString());
         }

         this.flushStrategy.checkOverflow((long)len);
         this.charcount += len;
         if (this.charBuffer.position() + len < this.charBuffer.limit()) {
            this.charBuffer.put(c, off, len);
         } else if (len > this.charBuffer.limit()) {
            this.flushCharBuffer();
            this.write(CharBuffer.wrap(c, off, len));
         } else {
            while(len > 0) {
               this.ensureCharBufferNotFull();
               int rem = this.charBuffer.remaining();
               int toCopy = Math.min(rem, len);
               this.charBuffer.put(c, off, toCopy);
               off += toCopy;
               len -= toCopy;
            }
         }

         this.flushStrategy.checkForFlush();
      }
   }

   public void print(String s) throws IOException {
      if (!this.released) {
         if (s == null) {
            s = "null";
         }

         int len = s.length();
         this.flushStrategy.checkOverflow((long)len);
         this.charcount += len;
         if (this.charBuffer.position() + len < this.charBuffer.limit()) {
            this.charBuffer.put(s);
         } else if (len > this.charBuffer.limit()) {
            this.flushCharBuffer();
            this.write(CharBuffer.wrap(s));
         } else {
            int off = 0;

            int toCopy;
            for(boolean forceFlush = false; len > 0; len -= toCopy) {
               this.ensureCharBufferNotFull();
               int rem = this.charBuffer.remaining();
               toCopy = Math.min(rem, len);
               if (rem < len && Character.isHighSurrogate(s.charAt(off + toCopy - 1))) {
                  --toCopy;
                  this.charBuffer.put(s, off, off + toCopy);
                  this.flushCharBuffer();
               } else {
                  this.charBuffer.put(s, off, off + toCopy);
               }

               off += toCopy;
            }
         }

         this.flushStrategy.checkForFlush();
      }
   }

   public void clearBuffer() {
      if (!this.released) {
         if (HTTPDebugLogger.isEnabled()) {
            p("clearBuffer ");
         }

         super.clearBuffer();
         this.charcount = 0;
         if (HTTPDebugLogger.isEnabled()) {
            showProps(this.buf);
         }

         this.buf = this.headBuf;
         this.buf.position(this.head.end).limit(END_OFFSET - this.head.end);
         this.charBuffer.clear();
      }
   }

   public void flush() throws IOException {
      if (!this.released) {
         if (HTTPDebugLogger.isEnabled()) {
            p("flush()");
         }

         this.ensureCharBufferEmpty();
         super.flush();
         if (HTTPDebugLogger.isEnabled()) {
            p(" After flush : tail.end " + this.tail.end + " count : " + this.count);
         }

         this.postFlush();
      }
   }

   void postFlush() {
      this.tail = ensureCapacity(this.tail);
      this.buf = makeByteBuffer(this.tail);
   }

   public int getCount() {
      try {
         this.ensureCharBufferEmpty();
      } catch (IOException var2) {
      }

      return this.count;
   }

   protected int getCountForCheckOverflow() {
      return this.charcount;
   }

   protected void flushBufferedDataToChunk() {
      if (this.charBuffer != null) {
         try {
            this.ensureCharBufferEmpty();
         } catch (IOException var2) {
         }

      }
   }

   private void ensureCharBufferNotFull() throws IOException {
      if (!this.charBuffer.hasRemaining()) {
         this.flushCharBuffer();
      }

   }

   private void ensureCharBufferEmpty() throws IOException {
      if (this.charBuffer.position() > 0) {
         this.flushCharBuffer();
      }

   }

   private void flushCharBuffer() throws IOException {
      this.charBuffer.flip();
      this.write(this.charBuffer);
      this.charBuffer.clear();
   }

   private void write(CharBuffer cb) throws IOException {
      if (cb.hasRemaining()) {
         this.tail = Chunk.tail(this.tail);

         while(cb.hasRemaining()) {
            int start = this.buf.position();
            if (HTTPDebugLogger.isEnabled()) {
               showProps(this.buf);
            }

            CoderResult cr = this.encoder.encode(cb, this.buf, true);
            if (HTTPDebugLogger.isEnabled()) {
               p("show props for buf from write(CB)");
            }

            if (HTTPDebugLogger.isEnabled()) {
               showProps(this.buf);
            }

            int pos = this.buf.position();
            this.tail.end = pos;
            this.count += pos - start;
            if (HTTPDebugLogger.isEnabled()) {
               p(" chunk props : tail.end " + this.tail.end + " count : " + this.count);
            }

            if (cr == CoderResult.UNDERFLOW) {
               if (HTTPDebugLogger.isEnabled()) {
                  p("Underflow detected ");
                  showProps(this.buf);
               }
               break;
            }

            if (cr == CoderResult.OVERFLOW) {
               if (HTTPDebugLogger.isEnabled()) {
                  p("Overflow detected, expand capacity");
                  showProps(this.buf);
               }

               if (!this.buf.hasRemaining()) {
                  this.tail = ensureCapacity(this.tail);
                  this.buf = makeByteBuffer(this.tail);
               } else {
                  byte[] tmpBuf = new byte[(int)this.encoder.maxBytesPerChar()];
                  ByteBuffer tmp = ByteBuffer.wrap(tmpBuf);
                  this.encoder.encode(cb, tmp, true);
                  if (tmp.position() == 0 && cb.hasRemaining()) {
                     for(int size = (int)this.encoder.maxBytesPerChar() + 1; tmp.position() == 0 && cb.hasRemaining(); ++size) {
                        tmpBuf = new byte[size];
                        tmp = ByteBuffer.wrap(tmpBuf);
                        this.encoder.encode(cb, tmp, true);
                     }

                     if (HTTPDebugLogger.isEnabled()) {
                        p("Exceptional overflow detected for surrogate pair char");
                     }
                  }

                  if (HTTPDebugLogger.isEnabled()) {
                     showProps(tmp);
                  }

                  tmp.flip();
                  this.implWrite(tmpBuf, 0, tmp.remaining());
               }
            } else {
               cr.throwException();
            }
         }

         int begin = this.buf.position();
         CoderResult cr = this.encoder.flush(this.buf);
         if (cr.isUnderflow()) {
            int end = this.buf.position();
            this.tail.end = end;
            this.count += end - begin;
         } else if (cr.isOverflow()) {
            ByteBuffer tmpBuf = ByteBuffer.allocate((int)this.encoder.maxBytesPerChar());
            this.encoder.flush(tmpBuf);
            tmpBuf.flip();
            this.implWrite(tmpBuf.array(), 0, tmpBuf.remaining());
         }

         this.encoder.reset();
      }
   }

   private void implWrite(byte[] b, int off, int len) throws IOException {
      while(len > 0) {
         int rem = this.buf.remaining();
         if (rem == 0) {
            this.tail = ensureCapacity(this.tail);
            this.buf = makeByteBuffer(this.tail);
            rem = this.buf.remaining();
         }

         int toCopy = Math.min(rem, len);
         this.buf.put(b, off, toCopy);
         off += toCopy;
         len -= toCopy;
         Chunk var10000 = this.tail;
         var10000.end += toCopy;
         this.count += toCopy;
      }

   }

   private static ByteBuffer makeByteBuffer(Chunk c) {
      if (HTTPDebugLogger.isEnabled()) {
         p(" makeByteBuffer :  c.end : " + c.end + " len : " + (END_OFFSET - c.end));
      }

      return ByteBuffer.wrap(c.buf, c.end, END_OFFSET - c.end);
   }

   private static void showProps(Buffer buf) {
      p(" capacity : " + buf.capacity() + " limit : " + buf.limit() + " position: " + buf.position());
   }

   private static void p(String out) {
      HTTPDebugLogger.debug("[CharsetChunkOutput]" + out);
   }
}
