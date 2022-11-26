package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.LDAPSSLSocketFactoryLookupServiceImpl;
import com.bea.common.security.servicecfg.LDAPSSLSocketFactoryLookupServiceConfig;
import javax.net.ssl.SSLContext;
import weblogic.management.security.RealmMBean;

class LDAPSSLSocketFactoryLookupServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return LDAPSSLSocketFactoryLookupServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName, SSLContext sslContext) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, LDAPSSLSocketFactoryLookupServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      serviceConfig.setConfig(new ConfigImpl(sslContext));
   }

   private static class ConfigImpl implements LDAPSSLSocketFactoryLookupServiceConfig {
      private SSLContext sslContext;

      public ConfigImpl(SSLContext sslContext) {
         this.sslContext = sslContext;
      }

      public SSLContext getSSLContext() {
         return this.sslContext;
      }
   }
}
