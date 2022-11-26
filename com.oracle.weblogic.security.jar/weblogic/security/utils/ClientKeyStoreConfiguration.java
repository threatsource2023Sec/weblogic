package weblogic.security.utils;

import weblogic.security.SecurityMessagesTextFormatter;

public class ClientKeyStoreConfiguration implements KeyStoreConfiguration {
   private String keystores;
   private static ClientKeyStoreConfiguration theInstance;

   public static synchronized ClientKeyStoreConfiguration getInstance() {
      if (theInstance == null) {
         theInstance = new ClientKeyStoreConfiguration();
      }

      return theInstance;
   }

   private String getProperty(String propertyName) {
      return System.getProperty(propertyName);
   }

   private ClientKeyStoreConfiguration() {
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

         this.keystores = "CustomIdentityAndJavaStandardTrust";
      }

      if (configError) {
         SecurityMessagesTextFormatter formatter = SecurityMessagesTextFormatter.getInstance();
         String msg1 = formatter.getSSLClientTrustKeyStoreConfigError();
         String msg2 = formatter.getSSLClientTrustKeyStoreSyntax();
         throw new RuntimeException(msg1 + "\n\n" + msg2);
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
      return this.getProperty("weblogic.security.CustomTrustKeyStorePassPhrase");
   }

   public String getJavaStandardTrustKeyStorePassPhrase() {
      return this.getProperty("weblogic.security.JavaStandardTrustKeyStorePassPhrase");
   }

   public static void main(String[] args) {
      KeyStoreConfigurationHelper helper = new KeyStoreConfigurationHelper(getInstance());
      KeyStoreInfo[] ks = helper.getTrustKeyStores();

      for(int i = 0; ks != null && i < ks.length; ++i) {
         System.out.println("TrustKeyStore[" + i + "]=" + ks[i]);
      }

   }
}
