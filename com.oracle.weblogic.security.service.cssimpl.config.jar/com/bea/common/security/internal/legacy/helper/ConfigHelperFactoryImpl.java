package com.bea.common.security.internal.legacy.helper;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.AuditServicesConfigHelper;
import com.bea.common.security.legacy.AuthenticationServicesConfigHelper;
import com.bea.common.security.legacy.AuthorizationServicesConfigHelper;
import com.bea.common.security.legacy.CertPathServicesConfigHelper;
import com.bea.common.security.legacy.ConfigHelperFactory;
import com.bea.common.security.legacy.CredentialMappingServicesConfigHelper;
import com.bea.common.security.legacy.IdentityServicesConfigHelper;
import com.bea.common.security.legacy.InternalServicesConfigHelper;
import com.bea.common.security.legacy.LegacyDomainInfo;
import com.bea.common.security.legacy.LoginSessionServiceConfigHelper;
import com.bea.common.security.legacy.SAML2SingleSignOnServicesConfigHelper;
import com.bea.common.security.legacy.SAMLSingleSignOnServiceConfigHelper;
import com.bea.common.security.legacy.SecurityProviderConfigHelper;
import com.bea.common.security.legacy.SecurityTokenServicesConfigHelper;
import com.bea.common.security.legacy.spi.LegacyConfigInfoSpi;
import com.bea.common.security.legacy.spi.LegacyEncryptorSpi;
import com.bea.common.security.provider.PrincipalValidatorImpl;
import com.bea.common.security.utils.LegacyEncryptor;
import com.bea.common.security.utils.LegacyEncryptorKey;
import java.security.KeyException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import weblogic.management.security.RealmMBean;
import weblogic.security.spi.PrincipalValidator;

public class ConfigHelperFactoryImpl extends ConfigHelperFactory {
   private SecurityProviderConfigHelper providerHelper = null;
   private LegacyConfigInfoSpi legacyConfigInfo = null;
   private InternalServicesConfigHelper internalServicesConfigHelper = null;

   public ConfigHelperFactoryImpl(RealmMBean realm, LegacyDomainInfo legacyDomainInfo) throws KeyException {
      if (legacyDomainInfo != null) {
         this.legacyConfigInfo = new LegacyConfigInfo(realm, legacyDomainInfo);
      }

      this.providerHelper = new SecurityProviderConfigHelperImpl(this.legacyConfigInfo, this);
   }

   public SecurityProviderConfigHelper getSecurityProviderConfigHelper() {
      return this.providerHelper;
   }

   public AuditServicesConfigHelper getAuditServicesConfigHelper(RealmMBean realmMBean) {
      return new AuditServicesConfigHelperImpl(realmMBean, this.providerHelper);
   }

   public AuthenticationServicesConfigHelper getAuthenticationServicesConfigHelper(RealmMBean realmMBean) {
      return new AuthenticationServicesConfigHelperImpl(realmMBean, this.providerHelper);
   }

   public AuthorizationServicesConfigHelper getAuthorizationServicesConfigHelper(RealmMBean realmMBean) {
      return new AuthorizationServicesConfigHelperImpl(realmMBean, this.providerHelper);
   }

   public CertPathServicesConfigHelper getCertPathServicesConfigHelper(RealmMBean realmMBean) {
      return new CertPathServicesConfigHelperImpl(realmMBean, this.providerHelper);
   }

   public CredentialMappingServicesConfigHelper getCredentialMappingServicesConfigHelper(RealmMBean realmMBean) {
      return new CredentialMappingServicesConfigHelperImpl(realmMBean, this.providerHelper);
   }

   public IdentityServicesConfigHelper getIdentityServicesConfigHelper(RealmMBean realmMBean) {
      return new IdentityServicesConfigHelperImpl(realmMBean, this.providerHelper);
   }

   public InternalServicesConfigHelper getInternalServicesConfigHelper(RealmMBean realmMBean) {
      if (this.internalServicesConfigHelper == null) {
         this.internalServicesConfigHelper = new InternalServicesConfigHelperImpl(realmMBean);
      }

      return this.internalServicesConfigHelper;
   }

   public SAMLSingleSignOnServiceConfigHelper getSAMLSingleSignOnServiceConfigHelper(RealmMBean realmMBean) {
      return new SAMLSingleSignOnServiceConfigHelperImpl(realmMBean, this.providerHelper);
   }

   public SecurityTokenServicesConfigHelper getSecurityTokenServicesConfigHelper(RealmMBean realmMBean) {
      return new SecurityTokenServicesConfigHelperImpl(realmMBean, this.providerHelper);
   }

   public SAML2SingleSignOnServicesConfigHelper getSAML2SingleSignOnServicesConfigHelper(RealmMBean realmMBean) {
      return new SAML2SingleSignOnServicesConfigHelperImpl(realmMBean, this.providerHelper, this.legacyConfigInfo);
   }

   public LoginSessionServiceConfigHelper getLoginSessionServiceConfigHelper(RealmMBean realmMBean) {
      return new LoginSessionServiceConfigHelperImpl();
   }

   private static class LegacyEncryptorSpiImpl implements LegacyEncryptorSpi {
      private LegacyEncryptor encryptor;

      public LegacyEncryptorSpiImpl(LegacyEncryptorKey key) throws KeyException {
         this.encryptor = new LegacyEncryptor(key);
         key.dispose();
      }

      public byte[] decryptBytes(byte[] clearOrEncryptedBytes) throws BadPaddingException, IllegalBlockSizeException {
         return this.encryptor.decryptBytes(clearOrEncryptedBytes);
      }

      public char[] decryptChar(String clearOrEncryptedString) throws BadPaddingException, IllegalBlockSizeException {
         return this.encryptor.decryptChar(clearOrEncryptedString);
      }

      public String decryptString(String clearOrEncryptedString) throws BadPaddingException, IllegalBlockSizeException {
         return this.encryptor.decryptString(clearOrEncryptedString);
      }

      public byte[] encryptBytes(byte[] clearOrEncryptedBytes) throws BadPaddingException, IllegalBlockSizeException {
         return this.encryptor.encryptBytes(clearOrEncryptedBytes);
      }

      public String encryptChar(char[] clearOrEncryptedChar) throws BadPaddingException, IllegalBlockSizeException {
         return this.encryptor.encryptChar(clearOrEncryptedChar);
      }

      public String encryptString(String clearOrEncryptedString) throws BadPaddingException, IllegalBlockSizeException {
         return this.encryptor.encryptString(clearOrEncryptedString);
      }

      public boolean isEncrypted(byte[] clearOrEncryptedBytes) {
         return this.encryptor.isEncrypted(clearOrEncryptedBytes);
      }

      public boolean isEncrypted(char[] clearOrEncryptedChars) {
         return this.encryptor.isEncrypted(clearOrEncryptedChars);
      }

      public boolean isEncrypted(String clearOrEncryptedString) {
         return this.encryptor.isEncrypted(clearOrEncryptedString);
      }
   }

   private static class LegacyConfigInfo implements LegacyConfigInfoSpi {
      private LegacyDomainInfo domainInfo;
      private RealmMBean realm;
      private LegacyEncryptorSpi legacyEncryptor;
      private String jaxpFactoryServiceName;
      private String ldapSSLSocketFactoryLookupServiceName;
      private String namedSQLConnectionLookupServiceName;
      private String samlKeyServiceName;
      private String storeServiceName;
      private String bootStrapServiceName;

      public LegacyConfigInfo(RealmMBean realm, LegacyDomainInfo domainInfo) throws KeyException {
         this.realm = realm;
         this.domainInfo = domainInfo;
         this.legacyEncryptor = new LegacyEncryptorSpiImpl(domainInfo.getLegacyEncryptorKey());
         this.jaxpFactoryServiceName = JAXPFactoryServiceConfigHelper.getServiceName(realm);
         this.ldapSSLSocketFactoryLookupServiceName = LDAPSSLSocketFactoryLookupServiceConfigHelper.getServiceName(realm);
         this.namedSQLConnectionLookupServiceName = NamedSQLConnectionLookupServiceConfigHelper.getServiceName(realm);
         this.samlKeyServiceName = SAMLKeyServiceConfigHelper.getServiceName(realm);
         this.storeServiceName = StoreServiceConfigHelper.getServiceName(realm);
         this.bootStrapServiceName = BootStrapServiceConfigHelper.getServiceName(realm);
      }

      public String getDomainName() {
         return this.domainInfo.getDomainName();
      }

      public boolean getProductionModeEnabled() {
         return this.domainInfo.getProductionModeEnabled();
      }

      public boolean getSecureModeEnabled() {
         return this.domainInfo.getSecureModeEnabled();
      }

      public String getRootDirectory() {
         return this.domainInfo.getRootDirectory();
      }

      public boolean getWebAppFilesCaseInsensitive() {
         return this.domainInfo.getWebAppFilesCaseInsensitive();
      }

      public String getServerName() {
         return this.domainInfo.getServerName();
      }

      public PrincipalValidator getPrincipalValidator(LoggerSpi logger) {
         return new PrincipalValidatorImpl(logger, this.domainInfo.getDomainCredential(), this.realm);
      }

      public boolean getManagementModificationsSupported() {
         return this.domainInfo.getManagementModificationsSupported();
      }

      public LegacyEncryptorSpi getLegacyEncryptor() {
         return this.legacyEncryptor;
      }

      public String getJAXPFactoryServiceName() {
         return this.jaxpFactoryServiceName;
      }

      public String getLDAPSSLSocketFactoryLookupServiceName() {
         return this.ldapSSLSocketFactoryLookupServiceName;
      }

      public String getNamedSQLConnectionLookupServiceName() {
         return this.namedSQLConnectionLookupServiceName;
      }

      public String getSAMLKeyServiceName() {
         return this.samlKeyServiceName;
      }

      public String getStoreServiceName() {
         return this.storeServiceName;
      }

      public String getBootStrapServiceName() {
         return this.bootStrapServiceName;
      }

      public String getNegotiateIdentityAsserterServiceName() {
         return NegotiateIdentityAsserterServiceConfigHelper.getServiceName(this.realm);
      }

      public String getPasswordValidationServiceName() {
         return PasswordValidationServiceConfigHelper.getServiceName(this.realm);
      }
   }
}
