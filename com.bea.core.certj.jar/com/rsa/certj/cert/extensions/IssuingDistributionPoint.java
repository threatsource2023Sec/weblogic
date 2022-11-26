package com.rsa.certj.cert.extensions;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.BooleanContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.cert.CRLExtension;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.RDN;

/** @deprecated */
public class IssuingDistributionPoint extends X509V3Extension implements CRLExtension {
   /** @deprecated */
   public static final int REASON_FLAGS_BITS = 9;
   /** @deprecated */
   public static final int REASON_FLAGS_MASK = -8388608;
   /** @deprecated */
   public static final int UNUSED = Integer.MIN_VALUE;
   /** @deprecated */
   public static final int KEY_COMPROMISE = 1073741824;
   /** @deprecated */
   public static final int CA_COMPROMISE = 536870912;
   /** @deprecated */
   public static final int AFFILIATION_CHANGED = 268435456;
   /** @deprecated */
   public static final int SUPERSEDED = 134217728;
   /** @deprecated */
   public static final int CESSATION_OF_OPERATION = 67108864;
   /** @deprecated */
   public static final int CERTIFICATE_HOLD = 33554432;
   /** @deprecated */
   public static final int PRIVILEGE_WITHDRAWN = 16777216;
   /** @deprecated */
   public static final int AA_COMPROMISE = 8388608;
   private static final int DISTRIBUTION_POINT_SPECIAL = 8454144;
   private static final int USER_CERTS_SPECIAL = 8519681;
   private static final int CA_CERTS_SPECIAL = 8519682;
   private static final int REASONS_SPECIAL = 8454147;
   private static final int INDIRECT_CRL_SPECIAL = 8519684;
   private static final int ATTRIBUTE_CERTS_SPECIAL = 8519685;
   private static final int FULL_NAME_SPECIAL = 8388608;
   private static final int NAME_RELATIVE_SPECIAL = 8388609;
   ASN1Template asn1TemplateValue;
   private GeneralNames genDistributionPoint;
   private RDN rdnDistributionPoint;
   private int reasonFlags = -1;
   private boolean userCerts;
   private boolean caCerts;
   private boolean indirectCRL;
   private boolean attributeCerts;

   /** @deprecated */
   public IssuingDistributionPoint() {
      this.extensionTypeFlag = 28;
      this.criticality = false;
      this.setStandardOID(28);
      this.extensionTypeString = "IssuingDistributionPoint";
   }

   /** @deprecated */
   public IssuingDistributionPoint(RDN var1, boolean var2, boolean var3, int var4, boolean var5, boolean var6, boolean var7) {
      this.extensionTypeFlag = 28;
      this.criticality = var7;
      this.setStandardOID(28);
      this.rdnDistributionPoint = var1;
      this.userCerts = var2;
      this.caCerts = var3;
      this.reasonFlags = var4;
      this.indirectCRL = var5;
      this.attributeCerts = var6;
      this.extensionTypeString = "IssuingDistributionPoint";
   }

   /** @deprecated */
   public IssuingDistributionPoint(GeneralNames var1, boolean var2, boolean var3, int var4, boolean var5, boolean var6, boolean var7) {
      this.extensionTypeFlag = 28;
      this.criticality = var7;
      this.setStandardOID(28);
      this.genDistributionPoint = var1;
      this.userCerts = var2;
      this.caCerts = var3;
      this.reasonFlags = var4;
      this.indirectCRL = var5;
      this.attributeCerts = var6;
      this.extensionTypeString = "IssuingDistributionPoint";
   }

   /** @deprecated */
   public void decodeValue(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            SequenceContainer var3 = new SequenceContainer(0);
            EndContainer var4 = new EndContainer();
            ChoiceContainer var5 = new ChoiceContainer(8454144);
            EncodedContainer var6 = new EncodedContainer(8400896);
            EncodedContainer var7 = new EncodedContainer(8401153);
            BooleanContainer var8 = new BooleanContainer(8519681);
            BooleanContainer var9 = new BooleanContainer(8519682);
            BitStringContainer var10 = new BitStringContainer(8454147);
            BooleanContainer var11 = new BooleanContainer(8519684);
            BooleanContainer var12 = new BooleanContainer(8519685);
            ASN1Container[] var13 = new ASN1Container[]{var3, var5, var6, var7, var4, var8, var9, var10, var11, var12, var4};
            ASN1.berDecode(var1, var2, var13);
            if (var6.dataPresent) {
               this.genDistributionPoint = new GeneralNames(var6.data, var6.dataOffset, 8388608);
            } else if (var7.dataPresent) {
               this.rdnDistributionPoint = new RDN(var7.data, var7.dataOffset, 8388609);
            } else {
               this.genDistributionPoint = null;
               this.rdnDistributionPoint = null;
            }

            this.userCerts = var8.dataPresent && var8.value;
            this.caCerts = var9.dataPresent && var9.value;
            if (var10.dataPresent) {
               if (var10.dataLen > 4) {
                  throw new CertificateException("Could not decode IssuingDistributionPoint extension.");
               }

               if (var10.dataLen == 0) {
                  this.reasonFlags = 0;
               } else {
                  int var14 = 0;
                  int var15 = var10.dataOffset;

                  for(int var16 = 24; var15 < var10.dataOffset + var10.dataLen; var16 -= 8) {
                     var14 |= (var10.data[var15] & 255) << var16;
                     ++var15;
                  }

                  var14 &= -8388608;
                  this.reasonFlags = var14;
               }
            } else {
               this.reasonFlags = -1;
            }

            this.indirectCRL = var11.dataPresent && var11.value;
            this.attributeCerts = var12.dataPresent && var12.value;
         } catch (ASN_Exception var17) {
            throw new CertificateException("Could not decode IssuingDistributionPoint extension.");
         } catch (NameException var18) {
            throw new CertificateException("Could not create new GeneralNames object.");
         }
      }
   }

   /** @deprecated */
   public void setIssuingDistributionPointName(GeneralNames var1) {
      this.genDistributionPoint = var1;
      this.rdnDistributionPoint = null;
   }

   /** @deprecated */
   public void setIssuingDistributionPointName(RDN var1) {
      this.rdnDistributionPoint = var1;
      this.genDistributionPoint = null;
   }

   /** @deprecated */
   public void setUserCerts(boolean var1) {
      this.userCerts = var1;
   }

   /** @deprecated */
   public void setCACerts(boolean var1) {
      this.caCerts = var1;
   }

   /** @deprecated */
   public void setReasonFlags(int var1) {
      this.reasonFlags = var1;
   }

   /** @deprecated */
   public void setIndirectCRL(boolean var1) {
      this.indirectCRL = var1;
   }

   /** @deprecated */
   public void setAttributeCerts(boolean var1) {
      this.attributeCerts = var1;
   }

   /** @deprecated */
   public Object getDistributionPointName() {
      return this.rdnDistributionPoint != null ? this.rdnDistributionPoint : this.genDistributionPoint;
   }

   /** @deprecated */
   public boolean getUserCerts() {
      return this.userCerts;
   }

   /** @deprecated */
   public boolean getCACerts() {
      return this.caCerts;
   }

   /** @deprecated */
   public int getReasonFlags() {
      return this.reasonFlags;
   }

   /** @deprecated */
   public boolean getIndirectCRL() {
      return this.indirectCRL;
   }

   /** @deprecated */
   public boolean getAttributeCerts() {
      return this.attributeCerts;
   }

   /** @deprecated */
   public int derEncodeValueInit() {
      byte var1 = 0;
      SequenceContainer var2 = new SequenceContainer(0, true, 0);
      ChoiceContainer var3 = null;
      BitStringContainer var4 = null;
      EncodedContainer var5 = null;
      EndContainer var10 = new EndContainer();
      if (this.genDistributionPoint != null || this.rdnDistributionPoint != null) {
         var3 = new ChoiceContainer(8454144, 0);

         try {
            var5 = this.encodeName();
         } catch (CertificateException var16) {
            return 0;
         }

         var1 = 1;
      }

      if (this.reasonFlags != -1) {
         var4 = new BitStringContainer(8454147, true, 0, this.reasonFlags, 9, true);
         if (var1 == 0) {
            var1 = 2;
         } else {
            var1 = 3;
         }
      }

      BooleanContainer var6 = this.createBooleanContainer(8519681, this.userCerts);
      BooleanContainer var7 = this.createBooleanContainer(8519682, this.caCerts);
      BooleanContainer var8 = this.createBooleanContainer(8519684, this.indirectCRL);
      BooleanContainer var9 = this.createBooleanContainer(8519685, this.attributeCerts);
      switch (var1) {
         case 0:
            ASN1Container[] var11 = new ASN1Container[]{var2, var6, var7, var8, var9, var10};
            this.asn1TemplateValue = new ASN1Template(var11);
            break;
         case 1:
            ASN1Container[] var12 = new ASN1Container[]{var2, var3, var5, var10, var6, var7, var8, var9, var10};
            this.asn1TemplateValue = new ASN1Template(var12);
            break;
         case 2:
            ASN1Container[] var13 = new ASN1Container[]{var2, var6, var7, var4, var8, var9, var10};
            this.asn1TemplateValue = new ASN1Template(var13);
            break;
         case 3:
            ASN1Container[] var14 = new ASN1Container[]{var2, var3, var5, var10, var6, var7, var4, var8, var9, var10};
            this.asn1TemplateValue = new ASN1Template(var14);
      }

      try {
         return this.asn1TemplateValue.derEncodeInit();
      } catch (ASN_Exception var15) {
         return 0;
      }
   }

   private BooleanContainer createBooleanContainer(int var1, boolean var2) {
      return new BooleanContainer(var1, var2, 0, var2);
   }

   private EncodedContainer encodeName() throws CertificateException {
      EncodedContainer var1 = null;

      try {
         int var2;
         byte[] var3;
         if (this.genDistributionPoint != null) {
            var2 = this.genDistributionPoint.getDERLen(8388608);
            var3 = new byte[var2];
            var2 = this.genDistributionPoint.getDEREncoding(var3, 0, 8388608);
            var1 = new EncodedContainer(12288, true, 0, var3, 0, var2);
         } else if (this.rdnDistributionPoint != null) {
            var2 = this.rdnDistributionPoint.getDERLen(8388609);
            var3 = new byte[var2];
            var2 = this.rdnDistributionPoint.getDEREncoding(var3, 0, 8388609);
            var1 = new EncodedContainer(12544, true, 0, var3, 0, var2);
         }

         return var1;
      } catch (ASN_Exception var5) {
         throw new CertificateException("Can't encode DistributionPointNames", var5);
      } catch (NameException var6) {
         throw new CertificateException("Can't encode DistributionPointNames", var6);
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
      IssuingDistributionPoint var1 = new IssuingDistributionPoint();
      if (this.genDistributionPoint != null) {
         var1.genDistributionPoint = (GeneralNames)this.genDistributionPoint.clone();
      }

      if (this.rdnDistributionPoint != null) {
         var1.rdnDistributionPoint = (RDN)this.rdnDistributionPoint.clone();
      }

      var1.reasonFlags = this.reasonFlags;
      var1.userCerts = this.userCerts;
      var1.caCerts = this.caCerts;
      var1.indirectCRL = this.indirectCRL;
      var1.attributeCerts = this.attributeCerts;
      if (this.asn1TemplateValue != null) {
         var1.derEncodeValueInit();
      }

      super.copyValues(var1);
      return var1;
   }

   /** @deprecated */
   protected void reset() {
      super.reset();
      this.genDistributionPoint = null;
      this.rdnDistributionPoint = null;
      this.reasonFlags = 0;
      this.userCerts = false;
      this.caCerts = false;
      this.indirectCRL = false;
      this.attributeCerts = false;
      this.asn1TemplateValue = null;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof IssuingDistributionPoint)) {
         return false;
      } else {
         IssuingDistributionPoint var2 = (IssuingDistributionPoint)var1;
         if (this.genDistributionPoint == null && var2.genDistributionPoint != null || this.genDistributionPoint != null && var2.genDistributionPoint == null) {
            return false;
         } else if (this.genDistributionPoint != null && !this.genDistributionPoint.equals(var2.genDistributionPoint)) {
            return false;
         } else if (this.rdnDistributionPoint == null && var2.rdnDistributionPoint != null || this.rdnDistributionPoint != null && var2.rdnDistributionPoint == null) {
            return false;
         } else if (this.rdnDistributionPoint != null && !this.rdnDistributionPoint.equals(var2.rdnDistributionPoint)) {
            return false;
         } else {
            return this.reasonFlags == var2.reasonFlags && this.userCerts == var2.userCerts && this.caCerts == var2.caCerts && this.indirectCRL == var2.indirectCRL && this.attributeCerts == var2.attributeCerts;
         }
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = 0;
      if (this.genDistributionPoint != null) {
         var1 = this.genDistributionPoint.hashCode();
      }

      if (this.rdnDistributionPoint != null) {
         var1 ^= this.rdnDistributionPoint.hashCode();
      }

      String var2 = this.userCerts + String.valueOf(this.caCerts) + this.indirectCRL + this.attributeCerts;
      var1 ^= var2.hashCode();
      return var1;
   }
}
