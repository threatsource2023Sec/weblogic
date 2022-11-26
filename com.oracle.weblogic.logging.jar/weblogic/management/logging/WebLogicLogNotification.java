package weblogic.management.logging;

import javax.management.Notification;
import weblogic.logging.LogEntry;
import weblogic.logging.ThrowableInfo;

public final class WebLogicLogNotification extends Notification {
   private static final long serialVersionUID = 8373884439485373606L;
   private static final boolean debug = false;
   private static final String DELIM = ".";
   private static final int SS_INDEX = 3;
   private static final int ID_INDEX = 4;
   private int severity;
   private String serverName;
   private String machineName;
   private String threadId;
   private String tranId;
   private String userId;
   private String message;
   private Throwable th;
   private int intId = -1;
   private String msgId = null;
   private String subsystem = null;
   private String idAsString = null;
   private String diagCtxId = null;
   private String stackTrace = null;

   public WebLogicLogNotification(String type, long sequenceNo, Object source, LogEntry logEntry) {
      super(type, source, sequenceNo, logEntry.getTimestamp(), logEntry.getLogMessage());
      this.machineName = logEntry.getMachineName();
      this.serverName = logEntry.getServerName();
      this.threadId = logEntry.getThreadName();
      this.userId = logEntry.getUserId();
      this.tranId = logEntry.getTransactionId();
      this.severity = logEntry.getSeverity();
      this.diagCtxId = logEntry.getDiagnosticContextId();
      ThrowableInfo thInfo = logEntry.getThrowableInfo();
      if (thInfo != null) {
         this.th = new Throwable(thInfo.getMessage());
         this.th.setStackTrace(thInfo.getStackTrace());
         this.stackTrace = thInfo.toString();
      }

      this.subsystem = logEntry.getSubsystem();
      this.msgId = logEntry.getId();
   }

   public final int getVersion() {
      return 1;
   }

   public String getType() {
      return super.getType();
   }

   public String getMachineName() {
      return this.machineName;
   }

   public String getServername() {
      return this.serverName;
   }

   public String getThreadId() {
      return this.threadId;
   }

   public String getUserId() {
      return this.userId;
   }

   public String getTransactionId() {
      return this.tranId;
   }

   public int getSeverity() {
      return this.severity;
   }

   /** @deprecated */
   @Deprecated
   public Throwable getThrowable() {
      return this.th;
   }

   public int getMessageId() {
      if (this.intId == -1) {
         String id = this.getMessageIdString();
         if (id != null) {
            try {
               this.intId = Integer.parseInt(id);
            } catch (NumberFormatException var3) {
            }
         }
      }

      return this.intId;
   }

   public String getId() {
      return this.msgId;
   }

   public String getMessageIdString() {
      if (this.idAsString == null) {
         int dash = this.msgId.indexOf("-");
         if (dash != -1) {
            this.idAsString = this.msgId.substring(dash + 1);
         } else {
            this.idAsString = this.msgId;
         }
      }

      return this.idAsString;
   }

   public String getSubsystem() {
      return this.subsystem;
   }

   public String getDiagnosticContextId() {
      return this.diagCtxId;
   }

   public String getStackTrace() {
      return this.stackTrace;
   }
}
