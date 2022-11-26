package com.rsa.certj.cert;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.cert.attributes.X501Attribute;
import com.rsa.certj.x.b;
import com.rsa.certj.x.d;
import com.rsa.jsafe.JSAFE_PrivateKey;
import java.security.SecureRandom;
import java.util.Arrays;

/** @deprecated */
public class PKCS10CertRequest extends CertRequest {
   /** @deprecated */
   public static final int PKCS10_VERSION_1 = 0;
   private byte[] requestInfo;
   private byte[] requestInfoDER;
   private int requestInfoDERLen;
   private int theVersion;
   private X500Name subjectName;
   private X501Attributes theAttributes;
   private ASN1Template asn1Template;
   /** @deprecated */
   protected int special;
   private ASN1Template asn1TemplateInfo;
   /** @deprecated */
   protected int infoSpecial;

   /** @deprecated */
   public PKCS10CertRequest() {
      this.theVersion = 0;
   }

   /** @deprecated */
   public PKCS10CertRequest(CertJ var1) {
      this.theVersion = 0;
      this.setCertJ(var1);
   }

   /** @deprecated */
   public PKCS10CertRequest(byte[] var1, int var2, int var3) throws CertificateException {
      this(var1, var2, var3, (CertJ)null);
   }

   /** @deprecated */
   public PKCS10CertRequest(byte[] var1, int var2, int var3, CertJ var4) throws CertificateException {
      this.theVersion = 0;
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.setCertRequestBER(var1, var2, var3);
         this.setCertJ(var4);
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

   private void setCertRequestBER(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null");
      } else {
         this.clearComponents();
         ASN1Container[] var4 = decodeRequest(var1, var2, var3);
         this.requestInfo = new byte[var4[1].dataLen];
         System.arraycopy(var4[1].data, var4[1].dataOffset, this.requestInfo, 0, var4[1].dataLen);
         this.setCertRequestInfo(var4[1].data, var4[1].dataOffset, 0);
         this.signatureAlgorithmBER = new byte[var4[2].dataLen];
         System.arraycopy(var4[2].data, var4[2].dataOffset, this.signatureAlgorithmBER, 0, var4[2].dataLen);
         this.signature = new byte[var4[3].dataLen];
         System.arraycopy(var4[3].data, var4[3].dataOffset, this.signature, 0, var4[3].dataLen);
      }
   }

   /** @deprecated */
   protected static ASN1Container[] decodeRequest(byte[] var0, int var1, int var2) throws CertificateException {
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
            throw new CertificateException("Could not BER decode the request.");
         }
      }
   }

   /** @deprecated */
   public int getDERLen(int var1) {
      return this.encodeInit(var1);
   }

   private int encodeInit(int var1) {
      this.special = var1;
      if (!this.signedByUs) {
         return 0;
      } else {
         if (this.requestInfoDER == null) {
            this.requestInfoDERLen = this.getCertRequestInfoDERLen(var1);
         }

         if (this.requestInfoDERLen == 0) {
            return 0;
         } else if (this.signature != null && this.signatureAlgorithmBER != null) {
            try {
               SequenceContainer var2 = new SequenceContainer(var1, true, 0);
               EndContainer var3 = new EndContainer();
               EncodedContainer var4 = new EncodedContainer(12288, true, 0, (byte[])null, 0, this.requestInfoDERLen);
               EncodedContainer var5 = new EncodedContainer(12288, true, 0, (byte[])null, 0, this.signatureAlgorithmBER.length);
               EncodedContainer var6 = new EncodedContainer(768, true, 0, (byte[])null, 0, this.signature.length);
               ASN1Container[] var7 = new ASN1Container[]{var2, var4, var5, var6, var3};
               this.asn1Template = new ASN1Template(var7);
               return this.asn1Template.derEncodeInit();
            } catch (ASN_Exception var8) {
               return 0;
            }
         } else {
            return 0;
         }
      }
   }

   /** @deprecated */
   public int getDEREncoding(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Specified array is null.");
      } else if (!this.signedByUs) {
         throw new CertificateException("Could not encode, missing data.");
      } else {
         int var4 = 0;

         try {
            if (this.asn1Template == null || var3 != this.special) {
               this.encodeInit(var3);
            }

            var4 += this.asn1Template.derEncode(var1, var2);
            this.asn1Template = null;
         } catch (ASN_Exception var6) {
            this.asn1Template = null;
            throw new CertificateException("Could not encode: ", var6);
         }

         if (this.requestInfoDER != null && this.requestInfoDERLen != 0 && this.infoSpecial == 0) {
            System.arraycopy(this.requestInfoDER, 0, var1, var2 + var4, this.requestInfoDERLen);
            var4 += this.requestInfoDERLen;
         } else {
            int var5 = this.getCertRequestInfoDEREncoding(var1, var2 + var4, 0);
            if (var5 == 0) {
               throw new CertificateException("Could not encode, missing data.");
            }

            var4 += var5;
         }

         System.arraycopy(this.signatureAlgorithmBER, 0, var1, var2 + var4, this.signatureAlgorithmBER.length);
         var4 += this.signatureAlgorithmBER.length;
         System.arraycopy(this.signature, 0, var1, var2 + var4, this.signature.length);
         return var4 + this.signature.length;
      }
   }

   /** @deprecated */
   public void setCertRequestInfo(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Encoding is null.");
      } else {
         this.clearSignature();
         this.clearTemplate();
         SequenceContainer var4 = new SequenceContainer(var3);
         EndContainer var5 = new EndContainer();
         IntegerContainer var6 = new IntegerContainer(0);
         EncodedContainer var7 = new EncodedContainer(12288);
         EncodedContainer var8 = new EncodedContainer(12288);
         EncodedContainer var9 = new EncodedContainer(8401152);
         ASN1Container[] var10 = new ASN1Container[]{var4, var6, var7, var8, var9, var5};

         try {
            ASN1.berDecode(var1, var2, var10);
         } catch (ASN_Exception var14) {
            throw new CertificateException("Could not BER decode the request info.");
         }

         this.checkVersionNumber(-1, var6.data, var6.dataOffset, var6.dataLen);

         try {
            this.subjectName = new X500Name(var7.data, var7.dataOffset, 0);
         } catch (NameException var13) {
            throw new CertificateException("Could not read the SubjectName: ", var13);
         }

         this.setSubjectPublicKey(var8.data, var8.dataOffset);

         try {
            this.theAttributes = new X501Attributes(var9.data, var9.dataOffset, 8388608);
         } catch (AttributeException var12) {
            throw new CertificateException("Could not read the Attributes: ", var12);
         }
      }
   }

   /** @deprecated */
   protected void checkVersionNumber(int var1, byte[] var2, int var3, int var4) throws CertificateException {
      if (var1 == -1) {
         if (var2 == null || var4 > 4) {
            throw new CertificateException("Invalid PKCS #10 Cert Request version.");
         }

         var1 = 0;

         for(int var5 = var3; var5 < var3 + var4; ++var5) {
            var1 = var1 << 8 | var2[var3] & 255;
         }
      }

      if (var1 != 0) {
         throw new CertificateException("Invalid PKCS #10 Cert Request version.");
      }
   }

   /** @deprecated */
   public int getCertRequestInfoDERLen(int var1) {
      return this.infoEncodeInit(var1);
   }

   private int infoEncodeInit(int var1) {
      this.infoSpecial = var1;
      if (this.subjectPublicKeyInfo != null && this.subjectName != null) {
         int var2 = this.subjectName.getDERLen(0);
         int var3 = 2;
         if (this.theAttributes != null) {
            var3 = this.theAttributes.getDERLen(8388608);
         }

         try {
            SequenceContainer var4 = new SequenceContainer(var1, true, 0);
            EndContainer var5 = new EndContainer();
            IntegerContainer var6 = new IntegerContainer(0, true, 0, this.theVersion);
            EncodedContainer var7 = new EncodedContainer(12288, true, 0, (byte[])null, 0, var2);
            EncodedContainer var8 = new EncodedContainer(12288, true, 0, (byte[])null, 0, this.subjectPublicKeyInfo.length);
            EncodedContainer var9 = new EncodedContainer(8401152, true, 0, (byte[])null, 0, var3);
            ASN1Container[] var10 = new ASN1Container[]{var4, var6, var7, var8, var9, var5};
            this.asn1TemplateInfo = new ASN1Template(var10);
            return this.asn1TemplateInfo.derEncodeInit();
         } catch (ASN_Exception var11) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   /** @deprecated */
   public int getCertRequestInfoDEREncoding(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Specified array is null.");
      } else if ((this.asn1TemplateInfo == null || var3 != this.infoSpecial) && this.infoEncodeInit(var3) == 0) {
         throw new CertificateException("Could not encode, missing data.");
      } else {
         int var4 = 0;

         try {
            var4 += this.asn1TemplateInfo.derEncode(var1, var2);
            this.asn1TemplateInfo = null;
         } catch (ASN_Exception var8) {
            this.asn1TemplateInfo = null;
            throw new CertificateException("Could not encode Cert Request: ", var8);
         }

         try {
            var4 += this.subjectName.getDEREncoding(var1, var2 + var4, 0);
         } catch (NameException var7) {
            throw new CertificateException("Cannot build Cert Request Info: ", var7);
         }

         System.arraycopy(this.subjectPublicKeyInfo, 0, var1, var2 + var4, this.subjectPublicKeyInfo.length);
         var4 += this.subjectPublicKeyInfo.length;
         if (this.theAttributes == null) {
            var2 += var4;
            var1[var2] = -96;
            var1[var2 + 1] = 0;
            var4 += 2;
         } else {
            try {
               var4 += this.theAttributes.getDEREncoding(var1, var2 + var4, 8388608);
            } catch (AttributeException var6) {
               throw new CertificateException("Could not encode CertRequest: ", var6);
            }
         }

         return var4;
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
      this.checkVersionNumber(var1, (byte[])null, 0, 0);
   }

   /** @deprecated */
   public int getVersion() {
      return this.theVersion;
   }

   /** @deprecated */
   public void setSubjectName(X500Name var1) throws CertificateException {
      this.setSubjectName(var1, b.c());
   }

   /** @deprecated */
   public void setSubjectName(X500Name var1, boolean var2) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();
      if (var1 == null) {
         throw new CertificateException("Cannot set the cert with the given subjectName.");
      } else {
         try {
            this.subjectName = (X500Name)var1.clone();
            if (var2) {
               d.a(this.subjectName);
            }

         } catch (CloneNotSupportedException var4) {
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
   public void addAttribute(X501Attribute var1) {
      if (var1 != null) {
         if (this.theAttributes == null) {
            this.theAttributes = new X501Attributes();
         }

         this.theAttributes.addAttribute(var1);
      }

   }

   /** @deprecated */
   public void setAttributes(X501Attributes var1) throws CertificateException {
      this.clearSignature();
      this.clearTemplate();

      try {
         if (var1 != null) {
            this.theAttributes = (X501Attributes)var1.clone();
         } else {
            this.theAttributes = null;
         }

      } catch (CloneNotSupportedException var3) {
         throw new CertificateException("Could not set the request object with the given attributes.");
      }
   }

   /** @deprecated */
   public X501Attributes getAttributes() {
      if (this.theAttributes == null) {
         return null;
      } else {
         try {
            return (X501Attributes)this.theAttributes.clone();
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   /** @deprecated */
   public void signCertRequest(String var1, String var2, JSAFE_PrivateKey var3, SecureRandom var4) throws CertificateException {
      if (var1 != null && var2 != null && var3 != null) {
         this.clearSignature();
         if (this.requestInfoDER == null) {
            this.requestInfoDERLen = this.getCertRequestInfoDERLen(0);
            this.requestInfoDER = new byte[this.requestInfoDERLen];
            this.requestInfoDERLen = this.getCertRequestInfoDEREncoding(this.requestInfoDER, 0, 0);
         }

         byte[] var5 = this.performSignature(var1, var2, var3, var4, this.requestInfoDER, 0, this.requestInfoDERLen);

         try {
            BitStringContainer var6 = new BitStringContainer(0, true, 0, var5, 0, var5.length, var5.length * 8, false);
            ASN1Container[] var7 = new ASN1Container[]{var6};
            this.signature = ASN1.derEncode(var7);
         } catch (ASN_Exception var8) {
            this.clearSignature();
            throw new CertificateException("Cannot sign the cert as presently set.");
         }
      } else {
         throw new CertificateException("Specified values are null.");
      }
   }

   /** @deprecated */
   public boolean verifyCertRequestSignature(String var1, SecureRandom var2) throws CertificateException {
      if (var1 == null) {
         throw new CertificateException("Specified values are null.");
      } else {
         if (this.requestInfo == null) {
            if (this.requestInfoDER == null) {
               throw new CertificateException("Cannot verify the signature, not all components are set.");
            }

            this.requestInfo = new byte[this.requestInfoDERLen];
            System.arraycopy(this.requestInfoDER, 0, this.requestInfo, 0, this.requestInfoDERLen);
         }

         if (this.signature != null && this.signatureAlgorithmBER != null) {
            byte[] var3 = this.getSignature();
            return this.performSignatureVerification(var1, var2, this.requestInfo, 0, this.requestInfo.length, var3, 0, var3.length);
         } else {
            throw new CertificateException("Cannot verify the signature, not all components are set.");
         }
      }
   }

   /** @deprecated */
   protected void clearTemplate() {
      this.asn1Template = null;
      this.asn1TemplateInfo = null;
      this.requestInfoDER = null;
      this.requestInfoDERLen = 0;
   }

   /** @deprecated */
   protected void clearComponents() {
      super.clearComponents();
      this.clearTemplate();
      this.requestInfo = null;
      this.theVersion = 0;
      this.subjectName = null;
      this.theAttributes = null;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (!(var1 instanceof PKCS10CertRequest)) {
         return false;
      } else {
         int var2 = this.getDERLen(0);
         if (var2 == 0) {
            return false;
         } else {
            byte[] var3 = new byte[var2];

            try {
               this.getDEREncoding(var3, 0, 0);
            } catch (CertificateException var9) {
               return false;
            }

            PKCS10CertRequest var4 = (PKCS10CertRequest)var1;
            int var5 = var4.getDERLen(0);
            if (var5 == 0) {
               return false;
            } else {
               byte[] var6 = new byte[var2];

               try {
                  var4.getDEREncoding(var6, 0, 0);
               } catch (CertificateException var8) {
                  return false;
               }

               return CertJUtils.byteArraysEqual(var3, var6);
            }
         }
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var1 = this.getDERLen(0);
      if (var1 == 0) {
         return 0;
      } else {
         byte[] var2 = new byte[var1];

         try {
            this.getDEREncoding(var2, 0, 0);
         } catch (CertificateException var4) {
            return 0;
         }

         return Arrays.hashCode(var2);
      }
   }
}
