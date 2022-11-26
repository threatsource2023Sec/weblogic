package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.CredentialMappingService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.SecurityTokenService;
import com.bea.common.security.service.ServiceTokenRequestContext;
import com.bea.common.security.service.TokenResponseContext;
import com.bea.common.security.servicecfg.SecurityTokenServiceConfig;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.Resource;

public class SecurityTokenServiceImpl implements ServiceLifecycleSpi, SecurityTokenService {
   private LoggerSpi logger;
   private boolean debug = false;
   private AuditService auditService;
   private CredentialMappingService credMapService;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.SecurityTokenService");
      this.debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (this.debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof SecurityTokenServiceConfig) {
         SecurityTokenServiceConfig myconfig = (SecurityTokenServiceConfig)config;
         String auditServiceName = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(auditServiceName);
         if (this.debug) {
            this.logger.debug(method + " got AuditService " + auditServiceName);
         }

         String credMapServiceName = myconfig.getCredentialMappingServiceName();
         if (credMapServiceName != null && !credMapServiceName.equals("")) {
            this.credMapService = (CredentialMappingService)dependentServices.getService(credMapServiceName);
            if (this.debug) {
               this.logger.debug(method + " got CredentialMapping Service " + credMapServiceName);
            }

            return Delegator.getProxy(SecurityTokenService.class, this);
         } else {
            throw new ServiceConfigurationException(ServiceLogger.getConfigurationMissingRequiredInfo(method, myconfig.getClass().getName(), "CredentialMappingServiceName"));
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "SecurityTokenServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public Object issueToken(String tokenType, Identity requestor, Identity secIdentity, Resource resource, ContextHandler handler) {
      String method = this.getClass().getName() + ".issueToken";
      if (this.debug) {
         this.logger.debug(method + " TokenType=" + tokenType);
         this.logger.debug(method + " Requestor Identity=" + requestor);
         this.logger.debug(method + " Security Identity=" + secIdentity);
         this.logger.debug(method + " Resource=" + resource);
      }

      try {
         Object[] tokens = this.credMapService.getCredentials(requestor, secIdentity, resource, handler, tokenType);
         return tokens != null && tokens.length != 0 ? tokens[0] : null;
      } catch (RuntimeException var8) {
         if (this.debug) {
            this.logger.debug(method + " failure.", var8);
         }

         throw var8;
      }
   }

   public TokenResponseContext challengeIssueToken(ServiceTokenRequestContext ctx) {
      throw new UnsupportedOperationException("challenge mechanism not supported");
   }
}
