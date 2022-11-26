package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.spi.PolicyConsumerProvider;
import weblogic.security.spi.ConsumptionException;
import weblogic.security.spi.PolicyCollectionHandler;
import weblogic.security.spi.PolicyCollectionInfo;
import weblogic.security.spi.PolicyConsumer;
import weblogic.security.spi.PolicyConsumerFactory;
import weblogic.security.spi.SecurityProvider;

public class PolicyConsumerProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.PolicyConsumerService");
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".init()");
      }

      PolicyConsumerProviderConfig myconfig = (PolicyConsumerProviderConfig)config;
      SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getPolicyConsumerProviderName());
      if (!(provider instanceof PolicyConsumerFactory)) {
         throw new ServiceConfigurationException(ServiceLogger.getNotInstanceof("PolicyConsumerFactory"));
      } else {
         PolicyConsumer policyConsumer = ((PolicyConsumerFactory)provider).getPolicyConsumer();
         if (policyConsumer == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("PolicyConsumerFactory", "PolicyConsumer"));
         } else {
            return new ServiceImpl(policyConsumer);
         }
      }
   }

   public void shutdown() {
   }

   private class ServiceImpl implements PolicyConsumerProvider {
      private PolicyConsumer policyConsumer;

      ServiceImpl(PolicyConsumer policyConsumer) {
         this.policyConsumer = policyConsumer;
      }

      public PolicyCollectionHandler getPolicyCollectionHandler(PolicyCollectionInfo info) throws ConsumptionException {
         PolicyCollectionHandler result = this.policyConsumer.getPolicyCollectionHandler(info);
         if (result != null) {
            result = (PolicyCollectionHandler)Delegator.getProxy(PolicyCollectionHandler.class, result);
         }

         return result;
      }
   }
}
