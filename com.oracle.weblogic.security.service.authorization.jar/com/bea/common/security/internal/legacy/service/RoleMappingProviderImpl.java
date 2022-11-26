package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.spi.RoleMappingProvider;
import weblogic.security.spi.RoleMapper;
import weblogic.security.spi.RoleProvider;

public class RoleMappingProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.RoleMappingService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      RoleMappingProviderConfig myconfig = (RoleMappingProviderConfig)config;
      RoleProvider provider = (RoleProvider)dependentServices.getService(myconfig.getRoleMappingProviderName());
      RoleMapper roleMapper = provider.getRoleMapper();
      if (roleMapper == null) {
         throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("RoleProvider", "RoleMapper"));
      } else {
         return new ServiceImpl(roleMapper);
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private class ServiceImpl implements RoleMappingProvider {
      private RoleMapper roleMapper;

      protected ServiceImpl(RoleMapper roleMapper) {
         this.roleMapper = (RoleMapper)Delegator.getProxy(RoleMapper.class, roleMapper);
      }

      public RoleMapper getRoleMapper() {
         return this.roleMapper;
      }
   }
}
