package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.PolicyDeployerProviderConfig;
import com.bea.common.security.internal.legacy.service.PolicyDeployerProviderImpl;
import weblogic.management.security.authorization.DeployableAuthorizerMBean;

class PolicyDeployerProviderConfigHelper {
   static String getServiceName(DeployableAuthorizerMBean providerMBean) {
      return PolicyDeployerProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static String getLoggingName(DeployableAuthorizerMBean providerMBean) {
      return providerMBean.getRealm().getName() + "_" + providerMBean.getProviderClassName();
   }

   static String[] addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, DeployableAuthorizerMBean[] providerMBeans) {
      String[] faceServiceNames = new String[providerMBeans.length];
      boolean enableDeploySync = false;
      int timeout = 0;

      for(int i = 0; i < providerMBeans.length; ++i) {
         if (i == 0) {
            enableDeploySync = providerMBeans[i].getRealm().isDeployableProviderSynchronizationEnabled();
            timeout = providerMBeans[i].getRealm().getDeployableProviderSynchronizationTimeout();
         }

         faceServiceNames[i] = getServiceName(providerMBeans[i]);
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(faceServiceNames[i], PolicyDeployerProviderImpl.class.getName(), false, getLoggingName(providerMBeans[i]));
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         String atzProviderName = SecurityProviderConfigHelperImpl._getServiceName(providerMBeans[i]);
         serviceConfig.addDependency(atzProviderName);
         serviceConfig.setConfig(new ConfigImpl(atzProviderName, !enableDeploySync, (long)timeout));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }

      return faceServiceNames;
   }

   private static class ConfigImpl implements PolicyDeployerProviderConfig {
      private String name = null;
      private boolean supportParallel;
      private long timeout;

      public ConfigImpl(String atzProviderName, boolean parallel, long timeout) {
         this.name = atzProviderName;
         this.supportParallel = parallel;
         this.timeout = timeout;
      }

      public String getAuthorizationProviderName() {
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
