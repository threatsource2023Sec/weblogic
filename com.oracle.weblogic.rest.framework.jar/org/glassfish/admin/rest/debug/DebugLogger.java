package org.glassfish.admin.rest.debug;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DebugLogger {
   private static final String DEFAULT_CATEGORY = "DebugManagementServicesResource";
   private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
   private String prefix;
   private weblogic.diagnostics.debug.DebugLogger debugLogger;

   public static DebugLogger getDebugLogger(Class clazz) {
      return getDebugLogger("DebugManagementServicesResource", clazz);
   }

   public static DebugLogger getDebugLogger(String category, Class clazz) {
      return new DebugLogger(category, clazz.getName());
   }

   public static String formatDate(long time) {
      return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")).format(new Date(time));
   }

   private DebugLogger(String category, String prefix) {
      this.prefix = prefix;
      this.debugLogger = weblogic.diagnostics.debug.DebugLogger.getDebugLogger(category);
   }

   public boolean isDebugEnabled() {
      return this.debugLogger.isDebugEnabled();
   }

   public void debug(String msg) {
      String message = this.prefix != null ? this.prefix + " " + msg : msg;
      this.debugLogger.debug(message);
   }

   public void debug(String msg, Throwable t) {
      String message = this.prefix != null ? this.prefix + " " + msg : msg;
      this.debugLogger.debug(message, t);
   }
}
