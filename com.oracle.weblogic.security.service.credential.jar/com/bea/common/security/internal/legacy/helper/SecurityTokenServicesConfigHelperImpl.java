package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.SecurityTokenServicesConfigHelper;
import com.bea.common.security.legacy.ServiceConfigCustomizer;
import weblogic.management.security.RealmMBean;

public class SecurityTokenServicesConfigHelperImpl extends BaseServicesConfigImpl implements SecurityTokenServicesConfigHelper {
   private ServiceConfigCustomizerImpl securityTokenServiceCustomizer = new ServiceConfigCustomizerImpl(this.getSecurityTokenServiceName());

   public String getSecurityTokenServiceName() {
      return SecurityTokenServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public SecurityTokenServicesConfigHelperImpl(RealmMBean realmMBean, SecurityProviderConfigHelper providerHelper) {
      super(realmMBean, providerHelper);
   }

   public ServiceConfigCustomizer getSecurityTokenServiceCustomizer() {
      return this.securityTokenServiceCustomizer;
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName) {
      if (!this.securityTokenServiceCustomizer.isServiceRemoved()) {
         SecurityTokenServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.securityTokenServiceCustomizer.getServiceName());
      }

   }
}
