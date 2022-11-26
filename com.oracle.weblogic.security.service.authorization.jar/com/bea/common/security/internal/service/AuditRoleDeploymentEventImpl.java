package com.bea.common.security.internal.service;

import com.bea.common.security.service.Identity;
import javax.security.auth.Subject;
import weblogic.security.service.ApplicationResource;
import weblogic.security.spi.AuditRoleDeploymentEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;

public class AuditRoleDeploymentEventImpl implements AuditRoleDeploymentEvent {
   static final String START_EVENT = "RoleManager Start Deploy Role Audit Event";
   static final String END_EVENT = "RoleManager End Deploy Role Audit Event";
   static final String DEPLOY_EVENT = "RoleManager Deploy Audit Event";
   static final String UNDEPLOY_EVENT = "RoleManager Undeploy Audit Event";
   static final String DELETE_EVENT = " RoleManager Delete Application Roles Audit Event";
   private static final String EVENT_TYPE_ATTR = "Event Type";
   private static final String SUBJECT_ATTR = "Subject";
   private static final String RESOURCE_ATTR = "Resource";
   private static final String RESOURCE_TYPE_ATTR = "Resource Type";
   private static final String ROLES_ATTR = "Role";
   private static final String USERANDGROUPNAMES_ATTR = "User and Group Names";
   private static final String SEPARATOR = " : ";
   private AuditSeverity severity;
   private String eventType;
   private Identity identity;
   private Exception exception;
   private String role = null;
   private String[] userAndGroupNames = null;
   private Resource resource = null;

   AuditRoleDeploymentEventImpl(AuditSeverity severity, String eventType, Identity identity, String appId, Exception exception) {
      this.severity = severity;
      this.eventType = eventType;
      this.identity = identity;
      this.resource = new ApplicationResource(appId);
      this.exception = exception;
   }

   AuditRoleDeploymentEventImpl(AuditSeverity severity, String eventType, Identity identity, Resource resource, String roleName, Exception exception) {
      this.severity = severity;
      this.eventType = eventType;
      this.identity = identity;
      this.resource = resource;
      this.role = roleName;
      this.exception = exception;
   }

   AuditRoleDeploymentEventImpl(AuditSeverity severity, String eventType, Identity identity, Resource resource, String roleName, String[] userAndGroupNames, Exception exception) {
      this.severity = severity;
      this.eventType = eventType;
      this.identity = identity;
      this.resource = resource;
      this.role = roleName;
      this.userAndGroupNames = userAndGroupNames;
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

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("<");
      this.writeAttributes(buf);
      buf.append(">");
      return buf.toString();
   }

   public String[] getUserAndGroupNames() {
      return this.userAndGroupNames;
   }

   public String getRoleName() {
      return this.role;
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

      if (this.role != null) {
         buf.append("<");
         buf.append("Role");
         buf.append(" = ");
         buf.append(this.role);
         buf.append(">");
      }

      if (this.userAndGroupNames != null) {
         buf.append("<");
         buf.append("User and Group Names");
         buf.append(" = ");
         buf.append(this.getUserAndGroupNamesString(this.userAndGroupNames));
         buf.append(">");
      }

   }

   private String getUserAndGroupNamesString(String[] userAndGroupNames) {
      StringBuffer result = new StringBuffer();

      for(int i = 0; i < userAndGroupNames.length; ++i) {
         if (i != 0) {
            result.append(" : ");
         }

         result.append(userAndGroupNames[i]);
      }

      return result.toString();
   }
}
