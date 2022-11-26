package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.internal.utils.collections.CombinedMap;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.BulkRoleMappingService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.servicecfg.RoleMappingServiceConfig;
import com.bea.common.security.spi.BulkRoleMappingProvider;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.BulkRoleMapper;
import weblogic.security.spi.Resource;
import weblogic.security.spi.RoleMapper;

public class BulkRoleMappingServiceImpl implements ServiceLifecycleSpi, BulkRoleMappingService {
   private LoggerSpi logger;
   private AuditService auditService;
   private RoleMapper[] roleMappers;
   private BulkRoleMapper[] bulkRoleMappers;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.RoleMappingService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof RoleMappingServiceConfig) {
         RoleMappingServiceConfig myconfig = (RoleMappingServiceConfig)config;
         String auditServiceName = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(auditServiceName);
         if (debug) {
            this.logger.debug(method + " got AuditService " + auditServiceName);
         }

         String[] names = myconfig.getRoleMapperNames();
         if (names != null && names.length >= 1) {
            this.roleMappers = new RoleMapper[names.length];
            this.bulkRoleMappers = new BulkRoleMapper[names.length];

            for(int i = 0; i < names.length; ++i) {
               BulkRoleMappingProvider provider = (BulkRoleMappingProvider)dependentServices.getService(names[i]);
               if (debug) {
                  this.logger.debug(method + " got RoleMapper " + names[i]);
               }

               this.roleMappers[i] = provider.getRoleMapper();
               this.bulkRoleMappers[i] = provider.getBulkRoleMapper();
            }

            return Delegator.getProxy("com.bea.common.security.service.BulkRoleMappingService", this);
         } else {
            throw new ServiceConfigurationException(ServiceLogger.getConfigurationMissingRequiredInfo(method, myconfig.getClass().getName(), "RoleMapperNames"));
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "RoleMappingServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private String printRoles(Map roles) {
      String rtn = "[";

      for(Iterator iter = roles.keySet().iterator(); iter.hasNext(); rtn = rtn + " \"" + (String)iter.next() + "\"") {
      }

      rtn = rtn + " ]";
      return rtn;
   }

   public Map getRoles(Identity identity, Resource resource, ContextHandler contextHandler) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getRoles" : null;
      if (debug) {
         this.logger.debug(method + " Identity=" + identity);
      }

      if (debug) {
         this.logger.debug(method + " Resource=" + resource);
      }

      Subject subject = identity.getSubject();
      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditRoleEventImpl(AuditSeverity.INFORMATION, identity, resource, contextHandler, (Map)null, (Exception)null));
      }

      Map allRoles = null;
      if (this.roleMappers.length == 1) {
         allRoles = this.roleMappers[0].getRoles(subject, resource, contextHandler);
      } else {
         Map[] maps = new Map[this.roleMappers.length];

         for(int i = 0; i < this.roleMappers.length; ++i) {
            maps[i] = this.roleMappers[i].getRoles(subject, resource, contextHandler);
         }

         allRoles = new CombinedMap(maps);
      }

      if (this.auditService.isAuditEnabled()) {
         this.auditService.writeEvent(new AuditRoleEventImpl(AuditSeverity.INFORMATION, identity, resource, contextHandler, (Map)null, (Exception)null));
      }

      if (debug) {
         this.logger.debug(method + " returning " + this.printRoles((Map)allRoles));
      }

      return (Map)allRoles;
   }

   public Map getRoles(Identity identity, List resources, ContextHandler contextHandler) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getRoles" : null;
      Resource resource;
      if (debug) {
         this.logger.debug(method + " Identity=" + identity);
         Iterator ri = resources.iterator();

         while(ri.hasNext()) {
            resource = (Resource)ri.next();
            this.logger.debug(method + " Resource=" + resource);
         }
      }

      Subject subject = identity.getSubject();
      resource = null;
      Object allRoles;
      if (this.bulkRoleMappers.length == 1) {
         allRoles = this.bulkRoleMappers[0].getRoles(subject, resources, contextHandler);
      } else {
         Map[] maps = new Map[this.bulkRoleMappers.length];

         for(int i = 0; i < this.bulkRoleMappers.length; ++i) {
            maps[i] = this.bulkRoleMappers[i].getRoles(subject, resources, contextHandler);
         }

         allRoles = new CombinedMap(maps);
      }

      if (debug) {
         StringBuffer sb = new StringBuffer();
         sb.append(method).append(" - ");
         sb.append(this.bulkRoleMappers.length).append(" RoleMappers returned: ");
         Iterator ri = resources.iterator();

         while(ri.hasNext()) {
            Resource resource = (Resource)ri.next();
            sb.append("Resource: ").append(resource.toString());
            Map roles = (Map)((Map)allRoles).get(resource);
            sb.append(roles != null ? this.printRoles(roles) : "<none>");
         }

         this.logger.debug(sb.toString());
      }

      return (Map)allRoles;
   }
}
