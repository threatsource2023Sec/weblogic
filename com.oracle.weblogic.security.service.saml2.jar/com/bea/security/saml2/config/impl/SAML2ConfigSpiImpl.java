package com.bea.security.saml2.config.impl;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.CredentialMappingService;
import com.bea.common.security.service.IdentityAssertionService;
import com.bea.common.security.service.IdentityService;
import com.bea.common.security.service.LoginSessionService;
import com.bea.common.security.service.SAMLKeyInfoSpi;
import com.bea.common.security.service.SAMLKeyService;
import com.bea.common.security.utils.CSSPlatformProxy;
import com.bea.common.store.service.StoreService;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.artifact.ArtifactResolver;
import com.bea.security.saml2.artifact.ArtifactStore;
import com.bea.security.saml2.artifact.SAML2ArtifactException;
import com.bea.security.saml2.artifact.impl.ArtifactResolverJSSEImpl;
import com.bea.security.saml2.artifact.impl.ArtifactResolverWLSImpl;
import com.bea.security.saml2.artifact.impl.ArtifactStoreImpl;
import com.bea.security.saml2.binding.BindingHandlerFactory;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.registry.PartnerManager;
import com.bea.security.saml2.service.ServiceFactory;
import com.bea.security.saml2.util.key.KeyManagerException;
import com.bea.security.saml2.util.key.SAML2KeyManager;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;

public class SAML2ConfigSpiImpl implements SAML2ConfigSpi, BeanUpdateListener {
   private String domainName;
   private String realmName;
   private LoggerSpi logger;
   private CredentialMappingService cmService;
   private Object cmProvider;
   private IdentityAssertionService iaService;
   private Object iaProvider;
   private LoginSessionService sessionService;
   private StoreService storeService;
   private AuditService auditService;
   private IdentityService identityService;
   private SAMLKeyService keyService;
   private SingleSignOnServicesConfigSpi localConfig;
   private ArtifactStore artifactStore;
   private ServiceFactory serviceFactory;
   private PartnerManager partnerManager;
   private BindingHandlerFactory bindingHandlerFactory;
   private LegacyEncryptorSpi encryptSpi;
   private SAML2KeyManager saml2KeyManager;
   private DescriptorBean mBean = null;
   private static String TMP_KEY_NAME = "SAML2_TMP_VALIDATE_KEY";

   public SAML2ConfigSpiImpl(LoggerSpi logger, CredentialMappingService cmService, Object cmProvider, IdentityAssertionService iaService, Object iaProvider, LoginSessionService sessionService, StoreService storeService, AuditService auditService, IdentityService identityService, SingleSignOnServicesConfigSpi ssoMBean, SAMLKeyService keyService) {
      this.logger = logger;
      this.cmService = cmService;
      this.cmProvider = cmProvider;
      this.iaService = iaService;
      this.iaProvider = iaProvider;
      this.sessionService = sessionService;
      this.storeService = storeService;
      this.auditService = auditService;
      this.identityService = identityService;
      this.localConfig = new SingleSignOnServicesConfigSpiImpl(ssoMBean);
      this.keyService = keyService;
      if (ssoMBean instanceof DescriptorBean) {
         this.mBean = (DescriptorBean)ssoMBean;
         this.mBean.addBeanUpdateListener(this);
      }

   }

   public void close() {
      if (this.mBean != null) {
         this.mBean.removeBeanUpdateListener(this);
      }

      if (this.serviceFactory != null) {
         this.serviceFactory.updateConfig((SAML2ConfigSpi)null);
      }

   }

   public LoggerSpi getLogger() {
      return this.logger;
   }

   public CredentialMappingService getCredentialMappingService() {
      return this.cmService;
   }

   public Object getSAML2CredentialMapperMBean() {
      return this.cmProvider;
   }

   public IdentityAssertionService getIdentityAssertionService() {
      return this.iaService;
   }

   public Object getSAML2IdentityAsserterMBean() {
      return this.iaProvider;
   }

   public LoginSessionService getSessionService() {
      return this.sessionService;
   }

   public IdentityService getIdentityService() {
      return this.identityService;
   }

   public StoreService getStoreService() {
      return this.storeService;
   }

   public AuditService getAuditService() {
      return this.auditService;
   }

   public SAMLKeyService getSAMLKeyService() {
      return this.keyService;
   }

   public synchronized SingleSignOnServicesConfigSpi getLocalConfiguration() {
      return this.localConfig;
   }

   public ArtifactStore getArtifactStore() {
      if (this.artifactStore == null) {
         synchronized(this) {
            if (this.artifactStore == null) {
               this.artifactStore = new ArtifactStoreImpl(this);
            }
         }
      }

      return this.artifactStore;
   }

   public synchronized ArtifactResolver getArtifactResolver() {
      return (ArtifactResolver)(CSSPlatformProxy.getInstance().isOnWLS() ? new ArtifactResolverWLSImpl(this) : new ArtifactResolverJSSEImpl(this));
   }

   public PartnerManager getPartnerManager() {
      if (this.partnerManager == null) {
         synchronized(this) {
            if (this.partnerManager == null) {
               this.partnerManager = PartnerManager.newInstance(this);
            }
         }
      }

      return this.partnerManager;
   }

   public ServiceFactory getServiceFactory() {
      if (this.serviceFactory == null) {
         synchronized(this) {
            if (this.serviceFactory == null) {
               this.serviceFactory = ServiceFactory.newServiceFactory(this);
            }
         }
      }

      return this.serviceFactory;
   }

   public BindingHandlerFactory getBindingHandlerFactory() {
      if (this.bindingHandlerFactory == null) {
         synchronized(this) {
            if (this.bindingHandlerFactory == null) {
               this.bindingHandlerFactory = new BindingHandlerFactory(this);
            }
         }
      }

      return this.bindingHandlerFactory;
   }

   public String getRealmName() {
      return this.realmName;
   }

   public String getDomainName() {
      return this.domainName;
   }

   public void setRealmName(String realmName) {
      this.realmName = realmName;
   }

   public void setDomainName(String domainName) {
      this.domainName = domainName;
   }

   public LegacyEncryptorSpi getEncryptSpi() {
      return this.encryptSpi;
   }

   public void setEncryptSpi(LegacyEncryptorSpi encryptSpi) {
      this.encryptSpi = encryptSpi;
   }

   public synchronized void setSAML2KeyManager(SAML2KeyManager keyManager) {
      this.saml2KeyManager = keyManager;
   }

   public synchronized SAML2KeyManager getSAML2KeyManager() {
      return this.saml2KeyManager;
   }

   public synchronized void activateUpdate(BeanUpdateEvent arg0) throws BeanUpdateFailedException {
      ((SingleSignOnServicesConfigSpiImpl)this.localConfig).updateConfig((SingleSignOnServicesConfigSpi)arg0.getProposedBean());

      try {
         this.saml2KeyManager = new SAML2KeyManager(this);
      } catch (KeyManagerException var4) {
         throw new BeanUpdateFailedException(var4.getMessage());
      }

      if (this.artifactStore != null) {
         try {
            this.artifactStore.updateConfig(this.getLocalConfiguration().getArtifactMaxCacheSize(), this.getLocalConfiguration().getArtifactTimeout());
         } catch (SAML2ArtifactException var3) {
            var3.printStackTrace();
         }

         this.artifactStore = null;
      }

      this.partnerManager = null;
      if (this.serviceFactory != null) {
         this.serviceFactory.updateConfig(this);
      }

      this.bindingHandlerFactory = null;
   }

   public void prepareUpdate(BeanUpdateEvent arg0) throws BeanUpdateRejectedException {
      this.checkLocalConfig((SingleSignOnServicesConfigSpi)arg0.getProposedBean());
   }

   public synchronized void rollbackUpdate(BeanUpdateEvent arg0) {
      ((SingleSignOnServicesConfigSpiImpl)this.localConfig).updateConfig((SingleSignOnServicesConfigSpi)arg0.getSourceBean());

      try {
         this.saml2KeyManager = new SAML2KeyManager(this);
      } catch (KeyManagerException var3) {
         throw new RuntimeException(var3.getMessage());
      }
   }

   private void checkLocalConfig(SingleSignOnServicesConfigSpi config) throws BeanUpdateRejectedException {
      this.checkKeyManagerConfig(config.getSSOSigningKeyPassPhrase(), config.getSSOSigningKeyAlias());
      this.checkKeyManagerConfig(config.getTransportLayerSecurityKeyPassPhrase(), config.getTransportLayerSecurityKeyAlias());
   }

   private void checkKeyManagerConfig(String keyPass, String alias) throws BeanUpdateRejectedException {
      char[] passphrase = null;
      SAMLKeyInfoSpi keyInfoSpi = null;
      if (keyPass != null) {
         passphrase = keyPass.toCharArray();
      }

      if (alias != null && alias.trim().length() != 0 && passphrase != null && passphrase.length != 0) {
         keyInfoSpi = this.keyService.getKeyInfo(TMP_KEY_NAME, alias, passphrase);
         if (keyInfoSpi == null || !keyInfoSpi.isValid()) {
            throw new BeanUpdateRejectedException(Saml2Logger.getInvalidKeyInfo());
         }
      }
   }

   private static class SingleSignOnServicesConfigSpiImpl implements SingleSignOnServicesConfigSpi {
      private int artifactMaxCacheSize;
      private int artifactTimeout;
      private int authnRequestMaxCacheSize;
      private int authnRequestTimeout;
      private String basicAuthPassword;
      private String contactPersonSurName;
      private String contactPersonGivenName;
      private String contactPersonEmailAddress;
      private String contactPersonCompany;
      private String basicAuthUsername;
      private byte[] basicAuthPasswordEncrypted;
      private String contactPersonTelephoneNumber;
      private String contactPersonType;
      private String defaultURL;
      private String entityID;
      private String errorPath;
      private String identityProviderPreferredBinding;
      private String loginReturnQueryParameter;
      private String loginURL;
      private String organizationName;
      private String organizationURL;
      private String publishedSiteURL;
      private String ssoSigningKeyAlias;
      private String ssoSigningKeyPassPhrase;
      private byte[] ssoSigningKeyPassPhraseEncrypted;
      private String serviceProviderPreferredBinding;
      private String transportLayerSecurityKeyAlias;
      private String transportLayerSecurityKeyPassPhrase;
      private byte[] transportLayerSecurityKeyPassPhraseEncrypted;
      private boolean forceAuthn;
      private boolean identityProviderArtifactBindingEnabled;
      private boolean identityProviderEnabled;
      private boolean identityProviderPOSTBindingEnabled;
      private boolean identityProviderRedirectBindingEnabled;
      private boolean postOneUseCheckEnabled;
      private boolean recipientCheckEnabled;
      private boolean passive;
      private boolean serviceProviderEnabled;
      private boolean serviceProviderArtifactBindingEnabled;
      private boolean signAuthnRequests;
      private boolean serviceProviderPOSTBindingEnabled;
      private boolean wantArtifactRequestsSigned;
      private boolean wantAssertionsSigned;
      private boolean wantAuthnRequestsSigned;
      private boolean wantBasicAuthClientAuthentication;
      private boolean wantTransportLayerSecurityClientAuthentication;
      private boolean useReplicatedCache;
      private boolean assertionEncryptionEnabled;
      private String dataEncryptionAlgorithm;
      private String keyEncryptionAlgorithm;
      private String[] metadataPublishedAlgorithms;
      private String assertionKeyAlias;
      private String assertionKeyAliasPassPhrase;
      private byte[] assertionKeyAliasPassPhraseEncrypted;
      private String[] allowedTargetHosts;

      public SingleSignOnServicesConfigSpiImpl(SingleSignOnServicesConfigSpi mbean) {
         this.updateConfig(mbean);
      }

      public void updateConfig(SingleSignOnServicesConfigSpi mbean) {
         this.artifactMaxCacheSize = mbean.getArtifactMaxCacheSize();
         this.artifactTimeout = mbean.getArtifactTimeout();
         this.authnRequestMaxCacheSize = mbean.getAuthnRequestMaxCacheSize();
         this.authnRequestTimeout = mbean.getAuthnRequestTimeout();
         this.basicAuthPassword = mbean.getBasicAuthPassword();
         this.basicAuthPasswordEncrypted = mbean.getBasicAuthPasswordEncrypted();
         this.basicAuthUsername = mbean.getBasicAuthUsername();
         this.contactPersonCompany = mbean.getContactPersonCompany();
         this.contactPersonEmailAddress = mbean.getContactPersonEmailAddress();
         this.contactPersonGivenName = mbean.getContactPersonGivenName();
         this.contactPersonSurName = mbean.getContactPersonSurName();
         this.contactPersonTelephoneNumber = mbean.getContactPersonTelephoneNumber();
         this.contactPersonType = mbean.getContactPersonType();
         this.defaultURL = mbean.getDefaultURL();
         this.entityID = mbean.getEntityID();
         this.errorPath = mbean.getErrorPath();
         this.forceAuthn = mbean.isForceAuthn();
         this.identityProviderArtifactBindingEnabled = mbean.isIdentityProviderArtifactBindingEnabled();
         this.identityProviderEnabled = mbean.isIdentityProviderEnabled();
         this.identityProviderPOSTBindingEnabled = mbean.isIdentityProviderPOSTBindingEnabled();
         this.identityProviderPreferredBinding = mbean.getIdentityProviderPreferredBinding();
         this.identityProviderRedirectBindingEnabled = mbean.isIdentityProviderRedirectBindingEnabled();
         this.loginReturnQueryParameter = mbean.getLoginReturnQueryParameter();
         this.loginURL = mbean.getLoginURL();
         this.organizationName = mbean.getOrganizationName();
         this.organizationURL = mbean.getOrganizationURL();
         this.passive = mbean.isPassive();
         this.postOneUseCheckEnabled = mbean.isPOSTOneUseCheckEnabled();
         this.publishedSiteURL = mbean.getPublishedSiteURL();
         this.recipientCheckEnabled = mbean.isRecipientCheckEnabled();
         this.serviceProviderArtifactBindingEnabled = mbean.isServiceProviderArtifactBindingEnabled();
         this.serviceProviderEnabled = mbean.isServiceProviderEnabled();
         this.serviceProviderPOSTBindingEnabled = mbean.isServiceProviderPOSTBindingEnabled();
         this.serviceProviderPreferredBinding = mbean.getServiceProviderPreferredBinding();
         this.signAuthnRequests = mbean.isSignAuthnRequests();
         this.ssoSigningKeyAlias = mbean.getSSOSigningKeyAlias();
         this.ssoSigningKeyPassPhrase = mbean.getSSOSigningKeyPassPhrase();
         this.ssoSigningKeyPassPhraseEncrypted = mbean.getSSOSigningKeyPassPhraseEncrypted();
         this.transportLayerSecurityKeyAlias = mbean.getTransportLayerSecurityKeyAlias();
         this.transportLayerSecurityKeyPassPhrase = mbean.getTransportLayerSecurityKeyPassPhrase();
         this.transportLayerSecurityKeyPassPhraseEncrypted = mbean.getTransportLayerSecurityKeyPassPhraseEncrypted();
         this.wantArtifactRequestsSigned = mbean.isWantArtifactRequestsSigned();
         this.wantAssertionsSigned = mbean.isWantAssertionsSigned();
         this.wantAuthnRequestsSigned = mbean.isWantAuthnRequestsSigned();
         this.wantBasicAuthClientAuthentication = mbean.isWantBasicAuthClientAuthentication();
         this.wantTransportLayerSecurityClientAuthentication = mbean.isWantTransportLayerSecurityClientAuthentication();
         this.useReplicatedCache = mbean.isReplicatedCacheEnabled();
         this.assertionEncryptionEnabled = mbean.isAssertionEncryptionEnabled();
         this.dataEncryptionAlgorithm = mbean.getDataEncryptionAlgorithm();
         this.keyEncryptionAlgorithm = mbean.getKeyEncryptionAlgorithm();
         this.metadataPublishedAlgorithms = mbean.getMetadataEncryptionAlgorithms();
         this.assertionKeyAlias = mbean.getAssertionEncryptionDecryptionKeyAlias();
         this.assertionKeyAliasPassPhrase = mbean.getAssertionEncryptionDecryptionKeyPassPhrase();
         this.assertionKeyAliasPassPhraseEncrypted = mbean.getAssertionEncryptionDecryptionKeyPassPhraseEncrypted();
         this.allowedTargetHosts = mbean.getAllowedTargetHosts();
      }

      public int getArtifactMaxCacheSize() {
         return this.artifactMaxCacheSize;
      }

      public int getArtifactTimeout() {
         return this.artifactTimeout;
      }

      public int getAuthnRequestMaxCacheSize() {
         return this.authnRequestMaxCacheSize;
      }

      public int getAuthnRequestTimeout() {
         return this.authnRequestTimeout;
      }

      public String getBasicAuthPassword() {
         return this.basicAuthPassword;
      }

      public byte[] getBasicAuthPasswordEncrypted() {
         return this.basicAuthPasswordEncrypted;
      }

      public String getBasicAuthUsername() {
         return this.basicAuthUsername;
      }

      public String getContactPersonCompany() {
         return this.contactPersonCompany;
      }

      public String getContactPersonEmailAddress() {
         return this.contactPersonEmailAddress;
      }

      public String getContactPersonGivenName() {
         return this.contactPersonGivenName;
      }

      public String getContactPersonSurName() {
         return this.contactPersonSurName;
      }

      public String getContactPersonTelephoneNumber() {
         return this.contactPersonTelephoneNumber;
      }

      public String getContactPersonType() {
         return this.contactPersonType;
      }

      public String getDefaultURL() {
         return this.defaultURL;
      }

      public String getEntityID() {
         return this.entityID;
      }

      public String getErrorPath() {
         return this.errorPath;
      }

      public String getIdentityProviderPreferredBinding() {
         return this.identityProviderPreferredBinding;
      }

      public String getLoginReturnQueryParameter() {
         return this.loginReturnQueryParameter;
      }

      public String getLoginURL() {
         return this.loginURL;
      }

      public String getOrganizationName() {
         return this.organizationName;
      }

      public String getOrganizationURL() {
         return this.organizationURL;
      }

      public String getPublishedSiteURL() {
         return this.publishedSiteURL;
      }

      public String getSSOSigningKeyAlias() {
         return this.ssoSigningKeyAlias;
      }

      public String getSSOSigningKeyPassPhrase() {
         return this.ssoSigningKeyPassPhrase;
      }

      public byte[] getSSOSigningKeyPassPhraseEncrypted() {
         return this.ssoSigningKeyPassPhraseEncrypted;
      }

      public String getServiceProviderPreferredBinding() {
         return this.serviceProviderPreferredBinding;
      }

      public String getTransportLayerSecurityKeyAlias() {
         return this.transportLayerSecurityKeyAlias;
      }

      public String getTransportLayerSecurityKeyPassPhrase() {
         return this.transportLayerSecurityKeyPassPhrase;
      }

      public byte[] getTransportLayerSecurityKeyPassPhraseEncrypted() {
         return this.transportLayerSecurityKeyPassPhraseEncrypted;
      }

      public boolean isForceAuthn() {
         return this.forceAuthn;
      }

      public boolean isIdentityProviderArtifactBindingEnabled() {
         return this.identityProviderArtifactBindingEnabled;
      }

      public boolean isIdentityProviderEnabled() {
         return this.identityProviderEnabled;
      }

      public boolean isIdentityProviderPOSTBindingEnabled() {
         return this.identityProviderPOSTBindingEnabled;
      }

      public boolean isIdentityProviderRedirectBindingEnabled() {
         return this.identityProviderRedirectBindingEnabled;
      }

      public boolean isPOSTOneUseCheckEnabled() {
         return this.postOneUseCheckEnabled;
      }

      public boolean isPassive() {
         return this.passive;
      }

      public boolean isRecipientCheckEnabled() {
         return this.recipientCheckEnabled;
      }

      public boolean isServiceProviderArtifactBindingEnabled() {
         return this.serviceProviderArtifactBindingEnabled;
      }

      public boolean isServiceProviderEnabled() {
         return this.serviceProviderEnabled;
      }

      public boolean isServiceProviderPOSTBindingEnabled() {
         return this.serviceProviderPOSTBindingEnabled;
      }

      public boolean isSignAuthnRequests() {
         return this.signAuthnRequests;
      }

      public boolean isWantArtifactRequestsSigned() {
         return this.wantArtifactRequestsSigned;
      }

      public boolean isWantAssertionsSigned() {
         return this.wantAssertionsSigned;
      }

      public boolean isWantAuthnRequestsSigned() {
         return this.wantAuthnRequestsSigned;
      }

      public boolean isWantBasicAuthClientAuthentication() {
         return this.wantBasicAuthClientAuthentication;
      }

      public boolean isWantTransportLayerSecurityClientAuthentication() {
         return this.wantTransportLayerSecurityClientAuthentication;
      }

      public boolean isReplicatedCacheEnabled() {
         return this.useReplicatedCache;
      }

      public boolean isAssertionEncryptionEnabled() {
         return this.assertionEncryptionEnabled;
      }

      public String getDataEncryptionAlgorithm() {
         return this.dataEncryptionAlgorithm;
      }

      public String getKeyEncryptionAlgorithm() {
         return this.keyEncryptionAlgorithm;
      }

      public String[] getMetadataEncryptionAlgorithms() {
         return this.metadataPublishedAlgorithms;
      }

      public String getAssertionEncryptionDecryptionKeyAlias() {
         return this.assertionKeyAlias;
      }

      public String getAssertionEncryptionDecryptionKeyPassPhrase() {
         return this.assertionKeyAliasPassPhrase;
      }

      public byte[] getAssertionEncryptionDecryptionKeyPassPhraseEncrypted() {
         return this.assertionKeyAliasPassPhraseEncrypted;
      }

      public String[] getAllowedTargetHosts() {
         return this.allowedTargetHosts;
      }
   }
}
