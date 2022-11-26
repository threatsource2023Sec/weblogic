package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.SAMLKeyInfoSpi;
import com.bea.common.security.service.SAMLKeyService;
import com.bea.common.security.servicecfg.SAMLKeyServiceConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SAMLKeyServiceImpl implements ServiceLifecycleSpi, SAMLKeyService {
   private LoggerSpi logger;
   private String keyStoreFile = null;
   private String keyStoreType = null;
   private char[] keyStorePassphrase = null;
   private static final int DEFAULT_POLL_INTERVAL = 15000;
   private int storeValidationPollInterval = 15000;
   private String defaultKeyAlias = null;
   private char[] defaultKeyPassphrase = null;
   private SAMLKeyInfoSpiImpl defaultKeyInfo = null;
   private HashMap namedKeyInfos = new HashMap();
   private boolean keyStoreFileConfigured = false;
   private KeyStore keyStore = null;
   private long lastKeyStoreFileModTime = 0L;
   private long lastKeyStoreFileCheckTime = 0L;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.SAMLKeyService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      if (config != null && config instanceof SAMLKeyServiceConfig) {
         SAMLKeyServiceConfig myconfig = (SAMLKeyServiceConfig)config;
         this.storeValidationPollInterval = myconfig.getStoreValidationPollInterval();
         if (this.storeValidationPollInterval <= 0) {
            this.logger.warn(ServiceLogger.getInvalidConfigurationSettingSupplied(method, "SAMLKeyServiceConfig", "StoreValidationPollInterval"));
            this.storeValidationPollInterval = 15000;
         }

         this.keyStoreFile = myconfig.getKeyStoreFile();
         if (this.keyStoreFile != null && this.keyStoreFile.length() != 0) {
            this.keyStoreFileConfigured = true;
         } else {
            this.logger.warn(ServiceLogger.getConfigurationMissingRequiredInfo(method, "SAMLKeyServiceConfig", "KeyStoreFile"));
         }

         this.keyStoreType = myconfig.getKeyStoreType();
         char[] temp = myconfig.getKeyStorePassPhrase();
         if (temp != null) {
            this.keyStorePassphrase = new char[temp.length];
            System.arraycopy(temp, 0, this.keyStorePassphrase, 0, temp.length);
         }

         if (!this.isKeystoreAccessible()) {
            this.logger.warn(ServiceLogger.getKeystoreNotAccessible(this.keyStoreFile, this.keyStoreType));
         }

         this.defaultKeyAlias = myconfig.getDefaultKeyAlias();
         temp = myconfig.getDefaultKeyPassphrase();
         if (temp != null) {
            this.defaultKeyPassphrase = new char[temp.length];
            System.arraycopy(temp, 0, this.defaultKeyPassphrase, 0, temp.length);
         }

         if (this.defaultKeyAlias == null || this.defaultKeyAlias.length() == 0) {
            this.logger.warn(ServiceLogger.getConfigurationMissingRequiredInfo(method, "SAMLKeyServiceConfig", "DefaultKeyAlias"));
         }

         return Delegator.getProxy((Class)SAMLKeyService.class, this);
      } else {
         throw new ServiceConfigurationException(ServiceLogger.getExpectedConfigurationNotSupplied(method, "SAMLKeyServiceConfig"));
      }
   }

   public synchronized void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

      this.keyStore = null;
      this.keyStoreFileConfigured = false;
      if (this.keyStorePassphrase != null) {
         Arrays.fill(this.keyStorePassphrase, '\u0000');
      }

      this.keyStorePassphrase = null;
      if (this.defaultKeyPassphrase != null) {
         Arrays.fill(this.defaultKeyPassphrase, '\u0000');
      }

      this.defaultKeyPassphrase = null;
      this.invalidateAllKeys(true);
   }

   public boolean isKeystoreAccessible() {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".isKeystoreAccessible";
      if (debug) {
         this.logger.debug(method);
      }

      synchronized(this) {
         if (!this.keyStoreFileConfigured) {
            if (debug) {
               this.logger.debug(method + " KeyStoreFile not configured, so it is not accessible");
            }

            return false;
         } else {
            return this.getKeyStore() != null;
         }
      }
   }

   public SAMLKeyInfoSpi getKeyInfo(String name, String keyAlias, char[] keyPassphrase) {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".getKeyInfo";
      if (debug) {
         this.logger.debug(method);
      }

      if (name == null) {
         throw new IllegalArgumentException(ServiceLogger.getNullArgumentSpecified("key name"));
      } else if (keyAlias == null) {
         throw new IllegalArgumentException(ServiceLogger.getNullArgumentSpecified("key alias"));
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

      if (name == null) {
         throw new IllegalArgumentException(ServiceLogger.getNullArgumentSpecified("key name"));
      } else {
         SAMLKeyInfoSpiImpl keyInfo = null;
         synchronized(this) {
            keyInfo = (SAMLKeyInfoSpiImpl)this.namedKeyInfos.get(name);
            return keyInfo;
         }
      }
   }

   public SAMLKeyInfoSpi getDefaultKeyInfo() {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".getDefaultKeyInfo";
      if (debug) {
         this.logger.debug(method);
      }

      synchronized(this) {
         if (this.defaultKeyAlias != null && this.defaultKeyAlias.length() != 0) {
            if (this.defaultKeyInfo == null) {
               this.defaultKeyInfo = new SAMLKeyInfoSpiImpl(this, this.logger, this.defaultKeyAlias, this.defaultKeyPassphrase);
            }

            return this.defaultKeyInfo;
         } else {
            if (debug) {
               this.logger.debug(method + " defaultKeyAlias not configured, returning null");
            }

            return null;
         }
      }
   }

   public SAMLKeyInfoSpi getDefaultKeyInfo(String name) {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".getDefaultKeyInfo";
      if (debug) {
         this.logger.debug(method);
      }

      synchronized(this) {
         if (this.defaultKeyAlias != null && this.defaultKeyAlias.length() != 0) {
            if (this.defaultKeyInfo == null) {
               this.defaultKeyInfo = new SAMLKeyInfoSpiImpl(this, this.logger, this.defaultKeyAlias, this.defaultKeyPassphrase);
            }

            this.namedKeyInfos.put(name, this.defaultKeyInfo);
            return this.defaultKeyInfo;
         } else {
            if (debug) {
               this.logger.debug(method + " defaultKeyAlias not configured, returning null");
            }

            return null;
         }
      }
   }

   private boolean storeValidityCheckNeeded() {
      long now = System.currentTimeMillis();
      long millisSinceLastCheck = now - this.lastKeyStoreFileCheckTime;
      return millisSinceLastCheck > (long)this.storeValidationPollInterval;
   }

   private synchronized void checkStoreValidity() {
      long now = System.currentTimeMillis();
      long millisSinceLastCheck = now - this.lastKeyStoreFileCheckTime;
      if (millisSinceLastCheck > (long)this.storeValidationPollInterval) {
         KeyStore var5 = this.getKeyStore();
      }

   }

   private KeyStore getKeyStore() {
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".getKeyStore";
      if (debug) {
         this.logger.debug(method);
      }

      synchronized(this) {
         if (this.keyStore == null) {
            if (debug) {
               this.logger.debug(method + " No keystore, getting one");
            }

            this.keyStore = this.openKeyStore();
            return this.keyStore;
         } else {
            long now = System.currentTimeMillis();
            long millisSinceLastCheck = now - this.lastKeyStoreFileCheckTime;
            if (millisSinceLastCheck > (long)this.storeValidationPollInterval) {
               if (debug) {
                  this.logger.debug(method + " Checking if the Keystore file was modified");
               }

               this.lastKeyStoreFileCheckTime = now;
               File storeFile = new File(this.keyStoreFile);
               if (storeFile != null && storeFile.isFile()) {
                  if (this.lastKeyStoreFileModTime != storeFile.lastModified()) {
                     if (debug) {
                        this.logger.debug(method + " keystore file has changed, reloading");
                     }

                     this.keyStore = this.openKeyStore();
                  }
               } else {
                  if (debug) {
                     this.logger.debug(method + " couldn't find or access file: " + this.keyStoreFile);
                  }

                  this.keyStore = null;
                  this.invalidateAllKeys(false);
               }
            }

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

      this.invalidateAllKeys(false);
      File storeFile = new File(this.keyStoreFile);
      if (storeFile != null && storeFile.isFile()) {
         Provider[] currentProviders;
         if (debug) {
            this.logger.debug(method + " KeyStore File:  " + storeFile.getAbsolutePath());
            this.logger.debug(method + " KeyStore Type:  " + this.keyStoreType);
            currentProviders = Security.getProviders();

            for(int index = 0; index < currentProviders.length; ++index) {
               this.logger.debug(method + " Security Providers:  " + currentProviders[index].getName() + "  " + currentProviders[index].getVersion());
            }
         }

         this.lastKeyStoreFileCheckTime = System.currentTimeMillis();
         this.lastKeyStoreFileModTime = storeFile.lastModified();
         currentProviders = null;

         KeyStore newKeyStore;
         try {
            newKeyStore = KeyStore.getInstance(this.keyStoreType);
         } catch (KeyStoreException var24) {
            SecurityLogger.logLoadKeyStoreKeyStoreException(this.logger, this.keyStoreType, var24.toString());
            return null;
         }

         FileInputStream is = null;

         try {
            is = new FileInputStream(storeFile);
         } catch (FileNotFoundException var23) {
            SecurityLogger.logLoadKeyStoreFileNotFoundException(this.logger, storeFile.getAbsolutePath(), var23.toString());
            return null;
         }

         Object var7;
         try {
            newKeyStore.load(is, this.keyStorePassphrase);
            return newKeyStore;
         } catch (CertificateException var25) {
            SecurityLogger.logLoadKeyStoreCertificateException(this.logger, storeFile.getAbsolutePath(), this.keyStoreType, var25.toString());
            var7 = null;
            return (KeyStore)var7;
         } catch (NoSuchAlgorithmException var26) {
            SecurityLogger.logLoadKeyStoreNoSuchAlgorithmException(this.logger, storeFile.getAbsolutePath(), this.keyStoreType, var26.toString());
            var7 = null;
         } catch (IOException var27) {
            SecurityLogger.logLoadKeyStoreIOException(this.logger, storeFile.getAbsolutePath(), this.keyStoreType, var27.toString());
            var7 = null;
            return (KeyStore)var7;
         } finally {
            try {
               is.close();
            } catch (IOException var22) {
            }

         }

         return (KeyStore)var7;
      } else {
         if (debug) {
            this.logger.debug(method + " couldn't find or access file: " + this.keyStoreFile);
         }

         return null;
      }
   }

   private void invalidateAllKeys(boolean clearPassphrase) {
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
               if (debug) {
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
