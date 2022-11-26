package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.ConfigHelperFactory;
import weblogic.management.security.RealmMBean;

class WLSIdentityServiceConfigHelper {
   private static String getServiceName(ClassLoader cssImplLoader, RealmMBean realmMBean) {
      return ConfigHelperFactory.getInstance(cssImplLoader).getAuthenticationServicesConfigHelper(realmMBean).getIdentityServiceName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String wlsLifecycleImplLoaderName, RealmMBean realmMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(cssImplLoader, realmMBean), WLSIdentityServiceImpl.class.getName(), false);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig, cssImplLoader));
      serviceConfig.setClassLoader(wlsLifecycleImplLoaderName);
   }

   private static class ConfigImpl implements WLSIdentityServiceConfig {
      private String auditServiceName;
      private String principalValidationServiceName;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig, ClassLoader cssImplLoader) {
         ConfigHelperFactory helperFactory = ConfigHelperFactory.getInstance(cssImplLoader);
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.auditServiceName = helperFactory.getAuditServicesConfigHelper(realmMBean).getAuditServiceName();
         serviceConfig.addDependency(this.auditServiceName);
         this.principalValidationServiceName = helperFactory.getAuthenticationServicesConfigHelper(realmMBean).getPrincipalValidationServiceName();
         serviceConfig.addDependency(this.principalValidationServiceName);
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      public String getPrincipalValidationServiceName() {
         return this.principalValidationServiceName;
      }

      // $FF: synthetic method
      ConfigImpl(RealmMBean x0, ServiceEngineManagedServiceConfig x1, ClassLoader x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
