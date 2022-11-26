package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.spi.CertPathBuilderProvider;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.X509Certificate;
import weblogic.security.pk.CertPathSelector;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.CertPathBuilderParametersSpi;
import weblogic.security.spi.CertPathProvider;

public class CertPathBuilderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.CertPathBuilderService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      CertPathBuilderConfig myconfig = (CertPathBuilderConfig)config;
      CertPathProvider provider = (CertPathProvider)dependentServices.getService(myconfig.getCertPathProviderName());
      return new CertPathBuilderProviderImpl(provider);
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private class CertPathBuilderParametersSpiImpl implements CertPathBuilderParametersSpi {
      private CertPathProvider provider;
      private CertPathSelector selector;
      private X509Certificate[] trustedCAs;
      private ContextHandler context;

      private CertPathBuilderParametersSpiImpl(CertPathProvider provider, CertPathSelector selector, X509Certificate[] trustedCAs, ContextHandler context) {
         this.provider = provider;
         this.trustedCAs = trustedCAs;
         this.selector = selector;
         this.context = context;
      }

      public Object clone() {
         throw new UnsupportedOperationException();
      }

      public CertPathProvider getCertPathProvider() {
         return this.provider;
      }

      public CertPathSelector getCertPathSelector() {
         return this.selector;
      }

      public X509Certificate[] getTrustedCAs() {
         return this.trustedCAs;
      }

      public ContextHandler getContext() {
         return this.context;
      }

      // $FF: synthetic method
      CertPathBuilderParametersSpiImpl(CertPathProvider x1, CertPathSelector x2, X509Certificate[] x3, ContextHandler x4, Object x5) {
         this(x1, x2, x3, x4);
      }
   }

   private class CertPathBuilderProviderImpl implements CertPathBuilderProvider {
      private CertPathProvider provider;
      private CertPathBuilder builder;

      public CertPathBuilderProviderImpl(CertPathProvider provider) throws ServiceConfigurationException {
         this.provider = provider;
         this.builder = provider.getCertPathBuilder();
         if (this.builder == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("CertPathProvider", "CertPathBuilder"));
         }
      }

      public CertPathBuilderResult build(CertPathSelector selector, X509Certificate[] trustedCAs, ContextHandler context) throws CertPathBuilderException, InvalidAlgorithmParameterException {
         return this.builder.build(CertPathBuilderImpl.this.new CertPathBuilderParametersSpiImpl(this.provider, selector, trustedCAs, context));
      }
   }
}
