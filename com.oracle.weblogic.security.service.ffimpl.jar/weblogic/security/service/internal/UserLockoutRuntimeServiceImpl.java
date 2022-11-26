package weblogic.security.service.internal;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import weblogic.security.SecurityLogger;

public class UserLockoutRuntimeServiceImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("SecurityUserLockout");
      if (this.logger == null) {
         throw new UnsupportedOperationException(SecurityLogger.getServiceNotFound("Logger", "SecurityUserLockout"));
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".init()");
         }

         UserLockoutRuntimeServiceConfig myconfig = (UserLockoutRuntimeServiceConfig)config;
         UserLockoutService ulockService = (UserLockoutService)dependentServices.getService(myconfig.getUserLockoutServiceName());
         return Delegator.getInstance((Class)UserLockoutRuntimeService.class, ulockService.getRuntimeService());
      }
   }

   public void shutdown() {
   }
}
