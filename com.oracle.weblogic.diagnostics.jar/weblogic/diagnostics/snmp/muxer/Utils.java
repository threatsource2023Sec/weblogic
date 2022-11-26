package weblogic.diagnostics.snmp.muxer;

import weblogic.diagnostics.debug.DebugLogger;

class Utils {
   static void debug(DebugLogger log, String className, String method, String msg) {
      log.debug(className + "." + method + "(): " + msg);
   }

   private static int byteArrayToInt(byte[] b, int offset, int versionFieldLen) {
      int value = 0;

      for(int i = 0; i < versionFieldLen; ++i) {
         int shift = (versionFieldLen - 1 - i) * 8;
         value += (b[i + offset] & 255) << shift;
      }

      return value;
   }
}
