package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.spi.ChallengeIdentityAsserter;
import com.bea.common.security.spi.ChallengeIdentityAssertionProvider;
import java.io.Serializable;
import javax.security.auth.callback.CallbackHandler;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuthenticationProvider;
import weblogic.security.spi.AuthenticationProviderV2;
import weblogic.security.spi.ChallengeIdentityAsserterV2;
import weblogic.security.spi.IdentityAsserter;
import weblogic.security.spi.IdentityAsserterV2;
import weblogic.security.spi.IdentityAssertionException;
import weblogic.security.spi.ProviderChallengeContext;
import weblogic.security.spi.SecurityProvider;

public class ChallengeIdentityAssertionProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.ChallengeIdentityAssertionService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      ChallengeIdentityAssertionProviderConfig myconfig = (ChallengeIdentityAssertionProviderConfig)config;
      SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getAuthenticationProviderName());
      if (provider instanceof AuthenticationProviderV2) {
         IdentityAsserterV2 identityAsserterV2 = ((AuthenticationProviderV2)provider).getIdentityAsserter();
         if (identityAsserterV2 == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AuthenticationProviderV2", "IdentityAsserterV2"));
         } else {
            ChallengeIdentityAsserterV2 v2 = identityAsserterV2 instanceof ChallengeIdentityAsserterV2 ? (ChallengeIdentityAsserterV2)identityAsserterV2 : null;
            ChallengeIdentityAsserter challengeIA = v2 != null ? new ChallengeIdentityAsserterV2Adapter(v2) : null;
            return new ProviderImpl(challengeIA);
         }
      } else if (provider instanceof AuthenticationProvider) {
         IdentityAsserter identityAsserterV1 = ((AuthenticationProvider)provider).getIdentityAsserter();
         if (identityAsserterV1 == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AuthenticationProvider", "IdentityAsserter"));
         } else {
            return new ProviderImpl((ChallengeIdentityAsserter)null);
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getNotInstanceof("AuthenticationProvider"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private static class ChallengeContextV1Impl implements ChallengeIdentityAsserter.ChallengeContext, Serializable {
      private CallbackHandler callbackHandler;
      private static final long serialVersionUID = 6255076948770623021L;
      private transient LoggerSpi logger = null;

      private ChallengeContextV1Impl(CallbackHandler callbackHandler) {
         this.callbackHandler = callbackHandler;
      }

      private ChallengeContextV1Impl(CallbackHandler callbackHandler, LoggerSpi logger) {
         this.callbackHandler = callbackHandler;
         this.logger = logger;
      }

      public boolean hasChallengeIdentityCompleted() {
         boolean debug = this.logger == null ? false : this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".hasChallengeIdentityCompleted" : null;
         if (debug) {
            this.logger.debug(method);
         }

         return true;
      }

      public Object getChallengeToken() {
         boolean debug = this.logger == null ? false : this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getChallengeToken" : null;
         if (debug) {
            this.logger.debug(method);
         }

         throw new IllegalStateException(ServiceLogger.getMultipleChallengesNotSupported());
      }

      public CallbackHandler getCallbackHandler() {
         boolean debug = this.logger == null ? false : this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getCallbackHandler" : null;
         if (debug) {
            this.logger.debug(method);
         }

         return this.callbackHandler;
      }

      public void continueChallengeIdentity(String tokenType, Object token, ContextHandler contextHandler) throws IdentityAssertionException {
         boolean debug = this.logger == null ? false : this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".continueChallengeIdentity" : null;
         if (debug) {
            this.logger.debug(method);
         }

         throw new IllegalStateException(ServiceLogger.getMultipleChallengesNotSupported());
      }
   }

   private static class ChallengeContextV2Impl implements ChallengeIdentityAsserter.ChallengeContext, Serializable {
      private ProviderChallengeContext v2Ctx;
      private static final long serialVersionUID = 3101004585575274049L;
      private transient ChallengeIdentityAsserterV2 v2;
      private transient LoggerSpi logger;

      private ChallengeContextV2Impl(ProviderChallengeContext v2Ctx) {
         this.v2 = null;
         this.logger = null;
         this.v2Ctx = v2Ctx;
      }

      private ChallengeContextV2Impl(ProviderChallengeContext v2Ctx, ChallengeIdentityAsserterV2 v2, LoggerSpi logger) {
         this.v2 = null;
         this.logger = null;
         this.v2 = v2;
         this.logger = logger;
         boolean debug = logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".constructor" : null;
         if (debug) {
            logger.debug(method);
         }

         this.v2Ctx = v2Ctx;
      }

      public boolean hasChallengeIdentityCompleted() {
         boolean debug = this.logger == null ? false : this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".hasChallengeIdentityCompleted" : null;
         if (debug) {
            this.logger.debug(method);
         }

         return this.v2Ctx.hasChallengeIdentityCompleted();
      }

      public Object getChallengeToken() {
         boolean debug = this.logger == null ? false : this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getChallengeToken" : null;
         if (debug) {
            this.logger.debug(method);
         }

         if (this.hasChallengeIdentityCompleted()) {
            throw new IllegalStateException(ServiceLogger.getHasChallengeIdentityNotCompleted("getChallengeToken"));
         } else {
            return this.v2Ctx.getChallengeToken();
         }
      }

      public CallbackHandler getCallbackHandler() {
         boolean debug = this.logger == null ? false : this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getCallbackHandler" : null;
         if (debug) {
            this.logger.debug(method);
         }

         if (!this.hasChallengeIdentityCompleted()) {
            throw new IllegalStateException(ServiceLogger.getHasChallengeIdentityAlreadyCompleted("getCallbackHandler"));
         } else {
            return this.v2Ctx.getCallbackHandler();
         }
      }

      public void continueChallengeIdentity(String tokenType, Object token, ContextHandler contextHandler) throws IdentityAssertionException {
         boolean debug = this.logger == null ? false : this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".continueChallengeIdentity" : null;
         if (debug) {
            this.logger.debug(method);
         }

         if (this.hasChallengeIdentityCompleted()) {
            throw new IllegalStateException(ServiceLogger.getHasChallengeIdentityNotCompleted("continueChallengeIdentity"));
         } else {
            if (this.v2 != null) {
               this.v2.continueChallengeIdentity(this.v2Ctx, tokenType, token, contextHandler);
            }

         }
      }

      // $FF: synthetic method
      ChallengeContextV2Impl(ProviderChallengeContext x0, ChallengeIdentityAsserterV2 x1, LoggerSpi x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private class ChallengeIdentityAsserterV2Adapter implements ChallengeIdentityAsserter {
      private ChallengeIdentityAsserterV2 v2;

      private ChallengeIdentityAsserterV2Adapter(ChallengeIdentityAsserterV2 v2) {
         this.v2 = v2;
      }

      public Object getChallengeToken(String tokenType) {
         return this.getChallengeToken(tokenType, (ContextHandler)null);
      }

      public Object getChallengeToken(String tokenType, ContextHandler handler) {
         boolean debug = ChallengeIdentityAssertionProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getChallengeToken" : null;
         if (debug) {
            ChallengeIdentityAssertionProviderImpl.this.logger.debug(method + " (tokenType=" + tokenType + ")" + (handler == null ? "" : " (ContextHandler.size=" + handler.size() + ")"));
         }

         return this.v2.getChallengeToken(tokenType, handler);
      }

      public ChallengeIdentityAsserter.ChallengeContext assertChallengeIdentity(String tokenType, Object token, ContextHandler contextHandler) throws IdentityAssertionException {
         boolean debug = ChallengeIdentityAssertionProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".assertChallengeIdentity" : null;
         if (debug) {
            ChallengeIdentityAssertionProviderImpl.this.logger.debug(method + "(" + tokenType + ")");
         }

         return new ChallengeContextV2Impl(this.v2.assertChallengeIdentity(tokenType, token, contextHandler), this.v2, ChallengeIdentityAssertionProviderImpl.this.logger);
      }

      // $FF: synthetic method
      ChallengeIdentityAsserterV2Adapter(ChallengeIdentityAsserterV2 x1, Object x2) {
         this(x1);
      }
   }

   private class ProviderImpl implements ChallengeIdentityAssertionProvider {
      private ChallengeIdentityAsserter provider;

      private ProviderImpl(ChallengeIdentityAsserter provider) {
         this.provider = provider;
      }

      public ChallengeIdentityAsserter getChallengeIdentityAsserter() {
         return this.provider;
      }

      // $FF: synthetic method
      ProviderImpl(ChallengeIdentityAsserter x1, Object x2) {
         this(x1);
      }
   }
}
