package com.rsa.certj.cert.extensions;

import com.rsa.certj.cert.CRLEntryExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.OCSPSingleExtension;

/** @deprecated */
public class CertificateIssuer extends X509V3Extension implements CRLEntryExtension, OCSPSingleExtension {
   private GeneralNames generalNames;

   /** @deprecated */
   public CertificateIssuer() {
      this.extensionTypeFlag = 29;
      this.criticality = false;
      this.setStandardOID(29);
      this.extensionTypeString = "CertificateIssuer";
   }

   /** @deprecated */
   public CertificateIssuer(GeneralNames var1, boolean var2) {
      this.extensionTypeFlag = 29;
      this.criticality = var2;
      if (var1 != null) {
         this.generalNames = var1;
      }

      this.setStandardOID(29);
      this.extensionTypeString = "CertificateIssuer";
   }

   /** @deprecated */
   public void setGeneralNames(GeneralNames var1) {
      try {
         if (var1 != null) {
            this.generalNames = (GeneralNames)var1.clone();
         }
      } catch (CloneNotSupportedException var3) {
      }

   }

   /** @deprecated */
   public GeneralNames getGeneralNames() {
      return this.generalNames;
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            this.generalNames = new GeneralNames(var1, var2, 0);
         } catch (NameException var4) {
            throw new CertificateException("Could not decode CertificateIssuer extension.");
         }
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      if (this.generalNames == null) {
         return 0;
      } else {
         try {
            return this.generalNames.getDERLen(this.special);
         } catch (NameException var2) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public int derEncodeValue(byte[] var1, int var2) {
      if (this.generalNames == null) {
         return 0;
      } else if (var1 == null) {
         return 0;
      } else {
         try {
            return this.generalNames.getDEREncoding(var1, var2, 0);
         } catch (NameException var4) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      CertificateIssuer var1 = new CertificateIssuer();
      if (this.generalNames != null) {
         var1.generalNames = (GeneralNames)this.generalNames.clone();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.generalNames = null;
   }
}
