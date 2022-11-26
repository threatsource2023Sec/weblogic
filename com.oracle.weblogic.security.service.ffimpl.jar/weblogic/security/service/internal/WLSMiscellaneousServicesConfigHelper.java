package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.security.legacy.ConfigHelperFactory;
import com.bea.common.security.service.IdentityService;
import weblogic.management.security.RealmMBean;

public class WLSMiscellaneousServicesConfigHelper {
   private WLSMiscellaneousServicesConfigHelper() {
   }

   public static String getApplicationVersioningServiceName(RealmMBean realmMBean) {
      return ApplicationVersioningServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   public static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String wlsLifecycleImplLoaderName, RealmMBean realmMBean, IdentityService identityService) {
      ApplicationVersioningServiceConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, realmMBean);
      ConfigHelperFactory.getInstance(cssImplLoader).getIdentityServicesConfigHelper(realmMBean).addToConfig(serviceEngineConfig, identityService);
   }
}
