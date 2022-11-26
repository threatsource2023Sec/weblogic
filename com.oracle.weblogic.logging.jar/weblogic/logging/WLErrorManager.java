package weblogic.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;

public class WLErrorManager extends ErrorManager {
   static final int ERR_COUNT_INTERVAL_MILLIS = Integer.getInteger("weblogic.log.ErrorCountIntervalMillis", 60000);
   static final int EXCEPTION_TOLERANCE_LIMIT = Integer.getInteger("weblogic.log.ExceptionToleranceLimit", 3);
   private final Handler handler;
   private List exceptionsEncountered = new ArrayList();
   private boolean exitImmediately = false;

   public WLErrorManager(Handler handler) {
      this.handler = handler;
   }

   public synchronized void error(String msg, Exception ex, int code) {
      if (!this.exitImmediately) {
         String handlerName = this.handler.getClass().getSimpleName();
         String error = LogMgmtTextTextFormatter.getInstance().getHandlerErrorMsg(handlerName, codeToDesc(code));
         System.err.println(error);
         ex.printStackTrace();
         this.exceptionsEncountered.add(System.currentTimeMillis());
         if (this.isMaxErrorCountReached() || code == 4) {
            this.exitImmediately = true;
            this.handler.close();
            System.err.println(LogMgmtTextTextFormatter.getInstance().getHandlerClosingMsg(handlerName));
         }

      }
   }

   private boolean isMaxErrorCountReached() {
      int size = this.exceptionsEncountered.size();
      if (size > EXCEPTION_TOLERANCE_LIMIT) {
         long first = (Long)this.exceptionsEncountered.get(0);
         long last = (Long)this.exceptionsEncountered.get(size - 1);
         long interval = last - first;
         if (interval < (long)ERR_COUNT_INTERVAL_MILLIS) {
            return true;
         }

         this.exceptionsEncountered.remove(0);
      }

      return false;
   }

   private static String codeToDesc(int code) {
      switch (code) {
         case 1:
            return "write()";
         case 2:
            return "flush()";
         case 3:
            return "close()";
         case 4:
            return "open()";
         case 5:
            return "format()";
         default:
            return "log()";
      }
   }
}
