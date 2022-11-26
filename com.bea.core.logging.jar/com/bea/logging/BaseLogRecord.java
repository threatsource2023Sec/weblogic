package com.bea.logging;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.i18n.logging.LogMessage;
import weblogic.i18n.logging.Severities;

public class BaseLogRecord extends LogRecord implements BaseLogEntry {
   private static final long serialVersionUID = -4414913723445251798L;
   private ThrowableWrapper throwableWrapper;
   private int severity;
   private String id;
   private String subSystem;
   private String threadId;
   private String userId;
   private String transactionId;
   private String serverName;
   private String diagnosticContextId;
   private String machineName;
   private Properties supplementalAttrs;
   private transient String formattedDate;
   private transient String diagnosticVolume;
   private transient boolean gatherable;
   private String severityString;
   private boolean excludePartition;

   private BaseLogRecord(Level level, String msg) {
      super(level, msg);
      this.severity = 64;
      this.id = MsgIdPrefixConverter.getDefaultMsgId();
      this.subSystem = "Default";
      this.threadId = "";
      this.userId = "";
      this.transactionId = "";
      this.serverName = "";
      this.diagnosticContextId = "";
      this.machineName = "";
      this.supplementalAttrs = new Properties();
      this.gatherable = false;
      this.threadId = Thread.currentThread().getName();
      String loggerName = this.getLoggerName();
      if (loggerName != null) {
         this.subSystem = loggerName;
      }

   }

   public BaseLogRecord(int severityLevel, String msg) {
      this(LogLevel.getLevel(severityLevel), msg);
      this.setSeverity(severityLevel);
   }

   public BaseLogRecord(int severity, String msg, Throwable throwable) {
      this(severity, msg);
      this.setThrown(throwable);
   }

   public BaseLogRecord(LogMessage logMessage) {
      this(logMessage.getSeverity(), logMessage.getMessage());
      this.setSeverity(logMessage.getSeverity());
      String msgIdPrefix = logMessage.getMessageIdPrefix();
      if (msgIdPrefix != null && msgIdPrefix.length() > 0) {
         String msgId = logMessage.getMessageId();
         if (msgId != null && msgId.length() > 0) {
            msgIdPrefix = MsgIdPrefixConverter.convertMsgIdPrefix(msgIdPrefix);
            this.id = msgIdPrefix + "-" + msgId;
         }
      } else {
         this.setId(logMessage.getMessageId());
      }

      boolean stackTraceEnabled = true;
      if (logMessage instanceof CatalogMessage) {
         CatalogMessage catMsg = (CatalogMessage)logMessage;
         this.setParameters(catMsg.getArguments());
         stackTraceEnabled = catMsg.isStackTraceEnabled();
         this.setDiagnosticVolume(catMsg.getDiagnosticVolume());
         this.setExcludePartition(catMsg.isExcludePartition());
      }

      Properties msgSuppAttrs = logMessage.getSupplementalAttributes();
      if (msgSuppAttrs != null && !msgSuppAttrs.isEmpty()) {
         Set entries = msgSuppAttrs.entrySet();
         Iterator var6 = entries.iterator();

         while(var6.hasNext()) {
            Map.Entry entry = (Map.Entry)var6.next();
            this.supplementalAttrs.put(entry.getKey(), entry.getValue());
         }
      }

      this.subSystem = logMessage.getSubsystem();
      this.setLoggerName(this.subSystem);
      Throwable th = logMessage.getThrowable();
      if (stackTraceEnabled) {
         this.setThrown(th);
      }

   }

   public BaseLogRecord(LogRecord rec) {
      super(rec.getLevel(), rec.getMessage());
      this.severity = 64;
      this.id = MsgIdPrefixConverter.getDefaultMsgId();
      this.subSystem = "Default";
      this.threadId = "";
      this.userId = "";
      this.transactionId = "";
      this.serverName = "";
      this.diagnosticContextId = "";
      this.machineName = "";
      this.supplementalAttrs = new Properties();
      this.gatherable = false;
      this.setLoggerName(rec.getLoggerName());
      this.setMillis(rec.getMillis());
      if (rec instanceof BaseLogRecord) {
         BaseLogRecord src = (BaseLogRecord)rec;
         this.throwableWrapper = src.throwableWrapper;
         this.setSeverity(src.severity);
         this.id = src.id;
         this.subSystem = src.subSystem;
         this.threadId = src.threadId;
         this.userId = src.userId;
         this.transactionId = src.transactionId;
         this.serverName = src.serverName;
         this.diagnosticContextId = src.diagnosticContextId;
         this.machineName = src.machineName;
         this.formattedDate = src.formattedDate;
         this.diagnosticVolume = src.diagnosticVolume;
         this.gatherable = src.gatherable;
      } else {
         this.severity = LogLevel.getSeverity(rec.getLevel());
         Throwable th = rec.getThrown();
         if (th != null) {
            this.throwableWrapper = new ThrowableWrapper(th);
         }

         this.threadId = "ThreadId:" + rec.getThreadID();
      }

   }

   public long getTimestamp() {
      return this.getMillis();
   }

   public String getLogMessage() {
      return this.getMessage();
   }

   public void ensureFormattedDateInitialized(DateFormatter df) {
      if (this.formattedDate == null) {
         this.formattedDate = df.formatDate(new Date(this.getTimestamp()));
      }

   }

   public String getFormattedDate() {
      this.ensureFormattedDateInitialized(DateFormatter.getDefaultInstance());
      return this.formattedDate;
   }

   public void setThrown(Throwable th) {
      if (th != null) {
         this.throwableWrapper = new ThrowableWrapper(th);
      } else {
         this.throwableWrapper = null;
      }

   }

   public Throwable getThrown() {
      return this.throwableWrapper != null ? this.throwableWrapper.getThrowable() : null;
   }

   public int getSeverity() {
      return this.severity;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String messageId) {
      if (messageId == null || messageId.length() == 0) {
         messageId = MsgIdPrefixConverter.getDefaultMsgId();
      }

      this.id = messageId;
   }

   public String getSubsystem() {
      return this.subSystem;
   }

   public ThrowableWrapper getThrowableWrapper() {
      return this.throwableWrapper;
   }

   public void setThrowableWrapper(ThrowableWrapper throwableInfo) {
      this.throwableWrapper = throwableInfo;
   }

   public void setThreadName(String tid) {
      if (tid != null) {
         this.threadId = tid;
      }

   }

   public String getThreadName() {
      return this.threadId;
   }

   public void setUserId(String uid) {
      if (uid != null) {
         this.userId = uid;
      }

   }

   public String getUserId() {
      return this.userId;
   }

   public void setTransactionId(String txid) {
      if (txid != null) {
         this.transactionId = txid;
      }

   }

   public String getTransactionId() {
      return this.transactionId;
   }

   public String getServerName() {
      return this.serverName;
   }

   public void setServerName(String name) {
      if (name != null) {
         this.serverName = name;
      }

   }

   public String getDiagnosticContextId() {
      return this.diagnosticContextId;
   }

   public void setDiagnosticContextId(String id) {
      if (id != null) {
         this.diagnosticContextId = id;
      }

   }

   public String getMachineName() {
      return this.machineName;
   }

   public void setMachineName(String machineName) {
      if (machineName != null) {
         this.machineName = machineName;
      }

   }

   public void setLoggerName(String loggerName) {
      if (loggerName != null) {
         super.setLoggerName(loggerName);
         this.subSystem = loggerName;
      }

   }

   public void setLevel(Level level) {
      super.setLevel(level);
      this.setSeverity(LogLevel.getSeverity(level));
   }

   public void setDiagnosticVolume(String diagnosticVolume) {
      this.diagnosticVolume = diagnosticVolume;
      if (diagnosticVolume != null && !diagnosticVolume.equalsIgnoreCase("off")) {
         this.gatherable = true;
      }

   }

   public String getDiagnosticVolume() {
      return this.diagnosticVolume;
   }

   public boolean isGatherable() {
      return this.gatherable;
   }

   public Properties getSupplementalAttributes() {
      return this.supplementalAttrs;
   }

   private void setSeverity(int severityLevel) {
      this.severity = severityLevel;
      this.severityString = Severities.severityNumToString(this.severity);
      this.supplementalAttrs.put(LoggingSupplementalAttribute.SUPP_ATTR_SEVERITY_VALUE.getAttributeName(), this.severity);
   }

   public String getSeverityString() {
      return this.severityString;
   }

   public String getPartitionId() {
      return this.supplementalAttrs.getProperty(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_ID.getAttributeName());
   }

   public String getPartitionName() {
      return this.supplementalAttrs.getProperty(LoggingSupplementalAttribute.SUPP_ATTR_PARTITION_NAME.getAttributeName());
   }

   public boolean isExcludePartition() {
      return true;
   }

   public void setExcludePartition(boolean value) {
      this.excludePartition = value;
   }
}
