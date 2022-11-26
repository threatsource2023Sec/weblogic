package weblogic.security.service.internal;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.SAMLKeyInfoSpi;
import com.bea.common.security.service.SAMLKeyService;
import com.bea.security.utils.keystore.CSSKeyStoreFactory;
import java.io.File;
import java.security.AccessController;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.SecurityLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.pki.keystore.WLSKeyStoreFactory;
import weblogic.security.utils.KeyStoreConfigurationHelper;
import weblogic.security.utils.KeyStoreInfo;
import weblogic.security.utils.MBeanKeyStoreConfiguration;

public class SAMLKeyServiceImpl implements ServiceLifecycleSpi, SAMLKeyService {
   private LoggerSpi logger;
   private AuthenticatedSubject kernelId;
   private String serverRootDirectory = null;
   private ServerMBean serverMBean = null;
   private SSLMBean sslMBean = null;
   private BeanUpdateListener listener = null;
   private String defaultKeyAlias = null;
   private char[] defaultKeyPassphrase = null;
   private SAMLKeyInfoSpiImpl defaultKeyInfo = null;
   private HashMap namedKeyInfos = new HashMap();
   private String keyStoreFile = null;
   private String keyStoreType = null;
   private long keystoreModTime = 0L;
   private long keystoreCheckTime = 0L;
   private KeyStore keyStore = null;
   private long lastKeyStoreFileModTime = 0L;
   private long lastKeyStoreFileCheckTime = 0L;
   private static final int KEYSTORE_POLL_INTERVAL = 15000;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.SAMLKeyService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof SAMLKeyServiceConfig) {
         SAMLKeyServiceConfig myconfig = (SAMLKeyServiceConfig)config;
         this.kernelId = myconfig.getKernelId();
         if (this.kernelId == null) {
            throw new ServiceConfigurationException(SecurityLogger.getNullParameterSupplied("kernelId"));
         } else {
            this.serverMBean = ManagementService.getRuntimeAccess(this.kernelId).getServer();
            if (this.serverMBean == null) {
               throw new ServiceInitializationException(SecurityLogger.getNullParameterSupplied("serverMBean"));
            } else {
               this.sslMBean = this.serverMBean.getSSL();
               if (this.sslMBean == null) {
                  throw new ServiceInitializationException(SecurityLogger.getNullParameterSupplied("sslMBean"));
               } else {
                  this.serverRootDirectory = this.serverMBean.getRootDirectory();
                  if (this.serverRootDirectory == null) {
                     throw new ServiceConfigurationException(SecurityLogger.getNullParameterSupplied("serverRootDirectory"));
                  } else {
                     this.setupMBeanNotificationListener();
                     return Delegator.getInstance((Class)SAMLKeyService.class, this);
                  }
               }
            }
         }
      } else {
         throw new ServiceConfigurationException(SecurityLogger.getNullParameterSupplied("SAMLKeyServiceConfig"));
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (this.sslMBean != null && this.listener != null) {
         this.sslMBean.removeBeanUpdateListener(this.listener);
      }

      if (this.serverMBean != null && this.listener != null) {
         this.serverMBean.removeBeanUpdateListener(this.listener);
      }

      this.invalidateKeystore(true);
   }

   public boolean isKeystoreAccessible() {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".isKeystoreAccessible";
      if (debug) {
         this.logger.debug(method);
      }

      synchronized(this) {
         return this.getKeyStore() != null;
      }
   }

   public SAMLKeyInfoSpi getKeyInfo(String name, String keyAlias, char[] keyPassphrase) {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".getKeyInfo";
      if (debug) {
         this.logger.debug(method);
      }

      if (name == null) {
         throw new IllegalArgumentException(SecurityLogger.getNullParameterSupplied("key name"));
      } else if (keyAlias == null) {
         throw new IllegalArgumentException(SecurityLogger.getNullParameterSupplied("key alias"));
      } else {
         SAMLKeyInfoSpiImpl keyInfo = null;
         synchronized(this) {
            keyInfo = (SAMLKeyInfoSpiImpl)this.namedKeyInfos.get(name);
            if (keyInfo != null && keyInfo != this.defaultKeyInfo) {
               if (debug) {
                  this.logger.debug(method + " Found existing KeyInfo for " + name);
               }

               keyInfo.update(keyAlias, keyPassphrase);
            } else {
               if (debug) {
                  this.logger.debug(method + " Creating new KeyInfo for " + name);
               }

               keyInfo = new SAMLKeyInfoSpiImpl(this, this.logger, keyAlias, keyPassphrase);
               this.namedKeyInfos.put(name, keyInfo);
            }

            return keyInfo;
         }
      }
   }

   public SAMLKeyInfoSpi getKeyInfo(String name) {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".getKeyInfo";
      if (debug) {
         this.logger.debug(method);
      }

      SAMLKeyInfoSpiImpl keyInfo = null;
      synchronized(this) {
         keyInfo = (SAMLKeyInfoSpiImpl)this.namedKeyInfos.get(name);
         return keyInfo;
      }
   }

   public SAMLKeyInfoSpi getDefaultKeyInfo() {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".getDefaultKeyInfo";
      if (debug) {
         this.logger.debug(method);
      }

      this.updateDefaultKeyInfo();
      return this.defaultKeyInfo;
   }

   public SAMLKeyInfoSpi getDefaultKeyInfo(String name) {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".getDefaultKeyInfo";
      if (debug) {
         this.logger.debug(method);
      }

      this.updateDefaultKeyInfo();
      this.namedKeyInfos.put(name, this.defaultKeyInfo);
      return this.defaultKeyInfo;
   }

   private void updateDefaultKeyInfo() {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".updateDefaultKeyInfo";
      if (debug) {
         this.logger.debug(method);
      }

      synchronized(this) {
         KeyStoreConfigurationHelper keystoreHelper = this.getKeystoreHelper();
         if (keystoreHelper != null) {
            this.defaultKeyAlias = keystoreHelper.getIdentityAlias();
            if (this.defaultKeyAlias != null && this.defaultKeyAlias.length() != 0) {
               this.defaultKeyPassphrase = keystoreHelper.getIdentityPrivateKeyPassPhrase();
               if (this.defaultKeyInfo == null) {
                  this.defaultKeyInfo = new SAMLKeyInfoSpiImpl(this, this.logger, this.defaultKeyAlias, this.defaultKeyPassphrase);
               } else {
                  this.defaultKeyInfo.update(this.defaultKeyAlias, this.defaultKeyPassphrase);
               }

            } else {
               if (debug) {
                  this.logger.debug(method + " defaultKeyAlias not configured");
               }

            }
         }
      }
   }

   private void setupMBeanNotificationListener() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".setupMBeanNotificationListener" : null;
      if (debug) {
         this.logger.debug(method);
      }

      try {
         this.listener = createBeanUpdateListener(this);
         this.sslMBean.addBeanUpdateListener(this.listener);
         this.serverMBean.addBeanUpdateListener(this.listener);
         if (debug) {
            this.logger.debug(method + " Registered for SSL and Server mbean notifications");
         }
      } catch (Exception var4) {
         if (debug) {
            this.logger.debug(method + " Unable to register for dynamic configuration changes", var4);
         }
      }

   }

   private static BeanUpdateListener createBeanUpdateListener(final SAMLKeyServiceImpl samlKeyService) {
      return new BeanUpdateListener() {
         public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
         }

         public void activateUpdate(BeanUpdateEvent event) {
            if (samlKeyService != null) {
               DescriptorBean bean = event.getSourceBean();
               samlKeyService.checkForConfigUpdate(bean);
            }
         }

         public void rollbackUpdate(BeanUpdateEvent event) {
         }
      };
   }

   private void checkForConfigUpdate(DescriptorBean bean) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".checkForConfigUpdate" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (bean instanceof ServerMBean) {
         if (this.keystoreHasChanged()) {
            this.invalidateKeystore(false);
         }
      } else if (bean instanceof SSLMBean) {
         if (this.keystoreHasChanged()) {
            this.invalidateKeystore(false);
         }

         this.updateDefaultKeyInfo();
      } else if (debug) {
         this.logger.debug(method + " Called for unrecognized MBean type");
      }

   }

   private synchronized boolean keystoreHasChanged() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".keystoreHasChanged" : null;
      if (debug) {
         this.logger.debug(method);
      }

      KeyStoreInfo keystoreInfo = this.getKeystoreInfo();
      if (keystoreInfo == null) {
         if (debug) {
            this.logger.debug(method + " Unable to get keystore info, returning true");
         }

         return true;
      } else if (this.keyStoreFile != null && this.keyStoreFile.equals(keystoreInfo.getFileName())) {
         if (this.keyStoreType != null && this.keyStoreType.equals(keystoreInfo.getType())) {
            File file = this.getKeystoreFile(this.keyStoreFile);
            if (file == null) {
               if (debug) {
                  this.logger.debug(method + " Unable to access keystore file, returning true");
               }

               return true;
            } else if (file.lastModified() > this.keystoreModTime) {
               if (debug) {
                  this.logger.debug(method + " Keystore modification time is greater than last access, returning true");
               }

               return true;
            } else {
               return false;
            }
         } else {
            if (debug) {
               this.logger.debug(method + " Keystore type has changed, returning true");
            }

            return true;
         }
      } else {
         if (debug) {
            this.logger.debug(method + " Keystore filename has changed, returning true");
         }

         return true;
      }
   }

   private synchronized void invalidateKeystore(boolean clearPassphrase) {
      this.keyStoreFile = null;
      this.keyStoreType = null;
      this.keystoreModTime = 0L;
      this.invalidateAllKeys(clearPassphrase);
   }

   private void invalidateAllKeys(boolean clearPassphrase) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".invalidateAllKeys" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (this.defaultKeyInfo != null) {
         this.defaultKeyInfo.invalidate();
      }

      if (this.namedKeyInfos != null && this.namedKeyInfos.size() > 0) {
         Collection collection = this.namedKeyInfos.values();
         Iterator iter = collection.iterator();
         SAMLKeyInfoSpiImpl keyInfo = null;

         while(iter.hasNext()) {
            keyInfo = (SAMLKeyInfoSpiImpl)iter.next();
            if (keyInfo != null) {
               keyInfo.invalidate();
               if (clearPassphrase) {
                  keyInfo.clearPassphrase();
               }
            }
         }
      }

   }

   private KeyStoreInfo getKeystoreInfo() {
      return this.getKeystoreInfo(this.getKeystoreHelper());
   }

   private KeyStoreInfo getKeystoreInfo(KeyStoreConfigurationHelper keystoreHelper) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getKeystoreInfo" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (keystoreHelper == null) {
         return null;
      } else {
         KeyStoreInfo keystoreInfo = keystoreHelper.getIdentityKeyStore();
         if (keystoreInfo == null) {
            if (debug) {
               this.logger.debug(method + " Invalid SSL configuration");
            }

            return null;
         } else {
            return keystoreInfo;
         }
      }
   }

   private KeyStoreConfigurationHelper getKeystoreHelper() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".getKeystoreHelper" : null;
      if (debug) {
         this.logger.debug(method);
      }

      String sslLocations = this.sslMBean.getIdentityAndTrustLocations();
      if (!sslLocations.equals("KeyStores")) {
         if (debug) {
            this.logger.debug(method + " SSL configuration is not using KeyStores");
         }

         return null;
      } else {
         return new KeyStoreConfigurationHelper(MBeanKeyStoreConfiguration.getInstance());
      }
   }

   private File getKeystoreFile(String filename) {
      if (filename != null && filename.length() > 0) {
         File file = new File(filename);
         if (!file.exists()) {
            file = new File(this.serverRootDirectory, filename);
         }

         if (file.exists()) {
            return file;
         }
      }

      return null;
   }

   private boolean storeValidityCheckNeeded() {
      long now = System.currentTimeMillis();
      long millisSinceLastCheck = now - this.lastKeyStoreFileCheckTime;
      return millisSinceLastCheck > 15000L;
   }

   private synchronized void checkStoreValidity() {
      long now = System.currentTimeMillis();
      long millisSinceLastCheck = now - this.lastKeyStoreFileCheckTime;
      if (millisSinceLastCheck > 15000L) {
         KeyStore var5 = this.getKeyStore();
      }

   }

   private static final long getKeyStoreLastModified(final String keyStoreType, final String keyStoreSource) {
      try {
         return (Long)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Long run() {
               return WLSKeyStoreFactory.getLastModified(keyStoreType, keyStoreSource);
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception e = var4.getException();
         if (null == e) {
            e = var4;
         }

         if (e instanceof RuntimeException) {
            throw (RuntimeException)e;
         } else {
            throw new RuntimeException("Unexpected exception, message=" + ((Exception)e).getMessage(), (Throwable)e);
         }
      }
   }

   private KeyStore getKeyStore() {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".getKeyStore";
      if (debug) {
         this.logger.debug(method);
      }

      synchronized(this) {
         if (this.keyStore != null && this.keyStoreFile != null) {
            long now = System.currentTimeMillis();
            long millisSinceLastCheck = now - this.lastKeyStoreFileCheckTime;
            if (millisSinceLastCheck > 15000L) {
               if (debug) {
                  this.logger.debug(method + " Checking if the Keystore file was modified");
               }

               this.lastKeyStoreFileCheckTime = now;
               String keyStoreSource;
               if (CSSKeyStoreFactory.isFileBasedKeyStore(this.keyStoreType)) {
                  File storeFile = this.getKeystoreFile(this.keyStoreFile);
                  if (storeFile == null || !storeFile.isFile()) {
                     if (debug) {
                        this.logger.debug(method + " couldn't find or access file: " + this.keyStoreFile);
                     }

                     this.keyStore = null;
                     this.invalidateAllKeys(false);
                     return this.keyStore;
                  }

                  keyStoreSource = storeFile.getAbsolutePath();
               } else {
                  keyStoreSource = this.keyStoreFile;
               }

               if (this.lastKeyStoreFileModTime != getKeyStoreLastModified(this.keyStoreType, keyStoreSource)) {
                  if (debug) {
                     this.logger.debug(method + " keystore file has changed, reloading");
                  }

                  this.keyStore = this.openKeyStore();
               }
            }

            return this.keyStore;
         } else {
            if (debug) {
               this.logger.debug(method + " No keystore, getting one");
            }

            this.keyStore = this.openKeyStore();
            return this.keyStore;
         }
      }
   }

   private KeyStore openKeyStore() {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".openKeyStore";
      if (debug) {
         this.logger.debug(method);
      }

      this.invalidateKeystore(false);
      KeyStoreInfo keystoreInfo = this.getKeystoreInfo();
      if (keystoreInfo == null) {
         return null;
      } else {
         this.keyStoreFile = keystoreInfo.getFileName();
         this.keyStoreType = keystoreInfo.getType();
         String keyStoreSource;
         if (CSSKeyStoreFactory.isFileBasedKeyStore(this.keyStoreType)) {
            File storeFile = this.getKeystoreFile(keystoreInfo.getFileName());
            if (storeFile == null || !storeFile.isFile()) {
               if (debug) {
                  this.logger.debug(method + " couldn't find or access file: " + this.keyStoreFile);
               }

               return null;
            }

            keyStoreSource = storeFile.getAbsolutePath();
         } else {
            keyStoreSource = keystoreInfo.getFileName();
         }

         if (debug) {
            this.logger.debug(method + " KeyStore File:  " + keyStoreSource);
            this.logger.debug(method + " KeyStore Type:  " + this.keyStoreType);
            Provider[] currentProviders = Security.getProviders();

            for(int index = 0; index < currentProviders.length; ++index) {
               this.logger.debug(method + " Security Providers:  " + currentProviders[index].getName() + "  " + currentProviders[index].getVersion());
            }
         }

         KeyStore newKeyStore = WLSKeyStoreFactory.getKeyStoreInstance(this.kernelId, this.keyStoreType, keyStoreSource, keystoreInfo.getPassPhrase());
         if (null != newKeyStore) {
            this.lastKeyStoreFileCheckTime = System.currentTimeMillis();
            this.lastKeyStoreFileModTime = getKeyStoreLastModified(this.keyStoreType, keyStoreSource);
         } else {
            this.lastKeyStoreFileCheckTime = 0L;
            this.lastKeyStoreFileModTime = 0L;
         }

         if (debug) {
            this.logger.debug(method + " keystore was " + (newKeyStore == null ? "not" : "") + " loaded:");
         }

         return newKeyStore;
      }
   }

   private static class SAMLKeyInfoSpiImpl implements SAMLKeyInfoSpi {
      private SAMLKeyServiceImpl serviceImpl = null;
      private LoggerSpi logger = null;
      private String keyAlias = null;
      private char[] keyPassphrase = null;
      private PrivateKey key = null;
      private Certificate cert = null;
      private Certificate[] chain = null;
      private List certAsAList = null;
      private boolean isValid = false;

      private SAMLKeyInfoSpiImpl() {
      }

      public SAMLKeyInfoSpiImpl(SAMLKeyServiceImpl serviceImpl, LoggerSpi logger, String keyAlias, char[] keyPassphrase) {
         this.serviceImpl = serviceImpl;
         this.logger = logger;
         this.update(keyAlias, keyPassphrase);
      }

      public PrivateKey getKey() {
         this.updateIfInvalid();
         return this.key;
      }

      public List getCertAsList() {
         this.updateIfInvalid();
         return this.certAsAList;
      }

      public boolean isValid() {
         this.updateIfInvalid();
         return this.isValid;
      }

      public Certificate getCert() {
         this.updateIfInvalid();
         return this.cert;
      }

      public Certificate[] getChain() {
         this.updateIfInvalid();
         return this.chain;
      }

      public void update(String keyAlias2, char[] keyPassphrase2) {
         boolean debug = this.logger.isDebugEnabled();
         String method = this.getClass().getName() + ".update";
         if (debug) {
            this.logger.debug(method);
         }

         synchronized(this.serviceImpl) {
            if ((this.keyAlias != null || keyAlias2 == null) && (this.keyAlias == null || this.keyAlias.equals(keyAlias2)) && Arrays.equals(this.keyPassphrase, keyPassphrase2)) {
               if (!this.isValid) {
                  if (debug) {
                     this.logger.debug(method + " Not valid, attempt update");
                  }

                  this.isValid = this.loadKeyInfo();
               } else if (debug) {
                  this.logger.debug(method + " Alias and password unchanged, not updating");
               }
            } else {
               if (debug) {
                  this.logger.debug(method + " Alias or password changed");
               }

               this.keyAlias = keyAlias2;
               this.clearPassphrase();
               if (keyPassphrase2 != null) {
                  this.keyPassphrase = new char[keyPassphrase2.length];
                  System.arraycopy(keyPassphrase2, 0, this.keyPassphrase, 0, keyPassphrase2.length);
               }

               this.isValid = this.loadKeyInfo();
            }

         }
      }

      private final void updateIfInvalid() {
         if (!this.isValid || this.serviceImpl.storeValidityCheckNeeded()) {
            synchronized(this.serviceImpl) {
               this.serviceImpl.checkStoreValidity();
               if (this.isValid) {
                  return;
               }

               this.isValid = this.loadKeyInfo();
            }
         }

      }

      private void invalidate() {
         boolean debug = this.logger.isDebugEnabled();
         String method = this.getClass().getName() + ".invalidate";
         if (debug) {
            this.logger.debug(method);
         }

         synchronized(this.serviceImpl) {
            this.isValid = false;
            this.key = null;
            this.cert = null;
            this.chain = null;
            this.certAsAList = null;
         }
      }

      private void clearPassphrase() {
         if (this.keyPassphrase != null) {
            Arrays.fill(this.keyPassphrase, '\u0000');
            this.keyPassphrase = null;
         }

      }

      private boolean loadKeyInfo() {
         boolean debug = this.logger.isDebugEnabled();
         String method = this.getClass().getName() + ".loadKeyInfo";
         if (debug) {
            this.logger.debug(method);
         }

         this.invalidate();
         KeyStore keyStore = this.serviceImpl.getKeyStore();
         if (keyStore == null) {
            if (debug) {
               this.logger.debug(method + " Unable to open keystore");
            }

            return false;
         } else {
            if (debug) {
               this.logger.debug(method + " Loading key for alias '" + this.keyAlias + "'");
            }

            Key tmpKey = null;

            try {
               tmpKey = keyStore.getKey(this.keyAlias, this.keyPassphrase);
               this.chain = keyStore.getCertificateChain(this.keyAlias);
            } catch (Exception var6) {
               if (debug) {
                  this.logger.debug(method + " Exception while loading key: " + var6.toString());
               }

               return false;
            }

            if (!(tmpKey instanceof PrivateKey)) {
               if (debug) {
                  this.logger.debug(method + " Private key not found");
               }

               return false;
            } else {
               if (debug) {
                  this.logger.debug(method + "  Private key found");
               }

               this.key = (PrivateKey)tmpKey;
               if (this.chain != null && this.chain.length != 0) {
                  if (debug) {
                     this.logger.debug(method + " Certificate chain length: " + this.chain.length);
                  }

                  if (this.chain[0] == null) {
                     if (debug) {
                        this.logger.debug(method + " End entity Certificate not found");
                     }

                     return false;
                  } else {
                     if (debug) {
                        this.logger.debug(method + " End entity Certificate found");
                     }

                     this.cert = this.chain[0];
                     this.certAsAList = Collections.singletonList(this.cert);
                     if (debug) {
                        this.logger.debug(method + " Successfully loaded key for alias '" + this.keyAlias + "'");
                     }

                     return true;
                  }
               } else {
                  if (debug) {
                     this.logger.debug(method + " Certificate chain not found");
                  }

                  return false;
               }
            }
         }
      }
   }
}
