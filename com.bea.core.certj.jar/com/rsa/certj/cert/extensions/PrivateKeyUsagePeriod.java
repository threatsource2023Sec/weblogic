package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.GenTimeContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import java.util.Date;

/** @deprecated */
public class PrivateKeyUsagePeriod extends X509V3Extension implements CertExtension {
   private Date notBefore;
   private Date notAfter;
   private static final int BEFORE_SPECIAL = 8454144;
   private static final int AFTER_SPECIAL = 8454145;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public PrivateKeyUsagePeriod() {
      this.extensionTypeFlag = 16;
      this.criticality = false;
      this.setStandardOID(16);
      this.extensionTypeString = "PrivateKeyUsagePeriod";
   }

   /** @deprecated */
   public PrivateKeyUsagePeriod(Date var1, Date var2, boolean var3) {
      this.extensionTypeFlag = 16;
      this.criticality = var3;
      this.setStandardOID(16);
      if (var1 != null) {
         this.notBefore = new Date(var1.getTime());
      }

      if (var2 != null) {
         this.notAfter = new Date(var2.getTime());
      }

      this.extensionTypeString = "PrivateKeyUsagePeriod";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         SequenceContainer var3 = new SequenceContainer(0);
         EndContainer var4 = new EndContainer();
         GenTimeContainer var5 = new GenTimeContainer(8454144);
         GenTimeContainer var6 = new GenTimeContainer(8454145);
         ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};

         try {
            ASN1.berDecode(var1, var2, var7);
         } catch (ASN_Exception var9) {
            throw new CertificateException("Could not decode PrivateKeyUsagePeriod extension.");
         }

         if (var5.dataPresent) {
            this.notBefore = new Date(var5.theTime.getTime());
         }

         if (var6.dataPresent) {
            this.notAfter = new Date(var6.theTime.getTime());
         }

      }
   }

   /** @deprecated */
   public void setNotBefore(Date var1) {
      if (var1 != null) {
         this.notBefore = new Date(var1.getTime());
      }

   }

   /** @deprecated */
   public Date getNotBefore() {
      return this.notBefore == null ? null : new Date(this.notBefore.getTime());
   }

   /** @deprecated */
   public void setNotAfter(Date var1) {
      if (var1 != null) {
         this.notAfter = new Date(var1.getTime());
      }

   }

   /** @deprecated */
   public Date getNotAfter() {
      return this.notAfter == null ? null : new Date(this.notAfter.getTime());
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      SequenceContainer var1 = new SequenceContainer(0, true, 0);
      EndContainer var2 = new EndContainer();
      GenTimeContainer var3 = null;
      GenTimeContainer var4 = null;
      byte var5 = 0;
      if (this.notBefore != null) {
         var3 = new GenTimeContainer(8454144, true, 0, this.notBefore);
         var5 = 1;
      }

      if (this.notAfter != null) {
         var4 = new GenTimeContainer(8454145, true, 0, this.notAfter);
         if (var5 == 1) {
            var5 = 3;
         } else {
            var5 = 2;
         }
      }

      switch (var5) {
         case 0:
            ASN1Container[] var6 = new ASN1Container[]{var1, var2};
            this.asn1TemplateValue = new ASN1Template(var6);
            break;
         case 1:
            ASN1Container[] var7 = new ASN1Container[]{var1, var3, var2};
            this.asn1TemplateValue = new ASN1Template(var7);
            break;
         case 2:
            ASN1Container[] var8 = new ASN1Container[]{var1, var4, var2};
            this.asn1TemplateValue = new ASN1Template(var8);
            break;
         case 3:
            ASN1Container[] var9 = new ASN1Container[]{var1, var3, var4, var2};
            this.asn1TemplateValue = new ASN1Template(var9);
      }

      try {
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var10) {
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
      PrivateKeyUsagePeriod var1 = new PrivateKeyUsagePeriod();
      var1.notBefore = this.notBefore;
      var1.notAfter = this.notAfter;
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.notBefore = null;
      this.notAfter = null;
      this.asn1TemplateValue = null;
   }
}
