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
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.CertExtension;
import com.rsa.certj.cert.CertificateException;
import java.util.Vector;

/** @deprecated */
public class PolicyMappings extends X509V3Extension implements CertExtension {
   private Vector[] issuerDomainPolicy = this.createVectorArray(3);
   private Vector[] subjectDomainPolicy = this.createVectorArray(3);
   ASN1Template asn1TemplateValue;

   /** @deprecated */
   public PolicyMappings() {
      this.extensionTypeFlag = 33;
      this.criticality = false;
      this.setStandardOID(33);
      this.extensionTypeString = "PolicyMappings";
   }

   /** @deprecated */
   public PolicyMappings(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6, boolean var7) {
      this.extensionTypeFlag = 33;
      this.criticality = var7;
      this.setStandardOID(33);
      if (var1 != null && var3 != 0) {
         this.issuerDomainPolicy[0].addElement(var1);
         this.issuerDomainPolicy[1].addElement(new Integer(var2));
         this.issuerDomainPolicy[2].addElement(new Integer(var3));
      }

      if (var4 != null && var6 != 0) {
         this.subjectDomainPolicy[0].addElement(var4);
         this.subjectDomainPolicy[1].addElement(new Integer(var5));
         this.subjectDomainPolicy[2].addElement(new Integer(var6));
      }

      this.extensionTypeString = "PolicyMappings";
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
            OfContainer var3 = new OfContainer(this.special, 12288, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               SequenceContainer var8 = new SequenceContainer(0);
               EndContainer var9 = new EndContainer();
               OIDContainer var10 = new OIDContainer(0);
               OIDContainer var11 = new OIDContainer(0);
               ASN1Container[] var12 = new ASN1Container[]{var8, var10, var11, var9};
               ASN1.berDecode(var7.data, var7.dataOffset, var12);
               this.issuerDomainPolicy[0].addElement(var10.data);
               this.issuerDomainPolicy[1].addElement(new Integer(var10.dataOffset));
               this.issuerDomainPolicy[2].addElement(new Integer(var10.dataLen));
               this.subjectDomainPolicy[0].addElement(var11.data);
               this.subjectDomainPolicy[1].addElement(new Integer(var11.dataOffset));
               this.subjectDomainPolicy[2].addElement(new Integer(var11.dataLen));
            }

         } catch (ASN_Exception var13) {
            throw new CertificateException("Could not decode PolicyMappings extension.");
         }
      }
   }

   /** @deprecated */
   public void setDomainPolicy(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6) {
      if (var1 != null && var3 != 0) {
         this.issuerDomainPolicy[0].addElement(var1);
         this.issuerDomainPolicy[1].addElement(new Integer(var2));
         this.issuerDomainPolicy[2].addElement(new Integer(var3));
      }

      if (var4 != null && var6 != 0) {
         this.subjectDomainPolicy[0].addElement(var4);
         this.subjectDomainPolicy[1].addElement(new Integer(var5));
         this.subjectDomainPolicy[2].addElement(new Integer(var6));
      }

   }

   /** @deprecated */
   public byte[] getIssuerDomainPolicy(int var1) throws CertificateException {
      if (this.issuerDomainPolicy[0].size() <= var1) {
         throw new CertificateException("Specified index is invalid.");
      } else if (this.issuerDomainPolicy[0].elementAt(var1) != null) {
         Integer var2 = (Integer)this.issuerDomainPolicy[1].elementAt(var1);
         Integer var3 = (Integer)this.issuerDomainPolicy[2].elementAt(var1);
         byte[] var4 = new byte[var3];
         System.arraycopy(this.issuerDomainPolicy[0].elementAt(var1), var2, var4, 0, var3);
         return var4;
      } else {
         return null;
      }
   }

   /** @deprecated */
   public byte[] getSubjectDomainPolicy(int var1) throws CertificateException {
      if (this.subjectDomainPolicy[0].size() <= var1) {
         throw new CertificateException("Specified index is invalid.");
      } else if (this.subjectDomainPolicy[0].elementAt(var1) != null) {
         Integer var2 = (Integer)this.subjectDomainPolicy[1].elementAt(var1);
         Integer var3 = (Integer)this.subjectDomainPolicy[2].elementAt(var1);
         byte[] var4 = new byte[var3];
         System.arraycopy(this.subjectDomainPolicy[0].elementAt(var1), var2, var4, 0, var3);
         return var4;
      } else {
         return null;
      }
   }

   /** @deprecated */
   public int getPolicyCount() {
      return this.subjectDomainPolicy[0].size();
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      Vector var1 = new Vector();

      try {
         OfContainer var2 = new OfContainer(this.special, true, 0, 12288, new EncodedContainer(12288));
         var1.addElement(var2);

         for(int var3 = 0; var3 < this.issuerDomainPolicy[0].size(); ++var3) {
            try {
               EncodedContainer var4 = this.encodeOID(var3);
               var2.addContainer(var4);
            } catch (Exception var5) {
               return 0;
            }
         }

         ASN1Container[] var7 = new ASN1Container[var1.size()];
         var1.copyInto(var7);
         this.asn1TemplateValue = new ASN1Template(var7);
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var6) {
         return 0;
      }
   }

   private EncodedContainer encodeOID(int var1) throws CertificateException {
      try {
         SequenceContainer var2 = new SequenceContainer(0, true, 0);
         EndContainer var3 = new EndContainer();
         int var4 = (Integer)this.issuerDomainPolicy[1].elementAt(var1);
         int var5 = (Integer)this.issuerDomainPolicy[2].elementAt(var1);
         OIDContainer var6 = new OIDContainer(16777216, true, 0, (byte[])((byte[])this.issuerDomainPolicy[0].elementAt(var1)), var4, var5);
         var4 = (Integer)this.subjectDomainPolicy[1].elementAt(var1);
         var5 = (Integer)this.subjectDomainPolicy[2].elementAt(var1);
         OIDContainer var7 = new OIDContainer(16777216, true, 0, (byte[])((byte[])this.subjectDomainPolicy[0].elementAt(var1)), var4, var5);
         ASN1Container[] var8 = new ASN1Container[]{var2, var6, var7, var3};
         ASN1Template var9 = new ASN1Template(var8);
         int var10 = var9.derEncodeInit();
         byte[] var11 = new byte[var10];
         var10 = var9.derEncode(var11, 0);
         return new EncodedContainer(12288, true, 0, var11, 0, var10);
      } catch (ASN_Exception var12) {
         throw new CertificateException(" Can't encode PolicyMappings");
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
      PolicyMappings var1 = new PolicyMappings();

      int var2;
      int var3;
      for(var2 = 0; var2 < this.issuerDomainPolicy.length; ++var2) {
         for(var3 = 0; var3 < this.issuerDomainPolicy[var2].size(); ++var3) {
            var1.issuerDomainPolicy[var2].addElement(this.issuerDomainPolicy[var2].elementAt(var3));
         }
      }

      for(var2 = 0; var2 < this.subjectDomainPolicy.length; ++var2) {
         for(var3 = 0; var3 < this.subjectDomainPolicy[var2].size(); ++var3) {
            var1.subjectDomainPolicy[var2].addElement(this.subjectDomainPolicy[var2].elementAt(var3));
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
      this.issuerDomainPolicy = null;
      this.subjectDomainPolicy = null;
      this.asn1TemplateValue = null;
   }

   /** @deprecated */
   public Vector getSubjectDomainPolicies(byte[] var1) {
      Vector var2 = new Vector();
      int var3 = this.getPolicyCount();

      for(int var4 = 0; var4 < var3; ++var4) {
         byte[] var5 = (byte[])((byte[])this.issuerDomainPolicy[0].get(var4));
         int var6 = (Integer)this.issuerDomainPolicy[1].get(var4);
         int var7 = (Integer)this.issuerDomainPolicy[2].get(var4);
         if (CertJUtils.byteArraysEqual(var5, var6, var7, var1)) {
            byte[] var8 = (byte[])((byte[])this.subjectDomainPolicy[0].get(var4));
            int var9 = (Integer)this.subjectDomainPolicy[1].get(var4);
            int var10 = (Integer)this.subjectDomainPolicy[2].get(var4);
            byte[] var11 = new byte[var10];
            System.arraycopy(var8, var9, var11, 0, var10);
            var2.add(var11);
         }
      }

      return var2;
   }
}
