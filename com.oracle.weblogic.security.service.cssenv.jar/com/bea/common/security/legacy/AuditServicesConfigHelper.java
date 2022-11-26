package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;

public interface AuditServicesConfigHelper {
   String getAuditServiceName();

   ServiceConfigCustomizer getAuditServiceCustomizer();

   void addToConfig(ServiceEngineConfig var1, String var2);
}
