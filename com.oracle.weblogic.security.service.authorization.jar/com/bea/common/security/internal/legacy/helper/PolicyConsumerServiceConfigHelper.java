package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.PolicyConsumerServiceImpl;
import com.bea.common.security.servicecfg.PolicyConsumerServiceConfig;
import java.util.ArrayList;
import java.util.List;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authorization.AuthorizerMBean;
import weblogic.management.security.authorization.PolicyConsumerMBean;

class PolicyConsumerServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return PolicyConsumerServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static AuthorizerMBean[] getFaceMBeans(RealmMBean realmMBean) {
      List policyConsumers = new ArrayList();
      AuthorizerMBean[] providerMBeans = realmMBean.getAuthorizers();

      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         if (providerMBeans[i] instanceof PolicyConsumerMBean) {
            policyConsumers.add(providerMBeans[i]);
         }
      }

      return (AuthorizerMBean[])policyConsumers.toArray(new AuthorizerMBean[policyConsumers.size()]);
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      AuthorizerMBean[] providerMBeans = getFaceMBeans(realmMBean);
      String[] faceNames = PolicyConsumerProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBeans);
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, PolicyConsumerServiceImpl.class.getName(), true);
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

   private static class ConfigImpl implements PolicyConsumerServiceConfig {
      private String[] faceNames = null;
      private String auditServiceName;
      private String identityServiceName;

      public ConfigImpl(String[] faceNames, String auditServiceName, String identityServiceName) {
         this.faceNames = faceNames;
         this.auditServiceName = auditServiceName;
         this.identityServiceName = identityServiceName;
      }

      public String[] getPolicyConsumerNames() {
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
