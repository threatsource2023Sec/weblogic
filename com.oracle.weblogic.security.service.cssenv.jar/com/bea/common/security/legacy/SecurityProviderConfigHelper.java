package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;
import weblogic.management.security.ProviderMBean;

public interface SecurityProviderConfigHelper {
   String getServiceName(ProviderMBean var1);

   void addToConfig(ServiceEngineConfig var1, String var2, ProviderMBean var3);

   void addToConfig(ServiceEngineConfig var1, String var2, ProviderMBean[] var3);
}
