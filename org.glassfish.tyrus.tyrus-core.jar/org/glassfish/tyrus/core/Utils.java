package org.glassfish.tyrus.core;

import java.net.URI;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;

public class Utils {
   private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
   private static final List FILTERED_HEADERS = Arrays.asList("Authorization");

   public static List parseHeaderValue(String headerValue) {
      List values = new ArrayList();
      int state = 0;
      StringBuilder sb = new StringBuilder();
      char[] var4 = headerValue.toCharArray();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         char c = var4[var6];
         switch (state) {
            case 0:
               if (!Character.isWhitespace(c)) {
                  if (c == '"') {
                     state = 2;
                     sb.append(c);
                  } else {
                     sb.append(c);
                     state = 1;
                  }
               }
               break;
            case 1:
               if (c != ',') {
                  sb.append(c);
               } else {
                  values.add(sb.toString());
                  sb = new StringBuilder();
                  state = 0;
               }
               break;
            case 2:
               if (c != '"') {
                  sb.append(c);
               } else {
                  sb.append(c);
                  values.add(sb.toString());
                  sb = new StringBuilder();
                  state = 3;
               }
               break;
            case 3:
               if (!Character.isWhitespace(c) && c == ',') {
                  state = 0;
               }
         }
      }

      if (sb.length() > 0) {
         values.add(sb.toString());
      }

      return values;
   }

   public static byte[] getRemainingArray(ByteBuffer buffer) {
      if (buffer == null) {
         return new byte[0];
      } else {
         byte[] ret = new byte[buffer.remaining()];
         if (buffer.hasArray()) {
            byte[] array = buffer.array();
            System.arraycopy(array, buffer.arrayOffset() + buffer.position(), ret, 0, ret.length);
         } else {
            buffer.get(ret);
         }

         return ret;
      }
   }

   public static String getHeaderFromList(List list) {
      StringBuilder sb = new StringBuilder();
      Iterator it = list.iterator();

      while(it.hasNext()) {
         sb.append(it.next());
         if (it.hasNext()) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   public static List getStringList(List list, Stringifier stringifier) {
      List result = new ArrayList();
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         Object item = var3.next();
         if (stringifier != null) {
            result.add(stringifier.toString(item));
         } else {
            result.add(item.toString());
         }
      }

      return result;
   }

   public static String getHeaderFromList(List list, Stringifier stringifier) {
      StringBuilder sb = new StringBuilder();
      Iterator it = list.iterator();

      while(it.hasNext()) {
         if (stringifier != null) {
            sb.append(stringifier.toString(it.next()));
         } else {
            sb.append(it.next());
         }

         if (it.hasNext()) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   public static void checkNotNull(Object reference, String parameterName) {
      if (reference == null) {
         throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL(parameterName));
      }
   }

   public static byte[] toArray(long value) {
      byte[] b = new byte[8];

      for(int i = 7; i >= 0 && value > 0L; --i) {
         b[i] = (byte)((int)(value & 255L));
         value >>= 8;
      }

      return b;
   }

   public static long toLong(byte[] bytes, int start, int end) {
      long value = 0L;

      for(int i = start; i < end; ++i) {
         value <<= 8;
         value ^= (long)bytes[i] & 255L;
      }

      return value;
   }

   public static List toString(byte[] bytes) {
      return toString(bytes, 0, bytes.length);
   }

   public static List toString(byte[] bytes, int start, int end) {
      List list = new ArrayList();

      for(int i = start; i < end; ++i) {
         list.add(Integer.toHexString(bytes[i] & 255).toUpperCase(Locale.US));
      }

      return list;
   }

   public static ByteBuffer appendBuffers(ByteBuffer buffer, ByteBuffer buffer1, int incomingBufferSize, int BUFFER_STEP_SIZE) {
      int limit = buffer.limit();
      int capacity = buffer.capacity();
      int remaining = buffer.remaining();
      int len = buffer1.remaining();
      if (len < capacity - limit) {
         buffer.mark();
         buffer.position(limit);
         buffer.limit(capacity);
         buffer.put(buffer1);
         buffer.limit(limit + len);
         buffer.reset();
         return buffer;
      } else if (remaining + len < capacity) {
         buffer.compact();
         buffer.put(buffer1);
         buffer.flip();
         return buffer;
      } else {
         int newSize = remaining + len;
         if (newSize > incomingBufferSize) {
            throw new IllegalArgumentException(LocalizationMessages.BUFFER_OVERFLOW());
         } else {
            int roundedSize = newSize % BUFFER_STEP_SIZE > 0 ? (newSize / BUFFER_STEP_SIZE + 1) * BUFFER_STEP_SIZE : newSize;
            ByteBuffer result = ByteBuffer.allocate(roundedSize > incomingBufferSize ? newSize : roundedSize);
            result.put(buffer);
            result.put(buffer1);
            result.flip();
            return result;
         }
      }
   }

   public static Object getProperty(Map properties, String key, Class type) {
      return getProperty(properties, key, type, (Object)null);
   }

   public static Object getProperty(Map properties, String key, Class type, Object defaultValue) {
      if (properties != null) {
         Object o = properties.get(key);
         if (o != null) {
            try {
               if (type.isAssignableFrom(o.getClass())) {
                  return o;
               }

               if (type.equals(Integer.class)) {
                  return Integer.valueOf(o.toString());
               }

               if (type.equals(Long.class)) {
                  return Long.valueOf(o.toString());
               }

               if (!type.equals(Boolean.class)) {
                  if (type.isEnum()) {
                     try {
                        return Enum.valueOf(type, o.toString().trim().toUpperCase(Locale.US));
                     } catch (Exception var6) {
                        return defaultValue;
                     }
                  }

                  return null;
               }

               return o.toString().equals("1") || Boolean.valueOf(o.toString());
            } catch (Throwable var7) {
               LOGGER.log(Level.CONFIG, String.format("Invalid type of configuration property of %s (%s), %s cannot be cast to %s", key, o.toString(), o.getClass().toString(), type.toString()));
               return null;
            }
         }
      }

      return defaultValue;
   }

   public static int getWsPort(URI uri) {
      return getWsPort(uri, uri.getScheme());
   }

   public static int getWsPort(URI uri, String scheme) {
      if (uri.getPort() == -1) {
         if ("wss".equals(scheme)) {
            return 443;
         } else {
            return "ws".equals(scheme) ? 80 : -1;
         }
      } else {
         return uri.getPort();
      }
   }

   public static Date parseHttpDate(String stringValue) throws ParseException {
      SimpleDateFormat formatRfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

      try {
         return formatRfc1123.parse(stringValue);
      } catch (ParseException var7) {
         SimpleDateFormat formatRfc1036 = new SimpleDateFormat("EEE, dd-MMM-yy HH:mm:ss zzz");

         try {
            return formatRfc1036.parse(stringValue);
         } catch (ParseException var6) {
            SimpleDateFormat formatAnsiCAsc = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy");
            return formatAnsiCAsc.parse(stringValue);
         }
      }
   }

   public static String stringifyUpgradeRequest(UpgradeRequest upgradeRequest) {
      if (upgradeRequest == null) {
         return null;
      } else {
         StringBuilder request = new StringBuilder();
         request.append("GET ");
         request.append(upgradeRequest.getRequestUri());
         request.append("\n");
         appendHeaders(request, upgradeRequest.getHeaders());
         return request.toString();
      }
   }

   public static String stringifyUpgradeResponse(UpgradeResponse upgradeResponse) {
      if (upgradeResponse == null) {
         return null;
      } else {
         StringBuilder request = new StringBuilder();
         request.append(upgradeResponse.getStatus());
         request.append("\n");
         appendHeaders(request, upgradeResponse.getHeaders());
         return request.toString();
      }
   }

   private static void appendHeaders(StringBuilder message, Map headers) {
      Iterator var2 = headers.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry header = (Map.Entry)var2.next();
         StringBuilder value = new StringBuilder();

         String valuePart;
         for(Iterator var5 = ((List)header.getValue()).iterator(); var5.hasNext(); value.append(valuePart)) {
            valuePart = (String)var5.next();
            if (value.length() != 0) {
               value.append(", ");
            }
         }

         appendHeader(message, (String)header.getKey(), value.toString());
      }

   }

   private static void appendHeader(StringBuilder message, String key, String value) {
      message.append(key);
      message.append(": ");
      Iterator var3 = FILTERED_HEADERS.iterator();

      while(var3.hasNext()) {
         String filteredHeader = (String)var3.next();
         if (filteredHeader.equals(key)) {
            value = "*****";
         }
      }

      message.append(value);
      message.append("\n");
   }

   public abstract static class Stringifier {
      abstract String toString(Object var1);
   }
}
