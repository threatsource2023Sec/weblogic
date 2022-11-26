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
public class X509CRL extends CRL {
   private static final long serialVersionUID = -5083647906625180012L;
   /** @deprecated */
   public static final int X509_VERSION_1 = 0;
   /** @deprecated */
   public static final int X509_VERSION_2 = 1;
   private byte[] innerDER;
   private int innerDERLen;
   private int theVersion;
   private X500Name issuerName;
   private RevokedCertificates revokedCerts;
   private boolean timeType;
   private Date thisUpdate;
   private Date nextUpdate;
   private X509V3Extensions crlExtensions;
   private int special;
   private ASN1Template asn1Template;
   private int innerSpecial;
   private ASN1Template asn1TemplateInner;
   private final Lock outerDERLock;
   private final Lock innerDERLock;

   /** @deprecated */
   public X509CRL() {
      this.theVersion = 0;
      this.special = 0;
      this.innerSpecial = 0;
      this.outerDERLock = new ReentrantLock();
      this.innerDERLock = new ReentrantLock();
   }

   /** @deprecated */
   public X509CRL(CertJ var1) {
      this.theVersion = 0;
      this.special = 0;
      this.innerSpecial = 0;
      this.outerDERLock = new ReentrantLock();
      this.innerDERLock = new ReentrantLock();
      this.setCertJ(var1);
   }

   /** @deprecated */
   public X509CRL(byte[] var1, int var2, int var3) throws CertificateException {
      this(var1, var2, var3, (CertJ)null);
   }

   /** @deprecated */
   public X509CRL(byte[] var1, int var2, int var3, CertJ var4) throws CertificateException {
      this.theVersion = 0;
      this.special = 0;
      this.innerSpecial = 0;
      this.outerDERLock = new ReentrantLock();
      this.innerDERLock = new ReentrantLock();
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.setCRLBER(var1, var2, var3);
         this.setCertJ(var4);
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
            throw new CertificateException("Could not read the BER encoding.");
         }
      }
   }

   private void setCRLBER(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.clearComponents();
         ASN1Container[] var4 = decodeCRL(var1, var2, var3);
         this.setInnerDER(var4[1].data, var4[1].dataOffset, var3);
         this.signature = new byte[var4[3].dataLen];
         System.arraycopy(var4[3].data, var4[3].dataOffset, this.signature, 0, var4[3].dataLen);
         this.setSignatureAlgorithm(var4[2].data, var4[2].dataOffset, var4[2].dataLen);
      }
   }

   /** @deprecated */
   protected static ASN1Container[] decodeCRL(byte[] var0, int var1, int var2) throws CertificateException {
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
            throw new CertificateException("Could not BER decode the CRL.");
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
                     throw new CertificateException("Could not encode: Possibly some of the required fields of this CRL object are not set.");
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
      if (var1 != 1) {
         throw new CertificateException("Invalid X.509 CRL version.");
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
         IntegerContainer var6 = new IntegerContainer(65536);
         EncodedContainer var7 = new EncodedContainer(12288);
         EncodedContainer var8 = new EncodedContainer(12288);
         ChoiceContainer var9 = new ChoiceContainer(0);
         UTCTimeContainer var10 = new UTCTimeContainer(0);
         GenTimeContainer var11 = new GenTimeContainer(0);
         ChoiceContainer var12 = new ChoiceContainer(65536);
         UTCTimeContainer var13 = new UTCTimeContainer(0);
         GenTimeContainer var14 = new GenTimeContainer(0);
         EncodedContainer var15 = new EncodedContainer(77824);
         EncodedContainer var16 = new EncodedContainer(10563584);
         ASN1Container[] var17 = new ASN1Container[]{var4, var6, var7, var8, var9, var10, var11, var5, var12, var13, var14, var5, var15, var16, var5};

         try {
            ASN1.berDecode(var1, var2, var17);
         } catch (ASN_Exception var21) {
            throw new CertificateException("Could not BER decode the CRL info.");
         }

         if (var6.dataPresent) {
            try {
               this.setVersionNumber(var6.getValueAsInt());
            } catch (ASN_Exception var20) {
               throw new CertificateException("Invalid version number: ", var20);
            }
         }

         this.setSignatureAlgorithm(var7.data, var7.dataOffset, var7.dataLen);

         try {
            this.setIssuerName(new X500Name(var8.data, var8.dataOffset, 0));
         } catch (NameException var19) {
            throw new CertificateException("Invalid issuer name: ", var19);
         }

         if (var10.dataPresent) {
            this.thisUpdate = var10.theTime;
         } else {
            this.thisUpdate = var11.theTime;
         }

         if (var13.dataPresent) {
            this.nextUpdate = var13.theTime;
         } else if (var14.dataPresent) {
            this.nextUpdate = var14.theTime;
         }

         if (var15.dataPresent) {
            this.revokedCerts = new RevokedCertificates(var15.data, var15.dataOffset, 0);
         }

         if (var16.dataPresent) {
            X509V3Extensions var18 = new X509V3Extensions(var16.data, var16.dataOffset, 10485760, 2);
            this.setExtensions(var18);
         }

         this.innerDERLen = getNextBEROffset(var1, var2) - var2;
         this.innerDER = new byte[this.innerDERLen];
         System.arraycopy(var1, var2, this.innerDER, 0, this.innerDERLen);
      }
   }

   /** @deprecated */
   public int getInnerDERLen() {
      this.innerDERLock.lock();

      int var1;
      try {
         if (this.innerDERLen != 0) {
            if (this.innerSpecial == this.special) {
               var1 = this.innerDERLen;
               return var1;
            }

            this.innerSpecial = this.special;
            this.innerDERClear();
         }

         var1 = this.innerDEREncodeInit();
      } finally {
         this.innerDERLock.unlock();
      }

      return var1;
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

   private int innerDEREncodeInit() {
      this.innerDERClear();
      if (this.thisUpdate != null && this.signatureAlgorithmBER != null) {
         if (this.issuerName == null && !this.checkExtensions(18)) {
            return 0;
         } else {
            SequenceContainer var1 = new SequenceContainer(this.innerSpecial, true, 0);
            EndContainer var2 = new EndContainer();
            boolean var3 = true;
            if (this.theVersion == 0) {
               var3 = false;
            }

            IntegerContainer var4 = new IntegerContainer(65536, var3, 0, this.theVersion);

            EncodedContainer var5;
            try {
               var5 = new EncodedContainer(12288, true, 0, this.signatureAlgorithmBER, 0, this.signatureAlgorithmBER.length);
            } catch (ASN_Exception var16) {
               return 0;
            }

            Object var6;
            if (this.timeType) {
               var6 = new GenTimeContainer(0, true, 0, this.thisUpdate);
            } else {
               var6 = new UTCTimeContainer(0, true, 0, this.thisUpdate);
            }

            var3 = false;
            if (this.nextUpdate != null) {
               var3 = true;
            }

            Object var7;
            if (this.timeType) {
               var7 = new GenTimeContainer(65536, var3, 0, this.nextUpdate);
            } else {
               var7 = new UTCTimeContainer(65536, var3, 0, this.nextUpdate);
            }

            var3 = false;
            int var8 = 0;

            try {
               if (this.revokedCerts != null) {
                  var8 = this.revokedCerts.getDERLen(65536);
                  var3 = true;
               }
            } catch (CertificateException var17) {
               return 0;
            }

            try {
               EncodedContainer var9 = new EncodedContainer(77824, var3, 0, (byte[])null, 0, var8);

               byte[] var10;
               try {
                  if (this.issuerName != null) {
                     var8 = this.issuerName.getDERLen(0);
                     var10 = new byte[var8];
                     this.issuerName.getDEREncoding(var10, 0, 0);
                  } else {
                     var8 = 2;
                     var10 = new byte[var8];
                     var10[0] = 48;
                     var10[1] = 0;
                  }
               } catch (NameException var14) {
                  return 0;
               }

               EncodedContainer var11 = new EncodedContainer(12288, true, 0, var10, 0, var8);
               var3 = false;
               var8 = 0;
               if (this.theVersion == 1 && this.crlExtensions != null) {
                  var8 = this.crlExtensions.getDERLen(10551296);
                  if (var8 != 0) {
                     var3 = true;
                  }
               }

               EncodedContainer var12 = new EncodedContainer(10563584, var3, 0, (byte[])null, 0, var8);
               ASN1Container[] var13 = new ASN1Container[]{var1, var4, var5, var11, (ASN1Container)var6, (ASN1Container)var7, var9, var12, var2};
               this.asn1TemplateInner = new ASN1Template(var13);
               this.innerDERLen = this.asn1TemplateInner.derEncodeInit();
               return this.innerDERLen;
            } catch (ASN_Exception var15) {
               return 0;
            }
         }
      } else {
         return 0;
      }
   }

   private void innerDEREncode() throws CertificateException {
      if (this.innerDER == null) {
         int var1 = 0;

         try {
            int var2 = this.getInnerDERLen();
            if (var2 == 0) {
               throw new CertificateException("Cannot encode innerDER, information missing.");
            }

            this.innerDER = new byte[var2];
            var1 += this.asn1TemplateInner.derEncode(this.innerDER, 0);
            this.asn1TemplateInner = null;
         } catch (ASN_Exception var3) {
            this.asn1TemplateInner = null;
            throw new CertificateException("Could not encode: ", var3);
         }

         if (this.revokedCerts != null) {
            var1 += this.revokedCerts.getDEREncoding(this.innerDER, var1, 65536, this.timeType);
         }

         if (this.theVersion == 1 && this.crlExtensions != null) {
            var1 += this.crlExtensions.getDEREncoding(this.innerDER, var1, 10551296);
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
   public byte[] getSignature() throws CertificateException {
      if (this.signature == null) {
         throw new CertificateException("Object not signed.");
      } else {
         BitStringContainer var1 = new BitStringContainer(0);
         ASN1Container[] var2 = new ASN1Container[]{var1};

         try {
            ASN1.berDecode(this.signature, 0, var2);
         } catch (ASN_Exception var4) {
            throw new CertificateException("Cannot extract the signature.");
         }

         byte[] var3 = new byte[var1.dataLen];
         System.arraycopy(var1.data, var1.dataOffset, var3, 0, var1.dataLen);
         return var3;
      }
   }

   /** @deprecated */
   public void setVersion(int var1) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      this.theVersion = var1;
      if (var1 != 0 && var1 != 1) {
         this.theVersion = 0;
         throw new CertificateException("Invalid CRL version: " + var1);
      }
   }

   /** @deprecated */
   public int getVersion() {
      return this.theVersion;
   }

   /** @deprecated */
   public void setIssuerName(X500Name var1) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      if (var1 == null) {
         if (!this.checkExtensions(18)) {
            throw new CertificateException("Cannot set the CRL with null issuerName.");
         }
      } else {
         try {
            this.issuerName = (X500Name)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CertificateException("Cannot set the CRL with the given issuerName.");
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
   public void setNextUpdate(Date var1) {
      this.clearSignature();
      this.clearTemplate();
      if (var1 != null) {
         this.nextUpdate = new Date(var1.getTime());
      }

   }

   /** @deprecated */
   public Date getNextUpdate() {
      return this.nextUpdate == null ? null : new Date(this.nextUpdate.getTime());
   }

   /** @deprecated */
   public void setThisUpdate(Date var1) {
      this.clearSignature();
      this.clearTemplate();
      if (var1 != null) {
         this.thisUpdate = new Date(var1.getTime());
      }

   }

   /** @deprecated */
   public Date getThisUpdate() {
      return this.thisUpdate == null ? null : new Date(this.thisUpdate.getTime());
   }

   /** @deprecated */
   public void setRevokedCertificates(RevokedCertificates var1) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      if (var1 != null) {
         try {
            this.revokedCerts = (RevokedCertificates)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new CertificateException("Cannot set the CRL with the given revoked Certs.");
         }
      }

   }

   /** @deprecated */
   public RevokedCertificates getRevokedCertificates() {
      if (this.revokedCerts == null) {
         return null;
      } else {
         try {
            return (RevokedCertificates)this.revokedCerts.clone();
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   /** @deprecated */
   public boolean compareIssuerName(X500Name var1) {
      return this.issuerName != null && var1 != null ? this.issuerName.equals(var1) : false;
   }

   /** @deprecated */
   public void setTimeType(boolean var1) {
      this.timeType = var1;
   }

   /** @deprecated */
   public void setExtensions(X509V3Extensions var1) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      if (var1 == null) {
         throw new CertificateException("CRL extensions are null.");
      } else if (var1.getExtensionsType() != 2) {
         throw new CertificateException("Wrong extensions type: should be CRL extensions.");
      } else {
         if (this.theVersion == 0) {
            for(int var3 = 0; var3 < var1.getExtensionCount(); ++var3) {
               X509V3Extension var2 = var1.getExtensionByIndex(var3);
               if (var2.getCriticality()) {
                  throw new CertificateException("Cannot set critical extensions on a version 1 CRL.");
               }
            }
         }

         try {
            this.crlExtensions = (X509V3Extensions)var1.clone();
         } catch (CloneNotSupportedException var4) {
            throw new CertificateException("Cannot set the CRL with the given extensions.");
         }
      }
   }

   /** @deprecated */
   public X509V3Extensions getExtensions() {
      if (this.crlExtensions == null) {
         return null;
      } else {
         try {
            return (X509V3Extensions)this.crlExtensions.clone();
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   /** @deprecated */
   public void signCRL(String var1, String var2, JSAFE_PrivateKey var3, SecureRandom var4) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      if (var1 != null && var2 != null && var3 != null) {
         try {
            String var5 = this.getSignatureFormat();
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
            throw new CertificateException("Cannot sign the CRL as presently set.");
         }
      } else {
         throw new CertificateException("Cannot sign, specified values are null.");
      }
   }

   /** @deprecated */
   public boolean verifyCRLSignature(String var1, JSAFE_PublicKey var2, SecureRandom var3) throws CertificateException {
      if (var1 != null && var2 != null) {
         this.innerDERLock.lock();

         boolean var5;
         try {
            if (this.innerDER == null) {
               throw new CertificateException("Cannot verify CRL, values not set.");
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
      if (this.theVersion == 1 && this.crlExtensions != null) {
         Vector var2 = this.crlExtensions.theExtensions;
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
      if (var1 != null && var1 instanceof X509CRL) {
         X509CRL var2 = (X509CRL)var1;

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
         } catch (CertificateException var8) {
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
      X509CRL var1 = (X509CRL)super.clone();
      if (this.signatureAlgorithmBER != null) {
         var1.signatureAlgorithmBER = (byte[])((byte[])this.signatureAlgorithmBER.clone());
      }

      var1.signatureAlgorithmFormat = this.signatureAlgorithmFormat;
      if (this.signature != null) {
         var1.signature = (byte[])((byte[])this.signature.clone());
      }

      if (this.theDevice != null) {
         var1.theDevice = this.theDevice;
      }

      if (this.theDeviceList != null) {
         var1.theDeviceList = (String[])((String[])this.theDeviceList.clone());
      }

      var1.theVersion = this.theVersion;
      if (this.issuerName != null) {
         var1.issuerName = (X500Name)this.issuerName.clone();
      }

      if (this.revokedCerts != null) {
         var1.revokedCerts = (RevokedCertificates)this.revokedCerts.clone();
      }

      if (this.thisUpdate != null) {
         var1.thisUpdate = new Date(this.thisUpdate.getTime());
      }

      if (this.nextUpdate != null) {
         var1.nextUpdate = new Date(this.nextUpdate.getTime());
      }

      if (this.crlExtensions != null) {
         var1.crlExtensions = (X509V3Extensions)this.crlExtensions.clone();
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
      this.theVersion = 0;
      this.issuerName = null;
      this.revokedCerts = null;
      this.thisUpdate = null;
      this.nextUpdate = null;
      this.crlExtensions = null;
      this.timeType = false;
   }

   /** @deprecated */
   protected Object writeReplace() throws ObjectStreamException {
      try {
         int var1 = this.getDERLen(0);
         byte[] var2 = new byte[var1];
         this.getDEREncoding(var2, 0, 0);
         return new X509CrlRep(var2);
      } catch (CertificateException var3) {
         throw new NotSerializableException(var3.getMessage());
      }
   }

   /** @deprecated */
   protected static class X509CrlRep implements Serializable {
      private static final long serialVersionUID = -5724087715786371523L;
      private byte[] encoding;

      /** @deprecated */
      protected X509CrlRep(byte[] var1) {
         this.encoding = var1;
      }

      /** @deprecated */
      protected Object readResolve() throws ObjectStreamException {
         try {
            return new X509CRL(this.encoding, 0, 0);
         } catch (CertificateException var2) {
            throw new NotSerializableException(var2.getMessage());
         }
      }
   }
}
