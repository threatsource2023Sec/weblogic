package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.service.IdentityService;

public interface IdentityServicesConfigHelper {
   String getIdentityServiceName();

   ServiceConfigCustomizer getIdentityServiceCustomizer();

   void addToConfig(ServiceEngineConfig var1, IdentityService var2);
}
