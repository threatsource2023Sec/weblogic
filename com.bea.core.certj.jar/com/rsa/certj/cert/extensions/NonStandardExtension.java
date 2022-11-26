package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN_Exception;
import com.rsa.certj.cert.CRLEntryExtension;
import com.rsa.certj.cert.CRLExtension;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.OCSPRequestExtension;
import com.rsa.certj.cert.OCSPSingleExtension;

/** @deprecated */
public class NonStandardExtension extends X509V3Extension implements CRLEntryExtension, CRLExtension, CertExtension, OCSPRequestExtension, OCSPSingleExtension {
   private byte[] valueBER;

   /** @deprecated */
   public NonStandardExtension() {
      this.criticality = false;
      this.extensionTypeFlag = -1;
      this.extensionTypeString = "NonStandardExtension";
   }

   /** @deprecated */
   public NonStandardExtension(byte[] var1, boolean var2, byte[] var3) {
      if (var1 != null) {
         this.theOID = (byte[])((byte[])var1.clone());
         this.theOIDLen = var1.length;
      }

      if (var3 != null) {
         this.valueBER = (byte[])((byte[])var3.clone());
      }

      this.criticality = var2;
      this.extensionTypeFlag = -1;
      this.extensionTypeString = "NonStandardExtension";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            int var3 = 1 + ASN1Lengths.determineLengthLen(var1, var2 + 1) + ASN1Lengths.determineLength(var1, var2 + 1);
            this.valueBER = new byte[var3];
            System.arraycopy(var1, var2, this.valueBER, 0, var3);
         } catch (ASN_Exception var4) {
         }

      }
   }

   /** @deprecated */
   public void setOID(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.theOID = new byte[var3];
         this.theOIDLen = var3;
         System.arraycopy(var1, var2, this.theOID, 0, var3);
      }

   }

   /** @deprecated */
   public void setValueBER(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         this.valueBER = new byte[var3];
         System.arraycopy(var1, var2, this.valueBER, 0, var3);
      }

   }

   /** @deprecated */
   public byte[] getValueBER() {
      if (this.valueBER == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.valueBER.length];
         System.arraycopy(this.valueBER, 0, var1, 0, this.valueBER.length);
         return var1;
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      return this.valueBER != null ? this.valueBER.length : 0;
   }

   /** @deprecated */
   public int derEncodeValue(byte[] var1, int var2) {
      if (this.valueBER == null) {
         return 0;
      } else {
         System.arraycopy(this.valueBER, 0, var1, var2, this.valueBER.length);
         return this.valueBER.length;
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      NonStandardExtension var1 = new NonStandardExtension();
      if (this.valueBER != null) {
         var1.valueBER = (byte[])((byte[])this.valueBER.clone());
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.valueBER = null;
   }
}
