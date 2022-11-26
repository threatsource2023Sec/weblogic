package weblogic.management.configuration;

import java.math.BigInteger;
import java.net.URI;
import javax.security.auth.x500.X500Principal;

public class CertRevocValidator {
   public static void validateCertRevoc(CertRevocMBean certRevoc) throws IllegalArgumentException {
      validateUniqueDn(certRevoc);
   }

   private static void validateUniqueDn(CertRevocMBean certRevoc) throws IllegalArgumentException {
      CertRevocCaMBean[] children = certRevoc.getCertRevocCas();
      if (null != children) {
         CertRevocCaMBean[] var2 = children;
         int var3 = children.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            CertRevocCaMBean s = var2[var4];
            if (null != s) {
               X500Principal dn = tryGetX500Principal(s);
               if (null != dn) {
                  CertRevocCaMBean[] var7 = children;
                  int var8 = children.length;

                  for(int var9 = 0; var9 < var8; ++var9) {
                     CertRevocCaMBean s2 = var7[var9];
                     if (null != s2 && s != s2) {
                        X500Principal dn2 = tryGetX500Principal(s2);
                        if (null != dn2 && dn.equals(dn2)) {
                           throw new IllegalArgumentException("Illegal duplicate distinguished name: " + dn.getName());
                        }
                     }
                  }
               }
            }
         }

      }
   }

   private static X500Principal tryGetX500Principal(CertRevocCaMBean s) {
      X500Principal dn = null;

      try {
         dn = new X500Principal(s.getDistinguishedName());
      } catch (Exception var3) {
      }

      return dn;
   }

   public static void validatePort(int value) {
      if (value != -1 && (value < 1 || value > 65535)) {
         throw new IllegalArgumentException("Illegal value for port: " + value);
      }
   }

   public static void validateX500PrincipalDN(String value) {
      if (null != value) {
         if (value.length() == 0) {
            throw new IllegalArgumentException("Illegal value for distinguished name: " + value);
         } else {
            try {
               new X500Principal(value);
            } catch (Exception var2) {
               throw new IllegalArgumentException("Illegal value for distinguished name: " + value, var2);
            }
         }
      }
   }

   public static void validateURL(String value) {
      if (null != value) {
         if (value.length() == 0) {
            throw new IllegalArgumentException("Illegal value for URL: " + value);
         } else {
            URI uri;
            try {
               uri = new URI(value);
            } catch (Exception var3) {
               throw new IllegalArgumentException("Illegal value for URL: " + value, var3);
            }

            if (!uri.isAbsolute()) {
               throw new IllegalArgumentException("Illegal value for URL, must be absolute: " + value);
            } else if (uri.isOpaque()) {
               throw new IllegalArgumentException("Illegal value for URL, must not be opaque: " + value);
            }
         }
      }
   }

   public static void validateCertSerialNumber(String value) {
      if (null != value) {
         if (value.length() == 0) {
            throw new IllegalArgumentException("Illegal value for serial number: " + value);
         } else {
            String rawHex = value.replaceAll("[ :]", "");
            if (rawHex.length() == 0) {
               throw new IllegalArgumentException("Illegal value for serial number: " + value);
            } else {
               try {
                  new BigInteger(rawHex, 16);
               } catch (Exception var3) {
                  throw new IllegalArgumentException("Illegal value for serial number: " + value, var3);
               }
            }
         }
      }
   }
}
