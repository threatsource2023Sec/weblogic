package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.legacy.service.AuditChannelConfig;
import com.bea.common.security.internal.legacy.service.AuditChannelImpl;
import weblogic.management.security.audit.AuditorMBean;

class AuditChannelConfigHelper {
   static String getServiceName(AuditorMBean providerMBean) {
      return AuditChannelConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, AuditorMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBeans[i]), AuditChannelImpl.class.getName(), false);
         serviceConfig.setConfig(new ConfigImpl(providerMBeans[i], serviceConfig));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }

   }

   private static class ConfigImpl implements AuditChannelConfig {
      private String name = null;

      public ConfigImpl(AuditorMBean providerMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.name = SecurityProviderConfigHelperImpl._getServiceName(providerMBean);
         serviceConfig.addDependency(this.name);
      }

      public String getAuditProviderName() {
         return this.name;
      }
   }
}
