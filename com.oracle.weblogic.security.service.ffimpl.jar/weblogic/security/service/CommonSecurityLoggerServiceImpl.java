package weblogic.security.service;

import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import java.util.HashMap;
import java.util.logging.Logger;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.LoggingHelper;

public class CommonSecurityLoggerServiceImpl implements LoggerService {
   private boolean appletEnvironment = false;
   private static final String COMMON = "com.bea.common.";
   private static final int COMMON_OFFSET = "com.bea.common.".length();
   private static final String ENGINE = "engine.";
   private static final int ENGINE_OFFSET = "engine.".length();
   private static final String SERVICE = "security.service.";
   private static final int SERVICE_OFFSET = "security.service.".length();
   private HashMap loggerImpls = null;
   private Logger serverLogger = null;

   public CommonSecurityLoggerServiceImpl() {
      this.loggerImpls = new HashMap();
      this.serverLogger = this.getServerLogger();
   }

   public synchronized LoggerSpi getLogger(String name) {
      boolean commonPackage = false;
      if (name != null && name.length() != 0) {
         LoggerSpi theLogger = (LoggerSpi)this.loggerImpls.get(name);
         if (theLogger != null) {
            return theLogger;
         } else {
            DebugLogger debugLogger = null;
            if (!name.startsWith("com.bea.common.")) {
               if (name.startsWith("Security")) {
                  debugLogger = this.getDebugLogger("Debug" + name);
               } else {
                  debugLogger = this.getDebugLogger(name);
               }
            } else {
               name = name.substring(COMMON_OFFSET);
               if (name.startsWith("security.service.")) {
                  name = name.substring(SERVICE_OFFSET);
                  if (name.equals("AccessDecisionService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtz");
                  } else if (name.equals("AdjudicationService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAdjudicator");
                  } else if (name.equals("AuditService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAuditor");
                  } else if (name.equals("AuthorizationService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtz");
                  } else if (name.equals("CertPathBuilderService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityCertPath");
                  } else if (name.equals("CertPathValidatorService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityCertPath");
                  } else if (name.equals("ChallengeIdentityAssertionService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("ChallengeIdentityAssertionTokenService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("CredentialMappingService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityCredMap");
                  } else if (name.equals("SecurityTokenService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityCredMap");
                  } else if (name.equals("Identity")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("IdentityAssertionCallbackService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("IdentityAssertionService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("IdentityAssertionTokenService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("IdentityCacheService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("IdentityImpersonationService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("IdentityService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("JAASAuthenticationConfigurationService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("JAASAuthenticationService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("JAASIdentityAssertionConfigurationService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("JAASLoginService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("PrincipalValidationService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("RoleMappingService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityRoleMap");
                  } else if (name.equals("StoreService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityRealm");
                  } else if (name.equals("SAML2Service")) {
                     debugLogger = this.getDebugLogger("DebugSecuritySAML2Service");
                  } else if (name.equals("SAMLKeyService")) {
                     debugLogger = this.getDebugLogger("DebugSecuritySAML2Service");
                  } else if (name.equals("PolicyConsumerService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtz");
                  } else if (name.equals("PolicyDeploymentService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtz");
                  } else if (name.equals("RoleConsumerService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtz");
                  } else if (name.equals("RoleDeploymentService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtz");
                  } else if (name.equals("NegotiateIdentityAsserterService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityAtn");
                  } else if (name.equals("NamedSQLConnectionLookupService")) {
                     debugLogger = this.getDebugLogger("DebugSecurityRealm");
                  }
               } else if (name.startsWith("engine.")) {
                  debugLogger = this.getDebugLogger("DebugSecurityRealm");
               }
            }

            LoggerSpi theLogger = new CommonSecurityLoggerSpiImpl(this.serverLogger, debugLogger);
            this.loggerImpls.put(name, theLogger);
            return theLogger;
         }
      } else {
         return null;
      }
   }

   private Logger getServerLogger() {
      if (this.appletEnvironment) {
         return null;
      } else {
         try {
            return LoggingHelper.getServerLogger();
         } catch (SecurityException var2) {
            this.appletEnvironment = true;
            return null;
         }
      }
   }

   private DebugLogger getDebugLogger(String name) {
      if (this.appletEnvironment) {
         return null;
      } else {
         try {
            return DebugLogger.getDebugLogger(name);
         } catch (SecurityException var3) {
            this.appletEnvironment = true;
            return null;
         }
      }
   }
}
