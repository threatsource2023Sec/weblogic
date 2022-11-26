package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.JAASAuthenticationConfigurationService;
import com.bea.common.security.service.JAASAuthenticationService;
import com.bea.common.security.service.JAASLoginService;
import com.bea.common.security.servicecfg.JAASAuthenticationServiceConfig;
import com.bea.common.security.utils.UsernameUtils;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.AuditAtnEventV2.AtnEventTypeV2;

public class JAASAuthenticationServiceImpl implements ServiceLifecycleSpi, JAASAuthenticationService {
   private LoggerSpi logger;
   private JAASAuthenticationConfigurationService jaasAuthenticationConfigurationService;
   private JAASLoginService jaasLoginService;
   private AuditService auditService;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.JAASAuthenticationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof JAASAuthenticationServiceConfig) {
         JAASAuthenticationServiceConfig myconfig = (JAASAuthenticationServiceConfig)config;
         String name = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got AuditService " + name);
         }

         name = myconfig.getJAASAuthenticationConfigurationServiceName();
         this.jaasAuthenticationConfigurationService = (JAASAuthenticationConfigurationService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got JAASAuthenticationConfigurationService " + name);
         }

         name = myconfig.getJAASLoginServiceName();
         this.jaasLoginService = (JAASLoginService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got JAASLoginService " + name);
         }

         return Delegator.getProxy(JAASAuthenticationService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "JAASAuthenticationServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public Identity authenticate(CallbackHandler callbackHandler, ContextHandler contextHandler) throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".authenticate" : null;
      if (debug) {
         this.logger.debug(method);
      }

      CallbackHandlerWrapper callbackHandlerWrapper = new CallbackHandlerWrapper(callbackHandler, contextHandler, this.logger);

      AuditAtnEventImpl atnAuditEvent;
      try {
         Identity identity = this.jaasLoginService.login(this.jaasAuthenticationConfigurationService.getJAASAuthenticationConfigurationName(), callbackHandlerWrapper, contextHandler);
         if (this.auditService != null && this.auditService.isAuditEnabled()) {
            atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, UsernameUtils.formatUserName(identity.getUser()), contextHandler, AtnEventTypeV2.AUTHENTICATE, (Exception)null);
            this.auditService.writeEvent(atnAuditEvent);
         }

         return identity;
      } catch (LoginException var8) {
         if (this.auditService != null && this.auditService.isAuditEnabled()) {
            atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, UsernameUtils.formatUserName(callbackHandlerWrapper.getUser()), contextHandler, AtnEventTypeV2.AUTHENTICATE, var8);
            this.auditService.writeEvent(atnAuditEvent);
         }

         throw var8;
      } catch (RuntimeException var9) {
         if (this.auditService != null && this.auditService.isAuditEnabled()) {
            atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, UsernameUtils.formatUserName(callbackHandlerWrapper.getUser()), contextHandler, AtnEventTypeV2.AUTHENTICATE, var9);
            this.auditService.writeEvent(atnAuditEvent);
         }

         throw var9;
      }
   }
}
