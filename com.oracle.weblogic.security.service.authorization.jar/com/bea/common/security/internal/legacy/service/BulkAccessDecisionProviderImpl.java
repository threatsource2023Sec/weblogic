package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.spi.BulkAccessDecisionProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AccessDecision;
import weblogic.security.spi.AuthorizationProvider;
import weblogic.security.spi.BulkAccessDecision;
import weblogic.security.spi.BulkAuthorizationProvider;
import weblogic.security.spi.Direction;
import weblogic.security.spi.InvalidPrincipalException;
import weblogic.security.spi.Resource;

public class BulkAccessDecisionProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.AccessDecisionService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      AccessDecisionProviderConfig myconfig = (AccessDecisionProviderConfig)config;
      AuthorizationProvider provider = (AuthorizationProvider)dependentServices.getService(myconfig.getAuthorizationProviderName());
      AccessDecision accessDecision = provider.getAccessDecision();
      if (accessDecision == null) {
         throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AuthorizationProvider", "AccessDecision"));
      } else {
         BulkAccessDecision bulkAccessDecision;
         if (provider instanceof BulkAuthorizationProvider) {
            bulkAccessDecision = ((BulkAuthorizationProvider)provider).getBulkAccessDecision();
            if (bulkAccessDecision == null) {
               throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("BulkAuthorizationProvider", "BulkAccessDecision"));
            }
         } else {
            bulkAccessDecision = this.wrap(accessDecision);
         }

         return new BulkServiceImpl(bulkAccessDecision, accessDecision);
      }
   }

   private BulkAccessDecision wrap(final AccessDecision accessDecision) {
      return new BulkAccessDecision() {
         public Map isAccessAllowed(Subject subject, Map roles, List resources, ContextHandler handler, Direction direction) throws InvalidPrincipalException {
            Map result = new HashMap(resources.size());
            Iterator ri = resources.iterator();

            while(ri.hasNext()) {
               Resource r = (Resource)ri.next();
               result.put(r, accessDecision.isAccessAllowed(subject, (Map)roles.get(r), r, handler, direction));
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

   private class BulkServiceImpl implements BulkAccessDecisionProvider {
      private AccessDecision accessDecision;
      private String accessDecisionClassName;
      private BulkAccessDecision bulkAccessDecision;

      private BulkServiceImpl(BulkAccessDecision bulkAccessDecision, AccessDecision accessDecision) {
         this.accessDecision = (AccessDecision)Delegator.getProxy("weblogic.security.spi.AccessDecision", accessDecision);
         this.accessDecisionClassName = accessDecision.getClass().getName();
         this.bulkAccessDecision = bulkAccessDecision;
      }

      public AccessDecision getAccessDecision() {
         return this.accessDecision;
      }

      public String getAccessDecisionClassName() {
         return this.accessDecisionClassName;
      }

      public BulkAccessDecision getBulkAccessDecision() {
         return this.bulkAccessDecision;
      }

      // $FF: synthetic method
      BulkServiceImpl(BulkAccessDecision x1, AccessDecision x2, Object x3) {
         this(x1, x2);
      }
   }
}
