package org.glassfish.grizzly.http.util;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public final class UEncoder {
   private static final Logger logger = Grizzly.logger(UEncoder.class);
   private static final BitSet initialSafeChars = new BitSet(128);
   private final BitSet safeChars;
   private C2BConverter c2b;
   private ByteChunk bb;
   private String encoding = "UTF8";
   private static final int debug = 0;

   public UEncoder() {
      this.safeChars = (BitSet)initialSafeChars.clone();
   }

   public void setEncoding(String s) {
      this.encoding = s;
   }

   public void addSafeCharacter(char c) {
      this.safeChars.set(c);
   }

   public void urlEncode(Writer buf, String s) throws IOException {
      this.urlEncode(buf, s, false);
   }

   public void urlEncode(Writer buf, String s, boolean toHexUpperCase) throws IOException {
      if (this.c2b == null) {
         this.bb = new ByteChunk(16);
         this.c2b = C2BConverter.getInstance(this.bb, this.encoding);
      }

      for(int i = 0; i < s.length(); ++i) {
         int c = s.charAt(i);
         if (this.safeChars.get(c)) {
            buf.write((char)c);
         } else {
            this.c2b.convert((char)c);
            if (c >= '\ud800' && c <= '\udbff' && i + 1 < s.length()) {
               int d = s.charAt(i + 1);
               if (d >= '\udc00' && d <= '\udfff') {
                  this.c2b.convert((char)d);
                  ++i;
               }
            }

            this.urlEncode(buf, this.bb.getBuffer(), this.bb.getStart(), this.bb.getLength(), toHexUpperCase);
            this.bb.recycle();
         }
      }

   }

   public void urlEncode(Writer buf, byte[] bytes, int off, int len) throws IOException {
      this.urlEncode(buf, bytes, off, len, false);
   }

   public void urlEncode(Writer buf, byte[] bytes, int off, int len, boolean toHexUpperCase) throws IOException {
      for(int j = off; j < len; ++j) {
         buf.write(37);
         char ch = Character.forDigit(bytes[j] >> 4 & 15, 16);
         if (toHexUpperCase) {
            ch = Character.toUpperCase(ch);
         }

         buf.write(ch);
         ch = Character.forDigit(bytes[j] & 15, 16);
         if (toHexUpperCase) {
            ch = Character.toUpperCase(ch);
         }

         buf.write(ch);
      }

   }

   public String encodeURL(String url) {
      return this.encodeURL(url, false);
   }

   public String encodeURL(String uri, boolean toHexUpperCase) {
      String outUri = null;

      try {
         CharArrayWriter out = new CharArrayWriter();
         this.urlEncode(out, uri, toHexUpperCase);
         outUri = out.toString();
      } catch (IOException var5) {
      }

      return outUri;
   }

   private static void initSafeChars() {
      int i;
      for(i = 97; i <= 122; ++i) {
         initialSafeChars.set(i);
      }

      for(i = 65; i <= 90; ++i) {
         initialSafeChars.set(i);
      }

      for(i = 48; i <= 57; ++i) {
         initialSafeChars.set(i);
      }

      initialSafeChars.set(36);
      initialSafeChars.set(45);
      initialSafeChars.set(95);
      initialSafeChars.set(46);
      initialSafeChars.set(33);
      initialSafeChars.set(42);
      initialSafeChars.set(39);
      initialSafeChars.set(40);
      initialSafeChars.set(41);
      initialSafeChars.set(44);
   }

   private static void log(String s) {
      if (logger.isLoggable(Level.FINE)) {
         logger.fine(s);
      }

   }

   static {
      initSafeChars();
   }
}
