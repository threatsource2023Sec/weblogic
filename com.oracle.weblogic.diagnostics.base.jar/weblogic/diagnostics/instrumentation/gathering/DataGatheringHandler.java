package weblogic.diagnostics.instrumentation.gathering;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import weblogic.logging.WLErrorManager;
import weblogic.logging.WLLevel;

public class DataGatheringHandler extends Handler {
   private boolean closed = false;

   public DataGatheringHandler(int severity) {
      this.setErrorManager(new WLErrorManager(this));
      this.setLevel(WLLevel.getLevel(severity));
   }

   public void publish(LogRecord rec) {
      if (this.isLoggable(rec)) {
         if (!this.closed) {
            DataGatheringManager.gatherLogRecord(rec);
         }

      }
   }

   public void flush() {
   }

   public void close() {
      this.closed = true;
   }
}
