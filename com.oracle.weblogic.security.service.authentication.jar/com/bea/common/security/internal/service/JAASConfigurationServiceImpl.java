package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.jdkutils.JAASConfiguration;
import java.util.Vector;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

public abstract class JAASConfigurationServiceImpl extends Configuration {
   private JAASProvider[] jaasProviders;
   protected String configurationName;
   protected boolean isOracleDefaultLoginConfigurationEnabled;
   private LoggerSpi logger;

   protected abstract JAASProvider getJAASProvider(Object var1);

   protected abstract String getNoJAASProvidersErrorMessage();

   protected void init(String[] jaasProviderNames, Services dependentServices) throws ServiceInitializationException {
      this.init(jaasProviderNames, dependentServices, false);
   }

   protected void init(String[] jaasProviderNames, Services dependentServices, boolean jaasDefaultConfiguration) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.JAASLoginService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (debug) {
         this.logger.debug(method + " getting JAASProviders");
      }

      Vector providers = new Vector();

      for(int i = 0; i < jaasProviderNames.length; ++i) {
         String name = jaasProviderNames[i];
         JAASProvider provider = this.getJAASProvider(dependentServices.getService(name));
         if (debug) {
            this.logger.debug(method + " got JAASProvider " + name);
         }

         if (provider.useProvider()) {
            if (debug) {
               this.logger.debug(method + " using JAASProvider");
            }

            providers.add(provider);
         } else if (debug) {
            this.logger.debug(method + " ignoring JAASProvider");
         }
      }

      if (providers.size() < 1) {
         throw new ServiceConfigurationException(this.getNoJAASProvidersErrorMessage());
      } else {
         this.jaasProviders = (JAASProvider[])((JAASProvider[])providers.toArray(new JAASProvider[0]));
         if (debug) {
            this.logger.debug(method + " got JAASProviders");
         }

         this.configurationName = this.getClass().getName() + JAASConfiguration.getInstanceCounter();
         if (debug) {
            this.logger.debug(method + " ConfigurationName=" + this.configurationName);
         }

         JAASConfiguration.registerConfiguration(this);
         if (debug) {
            this.logger.debug(method + " JAASDefaultConfiguration=" + jaasDefaultConfiguration);
         }

         this.isOracleDefaultLoginConfigurationEnabled = jaasDefaultConfiguration;
      }
   }

   void shutdown() {
      JAASConfiguration.unregisterConfiguration(this);
   }

   protected String getConfigurationName() {
      return this.configurationName;
   }

   private AppConfigurationEntry[] getAppConfigurationEntry() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getAppConfigurationEntry" : null;
      if (debug) {
         this.logger.debug(method);
      }

      AppConfigurationEntry[] entries = new AppConfigurationEntry[this.jaasProviders.length];

      for(int i = 0; i < entries.length; ++i) {
         entries[i] = LoginModuleWrapper.wrap(this.jaasProviders[i].getProviderAppConfigurationEntry(), this.jaasProviders[i].getClassLoader(), this.logger);
      }

      return entries;
   }

   public AppConfigurationEntry[] getAppConfigurationEntry(String configurationName) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getAppConfigurationEntry" : null;
      if (debug) {
         this.logger.debug(method + "(" + configurationName + ")");
      }

      if (this.isOracleDefaultLoginConfigurationEnabled && "OracleDefaultLoginConfiguration".equals(configurationName)) {
         return this.getAppConfigurationEntry();
      } else {
         return this.configurationName.equals(configurationName) ? this.getAppConfigurationEntry() : null;
      }
   }

   public void refresh() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".refresh" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   protected interface JAASProvider {
      boolean useProvider();

      AppConfigurationEntry getProviderAppConfigurationEntry();

      ClassLoader getClassLoader();
   }
}
