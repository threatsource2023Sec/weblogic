package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.BulkAccessDecisionService;
import com.bea.common.security.service.BulkAdjudicationService;
import com.bea.common.security.servicecfg.AdjudicationServiceConfig;
import com.bea.common.security.spi.BulkAdjudicationProvider;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AdjudicatorV2;
import weblogic.security.spi.BulkAdjudicator;
import weblogic.security.spi.Resource;
import weblogic.security.spi.Result;

public class BulkAdjudicationServiceImpl implements ServiceLifecycleSpi, BulkAdjudicationService {
   private LoggerSpi logger;
   private AuditService auditService;
   private AdjudicatorV2 adjudicatorV2;
   private BulkAdjudicator bulkAdjudicator;

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
         BulkAccessDecisionService accessDecisionService = (BulkAccessDecisionService)dependentServices.getService(name);
         if (debug) {
            this.logger.debug(method + " got AccessDecisionService " + name);
         }

         String[] accessDecisionClassNames = accessDecisionService.getAccessDecisionClassNames();
         if (debug) {
            this.logger.debug(method + " AccessDecisionClassNames = " + this.printStrings(accessDecisionClassNames));
         }

         String name = myconfig.getAdjudicatorV2Name();
         if (name != null) {
            BulkAdjudicationProvider provider = (BulkAdjudicationProvider)dependentServices.getService(name);
            if (debug) {
               this.logger.debug(method + " got BulkAdjudicatorProvider " + name);
            }

            this.adjudicatorV2 = provider.getAdjudicator();
            this.bulkAdjudicator = provider.getBulkAdjudicator();
            this.adjudicatorV2.initialize(accessDecisionClassNames);
            this.bulkAdjudicator.initialize(accessDecisionClassNames);
            if (debug) {
               this.logger.debug(method + " called BulkAdjudicator.initialize");
            }
         } else {
            if (accessDecisionClassNames.length != 1) {
               throw new ServiceConfigurationException(ServiceLogger.getAdjudicationServiceRequiresAdjudicatorV2ForMultipleAccessDecision());
            }

            if (debug) {
               this.logger.debug(method + " has no BulkAdjudicationProvider - OK since only one AccessDecision provider");
            }
         }

         return Delegator.getProxy("com.bea.common.security.service.BulkAdjudicationService", this);
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

   public Set adjudicate(List results, List resources, ContextHandler handler) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".adjudicate" : null;
      Iterator mi;
      if (debug) {
         Iterator ri = resources.iterator();

         while(ri.hasNext()) {
            Resource r = (Resource)ri.next();
            this.logger.debug(method + " Resource=" + r);
            StringBuffer sb = new StringBuffer();
            sb.append(method);
            sb.append(" Results=");
            mi = results.iterator();

            while(mi.hasNext()) {
               Map m = (Map)mi.next();
               Result x = (Result)m.get(r);
               sb.append(x != null ? x.toString() : "NONE");
               if (mi.hasNext()) {
                  sb.append(", ");
               }
            }

            this.logger.debug(sb.toString());
         }
      }

      Object adj;
      if (this.bulkAdjudicator == null) {
         adj = new HashSet();
         Iterator ri = resources.iterator();

         while(ri.hasNext()) {
            Resource resource = (Resource)ri.next();
            mi = results.iterator();
            if (mi.hasNext()) {
               Result result = (Result)((Map)mi.next()).get(resource);
               if (result == Result.PERMIT) {
                  ((Set)adj).add(resource);
               }

               if (mi.hasNext()) {
                  throw new IllegalArgumentException("Only one result per resource is expected in configuration without Adjudicator");
               }
            }
         }
      } else {
         adj = this.bulkAdjudicator.adjudicate(results, resources, handler);
      }

      if (debug) {
         StringBuffer sb = new StringBuffer();
         sb.append(method);
         sb.append(" Adjudictor permits access to: ");
         Iterator ri = ((Set)adj).iterator();

         while(ri.hasNext()) {
            sb.append(((Resource)ri.next()).toString());
            if (ri.hasNext()) {
               sb.append(", ");
            }
         }

         this.logger.debug(sb.toString());
      }

      return (Set)adj;
   }
}
