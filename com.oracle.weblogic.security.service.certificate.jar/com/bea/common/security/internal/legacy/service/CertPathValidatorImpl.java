package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.spi.CertPathValidatorProvider;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.X509Certificate;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.CertPathProvider;
import weblogic.security.spi.CertPathValidatorParametersSpi;

public class CertPathValidatorImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.CertPathValidatorService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      CertPathValidatorConfig myconfig = (CertPathValidatorConfig)config;
      CertPathProvider provider = (CertPathProvider)dependentServices.getService(myconfig.getCertPathProviderName());
      return new CertPathValidatorProviderImpl(provider);
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private class CertPathValidatorParametersSpiImpl implements CertPathValidatorParametersSpi {
      private CertPathProvider provider;
      private X509Certificate[] trustedCAs;
      private ContextHandler context;

      private CertPathValidatorParametersSpiImpl(CertPathProvider provider, X509Certificate[] trustedCAs, ContextHandler context) {
         this.provider = provider;
         this.trustedCAs = trustedCAs;
         this.context = context;
      }

      public Object clone() {
         throw new UnsupportedOperationException();
      }

      public CertPathProvider getCertPathProvider() {
         return this.provider;
      }

      public X509Certificate[] getTrustedCAs() {
         return this.trustedCAs;
      }

      public ContextHandler getContext() {
         return this.context;
      }

      // $FF: synthetic method
      CertPathValidatorParametersSpiImpl(CertPathProvider x1, X509Certificate[] x2, ContextHandler x3, Object x4) {
         this(x1, x2, x3);
      }
   }

   private class CertPathValidatorProviderImpl implements CertPathValidatorProvider {
      private CertPathProvider provider;
      private CertPathValidator validator;

      public CertPathValidatorProviderImpl(CertPathProvider provider) throws ServiceConfigurationException {
         this.provider = provider;
         this.validator = provider.getCertPathValidator();
         if (this.validator == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("CertPathProvider", "CertPathvalidator"));
         }
      }

      public CertPathValidatorResult validate(CertPath certPath, X509Certificate[] trustedCAs, ContextHandler context) throws CertPathValidatorException, InvalidAlgorithmParameterException {
         return this.validator.validate(certPath, CertPathValidatorImpl.this.new CertPathValidatorParametersSpiImpl(this.provider, trustedCAs, context));
      }
   }
}
