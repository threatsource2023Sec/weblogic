package weblogic.security.internal;

import java.util.HashSet;
import weblogic.logging.Loggable;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SecureModeMBean;
import weblogic.management.configuration.SecurityConfigurationMBean;
import weblogic.management.security.IdentityDomainAwareProviderMBean;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.AuthenticatorMBean;
import weblogic.management.security.authentication.IdentityAsserterMBean;
import weblogic.management.security.authorization.AuthorizerMBean;
import weblogic.management.security.authorization.DeployableAuthorizerMBean;
import weblogic.management.security.authorization.DeployableRoleMapperMBean;
import weblogic.management.security.authorization.PolicyConsumerMBean;
import weblogic.management.security.authorization.PolicyReaderMBean;
import weblogic.management.security.authorization.RoleMapperMBean;
import weblogic.management.security.authorization.RoleReaderMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;
import weblogic.management.security.pk.CertPathBuilderMBean;
import weblogic.management.security.pk.CertPathProviderMBean;
import weblogic.management.security.pk.CertPathValidatorMBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.management.utils.NotFoundException;
import weblogic.security.SecurityLogger;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.utils.SecurityUtils;
import weblogic.server.lifecycle.WebLogicServerRunState;
import weblogic.utils.LocatorUtilities;

public class RealmValidatorImpl {
   static final String DEFAULT_AUTHORIZER = "weblogic.security.providers.authorization.DefaultAuthorizationProviderImpl";
   static final String SAML_V1_IA = "weblogic.security.providers.saml.SAMLIdentityAsserterProviderImpl";
   static final String SAML_V2_IA = "weblogic.security.providers.saml.SAMLIdentityAsserterV2ProviderImpl";
   static final String SAML_V1_CREDMAP = "weblogic.security.providers.saml.SAMLCredentialMapperProviderImpl";
   static final String SAML_V2_CREDMAP = "weblogic.security.providers.saml.SAMLCredentialMapperV2ProviderImpl";
   private static final String ADMIN_ROLE = "Admin";
   private static final String ADMIN_IDD_GROUP = "AdminIDDGroup";
   private static final String ADMINISTRATIVE_GROUP = "AdministrativeGroup";
   private static final String WEBLOGIC_SECURITY = "weblogic.security";
   private static final String APP_ROLE_NAME = "IDCSAppRoleName";
   private static final String IN_SECURE_MODE = "InSecureMode";
   private static final String JNDI_POLICY = "type=<jndi>, application=, path={weblogic,management,mbeanservers}, action=lookup";
   private static boolean isBooting = true;

   public void validate(RealmMBean realm) throws ErrorCollectionException {
      boolean isIDD = this.isIDDDomain(realm);
      boolean checkIDDAware = isIDD || this.isIDDAwareProvidersRequired(realm);
      ErrorCollectionException errors = new ErrorCollectionException(SecurityLogger.getInvalidRealmWarning(realm.getName()));
      this.checkAuthenticationProviders(realm, errors);
      this.checkRoleMappers(realm, errors);
      int numAuthorizers = this.checkAuthorizers(realm, errors);
      this.checkAdjudicator(realm, errors, numAuthorizers);
      this.checkCredentialMappers(realm, errors);
      this.checkCertPathProviders(realm, errors);
      this.checkSAMLProviders(realm, errors);
      if (checkIDDAware) {
         this.checkIDDAwareProviders(realm, realm.getAuditors(), errors);
         this.checkIDDAwareProviders(realm, realm.getAuthorizers(), errors);
         this.checkIDDAwareProviders(realm, realm.getCredentialMappers(), errors);
         this.checkIDDAwareProviders(realm, realm.getRoleMappers(), errors);
      }

      if (isIDD && !isBooting()) {
         this.checkRoleMapperPolicies(realm, errors);
      }

      SecureModeMBean secMode = this.getSecureMode(realm);
      if (secMode != null && secMode.isSecureModeEnabled() && !isBooting()) {
         this.checkAtzPolicies(realm, errors);
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void checkAuthenticationProviders(RealmMBean realm, ErrorCollectionException errors) {
      AuthenticationProviderMBean[] providers = realm.getAuthenticationProviders();
      this.checkHaveAuthenticator(realm, providers, errors);
      this.checkActiveTypesUnique(realm, providers, errors);
   }

   private void checkHaveAuthenticator(RealmMBean realm, AuthenticationProviderMBean[] providers, ErrorCollectionException errors) {
      boolean found = false;

      for(int i = 0; !found && providers != null && i < providers.length; ++i) {
         if (providers[i] instanceof AuthenticatorMBean) {
            found = true;
         }
      }

      if (!found) {
         this.addError(errors, SecurityLogger.getInvalidRealmNoAuthenticatorWarning(realm.getName()));
      }

   }

   private void checkActiveTypesUnique(RealmMBean realm, AuthenticationProviderMBean[] providers, ErrorCollectionException errors) {
      HashSet allActiveTypes = new HashSet();

      for(int i = 0; providers != null && i < providers.length; ++i) {
         if (providers[i] instanceof IdentityAsserterMBean) {
            IdentityAsserterMBean provider = (IdentityAsserterMBean)providers[i];
            String[] activeTypes = provider.getActiveTypes();

            for(int j = 0; activeTypes != null && j < activeTypes.length; ++j) {
               String activeType = activeTypes[j];
               if (activeType != null && activeType.length() > 0) {
                  if (allActiveTypes.contains(activeType) && !"Authorization".equalsIgnoreCase(activeType)) {
                     this.addError(errors, SecurityLogger.getInvalidRealmMultipleIdentityAssertersForActiveTokenTypeWarning(realm.getName(), activeType));
                  } else {
                     allActiveTypes.add(activeType);
                  }
               }
            }
         }
      }

   }

   private void checkRoleMappers(RealmMBean realm, ErrorCollectionException errors) {
      RoleMapperMBean[] providers = realm.getRoleMappers();
      if (providers != null && providers.length != 0) {
         boolean foundDeployable = false;
         boolean foundDeployEnabled = false;

         for(int i = 0; !foundDeployEnabled && i < providers.length; ++i) {
            if (providers[i] instanceof DeployableRoleMapperMBean) {
               foundDeployable = true;
               DeployableRoleMapperMBean provider = (DeployableRoleMapperMBean)providers[i];
               if (provider.isRoleDeploymentEnabled()) {
                  foundDeployEnabled = true;
               }
            }
         }

         if (!foundDeployable) {
            if (!isBooting()) {
               this.addError(errors, SecurityLogger.getInvalidRealmNoDeployableRoleMapperWarning(realm.getName()));
            } else {
               SecurityLogger.logNoDeployableProviderProperlyConfigured(realm.getName(), "DeployableRoleMapper");
            }
         } else if (!foundDeployEnabled) {
            if (!isBooting()) {
               this.addError(errors, SecurityLogger.getInvalidRealmNoDeployableRoleMapperEnabledWarning(realm.getName()));
            } else {
               SecurityLogger.logNoDeployableProviderProperlyConfigured(realm.getName(), "DeployableRoleMapper");
            }
         }

      } else {
         this.addError(errors, SecurityLogger.getInvalidRealmNoRoleMapperWarning(realm.getName()));
      }
   }

   private int checkAuthorizers(RealmMBean realm, ErrorCollectionException errors) {
      AuthorizerMBean[] providers = realm.getAuthorizers();
      if (providers != null && providers.length != 0) {
         boolean foundDeployable = false;
         boolean foundDeployEnabled = false;

         for(int i = 0; !foundDeployEnabled && i < providers.length; ++i) {
            if (providers[i] instanceof DeployableAuthorizerMBean) {
               foundDeployable = true;
               DeployableAuthorizerMBean provider = (DeployableAuthorizerMBean)providers[i];
               if (provider.isPolicyDeploymentEnabled()) {
                  foundDeployEnabled = true;
               }
            }
         }

         if (!foundDeployable) {
            if (!isBooting()) {
               this.addError(errors, SecurityLogger.getInvalidRealmNoDeployableAuthorizerWarning(realm.getName()));
            } else {
               SecurityLogger.logNoDeployableProviderProperlyConfigured(realm.getName(), "DeployableAuthorizer");
            }
         } else if (!foundDeployEnabled) {
            if (!isBooting()) {
               this.addError(errors, SecurityLogger.getInvalidRealmNoDeployableAuthorizerEnabledWarning(realm.getName()));
            } else {
               SecurityLogger.logNoDeployableProviderProperlyConfigured(realm.getName(), "DeployableAuthorizer");
            }
         }

         if (realm.isDelegateMBeanAuthorization()) {
            boolean foundPolicyConsumer = false;

            for(int i = 0; !foundPolicyConsumer && i < providers.length; ++i) {
               if (providers[i] instanceof PolicyConsumerMBean) {
                  foundPolicyConsumer = true;
               }
            }

            if (!foundPolicyConsumer) {
               this.addError(errors, SecurityLogger.getInvalidRealmNoMBeanDelegationWarning(realm.getName()));
            }
         }

         return providers.length;
      } else {
         this.addError(errors, SecurityLogger.getInvalidRealmNoAuthorizerWarning(realm.getName()));
         return 0;
      }
   }

   private void checkCredentialMappers(RealmMBean realm, ErrorCollectionException errors) {
      CredentialMapperMBean[] providers = realm.getCredentialMappers();
      if (providers == null || providers.length == 0) {
         this.addError(errors, SecurityLogger.getInvalidRealmNoCredentialMapperWarning(realm.getName()));
      }
   }

   private void checkAdjudicator(RealmMBean realm, ErrorCollectionException errors, int numAuthorizers) {
      if (realm.getAdjudicator() == null && numAuthorizers > 1) {
         this.addError(errors, SecurityLogger.getInvalidRealmNoAdjudicatorWarning(realm.getName()));
      }

   }

   private boolean providerIsA(ProviderMBean provider, String desiredProviderClassName) {
      return provider == null ? false : desiredProviderClassName.equals(provider.getProviderClassName());
   }

   private int providerCount(ProviderMBean[] providers, String desiredProviderClassName) {
      int count = 0;

      for(int i = 0; providers != null && i < providers.length; ++i) {
         if (this.providerIsA(providers[i], desiredProviderClassName)) {
            ++count;
         }
      }

      return count;
   }

   private void checkCertPathProviders(RealmMBean realm, ErrorCollectionException errors) {
      CertPathProviderMBean[] providers = realm.getCertPathProviders();
      if (providers == null || providers.length < 1) {
         this.addError(errors, SecurityLogger.getInvalidRealmNoCertPathProvidersWarning(realm.getName()));
      }

      CertPathBuilderMBean builder = realm.getCertPathBuilder();
      if (builder == null) {
         this.addError(errors, SecurityLogger.getInvalidRealmNoCertPathBuilderWarning(realm.getName()));
      } else {
         if (builder.getRealm() == null || !builder.getRealm().getName().equals(realm.getName())) {
            this.addError(errors, SecurityLogger.getInvalidRealmIllegalCertPathBuilderWarning(realm.getName()));
         }

         boolean found = false;

         for(int i = 0; !found && providers != null && i < providers.length; ++i) {
            if (providers[i] instanceof CertPathValidatorMBean) {
               found = true;
            }
         }

         if (!found) {
            this.addError(errors, SecurityLogger.getInvalidRealmNoCertPathValidatorWarning(realm.getName()));
         }

      }
   }

   private void checkSAMLProviders(RealmMBean realm, ErrorCollectionException errors) {
      AuthenticationProviderMBean[] atn = realm.getAuthenticationProviders();
      int iaV1 = this.providerCount(atn, "weblogic.security.providers.saml.SAMLIdentityAsserterProviderImpl");
      int iaV2 = this.providerCount(atn, "weblogic.security.providers.saml.SAMLIdentityAsserterV2ProviderImpl");
      CredentialMapperMBean[] cred = realm.getCredentialMappers();
      int credV1 = this.providerCount(cred, "weblogic.security.providers.saml.SAMLCredentialMapperProviderImpl");
      int credV2 = this.providerCount(cred, "weblogic.security.providers.saml.SAMLCredentialMapperV2ProviderImpl");
      if (iaV1 != 0 || iaV2 != 0 || credV1 != 0 || credV2 != 0) {
         if (iaV1 <= 1 && iaV2 <= 1 && credV1 <= 1 && credV2 <= 1) {
            if (iaV1 > 0 && iaV2 > 0 || credV1 > 0 && credV2 > 0 || iaV1 > 0 && credV2 > 0 || iaV2 > 0 && credV1 > 0) {
               this.addError(errors, SecurityLogger.getInvalidRealmSAMLConfigWarning(realm.getName()));
            }
         } else {
            this.addError(errors, SecurityLogger.getInvalidRealmSAMLConfigWarning(realm.getName()));
         }
      }
   }

   private void addError(ErrorCollectionException errors, String error) {
      errors.add(new Exception(error));
   }

   private static synchronized boolean isBooting() {
      if (isBooting) {
         WebLogicServerRunState runState = (WebLogicServerRunState)LocatorUtilities.getService(WebLogicServerRunState.class);
         if (runState.getRunState() == 2) {
            isBooting = false;
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private DomainMBean getDomain(RealmMBean realm) {
      if (!(realm.getParentBean() instanceof SecurityConfigurationMBean)) {
         return null;
      } else {
         SecurityConfigurationMBean secCfg = (SecurityConfigurationMBean)realm.getParentBean();
         if (!(secCfg.getParentBean() instanceof DomainMBean)) {
            return null;
         } else {
            DomainMBean domain = (DomainMBean)secCfg.getParentBean();
            return domain;
         }
      }
   }

   private SecureModeMBean getSecureMode(RealmMBean realm) {
      if (!(realm.getParentBean() instanceof SecurityConfigurationMBean)) {
         return null;
      } else {
         SecurityConfigurationMBean secCfg = (SecurityConfigurationMBean)realm.getParentBean();
         return secCfg.getSecureMode();
      }
   }

   private boolean isIDDDomain(RealmMBean realm) {
      DomainMBean domain = this.getDomain(realm);
      return SecurityUtils.isIDDDomain(domain);
   }

   private boolean isIDDAwareProvidersRequired(RealmMBean realm) {
      DomainMBean domain = this.getDomain(realm);
      return domain.getSecurityConfiguration().isIdentityDomainAwareProvidersRequired();
   }

   private void checkRoleMapperPolicies(RealmMBean realm, ErrorCollectionException errors) {
      if (SecurityServiceManager.doesRealmExist(realm.getName())) {
         RoleMapperMBean[] roleMappers = realm.getRoleMappers();

         for(int i = 0; roleMappers != null && i < roleMappers.length; ++i) {
            RoleMapperMBean roleMapper = roleMappers[i];
            if (roleMapper instanceof RoleReaderMBean && roleMapper.getProviderClassName().startsWith("weblogic.security") && roleMapper instanceof IdentityDomainAwareProviderMBean) {
               RoleReaderMBean rr = (RoleReaderMBean)roleMapper;

               try {
                  String expr = rr.getRoleExpression("", "Admin");
                  if (expr != null && !expr.contains("AdminIDDGroup") && !expr.contains("AdministrativeGroup") && !expr.contains("IDCSAppRoleName")) {
                     Loggable msg = SecurityLogger.logPartitionsRequireNewRealmLoggable(realm.getName());
                     msg.log();
                     throw new IllegalArgumentException(msg.getMessage());
                  }
               } catch (NotFoundException var9) {
               }
            }
         }

      }
   }

   private void checkIDDAwareProviders(RealmMBean realm, ProviderMBean[] providers, ErrorCollectionException errors) {
      for(int i = 0; i < providers.length; ++i) {
         if (!(providers[i] instanceof IdentityDomainAwareProviderMBean)) {
            if (!isBooting()) {
               this.addError(errors, SecurityLogger.getInvalidRealmProviderNotIDDAwareWarning(realm.getName(), providers[i].getName()));
            } else {
               SecurityLogger.logNotIDDAwareProvider(realm.getName(), providers[i].getName());
            }
         }
      }

   }

   private void checkAtzPolicies(RealmMBean realm, ErrorCollectionException errors) {
      if (SecurityServiceManager.doesRealmExist(realm.getName())) {
         AuthorizerMBean[] authorizers = realm.getAuthorizers();

         for(int i = 0; authorizers != null && i < authorizers.length; ++i) {
            AuthorizerMBean authorizer = authorizers[i];
            if (authorizer instanceof PolicyReaderMBean && authorizer.getProviderClassName().startsWith("weblogic.security")) {
               PolicyReaderMBean rr = (PolicyReaderMBean)authorizer;

               try {
                  String expr = rr.getPolicyExpression("type=<jndi>, application=, path={weblogic,management,mbeanservers}, action=lookup");
                  if (expr != null && !expr.contains("InSecureMode")) {
                     Loggable msg = SecurityLogger.logSecureModeRequiresNewRealmLoggable(realm.getName());
                     msg.log();
                     throw new IllegalArgumentException(msg.getMessage());
                  }
               } catch (NotFoundException var9) {
               }
            }
         }

      }
   }
}
