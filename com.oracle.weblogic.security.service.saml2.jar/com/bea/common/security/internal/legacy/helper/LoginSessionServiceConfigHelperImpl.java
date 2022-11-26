package com.bea.common.security.internal.legacy.helper;

import com.bea.common.engine.ServiceEngineConfig;
import com.bea.common.engine.ServiceEngineManagedServiceConfig;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.legacy.LoginSessionServiceConfigHelper;
import weblogic.management.security.RealmMBean;

class LoginSessionServiceConfigHelperImpl implements LoginSessionServiceConfigHelper {
   public String getLoginSessionServiceName(RealmMBean realmMBean) {
      return LoginSessionServiceConfigHelperImpl.class.getName() + "_" + realmMBean.getName();
   }

   public void addToConfig(ServiceEngineConfig serviceEngineConfig, String lifecycleImplLoaderName, RealmMBean realmMBean) {
      ServiceEngineManagedServiceConfig serviceConfig = serviceEngineConfig.addServiceEngineManagedServiceConfig(this.getLoginSessionServiceName(realmMBean), "com.bea.common.security.internal.service.LoginSessionServiceImpl", true);
      serviceConfig.addDependency(LoggerService.SERVICE_NAME);
      serviceConfig.setClassLoader(lifecycleImplLoaderName);
   }
}
