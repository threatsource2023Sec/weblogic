package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.internal.utils.Delegator;
import javax.security.auth.callback.CallbackHandler;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuthenticationProvider;
import weblogic.security.spi.AuthenticationProviderV2;
import weblogic.security.spi.IdentityAsserter;
import weblogic.security.spi.IdentityAsserterV2;
import weblogic.security.spi.IdentityAssertionException;
import weblogic.security.spi.SecurityProvider;

public class IdentityAsserterV2Impl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.IdentityAssertionService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      IdentityAsserterV2Config myconfig = (IdentityAsserterV2Config)config;
      SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getAuthenticationProviderName());
      if (provider instanceof AuthenticationProviderV2) {
         IdentityAsserterV2 identityAsserterV2 = ((AuthenticationProviderV2)provider).getIdentityAsserter();
         if (identityAsserterV2 == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AuthenticationProviderV2", "IdentityAsserterV2"));
         } else {
            return Delegator.getProxy(IdentityAsserterV2.class, identityAsserterV2);
         }
      } else if (provider instanceof AuthenticationProvider) {
         IdentityAsserter identityAsserter = ((AuthenticationProvider)provider).getIdentityAsserter();
         if (identityAsserter == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AuthenticationProvider", "IdentityAsserter"));
         } else {
            return new IdentityAsserterV1Adapter(identityAsserter);
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

   private class IdentityAsserterV1Adapter implements IdentityAsserterV2 {
      private IdentityAsserter v1;

      private IdentityAsserterV1Adapter(IdentityAsserter v1) {
         this.v1 = v1;
      }

      public CallbackHandler assertIdentity(String type, Object token, ContextHandler handler) throws IdentityAssertionException {
         return this.v1.assertIdentity(type, token);
      }

      // $FF: synthetic method
      IdentityAsserterV1Adapter(IdentityAsserter x1, Object x2) {
         this(x1);
      }
   }
}
