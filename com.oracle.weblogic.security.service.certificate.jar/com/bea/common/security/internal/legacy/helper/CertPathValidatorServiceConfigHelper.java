package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.CertPathValidatorServiceImpl;
import com.bea.common.security.servicecfg.CertPathValidatorServiceConfig;
import java.util.ArrayList;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.pk.CertPathProviderMBean;
import weblogic.management.security.pk.CertPathValidatorMBean;

class CertPathValidatorServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return CertPathValidatorServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static CertPathValidatorMBean[] getFaceMBeans(RealmMBean realmMBean) {
      CertPathProviderMBean[] providerMBeans = realmMBean.getCertPathProviders();
      if (providerMBeans != null && providerMBeans.length != 0) {
         ArrayList providers = new ArrayList(providerMBeans.length);

         for(int i = 0; i < providerMBeans.length; ++i) {
            if (providerMBeans[i] instanceof CertPathValidatorMBean) {
               providers.add(providerMBeans[i]);
            }
         }

         return (CertPathValidatorMBean[])((CertPathValidatorMBean[])providers.toArray(new CertPathValidatorMBean[providers.size()]));
      } else {
         return null;
      }
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName) {
      CertPathValidatorMBean[] providerMBeans = getFaceMBeans(realmMBean);
      String[] faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

      for(int i = 0; i < faceNames.length; ++i) {
         faceNames[i] = CertPathValidatorConfigHelper.getServiceName(providerMBeans[i]);
      }

      CertPathValidatorConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBeans);
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, CertPathValidatorServiceImpl.class.getName(), true);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);

      for(int i = 0; i < faceNames.length; ++i) {
         serviceConfig.addDependency(faceNames[i]);
      }

      String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
      serviceConfig.addDependency(auditServiceName);
      serviceConfig.setConfig(new ConfigImpl(faceNames, auditServiceName));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static class ConfigImpl implements CertPathValidatorServiceConfig {
      private String[] faceNames = null;
      private String auditServiceName;

      public ConfigImpl(String[] faceNames, String auditServiceName) {
         this.faceNames = faceNames;
         this.auditServiceName = auditServiceName;
      }

      public String[] getCertPathValidatorNames() {
         return this.faceNames;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }
   }
}
