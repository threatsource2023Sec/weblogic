package weblogic.security.service;

import javax.security.auth.Subject;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.AuditApplicationVersionEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;

public class AuditApplicationVersionEventImpl implements AuditApplicationVersionEvent {
   public static final String CREATEVEROP = "Create Application Version";
   public static final String DELETEVEROP = "Delete Application Version";
   public static final String DELETEOP = "Delete Application";
   public static final String SECURITY_SERVICE = "Security Service";
   static final String ATZMGR = "Authorization";
   static final String ROLEMGR = "RoleManager";
   static final String CREDMGR = "Credential Mapping";
   private static final String EVENTTYPE = "Event Type = ";
   private static final String EVENTNAME = " Application Version Audit Event";
   private AuditSeverity severity;
   private String eventType;
   private Subject subject;
   private Resource resource;
   private String operation;
   private Exception exception;
   private StringBuffer toStringBuffer;

   public AuditApplicationVersionEventImpl(AuditSeverity severity, String mgrName, AuthenticatedSubject aSubject, String appId, String operation, Exception exception) {
      this.severity = severity;
      this.eventType = "Event Type = " + mgrName + " Application Version Audit Event";
      this.subject = aSubject != null ? aSubject.getSubject() : null;
      this.resource = new ApplicationResource(appId);
      this.operation = operation;
      this.exception = exception;
   }

   public String getEventType() {
      return this.eventType;
   }

   public String getOperationType() {
      return this.operation;
   }

   public Subject getSubject() {
      return this.subject;
   }

   public Resource getResource() {
      return this.resource;
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public Exception getFailureException() {
      return this.exception;
   }

   public String toString() {
      this.toStringBuffer = new StringBuffer();
      this.toStringBuffer.append("<");
      this.toStringBuffer.append("<");
      this.toStringBuffer.append(this.eventType);
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.subject == null ? "" : SubjectUtils.displaySubject(this.subject));
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.resource.getType());
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.resource.toString());
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.operation);
      this.toStringBuffer.append(">");
      this.toStringBuffer.append(">");
      return new String(this.toStringBuffer);
   }
}
