package org.apache.taglibs.standard.tag.common.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import org.apache.taglibs.standard.resources.Resources;

public class Util {
   private static final String REQUEST = "request";
   private static final String SESSION = "session";
   private static final String APPLICATION = "application";
   private static final String DEFAULT = "default";
   private static final String SHORT = "short";
   private static final String MEDIUM = "medium";
   private static final String LONG = "long";
   private static final String FULL = "full";
   public static final int HIGHEST_SPECIAL = 62;
   public static char[][] specialCharactersRepresentation = new char[63][];

   public static int getScope(String scope) {
      int ret = 1;
      if ("request".equalsIgnoreCase(scope)) {
         ret = 2;
      } else if ("session".equalsIgnoreCase(scope)) {
         ret = 3;
      } else if ("application".equalsIgnoreCase(scope)) {
         ret = 4;
      }

      return ret;
   }

   public static int getStyle(String style, String errCode) throws JspException {
      int ret = 2;
      if (style != null) {
         if ("default".equalsIgnoreCase(style)) {
            ret = 2;
         } else if ("short".equalsIgnoreCase(style)) {
            ret = 3;
         } else if ("medium".equalsIgnoreCase(style)) {
            ret = 2;
         } else if ("long".equalsIgnoreCase(style)) {
            ret = 1;
         } else {
            if (!"full".equalsIgnoreCase(style)) {
               throw new JspException(Resources.getMessage(errCode, (Object)style));
            }

            ret = 0;
         }
      }

      return ret;
   }

   public static String escapeXml(String buffer) {
      int start = 0;
      int length = buffer.length();
      char[] arrayBuffer = buffer.toCharArray();
      StringBuffer escapedBuffer = null;

      for(int i = 0; i < length; ++i) {
         char c = arrayBuffer[i];
         if (c <= '>') {
            char[] escaped = specialCharactersRepresentation[c];
            if (escaped != null) {
               if (start == 0) {
                  escapedBuffer = new StringBuffer(length + 5);
               }

               if (start < i) {
                  escapedBuffer.append(arrayBuffer, start, i - start);
               }

               start = i + 1;
               escapedBuffer.append(escaped);
            }
         }
      }

      if (start == 0) {
         return buffer;
      } else {
         if (start < length) {
            escapedBuffer.append(arrayBuffer, start, length - start);
         }

         return escapedBuffer.toString();
      }
   }

   public static String getContentTypeAttribute(String input, String name) {
      int index = input.toUpperCase().indexOf(name.toUpperCase());
      if (index == -1) {
         return null;
      } else {
         index += name.length();
         index = input.indexOf(61, index);
         if (index == -1) {
            return null;
         } else {
            ++index;
            input = input.substring(index).trim();
            byte begin;
            int end;
            if (input.charAt(0) == '"') {
               begin = 1;
               end = input.indexOf(34, begin);
               if (end == -1) {
                  return null;
               }
            } else {
               begin = 0;
               end = input.indexOf(59);
               if (end == -1) {
                  end = input.indexOf(32);
               }

               if (end == -1) {
                  end = input.length();
               }
            }

            return input.substring(begin, end).trim();
         }
      }
   }

   public static String URLEncode(String s, String enc) {
      if (s == null) {
         return "null";
      } else {
         if (enc == null) {
            enc = "UTF-8";
         }

         StringBuffer out = new StringBuffer(s.length());
         ByteArrayOutputStream buf = new ByteArrayOutputStream();
         OutputStreamWriter writer = null;

         try {
            writer = new OutputStreamWriter(buf, enc);
         } catch (UnsupportedEncodingException var9) {
            writer = new OutputStreamWriter(buf);
         }

         for(int i = 0; i < s.length(); ++i) {
            int c = s.charAt(i);
            if (c == ' ') {
               out.append('+');
            } else if (isSafeChar(c)) {
               out.append((char)c);
            } else {
               try {
                  writer.write(c);
                  writer.flush();
               } catch (IOException var10) {
                  buf.reset();
                  continue;
               }

               byte[] ba = buf.toByteArray();

               for(int j = 0; j < ba.length; ++j) {
                  out.append('%');
                  out.append(Character.forDigit(ba[j] >> 4 & 15, 16));
                  out.append(Character.forDigit(ba[j] & 15, 16));
               }

               buf.reset();
            }
         }

         return out.toString();
      }
   }

   private static boolean isSafeChar(int c) {
      if (c >= 97 && c <= 122) {
         return true;
      } else if (c >= 65 && c <= 90) {
         return true;
      } else if (c >= 48 && c <= 57) {
         return true;
      } else {
         return c == 45 || c == 95 || c == 46 || c == 33 || c == 126 || c == 42 || c == 39 || c == 40 || c == 41;
      }
   }

   public static Enumeration getRequestLocales(HttpServletRequest request) {
      Enumeration values = request.getHeaders("accept-language");
      return values.hasMoreElements() ? request.getLocales() : values;
   }

   static {
      specialCharactersRepresentation[38] = "&amp;".toCharArray();
      specialCharactersRepresentation[60] = "&lt;".toCharArray();
      specialCharactersRepresentation[62] = "&gt;".toCharArray();
      specialCharactersRepresentation[34] = "&#034;".toCharArray();
      specialCharactersRepresentation[39] = "&#039;".toCharArray();
   }
}
