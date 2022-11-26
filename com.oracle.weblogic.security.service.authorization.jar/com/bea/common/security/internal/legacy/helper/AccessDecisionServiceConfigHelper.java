package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.servicecfg.AccessDecisionServiceConfig;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authorization.AuthorizerMBean;

class AccessDecisionServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return AccessDecisionServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static AuthorizerMBean[] getFaceMBeans(RealmMBean realmMBean) {
      return realmMBean.getAuthorizers();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      AccessDecisionProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, getImplName(), true);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      AuthorizerMBean[] providerMBeans = getFaceMBeans(realmMBean);
      String[] faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];
      int top = providerMBeans != null ? providerMBeans.length : 0;

      for(int i = 0; i < top; ++i) {
         faceNames[i] = AccessDecisionProviderConfigHelper.getServiceName(providerMBeans[i]);
         serviceConfig.addDependency(faceNames[i]);
      }

      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      serviceConfig.setConfig(new ConfigImpl(faceNames, auditServiceName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static String getImplName() {
      return "com.bea.common.security.internal.service.AccessDecisionServiceImpl";
   }

   private static class ConfigImpl implements AccessDecisionServiceConfig {
      private String[] faceNames = null;
      private String auditServiceName;

      public ConfigImpl(String[] faceNames, String auditServiceName) {
         this.faceNames = faceNames;
         this.auditServiceName = auditServiceName;
      }

      public String[] getAccessDecisionNames() {
         return this.faceNames;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }
   }
}
