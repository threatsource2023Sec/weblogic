package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.RoleConsumerServiceImpl;
import com.bea.common.security.servicecfg.RoleConsumerServiceConfig;
import java.util.ArrayList;
import java.util.List;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authorization.RoleConsumerMBean;
import weblogic.management.security.authorization.RoleMapperMBean;

class RoleConsumerServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return RoleConsumerServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static RoleMapperMBean[] getFaceMBeans(RealmMBean realmMBean) {
      List roleConsumers = new ArrayList();
      RoleMapperMBean[] providerMBeans = realmMBean.getRoleMappers();

      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         if (providerMBeans[i] instanceof RoleConsumerMBean) {
            roleConsumers.add(providerMBeans[i]);
         }
      }

      return (RoleMapperMBean[])roleConsumers.toArray(new RoleMapperMBean[roleConsumers.size()]);
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      RoleMapperMBean[] providerMBeans = getFaceMBeans(realmMBean);
      String[] faceNames = RoleConsumerProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBeans);
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, RoleConsumerServiceImpl.class.getName(), true);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);

      for(int i = 0; i < faceNames.length; ++i) {
         serviceConfig.addDependency(faceNames[i]);
      }

      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      String identityServiceName = IdentityServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(identityServiceName);
      serviceConfig.setConfig(new ConfigImpl(faceNames, auditServiceName, identityServiceName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static class ConfigImpl implements RoleConsumerServiceConfig {
      private String[] faceNames = null;
      private String auditServiceName;
      private String identityServiceName;

      public ConfigImpl(String[] faceNames, String auditServiceName, String identityServiceName) {
         this.faceNames = faceNames;
         this.auditServiceName = auditServiceName;
         this.identityServiceName = identityServiceName;
      }

      public String[] getRoleConsumerNames() {
         return this.faceNames;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      public String getIdentityServiceName() {
         return this.identityServiceName;
      }
   }
}
