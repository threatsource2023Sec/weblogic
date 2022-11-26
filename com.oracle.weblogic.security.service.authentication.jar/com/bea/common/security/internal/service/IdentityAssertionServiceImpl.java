package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityAssertionCallbackService;
import com.bea.common.security.service.IdentityAssertionService;
import com.bea.common.security.service.IdentityAssertionTokenService;
import com.bea.common.security.servicecfg.IdentityAssertionServiceConfig;
import javax.security.auth.login.LoginException;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.IdentityAssertionException;

public class IdentityAssertionServiceImpl implements ServiceLifecycleSpi, IdentityAssertionService {
   private LoggerSpi logger;
   private AuditService auditService;
   private IdentityAssertionTokenService identityAssertionTokenService;
   private IdentityAssertionCallbackService identityAssertionCallbackService;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.IdentityAssertionService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      IdentityAssertionServiceConfig myconfig = (IdentityAssertionServiceConfig)config;
      String name = myconfig.getAuditServiceName();
      this.auditService = (AuditService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got AuditService " + name);
      }

      name = myconfig.getIdentityAssertionTokenServiceName();
      this.identityAssertionTokenService = (IdentityAssertionTokenService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got IdentityAssertionTokenService " + name);
      }

      name = myconfig.getIdentityAssertionCallbackServiceName();
      this.identityAssertionCallbackService = (IdentityAssertionCallbackService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got IdentityAssertionCallbackService " + name);
      }

      return Delegator.getProxy(IdentityAssertionService.class, this);
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public boolean isTokenTypeSupported(String tokenType) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".isTokenTypeSupported" : null;
      if (debug) {
         this.logger.debug(method + "(" + tokenType + ")");
      }

      return this.identityAssertionTokenService.isTokenTypeSupported(tokenType);
   }

   public Identity assertIdentity(String tokenType, Object token, ContextHandler contextHandler) throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".assertIdentity" : null;
      if (debug) {
         this.logger.debug(method + "(" + tokenType + ")");
      }

      try {
         return this.identityAssertionCallbackService.assertIdentity(this.identityAssertionTokenService.assertIdentity(tokenType, token, contextHandler), contextHandler);
      } catch (IdentityAssertionException var7) {
         throw new LoginException(SecurityLogger.getIdentityAssertionFailedExc(var7.toString()));
      }
   }
}
