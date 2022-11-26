package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.PolicyDeploymentServiceImpl;
import com.bea.common.security.servicecfg.PolicyDeploymentServiceConfig;
import java.util.ArrayList;
import java.util.List;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authorization.AuthorizerMBean;
import weblogic.management.security.authorization.DeployableAuthorizerMBean;

class PolicyDeploymentServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return PolicyDeploymentServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static DeployableAuthorizerMBean[] getFaceMBeans(RealmMBean realmMBean) {
      List providers = new ArrayList();
      AuthorizerMBean[] providerMBeans = realmMBean.getAuthorizers();

      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         if (providerMBeans[i] instanceof DeployableAuthorizerMBean) {
            DeployableAuthorizerMBean providerMBean = (DeployableAuthorizerMBean)providerMBeans[i];
            if (providerMBean.isPolicyDeploymentEnabled()) {
               providers.add(providerMBean);
            }
         }
      }

      return (DeployableAuthorizerMBean[])providers.toArray(new DeployableAuthorizerMBean[providers.size()]);
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      String[] faceNames = PolicyDeployerProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, PolicyDeploymentServiceImpl.class.getName(), true);
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

   private static class ConfigImpl implements PolicyDeploymentServiceConfig {
      private String[] faceNames;
      private String auditServiceName;
      private String identityServiceName;

      public ConfigImpl(String[] faceNames, String auditServiceName, String identityServiceName) {
         this.faceNames = faceNames;
         this.auditServiceName = auditServiceName;
         this.identityServiceName = identityServiceName;
      }

      public String[] getPolicyDeployerNames() {
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
