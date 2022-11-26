package com.bea.common.security.internal.service;

import java.security.cert.CertPath;
import java.security.cert.X509Certificate;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditCertPathValidatorEvent;
import weblogic.security.spi.AuditSeverity;

public class AuditCertPathValidatorEventImpl implements AuditCertPathValidatorEvent {
   private static final String eventType = "Event Type = CertPathValidator Audit Event ";
   private AuditSeverity severity;
   private CertPath certPath;
   private X509Certificate[] trustedCAs;
   private ContextHandler context;
   private Exception exception;

   AuditCertPathValidatorEventImpl(AuditSeverity severity, CertPath certPath, X509Certificate[] trustedCAs, ContextHandler context, Exception exception) {
      this.severity = severity;
      this.certPath = certPath;
      this.trustedCAs = trustedCAs;
      this.context = context;
      this.exception = exception;
   }

   public String getEventType() {
      return "Event Type = CertPathValidator Audit Event ";
   }

   public AuditSeverity getSeverity() {
      return this.severity;
   }

   public CertPath getCertPath() {
      return this.certPath;
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
      buf.append("Event Type = CertPathValidator Audit Event ");
      buf.append(">");
      String certPathString = this.certPath != null ? this.certPath.toString() : null;
      buf.append("<");
      buf.append(certPathString);
      buf.append("><");
      buf.append(">");
      return buf.toString();
   }
}
