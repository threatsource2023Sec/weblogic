package com.rsa.certj.provider.revocation.ocsp;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.NotSupportedException;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.cert.extensions.AuthorityInfoAccess;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.cert.extensions.OCSPAcceptableResponses;
import com.rsa.certj.cert.extensions.OCSPNonce;
import com.rsa.certj.cert.extensions.OCSPServiceLocator;
import com.rsa.certj.cert.extensions.X509V3Extension;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.random.RandomException;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_Parameters;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_Signature;
import java.util.Vector;

/** @deprecated */
public final class OCSPRequest {
   private CertJ certJ;
   private byte[] nonce;
   private OCSPCertID[] certIDs;
   private X509V3Extension[] serviceLocatorExtension;
   private X509V3Extensions[] singleRequestExtensions;
   private X509V3Extensions requestExtensions;
   private OCSPResponderInternal responder;

   OCSPRequest(CertJ var1, OCSPResponderInternal var2, Certificate[] var3) throws CertificateException, NotSupportedException {
      int var5 = var3.length;

      try {
         this.certJ = var1;
         this.setRequestExtensions(var2);
         int var6 = var2.getFlags();
         if ((var6 & 1) == 0) {
            JSAFE_SecureRandom var7 = var1.getRandomObject();
            byte[] var8 = new byte[16];
            var7.generateRandomBytes(var8, 0, 16);
            this.setNonce(var8, 0, 16);
         }

         this.singleRequestExtensions = new X509V3Extensions[var5];
         this.certIDs = new OCSPCertID[var5];
         this.serviceLocatorExtension = new X509V3Extension[var5];

         for(int var4 = 0; var4 < var5; ++var4) {
            X509Certificate var16 = (X509Certificate)var3[var4];
            X509Certificate var17 = var2.getResponderCACert(var16);
            this.certIDs[var4] = new OCSPCertID(var1, var17, var16, var2.getRequestControl().getDigestAlgorithm());
            X500Name var9 = var16.getIssuerName();
            X509V3Extensions var10 = var16.getExtensions();
            if (var10 != null) {
               AuthorityInfoAccess var11 = (AuthorityInfoAccess)var10.getExtensionByType(100);
               if (var11 != null) {
                  this.serviceLocatorExtension[var4] = new OCSPServiceLocator(var9, var11);
               }
            }
         }

         this.responder = var2;
      } catch (CertificateException var12) {
         throw new NotSupportedException(var12);
      } catch (NoServiceException var13) {
         throw new NotSupportedException(var13);
      } catch (RandomException var14) {
         throw new NotSupportedException(var14);
      } catch (NameException var15) {
         throw new NotSupportedException(var15);
      }
   }

   private void setRequestExtensions(OCSPResponderInternal var1) throws NotSupportedException {
      X509V3Extensions var2 = var1.getRequestControl().getRequestExtensions();

      try {
         if (var2 != null) {
            this.requestExtensions = (X509V3Extensions)var2.clone();
         } else {
            this.requestExtensions = new X509V3Extensions(5);
         }

         OCSPAcceptableResponses var3 = new OCSPAcceptableResponses();
         byte[] var4 = OCSPAcceptableResponses.ID_PKIX_OCSP_BASIC;
         int var5 = OCSPAcceptableResponses.ID_PKIX_OCSP_BASIC.length;
         ((OCSPAcceptableResponses)var3).addAcceptableResponse(var4, 0, var5);
         this.requestExtensions.addV3Extension(var3);
      } catch (CloneNotSupportedException var6) {
         throw new NotSupportedException(var6);
      } catch (CertificateException var7) {
         throw new NotSupportedException(var7);
      }
   }

   void setSingleRequestExtensions(int var1, X509V3Extensions var2) throws NotSupportedException {
      try {
         this.singleRequestExtensions[var1] = (X509V3Extensions)var2.clone();
      } catch (CloneNotSupportedException var4) {
         throw new NotSupportedException(var4);
      }
   }

   byte[] getNonce() {
      return this.nonce;
   }

   int setNonce(byte[] var1, int var2, int var3) throws CertificateException {
      if (var1 != null && var3 != 0) {
         this.nonce = new byte[var3];
         System.arraycopy(var1, var2, this.nonce, 0, var3);
         OCSPNonce var4 = new OCSPNonce(this.nonce, 0, var3);
         if (this.requestExtensions == null) {
            this.requestExtensions = new X509V3Extensions(5);
         }

         this.requestExtensions.addV3Extension(var4);
         return var3;
      } else {
         return 0;
      }
   }

   OCSPCertID getCertID(int var1) {
      return this.certIDs[var1];
   }

   byte[] encode(CertPathCtx var1) throws NotSupportedException {
      byte[] var2 = null;
      X500Name var5 = null;

      try {
         X509Certificate var4 = this.responder.getRequestControl().getSignerCert();
         if (var4 != null) {
            var5 = var4.getSubjectName();
         }

         byte[] var6 = this.encodeTBSRequestDER(0, var5, this.requestExtensions);
         if (var4 != null) {
            var2 = this.encodeRequestSignatureDER(this.certJ, var1, this.responder, var4, var6);
         }

         byte[] var3 = this.encodeOCSPRequestDER(var6, var2);
         return var3;
      } catch (Exception var7) {
         throw new NotSupportedException(var7);
      }
   }

   private byte[] encodeRequestSignatureDER(CertJ var1, CertPathCtx var2, OCSPResponderInternal var3, Certificate var4, byte[] var5) throws NotSupportedException, ASN_Exception {
      EndContainer var9 = new EndContainer();

      try {
         JSAFE_PrivateKey var10 = var2.getDatabase().selectPrivateKeyByCertificate(var4);
         String var11 = var3.getSignatureAlgorithm();
         JSAFE_Signature var12 = h.b(var11, var1.getDevice(), var1);
         var12.signInit(var10, (JSAFE_Parameters)null, var1.getRandomObject(), var1.getPKCS11Sessions());
         var12.signUpdate(var5, 0, var5.length);
         byte[] var7 = var12.signFinal();
         JSAFE_Signature var13 = h.b(var11, this.certJ.getDevice(), var1);
         byte[] var6 = var13.getDERAlgorithmID();
         SequenceContainer var14 = new SequenceContainer(10551296, true, 0);
         EncodedContainer var15 = new EncodedContainer(0, true, 0, var6, 0, var6.length);
         BitStringContainer var16 = new BitStringContainer(0, true, 0, var7, 0, var7.length, var7.length * 8, false);
         ASN1Container var17 = this.createCertsContainer(var3.getFlags(), var3.getRequestControl().getExtraCerts(), var2, var4);
         ASN1Container[] var18 = new ASN1Container[]{var14, var15, var16, var17, var9};
         ASN1Template var19 = new ASN1Template(var18);
         byte[] var8 = this.asnEncode(var19);
         return var8;
      } catch (JSAFE_Exception var20) {
         throw new NotSupportedException(var20);
      } catch (CertJException var21) {
         throw new NotSupportedException(var21);
      }
   }

   private byte[] encodeRequestListDER() throws NotSupportedException {
      try {
         OfContainer var1 = null;

         for(int var2 = 0; var2 < this.certIDs.length; ++var2) {
            X509V3Extensions var4;
            if (this.singleRequestExtensions[var2] == null) {
               if (this.serviceLocatorExtension[var2] == null) {
                  var4 = null;
               } else {
                  var4 = new X509V3Extensions(4);
                  var4.addV3Extension(this.serviceLocatorExtension[var2]);
               }
            } else if (this.serviceLocatorExtension[var2] == null) {
               var4 = this.singleRequestExtensions[var2];
            } else {
               var4 = (X509V3Extensions)this.singleRequestExtensions[var2].clone();
               var4.addV3Extension(this.serviceLocatorExtension[var2]);
            }

            byte[] var3 = this.encodeRequestDER(this.certIDs[var2].encode(), var4);
            var1 = new OfContainer(0, true, 0, 12288, new EncodedContainer(12288));
            var1.addContainer(new EncodedContainer(0, true, 0, var3, 0, var3.length));
         }

         ASN1Container[] var8 = new ASN1Container[]{var1};
         ASN1Template var9 = new ASN1Template(var8);
         return this.asnEncode(var9);
      } catch (ASN_Exception var5) {
         throw new NotSupportedException(var5);
      } catch (CertificateException var6) {
         throw new NotSupportedException(var6);
      } catch (CloneNotSupportedException var7) {
         throw new NotSupportedException(var7);
      }
   }

   private byte[] encodeTBSRequestDER(int var1, X500Name var2, X509V3Extensions var3) throws NotSupportedException, ASN_Exception {
      SequenceContainer var4 = new SequenceContainer(0, true, 0);
      EndContainer var9 = new EndContainer();

      try {
         IntegerContainer var5;
         if (var1 != 0) {
            var5 = new IntegerContainer(10682368, true, 0, var1);
         } else {
            var5 = new IntegerContainer(10682368, false, 0, 0);
         }

         EncodedContainer var6;
         if (var2 != null) {
            GeneralName var12 = new GeneralName();
            var12.setGeneralName(var2, 5);
            int var10 = var12.getDERLen(10551297);
            byte[] var11 = new byte[var10];
            var12.getDEREncoding(var11, 0, 10551297);
            var6 = new EncodedContainer(12288, true, 12288, var11, 0, var10);
         } else {
            var6 = new EncodedContainer(10551297, false, 0, (byte[])null, 0, 0);
         }

         byte[] var14 = this.encodeRequestListDER();
         EncodedContainer var8 = new EncodedContainer(0, true, 0, var14, 0, var14.length);
         EncodedContainer var7;
         if (var3 != null) {
            int var15 = var3.getDERLen(10551298);
            byte[] var17 = new byte[var15];
            var3.getDEREncoding(var17, 0, 10551298);
            var7 = new EncodedContainer(10551298, true, 0, var17, 0, var15);
         } else {
            var7 = new EncodedContainer(10551298, false, 0, (byte[])null, 0, 0);
         }

         ASN1Container[] var16 = new ASN1Container[]{var4, var5, var6, var8, var7, var9};
         ASN1Template var18 = new ASN1Template(var16);
         return this.asnEncode(var18);
      } catch (NameException var13) {
         throw new NotSupportedException(var13);
      }
   }

   private ASN1Container createCertsContainer(int var1, X509Certificate[] var2, CertPathCtx var3, Certificate var4) throws NotSupportedException, ASN_Exception {
      Vector var5 = new Vector();
      if ((var1 & 2) == 0) {
         var5.addElement(var4);
      }

      int var7;
      if ((var1 & 4) != 0) {
         Vector var6 = new Vector();

         try {
            this.certJ.buildCertPath(var3, var4, var6, (Vector)null, (Vector)null, (Vector)null);

            for(var7 = 0; var7 < var6.size(); ++var7) {
               var5.addElement((Certificate)var6.elementAt(var7));
            }
         } catch (CertJException var13) {
            throw new ASN_Exception(var13.getMessage());
         }
      }

      if (var2 != null) {
         for(int var14 = 0; var14 < var2.length; ++var14) {
            var5.addElement(var2[var14]);
         }
      }

      if (var5.isEmpty()) {
         return new EncodedContainer(10551296, false, 0, (byte[])null, 0, 0);
      } else {
         OfContainer var15 = new OfContainer(10551296, true, 0, 12288, new EncodedContainer(12288));

         for(var7 = 0; var7 < var5.size(); ++var7) {
            Certificate var8 = (Certificate)var5.elementAt(var7);
            byte[] var9 = new byte[((X509Certificate)var8).getDERLen(0)];

            try {
               ((X509Certificate)var8).getDEREncoding(var9, 0, 0);
               var15.addContainer(new EncodedContainer(0, true, 0, var9, 0, var9.length));
            } catch (CertificateException var11) {
               throw new NotSupportedException(var11);
            } catch (ASN_Exception var12) {
               throw new NotSupportedException(var12);
            }
         }

         return var15;
      }
   }

   private byte[] encodeRequestDER(byte[] var1, X509V3Extensions var2) throws NotSupportedException, ASN_Exception {
      byte[] var3 = null;
      int var4 = 0;
      if (var2 != null) {
         var4 = var2.getDERLen(10551296);
         var3 = new byte[var4];
         var2.getDEREncoding(var3, 0, 10551296);
      }

      SequenceContainer var5 = new SequenceContainer(0, true, 0);
      EncodedContainer var6 = new EncodedContainer(0, true, 0, var1, 0, var1.length);
      EncodedContainer var7 = new EncodedContainer(0, true, 0, var3, 0, var4);
      EndContainer var8 = new EndContainer();
      ASN1Container[] var9 = new ASN1Container[]{var5, var6, var7, var8};
      ASN1Template var10 = new ASN1Template(var9);
      return this.asnEncode(var10);
   }

   private byte[] encodeOCSPRequestDER(byte[] var1, byte[] var2) throws NotSupportedException, ASN_Exception {
      SequenceContainer var3 = new SequenceContainer(0, true, 0);
      EncodedContainer var4 = new EncodedContainer(0, true, 0, var1, 0, var1.length);
      EncodedContainer var5;
      if (var2 == null) {
         var5 = new EncodedContainer(10551296, false, 0, (byte[])null, 0, 0);
      } else {
         var5 = new EncodedContainer(10551296, true, 0, var2, 0, var2.length);
      }

      EndContainer var6 = new EndContainer();
      ASN1Container[] var7 = new ASN1Container[]{var3, var4, var5, var6};
      ASN1Template var8 = new ASN1Template(var7);
      return this.asnEncode(var8);
   }

   private byte[] asnEncode(ASN1Template var1) throws NotSupportedException {
      try {
         int var3 = var1.derEncodeInit();
         byte[] var2 = new byte[var3];
         int var4 = var1.derEncode(var2, 0);
         return var3 == var4 ? var2 : null;
      } catch (ASN_Exception var5) {
         throw new NotSupportedException(var5);
      }
   }
}
