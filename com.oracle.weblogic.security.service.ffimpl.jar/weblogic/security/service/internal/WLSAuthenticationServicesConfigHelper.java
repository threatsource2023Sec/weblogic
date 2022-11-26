package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.AuthenticationServicesConfigHelper;
import weblogic.management.provider.CommandLine;
import weblogic.management.security.RealmMBean;

public class WLSAuthenticationServicesConfigHelper {
   private WLSAuthenticationServicesConfigHelper() {
   }

   public static String getServletAuthenticationFilterServiceName(RealmMBean realmMBean) {
      return ServletAuthenticationFilterServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   public static String getWSPasswordDigestServiceName(RealmMBean realmMBean) {
      return WSPasswordDigestServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   public static String getUserLockoutAdministrationServiceName(RealmMBean realmMBean) {
      return UserLockoutAdministrationServiceConfigHelper.getServiceName(realmMBean);
   }

   public static String getUserLockoutCoordinationServiceName(RealmMBean realmMBean) {
      return UserLockoutCoordinationServiceConfigHelper.getServiceName(realmMBean);
   }

   public static String getUserLockoutRuntimeServiceName(RealmMBean realmMBean) {
      return UserLockoutRuntimeServiceConfigHelper.getServiceName(realmMBean);
   }

   public static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String cssLifecycleImplLoaderName, String wlsLifecycleImplLoaderName, RealmMBean realmMBean, AuthenticationServicesConfigHelper atnCfg) {
      atnCfg.getJAASLoginServiceCustomizer().renameService(WLSJAASLoginServiceConfigHelper.getBaseServiceName(cssImplLoader, realmMBean));
      long ttl_sysprop = CommandLine.getCommandLine().getIdentityAssertionTTLMillis();
      if (ttl_sysprop < 0L) {
         atnCfg.getIdentityCacheServiceCustomizer().setIdentityCacheEnabled(false);
      } else if (!realmMBean.isIdentityAssertionCacheEnabled()) {
         atnCfg.getIdentityCacheServiceCustomizer().setIdentityCacheEnabled(false);
      } else {
         atnCfg.getIdentityCacheServiceCustomizer().setIdentityCacheEnabled(true);
         atnCfg.getIdentityCacheServiceCustomizer().setIdentityAssertionDoNotCacheContextElements(realmMBean.getIdentityAssertionDoNotCacheContextElements());
         if (ttl_sysprop != 300000L) {
            atnCfg.getIdentityCacheServiceCustomizer().setIdentityCacheTTL(ttl_sysprop);
         } else {
            atnCfg.getIdentityCacheServiceCustomizer().setIdentityCacheTTL((long)(realmMBean.getIdentityAssertionCacheTTL() * 1000));
         }
      }

      atnCfg.addToConfig(serviceEngineConfig, cssLifecycleImplLoaderName);
      ServletAuthenticationFilterServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean);
      WSPasswordDigestServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean);
      UserLockoutServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean);
      UserLockoutRuntimeServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean);
      UserLockoutAdministrationServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean);
      UserLockoutCoordinationServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean);
      WLSJAASLoginServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean);
   }
}
