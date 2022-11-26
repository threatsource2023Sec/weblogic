package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.LDAPSSLSocketFactoryLookupService;
import com.bea.common.security.servicecfg.LDAPSSLSocketFactoryLookupServiceConfig;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class LDAPSSLSocketFactoryLookupServiceImpl implements ServiceLifecycleSpi, LDAPSSLSocketFactoryLookupService {
   private LoggerSpi logger;
   private SSLContext sslContext = null;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.SSLSocketFactoryLookupService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof LDAPSSLSocketFactoryLookupServiceConfig) {
         LDAPSSLSocketFactoryLookupServiceConfig myconfig = (LDAPSSLSocketFactoryLookupServiceConfig)config;
         this.sslContext = myconfig.getSSLContext();
         if (this.sslContext == null && debug) {
            this.logger.debug(ServiceLogger.getConfigurationMissingRequiredInfo(method, myconfig.getClass().getName(), "SSLContext"));
         }

         return Delegator.getProxy((Class)LDAPSSLSocketFactoryLookupService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "LDAPSSLSocketFactoryLookupServiceConfig"));
      }
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown");
      }

   }

   public SSLSocketFactory getSSLSocketFactory() {
      return this.sslContext == null ? null : this.sslContext.getSocketFactory();
   }
}
