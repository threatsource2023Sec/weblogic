package com.bea.common.security.internal.service;

import com.bea.common.security.service.Identity;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditRoleEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;
import weblogic.security.spi.RoleCollectionInfo;

public class AuditRoleConsumerEventImpl implements AuditRoleEvent {
   static final String GET_HANDLER_EVENT = "Role Consumer Get Handler";
   static final String SET_ROLE_EVENT = "Role Consumer Set Role";
   static final String HANDLER_DONE_EVENT = "Role Consumer Done";
   private static final String EVENT_TYPE_ATTR = "Event Type";
   private static final String SUBJECT_ATTR = "Subject";
   private static final String RESOURCE_ATTR = "Resource";
   private static final String ROLE_ATTR = "Role Name";
   private static final String USERS_ATTR = "Users";
   private static final String GROUP_ATTR = "Groups";
   private static final String INFO_ATTR = "Role Consumer Information";
   private static final String SEPARATOR = " : ";
   private AuditSeverity severity;
   private String eventType;
   private Identity identity;
   private Exception exception;
   private String roleName;
   private String[] userAndGroupNames;
   private Resource resource;
   private RoleCollectionInfo information;

   AuditRoleConsumerEventImpl(AuditSeverity severity, String eventType, Identity identity, RoleCollectionInfo information, Exception exception) {
      this.roleName = null;
      this.userAndGroupNames = null;
      this.resource = null;
      this.information = null;
      this.severity = severity;
      this.eventType = eventType;
      this.identity = identity;
      this.information = information;
      this.exception = exception;
   }

   AuditRoleConsumerEventImpl(AuditSeverity severity, String eventType, Identity identity, Exception exception) {
      this(severity, eventType, identity, (RoleCollectionInfo)null, exception);
   }

   AuditRoleConsumerEventImpl(AuditSeverity severity, String eventType, Identity identity, Resource resource, String roleName, String[] userAndGroupNames, Exception exception) {
      this.roleName = null;
      this.userAndGroupNames = null;
      this.resource = null;
      this.information = null;
      this.severity = severity;
      this.eventType = eventType;
      this.identity = identity;
      this.resource = resource;
      this.roleName = roleName;
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
         buf.append("Role Consumer Information");
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

      if (this.roleName != null) {
         buf.append("<");
         buf.append("Role Name");
         buf.append(" = ");
         buf.append(this.roleName);
         buf.append(">");
      }

      if (this.userAndGroupNames != null) {
         buf.append("<");
         buf.append("Users");
         buf.append(" = ");
         buf.append(this.getPrincipals(this.userAndGroupNames));
         buf.append(">");
      }

   }

   private String getPrincipals(String[] userAndGroupNames) {
      StringBuffer result = new StringBuffer();

      for(int i = 0; i < userAndGroupNames.length; ++i) {
         if (i != 0) {
            result.append(" : ");
         }

         result.append(userAndGroupNames[i]);
      }

      return result.toString();
   }

   private String getInformation(RoleCollectionInfo info) {
      String result = info.getName() + " : " + info.getVersion() + " : " + info.getTimestamp();
      Resource[] res = info.getResourceTypes();

      for(int i = 0; res != null && i < res.length; ++i) {
         result = result + " : " + res[i].getType();
      }

      return result;
   }

   public ContextHandler getContext() {
      return null;
   }
}
