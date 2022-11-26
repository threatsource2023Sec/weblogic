package weblogic.security.service.internal;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.ConfigHelperFactory;
import java.util.Vector;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.ServletAuthenticationFilterMBean;

class ServletAuthenticationFilterServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return ServletAuthenticationFilterServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   private static ServletAuthenticationFilterMBean[] getFaceMBeans(RealmMBean realmMBean) {
      Vector providers = new Vector();
      AuthenticationProviderMBean[] providerMBeans = realmMBean.getAuthenticationProviders();

      for(int i = 0; providerMBeans != null && i < providerMBeans.length; ++i) {
         if (providerMBeans[i] instanceof ServletAuthenticationFilterMBean) {
            providers.add(providerMBeans[i]);
         }
      }

      return (ServletAuthenticationFilterMBean[])((ServletAuthenticationFilterMBean[])providers.toArray(new ServletAuthenticationFilterMBean[providers.size()]));
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, ClassLoader cssImplLoader, String wlsLifecycleImplLoaderName, RealmMBean realmMBean) {
      ServletAuthenticationFilterProviderConfigHelper.addToConfig(serviceEngineConfig, cssImplLoader, wlsLifecycleImplLoaderName, getFaceMBeans(realmMBean));
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(realmMBean), ServletAuthenticationFilterServiceImpl.class.getName(), true);
      serviceConfig.setConfig(new ConfigImpl(realmMBean, serviceConfig, cssImplLoader));
      serviceConfig.setClassLoader(wlsLifecycleImplLoaderName);
   }

   private static class ConfigImpl implements ServletAuthenticationFilterServiceConfig {
      private String[] faceNames;
      private String auditServiceName;
      private RealmMBean realmMBean;

      private ConfigImpl(RealmMBean realmMBean, ServiceEngineManagedServiceConfig serviceConfig, ClassLoader cssImplLoader) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.realmMBean = realmMBean;
         ServletAuthenticationFilterMBean[] providerMBeans = ServletAuthenticationFilterServiceConfigHelper.getFaceMBeans(realmMBean);
         this.faceNames = new String[providerMBeans != null ? providerMBeans.length : 0];

         for(int i = 0; i < this.faceNames.length; ++i) {
            String name = ServletAuthenticationFilterProviderConfigHelper.getServiceName(providerMBeans[i]);
            this.faceNames[i] = name;
            serviceConfig.addDependency(name);
         }

         this.auditServiceName = ConfigHelperFactory.getInstance(cssImplLoader).getAuditServicesConfigHelper(realmMBean).getAuditServiceName();
         serviceConfig.addDependency(this.auditServiceName);
      }

      public String[] getServletAuthenticationFilterProviderNames() {
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
