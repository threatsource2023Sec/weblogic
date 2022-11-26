package weblogic.security.service;

import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.service.CertPathBuilderService;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.X509Certificate;
import weblogic.security.pk.CertPathSelector;
import weblogic.security.utils.SSLContextManager;

class WLSCertPathBuilderServiceWrapper implements CertPathBuilderService {
   private LoggerSpi logger;
   private CertPathBuilderService baseService;

   public WLSCertPathBuilderServiceWrapper(CertPathBuilderService baseService, LoggerService loggerService) {
      this.logger = loggerService.getLogger("SecurityCertPath");
      this.baseService = baseService;
   }

   static X509Certificate[] getActualTrustedCAs(X509Certificate[] trustedCAs) {
      if (trustedCAs != null) {
         for(int i = 0; i < trustedCAs.length; ++i) {
            if (trustedCAs[i] == null) {
               throw new IllegalArgumentException(SecurityLogger.getCertPathManagerNullTrustedCAError());
            }
         }

         return trustedCAs;
      } else {
         try {
            return SSLContextManager.getServerTrustedCAs();
         } catch (Exception var2) {
            throw new SecurityServiceRuntimeException(SecurityLogger.getGetDefaultTrustedCAsError(var2));
         }
      }
   }

   public CertPathBuilderResult build(CertPathSelector selector, X509Certificate[] trustedCAs, ContextHandler context) throws CertPathBuilderException, InvalidAlgorithmParameterException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".build" : null;
      if (debug) {
         this.logger.debug(method);
      }

      X509Certificate[] actualTrustedCAs = getActualTrustedCAs(trustedCAs);
      return this.baseService.build(selector, actualTrustedCAs, context);
   }
}
