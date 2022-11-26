package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.BootStrapServiceImpl;
import com.bea.common.security.servicecfg.BootStrapServiceConfig;
import java.util.Properties;
import weblogic.management.security.RealmMBean;

class BootStrapServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return BootStrapServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName, Properties bootStrapProperties) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, BootStrapServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      serviceConfig.setConfig(new ConfigImpl(bootStrapProperties));
   }

   private static class ConfigImpl implements BootStrapServiceConfig {
      private Properties bootStrapProperties;

      public ConfigImpl(Properties bootStrapProperties) {
         this.bootStrapProperties = bootStrapProperties;
      }

      public Properties getBootStrapProperties() {
         return this.bootStrapProperties;
      }

      public void setBootStrapProperties(Properties bootStrapProperties) {
         this.bootStrapProperties = bootStrapProperties;
      }
   }
}
