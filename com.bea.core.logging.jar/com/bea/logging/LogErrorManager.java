package com.bea.logging;

import java.util.logging.ErrorManager;
import java.util.logging.Handler;

public class LogErrorManager extends ErrorManager {
   private final Handler handler;
   private static final int EXCEPTION_TOLERANCE_LIMIT = 3;
   private int exceptionsEncountered = 0;
   private boolean exitImmediately = false;

   public LogErrorManager(Handler handler) {
      this.handler = handler;
   }

   public synchronized void error(String msg, Exception ex, int code) {
      if (!this.exitImmediately) {
         ++this.exceptionsEncountered;
         String error = null;
         if (this.exceptionsEncountered <= 3 && code != 4) {
            error = "Handler: '" + this.handler.toString() + "' raised exception" + codeToDesc(code) + ".";
         } else {
            this.exitImmediately = true;
            this.handler.close();
            error = "Handler: '" + this.handler.toString() + "' reported critical error(s). Shutting it down.";
         }

         System.err.println(error);
      }
   }

   private static String codeToDesc(int code) {
      switch (code) {
         case 1:
            return " when writing";
         case 2:
            return " when flushing";
         case 3:
            return " when closing";
         case 4:
            return " when opening";
         case 5:
            return " when formatting";
         default:
            return "";
      }
   }
}
