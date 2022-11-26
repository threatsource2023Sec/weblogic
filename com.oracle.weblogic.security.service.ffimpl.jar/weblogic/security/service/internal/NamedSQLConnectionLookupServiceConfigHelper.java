package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.ConfigHelperFactory;
import weblogic.management.security.RealmMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

class NamedSQLConnectionLookupServiceConfigHelper {
   static String getServiceName(ClassLoader cssImplLoader, RealmMBean realmMBean) {
      return ConfigHelperFactory.getInstance(cssImplLoader).getInternalServicesConfigHelper(realmMBean).getNamedSQLConnectionLookupServiceName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String lifecycleImplLoaderName, RealmMBean realmMBean, AuthenticatedSubject kernelId) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(cssImplLoader, realmMBean), NamedSQLConnectionLookupServiceImpl.class.getName(), true);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig, cssImplLoader, kernelId));
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }

   private static class ConfigImpl implements NamedSQLConnectionLookupServiceConfig {
      private AuthenticatedSubject kernelId;

      public ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig, ClassLoader cssImplLoader, AuthenticatedSubject kernelId) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.kernelId = kernelId;
      }

      public AuthenticatedSubject getKernelId() {
         return this.kernelId;
      }
   }
}
