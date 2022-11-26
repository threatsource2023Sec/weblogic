package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.ConfigHelperFactory;
import java.security.AccessController;
import weblogic.management.provider.ManagementService;
import weblogic.management.security.RealmMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class UserLockoutServiceConfigHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   static String getServiceName(RealmMBean realmMBean) {
      return UserLockoutServiceConfigHelper.class.getName() + "_" + realmMBean.getName() + "_" + realmMBean.getUserLockoutManager().getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String wlsLifecycleImplLoaderName, RealmMBean realmMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(realmMBean), UserLockoutServiceImpl.class.getName(), false);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig, cssImplLoader));
      serviceConfig.setClassLoader(wlsLifecycleImplLoaderName);
   }

   private static class ConfigImpl implements UserLockoutServiceConfig {
      private String auditServiceName;
      private String realmName;
      private String serverName;
      private String managementIDD;
      private boolean lockoutEnabled;
      private long lockoutThreshold;
      private long lockoutDuration;
      private long lockoutResetDuration;
      private long lockoutGCThreshold;
      private long lockoutCacheSize;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig, ClassLoader cssImplLoader) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.auditServiceName = ConfigHelperFactory.getInstance(cssImplLoader).getAuditServicesConfigHelper(realmMBean).getAuditServiceName();
         serviceConfig.addDependency(this.auditServiceName);
         this.realmName = realmMBean.getName();
         this.managementIDD = realmMBean.getManagementIdentityDomain();
         this.serverName = ManagementService.getRuntimeAccess(UserLockoutServiceConfigHelper.kernelId).getServer().getName();
         this.lockoutEnabled = realmMBean.getUserLockoutManager().isLockoutEnabled();
         this.lockoutThreshold = realmMBean.getUserLockoutManager().getLockoutThreshold();
         this.lockoutDuration = realmMBean.getUserLockoutManager().getLockoutDuration();
         this.lockoutResetDuration = realmMBean.getUserLockoutManager().getLockoutResetDuration();
         this.lockoutGCThreshold = realmMBean.getUserLockoutManager().getLockoutGCThreshold();
         this.lockoutCacheSize = realmMBean.getUserLockoutManager().getLockoutCacheSize();
      }

      public String getAuditServiceName() {
         return this.auditServiceName;
      }

      public String getRealmName() {
         return this.realmName;
      }

      public String getManagementIDD() {
         return this.managementIDD;
      }

      public String getServerName() {
         return this.serverName;
      }

      public boolean isLockoutEnabled() {
         return this.lockoutEnabled;
      }

      public long getLockoutThreshold() {
         return this.lockoutThreshold;
      }

      public long getLockoutDuration() {
         return this.lockoutDuration;
      }

      public long getLockoutResetDuration() {
         return this.lockoutResetDuration;
      }

      public long getLockoutGCThreshold() {
         return this.lockoutGCThreshold;
      }

      public long getLockoutCacheSize() {
         return this.lockoutCacheSize;
      }

      // $FF: synthetic method
      ConfigImpl(RealmMBean x0, ServiceEngineManagedServiceConfig x1, ClassLoader x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
