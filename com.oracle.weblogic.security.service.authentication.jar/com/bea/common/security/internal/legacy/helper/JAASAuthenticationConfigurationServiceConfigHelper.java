package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.JAASAuthenticationConfigurationServiceImpl;
import com.bea.common.security.servicecfg.JAASAuthenticationConfigurationServiceConfig;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;

class JAASAuthenticationConfigurationServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return JAASAuthenticationConfigurationServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static AuthenticationProviderMBean[] getFaceMBeans(RealmMBean realmMBean) {
      return realmMBean.getAuthenticationProviders();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      JAASAuthenticationProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, JAASAuthenticationConfigurationServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig));
   }

   private static class ConfigImpl implements JAASAuthenticationConfigurationServiceConfig {
      private String[] faceNames;
      private boolean isDefault;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         AuthenticationProviderMBean[] providerMBeans = JAASAuthenticationConfigurationServiceConfigHelper.getFaceMBeans(realmMBean);
         this.faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

         for(int i = 0; i < this.faceNames.length; ++i) {
            this.faceNames[i] = JAASAuthenticationProviderConfigHelper.getServiceName(providerMBeans[i]);
            serviceConfig.addDependency(this.faceNames[i]);
         }

         this.isDefault = realmMBean.isDefaultRealm();
      }

      public String[] getJAASAuthenticationProviderNames() {
         return this.faceNames;
      }

      public boolean isOracleDefaultLoginConfiguration() {
         return this.isDefault;
      }

      // $FF: synthetic method
      ConfigImpl(RealmMBean x0, ServiceEngineManagedServiceConfig x1, Object x2) {
         this(x0, x1);
      }
   }
}
