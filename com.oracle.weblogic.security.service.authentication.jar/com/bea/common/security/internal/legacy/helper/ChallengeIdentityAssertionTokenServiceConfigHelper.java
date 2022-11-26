package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.ChallengeIdentityAssertionTokenServiceImpl;
import com.bea.common.security.servicecfg.ChallengeIdentityAssertionTokenServiceConfig;
import java.util.Vector;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.IdentityAsserterMBean;

class ChallengeIdentityAssertionTokenServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return ChallengeIdentityAssertionTokenServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static IdentityAsserterMBean[] getFaceMBeans(RealmMBean realmMBean) {
      Vector providers = new Vector();
      AuthenticationProviderMBean[] providerMBeans = realmMBean.getAuthenticationProviders();

      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         if (providerMBeans[i] instanceof IdentityAsserterMBean) {
            providers.add(providerMBeans[i]);
         }
      }

      return (IdentityAsserterMBean[])((IdentityAsserterMBean[])providers.toArray(new IdentityAsserterMBean[providers.size()]));
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      ChallengeIdentityAssertionProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, ChallengeIdentityAssertionTokenServiceImpl.class.getName(), true);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
   }

   private static class IAConfigImpl implements ChallengeIdentityAssertionTokenServiceConfig.ChallengeIdentityAssertionProviderConfig {
      private String name;
      private String[] activeTypes;

      private IAConfigImpl(IdentityAsserterMBean providerMBean) {
         this.name = ChallengeIdentityAssertionProviderConfigHelper.getServiceName(providerMBean);
         this.activeTypes = providerMBean.getActiveTypes();
      }

      public String getChallengeIdentityAssertionProviderName() {
         return this.name;
      }

      public String[] getActiveTypes() {
         return this.activeTypes;
      }

      // $FF: synthetic method
      IAConfigImpl(IdentityAsserterMBean x0, Object x1) {
         this(x0);
      }
   }

   private static class ConfigImpl implements ChallengeIdentityAssertionTokenServiceConfig {
      private ChallengeIdentityAssertionTokenServiceConfig.ChallengeIdentityAssertionProviderConfig[] iaConfigs;
      private String auditServiceName;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         IdentityAsserterMBean[] providerMBeans = ChallengeIdentityAssertionTokenServiceConfigHelper.getFaceMBeans(realmMBean);
         this.iaConfigs = new IAConfigImpl[providerMBeans != null ? providerMBeans.length : 0];

         for(int i = 0; i < this.iaConfigs.length; ++i) {
            this.iaConfigs[i] = new IAConfigImpl(providerMBeans[i]);
            serviceConfig.addDependency(this.iaConfigs[i].getChallengeIdentityAssertionProviderName());
         }

         this.auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.auditServiceName);
      }

      public ChallengeIdentityAssertionTokenServiceConfig.ChallengeIdentityAssertionProviderConfig[] getChallengeIdentityAssertionProviderConfigs() {
         return this.iaConfigs;
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
