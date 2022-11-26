package com.rsa.certj.pkcs7;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OIDList;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.CertJInternalHelper;
import com.rsa.certj.CertJUtils;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.cert.AttributeException;
import com.rsa.certj.cert.CRL;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X501Attributes;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.attributes.ContentType;
import com.rsa.certj.cert.attributes.MessageDigest;
import com.rsa.certj.cert.attributes.X501Attribute;
import com.rsa.certj.provider.db.MemoryDB;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.path.CertPathException;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MessageDigest;
import com.rsa.jsafe.JSAFE_Parameters;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_Signature;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/** @deprecated */
public class SignedData extends ContentInfo {
   /** @deprecated */
   protected static final int CONTENT_INFO_PREFIX_LEN = 4;
   /** @deprecated */
   protected int version = -1;
   /** @deprecated */
   protected Vector digestIDs = new Vector();
   /** @deprecated */
   protected Vector digests = new Vector();
   /** @deprecated */
   protected Vector digestLengths = new Vector();
   /** @deprecated */
   protected Vector digestNames = new Vector();
   /** @deprecated */
   protected Vector certs = new Vector();
   /** @deprecated */
   protected Vector crls = new Vector();
   /** @deprecated */
   protected Vector signers = new Vector();
   private boolean detachedFlag = false;
   /** @deprecated */
   protected boolean preDigestFlag = false;
   private Vector failSigners = new Vector();
   private Vector failedPathSigners = new Vector();
   private Vector notFoundCertSigners = new Vector();
   private static final String JAVA = "Java";

   /** @deprecated */
   public SignedData(CertJ var1, CertPathCtx var2) {
      this.contentType = 2;
      this.theCertJ = var1;
      this.theCertPathCtx = var2;
   }

   /** @deprecated */
   public void setContentInfo(ContentInfo var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Unable to set: content is null.");
      } else {
         try {
            this.content = (ContentInfo)var1.clone();
         } catch (CloneNotSupportedException var3) {
            throw new PKCS7Exception("Unable to clone content ", var3);
         }
      }
   }

   /** @deprecated */
   public void setVersionNumber(int var1) {
      this.version = var1;
   }

   /** @deprecated */
   public void setDigest(byte[] var1, String var2) throws PKCS7Exception {
      int var3 = this.digestNames.indexOf(var2);
      if (var3 == -1) {
         byte[] var4 = DigestedData.setDigestAlgorithmInternal(this.theCertJ, var2);
         this.digestIDs.addElement(var4);
         this.digestNames.addElement(var2);
         this.digests.addElement(var1);
         this.digestLengths.addElement(new Integer(var1.length));
      } else {
         this.digests.setElementAt(var1, var3);
         this.digestLengths.setElementAt(new Integer(var1.length), var3);
      }

      this.preDigestFlag = true;
   }

   /** @deprecated */
   public int getVersionNumber() {
      return this.version;
   }

   /** @deprecated */
   public void addCertificate(X509Certificate var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Certificate is null.");
      } else {
         this.certs.addElement(var1);
      }
   }

   /** @deprecated */
   public Vector getCertificates() throws PKCS7Exception {
      return new Vector(this.certs);
   }

   /** @deprecated */
   public Vector getCRLs() throws PKCS7Exception {
      return new Vector(this.crls);
   }

   /** @deprecated */
   public void addCRL(X509CRL var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("CRL is null.");
      } else {
         this.crls.addElement(var1);
      }
   }

   /** @deprecated */
   public void addSignerInfo(SignerInfo var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Cannot add this Signer Information: it is null.");
      } else {
         try {
            this.signers.addElement((SignerInfo)var1.clone());
         } catch (CloneNotSupportedException var3) {
            throw new PKCS7Exception("Cannot add this Signer Information: ", var3);
         }
      }
   }

   /** @deprecated */
   public void createDetachedSignature() {
      this.detachedFlag = true;
   }

   /** @deprecated */
   public Vector getSignerInfos() {
      return new Vector(this.signers);
   }

   /** @deprecated */
   protected int getContentDERLen() throws PKCS7Exception {
      return this.contentDEREncodeInit();
   }

   /** @deprecated */
   protected int writeContent(byte[] var1, int var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Specified array is null.");
      } else {
         try {
            if (this.contentASN1Template == null) {
               this.getContentDERLen();
            }

            int var3 = this.contentASN1Template.derEncode(var1, var2);
            this.contentASN1Template = null;
            return var3;
         } catch (ASN_Exception var4) {
            this.contentASN1Template = null;
            throw new PKCS7Exception("Unable to DER encode SignedData message: ", var4);
         }
      }
   }

   /** @deprecated */
   public int estimateContentInfoDERLen() throws PKCS7Exception {
      SequenceContainer var1 = new SequenceContainer(0, true, 0);
      EndContainer var2 = new EndContainer();

      try {
         OIDContainer var3 = new OIDContainer(16777216, true, 0, (byte[])null, 0, 9);
         EncodedContainer var4 = new EncodedContainer(10616576, true, 0, this.maxBufferSize, (byte[])null, 0, this.estimateContentDERLen());
         ASN1Container[] var5 = new ASN1Container[]{var1, var3, var4, var2};
         return (new ASN1Template(var5)).derEncodeInit();
      } catch (ASN_Exception var6) {
         throw new PKCS7Exception("ContentInfo.getASN1Containers: ", var6);
      }
   }

   /** @deprecated */
   protected int estimateContentDERLen() throws PKCS7Exception {
      try {
         Vector var1 = new Vector();
         Iterator var2 = this.signers.iterator();

         try {
            while(var2.hasNext()) {
               var1.add((SignerInfo)((SignerInfo)var2.next()).clone());
            }
         } catch (CloneNotSupportedException var18) {
            throw new PKCS7Exception(var18);
         }

         if (this.flag == 0) {
            this.estimationSetContentData(var1);
         }

         EncodedContainer var4;
         EncodedContainer var5;
         OfContainer var6;
         Vector var7;
         EncodedContainer var8;
         int var9;
         int var11;
         ASN1Container[] var26;
         ASN1Template var27;
         if (!this.digestIDs.isEmpty()) {
            var5 = new EncodedContainer(12288, true, 0, (byte[])null, 0, 0);
            var6 = new OfContainer(0, true, 0, 12544, var5);
            var7 = new Vector();
            var7.addElement(var6);

            for(var9 = 0; var9 < this.digestIDs.size(); ++var9) {
               try {
                  byte[] var10 = (byte[])((byte[])this.digestIDs.elementAt(var9));
                  var8 = new EncodedContainer(12288, true, 0, (byte[])null, 0, var10.length);
                  var6.addContainer(var8);
               } catch (ASN_Exception var17) {
                  throw new PKCS7Exception("Unable to encode digest Alg IDs: ", var17);
               }
            }

            var26 = new ASN1Container[var7.size()];
            var7.copyInto(var26);
            var27 = new ASN1Template(var26);
            var11 = var27.derEncodeInit();
            var4 = new EncodedContainer(0, true, 0, (byte[])null, 0, var11);
         } else {
            var4 = new EncodedContainer(0, true, 0, (byte[])null, 0, 2);
         }

         EncodedContainer var3;
         if (!var1.isEmpty()) {
            var6 = new OfContainer(0, true, 0, 12544, new EncodedContainer(12288));
            var7 = new Vector();
            var7.addElement(var6);
            DatabaseService var23 = this.theCertPathCtx.getDatabase();
            if (var23 == null) {
               throw new PKCS7Exception("database field of theCertPathCtx object cannot be null.");
            }

            for(var9 = 0; var9 < var1.size(); ++var9) {
               try {
                  SignerInfo var28 = (SignerInfo)var1.elementAt(var9);
                  var11 = var28.getDERLen(0);
                  var3 = new EncodedContainer(0, true, 0, (byte[])null, 0, var11);
                  var6.addContainer(var3);
               } catch (ASN_Exception var16) {
                  throw new PKCS7Exception("Unable to encode SignerInfos: ", var16);
               }
            }

            var26 = new ASN1Container[var7.size()];
            var7.copyInto(var26);
            var27 = new ASN1Template(var26);
            var11 = var27.derEncodeInit();
            var5 = new EncodedContainer(0, true, 0, (byte[])null, 0, var11);
         } else {
            var5 = new EncodedContainer(0, true, 0, (byte[])null, 0, 2);
         }

         EncodedContainer var20 = null;
         if (!this.certs.isEmpty()) {
            OfContainer var21 = new OfContainer(8454144, true, 0, 12544, new EncodedContainer(12288));
            Vector var24 = new Vector();
            var24.addElement(var21);
            Iterator var29 = this.certs.iterator();

            while(var29.hasNext()) {
               X509Certificate var30 = (X509Certificate)var29.next();

               try {
                  var11 = var30.getDERLen(0);
                  var3 = new EncodedContainer(0, true, 0, (byte[])null, 0, var11);
                  var21.addContainer(var3);
               } catch (ASN_Exception var15) {
                  throw new PKCS7Exception("Unable to encode Certificates: ", var15);
               }
            }

            var26 = new ASN1Container[var24.size()];
            var24.copyInto(var26);
            var27 = new ASN1Template(var26);
            var11 = var27.derEncodeInit();
            var20 = new EncodedContainer(0, true, 0, (byte[])null, 0, var11);
         }

         EncodedContainer var22 = null;
         if (!this.crls.isEmpty()) {
            OfContainer var25 = new OfContainer(8454145, true, 0, 12544, new EncodedContainer(12288));
            Vector var31 = new Vector();
            var31.addElement(var25);
            Iterator var32 = this.crls.iterator();

            int var12;
            while(var32.hasNext()) {
               X509CRL var34 = (X509CRL)var32.next();

               try {
                  var12 = var34.getDERLen(0);
                  var3 = new EncodedContainer(0, true, 0, (byte[])null, 0, var12);
                  var25.addContainer(var3);
               } catch (ASN_Exception var14) {
                  throw new PKCS7Exception("Unable to encode CRLs: ", var14);
               }
            }

            ASN1Container[] var33 = new ASN1Container[var31.size()];
            var31.copyInto(var33);
            ASN1Template var37 = new ASN1Template(var33);
            var12 = var37.derEncodeInit();
            var22 = new EncodedContainer(0, true, 0, (byte[])null, 0, var12);
         }

         if (this.content == null && !this.preDigestFlag) {
            throw new PKCS7Exception("Content is NULL.");
         } else {
            if (this.detachedFlag) {
               if (this.oid == null) {
                  var9 = 13;
               } else {
                  var9 = this.oid.length + 4;
               }
            } else if (this.contentEncoding == null) {
               var9 = this.content.estimateContentInfoDERLen();
            } else {
               var9 = this.contentEncoding.length;
            }

            var8 = new EncodedContainer(0, true, 0, (byte[])null, 0, var9);
            if (this.version == -1) {
               this.version = 1;
            }

            SequenceContainer var35 = new SequenceContainer(10551296, true, 0);
            EndContainer var38 = new EndContainer();
            IntegerContainer var36 = new IntegerContainer(0, true, 0, this.version);
            ASN1Container[] var13;
            if (!this.crls.isEmpty()) {
               if (!this.certs.isEmpty()) {
                  var13 = new ASN1Container[]{var35, var36, var4, var8, var20, var22, var5, var38};
                  this.contentASN1Template = new ASN1Template(var13);
               } else {
                  var13 = new ASN1Container[]{var35, var36, var4, var8, var22, var5, var38};
                  this.contentASN1Template = new ASN1Template(var13);
               }
            } else if (!this.certs.isEmpty()) {
               var13 = new ASN1Container[]{var35, var36, var4, var8, var20, var5, var38};
               this.contentASN1Template = new ASN1Template(var13);
            } else {
               var13 = new ASN1Container[]{var35, var36, var4, var8, var5, var38};
               this.contentASN1Template = new ASN1Template(var13);
            }

            return this.contentASN1Template.derEncodeInit();
         }
      } catch (ASN_Exception var19) {
         throw new PKCS7Exception("Could not DER encode SignedData: ", var19);
      }
   }

   private int contentDEREncodeInit() throws PKCS7Exception {
      try {
         if (this.flag == 0) {
            this.setContentData();
         }

         EncodedContainer var2;
         EncodedContainer var3;
         OfContainer var4;
         Vector var5;
         EncodedContainer var6;
         Iterator var7;
         int var9;
         byte[] var10;
         ASN1Container[] var27;
         ASN1Template var31;
         if (!this.digestIDs.isEmpty()) {
            var3 = new EncodedContainer(12288, true, 0, (byte[])null, 0, 0);
            var4 = new OfContainer(0, true, 0, 12544, var3);
            var5 = new Vector();
            var5.addElement(var4);
            var7 = this.digestIDs.iterator();

            while(var7.hasNext()) {
               byte[] var8 = (byte[])var7.next();

               try {
                  var6 = new EncodedContainer(12288, true, 0, var8, 0, var8.length);
                  var4.addContainer(var6);
               } catch (ASN_Exception var17) {
                  throw new PKCS7Exception("Unable to encode digest Alg IDs: ", var17);
               }
            }

            var27 = new ASN1Container[var5.size()];
            var5.copyInto(var27);
            var31 = new ASN1Template(var27);
            var9 = var31.derEncodeInit();
            var10 = new byte[var9];
            var9 = var31.derEncode(var10, 0);
            var2 = new EncodedContainer(0, true, 0, var10, 0, var9);
         } else {
            byte[] var19 = new byte[]{49, 0};
            var2 = new EncodedContainer(0, true, 0, var19, 0, 2);
         }

         EncodedContainer var1;
         if (!this.signers.isEmpty()) {
            var4 = new OfContainer(0, true, 0, 12544, new EncodedContainer(12288));
            var5 = new Vector();
            var5.addElement(var4);
            Iterator var24 = this.signers.iterator();

            int var32;
            byte[] var35;
            while(var24.hasNext()) {
               SignerInfo var29 = (SignerInfo)var24.next();

               try {
                  var32 = var29.getDERLen(0);
                  var35 = new byte[var32];
                  var32 = var29.getDEREncoding(var35, 0, 0);
                  var1 = new EncodedContainer(0, true, 0, var35, 0, var32);
                  var4.addContainer(var1);
               } catch (ASN_Exception var16) {
                  throw new PKCS7Exception("Unable to encode SignerInfos: ", var16);
               }
            }

            ASN1Container[] var25 = new ASN1Container[var5.size()];
            var5.copyInto(var25);
            ASN1Template var30 = new ASN1Template(var25);
            var32 = var30.derEncodeInit();
            var35 = new byte[var32];
            var32 = var30.derEncode(var35, 0);
            var3 = new EncodedContainer(0, true, 0, var35, 0, var32);
         } else {
            byte[] var20 = new byte[]{49, 0};
            var3 = new EncodedContainer(0, true, 0, var20, 0, 2);
         }

         EncodedContainer var21 = null;
         if (!this.certs.isEmpty()) {
            OfContainer var22 = new OfContainer(8454144, true, 0, 12544, new EncodedContainer(12288));
            Vector var26 = new Vector();
            var26.addElement(var22);
            var7 = this.certs.iterator();

            while(var7.hasNext()) {
               X509Certificate var37 = (X509Certificate)var7.next();

               try {
                  var9 = var37.getDERLen(0);
                  var10 = new byte[var9];
                  var9 = var37.getDEREncoding(var10, 0, 0);
                  var1 = new EncodedContainer(0, true, 0, var10, 0, var9);
                  var22.addContainer(var1);
               } catch (ASN_Exception var14) {
                  throw new PKCS7Exception("Unable to encode Certificates: ", var14);
               } catch (CertificateException var15) {
                  throw new PKCS7Exception("Unable to encode Certificates: ", var15);
               }
            }

            var27 = new ASN1Container[var26.size()];
            var26.copyInto(var27);
            var31 = new ASN1Template(var27);
            var9 = var31.derEncodeInit();
            var10 = new byte[var9];
            var9 = var31.derEncode(var10, 0);
            var21 = new EncodedContainer(0, true, 0, var10, 0, var9);
         }

         EncodedContainer var23 = null;
         if (!this.crls.isEmpty()) {
            OfContainer var28 = new OfContainer(8454145, true, 0, 12544, new EncodedContainer(12288));
            Vector var33 = new Vector();
            var33.addElement(var28);
            Iterator var38 = this.crls.iterator();

            byte[] var11;
            int var40;
            while(var38.hasNext()) {
               X509CRL var42 = (X509CRL)var38.next();

               try {
                  var40 = var42.getDERLen(0);
                  var11 = new byte[var40];
                  var40 = var42.getDEREncoding(var11, 0, 0);
                  var1 = new EncodedContainer(0, true, 0, var11, 0, var40);
                  var28.addContainer(var1);
               } catch (ASN_Exception var12) {
                  throw new PKCS7Exception("Unable to encode CRLs: ", var12);
               } catch (CertificateException var13) {
                  throw new PKCS7Exception("Unable to encode CRLs: ", var13);
               }
            }

            ASN1Container[] var39 = new ASN1Container[var33.size()];
            var33.copyInto(var39);
            ASN1Template var43 = new ASN1Template(var39);
            var40 = var43.derEncodeInit();
            var11 = new byte[var40];
            var40 = var43.derEncode(var11, 0);
            var23 = new EncodedContainer(0, true, 0, var11, 0, var40);
         }

         if (this.content == null && !this.preDigestFlag) {
            throw new PKCS7Exception("Content is NULL.");
         } else {
            if (this.detachedFlag) {
               this.assignDetachedMessageContentInfoSeq();
            } else if (this.contentEncoding == null) {
               int var34 = this.content.getContentInfoDERLen();
               this.contentEncoding = new byte[var34];
               this.content.writeMessage(this.contentEncoding, 0);
            }

            var6 = new EncodedContainer(0, true, 0, this.contentEncoding, 0, this.contentEncoding.length);
            if (this.version == -1) {
               this.version = 1;
            }

            SequenceContainer var36 = new SequenceContainer(10551296, true, 0);
            EndContainer var41 = new EndContainer();
            IntegerContainer var44 = new IntegerContainer(0, true, 0, this.version);
            ASN1Container[] var45;
            if (!this.crls.isEmpty()) {
               if (!this.certs.isEmpty()) {
                  var45 = new ASN1Container[]{var36, var44, var2, var6, var21, var23, var3, var41};
                  this.contentASN1Template = new ASN1Template(var45);
               } else {
                  var45 = new ASN1Container[]{var36, var44, var2, var6, var23, var3, var41};
                  this.contentASN1Template = new ASN1Template(var45);
               }
            } else if (!this.certs.isEmpty()) {
               var45 = new ASN1Container[]{var36, var44, var2, var6, var21, var3, var41};
               this.contentASN1Template = new ASN1Template(var45);
            } else {
               var45 = new ASN1Container[]{var36, var44, var2, var6, var3, var41};
               this.contentASN1Template = new ASN1Template(var45);
            }

            return this.contentASN1Template.derEncodeInit();
         }
      } catch (ASN_Exception var18) {
         throw new PKCS7Exception("Could not DER encode SignedData: ", var18);
      }
   }

   /** @deprecated */
   protected void assignDetachedMessageContentInfoSeq() {
      this.contentEncoding = new byte[13];
      this.contentEncoding[0] = 48;
      this.contentEncoding[1] = 11;
      this.contentEncoding[2] = 6;
      this.contentEncoding[3] = 9;
      System.arraycopy(P7OID, 0, this.contentEncoding, 4, 9);
      this.contentEncoding[12] = this.preDigestFlag ? 1 : (byte)this.content.getContentType();
   }

   /** @deprecated */
   protected void digestContentData(int var1) throws PKCS7Exception {
      byte[] var2 = null;
      if (!this.preDigestFlag) {
         if (this.contentEncoding == null) {
            if (this.content == null) {
               throw new PKCS7Exception("There is no content to sign.");
            }

            int var3 = this.content.getContentInfoDERLen();
            this.contentEncoding = new byte[var3];
            this.content.writeMessage(this.contentEncoding, 0);
         }

         var2 = this.getEncodedContent();
      }

      try {
         if (var1 == 0) {
            Iterator var4 = this.signers.iterator();

            while(var4.hasNext()) {
               SignerInfo var5 = (SignerInfo)var4.next();
               String var6 = var5.getDigestAlgorithmName();
               if (var6 == null) {
                  throw new PKCS7Exception("Could not DER encode ContentInfo: Signer's digest algorithm is not set.");
               }

               this.addDigestAlgorithm(var5.getDigestAlgorithmOID(), var6);
            }
         }

         if (var2 != null) {
            if (!this.preDigestFlag) {
               for(int var10 = 0; var10 < this.digestIDs.size(); ++var10) {
                  byte[] var11 = (byte[])this.digestIDs.elementAt(var10);
                  JSAFE_MessageDigest var12 = h.f(var11, 0, this.getDeviceOrJava(), (CertJ)this.theCertJ);
                  byte[] var7 = (byte[])this.digests.elementAt(var10);
                  if (var7 == null) {
                     var12.digestInit();
                     var12.digestUpdate(var2, 0, var2.length);
                     byte[] var9 = var12.digestFinal();
                     var12.clearSensitiveData();
                     this.digests.setElementAt(var9, var10);
                     this.digestLengths.setElementAt(new Integer(var9.length), var10);
                  }
               }
            }

         }
      } catch (JSAFE_Exception var8) {
         throw new PKCS7Exception("Could not digest ContentInfo: ", var8);
      }
   }

   /** @deprecated */
   protected byte[] initOctets(ASN1Container var1, ASN1Container var2) throws ASN_Exception {
      byte[] var3 = null;
      if (CertJUtils.byteArraysEqual(var1.data, var1.dataOffset, var1.dataLen - 1, P7OID, 0, P7OID.length - 1)) {
         switch (var1.data[var1.dataOffset + var1.dataLen - 1]) {
            case 1:
               var3 = this.initDataOctets(var1, var2);
               break;
            default:
               int var4 = 1;
               var4 += ASN1Lengths.determineLengthLen(var2.data, var2.dataOffset + 1);
               ++var4;
               var4 += ASN1Lengths.determineLengthLen(var2.data, var2.dataOffset + var4);
               var3 = new byte[var2.dataLen - var4];
               System.arraycopy(var2.data, var2.dataOffset + var4, var3, 0, var2.dataLen - var4);
         }
      }

      return var3;
   }

   /** @deprecated */
   protected byte[] initDataOctets(ASN1Container var1, ASN1Container var2) throws ASN_Exception {
      OctetStringContainer var4 = new OctetStringContainer(10551296, true, 0, var2.data, var2.dataOffset, var2.dataLen);
      ASN1Container[] var5 = new ASN1Container[]{var4};
      ASN1.berDecode(var2.data, var2.dataOffset, var5);
      byte[] var3;
      if (var4.data != null && var4.dataLen != 0) {
         var3 = new byte[var4.dataLen];
         System.arraycopy(var4.data, var4.dataOffset, var3, 0, var4.dataLen);
      } else {
         var3 = new byte[0];
      }

      return var3;
   }

   /** @deprecated */
   protected void estimateDigestLens() throws PKCS7Exception {
      Enumeration var1 = this.signers.elements();

      while(var1.hasMoreElements()) {
         SignerInfo var2 = (SignerInfo)var1.nextElement();
         this.addDigestAlgorithm(var2.getDigestAlgorithmOID(), var2.getDigestAlgorithmName());
      }

      if (!this.preDigestFlag) {
         for(int var6 = 0; var6 < this.digestIDs.size(); ++var6) {
            byte[] var7 = (byte[])this.digestIDs.elementAt(var6);

            JSAFE_MessageDigest var3;
            try {
               var3 = h.f(var7, 0, this.getDeviceOrJava(), (CertJ)this.theCertJ);
            } catch (JSAFE_Exception var5) {
               throw new PKCS7Exception("Could not digest ContentInfo: ", var5);
            }

            int var4 = var3.getDigestSize();
            this.digestLengths.setElementAt(new Integer(var4), var6);
         }
      }

   }

   private void addDigestAlgorithm(byte[] var1, String var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Cannot add digest algorithm. Digest OID is null.");
      } else {
         if (var2 == null) {
            var2 = DigestedData.getDigestAlgorithmNameInternal((CertJ)this.theCertJ, var1, (String)null);
         }

         if (!this.digestNames.contains(var2)) {
            this.digestNames.addElement(var2);
            this.digestIDs.addElement(var1);
            this.digests.addElement((Object)null);
            this.digestLengths.addElement((Object)null);
         }

      }
   }

   private void estimationSetContentData(Vector var1) throws PKCS7Exception {
      this.estimateDigestLens();
      if (!var1.isEmpty()) {
         if (this.theCertJ == null) {
            throw new PKCS7Exception("CertJ object cannot be null.");
         } else if (this.theCertPathCtx == null) {
            throw new PKCS7Exception("CertPathCtx object cannot be null.");
         } else {
            DatabaseService var2 = this.theCertPathCtx.getDatabase();
            if (var2 == null) {
               throw new PKCS7Exception("database field of theCertPathCtx object cannot be null.");
            } else {
               Iterator var3 = var1.iterator();

               while(var3.hasNext()) {
                  SignerInfo var4 = (SignerInfo)var3.next();
                  int var5 = 0;
                  this.estimationSetSignerDigest(var4);
                  String var6 = var4.getEncryptionAlgorithmName();
                  if ("DSA".equals(var6)) {
                     var5 = 48;
                  }

                  if (var5 == 0) {
                     X509Certificate var7 = null;
                     Iterator var8 = this.certs.iterator();

                     while(var8.hasNext()) {
                        X509Certificate var9 = (X509Certificate)var8.next();
                        if (var9.getIssuerName().equals(var4.getIssuerName()) && CertJUtils.byteArraysEqual(var9.getSerialNumber(), var4.getSerialNumber())) {
                           var7 = var9;
                           break;
                        }
                     }

                     if (var7 == null) {
                        Vector var14 = new Vector();

                        int var17;
                        try {
                           var17 = var2.selectCertificateByIssuerAndSerialNumber(var4.getIssuerName(), var4.getSerialNumber(), var14);
                        } catch (NoServiceException var12) {
                           throw new PKCS7Exception("Could not estimate SignedData length: ", var12);
                        } catch (DatabaseException var13) {
                           throw new PKCS7Exception("Cannot get Cert from DB: ", var13);
                        }

                        if (var17 == 0) {
                           throw new PKCS7Exception("Certificate is missing.");
                        }

                        var7 = (X509Certificate)var14.elementAt(0);
                     }

                     try {
                        JSAFE_PublicKey var16 = var7.getSubjectPublicKey("Java");
                        var5 = var16.getKeyData()[0].length;
                     } catch (CertificateException var11) {
                        throw new PKCS7Exception("Cannot estimate signature length: ", var11);
                     }
                  }

                  byte[] var15 = new byte[var5];
                  var4.setEncryptedDigest(var15, 0, var15.length);
               }

            }
         }
      }
   }

   private void estimationSetSignerDigest(SignerInfo var1) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Cannot set SignerDigest: SignerInfo is null");
      } else {
         String var2 = var1.getDigestAlgorithmName();
         if (var2 == null) {
            throw new PKCS7Exception("Signer's digest algorithm is not set.");
         } else {
            int var3 = this.digestNames.indexOf(var2);
            Integer var4 = (Integer)this.digestLengths.elementAt(var3);
            if (var4 == null) {
               throw new PKCS7Exception("Cannot determine digest length");
            } else {
               X501Attributes var5 = var1.getAuthenticatedAttrs();
               if (var5 != null || this.content != null && this.content.getContentType() != 1) {
                  if (var5 == null) {
                     var5 = new X501Attributes();
                  }

                  ContentType var6 = (ContentType)var5.getAttributeByType(14);
                  if (var6 == null) {
                     var6 = new ContentType();
                  }

                  int var7;
                  if (this.preDigestFlag) {
                     var7 = 1;
                  } else {
                     var7 = this.content.getContentType();
                  }

                  byte[] var8 = new byte[9];
                  System.arraycopy(P7OID, 0, var8, 0, 9);
                  var8[8] = (byte)var7;
                  var6.setContentType(var8, 0, 9);
                  var5.addAttribute(var6);
                  MessageDigest var9 = (MessageDigest)var5.getAttributeByType(15);
                  if (var9 == null) {
                     var9 = new MessageDigest();
                  }

                  byte[] var10 = new byte[var4];
                  var9.setMessageDigest(var10, 0, var10.length);
                  var5.addAttribute(var9);
                  var1.setAuthenticatedAttrs(var5);
               }

            }
         }
      }
   }

   private void setContentData() throws PKCS7Exception {
      this.digestContentData(0);
      if (!this.signers.isEmpty()) {
         if (this.theCertJ == null) {
            throw new PKCS7Exception("CertJ object cannot be null.");
         } else if (this.theCertPathCtx == null) {
            throw new PKCS7Exception("CertPathCtx object cannot be null.");
         } else {
            DatabaseService var1 = this.theCertPathCtx.getDatabase();
            if (var1 == null) {
               throw new PKCS7Exception("Database field of the CertPathCtx object cannot be null");
            } else {
               DatabaseService var2 = null;
               MemoryDB var3 = null;
               if (!this.certs.isEmpty()) {
                  var3 = this.storeIntoTempDB(this.certs, (Vector)null);
                  var2 = this.addTempDB(var1, var3.getName());
               }

               DatabaseService var4 = var2 == null ? var1 : var2;

               SignerInfo var7;
               byte[] var8;
               for(Iterator var6 = this.signers.iterator(); var6.hasNext(); var7.setEncryptedDigest(var8, 0, var8.length)) {
                  var7 = (SignerInfo)var6.next();
                  String var5 = var7.getEncryptionAlgorithmName();
                  this.setupAttributes(var7, 0);
                  if (var5.indexOf("DSA") != -1) {
                     var8 = this.signDSAECDSA(var7, var4);
                  } else {
                     var8 = this.signRSA(var7, var4);
                  }
               }

               if (var2 != null) {
                  this.theCertJ.unbindService(var2);
               }

               if (var3 != null) {
                  try {
                     this.theCertJ.unregisterService(1, var3.getName());
                  } catch (InvalidParameterException var9) {
                  }
               }

            }
         }
      }
   }

   private void checkAttributes(SignerInfo var1) throws PKCS7Exception {
      X501Attributes var2 = var1.getAuthenticatedAttrs();
      if (var2 != null || this.content != null && this.content.getContentType() != 1) {
         if (var2 == null) {
            var2 = new X501Attributes();
         }

         X501Attribute var3 = var2.getAttributeByType(14);
         if (var3 == null) {
            throw new PKCS7Exception("ContentType attribute is missing");
         }

         X501Attribute var4 = var2.getAttributeByType(15);
         if (var4 == null) {
            throw new PKCS7Exception("MessageDigest attribute is missing");
         }
      }

   }

   private byte[] getHashOfContent(SignerInfo var1) throws PKCS7Exception {
      String var3 = var1.getDigestAlgorithmName();
      if (var3 == null) {
         throw new PKCS7Exception("Signers digest algorithm is not set");
      } else {
         this.setupAttributes(var1, 1);
         int var4 = this.digestNames.indexOf(var3);
         byte[] var2 = (byte[])this.digests.elementAt(var4);
         X501Attributes var5 = var1.getAuthenticatedAttrs();
         if (var5 != null || this.content != null && this.content.getContentType() != 1) {
            byte[] var6 = this.getEncodedAttributes(var1);
            var2 = this.hashMessage(var3, var6);
         }

         return var2;
      }
   }

   private void setupAttributes(SignerInfo var1, int var2) throws PKCS7Exception {
      X501Attributes var3 = var1.getAuthenticatedAttrs();
      if (var3 != null || this.content != null && this.content.getContentType() != 1) {
         String var4 = var1.getDigestAlgorithmName();
         int var5 = this.digestNames.indexOf(var4);
         byte[] var6 = (byte[])this.digests.elementAt(var5);
         if (var3 == null) {
            var3 = new X501Attributes();
         }

         Object var7 = var3.getAttributeByType(14);
         if (var7 == null) {
            var7 = new ContentType();
         }

         byte[] var8 = this.getOidType();
         ((ContentType)var7).setContentType(var8, 0, var8.length);
         var3.addAttribute((X501Attribute)var7);
         Object var9 = var3.getAttributeByType(15);
         if (var9 == null) {
            var9 = new MessageDigest();
         }

         if (var2 == 0) {
            if (var6 != null) {
               ((MessageDigest)var9).setMessageDigest(var6, 0, var6.length);
               var3.addAttribute((X501Attribute)var9);
               var1.setAuthenticatedAttrs(var3);
            }
         } else {
            byte[] var10 = ((MessageDigest)var9).getMessageDigest();
            if (!CertJUtils.byteArraysEqual(var10, var6)) {
               throw new PKCS7Exception("MessageDigest attribute value can not be verified.");
            }
         }
      }

   }

   private byte[] signDSAECDSA(SignerInfo var1, DatabaseService var2) throws PKCS7Exception {
      String var3 = var1.getDigestAlgorithmName();
      String var4 = var1.getEncryptionAlgorithmName();
      byte[] var5 = null;
      X501Attributes var7 = var1.getAuthenticatedAttrs();
      String var6 = var3 + "/" + var4 + "/NoPad";
      if (var7 != null) {
         var5 = this.getEncodedAttributes(var1);
      }

      if (var5 == null) {
         var5 = this.getEncodedContent();
      }

      JSAFE_PrivateKey var8 = this.getPrivateKeyFromDatabase(var2, var1);
      JSAFE_Signature var9 = null;

      byte[] var13;
      try {
         var9 = h.b(var6, this.getDeviceOrJava(), this.theCertJ);
         JSAFE_SecureRandom var10 = this.theCertJ.getRandomObject();
         var9.signInit(var8, (JSAFE_Parameters)null, var10, this.theCertJ.getPKCS11Sessions());
         int var11 = var9.getSignatureSize();
         byte[] var12 = new byte[var11];
         var9.signUpdate(var5, 0, var5.length);
         var11 = var9.signFinal(var12, 0);
         if (var11 != var12.length) {
            var13 = new byte[var11];
            System.arraycopy(var12, 0, var13, 0, var13.length);
            byte[] var14 = var13;
            return var14;
         }

         var13 = var12;
      } catch (JSAFE_Exception var20) {
         throw new PKCS7Exception("Could not perform Signing " + var20);
      } catch (NoServiceException var21) {
         throw new PKCS7Exception("Could not perform Signing " + var21);
      } catch (RandomException var22) {
         throw new PKCS7Exception("Could not perform Signing " + var22);
      } finally {
         if (var9 != null) {
            var9.clearSensitiveData();
         }

         if (var8 != null) {
            var8.clearSensitiveData();
         }

      }

      return var13;
   }

   private void verifyDSAECDSASignature(SignerInfo var1, JSAFE_PublicKey var2, byte[] var3) throws PKCS7Exception {
      String var4 = var1.getDigestAlgorithmName();
      String var5 = var1.getEncryptionAlgorithmName();
      byte[] var6 = null;
      X501Attributes var8 = var1.getAuthenticatedAttrs();
      String var7 = var4 + "/" + var5 + "/NoPad";
      if (var8 != null) {
         var6 = this.getEncodedAttributes(var1);
      }

      if (var6 == null) {
         var6 = this.getEncodedContent();
      }

      JSAFE_Signature var9 = null;

      try {
         var9 = h.b(var7, this.getDeviceOrJava(), this.theCertJ);
         JSAFE_SecureRandom var10 = this.theCertJ.getRandomObject();
         var9.verifyInit(var2, (JSAFE_Parameters)null, var10, this.theCertJ.getPKCS11Sessions());
         var9.verifyUpdate(var6, 0, var6.length);
         if (!var9.verifyFinal(var3, 0, var3.length)) {
            byte[] var11 = this.recodeSignature(var3);
            if (var11 == null) {
               this.failSigners.addElement(var1);
            } else {
               var9.verifyReInit();
               var9.verifyUpdate(var6, 0, var6.length);
               if (!var9.verifyFinal(var11, 0, var11.length)) {
                  this.failSigners.addElement(var1);
               }
            }
         }
      } catch (JSAFE_Exception var17) {
         throw new PKCS7Exception("Could not verify: ", var17);
      } catch (NoServiceException var18) {
         throw new PKCS7Exception("Could not verify: ", var18);
      } catch (RandomException var19) {
         throw new PKCS7Exception("Could not verify: ", var19);
      } finally {
         if (var9 != null) {
            var9.clearSensitiveData();
         }

      }

   }

   private byte[] recodeSignature(byte[] var1) {
      SequenceContainer var2 = new SequenceContainer(0);
      EndContainer var3 = new EndContainer();
      IntegerContainer var4 = new IntegerContainer(0);
      IntegerContainer var5 = new IntegerContainer(0);
      ASN1Container[] var6 = new ASN1Container[]{var2, var4, var5, var3};

      try {
         ASN1.berDecode(var1, 0, var6);
      } catch (ASN_Exception var17) {
         return null;
      }

      byte[] var7 = new byte[var4.dataLen];
      byte[] var8 = new byte[var5.dataLen];
      System.arraycopy(var4.data, var4.dataOffset, var7, 0, var7.length);
      System.arraycopy(var5.data, var5.dataOffset, var8, 0, var8.length);

      for(int var9 = var5.dataOffset + var5.dataLen; var9 < var1.length; ++var9) {
         if (var1[var9] != 0) {
            return null;
         }
      }

      SequenceContainer var18 = new SequenceContainer(0, true, 0);
      EndContainer var10 = new EndContainer();

      try {
         IntegerContainer var11 = new IntegerContainer(0, true, 0, var7, 0, var7.length, true);
         IntegerContainer var12 = new IntegerContainer(0, true, 0, var8, 0, var8.length, true);
         ASN1Container[] var13 = new ASN1Container[]{var18, var11, var12, var10};
         ASN1Template var14 = new ASN1Template(var13);
         var14.derEncodeInit();
         byte[] var15 = new byte[var7.length + var8.length + 6];
         var14.derEncode(var15, 0);
         return var15;
      } catch (ASN_Exception var16) {
         return null;
      }
   }

   private void verifyRSASignature(SignerInfo var1, JSAFE_PublicKey var2, byte[] var3) throws PKCS7Exception {
      String var4 = var1.getEncryptionAlgorithmName();
      String var6 = "NoDigest/" + var4 + "/PKCS1Block01Pad";
      byte[] var5 = this.getHashOfContent(var1);
      byte[] var7 = this.getDigestInfoBER(var1.getDigestAlgorithmOID(), var5);
      JSAFE_Signature var8 = null;

      try {
         var8 = h.b(var6, this.getDeviceOrJava(), this.theCertJ);
         JSAFE_SecureRandom var9 = this.theCertJ.getRandomObject();
         var8.verifyInit(var2, (JSAFE_Parameters)null, var9, this.theCertJ.getPKCS11Sessions());
         var8.verifyUpdate(var7, 0, var7.length);
         if (!var8.verifyFinal(var3, 0, var3.length)) {
            this.failSigners.addElement(var1);
         }
      } catch (JSAFE_Exception var15) {
         throw new PKCS7Exception("Could not verify: ", var15);
      } catch (NoServiceException var16) {
         throw new PKCS7Exception("Could not verify: ", var16);
      } catch (RandomException var17) {
         throw new PKCS7Exception("Could not verify: ", var17);
      } finally {
         if (var8 != null) {
            var8.clearSensitiveData();
         }

      }

   }

   private byte[] getEncodedAttributes(SignerInfo var1) throws PKCS7Exception {
      X501Attributes var2 = var1.getAuthenticatedAttrs();
      byte[] var3 = var1.getAuthenticatedAttrsBer();
      if (var3 == null) {
         var3 = new byte[var2.getDERLen(0)];

         try {
            var2.getDEREncoding(var3, 0, 0);
         } catch (AttributeException var5) {
            throw new PKCS7Exception("Could not get Attribute Encoding", var5);
         }
      }

      return var3;
   }

   private byte[] getEncodedContent() throws PKCS7Exception {
      Object var1 = null;
      if (this.contentEncoding == null) {
         if (this.content == null) {
            throw new PKCS7Exception("There is no content to sign.");
         }

         int var2 = this.content.getContentInfoDERLen();
         this.contentEncoding = new byte[var2];
         this.content.writeMessage(this.contentEncoding, 0);
      }

      SequenceContainer var10 = new SequenceContainer(0);
      EndContainer var3 = new EndContainer();
      OIDContainer var4 = new OIDContainer(16777216);
      EncodedContainer var5 = new EncodedContainer(10616576);
      ASN1Container[] var6 = new ASN1Container[]{var10, var4, var5, var3};

      try {
         ASN1.berDecode(this.contentEncoding, 0, var6);
         byte[] var9;
         if (var5.dataPresent) {
            var9 = this.initOctets(var4, var5);
         } else {
            var9 = new byte[0];
         }

         return var9;
      } catch (ASN_Exception var8) {
         throw new PKCS7Exception("Could not DER encode ContentInfo: ", var8);
      }
   }

   private byte[] signRSA(SignerInfo var1, DatabaseService var2) throws PKCS7Exception {
      String var3 = var1.getEncryptionAlgorithmName();
      byte[] var4 = this.getHashOfContent(var1);
      byte[] var5 = this.getDigestInfoBER(var1.getDigestAlgorithmOID(), var4);
      String var6 = "NoDigest/" + var3 + "/PKCS1Block01Pad";
      JSAFE_PrivateKey var7 = this.getPrivateKeyFromDatabase(var2, var1);
      JSAFE_Signature var8 = null;

      byte[] var12;
      try {
         var8 = h.b(var6, this.getDeviceOrJava(), this.theCertJ);
         JSAFE_SecureRandom var9 = this.theCertJ.getRandomObject();
         var8.signInit(var7, (JSAFE_Parameters)null, var9, this.theCertJ.getPKCS11Sessions());
         int var10 = var8.getSignatureSize();
         byte[] var11 = new byte[var10];
         var8.signUpdate(var5, 0, var5.length);
         var10 = var8.signFinal(var11, 0);
         if (var10 != var11.length) {
            var12 = new byte[var10];
            System.arraycopy(var11, 0, var12, 0, var12.length);
            byte[] var13 = var12;
            return var13;
         }

         var12 = var11;
      } catch (NoServiceException var19) {
         throw new PKCS7Exception("Could not perform Signing " + var19);
      } catch (JSAFE_Exception var20) {
         throw new PKCS7Exception("Could not perform Signing " + var20);
      } catch (RandomException var21) {
         throw new PKCS7Exception("Could not perform Signing " + var21);
      } finally {
         if (var8 != null) {
            var8.clearSensitiveData();
         }

         if (var7 != null) {
            var7.clearSensitiveData();
         }

      }

      return var12;
   }

   private void verifySignature() throws PKCS7Exception {
      if (!this.signers.isEmpty()) {
         if (this.theCertJ == null) {
            throw new PKCS7Exception("CertJ context object is null.");
         } else {
            DatabaseService var1 = null;
            if (this.theCertPathCtx != null) {
               var1 = this.theCertPathCtx.getDatabase();
            }

            DatabaseService var2 = null;
            MemoryDB var3 = null;
            String var4 = null;

            try {
               if (this.theCertPathCtx != null && (!this.certs.isEmpty() || !this.crls.isEmpty())) {
                  var3 = this.storeIntoTempDB(this.certs, this.crls);
                  var4 = var3.getName();
                  var2 = this.addTempDB(var1, var4);
               }

               DatabaseService var5 = var2 == null ? var1 : var2;
               if (this.certs.isEmpty() && var5 == null) {
                  throw new PKCS7Exception("Cannot get a certificate for all signers.");
               }

               Iterator var6 = this.signers.iterator();

               while(var6.hasNext()) {
                  SignerInfo var7 = (SignerInfo)var6.next();
                  CertPathCtx var9 = null;

                  JSAFE_PublicKey var8;
                  try {
                     Vector var10 = new Vector();
                     X509Certificate var11 = null;
                     if (var5 != null) {
                        int var12 = var5.selectCertificateByIssuerAndSerialNumber(var7.getIssuerName(), var7.getSerialNumber(), var10);
                        if (var12 == 0) {
                           this.notFoundCertSigners.addElement(var7);
                           continue;
                        }

                        var11 = (X509Certificate)var10.elementAt(0);
                        if (var2 != null && this.theCertPathCtx != null) {
                           var9 = new CertPathCtx(this.theCertPathCtx.getPathOptions(), this.theCertPathCtx.getTrustedCerts(), this.theCertPathCtx.getPolicies(), this.theCertPathCtx.getValidationTime(), var2);
                        }

                        CertPathCtx var13 = var9 == null ? this.theCertPathCtx : var9;
                        if (!this.theCertJ.buildCertPath(var13, var11, (Vector)null, (Vector)null, (Vector)null, (Vector)null)) {
                           this.failedPathSigners.addElement(var7);
                        }
                     } else {
                        if (this.certs.size() > 0 && (var7.getIssuerName() == null || var7.getSerialNumber() == null)) {
                           throw new PKCS7Exception("Signer's name or serial number is not set.");
                        }

                        Iterator var32 = this.certs.iterator();

                        while(var32.hasNext()) {
                           X509Certificate var35 = (X509Certificate)var32.next();
                           if (var7.getIssuerName().equals(var35.getIssuerName()) && CertJUtils.byteArraysEqual(var7.getSerialNumber(), var35.getSerialNumber())) {
                              var11 = var35;
                              break;
                           }
                        }

                        if (var11 == null) {
                           this.notFoundCertSigners.addElement(var7);
                           continue;
                        }

                        this.failedPathSigners.addElement(var7);
                     }

                     if (var11 == null) {
                        throw new PKCS7Exception("Cannot find signer's certificate.");
                     }

                     var8 = var11.getSubjectPublicKey(this.getDeviceOrJava());
                  } catch (NoServiceException var25) {
                     throw new PKCS7Exception("Cannot get Cert from DB: ", var25);
                  } catch (DatabaseException var26) {
                     throw new PKCS7Exception("Cannot get Cert from DB: ", var26);
                  } catch (CertificateException var27) {
                     throw new PKCS7Exception("Cannot get IssuerName and SerialNumber from cert: ", var27);
                  } catch (CertPathException var28) {
                     throw new PKCS7Exception("Cannot validate cert path: ", var28);
                  } catch (InvalidParameterException var29) {
                     throw new PKCS7Exception("Cannot validate cert path: ", var29);
                  }

                  byte[] var31 = var7.getDigestAlgorithmOID();
                  if (var31 == null) {
                     this.failSigners.addElement(var7);
                  } else {
                     String var33 = var7.getEncryptionAlgorithmName();
                     String var34 = var7.getDigestAlgorithmName();
                     if (var33 == null || var34 == null) {
                        throw new PKCS7Exception("Encryption Algorithm name or Digest name cannot be null");
                     }

                     this.checkAttributes(var7);
                     if (var33.indexOf("DSA") != -1) {
                        this.verifyDSAECDSASignature(var7, var8, var7.getEncryptedDigest());
                     } else {
                        this.verifyRSASignature(var7, var8, var7.getEncryptedDigest());
                     }
                  }
               }
            } finally {
               if (var2 != null) {
                  this.theCertJ.unbindService(var2);
               }

               if (var3 != null) {
                  try {
                     this.theCertJ.unregisterService(1, var3.getName());
                  } catch (InvalidParameterException var24) {
                  }
               }

            }

            if (!this.failSigners.isEmpty() || !this.failedPathSigners.isEmpty() && this.theCertPathCtx != null || !this.notFoundCertSigners.isEmpty()) {
               throw new PKCS7Exception(this.createExceptionMessage());
            }
         }
      }
   }

   private JSAFE_PrivateKey getPrivateKeyFromDatabase(DatabaseService var1, SignerInfo var2) throws PKCS7Exception {
      JSAFE_PrivateKey var3 = null;
      Vector var4 = new Vector();

      try {
         int var5 = var1.selectCertificateByIssuerAndSerialNumber(var2.getIssuerName(), var2.getSerialNumber(), var4);
         if (var5 == 0) {
            throw new PKCS7Exception("Certificate is missing.");
         }

         Iterator var6 = var4.iterator();

         while(var6.hasNext()) {
            Certificate var7 = (Certificate)var6.next();
            var3 = var1.selectPrivateKeyByCertificate(var7);
            if (var3 != null) {
               break;
            }
         }
      } catch (NoServiceException var8) {
         throw new PKCS7Exception("Could not perform Signing: " + var8);
      } catch (DatabaseException var9) {
         throw new PKCS7Exception("Could not perform Signing: " + var9);
      }

      if (var3 == null) {
         throw new PKCS7Exception("Private key is missing.");
      } else {
         var4.removeAllElements();
         return var3;
      }
   }

   private byte[] hashMessage(String var1, byte[] var2) throws PKCS7Exception {
      int var4 = this.digestNames.indexOf(var1);
      byte[] var5 = (byte[])this.digestIDs.elementAt(var4);
      JSAFE_MessageDigest var6 = null;

      try {
         var6 = h.f(var5, 0, "Java", (CertJ)this.theCertJ);
         var6.digestInit();
         var6.digestUpdate(var2, 0, var2.length);
         byte[] var3 = var6.digestFinal();
         return var3;
      } catch (JSAFE_Exception var8) {
         throw new PKCS7Exception("Could not create hash object");
      }
   }

   /** @deprecated */
   protected byte[] getSignerDigest(SignerInfo var1, int var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Cannot set SignerDigest: SignerInfo is null");
      } else {
         try {
            String var4 = var1.getDigestAlgorithmName();
            if (var4 == null) {
               throw new PKCS7Exception("Signer's digest algorithm is not set.");
            } else {
               int var5 = this.digestNames.indexOf(var4);
               byte[] var3 = (byte[])this.digests.elementAt(var5);
               byte[] var6 = (byte[])this.digestIDs.elementAt(var5);
               JSAFE_MessageDigest var7 = h.f(var6, 0, "Java", (CertJ)this.theCertJ);
               X501Attributes var8 = var1.getAuthenticatedAttrs();
               if (var8 != null || this.content != null && this.content.getContentType() != 1) {
                  if (var8 == null) {
                     var8 = new X501Attributes();
                  }

                  Object var9 = var8.getAttributeByType(14);
                  if (var9 == null) {
                     if (var2 == 1) {
                        throw new PKCS7Exception("While decoding, ContentType attribute is missing");
                     }

                     var9 = new ContentType();
                  }

                  if (var2 == 0) {
                     byte[] var10 = this.getOidType();
                     ((ContentType)var9).setContentType(var10, 0, var10.length);
                     var8.addAttribute((X501Attribute)var9);
                  }

                  Object var15 = var8.getAttributeByType(15);
                  if (var15 == null) {
                     if (var2 == 1) {
                        throw new PKCS7Exception("While decoding, MessageDigest attribute is missing");
                     }

                     var15 = new MessageDigest();
                  }

                  if (var2 == 0) {
                     if (var3 != null) {
                        ((MessageDigest)var15).setMessageDigest(var3, 0, var3.length);
                     }

                     var8.addAttribute((X501Attribute)var15);
                     var1.setAuthenticatedAttrs(var8);
                  } else {
                     byte[] var11 = ((MessageDigest)var15).getMessageDigest();
                     if (!CertJUtils.byteArraysEqual(var11, var3)) {
                        throw new PKCS7Exception("Value in MessageDigest attribute does not contain the right digest.");
                     }
                  }

                  byte[] var12 = var1.getAuthenticatedAttrsBer();
                  int var16;
                  if (var12 == null) {
                     var12 = new byte[var8.getDERLen(0)];
                     var16 = var8.getDEREncoding(var12, 0, 0);
                  } else {
                     var16 = var12.length;
                  }

                  var7.digestInit();
                  var7.digestUpdate(var12, 0, var16);
                  var3 = var7.digestFinal();
                  var7.clearSensitiveData();
               }

               return var3;
            }
         } catch (JSAFE_Exception var13) {
            throw new PKCS7Exception("Could not digest ContentInfo: ", var13);
         } catch (AttributeException var14) {
            throw new PKCS7Exception("Could not DER encode Attributes: ", var14);
         }
      }
   }

   /** @deprecated */
   protected byte[] getOidType() {
      int var1;
      if (this.preDigestFlag) {
         var1 = 1;
      } else {
         var1 = this.content.getContentType();
      }

      byte[] var2 = new byte[9];
      System.arraycopy(P7OID, 0, var2, 0, 9);
      var2[8] = (byte)var1;
      return var2;
   }

   private byte[] checkOID(byte[] var1) throws PKCS7Exception {
      SequenceContainer var3 = new SequenceContainer(0, true, 0);
      EndContainer var4 = new EndContainer();
      EncodedContainer var5 = new EncodedContainer(65536);
      OIDContainer var6 = new OIDContainer(0);
      ASN1Container[] var7 = new ASN1Container[]{var3, var6, var5, var4};
      OIDList.getTrans(var1, 0, var1.length, -1);

      try {
         ASN1.berDecode(var1, 0, var7);
      } catch (ASN_Exception var11) {
         throw new PKCS7Exception("Cannot decode algorithm OID: ", var11);
      }

      byte[] var2;
      if (var5.data == null) {
         byte[] var8 = new byte[]{5, 0};

         try {
            var5.addData(var8, 0, var8.length, true, true);
            var2 = ASN1.derEncode(var7);
         } catch (ASN_Exception var10) {
            throw new PKCS7Exception("Cannot encode algorithm OID: ", var10);
         }
      } else {
         var2 = var1;
      }

      return var2;
   }

   private byte[] getDigestInfoBER(byte[] var1, byte[] var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("DigestAlgorithm OID is null.");
      } else {
         try {
            byte[] var3 = this.checkOID(var1);
            SequenceContainer var4 = new SequenceContainer(0, true, 0);
            EndContainer var5 = new EndContainer();
            EncodedContainer var6 = new EncodedContainer(12288, true, 0, var3, 0, var3.length);
            int var7 = 0;
            if (var2 != null) {
               var7 = var2.length;
            }

            OctetStringContainer var8 = new OctetStringContainer(0, true, 0, var2, 0, var7);
            ASN1Container[] var9 = new ASN1Container[]{var4, var6, var8, var5};
            ASN1Template var10 = new ASN1Template(var9);
            int var11 = var10.derEncodeInit();
            byte[] var12 = new byte[var11];
            var10.derEncode(var12, 0);
            return var12;
         } catch (ASN_Exception var13) {
            throw new PKCS7Exception("Cannot encode digestInfo: ", var13);
         }
      }
   }

   /** @deprecated */
   protected boolean contentReadInit(byte[] var1, int var2, int var3) throws PKCS7Exception {
      try {
         SequenceContainer var4 = new SequenceContainer(10551296);
         EndContainer var5 = new EndContainer();
         IntegerContainer var6 = new IntegerContainer(0);
         EncodedContainer var7 = new EncodedContainer(12544);
         EncodedContainer var8 = new EncodedContainer(12288, true, 0, this.maxBufferSize, (byte[])null, 0, 0);
         EncodedContainer var9 = new EncodedContainer(8466688);
         EncodedContainer var10 = new EncodedContainer(8466689);
         EncodedContainer var11 = new EncodedContainer(12544);
         this.contentASN1Def = new ASN1Container[8];
         this.contentASN1Def[0] = var4;
         this.contentASN1Def[1] = var6;
         this.contentASN1Def[2] = var7;
         this.contentASN1Def[3] = var8;
         this.contentASN1Def[4] = var9;
         this.contentASN1Def[5] = var10;
         this.contentASN1Def[6] = var11;
         this.contentASN1Def[7] = var5;
         this.contentASN1Template = new ASN1Template(this.contentASN1Def);
         this.contentASN1Template.berDecodeInit();
         this.contentASN1Template.berDecodeUpdate(var1, var2, var3);
         if (this.contentASN1Def[0].checkTag()) {
            return false;
         } else {
            this.setDecodedValues();
            this.flag = 1;
            return true;
         }
      } catch (ASN_Exception var12) {
         throw new PKCS7Exception("Could not decode message: ", var12);
      }
   }

   private void setDecodedValues() throws PKCS7Exception {
      try {
         byte[] var1;
         if (this.version == -1) {
            if (!this.contentASN1Def[1].isComplete()) {
               if (this.contentASN1Def[1].data == null) {
                  return;
               }

               this.incompleteContainer = 1;
               this.copyNewData(this.contentASN1Def[1].data, this.contentASN1Def[1].dataOffset, this.contentASN1Def[1].dataLen, 0);
               return;
            }

            if (this.incompleteContainer == 1) {
               var1 = this.copyToOutput(this.contentASN1Def[1]);
               IntegerContainer var2 = new IntegerContainer(0, true, 0, var1, 0, var1.length, true);
               this.version = var2.getValueAsInt();
            } else {
               this.version = ((IntegerContainer)this.contentASN1Def[1]).getValueAsInt();
            }
         }

         if (this.digestIDs.isEmpty()) {
            if (!this.contentASN1Def[2].isComplete()) {
               if (this.contentASN1Def[2].data == null) {
                  return;
               }

               this.incompleteContainer = 2;
               this.copyNewData(this.contentASN1Def[2].data, this.contentASN1Def[2].dataOffset, this.contentASN1Def[2].dataLen, 0);
               return;
            }

            if (this.incompleteContainer == 2) {
               var1 = this.copyToOutput(this.contentASN1Def[2]);
               this.decodeDigestOIDs(var1, 0);
            } else {
               this.decodeDigestOIDs(this.contentASN1Def[2].data, this.contentASN1Def[2].dataOffset);
            }
         }

         if (this.content == null) {
            if (!this.contentASN1Def[3].isComplete()) {
               if (this.contentASN1Def[3].data == null) {
                  return;
               }

               this.incompleteContainer = 3;
               this.copyNewData(this.contentASN1Def[3].data, this.contentASN1Def[3].dataOffset, this.contentASN1Def[3].dataLen, this.maxBufferSize);
               return;
            }

            if (this.incompleteContainer == 3) {
               var1 = this.copyToOutput(this.contentASN1Def[3]);
               this.decodeContent(var1, 0, var1.length);
            } else {
               this.decodeContent(this.contentASN1Def[3].data, this.contentASN1Def[3].dataOffset, this.contentASN1Def[3].dataLen);
            }
         }

         if (this.noDigestsCalculated()) {
            this.digestContentData(1);
         }

         if (this.certs.isEmpty()) {
            if (!this.contentASN1Def[4].isComplete()) {
               if (this.contentASN1Def[4].data == null) {
                  return;
               }

               this.incompleteContainer = 4;
               this.copyNewData(this.contentASN1Def[4].data, this.contentASN1Def[4].dataOffset, this.contentASN1Def[4].dataLen, 0);
               return;
            }

            if (this.contentASN1Def[4].dataPresent) {
               if (this.incompleteContainer == 4) {
                  var1 = this.copyToOutput(this.contentASN1Def[4]);
                  this.decodeCerts(var1, 0);
               } else {
                  this.decodeCerts(this.contentASN1Def[4].data, this.contentASN1Def[4].dataOffset);
               }
            }
         }

         if (this.crls.isEmpty()) {
            if (!this.contentASN1Def[5].isComplete()) {
               if (this.contentASN1Def[5].data == null) {
                  return;
               }

               this.incompleteContainer = 5;
               this.copyNewData(this.contentASN1Def[5].data, this.contentASN1Def[5].dataOffset, this.contentASN1Def[5].dataLen, 0);
               return;
            }

            if (this.contentASN1Def[5].dataPresent) {
               if (this.incompleteContainer == 5) {
                  var1 = this.copyToOutput(this.contentASN1Def[5]);
                  this.decodeCRLs(var1, 0);
               } else {
                  this.decodeCRLs(this.contentASN1Def[5].data, this.contentASN1Def[5].dataOffset);
               }
            }
         }

         if (this.signers.isEmpty()) {
            if (!this.contentASN1Def[6].isComplete()) {
               if (this.contentASN1Def[6].data == null) {
                  return;
               }

               this.incompleteContainer = 6;
               this.copyNewData(this.contentASN1Def[6].data, this.contentASN1Def[6].dataOffset, this.contentASN1Def[6].dataLen, 0);
               return;
            }

            if (this.incompleteContainer == 6) {
               var1 = this.copyToOutput(this.contentASN1Def[6]);
               this.decodeSignerInfos(var1, 0);
            } else {
               this.decodeSignerInfos(this.contentASN1Def[6].data, this.contentASN1Def[6].dataOffset);
            }
         }

         if (!this.signers.isEmpty()) {
            this.verifySignature();
         }

      } catch (Exception var3) {
         throw new PKCS7Exception("Cannot set decoded values: ", var3);
      }
   }

   private void decodeDigestOIDs(byte[] var1, int var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Digest OIDs data is null.");
      } else {
         try {
            OfContainer var3 = new OfContainer(0, 12544, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               EncodedContainer var8 = new EncodedContainer(12288, true, 0, (byte[])null, 0, 0);
               ASN1Container[] var9 = new ASN1Container[]{var8};
               ASN1.berDecode(var7.data, var7.dataOffset, var9);
               byte[] var10 = new byte[var8.dataLen];
               System.arraycopy(var8.data, var8.dataOffset, var10, 0, var8.dataLen);
               this.addDigestAlgorithm(var10, (String)null);
            }

         } catch (ASN_Exception var11) {
            throw new PKCS7Exception("Cannot decode digest OIDs: ", var11);
         }
      }
   }

   /** @deprecated */
   protected void decodeContent(byte[] var1, int var2, int var3) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Content data is null.");
      } else {
         try {
            SequenceContainer var4 = new SequenceContainer(0);
            EndContainer var5 = new EndContainer();
            OIDContainer var6 = new OIDContainer(16777216);
            EncodedContainer var7 = new EncodedContainer(10616576, true, 0, this.maxBufferSize, (byte[])null, 0, 0);
            ASN1Container[] var8 = new ASN1Container[]{var4, var6, var7, var5};
            ASN1.berDecode(var1, var2, var8);
            this.content = this.buildContentContainer(var6.data, var6.dataOffset, var6.dataLen);
            if (this.content.readInit(var1, var2, var3, this.maxBufferSize) && this.content.readFinal()) {
               return;
            }
         } catch (Exception var9) {
            throw new PKCS7Exception("Cannot decode content: ", var9);
         }

         throw new PKCS7Exception("Cannot decode content.");
      }
   }

   /** @deprecated */
   protected ContentInfo buildContentContainer(byte[] var1, int var2, int var3) throws PKCS7Exception {
      return ContentInfo.getInstance(var1, var2, var3, this.theCertJ, this.theCertPathCtx);
   }

   private void decodeCerts(byte[] var1, int var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("Certs data is null.");
      } else {
         try {
            OfContainer var3 = new OfContainer(8454144, 12544, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               X509Certificate var8 = new X509Certificate(var7.data, var7.dataOffset, 0);
               this.addCertificate(var8);
            }

         } catch (Exception var9) {
            throw new PKCS7Exception("Cannot decode certs: ", var9);
         }
      }
   }

   private void decodeCRLs(byte[] var1, int var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("CRLs data is null.");
      } else {
         try {
            OfContainer var3 = new OfContainer(8454145, 12544, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               X509CRL var8 = new X509CRL(var7.data, var7.dataOffset, 0);
               this.addCRL(var8);
            }

         } catch (Exception var9) {
            throw new PKCS7Exception("Cannot decode crls: ", var9);
         }
      }
   }

   private void decodeSignerInfos(byte[] var1, int var2) throws PKCS7Exception {
      if (var1 == null) {
         throw new PKCS7Exception("SignerInfo data is null.");
      } else {
         try {
            OfContainer var3 = new OfContainer(0, 12544, new EncodedContainer(12288));
            ASN1Container[] var4 = new ASN1Container[]{var3};
            ASN1.berDecode(var1, var2, var4);
            int var5 = var3.getContainerCount();

            for(int var6 = 0; var6 < var5; ++var6) {
               ASN1Container var7 = var3.containerAt(var6);
               SignerInfo var8 = new SignerInfo(CertJInternalHelper.context(this.theCertJ), var7.data, var7.dataOffset, 0);
               this.addSignerInfo(var8);
            }

         } catch (Exception var9) {
            throw new PKCS7Exception("Cannot decode signerInfo: ", var9);
         }
      }
   }

   private String createExceptionMessage() {
      StringBuffer var1 = new StringBuffer();
      Iterator var2;
      SignerInfo var3;
      if (!this.notFoundCertSigners.isEmpty()) {
         var1.append("Cannot find certificates for signers: ");
         var2 = this.notFoundCertSigners.iterator();

         while(var2.hasNext()) {
            var3 = (SignerInfo)var2.next();

            try {
               if (var3.getIssuerName() != null && var3.getSerialNumber() != null) {
                  var1.append("\nIssuer Name ");
                  var1.append(var3.getIssuerName().toString());
                  var1.append(" , Serial Number ");
                  var1.append(this.printBuffer(var3.getSerialNumber()));
               }
            } catch (PKCS7Exception var7) {
            }
         }
      }

      if (!this.failedPathSigners.isEmpty()) {
         var1.append("\nCannot build and verify certPath for signers: ");
         var2 = this.failedPathSigners.iterator();

         while(var2.hasNext()) {
            var3 = (SignerInfo)var2.next();

            try {
               if (var3.getIssuerName() != null && var3.getSerialNumber() != null) {
                  var1.append("\n Issuer Name ");
                  var1.append(var3.getIssuerName().toString());
                  var1.append(" , Serial Number ");
                  var1.append(this.printBuffer(var3.getSerialNumber()));
               }
            } catch (PKCS7Exception var6) {
            }
         }
      }

      if (!this.failSigners.isEmpty()) {
         var1.append("\nCannot verify signature for signers: ");
         var2 = this.failSigners.iterator();

         while(var2.hasNext()) {
            var3 = (SignerInfo)var2.next();

            try {
               if (var3.getIssuerName() != null && var3.getSerialNumber() != null) {
                  var1.append("\n Issuer Name ");
                  var1.append(var3.getIssuerName().toString());
                  var1.append(" , Serial Number ");
                  var1.append(this.printBuffer(var3.getSerialNumber()));
               }
            } catch (PKCS7Exception var5) {
            }
         }
      }

      return var1.toString();
   }

   private String printBuffer(byte[] var1) {
      return this.printBuffer(var1, 0, var1.length);
   }

   private String printBuffer(byte[] var1, int var2, int var3) {
      StringBuffer var6 = new StringBuffer();

      while(var3 > 0) {
         for(int var7 = 0; var7 < 8 && var3 != 0; ++var2) {
            int var4 = var1[var2] & 255;
            String var5 = Integer.toHexString(var4);
            if (var5.length() == 1) {
               var6.append(" 0");
            } else {
               var6.append(" ");
            }

            var6.append(var5);
            --var3;
            ++var7;
         }

         var6.append("\n");
      }

      return var6.toString();
   }

   /** @deprecated */
   public Vector getFailedSigners() {
      return this.failSigners.isEmpty() ? null : new Vector(this.failSigners);
   }

   /** @deprecated */
   public Vector getFailedCertPathSigners() {
      return this.failedPathSigners.isEmpty() ? null : new Vector(this.failedPathSigners);
   }

   /** @deprecated */
   protected int contentReadUpdate(byte[] var1, int var2, int var3) throws PKCS7Exception {
      int var4 = 0;
      if (var1 == null) {
         return var4;
      } else if (this.contentASN1Template == null) {
         throw new PKCS7Exception("Call readInit before readUpdate.");
      } else {
         try {
            if (this.contentASN1Template.isComplete()) {
               return var4;
            } else {
               var4 = this.contentASN1Template.berDecodeUpdate(var1, var2, var3);
               this.setDecodedValues();
               return var4;
            }
         } catch (ASN_Exception var6) {
            throw new PKCS7Exception("Could not decode message: ", var6);
         }
      }
   }

   /** @deprecated */
   protected boolean contentReadFinal() throws PKCS7Exception {
      try {
         this.contentASN1Template.berDecodeFinal();
      } catch (ASN_Exception var2) {
         throw new PKCS7Exception("Cannot call readFinal: ", var2);
      }

      if (!this.contentASN1Template.isComplete()) {
         throw new PKCS7Exception("Cannot call readFinal, more message expected.");
      } else {
         return true;
      }
   }

   /** @deprecated */
   public Object clone() throws CloneNotSupportedException {
      SignedData var1 = (SignedData)super.clone();
      var1.version = this.version;
      var1.detachedFlag = this.detachedFlag;
      var1.preDigestFlag = this.preDigestFlag;
      if (this.digestIDs != null) {
         var1.digestIDs = new Vector(this.digestIDs);
      }

      if (this.digestNames != null) {
         var1.digestNames = new Vector(this.digestNames);
      }

      if (this.digestIDs != null) {
         var1.digests = new Vector(this.digests);
      }

      if (this.certs != null) {
         var1.certs = new Vector(this.certs);
      }

      if (this.crls != null) {
         var1.crls = new Vector(this.crls);
      }

      if (this.signers != null) {
         var1.signers = new Vector(this.signers);
      }

      return var1;
   }

   /** @deprecated */
   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof SignedData) {
         SignedData var2 = (SignedData)var1;
         if (var2.version != this.version) {
            return false;
         } else if (this.content != null && !this.content.equals(var2.content)) {
            return false;
         } else if (var2.preDigestFlag != this.preDigestFlag) {
            return false;
         } else {
            int var3 = this.signers.size();
            int var4 = var2.signers.size();
            if (var3 != var4) {
               return false;
            } else {
               int var5;
               int var6;
               for(var5 = 0; var5 < var3; ++var5) {
                  if (this.signers.elementAt(var5) != null) {
                     for(var6 = 0; var6 < var4 && !((SignerInfo)this.signers.elementAt(var5)).equals(var2.signers.elementAt(var6)); ++var6) {
                     }

                     if (var6 == var4) {
                        return false;
                     }
                  }
               }

               var3 = this.digestIDs.size();
               var4 = var2.digestIDs.size();
               if (var3 != var4) {
                  return false;
               } else {
                  for(var5 = 0; var5 < var3; ++var5) {
                     var6 = 0;
                     if (this.digestIDs.elementAt(var5) != null) {
                        for(var6 = 0; var6 < var4 && !CertJUtils.byteArraysEqual((byte[])this.digestIDs.elementAt(var5), (byte[])var2.digestIDs.elementAt(var6)); ++var6) {
                        }

                        if (var6 == var4) {
                           return false;
                        }
                     }

                     String var7 = (String)this.digestNames.elementAt(var5);
                     byte[] var8 = (byte[])this.digests.elementAt(var5);
                     String var9 = (String)var2.digestNames.elementAt(var6);
                     byte[] var10 = (byte[])var2.digests.elementAt(var6);
                     if (var7 == null) {
                        if (var9 != null) {
                           return false;
                        }
                     } else {
                        if (var9 == null) {
                           return false;
                        }

                        if (!var7.equals(var9)) {
                           return false;
                        }
                     }

                     if (var8 == null) {
                        if (var10 != null) {
                           return false;
                        }
                     } else {
                        if (var10 == null) {
                           return false;
                        }

                        if (!CertJUtils.byteArraysEqual(var8, var10)) {
                           return false;
                        }
                     }
                  }

                  var3 = this.failSigners.size();
                  var4 = var2.failSigners.size();
                  if (var3 != var4) {
                     return false;
                  } else {
                     for(var5 = 0; var5 < var3; ++var5) {
                        if (this.failSigners.elementAt(var5) != null) {
                           if (!((SignerInfo)this.failSigners.elementAt(var5)).equals(var2.failSigners.elementAt(var5))) {
                              return false;
                           }
                        } else if (var2.failSigners.elementAt(var5) != null) {
                           return false;
                        }
                     }

                     var3 = this.certs.size();
                     var4 = var2.certs.size();
                     if (var3 != var4) {
                        return false;
                     } else {
                        for(var5 = 0; var5 < var3; ++var5) {
                           if (this.certs.elementAt(var5) != null) {
                              for(var6 = 0; var6 < var4 && !((X509Certificate)this.certs.elementAt(var5)).equals(var2.certs.elementAt(var6)); ++var6) {
                              }

                              if (var6 == var4) {
                                 return false;
                              }
                           }
                        }

                        var3 = this.crls.size();
                        var4 = var2.crls.size();
                        if (var3 != var4) {
                           return false;
                        } else {
                           for(var5 = 0; var5 < var3; ++var5) {
                              if (this.crls.elementAt(var5) != null) {
                                 for(var6 = 0; var6 < var4 && !((X509CRL)this.crls.elementAt(var5)).equals(var2.crls.elementAt(var6)); ++var6) {
                                 }

                                 if (var6 == var4) {
                                    return false;
                                 }
                              }
                           }

                           return true;
                        }
                     }
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public int hashCode() {
      int var2 = 1;
      var2 = 31 * var2 + CertJInternalHelper.hashCodeValue(this.certs);
      var2 = 31 * var2 + CertJInternalHelper.hashCodeValue(this.crls);
      var2 = 31 * var2 + CertJInternalHelper.hashCodeValue(this.digestIDs);
      var2 = 31 * var2 + CertJInternalHelper.hashCodeValue(this.digestNames);
      var2 = 31 * var2 + CertJInternalHelper.hashCodeValue(this.digests);
      var2 = 31 * var2 + CertJInternalHelper.hashCodeValue(this.failSigners);
      var2 = 31 * var2 + CertJInternalHelper.hashCodeValue(this.signers);
      var2 = 31 * var2 + (this.preDigestFlag ? 1231 : 1237);
      var2 = 31 * var2 + this.version;
      return var2;
   }

   /** @deprecated */
   public void clearSensitiveData() {
      super.clearSensitiveData();
      this.version = -1;
      this.digestIDs = new Vector();
      this.digestNames = new Vector();
      this.digests = new Vector();
      this.digestLengths = new Vector();
      this.certs = new Vector();
      this.crls = new Vector();
      this.signers = new Vector();
   }

   private MemoryDB storeIntoTempDB(Vector var1, Vector var2) throws PKCS7Exception {
      if (var1 != null && !var1.isEmpty() || var2 != null && !var2.isEmpty()) {
         if (this.theCertJ == null) {
            throw new PKCS7Exception("SignedData.storeIntoTempDB: theCertJ should not be null.");
         } else {
            DatabaseService var3 = null;

            MemoryDB var12;
            try {
               String var4 = this.findUniqueDBName("Temp Memory DB");
               MemoryDB var5 = new MemoryDB(var4);
               this.theCertJ.registerService(var5);
               var3 = (DatabaseService)this.theCertJ.bindService(1, var4);
               int var6;
               if (var1 != null && !var1.isEmpty()) {
                  for(var6 = 0; var6 < var1.size(); ++var6) {
                     var3.insertCertificate((Certificate)var1.elementAt(var6));
                  }
               }

               if (var2 != null && !var2.isEmpty()) {
                  for(var6 = 0; var6 < var2.size(); ++var6) {
                     var3.insertCRL((CRL)var2.elementAt(var6));
                  }
               }

               var12 = var5;
            } catch (CertJException var10) {
               throw new PKCS7Exception("SignedData.storeIntoTempDB: ", var10);
            } finally {
               if (var3 != null) {
                  this.theCertJ.unbindService(var3);
               }

            }

            return var12;
         }
      } else {
         return null;
      }
   }

   private DatabaseService addTempDB(DatabaseService var1, String var2) throws PKCS7Exception {
      try {
         String[] var3 = var1.listProviderNames();
         String[] var4 = new String[var3.length + 1];
         System.arraycopy(var3, 0, var4, 0, var3.length);
         var4[var3.length] = var2;
         return (DatabaseService)this.theCertJ.bindServices(1, var4);
      } catch (CertJException var5) {
         throw new PKCS7Exception("SignedData.addTempDB: ", var5);
      }
   }

   private String findUniqueDBName(String var1) {
      String[] var2 = this.theCertJ.listProviderNames(1);
      if (!this.nameAlreadyExists(var1, var2)) {
         return var1;
      } else {
         int var3 = 0;

         while(true) {
            String var4 = var1 + var3;
            if (!this.nameAlreadyExists(var4, var2)) {
               return var4;
            }

            ++var3;
         }
      }
   }

   private boolean nameAlreadyExists(String var1, String[] var2) {
      for(int var3 = 0; var3 < var2.length; ++var3) {
         if (var1.equals(var2[var3])) {
            return true;
         }
      }

      return false;
   }

   private boolean noDigestsCalculated() {
      Enumeration var1 = this.digests.elements();

      do {
         if (!var1.hasMoreElements()) {
            return true;
         }
      } while(var1.nextElement() == null);

      return false;
   }
}
