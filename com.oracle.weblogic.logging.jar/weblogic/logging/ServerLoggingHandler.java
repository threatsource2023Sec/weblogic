package weblogic.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import weblogic.diagnostics.debug.DebugLogger;

public class ServerLoggingHandler extends Handler {
   private boolean closed = false;

   public ServerLoggingHandler() {
      Handler[] handlers = JDKLoggerFactory.getBridgeLogger().getHandlers();
      if (handlers != null) {
         Handler[] var2 = handlers;
         int var3 = handlers.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Handler h = var2[var4];
            if (h.getClass().getName().equals(ServerLoggingHandler.class.getName())) {
               this.closed = true;
            }
         }
      }

   }

   public void close() throws SecurityException {
      this.closed = true;
   }

   public void flush() {
   }

   public void publish(LogRecord logRecord) {
      if (!this.closed && this.isLoggable(logRecord)) {
         String loggerName = logRecord.getLoggerName();
         if (loggerName == null) {
            loggerName = "";
         }

         Logger logger = DebugLogger.getDefaultDebugLoggerRepository().getLogger();
         logger.log(logRecord);
      }

   }
}
