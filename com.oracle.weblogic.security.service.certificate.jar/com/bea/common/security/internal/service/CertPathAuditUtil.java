package com.bea.common.security.internal.service;

import com.bea.common.security.service.AuditService;
import java.security.cert.CertPath;
import java.security.cert.X509Certificate;
import weblogic.security.pk.CertPathSelector;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;

class CertPathAuditUtil {
   static void auditBuild(AuditService auditService, CertPathSelector selector, X509Certificate[] trustedCAs, ContextHandler context) {
      if (auditService.isAuditEnabled()) {
         AuditCertPathBuilderEventImpl event = new AuditCertPathBuilderEventImpl(AuditSeverity.SUCCESS, selector, trustedCAs, context, (Exception)null);
         auditService.writeEvent(event);
      }
   }

   static void auditBuilderException(AuditService auditService, CertPathSelector selector, X509Certificate[] trustedCAs, ContextHandler context, Exception e) {
      if (auditService.isAuditEnabled()) {
         AuditCertPathBuilderEventImpl event = new AuditCertPathBuilderEventImpl(AuditSeverity.FAILURE, selector, trustedCAs, context, e);
         auditService.writeEvent(event);
      }
   }

   static void auditValidate(AuditService auditService, CertPath certPath, X509Certificate[] trustedCAs, ContextHandler context) {
      if (auditService.isAuditEnabled()) {
         AuditCertPathValidatorEventImpl event = new AuditCertPathValidatorEventImpl(AuditSeverity.SUCCESS, certPath, trustedCAs, context, (Exception)null);
         auditService.writeEvent(event);
      }
   }

   static void auditValidatorException(AuditService auditService, CertPath certPath, X509Certificate[] trustedCAs, ContextHandler context, Exception e) {
      if (auditService.isAuditEnabled()) {
         AuditCertPathValidatorEventImpl event = new AuditCertPathValidatorEventImpl(AuditSeverity.FAILURE, certPath, trustedCAs, context, e);
         auditService.writeEvent(event);
      }
   }
}
