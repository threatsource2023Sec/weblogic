package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.JAASIdentityAssertionProviderConfig;
import com.bea.common.security.internal.legacy.service.JAASIdentityAssertionProviderImpl;
import weblogic.management.security.authentication.AuthenticationProviderMBean;

class JAASIdentityAssertionProviderConfigHelper {
   static String getServiceName(AuthenticationProviderMBean providerMBean) {
      return JAASIdentityAssertionProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, AuthenticationProviderMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBeans[i]), JAASIdentityAssertionProviderImpl.class.getName(), false);
         serviceConfig.setConfig(new ConfigImpl(providerMBeans[i], serviceConfig));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }

   }

   private static class ConfigImpl implements JAASIdentityAssertionProviderConfig {
      private String name = null;

      public ConfigImpl(AuthenticationProviderMBean providerMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.name = SecurityProviderConfigHelperImpl._getServiceName(providerMBean);
         serviceConfig.addDependency(this.name);
      }

      public String getAuthenticationProviderName() {
         return this.name;
      }
   }
}
