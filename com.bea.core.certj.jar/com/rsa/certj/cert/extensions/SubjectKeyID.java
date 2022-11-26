package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class SubjectKeyID extends X509V3Extension implements CertExtension {
   private byte[] keyID;
   private int keyIDOffset;
   private int keyIDLen;

   /** @deprecated */
   public SubjectKeyID() {
      this.extensionTypeFlag = 14;
      this.criticality = false;
      this.setStandardOID(14);
      this.extensionTypeString = "SubjectKeyID";
   }

   /** @deprecated */
   public SubjectKeyID(byte[] var1, int var2, int var3, boolean var4) {
      this.extensionTypeFlag = 14;
      this.criticality = var4;
      if (var1 != null && var3 != 0) {
         this.setKeyID(var1, var2, var3);
      }

      this.setStandardOID(14);
      this.extensionTypeString = "SubjectKeyID";
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
            this.keyID = null;
            throw new CertificateException("Could not decode SubjectKeyID extension.");
         }

         this.setKeyID(var3.data, var3.dataOffset, var3.dataLen);
      }
   }

   /** @deprecated */
   public void setKeyID(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         try {
            OctetStringContainer var4 = new OctetStringContainer(0, true, 0, var1, var2, var3);
            ASN1Container[] var5 = new ASN1Container[]{var4};
            this.keyID = ASN1.derEncode(var5);
            this.keyIDOffset = 1 + ASN1Lengths.determineLengthLen(this.keyID, 1);
            this.keyIDLen = this.keyID.length - this.keyIDOffset;
         } catch (ASN_Exception var6) {
            this.keyID = null;
            this.keyIDOffset = 0;
            this.keyIDLen = 0;
         }

      }
   }

   /** @deprecated */
   public byte[] getKeyID() {
      if (this.keyID == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.keyIDLen];
         System.arraycopy(this.keyID, this.keyIDOffset, var1, 0, this.keyIDLen);
         return var1;
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      return this.keyID != null ? this.keyID.length : 0;
   }

   /** @deprecated */
   public int derEncodeValue(byte[] var1, int var2) {
      if (var1 == null) {
         return 0;
      } else if (this.keyID == null) {
         return 0;
      } else {
         System.arraycopy(this.keyID, 0, var1, var2, this.keyID.length);
         return this.keyID.length;
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      SubjectKeyID var1 = new SubjectKeyID();
      if (this.keyID != null) {
         var1.keyID = (byte[])((byte[])this.keyID.clone());
         var1.keyIDOffset = this.keyIDOffset;
         var1.keyIDLen = this.keyIDLen;
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.keyID = null;
   }
}
