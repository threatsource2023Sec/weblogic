package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EnumeratedContainer;
import com.rsa.certj.cert.CRLEntryExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.OCSPSingleExtension;

/** @deprecated */
public class ReasonCode extends X509V3Extension implements CRLEntryExtension, OCSPSingleExtension {
   /** @deprecated */
   public static final int REASON_CODE_UNSPECIFIED = 0;
   /** @deprecated */
   public static final int REASON_CODE_KEY_COMPROMISE = 1;
   /** @deprecated */
   public static final int REASON_CODE_CA_COMPROMISE = 2;
   /** @deprecated */
   public static final int REASON_CODE_AFFILIATION_CHANGED = 3;
   /** @deprecated */
   public static final int REASON_CODE_SUPERSEDED = 4;
   /** @deprecated */
   public static final int REASON_CODE_CESSATION_OF_OPERATION = 5;
   /** @deprecated */
   public static final int REASON_CODE_CERTIFICATE_HOLD = 6;
   /** @deprecated */
   public static final int REASON_CODE_REMOVED_FROM_CRL = 8;
   /** @deprecated */
   public static final int REASON_CODE_PRIVILEGE_WITHDRAWN = 9;
   /** @deprecated */
   public static final int REASON_CODE_AA_COMPROMISE = 10;
   private int reasonCode;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public ReasonCode() {
      this.extensionTypeFlag = 21;
      this.criticality = false;
      this.setStandardOID(21);
      this.extensionTypeString = "ReasonCode";
   }

   /** @deprecated */
   public ReasonCode(int var1, boolean var2) throws CertificateException {
      this.validateReasonCode(var1);
      this.extensionTypeFlag = 21;
      this.criticality = var2;
      this.reasonCode = var1;
      this.setStandardOID(21);
      this.extensionTypeString = "ReasonCode";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.reasonCode = 0;

         try {
            EnumeratedContainer var3 = new EnumeratedContainer(0);
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            if (var3.dataLen == 0) {
               return;
            }

            this.reasonCode = var3.getValueAsInt();
         } catch (ASN_Exception var5) {
            throw new CertificateException("Could not decode ReasonCode extension.");
         }

         if (this.reasonCode < 0 || this.reasonCode > 8 || this.reasonCode == 7) {
            throw new CertificateException("Could not decode ReasonCode extension: Invalid Reason Code.");
         }
      }
   }

   /** @deprecated */
   public void setReasonCode(int var1) throws CertificateException {
      this.validateReasonCode(var1);
      this.reasonCode = var1;
   }

   /** @deprecated */
   public int getReasonCode() {
      return this.reasonCode;
   }

   /** @deprecated */
   public boolean verifyReasonCode(int var1) {
      return this.reasonCode == var1;
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      try {
         EnumeratedContainer var1 = new EnumeratedContainer(0, true, 0, this.reasonCode);
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
      ReasonCode var1 = new ReasonCode();
      var1.reasonCode = this.reasonCode;
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.reasonCode = 0;
      this.asn1TemplateValue = null;
   }

   private void validateReasonCode(int var1) throws CertificateException {
      if (var1 < 0 || var1 > 10 || var1 == 7) {
         throw new CertificateException("Invalid Reason Code.");
      }
   }
}
