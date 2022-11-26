package weblogic.security.service.internal;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import weblogic.security.SecurityLogger;
import weblogic.security.spi.SecurityProvider;
import weblogic.security.spi.VersionableApplicationProvider;

public class VersionableApplicationProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("SecurityRealm");
      if (this.logger == null) {
         throw new UnsupportedOperationException(SecurityLogger.getServiceNotFound("Logger", "SecurityRealm"));
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".init()");
         }

         VersionableApplicationProviderConfig myconfig = (VersionableApplicationProviderConfig)config;
         SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getProviderName());
         if (provider instanceof VersionableApplicationProvider) {
            return Delegator.getInstance((Class)VersionableApplicationProvider.class, provider);
         } else {
            throw new ServiceConfigurationException(SecurityLogger.getNotInstanceof("VersionableApplicationProvider"));
         }
      }
   }

   public void shutdown() {
   }
}
