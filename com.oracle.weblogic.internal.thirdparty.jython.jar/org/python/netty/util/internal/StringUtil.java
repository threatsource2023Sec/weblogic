package org.python.netty.util.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class StringUtil {
   public static final String EMPTY_STRING = "";
   public static final String NEWLINE = SystemPropertyUtil.get("line.separator", "\n");
   public static final char DOUBLE_QUOTE = '"';
   public static final char COMMA = ',';
   public static final char LINE_FEED = '\n';
   public static final char CARRIAGE_RETURN = '\r';
   public static final char TAB = '\t';
   public static final char SPACE = ' ';
   private static final String[] BYTE2HEX_PAD = new String[256];
   private static final String[] BYTE2HEX_NOPAD = new String[256];
   private static final int CSV_NUMBER_ESCAPE_CHARACTERS = 7;
   private static final char PACKAGE_SEPARATOR_CHAR = '.';

   private StringUtil() {
   }

   public static String substringAfter(String value, char delim) {
      int pos = value.indexOf(delim);
      return pos >= 0 ? value.substring(pos + 1) : null;
   }

   public static boolean commonSuffixOfLength(String s, String p, int len) {
      return s != null && p != null && len >= 0 && s.regionMatches(s.length() - len, p, p.length() - len, len);
   }

   public static String byteToHexStringPadded(int value) {
      return BYTE2HEX_PAD[value & 255];
   }

   public static Appendable byteToHexStringPadded(Appendable buf, int value) {
      try {
         buf.append(byteToHexStringPadded(value));
      } catch (IOException var3) {
         PlatformDependent.throwException(var3);
      }

      return buf;
   }

   public static String toHexStringPadded(byte[] src) {
      return toHexStringPadded(src, 0, src.length);
   }

   public static String toHexStringPadded(byte[] src, int offset, int length) {
      return ((StringBuilder)toHexStringPadded(new StringBuilder(length << 1), src, offset, length)).toString();
   }

   public static Appendable toHexStringPadded(Appendable dst, byte[] src) {
      return toHexStringPadded(dst, src, 0, src.length);
   }

   public static Appendable toHexStringPadded(Appendable dst, byte[] src, int offset, int length) {
      int end = offset + length;

      for(int i = offset; i < end; ++i) {
         byteToHexStringPadded(dst, src[i]);
      }

      return dst;
   }

   public static String byteToHexString(int value) {
      return BYTE2HEX_NOPAD[value & 255];
   }

   public static Appendable byteToHexString(Appendable buf, int value) {
      try {
         buf.append(byteToHexString(value));
      } catch (IOException var3) {
         PlatformDependent.throwException(var3);
      }

      return buf;
   }

   public static String toHexString(byte[] src) {
      return toHexString(src, 0, src.length);
   }

   public static String toHexString(byte[] src, int offset, int length) {
      return ((StringBuilder)toHexString(new StringBuilder(length << 1), src, offset, length)).toString();
   }

   public static Appendable toHexString(Appendable dst, byte[] src) {
      return toHexString(dst, src, 0, src.length);
   }

   public static Appendable toHexString(Appendable dst, byte[] src, int offset, int length) {
      assert length >= 0;

      if (length == 0) {
         return dst;
      } else {
         int end = offset + length;
         int endMinusOne = end - 1;

         int i;
         for(i = offset; i < endMinusOne && src[i] == 0; ++i) {
         }

         byteToHexString(dst, src[i++]);
         int remaining = end - i;
         toHexStringPadded(dst, src, i, remaining);
         return dst;
      }
   }

   public static String simpleClassName(Object o) {
      return o == null ? "null_object" : simpleClassName(o.getClass());
   }

   public static String simpleClassName(Class clazz) {
      String className = ((Class)ObjectUtil.checkNotNull(clazz, "clazz")).getName();
      int lastDotIdx = className.lastIndexOf(46);
      return lastDotIdx > -1 ? className.substring(lastDotIdx + 1) : className;
   }

   public static CharSequence escapeCsv(CharSequence value) {
      return escapeCsv(value, false);
   }

   public static CharSequence escapeCsv(CharSequence value, boolean trimWhiteSpace) {
      int length = ((CharSequence)ObjectUtil.checkNotNull(value, "value")).length();
      if (length == 0) {
         return value;
      } else {
         int start = 0;
         int last = length - 1;
         boolean trimmed = false;
         if (trimWhiteSpace) {
            start = indexOfFirstNonOwsChar(value, length);
            if (start == length) {
               return "";
            }

            last = indexOfLastNonOwsChar(value, start, length);
            trimmed = start > 0 || last < length - 1;
            if (trimmed) {
               length = last - start + 1;
            }
         }

         StringBuilder result = new StringBuilder(length + 7);
         boolean quoted = isDoubleQuote(value.charAt(start)) && isDoubleQuote(value.charAt(last)) && length != 1;
         boolean foundSpecialCharacter = false;
         boolean escapedDoubleQuote = false;

         for(int i = start; i <= last; ++i) {
            char current = value.charAt(i);
            switch (current) {
               case '"':
                  if (i != start && i != last) {
                     boolean isNextCharDoubleQuote = isDoubleQuote(value.charAt(i + 1));
                     if (!isDoubleQuote(value.charAt(i - 1)) && (!isNextCharDoubleQuote || i + 1 == last)) {
                        result.append('"');
                        escapedDoubleQuote = true;
                     }
                     break;
                  } else {
                     if (quoted) {
                        continue;
                     }

                     result.append('"');
                  }
               case '\n':
               case '\r':
               case ',':
                  foundSpecialCharacter = true;
            }

            result.append(current);
         }

         if (!escapedDoubleQuote && (!foundSpecialCharacter || quoted)) {
            if (trimmed) {
               return quoted ? quote(result) : result;
            } else {
               return value;
            }
         } else {
            return quote(result);
         }
      }
   }

   private static StringBuilder quote(StringBuilder builder) {
      return builder.insert(0, '"').append('"');
   }

   public static CharSequence unescapeCsv(CharSequence value) {
      int length = ((CharSequence)ObjectUtil.checkNotNull(value, "value")).length();
      if (length == 0) {
         return value;
      } else {
         int last = length - 1;
         boolean quoted = isDoubleQuote(value.charAt(0)) && isDoubleQuote(value.charAt(last)) && length != 1;
         if (!quoted) {
            validateCsvFormat(value);
            return value;
         } else {
            StringBuilder unescaped = InternalThreadLocalMap.get().stringBuilder();

            for(int i = 1; i < last; ++i) {
               char current = value.charAt(i);
               if (current == '"') {
                  if (!isDoubleQuote(value.charAt(i + 1)) || i + 1 == last) {
                     throw newInvalidEscapedCsvFieldException(value, i);
                  }

                  ++i;
               }

               unescaped.append(current);
            }

            return unescaped.toString();
         }
      }
   }

   public static List unescapeCsvFields(CharSequence value) {
      List unescaped = new ArrayList(2);
      StringBuilder current = InternalThreadLocalMap.get().stringBuilder();
      boolean quoted = false;
      int last = value.length() - 1;

      for(int i = 0; i <= last; ++i) {
         char c = value.charAt(i);
         if (quoted) {
            switch (c) {
               case '"':
                  if (i == last) {
                     unescaped.add(current.toString());
                     return unescaped;
                  }

                  ++i;
                  char next = value.charAt(i);
                  if (next == '"') {
                     current.append('"');
                  } else {
                     if (next != ',') {
                        throw newInvalidEscapedCsvFieldException(value, i - 1);
                     }

                     quoted = false;
                     unescaped.add(current.toString());
                     current.setLength(0);
                  }
                  break;
               default:
                  current.append(c);
            }
         } else {
            switch (c) {
               case '\n':
               case '\r':
                  throw newInvalidEscapedCsvFieldException(value, i);
               case '"':
                  if (current.length() != 0) {
                     throw newInvalidEscapedCsvFieldException(value, i);
                  }

                  quoted = true;
                  break;
               case ',':
                  unescaped.add(current.toString());
                  current.setLength(0);
                  break;
               default:
                  current.append(c);
            }
         }
      }

      if (quoted) {
         throw newInvalidEscapedCsvFieldException(value, last);
      } else {
         unescaped.add(current.toString());
         return unescaped;
      }
   }

   private static void validateCsvFormat(CharSequence value) {
      int length = value.length();
      int i = 0;

      while(i < length) {
         switch (value.charAt(i)) {
            case '\n':
            case '\r':
            case '"':
            case ',':
               throw newInvalidEscapedCsvFieldException(value, i);
            default:
               ++i;
         }
      }

   }

   private static IllegalArgumentException newInvalidEscapedCsvFieldException(CharSequence value, int index) {
      return new IllegalArgumentException("invalid escaped CSV field: " + value + " index: " + index);
   }

   public static int length(String s) {
      return s == null ? 0 : s.length();
   }

   public static boolean isNullOrEmpty(String s) {
      return s == null || s.isEmpty();
   }

   public static int indexOfNonWhiteSpace(CharSequence seq, int offset) {
      while(offset < seq.length()) {
         if (!Character.isWhitespace(seq.charAt(offset))) {
            return offset;
         }

         ++offset;
      }

      return -1;
   }

   public static boolean isSurrogate(char c) {
      return c >= '\ud800' && c <= '\udfff';
   }

   private static boolean isDoubleQuote(char c) {
      return c == '"';
   }

   public static boolean endsWith(CharSequence s, char c) {
      int len = s.length();
      return len > 0 && s.charAt(len - 1) == c;
   }

   public static CharSequence trimOws(CharSequence value) {
      int length = value.length();
      if (length == 0) {
         return value;
      } else {
         int start = indexOfFirstNonOwsChar(value, length);
         int end = indexOfLastNonOwsChar(value, start, length);
         return start == 0 && end == length - 1 ? value : value.subSequence(start, end + 1);
      }
   }

   private static int indexOfFirstNonOwsChar(CharSequence value, int length) {
      int i;
      for(i = 0; i < length && isOws(value.charAt(i)); ++i) {
      }

      return i;
   }

   private static int indexOfLastNonOwsChar(CharSequence value, int start, int length) {
      int i;
      for(i = length - 1; i > start && isOws(value.charAt(i)); --i) {
      }

      return i;
   }

   private static boolean isOws(char c) {
      return c == ' ' || c == '\t';
   }

   static {
      int i;
      for(i = 0; i < 10; ++i) {
         BYTE2HEX_PAD[i] = "0" + i;
         BYTE2HEX_NOPAD[i] = String.valueOf(i);
      }

      while(i < 16) {
         char c = (char)(97 + i - 10);
         BYTE2HEX_PAD[i] = "0" + c;
         BYTE2HEX_NOPAD[i] = String.valueOf(c);
         ++i;
      }

      while(i < BYTE2HEX_PAD.length) {
         String str = Integer.toHexString(i);
         BYTE2HEX_PAD[i] = str;
         BYTE2HEX_NOPAD[i] = str;
         ++i;
      }

   }
}
