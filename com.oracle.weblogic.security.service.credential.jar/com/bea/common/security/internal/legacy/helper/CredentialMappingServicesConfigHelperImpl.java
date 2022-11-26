package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.CredentialMappingServicesConfigHelper;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.ServiceConfigCustomizer;
import weblogic.management.security.RealmMBean;

public class CredentialMappingServicesConfigHelperImpl extends BaseServicesConfigImpl implements CredentialMappingServicesConfigHelper {
   private ServiceConfigCustomizerImpl credentialMappingServiceCustomizer = new ServiceConfigCustomizerImpl(this.getCredentialMappingServiceName());

   public String getCredentialMappingServiceName() {
      return CredentialMappingServiceConfigHelper.getServiceName(this.getRealmMBean());
   }

   public CredentialMappingServicesConfigHelperImpl(RealmMBean realmMBean, SecurityProviderConfigHelper providerHelper) {
      super(realmMBean, providerHelper);
   }

   public ServiceConfigCustomizer getCredentialMappingServiceCustomizer() {
      return this.credentialMappingServiceCustomizer;
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName) {
      this.getSecurityProviderConfigHelper().addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean().getCredentialMappers());
      if (!this.credentialMappingServiceCustomizer.isServiceRemoved()) {
         CredentialMappingServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, this.getRealmMBean(), this.credentialMappingServiceCustomizer.getServiceName());
      }

   }
}
