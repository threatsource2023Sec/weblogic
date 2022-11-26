package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class NetscapeCertType extends X509V3Extension implements CertExtension {
   /** @deprecated */
   public static final int CERT_TYPE_BITS = 8;
   /** @deprecated */
   public static final int CERT_TYPE_MASK = -16777216;
   /** @deprecated */
   public static final int SSL_CLIENT = Integer.MIN_VALUE;
   /** @deprecated */
   public static final int SSL_SERVER = 1073741824;
   /** @deprecated */
   public static final int SMIME_CLIENT = 536870912;
   /** @deprecated */
   public static final int OBJECT_SIGNING = 268435456;
   /** @deprecated */
   public static final int RESERVED = 134217728;
   /** @deprecated */
   public static final int SSL_CA = 67108864;
   /** @deprecated */
   public static final int SMIME_CA = 33554432;
   /** @deprecated */
   public static final int OBJECT_SIGNING_CA = 16777216;
   private int certType;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public NetscapeCertType() {
      this.extensionTypeFlag = 101;
      this.criticality = false;
      this.setSpecialOID(NETSCAPE_CERT_TYPE_OID);
      this.extensionTypeString = "NetscapeCertType";
   }

   /** @deprecated */
   public NetscapeCertType(int var1, boolean var2) {
      this.extensionTypeFlag = 101;
      this.criticality = var2;
      this.setSpecialOID(NETSCAPE_CERT_TYPE_OID);
      this.certType = var1 & -16777216;
      this.extensionTypeString = "NetscapeCertType";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.certType = 0;
         BitStringContainer var3 = new BitStringContainer(0);
         ASN1Container[] var4 = new ASN1Container[]{var3};

         try {
            ASN1.berDecode(var1, var2, var4);
         } catch (ASN_Exception var7) {
            throw new CertificateException("Could not decode NetscapeCertType extension.");
         }

         if (var3.dataLen != 0) {
            if (var3.dataLen > 4) {
               throw new CertificateException("Could not decode NetscapeCertType extension.");
            } else {
               int var5 = var3.dataOffset;

               for(int var6 = 24; var5 < var3.dataOffset + var3.dataLen; var6 -= 8) {
                  this.certType |= (var3.data[var5] & 255) << var6;
                  ++var5;
               }

               this.certType &= -16777216;
            }
         }
      }
   }

   /** @deprecated */
   public void setCertType(int var1) {
      this.certType = var1 & -16777216;
   }

   /** @deprecated */
   public int getCertType() {
      return this.certType;
   }

   /** @deprecated */
   public boolean verifyCertType(int var1) {
      int var2 = var1 & this.certType & -16777216;
      return var2 == var1;
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      BitStringContainer var1 = new BitStringContainer(0, true, 0, this.certType, 8, true);
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
      NetscapeCertType var1 = new NetscapeCertType();
      var1.certType = this.certType;
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.certType = 0;
      this.asn1TemplateValue = null;
   }
}
