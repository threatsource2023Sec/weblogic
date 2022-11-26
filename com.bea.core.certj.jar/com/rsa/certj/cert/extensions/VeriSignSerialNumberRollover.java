package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.IA5StringContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class VeriSignSerialNumberRollover extends X509V3Extension implements CertExtension {
   private String serialNumber;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public VeriSignSerialNumberRollover() {
      this.extensionTypeFlag = 115;
      this.criticality = false;
      this.setSpecialOID(VERISIGN_SERIAL_NUMBER_OID);
      this.extensionTypeString = "VeriSignSerialNumberRollover";
   }

   /** @deprecated */
   public VeriSignSerialNumberRollover(String var1, boolean var2) {
      this.extensionTypeFlag = 115;
      var2 = false;
      this.setSpecialOID(VERISIGN_SERIAL_NUMBER_OID);
      this.extensionTypeString = "VeriSignSerialNumberRollover";
      this.criticality = var2;
      this.serialNumber = var1;
   }

   /** @deprecated */
   public void setSerialNumber(String var1) {
      this.serialNumber = var1;
   }

   /** @deprecated */
   public String getSerialNumber() {
      return this.serialNumber;
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            IA5StringContainer var3 = new IA5StringContainer(0);
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            this.serialNumber = var3.getValueAsString();
         } catch (ASN_Exception var5) {
            throw new CertificateException("Could not decode VeriSignSerialNumberRollover extension.");
         }
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      try {
         IA5StringContainer var1 = new IA5StringContainer(0, true, 0, this.serialNumber);
         ASN1Container[] var2 = new ASN1Container[]{var1};
         this.asn1TemplateValue = new ASN1Template(var2);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var3) {
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
      VeriSignSerialNumberRollover var1 = new VeriSignSerialNumberRollover();
      if (this.serialNumber != null) {
         var1.serialNumber = this.serialNumber;
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
      this.serialNumber = null;
      this.asn1TemplateValue = null;
   }
}
