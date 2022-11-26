package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Template;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.BitStringContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OIDContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.spi.pki.PKIRequestMessage;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MAC;
import com.rsa.jsafe.JSAFE_MessageDigest;
import com.rsa.jsafe.JSAFE_Parameters;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_Signature;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.StringTokenizer;

abstract class CMPRequestCommon extends PKIRequestMessage {
   private static final int SALT_LEN = 20;
   private static final int PBHMAC_ITERATIONS = 1024;
   private static final byte[] HMAC_SHA1_OID = new byte[]{43, 6, 1, 5, 5, 8, 1, 2};
   private byte[] recipNonce;
   private TypeAndValue[] generalInfo;
   private int messageType;

   /** @deprecated */
   protected CMPRequestCommon(int var1, byte[] var2) {
      this(var1, var2, (String[])null, (TypeAndValue[])null);
   }

   /** @deprecated */
   protected CMPRequestCommon(int var1, byte[] var2, String[] var3, TypeAndValue[] var4) {
      super((Certificate)null, (Properties)null, false);
      this.messageType = var1;
      this.recipNonce = var2;
      this.generalInfo = var4;
      this.setFreeText(var3);
   }

   /** @deprecated */
   protected int getMessageType() {
      return this.messageType;
   }

   /** @deprecated */
   protected byte[] getRecipNonce() {
      return this.recipNonce;
   }

   /** @deprecated */
   protected TypeAndValue[] getGeneralInfo() {
      return this.generalInfo;
   }

   /** @deprecated */
   protected void setRecipNonce(byte[] var1) {
      this.recipNonce = var1;
   }

   /** @deprecated */
   protected byte[] derEncodeBody(CertJ var1) throws CMPException {
      throw new CMPException("CMPRequestCommon.derEncode: this method should be overwritten by sublclasses.");
   }

   /** @deprecated */
   protected byte[] derEncode(CMPProtectInfo var1, CertJ var2) throws CMPException {
      JSAFE_SecureRandom var3;
      try {
         var3 = var2.getRandomObject();
      } catch (CertJException var11) {
         throw new CMPException("CMPRequestCommon.writeMessage: unable to get a registered random service.", var11);
      }

      byte[] var4 = null;
      byte[] var5 = null;
      int var6 = -1;
      byte[] var7 = this.derEncodeBody(var2);
      if (var1 != null) {
         if (var1.pbmProtected()) {
            String var8 = var1.getAlgorithm();
            if (!var8.startsWith("PBE/HMAC/SHA1")) {
               throw new CMPException("CMPRequestCommon.writeMessage: PBM MAC algorithm specified by " + var8 + " is not supported.");
            }

            var5 = var3.generateRandomBytes(20);
            StringTokenizer var9 = new StringTokenizer(var8, "-");
            if (var9.hasMoreTokens()) {
               var9.nextToken();
               if (var9.hasMoreTokens()) {
                  var6 = Integer.parseInt(var9.nextToken());
               }
            }

            if (var6 <= 0) {
               var6 = 1024;
            }

            var4 = this.derEncodePBMAlg(var5, var6, var2);
         } else {
            var4 = this.derEncodeSignatureAlg(var1.getAlgorithm(), var2);
         }
      }

      byte[] var12 = (new PKIHeader(this, var1, var4, var3)).a();
      byte[] var13 = CMP.derEncodeProtectedPart(var12, 0, var12.length, var7, 0, var7.length);
      byte[] var10 = null;
      if (var1 != null) {
         if (var1.pbmProtected()) {
            var10 = this.computeProtection(var13, var1, var5, var2);
         } else {
            var10 = this.computeProtection(var13, var1, var3, var2);
         }
      }

      return this.derEncodePKIMessage(var12, var7, var10, this.getExtraCerts());
   }

   private byte[] computeProtection(byte[] var1, CMPProtectInfo var2, JSAFE_SecureRandom var3, CertJ var4) throws CMPException {
      DatabaseService var5 = var2.getDatabase();
      X509Certificate var6 = var2.getSenderCert();

      JSAFE_PrivateKey var7;
      try {
         var7 = var5.selectPrivateKeyByCertificate(var6);
      } catch (CertJException var17) {
         throw new CMPException("CMPRequestCommon.computeProtection: unable to find a signer private key in the database.", var17);
      }

      if (var7 == null) {
         throw new CMPException("CMPRequestCommon.computeProtection: unable to find a signer private key in the database.");
      } else {
         String var8 = var2.getAlgorithm();

         byte[] var10;
         try {
            JSAFE_Signature var9 = h.b(var8, var4.getDevice(), var4);
            var9.signInit(var7, (JSAFE_Parameters)null, var3, var4.getPKCS11Sessions());
            var9.signUpdate(var1, 0, var1.length);
            var10 = var9.signFinal();
         } catch (JSAFE_Exception var15) {
            throw new CMPException("CMPRequestCommon.createSignatureProtectionDER: unable to generate a signature for " + var8 + ".", var15);
         } finally {
            var7.clearSensitiveData();
         }

         return var10;
      }
   }

   private byte[] computeProtection(byte[] var1, CMPProtectInfo var2, byte[] var3, CertJ var4) throws CMPException {
      JSAFE_MAC var5 = null;

      byte[] var8;
      try {
         var5 = h.d(var2.getAlgorithm(), var4.getDevice(), var4);
         var5.setSalt(var3, 0, var3.length);
         JSAFE_SecretKey var6 = var5.getBlankKey();
         char[] var7 = var2.getSharedSecret();
         var6.setPassword(var7, 0, var7.length);
         var5.macInit(var6, (SecureRandom)null);
         var5.macUpdate(var1, 0, var1.length);
         var8 = var5.macFinal();
      } catch (JSAFE_Exception var12) {
         throw new CMPException("CMPRequestCommon.createPBMProtection: unable to compute PBM.", var12);
      } finally {
         if (var5 != null) {
            var5.clearSensitiveData();
         }

      }

      return var8;
   }

   private byte[] derEncodePKIMessage(byte[] var1, byte[] var2, byte[] var3, Certificate[] var4) throws CMPException {
      try {
         ASN1Container var5 = this.createExtraCertsContainer(var4);
         SequenceContainer var6 = new SequenceContainer(0, true, 0);
         EncodedContainer var7 = new EncodedContainer(0, true, 0, var1, 0, var1.length);
         EncodedContainer var8 = new EncodedContainer(0, true, 0, var2, 0, var2.length);
         BitStringContainer var9;
         if (var3 == null) {
            var9 = new BitStringContainer(10551296, false, 0, 0, 0, false);
         } else {
            var9 = new BitStringContainer(10551296, true, 0, var3, 0, var3.length, var3.length * 8, false);
         }

         EndContainer var10 = new EndContainer();
         ASN1Container[] var11 = new ASN1Container[]{var6, var7, var8, var9, var5, var10};
         ASN1Template var12 = new ASN1Template(var11);
         byte[] var13 = new byte[var12.derEncodeInit()];
         var12.derEncode(var13, 0);
         return var13;
      } catch (ASN_Exception var14) {
         throw new CMPException("CMPRequestCommon.derEncodePKIMessage: Encoding CMP message failed.", var14);
      }
   }

   private ASN1Container createExtraCertsContainer(Certificate[] var1) throws CMPException, ASN_Exception {
      if (var1 == null) {
         return new EncodedContainer(10551297, false, 0, (byte[])null, 0, 0);
      } else {
         OfContainer var2 = new OfContainer(10551297, true, 0, 12288, new EncodedContainer(12288));

         for(int var3 = 0; var3 < var1.length; ++var3) {
            Certificate var4 = var1[var3];
            if (!(var4 instanceof X509Certificate)) {
               throw new CMPException("CMPRequestCommon.createExtraCertsContainer:certificate in extraCerts should be an instance of X509Certificate.");
            }

            byte[] var5;
            try {
               var5 = new byte[((X509Certificate)var4).getDERLen(0)];
               ((X509Certificate)var4).getDEREncoding(var5, 0, 0);
            } catch (CertificateException var8) {
               throw new CMPException("CMPRequestCommon.createExtraCertsContainer: Encoding a certificate failed.", var8);
            }

            try {
               var2.addContainer(new EncodedContainer(0, true, 0, var5, 0, var5.length));
            } catch (ASN_Exception var7) {
               throw new CMPException("CMPRequestCommon.createExtraCertsContainer: unable to add an element of extraCerts.", var7);
            }
         }

         return var2;
      }
   }

   private byte[] derEncodePBMAlg(byte[] var1, int var2, CertJ var3) throws CMPException {
      byte[] var4 = this.derEncodePBMParameter(var1, var2, var3);

      try {
         SequenceContainer var5 = new SequenceContainer(10551297, true, 0);
         OIDContainer var6 = new OIDContainer(16777216, true, 0, CMP.PASSWORD_BASED_MAC_OID, 0, CMP.PASSWORD_BASED_MAC_OID.length);
         EncodedContainer var7 = new EncodedContainer(77824, true, 0, var4, 0, var4.length);
         EndContainer var8 = new EndContainer();
         ASN1Container[] var9 = new ASN1Container[]{var5, var6, var7, var8};
         ASN1Template var10 = new ASN1Template(var9);
         byte[] var11 = new byte[var10.derEncodeInit()];
         var10.derEncode(var11, 0);
         return var11;
      } catch (ASN_Exception var12) {
         throw new CMPException("CMPRequestCommon.encodePBMAlgorithmIdentifier: unable to encodeEncoding PBMAlgorithmIdentifier.", var12);
      }
   }

   private byte[] derEncodeSignatureAlg(String var1, CertJ var2) throws CMPException {
      byte[] var3;
      try {
         var3 = h.b(var1, var2.getDevice(), var2).getDERAlgorithmID();
      } catch (JSAFE_Exception var11) {
         throw new CMPException("CMPRequestCommon.derEncodeSignatureAlgorithm: unable to get algorithm identifier for " + var1 + ".", var11);
      }

      try {
         SequenceContainer var4 = new SequenceContainer(8388609, true, 0);
         EncodedContainer var5 = new EncodedContainer(0, true, 0, var3, 0, var3.length);
         EndContainer var6 = new EndContainer();
         ASN1Container[] var7 = new ASN1Container[]{var4, var5, var6};
         ASN1Template var8 = new ASN1Template(var7);
         byte[] var9 = new byte[var8.derEncodeInit()];
         var8.derEncode(var9, 0);
         return var9;
      } catch (ASN_Exception var10) {
         throw new CMPException("CMPRequestCommon.derEncodeSignatureAlgorithm: unable to encode signature algorithm.", var10);
      }
   }

   private byte[] derEncodePBMParameter(byte[] var1, int var2, CertJ var3) throws CMPException {
      String var4 = var3.getDevice();

      JSAFE_MessageDigest var5;
      try {
         var5 = h.a("SHA1", var4, var3);
      } catch (JSAFE_Exception var20) {
         throw new CMPException("CMPRequestCommon.derEncodePBMParameter.", var20);
      }

      byte[] var6 = var5.getDERAlgorithmID();

      JSAFE_MAC var7;
      try {
         var7 = h.d("HMAC/SHA1", var4, var3);
      } catch (JSAFE_Exception var19) {
         throw new CMPException("CMPRequestCommon.derEncodePBMParameter.", var19);
      }

      byte[] var8 = this.getMACAlgorithmID(var7);

      try {
         SequenceContainer var9 = new SequenceContainer(0, true, 0);
         OctetStringContainer var10 = new OctetStringContainer(0, true, 0, var1, 0, var1.length);
         EncodedContainer var11 = new EncodedContainer(12288, true, 0, var6, 0, var6.length);
         IntegerContainer var12 = new IntegerContainer(0, true, 0, var2);
         EncodedContainer var13 = new EncodedContainer(12288, true, 0, var8, 0, var8.length);
         EndContainer var14 = new EndContainer();
         ASN1Container[] var15 = new ASN1Container[]{var9, var10, var11, var12, var13, var14};
         ASN1Template var16 = new ASN1Template(var15);
         byte[] var17 = new byte[var16.derEncodeInit()];
         var16.derEncode(var17, 0);
         return var17;
      } catch (ASN_Exception var18) {
         throw new CMPException("CMPRequestCommon.derEncodePBMParameter: Encoding PBMParameter failed.", var18);
      }
   }

   private byte[] getMACAlgorithmID(JSAFE_MAC var1) throws CMPException {
      String var2 = var1.getMACAlgorithm();
      String var3 = var1.getDigestAlgorithm();
      if (var1.getMACAlgorithm().equals("HMAC") && var1.getDigestAlgorithm().equals("SHA1")) {
         try {
            SequenceContainer var4 = new SequenceContainer(0, true, 0);
            OIDContainer var5 = new OIDContainer(16777216, true, 0, HMAC_SHA1_OID, 0, HMAC_SHA1_OID.length);
            EncodedContainer var6 = new EncodedContainer(77824, false, 5, (byte[])null, 0, 0);
            EndContainer var7 = new EndContainer();
            ASN1Container[] var8 = new ASN1Container[]{var4, var5, var6, var7};
            ASN1Template var9 = new ASN1Template(var8);
            byte[] var10 = new byte[var9.derEncodeInit()];
            var9.derEncode(var10, 0);
            return var10;
         } catch (ASN_Exception var11) {
            throw new CMPException("CMPRequestCommon.getMACAlgorithmID: Encoding MAC Algorithm Identifier failed.", var11);
         }
      } else {
         throw new CMPException("CMPRequestCommon.getMACAlgorithmID: algorithm(" + var2 + "/" + var3 + ") not supported.  Use HMAC/SHA1.");
      }
   }
}
