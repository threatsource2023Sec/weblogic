package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;

/** @deprecated */
public class SubjectAltName extends X509V3Extension implements CertExtension {
   private GeneralNames generalNames = new GeneralNames();
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public SubjectAltName() {
      this.extensionTypeFlag = 17;
      this.criticality = false;
      this.setStandardOID(17);
      this.extensionTypeString = "SubjectAltName";
   }

   /** @deprecated */
   public SubjectAltName(GeneralNames var1, boolean var2) throws CertificateException {
      this.extensionTypeFlag = 17;
      this.criticality = var2;
      if (var1 == null) {
         throw new CertificateException("GeneralName is null.");
      } else {
         try {
            this.generalNames = (GeneralNames)var1.clone();
         } catch (CloneNotSupportedException var4) {
            throw new CertificateException("Cloning error. Cannot initialize SubjectAltName");
         }

         this.setStandardOID(17);
         this.extensionTypeString = "SubjectAltName";
      }
   }

   /** @deprecated */
   public void addGeneralName(GeneralName var1) {
      if (var1 != null) {
         this.generalNames.addGeneralName(var1);
      }

   }

   /** @deprecated */
   public void setGeneralNames(GeneralNames var1) throws CertificateException {
      try {
         if (var1 != null) {
            this.generalNames = (GeneralNames)var1.clone();
         }

      } catch (CloneNotSupportedException var3) {
         throw new CertificateException("Can't add new name to SubjectAltName");
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
            throw new CertificateException("Could not decode SubjectAltName extension.");
         }
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      try {
         byte[] var1 = new byte[this.generalNames.getDERLen(0)];
         int var2 = this.generalNames.getDEREncoding(var1, 0, 0);
         EncodedContainer var3 = new EncodedContainer(12288, true, 0, var1, 0, var2);
         ASN1Container[] var4 = new ASN1Container[]{var3};
         this.asn1TemplateValue = new ASN1Template(var4);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (Exception var5) {
         return 0;
      }
   }

   /** @deprecated */
   public int derEncodeValue(byte[] var1, int var2) {
      if (var1 == null) {
         return 0;
      } else if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
         return 0;
      } else {
         try {
            return this.asn1TemplateValue.derEncode(var1, var2);
         } catch (ASN_Exception var4) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      SubjectAltName var1 = new SubjectAltName();
      if (this.generalNames != null) {
         var1.generalNames = (GeneralNames)this.generalNames.clone();
      }

      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.generalNames = new GeneralNames();
   }
}
