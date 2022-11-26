package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.NegotiateIdentityAsserterServiceImpl;
import com.bea.common.security.servicecfg.NegotiateIdentityAsserterServiceConfig;
import weblogic.management.security.RealmMBean;

class NegotiateIdentityAsserterServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return NegotiateIdentityAsserterServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serverName) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serverName, NegotiateIdentityAsserterServiceImpl.class.getName(), true);
      String sessionServiceName = SessionServiceConfigHelper.getServiceName(realmMBean);
      String ciaServiceName = ChallengeIdentityAssertionServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.setConfig(new NegotiateIdentityAsserterServiceConfigImpl(sessionServiceName, ciaServiceName));
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      serviceConfig.addDependency(sessionServiceName);
      serviceConfig.addDependency(ciaServiceName);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static class NegotiateIdentityAsserterServiceConfigImpl implements NegotiateIdentityAsserterServiceConfig {
      String sessionServiceName;
      String ciaServiceName;

      public NegotiateIdentityAsserterServiceConfigImpl(String sessionServiceName, String ciaServiceName) {
         this.sessionServiceName = sessionServiceName;
         this.ciaServiceName = ciaServiceName;
      }

      public String getSessionServiceName() {
         return this.sessionServiceName;
      }

      public String getChallengeIdentityAssertionServiceName() {
         return this.ciaServiceName;
      }
   }
}
