package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.JAASAuthenticationProviderConfig;
import com.bea.common.security.internal.legacy.service.JAASAuthenticationProviderImpl;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.UserPasswordEditorMBean;

class JAASAuthenticationProviderConfigHelper {
   static String getServiceName(AuthenticationProviderMBean providerMBean) {
      return JAASAuthenticationProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, AuthenticationProviderMBean[] providerMBeans) {
      boolean isPasswordValidationServiceExist = false;

      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBeans[i]), JAASAuthenticationProviderImpl.class.getName(), false);
         serviceConfig.setConfig(new ConfigImpl(providerMBeans[i], serviceConfig));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
         if (providerMBeans[i] instanceof UserPasswordEditorMBean) {
            if (!isPasswordValidationServiceExist) {
               PasswordValidationServiceConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBeans[i].getRealm());
               isPasswordValidationServiceExist = true;
            }

            serviceConfig.addDependency(PasswordValidationServiceConfigHelper.getServiceName(providerMBeans[i].getRealm()));
         }
      }

   }

   private static class ConfigImpl implements JAASAuthenticationProviderConfig {
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
