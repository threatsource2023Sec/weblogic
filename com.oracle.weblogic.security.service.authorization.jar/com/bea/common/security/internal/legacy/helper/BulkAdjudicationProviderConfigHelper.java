package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.AdjudicatorProviderConfig;
import weblogic.management.security.authorization.AdjudicatorMBean;

class BulkAdjudicationProviderConfigHelper {
   static String getServiceName(AdjudicatorMBean providerMBean) {
      return BulkAdjudicationProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, AdjudicatorMBean providerMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBean), getImplName(), false);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      String adjudicatingProviderName = SecurityProviderConfigHelperImpl._getServiceName(providerMBean);
      serviceConfig.addDependency(adjudicatingProviderName);
      serviceConfig.setConfig(new ConfigImpl(adjudicatingProviderName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static String getImplName() {
      return "com.bea.common.security.internal.legacy.service.BulkAdjudicationProviderImpl";
   }

   private static class ConfigImpl implements AdjudicatorProviderConfig {
      private String adjudicatingProviderName = null;

      public ConfigImpl(String adjudicatingProviderName) {
         this.adjudicatingProviderName = adjudicatingProviderName;
      }

      public String getAdjudicationProviderName() {
         return this.adjudicatingProviderName;
      }
   }
}
