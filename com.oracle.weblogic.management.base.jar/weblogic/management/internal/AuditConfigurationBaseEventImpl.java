package weblogic.management.internal;

import javax.security.auth.Subject;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditConfigurationEvent;
import weblogic.security.spi.AuditSeverity;

public class AuditConfigurationBaseEventImpl implements AuditConfigurationEvent {
   private static final String EVENT_TYPE_ATTR = "Event Type";
   private static final String SUBJECT_ATTR = "Subject";
   private static final String OBJECT_ATTR = "Object";
   private AuditSeverity severity;
   private String eventType;
   private AuthenticatedSubject subject;
   private String objectName;
   private Exception exception;
   private ContextHandler context;

   public AuditConfigurationBaseEventImpl(AuditSeverity severity, String eventType, AuthenticatedSubject subject, String objectName) {
      this.severity = severity;
      this.eventType = eventType;
      this.subject = subject;
      this.objectName = objectName;
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

   public String getObjectName() {
      return this.objectName;
   }

   public Subject getSubject() {
      return this.subject.getSubject();
   }

   public void setContext(ContextHandler context) {
      this.context = context;
   }

   public ContextHandler getContext() {
      return this.context;
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
      buf.append("><");
      buf.append("Subject");
      buf.append(" = ");
      buf.append(SubjectUtils.displaySubject(this.subject));
      buf.append("><");
      buf.append("Object");
      buf.append(" = ");
      buf.append(this.objectName);
      buf.append(">");
   }
}
