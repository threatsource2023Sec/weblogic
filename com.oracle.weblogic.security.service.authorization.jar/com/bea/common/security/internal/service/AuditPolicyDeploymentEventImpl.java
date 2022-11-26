package com.bea.common.security.internal.service;

import com.bea.common.security.service.Identity;
import javax.security.auth.Subject;
import weblogic.security.service.ApplicationResource;
import weblogic.security.spi.AuditPolicyEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;

public class AuditPolicyDeploymentEventImpl implements AuditPolicyEvent {
   static final String START_EVENT = "Authorization Start Policy Deploy Audit Event";
   static final String END_EVENT = "Authorization End Policy Deploy Audit Event";
   static final String DEPLOY_EVENT = "Authorization Policy Deploy Audit Event";
   static final String UNDEPLOY_EVENT = "Authorization Policy Undeploy Audit Event";
   static final String DELETE_EVENT = "Authorization Delete Application Policies Audit Event";
   private static final String EVENT_TYPE_ATTR = "Event Type";
   private static final String SUBJECT_ATTR = "Subject";
   private static final String RESOURCE_ATTR = "Resource";
   private static final String RESOURCE_TYPE_ATTR = "Resource Type";
   private static final String ROLES_ATTR = "Allowed Roles";
   private static final String SEPARATOR = " : ";
   private AuditSeverity severity;
   private String eventType;
   private Identity identity;
   private Exception exception;
   private String[] roles = null;
   private Resource resource = null;

   AuditPolicyDeploymentEventImpl(AuditSeverity severity, String eventType, Identity identity, String appId, Exception exception) {
      this.severity = severity;
      this.eventType = eventType;
      this.identity = identity;
      this.resource = new ApplicationResource(appId);
      this.exception = exception;
   }

   AuditPolicyDeploymentEventImpl(AuditSeverity severity, String eventType, Identity identity, Resource resource, Exception exception) {
      this.severity = severity;
      this.eventType = eventType;
      this.identity = identity;
      this.resource = resource;
      this.exception = exception;
   }

   AuditPolicyDeploymentEventImpl(AuditSeverity severity, String eventType, Identity identity, Resource resource, String[] roleNames, Exception exception) {
      this.severity = severity;
      this.eventType = eventType;
      this.identity = identity;
      this.resource = resource;
      this.roles = roleNames;
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

   public Subject getSubject() {
      return this.identity == null ? null : this.identity.getSubject();
   }

   public Resource getResource() {
      return this.resource;
   }

   public String[] getRoleNames() {
      return this.roles;
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
      if (this.resource != null) {
         buf.append("<");
         buf.append("Resource Type");
         buf.append(" = ");
         buf.append(new String(this.resource.getType()));
         buf.append("><");
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
}
