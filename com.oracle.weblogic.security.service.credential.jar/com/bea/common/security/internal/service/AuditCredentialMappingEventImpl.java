package com.bea.common.security.internal.service;

import com.bea.common.security.service.Identity;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditCredentialMappingEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Resource;

public class AuditCredentialMappingEventImpl implements AuditCredentialMappingEvent {
   private static final String EVENT_TYPE = "Event Type = Credential Mapping Audit Event";
   private static PasswordCredentialHelper pcHelper = new PasswordCredentialHelper();
   private AuditSeverity severity;
   private Identity requestor;
   private Identity initiatorIdentity;
   private String initiatorString;
   private Resource resource;
   private String[] credTypes;
   private Object[] credentials;
   private ContextHandler context;
   private Exception exception;

   AuditCredentialMappingEventImpl(AuditSeverity severity, Identity requestor, Identity initiatorIdentity, String initiatorString, Resource resource, ContextHandler context, String[] credTypes, Object[] credentials, Exception exception) {
      this.severity = severity;
      this.requestor = requestor;
      this.initiatorIdentity = initiatorIdentity;
      this.initiatorString = initiatorString;
      this.resource = resource;
      this.context = context;
      this.credTypes = credTypes;
      this.credentials = credentials;
      this.exception = exception;
   }

   public String getEventType() {
      return "Event Type = Credential Mapping Audit Event";
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public Subject getRequestorSubject() {
      return this.requestor.getSubject();
   }

   public Subject getInitiatorSubject() {
      return this.initiatorIdentity.getSubject();
   }

   public String getInitiatorString() {
      return this.initiatorString;
   }

   public Resource getResource() {
      return this.resource;
   }

   public ContextHandler getContext() {
      return this.context;
   }

   public String[] getCredentialTypes() {
      return this.credTypes;
   }

   public Object[] getCredentials() {
      return this.credentials;
   }

   public Exception getFailureException() {
      return this.exception;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("<<").append("Event Type = Credential Mapping Audit Event").append("><");
      if (this.requestor != null) {
         buf.append(this.requestor.toString());
      }

      buf.append("><");
      if (this.initiatorIdentity != null) {
         buf.append(this.initiatorIdentity.toString());
      } else if (this.initiatorString != null) {
         buf.append(this.initiatorString);
      }

      buf.append("><");
      if (this.resource != null) {
         buf.append(this.resource.toString());
      }

      buf.append("><");
      int i;
      if (this.credTypes != null) {
         for(i = 0; i < this.credTypes.length; ++i) {
            if (i > 0) {
               buf.append(',');
            }

            buf.append(this.credTypes[i]);
         }
      }

      buf.append("><");
      if (this.credentials != null) {
         for(i = 0; i < this.credentials.length; ++i) {
            if (i > 0) {
               buf.append(',');
            }

            String tempuser = pcHelper.getPasswordCredentialUsername(this.credentials[i]);
            if (tempuser != null) {
               buf.append(tempuser);
            } else {
               buf.append(this.credentials[i].getClass().getName());
            }
         }
      }

      buf.append(">>");
      return buf.toString();
   }
}
