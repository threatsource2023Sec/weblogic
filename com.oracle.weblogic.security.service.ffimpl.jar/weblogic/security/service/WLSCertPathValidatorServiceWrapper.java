package weblogic.security.service;

import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.CertPathValidatorService;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.X509Certificate;

class WLSCertPathValidatorServiceWrapper implements CertPathValidatorService {
   private LoggerSpi logger;
   private CertPathValidatorService baseService;

   public WLSCertPathValidatorServiceWrapper(CertPathValidatorService baseService, LoggerService loggerService) {
      this.logger = loggerService.getLogger("SecurityCertPath");
      this.baseService = baseService;
   }

   public CertPathValidatorResult validate(CertPath certPath, X509Certificate[] trustedCAs, ContextHandler context) throws CertPathValidatorException, InvalidAlgorithmParameterException {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".validate" : null;
      if (debug) {
         this.logger.debug(method);
      }

      X509Certificate[] actualTrustedCAs = WLSCertPathBuilderServiceWrapper.getActualTrustedCAs(trustedCAs);
      return this.baseService.validate(certPath, actualTrustedCAs, context);
   }
}
