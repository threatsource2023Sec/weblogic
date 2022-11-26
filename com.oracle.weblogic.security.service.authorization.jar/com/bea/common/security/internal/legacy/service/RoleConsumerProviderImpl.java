package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.spi.RoleConsumerProvider;
import weblogic.security.spi.ConsumptionException;
import weblogic.security.spi.RoleCollectionHandler;
import weblogic.security.spi.RoleCollectionInfo;
import weblogic.security.spi.RoleConsumer;
import weblogic.security.spi.RoleConsumerFactory;
import weblogic.security.spi.SecurityProvider;

public class RoleConsumerProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.RoleConsumerService");
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".init()");
      }

      RoleConsumerProviderConfig myconfig = (RoleConsumerProviderConfig)config;
      SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getRoleConsumerProviderName());
      if (!(provider instanceof RoleConsumerFactory)) {
         throw new ServiceConfigurationException(ServiceLogger.getNotInstanceof("RoleConsumerFactory"));
      } else {
         RoleConsumer roleConsumer = ((RoleConsumerFactory)provider).getRoleConsumer();
         if (roleConsumer == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("RoleConsumerFactory", "RoleConsumer"));
         } else {
            return new ServiceImpl(roleConsumer);
         }
      }
   }

   public void shutdown() {
   }

   private class ServiceImpl implements RoleConsumerProvider {
      private RoleConsumer roleConsumer;

      ServiceImpl(RoleConsumer roleConsumer) {
         this.roleConsumer = roleConsumer;
      }

      public RoleCollectionHandler getRoleCollectionHandler(RoleCollectionInfo info) throws ConsumptionException {
         RoleCollectionHandler result = this.roleConsumer.getRoleCollectionHandler(info);
         if (result != null) {
            result = (RoleCollectionHandler)Delegator.getProxy(RoleCollectionHandler.class, result);
         }

         return result;
      }
   }
}
