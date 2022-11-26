package com.bea.core.jatmi.common;

import com.bea.core.jatmi.intf.LogService;

public final class ntrace {
   public static final int TBRIDGE_IO = 10000;
   public static final int TBRIDGE_EX = 15000;
   public static final int GWT_IO = 20000;
   public static final int GWT_EX = 25000;
   public static final int JATMI_IO = 50000;
   public static final int JATMI_EX = 55000;
   public static final int CORBA_IO = 60000;
   public static final int CORBA_EX = 65000;
   public static final int WTC_ERROR = 50;
   public static final int TB_EX = 1;
   public static final int GW_EX = 2;
   public static final int JA_EX = 4;
   public static final int CO_EX = 8;
   public static final int WTC_CNF = 16;
   public static final int WTC_PDU = 32;
   public static final int WTC_UDATA = 64;
   public static final int SEC_INT = 128;
   public static final int XA_INT = 256;
   public static final int TDOM_INT = 512;
   public static final int LOG_LEVEL_DEBUG = 0;
   public static final int LOG_LEVEL_INFO = 1;
   public static final int LOG_LEVEL_WARN = 2;
   public static final int LOG_LEVEL_ERROR = 3;
   public static final int LOG_LEVEL_FATAL = 4;
   private static int _level = -1;
   private static LogService _logger = null;

   public static boolean init(LogService n_logger) {
      if (n_logger != null && n_logger instanceof LogService) {
         _logger = n_logger;
         String tracelevel;
         if ((tracelevel = System.getProperty("Weblogic.wtc.TraceLevel")) != null) {
            _level = Integer.parseInt(tracelevel);
         }

         _logger.setTraceLevel(_level);
         _logger.doTrace("INFO: Logging service enabled.");
         return true;
      } else {
         return false;
      }
   }

   public static int getTraceLevel() {
      return _level;
   }

   public static void doTrace(String todo) {
      if (_logger != null) {
         _logger.doTrace(todo);
      }

   }

   public static void doTrace(int type, String todo) {
      if (_logger != null) {
         _logger.doTrace(type, todo);
      }

   }

   public static void doTrace(int level, int type, String todo) {
      if (_logger != null) {
         _logger.doTrace(level, type, todo);
      }

   }

   public static boolean isTraceEnabled(int type) {
      return _logger != null ? _logger.isTraceEnabled(type) : false;
   }

   public static boolean isMixedTraceEnabled(int type) {
      return _logger != null ? _logger.isMixedTraceEnabled(type) : false;
   }
}
