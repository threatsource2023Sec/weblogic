package com.bea.common.security.internal.service;

import com.bea.common.security.service.Identity;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.SecurityRole;
import weblogic.security.spi.AuditRoleEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;

public class AuditRoleEventImpl implements AuditRoleEvent {
   private AuditSeverity severity;
   private static final String eventType = "Event Type = RoleManager Audit Event ";
   private Identity identity;
   private Resource resource;
   private ContextHandler context;
   private Map roles;
   private Exception exception;
   private StringBuffer toStringBuffer;

   AuditRoleEventImpl(AuditSeverity severity, Identity identity, Resource resource, ContextHandler context, Map roles, Exception exception) {
      this.severity = severity;
      this.identity = identity;
      this.resource = resource;
      this.context = context;
      this.roles = roles;
      this.exception = exception;
   }

   public String getEventType() {
      return "Event Type = RoleManager Audit Event ";
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

   public Exception getFailureException() {
      return this.exception;
   }

   public ContextHandler getContext() {
      return this.context;
   }

   public Map getRoles() {
      return this.roles;
   }

   public String toString() {
      String resourceClassName = new String(this.resource.getType());
      this.toStringBuffer = new StringBuffer();
      this.toStringBuffer.append("<");
      this.toStringBuffer.append("<");
      this.toStringBuffer.append("Event Type = RoleManager Audit Event ");
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.identity == null ? "" : this.identity.toString());
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(resourceClassName == null ? "" : resourceClassName);
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(this.resource.toString());
      this.toStringBuffer.append("><");
      this.toStringBuffer.append(getRolesString(this.roles));
      this.toStringBuffer.append(">");
      this.toStringBuffer.append(">");
      return new String(this.toStringBuffer);
   }

   static String getRolesString(Map roles) {
      if (roles == null) {
         return "";
      } else if (roles.isEmpty()) {
         return "";
      } else {
         Collection roleCollection = null;
         Iterator roleCollectionIter = null;
         StringBuffer buf = new StringBuffer();
         roleCollection = roles.values();
         roleCollectionIter = roleCollection.iterator();

         while(roleCollectionIter.hasNext()) {
            SecurityRole aRole = (SecurityRole)((SecurityRole)roleCollectionIter.next());
            buf.append("||" + aRole.getName());
         }

         return buf.toString();
      }
   }
}
