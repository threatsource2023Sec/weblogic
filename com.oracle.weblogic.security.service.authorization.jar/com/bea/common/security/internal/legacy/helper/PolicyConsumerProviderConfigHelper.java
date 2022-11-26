package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.PolicyConsumerProviderConfig;
import com.bea.common.security.internal.legacy.service.PolicyConsumerProviderImpl;
import weblogic.management.security.authorization.AuthorizerMBean;

class PolicyConsumerProviderConfigHelper {
   static String getServiceName(AuthorizerMBean providerMBean) {
      return PolicyConsumerProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static String getLoggingName(AuthorizerMBean providerMBean) {
      return providerMBean.getRealm().getName() + "_" + providerMBean.getProviderClassName();
   }

   static String[] addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, AuthorizerMBean[] providerMBeans) {
      String[] faceServiceNames = new String[providerMBeans.length];

      for(int i = 0; i < providerMBeans.length; ++i) {
         faceServiceNames[i] = getServiceName(providerMBeans[i]);
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(faceServiceNames[i], PolicyConsumerProviderImpl.class.getName(), false, getLoggingName(providerMBeans[i]));
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         String atzProviderName = SecurityProviderConfigHelperImpl._getServiceName(providerMBeans[i]);
         serviceConfig.addDependency(atzProviderName);
         serviceConfig.setConfig(new ConfigImpl(atzProviderName));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }

      return faceServiceNames;
   }

   private static class ConfigImpl implements PolicyConsumerProviderConfig {
      private String atzProviderName = null;

      public ConfigImpl(String atzProviderName) {
         this.atzProviderName = atzProviderName;
      }

      public String getPolicyConsumerProviderName() {
         return this.atzProviderName;
      }
   }
}
