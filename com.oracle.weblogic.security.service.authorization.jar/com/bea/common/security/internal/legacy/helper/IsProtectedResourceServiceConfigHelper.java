package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.IsProtectedResourceServiceImpl;
import com.bea.common.security.servicecfg.IsProtectedResourceServiceConfig;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authorization.AuthorizerMBean;

class IsProtectedResourceServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return IsProtectedResourceServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName, boolean addedADServices) {
      AuthorizerMBean[] providerMBeans = realmMBean.getAuthorizers();
      String[] faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

      for(int i = 0; i < faceNames.length; ++i) {
         faceNames[i] = AccessDecisionProviderConfigHelper.getServiceName(providerMBeans[i]);
      }

      if (!addedADServices) {
         AccessDecisionProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBeans);
      }

      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, IsProtectedResourceServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);

      for(int i = 0; i < faceNames.length; ++i) {
         serviceConfig.addDependency(faceNames[i]);
      }

      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      serviceConfig.setConfig(new ConfigImpl(faceNames, auditServiceName));
   }

   private static class ConfigImpl implements IsProtectedResourceServiceConfig {
      private String[] faceNames = null;
      private String auditServiceName;

      public ConfigImpl(String[] faceNames, String auditServiceName) {
         this.faceNames = faceNames;
         this.auditServiceName = auditServiceName;
      }

      public String[] getAccessDecisionProviderNames() {
         return this.faceNames;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }
   }
}
