package com.bea.common.security.internal.service;

import com.bea.common.security.utils.UsernameUtils;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditAtnEventV2;
import weblogic.security.spi.AuditSeverity;

public class AuditAtnEventImpl implements AuditAtnEventV2 {
   private AuditSeverity severity;
   private String username;
   private ContextHandler handler;
   private AuditAtnEventV2.AtnEventTypeV2 atnEventType;
   private static final String eventType = "Event Type = Authentication Audit Event";
   private Exception exception;

   AuditAtnEventImpl(AuditSeverity severity, String username, ContextHandler handler, AuditAtnEventV2.AtnEventTypeV2 atnEventType, Exception exception) {
      this.severity = severity;
      this.username = username;
      this.handler = handler;
      this.atnEventType = atnEventType;
      this.exception = exception;
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public String getUsername() {
      return this.username;
   }

   public ContextHandler getHandler() {
      return this.handler;
   }

   public AuditAtnEventV2.AtnEventTypeV2 getAtnEventType() {
      return this.atnEventType;
   }

   public ContextHandler getContext() {
      return this.handler;
   }

   public Exception getFailureException() {
      return this.exception;
   }

   public String getEventType() {
      return "Event Type = Authentication Audit Event";
   }

   public String toString() {
      String toString = "<<Event Type = Authentication Audit Event><" + UsernameUtils.getTruncatedUsername(this.username) + "><" + this.atnEventType.toString() + ">>";
      return toString;
   }
}
