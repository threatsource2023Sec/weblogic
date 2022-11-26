package weblogic.security.service.internal;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.AuditService;
import java.util.Vector;
import weblogic.security.SecurityLogger;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.DigestNotAvailableException;
import weblogic.security.spi.WSPasswordDigest;
import weblogic.security.spi.AuditAtnEventV2.AtnEventTypeV2;

public class WSPasswordDigestServiceImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;
   private AuditService auditService;
   private WSPasswordDigest[] providers;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("SecurityAtn");
      if (this.logger == null) {
         throw new UnsupportedOperationException(SecurityLogger.getServiceNotFound("Logger", "SecurityAtn"));
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".init()");
         }

         boolean debug = this.logger.isDebugEnabled();
         String method = this.getClass().getName() + ".init";
         if (debug) {
            this.logger.debug(method);
         }

         WSPasswordDigestServiceConfig myconfig = (WSPasswordDigestServiceConfig)config;
         this.auditService = (AuditService)dependentServices.getService(myconfig.getAuditServiceName());
         Vector v = new Vector();
         String[] names = myconfig.getWSPasswordDigestProviderNames();

         for(int i = 0; i < names.length; ++i) {
            WSPasswordDigestProvider potentialProvider = (WSPasswordDigestProvider)dependentServices.getService(names[i]);
            WSPasswordDigest provider = potentialProvider.getWSPasswordDigest();
            if (provider != null) {
               v.add(provider);
            }
         }

         this.providers = (WSPasswordDigest[])((WSPasswordDigest[])v.toArray(new WSPasswordDigest[v.size()]));
         return new ServiceImpl();
      }
   }

   public void shutdown() {
   }

   private final class ServiceImpl implements WSPasswordDigestService {
      private ServiceImpl() {
      }

      private void checkDigestAvailable() throws DigestNotAvailableException {
         if (WSPasswordDigestServiceImpl.this.providers == null || WSPasswordDigestServiceImpl.this.providers.length < 1) {
            throw new DigestNotAvailableException();
         }
      }

      public byte[] getPasswordDigest(String username, byte[] nonce, String created) throws DigestNotAvailableException {
         boolean debug = WSPasswordDigestServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getPasswordDigest" : null;
         if (debug) {
            WSPasswordDigestServiceImpl.this.logger.debug(method);
         }

         this.checkDigestAvailable();
         DigestNotAvailableException lastException = null;
         int i = 0;

         while(i < WSPasswordDigestServiceImpl.this.providers.length) {
            AuditAtnEventImpl atnAuditEvent;
            try {
               if (debug) {
                  WSPasswordDigestServiceImpl.this.logger.debug(method + " Trying Provider[" + i + "]");
               }

               byte[] retVal = WSPasswordDigestServiceImpl.this.providers[i].getPasswordDigest(username, nonce, created);
               if (WSPasswordDigestServiceImpl.this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, username, (ContextHandler)null, AtnEventTypeV2.CREATEPASSWORDDIGEST, (Exception)null);
                  WSPasswordDigestServiceImpl.this.auditService.writeEvent(atnAuditEvent);
               }

               return retVal;
            } catch (DigestNotAvailableException var10) {
               if (WSPasswordDigestServiceImpl.this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, username, (ContextHandler)null, AtnEventTypeV2.CREATEPASSWORDDIGEST, (Exception)null);
                  WSPasswordDigestServiceImpl.this.auditService.writeEvent(atnAuditEvent);
               }

               lastException = var10;
               if (debug) {
                  WSPasswordDigestServiceImpl.this.logger.debug(method + " Provider[" + i + "] failed", var10);
               }

               ++i;
            }
         }

         throw lastException;
      }

      public byte[] getDerivedKey(String username, byte[] salt, int iteration) throws DigestNotAvailableException {
         boolean debug = WSPasswordDigestServiceImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".getPasswordDigest" : null;
         if (debug) {
            WSPasswordDigestServiceImpl.this.logger.debug(method);
         }

         this.checkDigestAvailable();
         DigestNotAvailableException lastException = null;
         int i = 0;

         while(i < WSPasswordDigestServiceImpl.this.providers.length) {
            AuditAtnEventImpl atnAuditEvent;
            try {
               if (debug) {
                  WSPasswordDigestServiceImpl.this.logger.debug(method + " Trying Provider[" + i + "]");
               }

               byte[] retVal = WSPasswordDigestServiceImpl.this.providers[i].getDerivedKey(username, salt, iteration);
               if (WSPasswordDigestServiceImpl.this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, username, (ContextHandler)null, AtnEventTypeV2.CREATEDERIVEDKEY, (Exception)null);
                  WSPasswordDigestServiceImpl.this.auditService.writeEvent(atnAuditEvent);
               }

               return retVal;
            } catch (DigestNotAvailableException var10) {
               if (WSPasswordDigestServiceImpl.this.auditService.isAuditEnabled()) {
                  atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.FAILURE, username, (ContextHandler)null, AtnEventTypeV2.CREATEDERIVEDKEY, (Exception)null);
                  WSPasswordDigestServiceImpl.this.auditService.writeEvent(atnAuditEvent);
               }

               lastException = var10;
               if (debug) {
                  WSPasswordDigestServiceImpl.this.logger.debug(method + " Provider[" + i + "] failed", var10);
               }

               ++i;
            }
         }

         throw lastException;
      }

      // $FF: synthetic method
      ServiceImpl(Object x1) {
         this();
      }
   }
}
