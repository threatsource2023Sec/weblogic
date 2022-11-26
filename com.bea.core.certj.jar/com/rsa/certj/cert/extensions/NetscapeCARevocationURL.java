package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.IA5StringContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class NetscapeCARevocationURL extends X509V3Extension implements CertExtension {
   private String revocationURL;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public NetscapeCARevocationURL() {
      this.extensionTypeFlag = 104;
      this.criticality = false;
      this.setSpecialOID(NETSCAPE_CA_REVOCATION_URL_OID);
      this.extensionTypeString = "NetscapeCARevocationURL";
   }

   /** @deprecated */
   public NetscapeCARevocationURL(String var1, boolean var2) {
      this.extensionTypeFlag = 104;
      this.criticality = var2;
      this.setSpecialOID(NETSCAPE_CA_REVOCATION_URL_OID);
      if (var1 != null) {
         this.revocationURL = var1;
      }

      this.extensionTypeString = "NetscapeCARevocationURL";
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
            this.revocationURL = var3.getValueAsString();
         } catch (ASN_Exception var5) {
            throw new CertificateException("Could not decode NetscapeCARevocationURL extension.");
         }
      }
   }

   /** @deprecated */
   public void setCARevocationURL(String var1) {
      if (var1 != null) {
         this.revocationURL = var1;
      }

   }

   /** @deprecated */
   public String getCARevocationURL() {
      return this.revocationURL;
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      try {
         IA5StringContainer var1 = new IA5StringContainer(0, true, 0, this.revocationURL);
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
      NetscapeCARevocationURL var1 = new NetscapeCARevocationURL();
      var1.revocationURL = this.revocationURL;
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.revocationURL = null;
      this.asn1TemplateValue = null;
   }
}
