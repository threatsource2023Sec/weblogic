package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;

/** @deprecated */
public class NameConstraints extends X509V3Extension implements CertExtension {
   private GeneralSubtrees permittedSubtrees;
   private static final int PERMITTED_SPECIAL = 8454144;
   private GeneralSubtrees excludedSubtrees;
   private static final int EXCLUDED_SPECIAL = 8454145;
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public NameConstraints() {
      this.extensionTypeFlag = 30;
      this.criticality = false;
      this.permittedSubtrees = new GeneralSubtrees();
      this.excludedSubtrees = new GeneralSubtrees();
      this.setStandardOID(30);
      this.extensionTypeString = "NameConstraints";
   }

   /** @deprecated */
   public NameConstraints(GeneralSubtrees var1, GeneralSubtrees var2, boolean var3) {
      this.extensionTypeFlag = 30;
      this.criticality = var3;
      if (var1 != null) {
         this.permittedSubtrees = var1;
      }

      if (var2 != null) {
         this.excludedSubtrees = var2;
      }

      this.setStandardOID(30);
      this.extensionTypeString = "NameConstraints";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         SequenceContainer var3 = new SequenceContainer(0);
         EndContainer var4 = new EndContainer();
         EncodedContainer var5 = new EncodedContainer(8466432);
         EncodedContainer var6 = new EncodedContainer(8466433);
         ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};

         try {
            ASN1.berDecode(var1, var2, var7);
         } catch (ASN_Exception var10) {
            throw new CertificateException("Could not decode Name Contraints extension.");
         }

         try {
            if (var5.dataPresent) {
               this.permittedSubtrees = new GeneralSubtrees(var5.data, var5.dataOffset, 8454144);
            }

            if (var6.dataPresent) {
               this.excludedSubtrees = new GeneralSubtrees(var6.data, var6.dataOffset, 8454145);
            }

         } catch (NameException var9) {
            throw new CertificateException("Could not decode Name Constraints extension!!!.");
         }
      }
   }

   /** @deprecated */
   public void setPermittedSubtrees(GeneralSubtrees var1) {
      if (var1 != null) {
         this.permittedSubtrees = var1;
      }

   }

   /** @deprecated */
   public void setExcludedSubtrees(GeneralSubtrees var1) {
      if (var1 != null) {
         this.excludedSubtrees = var1;
      }

   }

   /** @deprecated */
   public GeneralSubtrees getPermittedSubtrees() {
      return this.permittedSubtrees;
   }

   /** @deprecated */
   public GeneralSubtrees getExcludedSubtrees() {
      return this.excludedSubtrees;
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      SequenceContainer var1 = new SequenceContainer(0, true, 0);
      EndContainer var2 = new EndContainer();
      EncodedContainer var3 = null;
      EncodedContainer var4 = null;

      try {
         int var5;
         byte[] var6;
         byte var7;
         int var8;
         if (this.permittedSubtrees != null) {
            var5 = this.permittedSubtrees.getDERLen(8454144);
            var6 = new byte[var5];
            var7 = 0;
            var8 = this.permittedSubtrees.getDEREncoding(var6, var7, 8454144);
            var3 = new EncodedContainer(8466432, true, 0, var6, var7, var8);
         }

         if (this.excludedSubtrees != null) {
            var5 = this.excludedSubtrees.getDERLen(8454145);
            var6 = new byte[var5];
            var7 = 0;
            var8 = this.excludedSubtrees.getDEREncoding(var6, var7, 8454145);
            var4 = new EncodedContainer(8466433, true, 0, var6, var7, var8);
         }
      } catch (ASN_Exception var10) {
         return 0;
      } catch (NameException var11) {
         return 0;
      }

      ASN1Container[] var12;
      if (var3 != null) {
         if (var4 != null) {
            var12 = new ASN1Container[]{var1, var3, var4, var2};
            this.asn1TemplateValue = new ASN1Template(var12);
         } else {
            var12 = new ASN1Container[]{var1, var3, var2};
            this.asn1TemplateValue = new ASN1Template(var12);
         }
      } else if (var4 != null) {
         var12 = new ASN1Container[]{var1, var4, var2};
         this.asn1TemplateValue = new ASN1Template(var12);
      } else {
         var12 = new ASN1Container[]{var1, var2};
         this.asn1TemplateValue = new ASN1Template(var12);
      }

      try {
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var9) {
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
      GeneralSubtrees var1 = null;
      if (this.permittedSubtrees != null) {
         var1 = (GeneralSubtrees)this.permittedSubtrees.clone();
      }

      GeneralSubtrees var2 = null;
      if (this.excludedSubtrees != null) {
         var2 = (GeneralSubtrees)this.excludedSubtrees.clone();
      }

      NameConstraints var3 = new NameConstraints(var1, var2, this.criticality);
      if (this.asn1TemplateValue != null) {
         var3.derEncodeValueInit();
      }

      super.copyValues(var3);
      return var3;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.permittedSubtrees = null;
      this.excludedSubtrees = null;
      this.asn1TemplateValue = null;
   }
}
