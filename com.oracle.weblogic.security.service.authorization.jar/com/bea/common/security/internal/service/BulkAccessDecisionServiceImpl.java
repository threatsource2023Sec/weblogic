package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.BulkAccessDecisionService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.servicecfg.AccessDecisionServiceConfig;
import com.bea.common.security.spi.BulkAccessDecisionProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AccessDecision;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.BulkAccessDecision;
import weblogic.security.spi.Direction;
import weblogic.security.spi.InvalidPrincipalException;
import weblogic.security.spi.Resource;
import weblogic.security.spi.Result;

public class BulkAccessDecisionServiceImpl implements ServiceLifecycleSpi, BulkAccessDecisionService {
   private LoggerSpi logger;
   private AuditService auditService;
   private AccessDecision[] accessDecisions;
   private BulkAccessDecision[] bulkAccessDecisions;
   private String[] accessDecisionClassNames;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.AccessDecisionService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof AccessDecisionServiceConfig) {
         AccessDecisionServiceConfig myconfig = (AccessDecisionServiceConfig)config;
         String name = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got AuditService " + name);
         }

         String[] names = myconfig.getAccessDecisionNames();
         if (names != null && names.length >= 1) {
            this.accessDecisions = new AccessDecision[names.length];
            this.bulkAccessDecisions = new BulkAccessDecision[names.length];
            this.accessDecisionClassNames = new String[names.length];

            for(int i = 0; i < names.length; ++i) {
               Object provider = dependentServices.getService(names[i]);
               if (debug) {
                  this.logger.debug(method + " got AccessDecisionProvider " + names[i]);
               }

               if (provider instanceof BulkAccessDecisionProvider) {
                  BulkAccessDecisionProvider badp = (BulkAccessDecisionProvider)provider;
                  this.accessDecisions[i] = badp.getAccessDecision();
                  this.bulkAccessDecisions[i] = badp.getBulkAccessDecision();
                  this.accessDecisionClassNames[i] = badp.getAccessDecisionClassName();
               }
            }

            return Delegator.getProxy("com.bea.common.security.service.BulkAccessDecisionService", this);
         } else {
            throw new ServiceConfigurationException(ServiceLogger.getConfigurationMissingRequiredInfo(method, myconfig.getClass().getName(), "AccessDecisionNames"));
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "AccessDecisionServiceConfig"));
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
      if (roles != null) {
         for(Iterator iter = roles.keySet().iterator(); iter.hasNext(); rtn = rtn + " \"" + (String)iter.next() + "\"") {
         }
      }

      rtn = rtn + " ]";
      return rtn;
   }

   public Result[] isAccessAllowed(Identity identity, Map roles, Resource resource, ContextHandler contextHandler, Direction direction) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".isAccessAllowed" : null;
      if (debug) {
         this.logger.debug(method + " Identity=" + identity);
      }

      if (debug) {
         this.logger.debug(method + " Roles=" + this.printRoles(roles));
      }

      if (debug) {
         this.logger.debug(method + " Resource=" + resource);
      }

      if (debug) {
         this.logger.debug(method + " Direction=" + direction);
      }

      Subject subject = identity.getSubject();
      Result[] results = new Result[this.accessDecisions.length];

      for(int i = 0; i < this.accessDecisions.length; ++i) {
         try {
            results[i] = this.accessDecisions[i].isAccessAllowed(subject, roles, resource, contextHandler, direction);
            if (debug) {
               this.logger.debug(method + " AccessDecision returned " + results[i]);
            }
         } catch (RuntimeException var13) {
            if (var13 instanceof InvalidPrincipalException) {
               SecurityLogger.logInvalidPrincipalError(this.logger, this.accessDecisionClassNames[i], var13);
            } else {
               SecurityLogger.logAccessDecisionError(this.logger, this.accessDecisionClassNames[i], var13);
            }

            if (this.auditService.isAuditEnabled()) {
               AuditAtzEventImpl atzAuditEvent = new AuditAtzEventImpl(AuditSeverity.ERROR, identity, resource, contextHandler, direction, var13);
               this.auditService.writeEvent(atzAuditEvent);
            }

            throw var13;
         }
      }

      return results;
   }

   public List isAccessAllowed(Identity identity, Map roles, List resources, ContextHandler contextHandler, Direction direction) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".isAccessAllowed" : null;
      if (debug) {
         this.logger.debug(method + " Identity=" + identity);
         Iterator ri = resources.iterator();

         while(ri.hasNext()) {
            Resource resource = (Resource)ri.next();
            this.logger.debug(method + " Resource=" + resource);
            Map rr = (Map)roles.get(resource);
            if (rr != null) {
               this.logger.debug(method + " Roles=" + this.printRoles(rr));
            }
         }

         this.logger.debug(method + " Direction=" + direction);
      }

      Subject subject = identity.getSubject();
      List results = new ArrayList(this.accessDecisions.length);

      for(int i = 0; i < this.bulkAccessDecisions.length; ++i) {
         Map m = this.bulkAccessDecisions[i].isAccessAllowed(subject, roles, resources, contextHandler, direction);
         results.add(m);
         if (debug) {
            StringBuffer s = new StringBuffer();
            Iterator ei = m.entrySet().iterator();

            while(ei.hasNext()) {
               Map.Entry e = (Map.Entry)ei.next();
               s.append(e.getKey());
               s.append('=');
               s.append(((Result)e.getValue()).toString());
               if (ei.hasNext()) {
                  s.append(", ");
               }
            }

            this.logger.debug(method + " BulkAccessDecision returned " + s.toString());
         }
      }

      return results;
   }

   public String[] getAccessDecisionClassNames() {
      return this.accessDecisionClassNames;
   }
}
