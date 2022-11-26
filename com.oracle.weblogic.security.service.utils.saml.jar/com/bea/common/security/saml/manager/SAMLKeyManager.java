package com.bea.common.security.saml.manager;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.SAMLKeyInfoSpi;
import com.bea.common.security.service.SAMLKeyService;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.List;
import weblogic.security.spi.ProviderInitializationException;

public class SAMLKeyManager {
   private SAMLKeyService samlKeyService = null;
   private LoggerSpi LOGGER = null;
   private static SAMLKeyManager manager = null;
   private static SAMLKeyService initSamlKeyService = null;
   private boolean useSSLIdentity = false;
   private boolean initted = false;
   private static int SSL_IDENTITY_KEY = 0;
   private static int ASSERTIONS_KEY = 1;
   private static int PROTOCOL_KEY = 2;
   private static int SSL_CLIENT_KEY = 3;
   private static String[] keyNames = new String[]{"SSL Identity", "Assertion Signing", "Protocol Signing", "SSL Client"};
   private static final String TEMP_KEY_VALIDATE_NAME = "TEMPVERIFYKEYNAME";
   private KeyInfo[] keys = new KeyInfo[4];
   private AliasInfo[] aliasInfos = new AliasInfo[4];

   private final boolean isDebugEnabled() {
      return this.LOGGER != null & this.LOGGER.isDebugEnabled();
   }

   private final void logDebug(String method, String msg) {
      if (this.LOGGER != null && this.LOGGER.isDebugEnabled()) {
         this.LOGGER.debug("SAMLKeyManager: " + method + "(): " + msg);
      }

   }

   private SAMLKeyManager() {
   }

   private SAMLKeyManager(SAMLKeyService samlKeyService, boolean useSSLIdentity) {
      this.useSSLIdentity = useSSLIdentity;
      this.samlKeyService = samlKeyService;
   }

   private static synchronized SAMLKeyManager getManager(SAMLKeyService samlKeyService, boolean useSSLIdentity) throws ProviderInitializationException {
      if (manager != null && initSamlKeyService != null && initSamlKeyService != samlKeyService) {
         manager = null;
      }

      if (manager == null) {
         initSamlKeyService = samlKeyService;
         SAMLKeyManager tmpManager = new SAMLKeyManager(samlKeyService, useSSLIdentity);
         tmpManager.initialize();
         manager = tmpManager;
      }

      return manager;
   }

   public static SAMLKeyManager getManager(SAMLKeyService samlKeyService) {
      return getManager(samlKeyService, false);
   }

   public static SAMLKeyManager getV1Manager(SAMLKeyService samlKeyService) {
      return getManager(samlKeyService, true);
   }

   public static SAMLKeyManager getManager() {
      return manager;
   }

   private synchronized void initialize() throws ProviderInitializationException {
      if (!this.initted) {
         if (!this.samlKeyService.isKeystoreAccessible()) {
            throw new ProviderInitializationException("Invalid Keystore Configuration");
         }

         this.initted = true;
      }

   }

   public boolean checkAssertionsKeyConfiguration(String alias, String passphraseString) {
      return this.checkKeyConfiguration(ASSERTIONS_KEY, alias, passphraseString);
   }

   public boolean checkProtocolKeyConfiguration(String alias, String passphraseString) {
      return this.checkKeyConfiguration(PROTOCOL_KEY, alias, passphraseString);
   }

   public boolean checkSSLCLIENTKeyConfiguration(String alias, String passphraseString) {
      return this.checkKeyConfiguration(SSL_CLIENT_KEY, alias, passphraseString);
   }

   private boolean checkKeyConfiguration(int key, String alias, String passphraseString) {
      if (alias == null) {
         alias = "";
      }

      if (passphraseString == null) {
         passphraseString = "";
      }

      if (alias.equals("") && passphraseString.equals("")) {
         return true;
      } else if (alias.equals("")) {
         return false;
      } else {
         char[] passphrase = passphraseString.toCharArray();
         SAMLKeyInfoSpi samlKeyInfo = this.samlKeyService.getKeyInfo("TEMPVERIFYKEYNAME", alias, passphrase);
         return samlKeyInfo.isValid();
      }
   }

   private int getKeyIndex(int key) {
      return this.useSSLIdentity ? SSL_IDENTITY_KEY : key;
   }

   private String getKeyName(int key) {
      return keyNames[key];
   }

   public synchronized void setAssertionsKeyAliasInfo(String aliasName, String passphrase) {
      this.setKeyAliasInfo(ASSERTIONS_KEY, aliasName, passphrase);
   }

   public synchronized void setProtocolKeyAliasInfo(String aliasName, String passphrase) {
      this.setKeyAliasInfo(PROTOCOL_KEY, aliasName, passphrase);
   }

   public synchronized void setSSLClientKeyAliasInfo(String aliasName, String passphrase) {
      this.setKeyAliasInfo(SSL_CLIENT_KEY, aliasName, passphrase);
   }

   private void setKeyAliasInfo(int key, String aliasName, String passphrase) {
      this.invalidateKey(key);
      char[] passchars = null;
      if (passphrase != null) {
         passchars = passphrase.toCharArray();
      }

      if (aliasName != null && aliasName.length() != 0 && passchars != null && passchars.length != 0) {
         this.aliasInfos[key] = new AliasInfo(aliasName, passchars);
      } else {
         this.aliasInfos[key] = null;
      }

   }

   private AliasInfo getKeyAliasInfo(int key) {
      return this.useSSLIdentity ? null : this.aliasInfos[key];
   }

   private synchronized void invalidateKey(int key) {
      this.keys[this.getKeyIndex(key)] = null;
      this.aliasInfos[this.getKeyIndex(key)] = null;
   }

   private synchronized KeyInfo getKeyInfo(int key) {
      int keyIndex = this.getKeyIndex(key);
      this.logDebug("getKeyInfo", "Fetching key for index " + key + ", mapped index is " + keyIndex);
      KeyInfo keyInfo = this.keys[keyIndex];
      if (keyInfo == null) {
         this.logDebug("getKeyInfo", "Loading key for index " + keyIndex);
         SAMLKeyInfoSpi samlKeyInfo = null;
         if (this.useSSLIdentity) {
            samlKeyInfo = this.samlKeyService.getDefaultKeyInfo();
         } else {
            String keyName = this.getKeyName(keyIndex);
            AliasInfo aliasInfo = this.getKeyAliasInfo(key);
            if (aliasInfo == null) {
               samlKeyInfo = this.samlKeyService.getDefaultKeyInfo();
            } else {
               samlKeyInfo = this.samlKeyService.getKeyInfo(keyName, aliasInfo.getAlias(), aliasInfo.getPassPhrase());
            }
         }

         if (samlKeyInfo != null) {
            keyInfo = new KeyInfo(samlKeyInfo);
            this.keys[keyIndex] = keyInfo;
         }
      }

      return keyInfo;
   }

   public KeyInfo getAssertionSigningKeyInfo() {
      return this.getKeyInfo(ASSERTIONS_KEY);
   }

   public KeyInfo getProtocolSigningKeyInfo() {
      return this.getKeyInfo(PROTOCOL_KEY);
   }

   public KeyInfo getSSLClientIdentityKeyInfo() {
      return this.getKeyInfo(SSL_CLIENT_KEY);
   }

   public static class KeyInfo {
      private SAMLKeyInfoSpi keyInfo = null;

      public KeyInfo(SAMLKeyInfoSpi keyInfo) {
         this.keyInfo = keyInfo;
      }

      public PrivateKey getKey() {
         return this.keyInfo != null ? this.keyInfo.getKey() : null;
      }

      public Certificate getCert() {
         return this.keyInfo != null ? this.keyInfo.getCert() : null;
      }

      public Certificate[] getChain() {
         return this.keyInfo != null ? this.keyInfo.getChain() : null;
      }

      public List getCertAsList() {
         return this.keyInfo != null ? this.keyInfo.getCertAsList() : null;
      }
   }

   private static class AliasInfo {
      private String alias;
      private char[] passphrase;

      AliasInfo(String alias, char[] passphrase) {
         this.alias = alias;
         this.passphrase = passphrase;
      }

      String getAlias() {
         return this.alias;
      }

      char[] getPassPhrase() {
         return this.passphrase;
      }
   }
}
