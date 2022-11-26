package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.internal.service.SessionServiceImpl;
import weblogic.management.security.RealmMBean;

class SessionServiceConfigHelper {
   static String getServiceName(RealmMBean realmMBean) {
      return SessionServiceConfigHelper.class.getName() + "_" + realmMBean.getName();
   }

   static void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(getServiceName(realmMBean), SessionServiceImpl.class.getName(), false);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }
}
