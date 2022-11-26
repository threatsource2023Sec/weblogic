package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.jdkutils.JAASConfiguration;
import com.bea.common.security.service.JAASAuthenticationConfigurationService;
import com.bea.common.security.servicecfg.JAASAuthenticationConfigurationServiceConfig;
import com.bea.common.security.spi.JAASAuthenticationProvider;
import java.lang.reflect.InvocationTargetException;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

public class JAASAuthenticationConfigurationServiceImpl extends JAASConfigurationServiceImpl implements ServiceLifecycleSpi, JAASAuthenticationConfigurationService {
   private LoggerSpi logger;
   private static final String loginConfigClassName = "oracle.security.jps.internal.jaas.LoginConfigurationImpl";

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.JAASAuthenticationConfigurationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof JAASAuthenticationConfigurationServiceConfig) {
         JAASAuthenticationConfigurationServiceConfig myconfig = (JAASAuthenticationConfigurationServiceConfig)config;
         super.init(myconfig.getJAASAuthenticationProviderNames(), dependentServices, myconfig.isOracleDefaultLoginConfiguration());
         if (myconfig.isOracleDefaultLoginConfiguration()) {
            this.registerJPSLoginConfiguration();
         }

         return Delegator.getProxy(JAASAuthenticationConfigurationService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "JAASAuthenticationConfigurationServiceConfig"));
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

   public String getJAASAuthenticationConfigurationName() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getJAASAuthenticationConfigurationName()" : null;
      if (debug) {
         this.logger.debug(method);
      }

      return super.getConfigurationName();
   }

   protected String getNoJAASProvidersErrorMessage() {
      return ServiceLogger.getNoJAASProvidersErrorMessage("AuthenticationProviders");
   }

   protected JAASConfigurationServiceImpl.JAASProvider getJAASProvider(Object provider) {
      return new JAASProviderImpl((JAASAuthenticationProvider)provider);
   }

   private void registerJPSLoginConfiguration() {
      try {
         Object loginConfig = Class.forName("oracle.security.jps.internal.jaas.LoginConfigurationImpl").getConstructor().newInstance();
         JAASConfiguration.registerConfiguration((Configuration)loginConfig);
      } catch (ClassNotFoundException var2) {
      } catch (NoSuchMethodException var3) {
      } catch (IllegalArgumentException var4) {
      } catch (IllegalAccessException var5) {
      } catch (InvocationTargetException var6) {
      } catch (InstantiationException var7) {
      }
   }

   private class JAASProviderImpl implements JAASConfigurationServiceImpl.JAASProvider {
      private JAASAuthenticationProvider provider;

      private JAASProviderImpl(JAASAuthenticationProvider provider) {
         this.provider = provider;
      }

      public boolean useProvider() {
         boolean debug = JAASAuthenticationConfigurationServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".useProvider" : null;
         boolean rtn = this.provider.supportsAuthentication();
         if (debug) {
            JAASAuthenticationConfigurationServiceImpl.this.logger.debug(method + " returning " + rtn);
         }

         return rtn;
      }

      public AppConfigurationEntry getProviderAppConfigurationEntry() {
         boolean debug = JAASAuthenticationConfigurationServiceImpl.this.logger.isDebugEnabled();
         String method = this.getClass().getName() + ".getProviderAppConfigurationEntry";
         AppConfigurationEntry rtn = this.provider.getLoginModuleConfiguration();
         if (rtn == null) {
            throw new IllegalArgumentException(ServiceLogger.getNullObjectReturned(method, "AppConfigurationEntry"));
         } else {
            if (debug) {
               JAASAuthenticationConfigurationServiceImpl.this.logger.debug(method + " returning LoginModuleClassName=" + rtn.getLoginModuleName() + ", ControlFlag=" + rtn.getControlFlag());
            }

            return rtn;
         }
      }

      public ClassLoader getClassLoader() {
         return this.provider.getClassLoader();
      }

      // $FF: synthetic method
      JAASProviderImpl(JAASAuthenticationProvider x1, Object x2) {
         this(x1);
      }
   }
}
