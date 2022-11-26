package com.rsa.certj.provider.pki.cmp;

import com.rsa.asn1.ASN1;
import com.rsa.asn1.ASN1Container;
import com.rsa.asn1.ASN_Exception;
import com.rsa.asn1.ChoiceContainer;
import com.rsa.asn1.EncodedContainer;
import com.rsa.asn1.EndContainer;
import com.rsa.asn1.IntegerContainer;
import com.rsa.asn1.OctetStringContainer;
import com.rsa.asn1.OfContainer;
import com.rsa.asn1.SequenceContainer;
import com.rsa.certj.CertJ;
import com.rsa.certj.CertJException;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.cert.Certificate;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.X509Certificate;
import com.rsa.certj.crmf.CRMFException;
import com.rsa.certj.crmf.CertTemplate;
import com.rsa.certj.crmf.Control;
import com.rsa.certj.crmf.Controls;
import com.rsa.certj.crmf.EncryptedValue;
import com.rsa.certj.crmf.PKIPublicationInfo;
import com.rsa.certj.crmf.ProtocolEncryptionKey;
import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIStatusInfo;
import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_PublicKey;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.StringTokenizer;

/** @deprecated */
public class CMPCertResponseCommon extends CMPResponseCommon {
   private PKIPublicationInfo publicationInfo;

   /** @deprecated */
   protected CMPCertResponseCommon(int var1, PKIHeader var2, PKIStatusInfo var3) {
      super(var1, var2, var3);
   }

   /** @deprecated */
   public PKIPublicationInfo getPublicationInfo() {
      return this.publicationInfo;
   }

   /** @deprecated */
   protected static CMPCertResponseCommon berDecodeBody(int var0, PKIHeader var1, byte[] var2, int var3, CMPProtectInfo var4, CMPRequestCommon var5, CertJ var6) throws CMPException {
      if (!(var5 instanceof CMPCertRequestCommon)) {
         throw new CMPException("CMPCertResponseCommon.berDecodeBody: request should be an instance of CMPCertRequestCommon.");
      } else {
         CMPInitResponseMessage var7 = new CMPInitResponseMessage(var1, (PKIStatusInfo)null);
         return var7.decode(var0, var1, var2, var3, var4, (CMPCertRequestCommon)var5, var6);
      }
   }

   private CMPCertResponseCommon decode(int var1, PKIHeader var2, byte[] var3, int var4, CMPProtectInfo var5, CMPCertRequestCommon var6, CertJ var7) throws CMPException {
      a var8 = new a(var3, var4, 10551296 | var1, var7, var6, var5);
      b var9 = var8.c[0];
      Object var10;
      switch (var1) {
         case 1:
            var10 = new CMPInitResponseMessage(var2, var9.b);
            break;
         case 3:
            var10 = new CMPCertResponseMessage(var2, var9.b);
            break;
         default:
            throw new CMPException("CMPCertResponseCommon.berDecode: message type(" + var1 + ") not supported.");
      }

      c var11 = var9.c;
      if (var11 != null) {
         ((CMPCertResponseCommon)var10).setCertifiedKeyPairInfo(var11.a(), var11.b(), var11.e);
      }

      ((CMPCertResponseCommon)var10).setCACerts(var8.b);
      ((CMPCertResponseCommon)var10).setRegInfo(var9.d);
      return (CMPCertResponseCommon)var10;
   }

   private void setCertifiedKeyPairInfo(Certificate var1, JSAFE_PrivateKey var2, PKIPublicationInfo var3) {
      this.setCertificate(var1);
      this.setPrivateKey(var2);
      this.publicationInfo = var3;
   }

   private final class c {
      private Certificate b;
      private EncryptedValue c;
      private EncryptedValue d;
      private PKIPublicationInfo e;

      private c(byte[] var2, int var3, int var4, CertJ var5, CMPCertRequestCommon var6, CMPProtectInfo var7) throws CMPException {
         SequenceContainer var8 = new SequenceContainer(var4);
         ChoiceContainer var9 = new ChoiceContainer(0);
         EncodedContainer var10 = new EncodedContainer(10498048);
         EncodedContainer var11 = new EncodedContainer(10498049);
         EndContainer var12 = new EndContainer();
         EncodedContainer var13 = new EncodedContainer(10563584);
         EncodedContainer var14 = new EncodedContainer(10563585);
         EndContainer var15 = new EndContainer();
         ASN1Container[] var16 = new ASN1Container[]{var8, var9, var10, var11, var12, var13, var14, var15};

         try {
            ASN1.berDecode(var2, var3, var16);
         } catch (ASN_Exception var23) {
            throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.CertifiedKeyPair: decoding CertifiedKeyPair failed.", var23);
         }

         if (var10.dataPresent) {
            try {
               this.b = new X509Certificate(var10.data, var10.dataOffset, 10498048);
            } catch (CertificateException var22) {
               throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.CertifiedKeyPair: decoding X509Certificate failed.", var22);
            }
         }

         JSAFE_PrivateKey var17;
         if (var11.dataPresent) {
            var17 = this.a(var6, var7);

            try {
               this.c = new EncryptedValue(var5, (JSAFE_PublicKey)null, var17);
               this.c.decodeEncryptedValue(var11.data, var11.dataOffset, 10498049);
            } catch (CRMFException var21) {
               throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.CertifiedKeyPair: unable to decode EncryptedValue for encryptedCert.", var21);
            }
         }

         if (var13.dataPresent) {
            var17 = this.b(var6, var7);

            try {
               this.c = new EncryptedValue(var5, (JSAFE_PublicKey)null, var17);
               this.c.decodeEncryptedValue(var11.data, var11.dataOffset, 10498048);
            } catch (CRMFException var20) {
               throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.CertifiedKeyPair: unable to decode EncryptedValue for encrypted private key.", var20);
            }
         }

         if (var14.dataPresent) {
            try {
               Control var24 = Control.getInstance(var14.data, var14.dataOffset, 10563585);
               if (!(var24 instanceof PKIPublicationInfo)) {
                  throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.CertifiedKeyPair: decoded data is not a PKIPublicationInfo object.");
               }

               this.e = (PKIPublicationInfo)var24;
            } catch (CRMFException var19) {
               throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.CertifiedKeyPair: unable to decode PKIPublicationInfo.", var19);
            }
         }

      }

      private JSAFE_PrivateKey a(CMPCertRequestCommon var1, CMPProtectInfo var2) throws CMPException {
         try {
            CertTemplate var4 = var1.getCertTemplate();
            JSAFE_PublicKey var5 = var4.getSubjectPublicKey();
            if (var5 == null) {
               throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.findEncryptionPrivateKey: unable to find public key in CertTemplate.");
            } else {
               DatabaseService var6 = var2.getDatabase();
               if (var6 == null) {
                  throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.findEncryptionPrivateKey: database in protectinfo is null.");
               } else {
                  JSAFE_PrivateKey var3 = var6.selectPrivateKeyByPublicKey(var5);
                  if (var3 == null) {
                     throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.findEncryptionPrivateKey: private key for the certificate returned should be provided in protectInfo.");
                  } else {
                     return var3;
                  }
               }
            }
         } catch (CertJException var7) {
            throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.findEncryptionPrivateKey: ", var7);
         }
      }

      private JSAFE_PrivateKey b(CMPCertRequestCommon var1, CMPProtectInfo var2) throws CMPException {
         Controls var3 = var1.getControls();
         ProtocolEncryptionKey var4 = (ProtocolEncryptionKey)var3.getControlByType(5);
         if (var4 == null) {
            throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.findProtEncKey: request does not contain ProtEncKey control.");
         } else {
            JSAFE_PublicKey var5;
            try {
               var5 = var4.getSubjectPublicKey();
            } catch (CRMFException var9) {
               throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.findProtEncKey: unable to extract public key from ProtEncKey control.", var9);
            }

            DatabaseService var6 = var2.getDatabase();
            if (var6 == null) {
               throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.findProtEncKey: database in protectinfo is null.");
            } else {
               try {
                  return var6.selectPrivateKeyByPublicKey(var5);
               } catch (CertJException var8) {
                  throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.findProtEncKey: searching private key failed.", var8);
               }
            }
         }
      }

      private Certificate a() throws CMPException {
         if (this.b != null) {
            return this.b;
         } else if (this.c == null) {
            return null;
         } else {
            try {
               return new X509Certificate(this.c.getDecryptedValue(), 0, 0);
            } catch (CertificateException var2) {
               throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.getCertificate: unable to decode a certificate.");
            }
         }
      }

      private JSAFE_PrivateKey b() throws CMPException {
         if (this.d == null) {
            return null;
         } else {
            try {
               return this.d.getPrivateKey();
            } catch (CRMFException var2) {
               throw new CMPException("CMPCertResponseCommon$CertifiedKeyPair.getPrivateKey: unable to get decrypted private key.", var2);
            }
         }
      }

      // $FF: synthetic method
      c(byte[] var2, int var3, int var4, CertJ var5, CMPCertRequestCommon var6, CMPProtectInfo var7, Object var8) throws CMPException {
         this(var2, var3, var4, var5, var6, var7);
      }
   }

   private final class b {
      private PKIStatusInfo b;
      private c c;
      private Properties d;

      private b(byte[] var2, int var3, int var4, CertJ var5, CMPCertRequestCommon var6, CMPProtectInfo var7) throws CMPException {
         SequenceContainer var8 = new SequenceContainer(var4);
         IntegerContainer var9 = new IntegerContainer(0);
         EncodedContainer var10 = new EncodedContainer(12288);
         EncodedContainer var11 = new EncodedContainer(77824);
         OctetStringContainer var12 = new OctetStringContainer(65536);
         EndContainer var13 = new EndContainer();
         ASN1Container[] var14 = new ASN1Container[]{var8, var9, var10, var11, var12, var13};

         try {
            ASN1.berDecode(var2, var3, var14);
         } catch (ASN_Exception var18) {
            throw new CMPException("CMPCertResponseCommon$CertResponse.CertResponse: decoding CertResponse failed.", var18);
         }

         try {
            var9.getValueAsInt();
         } catch (ASN_Exception var17) {
            throw new CMPException("CMPCertResponseCommon$CertResponse.CertResponse: unable to get CertResponse.certReqId as int.", var17);
         }

         try {
            this.b = new PKIStatusInfo(var10.data, var10.dataOffset, 0);
         } catch (PKIException var16) {
            throw new CMPException("CMPCertResponseCommon$CertResponse.CertResponse: unable to instantiate PKIStatusInfo.", var16);
         }

         if (var11.dataPresent) {
            this.c = CMPCertResponseCommon.this.new c(var11.data, var11.dataOffset, 65536, var5, var6, var7);
         }

         if (var12.dataPresent && var12.dataLen != 0) {
            this.d = this.a(new String(var12.data, var12.dataOffset, var12.dataLen));
         }

      }

      private Properties a(String var1) throws CMPException {
         Properties var2 = new Properties();
         StringTokenizer var3 = new StringTokenizer(var1, "%");

         while(var3.hasMoreTokens()) {
            String var4 = var3.nextToken();
            StringTokenizer var5 = new StringTokenizer(var4, "?");
            if (!var5.hasMoreTokens()) {
               throw new CMPException("CMPCertResponseCommon$CertResponse.CertResponse: ill-formed rspInfo (? char not found).");
            }

            String var6 = var5.nextToken();
            if (!var5.hasMoreTokens()) {
               throw new CMPException("CMPCertResponseCommon$CertResponse.CertResponse: ill-formed rspInfo (nothing follows ? char).");
            }

            String var7 = var5.nextToken();

            try {
               var2.put(this.b(var6), this.b(var7));
            } catch (Exception var9) {
               throw new CMPException("CMPCertResponseCommon$CertResponse.CertResponse: Properties.put failed.", var9);
            }
         }

         return var2;
      }

      private String b(String var1) throws UnsupportedEncodingException {
         return URLDecoder.decode(var1, "Cp1252");
      }

      // $FF: synthetic method
      b(byte[] var2, int var3, int var4, CertJ var5, CMPCertRequestCommon var6, CMPProtectInfo var7, Object var8) throws CMPException {
         this(var2, var3, var4, var5, var6, var7);
      }
   }

   private final class a {
      private Certificate[] b;
      private b[] c;

      private a(byte[] var2, int var3, int var4, CertJ var5, CMPCertRequestCommon var6, CMPProtectInfo var7) throws CMPException {
         OfContainer var8;
         OfContainer var9;
         try {
            SequenceContainer var10 = new SequenceContainer(var4);
            var8 = new OfContainer(10551297, 12288, new EncodedContainer(12288));
            var9 = new OfContainer(0, 12288, new EncodedContainer(12288));
            EndContainer var11 = new EndContainer();
            ASN1Container[] var12 = new ASN1Container[]{var10, var8, var9, var11};
            ASN1.berDecode(var2, var3, var12);
         } catch (ASN_Exception var16) {
            throw new CMPException("CMPCertResponseCommon$CertRepMessage.CertRepMessage: decoding CertRepMessage failed.", var16);
         }

         int var17;
         int var18;
         ASN1Container var19;
         if (var8.dataPresent) {
            var17 = var8.getContainerCount();
            this.b = new Certificate[var17];

            for(var18 = 0; var18 < var17; ++var18) {
               try {
                  var19 = var8.containerAt(var18);
                  this.b[var18] = new X509Certificate(var19.data, var19.dataOffset, 0);
               } catch (ASN_Exception var14) {
                  throw new CMPException("CMPCertResponseCommon$CertRepMessage.CertRepMessage: unable to obtain component of caPubs.", var14);
               } catch (CertificateException var15) {
                  throw new CMPException("CMPCertResponseCommon$CertRepMessage.CertRepMessage: unable to decode a certificate.", var15);
               }
            }
         }

         var17 = var9.getContainerCount();
         this.c = new b[var17];

         for(var18 = 0; var18 < var17; ++var18) {
            try {
               var19 = var9.containerAt(var18);
               this.c[var18] = CMPCertResponseCommon.this.new b(var19.data, var19.dataOffset, 0, var5, var6, var7);
            } catch (ASN_Exception var13) {
               throw new CMPException("CMPCertResponseCommon$CertRepMessage.CertRepMessage: unable to obtain component of response.", var13);
            }
         }

      }

      // $FF: synthetic method
      a(byte[] var2, int var3, int var4, CertJ var5, CMPCertRequestCommon var6, CMPProtectInfo var7, Object var8) throws CMPException {
         this(var2, var3, var4, var5, var6, var7);
      }
   }
}
