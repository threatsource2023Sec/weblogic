package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.RoleMappingProviderConfig;
import weblogic.management.security.authorization.RoleMapperMBean;

class BulkRoleMappingProviderConfigHelper {
   static String getServiceName(RoleMapperMBean providerMBean) {
      return BulkRoleMappingProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RoleMapperMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBeans[i]), getImplName(), false);
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         String roleMappingProviderName = SecurityProviderConfigHelperImpl._getServiceName(providerMBeans[i]);
         serviceConfig.addDependency(roleMappingProviderName);
         serviceConfig.setConfig(new ConfigImpl(roleMappingProviderName));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }

   }

   private static String getImplName() {
      return "com.bea.common.security.internal.legacy.service.BulkRoleMappingProviderImpl";
   }

   private static class ConfigImpl implements RoleMappingProviderConfig {
      private String roleMappingProviderName = null;

      public ConfigImpl(String roleMappingProviderName) {
         this.roleMappingProviderName = roleMappingProviderName;
      }

      public String getRoleMappingProviderName() {
         return this.roleMappingProviderName;
      }
   }
}
