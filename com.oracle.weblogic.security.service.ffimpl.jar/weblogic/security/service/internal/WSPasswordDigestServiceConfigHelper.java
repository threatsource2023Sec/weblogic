package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.ConfigHelperFactory;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;

class WSPasswordDigestServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return WSPasswordDigestServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static AuthenticationProviderMBean[] getFaceMBeans(RealmMBean realmMBean) {
      AuthenticationProviderMBean[] providerMBeans = realmMBean.getAuthenticationProviders();
      return providerMBeans != null ? providerMBeans : new AuthenticationProviderMBean[0];
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String wlsLifecycleImplLoaderName, RealmMBean realmMBean) {
      WSPasswordDigestProviderConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(realmMBean), WSPasswordDigestServiceImpl.class.getName(), true);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig, cssImplLoader));
      serviceConfig.setClassLoader(wlsLifecycleImplLoaderName);
   }

   private static class ConfigImpl implements WSPasswordDigestServiceConfig {
      private String[] faceNames;
      private String auditServiceName;
      private RealmMBean realmMBean;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig, ClassLoader cssImplLoader) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.realmMBean = realmMBean;
         AuthenticationProviderMBean[] providerMBeans = WSPasswordDigestServiceConfigHelper.getFaceMBeans(realmMBean);
         this.faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

         for(int i = 0; i < this.faceNames.length; ++i) {
            String name = WSPasswordDigestProviderConfigHelper.getServiceName(providerMBeans[i]);
            this.faceNames[i] = name;
            serviceConfig.addDependency(name);
         }

         this.auditServiceName = ConfigHelperFactory.getInstance(cssImplLoader).getAuditServicesConfigHelper(realmMBean).getAuditServiceName();
         serviceConfig.addDependency(this.auditServiceName);
      }

      public String[] getWSPasswordDigestProviderNames() {
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
