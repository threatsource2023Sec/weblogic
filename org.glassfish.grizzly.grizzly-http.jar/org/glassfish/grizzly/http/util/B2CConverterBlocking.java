package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.utils.Charsets;

public class B2CConverterBlocking {
   private static final Logger logger = Grizzly.logger(B2CConverterBlocking.class);
   private IntermediateInputStream iis;
   private ReadConverter conv;
   private String encoding;
   static final int BUFFER_SIZE = 8192;
   final char[] result = new char[8192];

   protected B2CConverterBlocking() {
   }

   public B2CConverterBlocking(String encoding) throws IOException {
      this.encoding = encoding;
      this.reset();
   }

   public void recycle() {
      this.conv.recycle();
   }

   /** @deprecated */
   public void convert(ByteChunk bb, CharChunk cb) throws IOException {
      this.convert(bb, cb, cb.getBuffer().length - cb.getEnd());
   }

   public void convert(ByteChunk bb, CharChunk cb, int limit) throws IOException {
      this.iis.setByteChunk(bb);
      int debug = 0;

      try {
         while(limit > 0) {
            int size = limit < 8192 ? limit : 8192;
            int bbLengthBeforeRead = bb.getLength();
            int cnt = this.conv.read(this.result, 0, size);
            if (cnt <= 0) {
               if (debug > 0) {
                  this.log("EOF");
               }

               return;
            }

            if (debug > 1) {
               this.log("Converted: " + new String(this.result, 0, cnt));
            }

            cb.append((char[])this.result, 0, cnt);
            limit -= bbLengthBeforeRead - bb.getLength();
         }

      } catch (IOException var8) {
         if (debug > 0) {
            this.log("Resetting the converter " + var8.toString());
         }

         this.reset();
         throw var8;
      }
   }

   public static void convertASCII(MessageBytes mb) {
      if (mb.getType() == 2) {
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
      this.iis = new IntermediateInputStream();
      this.conv = new ReadConverter(this.iis, Charsets.lookupCharset(this.encoding));
   }

   void log(String s) {
      if (logger.isLoggable(Level.FINEST)) {
         logger.log(Level.FINEST, "B2CConverter: " + s);
      }

   }
}
