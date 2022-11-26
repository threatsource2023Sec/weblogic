package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.CertPathBuilderServiceImpl;
import com.bea.common.security.servicecfg.CertPathBuilderServiceConfig;
import java.util.ArrayList;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.pk.CertPathBuilderMBean;
import weblogic.management.security.pk.CertPathProviderMBean;
import weblogic.management.security.pk.CertPathValidatorMBean;

class CertPathBuilderServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return CertPathBuilderServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static CertPathValidatorMBean[] getFaceMBeans(RealmMBean realmMBean) {
      String builderName = null;
      if (realmMBean.getCertPathBuilder() != null) {
         builderName = realmMBean.getCertPathBuilder().getName();
      }

      CertPathProviderMBean[] providerMBeans = realmMBean.getCertPathProviders();
      if (providerMBeans != null && providerMBeans.length != 0) {
         ArrayList providers = new ArrayList(providerMBeans.length);

         for(int i = 0; i < providerMBeans.length; ++i) {
            CertPathProviderMBean providerMBean = providerMBeans[i];
            if (providerMBean instanceof CertPathValidatorMBean && (builderName == null || !builderName.equals(providerMBean.getName()))) {
               providers.add(providerMBean);
            }
         }

         return (CertPathValidatorMBean[])((CertPathValidatorMBean[])providers.toArray(new CertPathValidatorMBean[providers.size()]));
      } else {
         return null;
      }
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName, boolean validatorRemoved) {
      CertPathBuilderMBean builderMBean = realmMBean.getCertPathBuilder();
      if (builderMBean != null) {
         CertPathValidatorMBean[] providerMBeans = getFaceMBeans(realmMBean);
         String[] faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

         for(int i = 0; i < faceNames.length; ++i) {
            faceNames[i] = CertPathValidatorConfigHelper.getServiceName(providerMBeans[i]);
         }

         if (validatorRemoved) {
            CertPathValidatorConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, providerMBeans);
         }

         CertPathBuilderConfigHelper.addToConfig(serviceEngineConfig, lifecycleImplLoaderName, realmMBean.getCertPathBuilder());
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, CertPathBuilderServiceImpl.class.getName(), true);
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);

         for(int i = 0; i < faceNames.length; ++i) {
            serviceConfig.addDependency(faceNames[i]);
         }

         String certPathBuilderName = CertPathBuilderConfigHelper.getServiceName(realmMBean.getCertPathBuilder());
         serviceConfig.addDependency(certPathBuilderName);
         String auditServiceName = AuditServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(auditServiceName);
         serviceConfig.setConfig(new ConfigImpl(faceNames, certPathBuilderName, auditServiceName));
         serviceConfig.setClassLoader(lifecycleImplLoaderName);
      }
   }

   private static class ConfigImpl implements CertPathBuilderServiceConfig {
      private String[] faceNames = null;
      private String certPathBuilderName;
      private String auditServiceName;

      public ConfigImpl(String[] faceNames, String certPathBuilderName, String auditServiceName) {
         this.faceNames = faceNames;
         this.certPathBuilderName = certPathBuilderName;
         this.auditServiceName = auditServiceName;
      }

      public String getCertPathBuilderName() {
         return this.certPathBuilderName;
      }

      public String[] getCertPathValidatorNames() {
         return this.faceNames;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }
   }
}
