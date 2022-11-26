package weblogic.logging.jms;

import com.bea.logging.DateFormatter;
import java.util.Date;
import java.util.logging.LogRecord;
import weblogic.logging.ConsoleFormatter;
import weblogic.management.configuration.LogFileMBean;
import weblogic.utils.PlatformConstants;

public final class JMSMessageLogFileFormatter extends ConsoleFormatter {
   public static final String BEGIN_MARKER = "####";
   private static final String UNKNOWN = "Unknown";

   public JMSMessageLogFileFormatter(LogFileMBean logFileMBean) {
      this.setDateFormatter(new DateFormatter(logFileMBean.getDateFormatPattern()));
   }

   public String format(LogRecord rec) {
      return this.formatJMSMessageLogRecord((JMSMessageLogRecord)rec);
   }

   private String formatJMSMessageLogRecord(JMSMessageLogRecord jmsMessageLogRecord) {
      long timestamp = jmsMessageLogRecord.getEventTimeMillisStamp();
      String date = this.formatDate(new Date(timestamp));
      StringBuilder buf = new StringBuilder("####");
      appendToBuffer(buf, date);
      appendToBuffer(buf, jmsMessageLogRecord.getTransactionId());
      appendToBuffer(buf, jmsMessageLogRecord.getDiagnosticContextId());
      appendToBuffer(buf, Long.toString(timestamp));
      appendToBuffer(buf, Long.toString(jmsMessageLogRecord.getEventTimeNanoStamp()));
      appendToBuffer(buf, jmsMessageLogRecord.getJMSMessageId());
      appendToBuffer(buf, jmsMessageLogRecord.getJMSCorrelationId());
      appendToBuffer(buf, jmsMessageLogRecord.getJMSDestinationName());
      appendToBuffer(buf, jmsMessageLogRecord.getJMSMessageState());
      appendToBuffer(buf, jmsMessageLogRecord.getUser());
      appendToBuffer(buf, jmsMessageLogRecord.getDurableSubscriber());
      appendToBufferEscaped(buf, jmsMessageLogRecord.getMessage());
      if (jmsMessageLogRecord instanceof JMSMessageConsumerCreationLogRecord) {
         appendToBufferEscaped(buf, ((JMSMessageConsumerCreationLogRecord)jmsMessageLogRecord).getSelector());
      } else {
         appendToBuffer(buf, (String)null);
      }

      buf.append(PlatformConstants.EOL);
      return buf.toString();
   }
}
