package weblogic.servlet.logging;

import weblogic.utils.XSSUtils;

public class LoggerUtils {
   static final int MAX_LOGGING_URI_LENGTH;
   static final int DEFAULT_MAX_LOGGING_URI_LENGTH = 256;

   public static String encodeAndTruncate(String s) {
      if (s != null && s.length() > 0) {
         String result = s.length() > MAX_LOGGING_URI_LENGTH ? s.substring(0, MAX_LOGGING_URI_LENGTH) : s;
         return XSSUtils.encodeXSS(result);
      } else {
         return "-";
      }
   }

   static {
      int len = Integer.getInteger("weblogic.servlet.maxLoggingURILength", 256);
      MAX_LOGGING_URI_LENGTH = len > 0 ? len : 256;
   }
}
