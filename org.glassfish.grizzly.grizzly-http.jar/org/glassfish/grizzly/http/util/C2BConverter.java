package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.utils.Charsets;

public class C2BConverter {
   private static final Logger logger = Grizzly.logger(C2BConverter.class);
   protected ByteChunk bb;
   protected final String enc;
   protected final CharsetEncoder encoder;

   public C2BConverter(ByteChunk output, String encoding) throws IOException {
      this.bb = output;
      this.enc = encoding;
      this.encoder = Charsets.lookupCharset(this.enc).newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
   }

   public C2BConverter(String encoding) throws IOException {
      this(new ByteChunk(1024), encoding);
   }

   public static C2BConverter getInstance(ByteChunk output, String encoding) throws IOException {
      return new C2BConverter(output, encoding);
   }

   public ByteChunk getByteChunk() {
      return this.bb;
   }

   public String getEncoding() {
      return this.enc;
   }

   public void setByteChunk(ByteChunk bb) {
      this.bb = bb;
   }

   public void recycle() {
      this.bb.recycle();
   }

   public void convert(char[] c, int off, int len) throws IOException {
      CharBuffer cb = CharBuffer.wrap(c, off, len);
      byte[] barr = this.bb.getBuffer();
      int boff = this.bb.getEnd();
      ByteBuffer tmp = ByteBuffer.wrap(barr, boff, barr.length - boff);
      CoderResult cr = this.encoder.encode(cb, tmp, true);
      this.bb.setEnd(tmp.position());

      while(cr == CoderResult.OVERFLOW) {
         if (!this.bb.canGrow()) {
            this.bb.flushBuffer();
         }

         boff = this.bb.getEnd();
         barr = this.bb.getBuffer();
         tmp = ByteBuffer.wrap(barr, boff, barr.length - boff);
         cr = this.encoder.encode(cb, tmp, true);
         this.bb.setEnd(tmp.position());
      }

      if (cr != CoderResult.UNDERFLOW) {
         throw new IOException("Encoding error");
      }
   }

   public void convert(String s) throws IOException {
      this.convert((String)s, 0, s.length());
   }

   public void convert(String s, int off, int len) throws IOException {
      this.convert(s.toCharArray(), off, len);
   }

   public void convert(char c) throws IOException {
      char[] tmp = new char[]{c};
      this.convert((char[])tmp, 0, 1);
   }

   public void convert(MessageBytes mb) throws IOException {
      int type = mb.getType();
      if (type != 2) {
         ByteChunk orig = this.bb;
         this.setByteChunk(mb.getByteChunk());
         this.bb.recycle();
         this.bb.allocate(32, -1);
         if (type == 1) {
            this.convert(mb.getString());
         } else if (type == 3) {
            CharChunk charC = mb.getCharChunk();
            this.convert(charC.getBuffer(), charC.getStart(), charC.getLength());
         } else if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "XXX unknowon type {0}", type);
         }

         this.setByteChunk(orig);
      }
   }

   public void flushBuffer() throws IOException {
      this.bb.flushBuffer();
   }
}
