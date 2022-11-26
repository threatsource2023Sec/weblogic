package com.bea.security.css;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceNotFoundException;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.security.service.IdentityService;

/** @deprecated */
@Deprecated
public final class CSS {
   public static final String AUDIT_SERVICE = "AuditService";
   public static final String CHALLENGE_IDENTITY_ASSERTION_SERVICE = "ChallengeIdentityAssertionService";
   public static final String IDENTITY_ASSERTION_SERVICE = "IdentityAssertionService";
   public static final String JAAS_AUTHENTICATION_SERVICE = "JAASAuthenticationService";
   public static final String IMPERSONATION_SERVICE = "ImpersonationService";
   public static final String AUTHORIZATION_SERVICE = "AuthorizationService";
   public static final String ROLE_MAPPING_SERVICE = "RoleMappingService";
   public static final String IS_PROTECTED_SERVICE = "IsProtectedResourceService";
   public static final String BULK_AUTHORIZATION_SERVICE = "BulkAuthorizationService";
   public static final String BULK_ROLE_MAPPING_SERVICE = "BulkRoleMappingService";
   public static final String POLICY_CONSUMER_SERVICE = "PolicyConsumerService";
   public static final String ROLE_CONSUMER_SERVICE = "RoleConsumerService";
   public static final String POLICY_DEPLOYMENT_SERVICE = "PolicyDeploymentService";
   public static final String ROLE_DEPLOYMENT_SERVICE = "RoleDeploymentService";
   public static final String CERT_PATH_BUILDER_SERVICE = "CertPathBuilderService";
   public static final String CERT_PATH_VALIDATOR_SERVICE = "CertPathValidatorService";
   public static final String CREDENTIAL_MAPPING_SERVICE = "CredentialMappingService";
   public static final String SECURITY_TOKEN_SERVICE = "SecurityTokenService";
   public static final String IDENTITY_SERVICE = "IdentityService";
   public static final String LOGGER_SERVICE = "LoggerService";
   public static final String PRINCIPAL_VALIDATION_SERVICE = "PrincipalValidationService";
   public static final String SAML_SSO_SERVICE = "SAMLSingleSignOnService";
   public static final String SPNEGO_SSO_SERVICE = "SPNEGOSingleSignOnService";
   public static final String SAML2_SSO_SERVICE = "SingleSignOnService";
   public static final String LOGIN_SESSION_SERVICE = "LoginSessionService";
   public static final String AJUDICATOR_PROVIDER = "AdjudicatorProvider_";
   public static final String AUDITOR_PROVIDER = "AuditorProvider_";
   public static final String AUTHENTICATOR_PROVIDER = "AuthenticatorProvider_";
   public static final String IDENTITY_ASSERTER_PROVIDER = "IdentityAsserterProvider_";
   public static final String AUTHORIZER_PROVIDER = "AuthorizerProvider_";
   public static final String ROLE_MAPPER_PROVIDER = "RoleMapperProvider_";
   public static final String CREDENTIAL_MAPPER_PROVIDER = "CredentialMapperProvider_";
   public static final String CERT_PATH_BUILDER_PROVIDER = "CertPathBuilderProvider_";
   public static final String CERT_PATH_VALIDATOR_PROVIDER = "CertPathValidatorProvider_";
   private CSSDelegate delegate = null;

   private CSS() {
   }

   public static CSS getInstance() {
      return new CSS();
   }

   public synchronized Object getService(String serviceName) throws ServiceInitializationException, ServiceNotFoundException {
      return this.delegate.getService(serviceName);
   }

   public synchronized String getServiceLoggingName(String serviceName) throws ServiceNotFoundException {
      return this.delegate.getServiceLoggingName(serviceName);
   }

   public synchronized Object getServiceManagementObject(String serviceName) throws ServiceInitializationException, ServiceNotFoundException {
      return this.delegate.getServiceManagementObject(serviceName);
   }

   public synchronized void shutdown() {
      this.delegate.shutdown();
   }

   public synchronized void initialize(CSSConfig config, ClassLoader loader, IdentityService identity, LoggerService logger) throws CSSConfigurationException {
      this.delegate.initialize(config, loader, identity, logger);
   }

   public void setDelegate(CSSDelegate delegate) {
      if (this.delegate == null) {
         this.delegate = delegate;
      }

   }

   public CSSDelegate getDelegate() {
      return this.delegate;
   }
}
