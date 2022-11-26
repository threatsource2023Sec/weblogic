package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.ConfigHelperFactory;
import java.util.Vector;
import weblogic.management.security.ApplicationVersionerMBean;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.RealmMBean;

class ApplicationVersioningServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return ApplicationVersioningServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static boolean gatherMBeans(Vector providers, ProviderMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         if (!(providerMBeans[i] instanceof ApplicationVersionerMBean)) {
            return false;
         }

         providers.add(providerMBeans[i]);
      }

      return true;
   }

   private static ApplicationVersionerMBean[] getFaceMBeans(RealmMBean realmMBean) {
      Vector providers = new Vector();
      if (!gatherMBeans(providers, realmMBean.getRoleMappers())) {
         return null;
      } else if (!gatherMBeans(providers, realmMBean.getAuthorizers())) {
         return null;
      } else {
         return !gatherMBeans(providers, realmMBean.getCredentialMappers()) ? null : (ApplicationVersionerMBean[])((ApplicationVersionerMBean[])providers.toArray(new ApplicationVersionerMBean[providers.size()]));
      }
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String wlsLifecycleImplLoaderName, RealmMBean realmMBean) {
      VersionableApplicationProviderConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(realmMBean), ApplicationVersioningServiceImpl.class.getName(), true);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig, cssImplLoader));
      serviceConfig.setClassLoader(wlsLifecycleImplLoaderName);
   }

   private static class ConfigImpl implements ApplicationVersioningServiceConfig {
      private String[] faceNames;
      private String auditServiceName;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig, ClassLoader cssImplLoader) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         ApplicationVersionerMBean[] providerMBeans = ApplicationVersioningServiceConfigHelper.getFaceMBeans(realmMBean);
         this.faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

         for(int i = 0; i < this.faceNames.length; ++i) {
            String name = VersionableApplicationProviderConfigHelper.getServiceName(providerMBeans[i]);
            this.faceNames[i] = name;
            serviceConfig.addDependency(name);
         }

         this.auditServiceName = ConfigHelperFactory.getInstance(cssImplLoader).getAuditServicesConfigHelper(realmMBean).getAuditServiceName();
         serviceConfig.addDependency(this.auditServiceName);
      }

      public String[] getVersionableApplicationProviderNames() {
         return this.faceNames;
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      // $FF: synthetic method
      ConfigImpl(RealmMBean x0, ServiceEngineManagedServiceConfig x1, ClassLoader x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
