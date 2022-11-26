package org.glassfish.grizzly.http.util;

import java.nio.BufferOverflowException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.Cookie;

public class CookieSerializerUtils {
   public static void serializeServerCookie(StringBuilder buf, Cookie cookie) {
      serializeServerCookie(buf, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED, CookieUtils.ALWAYS_ADD_EXPIRES, cookie);
   }

   public static void serializeServerCookie(StringBuilder buf, boolean versionOneStrictCompliance, boolean rfc6265Support, boolean alwaysAddExpires, Cookie cookie) {
      serializeServerCookie(buf, versionOneStrictCompliance, rfc6265Support, alwaysAddExpires, cookie.getName(), cookie.getValue(), cookie.getVersion(), cookie.getPath(), cookie.getDomain(), cookie.getComment(), cookie.getMaxAge(), cookie.isSecure(), cookie.isHttpOnly());
   }

   public static void serializeServerCookie(StringBuilder buf, boolean versionOneStrictCompliance, boolean rfc6265Support, boolean alwaysAddExpires, String name, String value, int version, String path, String domain, String comment, int maxAge, boolean isSecure, boolean isHttpOnly) {
      buf.append(name);
      buf.append('=');
      version = maybeQuote2(version, buf, value, true, rfc6265Support);
      if (version == 1) {
         buf.append("; Version=1");
         if (comment != null) {
            buf.append("; Comment=");
            maybeQuote2(version, buf, comment, versionOneStrictCompliance, rfc6265Support);
         }
      }

      if (domain != null) {
         buf.append("; Domain=");
         maybeQuote2(version, buf, domain, versionOneStrictCompliance, rfc6265Support);
      }

      if (maxAge >= 0) {
         if (version > 0) {
            buf.append("; Max-Age=");
            buf.append(maxAge);
         }

         if (version == 0 || alwaysAddExpires) {
            buf.append("; Expires=");
            if (maxAge == 0) {
               buf.append(CookieUtils.ancientDate);
            } else {
               buf.append(((SimpleDateFormat)CookieUtils.OLD_COOKIE_FORMAT.get()).format(new Date(System.currentTimeMillis() + (long)maxAge * 1000L)));
            }
         }
      }

      if (path != null) {
         buf.append("; Path=");
         UEncoder encoder = new UEncoder();
         encoder.addSafeCharacter('/');
         encoder.addSafeCharacter('"');
         path = encoder.encodeURL(path, true);
         if (version == 0) {
            maybeQuote2(version, buf, path, versionOneStrictCompliance, rfc6265Support);
         } else {
            maybeQuote2(version, buf, path, "()<>@,;:\\\"[]?={} \t", false, versionOneStrictCompliance, rfc6265Support);
         }
      }

      if (isSecure) {
         buf.append("; Secure");
      }

      if (isHttpOnly) {
         buf.append("; HttpOnly");
      }

   }

   public static void serializeServerCookie(Buffer buf, Cookie cookie) {
      serializeServerCookie(buf, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.ALWAYS_ADD_EXPIRES, cookie);
   }

   public static void serializeServerCookie(Buffer buf, boolean versionOneStrictCompliance, boolean alwaysAddExpires, Cookie cookie) {
      serializeServerCookie(buf, versionOneStrictCompliance, alwaysAddExpires, cookie.getName(), cookie.getValue(), cookie.getVersion(), cookie.getPath(), cookie.getDomain(), cookie.getComment(), cookie.getMaxAge(), cookie.isSecure(), cookie.isHttpOnly());
   }

   public static void serializeServerCookie(Buffer buf, boolean versionOneStrictCompliance, boolean alwaysAddExpires, String name, String value, int version, String path, String domain, String comment, int maxAge, boolean isSecure, boolean isHttpOnly) {
      put(buf, name);
      put((Buffer)buf, 61);
      version = maybeQuote2(version, buf, value, true);
      if (version == 1) {
         put(buf, "; Version=1");
         if (comment != null) {
            put(buf, "; Comment=");
            maybeQuote2(version, buf, comment, versionOneStrictCompliance);
         }
      }

      if (domain != null) {
         put(buf, "; Domain=");
         maybeQuote2(version, buf, domain, versionOneStrictCompliance);
      }

      if (maxAge >= 0) {
         if (version > 0) {
            put(buf, "; Max-Age=");
            putInt(buf, maxAge);
         }

         if (version == 0 || alwaysAddExpires) {
            put(buf, "; Expires=");
            if (maxAge == 0) {
               put(buf, CookieUtils.ancientDate);
            } else {
               put(buf, ((SimpleDateFormat)CookieUtils.OLD_COOKIE_FORMAT.get()).format(new Date(System.currentTimeMillis() + (long)maxAge * 1000L)));
            }
         }
      }

      if (path != null) {
         put(buf, "; Path=");
         UEncoder encoder = new UEncoder();
         encoder.addSafeCharacter('/');
         encoder.addSafeCharacter('"');
         path = encoder.encodeURL(path, true);
         if (version == 0) {
            maybeQuote2(version, buf, path, versionOneStrictCompliance);
         } else {
            maybeQuote2(version, buf, path, "()<>@,;:\\\"[]?={} \t", false, versionOneStrictCompliance);
         }
      }

      if (isSecure) {
         put(buf, "; Secure");
      }

      if (isHttpOnly) {
         put(buf, "; HttpOnly");
      }

   }

   public static void serializeClientCookies(StringBuilder buf, Cookie... cookies) {
      serializeClientCookies(buf, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, CookieUtils.RFC_6265_SUPPORT_ENABLED, cookies);
   }

   public static void serializeClientCookies(StringBuilder buf, boolean versionOneStrictCompliance, boolean rfc6265Support, Cookie... cookies) {
      if (cookies.length != 0) {
         int version = cookies[0].getVersion();
         if (!rfc6265Support && version == 1) {
            buf.append("$Version=\"1\"; ");
         }

         for(int i = 0; i < cookies.length; ++i) {
            Cookie cookie = cookies[i];
            buf.append(cookie.getName());
            buf.append('=');
            maybeQuote2(version, buf, cookie.getValue(), true, rfc6265Support);
            if (!rfc6265Support && version == 1) {
               String domain = cookie.getDomain();
               if (domain != null) {
                  buf.append("; $Domain=");
                  maybeQuote2(version, buf, domain, versionOneStrictCompliance, rfc6265Support);
               }

               String path = cookie.getPath();
               if (path != null) {
                  buf.append("; $Path=");
                  UEncoder encoder = new UEncoder();
                  encoder.addSafeCharacter('/');
                  encoder.addSafeCharacter('"');
                  path = encoder.encodeURL(path, true);
                  maybeQuote2(version, buf, path, "()<>@,;:\\\"[]?={} \t", false, versionOneStrictCompliance, rfc6265Support);
               }
            }

            if (i < cookies.length - 1) {
               buf.append("; ");
            }
         }

      }
   }

   public static void serializeClientCookies(Buffer buf, Cookie... cookies) {
      serializeClientCookies(buf, CookieUtils.COOKIE_VERSION_ONE_STRICT_COMPLIANCE, cookies);
   }

   public static void serializeClientCookies(Buffer buf, boolean versionOneStrictCompliance, Cookie... cookies) {
      if (cookies.length != 0) {
         int version = cookies[0].getVersion();
         if (version == 1) {
            put(buf, "$Version=\"1\"; ");
         }

         for(int i = 0; i < cookies.length; ++i) {
            Cookie cookie = cookies[i];
            put(buf, cookie.getName());
            put(buf, "=");
            maybeQuote2(version, buf, cookie.getValue(), true);
            if (version == 1) {
               String domain = cookie.getDomain();
               if (domain != null) {
                  put(buf, "; $Domain=");
                  maybeQuote2(version, buf, domain, versionOneStrictCompliance);
               }

               String path = cookie.getPath();
               if (path != null) {
                  put(buf, "; $Path=");
                  UEncoder encoder = new UEncoder();
                  encoder.addSafeCharacter('/');
                  encoder.addSafeCharacter('"');
                  path = encoder.encodeURL(path, true);
                  maybeQuote2(version, buf, path, "()<>@,;:\\\"[]?={} \t", false, versionOneStrictCompliance);
               }
            }

            if (i < cookies.length - 1) {
               put(buf, "; ");
            }
         }

      }
   }

   public static int maybeQuote2(int version, StringBuilder buf, String value, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
      return maybeQuote2(version, buf, value, false, versionOneStrictCompliance, rfc6265Enabled);
   }

   public static int maybeQuote2(int version, StringBuilder buf, String value, boolean allowVersionSwitch, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
      return maybeQuote2(version, buf, value, (String)null, allowVersionSwitch, versionOneStrictCompliance, rfc6265Enabled);
   }

   public static int maybeQuote2(int version, StringBuilder buf, String value, String literals, boolean allowVersionSwitch, boolean versionOneStrictCompliance, boolean rfc6265Enabled) {
      if (value != null && value.length() != 0) {
         if (CookieUtils.containsCTL(value, version)) {
            throw new IllegalArgumentException("Control character in cookie value, consider BASE64 encoding your value");
         }

         if (alreadyQuoted(value)) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value, 1, value.length() - 1));
            buf.append('"');
         } else if (allowVersionSwitch && versionOneStrictCompliance && version == 0 && !CookieUtils.isToken2(value, literals)) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value, 0, value.length()));
            buf.append('"');
            version = 1;
         } else if (version == 0 && !CookieUtils.isToken(value, literals)) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value, 0, value.length()));
            buf.append('"');
         } else if (version == 1 && !CookieUtils.isToken2(value, literals)) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value, 0, value.length()));
            buf.append('"');
         } else if (version < 0 && rfc6265Enabled) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value, 0, value.length()));
            buf.append('"');
         } else {
            buf.append(value);
         }
      } else {
         buf.append("\"\"");
      }

      return version;
   }

   public static int maybeQuote2(int version, Buffer buf, String value, boolean versionOneStrictCompliance) {
      return maybeQuote2(version, buf, value, false, versionOneStrictCompliance);
   }

   public static int maybeQuote2(int version, Buffer buf, String value, boolean allowVersionSwitch, boolean versionOneStrictCompliance) {
      return maybeQuote2(version, buf, value, (String)null, allowVersionSwitch, versionOneStrictCompliance);
   }

   public static int maybeQuote2(int version, Buffer buf, String value, String literals, boolean allowVersionSwitch, boolean versionOneStrictCompliance) {
      if (value != null && value.length() != 0) {
         if (CookieUtils.containsCTL(value, version)) {
            throw new IllegalArgumentException("Control character in cookie value, consider BASE64 encoding your value");
         }

         if (alreadyQuoted(value)) {
            put((Buffer)buf, 34);
            put(buf, escapeDoubleQuotes(value, 1, value.length() - 1));
            put((Buffer)buf, 34);
         } else if (allowVersionSwitch && versionOneStrictCompliance && version == 0 && !CookieUtils.isToken2(value, literals)) {
            put((Buffer)buf, 34);
            put(buf, escapeDoubleQuotes(value, 0, value.length()));
            put((Buffer)buf, 34);
            version = 1;
         } else if (version == 0 && !CookieUtils.isToken(value, literals)) {
            put((Buffer)buf, 34);
            put(buf, escapeDoubleQuotes(value, 0, value.length()));
            put((Buffer)buf, 34);
         } else if (version == 1 && !CookieUtils.isToken2(value, literals)) {
            put((Buffer)buf, 34);
            put(buf, escapeDoubleQuotes(value, 0, value.length()));
            put((Buffer)buf, 34);
         } else {
            put(buf, value);
         }
      } else {
         put(buf, "\"\"");
      }

      return version;
   }

   private static String escapeDoubleQuotes(String s, int beginIndex, int endIndex) {
      if (s != null && s.length() != 0 && s.indexOf(34) != -1) {
         StringBuilder b = new StringBuilder();

         for(int i = beginIndex; i < endIndex; ++i) {
            char c = s.charAt(i);
            if (c == '\\') {
               b.append(c);
               ++i;
               if (i >= endIndex) {
                  throw new IllegalArgumentException("Invalid escape character in cookie value.");
               }

               b.append(s.charAt(i));
            } else if (c == '"') {
               b.append('\\').append('"');
            } else {
               b.append(c);
            }
         }

         return b.toString();
      } else {
         return s;
      }
   }

   public static boolean alreadyQuoted(String value) {
      return value != null && value.length() != 0 && value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"';
   }

   static void put(Buffer dstBuffer, int c) {
      dstBuffer.put((byte)c);
   }

   static void putInt(Buffer dstBuffer, int intValue) {
      put(dstBuffer, Integer.toString(intValue));
   }

   static void put(Buffer dstBuffer, String s) {
      int size = s.length();
      if (dstBuffer.remaining() < size) {
         throw new BufferOverflowException();
      } else {
         for(int i = 0; i < size; ++i) {
            dstBuffer.put((byte)s.charAt(i));
         }

      }
   }

   static void put(StringBuilder dstBuffer, int c) {
      dstBuffer.append((char)c);
   }

   static void putInt(StringBuilder dstBuffer, int intValue) {
      dstBuffer.append(intValue);
   }

   static void put(StringBuilder dstBuffer, String s) {
      dstBuffer.append(s);
   }
}
