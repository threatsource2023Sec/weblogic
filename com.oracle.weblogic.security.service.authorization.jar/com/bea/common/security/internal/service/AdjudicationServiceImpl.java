package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AccessDecisionService;
import com.bea.common.security.service.AdjudicationService;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.servicecfg.AdjudicationServiceConfig;
import com.bea.common.security.spi.AdjudicationProviderV2;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AdjudicatorV2;
import weblogic.security.spi.Resource;
import weblogic.security.spi.Result;

public class AdjudicationServiceImpl implements ServiceLifecycleSpi, AdjudicationService {
   private LoggerSpi logger;
   private AuditService auditService;
   private AdjudicatorV2 adjudicatorV2;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.AdjudicationService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof AdjudicationServiceConfig) {
         AdjudicationServiceConfig myconfig = (AdjudicationServiceConfig)config;
         String name = myconfig.getAuditServiceName();
         this.auditService = (AuditService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got AuditService " + name);
         }

         String name = myconfig.getAccessDecisionServiceName();
         AccessDecisionService accessDecisionService = (AccessDecisionService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got AccessDecisionService " + name);
         }

         String[] accessDecisionClassNames = accessDecisionService.getAccessDecisionClassNames();
         if (debug) {
            this.logger.debug(method + " AccessDecisionClassNames = " + this.printStrings(accessDecisionClassNames));
         }

         String name = myconfig.getAdjudicatorV2Name();
         if (name != null) {
            AdjudicationProviderV2 provider = (AdjudicationProviderV2)dependentServices.getService(name);
            if (debug) {
               this.logger.debug(method + " got AdjudicationProviderV2 " + name);
            }

            this.adjudicatorV2 = provider.getAdjudicator();
            this.adjudicatorV2.initialize(accessDecisionClassNames);
            if (debug) {
               this.logger.debug(method + " called AdjudicatorV2.initialize");
            }
         } else {
            if (accessDecisionClassNames.length != 1) {
               throw new ServiceConfigurationException(ServiceLogger.getAdjudicationServiceRequiresAdjudicatorV2ForMultipleAccessDecision());
            }

            if (debug) {
               this.logger.debug(method + " has no AdjudicationProviderV2 - OK since only one AccessDecision provider");
            }
         }

         return Delegator.getProxy("com.bea.common.security.service.AdjudicationService", this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "AdjudicationServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private String printStrings(String[] strings) {
      String rtn = "[";

      for(int i = 0; strings != null && i < strings.length; ++i) {
         rtn = rtn + " \"" + strings[i] + "\"";
      }

      rtn = rtn + " ]";
      return rtn;
   }

   private String printResults(Result[] results) {
      String rtn = "[";

      for(int i = 0; results != null && i < results.length; ++i) {
         rtn = rtn + " " + results[i];
      }

      rtn = rtn + " ]";
      return rtn;
   }

   public boolean adjudicate(Result[] results, Resource resource, ContextHandler contextHandler) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".adjudicate" : null;
      if (debug) {
         this.logger.debug(method + " Results=" + this.printResults(results));
      }

      if (debug) {
         this.logger.debug(method + " Resource=" + resource);
      }

      if (this.adjudicatorV2 == null && results.length == 1) {
         Result result = results[0];
         if (debug) {
            this.logger.debug(method + " only one AccessDecision returned " + result);
         }

         boolean rtn = result == Result.PERMIT;
         if (debug) {
            this.logger.debug(method + " not calling AdjudicatorV2, returning " + rtn);
         }

         return rtn;
      } else {
         boolean rtn = this.adjudicatorV2.adjudicate(results, resource, contextHandler);
         if (debug) {
            this.logger.debug(method + " Adjudictor returned " + rtn + ", returning that value");
         }

         return rtn;
      }
   }
}
