package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.spi.PrincipalValidationProvider;
import com.bea.common.security.spi.PrincipalValidatorWrapper;
import java.security.Principal;
import weblogic.security.spi.AuthenticationProvider;
import weblogic.security.spi.AuthenticationProviderV2;
import weblogic.security.spi.PrincipalValidator;
import weblogic.security.spi.SecurityProvider;

public class PrincipalValidationProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;
   private PrincipalValidator principalValidator;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.PrincipalValidationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      PrincipalValidationProviderConfig myconfig = (PrincipalValidationProviderConfig)config;
      SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getAuthenticationProviderName());
      PrincipalValidator originalPrincipalValidator;
      if (provider instanceof AuthenticationProviderV2) {
         originalPrincipalValidator = ((AuthenticationProviderV2)provider).getPrincipalValidator();
      } else {
         if (!(provider instanceof AuthenticationProvider)) {
            throw new ServiceConfigurationException(ServiceLogger.getNotInstanceof("AuthenticationProvider"));
         }

         originalPrincipalValidator = ((AuthenticationProvider)provider).getPrincipalValidator();
      }

      this.principalValidator = originalPrincipalValidator != null ? (PrincipalValidatorWrapper)Delegator.getProxy(PrincipalValidatorWrapper.class, new PrincipalValidatorWrapperImpl(originalPrincipalValidator)) : null;
      return new ServiceImpl();
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private class PrincipalValidatorWrapperImpl implements PrincipalValidatorWrapper {
      private PrincipalValidator principalValidator;

      public PrincipalValidatorWrapperImpl(PrincipalValidator pv) {
         this.principalValidator = pv;
      }

      public boolean validate(Principal principal) throws SecurityException {
         return this.principalValidator.validate(principal);
      }

      public boolean sign(Principal principal) {
         return this.principalValidator.sign(principal);
      }

      public Class getPrincipalBaseClass() {
         return this.principalValidator.getPrincipalBaseClass();
      }

      public String getPrincipalValidatorType() {
         return this.principalValidator.getClass().getName();
      }
   }

   private class ServiceImpl implements PrincipalValidationProvider {
      private ServiceImpl() {
      }

      public PrincipalValidator getPrincipalValidator() {
         boolean debug = PrincipalValidationProviderImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getPrincipalValidator" : null;
         if (debug) {
            PrincipalValidationProviderImpl.this.logger.debug(method);
         }

         return PrincipalValidationProviderImpl.this.principalValidator;
      }

      // $FF: synthetic method
      ServiceImpl(Object x1) {
         this();
      }
   }
}
