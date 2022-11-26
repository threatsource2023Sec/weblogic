package weblogic.nodemanager.server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
   private MessageFormat mf;
   private final String lineSeparator;
   public static final String GRIZZLY_ROOT_LOGGER = "org.glassfish.grizzly";

   public LogFormatter(String lineSeparator) {
      this.lineSeparator = lineSeparator;
   }

   public LogFormatter() {
      this(System.getProperty("line.separator"));
   }

   public synchronized String format(LogRecord rec) {
      Date date = new Date();
      Object[] args = new Object[2];
      StringBuffer sb = new StringBuffer();
      date.setTime(rec.getMillis());
      args[0] = date;
      args[1] = rec.getLevel().getName();
      if (this.mf == null) {
         this.mf = new MessageFormat("<{0,date,medium} {0,time,full}> <{1}>");
      }

      this.mf.format(args, sb, (FieldPosition)null);
      Object[] params = rec.getParameters();
      if (params != null && (rec.getLoggerName() == null || !rec.getLoggerName().startsWith("org.glassfish.grizzly"))) {
         for(int i = 0; i < params.length; ++i) {
            sb.append(" <");
            sb.append(params[i].toString());
            sb.append(">");
         }
      }

      sb.append(" <");
      sb.append(this.formatMessage(rec));
      sb.append(">");
      sb.append(this.lineSeparator);
      if (rec.getThrown() != null) {
         appendThrowable(sb, rec.getThrown());
         sb.append(this.lineSeparator);
      }

      return sb.toString();
   }

   public String format(Level level, String msg, Throwable thrown) {
      LogRecord lr = new LogRecord(level, msg);
      if (thrown != null) {
         lr.setThrown(thrown);
      }

      return this.format(lr);
   }

   public String format(Level level, String msg) {
      return this.format(level, msg, (Throwable)null);
   }

   private static void appendThrowable(StringBuffer sb, Throwable t) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      t.printStackTrace(pw);
      pw.close();
      sb.append(sw.toString());
   }
}
