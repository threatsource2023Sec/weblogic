package weblogic.nodemanager.server;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.utils.keystore.CSSKeyStoreFactory;
import com.bea.security.utils.keystore.KssAccessor;
import java.io.IOException;
import java.security.AccessController;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.common.Config;
import weblogic.nodemanager.common.ConfigException;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.utils.BaseKeyStoreConfigurationHelper;
import weblogic.security.utils.KeyStoreConfiguration;
import weblogic.security.utils.KeyStoreInfo;

public class SSLConfig extends Config {
   private String keyStores = "DemoIdentityAndDemoTrust";
   private String customIdentityKeyStoreFileName;
   private String customIdentityKeyStoreType;
   private String customIdentityKeyStorePassPhrase;
   private String customIdentityAlias;
   private String customIdentityPrivateKeyPassPhrase;
   private String[] cipherSuites;
   private String keyFile = "config/demokey.pm";
   private String keyPassword = "password";
   private String certificateFile = "config/democert.pm";
   private PrivateKey privateKey;
   private Certificate[] certChain;
   private ClearOrEncryptedService ces;
   private KeyStore keyStore;
   private char[] privateKeyPassPhrase;
   private boolean useKssForDemo = KssAccessor.isKssAvailable();
   public static final String KEY_STORES_PROP = "KeyStores";
   public static final String CUSTOM_IDENTITY_KEY_STORE_FILE_NAME_PROP = "CustomIdentityKeyStoreFileName";
   public static final String CUSTOM_IDENTITY_KEY_STORE_TYPE_PROP = "CustomIdentityKeyStoreType";
   public static final String CUSTOM_IDENTITY_KEY_STORE_PASS_PHRASE_PROP = "CustomIdentityKeyStorePassPhrase";
   public static final String CUSTOM_IDENTITY_ALIAS_PROP = "CustomIdentityAlias";
   public static final String CUSTOM_IDENTITY_PRIVATE_KEY_PASS_PHRASE_PROP = "CustomIdentityPrivateKeyPassPhrase";
   public static final String CUSTOM_TRUST_KEY_STORE_PASS_PHRASE_PROP = "CustomTrustKeyStorePassPhrase";
   public static final String JAVA_STANDARD_TRUST_KEY_STORE_PASS_PHRASE_PROP = "JavaStandardTrustKeyStorePassPhrase";
   public static final String IS_USE_KSS_FOR_DEMO_PROP = "UseKSSForDemo";
   public static final String CIPHER_SUITE_PROP = "CipherSuite";
   public static final String CIPHER_SUITES_PROP = "CipherSuites";
   public static final String CIPHER_SUITES_SEPARATOR = ",";
   public static final String KEY_FILE_PROP = "keyFile";
   public static final String KEY_PASSWORD_PROP = "keyPassword";
   public static final String CERTIFICATE_FILE_PROP = "certificateFile";
   public static final String DEMO_IDENTITY = "DemoIdentity";
   public static final String CUSTOM_IDENTITY = "CustomIdentity";
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();
   private static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");

   public SSLConfig(Properties props, ClearOrEncryptedService ces) throws IOException, ConfigException {
      super(props);
      this.ces = ces;
      this.loadProperties();
      props.remove("CustomIdentityKeyStorePassPhrase");
      props.remove("CustomIdentityPrivateKeyPassPhrase");
      if (this.keyFile != null && this.keyPassword != null && this.certificateFile != null) {
         throw new ConfigException("keyFile and certificateFile are no longer supported properties.");
      } else {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws IOException, ConfigException {
                  SSLConfig.this.loadKeyStoreConfig();
                  return null;
               }
            });
         } catch (PrivilegedActionException var5) {
            Exception e = var5.getException();
            if (e instanceof IOException) {
               throw (IOException)e;
            } else if (e instanceof ConfigException) {
               throw (ConfigException)e;
            } else {
               throw new RuntimeException("Unexpected exception.", e);
            }
         }
      }
   }

   private void loadProperties() throws ConfigException {
      this.keyStores = this.getProperty("KeyStores", this.keyStores);
      if ("DemoIdentity".equals(this.keyStores)) {
         this.keyStores = "DemoIdentityAndDemoTrust";
      } else if ("CustomIdentity".equals(this.keyStores)) {
         this.keyStores = "CustomIdentityAndCustomTrust";
      }

      this.customIdentityKeyStoreFileName = this.getProperty("CustomIdentityKeyStoreFileName");
      this.customIdentityKeyStoreType = this.getProperty("CustomIdentityKeyStoreType");
      this.customIdentityAlias = this.getProperty("CustomIdentityAlias");
      if (this.getProperty("CustomIdentityKeyStorePassPhrase") != null) {
         this.customIdentityKeyStorePassPhrase = this.ces.encrypt(this.getProperty("CustomIdentityKeyStorePassPhrase"));
      }

      if (this.getProperty("CustomIdentityPrivateKeyPassPhrase") != null) {
         this.customIdentityPrivateKeyPassPhrase = this.ces.encrypt(this.getProperty("CustomIdentityPrivateKeyPassPhrase"));
      }

      String cipherSuitesString = getCipherSuitesString(this.getProperty("CipherSuite"), this.getProperty("CipherSuites"));
      this.cipherSuites = parseStringWithSeparator(cipherSuitesString, ",");
      this.keyFile = this.getProperty("keyFile");
      this.keyPassword = this.getProperty("keyPassword");
      this.certificateFile = this.getProperty("certificateFile");
      this.useKssForDemo = this.getBooleanProperty("UseKSSForDemo", this.useKssForDemo);
   }

   protected static String getCipherSuitesString(String cipherSuite, String cipherSuites) throws ConfigException {
      if (cipherSuite != null && !cipherSuite.trim().isEmpty()) {
         if (cipherSuites != null && !cipherSuites.trim().isEmpty()) {
            throw new ConfigException(nmText.cannotSpecifyBoth("CipherSuite", "CipherSuites"));
         } else {
            nmLog.warning(nmText.propertyDeprecated("CipherSuite", "CipherSuites"));
            return cipherSuite.trim();
         }
      } else {
         return cipherSuites != null && !cipherSuites.trim().isEmpty() ? cipherSuites.trim() : null;
      }
   }

   public static String[] parseStringWithSeparator(String sourceString, String separator) {
      if (sourceString != null && !sourceString.trim().isEmpty()) {
         String[] splitted = sourceString.split(separator);
         List list = new ArrayList();
         String[] var4 = splitted;
         int var5 = splitted.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String cipherSuite = var4[var6];
            if (cipherSuite != null && !cipherSuite.trim().isEmpty()) {
               list.add(cipherSuite.trim());
            }
         }

         if (list.isEmpty()) {
            return null;
         } else {
            return (String[])list.toArray(new String[list.size()]);
         }
      } else {
         return null;
      }
   }

   private void loadKeyStoreConfig() throws IOException, ConfigException {
      KeyStoreConfigHelper helper = new KeyStoreConfigHelper(new KeyStoreConfig(), this.useKssForDemo);
      KeyStoreInfo info = helper.getIdentityKeyStore();
      NMServer.nmLog.info(nmText.getLoadingIDStore(info.toString()));
      this.keyStore = CSSKeyStoreFactory.getKeyStoreInstance(info.getType(), info.getFileName(), info.getPassPhrase(), new PluggableLoggerForSecurityAPI());
      if (this.keyStore == null) {
         throw new ConfigException(nmText.getIDStoreNotFound(info.getFileName()));
      } else {
         String alias = helper.getIdentityAlias();
         this.privateKeyPassPhrase = helper.getIdentityPrivateKeyPassPhrase();
         this.privateKey = this.obtainPrivateKey(helper, alias);
         if (this.privateKey == null) {
            throw new ConfigException(nmText.getUnknownKeyStoreID(alias));
         } else {
            this.certChain = this.obtainCertificateChain(alias);
            if (this.certChain == null || this.certChain.length == 0) {
               throw new ConfigException(nmText.getNoCertificate(alias));
            }
         }
      }
   }

   private Certificate[] obtainCertificateChain(final String alias) {
      try {
         return (Certificate[])AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Certificate[] run() throws KeyStoreException {
               return SSLConfig.this.keyStore.getCertificateChain(alias);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e = var4.getException();
         if (e instanceof KeyStoreException) {
            throw new IllegalStateException(nmText.getIdentityStoreNotInit());
         } else {
            throw new RuntimeException("Unexpected exception.", e);
         }
      }
   }

   private PrivateKey obtainPrivateKey(final BaseKeyStoreConfigurationHelper helper, final String alias) throws ConfigException {
      try {
         return (PrivateKey)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public PrivateKey run() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
               return (PrivateKey)SSLConfig.this.keyStore.getKey(alias, helper.getIdentityPrivateKeyPassPhrase());
            }
         });
      } catch (PrivilegedActionException var5) {
         Exception e = var5.getException();
         if (e instanceof KeyStoreException) {
            throw new InternalError("Identity key store not initialized");
         } else if (e instanceof NoSuchAlgorithmException) {
            throw new ConfigException(nmText.getIDAlgorithmNotFound(), e);
         } else if (e instanceof UnrecoverableKeyException) {
            throw new ConfigException(nmText.getIncorrectIDPassword());
         } else {
            throw new RuntimeException("Unexpected exception.", e);
         }
      }
   }

   public String[] getCipherSuites() {
      return this.cipherSuites;
   }

   public KeyStore getKeyStore() {
      return this.keyStore;
   }

   public PrivateKey getPrivateKey() {
      return this.privateKey;
   }

   public Certificate[] getCertificateChain() {
      return this.certChain;
   }

   public char[] getPrivateKeyPassPhrase() {
      return this.privateKeyPassPhrase;
   }

   public static boolean checkUpgrade(Properties props, Encryptor encryptor, boolean log, ClearOrEncryptedService ces) {
      boolean changed = false;
      String keyStorePass = props.getProperty("CustomIdentityKeyStorePassPhrase");
      String privateKeyPass = props.getProperty("CustomIdentityPrivateKeyPassPhrase");
      String customTrustPass = props.getProperty("CustomTrustKeyStorePassPhrase");
      String javaTrustPass = props.getProperty("JavaStandardTrustKeyStorePassPhrase");
      String clearKeyStorePass = null;
      String clearPrivateKeyPass = null;
      String encryptedKeyStorePass = null;
      String encryptedPrivateKeyPass = null;
      if (encryptor != null) {
         clearKeyStorePass = encryptor.decrypt(keyStorePass);
         clearPrivateKeyPass = encryptor.decrypt(privateKeyPass);
         if (clearKeyStorePass != null) {
            encryptedKeyStorePass = ces.encrypt(clearKeyStorePass);
         }

         if (clearPrivateKeyPass != null) {
            encryptedPrivateKeyPass = ces.encrypt(clearPrivateKeyPass);
         }
      } else {
         if (keyStorePass != null) {
            encryptedKeyStorePass = ces.encrypt(keyStorePass);
         }

         if (privateKeyPass != null) {
            encryptedPrivateKeyPass = ces.encrypt(privateKeyPass);
         }
      }

      if (keyStorePass != null && !keyStorePass.equals(encryptedKeyStorePass)) {
         props.setProperty("CustomIdentityKeyStorePassPhrase", encryptedKeyStorePass);
         if (log) {
            Upgrader.log(Level.INFO, nmText.getEncryptingProp("CustomIdentityKeyStorePassPhrase"));
         }

         changed = true;
      }

      if (privateKeyPass != null && !privateKeyPass.equals(encryptedPrivateKeyPass)) {
         props.setProperty("CustomIdentityPrivateKeyPassPhrase", encryptedPrivateKeyPass);
         if (log) {
            Upgrader.log(Level.INFO, nmText.getEncryptingProp("CustomIdentityPrivateKeyPassPhrase"));
         }

         changed = true;
      }

      if (customTrustPass != null) {
         props.remove("CustomTrustKeyStorePassPhrase");
         if (log) {
            Upgrader.log(Level.INFO, nmText.getRemovingProp("CustomTrustKeyStorePassPhrase"));
         }

         changed = true;
      }

      if (javaTrustPass != null) {
         props.remove("JavaStandardTrustKeyStorePassPhrase");
         if (log) {
            Upgrader.log(Level.INFO, nmText.getRemovingProp("JavaStandardTrustKeyStorePassPhrase"));
         }

         changed = true;
      }

      return changed;
   }

   private static class PluggableLoggerForSecurityAPI implements LoggerSpi {
      private final Logger nmLog;

      private PluggableLoggerForSecurityAPI() {
         this.nmLog = Logger.getLogger("weblogic.nodemanager");
      }

      public boolean isDebugEnabled() {
         return this.nmLog.isLoggable(Level.FINE);
      }

      public void debug(Object msg) {
         this.nmLog.log(Level.FINE, msg.toString());
      }

      public void debug(Object msg, Throwable th) {
         this.nmLog.log(Level.FINE, msg.toString(), th);
      }

      public void info(Object msg) {
         this.nmLog.info(msg.toString());
      }

      public void info(Object msg, Throwable th) {
         this.nmLog.log(Level.INFO, msg.toString(), th);
      }

      public void warn(Object msg) {
         this.nmLog.log(Level.WARNING, msg.toString());
      }

      public void warn(Object msg, Throwable th) {
         this.nmLog.log(Level.WARNING, msg.toString(), th);
      }

      public void error(Object msg) {
         this.nmLog.log(Level.SEVERE, msg.toString());
      }

      public void error(Object msg, Throwable th) {
         this.nmLog.log(Level.SEVERE, msg.toString(), th);
      }

      public void severe(Object msg) {
         this.nmLog.log(Level.SEVERE, msg.toString());
      }

      public void severe(Object msg, Throwable th) {
         this.nmLog.log(Level.SEVERE, msg.toString(), th);
      }

      // $FF: synthetic method
      PluggableLoggerForSecurityAPI(Object x0) {
         this();
      }
   }

   private class KeyStoreConfigHelper extends BaseKeyStoreConfigurationHelper {
      private boolean useKssForDemo;

      protected KeyStoreConfigHelper(KeyStoreConfiguration config, boolean isUseKssForDemo) {
         super(config);
         this.useKssForDemo = isUseKssForDemo;
      }

      protected boolean isUseKssForDemo() {
         return this.useKssForDemo;
      }
   }

   private class KeyStoreConfig implements KeyStoreConfiguration {
      private KeyStoreConfig() {
      }

      public String getKeyStores() {
         return SSLConfig.this.keyStores;
      }

      public String getCustomIdentityKeyStoreFileName() {
         return SSLConfig.this.customIdentityKeyStoreFileName;
      }

      public String getCustomIdentityKeyStoreType() {
         return SSLConfig.this.customIdentityKeyStoreType;
      }

      public String getCustomIdentityKeyStorePassPhrase() {
         return SSLConfig.this.ces.decrypt(SSLConfig.this.customIdentityKeyStorePassPhrase);
      }

      public String getCustomIdentityAlias() {
         return SSLConfig.this.customIdentityAlias;
      }

      public String getCustomIdentityPrivateKeyPassPhrase() {
         return SSLConfig.this.ces.decrypt(SSLConfig.this.customIdentityPrivateKeyPassPhrase);
      }

      public String getCustomTrustKeyStoreFileName() {
         return null;
      }

      public String getCustomTrustKeyStoreType() {
         return null;
      }

      public String getCustomTrustKeyStorePassPhrase() {
         return null;
      }

      public String getJavaStandardTrustKeyStorePassPhrase() {
         return null;
      }

      public String getOutboundPrivateKeyAlias() {
         return null;
      }

      public String getOutboundPrivateKeyPassPhrase() {
         return null;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("CustomIdentityKeyStoreType").append("=");
         sb.append(this.getCustomIdentityKeyStoreType()).append("\n");
         sb.append("KeyStores").append("=");
         sb.append(this.getKeyStores()).append("\n");
         sb.append("CustomIdentityAlias").append("=");
         sb.append(this.getCustomIdentityAlias()).append("\n");
         sb.append("CustomIdentityKeyStoreFileName").append("=");
         sb.append(this.getCustomIdentityKeyStoreFileName()).append("\n");
         sb.append("CustomIdentityKeyStorePassPhrase").append("=");
         sb.append(this.getCustomIdentityKeyStorePassPhrase()).append("\n");
         sb.append("CustomIdentityPrivateKeyPassPhrase").append("=");
         sb.append(this.getCustomIdentityPrivateKeyPassPhrase()).append("\n");
         return sb.toString();
      }

      // $FF: synthetic method
      KeyStoreConfig(Object x1) {
         this();
      }
   }
}
