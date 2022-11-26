package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;

public interface SecurityTokenServicesConfigHelper {
   String getSecurityTokenServiceName();

   ServiceConfigCustomizer getSecurityTokenServiceCustomizer();

   void addToConfig(ServiceEngineConfig var1, String var2);
}
