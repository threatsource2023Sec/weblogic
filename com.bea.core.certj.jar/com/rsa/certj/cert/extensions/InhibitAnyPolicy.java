package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.IntegerContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class InhibitAnyPolicy extends X509V3Extension implements CertExtension {
   private int skipCerts;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public InhibitAnyPolicy() {
      this.extensionTypeFlag = 54;
      this.criticality = false;
      this.setStandardOID(54);
      this.extensionTypeString = "InhibitAnyPolicy";
   }

   /** @deprecated */
   public InhibitAnyPolicy(int var1, boolean var2) {
      this.extensionTypeFlag = 54;
      this.criticality = var2;
      this.skipCerts = var1;
      this.setStandardOID(54);
      this.extensionTypeString = "InhibitAnyPolicy";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.skipCerts = 0;
         IntegerContainer var3 = new IntegerContainer(0);
         ASN1Container[] var4 = new ASN1Container[]{var3};

         try {
            ASN1.berDecode(var1, var2, var4);
            if (var3.dataLen != 0) {
               if (var3.dataLen > 4) {
                  throw new CertificateException("Could not decode InhibitAnyPolicy extension.");
               } else {
                  this.skipCerts = var3.getValueAsInt();
               }
            }
         } catch (ASN_Exception var6) {
            throw new CertificateException("Could not decode InhibitAnyPolicy extension.");
         }
      }
   }

   /** @deprecated */
   public void setSkipCerts(int var1) {
      this.skipCerts = var1;
   }

   /** @deprecated */
   public int getSkipCerts() {
      return this.skipCerts;
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      IntegerContainer var1 = new IntegerContainer(0, true, 0, this.skipCerts);
      ASN1Container[] var2 = new ASN1Container[]{var1};
      this.asn1TemplateValue = new ASN1Template(var2);

      try {
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var4) {
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
            int var3 = this.asn1TemplateValue.derEncode(var1, var2);
            this.asn1Template = null;
            return var3;
         } catch (ASN_Exception var5) {
            this.asn1Template = null;
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      InhibitAnyPolicy var1 = new InhibitAnyPolicy();
      var1.skipCerts = this.skipCerts;
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.skipCerts = 0;
      this.asn1TemplateValue = null;
   }
}
