package weblogic.servlet.internal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayMap;
import weblogic.utils.http.BytesToString;
import weblogic.utils.string.ThreadLocalDateFormat;

public abstract class AbstractResponseHeaders {
   protected static final String SYSTEM_ENCODING = System.getProperty("file.encoding");
   public static final String CACHE_CONTROL = "Cache-Control";
   protected static final String CONNECTION = "Connection";
   public static final String DATE = "Date";
   protected static final String PRAGMA = "Pragma";
   private static final String TRAILER = "Trailer";
   protected static final String TRANSFER_ENCODING = "Transfer-Encoding";
   private static final String WARNING = "Warning";
   protected static final String ACCEPT_RANGES = "Accept-Ranges";
   private static final String AGE = "Age";
   public static final String LOCATION = "Location";
   private static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
   private static final String RETRY_AFTER = "Retry-After";
   public static final String SERVER = "Server";
   private static final String VARY = "Vary";
   private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
   protected static final String CONTENT_LENGTH = "Content-Length";
   protected static final String CONTENT_TYPE = "Content-Type";
   private static final String CONTENT_ENCODING = "Content-Encoding";
   private static final String CONTENT_RANGE = "Content-Range";
   protected static final String EXPIRES = "Expires";
   protected static final String LAST_MODIFIED = "Last-Modified";
   protected static final String SET_COOKIE = "Set-Cookie";
   protected static final String CONTENT_DISPOSITION = "Content-Disposition";
   protected static final String WL_RESULT = "WL-Result";
   public static final String P3P = "P3P";
   public static final String X_POWERED_BY = "X-Powered-By";
   private static final String NO_CACHE = "no-cache";
   private static final String EQUAL = "=";
   private static final String QUOTE = "\"";
   private static final String COMMA = ",";
   private static final String NO_CACHE_SET_COOKIE = "no-cache=\"Set-Cookie\"";
   protected static final byte[] CRLF = new byte[]{13, 10};
   protected static final byte[] COLON_SPACE = new byte[]{58, 32};
   protected final Object[] values = new Object[15];
   protected ArrayMap genericHeaders;
   protected static final byte[][] standardHeadersAsBytes = new byte[][]{getValueBytes("Cache-Control"), getValueBytes("Connection"), getValueBytes("Date"), getValueBytes("Pragma"), getValueBytes("Transfer-Encoding"), getValueBytes("Accept-Ranges"), getValueBytes("Location"), getValueBytes("Server"), getValueBytes("Content-Length"), getValueBytes("Content-Type"), getValueBytes("Expires"), getValueBytes("Last-Modified"), getValueBytes("Content-Disposition"), getValueBytes("WL-Result"), getValueBytes("Trailer")};
   private static final int CACHE_CONTROL_OFFSET = 0;
   private static final int CONNECTION_OFFSET = 1;
   private static final int DATE_OFFSET = 2;
   private static final int PRAGMA_OFFSET = 3;
   private static final int TRANSFER_ENCODING_OFFSET = 4;
   private static final int ACCEPT_RANGES_OFFSET = 5;
   protected static final int LOCATION_OFFSET = 6;
   private static final int SERVER_OFFSET = 7;
   protected static final int CONTENT_LENGTH_OFFSET = 8;
   private static final int CONTENT_TYPE_OFFSET = 9;
   private static final int EXPIRES_OFFSET = 10;
   private static final int LAST_MODIFIED_OFFSET = 11;
   protected static final int CONTENT_DISPOSITION_OFFSET = 12;
   private static final int WL_RESULT_OFFSET = 13;
   private static final int TRAILER_OFFSET = 14;
   protected static final int NUM_HEADERS = 15;
   private static final Set disallowedTrailerFields = new HashSet();
   protected ServletResponseImpl response;
   protected String encoding;

   private static byte[] getValueBytes(String stdHeader) {
      try {
         return (stdHeader + ": ").getBytes("ISO-8859-1");
      } catch (UnsupportedEncodingException var2) {
         return (stdHeader + ": ").getBytes();
      }
   }

   private static boolean eq(String name, String constant, int len) {
      return name == constant || constant.regionMatches(true, 0, name, 0, len);
   }

   private static int getHeaderOffset(String name) {
      switch (name.length()) {
         case 4:
            if (eq(name, "Date", 4)) {
               return 2;
            }
         case 5:
         case 11:
         case 15:
         case 16:
         case 18:
         default:
            break;
         case 6:
            if (eq(name, "Server", 6)) {
               return 7;
            }

            if (eq(name, "Pragma", 6)) {
               return 3;
            }
            break;
         case 7:
            if (eq(name, "Expires", 7)) {
               return 10;
            }
            break;
         case 8:
            if (eq(name, "Location", 8)) {
               return 6;
            }

            if (eq(name, "Trailer", 7)) {
               return 14;
            }
            break;
         case 9:
            if (eq(name, "WL-Result", 9)) {
               return 13;
            }
            break;
         case 10:
            if (eq(name, "Connection", 10)) {
               return 1;
            }
            break;
         case 12:
            if (eq(name, "Content-Type", 12)) {
               return 9;
            }
            break;
         case 13:
            if (eq(name, "Last-Modified", 13)) {
               return 11;
            }

            if (eq(name, "Accept-Ranges", 13)) {
               return 5;
            }

            if (eq(name, "Cache-Control", 13)) {
               return 0;
            }
            break;
         case 14:
            if (eq(name, "Content-Length", 14)) {
               return 8;
            }
            break;
         case 17:
            if (eq(name, "Transfer-Encoding", 17)) {
               return 4;
            }
         case 19:
            if (eq(name, "Content-Disposition", 19)) {
               return 12;
            }
      }

      return -1;
   }

   static String getDateString(long time) {
      return ThreadLocalDateFormat.getInstance().getDate(time);
   }

   public void setEncoding(String newEnc) {
      if (BytesToString.is8BitUnicodeSubset(newEnc)) {
         this.encoding = null;
      } else {
         this.encoding = newEnc;
      }

   }

   protected void addHeader(String name, String value) {
      int offset = getHeaderOffset(name);
      Object oldval;
      if (offset > -1) {
         oldval = this.values[offset];
         if (this.isDuplicateStdHeaderAllowed(offset)) {
            this.values[offset] = this.mergeHeaderValue(oldval, value);
         } else {
            this.values[offset] = value;
         }

      } else {
         if (this.genericHeaders == null) {
            this.genericHeaders = new ArrayMap(8);
         }

         oldval = this.genericHeaders.get(name);
         this.genericHeaders.put(name, this.mergeHeaderValue(oldval, value));
      }
   }

   protected void setHeader(String name, String value) {
      int offset = getHeaderOffset(name);
      if (offset > -1) {
         this.values[offset] = value;
      } else {
         if (this.genericHeaders == null) {
            this.genericHeaders = new ArrayMap();
         }

         this.genericHeaders.put(name, value);
      }
   }

   protected String getHeader(String name) {
      int offset = getHeaderOffset(name);
      if (offset > -1) {
         return this.getHeaderValue(offset);
      } else if (this.genericHeaders == null) {
         return null;
      } else {
         Object value = this.genericHeaders.get(name);
         if (value == null) {
            return null;
         } else {
            return value instanceof String ? (String)value : ((String[])((String[])value))[0];
         }
      }
   }

   protected Collection getHeaderNames() {
      ArrayList ret = new ArrayList();

      for(int i = 0; i < 15; ++i) {
         Object value = this.values[i];
         if (value != null) {
            ret.add(new String(standardHeadersAsBytes[i]));
         }
      }

      if (this.genericHeaders != null) {
         Iterator it = this.genericHeaders.keySet().iterator();

         while(it.hasNext()) {
            String name = (String)it.next();
            ret.add(name);
         }
      }

      return ret;
   }

   protected Collection getHeaders(String name) {
      int offset = getHeaderOffset(name);
      if (offset > -1) {
         return this.transferValuesToHeaders(this.values[offset]);
      } else {
         return (Collection)(this.genericHeaders == null ? Collections.emptyList() : this.transferValuesToHeaders(this.genericHeaders.get(name)));
      }
   }

   protected void unsetHeader(String name) {
      int offset = getHeaderOffset(name);
      if (offset > -1) {
         this.values[offset] = null;
      } else {
         if (this.genericHeaders != null) {
            this.genericHeaders.remove(name);
         }

      }
   }

   protected boolean containsHeader(String name) {
      int offset = getHeaderOffset(name);
      if (offset > -1) {
         return this.values[offset] != null;
      } else {
         return this.genericHeaders == null ? false : this.genericHeaders.containsKey(name);
      }
   }

   protected void setIntHeader(String name, int value) {
      this.setHeader(name, Integer.toString(value));
   }

   protected void setDateHeader(String name, long date) {
      this.setHeader(name, getDateString(date));
   }

   protected void addIntHeader(String name, int value) {
      this.addHeader(name, Integer.toString(value));
   }

   protected void addDateHeader(String name, long date) {
      this.addHeader(name, getDateString(date));
   }

   protected void setContentLength(long value) {
      this.values[8] = Long.toString(value);
   }

   protected void setContentType(String value) {
      this.values[9] = value;
   }

   protected void setDate(long value) {
      this.values[2] = ThreadLocalDateFormat.getInstance().getDate(value);
   }

   protected void setServer(String value) {
      this.values[7] = value;
   }

   protected boolean getKeepAlive() {
      return "Keep-Alive".equalsIgnoreCase(this.getHeaderValue(1));
   }

   protected void setConnection(String value) {
      this.values[1] = value;
   }

   protected void disableCacheControlForCookie() {
      Object cacheControl = this.values[0];
      if (cacheControl == null) {
         this.values[0] = "no-cache=\"Set-Cookie\"";
      } else {
         String cacheControlValue = null;
         if (cacheControl instanceof String[]) {
            cacheControlValue = StringUtils.join((String[])((String[])cacheControl), ",");
         } else {
            cacheControlValue = (String)cacheControl;
         }

         String lowCaseCacheControl = cacheControlValue.toLowerCase();
         if (lowCaseCacheControl.indexOf("no-cache") < 0) {
            this.values[0] = cacheControl + "," + "no-cache=\"Set-Cookie\"";
         } else {
            String noCacheKey = "no-cache=\"";
            int start = lowCaseCacheControl.indexOf(noCacheKey);
            if (start >= 0) {
               int valueStart = start + noCacheKey.length();
               int valueEnd = lowCaseCacheControl.indexOf("\"", valueStart);
               String noCacheValue = cacheControlValue.substring(valueStart, valueEnd);
               StringBuilder result = new StringBuilder();
               result.append(cacheControlValue.substring(0, valueStart)).append("Set-Cookie").append(",").append(noCacheValue).append(cacheControlValue.substring(valueEnd));
               this.values[0] = result.toString();
            }

         }
      }
   }

   private Collection transferValuesToHeaders(Object values) {
      if (values == null) {
         return Collections.emptyList();
      } else if (!(values instanceof String[])) {
         return Arrays.asList(values.toString());
      } else {
         ArrayList ret = new ArrayList();
         String[] var3 = (String[])((String[])values);
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String value = var3[var5];
            ret.add(value);
         }

         return ret;
      }
   }

   private Object mergeHeaderValue(Object oldval, String value) {
      if (oldval == null) {
         return value;
      } else {
         String[] oldvals;
         if (oldval instanceof String) {
            oldvals = new String[]{(String)oldval, value};
            return oldvals;
         } else {
            oldvals = (String[])((String[])oldval);
            String[] newvals = new String[oldvals.length + 1];
            System.arraycopy(oldvals, 0, newvals, 0, oldvals.length);
            newvals[oldvals.length] = value;
            return newvals;
         }
      }
   }

   protected boolean isDuplicateStdHeaderAllowed(int offset) {
      switch (offset) {
         case 0:
         case 3:
         case 5:
            return true;
         default:
            return false;
      }
   }

   private String getHeaderValue(int offset) {
      Object val = this.values[offset];
      if (val == null) {
         return null;
      } else {
         return val instanceof String[] ? ((String[])((String[])val))[0] : val.toString();
      }
   }

   boolean skipDisallowedTrailerField(String name) {
      return disallowedTrailerFields.contains(name.toLowerCase());
   }

   public String toString() {
      StringBuilder buf = new StringBuilder(512);

      for(int i = 0; i < 15; ++i) {
         if (this.values[i] != null) {
            buf.append(new String(standardHeadersAsBytes[i])).append(": ").append(this.values[i]).append('\n');
         }
      }

      if (this.genericHeaders != null) {
         Iterator i = this.genericHeaders.keySet().iterator();

         while(i.hasNext()) {
            String name = (String)i.next();
            buf.append(name).append(": ").append(this.genericHeaders.get(name)).append('\n');
         }
      }

      return buf.toString();
   }

   protected abstract int writeHeaders(ServletOutputStreamImpl var1, String var2) throws IOException;

   protected abstract String getResponseInfo(String var1);

   static {
      disallowedTrailerFields.add("Transfer-Encoding".toLowerCase());
      disallowedTrailerFields.add("Content-Length".toLowerCase());
      disallowedTrailerFields.add("Cache-Control".toLowerCase());
      disallowedTrailerFields.add("Pragma".toLowerCase());
      disallowedTrailerFields.add("Proxy-Authenticate".toLowerCase());
      disallowedTrailerFields.add("WWW-Authenticate".toLowerCase());
      disallowedTrailerFields.add("Set-Cookie".toLowerCase());
      disallowedTrailerFields.add("Cookie".toLowerCase());
      disallowedTrailerFields.add("Age".toLowerCase());
      disallowedTrailerFields.add("Expires".toLowerCase());
      disallowedTrailerFields.add("Date".toLowerCase());
      disallowedTrailerFields.add("Location".toLowerCase());
      disallowedTrailerFields.add("Retry-After".toLowerCase());
      disallowedTrailerFields.add("Vary".toLowerCase());
      disallowedTrailerFields.add("Warning".toLowerCase());
      disallowedTrailerFields.add("Content-Encoding".toLowerCase());
      disallowedTrailerFields.add("Content-Type".toLowerCase());
      disallowedTrailerFields.add("Content-Range".toLowerCase());
      disallowedTrailerFields.add("Trailer".toLowerCase());
   }
}
