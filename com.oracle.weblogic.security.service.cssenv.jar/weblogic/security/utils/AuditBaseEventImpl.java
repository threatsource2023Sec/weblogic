package weblogic.security.utils;

import weblogic.security.spi.AuditSeverity;

public class AuditBaseEventImpl {
   private static final String EVENT_TYPE_ATTR = "Event Type";
   private AuditSeverity severity;
   private String eventType;
   private Exception exception;

   public AuditBaseEventImpl(AuditSeverity severity, String eventType) {
      this(severity, eventType, (Exception)null);
   }

   public AuditBaseEventImpl(AuditSeverity severity, String eventType, Exception exception) {
      this.severity = severity;
      this.eventType = eventType;
      this.exception = exception;
   }

   public void setFailureException(Exception exception) {
      this.exception = exception;
   }

   public Exception getFailureException() {
      return this.exception;
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public String getEventType() {
      return this.eventType;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("<");
      this.writeAttributes(buf);
      buf.append(">");
      return buf.toString();
   }

   protected void writeAttributes(StringBuffer buf) {
      buf.append("<");
      buf.append("Event Type");
      buf.append(" = ");
      buf.append(this.eventType);
      buf.append(">");
   }
}
