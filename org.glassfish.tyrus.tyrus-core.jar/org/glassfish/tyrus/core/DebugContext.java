package org.glassfish.tyrus.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DebugContext {
   private List logRecords = new ArrayList();
   private Map tracingHeaders = new HashMap();
   private final long startTimestamp = System.nanoTime();
   private final Level tracingLevel;
   private String sessionId = null;

   public DebugContext(TracingThreshold tracingThreshold) {
      if (DebugContext.TracingThreshold.SUMMARY == tracingThreshold) {
         this.tracingLevel = Level.FINE;
      } else {
         this.tracingLevel = Level.FINER;
      }

   }

   public DebugContext() {
      this.tracingLevel = Level.OFF;
   }

   public void appendLogMessage(Logger logger, Level loggingLevel, Type type, Object... messageParts) {
      this.appendLogMessageWithThrowable(logger, loggingLevel, type, (Throwable)null, messageParts);
   }

   public void appendTraceMessage(Logger logger, Level loggingLevel, Type type, Object... messageParts) {
      this.appendTraceMessageWithThrowable(logger, loggingLevel, type, (Throwable)null, messageParts);
   }

   public void appendLogMessageWithThrowable(Logger logger, Level loggingLevel, Type type, Throwable t, Object... messageParts) {
      if (logger.isLoggable(loggingLevel)) {
         String message = this.stringifyMessageParts(messageParts);
         if (this.sessionId == null) {
            this.logRecords.add(new LogRecord(logger, loggingLevel, type, message, t, false));
         } else if (t != null) {
            logger.log(loggingLevel, this.formatLogMessage(message, type, System.nanoTime()), t);
         } else {
            logger.log(loggingLevel, this.formatLogMessage(message, type, System.nanoTime()));
         }
      }

   }

   public void appendTraceMessageWithThrowable(Logger logger, Level loggingLevel, Type type, Throwable t, Object... messageParts) {
      if (this.tracingLevel.intValue() <= loggingLevel.intValue()) {
         String message = this.stringifyMessageParts(messageParts);
         this.appendTracingHeader(message);
      }

      this.appendLogMessageWithThrowable(logger, loggingLevel, type, t, messageParts);
   }

   public void appendStandardOutputMessage(Type type, String message) {
      if (this.sessionId == null) {
         this.logRecords.add(new LogRecord((Logger)null, Level.OFF, type, message, (Throwable)null, true));
      } else {
         System.out.println(this.formatLogMessage(message, type, System.nanoTime()));
      }

   }

   public void setSessionId(String sessionId) {
      this.sessionId = sessionId;
      this.flush();
   }

   public void flush() {
      if (this.sessionId == null) {
         this.sessionId = UUID.randomUUID().toString();
      }

      Iterator var1 = this.logRecords.iterator();

      while(var1.hasNext()) {
         LogRecord logRecord = (LogRecord)var1.next();
         if (logRecord.printToSout) {
            System.out.println(this.formatLogMessage(logRecord.message, logRecord.type, logRecord.timestamp));
         } else if (logRecord.logger.isLoggable(logRecord.loggingLevel)) {
            if (logRecord.t != null) {
               logRecord.logger.log(logRecord.loggingLevel, this.formatLogMessage(logRecord.message, logRecord.type, logRecord.timestamp), logRecord.t);
            } else {
               logRecord.logger.log(logRecord.loggingLevel, this.formatLogMessage(logRecord.message, logRecord.type, logRecord.timestamp));
            }
         }
      }

      this.logRecords.clear();
   }

   public Map getTracingHeaders() {
      return this.tracingHeaders;
   }

   private void appendTracingHeader(String message) {
      String headerName = "X-Tyrus-Tracing-" + String.format("%02d", this.tracingHeaders.size());
      this.tracingHeaders.put(headerName, Arrays.asList("[" + (System.nanoTime() - this.startTimestamp) / 1000000L + " ms] " + message));
   }

   private String formatLogMessage(String message, Type type, long timestamp) {
      StringBuilder formattedMessage = new StringBuilder();
      List messageLines = new ArrayList();
      StringTokenizer tokenizer = new StringTokenizer(message, "\n", true);

      while(tokenizer.hasMoreTokens()) {
         messageLines.add(tokenizer.nextToken());
      }

      String prefix;
      if (type == DebugContext.Type.MESSAGE_IN) {
         prefix = "< ";
      } else if (type == DebugContext.Type.MESSAGE_OUT) {
         prefix = "> ";
      } else {
         prefix = "* ";
      }

      boolean isFirst = true;
      Iterator var10 = messageLines.iterator();

      while(var10.hasNext()) {
         String line = (String)var10.next();
         if (isFirst) {
            formattedMessage.append(prefix).append("Session ").append(this.sessionId).append(" ");
            formattedMessage.append("[").append((timestamp - this.startTimestamp) / 1000000L).append(" ms]: ");
            formattedMessage.append(line);
            isFirst = false;
         } else {
            if (!"\n".equals(line)) {
               formattedMessage.append(prefix);
            }

            formattedMessage.append(line);
         }
      }

      return formattedMessage.toString();
   }

   private String stringifyMessageParts(Object... messageParts) {
      StringBuilder sb = new StringBuilder();
      Object[] var3 = messageParts;
      int var4 = messageParts.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object messagePart = var3[var5];
         sb.append(messagePart);
      }

      return sb.toString();
   }

   public static enum TracingThreshold {
      SUMMARY,
      TRACE;
   }

   public static enum TracingType {
      OFF,
      ON_DEMAND,
      ALL;
   }

   public static enum Type {
      MESSAGE_IN,
      MESSAGE_OUT,
      OTHER;
   }

   private static class LogRecord {
      private Logger logger;
      private Level loggingLevel;
      private Type type;
      private String message;
      private Throwable t;
      private boolean printToSout;
      private long timestamp;

      LogRecord(Logger logger, Level loggingLevel, Type Type, String message, Throwable t, boolean printToSout) {
         this.logger = logger;
         this.loggingLevel = loggingLevel;
         this.type = Type;
         this.message = message;
         this.t = t;
         this.printToSout = printToSout;
         this.timestamp = System.nanoTime();
      }
   }
}
