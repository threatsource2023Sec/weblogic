package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.servicecfg.StoreServiceConfig;
import java.util.Properties;
import weblogic.management.security.RealmMBean;

class StoreServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return StoreServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName, Properties storeProperties, Properties connectionProperties, Properties notificationProperties) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, getImplName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      serviceConfig.setConfig(new ConfigImpl(storeProperties, connectionProperties, notificationProperties));
   }

   private static String getImplName() {
      return "com.bea.common.security.internal.service.StoreServiceImpl";
   }

   private static class ConfigImpl implements StoreServiceConfig {
      private Properties storeProperties;
      private Properties connectionProperties;
      private Properties notificationProperties;

      public ConfigImpl(Properties storeProperties, Properties connectionProperties, Properties notificationProperties) {
         this.storeProperties = storeProperties;
         this.connectionProperties = connectionProperties;
         this.notificationProperties = notificationProperties;
      }

      public Properties getStoreProperties() {
         return this.storeProperties;
      }

      public Properties getConnectionProperties() {
         return this.connectionProperties;
      }

      public Properties getNotificationProperties() {
         return this.notificationProperties;
      }

      public void setStoreProperties(Properties storeProperties) {
         this.storeProperties = storeProperties;
      }

      public void setConnectionProperties(Properties connectionProperties) {
         this.connectionProperties = connectionProperties;
      }

      public void setNotificationProperties(Properties notificationProperties) {
         this.notificationProperties = notificationProperties;
      }
   }
}
