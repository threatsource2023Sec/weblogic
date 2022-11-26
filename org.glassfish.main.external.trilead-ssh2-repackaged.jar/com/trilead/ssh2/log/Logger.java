package com.trilead.ssh2.log;

import com.trilead.ssh2.DebugLogger;

public class Logger {
   public static boolean enabled = false;
   public static DebugLogger logger = null;
   private String className;

   public static final Logger getLogger(Class x) {
      return new Logger(x);
   }

   public Logger(Class x) {
      this.className = x.getName();
   }

   public final boolean isEnabled() {
      return enabled;
   }

   public final void log(int level, String message) {
      if (enabled) {
         DebugLogger target = logger;
         if (target != null) {
            target.log(level, this.className, message);
         }
      }
   }
}
