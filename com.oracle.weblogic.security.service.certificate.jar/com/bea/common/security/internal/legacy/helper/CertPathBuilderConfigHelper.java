package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.CertPathBuilderConfig;
import com.bea.common.security.internal.legacy.service.CertPathBuilderImpl;
import weblogic.management.security.pk.CertPathBuilderMBean;

class CertPathBuilderConfigHelper {
   static String getServiceName(CertPathBuilderMBean providerMBean) {
      return CertPathBuilderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, CertPathBuilderMBean providerMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBean), CertPathBuilderImpl.class.getName(), false);
      String builderName = SecurityProviderConfigHelperImpl._getServiceName(providerMBean);
      serviceConfig.addDependency(builderName);
      serviceConfig.setConfig(new ConfigImpl(builderName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
   }

   private static class ConfigImpl implements CertPathBuilderConfig {
      private String builderName = null;

      public ConfigImpl(String builderName) {
         this.builderName = builderName;
      }

      public String getCertPathProviderName() {
         return this.builderName;
      }
   }
}
