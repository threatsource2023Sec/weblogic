package com.sun.faces.util;

import com.sun.faces.config.WebConfiguration;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class HtmlUtils {
   private static final Set UTF_CHARSET = new HashSet(Arrays.asList("UTF-8", "UTF-16", "UTF-16BE", "UTF-16LE", "UTF-32", "UTF-32BE", "UTF-32LE", "x-UTF-16LE-BOM", "X-UTF-32BE-BOM", "X-UTF-32LE-BOM", ""));
   private static final char[] AMP_CHARS = "&amp;".toCharArray();
   private static final char[] QUOT_CHARS = "&quot;".toCharArray();
   private static final char[] GT_CHARS = "&gt;".toCharArray();
   private static final char[] LT_CHARS = "&lt;".toCharArray();
   private static final char[] EURO_CHARS = "&euro;".toCharArray();
   private static final char[] DEC_REF_START = "&#".toCharArray();
   private static final int MAX_BYTES_PER_CHAR = 10;
   private static final BitSet DONT_ENCODE_SET = new BitSet(256);
   private static char[][] sISO8859_1_Entities;
   private static char _LAST_EMPTY_ELEMENT_START;
   private static String[][] emptyElementArr;
   private static String[] aNames;
   private static String[] bNames;
   private static String[] cNames;
   private static String[] fNames;
   private static String[] hNames;
   private static String[] iNames;
   private static String[] lNames;
   private static String[] mNames;
   private static String[] pNames;

   public static void writeText(Writer out, boolean escapeUnicode, boolean escapeIsocode, char[] buffer, char[] text) throws IOException {
      writeText(out, escapeUnicode, escapeIsocode, buffer, text, 0, text.length);
   }

   public static void writeText(Writer out, boolean escapeUnicode, boolean escapeIsocode, char[] buff, char[] text, int start, int length) throws IOException {
      int buffLength = buff.length;
      int buffIndex = 0;
      int end = start + length;

      for(int i = start; i < end; ++i) {
         buffIndex = writeTextChar(out, escapeUnicode, escapeIsocode, text[i], buffIndex, buff, buffLength);
      }

      flushBuffer(out, buff, buffIndex);
   }

   public static void writeText(Writer out, boolean escapeUnicode, boolean escapeIsocode, char[] buff, String text, char[] textBuff) throws IOException {
      int length = text.length();
      if (length >= 16) {
         text.getChars(0, length, textBuff, 0);
         writeText(out, escapeUnicode, escapeIsocode, buff, textBuff, 0, length);
      } else {
         int buffLength = buff.length;
         int buffIndex = 0;

         for(int i = 0; i < length; ++i) {
            char ch = text.charAt(i);
            buffIndex = writeTextChar(out, escapeUnicode, escapeIsocode, ch, buffIndex, buff, buffLength);
         }

         flushBuffer(out, buff, buffIndex);
      }

   }

   private static int writeTextChar(Writer out, boolean escapeUnicode, boolean escapeIsocode, char ch, int buffIndex, char[] buff, int buffLength) throws IOException {
      if (ch <= 31 && !isPrintableControlChar(ch)) {
         return buffIndex;
      } else {
         int nextIndex;
         if (ch < 160) {
            if (ch >= '?') {
               nextIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
            } else if (ch >= '\'') {
               if (ch < '<') {
                  nextIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
               } else if (ch == '<') {
                  nextIndex = addToBuffer(out, buff, buffIndex, buffLength, LT_CHARS);
               } else if (ch == '>') {
                  nextIndex = addToBuffer(out, buff, buffIndex, buffLength, GT_CHARS);
               } else {
                  nextIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
               }
            } else if (ch == '&') {
               nextIndex = addToBuffer(out, buff, buffIndex, buffLength, AMP_CHARS);
            } else if (ch == '"') {
               nextIndex = addToBuffer(out, buff, buffIndex, buffLength, QUOT_CHARS);
            } else {
               nextIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
            }
         } else if (ch <= 255) {
            if (escapeIsocode) {
               nextIndex = addToBuffer(out, buff, buffIndex, buffLength, sISO8859_1_Entities[ch - 160]);
            } else {
               nextIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
            }
         } else if (escapeUnicode) {
            nextIndex = _writeDecRef(out, buff, buffIndex, buffLength, ch);
         } else {
            nextIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
         }

         return nextIndex;
      }
   }

   public static void writeAttribute(Writer out, boolean escapeUnicode, boolean escapeIsocode, char[] buff, String text, char[] textBuff, boolean isScriptInAttributeValueEnabled) throws IOException {
      int length = text.length();
      if (length >= 16) {
         if (length > textBuff.length) {
            textBuff = new char[length * 2];
         }

         text.getChars(0, length, textBuff, 0);
         writeAttribute(out, escapeUnicode, escapeIsocode, buff, textBuff, 0, length, isScriptInAttributeValueEnabled);
      } else {
         int buffLength = buff.length;
         int buffIndex = 0;

         for(int i = 0; i < length; ++i) {
            char ch = text.charAt(i);
            if (ch > 31 || isPrintableControlChar(ch)) {
               if (ch < 160) {
                  if (ch >= '?') {
                     if (ch == 's' && !isScriptInAttributeValueEnabled && i + 6 < text.length() && 'c' == text.charAt(i + 1) && 'r' == text.charAt(i + 2) && 'i' == text.charAt(i + 3) && 'p' == text.charAt(i + 4) && 't' == text.charAt(i + 5) && ':' == text.charAt(i + 6)) {
                        return;
                     }

                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
                  } else if (ch >= '\'') {
                     if (ch < '<') {
                        buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
                     } else if (ch == '<') {
                        buffIndex = addToBuffer(out, buff, buffIndex, buffLength, LT_CHARS);
                     } else if (ch == '>') {
                        buffIndex = addToBuffer(out, buff, buffIndex, buffLength, GT_CHARS);
                     } else {
                        buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
                     }
                  } else if (ch == '&') {
                     if (i + 1 < length && text.charAt(i + 1) == '{') {
                        buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
                     } else {
                        buffIndex = addToBuffer(out, buff, buffIndex, buffLength, AMP_CHARS);
                     }
                  } else if (ch == '"') {
                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, QUOT_CHARS);
                  } else {
                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
                  }
               } else if (ch <= 255) {
                  if (escapeIsocode) {
                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, sISO8859_1_Entities[ch - 160]);
                  } else {
                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
                  }
               } else if (escapeUnicode) {
                  buffIndex = _writeDecRef(out, buff, buffIndex, buffLength, ch);
               } else {
                  buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
               }
            }
         }

         flushBuffer(out, buff, buffIndex);
      }

   }

   public static void writeAttribute(Writer out, boolean escapeUnicode, boolean escapeIsocode, char[] buffer, char[] text) throws IOException {
      writeAttribute(out, escapeUnicode, escapeIsocode, buffer, text, 0, text.length, WebConfiguration.BooleanWebContextInitParameter.EnableScriptInAttributeValue.getDefaultValue());
   }

   public static void writeAttribute(Writer out, boolean escapeUnicode, boolean escapeIsocode, char[] buff, char[] text, int start, int length, boolean isScriptInAttributeValueEnabled) throws IOException {
      int buffLength = buff.length;
      int buffIndex = 0;
      int end = start + length;

      for(int i = start; i < end; ++i) {
         char ch = text[i];
         if (ch > 31 || isPrintableControlChar(ch)) {
            if (ch < 160) {
               if (ch >= '?') {
                  if (ch == 's' && !isScriptInAttributeValueEnabled && i + 6 < text.length && 'c' == text[i + 1] && 'r' == text[i + 2] && 'i' == text[i + 3] && 'p' == text[i + 4] && 't' == text[i + 5] && ':' == text[i + 6]) {
                     return;
                  }

                  buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
               } else if (ch >= '\'') {
                  if (ch < '<') {
                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
                  } else if (ch == '<') {
                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, LT_CHARS);
                  } else if (ch == '>') {
                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, GT_CHARS);
                  } else {
                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
                  }
               } else if (ch == '&') {
                  if (i + 1 < end && text[i + 1] == '{') {
                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
                  } else {
                     buffIndex = addToBuffer(out, buff, buffIndex, buffLength, AMP_CHARS);
                  }
               } else if (ch == '"') {
                  buffIndex = addToBuffer(out, buff, buffIndex, buffLength, QUOT_CHARS);
               } else {
                  buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
               }
            } else if (ch <= 255) {
               if (escapeIsocode) {
                  buffIndex = addToBuffer(out, buff, buffIndex, buffLength, sISO8859_1_Entities[ch - 160]);
               } else {
                  buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
               }
            } else if (escapeUnicode) {
               buffIndex = _writeDecRef(out, buff, buffIndex, buffLength, ch);
            } else {
               buffIndex = addToBuffer(out, buff, buffIndex, buffLength, ch);
            }
         }
      }

      flushBuffer(out, buff, buffIndex);
   }

   private static int _writeDecRef(Writer out, char[] buffer, int bufferIndex, int bufferLength, char ch) throws IOException {
      if (ch == 8364) {
         bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, EURO_CHARS);
         return bufferIndex;
      } else {
         bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, DEC_REF_START);
         int i;
         if (ch > 10000) {
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + ch / 10000));
            i = ch % 10000;
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + i / 1000));
            i %= 1000;
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + i / 100));
            i %= 100;
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + i / 10));
            i %= 10;
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + i));
         } else if (ch > 1000) {
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + ch / 1000));
            i = ch % 1000;
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + i / 100));
            i %= 100;
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + i / 10));
            i %= 10;
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + i));
         } else {
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + ch / 100));
            i = ch % 100;
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + i / 10));
            i %= 10;
            bufferIndex = addToBuffer(out, buffer, bufferIndex, bufferLength, (char)(48 + i));
         }

         return addToBuffer(out, buffer, bufferIndex, bufferLength, ';');
      }
   }

   private static int addToBuffer(Writer out, char[] buffer, int bufferIndex, int bufferLength, char ch) throws IOException {
      if (bufferIndex >= bufferLength) {
         out.write(buffer, 0, bufferIndex);
         bufferIndex = 0;
      }

      buffer[bufferIndex] = ch;
      return bufferIndex + 1;
   }

   private static int addToBuffer(Writer out, char[] buffer, int bufferIndex, int bufferLength, char[] toAdd) throws IOException {
      if (bufferIndex >= bufferLength || toAdd.length + bufferIndex >= bufferLength) {
         out.write(buffer, 0, bufferIndex);
         bufferIndex = 0;
      }

      System.arraycopy(toAdd, 0, buffer, bufferIndex, toAdd.length);
      return bufferIndex + toAdd.length;
   }

   private static int flushBuffer(Writer out, char[] buffer, int bufferIndex) throws IOException {
      if (bufferIndex > 0) {
         out.write(buffer, 0, bufferIndex);
      }

      return 0;
   }

   private HtmlUtils() {
   }

   public static void writeURL(Writer out, String text, char[] textBuff, String queryEncoding) throws IOException, UnsupportedEncodingException {
      int length = text.length();
      if (length >= 16) {
         text.getChars(0, length, textBuff, 0);
         writeURL(out, textBuff, 0, length, queryEncoding);
      } else {
         for(int i = 0; i < length; ++i) {
            char ch = text.charAt(i);
            if (ch >= '!' && ch <= '~') {
               if (ch == '"') {
                  out.write("%22");
               } else {
                  if (ch == '?') {
                     out.write(63);
                     encodeURIString(out, text, queryEncoding, i + 1);
                     return;
                  }

                  out.write(ch);
               }
            } else if (ch == ' ') {
               out.write(43);
            } else {
               writeURIDoubleHex(out, ch);
            }
         }
      }

   }

   public static void writeURL(Writer out, char[] textBuff, int start, int len, String queryEncoding) throws IOException, UnsupportedEncodingException {
      int end = start + len;

      for(int i = start; i < end; ++i) {
         char ch = textBuff[i];
         if (ch >= '!' && ch <= '~') {
            if (ch == '"') {
               out.write("%22");
            } else {
               if (ch == '?') {
                  out.write(63);
                  encodeURIString(out, textBuff, queryEncoding, i + 1, end);
                  return;
               }

               out.write(ch);
            }
         } else if (ch == ' ') {
            out.write(43);
         } else {
            writeURIDoubleHex(out, ch);
         }
      }

   }

   private static void encodeURIString(Writer out, String text, String encoding, int start) throws IOException {
      MyByteArrayOutputStream buf = null;
      OutputStreamWriter writer = null;
      char[] charArray = null;
      int length = text.length();

      for(int i = start; i < length; ++i) {
         char ch = text.charAt(i);
         if (DONT_ENCODE_SET.get(ch)) {
            if (ch == '&') {
               if (i + 1 < length && isAmpEscaped(text, i + 1)) {
                  out.write(ch);
               } else {
                  out.write(AMP_CHARS);
               }
            } else {
               out.write(ch);
            }
         } else {
            if (buf == null) {
               buf = new MyByteArrayOutputStream(10);
               if (encoding != null) {
                  writer = new OutputStreamWriter(buf, encoding);
               } else {
                  writer = new OutputStreamWriter(buf);
               }

               charArray = new char[1];
            }

            try {
               charArray[0] = ch;
               writer.write(charArray, 0, 1);
               writer.flush();
            } catch (IOException var13) {
               buf.reset();
               continue;
            }

            byte[] ba = buf.getBuf();
            int j = 0;

            for(int size = buf.size(); j < size; ++j) {
               writeURIDoubleHex(out, ba[j] + 256);
            }

            buf.reset();
         }
      }

   }

   private static void encodeURIString(Writer out, char[] textBuff, String encoding, int start, int end) throws IOException {
      MyByteArrayOutputStream buf = null;
      OutputStreamWriter writer = null;
      char[] charArray = null;

      for(int i = start; i < end; ++i) {
         char ch = textBuff[i];
         if (DONT_ENCODE_SET.get(ch)) {
            if (ch == '&') {
               if (i + 1 < end && isAmpEscaped(textBuff, i + 1)) {
                  out.write(ch);
               } else {
                  out.write(AMP_CHARS);
               }
            } else {
               out.write(ch);
            }
         } else {
            if (buf == null) {
               buf = new MyByteArrayOutputStream(10);
               if (encoding != null) {
                  writer = new OutputStreamWriter(buf, encoding);
               } else {
                  writer = new OutputStreamWriter(buf);
               }

               charArray = new char[1];
            }

            try {
               charArray[0] = ch;
               writer.write(charArray, 0, 1);
               writer.flush();
            } catch (IOException var13) {
               buf.reset();
               continue;
            }

            byte[] ba = buf.getBuf();
            int j = 0;

            for(int size = buf.size(); j < size; ++j) {
               writeURIDoubleHex(out, ba[j] + 256);
            }

            buf.reset();
         }
      }

   }

   private static boolean isPrintableControlChar(int ch) {
      return ch == 9 || ch == 10 || ch == 12 || ch == 13;
   }

   private static boolean isAmpEscaped(String text, int idx) {
      int i = 1;

      for(int ix = idx; i < AMP_CHARS.length; ++ix) {
         if (text.charAt(ix) != AMP_CHARS[i]) {
            return false;
         }

         ++i;
      }

      return true;
   }

   private static boolean isAmpEscaped(char[] text, int idx) {
      int i = 1;

      for(int ix = idx; i < AMP_CHARS.length; ++ix) {
         if (text[ix] != AMP_CHARS[i]) {
            return false;
         }

         ++i;
      }

      return true;
   }

   private static void writeURIDoubleHex(Writer out, int i) throws IOException {
      out.write(37);
      out.write(intToHex((i >> 4) % 16));
      out.write(intToHex(i % 16));
   }

   private static char intToHex(int i) {
      return i < 10 ? (char)(48 + i) : (char)(65 + (i - 10));
   }

   public static boolean validateEncoding(String encoding) {
      return Charset.isSupported(encoding);
   }

   public static boolean isISO8859_1encoding(String encoding) {
      return "ISO-8859-1".equals(encoding);
   }

   public static boolean isUTFencoding(String encoding) {
      return UTF_CHARSET.contains(encoding);
   }

   public static boolean isEmptyElement(String name) {
      char firstChar = name.charAt(0);
      if (firstChar > _LAST_EMPTY_ELEMENT_START) {
         return false;
      } else {
         String[] array = emptyElementArr[firstChar];
         if (array != null) {
            for(int i = array.length - 1; i >= 0; --i) {
               if (name.equalsIgnoreCase(array[i])) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   static {
      int i;
      for(i = 97; i <= 122; ++i) {
         DONT_ENCODE_SET.set(i);
      }

      for(i = 65; i <= 90; ++i) {
         DONT_ENCODE_SET.set(i);
      }

      for(i = 48; i <= 57; ++i) {
         DONT_ENCODE_SET.set(i);
      }

      DONT_ENCODE_SET.set(37);
      DONT_ENCODE_SET.set(43);
      DONT_ENCODE_SET.set(35);
      DONT_ENCODE_SET.set(38);
      DONT_ENCODE_SET.set(61);
      DONT_ENCODE_SET.set(45);
      DONT_ENCODE_SET.set(95);
      DONT_ENCODE_SET.set(46);
      DONT_ENCODE_SET.set(42);
      DONT_ENCODE_SET.set(126);
      DONT_ENCODE_SET.set(47);
      DONT_ENCODE_SET.set(39);
      DONT_ENCODE_SET.set(33);
      DONT_ENCODE_SET.set(40);
      DONT_ENCODE_SET.set(41);
      DONT_ENCODE_SET.set(59);
      sISO8859_1_Entities = new char[][]{"&nbsp;".toCharArray(), "&iexcl;".toCharArray(), "&cent;".toCharArray(), "&pound;".toCharArray(), "&curren;".toCharArray(), "&yen;".toCharArray(), "&brvbar;".toCharArray(), "&sect;".toCharArray(), "&uml;".toCharArray(), "&copy;".toCharArray(), "&ordf;".toCharArray(), "&laquo;".toCharArray(), "&not;".toCharArray(), "&shy;".toCharArray(), "&reg;".toCharArray(), "&macr;".toCharArray(), "&deg;".toCharArray(), "&plusmn;".toCharArray(), "&sup2;".toCharArray(), "&sup3;".toCharArray(), "&acute;".toCharArray(), "&micro;".toCharArray(), "&para;".toCharArray(), "&middot;".toCharArray(), "&cedil;".toCharArray(), "&sup1;".toCharArray(), "&ordm;".toCharArray(), "&raquo;".toCharArray(), "&frac14;".toCharArray(), "&frac12;".toCharArray(), "&frac34;".toCharArray(), "&iquest;".toCharArray(), "&Agrave;".toCharArray(), "&Aacute;".toCharArray(), "&Acirc;".toCharArray(), "&Atilde;".toCharArray(), "&Auml;".toCharArray(), "&Aring;".toCharArray(), "&AElig;".toCharArray(), "&Ccedil;".toCharArray(), "&Egrave;".toCharArray(), "&Eacute;".toCharArray(), "&Ecirc;".toCharArray(), "&Euml;".toCharArray(), "&Igrave;".toCharArray(), "&Iacute;".toCharArray(), "&Icirc;".toCharArray(), "&Iuml;".toCharArray(), "&ETH;".toCharArray(), "&Ntilde;".toCharArray(), "&Ograve;".toCharArray(), "&Oacute;".toCharArray(), "&Ocirc;".toCharArray(), "&Otilde;".toCharArray(), "&Ouml;".toCharArray(), "&times;".toCharArray(), "&Oslash;".toCharArray(), "&Ugrave;".toCharArray(), "&Uacute;".toCharArray(), "&Ucirc;".toCharArray(), "&Uuml;".toCharArray(), "&Yacute;".toCharArray(), "&THORN;".toCharArray(), "&szlig;".toCharArray(), "&agrave;".toCharArray(), "&aacute;".toCharArray(), "&acirc;".toCharArray(), "&atilde;".toCharArray(), "&auml;".toCharArray(), "&aring;".toCharArray(), "&aelig;".toCharArray(), "&ccedil;".toCharArray(), "&egrave;".toCharArray(), "&eacute;".toCharArray(), "&ecirc;".toCharArray(), "&euml;".toCharArray(), "&igrave;".toCharArray(), "&iacute;".toCharArray(), "&icirc;".toCharArray(), "&iuml;".toCharArray(), "&eth;".toCharArray(), "&ntilde;".toCharArray(), "&ograve;".toCharArray(), "&oacute;".toCharArray(), "&ocirc;".toCharArray(), "&otilde;".toCharArray(), "&ouml;".toCharArray(), "&divide;".toCharArray(), "&oslash;".toCharArray(), "&ugrave;".toCharArray(), "&uacute;".toCharArray(), "&ucirc;".toCharArray(), "&uuml;".toCharArray(), "&yacute;".toCharArray(), "&thorn;".toCharArray(), "&yuml;".toCharArray()};
      _LAST_EMPTY_ELEMENT_START = 'p';
      emptyElementArr = new String[_LAST_EMPTY_ELEMENT_START + 1][];
      aNames = new String[]{"area"};
      bNames = new String[]{"br", "base", "basefont"};
      cNames = new String[]{"col"};
      fNames = new String[]{"frame"};
      hNames = new String[]{"hr"};
      iNames = new String[]{"img", "input", "isindex"};
      lNames = new String[]{"link"};
      mNames = new String[]{"meta"};
      pNames = new String[]{"param"};
      emptyElementArr[97] = aNames;
      emptyElementArr[65] = aNames;
      emptyElementArr[98] = bNames;
      emptyElementArr[66] = bNames;
      emptyElementArr[99] = cNames;
      emptyElementArr[67] = cNames;
      emptyElementArr[102] = fNames;
      emptyElementArr[70] = fNames;
      emptyElementArr[104] = hNames;
      emptyElementArr[72] = hNames;
      emptyElementArr[105] = iNames;
      emptyElementArr[73] = iNames;
      emptyElementArr[108] = lNames;
      emptyElementArr[76] = lNames;
      emptyElementArr[109] = mNames;
      emptyElementArr[77] = mNames;
      emptyElementArr[112] = pNames;
      emptyElementArr[80] = pNames;
   }

   private static class MyByteArrayOutputStream extends ByteArrayOutputStream {
      public MyByteArrayOutputStream(int initialCapacity) {
         super(initialCapacity);
      }

      public byte[] getBuf() {
         return this.buf;
      }
   }
}
