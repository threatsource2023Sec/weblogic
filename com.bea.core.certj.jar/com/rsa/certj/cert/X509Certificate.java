package com.rsa.certj.cert;

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
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.attributes.V3ExtensionAttribute;
import com.rsa.certj.cert.attributes.X501Attribute;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.NotSerializableException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** @deprecated */
public class X509Certificate extends Certificate implements Serializable {
   private static final long serialVersionUID = -2111356029761194088L;
   private static final Date FIRST_GENERALIZED_TIME_DATE = new Date(2524608000000L);
   /** @deprecated */
   public static final int X509_VERSION_1 = 0;
   /** @deprecated */
   public static final int X509_VERSION_2 = 1;
   /** @deprecated */
   public static final int X509_VERSION_3 = 2;
   private byte[] innerDER;
   private int innerDERLen;
   private int theVersion;
   private X500Name subjectName;
   private X500Name issuerName;
   private byte[] serialNumber;
   private byte[] issuerUniqueID;
   private byte[] subjectUniqueID;
   private boolean timeType;
   private boolean timeTypeExplicitlySet;
   private Date notBefore;
   private Date notAfter;
   private X509V3Extensions theExtensions;
   private int special;
   private ASN1Template asn1Template;
   private int innerSpecial;
   private ASN1Template asn1TemplateInner;
   private ASN1Template asn1TemplateValidity;
   private final Lock outerDERLock;
   private final Lock innerDERLock;

   /** @deprecated */
   public X509Certificate() {
      this.theVersion = 2;
      this.special = 0;
      this.innerSpecial = 0;
      this.outerDERLock = new ReentrantLock();
      this.innerDERLock = new ReentrantLock();
   }

   /** @deprecated */
   public X509Certificate(CertJ var1) {
      this.theVersion = 2;
      this.special = 0;
      this.innerSpecial = 0;
      this.outerDERLock = new ReentrantLock();
      this.innerDERLock = new ReentrantLock();
      this.setCertJ(var1);
   }

   /** @deprecated */
   public X509Certificate(byte[] var1, int var2, int var3) throws CertificateException {
      this(var1, var2, var3, (CertJ)null);
   }

   /** @deprecated */
   public X509Certificate(byte[] var1, int var2, int var3, CertJ var4) throws CertificateException {
      this.theVersion = 2;
      this.special = 0;
      this.innerSpecial = 0;
      this.outerDERLock = new ReentrantLock();
      this.innerDERLock = new ReentrantLock();
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.setCertJ(var4);
         this.setCertBER(var1, var2, var3);
      }
   }

   private void checkSpecial(int var1) throws CertificateException {
      if (var1 != 0 && var1 != 4194304 && var1 != 6291456 && var1 != 12582912 && var1 != 14680064 && var1 != 65536 && var1 != 131072 && var1 != 8388608 && var1 != 10485760) {
         throw new CertificateException("Could not encode: Invalid 'special'");
      }
   }

   /** @deprecated */
   public static int getNextBEROffset(byte[] var0, int var1) throws CertificateException {
      if (var0 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         try {
            return var1 + 1 + ASN1Lengths.determineLengthLen(var0, var1 + 1) + ASN1Lengths.determineLength(var0, var1 + 1);
         } catch (ASN_Exception var3) {
            throw new CertificateException("Could not read the BER encoding.", var3);
         }
      }
   }

   private void setCertBER(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.clearComponents();
         ASN1Container[] var4 = decodeCert(var1, var2, var3);
         this.setInnerDER(var4[1].data, var4[1].dataOffset, this.special);
         this.signature = new byte[var4[3].dataLen];
         System.arraycopy(var4[3].data, var4[3].dataOffset, this.signature, 0, var4[3].dataLen);
         this.setSignatureAlgorithm(var4[2].data, var4[2].dataOffset, var4[2].dataLen);
      }
   }

   /** @deprecated */
   protected static ASN1Container[] decodeCert(byte[] var0, int var1, int var2) throws CertificateException {
      if (var0 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         SequenceContainer var3 = new SequenceContainer(var2);
         EndContainer var4 = new EndContainer();
         EncodedContainer var5 = new EncodedContainer(12288);
         EncodedContainer var6 = new EncodedContainer(12288);
         EncodedContainer var7 = new EncodedContainer(768);
         ASN1Container[] var8 = new ASN1Container[]{var3, var5, var6, var7, var4};

         try {
            ASN1.berDecode(var0, var1, var8);
            return var8;
         } catch (ASN_Exception var10) {
            throw new CertificateException("Could not BER decode the cert.", var10);
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) {
      this.outerDERLock.lock();

      byte var3;
      try {
         this.outerDERSetSpecial(var1);
         int var2 = this.outerDEREncodeInit();
         return var2;
      } catch (CertificateException var7) {
         var3 = 0;
      } finally {
         this.outerDERLock.unlock();
      }

      return var3;
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Specified array is null.");
      } else {
         this.outerDERLock.lock();

         int var6;
         try {
            int var4 = 0;

            int var5;
            try {
               this.outerDERSetSpecial(var3);
               if (this.asn1Template == null) {
                  var5 = this.outerDEREncodeInit();
                  if (var5 == 0) {
                     throw new CertificateException("Could not encode: Possibly some of the required fields of this certificate object are not set.");
                  }
               }

               var4 += this.asn1Template.derEncode(var1, var2);
               this.asn1Template = null;
            } catch (ASN_Exception var10) {
               this.asn1Template = null;
               throw new CertificateException("Could not encode: ", var10);
            }

            var5 = this.getInnerDER(var1, var2 + var4);
            if (var5 == 0) {
               throw new CertificateException("Could not encode, missing data.");
            }

            var4 += var5;
            System.arraycopy(this.signatureAlgorithmBER, 0, var1, var2 + var4, this.signatureAlgorithmBER.length);
            var4 += this.signatureAlgorithmBER.length;
            System.arraycopy(this.signature, 0, var1, var2 + var4, this.signature.length);
            var6 = var4 + this.signature.length;
         } finally {
            this.outerDERLock.unlock();
         }

         return var6;
      }
   }

   private int outerDEREncodeInit() {
      if (this.getInnerDERLen() == 0) {
         return 0;
      } else if (this.signatureAlgorithmBER != null && this.signature != null) {
         try {
            SequenceContainer var1 = new SequenceContainer(this.special, true, 0);
            EndContainer var2 = new EndContainer();
            EncodedContainer var3 = new EncodedContainer(12288, true, 0, (byte[])null, 0, this.innerDERLen);
            EncodedContainer var4 = new EncodedContainer(12288, true, 0, (byte[])null, 0, this.signatureAlgorithmBER.length);
            EncodedContainer var5 = new EncodedContainer(768, true, 0, (byte[])null, 0, this.signature.length);
            ASN1Container[] var6 = new ASN1Container[]{var1, var3, var4, var5, var2};
            this.asn1Template = new ASN1Template(var6);
            return this.asn1Template.derEncodeInit();
         } catch (ASN_Exception var7) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private void outerDERSetSpecial(int var1) throws CertificateException {
      if (var1 != this.special) {
         this.checkSpecial(var1);
         this.clearTemplate();
         this.special = var1;
      }

   }

   private void outerDERClear() {
      this.outerDERLock.lock();
      this.asn1Template = null;
      this.special = 0;
      this.outerDERLock.unlock();
   }

   /** @deprecated */
   protected void setVersionNumber(int var1) throws CertificateException {
      if (var1 != 0 && var1 != 1 && var1 != 2) {
         throw new CertificateException("Invalid X.509 Certificate version.");
      } else {
         this.theVersion = var1;
      }
   }

   private void setSignatureAlgorithm(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 != null && var3 != 0) {
         if (this.signatureAlgorithmBER == null) {
            this.signatureAlgorithmBER = new byte[var3];
            System.arraycopy(var1, var2, this.signatureAlgorithmBER, 0, var3);

            try {
               String var4 = AlgorithmID.berDecodeAlgID(var1, var2, 1, (EncodedContainer)null);
               if (var4 == null) {
                  throw new CertificateException("Unknown or invalid signature algorithm.");
               }
            } catch (ASN_Exception var5) {
               throw new CertificateException("Cannot decode signature algorithm.");
            }
         } else if (!CertJUtils.byteArraysEqual(var1, var2, var3, this.signatureAlgorithmBER)) {
            throw new CertificateException("Signature algorithms do not match.");
         }

      } else {
         throw new CertificateException("Unknown or invalid signature algorithm.");
      }
   }

   /** @deprecated */
   public void setInnerDER(byte[] var1, int var2) throws CertificateException {
      this.setInnerDER(var1, var2, 0);
   }

   private void setInnerDER(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.clearSignature();
         this.clearTemplate();
         this.innerSpecial = var3;
         SequenceContainer var4 = new SequenceContainer(this.innerSpecial);
         EndContainer var5 = new EndContainer();
         IntegerContainer var6 = new IntegerContainer(10616832);
         IntegerContainer var7 = new IntegerContainer(0);
         EncodedContainer var8 = new EncodedContainer(12288);
         EncodedContainer var9 = new EncodedContainer(12288);
         EncodedContainer var10 = new EncodedContainer(12288);
         EncodedContainer var11 = new EncodedContainer(12288);
         EncodedContainer var12 = new EncodedContainer(12288);
         EncodedContainer var13 = new EncodedContainer(8454913);
         EncodedContainer var14 = new EncodedContainer(8454914);
         EncodedContainer var15 = new EncodedContainer(10563587);
         ASN1Container[] var16 = new ASN1Container[]{var4, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var5};

         try {
            ASN1.berDecode(var1, var2, var16);
         } catch (ASN_Exception var21) {
            throw new CertificateException("Could not BER decode the cert info.", var21);
         }

         if (var6.dataPresent) {
            try {
               this.setVersionNumber(var6.getValueAsInt());
            } catch (ASN_Exception var20) {
               throw new CertificateException("Invalid version number: ", var20);
            }
         }

         this.setSerialNumber(var7.data, var7.dataOffset, var7.dataLen);
         this.setSignatureAlgorithm(var8.data, var8.dataOffset, var8.dataLen);

         try {
            this.setIssuerName(new X500Name(var9.data, var9.dataOffset, 0));
         } catch (NameException var19) {
            throw new CertificateException("Invalid issuer name: ", var19);
         }

         this.setValidityBER(var10.data, var10.dataOffset);

         try {
            this.setSubjectName(new X500Name(var11.data, var11.dataOffset, 0));
         } catch (NameException var18) {
            throw new CertificateException("Invalid subject name: ", var18);
         }

         this.setSubjectPublicKey(var12.data, var12.dataOffset);
         if (var13.dataPresent) {
            if (this.theVersion == 0) {
               throw new CertificateException("Version 1 certs not allowed to have issuer unique ID.");
            }

            this.issuerUniqueID = new byte[var13.dataLen];
            System.arraycopy(var13.data, var13.dataOffset, this.issuerUniqueID, 0, var13.dataLen);
         }

         if (var14.dataPresent) {
            if (this.theVersion == 0) {
               throw new CertificateException("Version 1 certs not allowed to have subject unique ID.");
            }

            this.subjectUniqueID = new byte[var14.dataLen];
            System.arraycopy(var14.data, var14.dataOffset, this.subjectUniqueID, 0, var14.dataLen);
         }

         if (var15.dataPresent) {
            X509V3Extensions var17 = new X509V3Extensions(var15.data, var15.dataOffset, 10485763, 1);
            this.setExtensions(var17);
         }

         this.innerDERLen = getNextBEROffset(var1, var2) - var2;
         this.innerDER = new byte[this.innerDERLen];
         System.arraycopy(var1, var2, this.innerDER, 0, this.innerDERLen);
      }
   }

   /** @deprecated */
   public int getInnerDERLen() {
      this.innerDERLock.lock();

      try {
         int var1;
         if (this.innerDERLen != 0) {
            if (this.innerSpecial == this.special) {
               var1 = this.innerDERLen;
               return var1;
            }

            this.innerSpecial = this.special;
            this.innerDERClear();
         }

         try {
            this.innerDEREncodeInit();
         } catch (CertificateException var6) {
            byte var2 = 0;
            return var2;
         }

         var1 = this.innerDERLen;
         return var1;
      } finally {
         this.innerDERLock.unlock();
      }
   }

   /** @deprecated */
   public int getInnerDER(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Passed array is null");
      } else {
         this.innerDERLock.lock();

         int var3;
         try {
            this.innerDEREncode();
            System.arraycopy(this.innerDER, 0, var1, var2, this.innerDERLen);
            var3 = this.innerDERLen;
         } finally {
            this.innerDERLock.unlock();
         }

         return var3;
      }
   }

   private void innerDEREncodeInit() throws CertificateException {
      this.innerDERClear();
      if (this.subjectPublicKeyInfo != null && this.signatureAlgorithmBER != null && this.serialNumber != null && this.notBefore != null && this.notAfter != null) {
         if (this.subjectName == null && !this.checkExtensions(17)) {
            throw new CertificateException("Cannot encode innerDER, subject name missing.");
         } else if (this.issuerName == null && !this.checkExtensions(18)) {
            throw new CertificateException("Cannot encode innerDER, issuer name missing.");
         } else {
            int var1 = this.getValidityDERLen();

            try {
               SequenceContainer var2 = new SequenceContainer(this.innerSpecial, true, 0);
               EndContainer var3 = new EndContainer();
               boolean var4 = true;
               if (this.theVersion == 0) {
                  var4 = false;
               }

               IntegerContainer var5 = new IntegerContainer(10616832, var4, 0, this.theVersion);
               IntegerContainer var6;
               if ((this.serialNumber[0] & 128) >> 7 == 0) {
                  var6 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, true);
               } else {
                  var6 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, false);
               }

               EncodedContainer var7 = new EncodedContainer(12288, true, 0, this.signatureAlgorithmBER, 0, this.signatureAlgorithmBER.length);
               EncodedContainer var8 = new EncodedContainer(12288, true, 0, (byte[])null, 0, var1);
               EncodedContainer var9 = new EncodedContainer(12288, true, 0, (byte[])null, 0, this.subjectPublicKeyInfo.length);
               int var10;
               if (this.issuerName != null) {
                  var10 = this.issuerName.getDERLen(0);
               } else {
                  var10 = 2;
               }

               EncodedContainer var11 = new EncodedContainer(12288, true, 0, (byte[])null, 0, var10);
               if (this.subjectName != null) {
                  var10 = this.subjectName.getDERLen(0);
               } else {
                  var10 = 2;
               }

               EncodedContainer var12 = new EncodedContainer(12288, true, 0, (byte[])null, 0, var10);
               var4 = false;
               var10 = 0;
               if (this.theVersion != 0 && this.issuerUniqueID != null) {
                  var4 = true;
                  var10 = this.issuerUniqueID.length;
               }

               EncodedContainer var13 = new EncodedContainer(8454913, var4, 0, (byte[])null, 0, var10);
               var4 = false;
               var10 = 0;
               if (this.theVersion != 0 && this.subjectUniqueID != null) {
                  var4 = true;
                  var10 = this.subjectUniqueID.length;
               }

               EncodedContainer var14 = new EncodedContainer(8454914, var4, 0, (byte[])null, 0, var10);
               var4 = false;
               var10 = 0;
               if (this.theVersion == 2 && this.theExtensions != null) {
                  var10 = this.theExtensions.getDERLen(10551299);
                  if (var10 != 0) {
                     var4 = true;
                  }
               }

               EncodedContainer var15 = new EncodedContainer(10563587, var4, 0, (byte[])null, 0, var10);
               ASN1Container[] var16 = new ASN1Container[]{var2, var5, var6, var7, var11, var8, var12, var9, var13, var14, var15, var3};
               this.asn1TemplateInner = new ASN1Template(var16);
               this.innerDERLen = this.asn1TemplateInner.derEncodeInit();
            } catch (ASN_Exception var17) {
               throw new CertificateException("Cannot encode innerDER, information missing.");
            }
         }
      } else {
         throw new CertificateException("Cannot encode innerDER, information missing.");
      }
   }

   private void innerDEREncode() throws CertificateException {
      if (this.innerDER == null) {
         int var1 = 0;

         int var2;
         try {
            var2 = this.getInnerDERLen();
            if (var2 == 0) {
               throw new CertificateException("Cannot encode innerDER, information missing.");
            }

            this.innerDER = new byte[var2];
            var1 += this.asn1TemplateInner.derEncode(this.innerDER, 0);
            this.asn1TemplateInner = null;
         } catch (ASN_Exception var4) {
            this.asn1TemplateInner = null;
            throw new CertificateException("Could not encode: ", var4);
         }

         try {
            if (this.issuerName != null) {
               var1 += this.issuerName.getDEREncoding(this.innerDER, var1, 0);
            } else {
               this.innerDER[var1] = 48;
               this.innerDER[var1 + 1] = 0;
               var1 += 2;
            }

            var2 = this.getValidityDEREncoding(this.innerDER, var1);
            if (var2 == 0) {
               throw new CertificateException("Could not encode Validity.");
            }

            var1 += var2;
            if (this.subjectName != null) {
               var1 += this.subjectName.getDEREncoding(this.innerDER, var1, 0);
            } else {
               this.innerDER[var1] = 48;
               this.innerDER[var1 + 1] = 0;
               var1 += 2;
            }

            System.arraycopy(this.subjectPublicKeyInfo, 0, this.innerDER, var1, this.subjectPublicKeyInfo.length);
            var1 += this.subjectPublicKeyInfo.length;
            if (this.theVersion != 0 && this.issuerUniqueID != null) {
               System.arraycopy(this.issuerUniqueID, 0, this.innerDER, var1, this.issuerUniqueID.length);
               var1 += this.issuerUniqueID.length;
            }

            if (this.theVersion != 0 && this.subjectUniqueID != null) {
               System.arraycopy(this.subjectUniqueID, 0, this.innerDER, var1, this.subjectUniqueID.length);
               var1 += this.subjectUniqueID.length;
            }

            if (this.theVersion == 2 && this.theExtensions != null) {
               var1 += this.theExtensions.getDEREncoding(this.innerDER, var1, 10551299);
            }
         } catch (NameException var3) {
            throw new CertificateException("Could not encode a Name: ", var3);
         }
      }

   }

   private void innerDERClear() {
      this.innerDERLock.lock();
      this.asn1TemplateInner = null;
      this.innerDER = null;
      this.innerDERLen = 0;
      this.innerSpecial = 0;
      this.innerDERLock.unlock();
   }

   /** @deprecated */
   public void setUnsignedCertFromPKCS10Request(PKCS10CertRequest var1) throws CertificateException {
      this.clearComponents();
      if (var1 == null) {
         throw new CertificateException("Cert Request is null.");
      } else {
         this.setSubjectName(var1.getSubjectName());
         this.setSubjectPublicKey(var1.getSubjectPublicKey("Java"));
         X501Attributes var2 = var1.getAttributes();
         if (var2 != null) {
            X501Attribute var3 = var2.getAttributeByType(2);
            if (var3 == null) {
               this.setVersion(0);
            } else {
               this.setVersion(2);
               this.setExtensions(((V3ExtensionAttribute)var3).getV3ExtensionAttribute());
            }
         }
      }
   }

   /** @deprecated */
   public byte[] getSignature() throws CertificateException {
      if (this.signature == null) {
         throw new CertificateException("Object not signed.");
      } else {
         BitStringContainer var1 = new BitStringContainer(0);
         ASN1Container[] var2 = new ASN1Container[]{var1};

         try {
            ASN1.berDecode(this.signature, 0, var2);
         } catch (ASN_Exception var4) {
            throw new CertificateException("Cannot extract the signature.", var4);
         }

         byte[] var3 = new byte[var1.dataLen];
         System.arraycopy(var1.data, var1.dataOffset, var3, 0, var1.dataLen);
         return var3;
      }
   }

   /** @deprecated */
   public void setVersion(int var1) throws CertificateException {
      if (var1 != this.theVersion) {
         if (var1 != 0 && var1 != 1 && var1 != 2) {
            throw new CertificateException("Invalid cert version: " + var1);
         } else {
            this.clearSignature();
            this.clearTemplate();
            switch (var1) {
               case 0:
                  if (!this.emptyExtensions(this.theExtensions)) {
                     throw new CertificateException("You can not use X509 V1 version for a certificate with extensions.");
                  }

                  if (this.issuerUniqueID != null) {
                     throw new CertificateException("You can not use X509 V1 version for a certificate with issuer unique ID.");
                  }

                  if (this.subjectUniqueID != null) {
                     throw new CertificateException("You can not use X509 V1 version for a certificate with subject unique ID.");
                  }
                  break;
               case 1:
                  if (!this.emptyExtensions(this.theExtensions)) {
                     throw new CertificateException("You can not use X509 V2 version for a certificate with extensions.");
                  }
            }

            this.theVersion = var1;
         }
      }
   }

   /** @deprecated */
   public int getVersion() {
      return this.theVersion;
   }

   /** @deprecated */
   public void setSubjectName(X500Name var1) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      if (var1 == null) {
         if (!this.checkExtensions(17)) {
            throw new CertificateException("Cannot set the cert with the given subjectName.");
         }
      } else {
         try {
            this.subjectName = (X500Name)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CertificateException("Cannot set the cert with the given subjectName.");
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
   public void setIssuerName(X500Name var1) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      if (var1 == null) {
         if (!this.checkExtensions(18)) {
            throw new CertificateException("Cannot set the cert with the given issuerName.");
         }
      } else {
         try {
            this.issuerName = (X500Name)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CertificateException("Cannot set the cert with the given issuerName.");
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
   public void setSerialNumber(byte[] var1, int var2, int var3) {
      this.clearSignature();
      this.clearTemplate();
      this.serialNumber = new byte[var3];
      if (var1 != null) {
         System.arraycopy(var1, var2, this.serialNumber, 0, var3);
      }
   }

   /** @deprecated */
   public byte[] getSerialNumber() {
      return this.serialNumber == null ? new byte[0] : (byte[])((byte[])this.serialNumber.clone());
   }

   /** @deprecated */
   public byte[] getIssuerAndSerialNumber() throws CertificateException {
      if (this.issuerName != null && this.serialNumber != null) {
         try {
            int var1 = this.issuerName.getDERLen(0);
            byte[] var2 = new byte[var1];
            var1 = this.issuerName.getDEREncoding(var2, 0, 0);
            SequenceContainer var3 = new SequenceContainer(0, true, 0);
            EndContainer var4 = new EndContainer();
            IntegerContainer var5;
            if ((this.serialNumber[0] & 128) >> 7 == 0) {
               var5 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, true);
            } else {
               var5 = new IntegerContainer(0, true, 0, this.serialNumber, 0, this.serialNumber.length, false);
            }

            EncodedContainer var6 = new EncodedContainer(12288, true, 0, var2, 0, var1);
            ASN1Container[] var7 = new ASN1Container[]{var3, var6, var5, var4};
            return ASN1.derEncode(var7);
         } catch (ASN_Exception var8) {
            throw new CertificateException("Cannot encode issuerSerial: ", var8);
         } catch (NameException var9) {
            throw new CertificateException("Cannot encode issuerSerial: ", var9);
         }
      } else {
         throw new CertificateException("Cannot get issuerSerial, not all values set.");
      }
   }

   /** @deprecated */
   public boolean compareIssuerAndSerialNumber(byte[] var1, int var2, int var3) {
      if (var1 != null && var3 != 0) {
         try {
            byte[] var4 = this.getIssuerAndSerialNumber();
            if (var4.length != var3) {
               return false;
            } else {
               for(int var5 = 0; var5 < var3; ++var2) {
                  if (var1[var2] != var4[var5]) {
                     return false;
                  }

                  ++var5;
               }

               return true;
            }
         } catch (CertificateException var6) {
            return false;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public boolean compareSubjectName(X500Name var1) {
      return this.subjectName != null && var1 != null ? this.subjectName.equals(var1) : false;
   }

   /** @deprecated */
   public void setTimeType(boolean var1) {
      this.timeType = var1;
      this.timeTypeExplicitlySet = true;
   }

   private void setValidityBER(byte[] var1, int var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         SequenceContainer var3 = new SequenceContainer(0);
         EndContainer var4 = new EndContainer();
         ChoiceContainer var5 = new ChoiceContainer(0);
         ChoiceContainer var6 = new ChoiceContainer(0);
         UTCTimeContainer var7 = new UTCTimeContainer(0);
         UTCTimeContainer var8 = new UTCTimeContainer(0);
         GenTimeContainer var9 = new GenTimeContainer(0);
         GenTimeContainer var10 = new GenTimeContainer(0);
         ASN1Container[] var11 = new ASN1Container[]{var3, var5, var7, var9, var4, var6, var8, var10, var4, var4};

         try {
            ASN1.berDecode(var1, var2, var11);
         } catch (ASN_Exception var14) {
            throw new CertificateException("Cannot extract Validity.", var14);
         }

         Date var12 = var9.theTime;
         if (!var9.dataPresent) {
            var12 = var7.theTime;
         }

         Date var13 = var10.theTime;
         if (!var10.dataPresent) {
            var13 = var8.theTime;
         }

         this.setValidity(var12, var13);
      }
   }

   /** @deprecated */
   public void setValidity(Date var1, Date var2) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      this.clearValidityTemplate();
      if (var1 != null && var2 != null) {
         this.notBefore = new Date(var1.getTime());
         this.notAfter = new Date(var2.getTime());
         if (!this.notAfter.after(this.notBefore)) {
            throw new CertificateException("Cannot set the validity with the given dates.");
         }
      } else {
         throw new CertificateException("Cannot set the validity with the given dates.");
      }
   }

   private void clearValidityTemplate() {
      this.asn1TemplateValidity = null;
   }

   private int getValidityDERLen() {
      SequenceContainer var1 = new SequenceContainer(0, true, 0);
      EndContainer var2 = new EndContainer();
      Object var3;
      Object var4;
      if (!this.timeTypeExplicitlySet) {
         if (this.notBefore.before(FIRST_GENERALIZED_TIME_DATE)) {
            var3 = new UTCTimeContainer(0, true, 0, this.notBefore);
         } else {
            var3 = new GenTimeContainer(0, true, 0, this.notBefore);
         }

         if (this.notAfter.before(FIRST_GENERALIZED_TIME_DATE)) {
            var4 = new UTCTimeContainer(0, true, 0, this.notAfter);
         } else {
            var4 = new GenTimeContainer(0, true, 0, this.notAfter);
         }
      } else if (this.timeType) {
         var3 = new GenTimeContainer(0, true, 0, this.notBefore);
         var4 = new GenTimeContainer(0, true, 0, this.notAfter);
      } else {
         var3 = new UTCTimeContainer(0, true, 0, this.notBefore);
         var4 = new UTCTimeContainer(0, true, 0, this.notAfter);
      }

      ASN1Container[] var5 = new ASN1Container[]{var1, (ASN1Container)var3, (ASN1Container)var4, var2};
      this.asn1TemplateValidity = new ASN1Template(var5);

      try {
         return this.asn1TemplateValidity.derEncodeInit();
      } catch (ASN_Exception var7) {
         return 0;
      }
   }

   private int getValidityDEREncoding(byte[] var1, int var2) {
      if (this.asn1TemplateValidity == null && this.getValidityDERLen() == 0) {
         return 0;
      } else {
         try {
            int var3 = this.asn1TemplateValidity.derEncode(var1, var2);
            this.asn1TemplateValidity = null;
            return var3;
         } catch (ASN_Exception var4) {
            this.asn1TemplateValidity = null;
            return 0;
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
   public boolean checkValidityDate(Date var1) {
      if (this.notBefore != null && this.notAfter != null && var1 != null) {
         return var1.compareTo(this.notBefore) >= 0 && var1.compareTo(this.notAfter) <= 0;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public void setIssuerUniqueID(byte[] var1, int var2, int var3) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      if (this.theVersion == 0) {
         throw new CertificateException("Cannot set unique ID on a version 1 cert.");
      } else if (var1 != null) {
         try {
            BitStringContainer var4 = new BitStringContainer(8388609, true, 0, var1, var2, var3, var3 * 8, false);
            ASN1Container[] var5 = new ASN1Container[]{var4};
            this.issuerUniqueID = ASN1.derEncode(var5);
         } catch (ASN_Exception var6) {
            throw new CertificateException("Cannot set issuerUniqueID: ", var6);
         }
      }
   }

   /** @deprecated */
   public byte[] getIssuerUniqueID() {
      if (this.issuerUniqueID == null) {
         return null;
      } else {
         try {
            int var1 = 2 + ASN1Lengths.determineLengthLen(this.issuerUniqueID, 1);
            byte[] var2 = new byte[this.issuerUniqueID.length - var1];
            System.arraycopy(this.issuerUniqueID, var1, var2, 0, var2.length);
            return var2;
         } catch (ASN_Exception var3) {
            return null;
         }
      }
   }

   /** @deprecated */
   public void setSubjectUniqueID(byte[] var1, int var2, int var3) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      if (this.theVersion == 0) {
         throw new CertificateException("Cannot set unique ID on a version 1 cert.");
      } else if (var1 != null) {
         try {
            BitStringContainer var4 = new BitStringContainer(8388610, true, 0, var1, var2, var3, var3 * 8, false);
            ASN1Container[] var5 = new ASN1Container[]{var4};
            this.subjectUniqueID = ASN1.derEncode(var5);
         } catch (ASN_Exception var6) {
            throw new CertificateException("Cannot set subjectUniqueID.", var6);
         }
      }
   }

   /** @deprecated */
   public byte[] getSubjectUniqueID() {
      if (this.subjectUniqueID == null) {
         return null;
      } else {
         try {
            int var1 = 2 + ASN1Lengths.determineLengthLen(this.subjectUniqueID, 1);
            byte[] var2 = new byte[this.subjectUniqueID.length - var1];
            System.arraycopy(this.subjectUniqueID, var1, var2, 0, var2.length);
            return var2;
         } catch (ASN_Exception var3) {
            return null;
         }
      }
   }

   /** @deprecated */
   public void setExtensions(X509V3Extensions var1) throws CertificateException {
      if (!this.emptyExtensions(var1)) {
         if (var1.getExtensionsType() != 1) {
            throw new CertificateException("Wrong extensions type: should be Cert extensions.");
         } else {
            this.clearSignature();
            this.clearTemplate();

            try {
               this.theExtensions = (X509V3Extensions)var1.clone();
            } catch (CloneNotSupportedException var3) {
               throw new CertificateException("Cannot set the cert with the given extensions.", var3);
            }

            if (this.theVersion != 2) {
               this.setVersion(2);
            }

         }
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
   public void signCertificate(String var1, String var2, JSAFE_PrivateKey var3, SecureRandom var4) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      if (var1 != null && var2 != null && var3 != null) {
         try {
            String var5 = this.getSignatureFormat(var1);
            if (var5 == null) {
               this.signatureAlgorithmBER = AlgorithmID.derEncodeAlgID(var1, 1, (byte[])null, 0, 0);
            } else {
               this.signatureAlgorithmBER = AlgorithmID.derEncodeAlgID(var5, 1, (byte[])null, 0, 0);
            }
         } catch (ASN_Exception var13) {
            throw new CertificateException("Cannot sign, unknown algorithm.", var13);
         }

         this.innerDERLock.lock();

         byte[] var14;
         try {
            this.innerDEREncode();
            var14 = this.performSignature(var1, var2, var3, var4, this.innerDER, 0, this.innerDERLen);
         } finally {
            this.innerDERLock.unlock();
         }

         try {
            BitStringContainer var6 = new BitStringContainer(0, true, 0, var14, 0, var14.length, var14.length * 8, false);
            ASN1Container[] var7 = new ASN1Container[]{var6};
            this.signature = ASN1.derEncode(var7);
         } catch (ASN_Exception var11) {
            this.clearSignature();
            throw new CertificateException("Cannot sign the cert as presently set.", var11);
         }
      } else {
         throw new CertificateException("Specified values are null.");
      }
   }

   /** @deprecated */
   public boolean verifyCertificateSignature(String var1, JSAFE_PublicKey var2, SecureRandom var3) throws CertificateException {
      if (var1 != null && var2 != null) {
         this.innerDERLock.lock();

         boolean var5;
         try {
            if (this.innerDER == null) {
               throw new CertificateException("Cannot verify certificate, values not set.");
            }

            byte[] var4 = this.getSignature();
            var5 = this.performSignatureVerification(var1, var2, var3, this.innerDER, 0, this.innerDERLen, var4, 0, var4.length);
         } finally {
            this.innerDERLock.unlock();
         }

         return var5;
      } else {
         throw new CertificateException("Specified values are null.");
      }
   }

   private boolean checkExtensions(int var1) {
      if (this.theVersion == 2 && this.theExtensions != null) {
         Vector var2 = this.theExtensions.theExtensions;
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            X509V3Extension var4 = (X509V3Extension)var3.next();
            if (var4.getExtensionType() == var1 && var4.getCriticality()) {
               return true;
            }
         }
      }

      return false;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof X509Certificate) {
         X509Certificate var2 = (X509Certificate)var1;

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
               return var3 != var4 ? false : Arrays.equals(var5, var6);
            }
         } catch (CertificateException var7) {
            return false;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = this.getDERLen(0);
      byte[] var2 = new byte[var1];

      try {
         this.getDEREncoding(var2, 0, 0);
      } catch (CertificateException var4) {
         return 0;
      }

      return Arrays.hashCode(var2);
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      X509Certificate var1 = (X509Certificate)super.clone();
      if (this.subjectPublicKeyInfo != null) {
         var1.subjectPublicKeyInfo = (byte[])((byte[])this.subjectPublicKeyInfo.clone());
      }

      if (this.subjectPublicKey != null) {
         var1.subjectPublicKey = (JSAFE_PublicKey)this.subjectPublicKey.clone();
      }

      if (this.signatureAlgorithmBER != null) {
         var1.signatureAlgorithmBER = (byte[])((byte[])this.signatureAlgorithmBER.clone());
      }

      if (this.signature != null) {
         var1.signature = (byte[])((byte[])this.signature.clone());
      }

      var1.signatureAlgorithmFormat = this.signatureAlgorithmFormat;
      var1.theVersion = this.theVersion;
      if (this.subjectName != null) {
         var1.subjectName = (X500Name)this.subjectName.clone();
      }

      if (this.issuerName != null) {
         var1.issuerName = (X500Name)this.issuerName.clone();
      }

      if (this.serialNumber != null) {
         var1.serialNumber = (byte[])((byte[])this.serialNumber.clone());
      }

      if (this.issuerUniqueID != null) {
         var1.issuerUniqueID = (byte[])((byte[])this.issuerUniqueID.clone());
      }

      if (this.subjectUniqueID != null) {
         var1.subjectUniqueID = (byte[])((byte[])this.subjectUniqueID.clone());
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

      this.innerDERLock.lock();

      try {
         var1.innerSpecial = this.innerSpecial;
         if (this.innerDER != null) {
            try {
               var1.innerDEREncode();
            } catch (CertificateException var11) {
            }
         }
      } finally {
         this.innerDERLock.unlock();
      }

      this.outerDERLock.lock();

      try {
         var1.special = this.special;
         if (this.asn1Template != null) {
            var1.outerDEREncodeInit();
         }
      } finally {
         this.outerDERLock.unlock();
      }

      if (this.asn1TemplateValidity != null) {
         var1.getValidityDERLen();
      }

      return var1;
   }

   private void clearTemplate() {
      this.outerDERClear();
      this.innerDERClear();
   }

   /** @deprecated */
   protected void clearComponents() {
      super.clearComponents();
      this.clearTemplate();
      this.theVersion = 2;
      this.subjectName = null;
      this.issuerName = null;
      this.serialNumber = null;
      this.issuerUniqueID = null;
      this.subjectUniqueID = null;
      this.notBefore = null;
      this.notAfter = null;
      this.theExtensions = null;
   }

   private boolean emptyExtensions(X509V3Extensions var1) {
      return var1 == null || var1.getExtensionCount() == 0;
   }

   /** @deprecated */
   protected Object writeReplace() throws ObjectStreamException {
      try {
         int var1 = this.getDERLen(0);
         byte[] var2 = new byte[var1];
         this.getDEREncoding(var2, 0, 0);
         return new X509CertRep(var2);
      } catch (CertificateException var3) {
         throw new NotSerializableException(var3.getMessage());
      }
   }

   /** @deprecated */
   protected static class X509CertRep implements Serializable {
      private static final long serialVersionUID = 2461303762189202977L;
      private byte[] encoding;

      /** @deprecated */
      protected X509CertRep(byte[] var1) {
         this.encoding = var1;
      }

      /** @deprecated */
      protected Object readResolve() throws ObjectStreamException {
         try {
            return new X509Certificate(this.encoding, 0, 0);
         } catch (CertificateException var2) {
            throw new NotSerializableException(var2.getMessage());
         }
      }
   }
}
