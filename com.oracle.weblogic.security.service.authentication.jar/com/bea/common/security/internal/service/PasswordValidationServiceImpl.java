package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.PasswordValidationService;
import com.bea.common.security.service.ValidationFailedException;
import com.bea.common.security.servicecfg.PasswordValidationServiceConfig;
import com.bea.common.security.spi.PasswordValidatorProvider;

public class PasswordValidationServiceImpl implements ServiceLifecycleSpi, PasswordValidationService {
   private LoggerSpi logger;
   private PasswordValidatorProvider[] providers;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.PasswordValidationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = null;
      if (debug) {
         method = this.getClass().getName() + ".init";
         this.logger.debug(method);
      }

      PasswordValidationServiceConfig myconfig = (PasswordValidationServiceConfig)config;
      String[] providerNames = myconfig.getValidationProviderNames();
      this.providers = new PasswordValidatorProvider[providerNames == null ? 0 : providerNames.length];

      for(int i = 0; providerNames != null && i < providerNames.length; ++i) {
         this.providers[i] = (PasswordValidatorProvider)dependentServices.getService(providerNames[i]);
         if (debug) {
            this.logger.debug(method + " got PasswordValidatorProvider " + providerNames[i]);
         }
      }

      return Delegator.getProxy(PasswordValidationService.class, this);
   }

   public void shutdown() {
      String method = this.logger.isDebugEnabled() ? this.getClass().getName() + ".shutdown" : null;
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(method);
      }

   }

   public void validate(String name, String password) throws ValidationFailedException {
      ValidationFailedException errors = null;

      for(int i = 0; this.providers != null && i < this.providers.length; ++i) {
         try {
            this.providers[i].validate(name, password);
         } catch (ValidationFailedException var6) {
            if (errors != null) {
               errors.add(var6);
            } else {
               errors = new ValidationFailedException();
               errors.add(var6);
            }
         }
      }

      if (errors != null) {
         throw errors;
      }
   }
}
