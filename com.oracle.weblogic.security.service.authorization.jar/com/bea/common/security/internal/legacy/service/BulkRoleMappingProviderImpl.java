package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.spi.BulkRoleMappingProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.BulkRoleMapper;
import weblogic.security.spi.BulkRoleProvider;
import weblogic.security.spi.Resource;
import weblogic.security.spi.RoleMapper;
import weblogic.security.spi.RoleProvider;

public class BulkRoleMappingProviderImpl implements ServiceLifecycleSpi {
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
         BulkRoleMapper bulkRoleMapper;
         if (provider instanceof BulkRoleProvider) {
            bulkRoleMapper = ((BulkRoleProvider)provider).getBulkRoleMapper();
            if (bulkRoleMapper == null) {
               throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("BulkRoleProvider", "BulkRoleMapper"));
            }
         } else {
            bulkRoleMapper = this.wrap(roleMapper);
         }

         return new BulkServiceImpl(bulkRoleMapper, roleMapper);
      }
   }

   private BulkRoleMapper wrap(final RoleMapper roleMapper) {
      return new BulkRoleMapper() {
         public Map getRoles(Subject subject, List resources, ContextHandler handler) {
            Map result = new HashMap(resources.size());
            Iterator ri = resources.iterator();

            while(ri.hasNext()) {
               Resource r = (Resource)ri.next();
               Map rx = roleMapper.getRoles(subject, r, handler);
               if (rx != null) {
                  result.put(r, rx);
               }
            }

            return result;
         }
      };
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private class BulkServiceImpl implements BulkRoleMappingProvider {
      private RoleMapper roleMapper;
      private BulkRoleMapper bulkRoleMapper;

      private BulkServiceImpl(BulkRoleMapper bulkRoleMapper, RoleMapper roleMapper) {
         this.roleMapper = roleMapper;
         this.bulkRoleMapper = bulkRoleMapper;
      }

      public RoleMapper getRoleMapper() {
         return this.roleMapper;
      }

      public BulkRoleMapper getBulkRoleMapper() {
         return this.bulkRoleMapper;
      }

      // $FF: synthetic method
      BulkServiceImpl(BulkRoleMapper x1, RoleMapper x2, Object x3) {
         this(x1, x2);
      }
   }
}
