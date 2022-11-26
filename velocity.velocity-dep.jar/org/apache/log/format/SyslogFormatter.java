package org.apache.log.format;

import org.apache.log.LogEvent;
import org.apache.log.Priority;

public class SyslogFormatter implements Formatter, org.apache.log.Formatter {
   public static final int PRIORITY_DEBUG = 7;
   public static final int PRIORITY_INFO = 6;
   public static final int PRIORITY_NOTICE = 5;
   public static final int PRIORITY_WARNING = 4;
   public static final int PRIORITY_ERR = 3;
   public static final int PRIORITY_CRIT = 2;
   public static final int PRIORITY_ALERT = 1;
   public static final int PRIORITY_EMERG = 0;
   public static final int FACILITY_KERN = 0;
   public static final int FACILITY_USER = 8;
   public static final int FACILITY_MAIL = 16;
   public static final int FACILITY_DAEMON = 24;
   public static final int FACILITY_AUTH = 32;
   public static final int FACILITY_SYSLOG = 40;
   public static final int FACILITY_LPR = 48;
   public static final int FACILITY_NEWS = 56;
   public static final int FACILITY_UUCP = 64;
   public static final int FACILITY_CRON = 72;
   public static final int FACILITY_AUTHPRIV = 80;
   public static final int FACILITY_FTP = 88;
   public static final int FACILITY_LOCAL0 = 128;
   public static final int FACILITY_LOCAL1 = 136;
   public static final int FACILITY_LOCAL2 = 144;
   public static final int FACILITY_LOCAL3 = 152;
   public static final int FACILITY_LOCAL4 = 160;
   public static final int FACILITY_LOCAL5 = 168;
   public static final int FACILITY_LOCAL6 = 176;
   public static final int FACILITY_LOCAL7 = 184;
   protected static final String[] FACILITY_DESCRIPTIONS = new String[]{"kern", "user", "mail", "daemon", "auth", "syslog", "lpr", "news", "uucp", "cron", "authpriv", "ftp", "", "", "", "", "local0", "local1", "local2", "local3", "local4", "local5", "local6", "local7"};
   private int m_facility;
   private boolean m_showFacilityBanner;

   public SyslogFormatter() {
      this(8);
   }

   public SyslogFormatter(int facility) {
      this(facility, true);
   }

   public SyslogFormatter(int facility, boolean showFacilityBanner) {
      this.m_facility = facility;
      this.m_showFacilityBanner = showFacilityBanner;
   }

   public String format(LogEvent event) {
      int priority = this.getSyslogPriority(event);
      int facility = this.getSyslogFacility(event);
      String message = event.getMessage();
      if (null == message) {
         message = "";
      }

      if (this.m_showFacilityBanner) {
         message = this.getFacilityDescription(facility) + ": " + message;
      }

      return "<" + (facility | priority) + "> " + message;
   }

   protected String getFacilityDescription(int facility) {
      return FACILITY_DESCRIPTIONS[facility >> 3];
   }

   protected int getSyslogFacility(LogEvent event) {
      return this.m_facility;
   }

   protected int getSyslogPriority(LogEvent event) {
      if (event.getPriority().isLowerOrEqual(Priority.DEBUG)) {
         return 7;
      } else if (event.getPriority().isLowerOrEqual(Priority.INFO)) {
         return 6;
      } else if (event.getPriority().isLowerOrEqual(Priority.WARN)) {
         return 4;
      } else {
         return event.getPriority().isLowerOrEqual(Priority.ERROR) ? 3 : 2;
      }
   }
}
