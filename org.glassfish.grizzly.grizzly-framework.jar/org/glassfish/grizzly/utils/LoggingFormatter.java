package org.glassfish.grizzly.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggingFormatter extends Formatter {
   private static final Logger log = Logger.getLogger(LoggingFormatter.class.getName());
   private static String lineSeparator = "\n";

   public String format(LogRecord record) {
      StringBuffer sb = new StringBuffer(128);
      sb.append('[').append(Thread.currentThread().getName()).append("] ");
      Date date = new Date(record.getMillis());
      sb.append(date.toString()).append(' ');
      sb.append('[').append(record.getLevel().getLocalizedName()).append("] ");
      if (record.getSourceClassName() != null) {
         sb.append(record.getSourceClassName());
      } else {
         sb.append(record.getLoggerName());
      }

      if (record.getSourceMethodName() != null) {
         sb.append(' ');
         sb.append(record.getSourceMethodName());
      }

      sb.append(':').append(lineSeparator);
      sb.append(this.formatMessage(record)).append(lineSeparator);
      if (record.getThrown() != null) {
         try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            pw.close();
            sb.append(sw.toString());
         } catch (Exception var6) {
         }
      }

      sb.append(lineSeparator);
      return sb.toString();
   }

   public static void main(String[] args) {
      log.info("Info Event");
      log.severe("Severe Event");
      Thread t = new Thread(new Runnable() {
         public void run() {
            LoggingFormatter.log.info("Info Event in Thread");
         }
      }, "Thread into main");
      t.start();
      log.log(Level.SEVERE, "exception", new Exception());
   }

   static {
      try {
         String separator = System.getProperty("line.separator");
         if (separator != null && separator.trim().length() > 0) {
            lineSeparator = separator;
         }
      } catch (SecurityException var1) {
      }

   }
}
