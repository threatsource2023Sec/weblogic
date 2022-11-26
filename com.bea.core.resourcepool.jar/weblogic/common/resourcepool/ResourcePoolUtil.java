package weblogic.common.resourcepool;

import java.io.PrintWriter;

class ResourcePoolUtil {
   static final boolean debug = false;
   static final int DBG_LVL_TRACE = 0;
   static final int DBG_LVL_HIGH = 1;
   static final int debugLevel = 0;

   static final boolean doLog() {
      return doLog(0);
   }

   static final boolean doLog(int dbgLvl) {
      return false;
   }

   static final void log(String msg) {
      CommonLogger.logDebugMsg(msg);
   }

   static final void log(PrintWriter pw, String msg) {
      if (pw != null) {
         pw.println(msg);
      } else {
         CommonLogger.logDebugMsg(msg);
      }

   }
}
