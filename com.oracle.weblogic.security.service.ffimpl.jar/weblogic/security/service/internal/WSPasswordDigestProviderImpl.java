package weblogic.security.service.internal;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import weblogic.security.SecurityLogger;
import weblogic.security.spi.SecurityProvider;
import weblogic.security.spi.WSPasswordDigest;

public class WSPasswordDigestProviderImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("SecurityAtn");
      if (this.logger == null) {
         throw new UnsupportedOperationException(SecurityLogger.getServiceNotFound("Logger", "SecurityAtn"));
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".init()");
         }

         WSPasswordDigestProviderConfig myconfig = (WSPasswordDigestProviderConfig)config;
         SecurityProvider provider = (SecurityProvider)dependentServices.getService(myconfig.getAuthenticationProviderName());
         return new ServiceImpl(provider);
      }
   }

   public void shutdown() {
   }

   private final class ServiceImpl implements WSPasswordDigestProvider {
      private WSPasswordDigest wsPasswordDigest;

      private ServiceImpl(SecurityProvider provider) {
         this.wsPasswordDigest = provider instanceof WSPasswordDigest ? (WSPasswordDigest)Delegator.getInstance((Class)WSPasswordDigest.class, provider) : null;
      }

      public WSPasswordDigest getWSPasswordDigest() {
         return this.wsPasswordDigest;
      }

      // $FF: synthetic method
      ServiceImpl(SecurityProvider x1, Object x2) {
         this(x1);
      }
   }
}
