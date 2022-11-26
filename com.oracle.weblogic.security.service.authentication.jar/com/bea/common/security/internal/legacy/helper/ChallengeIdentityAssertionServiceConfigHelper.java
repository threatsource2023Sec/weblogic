package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.ChallengeIdentityAssertionServiceImpl;
import com.bea.common.security.servicecfg.ChallengeIdentityAssertionServiceConfig;
import weblogic.management.security.RealmMBean;

class ChallengeIdentityAssertionServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return ChallengeIdentityAssertionServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, ChallengeIdentityAssertionServiceImpl.class.getName(), true);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
   }

   private static class ConfigImpl implements ChallengeIdentityAssertionServiceConfig {
      private String challengeIdentityAssertionTokenServiceName;
      private String identityAssertionCallbackServiceName;
      private String auditServiceName;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         this.challengeIdentityAssertionTokenServiceName = ChallengeIdentityAssertionTokenServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.challengeIdentityAssertionTokenServiceName);
         this.identityAssertionCallbackServiceName = IdentityAssertionCallbackServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.identityAssertionCallbackServiceName);
         this.auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.auditServiceName);
      }

      public String getChallengeIdentityAssertionTokenServiceName() {
         return this.challengeIdentityAssertionTokenServiceName;
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
