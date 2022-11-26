package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.CredentialMappingServiceImpl;
import com.bea.common.security.servicecfg.CredentialMappingServiceConfig;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;

class CredentialMappingServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return CredentialMappingServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static CredentialMapperMBean[] getFaceMBeans(RealmMBean realmMBean) {
      return realmMBean.getCredentialMappers();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      CredentialMapperMBean[] providerMBeans = getFaceMBeans(realmMBean);
      String[] faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

      for(int i = 0; i < faceNames.length; ++i) {
         faceNames[i] = CredentialMapperConfigHelper.getServiceName(providerMBeans[i]);
      }

      CredentialMapperConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBeans);
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, CredentialMappingServiceImpl.class.getName(), true);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);

      for(int i = 0; i < faceNames.length; ++i) {
         serviceConfig.addDependency(faceNames[i]);
      }

      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      serviceConfig.setConfig(new ConfigImpl(faceNames, auditServiceName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static class ConfigImpl implements CredentialMappingServiceConfig {
      private String[] faceNames = null;
      private String auditServiceName;

      public ConfigImpl(String[] faceNames, String auditServiceName) {
         this.faceNames = faceNames;
         this.auditServiceName = auditServiceName;
      }

      public String[] getCredentialMapperNames() {
         return this.faceNames;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }
   }
}
