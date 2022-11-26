package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.AuditServiceImpl;
import com.bea.common.security.servicecfg.AuditServiceConfig;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.audit.AuditorMBean;

class AuditServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return AuditServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static AuditorMBean[] getFaceMBeans(RealmMBean realmMBean) {
      return realmMBean.getAuditors();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      AuditorMBean[] providerMBeans = getFaceMBeans(realmMBean);
      String[] faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

      for(int i = 0; i < faceNames.length; ++i) {
         faceNames[i] = AuditChannelConfigHelper.getServiceName(providerMBeans[i]);
      }

      AuditChannelConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBeans);
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, AuditServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);

      for(int i = 0; i < faceNames.length; ++i) {
         serviceConfig.addDependency(faceNames[i]);
      }

      serviceConfig.setConfig(new ConfigImpl(faceNames));
   }

   private static class ConfigImpl implements AuditServiceConfig {
      private String[] faceNames = null;

      public ConfigImpl(String[] faceNames) {
         this.faceNames = faceNames;
      }

      public String[] getAuditChannelNames() {
         return this.faceNames;
      }
   }
}
