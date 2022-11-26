package weblogic.security.utils;

import weblogic.security.SecurityLogger;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.shared.LoggerWrapper;

public class PreMBeanKeyStoreConfiguration implements KeyStoreConfiguration {
   private static LoggerWrapper logger = LoggerWrapper.getInstance("SecurityKeyStore");
   private String keystores;
   private static PreMBeanKeyStoreConfiguration theInstance;
   private ClearOrEncryptedService ces;

   public static synchronized PreMBeanKeyStoreConfiguration getInstance() {
      if (theInstance == null) {
         theInstance = new PreMBeanKeyStoreConfiguration();
      }

      return theInstance;
   }

   private void debug(String msg) {
      logger.debug("PreMBeanKeyStoreConfiguration: " + msg);
   }

   private String getProperty(String propertyName) {
      return System.getProperty(propertyName);
   }

   private PreMBeanKeyStoreConfiguration() {
      boolean configError = false;
      String trustKeyStore = this.getProperty("weblogic.security.TrustKeyStore");
      String custom_keystore_file_name = this.getProperty("weblogic.security.CustomTrustKeyStoreFileName");
      if ("DemoTrust".equals(trustKeyStore)) {
         this.keystores = "DemoIdentityAndDemoTrust";
      } else if ("JavaStandardTrust".equals(trustKeyStore)) {
         this.keystores = "CustomIdentityAndJavaStandardTrust";
      } else if ("CustomTrust".equals(trustKeyStore)) {
         String filename = this.getProperty("weblogic.security.CustomTrustKeyStoreFileName");
         if (filename == null || filename.length() < 1) {
            configError = true;
         }

         this.keystores = "CustomIdentityAndCustomTrust";
      } else {
         if (trustKeyStore != null && trustKeyStore.length() > 0) {
            configError = true;
         }

         this.keystores = "DemoIdentityAndDemoTrust";
      }

      if (configError) {
         this.keystores = null;
         SecurityLogger.logServerTrustKeyStoreConfigError();
      }

      if (logger.isDebugEnabled()) {
         this.debug("constructor - explicitly configured=" + this.isExplicitlyConfigured());
         KeyStoreInfo[] ks = (new KeyStoreConfigurationHelper(this)).getTrustKeyStores();

         for(int i = 0; ks != null && i < ks.length; ++i) {
            this.debug("constructor - TrustKeyStore[" + i + "]=" + ks[i]);
         }
      }

      this.ces = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService());
   }

   public boolean isExplicitlyConfigured() {
      if (this.getProperty("weblogic.security.TrustKeyStore") != null) {
         return true;
      } else if (this.getProperty("weblogic.security.CustomTrustKeyStoreFileName") != null) {
         return true;
      } else if (this.getProperty("weblogic.security.CustomTrustKeyStoreType") != null) {
         return true;
      } else if (this.getProperty("weblogic.security.CustomTrustKeyStorePassPhrase") != null) {
         return true;
      } else {
         return this.getProperty("weblogic.security.JavaStandardTrustKeyStorePassPhrase") != null;
      }
   }

   public String getKeyStores() {
      return this.keystores;
   }

   public String getCustomIdentityKeyStoreFileName() {
      return null;
   }

   public String getCustomIdentityKeyStoreType() {
      return null;
   }

   public String getCustomIdentityKeyStorePassPhrase() {
      return null;
   }

   public String getCustomIdentityAlias() {
      return null;
   }

   public String getCustomIdentityPrivateKeyPassPhrase() {
      return null;
   }

   public String getOutboundPrivateKeyAlias() {
      return null;
   }

   public String getOutboundPrivateKeyPassPhrase() {
      return null;
   }

   public String getCustomTrustKeyStoreFileName() {
      return this.getProperty("weblogic.security.CustomTrustKeyStoreFileName");
   }

   public String getCustomTrustKeyStoreType() {
      return this.getProperty("weblogic.security.CustomTrustKeyStoreType");
   }

   public String getCustomTrustKeyStorePassPhrase() {
      return this.decryptValue(this.getProperty("weblogic.security.CustomTrustKeyStorePassPhrase"));
   }

   public String getJavaStandardTrustKeyStorePassPhrase() {
      return this.decryptValue(this.getProperty("weblogic.security.JavaStandardTrustKeyStorePassPhrase"));
   }

   private String decryptValue(String value) {
      return this.ces != null && value != null ? this.ces.decrypt(value) : value;
   }
}
