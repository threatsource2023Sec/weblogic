package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.AccessDecisionProviderConfig;
import weblogic.management.security.authorization.AuthorizerMBean;

class AccessDecisionProviderConfigHelper {
   static String getServiceName(AuthorizerMBean providerMBean) {
      return AccessDecisionProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, AuthorizerMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBeans[i]), getImplName(), false);
         serviceConfig.setConfig(new ConfigImpl(providerMBeans[i], serviceConfig));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }

   }

   private static String getImplName() {
      return "com.bea.common.security.internal.legacy.service.AccessDecisionProviderImpl";
   }

   private static class ConfigImpl implements AccessDecisionProviderConfig {
      private String name = null;

      public ConfigImpl(AuthorizerMBean providerMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.name = SecurityProviderConfigHelperImpl._getServiceName(providerMBean);
         serviceConfig.addDependency(this.name);
      }

      public String getAuthorizationProviderName() {
         return this.name;
      }
   }
}
