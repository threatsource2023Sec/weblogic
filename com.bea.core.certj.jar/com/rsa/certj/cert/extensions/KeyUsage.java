package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class KeyUsage extends X509V3Extension implements CertExtension {
   /** @deprecated */
   public static final int KEY_USAGE_BITS = 9;
   /** @deprecated */
   public static final int KEY_USAGE_MASK = -8388608;
   /** @deprecated */
   public static final int DIGITAL_SIGNATURE = Integer.MIN_VALUE;
   /** @deprecated */
   public static final int NON_REPUDIATION = 1073741824;
   /** @deprecated */
   public static final int KEY_ENCIPHERMENT = 536870912;
   /** @deprecated */
   public static final int DATA_ENCIPHERMENT = 268435456;
   /** @deprecated */
   public static final int KEY_AGREEMENT = 134217728;
   /** @deprecated */
   public static final int KEY_CERT_SIGN = 67108864;
   /** @deprecated */
   public static final int CRL_SIGN = 33554432;
   /** @deprecated */
   public static final int ENCIPHER_ONLY = 16777216;
   /** @deprecated */
   public static final int DECIPHER_ONLY = 8388608;
   private int keyUsage;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public KeyUsage() {
      this.extensionTypeFlag = 15;
      this.criticality = false;
      this.setStandardOID(15);
      this.extensionTypeString = "KeyUsage";
   }

   /** @deprecated */
   public KeyUsage(int var1, boolean var2) {
      this.extensionTypeFlag = 15;
      this.criticality = var2;
      this.keyUsage = var1 & -8388608;
      this.setStandardOID(15);
      this.extensionTypeString = "KeyUsage";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.keyUsage = 0;
         BitStringContainer var3 = new BitStringContainer(0);
         ASN1Container[] var4 = new ASN1Container[]{var3};

         try {
            ASN1.berDecode(var1, var2, var4);
         } catch (ASN_Exception var7) {
            throw new CertificateException("Could not decode KeyUsage extension.");
         }

         if (var3.dataLen != 0) {
            if (var3.dataLen > 4) {
               throw new CertificateException("Could not decode KeyUsage extension.");
            } else {
               int var5 = var3.dataOffset;

               for(int var6 = 24; var5 < var3.dataOffset + var3.dataLen; var6 -= 8) {
                  this.keyUsage |= (var3.data[var5] & 255) << var6;
                  ++var5;
               }

               this.keyUsage &= -8388608;
            }
         }
      }
   }

   /** @deprecated */
   public int getKeyUsage() {
      return this.keyUsage;
   }

   /** @deprecated */
   public boolean verifyKeyUsage(int var1) {
      int var2 = var1 & this.keyUsage & -8388608;
      return var2 == var1;
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      BitStringContainer var1 = new BitStringContainer(0, true, 0, this.keyUsage, 9, true);
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
      KeyUsage var1 = new KeyUsage();
      var1.keyUsage = this.keyUsage;
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.keyUsage = 0;
      this.asn1TemplateValue = null;
   }
}
