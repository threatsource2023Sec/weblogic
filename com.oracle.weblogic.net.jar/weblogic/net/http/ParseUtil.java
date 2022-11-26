package weblogic.net.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import weblogic.utils.encoders.ThreadLocalCoders;

public class ParseUtil {
   private static final long L_DIGIT = lowMask('0', '9');
   private static final long H_DIGIT = 0L;
   private static final long L_HEX;
   private static final long H_HEX;
   private static final long L_UPALPHA = 0L;
   private static final long H_UPALPHA;
   private static final long L_LOWALPHA = 0L;
   private static final long H_LOWALPHA;
   private static final long L_ALPHA = 0L;
   private static final long H_ALPHA;
   private static final long L_ALPHANUM;
   private static final long H_ALPHANUM;
   private static final long L_MARK;
   private static final long H_MARK;
   private static final long L_UNRESERVED;
   private static final long H_UNRESERVED;
   private static final long L_RESERVED;
   private static final long H_RESERVED;
   private static final long L_ESCAPED = 1L;
   private static final long H_ESCAPED = 0L;
   private static final long L_DASH;
   private static final long H_DASH;
   private static final long L_URIC;
   private static final long H_URIC;
   private static final long L_PCHAR;
   private static final long H_PCHAR;
   private static final long L_PATH;
   private static final long H_PATH;
   private static final long L_USERINFO;
   private static final long H_USERINFO;
   private static final long L_REG_NAME;
   private static final long H_REG_NAME;
   private static final long L_SERVER;
   private static final long H_SERVER;
   private static final char[] hexDigits;

   public static URI toURI(URL url) {
      String protocol = url.getProtocol();
      String auth = url.getAuthority();
      String path = url.getPath();
      String query = url.getQuery();
      String ref = url.getRef();
      if (path != null && !path.startsWith("/")) {
         path = "/" + path;
      }

      if (auth != null && auth.endsWith(":-1")) {
         auth = auth.substring(0, auth.length() - 3);
      }

      URI uri;
      try {
         uri = createURI(protocol, auth, path, query, ref);
      } catch (URISyntaxException var8) {
         uri = null;
      }

      return uri;
   }

   private static URI createURI(String scheme, String authority, String path, String query, String fragment) throws URISyntaxException {
      String s = toString(scheme, (String)null, authority, (String)null, (String)null, -1, path, query, fragment);
      checkPath(s, scheme, path);
      return new URI(s);
   }

   private static String toString(String scheme, String opaquePart, String authority, String userInfo, String host, int port, String path, String query, String fragment) {
      StringBuffer sb = new StringBuffer();
      if (scheme != null) {
         sb.append(scheme);
         sb.append(':');
      }

      appendSchemeSpecificPart(sb, opaquePart, authority, userInfo, host, port, path, query);
      appendFragment(sb, fragment);
      return sb.toString();
   }

   private static void appendSchemeSpecificPart(StringBuffer sb, String opaquePart, String authority, String userInfo, String host, int port, String path, String query) {
      if (opaquePart != null) {
         if (opaquePart.startsWith("//[")) {
            int end = opaquePart.indexOf("]");
            if (end != -1 && opaquePart.indexOf(":") != -1) {
               String doquote;
               String dontquote;
               if (end == opaquePart.length()) {
                  dontquote = opaquePart;
                  doquote = "";
               } else {
                  dontquote = opaquePart.substring(0, end + 1);
                  doquote = opaquePart.substring(end + 1);
               }

               sb.append(dontquote);
               sb.append(quote(doquote, L_URIC, H_URIC));
            }
         } else {
            sb.append(quote(opaquePart, L_URIC, H_URIC));
         }
      } else {
         appendAuthority(sb, authority, userInfo, host, port);
         if (path != null) {
            sb.append(quote(path, L_PATH, H_PATH));
         }

         if (query != null) {
            sb.append('?');
            sb.append(quote(query, L_URIC, H_URIC));
         }
      }

   }

   private static void appendAuthority(StringBuffer sb, String authority, String userInfo, String host, int port) {
      if (host != null) {
         sb.append("//");
         if (userInfo != null) {
            sb.append(quote(userInfo, L_USERINFO, H_USERINFO));
            sb.append('@');
         }

         boolean needBrackets = host.indexOf(58) >= 0 && !host.startsWith("[") && !host.endsWith("]");
         if (needBrackets) {
            sb.append('[');
         }

         sb.append(host);
         if (needBrackets) {
            sb.append(']');
         }

         if (port != -1) {
            sb.append(':');
            sb.append(port);
         }
      } else if (authority != null) {
         sb.append("//");
         if (authority.startsWith("[")) {
            int end = authority.indexOf("]");
            if (end != -1 && authority.indexOf(":") != -1) {
               String doquote;
               String dontquote;
               if (end == authority.length()) {
                  dontquote = authority;
                  doquote = "";
               } else {
                  dontquote = authority.substring(0, end + 1);
                  doquote = authority.substring(end + 1);
               }

               sb.append(dontquote);
               sb.append(quote(doquote, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
            }
         } else {
            sb.append(quote(authority, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
         }
      }

   }

   private static void appendFragment(StringBuffer sb, String fragment) {
      if (fragment != null) {
         sb.append('#');
         sb.append(quote(fragment, L_URIC, H_URIC));
      }

   }

   private static String quote(String s, long lowMask, long highMask) {
      int n = s.length();
      StringBuffer sb = null;
      boolean allowNonASCII = (lowMask & 1L) != 0L;

      for(int i = 0; i < s.length(); ++i) {
         char c = s.charAt(i);
         if (c < 128) {
            if (!match(c, lowMask, highMask) && !isEscaped(s, i)) {
               if (sb == null) {
                  sb = new StringBuffer();
                  sb.append(s.substring(0, i));
               }

               appendEscape(sb, (byte)c);
            } else if (sb != null) {
               sb.append(c);
            }
         } else if (allowNonASCII && (Character.isSpaceChar(c) || Character.isISOControl(c))) {
            if (sb == null) {
               sb = new StringBuffer();
               sb.append(s.substring(0, i));
            }

            appendEncoded(sb, c);
         } else if (sb != null) {
            sb.append(c);
         }
      }

      return sb == null ? s : sb.toString();
   }

   private static boolean isEscaped(String s, int pos) {
      if (s != null && s.length() > pos + 2) {
         return s.charAt(pos) == '%' && match(s.charAt(pos + 1), L_HEX, H_HEX) && match(s.charAt(pos + 2), L_HEX, H_HEX);
      } else {
         return false;
      }
   }

   private static void appendEncoded(StringBuffer sb, char c) {
      ByteBuffer bb = null;

      try {
         bb = ThreadLocalCoders.getEncoder("UTF-8").encode(CharBuffer.wrap("" + c));
      } catch (CharacterCodingException var4) {
         assert false;
      }

      while(bb.hasRemaining()) {
         int b = bb.get() & 255;
         if (b >= 128) {
            appendEscape(sb, (byte)b);
         } else {
            sb.append((char)b);
         }
      }

   }

   private static void appendEscape(StringBuffer sb, byte b) {
      sb.append('%');
      sb.append(hexDigits[b >> 4 & 15]);
      sb.append(hexDigits[b >> 0 & 15]);
   }

   private static boolean match(char c, long lowMask, long highMask) {
      if (c < '@') {
         return (1L << c & lowMask) != 0L;
      } else if (c < 128) {
         return (1L << c - 64 & highMask) != 0L;
      } else {
         return false;
      }
   }

   private static long lowMask(char first, char last) {
      long m = 0L;
      int f = Math.max(Math.min(first, 63), 0);
      int l = Math.max(Math.min(last, 63), 0);

      for(int i = f; i <= l; ++i) {
         m |= 1L << i;
      }

      return m;
   }

   private static long lowMask(String chars) {
      int n = chars.length();
      long m = 0L;

      for(int i = 0; i < n; ++i) {
         char c = chars.charAt(i);
         if (c < '@') {
            m |= 1L << c;
         }
      }

      return m;
   }

   private static long highMask(char first, char last) {
      long m = 0L;
      int f = Math.max(Math.min(first, 127), 64) - 64;
      int l = Math.max(Math.min(last, 127), 64) - 64;

      for(int i = f; i <= l; ++i) {
         m |= 1L << i;
      }

      return m;
   }

   private static long highMask(String chars) {
      int n = chars.length();
      long m = 0L;

      for(int i = 0; i < n; ++i) {
         char c = chars.charAt(i);
         if (c >= '@' && c < 128) {
            m |= 1L << c - 64;
         }
      }

      return m;
   }

   private static void checkPath(String s, String scheme, String path) throws URISyntaxException {
      if (scheme != null && path != null && path.length() > 0 && path.charAt(0) != '/') {
         throw new URISyntaxException(s, "Relative path in absolute URI");
      }
   }

   static {
      L_HEX = L_DIGIT;
      H_HEX = highMask('A', 'F') | highMask('a', 'f');
      H_UPALPHA = highMask('A', 'Z');
      H_LOWALPHA = highMask('a', 'z');
      H_ALPHA = H_LOWALPHA | H_UPALPHA;
      L_ALPHANUM = L_DIGIT | 0L;
      H_ALPHANUM = 0L | H_ALPHA;
      L_MARK = lowMask("-_.!~*'()");
      H_MARK = highMask("-_.!~*'()");
      L_UNRESERVED = L_ALPHANUM | L_MARK;
      H_UNRESERVED = H_ALPHANUM | H_MARK;
      L_RESERVED = lowMask(";/?:@&=+$,[]");
      H_RESERVED = highMask(";/?:@&=+$,[]");
      L_DASH = lowMask("-");
      H_DASH = highMask("-");
      L_URIC = L_RESERVED | L_UNRESERVED | 1L;
      H_URIC = H_RESERVED | H_UNRESERVED | 0L;
      L_PCHAR = L_UNRESERVED | 1L | lowMask(":@&=+$,");
      H_PCHAR = H_UNRESERVED | 0L | highMask(":@&=+$,");
      L_PATH = L_PCHAR | lowMask(";/");
      H_PATH = H_PCHAR | highMask(";/");
      L_USERINFO = L_UNRESERVED | 1L | lowMask(";:&=+$,");
      H_USERINFO = H_UNRESERVED | 0L | highMask(";:&=+$,");
      L_REG_NAME = L_UNRESERVED | 1L | lowMask("$,;:@&=+");
      H_REG_NAME = H_UNRESERVED | 0L | highMask("$,;:@&=+");
      L_SERVER = L_USERINFO | L_ALPHANUM | L_DASH | lowMask(".:@[]");
      H_SERVER = H_USERINFO | H_ALPHANUM | H_DASH | highMask(".:@[]");
      hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   }
}
