package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.PrincipalValidationProviderConfig;
import com.bea.common.security.internal.legacy.service.PrincipalValidationProviderImpl;
import weblogic.management.security.authentication.AuthenticationProviderMBean;

class PrincipalValidationProviderConfigHelper {
   static String getServiceName(AuthenticationProviderMBean providerMBean) {
      return PrincipalValidationProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static String[] addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, AuthenticationProviderMBean[] providerMBeans) {
      String[] providerServiceNames = new String[providerMBeans == null ? 0 : providerMBeans.length];

      for(int i = 0; i < providerServiceNames.length; ++i) {
         providerServiceNames[i] = getServiceName(providerMBeans[i]);
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(providerServiceNames[i], PrincipalValidationProviderImpl.class.getName(), false);
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         String atnProviderName = SecurityProviderConfigHelperImpl._getServiceName(providerMBeans[i]);
         serviceConfig.addDependency(atnProviderName);
         serviceConfig.setConfig(new ConfigImpl(atnProviderName));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }

      return providerServiceNames;
   }

   private static class ConfigImpl implements PrincipalValidationProviderConfig {
      private String atnProviderName;

      public ConfigImpl(String atnProviderName) {
         this.atnProviderName = atnProviderName;
      }

      public String getAuthenticationProviderName() {
         return this.atnProviderName;
      }
   }
}
