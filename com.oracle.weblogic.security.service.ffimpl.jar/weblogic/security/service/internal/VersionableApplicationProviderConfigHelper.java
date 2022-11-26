package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.ConfigHelperFactory;
import weblogic.management.security.ApplicationVersionerMBean;

class VersionableApplicationProviderConfigHelper {
   static String getServiceName(ApplicationVersionerMBean providerMBean) {
      return VersionableApplicationProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static String getLoggingName(ApplicationVersionerMBean providerMBean) {
      return providerMBean.getRealm().getName() + "_" + providerMBean.getProviderClassName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String wlsLifecycleImplLoaderName, ApplicationVersionerMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBeans[i]), VersionableApplicationProviderImpl.class.getName(), false, getLoggingName(providerMBeans[i]));
         serviceConfig.setConfig(new ConfigImpl(providerMBeans[i], serviceConfig, cssImplLoader));
         serviceConfig.setClassLoader(wlsLifecycleImplLoaderName);
      }

   }

   private static class ConfigImpl implements VersionableApplicationProviderConfig {
      private String name = null;

      public ConfigImpl(ApplicationVersionerMBean providerMBean, ServiceEngineManagedServiceConfig serviceConfig, ClassLoader cssImplLoader) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.name = ConfigHelperFactory.getInstance(cssImplLoader).getSecurityProviderConfigHelper().getServiceName(providerMBean);
         serviceConfig.addDependency(this.name);
      }

      public String getProviderName() {
         return this.name;
      }
   }
}
