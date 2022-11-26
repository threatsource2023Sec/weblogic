package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.internal.utils.Delegator;
import weblogic.security.spi.CredentialMapperV2;
import weblogic.security.spi.CredentialProviderV2;
import weblogic.security.spi.SecurityProvider;

public class CredentialMapperImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.CredentialMappingService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      CredentialMapperConfig myconfig = (CredentialMapperConfig)config;
      SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getCredentialProviderName());
      if (provider instanceof CredentialProviderV2) {
         CredentialMapperV2 credentialMapperV2 = ((CredentialProviderV2)provider).getCredentialProvider();
         if (credentialMapperV2 == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("CredentialProviderV2", "CredentialMapperV2"));
         } else {
            return Delegator.getProxy(CredentialMapperV2.class, credentialMapperV2);
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getNotInstanceof("CredentialProvider"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }
}
