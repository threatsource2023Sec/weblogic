package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.IntegerContainer;
import com.rsa.certj.cert.CRLExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class DeltaCRLIndicator extends X509V3Extension implements CRLExtension {
   private byte[] indicator;
   ASN1Template asn1TemplateValue;

   private DeltaCRLIndicator(boolean var1) {
      this.extensionTypeFlag = 27;
      this.criticality = var1;
      this.setStandardOID(27);
      this.extensionTypeString = "DeltaCRLIndicator";
   }

   /** @deprecated */
   public DeltaCRLIndicator() {
      this(false);
      this.setDeltaCRLIndicator(0);
   }

   /** @deprecated */
   public DeltaCRLIndicator(int var1, boolean var2) {
      this(var2);
      this.setDeltaCRLIndicator(var1);
   }

   /** @deprecated */
   public DeltaCRLIndicator(byte[] var1, int var2, int var3, boolean var4) {
      this(var4);
      this.setDeltaCRLIndicator(var1, var2, var3);
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         IntegerContainer var3 = new IntegerContainer(0);
         ASN1Container[] var4 = new ASN1Container[]{var3};

         try {
            ASN1.berDecode(var1, var2, var4);
            this.indicator = new byte[var3.dataLen];
            System.arraycopy(var3.data, var3.dataOffset, this.indicator, 0, var3.dataLen);
         } catch (ASN_Exception var6) {
            throw new CertificateException("Could not decode DeltaCRLIndicator extension.");
         }
      }
   }

   /** @deprecated */
   public void setDeltaCRLIndicator(int var1) {
      this.indicator = this.intToByteArray(var1);
   }

   /** @deprecated */
   public void setDeltaCRLIndicator(byte[] var1, int var2, int var3) {
      this.indicator = new byte[var3];
      System.arraycopy(var1, var2, this.indicator, 0, var3);
   }

   /** @deprecated */
   public int getDeltaCRLIndicator() throws CertificateException {
      if (this.indicator.length > 4) {
         throw new CertificateException("Can not represent integer in 32 bits.");
      } else {
         return this.byteArrayToInt(this.indicator);
      }
   }

   /** @deprecated */
   public byte[] getDeltaCRLIndicatorAsByteArray() {
      return this.indicator;
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      try {
         IntegerContainer var1 = new IntegerContainer(0, true, 0, this.indicator, 0, this.indicator.length, true);
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
      DeltaCRLIndicator var1 = new DeltaCRLIndicator();
      if (this.indicator != null) {
         var1.indicator = (byte[])((byte[])this.indicator.clone());
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
      this.setDeltaCRLIndicator(0);
      this.asn1TemplateValue = null;
   }
}
