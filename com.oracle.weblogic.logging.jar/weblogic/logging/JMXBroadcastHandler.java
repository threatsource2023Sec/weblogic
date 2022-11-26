package weblogic.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import weblogic.management.ManagementException;
import weblogic.management.logging.LogBroadcaster;

public class JMXBroadcastHandler extends Handler {
   private LogBroadcaster logBroadcaster = LogBroadcaster.getLogBroadcaster();
   boolean closed = false;

   public JMXBroadcastHandler() throws ManagementException {
   }

   public void publish(LogRecord rec) {
      if (!this.closed) {
         WLLogRecord logRecord = WLLogger.normalizeLogRecord(rec);
         this.logBroadcaster.translateLogEntry(logRecord);
      }

   }

   public void flush() {
   }

   public void close() {
      this.closed = true;
   }
}
