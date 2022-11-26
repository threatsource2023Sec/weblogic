package weblogic.security.pki.revocation.common;

import com.rsa.certj.CertJVersion;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.jsafe.CryptoJ;
import com.rsa.jsafe.JSAFE_PrivateKey;
import java.security.PrivateKey;
import java.util.logging.Level;

final class RsaUtil {
   private static final String DEVICE_NAME = "Java";

   static String getCryptoJDeviceList() {
      return "Java";
   }

   static boolean isFIPS140UsageOk(LogListener logger) {
      if (CertJVersion.isFIPS140Compliant()) {
         if (!CryptoJ.selfTestPassed()) {
            if (null != logger && logger.isLoggable(Level.FINE)) {
               logger.log(Level.FINE, "Using FIPS 140 compliant implementation, however self-tests failed.");
            }

            return false;
         }

         if (null != logger && logger.isLoggable(Level.FINEST)) {
            logger.log(Level.FINEST, "Using FIPS 140 compliant implementation, self-tests passed.");
         }
      } else if (null != logger && logger.isLoggable(Level.FINEST)) {
         logger.log(Level.FINEST, "Not using FIPS 140 compliant implementation.");
      }

      return true;
   }

   static X509Certificate toRsaCert(java.security.cert.X509Certificate cert, LogListener logger) {
      Util.checkNotNull("java.security.cert.X509Certificate", cert);

      try {
         int offset = 0;
         int special = 0;
         return new X509Certificate(cert.getEncoded(), offset, special);
      } catch (Exception var4) {
         if (null != logger && logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, var4, "Unable to convert java.security.cert.X509Certificate {0}.", cert);
         }

         return null;
      }
   }

   static JSAFE_PrivateKey toRsaPrivateKey(PrivateKey key, LogListener logger) {
      Util.checkNotNull("java.security.PrivateKey", key);

      try {
         int offset = 0;
         String device = getCryptoJDeviceList();
         return JSAFE_PrivateKey.getInstance(key.getEncoded(), offset, device);
      } catch (Exception var4) {
         if (null != logger && logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, var4, "Unable to convert java.security.PrivateKey.");
         }

         return null;
      }
   }

   static Boolean evalRevocStatusCode(CertRevocCheckMethodList.SelectableMethod method, int statusCode, LogListener logger) {
      Util.checkNotNull("SelectableMethod", method);
      switch (statusCode) {
         case 0:
            return Boolean.FALSE;
         case 1:
            return Boolean.TRUE;
         case 2:
            if (null != logger && logger.isLoggable(Level.FINEST)) {
               logger.log(Level.FINEST, "Revocation status unknown from {0}.", method);
            }

            return null;
         default:
            if (null != logger && logger.isLoggable(Level.FINE)) {
               logger.log(Level.FINE, "Revocation status unknown from {0}, unknown revocation status code {1}.", method, statusCode);
            }

            return null;
      }
   }

   static X509V3Extension getExtension(X509CRL crl, int type) {
      return crl == null ? null : getExtension(crl.getExtensions(), type);
   }

   static X509V3Extension getExtension(X509Certificate cert, int type) {
      return cert == null ? null : getExtension(cert.getExtensions(), type);
   }

   static X509V3Extension getExtension(X509V3Extensions extensions, int type) {
      X509V3Extension result = null;
      if (extensions != null) {
         try {
            result = extensions.getExtensionByType(type);
         } catch (CertificateException var4) {
         }
      }

      return result;
   }
}
