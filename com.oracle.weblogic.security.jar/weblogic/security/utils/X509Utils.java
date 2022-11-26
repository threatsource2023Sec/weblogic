package weblogic.security.utils;

import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import weblogic.security.SecurityMessagesTextFormatter;

public class X509Utils {
   private static SecurityMessagesTextFormatter formatter = SecurityMessagesTextFormatter.getInstance();

   public static boolean isEmpty(CertPath certPath) {
      if (certPath != null) {
         List certs = certPath.getCertificates();
         if (certs != null && certs.size() > 0) {
            return false;
         }
      }

      return true;
   }

   public static boolean containsNonX509Certificate(CertPath certPath) {
      if (isEmpty(certPath)) {
         return false;
      } else {
         List certs = certPath.getCertificates();

         for(int i = 0; i < certs.size(); ++i) {
            Object o = certs.get(i);
            if (!(o instanceof X509Certificate)) {
               return true;
            }
         }

         return false;
      }
   }

   public static X509Certificate[] getCertificates(CertPath certPath) {
      if (isEmpty(certPath)) {
         return new X509Certificate[0];
      } else {
         List list = certPath.getCertificates();
         return (X509Certificate[])((X509Certificate[])list.toArray(new X509Certificate[list.size()]));
      }
   }

   public static void validateOrdered(CertPath certPath) throws CertificateException {
      if (!isEmpty(certPath)) {
         if (containsNonX509Certificate(certPath)) {
            throw new AssertionError("Received a cert path containing a non-X509 certificate");
         } else {
            X509Certificate[] certs = getCertificates(certPath);
            if (certs != null && certs.length >= 2) {
               for(int i = 0; i < certs.length - 1; ++i) {
                  if (isSelfSigned(certs[i])) {
                     throw new CertificateException(formatter.getSelfSignedCertificateInChainError(certs[i].toString()));
                  }

                  validateIssuedBy(certs[i], certs[i + 1]);
               }

            }
         }
      }
   }

   public static boolean isOrdered(CertPath certPath) {
      try {
         validateOrdered(certPath);
         return true;
      } catch (CertificateException var2) {
         return false;
      }
   }

   public static String getName(X500Principal principal) {
      return principal != null ? principal.getName("RFC2253") : null;
   }

   public static boolean sameX500Principal(X500Principal principal1, X500Principal principal2) {
      if (principal1 == null && principal2 == null) {
         return true;
      } else if (principal1 != null && principal2 == null) {
         return false;
      } else if (principal1 == null && principal2 != null) {
         return false;
      } else {
         String name1 = getName(principal1);
         String name2 = getName(principal2);
         return name1.equals(name2);
      }
   }

   public static String getSubjectDN(X509Certificate cert) {
      return getName(cert.getSubjectX500Principal());
   }

   public static String getIssuerDN(X509Certificate cert) {
      return getName(cert.getIssuerX500Principal());
   }

   public static void validateIssuedBy(X509Certificate issued, X509Certificate issuer) throws CertificateException {
      if (!getIssuerDN(issued).equals(getSubjectDN(issuer))) {
         throw new CertificateException(formatter.getIssuerDNMismatchError(issued.toString(), issuer.toString()));
      } else {
         try {
            issued.verify(issuer.getPublicKey());
         } catch (Exception var3) {
            throw new CertificateException(formatter.getCertificateNotSignedByIssuerError(issued.toString(), issuer.toString()));
         }
      }
   }

   public static boolean isIssuedBy(X509Certificate issued, X509Certificate issuer) {
      try {
         validateIssuedBy(issued, issuer);
         return true;
      } catch (CertificateException var3) {
         return false;
      }
   }

   public static boolean isSelfSigned(X509Certificate cert) {
      return isIssuedBy(cert, cert);
   }
}
