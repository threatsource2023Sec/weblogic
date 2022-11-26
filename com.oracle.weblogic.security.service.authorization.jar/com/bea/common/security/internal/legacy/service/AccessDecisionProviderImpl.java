package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.spi.AccessDecisionProvider;
import weblogic.security.spi.AccessDecision;
import weblogic.security.spi.AuthorizationProvider;

public class AccessDecisionProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.AccessDecisionService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      AccessDecisionProviderConfig myconfig = (AccessDecisionProviderConfig)config;
      AuthorizationProvider provider = (AuthorizationProvider)dependentServices.getService(myconfig.getAuthorizationProviderName());
      AccessDecision accessDecision = provider.getAccessDecision();
      if (accessDecision == null) {
         throw new ServiceConfigurationException(ServiceLogger.getNullAccessDecision());
      } else {
         return new ServiceImpl(accessDecision);
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private class ServiceImpl implements AccessDecisionProvider {
      private AccessDecision accessDecision;
      private String accessDecisionClassName;

      private ServiceImpl(AccessDecision accessDecision) {
         this.accessDecision = (AccessDecision)Delegator.getProxy(AccessDecision.class, accessDecision);
         this.accessDecisionClassName = accessDecision.getClass().getName();
      }

      public AccessDecision getAccessDecision() {
         return this.accessDecision;
      }

      public String getAccessDecisionClassName() {
         return this.accessDecisionClassName;
      }

      // $FF: synthetic method
      ServiceImpl(AccessDecision x1, Object x2) {
         this(x1);
      }
   }
}
