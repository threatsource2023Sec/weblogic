package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.spi.JAASIdentityAssertionProvider;
import javax.security.auth.login.AppConfigurationEntry;
import weblogic.security.spi.AuthenticationProvider;
import weblogic.security.spi.AuthenticationProviderV2;
import weblogic.security.spi.SecurityProvider;

public class JAASIdentityAssertionProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.IdentityAssertionService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      JAASIdentityAssertionProviderConfig myconfig = (JAASIdentityAssertionProviderConfig)config;
      SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getAuthenticationProviderName());
      if (provider instanceof AuthenticationProviderV2) {
         return new V2Wrapper((AuthenticationProviderV2)provider);
      } else {
         return provider instanceof AuthenticationProvider ? new V1Wrapper((AuthenticationProvider)provider) : new EmptyWrapper();
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private class EmptyWrapper implements JAASIdentityAssertionProvider {
      private EmptyWrapper() {
      }

      public boolean supportsIdentityAssertion() {
         boolean debug = JAASIdentityAssertionProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".supportsIdentityAssertion" : null;
         if (debug) {
            JAASIdentityAssertionProviderImpl.this.logger.debug(method);
         }

         return false;
      }

      public AppConfigurationEntry getAssertionModuleConfiguration() {
         return null;
      }

      public ClassLoader getClassLoader() {
         return null;
      }

      // $FF: synthetic method
      EmptyWrapper(Object x1) {
         this();
      }
   }

   private class V1Wrapper implements JAASIdentityAssertionProvider {
      private AuthenticationProvider provider;

      private V1Wrapper(AuthenticationProvider provider) {
         this.provider = provider;
      }

      public boolean supportsIdentityAssertion() {
         boolean debug = JAASIdentityAssertionProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".supportsIdentityAssertion" : null;
         if (debug) {
            JAASIdentityAssertionProviderImpl.this.logger.debug(method);
         }

         return this.getAssertionModuleConfiguration() != null;
      }

      public AppConfigurationEntry getAssertionModuleConfiguration() {
         boolean debug = JAASIdentityAssertionProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getAssertionModuleConfiguration" : null;
         if (debug) {
            JAASIdentityAssertionProviderImpl.this.logger.debug(method);
         }

         return this.provider.getAssertionModuleConfiguration();
      }

      public ClassLoader getClassLoader() {
         boolean debug = JAASIdentityAssertionProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getClassLoader" : null;
         if (debug) {
            JAASIdentityAssertionProviderImpl.this.logger.debug(method);
         }

         return this.provider.getClass().getClassLoader();
      }

      // $FF: synthetic method
      V1Wrapper(AuthenticationProvider x1, Object x2) {
         this(x1);
      }
   }

   private class V2Wrapper implements JAASIdentityAssertionProvider {
      private AuthenticationProviderV2 provider;

      private V2Wrapper(AuthenticationProviderV2 provider) {
         this.provider = provider;
      }

      public boolean supportsIdentityAssertion() {
         boolean debug = JAASIdentityAssertionProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".supportsIdentityAssertion" : null;
         if (debug) {
            JAASIdentityAssertionProviderImpl.this.logger.debug(method);
         }

         return this.getAssertionModuleConfiguration() != null;
      }

      public AppConfigurationEntry getAssertionModuleConfiguration() {
         boolean debug = JAASIdentityAssertionProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getAssertionModuleConfiguration" : null;
         if (debug) {
            JAASIdentityAssertionProviderImpl.this.logger.debug(method);
         }

         return this.provider.getAssertionModuleConfiguration();
      }

      public ClassLoader getClassLoader() {
         boolean debug = JAASIdentityAssertionProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getClassLoader" : null;
         if (debug) {
            JAASIdentityAssertionProviderImpl.this.logger.debug(method);
         }

         return this.provider.getClass().getClassLoader();
      }

      // $FF: synthetic method
      V2Wrapper(AuthenticationProviderV2 x1, Object x2) {
         this(x1);
      }
   }
}
