package com.bea.common.security.internal.service;

import com.bea.common.security.service.Identity;
import javax.security.auth.Subject;
import weblogic.security.spi.AuditPolicyEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.PolicyCollectionInfo;
import weblogic.security.spi.Resource;

public class AuditPolicyConsumerEventImpl implements AuditPolicyEvent {
   static final String GET_HANDLER_EVENT = "Policy Consumer Get Handler";
   static final String SET_POLICY_EVENT = "Policy Consumer Set Policy";
   static final String SET_UNCHECKED_EVENT = "Policy Consumer Set Unchecked Policy";
   static final String HANDLER_DONE_EVENT = "Policy Consumer Done";
   private static final String EVENT_TYPE_ATTR = "Event Type";
   private static final String SUBJECT_ATTR = "Subject";
   private static final String RESOURCE_ATTR = "Resource";
   private static final String ROLES_ATTR = "Allowed Roles";
   private static final String INFO_ATTR = "Policy Consumer Information";
   private static final String SEPARATOR = " : ";
   private AuditSeverity severity;
   private String eventType;
   private Identity identity;
   private Exception exception;
   private String[] roles;
   private Resource resource;
   private PolicyCollectionInfo information;

   AuditPolicyConsumerEventImpl(AuditSeverity severity, String eventType, Identity identity, PolicyCollectionInfo information, Exception exception) {
      this.roles = null;
      this.resource = null;
      this.information = null;
      this.severity = severity;
      this.eventType = eventType;
      this.identity = identity;
      this.information = information;
      this.exception = exception;
   }

   AuditPolicyConsumerEventImpl(AuditSeverity severity, String eventType, Identity identity, Resource resource, String[] roles, Exception exception) {
      this.roles = null;
      this.resource = null;
      this.information = null;
      this.severity = severity;
      this.eventType = eventType;
      this.identity = identity;
      this.resource = resource;
      this.roles = roles;
      this.exception = exception;
   }

   AuditPolicyConsumerEventImpl(AuditSeverity severity, String eventType, Identity identity, Exception exception) {
      this(severity, eventType, identity, (Resource)null, (String[])null, exception);
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

   public Subject getSubject() {
      return this.identity == null ? null : this.identity.getSubject();
   }

   public Resource getResource() {
      return this.resource;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("<");
      this.writeAttributes(buf);
      buf.append(">");
      return buf.toString();
   }

   private void writeAttributes(StringBuffer buf) {
      buf.append("<");
      buf.append("Event Type");
      buf.append(" = ");
      buf.append(this.eventType);
      buf.append("><");
      buf.append("Subject");
      buf.append(" = ");
      buf.append(this.identity == null ? "" : this.identity.toString());
      buf.append(">");
      if (this.information != null) {
         buf.append("<");
         buf.append("Policy Consumer Information");
         buf.append(" = ");
         buf.append(this.getInformation(this.information));
         buf.append(">");
      }

      if (this.resource != null) {
         buf.append("<");
         buf.append("Resource");
         buf.append(" = ");
         buf.append(this.resource.toString());
         buf.append(">");
      }

      if (this.roles != null) {
         buf.append("<");
         buf.append("Allowed Roles");
         buf.append(" = ");
         buf.append(this.getRoles(this.roles));
         buf.append(">");
      }

   }

   private String getRoles(String[] roles) {
      StringBuffer result = new StringBuffer();

      for(int i = 0; i < roles.length; ++i) {
         if (i != 0) {
            result.append(" : ");
         }

         result.append(roles[i]);
      }

      return result.toString();
   }

   private String getInformation(PolicyCollectionInfo info) {
      String result = info.getName() + " : " + info.getVersion() + " : " + info.getTimestamp();
      Resource[] res = info.getResourceTypes();

      for(int i = 0; res != null && i < res.length; ++i) {
         result = result + " : " + res[i].getType();
      }

      return result;
   }
}
