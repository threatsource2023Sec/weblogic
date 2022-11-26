package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.servicecfg.RoleMappingServiceConfig;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authorization.RoleMapperMBean;

class BulkRoleMappingServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return BulkRoleMappingServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static RoleMapperMBean[] getFaceMBeans(RealmMBean realmMBean) {
      return realmMBean.getRoleMappers();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      BulkRoleMappingProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, getImplName(), true);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      RoleMapperMBean[] providerMBeans = getFaceMBeans(realmMBean);
      String[] faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

      for(int i = 0; i < faceNames.length; ++i) {
         faceNames[i] = BulkRoleMappingProviderConfigHelper.getServiceName(providerMBeans[i]);
         serviceConfig.addDependency(faceNames[i]);
      }

      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      serviceConfig.setConfig(new ConfigImpl(faceNames, auditServiceName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static String getImplName() {
      return "com.bea.common.security.internal.service.BulkRoleMappingServiceImpl";
   }

   private static class ConfigImpl implements RoleMappingServiceConfig {
      private String[] faceNames = null;
      private String auditServiceName;

      public ConfigImpl(String[] faceNames, String auditServiceName) {
         this.faceNames = faceNames;
         this.auditServiceName = auditServiceName;
      }

      public String[] getRoleMapperNames() {
         return this.faceNames;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }
   }
}
