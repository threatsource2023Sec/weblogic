package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.RoleConsumerProviderConfig;
import com.bea.common.security.internal.legacy.service.RoleConsumerProviderImpl;
import weblogic.management.security.authorization.RoleMapperMBean;

class RoleConsumerProviderConfigHelper {
   static String getServiceName(RoleMapperMBean providerMBean) {
      return RoleConsumerProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static String getLoggingName(RoleMapperMBean providerMBean) {
      return providerMBean.getRealm().getName() + "_" + providerMBean.getProviderClassName();
   }

   static String[] addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RoleMapperMBean[] providerMBeans) {
      String[] faceServiceNames = new String[providerMBeans.length];

      for(int i = 0; i < providerMBeans.length; ++i) {
         faceServiceNames[i] = getServiceName(providerMBeans[i]);
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(faceServiceNames[i], RoleConsumerProviderImpl.class.getName(), false, getLoggingName(providerMBeans[i]));
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         String atzProviderName = SecurityProviderConfigHelperImpl._getServiceName(providerMBeans[i]);
         serviceConfig.addDependency(atzProviderName);
         serviceConfig.setConfig(new ConfigImpl(atzProviderName));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }

      return faceServiceNames;
   }

   private static class ConfigImpl implements RoleConsumerProviderConfig {
      private String atzProviderName = null;

      public ConfigImpl(String atzProviderName) {
         this.atzProviderName = atzProviderName;
      }

      public String getRoleConsumerProviderName() {
         return this.atzProviderName;
      }
   }
}
