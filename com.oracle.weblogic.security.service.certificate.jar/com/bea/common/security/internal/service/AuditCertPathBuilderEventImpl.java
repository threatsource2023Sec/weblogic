package com.bea.common.security.internal.service;

import java.security.cert.X509Certificate;
import weblogic.security.pk.CertPathSelector;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditCertPathBuilderEvent;
import weblogic.security.spi.AuditSeverity;

public class AuditCertPathBuilderEventImpl implements AuditCertPathBuilderEvent {
   private static final String eventType = "Event Type = CertPathBuilder Audit Event ";
   private AuditSeverity severity;
   private CertPathSelector selector;
   private X509Certificate[] trustedCAs;
   private ContextHandler context;
   private Exception exception;

   AuditCertPathBuilderEventImpl(AuditSeverity severity, CertPathSelector selector, X509Certificate[] trustedCAs, ContextHandler context, Exception exception) {
      this.severity = severity;
      this.selector = selector;
      this.trustedCAs = trustedCAs;
      this.context = context;
      this.exception = exception;
   }

   public String getEventType() {
      return "Event Type = CertPathBuilder Audit Event ";
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public CertPathSelector getCertPathSelector() {
      return this.selector;
   }

   public X509Certificate[] getTrustedCAs() {
      return this.trustedCAs;
   }

   public ContextHandler getContext() {
      return this.context;
   }

   public Exception getFailureException() {
      return this.exception;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("<");
      buf.append("<");
      buf.append("Event Type = CertPathBuilder Audit Event ");
      buf.append(">");
      String selectorString = this.selector != null ? this.selector.toString() : null;
      buf.append("<");
      buf.append(selectorString);
      buf.append("><");
      buf.append(">");
      return buf.toString();
   }
}
