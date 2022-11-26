package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.servicecfg.AuthorizationServiceConfig;
import weblogic.management.security.RealmMBean;

class BulkAuthorizationServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return BulkAuthorizationServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, getImplName(), true);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      String accessDecisionServiceName = BulkAccessDecisionServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(accessDecisionServiceName);
      String adjudicationServiceName = BulkAdjudicationServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(adjudicationServiceName);
      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      serviceConfig.setConfig(new ConfigImpl(accessDecisionServiceName, adjudicationServiceName, auditServiceName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static String getImplName() {
      return "com.bea.common.security.internal.service.BulkAuthorizationServiceImpl";
   }

   private static class ConfigImpl implements AuthorizationServiceConfig {
      private String accessDecisionServiceName;
      private String adjudicationServiceName;
      private String auditServiceName;

      public ConfigImpl(String accessDecisionServiceName, String adjudicationServiceName, String auditServiceName) {
         this.accessDecisionServiceName = accessDecisionServiceName;
         this.adjudicationServiceName = adjudicationServiceName;
         this.auditServiceName = auditServiceName;
      }

      public String getAccessDecisionServiceName() {
         return this.accessDecisionServiceName;
      }

      public String getAdjudicationServiceName() {
         return this.adjudicationServiceName;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }
   }
}
