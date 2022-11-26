package org.glassfish.tyrus.client;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class SslContextConfigurator {
   public static final String TRUST_STORE_PROVIDER = "javax.net.ssl.trustStoreProvider";
   public static final String KEY_STORE_PROVIDER = "javax.net.ssl.keyStoreProvider";
   public static final String TRUST_STORE_FILE = "javax.net.ssl.trustStore";
   public static final String KEY_STORE_FILE = "javax.net.ssl.keyStore";
   public static final String TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
   public static final String KEY_STORE_PASSWORD = "javax.net.ssl.keyStorePassword";
   public static final String TRUST_STORE_TYPE = "javax.net.ssl.trustStoreType";
   public static final String KEY_STORE_TYPE = "javax.net.ssl.keyStoreType";
   public static final String KEY_FACTORY_MANAGER_ALGORITHM = "ssl.KeyManagerFactory.algorithm";
   public static final String TRUST_FACTORY_MANAGER_ALGORITHM = "ssl.TrustManagerFactory.algorithm";
   private static final Logger LOGGER = Logger.getLogger(SslContextConfigurator.class.getName());
   public static final SslContextConfigurator DEFAULT_CONFIG = new SslContextConfigurator();
   private String trustStoreProvider;
   private String keyStoreProvider;
   private String trustStoreType;
   private String keyStoreType;
   private char[] trustStorePassword;
   private char[] keyStorePassword;
   private char[] keyPassword;
   private String trustStoreFile;
   private String keyStoreFile;
   private byte[] trustStoreBytes;
   private byte[] keyStoreBytes;
   private String trustManagerFactoryAlgorithm;
   private String keyManagerFactoryAlgorithm;
   private String securityProtocol;

   public SslContextConfigurator() {
      this(true);
   }

   public SslContextConfigurator(boolean readSystemProperties) {
      this.securityProtocol = "TLS";
      if (readSystemProperties) {
         this.retrieve(System.getProperties());
      }

   }

   public SslContextConfigurator setTrustStoreProvider(String trustStoreProvider) {
      this.trustStoreProvider = trustStoreProvider;
      return this;
   }

   public SslContextConfigurator setKeyStoreProvider(String keyStoreProvider) {
      this.keyStoreProvider = keyStoreProvider;
      return this;
   }

   public SslContextConfigurator setTrustStoreType(String trustStoreType) {
      this.trustStoreType = trustStoreType;
      return this;
   }

   public SslContextConfigurator setKeyStoreType(String keyStoreType) {
      this.keyStoreType = keyStoreType;
      return this;
   }

   public SslContextConfigurator setTrustStorePassword(String trustStorePassword) {
      this.trustStorePassword = trustStorePassword.toCharArray();
      return this;
   }

   public SslContextConfigurator setKeyStorePassword(String keyStorePassword) {
      this.keyStorePassword = keyStorePassword.toCharArray();
      return this;
   }

   public SslContextConfigurator setKeyStorePassword(char[] keyStorePassword) {
      this.keyStorePassword = (char[])keyStorePassword.clone();
      return this;
   }

   public SslContextConfigurator setKeyPassword(String keyPassword) {
      this.keyPassword = keyPassword.toCharArray();
      return this;
   }

   public SslContextConfigurator setKeyPassword(char[] keyPassword) {
      this.keyPassword = (char[])keyPassword.clone();
      return this;
   }

   public SslContextConfigurator setTrustStoreFile(String trustStoreFile) {
      this.trustStoreFile = trustStoreFile;
      this.trustStoreBytes = null;
      return this;
   }

   public SslContextConfigurator setTrustStoreBytes(byte[] trustStoreBytes) {
      this.trustStoreBytes = (byte[])trustStoreBytes.clone();
      this.trustStoreFile = null;
      return this;
   }

   public SslContextConfigurator setKeyStoreFile(String keyStoreFile) {
      this.keyStoreFile = keyStoreFile;
      this.keyStoreBytes = null;
      return this;
   }

   public SslContextConfigurator setKeyStoreBytes(byte[] keyStoreBytes) {
      this.keyStoreBytes = (byte[])keyStoreBytes.clone();
      this.keyStoreFile = null;
      return this;
   }

   public SslContextConfigurator setTrustManagerFactoryAlgorithm(String trustManagerFactoryAlgorithm) {
      this.trustManagerFactoryAlgorithm = trustManagerFactoryAlgorithm;
      return this;
   }

   public SslContextConfigurator setKeyManagerFactoryAlgorithm(String keyManagerFactoryAlgorithm) {
      this.keyManagerFactoryAlgorithm = keyManagerFactoryAlgorithm;
      return this;
   }

   public SslContextConfigurator setSecurityProtocol(String securityProtocol) {
      this.securityProtocol = securityProtocol;
      return this;
   }

   public boolean validateConfiguration() {
      return this.validateConfiguration(false);
   }

   public boolean validateConfiguration(boolean needsKeyStore) {
      boolean valid = true;
      KeyStore trustStore;
      Object trustStoreInputStream;
      String tmfAlgorithm;
      if (this.keyStoreBytes == null && this.keyStoreFile == null) {
         valid &= !needsKeyStore;
      } else {
         try {
            if (this.keyStoreProvider != null) {
               trustStore = KeyStore.getInstance(this.keyStoreType != null ? this.keyStoreType : KeyStore.getDefaultType(), this.keyStoreProvider);
            } else {
               trustStore = KeyStore.getInstance(this.keyStoreType != null ? this.keyStoreType : KeyStore.getDefaultType());
            }

            trustStoreInputStream = null;

            try {
               if (this.keyStoreBytes != null) {
                  trustStoreInputStream = new ByteArrayInputStream(this.keyStoreBytes);
               } else if (!this.keyStoreFile.equals("NONE")) {
                  trustStoreInputStream = new FileInputStream(this.keyStoreFile);
               }

               trustStore.load((InputStream)trustStoreInputStream, this.keyStorePassword);
            } finally {
               try {
                  if (trustStoreInputStream != null) {
                     ((InputStream)trustStoreInputStream).close();
                  }
               } catch (IOException var55) {
                  LOGGER.log(Level.FINEST, "Could not close key store file", var55);
               }

            }

            tmfAlgorithm = this.keyManagerFactoryAlgorithm;
            if (tmfAlgorithm == null) {
               tmfAlgorithm = System.getProperty("ssl.KeyManagerFactory.algorithm", KeyManagerFactory.getDefaultAlgorithm());
            }

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(tmfAlgorithm);
            keyManagerFactory.init(trustStore, this.keyPassword != null ? this.keyPassword : this.keyStorePassword);
         } catch (KeyStoreException var57) {
            LOGGER.log(Level.FINE, "Error initializing key store", var57);
            valid = false;
         } catch (CertificateException var58) {
            LOGGER.log(Level.FINE, "Key store certificate exception.", var58);
            valid = false;
         } catch (UnrecoverableKeyException var59) {
            LOGGER.log(Level.FINE, "Key store unrecoverable exception.", var59);
            valid = false;
         } catch (FileNotFoundException var60) {
            LOGGER.log(Level.FINE, "Can't find key store file: " + this.keyStoreFile, var60);
            valid = false;
         } catch (IOException var61) {
            LOGGER.log(Level.FINE, "Error loading key store from file: " + this.keyStoreFile, var61);
            valid = false;
         } catch (NoSuchAlgorithmException var62) {
            LOGGER.log(Level.FINE, "Error initializing key manager factory (no such algorithm)", var62);
            valid = false;
         } catch (NoSuchProviderException var63) {
            LOGGER.log(Level.FINE, "Error initializing key store (no such provider)", var63);
            valid = false;
         }
      }

      if (this.trustStoreBytes != null || this.trustStoreFile != null) {
         try {
            if (this.trustStoreProvider != null) {
               trustStore = KeyStore.getInstance(this.trustStoreType != null ? this.trustStoreType : KeyStore.getDefaultType(), this.trustStoreProvider);
            } else {
               trustStore = KeyStore.getInstance(this.trustStoreType != null ? this.trustStoreType : KeyStore.getDefaultType());
            }

            trustStoreInputStream = null;

            try {
               if (this.trustStoreBytes != null) {
                  trustStoreInputStream = new ByteArrayInputStream(this.trustStoreBytes);
               } else if (!this.trustStoreFile.equals("NONE")) {
                  trustStoreInputStream = new FileInputStream(this.trustStoreFile);
               }

               trustStore.load((InputStream)trustStoreInputStream, this.trustStorePassword);
            } finally {
               try {
                  if (trustStoreInputStream != null) {
                     ((InputStream)trustStoreInputStream).close();
                  }
               } catch (IOException var47) {
                  LOGGER.log(Level.FINEST, "Could not close key store file", var47);
               }

            }

            tmfAlgorithm = this.trustManagerFactoryAlgorithm;
            if (tmfAlgorithm == null) {
               tmfAlgorithm = System.getProperty("ssl.TrustManagerFactory.algorithm", TrustManagerFactory.getDefaultAlgorithm());
            }

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(trustStore);
         } catch (KeyStoreException var49) {
            LOGGER.log(Level.FINE, "Error initializing trust store", var49);
            valid = false;
         } catch (CertificateException var50) {
            LOGGER.log(Level.FINE, "Trust store certificate exception.", var50);
            valid = false;
         } catch (FileNotFoundException var51) {
            LOGGER.log(Level.FINE, "Can't find trust store file: " + this.trustStoreFile, var51);
            valid = false;
         } catch (IOException var52) {
            LOGGER.log(Level.FINE, "Error loading trust store from file: " + this.trustStoreFile, var52);
            valid = false;
         } catch (NoSuchAlgorithmException var53) {
            LOGGER.log(Level.FINE, "Error initializing trust manager factory (no such algorithm)", var53);
            valid = false;
         } catch (NoSuchProviderException var54) {
            LOGGER.log(Level.FINE, "Error initializing trust store (no such provider)", var54);
            valid = false;
         }
      }

      return valid;
   }

   public SSLContext createSSLContext() {
      SSLContext sslContext = null;

      try {
         TrustManagerFactory trustManagerFactory = null;
         KeyManagerFactory keyManagerFactory = null;
         KeyStore trustStore;
         Object trustStoreInputStream;
         String tmfAlgorithm;
         if (this.keyStoreBytes != null || this.keyStoreFile != null) {
            try {
               if (this.keyStoreProvider != null) {
                  trustStore = KeyStore.getInstance(this.keyStoreType != null ? this.keyStoreType : KeyStore.getDefaultType(), this.keyStoreProvider);
               } else {
                  trustStore = KeyStore.getInstance(this.keyStoreType != null ? this.keyStoreType : KeyStore.getDefaultType());
               }

               trustStoreInputStream = null;

               try {
                  if (this.keyStoreBytes != null) {
                     trustStoreInputStream = new ByteArrayInputStream(this.keyStoreBytes);
                  } else if (!this.keyStoreFile.equals("NONE")) {
                     trustStoreInputStream = new FileInputStream(this.keyStoreFile);
                  }

                  trustStore.load((InputStream)trustStoreInputStream, this.keyStorePassword);
               } finally {
                  try {
                     if (trustStoreInputStream != null) {
                        ((InputStream)trustStoreInputStream).close();
                     }
                  } catch (IOException var60) {
                     LOGGER.log(Level.FINEST, "Could not close key store file", var60);
                  }

               }

               tmfAlgorithm = this.keyManagerFactoryAlgorithm;
               if (tmfAlgorithm == null) {
                  tmfAlgorithm = System.getProperty("ssl.KeyManagerFactory.algorithm", KeyManagerFactory.getDefaultAlgorithm());
               }

               keyManagerFactory = KeyManagerFactory.getInstance(tmfAlgorithm);
               keyManagerFactory.init(trustStore, this.keyPassword != null ? this.keyPassword : this.keyStorePassword);
            } catch (KeyStoreException var62) {
               LOGGER.log(Level.FINE, "Error initializing key store", var62);
            } catch (CertificateException var63) {
               LOGGER.log(Level.FINE, "Key store certificate exception.", var63);
            } catch (UnrecoverableKeyException var64) {
               LOGGER.log(Level.FINE, "Key store unrecoverable exception.", var64);
            } catch (FileNotFoundException var65) {
               LOGGER.log(Level.FINE, "Can't find key store file: " + this.keyStoreFile, var65);
            } catch (IOException var66) {
               LOGGER.log(Level.FINE, "Error loading key store from file: " + this.keyStoreFile, var66);
            } catch (NoSuchAlgorithmException var67) {
               LOGGER.log(Level.FINE, "Error initializing key manager factory (no such algorithm)", var67);
            } catch (NoSuchProviderException var68) {
               LOGGER.log(Level.FINE, "Error initializing key store (no such provider)", var68);
            }
         }

         if (this.trustStoreBytes != null || this.trustStoreFile != null) {
            try {
               if (this.trustStoreProvider != null) {
                  trustStore = KeyStore.getInstance(this.trustStoreType != null ? this.trustStoreType : KeyStore.getDefaultType(), this.trustStoreProvider);
               } else {
                  trustStore = KeyStore.getInstance(this.trustStoreType != null ? this.trustStoreType : KeyStore.getDefaultType());
               }

               trustStoreInputStream = null;

               try {
                  if (this.trustStoreBytes != null) {
                     trustStoreInputStream = new ByteArrayInputStream(this.trustStoreBytes);
                  } else if (!this.trustStoreFile.equals("NONE")) {
                     trustStoreInputStream = new FileInputStream(this.trustStoreFile);
                  }

                  trustStore.load((InputStream)trustStoreInputStream, this.trustStorePassword);
               } finally {
                  try {
                     if (trustStoreInputStream != null) {
                        ((InputStream)trustStoreInputStream).close();
                     }
                  } catch (IOException var52) {
                     LOGGER.log(Level.FINEST, "Could not close trust store file", var52);
                  }

               }

               tmfAlgorithm = this.trustManagerFactoryAlgorithm;
               if (tmfAlgorithm == null) {
                  tmfAlgorithm = System.getProperty("ssl.TrustManagerFactory.algorithm", TrustManagerFactory.getDefaultAlgorithm());
               }

               trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
               trustManagerFactory.init(trustStore);
            } catch (KeyStoreException var54) {
               LOGGER.log(Level.FINE, "Error initializing trust store", var54);
            } catch (CertificateException var55) {
               LOGGER.log(Level.FINE, "Trust store certificate exception.", var55);
            } catch (FileNotFoundException var56) {
               LOGGER.log(Level.FINE, "Can't find trust store file: " + this.trustStoreFile, var56);
            } catch (IOException var57) {
               LOGGER.log(Level.FINE, "Error loading trust store from file: " + this.trustStoreFile, var57);
            } catch (NoSuchAlgorithmException var58) {
               LOGGER.log(Level.FINE, "Error initializing trust manager factory (no such algorithm)", var58);
            } catch (NoSuchProviderException var59) {
               LOGGER.log(Level.FINE, "Error initializing trust store (no such provider)", var59);
            }
         }

         String secProtocol = "TLS";
         if (this.securityProtocol != null) {
            secProtocol = this.securityProtocol;
         }

         sslContext = SSLContext.getInstance(secProtocol);
         sslContext.init(keyManagerFactory != null ? keyManagerFactory.getKeyManagers() : null, trustManagerFactory != null ? trustManagerFactory.getTrustManagers() : null, (SecureRandom)null);
      } catch (KeyManagementException var69) {
         LOGGER.log(Level.FINE, "Key management error.", var69);
      } catch (NoSuchAlgorithmException var70) {
         LOGGER.log(Level.FINE, "Error initializing algorithm.", var70);
      }

      return sslContext;
   }

   public SslContextConfigurator retrieve(Properties props) {
      this.trustStoreProvider = props.getProperty("javax.net.ssl.trustStoreProvider");
      this.keyStoreProvider = props.getProperty("javax.net.ssl.keyStoreProvider");
      this.trustStoreType = props.getProperty("javax.net.ssl.trustStoreType");
      this.keyStoreType = props.getProperty("javax.net.ssl.keyStoreType");
      if (props.getProperty("javax.net.ssl.trustStorePassword") != null) {
         this.trustStorePassword = props.getProperty("javax.net.ssl.trustStorePassword").toCharArray();
      } else {
         this.trustStorePassword = null;
      }

      if (props.getProperty("javax.net.ssl.keyStorePassword") != null) {
         this.keyStorePassword = props.getProperty("javax.net.ssl.keyStorePassword").toCharArray();
      } else {
         this.keyStorePassword = null;
      }

      this.trustStoreFile = props.getProperty("javax.net.ssl.trustStore");
      this.keyStoreFile = props.getProperty("javax.net.ssl.keyStore");
      this.trustStoreBytes = null;
      this.keyStoreBytes = null;
      this.securityProtocol = "TLS";
      return this;
   }
}
