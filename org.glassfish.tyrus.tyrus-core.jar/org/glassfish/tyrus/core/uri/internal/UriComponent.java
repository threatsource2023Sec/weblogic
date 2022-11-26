package org.glassfish.tyrus.core.uri.internal;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class UriComponent {
   private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private static final String[] SCHEME = new String[]{"0-9", "A-Z", "a-z", "+", "-", "."};
   private static final String[] UNRESERVED = new String[]{"0-9", "A-Z", "a-z", "-", ".", "_", "~"};
   private static final String[] SUB_DELIMS = new String[]{"!", "$", "&", "'", "(", ")", "*", "+", ",", ";", "="};
   private static final boolean[][] ENCODING_TABLES = initEncodingTables();
   private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");
   private static final int[] HEX_TABLE = initHexTable();

   private UriComponent() {
   }

   public static void validate(String s, Type t) {
      validate(s, t, false);
   }

   public static void validate(String s, Type t, boolean template) {
      int i = _valid(s, t, template);
      if (i > -1) {
         throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_INVALID_CHARACTER(s, t, s.charAt(i), i));
      }
   }

   public static boolean valid(String s, Type t) {
      return valid(s, t, false);
   }

   public static boolean valid(String s, Type t, boolean template) {
      return _valid(s, t, template) == -1;
   }

   private static int _valid(String s, Type t, boolean template) {
      boolean[] table = ENCODING_TABLES[t.ordinal()];

      for(int i = 0; i < s.length(); ++i) {
         char c = s.charAt(i);
         if ((c < 128 && c != '%' && !table[c] || c >= 128) && (!template || c != '{' && c != '}')) {
            return i;
         }
      }

      return -1;
   }

   public static String contextualEncode(String s, Type t) {
      return _encode(s, t, false, true);
   }

   public static String contextualEncode(String s, Type t, boolean template) {
      return _encode(s, t, template, true);
   }

   public static String encode(String s, Type t) {
      return _encode(s, t, false, false);
   }

   public static String encode(String s, Type t, boolean template) {
      return _encode(s, t, template, false);
   }

   public static String encodeTemplateNames(String s) {
      int i = s.indexOf(123);
      if (i != -1) {
         s = s.replace("{", "%7B");
      }

      i = s.indexOf(125);
      if (i != -1) {
         s = s.replace("}", "%7D");
      }

      return s;
   }

   private static String _encode(String s, Type t, boolean template, boolean contextualEncode) {
      boolean[] table = ENCODING_TABLES[t.ordinal()];
      boolean insideTemplateParam = false;
      StringBuilder sb = null;

      for(int i = 0; i < s.length(); ++i) {
         char c = s.charAt(i);
         if (c < 128 && table[c]) {
            if (sb != null) {
               sb.append(c);
            }
         } else {
            if (template) {
               boolean leavingTemplateParam = false;
               if (c == '{') {
                  insideTemplateParam = true;
               } else if (c == '}') {
                  insideTemplateParam = false;
                  leavingTemplateParam = true;
               }

               if (insideTemplateParam || leavingTemplateParam) {
                  if (sb != null) {
                     sb.append(c);
                  }
                  continue;
               }
            }

            if (contextualEncode && c == '%' && i + 2 < s.length() && isHexCharacter(s.charAt(i + 1)) && isHexCharacter(s.charAt(i + 2))) {
               if (sb != null) {
                  sb.append('%').append(s.charAt(i + 1)).append(s.charAt(i + 2));
               }

               i += 2;
            } else {
               if (sb == null) {
                  sb = new StringBuilder();
                  sb.append(s.substring(0, i));
               }

               if (c < 128) {
                  if (c == ' ' && t == UriComponent.Type.QUERY_PARAM) {
                     sb.append('+');
                  } else {
                     appendPercentEncodedOctet(sb, c);
                  }
               } else {
                  appendUTF8EncodedCharacter(sb, c);
               }
            }
         }
      }

      return sb == null ? s : sb.toString();
   }

   private static void appendPercentEncodedOctet(StringBuilder sb, int b) {
      sb.append('%');
      sb.append(HEX_DIGITS[b >> 4]);
      sb.append(HEX_DIGITS[b & 15]);
   }

   private static void appendUTF8EncodedCharacter(StringBuilder sb, char c) {
      ByteBuffer bb = UTF_8_CHARSET.encode("" + c);

      while(bb.hasRemaining()) {
         appendPercentEncodedOctet(sb, bb.get() & 255);
      }

   }

   private static boolean[][] initEncodingTables() {
      boolean[][] tables = new boolean[UriComponent.Type.values().length][];
      List l = new ArrayList();
      l.addAll(Arrays.asList(SCHEME));
      tables[UriComponent.Type.SCHEME.ordinal()] = initEncodingTable(l);
      l.clear();
      l.addAll(Arrays.asList(UNRESERVED));
      tables[UriComponent.Type.UNRESERVED.ordinal()] = initEncodingTable(l);
      l.addAll(Arrays.asList(SUB_DELIMS));
      tables[UriComponent.Type.HOST.ordinal()] = initEncodingTable(l);
      tables[UriComponent.Type.PORT.ordinal()] = initEncodingTable(Arrays.asList("0-9"));
      l.add(":");
      tables[UriComponent.Type.USER_INFO.ordinal()] = initEncodingTable(l);
      l.add("@");
      tables[UriComponent.Type.AUTHORITY.ordinal()] = initEncodingTable(l);
      tables[UriComponent.Type.PATH_SEGMENT.ordinal()] = initEncodingTable(l);
      tables[UriComponent.Type.PATH_SEGMENT.ordinal()][59] = false;
      tables[UriComponent.Type.MATRIX_PARAM.ordinal()] = (boolean[])tables[UriComponent.Type.PATH_SEGMENT.ordinal()].clone();
      tables[UriComponent.Type.MATRIX_PARAM.ordinal()][61] = false;
      l.add("/");
      tables[UriComponent.Type.PATH.ordinal()] = initEncodingTable(l);
      l.add("?");
      tables[UriComponent.Type.QUERY.ordinal()] = initEncodingTable(l);
      tables[UriComponent.Type.QUERY_PARAM.ordinal()] = initEncodingTable(l);
      tables[UriComponent.Type.QUERY_PARAM.ordinal()][61] = false;
      tables[UriComponent.Type.QUERY_PARAM.ordinal()][43] = false;
      tables[UriComponent.Type.QUERY_PARAM.ordinal()][38] = false;
      tables[UriComponent.Type.QUERY_PARAM_SPACE_ENCODED.ordinal()] = tables[UriComponent.Type.QUERY_PARAM.ordinal()];
      tables[UriComponent.Type.FRAGMENT.ordinal()] = tables[UriComponent.Type.QUERY.ordinal()];
      return tables;
   }

   private static boolean[] initEncodingTable(List allowed) {
      boolean[] table = new boolean[128];
      Iterator var2 = allowed.iterator();

      while(true) {
         while(var2.hasNext()) {
            String range = (String)var2.next();
            if (range.length() == 1) {
               table[range.charAt(0)] = true;
            } else if (range.length() == 3 && range.charAt(1) == '-') {
               for(int i = range.charAt(0); i <= range.charAt(2); ++i) {
                  table[i] = true;
               }
            }
         }

         return table;
      }
   }

   public static String decode(String s, Type t) {
      if (s == null) {
         throw new IllegalArgumentException();
      } else {
         int n = s.length();
         if (n == 0) {
            return s;
         } else {
            if (s.indexOf(37) < 0) {
               if (t != UriComponent.Type.QUERY_PARAM) {
                  return s;
               }

               if (s.indexOf(43) < 0) {
                  return s;
               }
            } else {
               if (n < 2) {
                  throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_MALFORMED(1));
               }

               if (s.charAt(n - 2) == '%') {
                  throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_MALFORMED(n - 2));
               }
            }

            if (t == null) {
               return decode(s, n);
            } else {
               switch (t) {
                  case HOST:
                     return decodeHost(s, n);
                  case QUERY_PARAM:
                     return decodeQueryParam(s, n);
                  default:
                     return decode(s, n);
               }
            }
         }
      }
   }

   public static MultivaluedMap decodeQuery(URI u, boolean decode) {
      return decodeQuery(u.getRawQuery(), decode);
   }

   public static MultivaluedMap decodeQuery(String q, boolean decode) {
      return decodeQuery(q, true, decode);
   }

   public static MultivaluedMap decodeQuery(String q, boolean decodeNames, boolean decodeValues) {
      MultivaluedMap queryParameters = new MultivaluedStringMap();
      if (q != null && q.length() != 0) {
         int s = 0;

         do {
            int e = q.indexOf(38, s);
            if (e == -1) {
               decodeQueryParam(queryParameters, q.substring(s), decodeNames, decodeValues);
            } else if (e > s) {
               decodeQueryParam(queryParameters, q.substring(s, e), decodeNames, decodeValues);
            }

            s = e + 1;
         } while(s > 0 && s < q.length());

         return queryParameters;
      } else {
         return queryParameters;
      }
   }

   private static void decodeQueryParam(MultivaluedMap params, String param, boolean decodeNames, boolean decodeValues) {
      try {
         int equals = param.indexOf(61);
         if (equals > 0) {
            params.add(decodeNames ? URLDecoder.decode(param.substring(0, equals), "UTF-8") : param.substring(0, equals), decodeValues ? URLDecoder.decode(param.substring(equals + 1), "UTF-8") : param.substring(equals + 1));
         } else if (equals != 0 && param.length() > 0) {
            params.add(URLDecoder.decode(param, "UTF-8"), "");
         }

      } catch (UnsupportedEncodingException var5) {
         throw new IllegalArgumentException(var5);
      }
   }

   public static List decodePath(URI u, boolean decode) {
      String rawPath = u.getRawPath();
      if (rawPath != null && rawPath.length() > 0 && rawPath.charAt(0) == '/') {
         rawPath = rawPath.substring(1);
      }

      return decodePath(rawPath, decode);
   }

   public static List decodePath(String path, boolean decode) {
      List segments = new LinkedList();
      if (path == null) {
         return segments;
      } else {
         int e = -1;

         int s;
         do {
            s = e + 1;
            e = path.indexOf(47, s);
            if (e > s) {
               decodePathSegment(segments, path.substring(s, e), decode);
            } else if (e == s) {
               segments.add(UriComponent.PathSegmentImpl.EMPTY_PATH_SEGMENT);
            }
         } while(e != -1);

         if (s < path.length()) {
            decodePathSegment(segments, path.substring(s), decode);
         } else {
            segments.add(UriComponent.PathSegmentImpl.EMPTY_PATH_SEGMENT);
         }

         return segments;
      }
   }

   public static void decodePathSegment(List segments, String segment, boolean decode) {
      int colon = segment.indexOf(59);
      if (colon != -1) {
         segments.add(new PathSegmentImpl(colon == 0 ? "" : segment.substring(0, colon), decode, decodeMatrix(segment, decode)));
      } else {
         segments.add(new PathSegmentImpl(segment, decode));
      }

   }

   public static MultivaluedMap decodeMatrix(String pathSegment, boolean decode) {
      MultivaluedMap matrixMap = new MultivaluedStringMap();
      int s = pathSegment.indexOf(59) + 1;
      if (s != 0 && s != pathSegment.length()) {
         do {
            int e = pathSegment.indexOf(59, s);
            if (e == -1) {
               decodeMatrixParam(matrixMap, pathSegment.substring(s), decode);
            } else if (e > s) {
               decodeMatrixParam(matrixMap, pathSegment.substring(s, e), decode);
            }

            s = e + 1;
         } while(s > 0 && s < pathSegment.length());

         return matrixMap;
      } else {
         return matrixMap;
      }
   }

   private static void decodeMatrixParam(MultivaluedMap params, String param, boolean decode) {
      int equals = param.indexOf(61);
      if (equals > 0) {
         params.add(decode(param.substring(0, equals), UriComponent.Type.MATRIX_PARAM), decode ? decode(param.substring(equals + 1), UriComponent.Type.MATRIX_PARAM) : param.substring(equals + 1));
      } else if (equals != 0 && param.length() > 0) {
         params.add(decode(param, UriComponent.Type.MATRIX_PARAM), "");
      }

   }

   private static String decode(String s, int n) {
      StringBuilder sb = new StringBuilder(n);
      ByteBuffer bb = null;
      int i = 0;

      while(i < n) {
         char c = s.charAt(i++);
         if (c != '%') {
            sb.append(c);
         } else {
            bb = decodePercentEncodedOctets(s, i, bb);
            i = decodeOctets(i, bb, sb);
         }
      }

      return sb.toString();
   }

   private static String decodeQueryParam(String s, int n) {
      StringBuilder sb = new StringBuilder(n);
      ByteBuffer bb = null;
      int i = 0;

      while(i < n) {
         char c = s.charAt(i++);
         if (c != '%') {
            if (c != '+') {
               sb.append(c);
            } else {
               sb.append(' ');
            }
         } else {
            bb = decodePercentEncodedOctets(s, i, bb);
            i = decodeOctets(i, bb, sb);
         }
      }

      return sb.toString();
   }

   private static String decodeHost(String s, int n) {
      StringBuilder sb = new StringBuilder(n);
      ByteBuffer bb = null;
      boolean betweenBrackets = false;
      int i = 0;

      while(true) {
         while(i < n) {
            char c = s.charAt(i++);
            if (c == '[') {
               betweenBrackets = true;
            } else if (betweenBrackets && c == ']') {
               betweenBrackets = false;
            }

            if (c == '%' && !betweenBrackets) {
               bb = decodePercentEncodedOctets(s, i, bb);
               i = decodeOctets(i, bb, sb);
            } else {
               sb.append(c);
            }
         }

         return sb.toString();
      }
   }

   private static ByteBuffer decodePercentEncodedOctets(String s, int i, ByteBuffer bb) {
      if (bb == null) {
         bb = ByteBuffer.allocate(1);
      } else {
         bb.clear();
      }

      while(true) {
         bb.put((byte)(decodeHex(s, i++) << 4 | decodeHex(s, i++)));
         if (i == s.length() || s.charAt(i++) != '%') {
            bb.flip();
            return bb;
         }

         if (bb.position() == bb.capacity()) {
            bb.flip();
            ByteBuffer bb_new = ByteBuffer.allocate(s.length() / 3);
            bb_new.put(bb);
            bb = bb_new;
         }
      }
   }

   private static int decodeOctets(int i, ByteBuffer bb, StringBuilder sb) {
      if (bb.limit() == 1 && (bb.get(0) & 255) < 128) {
         sb.append((char)bb.get(0));
         return i + 2;
      } else {
         CharBuffer cb = UTF_8_CHARSET.decode(bb);
         sb.append(cb.toString());
         return i + bb.limit() * 3 - 1;
      }
   }

   private static int decodeHex(String s, int i) {
      int v = decodeHex(s.charAt(i));
      if (v == -1) {
         throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_INVALID_DIGIT(i, s.charAt(i)));
      } else {
         return v;
      }
   }

   private static int[] initHexTable() {
      int[] table = new int[128];
      Arrays.fill(table, -1);

      char c;
      for(c = '0'; c <= '9'; ++c) {
         table[c] = c - 48;
      }

      for(c = 'A'; c <= 'F'; ++c) {
         table[c] = c - 65 + 10;
      }

      for(c = 'a'; c <= 'f'; ++c) {
         table[c] = c - 97 + 10;
      }

      return table;
   }

   private static int decodeHex(char c) {
      return c < 128 ? HEX_TABLE[c] : -1;
   }

   public static boolean isHexCharacter(char c) {
      return c < 128 && HEX_TABLE[c] != -1;
   }

   public static String fullRelativeUri(URI uri) {
      if (uri == null) {
         return null;
      } else {
         String query = uri.getRawQuery();
         return uri.getRawPath() + (query != null && query.length() > 0 ? "?" + query : "");
      }
   }

   private static final class PathSegmentImpl implements PathSegment {
      private static final PathSegment EMPTY_PATH_SEGMENT = new PathSegmentImpl("", false);
      private final String path;
      private final MultivaluedMap matrixParameters;

      PathSegmentImpl(String path, boolean decode) {
         this(path, decode, new MultivaluedStringMap());
      }

      PathSegmentImpl(String path, boolean decode, MultivaluedMap matrixParameters) {
         this.path = decode ? UriComponent.decode(path, UriComponent.Type.PATH_SEGMENT) : path;
         this.matrixParameters = matrixParameters;
      }

      public String getPath() {
         return this.path;
      }

      public MultivaluedMap getMatrixParameters() {
         return this.matrixParameters;
      }

      public String toString() {
         return this.path;
      }
   }

   public static enum Type {
      UNRESERVED,
      SCHEME,
      AUTHORITY,
      USER_INFO,
      HOST,
      PORT,
      PATH,
      PATH_SEGMENT,
      MATRIX_PARAM,
      QUERY,
      QUERY_PARAM,
      QUERY_PARAM_SPACE_ENCODED,
      FRAGMENT;
   }
}
