package com.bea.adaptive.mbean.typing;

import weblogic.diagnostics.debug.DebugLogger;

class DebugHelper {
   static MBeanTypingUtilTextTextFormatter mtf = MBeanTypingUtilTextTextFormatter.getInstance();
   String flagName;
   private static DebugLogger debugLogger;
   static final String STDOUT_PREFIX = "com.bea.core.debug.";
   private static String DBG_MODULE_LABEL;

   DebugHelper(String baseFlag) {
      this(baseFlag, (String)null);
   }

   DebugHelper(String baseFlag, String stdoutPrefix) {
      this.flagName = "MBeanTypingUtility";
      this.flagName = baseFlag;
      initDebugLogger(this.flagName);
      if (this.dbg()) {
         String s = "The following debug flags are set\n";
         s = s + "  log: " + (debugLogger != null && debugLogger.isDebugEnabled()) + "\n";
         this.dbg(s);
      }

   }

   private static void initDebugLogger(String flagName) {
      debugLogger = DebugLogger.getDebugLogger(flagName);
   }

   final boolean isDebugEnabled() {
      return debugLogger.isDebugEnabled();
   }

   /** @deprecated */
   @Deprecated
   final boolean dbg() {
      return this.isDebugEnabled();
   }

   final void debug(Object o) {
      if (this.isDebugEnabled()) {
         debugLogger.debug(msgLabel() + o);
      }
   }

   /** @deprecated */
   @Deprecated
   void dbg(Object o) {
      this.debug(o);
   }

   private static String msgLabel() {
      return "[" + DBG_MODULE_LABEL + ":  - " + Thread.currentThread().getId() + "] ";
   }

   static {
      DBG_MODULE_LABEL = mtf.getDebugModuleLabel();
   }
}
