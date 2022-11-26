package com.bea.common.security.internal.service;

import weblogic.security.spi.AuditLifecycleEvent;
import weblogic.security.spi.AuditSeverity;

public class AuditLifecycleEventImpl implements AuditLifecycleEvent {
   private AuditSeverity severity;
   private AuditLifecycleEvent.AuditLifecycleEventType lifecycleEventType;
   private static final String eventType = "Event Type = AuditLifecycle Audit Event";
   private Exception exception;

   AuditLifecycleEventImpl(AuditSeverity severity, AuditLifecycleEvent.AuditLifecycleEventType lifecycleEventType, Exception exception) {
      this.severity = severity;
      this.lifecycleEventType = lifecycleEventType;
      this.exception = exception;
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public AuditLifecycleEvent.AuditLifecycleEventType getAuditLifecycleEventType() {
      return this.lifecycleEventType;
   }

   public String getEventType() {
      return "Event Type = AuditLifecycle Audit Event";
   }

   public Exception getFailureException() {
      return this.exception;
   }

   public String toString() {
      String toString = "<<Event Type = AuditLifecycle Audit Event><" + this.lifecycleEventType.toString() + ">>";
      return toString;
   }
}
