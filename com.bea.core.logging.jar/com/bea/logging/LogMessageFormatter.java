package com.bea.logging;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import weblogic.i18n.logging.Severities;
import weblogic.utils.PlatformConstants;
import weblogic.utils.PropertyHelper;

public class LogMessageFormatter extends Formatter {
   private static final String FIELD_PREFIX = "<";
   private static final String FIELD_SUFFIX = "> ";
   private static final String BEGIN_MARKER = "####";
   protected static final String ODL_FIELD_PREFIX = "[";
   protected static final String ODL_FIELD_SUFFIX = "] ";
   protected static final String EMPTY_SUPP_ATTRS = "[]";
   public static final int RECORD_DELIMITER = 0;
   public static final int FORMATTED_DATE = 1;
   public static final int SEVERITY = 2;
   public static final int SUBSYSTEM = 3;
   public static final int MACHINE_NAME = 4;
   public static final int SERVER_NAME = 5;
   public static final int THREAD_NAME = 6;
   public static final int USER_ID = 7;
   public static final int TRANSACTION_ID = 8;
   public static final int DIAG_CTX_ID = 9;
   public static final int TIMESTAMP = 10;
   public static final int SUPPLEMENTAL_ATTRS = 11;
   public static final int MESSAGE_ID = 12;
   public static final int MESSAGE = 13;
   static final int[] STDOUT_FIELDS = new int[]{1, 2, 3, 12, 13};
   public static final int[] LOG_FILE_FIELDS = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
   private DateFormatter dateFormatter;
   private boolean msgIdEnabled;
   private boolean stackTraceEnabled;
   private int stackTraceDepth;
   private int[] fields;
   private static boolean logFormatCompatibilityEnabled = PropertyHelper.getBoolean("weblogic.LogFormatCompatibilityEnabled");

   public LogMessageFormatter() {
      this(STDOUT_FIELDS);
   }

   public LogMessageFormatter(int[] fieldFormat) {
      this.dateFormatter = DateFormatter.getDefaultInstance();
      this.msgIdEnabled = true;
      this.stackTraceEnabled = true;
      this.stackTraceDepth = -1;
      this.fields = STDOUT_FIELDS;
      this.fields = fieldFormat;
   }

   public void setMessageIdEnabled(boolean value) {
      this.msgIdEnabled = value;
   }

   public void setDateFormatPattern(String pattern) {
      this.dateFormatter = new DateFormatter(pattern);
   }

   public void setStackTraceEnabled(boolean traceEnabled) {
      this.stackTraceEnabled = traceEnabled;
   }

   public void setStackTraceDepth(int depth) {
      this.stackTraceDepth = depth;
   }

   public String format(LogRecord rec) {
      Object baseLogEntry;
      if (rec instanceof BaseLogEntry) {
         baseLogEntry = (BaseLogEntry)rec;
      } else {
         baseLogEntry = new BaseLogRecord(rec);
      }

      return this.formatBaseLogEntry((BaseLogEntry)baseLogEntry);
   }

   public String formatBaseLogEntry(BaseLogEntry baseLogEntry) {
      String msgId = baseLogEntry.getId();
      ThrowableWrapper thInfo = baseLogEntry.getThrowableWrapper();
      int severity = baseLogEntry.getSeverity();
      String machine = baseLogEntry.getMachineName();
      String server = baseLogEntry.getServerName();
      String threadId = baseLogEntry.getThreadName();
      String userId = baseLogEntry.getUserId();
      String txId = baseLogEntry.getTransactionId();
      String diagCtxId = baseLogEntry.getDiagnosticContextId();
      String suppAttrs = renderSupplementalAttributes(baseLogEntry.getSupplementalAttributes());
      StringBuilder buf = new StringBuilder();
      long timestamp = baseLogEntry.getTimestamp();

      for(int i = 0; i < this.fields.length; ++i) {
         switch (this.fields[i]) {
            case 0:
               buf.append("####");
               break;
            case 1:
               String date = this.dateFormatter.formatDate(new Date(timestamp));
               this.appendToBuffer(buf, date);
               break;
            case 2:
               this.appendToBuffer(buf, Severities.severityNumToString(severity));
               break;
            case 3:
               this.appendToBuffer(buf, baseLogEntry.getSubsystem());
               break;
            case 4:
               this.appendToBuffer(buf, machine);
               break;
            case 5:
               this.appendToBuffer(buf, server);
               break;
            case 6:
               this.appendToBuffer(buf, threadId);
               break;
            case 7:
               this.appendToBuffer(buf, userId);
               break;
            case 8:
               this.appendToBuffer(buf, txId);
               break;
            case 9:
               this.appendToBuffer(buf, diagCtxId);
               break;
            case 10:
               this.appendToBuffer(buf, Long.toString(timestamp));
               break;
            case 11:
               if (!logFormatCompatibilityEnabled) {
                  this.appendToBuffer(buf, suppAttrs);
               }
               break;
            case 12:
               if (this.msgIdEnabled) {
                  this.appendToBuffer(buf, msgId);
               }
               break;
            case 13:
               StringBuilder temp = new StringBuilder();
               temp.append(baseLogEntry.getLogMessage());
               if (this.stackTraceEnabled && thInfo != null) {
                  temp.append(PlatformConstants.EOL);
                  if (severity >= 64) {
                     temp.append(thInfo);
                  } else {
                     temp.append(thInfo.toString(this.stackTraceDepth));
                  }
               }

               this.appendToBuffer(buf, temp.toString());
         }
      }

      buf.append(PlatformConstants.EOL);
      return buf.toString();
   }

   protected void appendToBuffer(StringBuilder buf, String str) {
      buf.append("<");
      buf.append(str != null ? str : "");
      buf.append("> ");
   }

   public static String renderSupplementalAttributes(Properties p) {
      int size = p.size();
      if (size == 0) {
         return "[]";
      } else {
         StringBuilder sb = new StringBuilder();
         Set entrySet = p.entrySet();
         Iterator var4 = entrySet.iterator();

         while(var4.hasNext()) {
            Map.Entry e = (Map.Entry)var4.next();
            String key = e.getKey() != null ? e.getKey().toString() : "";
            String value = e.getValue() != null ? e.getValue().toString() : "";
            if (value != null && !value.isEmpty()) {
               sb.append("[");
               sb.append(key.toString());
               sb.append(": ");
               sb.append(value.toString());
               sb.append("] ");
            }
         }

         return sb.toString();
      }
   }

   public int getStackTraceDepth() {
      return this.stackTraceDepth;
   }

   public boolean isStackTraceEnabled() {
      return this.stackTraceEnabled;
   }

   public static boolean isLogFormatCompatibilityEnabled() {
      return logFormatCompatibilityEnabled;
   }

   public static void setLogFormatCompatibilityEnabled(boolean value) {
      logFormatCompatibilityEnabled = value;
   }
}
