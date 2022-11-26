package weblogic.security.internal;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.legacy.spi.SAMLSingleSignOnServiceConfigInfoSpi;
import com.bea.common.security.saml.manager.SAMLKeyManager;
import com.bea.common.security.saml.utils.SAMLSourceId;
import com.bea.common.security.saml.utils.SAMLUtil;
import java.security.AccessController;
import java.util.Properties;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.FederationServicesMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.providers.saml.SAMLAssertionStore;
import weblogic.security.providers.saml.SAMLBeanUpdateListener;
import weblogic.security.providers.saml.SAMLCredentialMapperV2MBean;
import weblogic.security.providers.saml.SAMLIdentityAsserterV2MBean;
import weblogic.security.providers.saml.SAMLUsedAssertionCache;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.shared.LoggerWrapper;
import weblogic.security.spi.ProviderInitializationException;

public class SAMLSingleSignOnServiceConfigInfoImpl implements SAMLSingleSignOnServiceConfigInfoSpi, SAMLBeanUpdateListener.UpdateListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private FederationServicesMBean fsMBean = null;
   protected BeanUpdateListener listener = null;
   private boolean sourceSiteDisabled = false;
   private boolean destinationSiteDisabled = false;
   private static final int CONFIG_NONE = 0;
   private static final int CONFIG_V2 = 2;
   private int configVersion = 0;
   private String[] itsURIs = null;
   private String[] arsURIs = null;
   private String sourceIdHex = null;
   private byte[] sourceIdBytes = null;
   private String assertionStoreClassName = null;
   private Properties assertionStoreProperties = null;
   private String[] allowedTargetHosts;
   private String[] acsURIs = null;
   private String usedAssertionCacheClassName = null;
   private Properties usedAssertionCacheProperties = null;
   private static LoggerWrapper LOGGER = LoggerWrapper.getInstance("SecuritySAMLService");

   protected static void logDebug(String className, String method, String msg) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug(className + "." + method + "(): " + msg);
      }

   }

   private static void logDebug(String method, String msg) {
      logDebug("SAMLSingleSignOnServiceConfigInfoImpl", method, msg);
   }

   public SAMLSingleSignOnServiceConfigInfoImpl() {
      this.initialize();
   }

   private void initialize() {
      this.fsMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getFederationServices();
      RealmMBean realm = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().getDefaultRealm();
      AuthenticationProviderMBean v2Asserter = null;
      AuthenticationProviderMBean[] authenticators = realm.getAuthenticationProviders();

      for(int i = 0; authenticators != null && i < authenticators.length; ++i) {
         if (authenticators[i] instanceof SAMLIdentityAsserterV2MBean) {
            v2Asserter = authenticators[i];
         }
      }

      CredentialMapperMBean v2CredMapper = null;
      CredentialMapperMBean[] credmappers = realm.getCredentialMappers();

      for(int i = 0; credmappers != null && i < credmappers.length; ++i) {
         if (credmappers[i] instanceof SAMLCredentialMapperV2MBean) {
            v2CredMapper = credmappers[i];
         }
      }

      if (v2Asserter != null || v2CredMapper != null) {
         this.configVersion = 2;
      }

      if (v2Asserter == null) {
         this.destinationSiteDisabled = true;
      }

      if (v2CredMapper == null) {
         this.sourceSiteDisabled = true;
      }

      this.validateConfiguration();
      this.updateConfiguration();
      this.listener = SAMLBeanUpdateListener.registerListener(this.fsMBean, this, "SAMLSingleSignOnServiceConfigInfoImpl", (LoggerSpi)null);
   }

   private void validateConfiguration() {
      if (this.configVersion == 2) {
         this.validateFederationServices(this.fsMBean);
      }

   }

   private void updateConfiguration() {
      if (this.configVersion == 2 && this.fsMBean != null) {
         this.updateFederationServices(this.fsMBean);
      }

   }

   public void prepareBeanUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      DescriptorBean bean = event.getProposedBean();
      logDebug("listener", "prepareBeanUpdate() called");

      try {
         if (bean instanceof FederationServicesMBean) {
            this.validateFederationServices((FederationServicesMBean)bean);
         }
      } catch (Exception var4) {
         logDebug("listener", "prepareBeanUpdate() failed: " + var4.getMessage());
         throw new BeanUpdateRejectedException(var4.toString());
      }

      logDebug("listener", "prepareBeanUpdate() succeeded");
   }

   public void handleBeanUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      DescriptorBean bean = event.getSourceBean();
      logDebug("listener", "handleBeanUpdate() called");

      try {
         if (bean instanceof FederationServicesMBean) {
            this.updateFederationServices((FederationServicesMBean)bean);
         }
      } catch (Exception var4) {
         logDebug("listener", "Update failed");
         throw new BeanUpdateFailedException(var4.toString());
      }

      logDebug("listener", "Update succeeded");
   }

   public void rollbackBeanUpdate(BeanUpdateEvent event) {
      logDebug("listener", "rollbackBeanUpdate() called, nothing to do");
   }

   private void validateFederationServices(FederationServicesMBean federationServicesMBean) throws ProviderInitializationException {
      boolean itsArtifactEnabled = federationServicesMBean.isSourceSiteEnabled();
      this.validateSourceSite(itsArtifactEnabled, federationServicesMBean.getAssertionStoreClassName(), federationServicesMBean.getAssertionStoreProperties(), federationServicesMBean.getSourceSiteURL());
      this.validateDestinationSite(federationServicesMBean.isDestinationSiteEnabled(), federationServicesMBean.isPOSTOneUseCheckEnabled(), federationServicesMBean.getUsedAssertionCacheClassName(), federationServicesMBean.getUsedAssertionCacheProperties());
      SAMLKeyManager keyManager = SAMLKeyManager.getManager();
      if (keyManager != null) {
         String alias = federationServicesMBean.getSigningKeyAlias();
         String passphrase = federationServicesMBean.getSigningKeyPassPhrase();
         if (!keyManager.checkProtocolKeyConfiguration(alias, passphrase)) {
            throw new ProviderInitializationException(ServiceLogger.getSAMLInvalidSigningKey(alias));
         }

         alias = federationServicesMBean.getSSLClientIdentityAlias();
         passphrase = federationServicesMBean.getSSLClientIdentityPassPhrase();
         if (!keyManager.checkSSLCLIENTKeyConfiguration(alias, passphrase)) {
            throw new ProviderInitializationException(ServiceLogger.getSAMLInvalidSSLKey(alias));
         }
      }

   }

   private void updateFederationServices(FederationServicesMBean federationServicesMBean) {
      logDebug("updateFederationServices", "Initializing static source site params");
      this.itsURIs = federationServicesMBean.getIntersiteTransferURIs();
      this.arsURIs = federationServicesMBean.getAssertionRetrievalURIs();
      this.assertionStoreClassName = federationServicesMBean.getAssertionStoreClassName();
      this.assertionStoreProperties = federationServicesMBean.getAssertionStoreProperties();
      logDebug("updateFederationServices", "Initializing static destination site params");
      this.acsURIs = federationServicesMBean.getAssertionConsumerURIs();
      this.allowedTargetHosts = federationServicesMBean.getAllowedTargetHosts();
      this.usedAssertionCacheClassName = federationServicesMBean.getUsedAssertionCacheClassName();
      this.usedAssertionCacheProperties = federationServicesMBean.getUsedAssertionCacheProperties();
      SAMLSourceId sourceId = this.instantiateSourceId(federationServicesMBean.getSourceSiteURL());
      if (sourceId != null) {
         this.sourceIdHex = sourceId.getSourceIdHex();
         this.sourceIdBytes = sourceId.getSourceIdBytes();
      }

      SAMLKeyManager keyManager = SAMLKeyManager.getManager();
      if (keyManager != null) {
         String alias = federationServicesMBean.getSigningKeyAlias();
         String passphrase = federationServicesMBean.getSigningKeyPassPhrase();
         if (alias != null && !alias.equals("")) {
            logDebug("updateFederationServices", "Setting SigningKey: " + alias);
            if (passphrase == null) {
               passphrase = "";
            }

            keyManager.setProtocolKeyAliasInfo(alias, passphrase);
         }

         alias = federationServicesMBean.getSSLClientIdentityAlias();
         passphrase = federationServicesMBean.getSSLClientIdentityPassPhrase();
         if (alias != null && !alias.equals("")) {
            logDebug("updateFederationServices", "Setting SSLClientKey: " + alias);
            if (passphrase == null) {
               passphrase = "";
            }

            keyManager.setSSLClientKeyAliasInfo(alias, passphrase);
         }
      }

   }

   private void validateSourceSite(boolean itsArtifactEnabled, String assertionStoreClassName, Properties assertionStoreProperties, String sourceSiteURL) throws ProviderInitializationException {
      if (itsArtifactEnabled) {
         if (assertionStoreClassName != null && assertionStoreClassName.length() != 0) {
            try {
               SAMLAssertionStore assertionStore = (SAMLAssertionStore)SAMLUtil.instantiatePlugin(assertionStoreClassName, SAMLAssertionStore.class.getName());
               if (assertionStore != null) {
                  assertionStore.initStore(assertionStoreProperties);
               }
            } catch (Exception var7) {
               LoggableMessageSpi spi = SecurityLogger.logSAMLAssertionCacheInitFailLoggable(assertionStoreClassName, var7);
               throw new ProviderInitializationException(spi.getFormattedMessageBody());
            }
         }

         if (sourceSiteURL == null || sourceSiteURL.length() == 0) {
            throw new ProviderInitializationException(ServiceLogger.getSAMLInvalidSourceSiteConfig("URL"));
         }

         SAMLSourceId sourceId = this.instantiateSourceId(sourceSiteURL);
         if (sourceId == null) {
            throw new ProviderInitializationException(ServiceLogger.getSAMLInvalidSourceSiteConfig("source id"));
         }
      }

   }

   private void validateDestinationSite(boolean acsPostEnabled, boolean postOneUseCheckEnabled, String usedAssertionCacheClassName, Properties usedAssertionCacheProperties) throws ProviderInitializationException {
      if (acsPostEnabled && postOneUseCheckEnabled && usedAssertionCacheClassName != null && usedAssertionCacheClassName.length() != 0) {
         try {
            SAMLUsedAssertionCache usedAssertionCache = (SAMLUsedAssertionCache)SAMLUtil.instantiatePlugin(usedAssertionCacheClassName, SAMLUsedAssertionCache.class.getName());
            if (usedAssertionCache != null) {
               usedAssertionCache.initCache(usedAssertionCacheProperties);
            }
         } catch (Exception var7) {
            LoggableMessageSpi spi = SecurityLogger.logSAMLAssertionCacheInitFailLoggable(usedAssertionCacheClassName, var7);
            throw new ProviderInitializationException(spi.getFormattedMessageBody());
         }
      }

   }

   private SAMLSourceId instantiateSourceId(String sourceIdString) throws ProviderInitializationException {
      if (sourceIdString != null && !sourceIdString.equals("")) {
         SAMLSourceId tmp = null;

         try {
            tmp = new SAMLSourceId(sourceIdString);
            return tmp;
         } catch (IllegalArgumentException var4) {
            return null;
         }
      } else {
         return null;
      }
   }

   public boolean isV1Config() {
      return false;
   }

   public boolean isV2Config() {
      return this.configVersion == 2;
   }

   public byte[] getSourceIdBytes() {
      return this.sourceIdBytes;
   }

   public String getSourceIdHex() {
      return this.sourceIdHex;
   }

   public String[] getIntersiteTransferURIs() {
      return this.itsURIs;
   }

   public String[] getAssertionRetrievalURIs() {
      return this.arsURIs;
   }

   public String[] getAssertionConsumerURIs() {
      return this.acsURIs;
   }

   public String getAssertionStoreClassName() {
      return this.assertionStoreClassName;
   }

   public Properties getAssertionStoreProperties() {
      return this.assertionStoreProperties;
   }

   public String getUsedAssertionCacheClassName() {
      return this.usedAssertionCacheClassName;
   }

   public Properties getUsedAssertionCacheProperties() {
      return this.usedAssertionCacheProperties;
   }

   public boolean isSourceSiteEnabled() {
      if (this.sourceSiteDisabled) {
         return false;
      } else {
         return this.fsMBean.isSourceSiteEnabled() && this.itsURIs != null;
      }
   }

   public boolean isDestinationSiteEnabled() {
      if (this.destinationSiteDisabled) {
         return false;
      } else {
         return this.fsMBean.isDestinationSiteEnabled() && this.acsURIs != null;
      }
   }

   public boolean isITSArtifactEnabled() {
      return this.isSourceSiteEnabled();
   }

   public boolean isITSPostEnabled() {
      return this.isSourceSiteEnabled();
   }

   public boolean isACSArtifactEnabled() {
      return this.isDestinationSiteEnabled() ? this.fsMBean.isDestinationSiteEnabled() : false;
   }

   public boolean isACSPostEnabled() {
      return this.isDestinationSiteEnabled() ? this.fsMBean.isDestinationSiteEnabled() : false;
   }

   public boolean isITSRequiresSSL() {
      return this.fsMBean.isITSRequiresSSL();
   }

   public boolean isACSRequiresSSL() {
      return this.fsMBean.isACSRequiresSSL();
   }

   public boolean isARSRequiresSSL() {
      return this.fsMBean.isARSRequiresSSL();
   }

   public boolean isARSRequiresTwoWaySSL() {
      return this.fsMBean.isARSRequiresTwoWaySSL();
   }

   public boolean isPOSTOneUseCheckEnabled() {
      return this.fsMBean.isPOSTOneUseCheckEnabled();
   }

   public boolean isPOSTRecipientCheckEnabled() {
      return this.fsMBean.isPOSTRecipientCheckEnabled();
   }

   public String getSigningKeyAlias() {
      return this.fsMBean.getSigningKeyAlias();
   }

   public String getSigningKeyPassPhrase() {
      return this.fsMBean.getSigningKeyPassPhrase();
   }

   public String getSSLClientIdentityAlias() {
      return this.fsMBean.getSSLClientIdentityAlias();
   }

   public String getSSLClientIdentityPassPhrase() {
      return this.fsMBean.getSSLClientIdentityPassPhrase();
   }

   public void close() {
      if (this.fsMBean != null && this.listener != null) {
         this.fsMBean.removeBeanUpdateListener(this.listener);
      }

   }

   public String[] getAllowedTargetHosts() {
      return this.fsMBean.getAllowedTargetHosts();
   }
}
