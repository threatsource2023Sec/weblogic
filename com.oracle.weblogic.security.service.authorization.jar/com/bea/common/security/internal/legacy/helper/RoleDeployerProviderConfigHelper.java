package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.RoleDeployerProviderConfig;
import com.bea.common.security.internal.legacy.service.RoleDeployerProviderImpl;
import weblogic.management.security.authorization.DeployableRoleMapperMBean;

class RoleDeployerProviderConfigHelper {
   static String getServiceName(DeployableRoleMapperMBean providerMBean) {
      return RoleDeployerProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static String getLoggingName(DeployableRoleMapperMBean providerMBean) {
      return providerMBean.getRealm().getName() + "_" + providerMBean.getProviderClassName();
   }

   static String[] addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, DeployableRoleMapperMBean[] providerMBeans) {
      String[] faceServiceNames = new String[providerMBeans.length];
      boolean enableDeploySync = false;
      int timeout = 0;

      for(int i = 0; i < providerMBeans.length; ++i) {
         if (i == 0) {
            enableDeploySync = providerMBeans[i].getRealm().isDeployableProviderSynchronizationEnabled();
            timeout = providerMBeans[i].getRealm().getDeployableProviderSynchronizationTimeout();
         }

         faceServiceNames[i] = getServiceName(providerMBeans[i]);
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(faceServiceNames[i], RoleDeployerProviderImpl.class.getName(), false, getLoggingName(providerMBeans[i]));
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         String atzProviderName = SecurityProviderConfigHelperImpl._getServiceName(providerMBeans[i]);
         serviceConfig.addDependency(atzProviderName);
         serviceConfig.setConfig(new ConfigImpl(atzProviderName, !enableDeploySync, (long)timeout));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }

      return faceServiceNames;
   }

   private static class ConfigImpl implements RoleDeployerProviderConfig {
      private String name = null;
      private boolean supportParallel;
      private long timeout;

      public ConfigImpl(String atzProviderName, boolean parallel, long timeout) {
         this.name = atzProviderName;
         this.supportParallel = parallel;
         this.timeout = timeout;
      }

      public String getRoleProviderName() {
         return this.name;
      }

      public boolean isSupportParallelDeploy() {
         return this.supportParallel;
      }

      public long getDeployTimeout() {
         return this.timeout;
      }
   }
}
