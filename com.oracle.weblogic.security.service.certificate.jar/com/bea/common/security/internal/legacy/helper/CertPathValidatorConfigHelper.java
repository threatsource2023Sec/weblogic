package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.CertPathValidatorConfig;
import com.bea.common.security.internal.legacy.service.CertPathValidatorImpl;
import weblogic.management.security.pk.CertPathValidatorMBean;

class CertPathValidatorConfigHelper {
   static String getServiceName(CertPathValidatorMBean providerMBean) {
      return CertPathValidatorConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, CertPathValidatorMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBeans[i]), CertPathValidatorImpl.class.getName(), false);
         String validatorName = SecurityProviderConfigHelperImpl._getServiceName(providerMBeans[i]);
         serviceConfig.addDependency(validatorName);
         serviceConfig.setConfig(new ConfigImpl(validatorName));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      }

   }

   private static class ConfigImpl implements CertPathValidatorConfig {
      private String validatorName = null;

      public ConfigImpl(String validatorName) {
         this.validatorName = validatorName;
      }

      public String getCertPathProviderName() {
         return this.validatorName;
      }
   }
}
