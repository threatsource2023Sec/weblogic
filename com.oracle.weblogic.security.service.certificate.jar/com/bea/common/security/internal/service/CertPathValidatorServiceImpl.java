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
import com.bea.common.security.service.CertPathValidatorService;
import com.bea.common.security.servicecfg.CertPathValidatorServiceConfig;
import com.bea.common.security.spi.CertPathValidatorProvider;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import weblogic.security.service.ContextHandler;

public class CertPathValidatorServiceImpl implements ServiceLifecycleSpi, CertPathValidatorService {
   private LoggerSpi logger;
   private AuditService auditService;
   private CertPathValidatorProvider[] certPathValidators;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.CertPathValidatorService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof CertPathValidatorServiceConfig) {
         CertPathValidatorServiceConfig myconfig = (CertPathValidatorServiceConfig)config;
         String auditServiceName = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(auditServiceName);
         if (debug) {
            this.logger.debug(method + " got AuditService " + auditServiceName);
         }

         String[] names = myconfig.getCertPathValidatorNames();
         if (names != null && names.length >= 1) {
            this.certPathValidators = new CertPathValidatorProvider[names.length];

            for(int i = 0; i < names.length; ++i) {
               CertPathValidatorProvider provider = (CertPathValidatorProvider)dependentServices.getService(names[i]);
               if (debug) {
                  this.logger.debug(method + " got CertPathValidator " + names[i]);
               }

               this.certPathValidators[i] = provider;
            }

            return Delegator.getProxy(CertPathValidatorService.class, this);
         } else {
            throw new ServiceConfigurationException(ServiceLogger.getConfigurationMissingRequiredInfo(method, myconfig.getClass().getName(), "CertPathValidatorNames"));
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "CertPathValidatorServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public CertPathValidatorResult validate(CertPath certPath, X509Certificate[] trustedCAs, ContextHandler context) throws CertPathValidatorException, InvalidAlgorithmParameterException {
      boolean debug = this.logger.isDebugEnabled();

      try {
         if (X509Utils.isEmpty(certPath)) {
            throw new IllegalArgumentException(SecurityLogger.getCertPathValidatorEmptyCertPathError());
         }

         if (!certPath.getType().equals("X.509")) {
            throw new IllegalArgumentException(ServiceLogger.getIncorrectCertPathType("X.509"));
         }

         X509Utils.validateOrdered(certPath);
      } catch (CertificateException var12) {
         if (debug) {
            this.logger.debug("CertPathValidatorServiceImpl.validate() failed.", var12);
         }

         CertPathAuditUtil.auditValidatorException(this.auditService, certPath, trustedCAs, context, var12);
         throw new CertPathValidatorException(var12.getMessage(), var12);
      } catch (IllegalArgumentException var13) {
         if (debug) {
            this.logger.debug("CertPathValidatorServiceImpl.validate() failed.", var13);
         }

         throw var13;
      } catch (RuntimeException var14) {
         if (debug) {
            this.logger.debug("CertPathValidatorServiceImpl.validate() failed.", var14);
         }

         CertPathAuditUtil.auditValidatorException(this.auditService, certPath, trustedCAs, context, var14);
         throw var14;
      }

      int valCount = this.certPathValidators != null ? this.certPathValidators.length : 0;
      CertPathValidatorServiceResult result = new CertPathValidatorServiceResult(valCount);

      try {
         for(int i = 0; i < valCount; ++i) {
            result.add(this.certPathValidators[i].validate(certPath, trustedCAs, context));
         }
      } catch (CertPathValidatorException var9) {
         if (debug) {
            this.logger.debug("CertPathValidatorServiceImpl.validate() failed.", var9);
         }

         CertPathAuditUtil.auditValidatorException(this.auditService, certPath, trustedCAs, context, var9);
         throw var9;
      } catch (IllegalArgumentException var10) {
         if (debug) {
            this.logger.debug("CertPathValidatorServiceImpl.validate() failed.", var10);
         }

         boolean old = Boolean.getBoolean("weblogic.security.dontValidateIfSSLErrors");
         if (!old) {
            CertPathAuditUtil.auditValidatorException(this.auditService, certPath, trustedCAs, context, var10);
         }

         throw var10;
      } catch (RuntimeException var11) {
         if (debug) {
            this.logger.debug("CertPathValidatorServiceImpl.validate() failed.", var11);
         }

         CertPathAuditUtil.auditValidatorException(this.auditService, certPath, trustedCAs, context, var11);
         throw var11;
      }

      if (debug) {
         this.logger.debug("CertPathValidatorServiceImpl.validate() success.");
      }

      CertPathAuditUtil.auditValidate(this.auditService, certPath, trustedCAs, context);
      return result;
   }

   private class CertPathValidatorServiceResult implements CertPathValidatorResult {
      private ArrayList results;

      private CertPathValidatorServiceResult(ArrayList results) {
         this.results = results;
      }

      public CertPathValidatorServiceResult(int initialCapacity) {
         this.results = new ArrayList(initialCapacity);
      }

      public void add(CertPathValidatorResult result) {
         this.results.add(result);
      }

      public CertPathValidatorResult[] getResults() {
         return (CertPathValidatorResult[])((CertPathValidatorResult[])this.results.toArray(new CertPathValidatorResult[this.results.size()]));
      }

      public Object clone() {
         return CertPathValidatorServiceImpl.this.new CertPathValidatorServiceResult((ArrayList)this.results.clone());
      }
   }
}
