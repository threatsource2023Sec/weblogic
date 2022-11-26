package com.bea.common.security.internal.service;

import com.bea.common.security.service.Identity;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditAtzEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;

public class AuditAtzEventImpl implements AuditAtzEvent {
   private AuditSeverity severity;
   private static final String eventType = "Event Type = Authorization Audit Event V2 ";
   private Identity identity;
   private Resource resource;
   private ContextHandler context;
   private Exception exception;
   private Direction direction;
   private StringBuffer toStringBuffer;

   AuditAtzEventImpl(AuditSeverity severity, Identity identity, Resource resource, ContextHandler context, Direction direction, Exception exception) {
      this.severity = severity;
      this.identity = identity;
      this.resource = resource;
      this.context = context;
      this.direction = direction;
      this.exception = exception;
   }

   public String getEventType() {
      return "Event Type = Authorization Audit Event V2 ";
   }

   public Subject getSubject() {
      return this.identity != null ? this.identity.getSubject() : null;
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public Resource getResource() {
      return this.resource;
   }

   public Direction getDirection() {
      return this.direction;
   }

   public Exception getFailureException() {
      return this.exception;
   }

   public ContextHandler getContext() {
      return this.context;
   }

   public String toString() {
      String resourceClassName = new String(this.resource.getType());
      this.toStringBuffer = new StringBuffer();
      this.toStringBuffer.append("<");
      this.toStringBuffer.append("<");
      this.toStringBuffer.append("Event Type = Authorization Audit Event V2 ");
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.identity == null ? "" : this.identity.toString());
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.direction.toString());
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(resourceClassName == null ? "" : resourceClassName);
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.resource.toString());
      this.toStringBuffer.append(">");
      this.toStringBuffer.append(">");
      return new String(this.toStringBuffer);
   }
}
