package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.IdentityAssertionServiceImpl;
import com.bea.common.security.servicecfg.IdentityAssertionServiceConfig;
import weblogic.management.security.RealmMBean;

class IdentityAssertionServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return IdentityAssertionServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, IdentityAssertionServiceImpl.class.getName(), true);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static class ConfigImpl implements IdentityAssertionServiceConfig {
      private String identityAssertionTokenServiceName;
      private String identityAssertionCallbackServiceName;
      private String auditServiceName;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.identityAssertionTokenServiceName = IdentityAssertionTokenServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.identityAssertionTokenServiceName);
         this.identityAssertionCallbackServiceName = IdentityAssertionCallbackServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.identityAssertionCallbackServiceName);
         this.auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.auditServiceName);
      }

      public String getIdentityAssertionTokenServiceName() {
         return this.identityAssertionTokenServiceName;
      }

      public String getIdentityAssertionCallbackServiceName() {
         return this.identityAssertionCallbackServiceName;
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
