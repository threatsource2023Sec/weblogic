package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.spi.JAASAuthenticationProvider;
import javax.security.auth.login.AppConfigurationEntry;
import weblogic.security.spi.AuthenticationProvider;
import weblogic.security.spi.AuthenticationProviderV2;
import weblogic.security.spi.SecurityProvider;

public class JAASAuthenticationProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.JAASAuthenticationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      JAASAuthenticationProviderConfig myconfig = (JAASAuthenticationProviderConfig)config;
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

   private class EmptyWrapper implements JAASAuthenticationProvider {
      private EmptyWrapper() {
      }

      public boolean supportsAuthentication() {
         boolean debug = JAASAuthenticationProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".supportsAuthentication" : null;
         if (debug) {
            JAASAuthenticationProviderImpl.this.logger.debug(method);
         }

         return false;
      }

      public AppConfigurationEntry getLoginModuleConfiguration() {
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

   private class V1Wrapper implements JAASAuthenticationProvider {
      private AuthenticationProvider provider;

      private V1Wrapper(AuthenticationProvider provider) {
         this.provider = provider;
      }

      public boolean supportsAuthentication() {
         boolean debug = JAASAuthenticationProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".supportsAuthentication" : null;
         if (debug) {
            JAASAuthenticationProviderImpl.this.logger.debug(method);
         }

         return this.getLoginModuleConfiguration() != null;
      }

      public AppConfigurationEntry getLoginModuleConfiguration() {
         boolean debug = JAASAuthenticationProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getLoginModuleConfiguration" : null;
         if (debug) {
            JAASAuthenticationProviderImpl.this.logger.debug(method);
         }

         return this.provider.getLoginModuleConfiguration();
      }

      public ClassLoader getClassLoader() {
         boolean debug = JAASAuthenticationProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getClassLoader" : null;
         if (debug) {
            JAASAuthenticationProviderImpl.this.logger.debug(method);
         }

         return this.provider.getClass().getClassLoader();
      }

      // $FF: synthetic method
      V1Wrapper(AuthenticationProvider x1, Object x2) {
         this(x1);
      }
   }

   private class V2Wrapper implements JAASAuthenticationProvider {
      private AuthenticationProviderV2 provider;

      private V2Wrapper(AuthenticationProviderV2 provider) {
         this.provider = provider;
      }

      public boolean supportsAuthentication() {
         boolean debug = JAASAuthenticationProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".supportsAuthentication" : null;
         if (debug) {
            JAASAuthenticationProviderImpl.this.logger.debug(method);
         }

         return this.getLoginModuleConfiguration() != null;
      }

      public AppConfigurationEntry getLoginModuleConfiguration() {
         boolean debug = JAASAuthenticationProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getLoginModuleConfiguration" : null;
         if (debug) {
            JAASAuthenticationProviderImpl.this.logger.debug(method);
         }

         return this.provider.getLoginModuleConfiguration();
      }

      public ClassLoader getClassLoader() {
         boolean debug = JAASAuthenticationProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getClassLoader" : null;
         if (debug) {
            JAASAuthenticationProviderImpl.this.logger.debug(method);
         }

         return this.provider.getClass().getClassLoader();
      }

      // $FF: synthetic method
      V2Wrapper(AuthenticationProviderV2 x1, Object x2) {
         this(x1);
      }
   }
}
