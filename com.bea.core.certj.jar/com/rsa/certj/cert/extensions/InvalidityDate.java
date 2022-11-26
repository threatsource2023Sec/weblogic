package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.GenTimeContainer;
import com.rsa.certj.cert.CRLEntryExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.OCSPSingleExtension;
import java.util.Date;

/** @deprecated */
public class InvalidityDate extends X509V3Extension implements CRLEntryExtension, OCSPSingleExtension {
   private Date date;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public InvalidityDate() {
      this.extensionTypeFlag = 24;
      this.criticality = false;
      this.setStandardOID(24);
      this.extensionTypeString = "InvalidityDate";
   }

   /** @deprecated */
   public InvalidityDate(Date var1, boolean var2) {
      this.extensionTypeFlag = 24;
      this.criticality = var2;
      if (var1 != null) {
         this.date = new Date(var1.getTime());
      }

      this.setStandardOID(24);
      this.extensionTypeString = "InvalidityDate";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         GenTimeContainer var3 = new GenTimeContainer(0);
         ASN1Container[] var4 = new ASN1Container[]{var3};

         try {
            ASN1.berDecode(var1, var2, var4);
         } catch (ASN_Exception var6) {
            throw new CertificateException("Could not decode InvalidityDate extension.");
         }

         this.date = var3.theTime;
      }
   }

   /** @deprecated */
   public void setDate(Date var1) {
      if (var1 != null) {
         this.date = new Date(var1.getTime());
      }

   }

   /** @deprecated */
   public Date getDate() {
      return this.date == null ? null : new Date(this.date.getTime());
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      GenTimeContainer var1 = new GenTimeContainer(0, true, 0, this.date);
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
      InvalidityDate var1 = new InvalidityDate();
      if (this.date != null) {
         var1.date = new Date(this.date.getTime());
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
      this.date = null;
      this.asn1TemplateValue = null;
   }
}
