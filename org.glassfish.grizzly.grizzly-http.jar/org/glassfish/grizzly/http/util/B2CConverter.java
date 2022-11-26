package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.utils.Charsets;

public class B2CConverter {
   private static final boolean IS_OLD_IO_MODE = Boolean.getBoolean(B2CConverter.class.getName() + ".blockingMode");
   private static final Logger logger = Grizzly.logger(B2CConverter.class);
   private static final int MAX_NUMBER_OF_BYTES_PER_CHARACTER = 16;
   private CharsetDecoder decoder;
   private final ByteBuffer remainder = ByteBuffer.allocate(16);
   private B2CConverterBlocking blockingConverter;

   protected B2CConverter() {
      this.init("US-ASCII");
   }

   public B2CConverter(String encoding) throws IOException {
      this.init(encoding);
   }

   protected void init(String encoding) {
      if (IS_OLD_IO_MODE) {
         try {
            this.blockingConverter = new B2CConverterBlocking(encoding);
         } catch (IOException var3) {
            throw new IllegalStateException("Can not initialize blocking converter");
         }
      } else {
         Charset charset = Charsets.lookupCharset(encoding);
         this.decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
      }

   }

   public void recycle() {
      if (IS_OLD_IO_MODE) {
         this.blockingConverter.recycle();
      }

   }

   public void convert(ByteChunk bb, CharChunk cb) throws IOException {
      this.convert(bb, cb, cb.getBuffer().length - cb.getEnd());
   }

   public void convert(ByteChunk bb, CharChunk cb, int limit) throws IOException {
      if (IS_OLD_IO_MODE) {
         this.blockingConverter.convert(bb, cb, limit);
      } else {
         try {
            int bbAvailable = bb.getEnd() - bb.getStart();
            if (limit > bbAvailable) {
               limit = bbAvailable;
            }

            byte[] barr = bb.getBuffer();
            int boff = bb.getStart();
            ByteBuffer tmp_bb = ByteBuffer.wrap(barr, boff, limit);
            char[] carr = cb.getBuffer();
            int coff = cb.getEnd();
            int remain = carr.length - coff;
            int cbLimit = cb.getLimit();
            if (remain < limit && (cbLimit < 0 || cbLimit > carr.length)) {
               cb.makeSpace(limit);
               carr = cb.getBuffer();
               coff = cb.getEnd();
            }

            CharBuffer tmp_cb = CharBuffer.wrap(carr, coff, carr.length - coff);
            if (this.remainder.position() > 0) {
               this.flushRemainder(tmp_bb, tmp_cb);
            }

            CoderResult cr = this.decoder.decode(tmp_bb, tmp_cb, false);
            cb.setEnd(tmp_cb.position());

            while(cr == CoderResult.OVERFLOW) {
               cb.flushBuffer();
               coff = cb.getEnd();
               carr = cb.getBuffer();
               tmp_cb = CharBuffer.wrap(carr, coff, carr.length - coff);
               cr = this.decoder.decode(tmp_bb, tmp_cb, false);
               cb.setEnd(tmp_cb.position());
            }

            bb.setStart(tmp_bb.position());
            if (tmp_bb.hasRemaining()) {
               this.remainder.put(tmp_bb);
            }

            if (cr != CoderResult.UNDERFLOW) {
               throw new IOException("Encoding error");
            }
         } catch (IOException var14) {
            int debug = 0;
            if (debug > 0) {
               this.log("B2CConverter " + var14.toString());
            }

            this.decoder.reset();
            throw var14;
         }
      }
   }

   public static void convertASCII(MessageBytes mb) {
      if (IS_OLD_IO_MODE) {
         B2CConverterBlocking.convertASCII(mb);
      } else if (mb.getType() == 2) {
         ByteChunk bc = mb.getByteChunk();
         CharChunk cc = mb.getCharChunk();
         int length = bc.getLength();
         cc.allocate(length, -1);
         byte[] bbuf = bc.getBuffer();
         char[] cbuf = cc.getBuffer();
         int start = bc.getStart();

         for(int i = 0; i < length; ++i) {
            cbuf[i] = (char)(bbuf[i + start] & 255);
         }

         mb.setChars(cbuf, 0, length);
      }
   }

   public void reset() throws IOException {
      if (IS_OLD_IO_MODE) {
         this.blockingConverter.reset();
      } else {
         if (this.decoder != null) {
            this.decoder.reset();
            this.remainder.clear();
         }

      }
   }

   void log(String s) {
      if (logger.isLoggable(Level.FINEST)) {
         logger.log(Level.FINEST, "B2CConverter: " + s);
      }

   }

   private void flushRemainder(ByteBuffer tmp_bb, CharBuffer tmp_cb) {
      while(true) {
         if (this.remainder.position() > 0 && tmp_bb.hasRemaining()) {
            this.remainder.put(tmp_bb.get());
            this.remainder.flip();
            CoderResult cr = this.decoder.decode(this.remainder, tmp_cb, false);
            if (cr == CoderResult.OVERFLOW) {
               throw new IllegalStateException("CharChunk is not big enough");
            }

            if (this.remainder.hasRemaining()) {
               this.remainder.compact();
               continue;
            }

            this.remainder.clear();
         }

         return;
      }
   }
}
