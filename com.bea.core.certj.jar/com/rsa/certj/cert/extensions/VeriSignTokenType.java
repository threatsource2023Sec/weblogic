package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class VeriSignTokenType extends X509V3Extension implements CertExtension {
   /** @deprecated */
   public static final int TOKEN_TYPE_BITS = 4;
   /** @deprecated */
   public static final int TOKEN_TYPE_MASK = -268435456;
   /** @deprecated */
   public static final int PRIVATE_KEY_STORAGE = Integer.MIN_VALUE;
   /** @deprecated */
   public static final int GENERATED_AND_STORED = 1073741824;
   /** @deprecated */
   public static final int SAFE_EXPORT = 536870912;
   /** @deprecated */
   public static final int NON_EXPORTABLE = 268435456;
   private int tokenType;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public VeriSignTokenType() {
      this.extensionTypeFlag = 114;
      this.criticality = false;
      this.setSpecialOID(VERISIGN_TOKEN_TYPE_OID);
      this.extensionTypeString = "VeriSignTokenType";
   }

   /** @deprecated */
   public VeriSignTokenType(int var1, boolean var2) {
      this.extensionTypeFlag = 114;
      var2 = false;
      this.setSpecialOID(VERISIGN_TOKEN_TYPE_OID);
      this.extensionTypeString = "VeriSignTokenType";
      this.criticality = var2;
      this.tokenType = var1;
   }

   /** @deprecated */
   public void setTokenType(int var1) {
      this.tokenType = var1 & -268435456;
   }

   /** @deprecated */
   public int getTokenType() {
      return this.tokenType;
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.tokenType = 0;

         try {
            BitStringContainer var3 = new BitStringContainer(0);
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            if (var3.dataLen != 0) {
               if (var3.dataLen > 4) {
                  throw new CertificateException("Could not decode VeriSignTokenType extension.");
               } else {
                  int var5 = var3.dataOffset;

                  for(int var6 = 24; var5 < var3.dataOffset + var3.dataLen; var6 -= 8) {
                     this.tokenType |= (var3.data[var5] & 255) << var6;
                     ++var5;
                  }

                  this.tokenType &= -268435456;
               }
            }
         } catch (ASN_Exception var7) {
            throw new CertificateException("Could not decode VeriSignTokenType extension.");
         }
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      BitStringContainer var1 = new BitStringContainer(0, true, 0, this.tokenType, 4, true);
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
      VeriSignTokenType var1 = new VeriSignTokenType();
      var1.tokenType = this.tokenType;
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.tokenType = 0;
      this.asn1TemplateValue = null;
   }
}
