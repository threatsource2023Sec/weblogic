package weblogic.management.configuration;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.management.InvalidAttributeValueException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import weblogic.logging.Loggable;
import weblogic.security.SecurityLogger;
import weblogic.security.utils.SSLSetupLogging;
import weblogic.utils.Debug;

public final class ServerLegalHelper {
   public static final int DEFAULT_THREAD_POOL_SIZE = 15;
   public static final int PRODUCTION_MODE_THREAD_POOL_SIZE = 25;
   public static final String TLSv1_2 = "TLSv1.2";
   public static final String[] SUPPORTED_PROTOCOLS;
   private static String minimumTLSProtocolFromSysProperty = System.getProperty("weblogic.security.SSL.minimumProtocolVersion");
   private static final String[] DATA_ENC_ALGORITHMS = new String[]{"aes128-cbc", "aes192-cbc", "aes256-cbc", "tripledes-cbc", "aes128-gcm", "aes192-gcm", "aes256-gcm"};
   private static final String[] KEY_ENC_ALGORITHMS = new String[]{"rsa-oaep", "rsa-1_5", "rsa-oaep-mgf1p"};
   private static final String[] ALL_ALGORITHMS = new String[]{"aes128-cbc", "aes192-cbc", "aes256-cbc", "tripledes-cbc", "aes128-gcm", "aes192-gcm", "aes256-gcm", "rsa-1_5", "rsa-oaep-mgf1p", "rsa-oaep"};
   public static final String LEGACY_PROTOCOL_PATTERN = "(?i)ALL|SSL3|TLS1";
   private static final boolean IS_LEGACY_PROTOCOL_VERSION_CONFIGURED = System.getProperty("weblogic.security.SSL.protocolVersion") != null && System.getProperty("weblogic.security.SSL.protocolVersion").matches("(?i)ALL|SSL3|TLS1");
   private static final String[] DEFAULT_EXCLUDED_CIPHER_SUITES = new String[]{"TLS_RSA_*", "*_CBC_*"};

   public static boolean isSSLListenPortEnabled(ServerTemplateMBean self) {
      if (Boolean.getBoolean("weblogic.mbeanLegalClause.ByPass")) {
         return true;
      } else if (self.getSSL().isEnabled()) {
         return true;
      } else {
         Debug.assertion(self.getParent() != null);
         if (((DomainMBean)self.getParent()).isAdministrationPortEnabled()) {
            return true;
         } else {
            NetworkAccessPointMBean[] naps = self.getNetworkAccessPoints();

            for(int i = 0; i < naps.length; ++i) {
               if (naps[i].isEnabled()) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public static boolean isListenPortEnabled(SSLMBean self) {
      if (Boolean.getBoolean("weblogic.mbeanLegalClause.ByPass")) {
         return true;
      } else {
         ServerTemplateMBean server = (ServerTemplateMBean)self.getParent();
         Debug.assertion(server != null);
         return server.isListenPortEnabled() ? true : isSSLListenPortEnabled(server);
      }
   }

   public static void validateSSL(SSLMBean ssl) throws IllegalArgumentException {
      if (!ssl.isEnabled() && !isListenPortEnabled(ssl)) {
         throw new IllegalArgumentException("Either ListenPort or SSLListenPort must be enabled");
      } else {
         if (ssl.isEnabled()) {
            ServerTemplateMBean server = (ServerTemplateMBean)ssl.getParent();
            if (server != null) {
               int port = server.getListenPort();
               if (port == ssl.getListenPort()) {
                  Loggable l = SecurityLogger.logSSLListenPortSameAsServerListenPortLoggable(Integer.toString(port));
                  l.log();
                  throw new IllegalArgumentException(l.getMessage());
               }
            }
         }

      }
   }

   public static void validateServer(ServerTemplateMBean server) throws IllegalArgumentException {
      if (!server.isListenPortEnabled() && !isSSLListenPortEnabled(server)) {
         throw new IllegalArgumentException("Either ListenPort or SSLListenPort must be enabled");
      } else {
         String serverName = server.getName();

         try {
            if (!LegalHelper.serverMBeanSetNameLegalCheck(serverName, server)) {
               throw new IllegalArgumentException("ServerName " + serverName + " is invalid");
            }
         } catch (InvalidAttributeValueException var4) {
            throw new IllegalArgumentException(var4.getMessage());
         }

         CoherenceClusterSystemResourceMBean cohMBean = server.getCoherenceClusterSystemResource();
         if (cohMBean != null) {
            ClusterMBean cluster = server.getCluster();
            if (cluster != null && cluster.getCoherenceClusterSystemResource() != null && !cluster.getCoherenceClusterSystemResource().getName().equals(cohMBean.getName())) {
               throw new IllegalArgumentException("Server " + serverName + " is part of WLS Cluster " + cluster.getName() + " which is part of Coherence cluster " + cluster.getCoherenceClusterSystemResource().getName() + ". So Server cannot be part of another Coherence cluster " + cohMBean.getName());
            }
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public static void checkListenAddress(ServerMBean self, Object value) throws InvalidAttributeValueException {
      if (value == null) {
         throw new InvalidAttributeValueException("null port");
      } else if (!(value instanceof Integer)) {
         throw new InvalidAttributeValueException("port not integer:" + value);
      } else {
         int port = (Integer)value;
         if (self.getSSL().getListenPort() == port) {
            throw new InvalidAttributeValueException("Listen port cannot be the same as SSL port");
         }
      }
   }

   public static void validateFederationServices(FederationServicesMBean fed) throws IllegalArgumentException {
   }

   public static void validateSingleSignOnServices(SingleSignOnServicesMBean sso) throws IllegalArgumentException {
   }

   public static void validateMinimumSSLProtocol(String minimumSSLProtocol) throws InvalidAttributeValueException {
      boolean isProtocolSupported = false;
      if (minimumSSLProtocol != null && minimumSSLProtocol.trim().length() > 0) {
         if (SUPPORTED_PROTOCOLS == null || SUPPORTED_PROTOCOLS.length <= 0) {
            throw new IllegalStateException("Cannot obtain the supported protocols by the configured JSSE provider.");
         }

         String[] var2 = SUPPORTED_PROTOCOLS;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String p = var2[var4];
            if (p.equalsIgnoreCase(minimumSSLProtocol)) {
               isProtocolSupported = true;
               if (p.startsWith("SSLv2")) {
                  throw new InvalidAttributeValueException("Minimum protocol version must be SSLv3 or higher.  TLSv1.2 or later is recommended.");
               }

               if (p.equalsIgnoreCase("TLSv1.1") || p.equalsIgnoreCase("TLSv1") || p.equalsIgnoreCase("SSLv3")) {
                  SSLSetupLogging.info("TLSv1.2 is recommended as the minimum TLS protocol.");
               }
               break;
            }
         }
      }

      if (!isProtocolSupported) {
         throw new InvalidAttributeValueException("SSL/TLS protocol [" + minimumSSLProtocol + "] is not valid.");
      }
   }

   public static String getDerivedMinimumTLSProtocol() {
      return minimumTLSProtocolFromSysProperty;
   }

   public static String[] getDerivedExcludedCiphersuites(String minimumProtocolVersion) {
      return (minimumProtocolVersion != null && minimumProtocolVersion.trim().length() != 0 || IS_LEGACY_PROTOCOL_VERSION_CONFIGURED) && !"TLSv1.2".equalsIgnoreCase(minimumProtocolVersion) ? null : DEFAULT_EXCLUDED_CIPHER_SUITES;
   }

   public static void validateSAMLDataEncryptionAlgorithm(String dataEncryptionAlgorithm) throws InvalidAttributeValueException {
      boolean valid = false;
      if (dataEncryptionAlgorithm != null) {
         String[] var2 = DATA_ENC_ALGORITHMS;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String alg = var2[var4];
            if (alg.equalsIgnoreCase(dataEncryptionAlgorithm)) {
               valid = true;
               break;
            }
         }
      }

      if (!valid) {
         throw new InvalidAttributeValueException(dataEncryptionAlgorithm + " is not a valid XML Security data Encryption algorithm. ");
      }
   }

   public static void validateSAMLKeyEncryptionAlgorithm(String keyEncryptionAlgorithm) throws InvalidAttributeValueException {
      boolean valid = false;
      if (keyEncryptionAlgorithm != null) {
         String[] var2 = KEY_ENC_ALGORITHMS;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String alg = var2[var4];
            if (alg.equalsIgnoreCase(keyEncryptionAlgorithm)) {
               valid = true;
               break;
            }
         }
      }

      if (!valid) {
         throw new InvalidAttributeValueException(keyEncryptionAlgorithm + " is not a valid XML Security key Encryption algorithm. ");
      }
   }

   public static void validateAlgorithms(String[] algorithms) throws InvalidAttributeValueException {
      if (algorithms != null && algorithms.length != 0) {
         String[] var1 = algorithms;
         int var2 = algorithms.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            boolean currentAlgFound = false;
            String[] var6 = ALL_ALGORITHMS;
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String dataAlgorithm = var6[var8];
               if (s.equals(dataAlgorithm)) {
                  currentAlgFound = true;
                  break;
               }
            }

            if (!currentAlgFound) {
               throw new InvalidAttributeValueException(s + " algorithm is not supported.");
            }
         }

      } else {
         throw new InvalidAttributeValueException("Data and key encryption algorithm list cannot be null or empty.");
      }
   }

   static {
      try {
         SSLContext sslContext = SSLContext.getInstance("TLSv1");
         sslContext.init((KeyManager[])null, (TrustManager[])null, (SecureRandom)null);
         SUPPORTED_PROTOCOLS = sslContext.getSupportedSSLParameters().getProtocols();
      } catch (KeyManagementException | NoSuchAlgorithmException var1) {
         throw new IllegalStateException("Failed to get the supported SSL/TLS protocols from the default SSLContext: " + var1.getMessage());
      }
   }
}
