package weblogic.logging;

import com.bea.logging.DateFormatter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.regex.Pattern;
import weblogic.management.configuration.CommonLogMBean;
import weblogic.management.configuration.KernelMBean;
import weblogic.management.configuration.LogMBean;
import weblogic.utils.PlatformConstants;
import weblogic.utils.PropertyHelper;

public class ConsoleFormatter extends Formatter implements LogEntryFormatter {
   public static final String FIELD_PREFIX = "<";
   public static final String FIELD_SUFFIX = "> ";
   private static Pattern FIELD_PREFIX_PATTERN = Pattern.compile("<");
   private static Pattern FIELD_SUFFIX_PATTERN = Pattern.compile(">");
   private static final boolean LOG_ANALYZER = PropertyHelper.getBoolean("logAnalyzer");
   private CommonLogMBean logMBean;
   private DateFormatter dateFormatter;

   public static String formatForConsole(CommonLogMBean logConfig, LogEntry logEntry, DateFormatter df) {
      StringBuilder buf = new StringBuilder();
      long timestamp = logEntry.getTimestamp();
      if (LOG_ANALYZER) {
         appendToBuffer(buf, Long.toString(timestamp));
      }

      logEntry.ensureFormattedDateInitialized(df);
      String date = logEntry.getFormattedDate();
      appendToBuffer(buf, date);
      appendToBuffer(buf, SeverityI18N.severityNumToString(logEntry.getSeverity()));
      appendToBuffer(buf, logEntry.getSubsystem());
      if (logConfig == null || logConfig.getStdoutFormat().equals("standard")) {
         appendToBuffer(buf, logEntry.getId());
      }

      buf.append("<");
      buf.append(logEntry.getLogMessage());
      if ((logConfig == null || logConfig.isStdoutLogStack()) && logEntry.getThrowableWrapper() != null) {
         buf.append(PlatformConstants.EOL);
         if (logConfig != null && Severities.severityStringToNum(logConfig.getStdoutSeverity()) < 64) {
            buf.append(logEntry.getThrowableWrapper().toString(logConfig.getStacktraceDepth()));
         } else {
            buf.append(logEntry.getThrowableWrapper());
         }
      }

      buf.append("> ");
      buf.append(PlatformConstants.EOL);
      return buf.toString();
   }

   public ConsoleFormatter() {
      this.dateFormatter = DateFormatter.getDefaultInstance();
   }

   /** @deprecated */
   @Deprecated
   public ConsoleFormatter(KernelMBean kernelMBean) {
      this(kernelMBean.getLog());
   }

   /** @deprecated */
   @Deprecated
   public ConsoleFormatter(LogMBean logMBean) {
      this((CommonLogMBean)logMBean);
   }

   public ConsoleFormatter(CommonLogMBean logMBean) {
      this.dateFormatter = DateFormatter.getDefaultInstance();
      this.logMBean = logMBean;
      this.dateFormatter = new DateFormatter(logMBean.getDateFormatPattern());
   }

   public DateFormatter getDateFormatter() {
      return this.dateFormatter;
   }

   public void setDateFormatter(DateFormatter df) {
      this.dateFormatter = df;
   }

   public String format(LogRecord rec) {
      WLLogRecord logRecord = WLLogger.normalizeLogRecord(rec);
      return this.toString(logRecord);
   }

   public String toString(LogEntry logEntry) {
      return formatForConsole(this.logMBean, logEntry, this.dateFormatter);
   }

   public static final String formatDateObject(Date date) {
      return DateFormatter.getDefaultInstance().formatDate(date);
   }

   /** @deprecated */
   @Deprecated
   protected static final void appendToBufferEscaped(StringBuffer buf, String str) {
      if (str != null) {
         str = FIELD_PREFIX_PATTERN.matcher(str).replaceAll("&lt;");
         str = FIELD_SUFFIX_PATTERN.matcher(str).replaceAll("&gt;");
      } else {
         str = "";
      }

      appendToBuffer(buf, str);
   }

   /** @deprecated */
   @Deprecated
   protected static final void appendToBuffer(StringBuffer buf, String str) {
      buf.append("<");
      buf.append(str != null ? str : "");
      buf.append("> ");
   }

   /** @deprecated */
   @Deprecated
   protected String formatDate(Date date) {
      return this.dateFormatter.formatDate(date);
   }

   /** @deprecated */
   @Deprecated
   protected void appendBuf(StringBuffer buf, String str) {
      appendToBuffer(buf, str);
   }

   protected static final void appendToBuffer(StringBuilder buf, String str) {
      buf.append("<");
      buf.append(str != null ? str : "");
      buf.append("> ");
   }

   protected static final void appendToBufferEscaped(StringBuilder buf, String str) {
      if (str != null) {
         str = FIELD_PREFIX_PATTERN.matcher(str).replaceAll("&lt;");
         str = FIELD_SUFFIX_PATTERN.matcher(str).replaceAll("&gt;");
      } else {
         str = "";
      }

      appendToBuffer(buf, str);
   }
}
