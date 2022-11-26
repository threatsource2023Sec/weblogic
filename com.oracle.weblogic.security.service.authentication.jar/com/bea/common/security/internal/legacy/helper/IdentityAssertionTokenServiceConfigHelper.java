package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.IdentityAssertionTokenServiceImpl;
import com.bea.common.security.servicecfg.IdentityAssertionTokenServiceConfig;
import java.util.Vector;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.IdentityAsserterMBean;

class IdentityAssertionTokenServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return IdentityAssertionTokenServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
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
      IdentityAsserterV2ConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, IdentityAssertionTokenServiceImpl.class.getName(), true);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static class IAConfigImpl implements IdentityAssertionTokenServiceConfig.IdentityAsserterV2Config {
      private String name;
      private String[] activeTypes;

      private IAConfigImpl(IdentityAsserterMBean providerMBean) {
         this.name = IdentityAsserterV2ConfigHelper.getServiceName(providerMBean);
         this.activeTypes = providerMBean.getActiveTypes();
      }

      public String getIdentityAsserterV2Name() {
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

   private static class ConfigImpl implements IdentityAssertionTokenServiceConfig {
      private IdentityAssertionTokenServiceConfig.IdentityAsserterV2Config[] iaConfigs;
      private String auditServiceName;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         IdentityAsserterMBean[] providerMBeans = IdentityAssertionTokenServiceConfigHelper.getFaceMBeans(realmMBean);
         this.iaConfigs = new IAConfigImpl[providerMBeans != null ? providerMBeans.length : 0];

         for(int i = 0; i < this.iaConfigs.length; ++i) {
            this.iaConfigs[i] = new IAConfigImpl(providerMBeans[i]);
            serviceConfig.addDependency(this.iaConfigs[i].getIdentityAsserterV2Name());
         }

         this.auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.auditServiceName);
      }

      public IdentityAssertionTokenServiceConfig.IdentityAsserterV2Config[] getIdentityAsserterV2Configs() {
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
