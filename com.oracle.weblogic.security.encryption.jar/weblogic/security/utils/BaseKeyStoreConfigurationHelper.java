package weblogic.security.utils;

import java.io.File;

public class BaseKeyStoreConfigurationHelper {
   protected KeyStoreConfiguration config;

   public BaseKeyStoreConfigurationHelper(KeyStoreConfiguration config) {
      this.config = config;
   }

   protected boolean isValid() {
      return this.config.getKeyStores() != null;
   }

   protected boolean isDemoIdentity() {
      return "DemoIdentityAndDemoTrust".equals(this.config.getKeyStores());
   }

   protected boolean isDemoTrust() {
      return "DemoIdentityAndDemoTrust".equals(this.config.getKeyStores());
   }

   protected boolean isJavaStandardTrust() {
      return "CustomIdentityAndJavaStandardTrust".equals(this.config.getKeyStores());
   }

   protected boolean isCustomTrust() {
      return "CustomIdentityAndCustomTrust".equals(this.config.getKeyStores());
   }

   protected String emptyToNull(String val) {
      return val != null && val.length() < 1 ? null : val;
   }

   protected String getAbsolutePath(String path) {
      return this.emptyToNull(path) == null ? null : (new File(path)).getAbsolutePath();
   }

   protected boolean isUseKssForDemo() {
      return false;
   }

   private KeyStoreInfo getKssDemoIdentityKeyStoreInfo() {
      return new KeyStoreInfo("kss://system/demoidentity", "kss", "DemoIdentityKeyStorePassPhrase");
   }

   private KeyStoreInfo getWlsLegacyDemoIdentityKeyStoreInfo() {
      return new KeyStoreInfo(KeyStoreConstants.getDemoIdentityKeyStoreFileName(), "jks", "DemoIdentityKeyStorePassPhrase");
   }

   private KeyStoreInfo getDemoIdentityKeyStoreInfo() {
      return this.isUseKssForDemo() ? this.getKssDemoIdentityKeyStoreInfo() : this.getWlsLegacyDemoIdentityKeyStoreInfo();
   }

   private KeyStoreInfo getCustomIdentityKeyStoreInfo() {
      KeyStoreInfo channelIdentityKeyStoreInfo = this.getChannelIdentityKeyStoreInfo();
      String ksFileName;
      String ksType;
      char[] ksPassPhrase;
      String ksSource;
      if (null != channelIdentityKeyStoreInfo) {
         ksFileName = channelIdentityKeyStoreInfo.getFileName();
         ksType = channelIdentityKeyStoreInfo.getType();
         ksPassPhrase = channelIdentityKeyStoreInfo.getPassPhrase();
      } else {
         ksFileName = this.config.getCustomIdentityKeyStoreFileName();
         ksType = this.config.getCustomIdentityKeyStoreType();
         ksSource = this.config.getCustomIdentityKeyStorePassPhrase();
         ksPassPhrase = null == ksSource ? null : ksSource.toCharArray();
      }

      if (isFileBasedKeyStore(ksType)) {
         ksSource = this.getAbsolutePath(ksFileName);
      } else {
         ksSource = ksFileName;
      }

      return new KeyStoreInfo(ksSource, ksType, ksPassPhrase);
   }

   private KeyStoreInfo getKssDemoTrustKeyStoreInfo() {
      return new KeyStoreInfo("kss://system/trust", "kss", KeyStoreConstants.KSS_DEMO_TRUST_KEYSTORE_PASSPHRASE);
   }

   private KeyStoreInfo getJavaStandardTrustKeyStoreInfo() {
      return new KeyStoreInfo(KeyStoreConstants.getJavaStandardTrustKeyStoreFileName(), "jks", this.emptyToNull(this.config.getJavaStandardTrustKeyStorePassPhrase()));
   }

   private KeyStoreInfo[] getWlsLegacyDemoTrustKeyStoreInfo() {
      KeyStoreInfo[] keystores = new KeyStoreInfo[]{new KeyStoreInfo(KeyStoreConstants.getDemoTrustKeyStoreFileName(), "jks", "DemoTrustKeyStorePassPhrase"), this.getJavaStandardTrustKeyStoreInfo()};
      return keystores;
   }

   private KeyStoreInfo[] getDemoTrustKeyStoreInfo() {
      KeyStoreInfo[] keystores;
      if (this.isUseKssForDemo()) {
         keystores = new KeyStoreInfo[]{this.getKssDemoTrustKeyStoreInfo(), this.getJavaStandardTrustKeyStoreInfo()};
      } else {
         keystores = this.getWlsLegacyDemoTrustKeyStoreInfo();
      }

      return keystores;
   }

   private KeyStoreInfo getCustomTrustKeyStoreInfo() {
      String ksType = this.config.getCustomTrustKeyStoreType();
      String ksSource;
      if (isFileBasedKeyStore(ksType)) {
         ksSource = this.getAbsolutePath(this.config.getCustomTrustKeyStoreFileName());
      } else {
         ksSource = this.config.getCustomTrustKeyStoreFileName();
      }

      return new KeyStoreInfo(ksSource, this.emptyToNull(ksType), this.emptyToNull(this.config.getCustomTrustKeyStorePassPhrase()));
   }

   public KeyStoreInfo getIdentityKeyStore() {
      if (!this.isValid()) {
         return null;
      } else {
         return this.isDemoIdentity() ? this.getDemoIdentityKeyStoreInfo() : this.getCustomIdentityKeyStoreInfo();
      }
   }

   public KeyStoreInfo[] getTrustKeyStores() {
      if (!this.isValid()) {
         return null;
      } else {
         KeyStoreInfo[] keystores = null;
         if (this.isDemoTrust()) {
            keystores = this.getDemoTrustKeyStoreInfo();
         } else if (this.isJavaStandardTrust()) {
            keystores = new KeyStoreInfo[]{this.getJavaStandardTrustKeyStoreInfo()};
         } else {
            keystores = new KeyStoreInfo[]{this.getCustomTrustKeyStoreInfo()};
         }

         return keystores;
      }
   }

   public String getIdentityAlias() {
      if (!this.isValid()) {
         return null;
      } else if (this.isDemoTrust()) {
         return "DemoIdentity";
      } else {
         String channelAlias = this.getChannelPrivateKeyAlias();
         return channelAlias != null ? channelAlias : this.emptyToNull(this.config.getCustomIdentityAlias());
      }
   }

   public char[] getIdentityPrivateKeyPassPhrase() {
      if (!this.isValid()) {
         return null;
      } else {
         String passphrase = null;
         if (this.isDemoTrust()) {
            passphrase = "DemoIdentityPassPhrase";
         } else if (this.getChannelPrivateKeyPassPhrase() != null) {
            passphrase = this.emptyToNull(this.getChannelPrivateKeyPassPhrase());
         } else {
            passphrase = this.emptyToNull(this.config.getCustomIdentityPrivateKeyPassPhrase());
         }

         return passphrase != null && passphrase.length() > 0 ? passphrase.toCharArray() : null;
      }
   }

   protected String getChannelPrivateKeyAlias() {
      return null;
   }

   protected String getChannelPrivateKeyPassPhrase() {
      return null;
   }

   protected KeyStoreInfo getChannelIdentityKeyStoreInfo() {
      return null;
   }

   private static boolean isFileBasedKeyStore(String keyStoreType) {
      if (null != keyStoreType && 0 != keyStoreType.trim().length()) {
         return !"KSS".equalsIgnoreCase(keyStoreType);
      } else {
         return false;
      }
   }
}
