package org.apache.xmlbeans.impl.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class SniffedXmlInputStream extends BufferedInputStream {
   public static int MAX_SNIFFED_BYTES = 192;
   private static Charset dummy1 = Charset.forName("UTF-8");
   private static Charset dummy2 = Charset.forName("UTF-16");
   private static Charset dummy3 = Charset.forName("UTF-16BE");
   private static Charset dummy4 = Charset.forName("UTF-16LE");
   private static Charset dummy5 = Charset.forName("ISO-8859-1");
   private static Charset dummy6 = Charset.forName("US-ASCII");
   private static Charset dummy7 = Charset.forName("Cp1252");
   private String _encoding = this.sniffFourBytes();
   private static char[] WHITESPACE = new char[]{' ', '\r', '\t', '\n'};
   private static char[] NOTNAME = new char[]{'=', ' ', '\r', '\t', '\n', '?', '>', '<', '\'', '"'};

   public SniffedXmlInputStream(InputStream stream) throws IOException {
      super(stream);
      if (this._encoding != null && this._encoding.equals("IBM037")) {
         String encoding = this.sniffForXmlDecl(this._encoding);
         if (encoding != null) {
            this._encoding = encoding;
         }
      }

      if (this._encoding == null) {
         this._encoding = this.sniffForXmlDecl("UTF-8");
      }

      if (this._encoding == null) {
         this._encoding = "UTF-8";
      }

   }

   private int readAsMuchAsPossible(byte[] buf, int startAt, int len) throws IOException {
      int total;
      int count;
      for(total = 0; total < len; total += count) {
         count = this.read(buf, startAt + total, len - total);
         if (count < 0) {
            break;
         }
      }

      return total;
   }

   private String sniffFourBytes() throws IOException {
      this.mark(4);
      int skip = false;

      String var5;
      try {
         byte[] buf = new byte[4];
         if (this.readAsMuchAsPossible(buf, 0, 4) < 4) {
            Object var9 = null;
            return (String)var9;
         }

         long result = (long)(-16777216 & buf[0] << 24 | 16711680 & buf[1] << 16 | '\uff00' & buf[2] << 8 | 255 & buf[3]);
         if (result == 65279L) {
            var5 = "UCS-4";
            return var5;
         }

         if (result == -131072L) {
            var5 = "UCS-4";
            return var5;
         }

         if (result == 60L) {
            var5 = "UCS-4BE";
            return var5;
         }

         if (result == 1006632960L) {
            var5 = "UCS-4LE";
            return var5;
         }

         if (result == 3932223L) {
            var5 = "UTF-16BE";
            return var5;
         }

         if (result == 1006649088L) {
            var5 = "UTF-16LE";
            return var5;
         }

         if (result == 1010792557L) {
            var5 = null;
            return var5;
         }

         if (result == 1282385812L) {
            var5 = "IBM037";
            return var5;
         }

         if ((result & -65536L) != -16842752L) {
            if ((result & -65536L) == -131072L) {
               var5 = "UTF-16";
               return var5;
            }

            if ((result & -256L) == -272908544L) {
               var5 = "UTF-8";
               return var5;
            }

            var5 = null;
            return var5;
         }

         var5 = "UTF-16";
      } finally {
         this.reset();
      }

      return var5;
   }

   private String sniffForXmlDecl(String encoding) throws IOException {
      this.mark(MAX_SNIFFED_BYTES);

      try {
         byte[] bytebuf = new byte[MAX_SNIFFED_BYTES];
         int bytelimit = this.readAsMuchAsPossible(bytebuf, 0, MAX_SNIFFED_BYTES);
         Charset charset = Charset.forName(encoding);
         Reader reader = new InputStreamReader(new ByteArrayInputStream(bytebuf, 0, bytelimit), charset);
         char[] buf = new char[bytelimit];
         int limit = 0;

         while(true) {
            if (limit < bytelimit) {
               int count = reader.read(buf, limit, bytelimit - limit);
               if (count >= 0) {
                  limit += count;
                  continue;
               }
            }

            String var12 = extractXmlDeclEncoding(buf, 0, limit);
            return var12;
         }
      } finally {
         this.reset();
      }
   }

   public String getXmlEncoding() {
      return this._encoding;
   }

   static String extractXmlDeclEncoding(char[] buf, int offset, int size) {
      int limit = offset + size;
      int xmlpi = firstIndexOf("<?xml", buf, offset, limit);
      if (xmlpi >= 0) {
         int i = xmlpi + 5;
         ScannedAttribute attr = new ScannedAttribute();

         while(i < limit) {
            i = scanAttribute(buf, i, limit, attr);
            if (i < 0) {
               return null;
            }

            if (attr.name.equals("encoding")) {
               return attr.value;
            }
         }
      }

      return null;
   }

   private static int firstIndexOf(String s, char[] buf, int startAt, int limit) {
      assert s.length() > 0;

      char[] lookFor = s.toCharArray();
      char firstchar = lookFor[0];

      label33:
      for(limit -= lookFor.length; startAt < limit; ++startAt) {
         if (buf[startAt] == firstchar) {
            for(int i = 1; i < lookFor.length; ++i) {
               if (buf[startAt + i] != lookFor[i]) {
                  continue label33;
               }
            }

            return startAt;
         }
      }

      return -1;
   }

   private static int nextNonmatchingByte(char[] lookFor, char[] buf, int startAt, int limit) {
      label21:
      while(true) {
         if (startAt < limit) {
            int thischar = buf[startAt];

            for(int i = 0; i < lookFor.length; ++i) {
               if (thischar == lookFor[i]) {
                  ++startAt;
                  continue label21;
               }
            }

            return startAt;
         }

         return -1;
      }
   }

   private static int nextMatchingByte(char[] lookFor, char[] buf, int startAt, int limit) {
      while(startAt < limit) {
         int thischar = buf[startAt];

         for(int i = 0; i < lookFor.length; ++i) {
            if (thischar == lookFor[i]) {
               return startAt;
            }
         }

         ++startAt;
      }

      return -1;
   }

   private static int nextMatchingByte(char lookFor, char[] buf, int startAt, int limit) {
      while(startAt < limit) {
         if (buf[startAt] == lookFor) {
            return startAt;
         }

         ++startAt;
      }

      return -1;
   }

   private static int scanAttribute(char[] buf, int startAt, int limit, ScannedAttribute attr) {
      int nameStart = nextNonmatchingByte(WHITESPACE, buf, startAt, limit);
      if (nameStart < 0) {
         return -1;
      } else {
         int nameEnd = nextMatchingByte(NOTNAME, buf, nameStart, limit);
         if (nameEnd < 0) {
            return -1;
         } else {
            int equals = nextNonmatchingByte(WHITESPACE, buf, nameEnd, limit);
            if (equals < 0) {
               return -1;
            } else if (buf[equals] != '=') {
               return -1;
            } else {
               int valQuote = nextNonmatchingByte(WHITESPACE, buf, equals + 1, limit);
               if (buf[valQuote] != '\'' && buf[valQuote] != '"') {
                  return -1;
               } else {
                  int valEndquote = nextMatchingByte(buf[valQuote], buf, valQuote + 1, limit);
                  if (valEndquote < 0) {
                     return -1;
                  } else {
                     attr.name = new String(buf, nameStart, nameEnd - nameStart);
                     attr.value = new String(buf, valQuote + 1, valEndquote - valQuote - 1);
                     return valEndquote + 1;
                  }
               }
            }
         }
      }
   }

   private static class ScannedAttribute {
      public String name;
      public String value;

      private ScannedAttribute() {
      }

      // $FF: synthetic method
      ScannedAttribute(Object x0) {
         this();
      }
   }
}
