package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.JAXPFactoryServiceImpl;
import com.bea.common.security.servicecfg.JAXPFactoryServiceConfig;
import weblogic.management.security.RealmMBean;

class JAXPFactoryServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return JAXPFactoryServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName, String documentBuilderFactoryClassName, String transformerFactoryClassName) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, JAXPFactoryServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      serviceConfig.setConfig(new ConfigImpl(documentBuilderFactoryClassName, transformerFactoryClassName));
   }

   private static class ConfigImpl implements JAXPFactoryServiceConfig {
      private String documentBuilderFactoryClassName;
      private String transformerFactoryClassName;

      public ConfigImpl(String documentBuilderFactoryClassName, String transformerFactoryClassName) {
         this.documentBuilderFactoryClassName = documentBuilderFactoryClassName;
         this.transformerFactoryClassName = transformerFactoryClassName;
      }

      public String getDocumentBuilderFactoryClassName() {
         return this.documentBuilderFactoryClassName;
      }

      public String getTransformerFactoryClassName() {
         return this.transformerFactoryClassName;
      }
   }
}
