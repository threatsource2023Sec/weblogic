package weblogic.security.SSL.jsseadapter;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import utils.der2pem;
import weblogic.management.provider.CommandLine;
import weblogic.security.SSL.SSLEnabledProtocolVersions;
import weblogic.security.SSL.SSLEnabledProtocolVersionsLogging;
import weblogic.security.utils.SSLSetup;
import weblogic.security.utils.SSLSetupLogging;
import weblogic.utils.encoders.BASE64Decoder;

public final class JaSSLSupport {
   private static final String SSL3 = "SSLv3";
   private static final String TLS_ONLY = "TLS";
   private static final String ALL_KEY = "ALL";
   private static final String SSL3_TLS = "SSL3_TLS";
   private static final String SSLv2Hello = "SSLv2Hello";
   public static final String DEFAULT_MIN_PROTOCOL = "TLSv1.2";
   private static final String[] SSL3_ONLY = new String[]{"SSLv3"};
   private static final String[] PROVIDER_TLS_SUPPORTED_PROTOCOLS = getSupportedTLSProtocolsFromProvider();
   private static final Map SUPPORTED_PROTOCOLS;
   private static final boolean disableNullCipher;
   private static boolean allowUnencryptedNullCipher;
   private static boolean sendEmptyCAList;
   private static volatile boolean x509BasicConstraintsStrict;
   private static volatile boolean noV1CAs;
   private static boolean anonymousCipherAllowed;
   public static final String JDK_TLS_CLIENT_PROTOCOLS = "jdk.tls.client.protocols";
   public static final boolean IS_JDK_TLS_CLIENT_PROTOCOLS_CONFIGURED;

   private static Map initSupportedProtocols(String[] allSupportedProtocols) {
      Map sps = new HashMap(4);
      sps.put("SSLv3", SSL3_ONLY);
      String[] allTLS = grabTLSProtocols(allSupportedProtocols);
      String[] ssl3_tls = new String[allTLS.length + 1];
      System.arraycopy(SSL3_ONLY, 0, ssl3_tls, 0, SSL3_ONLY.length);
      System.arraycopy(allTLS, 0, ssl3_tls, 1, allTLS.length);
      sps.put("TLS", allTLS);
      sps.put("SSL3_TLS", ssl3_tls);
      sps.put("ALL", allSupportedProtocols);
      return sps;
   }

   private static String[] getSupportedTLSProtocolsFromProvider() {
      String[] protocols = null;

      try {
         SSLContext sslContext = SSLContext.getInstance("TLSv1");
         sslContext.init((KeyManager[])null, (TrustManager[])null, (SecureRandom)null);
         protocols = sslContext.getSupportedSSLParameters().getProtocols();
         return protocols;
      } catch (KeyManagementException | NoSuchAlgorithmException var2) {
         throw new IllegalStateException("Failed to get the supported SSL/TLS protocols from the TLSv1 SSLContext: " + var2.getMessage());
      }
   }

   public static String[] getEnabledProtocols(String[] supportedProtocols, String minVersion, boolean isSSLv2HelloIncluded) {
      String[] protocols = null;
      String[] enabledProtocols = null;
      String localMinProtocol = null;
      if (null != minVersion && minVersion.trim().length() > 0 && supportedProtocols != null && supportedProtocols.length > 0) {
         protocols = SSLEnabledProtocolVersions.getJSSEProtocolVersions(minVersion, supportedProtocols, new SSLEnabledProtocolVersionsLogging());
         SSLSetupLogging.info("Using the MBean minimum SSL/TLS version: " + minVersion);
      } else {
         String sysPropertyMinVersion = SSLSetup.getMinimumProtocolVersion();
         if (sysPropertyMinVersion != null && sysPropertyMinVersion.trim().length() > 0) {
            protocols = SSLEnabledProtocolVersions.getJSSEProtocolVersions(sysPropertyMinVersion, supportedProtocols, new SSLEnabledProtocolVersionsLogging());
            SSLSetupLogging.info("Using the minimum SSL/TLS version: " + sysPropertyMinVersion + " from -Dweblogic.security.SSL.minimumProtocolVersion.");
         } else {
            int pV = SSLSetup.getLegacyProtocolVersion();
            String n = null;
            switch (pV) {
               case 0:
                  n = "TLS";
                  break;
               case 1:
                  n = "SSLv3";
                  break;
               case 2:
                  n = "SSL3_TLS";
                  break;
               case 3:
                  n = "ALL";
            }

            String protocolVersion = CommandLine.getCommandLine().getSSLVersion();
            if (n != null) {
               protocols = (String[])SUPPORTED_PROTOCOLS.get(n);
               SSLSetupLogging.info("Using SSL/TLS protocol version: " + protocolVersion + " from -Dweblogic.security.SSL.protocolVersion.");
            } else {
               localMinProtocol = "TLSv1.2";
               if (protocolVersion != null) {
                  SSLSetupLogging.info("Invalid SSL/TLS protocol version for -Dweblogic.security.SSL.protocolVersion: " + protocolVersion);
               }

               if (minVersion != null) {
                  SSLSetupLogging.info("Invalid minimum SSL/TLS parameter: " + minVersion);
               }

               SSLSetupLogging.info("Using TLSv1.2 as the default minimum TLS protocol.");
            }
         }
      }

      if (protocols == null && localMinProtocol != null) {
         protocols = SSLEnabledProtocolVersions.getJSSEProtocolVersions(localMinProtocol, supportedProtocols, new SSLEnabledProtocolVersionsLogging());
      }

      if (protocols != null) {
         enabledProtocols = protocols;
         if (isSSLv2HelloIncluded && containsSSLv2Hello(supportedProtocols) && !containsSSLv2Hello(protocols)) {
            enabledProtocols = new String[protocols.length + 1];
            System.arraycopy(protocols, 0, enabledProtocols, 0, protocols.length);
            enabledProtocols[protocols.length] = "SSLv2Hello";
         }
      }

      return enabledProtocols;
   }

   public static String[] getEnabledProtocols(String[] supportedProtocols, String minVersion) {
      return getEnabledProtocols(supportedProtocols, minVersion, false);
   }

   private static boolean containsSSLv2Hello(String[] protocols) {
      return containsElement(protocols, "SSLv2Hello");
   }

   private static boolean containsElement(String[] array, String strToFind) {
      boolean result = false;
      if (array != null && array.length > 0) {
         String[] var3 = array;
         int var4 = array.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            if (s != null && s.equalsIgnoreCase(strToFind)) {
               result = true;
               break;
            }
         }
      }

      return result;
   }

   public static String[] combineCiphers(String[] cs1, String[] cs2) {
      int size = 0;
      if (cs1 != null) {
         size += cs1.length;
      }

      if (cs2 != null) {
         size += cs2.length;
      }

      List result = new ArrayList(size);
      String[] t;
      int var5;
      int var6;
      String s;
      if (cs1 != null && cs1.length > 0) {
         t = cs1;
         var5 = cs1.length;

         for(var6 = 0; var6 < var5; ++var6) {
            s = t[var6];
            result.add(s);
         }
      }

      if (cs2 != null && cs2.length > 0) {
         t = cs2;
         var5 = cs2.length;

         for(var6 = 0; var6 < var5; ++var6) {
            s = t[var6];
            result.add(s);
         }
      }

      t = new String[result.size()];
      return (String[])result.toArray(t);
   }

   public static synchronized boolean isUnEncrytedNullCipherAllowed() {
      if (disableNullCipher) {
         if (allowUnencryptedNullCipher) {
            throw new IllegalArgumentException("Can not start SSL due to conflicting configuration - System configure parameter of weblogic.security.disableNullCipher = true, and configure parameter weblogic.security.ssl.allowUnencryptedNullCipher = true");
         } else {
            return false;
         }
      } else {
         return allowUnencryptedNullCipher;
      }
   }

   static boolean isAnonymousCipherAllowed() {
      return anonymousCipherAllowed;
   }

   public static synchronized void setSendEmptyCAList(boolean emptyCAList) {
      sendEmptyCAList = emptyCAList;
   }

   public static synchronized boolean isSendEmptyCAListEnabled() {
      return sendEmptyCAList;
   }

   public static PrivateKey getLocalIdentityPrivateKey(InputStream stream, char[] password) throws KeyManagementException {
      return RSAPKFactory.getPrivateKey(stream, password);
   }

   static byte[] readFully(InputStream stream) throws IOException {
      DataInputStream dis = new DataInputStream(stream);
      byte[] bytes = new byte[dis.available()];
      dis.readFully(bytes);
      return bytes;
   }

   static byte[] decodeData(String keyData) throws IOException {
      BASE64Decoder decoder = new BASE64Decoder();
      return decoder.decodeBuffer(keyData);
   }

   static void loadCerts(KeyStore ks, Certificate[] trustAnchors) {
      Certificate[] var2 = trustAnchors;
      int var3 = trustAnchors.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Certificate cert = var2[var4];

         try {
            ks.setCertificateEntry(cert.toString(), cert);
         } catch (KeyStoreException var7) {
            if (JaLogger.isLoggable(Level.SEVERE)) {
               JaLogger.log(Level.SEVERE, JaLogger.Component.TRUSTSTORE_MANAGER, var7, "Error loading CAs into trust KeyStore.");
            }
         }
      }

   }

   static void setX509BasicConstraintsStrict(boolean strict) {
      x509BasicConstraintsStrict = strict;
   }

   static boolean isX509BasicConstraintsStrict() {
      return x509BasicConstraintsStrict;
   }

   static void setNoV1CAs(boolean noV1CAs) {
      JaSSLSupport.noV1CAs = noV1CAs;
   }

   static boolean isNoV1CAs() {
      return noV1CAs;
   }

   private static String[] grabTLSProtocols(String[] allProtocols) {
      String[] tlsProtocols = new String[0];
      if (allProtocols != null && allProtocols.length > 0) {
         List l = new ArrayList();
         String[] var3 = allProtocols;
         int var4 = allProtocols.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            if (s.startsWith("TLS")) {
               l.add(s);
            }
         }

         if (l.size() > 0) {
            tlsProtocols = (String[])((String[])l.toArray(tlsProtocols));
         }
      }

      return tlsProtocols;
   }

   static ByteArrayOutputStream convertDER2PEM(InputStream is) {
      ByteArrayOutputStream os = new ByteArrayOutputStream();

      try {
         der2pem.convertEncryptedKey(is, os);
      } catch (IOException var3) {
         if (JaLogger.isLoggable(Level.SEVERE)) {
            JaLogger.log(Level.SEVERE, JaLogger.Component.SSLCONTEXT, var3, "Error converting a DER inputstream to PEM.");
         }
      }

      return os;
   }

   static {
      SUPPORTED_PROTOCOLS = initSupportedProtocols(PROVIDER_TLS_SUPPORTED_PROTOCOLS);
      disableNullCipher = Boolean.getBoolean("weblogic.security.disableNullCipher");
      allowUnencryptedNullCipher = Boolean.getBoolean("weblogic.ssl.AllowUnencryptedNullCipher");
      sendEmptyCAList = false;
      x509BasicConstraintsStrict = false;
      noV1CAs = false;
      anonymousCipherAllowed = Boolean.getBoolean("weblogic.security.SSL.AllowAnonymousCipher");
      IS_JDK_TLS_CLIENT_PROTOCOLS_CONFIGURED = System.getProperty("jdk.tls.client.protocols") != null;
   }
}
