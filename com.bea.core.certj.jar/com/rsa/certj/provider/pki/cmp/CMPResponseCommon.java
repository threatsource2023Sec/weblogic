package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN1Lengths;
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
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.pki.PKIResponseMessage;
import com.rsa.certj.spi.pki.PKIStatusInfo;
import com.rsa.certj.x.h;
import com.rsa.jsafe.JSAFE_Exception;
import com.rsa.jsafe.JSAFE_MAC;
import com.rsa.jsafe.JSAFE_Parameters;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SecureRandom;
import com.rsa.jsafe.JSAFE_Signature;
import java.security.SecureRandom;
import java.util.Date;

/** @deprecated */
public abstract class CMPResponseCommon extends PKIResponseMessage {
   private GeneralName senderName;
   private GeneralName recipientName;
   private byte[] senderKID;
   private byte[] recipKID;
   private byte[] senderNonce;
   private byte[] recipNonce;
   private TypeAndValue[] generalInfo;
   private int messageType = -1;

   /** @deprecated */
   protected CMPResponseCommon(int var1, PKIHeader var2, PKIStatusInfo var3) {
      super(var3);
      this.messageType = var1;
      this.senderName = var2.b;
      this.recipientName = var2.c;
      this.senderKID = var2.b();
      this.recipKID = var2.c();
      this.senderNonce = var2.d();
      this.recipNonce = var2.e();
      this.generalInfo = var2.l;
   }

   /** @deprecated */
   protected int getMessageType() {
      return this.messageType;
   }

   /** @deprecated */
   public GeneralName getSenderName() {
      return this.senderName;
   }

   /** @deprecated */
   public GeneralName getRecipientName() {
      return this.recipientName;
   }

   /** @deprecated */
   public byte[] getSenderKID() {
      return this.senderKID;
   }

   /** @deprecated */
   public byte[] getRecipKID() {
      return this.recipKID;
   }

   /** @deprecated */
   public byte[] getSenderNonce() {
      return this.senderNonce;
   }

   /** @deprecated */
   public byte[] getRecipNonce() {
      return this.recipNonce;
   }

   /** @deprecated */
   public TypeAndValue[] getGeneralInfo() {
      return this.generalInfo;
   }

   /** @deprecated */
   protected static CMPResponseCommon berDecode(byte[] var0, CMPProtectInfo var1, CMPRequestCommon var2, CertJ var3) throws CMPException {
      EncodedContainer var4;
      EncodedContainer var5;
      BitStringContainer var6;
      OfContainer var7;
      try {
         SequenceContainer var8 = new SequenceContainer(0);
         var4 = new EncodedContainer(12288);
         var5 = new EncodedContainer(65280);
         var6 = new BitStringContainer(10551296);
         var7 = new OfContainer(10551297, 12288, new EncodedContainer(12288));
         EndContainer var9 = new EndContainer();
         ASN1Container[] var10 = new ASN1Container[]{var8, var4, var5, var6, var7, var9};
         ASN1.berDecode(var0, 0, var10);
      } catch (ASN_Exception var12) {
         throw new CMPException("CMPResponseCommon.berDecode: unable to decode response message.", var12);
      }

      PKIHeader var13 = new PKIHeader(var4.data, var4.dataOffset);
      CMPResponseCommon var14 = berDecodeBody(var13, var5.data, var5.dataOffset, var1, var2, var3);
      Certificate[] var15 = decodeExtraCerts(var7);
      var14.setExtraCerts(var15);
      var14.setFreeText(var13.k);
      var14.setMessageTime(var13.d);
      var14.setTransactionID(var13.f());
      Certificate[] var11 = var14.getCACerts();
      if (!verifyProtection(var1, var6, var4, var5, var13, var15, var11, var3)) {
         throw new CMPException("CMPResponseCommon.berDecode: unable to verify protection.");
      } else {
         return var14;
      }
   }

   /** @deprecated */
   protected static CMPResponseCommon berDecodeBody(PKIHeader var0, byte[] var1, int var2, CMPProtectInfo var3, CMPRequestCommon var4, CertJ var5) throws CMPException {
      int var7 = 255 & var1[var2] - 160;
      Object var6;
      switch (var7) {
         case 0:
         case 2:
         case 4:
         case 5:
         case 7:
         case 9:
         case 11:
         case 13:
         case 24:
         default:
            throw new CMPException("CMPResponseCommon.berDecodeBody: unexpected response message type(" + var7 + ").");
         case 1:
         case 3:
            var6 = CMPCertResponseCommon.berDecodeBody(var7, var0, var1, var2, var3, var4, var5);
            break;
         case 6:
         case 8:
         case 10:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 20:
         case 21:
         case 22:
            throw new CMPException("CMPResponseCommon.berDecodeBody: unsupported response message type(" + var7 + ").");
         case 12:
            var6 = CMPRevokeResponseMessage.berDecodeBody(var0, var1, var2);
            break;
         case 19:
            var6 = CMPConfirmMessage.berDecodeBody(var0, var1, var2);
            break;
         case 23:
            var6 = CMPErrorMessage.berDecodeBody(var0, var1, var2);
      }

      return (CMPResponseCommon)var6;
   }

   private static boolean verifyProtection(CMPProtectInfo var0, ASN1Container var1, ASN1Container var2, ASN1Container var3, PKIHeader var4, Certificate[] var5, Certificate[] var6, CertJ var7) throws CMPException {
      if (!var1.dataPresent) {
         return true;
      } else if (var0 == null) {
         throw new CMPException("CMPResponseCommon.verifyProtection: protectInfo should be null.");
      } else {
         byte[] var8 = CMP.derEncodeProtectedPart(var2.data, var2.dataOffset, var2.dataLen, var3.data, var3.dataOffset, var3.dataLen);
         byte[] var9 = var4.g();
         JSAFE_Signature var10 = null;

         try {
            int var11 = 1 + ASN1Lengths.determineLengthLen(var9, 1);
            var10 = h.b(var9, var11, var7.getDevice(), var7);
         } catch (JSAFE_Exception var12) {
         } catch (ASN_Exception var13) {
            throw new CMPException("CMPResponseCommon.verifyProtection.", var13);
         }

         if (var10 != null) {
            return verifyProtection(var10, var0, var1, var8, var5, var6, var7);
         } else if (!var0.pbmProtected()) {
            throw new CMPException("CMPResponseCommon.verifyProtection: protectInfo should contain PBM protection info.");
         } else {
            return verifyProtection(var9, var0, var1, var8, var7);
         }
      }
   }

   private static boolean verifyProtection(JSAFE_Signature var0, CMPProtectInfo var1, ASN1Container var2, byte[] var3, Certificate[] var4, Certificate[] var5, CertJ var6) throws CMPException {
      X509Certificate[] var7 = var1.getCACerts();
      CertPathCtx var8 = new CertPathCtx(0, var7, (byte[][])null, new Date(), var1.getDatabase());

      JSAFE_SecureRandom var9;
      try {
         var9 = var6.getRandomObject();
      } catch (CertJException var12) {
         throw new CMPException("CMPResponseCommon.verifyProtection: unable to get a registered random service.", var12);
      }

      X509Certificate var10 = var1.getRecipCert();
      if (var10 != null && verifySignature(var0, var8, var10, var3, var2, var9, var6)) {
         return true;
      } else {
         int var11;
         if (var7 != null) {
            for(var11 = 0; var11 < var7.length; ++var11) {
               if (var7[var11] != null && verifySignature(var0, var8, var7[var11], var3, var2, var9, var6)) {
                  return true;
               }
            }
         }

         if (var5 != null) {
            for(var11 = 0; var11 < var5.length; ++var11) {
               if (var5[var11] != null && verifySignature(var0, var8, var5[var11], var3, var2, var9, var6)) {
                  return true;
               }
            }
         }

         if (var4 != null) {
            for(var11 = 0; var11 < var4.length; ++var11) {
               if (var4[var11] != null && verifySignature(var0, var8, var4[var11], var3, var2, var9, var6)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private static boolean verifySignature(JSAFE_Signature var0, CertPathCtx var1, Certificate var2, byte[] var3, ASN1Container var4, JSAFE_SecureRandom var5, CertJ var6) {
      try {
         if (var6.verifyCertPath(var1, var2)) {
            var0.verifyInit(var2.getSubjectPublicKey(var6.getDevice()), (JSAFE_Parameters)null, var5, var6.getPKCS11Sessions());
            var0.verifyUpdate(var3, 0, var3.length);
            byte[] var7 = new byte[var4.dataLen];
            System.arraycopy(var4.data, var4.dataOffset, var7, 0, var7.length);
            if (var0.verifyFinal(var7, 0, var7.length)) {
               return true;
            }
         }
      } catch (CertificateException var8) {
      } catch (CertJException var9) {
      } catch (JSAFE_Exception var10) {
      }

      return false;
   }

   private static boolean verifyProtection(byte[] var0, CMPProtectInfo var1, ASN1Container var2, byte[] var3, CertJ var4) throws CMPException {
      char[] var5 = var1.getSharedSecret();
      SequenceContainer var6 = new SequenceContainer(10551297);
      OIDContainer var7 = new OIDContainer(16777216);
      EncodedContainer var8 = new EncodedContainer(77824);
      EndContainer var9 = new EndContainer();
      ASN1Container[] var10 = new ASN1Container[]{var6, var7, var8, var9};

      try {
         ASN1.berDecode(var0, 0, var10);
      } catch (ASN_Exception var30) {
         throw new CMPException("CMPResponseCommon.verifyProtection: Decoding PKIHeader.protectionAlg failed.", var30);
      }

      if (!oidsEqual(CMP.PASSWORD_BASED_MAC_OID, 0, CMP.PASSWORD_BASED_MAC_OID.length, var7.data, var7.dataOffset, var7.dataLen)) {
         throw new CMPException("CMPResponseCommon.verifyProtection: unsupported PBM algorithm.");
      } else {
         SequenceContainer var11 = new SequenceContainer(0);
         OctetStringContainer var12 = new OctetStringContainer(0);
         EncodedContainer var13 = new EncodedContainer(12288);
         IntegerContainer var14 = new IntegerContainer(0);
         EncodedContainer var15 = new EncodedContainer(12288);
         var9 = new EndContainer();
         ASN1Container[] var16 = new ASN1Container[]{var11, var12, var13, var14, var15, var9};

         try {
            ASN1.berDecode(var8.data, var8.dataOffset, var16);
         } catch (ASN_Exception var29) {
            throw new CMPException("CMPResponseCommon.verifyProtection: decoding PKIHeader.protectionAlg failed.", var29);
         }

         JSAFE_MAC var17 = null;

         int var18;
         try {
            var18 = var14.getValueAsInt();
         } catch (ASN_Exception var28) {
            throw new CMPException("CMPResponseCommon.verifyProtection: unable to get iterationCount as integer.", var28);
         }

         boolean var20;
         try {
            var17 = h.d("PBE/HMAC/SHA1/PKIXPBE-" + var18, var4.getDevice(), var4);
            var17.setSalt(var12.data, var12.dataOffset, var12.dataLen);
            JSAFE_SecretKey var19 = var17.getBlankKey();
            var19.setPassword(var5, 0, var5.length);
            var17.verifyInit(var19, (SecureRandom)null);
            var17.verifyUpdate(var3, 0, var3.length);
            var20 = var17.verifyFinal(var2.data, var2.dataOffset, var2.dataLen);
         } catch (JSAFE_Exception var27) {
            throw new CMPException("CMPResponseCommon.verifyProtection: unable to verify PBM.", var27);
         } finally {
            if (var17 != null) {
               var17.clearSensitiveData();
            }

         }

         return var20;
      }
   }

   private static Certificate[] decodeExtraCerts(OfContainer var0) throws CMPException {
      if (!var0.dataPresent) {
         return null;
      } else {
         int var1 = var0.getContainerCount();
         Certificate[] var2 = new Certificate[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            try {
               ASN1Container var4 = var0.containerAt(var3);
               var2[var3] = new X509Certificate(var4.data, var4.dataOffset, 0);
            } catch (ASN_Exception var5) {
               throw new CMPException("CMPResponseCommon.decodeExtraCerts: unable to get an element container of OfContainer.", var5);
            } catch (CertificateException var6) {
               throw new CMPException("CMPResponseCommon.decodeExtraCerts: unable to decode a certificate.", var6);
            }
         }

         return var2;
      }
   }

   private static boolean oidsEqual(byte[] var0, int var1, int var2, byte[] var3, int var4, int var5) {
      if (var2 != var5) {
         return false;
      } else {
         for(int var6 = 0; var6 < var2; ++var6) {
            if (var0[var1 + var6] != var3[var4 + var6]) {
               return false;
            }
         }

         return true;
      }
   }
}
