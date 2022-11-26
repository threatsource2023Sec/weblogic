package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AdjudicationProvider;
import weblogic.security.spi.AdjudicationProviderV2;
import weblogic.security.spi.Adjudicator;
import weblogic.security.spi.AdjudicatorV2;
import weblogic.security.spi.Resource;
import weblogic.security.spi.Result;

public class AdjudicationProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.AdjudicationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      AdjudicatorProviderConfig myconfig = (AdjudicatorProviderConfig)config;
      Object provider = dependentServices.getService(myconfig.getAdjudicationProviderName());
      if (provider instanceof AdjudicationProvider) {
         Adjudicator adjudicator = ((AdjudicationProvider)provider).getAdjudicator();
         if (adjudicator == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AdjudicationProvider", "Adjudicator"));
         } else {
            return new ServiceImpl(new V1Adapter(adjudicator));
         }
      } else if (provider instanceof AdjudicationProviderV2) {
         AdjudicatorV2 adjudicator = ((AdjudicationProviderV2)provider).getAdjudicator();
         if (adjudicator == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AdjudicationProviderV2", "AdjudicatorV2"));
         } else {
            return new ServiceImpl(adjudicator);
         }
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getNotInstanceof("AdjudicationProvider"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private class ServiceImpl implements com.bea.common.security.spi.AdjudicationProviderV2 {
      private AdjudicatorV2 adjudicator;

      protected ServiceImpl(AdjudicatorV2 adjudicator) {
         this.adjudicator = adjudicator;
      }

      public AdjudicatorV2 getAdjudicator() {
         return this.adjudicator;
      }
   }

   private class V1Adapter implements AdjudicatorV2 {
      private Adjudicator v1;

      private V1Adapter(Adjudicator v1) {
         this.v1 = v1;
      }

      public void initialize(String[] accessDecisionClassNames) {
         this.v1.initialize(accessDecisionClassNames);
      }

      public boolean adjudicate(Result[] results, Resource resource, ContextHandler contextHandler) {
         return this.v1.adjudicate(results);
      }

      // $FF: synthetic method
      V1Adapter(Adjudicator x1, Object x2) {
         this(x1);
      }
   }
}
