package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.servicecfg.AdjudicationServiceConfig;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authorization.AdjudicatorMBean;

class BulkAdjudicationServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return BulkAdjudicationServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      AdjudicatorMBean providerMBean = realmMBean.getAdjudicator();
      if (providerMBean != null) {
         BulkAdjudicationProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBean);
      }

      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, getImplName(), true);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      String faceName = null;
      if (providerMBean != null) {
         faceName = BulkAdjudicationProviderConfigHelper.getServiceName(providerMBean);
         serviceConfig.addDependency(faceName);
      }

      String accessDecisionServiceName = BulkAccessDecisionServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(accessDecisionServiceName);
      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      serviceConfig.setConfig(new ConfigImpl(faceName, accessDecisionServiceName, auditServiceName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static String getImplName() {
      return "com.bea.common.security.internal.service.BulkAdjudicationServiceImpl";
   }

   private static class ConfigImpl implements AdjudicationServiceConfig {
      private String faceName = null;
      private String auditServiceName;
      private String accessDecisionServiceName;

      public ConfigImpl(String faceName, String accessDecisionServiceName, String auditServiceName) {
         this.faceName = faceName;
         this.accessDecisionServiceName = accessDecisionServiceName;
         this.auditServiceName = auditServiceName;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      public String getAdjudicatorV2Name() {
         return this.faceName;
      }

      public String getAccessDecisionServiceName() {
         return this.accessDecisionServiceName;
      }
   }
}
