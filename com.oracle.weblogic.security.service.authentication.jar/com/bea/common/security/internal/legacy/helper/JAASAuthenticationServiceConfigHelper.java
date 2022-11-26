package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.JAASAuthenticationServiceImpl;
import com.bea.common.security.servicecfg.JAASAuthenticationServiceConfig;
import weblogic.management.security.RealmMBean;

class JAASAuthenticationServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return JAASAuthenticationServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, JAASAuthenticationServiceImpl.class.getName(), true);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static class ConfigImpl implements JAASAuthenticationServiceConfig {
      private String auditServiceName;
      private String jaasAuthenticationConfigurationServiceName;
      private String jaasLoginServiceName;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.jaasAuthenticationConfigurationServiceName = JAASAuthenticationConfigurationServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.jaasAuthenticationConfigurationServiceName);
         this.jaasLoginServiceName = JAASLoginServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.jaasLoginServiceName);
         this.auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.auditServiceName);
      }

      public String getJAASAuthenticationConfigurationServiceName() {
         return this.jaasAuthenticationConfigurationServiceName;
      }

      public String getJAASLoginServiceName() {
         return this.jaasLoginServiceName;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      // $FF: synthetic method
      ConfigImpl(RealmMBean x0, ServiceEngineManagedServiceConfig x1, Object x2) {
         this(x0, x1);
      }
   }
}
