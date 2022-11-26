package weblogic.servlet.internal;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import weblogic.utils.StringUtils;

public final class CharsetMap {
   private Map userMap = null;

   public CharsetMap(Map userCharsetMap) {
      this.userMap = userCharsetMap;
   }

   public String getJavaCharset(String charset) {
      if (charset == null) {
         return null;
      } else {
         String jcs = null;
         if (this.userMap != null) {
            jcs = (String)this.userMap.get(charset);
         }

         if (jcs == null) {
            jcs = weblogic.utils.CharsetMap.getJavaFromIANA(charset);
         }

         return jcs == null ? charset : jcs;
      }
   }

   public void addMapping(String ianaName, String javaName) {
      if (this.userMap == null) {
         this.userMap = new HashMap();
      }

      this.userMap.put(ianaName, javaName);
   }

   public void addMapping(Map charsetMap) {
      if (charsetMap != null && !charsetMap.isEmpty()) {
         if (this.userMap == null) {
            this.userMap = new HashMap();
         }

         this.userMap.putAll(charsetMap);
      }
   }

   public InputStreamReader makeI18NReader(String charset, InputStream sis) throws UnsupportedEncodingException {
      if (charset == null) {
         return new InputStreamReader(sis);
      } else {
         try {
            return new InputStreamReader(sis, this.getJavaCharset(charset));
         } catch (IllegalArgumentException var4) {
            throw new UnsupportedEncodingException(this.getEncodingErr(charset));
         } catch (UnsupportedEncodingException var5) {
            throw new UnsupportedEncodingException(this.getEncodingErr(charset));
         }
      }
   }

   private String getEncodingErr(String charset) {
      String err = charset.equals(this.getJavaCharset(charset)) ? "Charset: '" + charset + "' not recognized, and there is no alias for it in the WebServerMBean" : "Neither charset: '" + charset + "' nor the WebServerMBean alias for it: '" + this.getJavaCharset(charset) + "' identifies a supported Java charset.";
      return err;
   }

   static boolean isCharsetAllowedForType(String contentType) {
      if (contentType == null) {
         return false;
      } else {
         return StringUtils.indexOfIgnoreCase("application/octet-stream", contentType) <= -1;
      }
   }
}
