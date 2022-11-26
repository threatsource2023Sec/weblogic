package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;

/** @deprecated */
public class PolicyConstraints extends X509V3Extension implements CertExtension {
   private int requireExplicitPolicy = -1;
   private int inhibitPolicyMapping = -1;
   ASN1Template asn1TemplateValue;
   private static final int POLICY_SPECIAL = 8454144;
   private static final int MAPPING_SPECIAL = 8454145;

   /** @deprecated */
   public PolicyConstraints() {
      this.extensionTypeFlag = 36;
      this.criticality = false;
      this.setStandardOID(36);
      this.extensionTypeString = "PolicyConstraints";
   }

   /** @deprecated */
   public PolicyConstraints(int var1, int var2, boolean var3) {
      this.extensionTypeFlag = 36;
      this.criticality = var3;
      this.setStandardOID(36);
      this.requireExplicitPolicy = var1;
      this.inhibitPolicyMapping = var2;
      this.extensionTypeString = "PolicyConstraints";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         SequenceContainer var3 = new SequenceContainer(0);
         EndContainer var4 = new EndContainer();
         IntegerContainer var5 = new IntegerContainer(8454144);
         IntegerContainer var6 = new IntegerContainer(8454145);
         ASN1Container[] var7 = new ASN1Container[]{var3, var5, var6, var4};

         try {
            ASN1.berDecode(var1, var2, var7);
         } catch (ASN_Exception var9) {
            throw new CertificateException("Could not decode Policy Constraints extension.");
         }

         if (var5.dataPresent) {
            this.requireExplicitPolicy = this.bytesToInt(var5.data, var5.dataOffset, var5.dataLen);
         }

         if (var6.dataPresent) {
            this.inhibitPolicyMapping = this.bytesToInt(var6.data, var6.dataOffset, var6.dataLen);
         }

      }
   }

   private int bytesToInt(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 != null && var3 != 0) {
         if (var3 > 4) {
            throw new CertificateException("Could not decode AuthorityKeyID extension.");
         } else {
            int var4 = 0;

            for(int var5 = var2; var5 < var3 + var2; ++var5) {
               var4 <<= 8;
               var4 |= var1[var5] & 255;
            }

            return var4;
         }
      } else {
         return 0;
      }
   }

   /** @deprecated */
   public void setExplicitPolicy(int var1) {
      this.requireExplicitPolicy = var1;
   }

   /** @deprecated */
   public void setPolicyMapping(int var1) {
      this.inhibitPolicyMapping = var1;
   }

   /** @deprecated */
   public int getExplicitPolicy() {
      return this.requireExplicitPolicy;
   }

   /** @deprecated */
   public int getPolicyMapping() {
      return this.inhibitPolicyMapping;
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      SequenceContainer var1 = new SequenceContainer(0, true, 0);
      EndContainer var2 = new EndContainer();
      IntegerContainer var3 = null;
      if (this.requireExplicitPolicy != -1) {
         var3 = new IntegerContainer(8454144, true, 0, this.requireExplicitPolicy);
      }

      IntegerContainer var4 = null;
      if (this.inhibitPolicyMapping != -1) {
         var4 = new IntegerContainer(8454145, true, 0, this.inhibitPolicyMapping);
      }

      ASN1Container[] var5;
      if (var3 != null) {
         if (var4 != null) {
            var5 = new ASN1Container[]{var1, var3, var4, var2};
            this.asn1TemplateValue = new ASN1Template(var5);
         } else {
            var5 = new ASN1Container[]{var1, var3, var2};
            this.asn1TemplateValue = new ASN1Template(var5);
         }
      } else if (var4 != null) {
         var5 = new ASN1Container[]{var1, var4, var2};
         this.asn1TemplateValue = new ASN1Template(var5);
      } else {
         var5 = new ASN1Container[]{var1, var2};
         this.asn1TemplateValue = new ASN1Template(var5);
      }

      try {
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var6) {
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
      PolicyConstraints var1 = new PolicyConstraints();
      var1.requireExplicitPolicy = this.requireExplicitPolicy;
      var1.inhibitPolicyMapping = this.inhibitPolicyMapping;
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.requireExplicitPolicy = 0;
      this.inhibitPolicyMapping = 0;
      this.asn1TemplateValue = null;
   }
}
