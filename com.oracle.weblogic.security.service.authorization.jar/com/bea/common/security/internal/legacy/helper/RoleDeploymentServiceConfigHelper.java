package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.RoleDeploymentServiceImpl;
import com.bea.common.security.servicecfg.RoleDeploymentServiceConfig;
import java.util.ArrayList;
import java.util.List;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authorization.DeployableRoleMapperMBean;
import weblogic.management.security.authorization.RoleMapperMBean;

class RoleDeploymentServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return RoleDeploymentServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static DeployableRoleMapperMBean[] getFaceMBeans(RealmMBean realmMBean) {
      List providers = new ArrayList();
      RoleMapperMBean[] providerMBeans = realmMBean.getRoleMappers();

      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         if (providerMBeans[i] instanceof DeployableRoleMapperMBean) {
            DeployableRoleMapperMBean providerMBean = (DeployableRoleMapperMBean)providerMBeans[i];
            if (providerMBean.isRoleDeploymentEnabled()) {
               providers.add(providerMBean);
            }
         }
      }

      return (DeployableRoleMapperMBean[])providers.toArray(new DeployableRoleMapperMBean[providers.size()]);
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      String[] faceNames = RoleDeployerProviderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, RoleDeploymentServiceImpl.class.getName(), true);
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

   private static class ConfigImpl implements RoleDeploymentServiceConfig {
      private String[] faceNames;
      private String auditServiceName;
      private String identityServiceName;

      public ConfigImpl(String[] faceNames, String auditServiceName, String identityServiceName) {
         this.faceNames = faceNames;
         this.auditServiceName = auditServiceName;
         this.identityServiceName = identityServiceName;
      }

      public String[] getRoleDeployerNames() {
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
