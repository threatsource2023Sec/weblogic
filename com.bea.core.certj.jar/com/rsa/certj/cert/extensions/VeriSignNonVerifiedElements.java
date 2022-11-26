package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.certj.cert.AttributeException;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.attributes.X501Attribute;

/** @deprecated */
public class VeriSignNonVerifiedElements extends X509V3Extension implements CertExtension {
   private X501Attributes theAttributes = new X501Attributes();
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public VeriSignNonVerifiedElements() {
      this.extensionTypeFlag = 116;
      this.criticality = false;
      this.setSpecialOID(VERISIGN_NON_VERIFIED_OID);
      this.extensionTypeString = "VeriSignNonVerifiedElements";
   }

   /** @deprecated */
   public VeriSignNonVerifiedElements(X501Attributes var1, boolean var2) {
      this.extensionTypeFlag = 116;
      var2 = false;
      this.setSpecialOID(VERISIGN_NON_VERIFIED_OID);
      this.extensionTypeString = "VeriSignNonVerifiedElements";
      this.criticality = var2;

      try {
         if (var1 != null) {
            this.theAttributes = (X501Attributes)var1.clone();
         }
      } catch (CloneNotSupportedException var4) {
      }

   }

   /** @deprecated */
   public void addAttribute(X501Attribute var1) {
      if (var1 != null) {
         this.theAttributes.addAttribute(var1);
      }

   }

   /** @deprecated */
   public void setAttributes(X501Attributes var1) {
      try {
         if (var1 != null) {
            this.theAttributes = (X501Attributes)var1.clone();
         }
      } catch (CloneNotSupportedException var3) {
      }

   }

   /** @deprecated */
   public int getAttributeCount() {
      return this.theAttributes.getAttributeCount();
   }

   /** @deprecated */
   public X501Attribute getAttributeByIndex(int var1) throws CertificateException {
      if (var1 < this.theAttributes.getAttributeCount()) {
         return this.theAttributes.getAttributeByIndex(var1);
      } else {
         throw new CertificateException("Invalid Index");
      }
   }

   /** @deprecated */
   public X501Attributes getAttributes() {
      try {
         return (X501Attributes)this.theAttributes.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            this.theAttributes = new X501Attributes(var1, var2, 0);
         } catch (AttributeException var4) {
            throw new CertificateException("Could not decode VeriSignNonVerifiedElements extension.");
         }
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      try {
         byte[] var1 = new byte[this.theAttributes.getDERLen(0)];
         int var2 = this.theAttributes.getDEREncoding(var1, 0, 0);
         EncodedContainer var3 = new EncodedContainer(12544, true, 0, var1, 0, var2);
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
      } else {
         try {
            if (this.asn1TemplateValue == null && this.derEncodeValueInit() == 0) {
               return 0;
            } else {
               int var3 = this.asn1TemplateValue.derEncode(var1, var2);
               return var3;
            }
         } catch (ASN_Exception var4) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      VeriSignNonVerifiedElements var1 = new VeriSignNonVerifiedElements();
      var1.theAttributes = (X501Attributes)this.theAttributes.clone();
      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.theAttributes = null;
   }
}
