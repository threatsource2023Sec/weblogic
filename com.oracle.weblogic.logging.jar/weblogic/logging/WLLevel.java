package weblogic.logging;

import com.bea.logging.LogLevel;

public class WLLevel extends LogLevel {
   public static final WLLevel OFF = new WLLevel("Off", Integer.MAX_VALUE, 0);
   public static final WLLevel EMERGENCY = new WLLevel("Emergency", 1090, 1);
   public static final WLLevel CRITICAL = new WLLevel("Critical", 1030, 4);
   public static final WLLevel ERROR = new WLLevel("Error", 980, 8);
   public static final WLLevel ALERT = new WLLevel("Alert", 1060, 2);
   public static final WLLevel NOTICE = new WLLevel("Notice", 880, 32);
   public static final WLLevel DEBUG = new WLLevel("Debug", 495, 128);
   public static final WLLevel WARNING;
   public static final WLLevel INFO;
   public static final WLLevel TRACE;

   protected WLLevel(String name, int value, int severity) {
      super(name, value, severity);
   }

   static {
      WARNING = new WLLevel("Warning", WARNING_INT, 16);
      INFO = new WLLevel("Info", INFO_INT, 64);
      TRACE = new WLLevel("Trace", 295, 256);
   }
}
