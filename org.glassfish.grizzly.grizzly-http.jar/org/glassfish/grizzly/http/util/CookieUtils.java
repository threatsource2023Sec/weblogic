package org.glassfish.grizzly.http.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.glassfish.grizzly.Buffer;

public final class CookieUtils {
   public static final boolean COOKIE_VERSION_ONE_STRICT_COMPLIANCE = Boolean.getBoolean("org.glassfish.web.rfc2109_cookie_names_enforced");
   public static final boolean RFC_6265_SUPPORT_ENABLED = Boolean.getBoolean("org.glassfish.web.rfc_6265_support_enabled");
   public static final boolean ALWAYS_ADD_EXPIRES = Boolean.valueOf(System.getProperty("org.glassfish.grizzly.util.http.ServerCookie.ALWAYS_ADD_EXPIRES", "true"));
   static final char[] SEPARATORS = new char[]{'\t', ' ', '"', '\'', '(', ')', ',', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '{', '}'};
   static final boolean[] separators = new boolean[128];
   static final String OLD_COOKIE_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";
   public static final ThreadLocal OLD_COOKIE_FORMAT;
   static final String ancientDate;
   static final String tspecials = ",; ";
   static final String tspecials2 = "()<>@,;:\\\"/[]?={} \t";
   static final String tspecials2NoSlash = "()<>@,;:\\\"[]?={} \t";

   public static boolean isToken(String value) {
      return isToken(value, (String)null);
   }

   public static boolean isToken(String value, String literals) {
      String ts = literals == null ? ",; " : literals;
      if (value == null) {
         return true;
      } else {
         int len = value.length();

         for(int i = 0; i < len; ++i) {
            char c = value.charAt(i);
            if (ts.indexOf(c) != -1) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean containsCTL(String value, int version) {
      if (value == null) {
         return false;
      } else {
         int len = value.length();

         for(int i = 0; i < len; ++i) {
            char c = value.charAt(i);
            if ((c < ' ' || c >= 127) && c != '\t') {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean isToken2(String value) {
      return isToken2(value, (String)null);
   }

   public static boolean isToken2(String value, String literals) {
      String ts = literals == null ? "()<>@,;:\\\"/[]?={} \t" : literals;
      if (value == null) {
         return true;
      } else {
         int len = value.length();

         for(int i = 0; i < len; ++i) {
            char c = value.charAt(i);
            if (ts.indexOf(c) != -1) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean equals(String s, byte[] b, int start, int end) {
      int blen = end - start;
      if (b != null && blen == s.length()) {
         int boff = start;

         for(int i = 0; i < blen; ++i) {
            if (b[boff++] != s.charAt(i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean equals(String s, Buffer b, int start, int end) {
      int blen = end - start;
      if (b != null && blen == s.length()) {
         int boff = start;

         for(int i = 0; i < blen; ++i) {
            if (b.get(boff++) != s.charAt(i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean equals(String s1, String s2, int start, int end) {
      int blen = end - start;
      if (s2 != null && blen == s1.length()) {
         int boff = start;

         for(int i = 0; i < blen; ++i) {
            if (s2.charAt(boff++) != s1.charAt(i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean equalsIgnoreCase(String s, Buffer b, int start, int end) {
      int blen = end - start;
      if (b != null && blen == s.length()) {
         int boff = start;

         for(int i = 0; i < blen; ++i) {
            int b1 = Ascii.toLower(b.get(boff++));
            int b2 = Ascii.toLower(s.charAt(i));
            if (b1 != b2) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean equalsIgnoreCase(String s, byte[] b, int start, int end) {
      int blen = end - start;
      if (b != null && blen == s.length()) {
         int boff = start;

         for(int i = 0; i < blen; ++i) {
            int b1 = Ascii.toLower(b[boff++]);
            int b2 = Ascii.toLower(s.charAt(i));
            if (b1 != b2) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean equalsIgnoreCase(String s1, String s2, int start, int end) {
      int blen = end - start;
      if (s2 != null && blen == s1.length()) {
         int boff = start;

         for(int i = 0; i < blen; ++i) {
            int b1 = Ascii.toLower(s1.charAt(i));
            int b2 = Ascii.toLower(s2.charAt(boff++));
            if (b1 != b2) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean isSeparator(int c) {
      return isSeparator(c, true);
   }

   public static boolean isSeparator(int c, boolean parseAsVersion1) {
      if (parseAsVersion1) {
         return c > 0 && c < 126 && separators[c];
      } else {
         return c == 59 || c == 44;
      }
   }

   public static boolean isWhiteSpace(int c) {
      return c == 32 || c == 9 || c == 10 || c == 13 || c == 12;
   }

   public static int getTokenEndPosition(Buffer buffer, int off, int end) {
      return getTokenEndPosition(buffer, off, end, true);
   }

   public static int getTokenEndPosition(Buffer buffer, int off, int end, boolean parseAsVersion1) {
      int pos;
      for(pos = off; pos < end && !isSeparator(buffer.get(pos), parseAsVersion1); ++pos) {
      }

      return pos > end ? end : pos;
   }

   public static int getTokenEndPosition(byte[] bytes, int off, int end) {
      return getTokenEndPosition(bytes, off, end, true);
   }

   public static int getTokenEndPosition(byte[] bytes, int off, int end, boolean parseAsVersion1) {
      int pos;
      for(pos = off; pos < end && !isSeparator(bytes[pos], parseAsVersion1); ++pos) {
      }

      return pos > end ? end : pos;
   }

   public static int getTokenEndPosition(String s, int off, int end) {
      return getTokenEndPosition(s, off, end, true);
   }

   public static int getTokenEndPosition(String s, int off, int end, boolean parseAsVersion1) {
      int pos;
      for(pos = off; pos < end && !isSeparator(s.charAt(pos), parseAsVersion1); ++pos) {
      }

      return pos > end ? end : pos;
   }

   public static int getQuotedValueEndPosition(Buffer buffer, int off, int end) {
      int pos = off;

      while(true) {
         while(pos < end) {
            if (buffer.get(pos) == 34) {
               return pos;
            }

            if (buffer.get(pos) == 92 && pos < end - 1) {
               pos += 2;
            } else {
               ++pos;
            }
         }

         return end;
      }
   }

   public static int getQuotedValueEndPosition(byte[] bytes, int off, int end) {
      int pos = off;

      while(true) {
         while(pos < end) {
            if (bytes[pos] == 34) {
               return pos;
            }

            if (bytes[pos] == 92 && pos < end - 1) {
               pos += 2;
            } else {
               ++pos;
            }
         }

         return end;
      }
   }

   public static int getQuotedValueEndPosition(String s, int off, int end) {
      int pos = off;

      while(true) {
         while(pos < end) {
            if (s.charAt(pos) == '"') {
               return pos;
            }

            if (s.charAt(pos) == '\\' && pos < end - 1) {
               pos += 2;
            } else {
               ++pos;
            }
         }

         return end;
      }
   }

   static {
      for(int i = 0; i < 128; ++i) {
         separators[i] = false;
      }

      char[] var4 = SEPARATORS;
      int var1 = var4.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         char SEPARATOR = var4[var2];
         separators[SEPARATOR] = true;
      }

      OLD_COOKIE_FORMAT = new ThreadLocal() {
         protected SimpleDateFormat initialValue() {
            SimpleDateFormat f = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", Locale.US);
            f.setTimeZone(TimeZone.getTimeZone("GMT"));
            return f;
         }
      };
      ancientDate = ((SimpleDateFormat)OLD_COOKIE_FORMAT.get()).format(new Date(10000L));
   }
}
