package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.internal.utils.X509Utils;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.CertPathBuilderService;
import com.bea.common.security.servicecfg.CertPathBuilderServiceConfig;
import com.bea.common.security.spi.CertPathBuilderProvider;
import com.bea.common.security.spi.CertPathValidatorProvider;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import weblogic.security.pk.CertPathSelector;
import weblogic.security.service.ContextHandler;

public class CertPathBuilderServiceImpl implements ServiceLifecycleSpi, CertPathBuilderService {
   private LoggerSpi logger;
   private AuditService auditService;
   private CertPathBuilderProvider certPathBuilder;
   private CertPathValidatorProvider[] certPathValidators;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.CertPathBuilderService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof CertPathBuilderServiceConfig) {
         CertPathBuilderServiceConfig myconfig = (CertPathBuilderServiceConfig)config;
         String auditServiceName = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(auditServiceName);
         if (debug) {
            this.logger.debug(method + " got AuditService " + auditServiceName);
         }

         String builderServiceName = myconfig.getCertPathBuilderName();
         this.certPathBuilder = (CertPathBuilderProvider)dependentServices.getService(builderServiceName);
         if (debug) {
            this.logger.debug(method + " got CertPathBuilder " + builderServiceName);
         }

         String[] names = myconfig.getCertPathValidatorNames();
         if (names != null && names.length > 0) {
            this.certPathValidators = new CertPathValidatorProvider[names.length];

            for(int i = 0; i < names.length; ++i) {
               CertPathValidatorProvider provider = (CertPathValidatorProvider)dependentServices.getService(names[i]);
               if (debug) {
                  this.logger.debug(method + " got CertPathValidator " + names[i]);
               }

               this.certPathValidators[i] = provider;
            }
         }

         return Delegator.getProxy(CertPathBuilderService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "CertPathBuilderServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public CertPathBuilderResult build(CertPathSelector selector, X509Certificate[] trustedCAs, ContextHandler context) throws CertPathBuilderException, InvalidAlgorithmParameterException {
      boolean debug = this.logger.isDebugEnabled();
      CertPathBuilderResult result = null;

      try {
         result = this.certPathBuilder.build(selector, trustedCAs, context);
         CertPath certPath = result.getCertPath();
         if (X509Utils.isEmpty(certPath)) {
            throw new CertPathBuilderException(SecurityLogger.getCertPathBuilderProviderReturnedEmptyCertPathError());
         }

         if (!certPath.getType().equals("X.509")) {
            throw new CertPathBuilderException(ServiceLogger.getCertPathBuildReturnedNonX509CertPath());
         }

         try {
            X509Utils.validateOrdered(certPath);
         } catch (CertificateException var8) {
            throw new CertPathBuilderException(SecurityLogger.getCertPathBuilderProviderUnorderedCertPathError(var8.getMessage(), certPath.toString()), var8);
         }
      } catch (CertPathBuilderException var12) {
         if (debug) {
            this.logger.debug("CertPathBuilderServiceImpl.build() failed.", var12);
         }

         CertPathAuditUtil.auditBuilderException(this.auditService, selector, trustedCAs, context, var12);
         throw var12;
      } catch (IllegalArgumentException var13) {
         if (debug) {
            this.logger.debug("CertPathBuilderServiceImpl.build() failed.", var13);
         }

         throw var13;
      } catch (RuntimeException var14) {
         if (debug) {
            this.logger.debug("CertPathBuilderServiceImpl.build() failed.", var14);
         }

         CertPathAuditUtil.auditBuilderException(this.auditService, selector, trustedCAs, context, var14);
         throw var14;
      }

      try {
         for(int i = 0; this.certPathValidators != null && i < this.certPathValidators.length; ++i) {
            this.certPathValidators[i].validate(result.getCertPath(), trustedCAs, context);
         }
      } catch (CertPathValidatorException var9) {
         if (debug) {
            this.logger.debug("CertPathBuilderServiceImpl.build() failed to validate.", var9);
         }

         CertPathAuditUtil.auditValidatorException(this.auditService, result.getCertPath(), trustedCAs, context, var9);
         throw new CertPathBuilderException(var9.getMessage(), var9);
      } catch (IllegalArgumentException var10) {
         if (debug) {
            this.logger.debug("CertPathBuilderServiceImpl.build() failed to validate.", var10);
         }

         throw var10;
      } catch (RuntimeException var11) {
         if (debug) {
            this.logger.debug("CertPathBuilderServiceImpl.build() failed to validate.", var11);
         }

         CertPathAuditUtil.auditValidatorException(this.auditService, result.getCertPath(), trustedCAs, context, var11);
         throw var11;
      }

      if (debug) {
         this.logger.debug("CertPathBuilderServiceImpl.build() success.");
      }

      CertPathAuditUtil.auditBuild(this.auditService, selector, trustedCAs, context);
      return result;
   }
}
