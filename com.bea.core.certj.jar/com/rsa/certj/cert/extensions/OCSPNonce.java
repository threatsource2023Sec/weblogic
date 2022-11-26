package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.OCSPRequestExtension;

/** @deprecated */
public class OCSPNonce extends X509V3Extension implements OCSPRequestExtension {
   private byte[] nonceValue;
   private int nonceValueLen;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public OCSPNonce() {
      this.extensionTypeFlag = 120;
      this.criticality = false;
      this.setSpecialOID(OCSP_NONCE_OID);
      this.extensionTypeString = "OCSPNonce";
      this.nonceValue = null;
      this.nonceValueLen = 0;
   }

   /** @deprecated */
   public OCSPNonce(byte[] var1, int var2, int var3) {
      this.extensionTypeFlag = 120;
      this.setSpecialOID(OCSP_NONCE_OID);
      this.extensionTypeString = "OCSPNonce";
      this.criticality = false;
      this.setNonceValue(var1, var2, var3);
   }

   /** @deprecated */
   public void setNonceValue(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.nonceValue = new byte[var3];
         System.arraycopy(var1, var2, this.nonceValue, 0, var3);
         this.nonceValueLen = var3;
      }
   }

   /** @deprecated */
   public byte[] getNonceValue() {
      return this.nonceValue == null ? null : this.nonceValue;
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         OctetStringContainer var3 = new OctetStringContainer(0);
         ASN1Container[] var4 = new ASN1Container[]{var3};

         try {
            ASN1.berDecode(var1, var2, var4);
         } catch (ASN_Exception var6) {
            this.nonceValue = null;
            this.nonceValueLen = 0;
            throw new CertificateException("Could not decode OCSPNonce extension.");
         }

         this.setNonceValue(var3.data, var3.dataOffset, var3.dataLen);
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      if (this.nonceValue != null && this.nonceValueLen != 0) {
         try {
            OctetStringContainer var1 = new OctetStringContainer(0, true, 0, this.nonceValue, 0, this.nonceValueLen);
            ASN1Container[] var2 = new ASN1Container[]{var1};
            this.asn1TemplateValue = new ASN1Template(var2);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (ASN_Exception var3) {
            return 0;
         }
      } else {
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
      OCSPNonce var1 = new OCSPNonce();
      if (this.nonceValue != null) {
         var1.nonceValueLen = this.nonceValueLen;
         var1.nonceValue = new byte[var1.nonceValueLen];
         System.arraycopy(this.nonceValue, 0, var1.nonceValue, 0, var1.nonceValueLen);
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
      this.asn1TemplateValue = null;
   }
}
