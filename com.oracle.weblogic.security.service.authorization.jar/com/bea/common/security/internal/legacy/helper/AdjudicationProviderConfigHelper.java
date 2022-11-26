package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.AdjudicatorProviderConfig;
import weblogic.management.security.authorization.AdjudicatorMBean;

class AdjudicationProviderConfigHelper {
   static String getServiceName(AdjudicatorMBean providerMBean) {
      return AdjudicationProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, AdjudicatorMBean providerMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBean), getImplName(), false);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      String adjudicationProviderName = SecurityProviderConfigHelperImpl._getServiceName(providerMBean);
      serviceConfig.addDependency(adjudicationProviderName);
      serviceConfig.setConfig(new ConfigImpl(adjudicationProviderName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static String getImplName() {
      return "com.bea.common.security.internal.legacy.service.AdjudicationProviderImpl";
   }

   private static class ConfigImpl implements AdjudicatorProviderConfig {
      private String adjudicationProviderName = null;

      public ConfigImpl(String adjudicationProviderName) {
         this.adjudicationProviderName = adjudicationProviderName;
      }

      public String getAdjudicationProviderName() {
         return this.adjudicationProviderName;
      }
   }
}
