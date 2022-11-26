package com.bea.security.saml2.util.key;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.SAMLKeyInfoSpi;
import com.bea.common.security.service.SAMLKeyService;
import com.bea.security.saml2.Saml2Logger;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.List;

public class SAML2KeyManager {
   public static final String SSL_KEY = "saml2_ssl_key";
   public static final String SSO_KEY = "saml2_sso_key";
   public static final String ASSERTION_KEY = "saml2_assertion_key";
   public static final String ASSERTION_ENCRYPTION_DECRYPTION_KEY = "saml2_assertion_encryption_decryption_key";
   private boolean initted = false;
   private SAMLKeyService keyService = null;
   private SAML2ConfigSpi config = null;
   private LoggerSpi log = null;

   public SAML2KeyManager(SAML2ConfigSpi config) throws KeyManagerException {
      this.keyService = config.getSAMLKeyService();
      this.log = config.getLogger();
      this.config = config;
      this.initialize();
      char[] keypass = null;
      if (config.getLocalConfiguration().getSSOSigningKeyPassPhrase() != null) {
         keypass = config.getLocalConfiguration().getSSOSigningKeyPassPhrase().toCharArray();
      }

      this.setKeyAliasInfo("saml2_sso_key", config.getLocalConfiguration().getSSOSigningKeyAlias(), keypass);
      keypass = null;
      if (config.getLocalConfiguration().getTransportLayerSecurityKeyPassPhrase() != null) {
         keypass = config.getLocalConfiguration().getTransportLayerSecurityKeyPassPhrase().toCharArray();
      }

      this.setKeyAliasInfo("saml2_ssl_key", config.getLocalConfiguration().getTransportLayerSecurityKeyAlias(), keypass);
      keypass = null;
      if (config.getLocalConfiguration().getAssertionEncryptionDecryptionKeyPassPhrase() != null) {
         keypass = config.getLocalConfiguration().getAssertionEncryptionDecryptionKeyPassPhrase().toCharArray();
      }

      this.setKeyAliasInfo("saml2_assertion_encryption_decryption_key", config.getLocalConfiguration().getAssertionEncryptionDecryptionKeyAlias(), keypass);
   }

   public SAML2KeyManager(SAMLKeyService service, LoggerSpi log) throws KeyManagerException {
      this.keyService = service;
      this.log = log;
      this.initialize();
   }

   private synchronized void initialize() throws KeyManagerException {
      if (!this.initted) {
         if (!this.keyService.isKeystoreAccessible()) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("Invalid Keystore Configuration");
            }

            throw new KeyManagerException(Saml2Logger.getInvalidKeystoreConfiguration());
         }

         this.initted = true;
      }

   }

   public void setKeyAliasInfo(String keyName, String alias, char[] passphrase) throws KeyManagerException {
      if (keyName != null && !keyName.equals("")) {
         SAMLKeyInfoSpi keyInfoSpi = null;
         if (alias != null && alias.trim().length() != 0 && passphrase != null && passphrase.length != 0) {
            keyInfoSpi = this.keyService.getKeyInfo(keyName, alias, passphrase);
         } else {
            if (this.log.isDebugEnabled()) {
               this.log.debug("Alias or passphrase is null or empty. Default key set.");
            }

            keyInfoSpi = this.keyService.getDefaultKeyInfo(keyName);
         }

         if (keyInfoSpi == null || !keyInfoSpi.isValid()) {
            if (this.log.isDebugEnabled()) {
               this.log.debug("Invalid Key info.");
            }

            throw new KeyManagerException(Saml2Logger.getInvalidKeyInfo());
         }
      } else {
         throw new KeyManagerException(Saml2Logger.getEmptyKeyName());
      }
   }

   private KeyInfo getKeyInfo(String keyName) {
      if (keyName != null && !keyName.equals("")) {
         SAMLKeyInfoSpi keyInfoSpi = null;
         keyInfoSpi = this.keyService.getKeyInfo(keyName);
         if (keyInfoSpi == null || !keyInfoSpi.isValid()) {
            keyInfoSpi = this.keyService.getDefaultKeyInfo();
         }

         if (keyInfoSpi != null && keyInfoSpi.isValid()) {
            return new KeyInfo(keyInfoSpi);
         } else {
            this.log.warn(Saml2Logger.getCannotGetKeyInfo(keyName));
            return null;
         }
      } else {
         return null;
      }
   }

   public KeyInfo getSSLKeyInfo() {
      return this.getKeyInfo("saml2_ssl_key");
   }

   public KeyInfo getSSOKeyInfo() {
      return this.getKeyInfo("saml2_sso_key");
   }

   public KeyInfo getAssertionKeyInfo() {
      return this.getKeyInfo("saml2_assertion_key");
   }

   public KeyInfo getAssertionEncryptionDecryptionKeyInfo() {
      return this.getKeyInfo("saml2_assertion_encryption_decryption_key");
   }

   public static class KeyInfo {
      private SAMLKeyInfoSpi keyInfoSpi = null;

      public KeyInfo(SAMLKeyInfoSpi keyInfoSpi) {
         this.keyInfoSpi = keyInfoSpi;
      }

      public PrivateKey getKey() {
         return this.keyInfoSpi.getKey();
      }

      public Certificate getCert() {
         return this.keyInfoSpi.getCert();
      }

      public Certificate[] getChain() {
         return this.keyInfoSpi.getChain();
      }

      public List getCertAsList() {
         return this.keyInfoSpi.getCertAsList();
      }
   }
}
