package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.IdentityServicesConfigHelper;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.ServiceConfigCustomizer;
import com.bea.common.security.service.IdentityService;
import weblogic.management.security.RealmMBean;

public class IdentityServicesConfigHelperImpl extends BaseServicesConfigImpl implements IdentityServicesConfigHelper {
   ServiceConfigCustomizerImpl identityServiceCustomizer = new ServiceConfigCustomizerImpl(this.getIdentityServiceName());

   public String getIdentityServiceName() {
      return IdentityServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public IdentityServicesConfigHelperImpl(RealmMBean realmMBean, SecurityProviderConfigHelper providerHelper) {
      super(realmMBean, providerHelper);
   }

   public ServiceConfigCustomizer getIdentityServiceCustomizer() {
      return this.identityServiceCustomizer;
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, IdentityService identityService) {
      if (!this.identityServiceCustomizer.isServiceRemoved()) {
         serviceEngineConfig.addEnvironmentManagedServiceConfig(this.identityServiceCustomizer.getServiceName(), identityService, true);
      }

   }
}
