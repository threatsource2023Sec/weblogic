package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.OIDContainer;
import com.rsa.certj.cert.CRLEntryExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.OCSPSingleExtension;

/** @deprecated */
public class HoldInstructionCode extends X509V3Extension implements CRLEntryExtension, OCSPSingleExtension {
   private byte[] holdInstruction;
   private int holdInstructionOffset;
   private int holdInstructionLen;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public HoldInstructionCode() {
      this.extensionTypeFlag = 23;
      this.criticality = false;
      this.setStandardOID(23);
      this.extensionTypeString = "HoldInstructionCode";
   }

   /** @deprecated */
   public HoldInstructionCode(byte[] var1, int var2, int var3, boolean var4) {
      this.extensionTypeFlag = 23;
      this.criticality = var4;
      if (var1 != null && var3 != 0) {
         this.setCode(var1, var2, var3);
      }

      this.setStandardOID(23);
      this.extensionTypeString = "HoldInstructionCode";
   }

   /** @deprecated */
   public void setCode(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         try {
            OIDContainer var4 = new OIDContainer(0, true, 0, var1, var2, var3);
            ASN1Container[] var5 = new ASN1Container[]{var4};
            this.holdInstruction = ASN1.derEncode(var5);
            this.holdInstructionOffset = 1 + ASN1Lengths.determineLengthLen(this.holdInstruction, 1);
            this.holdInstructionLen = this.holdInstruction.length - this.holdInstructionOffset;
         } catch (ASN_Exception var6) {
            this.holdInstruction = null;
            this.holdInstructionOffset = 0;
            this.holdInstructionLen = 0;
         }

      }
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         OIDContainer var3 = new OIDContainer(0);
         ASN1Container[] var4 = new ASN1Container[]{var3};

         try {
            ASN1.berDecode(var1, var2, var4);
         } catch (ASN_Exception var6) {
            this.holdInstruction = null;
            throw new CertificateException("Could not decode HoldInstructionCode extension.");
         }

         this.setCode(var3.data, var3.dataOffset, var3.dataLen);
      }
   }

   /** @deprecated */
   public byte[] getCode() {
      if (this.holdInstruction == null) {
         return null;
      } else {
         byte[] var1 = new byte[this.holdInstructionLen];
         System.arraycopy(this.holdInstruction, this.holdInstructionOffset, var1, 0, this.holdInstructionLen);
         return var1;
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      try {
         OIDContainer var1 = new OIDContainer(0, true, 0, this.holdInstruction, this.holdInstructionOffset, this.holdInstructionLen);
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
      HoldInstructionCode var1 = new HoldInstructionCode();
      if (this.holdInstruction != null) {
         var1.holdInstruction = (byte[])((byte[])this.holdInstruction.clone());
         var1.holdInstructionOffset = this.holdInstructionOffset;
         var1.holdInstructionLen = this.holdInstructionLen;
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.holdInstruction = null;
      this.holdInstructionLen = 0;
      this.holdInstructionOffset = 0;
   }
}
