package com.bea.logging;

import java.util.logging.Level;

public class LogLevel extends Level {
   public static final int OFF_INT = Integer.MAX_VALUE;
   public static final int EMERGENCY_INT = 1090;
   public static final int ALERT_INT = 1060;
   public static final int CRITICAL_INT = 1030;
   public static final int ERROR_INT = 980;
   public static final int WARNING_INT;
   public static final int NOTICE_INT = 880;
   public static final int INFO_INT;
   public static final int DEBUG_INT = 495;
   public static final int TRACE_INT = 295;
   public static final LogLevel OFF;
   public static final LogLevel EMERGENCY;
   public static final LogLevel CRITICAL;
   public static final LogLevel ERROR;
   public static final LogLevel ALERT;
   public static final LogLevel NOTICE;
   public static final LogLevel DEBUG;
   public static final LogLevel WARNING;
   public static final LogLevel INFO;
   public static final LogLevel TRACE;
   public static final String LOGGING_TEXT_PROPS = "weblogic.i18n.logging.LoggingTextLocalizer";
   private static final long serialVersionUID = 1796084591280954044L;
   private final int severity;
   private String localizedName;

   protected LogLevel(String name, int value, int severity) {
      super(name, value, "weblogic.i18n.logging.LoggingTextLocalizer");
      this.severity = severity;
      this.localizedName = super.getLocalizedName();
   }

   /** @deprecated */
   @Deprecated
   protected String getHeader(Level level) {
      return level.getLocalizedName();
   }

   public int getSeverity() {
      return this.severity;
   }

   public String getLocalizedName() {
      return this.localizedName;
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof LogLevel) {
         LogLevel other = (LogLevel)obj;
         return this.intValue() == other.intValue() && this.getName().equals(other.getName());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.intValue();
   }

   public static Level getLevel(int severity) {
      switch (severity) {
         case 0:
            return OFF;
         case 1:
            return EMERGENCY;
         case 2:
            return ALERT;
         case 4:
            return CRITICAL;
         case 8:
            return ERROR;
         case 16:
            return WARNING;
         case 32:
            return NOTICE;
         case 64:
            return INFO;
         case 128:
            return DEBUG;
         case 256:
            return TRACE;
         default:
            return INFO;
      }
   }

   public static int getSeverity(Level level) {
      int levelValue = level.intValue();
      if (levelValue == Integer.MAX_VALUE) {
         return 0;
      } else if (levelValue >= 1090) {
         return 1;
      } else if (levelValue >= 1060) {
         return 2;
      } else if (levelValue >= 1030) {
         return 4;
      } else if (levelValue >= 980) {
         return 8;
      } else if (levelValue >= WARNING_INT) {
         return 16;
      } else if (levelValue >= 880) {
         return 32;
      } else if (levelValue >= INFO_INT) {
         return 64;
      } else {
         return levelValue >= 495 ? 128 : 256;
      }
   }

   static {
      WARNING_INT = Level.WARNING.intValue();
      INFO_INT = Level.INFO.intValue();
      OFF = new LogLevel("Off", Integer.MAX_VALUE, 0);
      EMERGENCY = new LogLevel("Emergency", 1090, 1);
      CRITICAL = new LogLevel("Critical", 1030, 4);
      ERROR = new LogLevel("Error", 980, 8);
      ALERT = new LogLevel("Alert", 1060, 2);
      NOTICE = new LogLevel("Notice", 880, 32);
      DEBUG = new LogLevel("Debug", 495, 128);
      WARNING = new LogLevel("Warning", WARNING_INT, 16);
      INFO = new LogLevel("Info", INFO_INT, 64);
      TRACE = new LogLevel("Trace", 295, 256);
   }
}
