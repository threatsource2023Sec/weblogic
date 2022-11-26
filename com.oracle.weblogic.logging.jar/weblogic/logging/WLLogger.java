package weblogic.logging;

import com.bea.logging.BaseLogger;
import java.util.ResourceBundle;
import java.util.WeakHashMap;
import java.util.logging.LogRecord;

public class WLLogger extends BaseLogger {
   private static final WeakHashMap NORMALIZED_CACHE = new WeakHashMap();
   private static final boolean DEBUG = false;

   public WLLogger(String name) {
      super(name);
   }

   public void log(LogRecord record) {
      if (!(record instanceof LogEntry)) {
         WLLogRecord wlLogRecord = normalizeLogRecord((LogRecord)record, false);
         LogEntryInitializer.initializeLogEntry(wlLogRecord);
         record = wlLogRecord;
      }

      super.log((LogRecord)record);
   }

   /** @deprecated */
   @Deprecated
   public static WLLogRecord normalizeLogRecord(LogRecord record) {
      return normalizeLogRecord(record, true);
   }

   public static WLLogRecord normalizeLogRecord(LogRecord lr, boolean cacheRecord) {
      if (lr instanceof WLLogRecord) {
         return (WLLogRecord)lr;
      } else {
         WLLogRecord rec = null;
         synchronized(NORMALIZED_CACHE) {
            rec = (WLLogRecord)NORMALIZED_CACHE.get(lr);
         }

         if (rec != null) {
            return rec;
         } else {
            rec = new WLLogRecord(lr.getLevel(), formatMessage(lr), lr.getThrown());
            String msg = lr.getMessage();
            if (msg != null && !msg.isEmpty()) {
               ResourceBundle rb = lr.getResourceBundle();
               if (rb != null && rb.containsKey(msg)) {
                  String msgBody = lr.getResourceBundle().getString(msg);
                  if (msgBody != null && !msgBody.isEmpty()) {
                     rec.setId(msg);
                  }
               }
            }

            rec.setLoggerName(lr.getLoggerName() == null ? "Default" : lr.getLoggerName());
            rec.setMillis(lr.getMillis());
            rec.setParameters(lr.getParameters());
            rec.setResourceBundle(lr.getResourceBundle());
            rec.setResourceBundleName(lr.getResourceBundleName());
            rec.setSequenceNumber(lr.getSequenceNumber());
            rec.setSourceClassName(lr.getSourceClassName());
            rec.setSourceMethodName(lr.getSourceMethodName());
            rec.setThreadID(lr.getThreadID());
            rec.setThrown(lr.getThrown());
            if (cacheRecord) {
               synchronized(NORMALIZED_CACHE) {
                  NORMALIZED_CACHE.put(lr, rec);
               }
            }

            return rec;
         }
      }
   }
}
