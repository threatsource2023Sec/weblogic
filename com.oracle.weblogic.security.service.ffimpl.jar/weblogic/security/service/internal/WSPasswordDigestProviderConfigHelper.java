package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.ConfigHelperFactory;
import weblogic.management.security.authentication.AuthenticationProviderMBean;

class WSPasswordDigestProviderConfigHelper {
   static String getServiceName(AuthenticationProviderMBean providerMBean) {
      return WSPasswordDigestProviderConfigHelper.class.getName() + "_" + providerMBean.getRealm().getName() + "_" + providerMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String wlsLifecycleImplLoaderName, AuthenticationProviderMBean[] providerMBeans) {
      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(providerMBeans[i]), WSPasswordDigestProviderImpl.class.getName(), false);
         serviceConfig.setConfig(new ConfigImpl(providerMBeans[i], serviceConfig, cssImplLoader));
         serviceConfig.setClassLoader(wlsLifecycleImplLoaderName);
      }

   }

   private static class ConfigImpl implements WSPasswordDigestProviderConfig {
      private String name = null;

      public ConfigImpl(AuthenticationProviderMBean providerMBean, ServiceEngineManagedServiceConfig serviceConfig, ClassLoader cssImplLoader) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.name = ConfigHelperFactory.getInstance(cssImplLoader).getSecurityProviderConfigHelper().getServiceName(providerMBean);
         serviceConfig.addDependency(this.name);
      }

      public String getAuthenticationProviderName() {
         return this.name;
      }
   }
}
