package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.ConfigHelperFactory;
import weblogic.management.security.RealmMBean;

class WLSJAASLoginServiceConfigHelper {
   private static String getServiceName(ClassLoader cssImplLoader, RealmMBean realmMBean) {
      return ConfigHelperFactory.getInstance(cssImplLoader).getAuthenticationServicesConfigHelper(realmMBean).getJAASLoginServiceName();
   }

   static String getBaseServiceName(ClassLoader cssImplLoader, RealmMBean realmMBean) {
      return WLSJAASLoginServiceConfigHelper.class.getName() + "_" + getServiceName(cssImplLoader, realmMBean);
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String wlsLifecycleImplLoaderName, RealmMBean realmMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(cssImplLoader, realmMBean), WLSJAASLoginServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(wlsLifecycleImplLoaderName);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig, cssImplLoader));
   }

   private static class ConfigImpl implements WLSJAASLoginServiceConfig {
      private String auditServiceName;
      private String jaasLoginServiceName;
      private String userLockoutRuntimeServiceName;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig, ClassLoader cssImplLoader) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.auditServiceName = ConfigHelperFactory.getInstance(cssImplLoader).getAuditServicesConfigHelper(realmMBean).getAuditServiceName();
         serviceConfig.addDependency(this.auditServiceName);
         this.jaasLoginServiceName = WLSJAASLoginServiceConfigHelper.getBaseServiceName(cssImplLoader, realmMBean);
         serviceConfig.addDependency(this.jaasLoginServiceName);
         this.userLockoutRuntimeServiceName = UserLockoutRuntimeServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.userLockoutRuntimeServiceName);
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      public String getJAASLoginServiceName() {
         return this.jaasLoginServiceName;
      }

      public String getUserLockoutRuntimeServiceName() {
         return this.userLockoutRuntimeServiceName;
      }

      // $FF: synthetic method
      ConfigImpl(RealmMBean x0, ServiceEngineManagedServiceConfig x1, ClassLoader x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
