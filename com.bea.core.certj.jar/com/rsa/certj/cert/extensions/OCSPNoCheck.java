package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1Template;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class OCSPNoCheck extends X509V3Extension implements CertExtension {
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public OCSPNoCheck() {
      this.extensionTypeFlag = 117;
      this.criticality = false;
      this.setSpecialOID(OCSP_NOCHECK_OID);
      this.extensionTypeString = "OCSPNoCheck";
   }

   /** @deprecated */
   public OCSPNoCheck(boolean var1) {
      this.extensionTypeFlag = 117;
      this.setSpecialOID(OCSP_NOCHECK_OID);
      this.extensionTypeString = "OCSPNoCheck";
      this.criticality = var1;
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else if (var1.length < 2 + var2) {
         throw new CertificateException("Invalid  encoding of OCSP NoCheck extension.");
      } else if (var1[var2] != 5 || var1[var2 + 1] != 0) {
         throw new CertificateException("Invalid  encoding of OCSP NoCheck extension.");
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      return 2;
   }

   /** @deprecated */
   public int derEncodeValue(byte[] var1, int var2) {
      if (var1 == null) {
         return 0;
      } else {
         var1[var2] = 5;
         var1[var2 + 1] = 0;
         return 2;
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      OCSPNoCheck var1 = new OCSPNoCheck();
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.asn1TemplateValue = null;
   }
}
