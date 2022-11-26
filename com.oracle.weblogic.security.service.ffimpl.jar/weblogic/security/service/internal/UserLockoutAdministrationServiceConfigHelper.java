package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import weblogic.management.security.RealmMBean;

class UserLockoutAdministrationServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return UserLockoutAdministrationServiceConfigHelper.class.getName() + "_" + realmMBean.getName() + "_" + realmMBean.getUserLockoutManager().getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String wlsLifecycleImplLoaderName, RealmMBean realmMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(realmMBean), UserLockoutAdministrationServiceImpl.class.getName(), true);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig, cssImplLoader));
      serviceConfig.setClassLoader(wlsLifecycleImplLoaderName);
   }

   private static class ConfigImpl implements UserLockoutAdministrationServiceConfig {
      private String userLockoutServiceName;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig, ClassLoader cssImplLoader) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.userLockoutServiceName = UserLockoutServiceConfigHelper.getServiceName(realmMBean);
         serviceConfig.addDependency(this.userLockoutServiceName);
      }

      public String getUserLockoutServiceName() {
         return this.userLockoutServiceName;
      }

      // $FF: synthetic method
      ConfigImpl(RealmMBean x0, ServiceEngineManagedServiceConfig x1, ClassLoader x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
