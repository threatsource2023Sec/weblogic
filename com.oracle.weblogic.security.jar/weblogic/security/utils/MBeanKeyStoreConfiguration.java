package weblogic.security.utils;

import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.security.SecurityLogger;
import weblogic.security.SecurityRuntimeAccess;
import weblogic.security.shared.LoggerWrapper;
import weblogic.utils.LocatorUtilities;

public class MBeanKeyStoreConfiguration implements KeyStoreConfiguration {
   private static LoggerWrapper logger = LoggerWrapper.getInstance("SecurityKeyStore");
   private static MBeanKeyStoreConfiguration theInstance;
   private ServerMBean server;
   private SSLMBean ssl;
   private boolean usePreCfg;
   private boolean valid;

   public static synchronized MBeanKeyStoreConfiguration getInstance() {
      if (theInstance == null) {
         theInstance = new MBeanKeyStoreConfiguration();
      }

      return theInstance;
   }

   private void debug(String msg) {
      logger.debug("MBeanKeyStoreConfiguration: " + msg);
   }

   private PreMBeanKeyStoreConfiguration getPreCfg() {
      return PreMBeanKeyStoreConfiguration.getInstance();
   }

   private MBeanKeyStoreConfiguration() {
      SecurityRuntimeAccess runtimeAccess = (SecurityRuntimeAccess)AccessController.doPrivileged(new PrivilegedAction() {
         public SecurityRuntimeAccess run() {
            return (SecurityRuntimeAccess)LocatorUtilities.getService(SecurityRuntimeAccess.class);
         }
      });
      this.server = runtimeAccess.getServer();
      this.ssl = this.server.getSSL();
      this.valid = true;
      if ("CustomIdentityAndCommandLineTrust".equals(this.server.getKeyStores())) {
         this.usePreCfg = true;
         if (logger.isDebugEnabled()) {
            this.debug("constructor - using command line trust config");
         }

         if ("DemoIdentityAndDemoTrust".equals(this.getPreCfg().getKeyStores())) {
            this.valid = false;
            SecurityLogger.logServerDemoCommandLineTrust();
         }
      } else {
         this.usePreCfg = false;
         if (logger.isDebugEnabled()) {
            this.debug("constructor - using mbean trust config");
         }

         if (!this.MBeanAndCommandLineTrustEqual()) {
            this.valid = false;
            SecurityLogger.logServerTrustKeyStoreMisMatchError();
         }
      }

      if (logger.isDebugEnabled()) {
         KeyStoreInfo[] ks = (new KeyStoreConfigurationHelper(this)).getTrustKeyStores();

         for(int i = 0; ks != null && i < ks.length; ++i) {
            this.debug("constructor - TrustKeyStore[" + i + "]=" + ks[i]);
         }
      }

   }

   private boolean MBeanAndCommandLineTrustEqual() {
      PreMBeanKeyStoreConfiguration preCfg = this.getPreCfg();
      if (!preCfg.isExplicitlyConfigured()) {
         return true;
      } else {
         KeyStoreInfo[] ks1 = (new KeyStoreConfigurationHelper(this)).getTrustKeyStores();
         KeyStoreInfo[] ks2 = (new KeyStoreConfigurationHelper(preCfg)).getTrustKeyStores();
         if (ks1 == null && ks2 == null) {
            return true;
         } else if (ks1 != null && ks2 != null) {
            if (ks1.length != ks2.length) {
               return false;
            } else {
               for(int i = 0; i < ks1.length; ++i) {
                  if (!ks1[i].equals(ks2[i])) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   public String getKeyStores() {
      if (!this.valid) {
         return null;
      } else {
         return this.usePreCfg ? this.getPreCfg().getKeyStores() : this.server.getKeyStores();
      }
   }

   public String getCustomIdentityKeyStoreFileName() {
      return this.server.getCustomIdentityKeyStoreFileName();
   }

   public String getCustomIdentityKeyStoreType() {
      return this.server.getCustomIdentityKeyStoreType();
   }

   public String getCustomIdentityKeyStorePassPhrase() {
      return this.server.getCustomIdentityKeyStorePassPhrase();
   }

   public String getCustomIdentityAlias() {
      return this.ssl.getServerPrivateKeyAlias();
   }

   public String getCustomIdentityPrivateKeyPassPhrase() {
      return this.ssl.getServerPrivateKeyPassPhrase();
   }

   public String getOutboundPrivateKeyAlias() {
      return this.ssl.getOutboundPrivateKeyAlias();
   }

   public String getOutboundPrivateKeyPassPhrase() {
      return this.ssl.getOutboundPrivateKeyPassPhrase();
   }

   public String getCustomTrustKeyStoreFileName() {
      return this.usePreCfg ? this.getPreCfg().getCustomTrustKeyStoreFileName() : this.server.getCustomTrustKeyStoreFileName();
   }

   public String getCustomTrustKeyStoreType() {
      return this.usePreCfg ? this.getPreCfg().getCustomTrustKeyStoreType() : this.server.getCustomTrustKeyStoreType();
   }

   public String getCustomTrustKeyStorePassPhrase() {
      return this.usePreCfg ? this.getPreCfg().getCustomTrustKeyStorePassPhrase() : this.server.getCustomTrustKeyStorePassPhrase();
   }

   public String getJavaStandardTrustKeyStorePassPhrase() {
      return this.usePreCfg ? this.getPreCfg().getJavaStandardTrustKeyStorePassPhrase() : this.server.getJavaStandardTrustKeyStorePassPhrase();
   }
}
