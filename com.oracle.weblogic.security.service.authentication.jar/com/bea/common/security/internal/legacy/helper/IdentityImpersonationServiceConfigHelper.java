package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.IdentityImpersonationServiceImpl;
import com.bea.common.security.servicecfg.IdentityImpersonationServiceConfig;
import weblogic.management.security.RealmMBean;

class IdentityImpersonationServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return IdentityImpersonationServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, IdentityImpersonationServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig));
   }

   private static class ConfigImpl implements IdentityImpersonationServiceConfig {
      private String auditServiceName;
      private String identityServiceName;
      private String identityCacheServiceName;
      private String jaasIdentityAssertionConfigurationServiceName;
      private String jaasLoginServiceName;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.auditServiceName);
         this.identityServiceName = IdentityServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.identityServiceName);
         this.identityCacheServiceName = IdentityCacheServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.identityCacheServiceName);
         this.jaasIdentityAssertionConfigurationServiceName = JAASIdentityAssertionConfigurationServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.jaasIdentityAssertionConfigurationServiceName);
         this.jaasLoginServiceName = JAASLoginServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.jaasLoginServiceName);
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      public String getIdentityServiceName() {
         return this.identityServiceName;
      }

      public String getIdentityCacheServiceName() {
         return this.identityCacheServiceName;
      }

      public String getJAASIdentityAssertionConfigurationServiceName() {
         return this.jaasIdentityAssertionConfigurationServiceName;
      }

      public String getJAASLoginServiceName() {
         return this.jaasLoginServiceName;
      }

      // $FF: synthetic method
      ConfigImpl(RealmMBean x0, ServiceEngineManagedServiceConfig x1, Object x2) {
         this(x0, x1);
      }
   }
}
