package weblogic.security.SSL.jsseadapter;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import javax.net.ssl.CertPathTrustManagerParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import weblogic.kernel.Kernel;
import weblogic.security.pki.revocation.common.RevocationCertPathChecker;
import weblogic.security.pki.revocation.wls.WlsCertRevocContext;
import weblogic.security.utils.SSLSetup;

class JaTrustManager implements X509TrustManager {
   private final X509Certificate[] trustedCAs;
   private final Set trustAnchors;
   private X509TrustManager xTm;
   private static final String ID_CE_BASIC_CONSTRAINTS = "2.5.29.19";
   private static final String ID_CE_KEY_USAGE = "2.5.29.15";
   private static final int CERT_X509_V1 = 1;
   private static final int CERT_X509_V3 = 3;

   JaTrustManager(X509Certificate[] certs) {
      this.trustedCAs = this.copyCerts(certs);
      this.trustAnchors = Collections.unmodifiableSet(createTrustAnchors(certs));
      TrustManagerFactory tmf = null;

      try {
         tmf = TrustManagerFactory.getInstance("PKIX");
         if (Kernel.isServer()) {
            CertSelector certSel = new X509CertSelector();
            PKIXBuilderParameters cpParams = new PKIXBuilderParameters(this.trustAnchors, certSel);
            cpParams.setRevocationEnabled(false);
            Set trustedCertsAsSet = this.toUnmodifiableSet(this.trustedCAs);
            WlsCertRevocContext revocContext = new WlsCertRevocContext(trustedCertsAsSet);
            RevocationCertPathChecker revocChecker = RevocationCertPathChecker.getInstance(revocContext);
            cpParams.addCertPathChecker(revocChecker);
            CertPathTrustManagerParameters cptmp = new CertPathTrustManagerParameters(cpParams);
            tmf.init(cptmp);
         } else {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load((InputStream)null, (char[])null);
            loadCerts(trustStore, this.trustedCAs);
            tmf.init(trustStore);
         }
      } catch (Exception var9) {
         if (JaLogger.isLoggable(Level.WARNING)) {
            JaLogger.log(Level.WARNING, JaLogger.Component.TRUSTSTORE_MANAGER, var9, "Error initializing trust manager factory: {0}.", var9.getMessage());
         }
      }

      if (tmf != null) {
         TrustManager[] tms = tmf.getTrustManagers();
         TrustManager[] var13 = tms;
         int var14 = tms.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            TrustManager t = var13[var15];
            if (t instanceof X509TrustManager) {
               this.xTm = (X509TrustManager)t;
               break;
            }
         }
      }

      if (null == this.xTm) {
         String msg = "Unable to determine TrustManager.";
         if (JaLogger.isLoggable(Level.WARNING)) {
            JaLogger.log(Level.WARNING, JaLogger.Component.TRUSTSTORE_MANAGER, "Unable to determine TrustManager.");
         }

         throw new IllegalStateException("Unable to determine TrustManager.");
      }
   }

   public void checkClientTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
      this.xTm.checkClientTrusted(x509Certificates, authType);
      this.checkCertPath(x509Certificates);
   }

   public void checkServerTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
      this.xTm.checkServerTrusted(x509Certificates, authType);
      this.checkKeyUsage(x509Certificates);
      this.checkCertPath(x509Certificates);
   }

   public X509Certificate[] getAcceptedIssuers() {
      X509Certificate[] certs;
      if (!Boolean.getBoolean("weblogic.security.SSL.sendEmptyCAList")) {
         certs = this.copyCerts(this.trustedCAs);
      } else {
         certs = new X509Certificate[0];
      }

      return certs;
   }

   void checkCertPath(X509Certificate[] peerCerts) throws CertificateException {
      if ((null == peerCerts || peerCerts.length <= 0) && JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.TRUSTSTORE_MANAGER, "Empty peer certificate chain.");
      }

      PKIXCertPathBuilderResult result = this.buildPKIXCertPath(peerCerts);
      if (this.hasCertPath(result)) {
         String errMsg;
         if (JaSSLSupport.isNoV1CAs() && this.hasV1CAs(result)) {
            errMsg = "The certificate path has a version 1 CA certificate. Version 1 CA certificates are disallowed.";
            if (JaLogger.isLoggable(Level.WARNING)) {
               JaLogger.log(Level.WARNING, JaLogger.Component.TRUSTSTORE_MANAGER, "The certificate path has a version 1 CA certificate. Version 1 CA certificates are disallowed.");
            }

            throw new CertificateException("The certificate path has a version 1 CA certificate. Version 1 CA certificates are disallowed.");
         }

         if (JaSSLSupport.isX509BasicConstraintsStrict() && !this.isBasicConstraintsExtensionMarkedCritical(result)) {
            errMsg = "The Basic Constraints extension of at least one of the version 3 CA certificates in the chain is not marked critical.  This is being rejected due to the strict enforcement of Basic Constraints.";
            if (JaLogger.isLoggable(Level.WARNING)) {
               JaLogger.log(Level.WARNING, JaLogger.Component.TRUSTSTORE_MANAGER, "The Basic Constraints extension of at least one of the version 3 CA certificates in the chain is not marked critical.  This is being rejected due to the strict enforcement of Basic Constraints.");
            }

            throw new CertificateException("The Basic Constraints extension of at least one of the version 3 CA certificates in the chain is not marked critical.  This is being rejected due to the strict enforcement of Basic Constraints.");
         }
      } else if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.TRUSTSTORE_MANAGER, "Additional cert path checks encountered empty cert path.");
      }

   }

   void checkKeyUsage(X509Certificate[] peerCertChain) throws CertificateException {
      if (peerCertChain != null && peerCertChain.length > 0) {
         boolean badKU = false;

         for(int i = 0; i < peerCertChain.length; ++i) {
            Set OIDs = peerCertChain[i].getCriticalExtensionOIDs();
            if (isElementFound(OIDs, "2.5.29.15")) {
               boolean[] keyUsage = peerCertChain[i].getKeyUsage();
               if (keyUsage != null) {
                  String keyAlg = peerCertChain[i].getPublicKey().getAlgorithm();
                  if (keyAlg != null && keyAlg.equalsIgnoreCase("RSA")) {
                     if (i == 0) {
                        if (!keyUsage[0] && !keyUsage[1] && !keyUsage[2] && !keyUsage[3]) {
                           badKU = true;
                           break;
                        }
                     } else if (!keyUsage[0] && !keyUsage[1] && !keyUsage[2] && !keyUsage[3] && !keyUsage[5] && !keyUsage[6]) {
                        badKU = true;
                        break;
                     }
                  }
               }
            }
         }

         if (badKU) {
            SSLSetup.logCertificateChainAlgKeyUsageFailure((SSLSocket)null);
            throw new CertificateException("The peer certificate's KeyUsage constraints forbid its key use by the key agreement algorithm.");
         }
      }

   }

   private static void loadCerts(KeyStore ks, Certificate[] trustAnchors) {
      int i = 0;
      Certificate[] var3 = trustAnchors;
      int var4 = trustAnchors.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Certificate cert = var3[var5];

         try {
            if (null == cert) {
               if (JaLogger.isLoggable(Level.FINEST)) {
                  JaLogger.log(Level.FINEST, JaLogger.Component.TRUSTSTORE_MANAGER, "Null trusted certificate encountered.");
               }
            } else {
               ks.setCertificateEntry(Integer.toString(i), cert);
               ++i;
            }
         } catch (KeyStoreException var8) {
            if (JaLogger.isLoggable(Level.WARNING)) {
               JaLogger.log(Level.WARNING, JaLogger.Component.TRUSTSTORE_MANAGER, var8, "Unable to add certificate to keystore: cert={0}, message={1}.", cert.toString(), var8.getMessage());
            }
         }
      }

   }

   boolean isBasicConstraintsExtensionMarkedCritical(PKIXCertPathBuilderResult result) {
      CertPath certPath = result.getCertPath();
      boolean first = true;
      Iterator var4 = certPath.getCertificates().iterator();

      while(var4.hasNext()) {
         Certificate cert = (Certificate)var4.next();
         if (first) {
            first = false;
         } else if (!(cert instanceof X509Certificate)) {
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.TRUSTSTORE_MANAGER, "Critical Basic Constraint Extensions check skipping non-X509Certificate instance: {0}", cert);
            }
         } else {
            X509Certificate x509cert = (X509Certificate)cert;
            if (x509cert.getVersion() != 3) {
               if (JaLogger.isLoggable(Level.FINER)) {
                  JaLogger.log(Level.FINER, JaLogger.Component.TRUSTSTORE_MANAGER, "Checking for Critical Basic Constraint Extensions, skipping non-v3 cert: Version={0}, SubjectDN={1}.", x509cert.getVersion(), x509cert.getSubjectDN().toString());
               }
            } else {
               Set OIDs = x509cert.getCriticalExtensionOIDs();
               if (!isElementFound(OIDs, "2.5.29.19")) {
                  if (JaLogger.isLoggable(Level.FINE)) {
                     JaLogger.log(Level.FINE, JaLogger.Component.TRUSTSTORE_MANAGER, "Found v3 cert without critical BasicConstraints extension: {0}", x509cert.getSubjectDN().toString());
                  }

                  return false;
               }
            }
         }
      }

      TrustAnchor trustAnchor = result.getTrustAnchor();
      X509Certificate anchorCert = trustAnchor.getTrustedCert();
      if (anchorCert.getVersion() != 3) {
         if (JaLogger.isLoggable(Level.FINER)) {
            JaLogger.log(Level.FINER, JaLogger.Component.TRUSTSTORE_MANAGER, "Checking for Critical Basic Constraint Extensions, skipping non-v3 anchor cert: Version={0}, SubjectDN={1}.", anchorCert.getVersion(), anchorCert.getSubjectDN().toString());
         }

         return true;
      } else {
         Set OIDs = anchorCert.getCriticalExtensionOIDs();
         if (!isElementFound(OIDs, "2.5.29.19")) {
            if (JaLogger.isLoggable(Level.FINE)) {
               JaLogger.log(Level.FINE, JaLogger.Component.TRUSTSTORE_MANAGER, "Found v3 anchor cert without critical BasicConstraints extension: {0}", anchorCert.getSubjectDN().toString());
            }

            return false;
         } else {
            return true;
         }
      }
   }

   boolean hasV1CAs(PKIXCertPathBuilderResult result) {
      CertPath certPath = result.getCertPath();
      boolean first = true;
      Iterator var4 = certPath.getCertificates().iterator();

      while(var4.hasNext()) {
         Certificate cert = (Certificate)var4.next();
         if (first) {
            first = false;
         } else if (!(cert instanceof X509Certificate)) {
            if (JaLogger.isLoggable(Level.FINEST)) {
               JaLogger.log(Level.FINEST, JaLogger.Component.TRUSTSTORE_MANAGER, "V1 CA certificate check skipping non-X509Certificate instance: {0}", cert);
            }
         } else {
            X509Certificate x509cert = (X509Certificate)cert;
            if (x509cert.getVersion() == 1) {
               if (JaLogger.isLoggable(Level.FINE)) {
                  JaLogger.log(Level.FINE, JaLogger.Component.TRUSTSTORE_MANAGER, "Found version 1 certificate: {0}", x509cert.getSubjectDN().toString());
               }

               return true;
            }
         }
      }

      TrustAnchor trustAnchor = result.getTrustAnchor();
      X509Certificate anchorCert = trustAnchor.getTrustedCert();
      if (anchorCert.getVersion() == 1) {
         if (JaLogger.isLoggable(Level.FINE)) {
            JaLogger.log(Level.FINE, JaLogger.Component.TRUSTSTORE_MANAGER, "Found version 1 anchor certificate: {0}", anchorCert.getSubjectDN().toString());
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean isElementFound(Set arr, String element) {
      if (arr != null && element != null && arr.size() > 0) {
         Iterator var2 = arr.iterator();

         while(var2.hasNext()) {
            String s = (String)var2.next();
            if (element.equalsIgnoreCase(s)) {
               return true;
            }
         }
      }

      return false;
   }

   static Set createTrustAnchors(X509Certificate[] trustedCAs) {
      Set trustAnchors = new HashSet();
      if (null != trustedCAs && trustedCAs.length > 0) {
         X509Certificate[] var2 = trustedCAs;
         int var3 = trustedCAs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            X509Certificate cert = var2[var4];
            if (null == cert) {
               if (JaLogger.isLoggable(Level.FINEST)) {
                  JaLogger.log(Level.FINEST, JaLogger.Component.TRUSTSTORE_MANAGER, "Null certificate encountered while populating trust anchors.");
               }
            } else {
               trustAnchors.add(new TrustAnchor(cert, (byte[])null));
            }
         }

         return trustAnchors;
      } else {
         if (JaLogger.isLoggable(Level.WARNING)) {
            JaLogger.log(Level.WARNING, JaLogger.Component.TRUSTSTORE_MANAGER, "No trusted CAs available to populate trust anchors.");
         }

         return trustAnchors;
      }
   }

   PKIXCertPathBuilderResult buildPKIXCertPath(X509Certificate[] peerCerts) throws CertificateException {
      try {
         X509CertSelector targetConstraints = new X509CertSelector();
         targetConstraints.setSubject(peerCerts[0].getSubjectX500Principal());
         PKIXBuilderParameters cpParams = new PKIXBuilderParameters(this.trustAnchors, targetConstraints);
         cpParams.addCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(Arrays.asList(peerCerts))));
         cpParams.setRevocationEnabled(false);
         CertPathBuilder cpBuilder = CertPathBuilder.getInstance("PKIX");
         PKIXCertPathBuilderResult result = (PKIXCertPathBuilderResult)cpBuilder.build(cpParams);
         return result;
      } catch (Exception var6) {
         if (JaLogger.isLoggable(Level.WARNING)) {
            JaLogger.log(Level.WARNING, JaLogger.Component.TRUSTSTORE_MANAGER, var6, "Error using PKIX CertPathBuilder.");
         }

         throw new IllegalStateException("Error using PKIX CertPathBuilder.", var6);
      }
   }

   boolean hasCertPath(PKIXCertPathBuilderResult result) {
      if (null == result) {
         throw new IllegalArgumentException("Expected non-null PKIXCertPathBuilderResult.");
      } else if (result.getCertPath() != null && result.getCertPath().getCertificates() != null && result.getCertPath().getCertificates().size() > 0) {
         return true;
      } else {
         return result.getTrustAnchor() != null && result.getTrustAnchor().getTrustedCert() != null;
      }
   }

   X509Certificate[] copyCerts(X509Certificate[] certs) {
      if (null != certs && certs.length > 0) {
         X509Certificate[] output = new X509Certificate[certs.length];
         System.arraycopy(certs, 0, output, 0, certs.length);
         return output;
      } else {
         if (JaLogger.isLoggable(Level.FINEST)) {
            JaLogger.log(Level.FINEST, JaLogger.Component.TRUSTSTORE_MANAGER, "No certs to copy.");
         }

         return new X509Certificate[0];
      }
   }

   private Set toUnmodifiableSet(X509Certificate[] certs) {
      if (null == certs) {
         certs = new X509Certificate[0];
      }

      return Collections.unmodifiableSet(new HashSet(Arrays.asList(certs)));
   }
}
