package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.IdentityAssertionTokenService;
import com.bea.common.security.servicecfg.IdentityAssertionTokenServiceConfig;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import javax.security.auth.callback.CallbackHandler;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.IdentityAsserterV2;
import weblogic.security.spi.IdentityAssertionException;
import weblogic.security.spi.AuditAtnEventV2.AtnEventTypeV2;

public class IdentityAssertionTokenServiceImpl implements ServiceLifecycleSpi, IdentityAssertionTokenService {
   private LoggerSpi logger;
   private AuditService auditService;
   private TreeMap tokenTypeToIdentityAsserterMap;

   public IdentityAssertionTokenServiceImpl() {
      this.tokenTypeToIdentityAsserterMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
   }

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.IdentityAssertionTokenService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      IdentityAssertionTokenServiceConfig myconfig = (IdentityAssertionTokenServiceConfig)config;
      String name = myconfig.getAuditServiceName();
      this.auditService = (AuditService)dependentServices.getService(name);
      if (debug) {
         this.logger.debug(method + " got AuditService " + name);
      }

      HashSet allActiveTypes = new HashSet();
      IdentityAssertionTokenServiceConfig.IdentityAsserterV2Config[] iaConfigs = myconfig.getIdentityAsserterV2Configs();

      for(int i = 0; i < iaConfigs.length; ++i) {
         IdentityAsserterV2 ia = (IdentityAsserterV2)dependentServices.getService(iaConfigs[i].getIdentityAsserterV2Name());
         String[] activeTypes = iaConfigs[i].getActiveTypes();

         for(int j = 0; activeTypes != null && j < activeTypes.length; ++j) {
            String activeType = activeTypes[j];
            if (allActiveTypes.contains(activeType) && !"Authorization".equalsIgnoreCase(activeType)) {
               throw new ServiceConfigurationException(ServiceLogger.getNonexclusiveToken("IdentityAsserterV2s", activeType));
            }

            ArrayList ias = (ArrayList)this.tokenTypeToIdentityAsserterMap.get(activeType);
            if (ias == null) {
               ias = new ArrayList();
            } else if (!"Authorization".equalsIgnoreCase(activeType)) {
               allActiveTypes.add(activeType);
               continue;
            }

            ias.add(ia);
            this.tokenTypeToIdentityAsserterMap.put(activeType, ias);
            allActiveTypes.add(activeType);
         }
      }

      return Delegator.getProxy(IdentityAssertionTokenService.class, this);
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   public boolean isTokenTypeSupported(String tokenType) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".tokenType" : null;
      if (debug) {
         this.logger.debug(method + "(" + tokenType + ")");
      }

      return this.tokenTypeToIdentityAsserterMap.containsKey(tokenType);
   }

   public CallbackHandler assertIdentity(String tokenType, Object token, ContextHandler contextHandler) throws IdentityAssertionException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".assertIdentity" : null;
      if (debug) {
         this.logger.debug(method + "(" + tokenType + ")");
      }

      ArrayList ias = (ArrayList)this.tokenTypeToIdentityAsserterMap.get(tokenType);
      if (ias != null && !ias.isEmpty()) {
         AuditAtnEventImpl atnAuditEvent;
         try {
            if (ias.size() == 1) {
               IdentityAsserterV2 asserter = (IdentityAsserterV2)ias.get(0);
               return asserter.assertIdentity(tokenType, token, contextHandler);
            } else {
               ArrayList excs = new ArrayList();
               Iterator var15 = ias.iterator();

               while(var15.hasNext()) {
                  IdentityAsserterV2 asserter = (IdentityAsserterV2)var15.next();

                  try {
                     return asserter.assertIdentity(tokenType, token, contextHandler);
                  } catch (Exception var11) {
                     excs.add(var11);
                  }
               }

               this.throwCombinedException(excs, tokenType);
               return null;
            }
         } catch (IdentityAssertionException var12) {
            if (this.auditService.isAuditEnabled()) {
               atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, "", contextHandler, AtnEventTypeV2.ASSERTIDENTITY, var12);
               this.auditService.writeEvent(atnAuditEvent);
            }

            if (debug) {
               this.logger.debug(method + " - IdentityAssertionException");
            }

            throw new IdentityAssertionException(SecurityLogger.getIdentityAssertionFailedExc(var12.toString()));
         } catch (RuntimeException var13) {
            if (this.auditService.isAuditEnabled()) {
               atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, "", contextHandler, AtnEventTypeV2.ASSERTIDENTITY, var13);
               this.auditService.writeEvent(atnAuditEvent);
            }

            if (debug) {
               this.logger.debug(method + " - Exception type: " + var13.getClass().getName());
            }

            throw var13;
         }
      } else {
         throw new IdentityAssertionException(SecurityLogger.getIAHdlrUnsupTokenType(tokenType));
      }
   }

   private void throwCombinedException(ArrayList exceptions, String tokenType) throws IdentityAssertionException {
      boolean rtException = false;
      String msg = "";

      Exception e;
      for(Iterator var5 = exceptions.iterator(); var5.hasNext(); msg = msg + e.toString() + " ") {
         e = (Exception)var5.next();
         if (e instanceof RuntimeException) {
            rtException = true;
         }
      }

      Exception e;
      Iterator var10;
      if (rtException) {
         RuntimeException rte = new RuntimeException(SecurityLogger.getIdentityAssertionFailedExc(msg));
         var10 = exceptions.iterator();

         while(var10.hasNext()) {
            e = (Exception)var10.next();
            rte.addSuppressed(e);
         }

         throw rte;
      } else {
         IdentityAssertionException iae = new IdentityAssertionException(SecurityLogger.getIdentityAssertionFailedExc(msg));
         var10 = exceptions.iterator();

         while(var10.hasNext()) {
            e = (Exception)var10.next();
            iae.addSuppressed(e);
         }

         throw iae;
      }
   }
}
