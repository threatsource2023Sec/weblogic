package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.IdentityAsserterV2Config;
import com.bea.common.security.internal.legacy.service.IdentityAsserterV2Impl;
import weblogic.management.security.authentication.IdentityAsserterMBean;

class IdentityAsserterV2ConfigHelper {
   static String getServiceName(IdentityAsserterMBean providerMBean) {
      return IdentityAsserterV2ConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, IdentityAsserterMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBeans[i]), IdentityAsserterV2Impl.class.getName(), false);
         serviceConfig.setConfig(new ConfigImpl(providerMBeans[i], serviceConfig));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }

   }

   private static class ConfigImpl implements IdentityAsserterV2Config {
      private String name = null;

      public ConfigImpl(IdentityAsserterMBean providerMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.name = SecurityProviderConfigHelperImpl._getServiceName(providerMBean);
         serviceConfig.addDependency(this.name);
      }

      public String getAuthenticationProviderName() {
         return this.name;
      }
   }
}
