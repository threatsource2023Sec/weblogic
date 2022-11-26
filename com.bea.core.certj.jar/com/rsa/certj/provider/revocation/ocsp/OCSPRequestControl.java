package com.rsa.certj.provider.revocation.ocsp;

import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;

/** @deprecated */
public final class OCSPRequestControl implements Cloneable {
   private X509Certificate requestSignerCert;
   private String digestAlg;
   private String signatureAlg;
   private X509Certificate[] extraCerts;
   private X509V3Extensions requestExtensions;

   /** @deprecated */
   public OCSPRequestControl(X509Certificate var1, String var2, String var3, X509Certificate[] var4, X509V3Extensions var5) throws InvalidParameterException {
      try {
         if (var1 != null) {
            this.requestSignerCert = (X509Certificate)var1.clone();
         }

         if (var2 != null) {
            this.digestAlg = var2;
         }

         if (var3 != null) {
            this.signatureAlg = var3;
         }

         if (var4 != null) {
            this.extraCerts = new X509Certificate[var4.length];

            for(int var6 = 0; var6 < var4.length; ++var6) {
               this.extraCerts[var6] = (X509Certificate)var4[var6].clone();
            }
         }

         if (var5 != null) {
            this.requestExtensions = (X509V3Extensions)var5.clone();
         }

      } catch (CloneNotSupportedException var7) {
         throw new InvalidParameterException(var7);
      }
   }

   /** @deprecated */
   public OCSPRequestControl(X509Certificate var1) throws InvalidParameterException {
      try {
         if (var1 != null) {
            this.requestSignerCert = (X509Certificate)var1.clone();
            this.digestAlg = "SHA1";
            this.signatureAlg = "SHA1/RSA/PKCS1Block01Pad";
         }

      } catch (CloneNotSupportedException var3) {
         throw new InvalidParameterException(var3);
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      try {
         return new OCSPRequestControl(this.requestSignerCert, this.digestAlg, this.signatureAlg, this.extraCerts, this.requestExtensions);
      } catch (InvalidParameterException var2) {
         throw new CloneNotSupportedException(var2.getMessage());
      }
   }

   /** @deprecated */
   public void setDigestAlgorithm(String var1) throws InvalidParameterException {
      this.digestAlg = var1 == null ? null : var1;
   }

   /** @deprecated */
   public void setSignatureAlgorithm(String var1) throws InvalidParameterException {
      this.signatureAlg = var1 == null ? null : var1;
   }

   /** @deprecated */
   public void setExtraCerts(X509Certificate[] var1) throws InvalidParameterException {
      try {
         if (var1 == null) {
            this.extraCerts = null;
         } else {
            this.extraCerts = new X509Certificate[var1.length];

            for(int var2 = 0; var2 < var1.length; ++var2) {
               this.extraCerts[var2] = (X509Certificate)var1[var2].clone();
            }
         }

      } catch (CloneNotSupportedException var3) {
         throw new InvalidParameterException(var3);
      }
   }

   /** @deprecated */
   public void setSignerCert(X509Certificate var1) throws InvalidParameterException {
      try {
         if (var1 != null) {
            this.requestSignerCert = (X509Certificate)var1.clone();
         }

      } catch (CloneNotSupportedException var3) {
         throw new InvalidParameterException(var3);
      }
   }

   /** @deprecated */
   public void setRequestExtensions(X509V3Extensions var1) throws InvalidParameterException {
      try {
         this.requestExtensions = var1 == null ? null : (X509V3Extensions)var1.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InvalidParameterException(var3);
      }
   }

   /** @deprecated */
   public String getDigestAlgorithm() {
      return this.digestAlg;
   }

   /** @deprecated */
   public String getSignatureAlgorithm() {
      return this.signatureAlg;
   }

   /** @deprecated */
   public X509Certificate getSignerCert() {
      return this.requestSignerCert;
   }

   /** @deprecated */
   public X509Certificate[] getExtraCerts() {
      return this.extraCerts;
   }

   /** @deprecated */
   public X509V3Extensions getRequestExtensions() {
      return this.requestExtensions;
   }
}
