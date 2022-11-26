package com.rsa.certj.crmf;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.AlgorithmID;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.GenTimeContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.asn1.UTCTimeContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.x.b;
import com.rsa.certj.x.d;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/** @deprecated */
public class CertTemplate implements Serializable, Cloneable {
   private int theVersion = -1;
   private X500Name subjectName;
   private X500Name issuerName;
   private byte[] serialNumber;
   /** @deprecated */
   protected byte[] subjectPublicKeyInfo;
   /** @deprecated */
   protected byte[] signatureAlgorithmBER;
   private byte[] issuerUniqueID;
   private byte[] subjectUniqueID;
   private boolean timeType;
   private Date notBefore;
   private Date notAfter;
   private X509V3Extensions theExtensions;
   /** @deprecated */
   protected int special;
   private ASN1Template asn1Template;
   private ASN1Template asn1TemplateValidity;
   /** @deprecated */
   public static final int RSA_WITH_SHA1_PKCS = 0;
   /** @deprecated */
   public static final int RSA_WITH_SHA1_ISO_OIW = 1;
   /** @deprecated */
   public static final int DSA_WITH_SHA1_X930 = 2;
   /** @deprecated */
   public static final int DSA_WITH_SHA1_X957 = 3;
   /** @deprecated */
   protected int signatureAlgorithmFormat = -1;

   /** @deprecated */
   public CertTemplate() {
   }

   /** @deprecated */
   public CertTemplate(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("CertTemplate Encoding is null.");
      } else {
         this.setCertTemplateBER(var1, var2, var3);
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CRMFException {
      if (var0 == null) {
         throw new CRMFException("CertTemplate Encoding is null.");
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new CRMFException("Could not read the BER encoding.", var3);
         }
      }
   }

   /** @deprecated */
   public void setCertTemplateBER(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("CertTemplate Encoding is null.");
      } else {
         SequenceContainer var4 = new SequenceContainer(var3);
         EndContainer var5 = new EndContainer();
         IntegerContainer var6 = new IntegerContainer(8454144);
         IntegerContainer var7 = new IntegerContainer(8454145);
         EncodedContainer var8 = new EncodedContainer(8466434);
         EncodedContainer var9 = new EncodedContainer(10563587);
         EncodedContainer var10 = new EncodedContainer(8466436);
         EncodedContainer var11 = new EncodedContainer(10563589);
         EncodedContainer var12 = new EncodedContainer(8466438);
         EncodedContainer var13 = new EncodedContainer(8454919);
         EncodedContainer var14 = new EncodedContainer(8454920);
         EncodedContainer var15 = new EncodedContainer(8466441);
         ASN1Container[] var16 = new ASN1Container[]{var4, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var5};

         try {
            ASN1.berDecode(var1, var2, var16);
         } catch (ASN_Exception var21) {
            throw new CRMFException("Could not BER decode the cert template info. ", var21);
         }

         if (var6.dataPresent) {
            this.setVersionNumber(var6.data, var6.dataOffset, var6.dataLen);
         }

         if (var7.dataPresent) {
            this.setSerialNumber(var7.data, var7.dataOffset, var7.dataLen);
         }

         if (var8.dataPresent) {
            this.setSignatureAlgorithm(var8.data, var8.dataOffset, var8.dataLen);
         }

         X500Name var17;
         if (var9.dataPresent) {
            try {
               var17 = new X500Name(var9.data, var9.dataOffset, 10551299);
               this.setIssuerName(var17);
            } catch (NameException var20) {
               throw new CRMFException("Invalid issuer name: ", var20);
            }
         }

         if (var10.dataPresent) {
            this.setValidityBER(var10.data, var10.dataOffset);
         }

         if (var11.dataPresent) {
            try {
               var17 = new X500Name(var11.data, var11.dataOffset, 10551301);
               this.setSubjectName(var17);
            } catch (NameException var19) {
               throw new CRMFException("Invalid subject name: ", var19);
            }
         }

         if (var12.dataPresent) {
            this.setSubjectPublicKey(var12.data, var12.dataOffset);
         }

         if (var13.dataPresent) {
            this.issuerUniqueID = new byte[var13.dataLen];
            System.arraycopy(var13.data, var13.dataOffset, this.issuerUniqueID, 0, var13.dataLen);
         }

         if (var14.dataPresent) {
            this.subjectUniqueID = new byte[var14.dataLen];
            System.arraycopy(var14.data, var14.dataOffset, this.subjectUniqueID, 0, var14.dataLen);
         }

         try {
            if (var15.dataPresent) {
               X509V3Extensions var22 = new X509V3Extensions(var15.data, var15.dataOffset, 8388617, 1);
               this.setExtensions(var22);
            }

         } catch (CertificateException var18) {
            throw new CRMFException("Cannot decode extensions. ", var18);
         }
      }
   }

   /** @deprecated */
   protected void setVersionNumber(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 <= 4) {
         int var4 = 0;

         for(int var5 = var2; var5 < var2 + var3; ++var5) {
            var4 = var4 << 8 | var1[var2] & 255;
         }

         this.theVersion = var4;
      } else {
         throw new CRMFException("Invalid Certificate version.");
      }
   }

   /** @deprecated */
   public void setVersion(int var1) {
      this.theVersion = var1;
   }

   /** @deprecated */
   public int getVersion() {
      return this.theVersion;
   }

   /** @deprecated */
   public void setSignatureAlgorithm(String var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Specified SignatureAlgorithm value is null.");
      } else {
         try {
            String var2 = this.getSignatureFormat(var1);
            if (var2 == null) {
               this.signatureAlgorithmBER = AlgorithmID.derEncodeAlgID(var1, 1, (byte[])null, 0, 0);
            } else {
               this.signatureAlgorithmBER = AlgorithmID.derEncodeAlgID(var2, 1, (byte[])null, 0, 0);
            }

         } catch (ASN_Exception var3) {
            throw new CRMFException("Cannot set, unknown algorithm.", var3);
         }
      }
   }

   /** @deprecated */
   public void setSignatureAlgorithm(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         try {
            this.signatureAlgorithmBER = new byte[var3];
            var1[var2] = 48;
            System.arraycopy(var1, var2, this.signatureAlgorithmBER, 0, var3);
            String var4 = AlgorithmID.berDecodeAlgID(var1, var2, 1, (EncodedContainer)null);
            if (var4 == null) {
               throw new CRMFException("Cannot recognize the signature algorithm.");
            }
         } catch (ASN_Exception var5) {
            throw new CRMFException(var5);
         }
      } else {
         throw new CRMFException("Signature Algorithm is null.");
      }
   }

   /** @deprecated */
   public void setSignatureStandard(int var1) {
      this.signatureAlgorithmFormat = var1;
   }

   /** @deprecated */
   public int getSignatureStandard() {
      return this.signatureAlgorithmFormat;
   }

   /** @deprecated */
   public String getSignatureFormat(String var1) {
      if (var1 == null) {
         return null;
      } else {
         switch (this.signatureAlgorithmFormat) {
            case 0:
               if (var1.equals("SHA1/RSA/PKCS1Block01Pad")) {
                  return "RSAWithSHA1PKCS";
               }

               return null;
            case 1:
               if (var1.equals("SHA1/RSA/PKCS1Block01Pad")) {
                  return "RSAWithSHA1ISO_OIW";
               }

               return null;
            case 2:
               if (!var1.equals("SHA1/DSA") && !var1.equals("SHA1/DSA/NoPad")) {
                  return null;
               }

               return "DSAWithSHA1X930";
            case 3:
               if (!var1.equals("SHA1/DSA") && !var1.equals("SHA1/DSA/NoPad")) {
                  return null;
               }

               return "DSAWithSHA1X957";
            default:
               return var1.equals("SHA1/DSA") ? "SHA1/DSA/NoPad" : null;
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) throws CRMFException {
      return this.encodeInit(var1);
   }

   private int encodeInit(int var1) throws CRMFException {
      this.special = var1;
      byte[] var2 = null;

      try {
         SequenceContainer var4 = new SequenceContainer(var1, true, 0);
         EndContainer var5 = new EndContainer();
         boolean var6 = false;
         if (this.theVersion != -1) {
            var6 = true;
         }

         IntegerContainer var7 = new IntegerContainer(8454144, var6, 0, this.theVersion);
         IntegerContainer var8;
         if (this.serialNumber == null) {
            var8 = new IntegerContainer(8454145, false, 0, 0);
         } else if ((this.serialNumber[0] & 128) >> 7 == 0) {
            var8 = new IntegerContainer(8454145, true, 0, this.serialNumber, 0, this.serialNumber.length, true);
         } else {
            var8 = new IntegerContainer(8454145, true, 0, this.serialNumber, 0, this.serialNumber.length, false);
         }

         int var3;
         if (this.signatureAlgorithmBER == null) {
            var6 = false;
            var3 = 0;
         } else {
            var6 = true;
            var3 = this.signatureAlgorithmBER.length;
            this.signatureAlgorithmBER[0] = -94;
         }

         EncodedContainer var9 = new EncodedContainer(8466434, var6, 0, this.signatureAlgorithmBER, 0, var3);
         if (this.notBefore == null && this.notAfter == null) {
            var6 = false;
            var3 = 0;
         } else {
            var3 = this.getValidityDERLen(8454148);
            var6 = true;
            var2 = new byte[var3];
            var3 = this.getValidityDEREncoding(var2, 0, 8454148);
         }

         EncodedContainer var10 = new EncodedContainer(8466436, var6, 0, var2, 0, var3);
         if (this.subjectPublicKeyInfo != null) {
            var6 = true;
            this.subjectPublicKeyInfo[0] = -90;
            var3 = this.subjectPublicKeyInfo.length;
         } else {
            var6 = false;
            var3 = 0;
         }

         EncodedContainer var11 = new EncodedContainer(8466438, var6, 0, this.subjectPublicKeyInfo, 0, var3);

         try {
            if (this.issuerName != null) {
               var3 = this.issuerName.getDERLen(10551299);
               var2 = new byte[var3];
               var3 = this.issuerName.getDEREncoding(var2, 0, 10551299);
               var6 = true;
            } else {
               var6 = false;
               var3 = 0;
            }
         } catch (NameException var19) {
            throw new CRMFException(var19);
         }

         EncodedContainer var12 = new EncodedContainer(10563587, var6, 0, var2, 0, var3);

         try {
            if (this.subjectName != null) {
               var3 = this.subjectName.getDERLen(10551301);
               var2 = new byte[var3];
               var3 = this.subjectName.getDEREncoding(var2, 0, 10551301);
               var6 = true;
            } else {
               var6 = false;
               var3 = 0;
            }
         } catch (NameException var18) {
            throw new CRMFException(var18);
         }

         EncodedContainer var13 = new EncodedContainer(10563589, var6, 0, var2, 0, var3);
         if (this.issuerUniqueID != null) {
            var6 = true;
            var3 = this.issuerUniqueID.length;
         } else {
            var6 = false;
            var3 = 0;
         }

         EncodedContainer var14 = new EncodedContainer(8454919, var6, 0, this.issuerUniqueID, 0, var3);
         if (this.subjectUniqueID != null) {
            var6 = true;
            var3 = this.subjectUniqueID.length;
         } else {
            var6 = false;
            var3 = 0;
         }

         EncodedContainer var15 = new EncodedContainer(8454920, var6, 0, this.subjectUniqueID, 0, var3);
         if (this.theExtensions != null) {
            var3 = this.theExtensions.getDERLen(8466441);
            var6 = true;
            var2 = new byte[var3];
            var3 = this.theExtensions.getDEREncoding(var2, 0, 8466441);
         } else {
            var6 = false;
            var3 = 0;
         }

         EncodedContainer var16 = new EncodedContainer(8466441, var6, 0, var2, 0, var3);
         ASN1Container[] var17 = new ASN1Container[]{var4, var7, var8, var9, var12, var10, var13, var11, var14, var15, var16, var5};
         this.asn1Template = new ASN1Template(var17);
         return this.asn1Template.derEncodeInit();
      } catch (ASN_Exception var20) {
         throw new CRMFException(var20);
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Passed array is null in the cert template ");
      } else {
         try {
            int var4;
            if (this.asn1Template == null) {
               var4 = this.encodeInit(var3);
               if (var4 == 0) {
                  throw new CRMFException("Cannot encode cert template, information missing.");
               }
            }

            var4 = this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
            return var4;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new CRMFException("Could not encode the cert template: ", var6);
         }
      }
   }

   private int getValidityDERLen(int var1) throws CRMFException {
      SequenceContainer var2 = new SequenceContainer(var1, true, 0);
      EndContainer var3 = new EndContainer();
      if (this.notBefore == null && this.notAfter == null) {
         throw new CRMFException("Validity dates are not set.");
      } else {
         boolean var6 = false;
         boolean var7 = false;
         if (this.notBefore != null) {
            var6 = true;
         }

         if (this.notAfter != null) {
            var7 = true;
         }

         Object var4;
         Object var5;
         if (this.timeType) {
            var4 = new GenTimeContainer(10551296, var6, 0, this.notBefore);
            var5 = new GenTimeContainer(10551297, var7, 0, this.notAfter);
         } else {
            var4 = new UTCTimeContainer(10551296, var6, 0, this.notBefore);
            var5 = new UTCTimeContainer(10551297, var7, 0, this.notAfter);
         }

         ASN1Container[] var8 = new ASN1Container[]{var2, (ASN1Container)var4, (ASN1Container)var5, var3};
         this.asn1TemplateValidity = new ASN1Template(var8);

         try {
            return this.asn1TemplateValidity.derEncodeInit();
         } catch (ASN_Exception var10) {
            throw new CRMFException(var10);
         }
      }
   }

   /** @deprecated */
   public Date getStartDate() {
      return this.notBefore == null ? null : new Date(this.notBefore.getTime());
   }

   /** @deprecated */
   public Date getEndDate() {
      return this.notAfter == null ? null : new Date(this.notAfter.getTime());
   }

   /** @deprecated */
   public void setTimeType(boolean var1) {
      this.timeType = var1;
   }

   private int getValidityDEREncoding(byte[] var1, int var2, int var3) throws CRMFException {
      if (this.asn1TemplateValidity == null && this.getValidityDERLen(var3) == 0) {
         return 0;
      } else {
         try {
            int var4 = this.asn1TemplateValidity.derEncode(var1, var2);
            this.asn1TemplateValidity = null;
            return var4;
         } catch (ASN_Exception var5) {
            this.asn1TemplateValidity = null;
            throw new CRMFException(var5);
         }
      }
   }

   private void setValidityBER(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("The cert template encoding is null.");
      } else {
         SequenceContainer var3 = new SequenceContainer(8454148);
         EndContainer var4 = new EndContainer();
         ChoiceContainer var5 = new ChoiceContainer(10551296);
         ChoiceContainer var6 = new ChoiceContainer(10551297);
         UTCTimeContainer var7 = new UTCTimeContainer(0);
         UTCTimeContainer var8 = new UTCTimeContainer(0);
         GenTimeContainer var9 = new GenTimeContainer(0);
         GenTimeContainer var10 = new GenTimeContainer(0);
         ASN1Container[] var11 = new ASN1Container[]{var3, var5, var7, var9, var4, var6, var8, var10, var4, var4};

         try {
            ASN1.berDecode(var1, var2, var11);
         } catch (ASN_Exception var14) {
            throw new CRMFException("Cannot extract Validity. ", var14);
         }

         Date var12 = var9.theTime;
         if (!var9.dataPresent) {
            var12 = var7.theTime;
         } else {
            this.timeType = true;
         }

         Date var13 = var10.theTime;
         if (!var10.dataPresent) {
            var13 = var8.theTime;
         } else {
            this.timeType = true;
         }

         this.setValidity(var12, var13);
      }
   }

   /** @deprecated */
   public void setValidity(Date var1, Date var2) throws CRMFException {
      if (var1 == null && var2 == null) {
         throw new CRMFException("Cannot set the validity with the NULL dates.");
      } else {
         if (var1 != null) {
            this.notBefore = new Date(var1.getTime());
         }

         if (var2 != null) {
            this.notAfter = new Date(var2.getTime());
         }

         if (this.notAfter != null && this.notBefore != null && !this.notAfter.after(this.notBefore)) {
            throw new CRMFException("Cannot set the validity with the given dates.");
         }
      }
   }

   /** @deprecated */
   public void setSerialNumber(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         this.serialNumber = new byte[var3];
         System.arraycopy(var1, var2, this.serialNumber, 0, var3);
      } else {
         throw new CRMFException("Passed in SerialNumber value is null.");
      }
   }

   /** @deprecated */
   public byte[] getSerialNumber() {
      return this.serialNumber == null ? null : (byte[])this.serialNumber.clone();
   }

   /** @deprecated */
   public void setIssuerName(X500Name var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Passed in IssuerName value is null.");
      } else {
         try {
            this.issuerName = (X500Name)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CRMFException("Cannot set the cert template with the given issuerName.", var3);
         }
      }
   }

   /** @deprecated */
   public X500Name getIssuerName() {
      if (this.issuerName == null) {
         return null;
      } else {
         try {
            return (X500Name)this.issuerName.clone();
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   /** @deprecated */
   public void setSubjectName(X500Name var1) throws CRMFException {
      this.setSubjectName(var1, b.c());
   }

   /** @deprecated */
   public void setSubjectName(X500Name var1, boolean var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Passed in SubjectName value is null.");
      } else {
         try {
            this.subjectName = (X500Name)var1.clone();
            if (var2) {
               d.a(this.subjectName);
            }

         } catch (CloneNotSupportedException var4) {
            throw new CRMFException("Cannot set the cert template with the given subjectName.", var4);
         }
      }
   }

   /** @deprecated */
   public X500Name getSubjectName() {
      if (this.subjectName == null) {
         return null;
      } else {
         try {
            return (X500Name)this.subjectName.clone();
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   /** @deprecated */
   public void setIssuerUniqueID(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         try {
            BitStringContainer var4 = new BitStringContainer(8388615, true, 0, var1, var2, var3, var3 * 8, false);
            ASN1Container[] var5 = new ASN1Container[]{var4};
            this.issuerUniqueID = ASN1.derEncode(var5);
         } catch (ASN_Exception var6) {
            throw new CRMFException("Cannot set issuerUniqueID: ", var6);
         }
      } else {
         throw new CRMFException("Passed in IssuerUniqueID value is null.");
      }
   }

   /** @deprecated */
   public byte[] getIssuerUniqueID() {
      if (this.issuerUniqueID == null) {
         return null;
      } else {
         int var1;
         try {
            var1 = 2 + ASN1Lengths.determineLengthLen(this.issuerUniqueID, 1);
         } catch (ASN_Exception var3) {
            return null;
         }

         byte[] var2 = new byte[this.issuerUniqueID.length - var1];
         System.arraycopy(this.issuerUniqueID, var1, var2, 0, var2.length);
         return var2;
      }
   }

   /** @deprecated */
   public void setSubjectUniqueID(byte[] var1, int var2, int var3) throws CRMFException {
      if (var1 != null && var3 != 0) {
         try {
            BitStringContainer var4 = new BitStringContainer(8388616, true, 0, var1, var2, var3, var3 * 8, false);
            ASN1Container[] var5 = new ASN1Container[]{var4};
            this.subjectUniqueID = ASN1.derEncode(var5);
         } catch (ASN_Exception var6) {
            throw new CRMFException("Cannot set subjectUniqueID: ", var6);
         }
      } else {
         throw new CRMFException("Passed in SubjectUniqueID value is null.");
      }
   }

   /** @deprecated */
   public byte[] getSubjectUniqueID() {
      if (this.subjectUniqueID == null) {
         return null;
      } else {
         int var1;
         try {
            var1 = 2 + ASN1Lengths.determineLengthLen(this.subjectUniqueID, 1);
         } catch (ASN_Exception var3) {
            return null;
         }

         byte[] var2 = new byte[this.subjectUniqueID.length - var1];
         System.arraycopy(this.subjectUniqueID, var1, var2, 0, var2.length);
         return var2;
      }
   }

   /** @deprecated */
   public String getSignatureAlgorithm() throws CRMFException {
      try {
         if (this.signatureAlgorithmBER == null) {
            throw new CRMFException("Object not set with signature algorithm.");
         } else {
            this.signatureAlgorithmBER[0] = 48;
            return AlgorithmID.berDecodeAlgID(this.signatureAlgorithmBER, 0, 1, (EncodedContainer)null);
         }
      } catch (ASN_Exception var2) {
         throw new CRMFException("Invalid Signature Algorithm.", var2);
      }
   }

   /** @deprecated */
   public byte[] getSignatureAlgorithmDER() throws CRMFException {
      if (this.signatureAlgorithmBER == null) {
         throw new CRMFException("Object not set with signature algorithm.");
      } else {
         this.signatureAlgorithmBER[0] = 48;
         return (byte[])this.signatureAlgorithmBER.clone();
      }
   }

   /** @deprecated */
   public void setSubjectPublicKey(JSAFE_PublicKey var1) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Public key is null.");
      } else {
         try {
            String var2;
            if (var1.getAlgorithm().compareTo("DSA") == 0) {
               var2 = "DSAPublicKeyX957BER";
            } else {
               var2 = var1.getAlgorithm() + "PublicKeyBER";
            }

            byte[][] var3 = var1.getKeyData(var2);
            this.subjectPublicKeyInfo = var3[0];
         } catch (JSAFE_Exception var4) {
            throw new CRMFException("Could not read the public key. ", var4);
         }
      }
   }

   /** @deprecated */
   public void setSubjectPublicKey(byte[] var1, int var2) throws CRMFException {
      if (var1 == null) {
         throw new CRMFException("Public key encoding is null.");
      } else {
         JSAFE_PublicKey var3 = null;

         try {
            var1[var2] = 48;
            var3 = h.a(var1, var2, "Java", (CertJ)null);
            this.setSubjectPublicKey(var3);
         } catch (JSAFE_Exception var8) {
            throw new CRMFException("Could not read the public key. ", var8);
         } finally {
            if (var3 != null) {
               var3.clearSensitiveData();
            }

         }

      }
   }

   /** @deprecated */
   public JSAFE_PublicKey getSubjectPublicKey() throws CRMFException {
      if (this.subjectPublicKeyInfo == null) {
         return null;
      } else {
         try {
            this.subjectPublicKeyInfo[0] = 48;
            return h.a(this.subjectPublicKeyInfo, 0, "Java", (CertJ)((CertJ)null));
         } catch (JSAFE_Exception var2) {
            throw new CRMFException("Cannot retrieve the public key: ", var2);
         }
      }
   }

   /** @deprecated */
   public byte[] getSubjectPublicKeyBER() {
      if (this.subjectPublicKeyInfo == null) {
         return null;
      } else {
         this.subjectPublicKeyInfo[0] = 48;
         return (byte[])this.subjectPublicKeyInfo.clone();
      }
   }

   /** @deprecated */
   public void setExtensions(X509V3Extensions var1) throws CRMFException {
      try {
         if (var1 == null) {
            throw new CRMFException("Extensions are null.");
         } else if (var1.getExtensionsType() != 1) {
            throw new CRMFException("Wrong extensions type: should be Cert extensions.");
         } else {
            this.theExtensions = (X509V3Extensions)var1.clone();
         }
      } catch (CloneNotSupportedException var3) {
         throw new CRMFException("Cannot set the cert with the given extensions.", var3);
      }
   }

   /** @deprecated */
   public X509V3Extensions getExtensions() {
      if (this.theExtensions == null) {
         return null;
      } else {
         try {
            return (X509V3Extensions)this.theExtensions.clone();
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof CertTemplate) {
         CertTemplate var2 = (CertTemplate)var1;

         try {
            int var3 = this.getDERLen(0);
            int var4 = var2.getDERLen(0);
            if (var3 != var4) {
               return false;
            } else {
               byte[] var5 = new byte[var3];
               byte[] var6 = new byte[var4];
               var3 = this.getDEREncoding(var5, 0, 0);
               var4 = var2.getDEREncoding(var6, 0, 0);
               if (var3 != var4) {
                  return false;
               } else {
                  for(int var7 = 0; var7 < var3; ++var7) {
                     if (var5[var7] != var6[var7]) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         } catch (CRMFException var8) {
            return false;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var2 = 1;

      try {
         int var3 = this.getDERLen(0);
         byte[] var4 = new byte[var3];
         this.getDEREncoding(var4, 0, 0);
         var2 = 31 * var2 + Arrays.hashCode(var4);
         return var2;
      } catch (CRMFException var5) {
         return 0;
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      CertTemplate var1 = (CertTemplate)super.clone();
      if (this.subjectPublicKeyInfo != null) {
         var1.subjectPublicKeyInfo = (byte[])this.subjectPublicKeyInfo.clone();
      }

      if (this.signatureAlgorithmBER != null) {
         var1.signatureAlgorithmBER = (byte[])this.signatureAlgorithmBER.clone();
      }

      var1.theVersion = this.theVersion;
      var1.signatureAlgorithmFormat = this.signatureAlgorithmFormat;
      if (this.subjectName != null) {
         var1.subjectName = (X500Name)this.subjectName.clone();
      }

      if (this.issuerName != null) {
         var1.issuerName = (X500Name)this.issuerName.clone();
      }

      if (this.serialNumber != null) {
         var1.serialNumber = (byte[])this.serialNumber.clone();
      }

      if (this.issuerUniqueID != null) {
         var1.issuerUniqueID = (byte[])this.issuerUniqueID.clone();
      }

      if (this.subjectUniqueID != null) {
         var1.subjectUniqueID = (byte[])this.subjectUniqueID.clone();
      }

      var1.timeType = this.timeType;
      if (this.notBefore != null) {
         var1.notBefore = new Date(this.notBefore.getTime());
      }

      if (this.notAfter != null) {
         var1.notAfter = new Date(this.notAfter.getTime());
      }

      if (this.theExtensions != null) {
         var1.theExtensions = (X509V3Extensions)this.theExtensions.clone();
      }

      var1.special = this.special;

      try {
         if (this.asn1Template != null) {
            var1.encodeInit(this.special);
         }

         return var1;
      } catch (CRMFException var3) {
         throw new CloneNotSupportedException(var3.getMessage());
      }
   }
}
