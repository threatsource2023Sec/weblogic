package weblogic.security.utils;

import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.net.ssl.SSLSocket;
import weblogic.kernel.Kernel;
import weblogic.logging.Loggable;
import weblogic.protocol.ServerChannel;
import weblogic.security.SecurityLogger;
import weblogic.security.SSL.CertPathTrustManager;
import weblogic.security.SSL.TrustManager;

public class SSLTrustValidator implements SSLTruster {
   private boolean peerCertsRequired;
   private boolean overrideAllowed;
   private TrustManager trustManager;
   private byte[][] rootCAFingerPrints;
   private String proxyHostName;
   private String urlHostName;

   public SSLTrustValidator() {
      this((ServerChannel)null);
   }

   public SSLTrustValidator(ServerChannel serverChannel) {
      this.peerCertsRequired = false;
      this.overrideAllowed = true;
      this.trustManager = null;
      this.rootCAFingerPrints = (byte[][])null;
      this.proxyHostName = null;
      this.urlHostName = null;
      if (Kernel.isServer()) {
         this.setTrustManager(new CertPathTrustManager(serverChannel));
      }

   }

   public void setTrustManager(TrustManager trustManager) {
      this.trustManager = trustManager;
   }

   public void setRootCAFingerPrints(byte[][] rootCAs) {
      this.rootCAFingerPrints = rootCAs;
   }

   public void setPeerCertsRequired(boolean required) {
      this.peerCertsRequired = required;
   }

   public boolean isPeerCertsRequired() {
      return this.peerCertsRequired;
   }

   public void setAllowOverride(boolean allowOverride) {
      this.overrideAllowed = allowOverride;
   }

   public void setProxyMapping(String proxyHostName, String urlHostName) {
      this.urlHostName = urlHostName;
      this.proxyHostName = proxyHostName;
   }

   public int validationCallback(X509Certificate[] chain, int validateErr, SSLSocket sslSocket, X509Certificate[] trustedCAs) {
      boolean info = SSLSetup.isDebugEnabled();
      int validationStatus = validateErr;
      if (info) {
         SSLSetup.info("validationCallback: validateErr = " + validateErr);
         if (chain != null) {
            for(int i = 0; i < chain.length; ++i) {
               SSLSetup.info("  cert[" + i + "] = " + chain[i]);
            }
         }
      }

      if ((validateErr & 16) != 0 && this.rootCAFingerPrints != null && chain != null && chain.length > 0) {
         try {
            byte[] certFPrints = SSLCertUtility.getFingerprint(chain[chain.length - 1]);

            for(int i = 0; i < this.rootCAFingerPrints.length; ++i) {
               if (Arrays.equals(certFPrints, this.rootCAFingerPrints[i])) {
                  validationStatus &= -21;
                  if (info) {
                     SSLSetup.info("Untrusted cert now trusted by legacy check");
                  }
                  break;
               }
            }
         } catch (CertificateEncodingException var14) {
            SSLSetup.debug(1, var14, "Error while getting encoded certificate during trust validation");
         } catch (NoSuchAlgorithmException var15) {
            SSLSetup.debug(1, var15, "Error getting certificate finger print.");
         }
      }

      if (chain == null || chain.length == 0) {
         if (this.peerCertsRequired) {
            if (info) {
               SSLSetup.info("Required peer certificates not supplied by peer");
            }

            validationStatus |= 4;
         } else {
            if (info) {
               SSLSetup.info("Peer certificates are not required and were not supplied by peer");
            }

            validationStatus = 0;
         }
      }

      if (this.trustManager != null) {
         TrustManagerEnvironment.push(trustedCAs, sslSocket);
         boolean trusted = false;

         try {
            trusted = this.trustManager.certificateCallback(chain, validationStatus);
         } finally {
            TrustManagerEnvironment.pop();
         }

         if (!trusted && validationStatus == 0) {
            validationStatus |= 32;
         }

         if (info) {
            SSLSetup.info("weblogic user specified trustmanager validation status " + validationStatus);
         }
      }

      if (validationStatus != 0) {
         this.logValidationError(validationStatus, sslSocket);
         if (!this.overrideAllowed) {
            if (info) {
               SSLSetup.info("User defined JSSE trustmanagers not allowed to override");
            }

            validationStatus |= 64;
         }
      }

      if (info) {
         SSLSetup.info("SSLTrustValidator returns: " + validationStatus);
      }

      return validationStatus;
   }

   private String getTrustManagerClassName() {
      return this.trustManager != null ? this.trustManager.getClass().getName() : null;
   }

   private String getPeerName(SSLSocket socket) {
      String peerName = SSLSetup.getPeerName(socket);
      if (this.proxyHostName != null && this.urlHostName != null) {
         InetAddress address = socket.getInetAddress();
         if (address != null && (this.proxyHostName.equals(address.getHostName()) || this.proxyHostName.equals(address.getHostAddress()))) {
            peerName = peerName + " --> " + this.urlHostName;
         }
      }

      return peerName;
   }

   private void logValidationError(int validationStatus, SSLSocket sslSocket) {
      if (SSLSetup.logSSLRejections()) {
         String peerName = this.getPeerName(sslSocket);
         Loggable[] messages = new Loggable[5];
         int size = 0;
         if ((validationStatus & 1) != 0) {
            messages[size++] = SecurityLogger.logHandshakeCertInvalidErrorLoggable(peerName);
         }

         if ((validationStatus & 2) != 0) {
            messages[size++] = SecurityLogger.logHandshakeCertExpiredErrorLoggable(peerName);
         }

         if ((validationStatus & 4) != 0) {
            messages[size++] = SSLSetup.isFatClient() ? SecurityLogger.logFatClientHandshakeCertIncompleteErrorLoggable(peerName) : SecurityLogger.logHandshakeCertIncompleteErrorLoggable(peerName);
         }

         if ((validationStatus & 16) != 0) {
            messages[size++] = SSLSetup.isFatClient() ? SecurityLogger.logFatClientHandshakeCertUntrustedErrorLoggable(peerName) : SecurityLogger.logHandshakeCertUntrustedErrorLoggable(peerName);
         }

         if ((validationStatus & 32) != 0) {
            messages[size++] = SSLSetup.isFatClient() ? SecurityLogger.logFatClientHandshakeCertValidationErrorLoggable(peerName, this.getTrustManagerClassName()) : SecurityLogger.logHandshakeCertValidationErrorLoggable(peerName, this.getTrustManagerClassName());
         }

         if (size > 0) {
            StringBuffer details = null;
            if (sslSocket != null) {
               details = new StringBuffer();
            }

            for(int i = 0; i < size; ++i) {
               messages[i].log();
               if (sslSocket != null) {
                  if (i > 0) {
                     details.append(", ");
                  }

                  details.append(messages[i].getMessage());
               }
            }

            if (sslSocket != null) {
               SSLSetup.setFailureDetails(sslSocket.getSession(), details.toString());
            }
         }
      }

      if (SSLSetup.isDebugEnabled()) {
         SSLSetup.info("Validation error = " + validationStatus);
         if ((validationStatus & 1) != 0) {
            SSLSetup.info("Certificate chain is invalid");
         }

         if ((validationStatus & 2) != 0) {
            SSLSetup.info("Expired certificate");
         }

         if ((validationStatus & 4) != 0) {
            SSLSetup.info("Certificate chain is incomplete");
         }

         if ((validationStatus & 16) != 0) {
            SSLSetup.info("Certificate chain is untrusted");
         }

         if ((validationStatus & 32) != 0) {
            SSLSetup.info("Certificate chain was not validated by the custom trust manager even though built-in SSL validated it.");
         }
      }

   }
}
