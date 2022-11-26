package weblogic.security.service;

import com.bea.common.security.service.CertPathBuilderService;
import com.bea.common.security.service.CertPathValidatorService;
import com.bea.security.css.CSS;
import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.X509Certificate;
import weblogic.management.security.ProviderMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.pk.CertPathBuilderParameters;
import weblogic.security.pk.CertPathSelector;
import weblogic.security.pk.CertPathValidatorParameters;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.shared.LoggerWrapper;

public class CertPathManagerImpl implements SecurityService, CertPathManager {
   private CertPathBuilderService certPathBuilderService = null;
   private CertPathValidatorService certPathValidatorService = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String MY_JDK_SECURITY_PROVIDER_NAME = "WLSJDKCertPathProvider";
   private static final String BUILDER_ALGORITHM = "WLSCertPathBuilder";
   private static final String VALIDATOR_ALGORITHM = "WLSCertPathValidator";
   private static LoggerWrapper log = LoggerWrapper.getInstance("SecurityCertPath");

   public CertPathManagerImpl() {
   }

   public CertPathManagerImpl(RealmServices realmServices, ProviderMBean[] configuration) {
      this.initialize(realmServices, configuration);
   }

   public void initialize(RealmServices realmServices, ProviderMBean[] configuration) {
      if (log.isDebugEnabled()) {
         log.debug("CertPathManager will use common security");
      }

      try {
         CSS css = realmServices.getCSS();
         this.certPathBuilderService = (CertPathBuilderService)css.getService("CertPathBuilderService");
         this.certPathValidatorService = (CertPathValidatorService)css.getService("CertPathValidatorService");
      } catch (Exception var5) {
         if (log.isDebugEnabled()) {
            SecurityLogger.logStackTrace(var5);
         }

         SecurityServiceRuntimeException ssre = new SecurityServiceRuntimeException(SecurityLogger.getExceptionObtainingService("Common CertPath Service", var5.toString()));
         ssre.initCause(var5);
         throw ssre;
      }

      if (log.isDebugEnabled()) {
         log.debug("CertPathManager initialized with " + configuration.length + "CertPathProvider(s).");
      }

      if (Security.getProvider("WLSJDKCertPathProvider") == null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               Security.addProvider(CertPathManagerImpl.this.new MyJDKSecurityProvider());
               return null;
            }
         });
      }

   }

   public void start() {
   }

   public void suspend() {
   }

   public void shutdown() {
      this.certPathBuilderService = null;
      this.certPathValidatorService = null;
   }

   public CertPathBuilderResult build(CertPathSelector selector, X509Certificate[] trustedCAs, ContextHandler context) throws CertPathBuilderException, InvalidAlgorithmParameterException {
      return this.certPathBuilderService.build(selector, trustedCAs, context);
   }

   public CertPathValidatorResult validate(CertPath certPath, X509Certificate[] trustedCAs, ContextHandler context) throws CertPathValidatorException, InvalidAlgorithmParameterException {
      return this.certPathValidatorService.validate(certPath, trustedCAs, context);
   }

   private static void checkRealm(String realm) throws InvalidAlgorithmParameterException {
      if (!SecurityServiceManager.getContextSensitiveRealmName().equals(realm)) {
         if (!SecurityServiceManager.doesRealmExistInternal(realm)) {
            throw new IllegalArgumentException(SecurityLogger.getInvalidRealmName(realm));
         }
      }
   }

   private class MyJDKSecurityProvider extends Provider {
      private MyJDKSecurityProvider() {
         super("WLSJDKCertPathProvider", 1.0, "WebLogic JDK CertPath provider");
         this.put("CertPathValidator.WLSCertPathValidator", "weblogic.security.service.CertPathManagerImpl$JDKCertPathValidator");
         this.put("CertPathBuilder.WLSCertPathBuilder", "weblogic.security.service.CertPathManagerImpl$JDKCertPathBuilder");
      }

      // $FF: synthetic method
      MyJDKSecurityProvider(Object x1) {
         this();
      }
   }

   public static class JDKCertPathValidator extends CertPathValidatorSpi {
      public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters params) throws CertPathValidatorException, InvalidAlgorithmParameterException {
         if (!(params instanceof CertPathValidatorParameters)) {
            throw new InvalidAlgorithmParameterException(SecurityLogger.getWLSJDKCertPathValidatorIllegalCertPathParameters());
         } else {
            CertPathValidatorParameters typedParams = (CertPathValidatorParameters)params;
            String realm = typedParams.getRealmName();
            CertPathManagerImpl.checkRealm(realm);
            SecurityService.ServiceType type = ServiceType.CERTPATH;
            CertPathManager manager = (CertPathManager)SecurityServiceManager.getSecurityService(CertPathManagerImpl.kernelId, realm, type);
            return manager.validate(certPath, typedParams.getTrustedCAs(), typedParams.getContext());
         }
      }
   }

   public static class JDKCertPathBuilder extends CertPathBuilderSpi {
      public CertPathBuilderResult engineBuild(CertPathParameters params) throws CertPathBuilderException, InvalidAlgorithmParameterException {
         if (!(params instanceof CertPathBuilderParameters)) {
            throw new InvalidAlgorithmParameterException(SecurityLogger.getWLSJDKCertPathBuilderIllegalCertPathParameters());
         } else {
            CertPathBuilderParameters typedParams = (CertPathBuilderParameters)params;
            String realm = typedParams.getRealmName();
            CertPathManagerImpl.checkRealm(realm);
            SecurityService.ServiceType type = ServiceType.CERTPATH;
            CertPathManager manager = (CertPathManager)SecurityServiceManager.getSecurityService(CertPathManagerImpl.kernelId, realm, type);
            return manager.build(typedParams.getSelector(), typedParams.getTrustedCAs(), typedParams.getContext());
         }
      }
   }
}
