package weblogic.security.service.internal;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.LDAPSSLSocketFactoryLookupService;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLSocketFactory;
import weblogic.management.configuration.ConfigurationException;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.utils.SSLContextManager;

public class LDAPSSLSocketFactoryLookupServiceImpl implements ServiceLifecycleSpi, LDAPSSLSocketFactoryLookupService {
   private LoggerSpi logger;
   private AuthenticatedSubject kernelId;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.LDAPSSLSocketFactoryLookupService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof LDAPSSLSocketFactoryLookupServiceConfig) {
         LDAPSSLSocketFactoryLookupServiceConfig myconfig = (LDAPSSLSocketFactoryLookupServiceConfig)config;
         this.kernelId = myconfig.getKernelId();
         if (this.kernelId == null) {
            throw new ServiceConfigurationException(SecurityLogger.getNullParameterSupplied("LDAPSSLSocketFactoryLookupServiceConfig.getKernelId"));
         } else {
            return Delegator.getInstance((Class)LDAPSSLSocketFactoryLookupService.class, this);
         }
      } else {
         throw new ServiceConfigurationException(SecurityLogger.getNullParameterSupplied("LDAPSSLSocketFactoryLookupServiceConfig"));
      }
   }

   public void shutdown() {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug(this.getClass().getName() + ".shutdown");
      }

   }

   public SSLSocketFactory getSSLSocketFactory() {
      try {
         return SSLContextManager.getDefaultClientSSLSocketFactory("ldaps", this.kernelId);
      } catch (ConfigurationException var2) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".getSSLSocketFactory got ConfigurationException " + var2.getMessage());
         }
      } catch (CertificateException var3) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".getSSLSocketFactory got CertificateException " + var3.getMessage());
         }
      } catch (Exception var4) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".getSSLSocketFactory got an error in creating SSL socket factory: " + var4.getMessage());
         }
      }

      return null;
   }
}
