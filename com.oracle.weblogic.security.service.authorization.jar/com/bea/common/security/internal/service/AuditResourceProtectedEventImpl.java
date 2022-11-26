package com.bea.common.security.internal.service;

import com.bea.common.security.service.Identity;
import javax.security.auth.Subject;
import weblogic.security.spi.AuditPolicyEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;

public class AuditResourceProtectedEventImpl implements AuditPolicyEvent {
   private AuditSeverity severity;
   private String auditRecordText = "";
   private static final String eventType = "Event Type = Authorization Is Resource Protected Audit Event ";
   private Identity identity;
   private Resource resource;
   private Exception exception;
   private StringBuffer toStringBuffer;

   public AuditResourceProtectedEventImpl(AuditSeverity severity, Identity identity, Resource resource, Exception exception) {
      this.severity = severity;
      this.identity = identity;
      this.resource = resource;
      this.exception = exception;
   }

   public String getEventType() {
      return "Event Type = Authorization Is Resource Protected Audit Event ";
   }

   public Subject getSubject() {
      return this.identity == null ? null : this.identity.getSubject();
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public Resource getResource() {
      return this.resource;
   }

   public Exception getFailureException() {
      return this.exception;
   }

   public String toString() {
      String resourceClassName = new String(this.resource.getType());
      this.toStringBuffer = new StringBuffer();
      this.toStringBuffer.append("<");
      this.toStringBuffer.append("<");
      this.toStringBuffer.append("Event Type = Authorization Is Resource Protected Audit Event ");
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.identity == null ? "" : this.identity.toString());
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(resourceClassName == null ? "" : resourceClassName);
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.resource.toString());
      this.toStringBuffer.append(">");
      this.toStringBuffer.append(">");
      return new String(this.toStringBuffer);
   }
}
