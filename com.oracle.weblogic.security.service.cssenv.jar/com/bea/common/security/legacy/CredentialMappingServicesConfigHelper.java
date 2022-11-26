package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;

public interface CredentialMappingServicesConfigHelper {
   String getCredentialMappingServiceName();

   ServiceConfigCustomizer getCredentialMappingServiceCustomizer();

   void addToConfig(ServiceEngineConfig var1, String var2);
}
