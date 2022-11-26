package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.ChallengeIdentityAssertionService;
import com.bea.common.security.service.ChallengeIdentityAssertionTokenService;
import com.bea.common.security.service.Identity;
import com.bea.common.security.service.IdentityAssertionCallbackService;
import com.bea.common.security.servicecfg.ChallengeIdentityAssertionServiceConfig;
import javax.security.auth.login.LoginException;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.IdentityAssertionException;
import weblogic.security.spi.AuditAtnEventV2.AtnEventTypeV2;

public class ChallengeIdentityAssertionServiceImpl implements ServiceLifecycleSpi, ChallengeIdentityAssertionService {
   private LoggerSpi logger;
   private AuditService auditService;
   private ChallengeIdentityAssertionTokenService challengeIdentityAssertionTokenService;
   private IdentityAssertionCallbackService identityAssertionCallbackService;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.ChallengeIdentityAssertionService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      ChallengeIdentityAssertionServiceConfig myconfig = (ChallengeIdentityAssertionServiceConfig)config;
      String name = myconfig.getAuditServiceName();
      this.auditService = (AuditService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got AuditService " + name);
      }

      name = myconfig.getChallengeIdentityAssertionTokenServiceName();
      this.challengeIdentityAssertionTokenService = (ChallengeIdentityAssertionTokenService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got ChallengeIdentityAssertionTokenService " + name);
      }

      name = myconfig.getIdentityAssertionCallbackServiceName();
      this.identityAssertionCallbackService = (IdentityAssertionCallbackService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got IdentityAssertionCallbackService " + name);
      }

      return Delegator.getProxy(ChallengeIdentityAssertionService.class, this);
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

      return this.challengeIdentityAssertionTokenService.isTokenTypeSupported(tokenType);
   }

   public Object getChallengeToken(String tokenType, ContextHandler ctx) throws IdentityAssertionException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getChallengeToken" : null;
      if (debug) {
         this.logger.debug(method + " (tokenType=" + tokenType + ")" + (ctx == null ? "" : " (ContextHandler.size=" + ctx.size() + ")"));
      }

      try {
         return this.challengeIdentityAssertionTokenService.getChallengeToken(tokenType, ctx);
      } catch (RuntimeException var7) {
         if (this.auditService.isAuditEnabled()) {
            AuditAtnEventImpl atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, "", ctx, AtnEventTypeV2.ASSERTIDENTITY, var7);
            this.auditService.writeEvent(atnAuditEvent);
         }

         throw var7;
      }
   }

   public Object getChallengeToken(String tokentype) throws IdentityAssertionException {
      return this.getChallengeToken(tokentype, (ContextHandler)null);
   }

   public ChallengeIdentityAssertionService.ChallengeContext assertChallengeIdentity(String tokenType, Object token, ContextHandler ctx) throws LoginException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".assertChallengeIdentity" : null;
      if (debug) {
         this.logger.debug(method + "(" + tokenType + ")");
      }

      AuditAtnEventImpl atnAuditEvent;
      try {
         return new ChallengeContextImpl(this.challengeIdentityAssertionTokenService.assertChallengeIdentity(tokenType, token, ctx), ctx);
      } catch (IdentityAssertionException var8) {
         if (this.auditService.isAuditEnabled()) {
            atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, "", ctx, AtnEventTypeV2.ASSERTIDENTITY, var8);
            this.auditService.writeEvent(atnAuditEvent);
         }

         if (debug) {
            this.logger.debug(method + " - IdentityAssertionException");
         }

         throw new LoginException(var8.toString());
      } catch (RuntimeException var9) {
         if (this.auditService.isAuditEnabled()) {
            atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, "", ctx, AtnEventTypeV2.ASSERTIDENTITY, var9);
            this.auditService.writeEvent(atnAuditEvent);
         }

         if (debug) {
            this.logger.debug(method + " - RuntimeException");
         }

         throw var9;
      }
   }

   private class ChallengeContextImpl implements ChallengeIdentityAssertionService.ChallengeContext {
      private ChallengeIdentityAssertionTokenService.ChallengeContext provider;
      private Identity identity;

      private ChallengeContextImpl(ChallengeIdentityAssertionTokenService.ChallengeContext provider, ContextHandler contextHandler) throws LoginException {
         boolean debug = ChallengeIdentityAssertionServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".constructor" : null;
         if (debug) {
            ChallengeIdentityAssertionServiceImpl.this.logger.debug(method);
         }

         this.provider = provider;
         if (this.hasChallengeIdentityCompleted()) {
            this.identity = ChallengeIdentityAssertionServiceImpl.this.identityAssertionCallbackService.assertIdentity(provider.getCallbackHandler(), contextHandler);
         }

      }

      public boolean hasChallengeIdentityCompleted() {
         boolean debug = ChallengeIdentityAssertionServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".hasChallengeIdentityCompleted" : null;
         if (debug) {
            ChallengeIdentityAssertionServiceImpl.this.logger.debug(method);
         }

         return this.provider.hasChallengeIdentityCompleted();
      }

      public Object getChallengeToken() {
         boolean debug = ChallengeIdentityAssertionServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getChallengeToken" : null;
         if (debug) {
            ChallengeIdentityAssertionServiceImpl.this.logger.debug(method);
         }

         if (this.hasChallengeIdentityCompleted()) {
            throw new IllegalStateException(ServiceLogger.getHasChallengeIdentityNotCompleted("getChallengeToken"));
         } else {
            return this.provider.getChallengeToken();
         }
      }

      public Identity getIdentity() {
         boolean debug = ChallengeIdentityAssertionServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getIdentity" : null;
         if (debug) {
            ChallengeIdentityAssertionServiceImpl.this.logger.debug(method);
         }

         return this.identity;
      }

      public void continueChallengeIdentity(String tokenType, Object token, ContextHandler contextHandler) throws LoginException {
         boolean debug = ChallengeIdentityAssertionServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".continueChallengeIdentity" : null;
         if (debug) {
            ChallengeIdentityAssertionServiceImpl.this.logger.debug(method);
         }

         if (this.hasChallengeIdentityCompleted()) {
            throw new IllegalStateException(ServiceLogger.getHasChallengeIdentityNotCompleted("continueChallengeIdentity"));
         } else {
            AuditAtnEventImpl atnAuditEvent;
            try {
               this.provider.continueChallengeIdentity(tokenType, token, contextHandler);
            } catch (IdentityAssertionException var8) {
               if (ChallengeIdentityAssertionServiceImpl.this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, "", contextHandler, AtnEventTypeV2.ASSERTIDENTITY, var8);
                  ChallengeIdentityAssertionServiceImpl.this.auditService.writeEvent(atnAuditEvent);
               }

               if (debug) {
                  ChallengeIdentityAssertionServiceImpl.this.logger.debug(method + " - IdentityAssertionException");
               }

               throw new LoginException(var8.toString());
            } catch (RuntimeException var9) {
               if (ChallengeIdentityAssertionServiceImpl.this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, "", contextHandler, AtnEventTypeV2.ASSERTIDENTITY, var9);
                  ChallengeIdentityAssertionServiceImpl.this.auditService.writeEvent(atnAuditEvent);
               }

               if (debug) {
                  ChallengeIdentityAssertionServiceImpl.this.logger.debug(method + " - RuntimeException");
               }

               throw var9;
            }

            if (this.hasChallengeIdentityCompleted()) {
               this.identity = ChallengeIdentityAssertionServiceImpl.this.identityAssertionCallbackService.assertIdentity(this.provider.getCallbackHandler(), contextHandler);
            }

         }
      }

      // $FF: synthetic method
      ChallengeContextImpl(ChallengeIdentityAssertionTokenService.ChallengeContext x1, ContextHandler x2, Object x3) throws LoginException {
         this(x1, x2);
      }
   }
}
