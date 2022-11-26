package com.bea.common.security.internal.utils;

import com.bea.common.security.internal.service.ServiceLogger;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;

public class X509Utils {
   public static boolean isEmpty(CertPath certPath) {
      return certPath == null || certPath.getCertificates().size() == 0;
   }

   public static void validateOrdered(CertPath certPath) throws CertificateException {
      if (!certPath.getType().equals("X.509")) {
         throw new IllegalArgumentException(ServiceLogger.getNonX509CertPath());
      } else {
         Iterator certs = certPath.getCertificates().iterator();
         X509Certificate issuerCert;
         if (certs.hasNext()) {
            for(X509Certificate cert = (X509Certificate)certs.next(); certs.hasNext(); cert = issuerCert) {
               if (isSelfSigned(cert)) {
                  throw new CertificateException(ServiceLogger.getSelfSignedCertificateInChain(cert.toString()));
               }

               issuerCert = (X509Certificate)certs.next();
               validateIssuedBy(cert, issuerCert);
            }
         }

      }
   }

   public static void validateIssuedBy(X509Certificate issued, X509Certificate issuer) throws CertificateException {
      if (!issued.getIssuerX500Principal().equals(issuer.getSubjectX500Principal())) {
         throw new CertificateException(ServiceLogger.getIssuerDNMismatch(issued.toString(), issuer.toString()));
      } else {
         try {
            issued.verify(issuer.getPublicKey());
         } catch (Exception var3) {
            throw new CertificateException(ServiceLogger.getCertificateNotSignedByIssuer(issued.toString(), issuer.toString()));
         }
      }
   }

   public static boolean isIssuedBy(X509Certificate issued, X509Certificate issuer) {
      if (!issued.getIssuerX500Principal().equals(issuer.getSubjectX500Principal())) {
         return false;
      } else {
         try {
            issued.verify(issuer.getPublicKey());
            return true;
         } catch (Exception var3) {
            return false;
         }
      }
   }

   public static boolean isSelfSigned(X509Certificate cert) {
      return isIssuedBy(cert, cert);
   }
}
