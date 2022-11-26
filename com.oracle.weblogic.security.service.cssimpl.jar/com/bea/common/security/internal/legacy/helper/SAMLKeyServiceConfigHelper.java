package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.SAMLKeyServiceImpl;
import com.bea.common.security.servicecfg.SAMLKeyServiceConfig;
import weblogic.management.security.RealmMBean;

class SAMLKeyServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return SAMLKeyServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName, String keyStoreFile, String keyStoreType, char[] keyStorePassPhrase, int storeValidationPollInterval, String defaultKeyAlias, char[] defaultKeyPassphrase) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, SAMLKeyServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      serviceConfig.setConfig(new ConfigImpl(keyStoreFile, keyStoreType, keyStorePassPhrase, storeValidationPollInterval, defaultKeyAlias, defaultKeyPassphrase));
   }

   private static class ConfigImpl implements SAMLKeyServiceConfig {
      private String keyStoreFile;
      private String keyStoreType;
      private char[] keyStorePassPhrase;
      private int storeValidationPollInterval;
      private String defaultKeyAlias;
      private char[] defaultKeyPassphrase;

      public ConfigImpl(String keyStoreFile, String keyStoreType, char[] keyStorePassPhrase, int storeValidationPollInterval, String defaultKeyAlias, char[] defaultKeyPassphrase) {
         this.keyStoreFile = keyStoreFile;
         this.keyStoreType = keyStoreType;
         this.keyStorePassPhrase = keyStorePassPhrase;
         this.storeValidationPollInterval = storeValidationPollInterval;
         this.defaultKeyAlias = defaultKeyAlias;
         this.defaultKeyPassphrase = defaultKeyPassphrase;
      }

      public String getKeyStoreFile() {
         return this.keyStoreFile;
      }

      public String getKeyStoreType() {
         return this.keyStoreType;
      }

      public char[] getKeyStorePassPhrase() {
         return this.keyStorePassPhrase;
      }

      public int getStoreValidationPollInterval() {
         return this.storeValidationPollInterval;
      }

      public String getDefaultKeyAlias() {
         return this.defaultKeyAlias;
      }

      public char[] getDefaultKeyPassphrase() {
         return this.defaultKeyPassphrase;
      }
   }
}
