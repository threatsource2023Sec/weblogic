package weblogic.security.service;

import weblogic.security.spi.AuditMgmtEvent;
import weblogic.security.spi.AuditSeverity;

public class AuditSecurityManagementEventImpl implements AuditMgmtEvent {
   private static final String EVENT_TYPE = "Event Type = ";
   private static final String EVENT_NAME = " Security Management Audit Event";
   private AuditSeverity severity;
   private String eventType;
   private String message;
   private Exception exception;

   AuditSecurityManagementEventImpl(AuditSeverity severity, String type, String msg, Exception exception) {
      this.severity = severity;
      this.eventType = "Event Type = " + type + " Security Management Audit Event";
      this.message = msg;
      this.exception = exception;
   }

   public String getEventType() {
      return this.eventType;
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public Exception getFailureException() {
      return this.exception;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("<<").append("Event Type = ").append("><");
      buf.append(this.message).append(">");
      if (this.exception != null) {
         buf.append("<").append(this.exception.toString()).append(">");
      }

      buf.append(">");
      return buf.toString();
   }
}
