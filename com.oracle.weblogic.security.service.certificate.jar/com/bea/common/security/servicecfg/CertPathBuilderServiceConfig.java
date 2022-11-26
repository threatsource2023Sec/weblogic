package com.bea.common.security.servicecfg;

public interface CertPathBuilderServiceConfig {
   String getAuditServiceName();

   String getCertPathBuilderName();

   String[] getCertPathValidatorNames();
}
