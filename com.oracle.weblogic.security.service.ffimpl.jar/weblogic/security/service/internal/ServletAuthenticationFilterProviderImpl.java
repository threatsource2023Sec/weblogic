package weblogic.security.service.internal;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import weblogic.security.SecurityLogger;
import weblogic.security.spi.SecurityProvider;
import weblogic.security.spi.ServletAuthenticationFilter;

public class ServletAuthenticationFilterProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("SecurityAtn");
      if (this.logger == null) {
         throw new UnsupportedOperationException(SecurityLogger.getServiceNotFound("Logger", "SecurityAtn"));
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".init()");
         }

         ServletAuthenticationFilterProviderConfig myconfig = (ServletAuthenticationFilterProviderConfig)config;
         SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getAuthenticationProviderName());
         if (provider instanceof ServletAuthenticationFilter) {
            return Delegator.getInstance((Class)ServletAuthenticationFilter.class, provider);
         } else {
            throw new ServiceConfigurationException(SecurityLogger.getNotInstanceof("ServletAuthenticationFilter"));
         }
      }
   }

   public void shutdown() {
   }
}
