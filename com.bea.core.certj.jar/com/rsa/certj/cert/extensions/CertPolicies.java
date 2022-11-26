package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import java.util.Vector;

/** @deprecated */
public class CertPolicies extends X509V3Extension implements CertExtension {
   private Vector[] policy = this.createVectorArray(2);
   private ASN1Template asn1TemplateValue;

   /** @deprecated */
   public CertPolicies() {
      this.extensionTypeFlag = 32;
      this.criticality = false;
      this.setStandardOID(32);
      this.extensionTypeString = "CertPolicies";
   }

   /** @deprecated */
   public CertPolicies(byte[] var1, int var2, int var3, PolicyQualifiers var4, boolean var5) {
      this.extensionTypeFlag = 32;
      this.criticality = var5;
      this.setStandardOID(32);
      if (var1 != null && var3 != 0) {
         byte[] var6 = new byte[var3];
         System.arraycopy(var1, var2, var6, 0, var3);
         this.policy[0].addElement(var6);
         this.policy[1].addElement(var4);
      }

      this.extensionTypeString = "CertPolicies";
   }

   /** @deprecated */
   public void addCertPolicy(byte[] var1, int var2, int var3, PolicyQualifiers var4) {
      if (var1 != null && var3 != 0) {
         byte[] var5 = new byte[var3];
         System.arraycopy(var1, var2, var5, 0, var3);
         this.policy[0].addElement(var5);
         this.policy[1].addElement(var4);
      }

   }

   /** @deprecated */
   public byte[] getCertPolicyId(int var1) throws CertificateException {
      if (this.policy[0].size() <= var1) {
         throw new CertificateException("Specified index is invalid.");
      } else {
         return (byte[])((byte[])this.policy[0].elementAt(var1));
      }
   }

   /** @deprecated */
   public PolicyQualifiers getPolicyQualifiers(int var1) throws CertificateException {
      if (this.policy[1].size() <= var1) {
         throw new CertificateException("Specified index is invalid.");
      } else {
         return (PolicyQualifiers)this.policy[1].elementAt(var1);
      }
   }

   /** @deprecated */
   public int getPoliciesCount() {
      return this.policy[0].size();
   }

   private Vector[] createVectorArray(int var1) {
      Vector[] var2 = new Vector[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = new Vector();
      }

      return var2;
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            OfContainer var3 = new OfContainer(0, 12288, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               SequenceContainer var8 = new SequenceContainer(0);
               EndContainer var9 = new EndContainer();
               EncodedContainer var10 = new EncodedContainer(77824);
               OIDContainer var11 = new OIDContainer(0);
               ASN1Container[] var12 = new ASN1Container[]{var8, var11, var10, var9};
               ASN1.berDecode(var7.data, var7.dataOffset, var12);
               if (var11.dataLen != 0) {
                  byte[] var13 = new byte[var11.dataLen];
                  System.arraycopy(var11.data, var11.dataOffset, var13, 0, var11.dataLen);
                  this.policy[0].addElement(var13);
               }

               if (var10.dataPresent) {
                  this.policy[1].addElement(new PolicyQualifiers(var10.data, var10.dataOffset, 65536));
               } else {
                  this.policy[1].addElement((Object)null);
               }
            }

         } catch (ASN_Exception var14) {
            throw new CertificateException("Could not decode Certificate Policies extension.");
         } catch (CertificateException var15) {
            throw new CertificateException("Could not decode Certificate Policies extension.");
         }
      }
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      if (this.policy[0].isEmpty()) {
         return 0;
      } else {
         try {
            Vector var1 = new Vector();
            OfContainer var2 = new OfContainer(this.special, true, 0, 12288, new EncodedContainer(12288));
            var1.addElement(var2);

            for(int var3 = 0; var3 < this.policy[0].size(); ++var3) {
               EncodedContainer var4 = this.encodePolicy(var3);
               var2.addContainer(var4);
            }

            ASN1Container[] var6 = new ASN1Container[var1.size()];
            var1.copyInto(var6);
            this.asn1TemplateValue = new ASN1Template(var6);
            return this.asn1TemplateValue.derEncodeInit();
         } catch (Exception var5) {
            return 0;
         }
      }
   }

   private EncodedContainer encodePolicy(int var1) throws CertificateException {
      SequenceContainer var3 = new SequenceContainer(0, true, 0);
      EndContainer var4 = new EndContainer();
      byte[] var6 = (byte[])((byte[])this.policy[0].elementAt(var1));

      try {
         OIDContainer var7 = new OIDContainer(16777216, true, 0, var6, 0, var6.length);
         PolicyQualifiers var8 = (PolicyQualifiers)this.policy[1].elementAt(var1);
         ASN1Template var5;
         if (var8 != null) {
            byte[] var9 = new byte[var8.getDERLen(65536)];
            int var10 = var8.getDEREncoding(var9, 0, 65536);
            EncodedContainer var11 = new EncodedContainer(77824, true, 0, var9, 0, var10);
            ASN1Container[] var12 = new ASN1Container[]{var3, var7, var11, var4};
            var5 = new ASN1Template(var12);
         } else {
            ASN1Container[] var14 = new ASN1Container[]{var3, var7, var4};
            var5 = new ASN1Template(var14);
         }

         int var15 = var5.derEncodeInit();
         byte[] var16 = new byte[var15];
         var15 = var5.derEncode(var16, 0);
         EncodedContainer var2 = new EncodedContainer(12288, true, 0, var16, 0, var15);
         return var2;
      } catch (ASN_Exception var13) {
         throw new CertificateException(" Can't encode Certificate Policy");
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
            return this.asn1TemplateValue.derEncode(var1, var2);
         } catch (ASN_Exception var4) {
            return 0;
         }
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      CertPolicies var1 = new CertPolicies();

      for(int var2 = 0; var2 < this.policy.length; ++var2) {
         for(int var3 = 0; var3 < this.policy[var2].size(); ++var3) {
            var1.policy[var2].addElement(this.policy[var2].elementAt(var3));
         }
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
      this.policy = this.createVectorArray(2);
   }
}
