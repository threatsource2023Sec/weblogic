package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AccessDecisionService;
import com.bea.common.security.service.AdjudicationService;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.AuthorizationService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.servicecfg.AuthorizationServiceConfig;
import java.util.Map;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;
import weblogic.security.spi.Result;

public class AuthorizationServiceImpl implements ServiceLifecycleSpi, AuthorizationService {
   private LoggerSpi logger;
   private AuditService auditService;
   private AccessDecisionService accessDecisionService;
   private AdjudicationService adjudicationService;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.AuthorizationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof AuthorizationServiceConfig) {
         AuthorizationServiceConfig myconfig = (AuthorizationServiceConfig)config;
         String name = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got AuditService " + name);
         }

         name = myconfig.getAccessDecisionServiceName();
         this.accessDecisionService = (AccessDecisionService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got AccessDecisionService " + name);
         }

         name = myconfig.getAdjudicationServiceName();
         this.adjudicationService = (AdjudicationService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got AdjudicationService " + name);
         }

         return Delegator.getProxy("com.bea.common.security.service.AuthorizationService", this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "AuthorizationServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public boolean isAccessAllowed(Identity identity, Map roles, Resource resource, ContextHandler contextHandler, Direction direction) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".isAccessAllowed" : null;
      Result[] results = null;

      try {
         results = this.accessDecisionService.isAccessAllowed(identity, roles, resource, contextHandler, direction);
      } catch (RuntimeException var13) {
         if (debug) {
            this.logger.debug(method + " got an exception returning: false", var13);
         }

         return false;
      }

      boolean adjudication = false;

      try {
         adjudication = this.adjudicationService.adjudicate(results, resource, contextHandler);
      } catch (RuntimeException var12) {
         if (this.auditService.isAuditEnabled()) {
            AuditAtzEventImpl atzAuditEvent = new AuditAtzEventImpl(AuditSeverity.ERROR, identity, resource, contextHandler, direction, (Exception)null);
            this.auditService.writeEvent(atzAuditEvent);
         }

         if (debug) {
            this.logger.debug(method + " Adjudicator exception.", var12);
         }

         throw var12;
      }

      if (this.auditService.isAuditEnabled()) {
         AuditAtzEventImpl atzAuditEvent = new AuditAtzEventImpl(adjudication ? AuditSeverity.SUCCESS : AuditSeverity.FAILURE, identity, resource, contextHandler, direction, (Exception)null);
         this.auditService.writeEvent(atzAuditEvent);
      }

      if (debug) {
         this.logger.debug(method + " returning adjudicated: " + adjudication);
      }

      return adjudication;
   }
}
