package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.SecurityTokenServiceImpl;
import com.bea.common.security.servicecfg.SecurityTokenServiceConfig;
import weblogic.management.security.RealmMBean;

class SecurityTokenServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return SecurityTokenServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, SecurityTokenServiceImpl.class.getName(), true);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      String credMapServiceName = CredentialMappingServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(credMapServiceName);
      serviceConfig.setConfig(new ConfigImpl(auditServiceName, credMapServiceName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static class ConfigImpl implements SecurityTokenServiceConfig {
      private String auditServiceName;
      private String credMapServiceName;

      public ConfigImpl(String auditServiceName, String credMapServiceName) {
         this.auditServiceName = auditServiceName;
         this.credMapServiceName = credMapServiceName;
      }

      public String getCredentialMappingServiceName() {
         return this.credMapServiceName;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }
   }
}
