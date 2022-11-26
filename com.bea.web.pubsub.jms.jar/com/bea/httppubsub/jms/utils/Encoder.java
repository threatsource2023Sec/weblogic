package com.bea.httppubsub.jms.utils;

import java.io.StringWriter;
import java.util.HashSet;

public class Encoder {
   private static final char[] HEXDIGITS = "0123456789ABCDEF".toCharArray();
   private static final String STAR_ENCODED;
   private static final String SLASH_ENCODED;
   private static final String[] stringArray;
   private static final int[] base16;

   public static String encode(String string) {
      StringBuilder sb = encode(string, string.length());
      return sb.toString();
   }

   public static String[] splitAndEncode(String string) {
      HashSet strings = new HashSet();
      int slashIndex = -5;
      string = encode(string, string.length()).toString();
      String sub = "";

      while(-1 < (slashIndex = string.indexOf(SLASH_ENCODED, slashIndex + 5))) {
         sub = string.substring(0, slashIndex);
         strings.add(sub + SLASH_ENCODED + STAR_ENCODED + STAR_ENCODED);
      }

      strings.add(sub + SLASH_ENCODED + STAR_ENCODED);
      if (string.length() != 0) {
         strings.add(string);
      }

      return (String[])strings.toArray(stringArray);
   }

   private static StringBuilder encode(String sr, int length_hint) {
      char[] writeBuffer = new char[5];
      StringBuilder sb = new StringBuilder(length_hint);
      int read_char = true;

      for(int ii = 0; ii < sr.length(); ++ii) {
         int read_char = sr.codePointAt(ii);
         if (read_char != 36 && Character.isJavaIdentifierStart(read_char)) {
            if (Character.isJavaIdentifierStart(read_char)) {
               sb.appendCodePoint(read_char);
            }
         } else {
            writeBuffer[0] = '$';
            writeBuffer[1] = HEXDIGITS[(read_char >> 16 & 255) / 16];
            writeBuffer[2] = HEXDIGITS[(read_char >> 16 & 255) % 16];
            writeBuffer[3] = HEXDIGITS[(read_char & 255) / 16];
            writeBuffer[4] = HEXDIGITS[(read_char & 255) % 16];
            sb.append(writeBuffer);
         }
      }

      return sb;
   }

   public static String decode(String str) {
      StringWriter sw = new StringWriter(str.length());

      for(int ii = 0; ii < str.length(); ++ii) {
         int read_char = str.codePointAt(ii);
         if (read_char != 36) {
            sw.write(read_char);
         } else {
            int value = 0;

            for(int jj = 0; jj < 4 && ii < str.length(); ++jj) {
               ++ii;
               int point = str.codePointAt(ii);
               point = point <= 57 && point >= 48 ? point - 48 : (point <= 70 && point >= 65 ? point - 65 + 10 : -1);
               value += point * base16[jj];
            }

            sw.write(value);
         }
      }

      return sw.toString();
   }

   static {
      STAR_ENCODED = "$" + HEXDIGITS[0] + HEXDIGITS[0] + HEXDIGITS[2] + HEXDIGITS[10];
      SLASH_ENCODED = "$" + HEXDIGITS[0] + HEXDIGITS[0] + HEXDIGITS[2] + HEXDIGITS[15];
      stringArray = new String[0];
      base16 = new int[]{4096, 256, 16, 1};
   }
}
