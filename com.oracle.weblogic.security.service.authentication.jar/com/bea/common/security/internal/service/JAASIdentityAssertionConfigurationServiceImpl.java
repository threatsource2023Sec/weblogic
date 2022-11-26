package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.JAASIdentityAssertionConfigurationService;
import com.bea.common.security.servicecfg.JAASIdentityAssertionConfigurationServiceConfig;
import com.bea.common.security.spi.JAASIdentityAssertionProvider;
import javax.security.auth.login.AppConfigurationEntry;

public class JAASIdentityAssertionConfigurationServiceImpl extends JAASConfigurationServiceImpl implements ServiceLifecycleSpi, JAASIdentityAssertionConfigurationService {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.JAASIdentityAssertionConfigurationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof JAASIdentityAssertionConfigurationServiceConfig) {
         JAASIdentityAssertionConfigurationServiceConfig myconfig = (JAASIdentityAssertionConfigurationServiceConfig)config;
         super.init(myconfig.getJAASIdentityAssertionProviderNames(), dependentServices);
         return Delegator.getProxy(JAASIdentityAssertionConfigurationService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "JAASIdentityAssertionConfigurationServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

      super.shutdown();
   }

   public String getJAASIdentityAssertionConfigurationName() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getJAASIdentityAssertionConfigurationName()" : null;
      if (debug) {
         this.logger.debug(method);
      }

      return super.getConfigurationName();
   }

   protected String getNoJAASProvidersErrorMessage() {
      return ServiceLogger.getNoJAASProvidersErrorMessage("IdentityAssertionProviders");
   }

   protected JAASConfigurationServiceImpl.JAASProvider getJAASProvider(Object provider) {
      return new JAASProviderImpl((JAASIdentityAssertionProvider)provider);
   }

   private class JAASProviderImpl implements JAASConfigurationServiceImpl.JAASProvider {
      private JAASIdentityAssertionProvider provider;

      private JAASProviderImpl(JAASIdentityAssertionProvider provider) {
         this.provider = provider;
      }

      public boolean useProvider() {
         boolean debug = JAASIdentityAssertionConfigurationServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".useProvider" : null;
         boolean rtn = this.provider.supportsIdentityAssertion();
         if (debug) {
            JAASIdentityAssertionConfigurationServiceImpl.this.logger.debug(method + " returning " + rtn);
         }

         return rtn;
      }

      public AppConfigurationEntry getProviderAppConfigurationEntry() {
         boolean debug = JAASIdentityAssertionConfigurationServiceImpl.this.logger.isDebugEnabled();
         String method = this.getClass().getName() + ".getProviderAppConfigurationEntry";
         AppConfigurationEntry rtn = this.provider.getAssertionModuleConfiguration();
         if (rtn == null) {
            throw new IllegalArgumentException(ServiceLogger.getNullObjectReturned(method, "AppConfigurationEntry"));
         } else {
            if (debug) {
               JAASIdentityAssertionConfigurationServiceImpl.this.logger.debug(method + " returning LoginModuleClassName=" + rtn.getLoginModuleName() + ", ControlFlag=" + rtn.getControlFlag());
            }

            return rtn;
         }
      }

      public ClassLoader getClassLoader() {
         return this.provider.getClassLoader();
      }

      // $FF: synthetic method
      JAASProviderImpl(JAASIdentityAssertionProvider x1, Object x2) {
         this(x1);
      }
   }
}
