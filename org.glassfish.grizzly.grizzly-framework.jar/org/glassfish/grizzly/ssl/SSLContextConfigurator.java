package org.glassfish.grizzly.ssl;

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
import org.glassfish.grizzly.Grizzly;

public class SSLContextConfigurator {
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
   private static final Logger LOGGER = Grizzly.logger(SSLContextConfigurator.class);
   public static final SSLContextConfigurator DEFAULT_CONFIG = new SSLContextConfigurator();
   private String trustStoreProvider;
   private String keyStoreProvider;
   private String trustStoreType;
   private String keyStoreType;
   private char[] trustStorePass;
   private char[] keyStorePass;
   private char[] keyPass;
   private String trustStoreFile;
   private String keyStoreFile;
   private byte[] trustStoreBytes;
   private byte[] keyStoreBytes;
   private String trustManagerFactoryAlgorithm;
   private String keyManagerFactoryAlgorithm;
   private String securityProtocol;

   public SSLContextConfigurator() {
      this(true);
   }

   public SSLContextConfigurator(boolean readSystemProperties) {
      this.securityProtocol = "TLS";
      if (readSystemProperties) {
         this.retrieve(System.getProperties());
      }

   }

   public void setTrustStoreProvider(String trustStoreProvider) {
      this.trustStoreProvider = trustStoreProvider;
   }

   public void setKeyStoreProvider(String keyStoreProvider) {
      this.keyStoreProvider = keyStoreProvider;
   }

   public void setTrustStoreType(String trustStoreType) {
      this.trustStoreType = trustStoreType;
   }

   public void setKeyStoreType(String keyStoreType) {
      this.keyStoreType = keyStoreType;
   }

   public void setTrustStorePass(String trustStorePass) {
      this.trustStorePass = trustStorePass.toCharArray();
   }

   public void setKeyStorePass(String keyStorePass) {
      this.keyStorePass = keyStorePass.toCharArray();
   }

   public void setKeyStorePass(char[] keyStorePass) {
      this.keyStorePass = keyStorePass;
   }

   public void setKeyPass(String keyPass) {
      this.keyPass = keyPass.toCharArray();
   }

   public void setKeyPass(char[] keyPass) {
      this.keyPass = keyPass;
   }

   public void setTrustStoreFile(String trustStoreFile) {
      this.trustStoreFile = trustStoreFile;
      this.trustStoreBytes = null;
   }

   public void setTrustStoreBytes(byte[] trustStoreBytes) {
      this.trustStoreBytes = trustStoreBytes;
      this.trustStoreFile = null;
   }

   public void setKeyStoreFile(String keyStoreFile) {
      this.keyStoreFile = keyStoreFile;
      this.keyStoreBytes = null;
   }

   public void setKeyStoreBytes(byte[] keyStoreBytes) {
      this.keyStoreBytes = keyStoreBytes;
      this.keyStoreFile = null;
   }

   public void setTrustManagerFactoryAlgorithm(String trustManagerFactoryAlgorithm) {
      this.trustManagerFactoryAlgorithm = trustManagerFactoryAlgorithm;
   }

   public void setKeyManagerFactoryAlgorithm(String keyManagerFactoryAlgorithm) {
      this.keyManagerFactoryAlgorithm = keyManagerFactoryAlgorithm;
   }

   public void setSecurityProtocol(String securityProtocol) {
      this.securityProtocol = securityProtocol;
   }

   /** @deprecated */
   @Deprecated
   public boolean validateConfiguration() {
      return this.validateConfiguration(false);
   }

   /** @deprecated */
   @Deprecated
   public boolean validateConfiguration(boolean needsKeyStore) {
      boolean valid = true;
      KeyStore trustStore;
      String tmfAlgorithm;
      if (this.keyStoreBytes == null && this.keyStoreFile == null) {
         valid = !needsKeyStore;
      } else {
         try {
            if (this.keyStoreProvider != null) {
               trustStore = KeyStore.getInstance(this.keyStoreType != null ? this.keyStoreType : KeyStore.getDefaultType(), this.keyStoreProvider);
            } else {
               trustStore = KeyStore.getInstance(this.keyStoreType != null ? this.keyStoreType : KeyStore.getDefaultType());
            }

            loadBytes(this.keyStoreBytes, this.keyStoreFile, this.keyStorePass, trustStore);
            tmfAlgorithm = this.keyManagerFactoryAlgorithm;
            if (tmfAlgorithm == null) {
               tmfAlgorithm = System.getProperty("ssl.KeyManagerFactory.algorithm", KeyManagerFactory.getDefaultAlgorithm());
            }

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(tmfAlgorithm);
            keyManagerFactory.init(trustStore, this.keyPass != null ? this.keyPass : this.keyStorePass);
         } catch (KeyStoreException var12) {
            LOGGER.log(Level.FINE, "Error initializing key store", var12);
            valid = false;
         } catch (CertificateException var13) {
            LOGGER.log(Level.FINE, "Key store certificate exception.", var13);
            valid = false;
         } catch (UnrecoverableKeyException var14) {
            LOGGER.log(Level.FINE, "Key store unrecoverable exception.", var14);
            valid = false;
         } catch (FileNotFoundException var15) {
            LOGGER.log(Level.FINE, "Can't find key store file: " + this.keyStoreFile, var15);
            valid = false;
         } catch (IOException var16) {
            LOGGER.log(Level.FINE, "Error loading key store from file: " + this.keyStoreFile, var16);
            valid = false;
         } catch (NoSuchAlgorithmException var17) {
            LOGGER.log(Level.FINE, "Error initializing key manager factory (no such algorithm)", var17);
            valid = false;
         } catch (NoSuchProviderException var18) {
            LOGGER.log(Level.FINE, "Error initializing key store (no such provider)", var18);
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

            loadBytes(this.trustStoreBytes, this.trustStoreFile, this.trustStorePass, trustStore);
            tmfAlgorithm = this.trustManagerFactoryAlgorithm;
            if (tmfAlgorithm == null) {
               tmfAlgorithm = System.getProperty("ssl.TrustManagerFactory.algorithm", TrustManagerFactory.getDefaultAlgorithm());
            }

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(trustStore);
         } catch (KeyStoreException var6) {
            LOGGER.log(Level.FINE, "Error initializing trust store", var6);
            valid = false;
         } catch (CertificateException var7) {
            LOGGER.log(Level.FINE, "Trust store certificate exception.", var7);
            valid = false;
         } catch (FileNotFoundException var8) {
            LOGGER.log(Level.FINE, "Can't find trust store file: " + this.trustStoreFile, var8);
            valid = false;
         } catch (IOException var9) {
            LOGGER.log(Level.FINE, "Error loading trust store from file: " + this.trustStoreFile, var9);
            valid = false;
         } catch (NoSuchAlgorithmException var10) {
            LOGGER.log(Level.FINE, "Error initializing trust manager factory (no such algorithm)", var10);
            valid = false;
         } catch (NoSuchProviderException var11) {
            LOGGER.log(Level.FINE, "Error initializing trust store (no such provider)", var11);
            valid = false;
         }
      }

      return valid;
   }

   /** @deprecated */
   @Deprecated
   public SSLContext createSSLContext() {
      return this.createSSLContext(false);
   }

   public SSLContext createSSLContext(boolean throwException) {
      SSLContext sslContext = null;

      try {
         TrustManagerFactory trustManagerFactory = null;
         KeyManagerFactory keyManagerFactory = null;
         KeyStore trustStore;
         String tmfAlgorithm;
         if (this.keyStoreBytes != null || this.keyStoreFile != null) {
            try {
               if (this.keyStoreProvider != null) {
                  trustStore = KeyStore.getInstance(this.keyStoreType != null ? this.keyStoreType : KeyStore.getDefaultType(), this.keyStoreProvider);
               } else {
                  trustStore = KeyStore.getInstance(this.keyStoreType != null ? this.keyStoreType : KeyStore.getDefaultType());
               }

               loadBytes(this.keyStoreBytes, this.keyStoreFile, this.keyStorePass, trustStore);
               tmfAlgorithm = this.keyManagerFactoryAlgorithm;
               if (tmfAlgorithm == null) {
                  tmfAlgorithm = System.getProperty("ssl.KeyManagerFactory.algorithm", KeyManagerFactory.getDefaultAlgorithm());
               }

               keyManagerFactory = KeyManagerFactory.getInstance(tmfAlgorithm);
               keyManagerFactory.init(trustStore, this.keyPass != null ? this.keyPass : this.keyStorePass);
            } catch (KeyStoreException var7) {
               LOGGER.log(Level.FINE, "Error initializing key store", var7);
               if (throwException) {
                  throw new GenericStoreException(var7);
               }
            } catch (CertificateException var8) {
               LOGGER.log(Level.FINE, "Key store certificate exception.", var8);
               if (throwException) {
                  throw new GenericStoreException(var8);
               }
            } catch (UnrecoverableKeyException var9) {
               LOGGER.log(Level.FINE, "Key store unrecoverable exception.", var9);
               if (throwException) {
                  throw new GenericStoreException(var9);
               }
            } catch (FileNotFoundException var10) {
               LOGGER.log(Level.FINE, "Can't find key store file: " + this.keyStoreFile, var10);
               if (throwException) {
                  throw new GenericStoreException(var10);
               }
            } catch (IOException var11) {
               LOGGER.log(Level.FINE, "Error loading key store from file: " + this.keyStoreFile, var11);
               if (throwException) {
                  throw new GenericStoreException(var11);
               }
            } catch (NoSuchAlgorithmException var12) {
               LOGGER.log(Level.FINE, "Error initializing key manager factory (no such algorithm)", var12);
               if (throwException) {
                  throw new GenericStoreException(var12);
               }
            } catch (NoSuchProviderException var13) {
               LOGGER.log(Level.FINE, "Error initializing key store (no such provider)", var13);
            }
         }

         if (this.trustStoreBytes != null || this.trustStoreFile != null) {
            try {
               if (this.trustStoreProvider != null) {
                  trustStore = KeyStore.getInstance(this.trustStoreType != null ? this.trustStoreType : KeyStore.getDefaultType(), this.trustStoreProvider);
               } else {
                  trustStore = KeyStore.getInstance(this.trustStoreType != null ? this.trustStoreType : KeyStore.getDefaultType());
               }

               loadBytes(this.trustStoreBytes, this.trustStoreFile, this.trustStorePass, trustStore);
               tmfAlgorithm = this.trustManagerFactoryAlgorithm;
               if (tmfAlgorithm == null) {
                  tmfAlgorithm = System.getProperty("ssl.TrustManagerFactory.algorithm", TrustManagerFactory.getDefaultAlgorithm());
               }

               trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
               trustManagerFactory.init(trustStore);
            } catch (KeyStoreException var14) {
               LOGGER.log(Level.FINE, "Error initializing trust store", var14);
               if (throwException) {
                  throw new GenericStoreException(var14);
               }
            } catch (CertificateException var15) {
               LOGGER.log(Level.FINE, "Trust store certificate exception.", var15);
               if (throwException) {
                  throw new GenericStoreException(var15);
               }
            } catch (FileNotFoundException var16) {
               LOGGER.log(Level.FINE, "Can't find trust store file: " + this.trustStoreFile, var16);
               if (throwException) {
                  throw new GenericStoreException(var16);
               }
            } catch (IOException var17) {
               LOGGER.log(Level.FINE, "Error loading trust store from file: " + this.trustStoreFile, var17);
               if (throwException) {
                  throw new GenericStoreException(var17);
               }
            } catch (NoSuchAlgorithmException var18) {
               LOGGER.log(Level.FINE, "Error initializing trust manager factory (no such algorithm)", var18);
               if (throwException) {
                  throw new GenericStoreException(var18);
               }
            } catch (NoSuchProviderException var19) {
               LOGGER.log(Level.FINE, "Error initializing trust store (no such provider)", var19);
               if (throwException) {
                  throw new GenericStoreException(var19);
               }
            }
         }

         String secProtocol = "TLS";
         if (this.securityProtocol != null) {
            secProtocol = this.securityProtocol;
         }

         sslContext = SSLContext.getInstance(secProtocol);
         sslContext.init(keyManagerFactory != null ? keyManagerFactory.getKeyManagers() : null, trustManagerFactory != null ? trustManagerFactory.getTrustManagers() : null, (SecureRandom)null);
      } catch (KeyManagementException var20) {
         LOGGER.log(Level.FINE, "Key management error.", var20);
         if (throwException) {
            throw new GenericStoreException(var20);
         }
      } catch (NoSuchAlgorithmException var21) {
         LOGGER.log(Level.FINE, "Error initializing algorithm.", var21);
         if (throwException) {
            throw new GenericStoreException(var21);
         }
      }

      return sslContext;
   }

   public void retrieve(Properties props) {
      this.trustStoreProvider = props.getProperty("javax.net.ssl.trustStoreProvider");
      this.keyStoreProvider = props.getProperty("javax.net.ssl.keyStoreProvider");
      this.trustStoreType = props.getProperty("javax.net.ssl.trustStoreType");
      this.keyStoreType = props.getProperty("javax.net.ssl.keyStoreType");
      if (props.getProperty("javax.net.ssl.trustStorePassword") != null) {
         this.trustStorePass = props.getProperty("javax.net.ssl.trustStorePassword").toCharArray();
      } else {
         this.trustStorePass = null;
      }

      if (props.getProperty("javax.net.ssl.keyStorePassword") != null) {
         this.keyStorePass = props.getProperty("javax.net.ssl.keyStorePassword").toCharArray();
      } else {
         this.keyStorePass = null;
      }

      this.trustStoreFile = props.getProperty("javax.net.ssl.trustStore");
      this.keyStoreFile = props.getProperty("javax.net.ssl.keyStore");
      this.trustStoreBytes = null;
      this.keyStoreBytes = null;
      this.securityProtocol = "TLS";
   }

   private static void loadBytes(byte[] bytes, String storeFile, char[] password, KeyStore store) throws IOException, CertificateException, NoSuchAlgorithmException {
      InputStream inputStream = null;

      try {
         if (bytes != null) {
            inputStream = new ByteArrayInputStream(bytes);
         } else if (!"NONE".equals(storeFile)) {
            inputStream = new FileInputStream(storeFile);
         }

         store.load((InputStream)inputStream, password);
      } finally {
         try {
            if (inputStream != null) {
               ((InputStream)inputStream).close();
            }
         } catch (IOException var11) {
         }

      }

   }

   public static final class GenericStoreException extends RuntimeException {
      public GenericStoreException(Throwable cause) {
         super(cause);
      }
   }
}
