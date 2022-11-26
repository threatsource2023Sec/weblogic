package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.JAASIdentityAssertionConfigurationServiceImpl;
import com.bea.common.security.servicecfg.JAASIdentityAssertionConfigurationServiceConfig;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;

class JAASIdentityAssertionConfigurationServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return JAASIdentityAssertionConfigurationServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static AuthenticationProviderMBean[] getFaceMBeans(RealmMBean realmMBean) {
      return realmMBean.getAuthenticationProviders();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      JAASIdentityAssertionProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, JAASIdentityAssertionConfigurationServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig));
   }

   private static class ConfigImpl implements JAASIdentityAssertionConfigurationServiceConfig {
      private String[] faceNames;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         AuthenticationProviderMBean[] providerMBeans = JAASIdentityAssertionConfigurationServiceConfigHelper.getFaceMBeans(realmMBean);
         this.faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

         for(int i = 0; i < this.faceNames.length; ++i) {
            this.faceNames[i] = JAASIdentityAssertionProviderConfigHelper.getServiceName(providerMBeans[i]);
            serviceConfig.addDependency(this.faceNames[i]);
         }

      }

      public String[] getJAASIdentityAssertionProviderNames() {
         return this.faceNames;
      }

      // $FF: synthetic method
      ConfigImpl(RealmMBean x0, ServiceEngineManagedServiceConfig x1, Object x2) {
         this(x0, x1);
      }
   }
}
