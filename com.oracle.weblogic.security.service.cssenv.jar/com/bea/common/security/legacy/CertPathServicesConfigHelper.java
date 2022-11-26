package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;

public interface CertPathServicesConfigHelper {
   String getCertPathBuilderServiceName();

   String getCertPathValidatorServiceName();

   ServiceConfigCustomizer getCertPathBuilderServiceCustomizer();

   ServiceConfigCustomizer getCertPathValidatorServiceCustomizer();

   void addToConfig(ServiceEngineConfig var1, String var2);
}
