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
import com.bea.common.security.service.ChallengeIdentityAssertionTokenService;
import com.bea.common.security.servicecfg.ChallengeIdentityAssertionTokenServiceConfig;
import com.bea.common.security.spi.ChallengeIdentityAsserter;
import com.bea.common.security.spi.ChallengeIdentityAssertionProvider;
import java.util.HashMap;
import javax.security.auth.callback.CallbackHandler;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.IdentityAssertionException;

public class ChallengeIdentityAssertionTokenServiceImpl implements ServiceLifecycleSpi, ChallengeIdentityAssertionTokenService {
   private LoggerSpi logger;
   private AuditService auditService;
   private HashMap tokenTypeToChallengeIdentityAsserterMap = new HashMap();

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.ChallengeIdentityAssertionTokenService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      ChallengeIdentityAssertionTokenServiceConfig myconfig = (ChallengeIdentityAssertionTokenServiceConfig)config;
      String name = myconfig.getAuditServiceName();
      this.auditService = (AuditService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got AuditService " + name);
      }

      ChallengeIdentityAssertionTokenServiceConfig.ChallengeIdentityAssertionProviderConfig[] iaConfigs = myconfig.getChallengeIdentityAssertionProviderConfigs();

      for(int i = 0; i < iaConfigs.length; ++i) {
         ChallengeIdentityAssertionProvider provider = (ChallengeIdentityAssertionProvider)dependentServices.getService(iaConfigs[i].getChallengeIdentityAssertionProviderName());
         ChallengeIdentityAsserter challengeIA = provider.getChallengeIdentityAsserter();
         if (challengeIA != null) {
            String[] activeTypes = iaConfigs[i].getActiveTypes();

            for(int j = 0; activeTypes != null && j < activeTypes.length; ++j) {
               String activeType = activeTypes[j];
               if (this.tokenTypeToChallengeIdentityAsserterMap.put(activeType, challengeIA) != null) {
                  throw new ServiceConfigurationException(ServiceLogger.getNonexclusiveToken("ChallengeIdentityAsserter", activeType));
               }
            }
         }
      }

      return Delegator.getProxy(ChallengeIdentityAssertionTokenService.class, this);
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private ChallengeIdentityAsserter getAsserter(String tokenType) throws IdentityAssertionException {
      ChallengeIdentityAsserter asserter = (ChallengeIdentityAsserter)this.tokenTypeToChallengeIdentityAsserterMap.get(tokenType);
      if (asserter == null) {
         throw new IdentityAssertionException(ServiceLogger.getUnsupportedToken(tokenType));
      } else {
         return (ChallengeIdentityAsserter)this.tokenTypeToChallengeIdentityAsserterMap.get(tokenType);
      }
   }

   public boolean isTokenTypeSupported(String tokenType) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".isTokenTypeSupported" : null;
      if (debug) {
         this.logger.debug(method + "(" + tokenType + ")");
      }

      return this.tokenTypeToChallengeIdentityAsserterMap.containsKey(tokenType);
   }

   public Object getChallengeToken(String tokenType, ContextHandler handler) throws IdentityAssertionException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getChallengeToken" : null;
      if (debug) {
         this.logger.debug(method + " (tokenType=" + tokenType + ")" + (handler == null ? "" : " (ContextHandler.size=" + handler.size() + ")"));
      }

      if (tokenType == null) {
         throw new IdentityAssertionException(SecurityLogger.getNullTokenTypeParam());
      } else {
         return this.getAsserter(tokenType).getChallengeToken(tokenType, handler);
      }
   }

   public Object getChallengeToken(String tokentype) throws IdentityAssertionException {
      return this.getChallengeToken(tokentype, (ContextHandler)null);
   }

   public ChallengeIdentityAssertionTokenService.ChallengeContext assertChallengeIdentity(String tokenType, Object token, ContextHandler ctx) throws IdentityAssertionException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".assertChallengeIdentity" : null;
      if (debug) {
         this.logger.debug(method + "(" + tokenType + ")");
      }

      return new ChallengeContextImpl(this.getAsserter(tokenType).assertChallengeIdentity(tokenType, token, ctx));
   }

   private class ChallengeContextImpl implements ChallengeIdentityAssertionTokenService.ChallengeContext {
      private ChallengeIdentityAsserter.ChallengeContext provider;

      private ChallengeContextImpl(ChallengeIdentityAsserter.ChallengeContext provider) {
         boolean debug = ChallengeIdentityAssertionTokenServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".constructor" : null;
         if (debug) {
            ChallengeIdentityAssertionTokenServiceImpl.this.logger.debug(method);
         }

         this.provider = provider;
      }

      public boolean hasChallengeIdentityCompleted() {
         boolean debug = ChallengeIdentityAssertionTokenServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".hasChallengeIdentityCompleted" : null;
         if (debug) {
            ChallengeIdentityAssertionTokenServiceImpl.this.logger.debug(method);
         }

         return this.provider.hasChallengeIdentityCompleted();
      }

      public Object getChallengeToken() {
         boolean debug = ChallengeIdentityAssertionTokenServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getChallengeToken" : null;
         if (debug) {
            ChallengeIdentityAssertionTokenServiceImpl.this.logger.debug(method);
         }

         if (this.hasChallengeIdentityCompleted()) {
            throw new IllegalStateException(ServiceLogger.getHasChallengeIdentityNotCompleted("getChallengeToken"));
         } else {
            return this.provider.getChallengeToken();
         }
      }

      public CallbackHandler getCallbackHandler() {
         boolean debug = ChallengeIdentityAssertionTokenServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getCallbackHandler" : null;
         if (debug) {
            ChallengeIdentityAssertionTokenServiceImpl.this.logger.debug(method);
         }

         if (!this.hasChallengeIdentityCompleted()) {
            throw new IllegalStateException(ServiceLogger.getHasChallengeIdentityAlreadyCompleted("getCallbackHandler"));
         } else {
            return this.provider.getCallbackHandler();
         }
      }

      public void continueChallengeIdentity(String tokenType, Object token, ContextHandler contextHandler) throws IdentityAssertionException {
         boolean debug = ChallengeIdentityAssertionTokenServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".continueChallengeIdentity" : null;
         if (debug) {
            ChallengeIdentityAssertionTokenServiceImpl.this.logger.debug(method);
         }

         if (this.hasChallengeIdentityCompleted()) {
            throw new IllegalStateException(ServiceLogger.getHasChallengeIdentityNotCompleted("continueChallengeIdentity"));
         } else {
            this.provider.continueChallengeIdentity(tokenType, token, contextHandler);
         }
      }

      // $FF: synthetic method
      ChallengeContextImpl(ChallengeIdentityAsserter.ChallengeContext x1, Object x2) {
         this(x1);
      }
   }
}
