package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.utils.Charsets;

public class HttpUtils {
   private static final float[] MULTIPLIERS = new float[]{0.1F, 0.01F, 0.001F};

   public static String composeContentType(String contentType, String characterEncoding) {
      if (characterEncoding == null) {
         return contentType;
      } else {
         boolean hasCharset = false;
         int semicolonIndex = -1;

         int index;
         for(index = contentType.indexOf(59); index != -1; index = contentType.indexOf(59, index)) {
            int len = contentType.length();

            for(semicolonIndex = index++; index < len && contentType.charAt(index) == ' '; ++index) {
            }

            if (index + 8 < len && contentType.charAt(index) == 'c' && contentType.charAt(index + 1) == 'h' && contentType.charAt(index + 2) == 'a' && contentType.charAt(index + 3) == 'r' && contentType.charAt(index + 4) == 's' && contentType.charAt(index + 5) == 'e' && contentType.charAt(index + 6) == 't' && contentType.charAt(index + 7) == '=') {
               hasCharset = true;
               break;
            }
         }

         String newContentType;
         if (hasCharset) {
            newContentType = contentType.substring(0, semicolonIndex);
            String tail = contentType.substring(index + 8);
            int nextParam = tail.indexOf(59);
            if (nextParam != -1) {
               newContentType = newContentType + tail.substring(nextParam);
            }
         } else {
            newContentType = contentType;
         }

         StringBuilder sb = new StringBuilder(newContentType.length() + characterEncoding.length() + 9);
         return sb.append(newContentType).append(";charset=").append(characterEncoding).toString();
      }
   }

   public static float convertQValueToFloat(DataChunk dc, int startIdx, int stopIdx) {
      float qvalue = 0.0F;
      DataChunk.Type type = dc.getType();

      try {
         int offs;
         switch (type) {
            case String:
               qvalue = convertQValueToFloat(dc.toString(), startIdx, stopIdx);
               break;
            case Buffer:
               BufferChunk bc = dc.getBufferChunk();
               offs = bc.getStart();
               qvalue = convertQValueToFloat(bc.getBuffer(), offs + startIdx, offs + stopIdx);
               break;
            case Chars:
               CharChunk cc = dc.getCharChunk();
               offs = cc.getStart();
               qvalue = convertQValueToFloat(cc.getChars(), offs + startIdx, offs + stopIdx);
         }
      } catch (Exception var7) {
         qvalue = 0.0F;
      }

      return qvalue;
   }

   public static float convertQValueToFloat(Buffer buffer, int startIdx, int stopIdx) {
      float result = 0.0F;
      boolean firstDigitProcessed = false;
      int multIdx = -1;
      int i = 0;

      for(int len = stopIdx - startIdx; i < len; ++i) {
         char c = (char)buffer.get(i + startIdx);
         if (multIdx == -1) {
            if (firstDigitProcessed && c != '.') {
               throw new IllegalArgumentException("Invalid qvalue, " + buffer.toStringContent(Constants.DEFAULT_HTTP_CHARSET, startIdx, stopIdx) + ", detected");
            }

            if (c == '.') {
               multIdx = 0;
               continue;
            }
         }

         if (!Character.isDigit(c)) {
            throw new IllegalArgumentException("Invalid qvalue, " + buffer.toStringContent(Constants.DEFAULT_HTTP_CHARSET, startIdx, stopIdx) + ", detected");
         }

         if (multIdx == -1) {
            result += (float)Character.digit(c, 10);
            firstDigitProcessed = true;
            if (result > 1.0F) {
               throw new IllegalArgumentException("Invalid qvalue, " + buffer.toStringContent(Constants.DEFAULT_HTTP_CHARSET, startIdx, stopIdx) + ", detected");
            }
         } else {
            if (multIdx >= MULTIPLIERS.length) {
               throw new IllegalArgumentException("Invalid qvalue, " + buffer.toStringContent(Constants.DEFAULT_HTTP_CHARSET, startIdx, stopIdx) + ", detected");
            }

            result += (float)Character.digit(c, 10) * MULTIPLIERS[multIdx++];
         }
      }

      return result;
   }

   public static float convertQValueToFloat(String string, int startIdx, int stopIdx) {
      float result = 0.0F;
      boolean firstDigitProcessed = false;
      int multIdx = -1;
      int i = 0;

      for(int len = stopIdx - startIdx; i < len; ++i) {
         char c = string.charAt(i + startIdx);
         if (multIdx == -1) {
            if (firstDigitProcessed && c != '.') {
               throw new IllegalArgumentException("Invalid qvalue, " + new String(string.toCharArray(), startIdx, stopIdx) + ", detected");
            }

            if (c == '.') {
               multIdx = 0;
               continue;
            }
         }

         if (!Character.isDigit(c)) {
            throw new IllegalArgumentException("Invalid qvalue, " + new String(string.toCharArray(), startIdx, stopIdx) + ", detected");
         }

         if (multIdx == -1) {
            result += (float)Character.digit(c, 10);
            firstDigitProcessed = true;
            if (result > 1.0F) {
               throw new IllegalArgumentException("Invalid qvalue, " + new String(string.toCharArray(), startIdx, stopIdx) + ", detected");
            }
         } else {
            if (multIdx >= MULTIPLIERS.length) {
               throw new IllegalArgumentException("Invalid qvalue, " + new String(string.toCharArray(), startIdx, stopIdx) + ", detected");
            }

            result += (float)Character.digit(c, 10) * MULTIPLIERS[multIdx++];
         }
      }

      return result;
   }

   public static float convertQValueToFloat(char[] chars, int startIdx, int stopIdx) {
      float result = 0.0F;
      boolean firstDigitProcessed = false;
      int multIdx = -1;
      int i = 0;

      for(int len = stopIdx - startIdx; i < len; ++i) {
         char c = chars[i + startIdx];
         if (multIdx == -1) {
            if (firstDigitProcessed && c != '.') {
               throw new IllegalArgumentException("Invalid qvalue, " + new String(chars, startIdx, stopIdx) + ", detected");
            }

            if (c == '.') {
               multIdx = 0;
               continue;
            }
         }

         if (!Character.isDigit(c)) {
            throw new IllegalArgumentException("Invalid qvalue, " + new String(chars, startIdx, stopIdx) + ", detected");
         }

         if (multIdx == -1) {
            result += (float)Character.digit(c, 10);
            firstDigitProcessed = true;
            if (result > 1.0F) {
               throw new IllegalArgumentException("Invalid qvalue, " + new String(chars, startIdx, stopIdx) + ", detected");
            }
         } else {
            if (multIdx >= MULTIPLIERS.length) {
               throw new IllegalArgumentException("Invalid qvalue, " + new String(chars, startIdx, stopIdx) + ", detected");
            }

            result += (float)Character.digit(c, 10) * MULTIPLIERS[multIdx++];
         }
      }

      return result;
   }

   public static int longToBuffer(long value, byte[] buffer) {
      int i = buffer.length;
      if (value == 0L) {
         --i;
         buffer[i] = 48;
         return i;
      } else {
         int radix = true;
         boolean negative;
         if (value < 0L) {
            negative = true;
            value = -value;
         } else {
            negative = false;
         }

         do {
            int ch = 48 + (int)(value % 10L);
            --i;
            buffer[i] = (byte)ch;
         } while((value /= 10L) != 0L);

         if (negative) {
            --i;
            buffer[i] = 45;
         }

         return i;
      }
   }

   public static void longToBuffer(long value, Buffer buffer) {
      if (value == 0L) {
         buffer.put(0, (byte)48);
         buffer.limit(1);
      } else {
         int radix = true;
         boolean negative;
         if (value < 0L) {
            negative = true;
            value = -value;
         } else {
            negative = false;
         }

         int position = buffer.limit();

         do {
            int ch = 48 + (int)(value % 10L);
            --position;
            buffer.put(position, (byte)ch);
         } while((value /= 10L) != 0L);

         if (negative) {
            --position;
            buffer.put(position, (byte)45);
         }

         buffer.position(position);
      }
   }

   public static DataChunk filterNonPrintableCharacters(DataChunk message) {
      if (message != null && !message.isNull()) {
         try {
            message.toChars(Charsets.ASCII_CHARSET);
         } catch (CharConversionException var7) {
         }

         CharChunk charChunk = message.getCharChunk();
         char[] content = charChunk.getChars();
         int start = charChunk.getStart();
         int end = charChunk.getEnd();

         for(int i = start; i < end; ++i) {
            char c = content[i];
            if (c <= 31 && c != '\t' || c == 127 || c > 255) {
               content[i] = ' ';
            }
         }

         return message;
      } else {
         return null;
      }
   }

   public static DataChunk filter(DataChunk message) {
      if (message != null && !message.isNull()) {
         try {
            message.toChars(Charsets.ASCII_CHARSET);
         } catch (CharConversionException var7) {
         }

         CharChunk charChunk = message.getCharChunk();
         char[] content = charChunk.getChars();
         StringBuilder result = null;
         int i = charChunk.getStart();

         for(int end = charChunk.getEnd(); i < end; ++i) {
            switch (content[i]) {
               case '"':
                  if (result == null) {
                     result = new StringBuilder(content.length + 50);
                     result.append(content, 0, i);
                  }

                  result.append("&quot;");
                  continue;
               case '&':
                  if (result == null) {
                     result = new StringBuilder(content.length + 50);
                     result.append(content, 0, i);
                  }

                  result.append("&amp;");
                  continue;
               case '<':
                  if (result == null) {
                     result = new StringBuilder(content.length + 50);
                     result.append(content, 0, i);
                  }

                  result.append("&lt;");
                  continue;
               case '>':
                  if (result == null) {
                     result = new StringBuilder(content.length + 50);
                     result.append(content, 0, i);
                  }

                  result.append("&gt;");
                  continue;
            }

            char c = content[i];
            if ((c > 31 || c == '\t') && c != 127 && c <= 255) {
               if (result != null) {
                  result.append(c);
               }
            } else {
               if (result == null) {
                  result = new StringBuilder(content.length + 50);
                  result.append(content, 0, i);
               }

               result.append("&#").append(c).append(';');
            }
         }

         if (result != null) {
            i = result.length();
            char[] finalResult = new char[i];
            result.getChars(0, i, finalResult, 0);
            message.setChars(finalResult, 0, finalResult.length);
         }

         return message;
      } else {
         return null;
      }
   }

   public static String filter(String message) {
      if (message == null) {
         return null;
      } else {
         StringBuilder result = null;
         int len = message.length();

         for(int i = 0; i < len; ++i) {
            char c = message.charAt(i);
            switch (c) {
               case '"':
                  if (result == null) {
                     result = new StringBuilder(len + 50);
                     result.append(message, 0, i);
                  }

                  result.append("&quot;");
                  continue;
               case '&':
                  if (result == null) {
                     result = new StringBuilder(len + 50);
                     result.append(message, 0, i);
                  }

                  result.append("&amp;");
                  continue;
               case '<':
                  if (result == null) {
                     result = new StringBuilder(len + 50);
                     result.append(message, 0, i);
                  }

                  result.append("&lt;");
                  continue;
               case '>':
                  if (result == null) {
                     result = new StringBuilder(len + 50);
                     result.append(message, 0, i);
                  }

                  result.append("&gt;");
                  continue;
            }

            if ((c > 31 || c == '\t') && c != 127 && c <= 255) {
               if (result != null) {
                  result.append(c);
               }
            } else {
               if (result == null) {
                  result = new StringBuilder(len + 50);
                  result.append(message, 0, i);
               }

               result.append("&#").append(c).append(';');
            }
         }

         return result == null ? message : result.toString();
      }
   }
}
