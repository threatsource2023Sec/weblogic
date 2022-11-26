package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.CredentialMapperConfig;
import com.bea.common.security.internal.legacy.service.CredentialMapperImpl;
import weblogic.management.security.credentials.CredentialMapperMBean;

class CredentialMapperConfigHelper {
   static String getServiceName(CredentialMapperMBean providerMBean) {
      return CredentialMapperConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, CredentialMapperMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBeans[i]), CredentialMapperImpl.class.getName(), false);
         serviceConfig.setConfig(new ConfigImpl(providerMBeans[i], serviceConfig));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      }

   }

   private static class ConfigImpl implements CredentialMapperConfig {
      private String name = null;

      public ConfigImpl(CredentialMapperMBean providerMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         this.name = SecurityProviderConfigHelperImpl._getServiceName(providerMBean);
         serviceConfig.addDependency(this.name);
      }

      public String getCredentialProviderName() {
         return this.name;
      }
   }
}
