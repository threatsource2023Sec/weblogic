package com.bea.common.security.internal.service;

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
import com.bea.common.security.service.IdentityCacheService;
import com.bea.common.security.service.IdentityImpersonationService;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.JAASIdentityAssertionConfigurationService;
import com.bea.common.security.service.JAASLoginService;
import com.bea.common.security.servicecfg.IdentityImpersonationServiceConfig;
import com.bea.common.security.utils.UsernameUtils;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.AuditAtnEventV2.AtnEventTypeV2;

public class IdentityImpersonationServiceImpl implements ServiceLifecycleSpi, IdentityImpersonationService {
   private LoggerSpi logger;
   private AuditService auditService;
   private IdentityService identityService;
   private IdentityCacheService identityCacheService;
   private JAASLoginService jaasLoginService;
   private JAASIdentityAssertionConfigurationService jaasIdentityAssertionConfigurationService;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.IdentityImpersonationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      IdentityImpersonationServiceConfig myconfig = (IdentityImpersonationServiceConfig)config;
      String name = myconfig.getAuditServiceName();
      this.auditService = (AuditService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got AuditService " + name);
      }

      name = myconfig.getIdentityServiceName();
      this.identityService = (IdentityService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got IdentityService " + name);
      }

      name = myconfig.getIdentityCacheServiceName();
      this.identityCacheService = (IdentityCacheService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got IdentityCacheService " + name);
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

      return Delegator.getProxy(IdentityImpersonationService.class, this);
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public Identity impersonateIdentity(String userName, ContextHandler contextHandler) throws LoginException {
      throw new UnsupportedOperationException("use impersonateIdentity(CallbackHandler callbackHandler, boolean virtual, ContextHandler contextHandler)");
   }

   public Identity impersonateIdentity(CallbackHandler callbackHandler, boolean virtual, ContextHandler contextHandler) throws LoginException {
      if (virtual) {
         throw new UnsupportedOperationException("virtual users not supported");
      } else {
         boolean debug = this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".impersonateIdentity" : null;
         if (debug) {
            this.logger.debug(method + "(" + callbackHandler + " ; " + contextHandler + ")");
         }

         IdentityDomainNames user = CallbackUtils.getUser(callbackHandler, this.logger);
         String userName = null;
         if (user != null) {
            userName = user.getName();
         }

         Identity identity;
         AuditAtnEventImpl atnAuditEvent;
         if (userName == null) {
            identity = this.identityService.getAnonymousIdentity();
            if (identity != null) {
               if (debug) {
                  this.logger.debug(method + " returning anonymous");
               }

               if (this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, identity.getUsername(), contextHandler, AtnEventTypeV2.IMPERSONATEIDENTITY, (Exception)null);
                  this.auditService.writeEvent(atnAuditEvent);
               }

               return identity;
            } else {
               if (debug) {
                  this.logger.debug(method + " environment does not support anonymous, performing login");
               }

               throw new LoginException(ApiLogger.getNotSupportAnonymous());
            }
         } else {
            identity = this.identityCacheService.getCachedIdentity(user, contextHandler);
            if (identity != null) {
               if (debug) {
                  this.logger.debug(method + " founded cached identity " + identity);
               }

               if (this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, UsernameUtils.formatUserName(identity.getUser()), contextHandler, AtnEventTypeV2.IMPERSONATEIDENTITY, (Exception)null);
                  this.auditService.writeEvent(atnAuditEvent);
               }

               return identity;
            } else {
               if (debug) {
                  this.logger.debug(method + " did not find a cached identity.");
               }

               AuditAtnEventImpl atnAuditEvent;
               try {
                  identity = this.jaasLoginService.login(this.jaasIdentityAssertionConfigurationService.getJAASIdentityAssertionConfigurationName(), callbackHandler, contextHandler);
                  if (identity != null) {
                     this.identityCacheService.cacheIdentity(identity, contextHandler);
                     if (this.auditService.isAuditEnabled()) {
                        atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, UsernameUtils.formatUserName(identity.getUser()), contextHandler, AtnEventTypeV2.IMPERSONATEIDENTITY, (Exception)null);
                        this.auditService.writeEvent(atnAuditEvent);
                     }
                  } else if (this.auditService.isAuditEnabled()) {
                     atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, UsernameUtils.formatUserName(user), contextHandler, AtnEventTypeV2.IMPERSONATEIDENTITY, (Exception)null);
                     this.auditService.writeEvent(atnAuditEvent);
                  }

                  return identity;
               } catch (LoginException var11) {
                  if (this.auditService.isAuditEnabled()) {
                     atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, UsernameUtils.formatUserName(user), contextHandler, AtnEventTypeV2.IMPERSONATEIDENTITY, var11);
                     this.auditService.writeEvent(atnAuditEvent);
                  }

                  throw var11;
               } catch (RuntimeException var12) {
                  if (this.auditService.isAuditEnabled()) {
                     atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, UsernameUtils.formatUserName(user), contextHandler, AtnEventTypeV2.IMPERSONATEIDENTITY, var12);
                     this.auditService.writeEvent(atnAuditEvent);
                  }

                  throw var12;
               }
            }
         }
      }
   }
}
