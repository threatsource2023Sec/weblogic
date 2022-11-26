package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.ApiLogger;
import com.bea.common.security.internal.utils.CallbackUtils;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityAssertionCallbackService;
import com.bea.common.security.service.IdentityCacheService;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.JAASIdentityAssertionConfigurationService;
import com.bea.common.security.service.JAASLoginService;
import com.bea.common.security.servicecfg.IdentityAssertionCallbackServiceConfig;
import com.bea.common.security.utils.UsernameUtils;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.AuditAtnEventV2.AtnEventTypeV2;

public class IdentityAssertionCallbackServiceImpl implements ServiceLifecycleSpi, IdentityAssertionCallbackService {
   private LoggerSpi logger;
   private AuditService auditService;
   private IdentityCacheService identityCacheService;
   private IdentityService identityService;
   private JAASIdentityAssertionConfigurationService jaasIdentityAssertionConfigurationService;
   private JAASLoginService jaasLoginService;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.IdentityAssertionCallbackService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof IdentityAssertionCallbackServiceConfig) {
         IdentityAssertionCallbackServiceConfig myconfig = (IdentityAssertionCallbackServiceConfig)config;
         String name = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got AuditService " + name);
         }

         name = myconfig.getIdentityCacheServiceName();
         this.identityCacheService = (IdentityCacheService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got IdentityCacheService " + name);
         }

         name = myconfig.getIdentityServiceName();
         this.identityService = (IdentityService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got IdentityService " + name);
         }

         name = myconfig.getJAASIdentityAssertionConfigurationServiceName();
         this.jaasIdentityAssertionConfigurationService = (JAASIdentityAssertionConfigurationService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got JAASIdentityAssertionConfigurationService " + name);
         }

         name = myconfig.getJAASLoginServiceName();
         this.jaasLoginService = (JAASLoginService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got JAASLoginService " + name);
         }

         return Delegator.getProxy(IdentityAssertionCallbackService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "IdentityAssertionCallbackServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public Identity assertIdentity(CallbackHandler callbackHandler, ContextHandler contextHandler) throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".assertIdentity" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (callbackHandler == null) {
         if (debug) {
            this.logger.debug(method + " received null callback handler");
         }

         Identity anon = this.identityService.getAnonymousIdentity();
         if (anon != null) {
            if (this.auditService != null) {
               AuditAtnEventImpl atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, anon.getUsername(), contextHandler, AtnEventTypeV2.ASSERTIDENTITY, (Exception)null);
               this.auditService.writeEvent(atnAuditEvent);
            }

            if (debug) {
               this.logger.debug(method + " returning anonymous");
            }

            return anon;
         } else {
            if (debug) {
               this.logger.debug(method + " environment does not support anonymous, performing login");
            }

            throw new LoginException(ApiLogger.getNotSupportAnonymous());
         }
      } else {
         IdentityDomainNames user = CallbackUtils.getUser(callbackHandler, this.logger);
         boolean isVirtualUserAllowed = CallbackUtils.isVirtualUserAllowed(callbackHandler);
         Identity identity = null;
         if (!isVirtualUserAllowed) {
            identity = this.identityCacheService.getCachedIdentity(user, contextHandler);
         }

         if (identity != null) {
            if (debug) {
               this.logger.debug(method + " founded cached identity " + identity);
            }

            if (this.auditService != null) {
               AuditAtnEventImpl atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, UsernameUtils.formatUserName(identity.getUser()), contextHandler, AtnEventTypeV2.ASSERTIDENTITY, (Exception)null);
               this.auditService.writeEvent(atnAuditEvent);
            }

            return identity;
         } else {
            if (debug) {
               this.logger.debug(method + " did not find a cached identity.");
            }

            CallbackHandlerWrapper callbackHandlerWrapper = new CallbackHandlerWrapper(callbackHandler, contextHandler, this.logger);

            AuditAtnEventImpl atnAuditEvent;
            try {
               identity = this.jaasLoginService.login(this.jaasIdentityAssertionConfigurationService.getJAASIdentityAssertionConfigurationName(), callbackHandlerWrapper, contextHandler);
               AuditAtnEventImpl atnAuditEvent;
               if (identity != null) {
                  if (this.auditService.isAuditEnabled()) {
                     atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, UsernameUtils.formatUserName(identity.getUser()), contextHandler, AtnEventTypeV2.ASSERTIDENTITY, (Exception)null);
                     this.auditService.writeEvent(atnAuditEvent);
                  }

                  if (!isVirtualUserAllowed) {
                     this.identityCacheService.cacheIdentity(identity, contextHandler);
                  }
               } else if (this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, UsernameUtils.formatUserName(user), contextHandler, AtnEventTypeV2.ASSERTIDENTITY, (Exception)null);
                  this.auditService.writeEvent(atnAuditEvent);
               }

               return identity;
            } catch (LoginException var11) {
               if (this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, UsernameUtils.formatUserName(user), contextHandler, AtnEventTypeV2.ASSERTIDENTITY, var11);
                  this.auditService.writeEvent(atnAuditEvent);
               }

               throw var11;
            } catch (RuntimeException var12) {
               if (this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, UsernameUtils.formatUserName(user), contextHandler, AtnEventTypeV2.ASSERTIDENTITY, var12);
                  this.auditService.writeEvent(atnAuditEvent);
               }

               throw var12;
            }
         }
      }
   }
}
