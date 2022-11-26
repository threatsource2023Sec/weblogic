package weblogic.logging;

import com.bea.logging.BaseLogEntry;
import com.bea.logging.LogMessageFormatter;
import java.util.logging.LogRecord;
import weblogic.management.configuration.LogFileMBean;

public final class LogFileFormatter extends LogMessageFormatter {
   public LogFileFormatter() {
      super(LOG_FILE_FIELDS);
   }

   public LogFileFormatter(LogFileMBean logFileMBean) {
      super(LOG_FILE_FIELDS);
      this.setDateFormatPattern(logFileMBean.getDateFormatPattern());
   }

   public String format(LogRecord rec) {
      Object logRecord;
      if (rec instanceof LogEntry) {
         logRecord = (LogEntry)rec;
      } else {
         logRecord = WLLogger.normalizeLogRecord(rec);
      }

      return this.formatBaseLogEntry((BaseLogEntry)logRecord);
   }
}
