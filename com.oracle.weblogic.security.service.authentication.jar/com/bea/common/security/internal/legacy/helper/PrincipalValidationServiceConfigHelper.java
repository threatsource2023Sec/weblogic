package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.PrincipalValidationServiceImpl;
import com.bea.common.security.servicecfg.PrincipalValidationServiceConfig;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;

class PrincipalValidationServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return PrincipalValidationServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      AuthenticationProviderMBean[] providerMBeans = realmMBean.getAuthenticationProviders();
      String[] faceNames = PrincipalValidationProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBeans);
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, PrincipalValidationServiceImpl.class.getName(), true);

      for(int i = 0; i < faceNames.length; ++i) {
         serviceConfig.addDependency(faceNames[i]);
      }

      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      serviceConfig.setConfig(new ConfigImpl(faceNames, auditServiceName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   static class ConfigImpl implements PrincipalValidationServiceConfig {
      private String[] faceNames;
      private String auditServiceName;

      public ConfigImpl(String[] faceNames, String auditServiceName) {
         this.faceNames = faceNames;
         this.auditServiceName = auditServiceName;
      }

      public String[] getPrincipalValidationProviderNames() {
         return this.faceNames;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }
   }
}
