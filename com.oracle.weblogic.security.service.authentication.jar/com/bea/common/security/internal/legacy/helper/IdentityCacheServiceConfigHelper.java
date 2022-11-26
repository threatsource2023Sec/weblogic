package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.IdentityCacheServiceImpl;
import com.bea.common.security.servicecfg.IdentityCacheServiceConfig;
import weblogic.management.security.RealmMBean;

class IdentityCacheServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return IdentityCacheServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean, String serviceName, boolean enabled, int max, long ttl, String[] eleNames) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(serviceName, IdentityCacheServiceImpl.class.getName(), true);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
      serviceConfig.setConfig(new ConfigImpl(enabled, max, ttl, eleNames, serviceConfig));
   }

   private static class ConfigImpl implements IdentityCacheServiceConfig {
      private boolean enabled;
      private int max;
      private long ttl;
      private String[] eleNames;

      private ConfigImpl(boolean enabled, int max, long ttl, String[] eleNames, ServiceEngineManagedServiceConfig serviceConfig) {
         serviceConfig.addDependency(LoggerService.SERVICE_NAME);
         this.enabled = enabled;
         this.max = max;
         this.ttl = ttl;
         if (eleNames != null && eleNames.length > 0) {
            this.eleNames = eleNames;
         } else {
            this.eleNames = new String[0];
         }

      }

      public boolean isIdentityCacheEnabled() {
         return this.enabled;
      }

      public int getMaxIdentitiesInCache() {
         return this.max;
      }

      public long getIdentityCacheTTL() {
         return this.ttl;
      }

      public String[] getIdentityAssertionDoNotCacheContextElements() {
         return this.eleNames;
      }

      // $FF: synthetic method
      ConfigImpl(boolean x0, int x1, long x2, String[] x3, ServiceEngineManagedServiceConfig x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }
}
