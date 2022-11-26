package weblogic.servlet.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import weblogic.utils.io.Chunk;

public final class WLOutputStreamWriter extends Writer {
   private final CharsetEncoder encoder;
   private final OutputStream out;
   private final ByteBuffer bb;
   private int pos;
   private boolean error;
   private boolean closed;
   private boolean haveLeftoverChar;
   private char leftoverChar;
   private CharBuffer lcb;
   private Chunk head;

   public WLOutputStreamWriter(OutputStream out, String enc) {
      this(out, Charset.forName(enc).newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE));
   }

   public WLOutputStreamWriter(OutputStream out) {
      this(out, Charset.defaultCharset().newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE));
   }

   private WLOutputStreamWriter(OutputStream out, CharsetEncoder encoder) {
      this.pos = 0;
      this.error = false;
      this.closed = false;
      this.haveLeftoverChar = false;
      if (out == null) {
         throw new NullPointerException("out is null");
      } else {
         this.out = out;
         this.encoder = encoder;
         this.head = Chunk.getChunk();
         this.bb = ByteBuffer.wrap(this.head.buf);
      }
   }

   public String getEncoding() {
      return this.encoder.charset().name();
   }

   public void write(int c) throws IOException {
      if (!this.error) {
         char[] cbuf = new char[]{(char)c};
         this.write((char[])cbuf, 0, 1);
      }
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      if (!this.error) {
         if (off >= 0 && off <= cbuf.length && len >= 0 && off + len <= cbuf.length && off + len >= 0) {
            if (len != 0) {
               this.write(CharBuffer.wrap(cbuf, off, len));
            }
         } else {
            this.release();
            this.error = true;
            throw new IndexOutOfBoundsException();
         }
      }
   }

   public void write(String str, int off, int len) throws IOException {
      if (!this.error) {
         if (str != null && len != 0) {
            int strLen = str.length();
            if (off >= 0 && off <= strLen && len >= 0 && off + len <= strLen && off + len >= 0) {
               CharChunk charChunk = null;
               CharBuffer charBuffer = null;

               try {
                  if (CharChunk.JSPCHUNK_SIZE < len) {
                     charBuffer = CharBuffer.allocate(len);
                  } else {
                     charChunk = CharChunk.getJspCharChunk();
                     charBuffer = charChunk.getWriteCharBuffer();
                  }

                  str.getChars(off, off + len, charBuffer.array(), 0);
                  charBuffer.clear();
                  charBuffer.limit(len);
                  this.write(charBuffer);
               } finally {
                  if (charChunk != null) {
                     CharChunk.releaseJspChunks(charChunk);
                  }

               }

            } else {
               this.release();
               this.error = true;
               throw new IndexOutOfBoundsException();
            }
         }
      }
   }

   private void write(CharBuffer charBuffer) throws IOException {
      if (this.haveLeftoverChar) {
         this.flushLeftoverChar(charBuffer, false);
      }

      while(charBuffer.hasRemaining()) {
         CoderResult result = this.encoder.encode(charBuffer, this.bb, true);
         if (result.isUnderflow()) {
            if (charBuffer.remaining() == 1) {
               this.haveLeftoverChar = true;
               this.leftoverChar = charBuffer.get();
            }
            break;
         }

         if (result.isOverflow()) {
            this.writeBytes();
         } else {
            this.release();
            this.error = true;
            result.throwException();
         }
      }

      this.flushBuffer();
   }

   public void close() throws IOException {
      if (!this.closed) {
         try {
            this.flushLeftoverChar((CharBuffer)null, true);

            while(true) {
               CoderResult coderresult = this.encoder.flush(this.bb);
               if (coderresult.isUnderflow()) {
                  if (this.bb.position() > 0) {
                     this.writeBytes();
                  }
                  break;
               }

               if (coderresult.isOverflow()) {
                  this.writeBytes();
               } else {
                  coderresult.throwException();
               }
            }
         } finally {
            this.out.close();
            this.release();
            this.closed = true;
         }

      }
   }

   public void flush() throws IOException {
      if (!this.error) {
         if (!this.closed) {
            this.flushBuffer();
            this.out.flush();
         }
      }
   }

   private void writeBytes() throws IOException {
      this.bb.flip();
      int limit = this.bb.limit();
      int pos = this.bb.position();
      int toWrite = pos > limit ? 0 : limit - pos;
      this.out.write(this.bb.array(), this.bb.arrayOffset() + pos, toWrite);
      this.bb.clear();
   }

   private void flushLeftoverChar(CharBuffer buffer, boolean endOfInput) throws IOException {
      if (this.haveLeftoverChar || endOfInput) {
         if (this.lcb == null) {
            this.lcb = CharBuffer.allocate(2);
         } else {
            this.lcb.clear();
         }

         if (this.haveLeftoverChar) {
            this.lcb.put(this.leftoverChar);
         }

         if (buffer != null && buffer.hasRemaining()) {
            this.lcb.put(buffer.get());
         }

         this.lcb.flip();

         while(this.lcb.hasRemaining() || endOfInput) {
            CoderResult coderresult = this.encoder.encode(this.lcb, this.bb, endOfInput);
            if (coderresult.isUnderflow()) {
               if (this.lcb.hasRemaining()) {
                  throw new Error();
               }
               break;
            }

            if (coderresult.isOverflow()) {
               this.writeBytes();
            } else {
               coderresult.throwException();
            }
         }

         this.haveLeftoverChar = false;
      }
   }

   private void flushBuffer() throws IOException {
      if (!this.error) {
         if (!this.closed) {
            if (this.bb.position() > 0) {
               this.writeBytes();
            }

         }
      }
   }

   public void release() {
      if (this.head != null) {
         Chunk.releaseChunks(this.head);
         this.head = null;
      }

   }
}
